package tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import view.MainPage;
import view.SearchPage;
import static tests.ProjectLogger.logger;

public class BaseTest {

    private String path;
    protected WebDriver webDriver;
    protected MainPage mainPage;
    protected SearchPage searchPage;
    protected static String SEARCH_TEXT = "dress";

    private static final ProjectLogger projectLogger = new ProjectLogger();

    @Parameters("browser")
    @BeforeMethod
    public void setUp(@Optional("chrome") String browser) {
//        path = System.getProperty("user.dir");
//        if (browser.equalsIgnoreCase("chrome")) {
//            System.setProperty("webdriver.chrome.driver", path + "/drivers/chromedriver.exe");
//            webDriver = new ChromeDriver();
//        } else if (browser.equalsIgnoreCase("firefox")) {
//            System.setProperty("webdriver.gecko.driver", path + "/drivers/geckodriver.exe");
//            webDriver = new FirefoxDriver();
//        } else if (browser.equalsIgnoreCase("ie")){
//            System.setProperty("webdriver.ie.driver", path + "/drivers/IEDriverServer.exe");
//            webDriver = new InternetExplorerDriver();
//        }

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
}
