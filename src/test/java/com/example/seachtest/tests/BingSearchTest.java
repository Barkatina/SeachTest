package com.example.seachtest.tests;

import com.example.seachtest.pages.MainPage;
import com.example.seachtest.pages.ResultsPage;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Nested
class BingSearchTest {
    private final String input = "Selenium";
    private WebDriver driver;
    private MainPage mainPage;
    private ResultsPage resultsPage;



    @BeforeEach
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("https://www2.bing.com/");
        mainPage = new MainPage(driver);
        resultsPage = new ResultsPage(driver);
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void searchFieldTest() {
        mainPage.setText(input);
        assertEquals(input, resultsPage.getTextFromSearchField(), "Текст не совпал");
    }
    @Test
    public void searchResultTest() {
        mainPage.setText(input);
        resultsPage.clickElement(0);
        if (driver.getWindowHandles().size() == 1) {
            System.out.println("Ссылка открылась в той же вкладке");
            resultsPage.getWait().until(ExpectedConditions.urlContains("https://www.selenium.dev/"));
            assertTrue(getCurrentUrl().startsWith("https://www.selenium.dev/"), "не корректный переход по ссылке");
        } else {
            System.out.println("Ссылка открылась в новой вкладке");
            assertTrue(getTabUrl(driver).startsWith("https://www.selenium.dev/"), "не корректный переход по ссылке");
        }
    }
    private String getTabUrl(WebDriver driver) {
        List<String> tabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(1));
        return getCurrentUrl();
    }
    private String getCurrentUrl() {
        String url = driver.getCurrentUrl();
        System.out.println(url);
        return url;
    }
}

