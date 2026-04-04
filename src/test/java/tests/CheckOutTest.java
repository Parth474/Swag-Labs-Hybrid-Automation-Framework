package tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import basetest.BaseTest;
import pages.CartPage;
import pages.CheckOutPage;
import pages.LoginPage;

public class CheckOutTest extends BaseTest {

    private CheckOutPage checkout;
    private CartPage cart;
    private LoginPage login;

    @BeforeMethod(alwaysRun = true)
    @Parameters("user")
    public void checkOutSetup(String username) {

        logger.info("===== TEST SETUP STARTED =====");

        checkout = new CheckOutPage(driver);
        cart = new CartPage(driver);
        login = new LoginPage(driver);

        logger.info("Logging into application with user: " + username);

        login.login(username, config.getProperty("default_password"));

        logger.info("Login successful");

    }

    private void startCheckoutFlow() {

        logger.info("Adding product to cart");

        cart.addProductToCart();

        logger.info("Opening cart");

        checkout.openCart();

        logger.info("Clicking checkout");

        checkout.clickCheckout();
    }

    private void startCheckoutWithMultipleProducts() {
        logger.info("Adding multiple products to cart");

        checkout.addMultipleProductsToCart();

        logger.info("Opening cart");

        checkout.openCart();

        logger.info("Clicking checkout");

        checkout.clickCheckout();
    }

    @Test(groups= {"smoke"})
    public void TC36_VerifyCancelButton() {

        logger.info("===== TEST: Verify Cancel Button =====");

        startCheckoutFlow();
        logger.info("Clicking cancel button");
        checkout.cancelCheckout();
        Assert.assertTrue(checkout.isCartPageDisplayed(),
                "Cancel button did not navigate back to cart page");

        logger.info("Cancel button verified successfully");
    }

    @Test
    public void TC37_verifyContinueWithoutInfo() {

        logger.info("===== TEST: Continue Without Info =====");

        startCheckoutFlow();

        logger.info("Clicking continue without filling details");

        checkout.clickContinue();

        Assert.assertTrue(checkout.isErrorDisplayed(),
                "Error message not displayed when checkout info missing");

        logger.info("Error message displayed as expected");
    }

    @Test(groups= {"regression", "sanity"})
    public void TC38_verifyContinueWithInfo() {

        logger.info("===== TEST: Continue With Checkout Info =====");

        startCheckoutFlow();

        logger.info("Filling checkout information");

        checkout.fillCheckoutInfo("Parth", "Dangare", "444001");

        checkout.clickContinue();

        Assert.assertTrue(checkout.isOverviewPageDisplayed(),
                "Overview page not displayed after entering checkout info");

        logger.info("Successfully navigated to overview page");
    }

    @Test
    public void TC35_VerifyItemTotalCalculation() {

        logger.info("===== TEST: Item Total Calculation =====");

        startCheckoutWithMultipleProducts();

        checkout.fillCheckoutInfo("Parth", "Dangare", "444001");

        checkout.clickContinue();

        double itemTotal = checkout.calculateItemTotal();
        double subTotal = checkout.getSubTotal();

        logger.info("Calculated Item Total: " + itemTotal);
        logger.info("Displayed SubTotal: " + subTotal);

        Assert.assertEquals(itemTotal, subTotal,
                "Item total calculation mismatch");

        logger.info("Item total calculation verified successfully");
    }

    @Test
    public void TC35_VerifyCartTotalCalculation() {

        logger.info("===== TEST: Cart Total Calculation =====");

        startCheckoutWithMultipleProducts();

        checkout.fillCheckoutInfo("Parth", "Dangare", "444001");

        checkout.clickContinue();

        double subTotal = checkout.getSubTotal();
        double tax = checkout.getTax();
        double total = checkout.getTotal();

        double expectedTotal = subTotal + tax;

        logger.info("Subtotal: " + subTotal);
        logger.info("Tax: " + tax);
        logger.info("Displayed Total: " + total);

        Assert.assertEquals(expectedTotal, total,
                "Cart total calculation incorrect");

        logger.info("Cart total calculation verified successfully");
    }

    @Test(groups= {"smoke"})
    public void TC39_VerifyFinishOrder() {

        logger.info("===== TEST: Finish Order =====");

        startCheckoutFlow();

        checkout.fillCheckoutInfo("Parth", "Dangare", "444001");

        checkout.clickContinue();

        logger.info("Clicking finish order");

        checkout.finishOrder();

        Assert.assertTrue(checkout.isSuccessMessageDisplayed(),
                "Order success message not displayed");

        logger.info("Order completed successfully");
    }

    @Test(groups= {"smoke"})
    public void TC41_verifyBackHomeButton() {

        logger.info("===== TEST: Back Home Button =====");

        startCheckoutFlow();

        checkout.fillCheckoutInfo("Parth", "Dangare", "444001");

        checkout.clickContinue();

        checkout.finishOrder();

        logger.info("Clicking Back Home button");

        checkout.clickBackHomeBtn();

        Assert.assertTrue(checkout.isCatalogDisplayed(), "Did not navigate back to product catalog");
        logger.info("Back Home navigation verified successfully");
    }

    @Test(groups = {"regression", "sanity"})
    public void TC32_VerifyCartBadgeCount() {

        logger.info("===== TEST: Cart Badge Count =====");

        startCheckoutWithMultipleProducts();

        checkout.fillCheckoutInfo("Parth", "Dangare", "444001");

        checkout.clickContinue();

        int itemCount = checkout.getCartItemCount();
        int badgeCount = checkout.getCartBadgeCount();

        logger.info("Items in checkout: " + itemCount);
        logger.info("Cart badge count: " + badgeCount);

        Assert.assertEquals(itemCount, badgeCount,
                "Cart badge count does not match items in cart");

        logger.info("Cart badge verified successfully");
    }
}