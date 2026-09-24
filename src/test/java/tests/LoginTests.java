package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.ProductsPage;

public class LoginTests extends BaseTest {

    @Test(description = "Позитивная авторизация с standard_user")
    public void testPositiveLogin() {
        LoginPage loginPage = new LoginPage(driver);
        ProductsPage productsPage = new ProductsPage(driver);

        // Открываем страницу логина
        loginPage.openLoginPage();

        // Вводим учетные данные
        loginPage.login("standard_user", "secret_sauce");

        // Проверяем переход на /inventory.html
        String currentUrl = loginPage.getCurrentURL();
        Assert.assertTrue(currentUrl.contains("/inventory.html"),
            "URL не содержит /inventory.html. Текущий URL: " + currentUrl);

        // Проверяем наличие заголовка «Products»
        Assert.assertTrue(productsPage.isProductsPageDisplayed(),
            "Страница товаров не отображается");
        Assert.assertEquals(productsPage.getPageTitle(), "Products",
            "Заголовок страницы не совпадает");
    }

    @Test(description = "Негативная авторизация - заблокированный пользователь")
    public void testBlockedUserLogin() {
        LoginPage loginPage = new LoginPage(driver);

        // Открываем страницу логина
        loginPage.openLoginPage();

        // Пытаемся авторизоваться с заблокированным пользователем
        loginPage.login("locked_out_user", "secret_sauce");

        // Проверяем наличие сообщения об ошибке
        Assert.assertTrue(loginPage.isErrorMessageDisplayed(),
            "Сообщение об ошибке не отображается");

        String errorMessage = loginPage.getErrorMessage();
        Assert.assertTrue(errorMessage.contains("Epic sadface") || errorMessage.contains("locked"),
            "Сообщение об ошибке не содержит ожидаемого текста. Текст: " + errorMessage);
    }

    /**
     * Дополнительный тест: авторизация с неверным паролем
     */
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
}
