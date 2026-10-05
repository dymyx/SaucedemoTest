package tests;

import base.BaseTest;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.CartPage;
import pages.LoginPage;
import pages.ProductsPage;

@Epic("Взаимодействие с элементами")
@Feature("Интерактивные элементы страницы")
public class InteractionTests extends BaseTest {

    @Story("Работа с корзиной")
    @Severity(SeverityLevel.CRITICAL)
    @Owner("dymyx")
    @Test(description = "Проверка отображения счётчика корзины после добавления товара")
    public void testCartBadgeDisplayed() {
        LoginPage loginPage = new LoginPage(driver);
        ProductsPage productsPage = new ProductsPage(driver);

        loginPage.openLoginPage();
        loginPage.login("standard_user", "secret_sauce");

        int initialCount = productsPage.getCartItemCount();
        Assert.assertEquals(initialCount, 0, "Изначально корзина должна быть пустой");

        productsPage.addProductToCart("Sauce Labs Backpack");
        int countAfterAdd = productsPage.getCartItemCount();
        Assert.assertEquals(countAfterAdd, 1, "После добавления должен быть 1 товар");
    }

    @Story("Фильтрация и поиск")
    @Severity(SeverityLevel.NORMAL)
    @Owner("dymyx")
    @Test(description = "Проверка загрузки и отображения списка товаров")
    public void testProductsLoadAndDisplay() {
        LoginPage loginPage = new LoginPage(driver);
        ProductsPage productsPage = new ProductsPage(driver);

        loginPage.openLoginPage();
        loginPage.login("standard_user", "secret_sauce");

        Assert.assertTrue(productsPage.isProductsPageDisplayed(),
            "Страница товаров должна быть видна");

        java.util.List<String> productNames = productsPage.getProductNames();
        Assert.assertFalse(productNames.isEmpty(), "Должны быть товары на странице");
        Assert.assertTrue(productNames.size() > 0, "Должно быть минимум 1 товар");

        java.util.List<String> productPrices = productsPage.getProductPrices();
        Assert.assertEquals(productNames.size(), productPrices.size(),
            "Количество товаров и цен должно совпадать");
    }

    @Story("Навигация")
    @Severity(SeverityLevel.NORMAL)
    @Owner("dymyx")
    @Test(description = "Проверка работы кнопки оформления заказа в корзине")
    public void testCheckoutButtonInCart() {
        LoginPage loginPage = new LoginPage(driver);
        ProductsPage productsPage = new ProductsPage(driver);
        CartPage cartPage = new CartPage(driver);

        loginPage.openLoginPage();
        loginPage.login("standard_user", "secret_sauce");

        productsPage.addProductToCart("Sauce Labs Backpack");
        productsPage.openCart();

        Assert.assertTrue(cartPage.isCheckoutButtonDisplayed(),
            "Кнопка Checkout должна быть видна в корзине");
        Assert.assertEquals(cartPage.getCartItemCount(), 1,
            "В корзине должен быть 1 товар");
    }

    @Story("Навигация")
    @Severity(SeverityLevel.NORMAL)
    @Owner("dymyx")
    @Test(description = "Проверка открытия корзины и отображения добавленных товаров")
    public void testOpenCartAndViewItems() {
        LoginPage loginPage = new LoginPage(driver);
        ProductsPage productsPage = new ProductsPage(driver);
        CartPage cartPage = new CartPage(driver);

        loginPage.openLoginPage();
        loginPage.login("standard_user", "secret_sauce");

        String firstProduct = "Sauce Labs Backpack";
        String secondProduct = "Sauce Labs Bike Light";

        productsPage.addProductToCart(firstProduct);
        productsPage.addProductToCart(secondProduct);
        productsPage.openCart();

        java.util.List<String> cartItemNames = cartPage.getAllCartItemNames();
        Assert.assertEquals(cartItemNames.size(), 2, "В корзине должно быть 2 товара");
        Assert.assertTrue(cartItemNames.get(0).contains("Backpack"),
            "Первый товар должен быть Backpack");
        Assert.assertTrue(cartItemNames.get(1).contains("Bike Light"),
            "Второй товар должен быть Bike Light");
    }

    @Story("Валидация данных")
    @Severity(SeverityLevel.NORMAL)
    @Owner("dymyx")
    @Test(description = "Проверка формата отображения цен товаров")
    public void testProductPriceFormat() {
        LoginPage loginPage = new LoginPage(driver);
        ProductsPage productsPage = new ProductsPage(driver);

        loginPage.openLoginPage();
        loginPage.login("standard_user", "secret_sauce");
        Assert.assertTrue(productsPage.isProductsPageDisplayed(), "Не удалось авторизоваться");

        java.util.List<String> prices = productsPage.getProductPrices();
        Assert.assertFalse(prices.isEmpty(), "Должны быть цены товаров");

        for (String price : prices) {
            Assert.assertTrue(price.startsWith("$"), "Цена должна начинаться с символа $");
            Assert.assertTrue(price.matches("\\$\\d+\\.\\d{2}"),
                "Формат цены должен быть $XX.XX, но получено: " + price);
        }
    }
}
