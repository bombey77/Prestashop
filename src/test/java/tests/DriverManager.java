package tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import java.util.concurrent.TimeUnit;

public class DriverManager {

    private static WebDriver webDriver;
    private static String path;

    public static WebDriver getDriverManager(String browser) {
        path = System.getProperty("user.dir");
        if (browser.equalsIgnoreCase("chrome")) {
            System.setProperty("webdriver.chrome.driver", path + "/drivers/chromedriver.exe");

            ChromeOptions options = new ChromeOptions();
            options.addArguments("--headless");
            options.addArguments("--disable-gpu");
            options.addArguments("window-size=1024,768");
            options.addArguments("--no-sandbox");
            options.setExperimentalOption("useAutomationExtension", false);

            webDriver = new ChromeDriver(options);
        } else if (browser.equalsIgnoreCase("firefox")) {
            System.setProperty("webdriver.gecko.driver", path + "/drivers/geckodriver.exe");
            webDriver = new FirefoxDriver();
        } else if (browser.equalsIgnoreCase("ie")){
            System.setProperty("webdriver.ie.driver", path + "/drivers/IEDriverServer.exe");
            webDriver = new InternetExplorerDriver();
        }
        setConfiguredDriver();
        return webDriver;
    }

    public static void setConfiguredDriver() {
        webDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        webDriver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
        webDriver.manage().window().maximize();
    }
}
