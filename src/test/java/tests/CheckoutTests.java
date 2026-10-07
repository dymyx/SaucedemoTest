package tests;

import base.BaseTest;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.CartPage;
import pages.CheckoutPage;
import pages.LoginPage;
import pages.ProductsPage;

@Epic("Оформление заказа")
@Feature("Процесс покупки")
public class CheckoutTests extends BaseTest {

    @Story("Успешное оформление")
    @Severity(SeverityLevel.CRITICAL)
    @Owner("dymyx")
    @Test(description = "Успешное оформление заказа")
    public void testSuccessfulCheckout() {
        LoginPage loginPage = new LoginPage(driver);
        ProductsPage productsPage = new ProductsPage(driver);
        CartPage cartPage = new CartPage(driver);
        CheckoutPage checkoutPage = new CheckoutPage(driver);

        loginPage.openLoginPage();
        loginPage.login("standard_user", "secret_sauce");
        Assert.assertTrue(productsPage.isProductsPageDisplayed(), "Не удалось авторизоваться");

        productsPage.addProductToCart("Sauce Labs Backpack");
        productsPage.openCart();
        Assert.assertEquals(cartPage.getCartItemCount(), 1, "В корзине должен быть 1 товар");

        cartPage.clickCheckout();
        checkoutPage.fillCheckoutInfo("John", "Doe", "12345");
        checkoutPage.clickContinue();

        Assert.assertTrue(checkoutPage.isOverviewPageDisplayed(),
            "Не удалось перейти на страницу Overview");

        String totalPrice = checkoutPage.getTotalPrice();
        Assert.assertFalse(totalPrice.isEmpty(), "Итоговая сумма не отображается");

        checkoutPage.clickFinish();

        Assert.assertTrue(checkoutPage.isOrderCompleteDisplayed(),
            "Сообщение об успешном завершении не отображается");
        String completeMessage = checkoutPage.getCompleteMessage();
        Assert.assertTrue(completeMessage.contains("Thank you"),
            "Сообщение не содержит 'Thank you'. Текст: " + completeMessage);
    }

    @Story("Валидация форм")
    @Severity(SeverityLevel.NORMAL)
    @Owner("dymyx")
    @Test(description = "Валидация - невозможно продолжить с пустым First Name")
    public void testCheckoutValidationEmptyFirstName() {
        LoginPage loginPage = new LoginPage(driver);
        ProductsPage productsPage = new ProductsPage(driver);
        CartPage cartPage = new CartPage(driver);
        CheckoutPage checkoutPage = new CheckoutPage(driver);

        loginPage.openLoginPage();
        loginPage.login("standard_user", "secret_sauce");
        productsPage.addProductToCart("Sauce Labs Backpack");
        productsPage.openCart();

        cartPage.clickCheckout();

        checkoutPage.fillLastName("Doe");
        checkoutPage.fillPostalCode("12345");
        checkoutPage.clickContinue();

        Assert.assertTrue(checkoutPage.isErrorMessageDisplayed(),
            "Должна быть ошибка для пустого First Name");
        String errorMessage = checkoutPage.getErrorMessage();
        Assert.assertTrue(errorMessage.toLowerCase().contains("first"),
            "Ошибка должна быть о First Name");
    }

    @Story("Валидация форм")
    @Severity(SeverityLevel.NORMAL)
    @Owner("dymyx")
    @Test(description = "Валидация - невозможно продолжить с пустым Postal Code")
    public void testCheckoutValidationEmptyPostalCode() {
        LoginPage loginPage = new LoginPage(driver);
        ProductsPage productsPage = new ProductsPage(driver);
        CartPage cartPage = new CartPage(driver);
        CheckoutPage checkoutPage = new CheckoutPage(driver);

        loginPage.openLoginPage();
        loginPage.login("standard_user", "secret_sauce");
        productsPage.addProductToCart("Sauce Labs Backpack");
        productsPage.openCart();

        cartPage.clickCheckout();

        checkoutPage.fillFirstName("John");
        checkoutPage.fillLastName("Doe");
        checkoutPage.clickContinue();

        Assert.assertTrue(checkoutPage.isErrorMessageDisplayed(),
            "Должна быть ошибка для пустого Postal Code");
        String errorMessage = checkoutPage.getErrorMessage();
        Assert.assertTrue(errorMessage.toLowerCase().contains("postal"),
            "Ошибка должна быть о Postal Code");
    }
}
