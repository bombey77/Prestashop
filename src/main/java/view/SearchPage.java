package view;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class SearchPage {

    private WebDriver webDriver;

    public SearchPage(WebDriver webDriver) {
        this.webDriver = webDriver;
        webDriver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        PageFactory.initElements(webDriver, this);
    }


    @FindBy(xpath = "//*[@id=\"search_widget\"]/form/input[2]")
    private WebElement searchField;

    @FindBy(xpath = "//*[@id=\"search_widget\"]/form/button/i")
    private WebElement serchButton;

    @FindBy(xpath = "(//*[@id=\"js-product-list\"]/div/*)")
    private WebElement fieldOfSearchedElements;

    @FindBy(xpath = "//*[@id=\"js-product-list\"]/div[1]/*")
    private List<WebElement> listOfSearchedElements;

    @FindBy(xpath = "//*[@id=\"js-product-list-top\"]/div[1]/p")
    private WebElement countOfSearchedElements;


    public void searchByCatalog(String text) {
        searchField.clear();
        searchField.sendKeys(text.toLowerCase());
        serchButton.click();
    }

    public WebElement getFieldOfSearchedElements() {
        fieldOfSearchedElements = (new WebDriverWait(webDriver, 30))
                .until(ExpectedConditions.presenceOfElementLocated(By.xpath("(//*[@id=\"js-product-list\"]/div/*)")));
        return fieldOfSearchedElements;
    }

    public List<WebElement> getListOfSearchedElements() {
        return listOfSearchedElements;
    }

    public int getCountOfSearchedElements() {
        String[] countOfElementsText = countOfSearchedElements.getText().split(" ");
        int elements = Integer.valueOf(countOfElementsText[1].substring(0, countOfElementsText[1].length() -1));
        return elements;
    }

    public boolean findTextInSearchedElements(String text) {
        boolean contains = true;
//        searchByCatalog(text);
        List<WebElement> listOfSearchedElements = getListOfSearchedElements();
        List<String> listOfTitle = new ArrayList<String>();

        for (int i = 0; i < getListOfSearchedElements().size(); i++) {
            listOfTitle.add((listOfSearchedElements.get(i).getText()).toLowerCase());
        }

        for (int i = 0; i < listOfTitle.size(); i++) {
            System.out.println(i + " element found " + text + " = " + listOfTitle.get(i).contains(text));
            if (!listOfTitle.get(i).contains(text)) contains = false;
        }
        return contains;
    }

    public boolean currencyOfItemInSearchList(MainPage mainPage, String text) {
        if (!mainPage.getCurrencySign().equals("$")) {
            mainPage.clickCurrencyDropDownButton();
            mainPage.clickUSDCurrencyDropDownButton();
        }
        searchByCatalog(text);
        return findTextInSearchedElements("$");
    }



}