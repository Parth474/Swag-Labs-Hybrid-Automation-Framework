package tests;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import basetest.BaseTest;
import pages.LoginPage;
import pages.ProductCatalogPage;

public class ProductCatalogTest extends BaseTest {

    private ProductCatalogPage catalog;
    private LoginPage login;

    @BeforeMethod(alwaysRun=true)
    @Parameters("user")
    public void catalogSetUp(@Optional("standard_user") String username) {

        login = new LoginPage(driver);
        catalog = new ProductCatalogPage(driver);

        login.login(username, config.getProperty("default_password"));
    }

    @Test(groups= {"smoke", "regression"})
    public void TC21_shouldDisplayProductList() {

        logger.info("Verifying product list visibility");

        Assert.assertTrue(catalog.isProductListVisible(),
                "Product list is not visible on catalog page");
    }

    @Test(groups= {"regression", "sanity"})
    public void TC22_shouldAddAllProductsToCart() {

        logger.info("Addin  g all products to cart");

        Assert.assertTrue(catalog.verifyAddToCartFunctionality(),
                "Cart count does not match added products");
    }

    @Test(groups= {"regression", "sanity"})
    public void TC23_shouldMatchProductDetailsBetweenCatalogAndDetailsPage() {

        logger.info("Validating product information consistency");

        Assert.assertTrue(catalog.verifyFirstProductDetails(),
                "Product details mismatch between catalog and details page");
    }

    @Test
    public void TC24_shouldOpenHamburgerMenu() {

        logger.info("Opening hamburger menu");

        catalog.openHamburgerMenu();

        Assert.assertTrue(catalog.isHamburgerMenuOpened(),
                "Hamburger menu did not open");
    }

    @Test
    public void TC24_shouldCloseHamburgerMenu() {

        logger.info("Closing hamburger menu");

        catalog.openHamburgerMenu();
        catalog.closeHamburgerMenu();

        Assert.assertTrue(catalog.isHamburgerMenuClosed(),
                "Hamburger menu did not close");
    }

    @Test(groups= {"regression", "sanity"})
    public void TC15_TO_TC20_shouldSortProductsCorrectly() {

        logger.info("Verifying all sorting options");

        List<String> filterValues = catalog.getFilterValues();

        for (String value : filterValues) {
            catalog.applyFilter(value);
            Assert.assertTrue(catalog.verifySorting(value), "Sorting failed for option: " + value);
        }
    }
    
    @Test(groups={"smoke" ,"regression"})
    public void TC14_shouldLogoutSuccessfully() {

        logger.info("Logging out from application");

        catalog.openHamburgerMenu();

        Assert.assertTrue(catalog.logout(),
                "Logout failed");
    }
}