package tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

import java.io.File;
import java.util.concurrent.TimeUnit;

public class DriverManager {

    private static WebDriver webDriver;
    private static String path;

    public static WebDriver getDriverManager(String browser) {
        File chromeDriver = new File("/usr/bin/chromedriver");

        path = System.getProperty("user.dir");
        if (browser.equalsIgnoreCase("chrome")) {
            System.setProperty("webdriver.chrome.driver", path + chromeDriver.getAbsolutePath());
            webDriver = new ChromeDriver();
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
