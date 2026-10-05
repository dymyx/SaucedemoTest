package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CheckoutPage extends BasePage {
    // Checkout: Information
    private final By firstNameInput = By.id("first-name");
    private final By lastNameInput = By.id("last-name");
    private final By postalCodeInput = By.id("postal-code");
    private final By continueButton = By.id("continue");
    private final By errorMessage = By.cssSelector("[data-test='error']");

    // Checkout: Overview
    private final By overviewTitle = By.cssSelector("[data-test='title']");
    private final By totalPrice = By.cssSelector("[data-test='total-label']");
    private final By finishButton = By.id("finish");

    // Order Complete
    private final By completeMessage = By.cssSelector("[data-test='complete-header']");

    public CheckoutPage(WebDriver driver) {
        super(driver);
    }

    @Step("Заполнение имени: {firstName}")
    public void fillFirstName(String firstName) {
        sendKeys(firstNameInput, firstName);
    }

    @Step("Заполнение фамилии: {lastName}")
    public void fillLastName(String lastName) {
        sendKeys(lastNameInput, lastName);
    }

    @Step("Заполнение почтового кода: {postalCode}")
    public void fillPostalCode(String postalCode) {
        sendKeys(postalCodeInput, postalCode);
    }

    @Step("Заполнение информации оформления: {firstName} {lastName} {postalCode}")
    public void fillCheckoutInfo(String firstName, String lastName, String postalCode) {
        fillFirstName(firstName);
        fillLastName(lastName);
        fillPostalCode(postalCode);
    }

    @Step("Клик по кнопке 'Continue'")
    public void clickContinue() {
        click(continueButton);
    }

    @Step("Проверка видимости сообщения об ошибке")
    public boolean isErrorMessageDisplayed() {
        return isElementDisplayed(errorMessage);
    }

    @Step("Получение текста сообщения об ошибке")
    public String getErrorMessage() {
        try {
            return getText(errorMessage);
        } catch (Exception e) {
            return "";
        }
    }

    @Step("Проверка видимости страницы обзора заказа")
    public boolean isOverviewPageDisplayed() {
        try {
            String title = getText(overviewTitle);
            return title.contains("Overview") || title.contains("Checkout");
        } catch (Exception e) {
            return false;
        }
    }

    @Step("Получение общей стоимости")
    public String getTotalPrice() {
        return getText(totalPrice);
    }

    @Step("Клик по кнопке 'Finish'")
    public void clickFinish() {
        click(finishButton);
    }

    @Step("Проверка отображения сообщения о завершении заказа")
    public boolean isOrderCompleteDisplayed() {
        return isElementDisplayed(completeMessage);
    }

    @Step("Получение сообщения о завершении заказа")
    public String getCompleteMessage() {
        try {
            return getText(completeMessage);
        } catch (Exception e) {
            return "";
        }
    }
}
