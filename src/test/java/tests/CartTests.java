package tests;

import base.BaseTest;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.CartPage;
import pages.LoginPage;
import pages.ProductsPage;

@Epic("Работа с корзиной")
@Feature("Управление товарами в корзине")
public class CartTests extends BaseTest {

    @Story("Добавление товара в корзину")
    @Severity(SeverityLevel.CRITICAL)
    @Owner("dymyx")
    @Test(description = "Добавление одного товара в корзину")
    public void testAddSingleItemToCart() {
        LoginPage loginPage = new LoginPage(driver);
        ProductsPage productsPage = new ProductsPage(driver);
        CartPage cartPage = new CartPage(driver);

        loginPage.openLoginPage();
        loginPage.login("standard_user", "secret_sauce");
        Assert.assertTrue(productsPage.isProductsPageDisplayed(), "Не удалось авторизоваться");

        String productName = "Sauce Labs Backpack";
        productsPage.addProductToCart(productName);

        int cartCount = productsPage.getCartItemCount();
        Assert.assertEquals(cartCount, 1, "Счётчик корзины должен быть равен 1");

        productsPage.openCart();

        Assert.assertEquals(cartPage.getCartItemCount(), 1, "В корзине должен быть 1 товар");
        String cartItemName = cartPage.getCartItemName(0);
        Assert.assertTrue(cartItemName.contains("Backpack"),
            "Наименование товара не совпадает. Получено: " + cartItemName);

        String cartItemPrice = cartPage.getCartItemPrice(0);
        Assert.assertFalse(cartItemPrice.isEmpty(), "Цена товара не отображается");
        Assert.assertTrue(cartItemPrice.contains("$"), "Цена должна содержать символ $");
    }

    @Story("Добавление товара в корзину")
    @Severity(SeverityLevel.CRITICAL)
    @Owner("dymyx")
    @Test(description = "Добавление нескольких товаров в корзину")
    public void testAddMultipleItemsToCart() {
        LoginPage loginPage = new LoginPage(driver);
        ProductsPage productsPage = new ProductsPage(driver);
        CartPage cartPage = new CartPage(driver);

        loginPage.openLoginPage();
        loginPage.login("standard_user", "secret_sauce");
        Assert.assertTrue(productsPage.isProductsPageDisplayed(), "Не удалось авторизоваться");

        productsPage.addProductToCart("Sauce Labs Backpack");
        productsPage.addProductToCart("Sauce Labs Bike Light");
        productsPage.addProductToCart("Sauce Labs Bolt T-Shirt");

        int cartCount = productsPage.getCartItemCount();
        Assert.assertEquals(cartCount, 3, "Счётчик корзины должен быть равен 3");

        productsPage.openCart();
        Assert.assertEquals(cartPage.getCartItemCount(), 3, "В корзине должно быть 3 товара");
    }
}
