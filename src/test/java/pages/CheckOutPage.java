package pages;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CheckOutPage {

    WebDriver driver;
    WebDriverWait wait;
    CartPage cart;

    public CheckOutPage(WebDriver driver) {
        this.driver = driver;
        this.cart = new CartPage(driver);
        PageFactory.initElements(driver, this);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @FindBy(id="shopping_cart_container")
    WebElement cartBtn;

    @FindBy(css="button#checkout")
    WebElement checkoutBtn;

    @FindBy(css="span.shopping_cart_badge")
    List<WebElement> cartBadges;

    @FindBy(id="first-name")
    WebElement firstName;

    @FindBy(id="last-name")
    WebElement lastName;

    @FindBy(id="postal-code")
    WebElement postalCode;

    @FindBy(css="div.error h3")
    WebElement errorMsg;

    @FindBy(id="cancel")
    WebElement cancelBtn;

    @FindBy(id="continue")
    WebElement continueBtn;

    @FindBy(css="div.cart_item_label")
    List<WebElement> cartItems;

    @FindBy(css="div.summary_subtotal_label")
    WebElement subTotal;

    @FindBy(css="div.summary_tax_label")
    WebElement totalTax;

    @FindBy(css="div.summary_total_label")
    WebElement total;

    @FindBy(css="button#finish")
    WebElement finishBtn;

    @FindBy(id="back-to-products")
    WebElement backHomeBtn;

    @FindBy(css="h2.complete-header")
    WebElement orderSuccessMsg;

    @FindBy(css="div.inventory_item")
    List<WebElement> catalogItems;

    // ---------- Cart Actions ----------

    public void openCart() {
        cartBtn.click();
    }

    public void clickCheckout() {
        checkoutBtn.click();
    }

    public void clickContinue() {
        continueBtn.click();
    }

    public void cancelCheckout() {
        cancelBtn.click();
    }

    public void finishOrder() {
    	wait.until(ExpectedConditions.elementToBeClickable(finishBtn));
        finishBtn.click();
    }

    public void clickBackHomeBtn() {
    	wait.until(ExpectedConditions.elementToBeClickable(backHomeBtn));
        backHomeBtn.click();
    }

    // ---------- Product Actions ----------

    public void addMultipleProductsToCart() {

        for (int i = 0; i < catalogItems.size() / 2; i++) {
            catalogItems.get(i)
                    .findElement(By.xpath(".//button[text()='Add to cart']"))
                    .click();
        }
    }

    // ---------- Checkout Info ----------

    public void fillCheckoutInfo(String first, String last, String code) {

        wait.until(ExpectedConditions.visibilityOf(firstName));

        firstName.sendKeys(first);
        lastName.sendKeys(last);
        postalCode.sendKeys(code);
    }

    // ---------- Navigation Checks ----------

    public boolean isOverviewPageDisplayed() {

        return wait.until(ExpectedConditions
                .visibilityOfElementLocated(By.cssSelector("div.summary_info")))
                .isDisplayed();
    }

    public boolean isCartPageDisplayed() {

        return wait.until(ExpectedConditions
                .visibilityOfElementLocated(By.xpath("//span[contains(text(),'Your Cart')]")))
                .isDisplayed();
    }

    public boolean isCatalogDisplayed() {

        return wait.until(ExpectedConditions
                .visibilityOfElementLocated(By.id("inventory_container")))
                .isDisplayed();
    }

    public boolean isSuccessMessageDisplayed() {

        return wait.until(ExpectedConditions.visibilityOf(orderSuccessMsg))
                .isDisplayed();
    }

    public boolean isErrorDisplayed() {

        return errorMsg.isDisplayed();
    }

    // ---------- Price Calculations ----------

    public double calculateItemTotal() {

        double totalItemPrice = 0;

        for (WebElement item : cartItems) {

            String price = item.findElement(By.cssSelector("div.inventory_item_price"))
                    .getText()
                    .replace("$", "");

            totalItemPrice += Double.parseDouble(price);
        }

        return totalItemPrice;
    }

    public double getSubTotal() {
    	wait.until(ExpectedConditions.visibilityOf(subTotal));
        return Double.parseDouble(subTotal.getText().replaceAll("[^0-9.]", ""));
    }

    public double getTax() {
    	wait.until(ExpectedConditions.visibilityOf(totalTax));
        return Double.parseDouble(totalTax.getText().replaceAll("[^0-9.]", ""));
    }

    public double getTotal() {
    	wait.until(ExpectedConditions.visibilityOf(total));
        return Double.parseDouble(total.getText().replaceAll("[^0-9.]", ""));
    }

    // ---------- Cart Badge ----------

    public int getCartBadgeCount() {

        if (cartBadges.size() == 0)
            return 0;

        return Integer.parseInt(cartBadges.get(0).getText());
    }

    public int getCartItemCount() {
        return cartItems.size();
    }

    // ---------- Product Details ----------

    public List<String> getOverviewProductNames() {

        List<String> names = new ArrayList<>();

        for (WebElement item : cartItems) {
            names.add(item.findElement(By.cssSelector("div.inventory_item_name")).getText());
        }

        return names;
    }
}