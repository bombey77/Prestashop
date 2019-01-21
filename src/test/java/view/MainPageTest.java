package view;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class MainPageTest {

    private String path;
    private String mainPageTitle = "prestashop-automation";
    private WebDriver webDriver;
    private WebDriverWait webDriverWait;
    private MainPage mainPage;

    @BeforeMethod
    public void setUp(/*@Optional("chrome") String browser*/) {
        path = System.getProperty("user.dir");
        System.setProperty("webdriver.chrome.driver", path + "/src/main/java/drivers/chromedriver.exe");
        webDriver = new ChromeDriver();
        webDriver.get("http://prestashop-automation.qatestlab.com.ua/ru/");

        webDriver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        mainPage = new MainPage(webDriver);
    }


    @Test(priority = 0)
    public void openMainPage() {
        Assert.assertEquals(mainPage.getMainPageTitle(), mainPageTitle);
    }

    @Test(priority = 1)
    public void checkForCurrencySign() {
        mainPage.openMainPage();
        webDriver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Валюта:'])[1]/following::span[1]")).click();
        webDriver.findElement(By.linkText("UAH ₴"));
        String ss = webDriver.findElement(By.xpath("//*[@id=\"_desktop_currency_selector\"]/div/span[2]")).getText();
        char[] array = ss.toCharArray();
        System.out.println("ss = " + array[array.length-1]);

        List<WebElement> list = webDriver.findElements(By.xpath("//*[@id=\"content\"]/section/div/*"));
        for (int i = 0; i < list.size(); i++) {
            String s = list.get(i).getText();
            Assert.assertTrue(s.contains("₴"));
        }

        System.out.println(list.size());

    }

    @Test(priority = 1)
    public void checkForUAHCurrency() throws InterruptedException {
        mainPage.openMainPage();
        mainPage.clickCurrencyDropDownButton();
//        webDriver.findElement(By.linkText("UAH ₴"));
        String ss = webDriver.findElement(By.xpath("//*[@id=\"_desktop_currency_selector\"]/div/span[2]")).getText();
        char[] array = ss.toCharArray();
        System.out.println("ss = " + array[array.length-1]);

        List<WebElement> list = webDriver.findElements(By.xpath("//*[@id=\"content\"]/section/div/*"));
        for (int i = 0; i < list.size(); i++) {
            String s = list.get(i).getText();
            Assert.assertTrue(s.contains("₴"));
        }

//        String text = list.get(1).getText();
//        WebElement firstElement = list.get(0);
//        System.out.println(text.contains("₴"));

        System.out.println(list.size());

    }

    @AfterMethod
    public void tearDown() {
        webDriver.quit();
    }
}