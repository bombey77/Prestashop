package view;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import tests.BaseTest;

import java.util.LinkedList;
import java.util.List;

public class BasePage extends BaseTest {

    public void wait(WebElement webElement) {
        new WebDriverWait(webDriver, 30)
                .until(ExpectedConditions.visibilityOf(webElement));
    }

    public void wait(String...className) {
        for(String clName : className) {
            new WebDriverWait(webDriver, 30).until(ExpectedConditions.visibilityOfElementLocated(
                    By.className(clName)));
        }
    }

    public void wait(List<WebElement> listWebElement) {
        new WebDriverWait(webDriver, 30)
                .until(ExpectedConditions.refreshed(ExpectedConditions.visibilityOfAllElements(
                        listWebElement)));
    }

    public void click(WebElement webElement) {
        wait(webElement);
        webElement.click();
    }

    public void clear(WebElement webElement) {
        wait(webElement);
        webElement.clear();
    }

    public List<String> findCurrencySignInSearchedElement(String text, List<WebElement> listOfPrices) {
        wait(listOfPrices);
        List<String> list = new LinkedList<>();
        listOfPrices.forEach(t -> {
            System.out.println("Element found " + text + " = " + t.getText().toLowerCase().contains(text));
            list.add(t.getText().toLowerCase().substring(t.getText().length()-1));});
        return list;
    }
}
