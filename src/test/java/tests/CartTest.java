package tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import basetest.BaseTest;
import pages.CartPage;
import pages.LoginPage;

public class CartTest extends BaseTest {

    private CartPage cart;
    private LoginPage login;

    @BeforeMethod(alwaysRun = true)
    @Parameters("user")
    public void openBrowser(String username) {

        logger.info("Initializing page objects");

        cart = new CartPage(driver);
        login = new LoginPage(driver);

        logger.info("Logging in with user: " + username);

        login.login(username, config.getProperty("default_password"));
    }
    
    @Test(groups= {"smoke"})
    public void TC25_VerifyCartNavigation() {
    	Assert.assertTrue(cart.cartNav(), "Cart Navigation Not Working!!");
    }

    // Verify product details in cart match product catalog
    @Test(groups = {"regression"})
    public void TC26_VerifyCartItemData() {

        logger.info("Validating product details in cart");

        Assert.assertTrue(cart.validateItemDetails(),
                "Product details in cart do not match catalog");
    }

    // Verify continue shopping button navigates back to catalog
    @Test(groups = {"regression"})
    public void TC31_VerifyContinueShopping() {

        logger.info("Validating Continue Shopping functionality");

        Assert.assertTrue(cart.checkContinueShoppingBtn(),
                "Continue shopping did not navigate back to product catalog");
    }

    // Verify checkout behavior when cart is empty
    @Test(groups = {"regression"})
    public void TC33_VerifyCheckoutWithEmptyCart() {

        logger.info("Clicking checkout with empty cart");

        cart.clickCheckOutBtn();

        Assert.assertFalse(cart.isCheckoutInfoDisplayed(),
                "Checkout page should not appear for empty cart");
    }

    // Verify checkout page opens when product added
    @Test(groups = {"regression"})
    public void shouldNavigateToCheckoutWhenProductAdded() {

        logger.info("Adding product to cart");

        cart.addProductToCart();

        logger.info("Navigating to checkout");

        cart.clickCheckOutBtn();

        Assert.assertTrue(cart.isCheckoutInfoDisplayed(),
                "Checkout page not displayed after adding product");
    }
    
    @Test(groups= {""}) // //////////////////////To be implemented
    public void TC28_VerifyCartPersistAfterLogout() {
    	logger.info("Adding product to cart");

        cart.addProductToCart();

        cart.openCart();

    }

    // Verify cart items persist after refresh
    @Test(groups = {"regression"})
    public void TC29_VerifyCartPersist() {

        logger.info("Adding product to cart");

        cart.addProductToCart();

        cart.openCart();

        logger.info("Refreshing page to validate persistence");

        driver.navigate().refresh();

        Assert.assertTrue(cart.isCartNotEmpty(),
                "Cart items did not persist after refresh");
    }

    // Verify removing item updates cart badge
    @Test(groups = {"regression"})
    public void TC30_VerifyRemoveItem() {

        logger.info("Adding product to cart");

        cart.addProductToCart();

        cart.openCart();

        int currentBadge = cart.getCartBadge();

        logger.info("Removing item from cart");

        cart.removeItem();

        int updatedBadge = cart.getCartBadge();

        Assert.assertEquals(updatedBadge, currentBadge - 1,
                "Cart badge did not decrease after removing item");
    }
}