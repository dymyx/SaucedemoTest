package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CheckoutPage {
    private WebDriver driver;
    private WebDriverWait wait;

    // Locators - Checkout: Your Information
    private final By firstNameInput = By.id("first-name");
    private final By lastNameInput = By.id("last-name");
    private final By postalCodeInput = By.id("postal-code");
    private final By continueButton = By.id("continue");
    private final By errorMessage = By.cssSelector("[data-test='error']");

    // Locators - Checkout: Overview
    private final By overviewTitle = By.cssSelector("[data-test='title']");
    private final By totalPrice = By.cssSelector("[data-test='total-label']");
    private final By finishButton = By.id("finish");

    // Locators - Order Complete
    private final By completeMessage = By.cssSelector("[data-test='complete-header']");

    public CheckoutPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void fillFirstName(String firstName) {
        driver.findElement(firstNameInput).sendKeys(firstName);
    }

    public void fillLastName(String lastName) {
        driver.findElement(lastNameInput).sendKeys(lastName);
    }

    public void fillPostalCode(String postalCode) {
        driver.findElement(postalCodeInput).sendKeys(postalCode);
    }

    public void fillCheckoutInfo(String firstName, String lastName, String postalCode) {
        fillFirstName(firstName);
        fillLastName(lastName);
        fillPostalCode(postalCode);
    }

    public void clickContinue() {
        driver.findElement(continueButton).click();
    }

    public boolean isErrorMessageDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(errorMessage)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public String getErrorMessage() {
        try {
            return driver.findElement(errorMessage).getText();
        } catch (Exception e) {
            return "";
        }
    }

    public boolean isOverviewPageDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(overviewTitle));
            String title = driver.findElement(overviewTitle).getText();
            return title.contains("Overview") || title.contains("Checkout");
        } catch (Exception e) {
            return false;
        }
    }

    public String getTotalPrice() {
        return driver.findElement(totalPrice).getText();
    }

    public void clickFinish() {
        driver.findElement(finishButton).click();
    }

    public boolean isOrderCompleteDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(completeMessage)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public String getCompleteMessage() {
        try {
            return driver.findElement(completeMessage).getText();
        } catch (Exception e) {
            return "";
        }
    }
}
