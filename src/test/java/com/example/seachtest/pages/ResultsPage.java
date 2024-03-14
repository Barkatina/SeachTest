package com.example.seachtest.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.NoSuchElementException;

public class ResultsPage {
    @FindBy(css = "#sb_form_q")
    private WebElement searchField;

    @FindBy(css = "h2>a[href]")
    private List<WebElement> results;
    private WebDriver driver;


    public void clickElement(int num) {
        results.get(num).click();
        System.out.println("Кликнули по ссылке" + " " + num);
    }

    public String getTextFromSearchField() {
        String val = searchField.getAttribute("value");
        System.out.println("В строке поиска текст"+ " "+ val);
        return val;
    }
    public WebDriverWait waits (long timeOutInSeconds){
        WebDriverWait webDriverWait = new WebDriverWait(driver, timeOutInSeconds);
        webDriverWait.ignoring(NoSuchElementException.class)
                .ignoring(ElementNotInteractableException.class)
                .ignoring(StaleElementReferenceException.class);
        return webDriverWait;
    }
    public By waitForElementVisibale(By findStrategy, long timeOutInSeconds){
        waits(timeOutInSeconds).until((ExpectedConditions.elementToBeClickable(findStrategy));
        return findStrategy;
    }
    public WebElement findByCss(String css, Duration duration){
       return driver.findElement(waitForElementVisibale(By.cssSelector(css),duration.getSeconds());
    }

    public ResultsPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

}
