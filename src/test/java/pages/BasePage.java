package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;
    protected final String BASE_URL = "https://www.saucedemo.com";

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Step("Ожидание присутствия элемента по локатору: {locator}")
    protected WebElement waitForPresence(By locator) {
        return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    @Step("Ожидание видимости элемента по локатору: {locator}")
    protected WebElement waitForVisibility(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    @Step("Ожидание кликабельности элемента по локатору: {locator}")
    protected WebElement waitForClickability(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    @Step("Ожидание исчезновения элемента по локатору: {locator}")
    protected void waitForInvisibility(By locator) {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    @Step("Ожидание URL, содержащего: {fragment}")
    protected void waitForUrlContaining(String fragment) {
        wait.until(ExpectedConditions.urlContains(fragment));
    }

    @Step("Ожидание непустого текста элемента по локатору: {locator}")
    protected String waitForNonEmptyText(By locator) {
        return wait.until(d -> {
            List<WebElement> elements = d.findElements(locator);
            if (elements.isEmpty()) {
                return null;
            }
            String text = elements.get(0).getText();
            return text == null || text.isBlank() ? null : text;
        });
    }

    @Step("Отправка текста в элемент")
    protected void sendKeys(By locator, String text) {
        WebElement element = waitForVisibility(locator);
        element.clear();
        element.sendKeys(text);
    }

    @Step("Клик по элементу")
    protected void click(By locator) {
        WebElement element = waitForClickability(locator);
        element.click();
    }

    @Step("Получение текста элемента")
    protected String getText(By locator) {
        return waitForVisibility(locator).getText();
    }

    @Step("Проверка видимости элемента")
    protected boolean isElementDisplayed(By locator) {
        try {
            return waitForVisibility(locator).isDisplayed();
        } catch (TimeoutException | NoSuchElementException e) {
            return false;
        }
    }

    @Step("Получение скриншота страницы")
    public byte[] takeScreenshot() {
        if (driver instanceof TakesScreenshot) {
            return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
        }
        return new byte[0];
    }

    @Step("Получение исходного кода страницы")
    public String getPageSource() {
        return driver.getPageSource();
    }
}
