package view;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class MainPage {

    private WebDriver webDriver;
    private String mainPageTitle = "prestashop-automation";

    private String mainPage = "http://prestashop-automation.qatestlab.com.ua/ru/";
    private String currncySign;

    private WebElement currencySignButton;
    private List<WebElement> popularGoods;

    public MainPage(WebDriver webDriver) {
        this.webDriver = webDriver;
//        currencySignButton = webDriver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Валюта:'])[1]/following::span[1]"));
        PageFactory.initElements(webDriver, this);
    }

    public String getMainPageTitle() {
        mainPageTitle = webDriver.getTitle();
        return mainPageTitle;
    }

    public void openMainPage() {
        webDriver.get(mainPage);
    }

    public void clickCurrencyDropDownButton() {
        currencySignButton = webDriver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Валюта:'])[1]/following::span[1]"));
        currencySignButton.click();
    }

    public String getCurrncySign() {
        String sign = webDriver.findElement(By.xpath("//*[@id=\"_desktop_currency_selector\"]/div/span[2]")).getText();
        char[] array = sign.toCharArray();
        String res = String.valueOf(array[array.length-1]);
        return res;
    }




}
