package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class CartPage extends BasePage {
    private final By cartItems = By.cssSelector("[data-test='inventory-item']");
    private final By checkoutButton = By.id("checkout");
    private final By cartItemName = By.cssSelector("[data-test='inventory-item-name']");
    private final By cartItemPrice = By.cssSelector("[data-test='inventory-item-price']");

    public CartPage(WebDriver driver) {
        super(driver);
    }

    @Step("Ожидание загрузки страницы корзины")
    public void waitForCartPageLoaded() {
        waitForUrlContaining("/cart.html");
        waitForPresence(cartItems);
    }

    @Step("Проверка открытия страницы корзины")
    public boolean isCartPageDisplayed() {
        return driver.getCurrentUrl().contains("/cart.html");
    }

    @Step("Получение количества товаров в корзине")
    public int getCartItemCount() {
        waitForCartPageLoaded();
        return driver.findElements(cartItems).size();
    }

    @Step("Получение названия товара с индексом {index}")
    public String getCartItemName(int index) {
        waitForCartPageLoaded();
        List<WebElement> items = driver.findElements(cartItems);
        return items.get(index).findElement(cartItemName).getText();
    }

    @Step("Получение цены товара с индексом {index}")
    public String getCartItemPrice(int index) {
        waitForCartPageLoaded();
        List<WebElement> items = driver.findElements(cartItems);
        return items.get(index).findElement(cartItemPrice).getText();
    }

    @Step("Получение списка всех названий товаров в корзине")
    public List<String> getAllCartItemNames() {
        waitForCartPageLoaded();
        return driver.findElements(cartItemName).stream()
            .map(WebElement::getText)
            .toList();
    }

    @Step("Клик по кнопке оформления заказа")
    public void clickCheckout() {
        click(checkoutButton);
    }

    @Step("Проверка видимости кнопки оформления заказа")
    public boolean isCheckoutButtonDisplayed() {
        return isElementDisplayed(checkoutButton);
    }
}
