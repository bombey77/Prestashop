package tests;

import io.qameta.allure.Attachment;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import view.MainPage;
import view.SearchPage;

import static tests.ProjectLogger.logger;

public class BaseTest {

    protected static WebDriver webDriver;
    protected MainPage mainPage;
    protected SearchPage searchPage;
    protected static String SEARCH_TEXT = "dress";
    private static final ProjectLogger projectLogger = new ProjectLogger();

    @Parameters("browser")
    @BeforeMethod
    public void setUp(@Optional("chrome") String browser) {

        webDriver = DriverManager.getDriverManager(browser);
        webDriver.get("http://prestashop-automation.qatestlab.com.ua/ru/");
        logger.info("Main Page opened");
        mainPage = new MainPage(webDriver);
        searchPage = new SearchPage(webDriver);
    }

    @AfterMethod
    public void tearDown() {
        webDriver.quit();
        logger.info("Web Page closed");
    }

    @Attachment(type = "image/png")
    public byte[] makeScreenshot() {
        return ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.BYTES);
    }
}
