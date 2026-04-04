package tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import basetest.BaseTest;
import pages.CartPage;
import pages.CheckOutPage;
import pages.LoginPage;
import pages.ProductCatalogPage;
import pages.ProductDetailPage;

public class ValidEndToEndTest extends BaseTest {

    private LoginPage login;
    private ProductCatalogPage catalog;
    private ProductDetailPage detail;
    private CartPage cart;
    private CheckOutPage checkout;

    @BeforeMethod(alwaysRun = true)
    public void e2eSetUp() {

        logger.info("===== E2E Test Setup Started =====");

        login = new LoginPage(driver);
        catalog = new ProductCatalogPage(driver);
        detail = new ProductDetailPage(driver);
        cart = new CartPage(driver);
        checkout = new CheckOutPage(driver);

        logger.info("Page objects initialized");
    }

    // Common methods
    private void loginWithValidUser() {

        logger.info("Logging in with valid user");

        login.login(
                "standard_user",
                config.getProperty("default_password")
        );

        Assert.assertTrue(
                catalog.isOnCatalogPage(),
                "Login failed - catalog page not displayed"
        );

        logger.info("Login successful");
    }

    private void completeCheckoutFlow() {

        logger.info("Proceeding to checkout");

        checkout.clickCheckout();

        logger.info("Filling checkout information");

        checkout.fillCheckoutInfo(
                config.getProperty("firstname"),
                config.getProperty("lastname"),
                config.getProperty("zipcode")
        );

        checkout.clickContinue();

        logger.info("Finishing order");

        checkout.finishOrder();

        Assert.assertTrue(
                checkout.isSuccessMessageDisplayed(),
                "Order was not completed successfully"
        );

        logger.info("Order completed successfully");
    }

    // ---------------------------
    // E2E TEST SCENARIOS
    // ---------------------------

    @Test(groups = "e2e", priority = 1)
    public void e2e_AddProductFromCatalog() {

        logger.info("===== E2E Scenario: Add Product From Catalog =====");

        loginWithValidUser();

        logger.info("Adding product from catalog");

        catalog.clickFirstProductAddToCartBtn();

        cart.openCart();

        completeCheckoutFlow();

        checkout.clickBackHomeBtn();

        Assert.assertTrue(
                checkout.isCatalogDisplayed(),
                "Back Home button did not navigate to catalog page"
        );

        logger.info("E2E scenario completed successfully");
    }

    @Test(groups = "e2e", priority = 2)
    public void e2e_AddProductFromDetailPage() {

        logger.info("===== E2E Scenario: Add Product From Detail Page =====");

        loginWithValidUser();

        catalog.clickFirstProductName();

        Assert.assertTrue(
                detail.isOnDetailPage(),
                "Not navigated to product detail page"
        );

        logger.info("Adding product from detail page");

        detail.clickAddToCartBtn();

        cart.openCart();

        completeCheckoutFlow();

        checkout.clickBackHomeBtn();

        Assert.assertTrue(
                checkout.isCatalogDisplayed(),
                "Back Home button did not navigate to catalog page"
        );

        logger.info("E2E scenario completed successfully");
    }

    @Test(groups = "e2e", priority = 3)
    public void e2e_MultipleProductsCheckout() {

        logger.info("===== E2E Scenario: Multiple Products Checkout =====");

        loginWithValidUser();

        logger.info("Adding multiple products to cart");

        catalog.addMultipleProductsToCart();

        cart.openCart();

        completeCheckoutFlow();

        checkout.clickBackHomeBtn();

        Assert.assertTrue(
                checkout.isCatalogDisplayed(),
                "Back Home button did not navigate to catalog page"
        );

        logger.info("E2E scenario completed successfully");
    }
}