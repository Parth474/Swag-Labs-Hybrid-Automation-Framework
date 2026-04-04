package tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import basetest.BaseTest;
import dataprovider.TestDataProvider;
import pages.LoginPage;
import pages.ProductCatalogPage;
import utils.ExcelUtil;

public class LoginTest extends BaseTest {

    private LoginPage login;
    private ProductCatalogPage catalog;

    String excelPath = System.getProperty("user.dir")+ "/src/test/resources/testData/TestData.xlsx";

    @BeforeMethod(alwaysRun = true)
    public void loginSetUp() {
        login = new LoginPage(driver);
        catalog = new ProductCatalogPage(driver);
    }

    // ================= VALID LOGIN =================
    @Test(groups = {"smoke", "regression"})
    @Parameters("user")
    public void TC01_validLogin(String username) {

        logger.info("===== VALID LOGIN TEST STARTED =====");

        login.login(username, config.getProperty("default_password"));

        Assert.assertTrue(login.isLoginSuccessful(), "Login failed for valid user");
        logger.info("Login successful for user: " + username);
    }

    // ================= DATA DRIVEN =================
    @Test(dataProvider = "getLoginData", dataProviderClass = TestDataProvider.class, groups= {"regression", "sanity"})
    public void TC02_To_TC10_loginDataDrivenTest(String username, String password, String expectedResult, int row) {

        logger.info("Running login test for: " + username);

        login.login(username, password);

        String actualResult;

        if (login.isLoginSuccessful()) {
            actualResult = "Pass";
        } else {
            actualResult = "Fail";
        }

        // Assertion based on expected result
        Assert.assertEquals(actualResult, expectedResult,
                "Mismatch for user: " + username);

        // Write back result
        ExcelUtil.updateResult(excelPath,
                "loginData",
                row,
                3,
                actualResult);

        logger.info("Test completed for: " + username +
                " | Expected: " + expectedResult +
                " | Actual: " + actualResult);
    }

    // ================= Locked User =================
    @Test(groups = {"regression", "smoke"})
    public void TC11_lockedUserTest() {

        logger.info("===== LOCKED USER TEST =====");

        login.login("locked_out_user", config.getProperty("default_password"));

        Assert.assertTrue(login.isErrorDisplayed(),
                "Error message not displayed for locked user");

        logger.info("Error displayed: " + login.getErrorMessage());
    }

    // ================= REFRESH =================
    @Test(groups = {"regression"})
    public void TC14_refreshClearsFieldsTest() {

        logger.info("===== REFRESH FIELD RESET TEST =====");

        login.enterUsername("test");
        login.enterPassword("test");

        driver.navigate().refresh();

        Assert.assertTrue(login.areFieldsEmpty(),
                "Fields are not empty after refresh");

        logger.info("Fields cleared successfully after refresh.");
    }

    // ================= PASSWORD MASK =================
    @Test(groups = {"regression"})
    public void TC12_passwordMaskTest() {

        logger.info("===== PASSWORD MASK TEST =====");

        Assert.assertEquals(login.getPasswordFieldType(),
                "password",
                "Password field is not masked");

        logger.info("Password field is masked correctly.");
    }
    
    @Test(groups= {"regression", "functional"})
    public void TC14_LogoutFunctionality() {
    	login.login("standard_user", "secret_sauce");
    	catalog.openHamburgerMenu();
    	Assert.assertTrue(catalog.logout(), "Logout failed");
    }
}
