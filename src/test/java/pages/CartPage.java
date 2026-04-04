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

public class CartPage {
	WebDriver driver;
	WebDriverWait wait;
	
	public CartPage(WebDriver driver){
		this.driver = driver;
		PageFactory.initElements(driver, this);
		wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	}
	
	@FindBy(id="continue-shopping")
	public WebElement continueShoppingBtn;
	
	@FindBy(id="remove-sauce-labs-backpack")
	WebElement removeBtn;
	
	@FindBy(id="checkout")
	WebElement checkOutBtn;
	
	By checkOutInfo = By.cssSelector("div.checkout_info");
	By catalog = By.id("inventory_container");
	
	By cartList = By.className("cart_item");
	By cartProductTitle = By.className("inventory_item_name");
	By cartProductDesc = By.className("inventory_item_desc");
	By cartProductPrice = By.className("inventory_item_price");
	By cartItemRemoveBtn = By.id("remove-sauce-labs-backpack");
	By cartBadge = By.cssSelector("span.shopping_cart_badge");
	
	// Product details from product catalog
	public By proName = By.cssSelector("div.inventory_item_name");
	public By proDesc = By.cssSelector("div.inventory_item_desc");
	public By proPrice = By.cssSelector("div.inventory_item_price");
	public By proAddCartBtn = By.id("add-to-cart-sauce-labs-backpack");
	
	@FindBy(css="a.shopping_cart_link")
	WebElement cartBtn;
	
	@FindBy(css="div.inventory_list div.inventory_item")
	public List<WebElement> productList;
	
	public boolean validateItemDetails() {
		WebElement firstProduct = productList.get(0);
		
		String title = firstProduct.findElement(proName).getText();
		String desc = firstProduct.findElement(proDesc).getText();
		String price = (firstProduct.findElement(proPrice).getText()).replace("$", "");
		
		firstProduct.findElement(proAddCartBtn).click();
		
		cartBtn.click();
		
		wait.until(ExpectedConditions.elementToBeClickable(continueShoppingBtn));
		
		String cartItemTitle = driver.findElement(cartProductTitle).getText();
		String cartItemDesc = driver.findElement(cartProductDesc).getText();
		String cartItemPrice = (driver.findElement(cartProductPrice).getText()).replace("$", "");
		
		System.out.println(title + " " + cartItemTitle);
		System.out.println(desc + " " + cartItemDesc);
		System.out.println(price + " " + cartItemPrice);
		
		return title.equals(cartItemTitle) && desc.equals(cartItemDesc) && price.equals(cartItemPrice);
	}
	
	public void addProductToCart() {
		productList.getFirst().findElement(proAddCartBtn).click();
	}
	
	public boolean isCartNotEmpty() {
	    return driver.findElements(cartList).size() > 0;
	}
	
	public String getProductTitle() {
		return productList.getFirst().findElement(proName).getText();
	}
	public String getProductDesc() {
		return productList.getFirst().findElement(proDesc).getText();
	}
	
	public String getProductPrice() {
		return (productList.getFirst().findElement(proPrice).getText()).replace("$", "");
	}
	
	public boolean checkContinueShoppingBtn() {
		cartBtn.click();
		wait.until(ExpectedConditions.elementToBeClickable(continueShoppingBtn)).click();
		
		return driver.findElement(catalog).isDisplayed();
	}
	
	public void clickCheckOutBtn() {
		cartBtn.click();
		wait.until(ExpectedConditions.elementToBeClickable(checkOutBtn)).click();
	}
	
	public void openCart() {
		cartBtn.click();
	}
	
	public void removeItem() {
		List<WebElement> removeBtns = driver.findElements(By.id("remove-sauce-labs-backpack"));
		
		for(WebElement removeBtn : removeBtns) {
			removeBtn.click();
		}
	}
	
	public boolean cartNav() {
		return checkContinueShoppingBtn();
	}
	
	public int getCartBadge() {
		List<WebElement> badge = driver.findElements(cartBadge);
		int badgeCnt = 0;
		if(badge.size()>0) {
			badgeCnt = Integer.parseInt( badge.get(0).getText());
			return badgeCnt;
		}
		
		return 0;
	}
	
	public boolean isCheckoutInfoDisplayed() {
	    return wait.until(ExpectedConditions
	        .visibilityOfElementLocated(checkOutInfo)).isDisplayed();
	}
}
