package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

public class ProductsPage extends BasePage {
    private static final int MAX_ADD_ATTEMPTS = 3;

    private final By pageTitle = By.cssSelector("[data-test='title']");
    private final By cartBadge = By.cssSelector("[data-test='shopping-cart-badge']");
    private final By cartLink = By.cssSelector("[data-test='shopping-cart-link']");
    private final By sortDropdown = By.cssSelector("[data-test='product-sort-container']");
    private final By productNamesByXpath = By.cssSelector("[data-test='inventory-item-name']");
    private final By productPricesByXpath = By.cssSelector("[data-test='inventory-item-price']");

    public ProductsPage(WebDriver driver) {
        super(driver);
    }

    @Step("Проверка видимости страницы товаров")
    public boolean isProductsPageDisplayed() {
        return isElementDisplayed(pageTitle);
    }

    @Step("Получение заголовка страницы")
    public String getPageTitle() {
        return getText(pageTitle);
    }

    @Step("Добавление товара в корзину: {productName}")
    public void addProductToCart(String productName) {
        for (int attempt = 1; attempt <= MAX_ADD_ATTEMPTS; attempt++) {
            waitForClickability(cartButton(productName, "add-to-cart")).click();
            if (isProductInCart(productName)) {
                return;
            }
        }
        throw new IllegalStateException("Не удалось добавить товар в корзину: " + productName);
    }

    private By cartButton(String productName, String state) {
        return By.xpath("//div[@data-test='inventory-item'][.//div[@data-test='inventory-item-name']"
            + "[normalize-space(.)='" + productName + "']]//button[contains(@data-test, '" + state + "')]");
    }

    private boolean isProductInCart(String productName) {
        return wait.until(d -> d.findElements(cartButton(productName, "remove")).isEmpty()
            ? null
            : Boolean.TRUE) != null;
    }

    @Step("Получение количества товаров в корзине")
    public int getCartItemCount() {
        if (driver.findElements(cartBadge).isEmpty()) {
            return 0;
        }
        return Integer.parseInt(waitForNonEmptyText(cartBadge));
    }

    @Step("Открытие корзины")
    public void openCart() {
        click(cartLink);
        waitForUrlContaining("/cart.html");
    }

    @Step("Сортировка товаров по: {sortOption}")
    public void sortProducts(String sortOption) {
        Select select = new Select(driver.findElement(sortDropdown));
        select.selectByValue(sortOption);
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @Step("Получение списка названий товаров")
    public List<String> getProductNames() {
        return driver.findElements(productNamesByXpath).stream()
            .map(WebElement::getText)
            .toList();
    }

    @Step("Получение списка цен товаров")
    public List<String> getProductPrices() {
        return driver.findElements(productPricesByXpath).stream()
            .map(WebElement::getText)
            .toList();
    }

    @Step("Получение списка цен товаров (как Double)")
    public List<Double> getProductPricesAsDouble() {
        return getProductPrices().stream()
            .map(price -> Double.parseDouble(price.replace("$", "")))
            .toList();
    }
}
