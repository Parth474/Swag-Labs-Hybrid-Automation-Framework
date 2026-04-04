package pages;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ProductDetailPage {

    private WebDriver driver;
    private WebDriverWait wait;

    public ProductDetailPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    // ---------- LOCATORS ----------

    private By productCards = By.cssSelector("div.inventory_item");
    private By productTitle = By.className("inventory_item_name");

    private By detailPageContainer =
            By.cssSelector("div.inventory_details_container");

    private By detailTitle =
            By.className("inventory_details_name");

    private By cartBadge =
            By.cssSelector("span.shopping_cart_badge");

    // ---------- WEB ELEMENTS ----------

    @FindBy(css = "button[id^='add-to-cart']")
    private WebElement addToCartBtn;

    @FindBy(css = "button[id^='remove']")
    private WebElement removeBtn;

    @FindBy(id = "back-to-products")
    private WebElement backBtn;

    // ---------- PRODUCT LIST ----------

    public int getProductCount() {

        return driver.findElements(productCards).size();
    }

    public List<WebElement> getProductCards() {

        return driver.findElements(productCards);
    }

    // ---------- PRODUCT NAVIGATION ----------

    public void openProduct(int index) {

        WebElement card = getProductCards().get(index);

        card.findElement(productTitle).click();

        wait.until(ExpectedConditions
                .visibilityOfElementLocated(detailPageContainer));
    }

    public boolean verifyProductDetails(int index) {

        WebElement card = getProductCards().get(index);

        String catalogTitle =
                card.findElement(productTitle).getText();

        card.findElement(productTitle).click();

        wait.until(ExpectedConditions
                .visibilityOfElementLocated(detailPageContainer));

        String detailTitleText =
                driver.findElement(detailTitle).getText();

        return catalogTitle.equals(detailTitleText);
    }

    public boolean isOnDetailPage() {

        return wait.until(ExpectedConditions
                .visibilityOfElementLocated(detailPageContainer))
                .isDisplayed();
    }

    public boolean verifyBackNavigation(int index) {

        WebElement card = getProductCards().get(index);

        String productName =
                card.findElement(productTitle).getText();

        card.findElement(productTitle).click();

        wait.until(ExpectedConditions
                .elementToBeClickable(backBtn)).click();

        wait.until(ExpectedConditions
                .visibilityOfElementLocated(productCards));

        return driver.findElement(
                By.xpath("//*[text()='" + productName + "']"))
                .isDisplayed();
    }

    // ---------- CART BADGE ----------

    public int getBadgeNumber() {

        List<WebElement> badges =
                driver.findElements(cartBadge);

        if (!badges.isEmpty()) {
            return Integer.parseInt(badges.get(0).getText());
        }

        return 0;
    }

    // ---------- ADD TO CART ----------

    public void clickAddToCartBtn() {

        wait.until(ExpectedConditions
                .elementToBeClickable(addToCartBtn)).click();
    }

    public void clickRemoveFromCart() {

        wait.until(ExpectedConditions
                .elementToBeClickable(removeBtn)).click();
    }

    public boolean verifyAddToCart() {

        openProduct(0);

        int before = getBadgeNumber();

        clickAddToCartBtn();

        wait.until(driver -> getBadgeNumber() == before + 1);

        return getBadgeNumber() == before + 1;
    }

    public boolean verifyRemoveFromCart() {

        openProduct(0);

        int before = getBadgeNumber();

        clickAddToCartBtn();

        wait.until(driver -> getBadgeNumber() == before + 1);

        clickRemoveFromCart();

        wait.until(driver -> getBadgeNumber() == before);

        return getBadgeNumber() == before;
    }
}