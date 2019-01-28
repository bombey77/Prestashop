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

    @FindBy(xpath = "//*[contains(@class,'dropdown-item') and text()='UAH ₴']")
    private WebElement uahCurrencySignButton;

    @FindBy(xpath = "//*[contains(@class,'dropdown-item') and text()='EUR €']")
    private WebElement eurCurrencySignButton;

    @FindBy(xpath = "//*[contains(@class,'dropdown-item') and text()='USD $']")
    private WebElement usdCurrencySignButton;

    @FindBy(xpath = "//*[@class='products']/article")
    private List<WebElement> listOfPopularGoods;

    public MainPage(WebDriver webDriver) {
        this.webDriver = webDriver;
        PageFactory.initElements(webDriver, this);
    }

    public void clickCurrencyButton() {
        new WebDriverWait(webDriver, 30)
                .until(ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//*[@class='expand-more _gray-darker hidden-sm-down']")));
        currencySignButton.click();
    }

    public void clickCurrencyDropDownButton(CurrencyButton button) {
        switch(button) {
            case EUR:
                new WebDriverWait(webDriver, 30)
                        .until(ExpectedConditions.visibilityOfElementLocated(
                                By.xpath("//*[contains(@class,'dropdown-item') and text()='EUR €']")));
                eurCurrencySignButton.click();
                break;
            case USD:
                new WebDriverWait(webDriver, 30)
                        .until(ExpectedConditions.visibilityOfElementLocated(
                                By.xpath("//*[contains(@class,'dropdown-item') and text()='USD $']")));
                usdCurrencySignButton.click();
                break;
            case UAH:
                new WebDriverWait(webDriver, 30)
                        .until(ExpectedConditions.visibilityOfElementLocated(
                                By.xpath("//*[contains(@class,'dropdown-item') and text()='UAH ₴']")));
                uahCurrencySignButton.click();
                break;
            default:
                System.out.println("Currency button wasn't chosen");
                break;
        }
    }

//    public void clickEURCurrencyDropDownButton() {
//
//        new WebDriverWait(webDriver, 30)
//                .until(ExpectedConditions.visibilityOfElementLocated(
//                        By.xpath("//*[contains(@class,'dropdown-item') and text()='EUR €']")));
//        eurCurrencySignButton.click();
//    }
//
//    public void clickUSDCurrencyDropDownButton() {
//        new WebDriverWait(webDriver, 30)
//                .until(ExpectedConditions.visibilityOfElementLocated(
//                        By.xpath("//*[contains(@class,'dropdown-item') and text()='USD $']")));
//        usdCurrencySignButton.click();
//    }

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
                        By.xpath("//*[@class='products']/article")));
        return listOfPopularGoods;
    }

    public WebElement getCurrencySignButton() {
        new WebDriverWait(webDriver, 30)
                .until(ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//*[@class='expand-more _gray-darker hidden-sm-down']")));
        return currencySignButton;
    }


}