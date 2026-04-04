package tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import basetest.BaseTest;
import pages.LoginPage;
import pages.ProductDetailPage;

public class ProductPageTest extends BaseTest {
    private ProductDetailPage detail;
    private LoginPage login;

    @BeforeMethod(alwaysRun = true)
    @Parameters("user")
    public void productPageSetUp(String username) {

        logger.info("===== Product Page Test Setup =====");

        detail = new ProductDetailPage(driver);
        login = new LoginPage(driver);

        logger.info("Logging in with user: " + username);

        login.login(username, config.getProperty("default_password"));

        logger.info("Login successful");
    }

    @Test(priority = 1, groups = {"regression", "sanity", "product"})
    public void TC42_VerifyFirstProductDetails() {

        logger.info("Running test: verifyFirstProductDetails");

        Assert.assertTrue(
                detail.verifyProductDetails(0),
                "First product details mismatch between catalog and detail page"
        );

        logger.info("First product details verified successfully");
    }

    @Test(priority = 2, groups = {"regression", "product"})
    public void TC42_VerifyLastProductDetails() {

        logger.info("Running test: verifyLastProductDetails");

        int lastIndex = detail.getProductCount() - 1;

        Assert.assertTrue(
                detail.verifyProductDetails(lastIndex),
                "Last product details mismatch between catalog and detail page"
        );

        logger.info("Last product details verified successfully");
    }

    @Test(priority = 3, groups = {"smoke", "regression", "navigation"})
    public void VerifyBackButton() {

        logger.info("Running test: verifyBackButton");

        Assert.assertTrue(
                detail.verifyBackNavigation(0),
                "Back button navigation failed"
        );

        logger.info("Back button navigation verified successfully");
    }

    @Test(priority = 4, groups = {"smoke", "regression", "cart", "sanity"})
    public void TC43_VerifyAddToCart() {
        logger.info("Running test: verifyAddToCart");
        Assert.assertTrue(
                detail.verifyAddToCart(),
                "Add to cart functionality failed"
        );
        logger.info("Add to cart verified successfully");
    }

    @Test(priority = 5, groups = {"smoke", "regression", "cart", "sanity"})
    public void TC44_VerifyRemoveFromCart() {

        logger.info("Running test: verifyRemoveFromCart");

        Assert.assertTrue(
                detail.verifyRemoveFromCart(),
                "Remove from cart functionality failed"
        );

        logger.info("Remove from cart verified successfully");
    }
}