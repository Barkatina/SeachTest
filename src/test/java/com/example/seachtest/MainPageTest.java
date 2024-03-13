package com.example.seachtest;

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
class MainPageTest {
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
    public void search() {
        String input = "Selenium";
        WebElement searchField = driver.findElement(By.cssSelector("#sb_form_q"));
        searchField.sendKeys(input);
        searchField.submit();
        WebElement searchPageField = driver.findElement(By.cssSelector("#sb_form_q"));
        assertEquals("Selenium", searchPageField.getAttribute("value"));
    }

    @Test
    public void searchUrl() {
        search();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(6));
        wait.until(ExpectedConditions.and(
                ExpectedConditions.attributeContains(By.cssSelector("h2 > a[href]"), "href", "selenium"),
                ExpectedConditions.elementToBeClickable(By.cssSelector("h2 > a[href]"))
        ));
        List<WebElement> results = driver.findElements(By.cssSelector("h2>a[href]"));
        clickElement(results, 0);
        if (driver.getWindowHandles().size() == 1) {
            System.out.println("Ссылка открылась в той же вкладке");
            wait.until(ExpectedConditions.urlContains("https://www.selenium.dev/"));
            assertTrue(getCurrentUrl().startsWith("https://www.selenium.dev/"), "не корректный переход по ссылке");
        } else {
            System.out.println("Ссылка открылась в новой вкладке");
            assertTrue(getTabUrl(driver).startsWith("https://www.selenium.dev/"), "не корректный переход по ссылке");
        }
    }


    private void clickElement(List<WebElement> results, int num) {
        results.get(num).click();
        System.out.println("Кликнули по ссылке" + " " + num);
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


