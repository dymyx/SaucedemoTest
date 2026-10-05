package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage {
    private final By usernameInput = By.id("user-name");
    private final By passwordInput = By.id("password");
    private final By loginButton = By.id("login-button");
    private final By errorMessage = By.cssSelector("[data-test='error']");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    @Step("Открытие страницы логина")
    public void openLoginPage() {
        driver.navigate().to(BASE_URL);
    }

    @Step("Ввод имени пользователя: {username}")
    public void enterUsername(String username) {
        sendKeys(usernameInput, username);
    }

    @Step("Ввод пароля: {password}")
    public void enterPassword(String password) {
        sendKeys(passwordInput, password);
    }

    @Step("Клик по кнопке логина")
    public void clickLoginButton() {
        click(loginButton);
    }

    @Step("Вход с учётными данными: {username} / {password}")
    public void login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLoginButton();
    }

    @Step("Получение текущего URL")
    public String getCurrentURL() {
        return driver.getCurrentUrl();
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
}
