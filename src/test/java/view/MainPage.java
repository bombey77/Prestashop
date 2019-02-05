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

//    public void clickCurrencyDropDownButton(CurrencyButton button) {
//        switch(button) {
//            case EUR:
//                new WebDriverWait(webDriver, 30)
//                        .until(ExpectedConditions.visibilityOfElementLocated(
//                                By.xpath("//*[contains(@class,'dropdown-item') and text()='EUR €']")));
//                eurCurrencySignButton.click();
//                break;
//            case USD:
//                new WebDriverWait(webDriver, 30)
//                        .until(ExpectedConditions.visibilityOfElementLocated(
//                                By.xpath("//*[contains(@class,'dropdown-item') and text()='USD $']")));
//                usdCurrencySignButton.click();
//                break;
//            case UAH:
//                new WebDriverWait(webDriver, 30)
//                        .until(ExpectedConditions.visibilityOfElementLocated(
//                                By.xpath("//*[contains(@class,'dropdown-item') and text()='UAH ₴']")));
//                uahCurrencySignButton.click();
//                break;
//            default:
//                System.out.println("Currency button wasn't chosen");
//                break;
//        }
//    }

    public String getCurrencySign() {
        int one = 1;
        wait(currencySignButton);
//        String textCurrencySignButton = currencySignButton.getText();
        char[] arrayCurrencySignButton = currencySignButton.getText().toCharArray();
//        String currencySign = String.valueOf(arrayCurrencySignButton[arrayCurrencySignButton.length-one]);
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