package tests;

import base.BaseTest;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.ProductsPage;

import java.util.ArrayList;
import java.util.List;

@Epic("Функциональность")
@Feature("Сортировка товаров")
public class SortingTests extends BaseTest {

    @Story("Сортировка по названию")
    @Severity(SeverityLevel.NORMAL)
    @Owner("dymyx")
    @Test(description = "Сортировка товаров A→Z (по названию от A к Z)")
    public void testSortByNameAscending() {
        LoginPage loginPage = new LoginPage(driver);
        ProductsPage productsPage = new ProductsPage(driver);

        loginPage.openLoginPage();
        loginPage.login("standard_user", "secret_sauce");
        Assert.assertTrue(productsPage.isProductsPageDisplayed(), "Не удалось авторизоваться");

        productsPage.sortProducts("az");

        List<String> productNames = productsPage.getProductNames();
        Assert.assertFalse(productNames.isEmpty(), "Список товаров пустой");

        List<String> sortedNames = new ArrayList<>(productNames);
        sortedNames.sort(String::compareTo);

        Assert.assertEquals(productNames, sortedNames,
            "Товары не отсортированы по названию от A к Z");
    }

    @Story("Сортировка по названию")
    @Severity(SeverityLevel.NORMAL)
    @Owner("dymyx")
    @Test(description = "Сортировка товаров Z→A (по названию от Z к A)")
    public void testSortByNameDescending() {
        LoginPage loginPage = new LoginPage(driver);
        ProductsPage productsPage = new ProductsPage(driver);

        loginPage.openLoginPage();
        loginPage.login("standard_user", "secret_sauce");
        Assert.assertTrue(productsPage.isProductsPageDisplayed(), "Не удалось авторизоваться");

        productsPage.sortProducts("za");

        List<String> productNames = productsPage.getProductNames();
        Assert.assertFalse(productNames.isEmpty(), "Список товаров пустой");

        List<String> sortedNames = new ArrayList<>(productNames);
        sortedNames.sort((a, b) -> b.compareTo(a));

        Assert.assertEquals(productNames, sortedNames,
            "Товары не отсортированы по названию от Z к A");
    }

    @Story("Сортировка по цене")
    @Severity(SeverityLevel.NORMAL)
    @Owner("dymyx")
    @Test(description = "Сортировка товаров по цене (низкая→высокая)")
    public void testSortByPriceAscending() {
        LoginPage loginPage = new LoginPage(driver);
        ProductsPage productsPage = new ProductsPage(driver);

        loginPage.openLoginPage();
        loginPage.login("standard_user", "secret_sauce");
        Assert.assertTrue(productsPage.isProductsPageDisplayed(), "Не удалось авторизоваться");

        productsPage.sortProducts("lohi");

        List<Double> prices = productsPage.getProductPricesAsDouble();
        Assert.assertFalse(prices.isEmpty(), "Список цен пустой");

        List<Double> sortedPrices = new ArrayList<>(prices);
        sortedPrices.sort(Double::compareTo);

        Assert.assertEquals(prices, sortedPrices,
            "Товары не отсортированы по цене (низкая→высокая)");
    }

    @Story("Сортировка по цене")
    @Severity(SeverityLevel.NORMAL)
    @Owner("dymyx")
    @Test(description = "Сортировка товаров по цене (высокая→низкая)")
    public void testSortByPriceDescending() {
        LoginPage loginPage = new LoginPage(driver);
        ProductsPage productsPage = new ProductsPage(driver);

        loginPage.openLoginPage();
        loginPage.login("standard_user", "secret_sauce");
        Assert.assertTrue(productsPage.isProductsPageDisplayed(), "Не удалось авторизоваться");

        productsPage.sortProducts("hilo");

        List<Double> prices = productsPage.getProductPricesAsDouble();
        Assert.assertFalse(prices.isEmpty(), "Список цен пустой");

        List<Double> sortedPrices = new ArrayList<>(prices);
        sortedPrices.sort((a, b) -> b.compareTo(a));

        Assert.assertEquals(prices, sortedPrices,
            "Товары не отсортированы по цене (высокая→низкая)");
    }
}
