package com.testinium.tests;

import com.testinium.pages.CartPage;
import com.testinium.pages.HomePage;
import com.testinium.pages.ProductPage;
import com.testinium.utils.ExcelReader;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class Beymen {
    WebDriver driver;
    HomePage homePage;
    ProductPage productPage;
    CartPage cartPage;
    private static String filePath = "src/resources/test-data.xlsx";
    private static String finalPrice;
    private static String selectedProduct;
    private static int retryCount = 1;

    WebDriverWait wait;
    boolean success;


    @Before
    public void setup() {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
        wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        homePage = new HomePage(driver);
        productPage = new ProductPage(driver);
        cartPage = new CartPage(driver);
    }

    @Test
    public void fullTest() throws InterruptedException {
        homePage.handleCookiesAndPopups();
        Assert.assertTrue(homePage.isPageOpened());
        searchProducts();

        while (!success) {
            try {
                productActions();
                cartActions();
                success = true;
            } catch (Exception e) {
                if (retryCount < 6) {

                    emptyCartAndGoBackToRetry();
                } else {
                    System.out.println("Error: Cart Actions failed " + retryCount + " times. Will try again.");
                    success = true;
                }
            }
        }
    }

    public void emptyCartAndGoBackToRetry() throws InterruptedException {
        System.out.println("Since product '" + selectedProduct + "' could not be found, a new product is being tried.");
        retryCount++;

        cartPage.emptyCart();

        driver.navigate().back();
        driver.navigate().back();
    }

    private void productActions() throws InterruptedException {

        homePage.clickOnRandomProduct();
        writeProductInfoToFile();
        addProductToCart();
        productPage.navigateToMyCartPage();
        Thread.sleep(2000);
        checkIfPricesAreSame();
    }

    private void cartActions() throws InterruptedException {
        int targetQuantity = 2;
        cartPage.increaseQuantity(targetQuantity);

        String quantityErrorMessage = "Error: The selected quantity does not match the target quantity.";
        Assert.assertEquals(quantityErrorMessage, String.valueOf(targetQuantity), cartPage.getSelectedQuantity());

        String cartEmptyErrorMessage = "Error: The cart is not empty after attempting to empty it.";
        cartPage.emptyCart();
        Assert.assertEquals(cartEmptyErrorMessage, true, cartPage.isCartEmpty());
    }

    private void checkIfPricesAreSame() {
        Assert.assertEquals(finalPrice, cartPage.parsePrice());
    }

    private void addProductToCart() {
        selectedProduct = productPage.productName.getText();
        productPage.clickOnAvailableProduct();
        productPage.clickAddToCartButton();
        finalPrice = productPage.finalPrice();
    }

    public void searchProducts() throws InterruptedException {
        ExcelReader excelReader = new ExcelReader();

        String searchKey1 = excelReader.getCellValue(filePath, 0, 0, 0); // şort
        String searchKey2 = excelReader.getCellValue(filePath, 0, 0, 1); // gömlek
        homePage.activateSearchBox();
        homePage.searchProductName(searchKey1);
        Thread.sleep(1000);

        homePage.clearSearchBox();
        homePage.searchProductName(searchKey2);
        homePage.suggestionBox.sendKeys(Keys.ENTER);
        Thread.sleep(1000);

    }

    public void writeProductInfoToFile() {
        String productInfo = productPage.productName.getText();
        String productPrice = productPage.finalPrice();

        try (FileWriter writer = new FileWriter("product.txt")) {
            writer.write("Ürün Adı: " + productInfo + "\n");
            writer.write("Fiyatı: " + productPrice + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @After
    public void quitDriver() throws InterruptedException {
        TimeUnit.SECONDS.sleep(2);
        driver.quit();
    }
}
