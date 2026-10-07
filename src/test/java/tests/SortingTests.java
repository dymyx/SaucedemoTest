package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.ProductsPage;

import java.util.ArrayList;
import java.util.List;

public class SortingTests extends BaseTest {

    @Test(description = "Сортировка товаров A→Z (по названию от A к Z)")
    public void testSortByNameAscending() {
        LoginPage loginPage = new LoginPage(driver);
        ProductsPage productsPage = new ProductsPage(driver);

        // Авторизуемся
        loginPage.openLoginPage();
        loginPage.login("standard_user", "secret_sauce");
        Assert.assertTrue(productsPage.isProductsPageDisplayed(), "Не удалось авторизоваться");

        // Сортируем по названию A→Z
        productsPage.sortProducts("az");

        // Получаем список названий товаров
        List<String> productNames = productsPage.getProductNames();
        Assert.assertFalse(productNames.isEmpty(), "Список товаров пустой");

        // Проверяем что список отсортирован по алфавиту
        List<String> sortedNames = new ArrayList<>(productNames);
        sortedNames.sort(String::compareTo);

        Assert.assertEquals(productNames, sortedNames,
            "Товары не отсортированы по названию от A к Z");
    }

    @Test(description = "Сортировка товаров Z→A (по названию от Z к A)")
    public void testSortByNameDescending() {
        LoginPage loginPage = new LoginPage(driver);
        ProductsPage productsPage = new ProductsPage(driver);

        // Авторизуемся
        loginPage.openLoginPage();
        loginPage.login("standard_user", "secret_sauce");
        Assert.assertTrue(productsPage.isProductsPageDisplayed(), "Не удалось авторизоваться");

        // Сортируем по названию Z→A
        productsPage.sortProducts("za");

        // Получаем список названий товаров
        List<String> productNames = productsPage.getProductNames();
        Assert.assertFalse(productNames.isEmpty(), "Список товаров пустой");

        // Проверяем что список отсортирован в обратном порядке
        List<String> sortedNames = new ArrayList<>(productNames);
        sortedNames.sort((a, b) -> b.compareTo(a));

        Assert.assertEquals(productNames, sortedNames,
            "Товары не отсортированы по названию от Z к A");
    }

    @Test(description = "Сортировка товаров по цене (низкая→высокая)")
    public void testSortByPriceAscending() {
        LoginPage loginPage = new LoginPage(driver);
        ProductsPage productsPage = new ProductsPage(driver);

        // Авторизуемся
        loginPage.openLoginPage();
        loginPage.login("standard_user", "secret_sauce");
        Assert.assertTrue(productsPage.isProductsPageDisplayed(), "Не удалось авторизоваться");

        // Сортируем по цене (низкая→высокая)
        productsPage.sortProducts("lohi");

        // Получаем список цен
        List<Double> prices = productsPage.getProductPricesAsDouble();
        Assert.assertFalse(prices.isEmpty(), "Список цен пустой");

        // Проверяем что цены отсортированы в возрастающем порядке
        List<Double> sortedPrices = new ArrayList<>(prices);
        sortedPrices.sort(Double::compareTo);

        Assert.assertEquals(prices, sortedPrices,
            "Товары не отсортированы по цене (низкая→высокая)");
    }

    @Test(description = "Сортировка товаров по цене (высокая→низкая)")
    public void testSortByPriceDescending() {
        LoginPage loginPage = new LoginPage(driver);
        ProductsPage productsPage = new ProductsPage(driver);

        // Авторизуемся
        loginPage.openLoginPage();
        loginPage.login("standard_user", "secret_sauce");
        Assert.assertTrue(productsPage.isProductsPageDisplayed(), "Не удалось авторизоваться");

        // Сортируем по цене (высокая→низкая)
        productsPage.sortProducts("hilo");

        // Получаем список цен
        List<Double> prices = productsPage.getProductPricesAsDouble();
        Assert.assertFalse(prices.isEmpty(), "Список цен пустой");

        // Проверяем что цены отсортированы в убывающем порядке
        List<Double> sortedPrices = new ArrayList<>(prices);
        sortedPrices.sort((a, b) -> b.compareTo(a));

        Assert.assertEquals(prices, sortedPrices,
            "Товары не отсортированы по цене (высокая→низкая)");
    }
}
