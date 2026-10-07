package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.ProductsPage;
import pages.CartPage;

public class CartTests extends BaseTest {

    @Test(description = "Добавление одного товара в корзину")
    public void testAddSingleItemToCart() {
        LoginPage loginPage = new LoginPage(driver);
        ProductsPage productsPage = new ProductsPage(driver);
        CartPage cartPage = new CartPage(driver);

        // Авторизуемся
        loginPage.openLoginPage();
        loginPage.login("standard_user", "secret_sauce");
        Assert.assertTrue(productsPage.isProductsPageDisplayed(), "Не удалось авторизоваться");

        // Добавляем товар в корзину
        String productName = "Sauce Labs Backpack";
        productsPage.addProductToCart(productName);

        // Проверяем счётчик корзины
        int cartCount = productsPage.getCartItemCount();
        Assert.assertEquals(cartCount, 1, "Счётчик корзины должен быть равен 1");

        // Открываем корзину
        productsPage.openCart();

        // Проверяем наименование товара в корзине
        Assert.assertEquals(cartPage.getCartItemCount(), 1, "В корзине должен быть 1 товар");
        String cartItemName = cartPage.getCartItemName(0);
        Assert.assertTrue(cartItemName.contains("Backpack"),
            "Наименование товара не совпадает. Получено: " + cartItemName);

        // Проверяем цену
        String cartItemPrice = cartPage.getCartItemPrice(0);
        Assert.assertFalse(cartItemPrice.isEmpty(), "Цена товара не отображается");
        Assert.assertTrue(cartItemPrice.contains("$"), "Цена должна содержать символ $");
    }

    /**
     * Дополнительный тест: добавление нескольких товаров в корзину
     */
    @Test(description = "Добавление нескольких товаров в корзину")
    public void testAddMultipleItemsToCart() {
        LoginPage loginPage = new LoginPage(driver);
        ProductsPage productsPage = new ProductsPage(driver);
        CartPage cartPage = new CartPage(driver);

        // Авторизуемся
        loginPage.openLoginPage();
        loginPage.login("standard_user", "secret_sauce");
        Assert.assertTrue(productsPage.isProductsPageDisplayed(), "Не удалось авторизоваться");

        // Добавляем несколько товаров
        productsPage.addProductToCart("Sauce Labs Backpack");
        productsPage.addProductToCart("Sauce Labs Bike Light");
        productsPage.addProductToCart("Sauce Labs Bolt T-Shirt");

        // Проверяем счётчик корзины
        int cartCount = productsPage.getCartItemCount();
        Assert.assertEquals(cartCount, 3, "Счётчик корзины должен быть равен 3");

        // Открываем корзину и проверяем количество товаров
        productsPage.openCart();
        Assert.assertEquals(cartPage.getCartItemCount(), 3, "В корзине должно быть 3 товара");
    }
}
