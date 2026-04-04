//package pages;
//
//import java.time.Duration;
//
//import org.openqa.selenium.By;
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.support.ui.WebDriverWait;
//
//public class LoginPage {
//	WebDriver driver;
//	WebDriverWait wait;
//	public LoginPage(WebDriver driver) {
//		this.driver = driver;
//		wait = new WebDriverWait(driver, Duration.ofSeconds(10));
//	}
//	
//	// Locators
//	By username = By.id("user-name");
//	By password = By.id("password");
//	By loginBtn = By.id("login-button");
//	By error = By.xpath("//h3[@data-test='error']");
//	By catalog = By.xpath("//*[contains(text(),'Products')]");
//	
//	// Methods
//	public void fillUserData(String userN, String pass) {
//		driver.findElement(username).sendKeys(userN);
//		driver.findElement(password).sendKeys(pass);
//		clickLogin();
//	}
//	public void fillUser(String userN) {
//		driver.findElement(username).sendKeys(userN);
//	}
//	
//	public void fillPass(String pass) {
//		driver.findElement(password).sendKeys(pass);
//	}
//	
//	public void clickLogin() {
//		driver.findElement(loginBtn).click();
//	}
//	
//	public boolean productCatalog() {
//		return driver.findElements(catalog).size()>0;
//	}
//	
//	public boolean errorDisp() {
//		return driver.findElements(error).size()>0;
//	}
//	
//	public void errMsg() {
//		if(errorDisp()) {
//			System.out.println("Error message displayed: " + driver.findElement(error).getText());
//		}else {
//			System.out.println("Error not displayed");
//		}
//	}
//}


package pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {

    private WebDriver driver;
    private WebDriverWait wait;

    // ===== Locators (PRIVATE – never expose) =====
    private By username = By.id("user-name");
    private By password = By.id("password");
    private By loginBtn = By.id("login-button");
    private By error = By.cssSelector("h3[data-test='error']");
    private By productsHeader = By.xpath("//span[text()='Products']");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // ===== Actions =====

    public void enterUsername(String user) {
        driver.findElement(username).clear();
        driver.findElement(username).sendKeys(user);
    }

    public void enterPassword(String pass) {
        driver.findElement(password).clear();
        driver.findElement(password).sendKeys(pass);
    }

    public void clickLogin() {
        driver.findElement(loginBtn).click();
    }

    public void login(String user, String pass) {
        enterUsername(user);
        enterPassword(pass);
        clickLogin();
    }

    // ===== Validations =====

    public boolean isLoginSuccessful() {
        return driver.findElements(productsHeader).size() > 0;
    }

    public boolean isErrorDisplayed() {
        return driver.findElements(error).size() > 0;
    }

    public String getErrorMessage() {
        return driver.findElement(error).getText();
    }

    public boolean areFieldsEmpty() {
        return driver.findElement(username).getAttribute("value").isEmpty()
                && driver.findElement(password).getAttribute("value").isEmpty();
    }

    public String getPasswordFieldType() {
        return driver.findElement(password).getAttribute("type");
    }
}
