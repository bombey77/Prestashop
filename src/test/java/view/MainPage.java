package view;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class MainPage extends BasePage {

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
        click(currencySignButton);
    }

    public void clickCurrencyDropDownButton(CurrencyButton button) {
        click(setCurrencyDropDownButton(button));
    }

    public WebElement setCurrencyDropDownButton(CurrencyButton button) {
        if (button == CurrencyButton.EUR) {
            return eurCurrencySignButton;
        } else if (button == CurrencyButton.USD) {
            return usdCurrencySignButton;
        } else return uahCurrencySignButton;
    }

    public String getCurrencySign() {
        int one = 1;
        wait(currencySignButton);
        char[] arrayCurrencySignButton = currencySignButton.getText().toCharArray();
        return String.valueOf(arrayCurrencySignButton[arrayCurrencySignButton.length-one]);
    }

    public List<WebElement> getListOfPopularGoods() {
        wait(listOfPopularGoods);
        return listOfPopularGoods;
    }

    public WebElement getCurrencySignButton() {
        wait(currencySignButton);
        return currencySignButton;
    }
}