package com.example.seachtest.tests;

import com.example.seachtest.pages.MainPage;
import com.example.seachtest.pages.ResultsPage;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Nested
class BingSearchTest {
    private WebDriver driver;


    @BeforeEach
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        // Fix the issue https://github.com/SeleniumHQ/selenium/issues/11750
        options.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("https://www2.bing.com/");

    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void searchFieldTest() {
        String input = "Selenium";
        MainPage mp = new MainPage(driver);
        mp.setText(input);
        ResultsPage rp = new ResultsPage(driver);
        assertEquals(input, rp.getTextFromSearchField(), "Текст не совпал");
    }

    @Test
    public void searchResultTest() {
        String input = "Selenium";
        MainPage mp = new MainPage(driver);
        mp.setText(input);
        ResultsPage rp = new ResultsPage(driver);
        rp.clickElement(0);
        if (driver.getWindowHandles().size() == 1) {
            System.out.println("Ссылка открылась в той же вкладке");
            rp.wait (6).until(ExpectedConditions.urlContains("https://www.selenium.dev/"));
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


