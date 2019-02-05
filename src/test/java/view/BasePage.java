package view;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import tests.BaseTest;

import java.util.List;

public class BasePage extends BaseTest {

    public void wait(WebElement webElement) {
        new WebDriverWait(webDriver, 30)
                .until(ExpectedConditions.visibilityOf(webElement));
    }

    public void wait(List<WebElement> listWebElement) {
        new WebDriverWait(webDriver, 30)
                .until(ExpectedConditions.visibilityOfAllElements(listWebElement));
    }

    public void click(WebElement webElement) {
        wait(webElement);
        webElement.click();
    }

    public void clear(WebElement webElement) {
        wait(webElement);
        webElement.clear();
    }
}
