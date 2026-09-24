package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class CartPage {
    private WebDriver driver;
    private WebDriverWait wait;

    // Locators
    private final By cartItems = By.cssSelector("[data-test='inventory-item']");
    private final By checkoutButton = By.id("checkout");
    private final By cartItemName = By.cssSelector("[data-test='inventory-item-name']");
    private final By cartItemPrice = By.cssSelector("[data-test='inventory-item-price']");

    public CartPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public int getCartItemCount() {
        try {
            return driver.findElements(cartItems).size();
        } catch (Exception e) {
            return 0;
        }
    }

    public String getCartItemName(int index) {
        try {
            List<WebElement> items = driver.findElements(cartItems);
            return items.get(index).findElement(cartItemName).getText();
        } catch (Exception e) {
            return "";
        }
    }

    public String getCartItemPrice(int index) {
        try {
            List<WebElement> items = driver.findElements(cartItems);
            return items.get(index).findElement(cartItemPrice).getText();
        } catch (Exception e) {
            return "";
        }
    }

    public List<String> getAllCartItemNames() {
        return driver.findElements(cartItemName).stream()
            .map(WebElement::getText)
            .toList();
    }

    public void clickCheckout() {
        driver.findElement(checkoutButton).click();
    }

    public boolean isCheckoutButtonDisplayed() {
        try {
            return driver.findElement(checkoutButton).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}
