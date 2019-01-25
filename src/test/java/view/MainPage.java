package view;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import tests.BaseTest;
import java.util.List;

public class MainPage extends BaseTest {

    @FindBy(xpath = "//*[@class='expand-more _gray-darker hidden-sm-down']")
    private WebElement currencySignButton;

    @FindBy(xpath = "//*[@id='_desktop_currency_selector']/div/ul/li[1]/a")
    private WebElement eurElementCurrencySignButton;

    @FindBy(xpath = "//*[@id='_desktop_currency_selector']/div/ul/li[3]/a")
    private WebElement usdElementCurrencySignButton;

    @FindBy(xpath = "//*[@id='content']/section/div/*")
    private List<WebElement> listOfPopularGoods;

    public MainPage(WebDriver webDriver) {
        this.webDriver = webDriver;
        PageFactory.initElements(webDriver, this);
    }

    public void clickCurrencyDropDownButton() {
        new WebDriverWait(webDriver, 30)
                .until(ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//*[@class='expand-more _gray-darker hidden-sm-down']")));
        currencySignButton.click();
    }

    public void clickEURCurrencyDropDownButton() {

        new WebDriverWait(webDriver, 30)
                .until(ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//*[@id='_desktop_currency_selector']/div/ul/li[1]/a")));
        eurElementCurrencySignButton.click();
    }

    public void clickUSDCurrencyDropDownButton() {
        new WebDriverWait(webDriver, 30)
                .until(ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//*[@id='_desktop_currency_selector']/div/ul/li[3]/a")));
        usdElementCurrencySignButton.click();
    }

    public String getCurrencySign() {
        int one = 1;
        new WebDriverWait(webDriver, 30)
                .until(ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//*[@class='expand-more _gray-darker hidden-sm-down']")));
        String textCurrencySignButton = currencySignButton.getText();
        char[] arrayCurrencySignButton = textCurrencySignButton.toCharArray();
        String currencySign = String.valueOf(arrayCurrencySignButton[arrayCurrencySignButton.length-one]);
        return currencySign;
    }

    public List<WebElement> getListOfPopularGoods() {
        new WebDriverWait(webDriver, 30)
                .until(ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//*[@id='content']/section/div/*")));
        return listOfPopularGoods;
    }

    public WebElement getCurrencySignButton() {
        new WebDriverWait(webDriver, 30)
                .until(ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//*[@class='expand-more _gray-darker hidden-sm-down']")));
        return currencySignButton;
    }


}