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
    private String path;


    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public static WebDriver getDriverManager(String browser) {
        DriverManager driverManager = new DriverManager();
        OsCheck.OSType os = OsCheck.getOperatingSystemType();
        if (os == OsCheck.OSType.LINUX && browser.equalsIgnoreCase("chrome")) {
            driverManager.setPath(System.getProperty("user.dir") + "/drivers/chromedriver");
            System.setProperty("webdriver.chrome.driver", driverManager.getPath());
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--headless");
            options.addArguments("--disable-gpu");
            options.addArguments("window-size=1024,768");
            options.addArguments("--no-sandbox");
            options.setExperimentalOption("useAutomationExtension", false);
            webDriver = new ChromeDriver(options);
        } else if (os == OsCheck.OSType.WINDOWS && browser.equalsIgnoreCase("chrome")) {
            driverManager.setPath(System.getProperty("user.dir") + "/drivers/chromedriver.exe");
            System.setProperty("webdriver.chrome.driver", driverManager.getPath());
            webDriver = new ChromeDriver();
        } else if (os == OsCheck.OSType.WINDOWS && browser.equalsIgnoreCase("firefox")) {
            driverManager.setPath(System.getProperty("user.dir") + "/drivers/geckodriver.exe");
            System.setProperty("webdriver.gecko.driver", driverManager.getPath());
            webDriver = new FirefoxDriver();
        } else if (os == OsCheck.OSType.WINDOWS && browser.equalsIgnoreCase("ie")){
            driverManager.setPath(System.getProperty("user.dir") + "/drivers/IEDriverServer.exe");
            System.setProperty("webdriver.ie.driver", driverManager.getPath());
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
