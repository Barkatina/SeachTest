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

    private WebDriverWait wait;


    public void clickElement(int num) {
        wait(6).until(ExpectedConditions.visibilityOfAllElements(results));
        results.get(num).click();
        System.out.println("Кликнули по ссылке" + " " + num);
    }

    public String getTextFromSearchField() {
        String val = searchField.getAttribute("value");
        System.out.println("В строке поиска текст" + " " + val);
        return val;
    }


    public ResultsPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        wait = new WebDriverWait(driver, Duration.ofSeconds(6));
    }

}
