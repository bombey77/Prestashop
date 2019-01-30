package tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import java.util.concurrent.TimeUnit;

public class DriverManager {

    private static WebDriver webDriver;
    private static String path = System.getProperty("user.dir");


    public static String getPath() {
        return path;
    }

    public static void setPath(String path) {
        DriverManager.path += path;
    }

    public static WebDriver getDriverManager(String browser) {
//        path = System.getProperty("user.dir");

        OsCheck.OSType os = OsCheck.getOperatingSystemType();
        if (os == OsCheck.OSType.LINUX && browser.equalsIgnoreCase("chrome")) {
            setPath("/drivers/chromedriver");
            System.setProperty("webdriver.chrome.driver", getPath());
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--headless");
            options.addArguments("--disable-gpu");
            options.addArguments("window-size=1024,768");
            options.addArguments("--no-sandbox");
            options.setExperimentalOption("useAutomationExtension", false);
            webDriver = new ChromeDriver(options);
        } else if (os == OsCheck.OSType.WINDOWS && browser.equalsIgnoreCase("chrome")) {
            setPath("/drivers/chromedriver.exe");
            System.setProperty("webdriver.chrome.driver", getPath());
            webDriver = new ChromeDriver();
        } else if (os == OsCheck.OSType.WINDOWS && browser.equalsIgnoreCase("firefox")) {
            setPath("/drivers/geckodriver.exe");
            System.setProperty("webdriver.gecko.driver", getPath());
            webDriver = new FirefoxDriver();
        } else if (os == OsCheck.OSType.WINDOWS && browser.equalsIgnoreCase("ie")){
            setPath("/drivers/IEDriverServer.exe");
            System.setProperty("webdriver.ie.driver", getPath());
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

    public static WebDriver getWebDriver() {
        return webDriver;
    }
}
