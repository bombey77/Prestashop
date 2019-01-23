package tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import view.MainPage;
import view.SearchPage;
import java.util.concurrent.TimeUnit;

public class BaseTest {

    private String path;
    protected static String MAIN_PAGE_TITLE = "prestashop-automation";
    protected WebDriver webDriver;
    protected MainPage mainPage;
    protected SearchPage searchPage;
    protected static String SEARCH_TEXT = "dress";

    @Parameters("browser")
    @BeforeMethod
    public void setUp(@Optional("chrome") String browser) {
        path = System.getProperty("user.dir");
        if (browser.equalsIgnoreCase("chrome")) {
            System.setProperty("webdriver.chrome.driver", path + "/drivers/chromedriver.exe");
            webDriver = new ChromeDriver();
        } else if (browser.equalsIgnoreCase("firefox")) {
            System.setProperty("webdriver.gecko.driver", path + "/drivers/geckodriver.exe");
            webDriver = new FirefoxDriver();
        } else if (browser.equalsIgnoreCase("ie")){
            System.setProperty("webdriver.ie.driver", path + "/drivers/IEDriverServer.exe");
            webDriver = new InternetExplorerDriver();
        }

        webDriver.manage().window().maximize();
        webDriver.get("http://prestashop-automation.qatestlab.com.ua/ru/");
        webDriver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        mainPage = new MainPage(webDriver);
        searchPage = new SearchPage(webDriver);
    }

    @AfterMethod
    public void tearDown() {
        webDriver.quit();
    }
}
