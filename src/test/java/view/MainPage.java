package view;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import tests.BaseTest;
import java.util.List;

public class MainPage extends BaseTest {

    private static String MAIN_PAGE_URL = "http://prestashop-automation.qatestlab.com.ua/ru/";

    @FindBy(xpath = "(.//*[normalize-space(text()) and normalize-space(.)='Валюта:'])[1]/following::span[1]")
    private WebElement currencySignButton;

    @FindBy(xpath = "//*[@id='_desktop_currency_selector']/div/ul/li[1]/a")
    private WebElement eurElementCurrencySignButton;

    @FindBy(xpath = "//*[@id='_desktop_currency_selector']/div/ul/li[3]/a")
    private WebElement usdElementCurrencySignButton;

    @FindBy(xpath = "//*[@id='content']/section/div/*")
    private List<WebElement> listPopularGoods;

    @FindBy(xpath = "//*[@id='search_widget']/form/input[2]")
    private WebElement searchField;

    @FindBy(xpath = "//*[@id='search_widget']/form/button/i")
    private WebElement serchButton;

    @FindBy(xpath = "(//*[@id='js-product-list']/div/*)")
    private WebElement fieldOfSearchedElements;

    public MainPage(WebDriver webDriver) {
        this.webDriver = webDriver;
        PageFactory.initElements(webDriver, this);
    }

    public String getMainPageTitle() {
        return MAIN_PAGE_TITLE;
    }

    public void openMainPage() {
        webDriver.get(MAIN_PAGE_URL);
    }

    public void clickCurrencyDropDownButton() {
        currencySignButton.click();
    }

    public void clickEURCurrencyDropDownButton() {
        eurElementCurrencySignButton.click();
    }

    public void clickUSDCurrencyDropDownButton() {
        usdElementCurrencySignButton.click();
    }

    public String getCurrencySign() {
        String textCurrencySignButton = currencySignButton.getText();
        char[] array = textCurrencySignButton.toCharArray();
        String currencySign = String.valueOf(array[array.length-1]);
        return currencySign;
    }

    public List<WebElement> getListOfPopularGoods() {
        return listPopularGoods;
    }

    public int getListSizeOfPopularGoods() {
        return listPopularGoods.size();
    }

    public WebElement getCurrencySignButton() {
        return currencySignButton;
    }
}