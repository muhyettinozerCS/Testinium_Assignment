package com.testinium.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class ProductPage {
    private WebDriver driver;
    @FindBy(className = "o-productDetail__description")
    public WebElement productName;

    @FindBy(className = "m-price__new")
    public WebElement fullPrice;

    @FindBy(className = "m-price__lastPrice")
    private WebElement discountedPrice;

    @FindBy(className = "m-variation")
    private WebElement productSizes;

    @FindBy(xpath = "//*[@id='addBasket']")
    private WebElement addToCartButton;

    public ProductPage(WebDriver driver) {
        this.driver = driver;

        //Initialise Elements
        PageFactory.initElements(driver, this);
    }

    /**
     * Beymen shows price in three formats Ex: 1.250 TL, 1.250,00 TL or 3.500,99 TL
     * Format changes based on the decimal and the current page
     * This method handles different formats and parse to common format
     *
     * @param price Unparsed price string.
     * @return parsed price.
     */
    public static String parsePrice(String price) {
        // Eğer metin içinde "," bulunmuyorsa, sonuna ",00" ekleyin
        price = price.split(" ")[0];
        if (!price.contains(",")) {
            return price + ",00";
        }
        // TODO remove comments
        // "," zaten varsa, metni olduğu gibi bırakın
        return price;
    }

    private WebElement findFirstAvailableSize() {
        List<WebElement> availableSizes = productSizes.findElements(By.cssSelector("span:not(.m-variation__item.-disabled)"));
        return availableSizes.get(0);
    }

    // TODO
    public void clickOnAvailableProduct() {
        WebElement size = findFirstAvailableSize();
        if (size != null) size.click();
    }

    public void clickAddToCartButton() {
        addToCartButton.click();
    }

    public void navigateToMyCartPage() {
        driver.get("https://www.beymen.com/cart");
    }

    /**
     * This method returns either the discounted price or full price
     * according to discounted price existence
     *
     * @return final price.
     */


    public String finalPrice() {
        try {
            return addCommaIfNotPresent(discountedPrice.getText().split("\n")[1].trim());
        } catch (Exception e) {
            return addCommaIfNotPresent(fullPrice.getText());
        }

    }

    /**
     * This method returns either the finalprice
     *
     * @return fraction price.
     */
    private static String addCommaIfNotPresent(String fraction) {
        // Eğer metin içinde "," bulunmuyorsa, sonuna ",00" ekleyin
        fraction = fraction.split(" ")[0];
        if (!fraction.contains(",")) {
            return fraction + ",00";
        }
        // "," zaten varsa, metni olduğu gibi bırakın
        return fraction;
    }
}
