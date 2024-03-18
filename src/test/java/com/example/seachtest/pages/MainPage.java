package com.example.seachtest.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

// "https://www2.bing.com/"
public class MainPage {
    @FindBy(css = "#sb_form_q")
    private WebElement searchField;

    public void setText(String text) {
        searchField.sendKeys(text);
        searchField.submit();
        System.out.println("Введен текст:" + text);

    }

    public MainPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }
}
