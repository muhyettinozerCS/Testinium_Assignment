package com.testinium.pages;

import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.Random;

public class HomePage {
    private WebDriver driver;

    @FindBy(className = "o-header__search--input")
    public WebElement searchBox;
    @FindBy(id = "o-searchSuggestion__input")
    public WebElement suggestionBox;
    @FindBy(className = "o-header__search--close")
    private WebElement clearButton;
    @FindBy(className = "m-productImageList")
    private List<WebElement> products;
    @FindBy(xpath = "//*[@id='onetrust-accept-btn-handler']")
    private WebElement acceptCookiesButton;
    @FindBy(className = "o-modal__closeButton")
    private WebElement closeGenderSelectionButton;
    @FindBy(className = "dn-slide-deny-btn")
    private WebElement closeNotificationButton;

    WebDriverWait wait;

    public HomePage(WebDriver driver) {
        this.driver = driver;
        driver.get("https://www.beymen.com");
        driver.manage().window().maximize();
        //Initialise Elements
        PageFactory.initElements(driver, this);
    }

    /**
     * This method handles cookie, gender selection popup and desktop notification
     */
    public void handleCookiesAndPopups() {
        acceptCookiesButton.click();
        closeGenderSelectionButton.click();
        try {
            // TODO
            //closeNotificationButton.click();
            //wait.until(ExpectedConditions.visibilityOf(closeNotificationButton)).click();
        } catch (TimeoutException e) {
            System.out.println("Timeout waiting for element to be visible");
        }
    }

    public void clickOnRandomProduct() {
        Random random = new Random();
        int randomIndex = random.nextInt(products.size());
        products.get(randomIndex).click();
    }

    public boolean isPageOpened() {
        String expectedTitle = "Beymen.com – Türkiye’nin Tek Dijital Lüks Platformu";
        return driver.getTitle().contains(expectedTitle);
    }

    public void activateSearchBox() {
        searchBox.click();
    }

    public void searchProductName(String productName) {
        suggestionBox.sendKeys(productName);
    }

    public void clearSearchBox() {
        clearButton.click();
    }
}
