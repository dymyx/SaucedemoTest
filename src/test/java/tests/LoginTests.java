package tests;

import base.BaseTest;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.ProductsPage;

@Epic("Авторизация")
@Feature("Логин в приложение")
public class LoginTests extends BaseTest {

    @Story("Позитивная авторизация")
    @Severity(SeverityLevel.CRITICAL)
    @Owner("dymyx")
    @Test(description = "Позитивная авторизация с standard_user")
    public void testPositiveLogin() {
        LoginPage loginPage = new LoginPage(driver);
        ProductsPage productsPage = new ProductsPage(driver);

        loginPage.openLoginPage();
        loginPage.login("standard_user", "secret_sauce");

        String currentUrl = loginPage.getCurrentURL();
        Assert.assertTrue(currentUrl.contains("/inventory.html"),
            "URL не содержит /inventory.html. Текущий URL: " + currentUrl);

        Assert.assertTrue(productsPage.isProductsPageDisplayed(),
            "Страница товаров не отображается");
        Assert.assertEquals(productsPage.getPageTitle(), "Products",
            "Заголовок страницы не совпадает");
    }

    @Story("Негативная авторизация")
    @Severity(SeverityLevel.CRITICAL)
    @Owner("dymyx")
    @Test(description = "Негативная авторизация - заблокированный пользователь")
    public void testBlockedUserLogin() {
        LoginPage loginPage = new LoginPage(driver);

        loginPage.openLoginPage();
        loginPage.login("locked_out_user", "secret_sauce");

        Assert.assertTrue(loginPage.isErrorMessageDisplayed(),
            "Сообщение об ошибке не отображается");

        String errorMessage = loginPage.getErrorMessage();
        Assert.assertTrue(errorMessage.contains("Epic sadface") || errorMessage.contains("locked"),
            "Сообщение об ошибке не содержит ожидаемого текста. Текст: " + errorMessage);
    }

    @Story("Негативная авторизация")
    @Severity(SeverityLevel.NORMAL)
    @Owner("dymyx")
    @Test(description = "Авторизация с неверным паролем")
    public void testLoginWithWrongPassword() {
        LoginPage loginPage = new LoginPage(driver);

        loginPage.openLoginPage();
        loginPage.login("standard_user", "wrong_password");

        Assert.assertTrue(loginPage.isErrorMessageDisplayed(),
            "Сообщение об ошибке не отображается");
        String errorMessage = loginPage.getErrorMessage();
        Assert.assertTrue(errorMessage.toLowerCase().contains("username and password"),
            "Сообщение об ошибке не содержит ожидаемого текста");
    }

    @Story("Негативная авторизация")
    @Severity(SeverityLevel.NORMAL)
    @Owner("dymyx")
    @Test(description = "Авторизация с пустым полем имени пользователя")
    public void testLoginWithEmptyUsername() {
        LoginPage loginPage = new LoginPage(driver);

        loginPage.openLoginPage();
        loginPage.enterPassword("secret_sauce");
        loginPage.clickLoginButton();

        Assert.assertTrue(loginPage.isErrorMessageDisplayed(),
            "Сообщение об ошибке не отображается");
        String errorMessage = loginPage.getErrorMessage();
        Assert.assertTrue(errorMessage.toLowerCase().contains("username"),
            "Сообщение об ошибке должно упоминать имя пользователя");
    }
}
