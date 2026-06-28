package basetest;

import java.io.FileInputStream;
import java.net.URL;
import java.time.Duration;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import org.openqa.selenium.remote.RemoteWebDriver;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

public class BaseTest {

    public WebDriver driver;
    public Logger logger;
    protected Properties config;

    @BeforeMethod(alwaysRun = true)
    @Parameters({"browser", "os"})
    public void setUp(@Optional("chrome") String browser,
                    @Optional("windows") String opSys) throws Exception {

        logger = LogManager.getLogger(this.getClass());

        String gridUrl = "http://localhost:4444";

        // Load config file
        FileInputStream file = new FileInputStream(System.getProperty("user.dir") + "/src/test/resources/config.properties");

        config = new Properties();
        config.load(file);

        String executionEnv = config.getProperty("execution_env");

        if (executionEnv.equalsIgnoreCase("remote")) {

            if (browser.equalsIgnoreCase("chrome")) {

                ChromeOptions options = createChromeOptions();

                if (opSys.equalsIgnoreCase("linux")) {
                    options.setPlatformName("Linux");
                }

                driver = new RemoteWebDriver(new URL(gridUrl), options);

            } else if (browser.equalsIgnoreCase("firefox")) {

                FirefoxOptions options = new FirefoxOptions();

                if (opSys.equalsIgnoreCase("linux")) {
                    options.setPlatformName("Linux");
                }

                driver = new RemoteWebDriver(new URL(gridUrl), options);

            } else {
                throw new InputMismatchException("Invalid browser requested");
            }

        } else {

            if (browser.equalsIgnoreCase("chrome")) {

                ChromeOptions options = createChromeOptions();
                driver = new ChromeDriver(options);

            } else if (browser.equalsIgnoreCase("firefox")) {

                driver = new FirefoxDriver();

            }else if(browser.equalsIgnoreCase("edge")) {
                driver = new EdgeDriver();
            } else {
                throw new Exception("Invalid browser for local execution");
            }
        }

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();

        driver.get(config.getProperty("testUrl"));
    }


    // ---------- CHROME OPTIONS ----------
    private ChromeOptions createChromeOptions() {

        ChromeOptions options = new ChromeOptions();

        options.addArguments("--disable-notifications");
        options.addArguments("--disable-infobars");
        options.addArguments("--disable-popup-blocking");

        options.setExperimentalOption("excludeSwitches",
                new String[]{"enable-automation"});

        options.setExperimentalOption("useAutomationExtension", false);

        Map<String, Object> prefs = new HashMap<>();

        prefs.put("credentials_enable_service", false);
        prefs.put("profile.password_manager_enabled", false);
        prefs.put("profile.password_manager_leak_detection", false);

        options.setExperimentalOption("prefs", prefs);

        return options;
    }


    @AfterMethod(alwaysRun = true)
    public void closeBrowser() {

        if (driver != null) {
            driver.quit();
        }
    }
}
