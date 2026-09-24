package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class ProductsPage {
    private WebDriver driver;
    private WebDriverWait wait;

    // Locators
    private final By pageTitle = By.cssSelector("[data-test='title']");
    private final By cartBadge = By.cssSelector("[data-test='shopping-cart-badge']");
    private final By cartLink = By.cssSelector("[data-test='shopping-cart-link']");
    private final By addToCartButtonTemplate = By.xpath("//button[contains(@data-test, 'add-to-cart')]");
    private final By sortDropdown = By.cssSelector("[data-test='product-sort-container']");
    private final By productNamesByXpath = By.cssSelector("[data-test='inventory-item-name']");
    private final By productPricesByXpath = By.cssSelector("[data-test='inventory-item-price']");

    public ProductsPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public boolean isProductsPageDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(pageTitle)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public String getPageTitle() {
        return driver.findElement(pageTitle).getText();
    }

    public void addProductToCart(String productName) {
        WebElement button = driver.findElement(
            By.xpath("//div[@data-test='inventory-item'][.//div[@data-test='inventory-item-name'][normalize-space(.)='"
                + productName + "']]//button[contains(@data-test, 'add-to-cart')]")
        );
        button.click();
    }

    public int getCartItemCount() {
        try {
            String badgeText = driver.findElement(cartBadge).getText();
            return Integer.parseInt(badgeText);
        } catch (Exception e) {
            return 0;
        }
    }

    public void openCart() {
        driver.findElement(cartLink).click();
    }

    public void sortProducts(String sortOption) {
        Select select = new Select(driver.findElement(sortDropdown));
        select.selectByValue(sortOption);
        // Даем время на обновление списка
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public List<String> getProductNames() {
        return driver.findElements(productNamesByXpath).stream()
            .map(WebElement::getText)
            .toList();
    }

    public List<String> getProductPrices() {
        return driver.findElements(productPricesByXpath).stream()
            .map(WebElement::getText)
            .toList();
    }

    public List<Double> getProductPricesAsDouble() {
        return getProductPrices().stream()
            .map(price -> Double.parseDouble(price.replace("$", "")))
            .toList();
    }
}
