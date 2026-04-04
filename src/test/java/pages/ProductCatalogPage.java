package pages;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ProductCatalogPage {

    private WebDriver driver;
    private WebDriverWait wait;

    private By productName = By.cssSelector(".inventory_item_name");
    private By productDesc = By.cssSelector(".inventory_item_desc");
    private By productPrice = By.cssSelector(".inventory_item_price");

    private By detailTitle = By.cssSelector(".inventory_details_name");
    private By detailDesc = By.cssSelector(".inventory_details_desc");
    private By detailPrice = By.cssSelector(".inventory_details_price");
    
    @FindBy(css="div.inventory_list div.inventory_item")
    private List<WebElement> productList;

    @FindBy(xpath="//button[contains(@class, 'btn_inventory')]")
    private List<WebElement> addCartButtons;

    @FindBy(css="span.shopping_cart_badge")
    private WebElement cartCount;

    @FindBy(id="react-burger-menu-btn")
    private WebElement hamburgerBtn;

    @FindBy(id="react-burger-cross-btn")
    private WebElement closeHamburgerBtn;

    @FindBy(id="logout_sidebar_link")
    private WebElement logoutBtn;

    @FindBy(css="select.product_sort_container")
    private WebElement filterDropdown;

    public ProductCatalogPage(WebDriver driver) {

        this.driver = driver;
        PageFactory.initElements(driver, this);

        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public boolean isProductListVisible() {

        wait.until(ExpectedConditions.visibilityOfAllElements(productList));
        return productList.size() > 0;
    }

    public boolean verifyAddToCartFunctionality() {

        int expectedCount = 0;

        for (WebElement btn : addCartButtons) {
            btn.click();
            expectedCount++;
        }

        int actual = Integer.parseInt(cartCount.getText());

        return expectedCount == actual;
    }

    public boolean verifyFirstProductDetails() {

        WebElement firstProduct = productList.get(0);

        String title = firstProduct.findElement(productName).getText();
        String desc = firstProduct.findElement(productDesc).getText();
        String price = firstProduct.findElement(productPrice).getText();

        firstProduct.findElement(productName).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(detailTitle));

        String detailTitleText = driver.findElement(detailTitle).getText();
        String detailDescText = driver.findElement(detailDesc).getText();
        String detailPriceText = driver.findElement(detailPrice).getText();

        driver.navigate().back();

        return title.equals(detailTitleText)
                && desc.equals(detailDescText)
                && price.equals(detailPriceText);
    }

    public void clickFirstProductName() {

        wait.until(ExpectedConditions.visibilityOfAllElements(productList));

        productList.get(0)
                .findElement(productName)
                .click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(detailTitle));
    }
    
    public void clickFirstProductAddToCartBtn() {

        wait.until(ExpectedConditions.visibilityOfAllElements(addCartButtons));

        addCartButtons.get(0).click();
    }
    
    public void addMultipleProductsToCart() {

        wait.until(ExpectedConditions.visibilityOfAllElements(addCartButtons));

        int totalProducts = addCartButtons.size();

        for (int i = 0; i < totalProducts / 2; i++) {
            addCartButtons.get(i).click();
        }
    }
    
    public void openHamburgerMenu() {

        wait.until(ExpectedConditions.elementToBeClickable(hamburgerBtn)).click();
        wait.until(ExpectedConditions.visibilityOf(closeHamburgerBtn));
    }

    public void closeHamburgerMenu() {

        closeHamburgerBtn.click();
        wait.until(ExpectedConditions.invisibilityOf(closeHamburgerBtn));
    }

    public boolean isHamburgerMenuOpened() {

        return closeHamburgerBtn.isDisplayed();
    }

    public boolean isHamburgerMenuClosed() {

        return !closeHamburgerBtn.isDisplayed();
    }
    
    public boolean logout() {

        wait.until(ExpectedConditions.elementToBeClickable(logoutBtn)).click();

        return wait.until(ExpectedConditions
                .visibilityOfElementLocated(By.cssSelector("div.login_wrapper")))
                .isDisplayed();
    }

    public boolean isOnCatalogPage() { 
    	return wait.until(
    			ExpectedConditions
    			.visibilityOfElementLocated(By.id("inventory_container")))
    			.isDisplayed(); 
    }
    
    public List<String> getFilterValues() {

        Select select = new Select(filterDropdown);
        return select.getOptions()
                .stream()
                .map(e -> e.getAttribute("value"))
                .toList();
    }

    public void applyFilter(String value) {

        Select select = new Select(filterDropdown);
        select.selectByValue(value);
    }

    public boolean verifySorting(String option) {

        if (option.equals("az") || option.equals("za")) {

            List<String> actual = driver.findElements(productName)
                    .stream()
                    .map(WebElement::getText)
                    .toList();

            List<String> sorted = new ArrayList<>(actual);

            if (option.equals("az"))
                Collections.sort(sorted);
            else
                Collections.sort(sorted, Collections.reverseOrder());

            return actual.equals(sorted);
        }

        if (option.equals("lohi") || option.equals("hilo")) {

            List<Double> actual = driver.findElements(productPrice)
                    .stream()
                    .map(e -> Double.parseDouble(
                            e.getText().replaceAll("[^0-9.]", "")))
                    .toList();

            List<Double> sorted = new ArrayList<>(actual);

            if (option.equals("lohi"))
                Collections.sort(sorted);
            else
                Collections.sort(sorted, Collections.reverseOrder());

            return actual.equals(sorted);
        }

        return false;
    }
}