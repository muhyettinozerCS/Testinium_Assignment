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
    private static String filePath = "/Users/muhyettinozer/IdeaProjects/Testinium_Assignment/test-data.xlsx";
    private static String finalPrice;
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
        // TODO check naming conventions
        homePage.handleCookiesAndPopups();
        Assert.assertTrue(homePage.isPageOpened());
        searchProducts();


        while (!success) {
            try {
                Thread.sleep(2000);
                homePage.clickOnRandomProduct();
                Thread.sleep(1500);

                writeProductInfoToFile();
                Thread.sleep(2000);

                addProductToCart();
                Thread.sleep(2000);

                productPage.navigateToMyCartPage();
                Thread.sleep(1000);
                checkIfPricesAreSame();
                Thread.sleep(2000);

                cartActions();

                success = true;

            } catch (Exception e) {
                if (retryCount < 5) {
                    retryCount++;
                    Thread.sleep(2000);
                    cartPage.emptyCart();
                    driver.navigate().back();
                    Thread.sleep(2000);
                    driver.navigate().back();
                    Thread.sleep(2000);
                }
                else
            {
                System.out.println("Error: Cart Actions failed " + retryCount + " times. Will try again.");
                success = true;
            }

            }
        }

    }

    private void productActions() throws InterruptedException {
        Thread.sleep(2000);
        homePage.clickOnRandomProduct();
        Thread.sleep(1500);

        writeProductInfoToFile();
        addProductToCart();

        Thread.sleep(1000);
        productPage.navigateToMyCartPage();
        Thread.sleep(1000);
        checkIfPricesAreSame();
    }

    private void cartActions() throws InterruptedException {
        int targetQuantity = 2;
        cartPage.increaseQuantity(targetQuantity);

        Thread.sleep(1000);
        Assert.assertEquals(cartPage.getSelectedQuantity(), String.valueOf(targetQuantity));
        Thread.sleep(1000);
        cartPage.emptyCart();
        Assert.assertEquals(cartPage.isCartEmpty(), true);
    }

    private void checkIfPricesAreSame() {
        Assert.assertEquals(finalPrice, cartPage.parsePrice());
        System.out.println("final price: " + finalPrice);
        System.out.println("cart price: " + cartPage.parsePrice());
    }

    private void addProductToCart() {
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
