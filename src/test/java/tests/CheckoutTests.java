package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.ProductsPage;
import pages.CartPage;
import pages.CheckoutPage;

public class CheckoutTests extends BaseTest {

    @Test(description = "Успешное оформление заказа")
    public void testSuccessfulCheckout() {
        LoginPage loginPage = new LoginPage(driver);
        ProductsPage productsPage = new ProductsPage(driver);
        CartPage cartPage = new CartPage(driver);
        CheckoutPage checkoutPage = new CheckoutPage(driver);

        // Авторизуемся
        loginPage.openLoginPage();
        loginPage.login("standard_user", "secret_sauce");
        Assert.assertTrue(productsPage.isProductsPageDisplayed(), "Не удалось авторизоваться");

        // Добавляем товар в корзину
        productsPage.addProductToCart("Sauce Labs Backpack");

        // Открываем корзину
        productsPage.openCart();
        Assert.assertEquals(cartPage.getCartItemCount(), 1, "В корзине должен быть 1 товар");

        // Нажимаем Checkout
        cartPage.clickCheckout();

        // Заполняем форму с информацией
        checkoutPage.fillCheckoutInfo("John", "Doe", "12345");
        checkoutPage.clickContinue();

        // Проверяем что мы на странице Overview
        Assert.assertTrue(checkoutPage.isOverviewPageDisplayed(),
            "Не удалось перейти на страницу Overview");

        // Проверяем наименование товара на странице Overview
        String totalPrice = checkoutPage.getTotalPrice();
        Assert.assertFalse(totalPrice.isEmpty(), "Итоговая сумма не отображается");

        // Нажимаем Finish
        checkoutPage.clickFinish();

        // Проверяем сообщение об успешном завершении
        Assert.assertTrue(checkoutPage.isOrderCompleteDisplayed(),
            "Сообщение об успешном завершении не отображается");
        String completeMessage = checkoutPage.getCompleteMessage();
        Assert.assertTrue(completeMessage.contains("Thank you"),
            "Сообщение не содержит 'Thank you'. Текст: " + completeMessage);
    }

    /**
     * Дополнительный тест: валидация пустых полей при оформлении
     */
    @Test(description = "Валидация - невозможно продолжить с пустым First Name")
    public void testCheckoutValidationEmptyFirstName() {
        LoginPage loginPage = new LoginPage(driver);
        ProductsPage productsPage = new ProductsPage(driver);
        CartPage cartPage = new CartPage(driver);
        CheckoutPage checkoutPage = new CheckoutPage(driver);

        // Авторизуемся и добавляем товар
        loginPage.openLoginPage();
        loginPage.login("standard_user", "secret_sauce");
        productsPage.addProductToCart("Sauce Labs Backpack");
        productsPage.openCart();

        // Переходим к оформлению
        cartPage.clickCheckout();

        // Пытаемся продолжить с пустым First Name
        checkoutPage.fillLastName("Doe");
        checkoutPage.fillPostalCode("12345");
        checkoutPage.clickContinue();

        // Проверяем ошибку
        Assert.assertTrue(checkoutPage.isErrorMessageDisplayed(),
            "Должна быть ошибка для пустого First Name");
        String errorMessage = checkoutPage.getErrorMessage();
        Assert.assertTrue(errorMessage.toLowerCase().contains("first"),
            "Ошибка должна быть о First Name");
    }

    /**
     * Дополнительный тест: валидация пустого Postal Code
     */
    @Test(description = "Валидация - невозможно продолжить с пустым Postal Code")
    public void testCheckoutValidationEmptyPostalCode() {
        LoginPage loginPage = new LoginPage(driver);
        ProductsPage productsPage = new ProductsPage(driver);
        CartPage cartPage = new CartPage(driver);
        CheckoutPage checkoutPage = new CheckoutPage(driver);

        // Авторизуемся и добавляем товар
        loginPage.openLoginPage();
        loginPage.login("standard_user", "secret_sauce");
        productsPage.addProductToCart("Sauce Labs Backpack");
        productsPage.openCart();

        // Переходим к оформлению
        cartPage.clickCheckout();

        // Пытаемся продолжить с пустым Postal Code
        checkoutPage.fillFirstName("John");
        checkoutPage.fillLastName("Doe");
        checkoutPage.clickContinue();

        // Проверяем ошибку
        Assert.assertTrue(checkoutPage.isErrorMessageDisplayed(),
            "Должна быть ошибка для пустого Postal Code");
        String errorMessage = checkoutPage.getErrorMessage();
        Assert.assertTrue(errorMessage.toLowerCase().contains("postal"),
            "Ошибка должна быть о Postal Code");
    }
}
