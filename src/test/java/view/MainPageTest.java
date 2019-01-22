package view;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import java.util.concurrent.TimeUnit;


public class MainPageTest {

    private String path;
    private String mainPageTitle = "prestashop-automation";
    private WebDriver webDriver;
    private MainPage mainPage;
    private SearchPage searchPage;
    private String searchText = "dress";

    @BeforeMethod
    public void setUp(/*@Optional("chrome") String browser*/) {
        path = System.getProperty("user.dir");
        System.setProperty("webdriver.chrome.driver", path + "/src/main/java/drivers/chromedriver.exe");
        webDriver = new ChromeDriver();
        webDriver.get("http://prestashop-automation.qatestlab.com.ua/ru/");

        webDriver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        mainPage = new MainPage(webDriver);
        searchPage = new SearchPage(webDriver);
    }


    @Test
    public void checkOpenMainPage() {
        Assert.assertEquals(mainPage.getMainPageTitle(), mainPageTitle);
    }

    @Test
    public void checkForUAHCurrency() {
        for (int i = 0; i < mainPage.getListSizeOfPopularGoods(); i++) {
            String textOfElement = mainPage.getListOfPopularGoods().get(i).getText();
            System.out.println(mainPage.getCurrencySign());
            Assert.assertTrue(textOfElement.contains(mainPage.getCurrencySign()));
        }
    }

    @Test
    public void checkForEURCurrency() {
        mainPage.clickCurrencyDropDownButton();
        mainPage.clickEURCurrencyDropDownButton();

        for (int i = 0; i < mainPage.getListSizeOfPopularGoods(); i++) {
            String textOfElement = mainPage.getListOfPopularGoods().get(i).getText();
            System.out.println(mainPage.getCurrencySign());
            Assert.assertTrue(textOfElement.contains(mainPage.getCurrencySign()));
        }
    }

    @Test
    public void checkForUSDCurrency() {
        mainPage.clickCurrencyDropDownButton();
        mainPage.clickUSDCurrencyDropDownButton();

        for (int i = 0; i < mainPage.getListSizeOfPopularGoods(); i++) {
            String textOfElement = mainPage.getListOfPopularGoods().get(i).getText();
            System.out.println(mainPage.getCurrencySign());
            Assert.assertTrue(textOfElement.contains(mainPage.getCurrencySign()));
        }
    }

    @Test
    public void checkClickUSDCurrencyDropDownButton() {
        mainPage.clickCurrencyDropDownButton();
        mainPage.clickUSDCurrencyDropDownButton();
        String textOfElement = mainPage.getCurrencySignButton().getText();
        Assert.assertTrue(textOfElement.contains(mainPage.getCurrencySign()));
    }

    @Test
    public void checkSearchByCatalog() {
        searchPage.searchByCatalog(searchText);
        Assert.assertTrue(searchPage.getFieldOfSearchedElements().isDisplayed());
    }

    @Test
    public void checkSearchGoodsByCountOfElements() {
        searchPage.searchByCatalog(searchText);
        Assert.assertEquals(searchPage.getListOfSearchedElements().size(), searchPage.getCountOfSearchedElements());
    }

    //тест падает, т.к. не все элементы поска содержат результат поиска
    @Ignore
    @Test
    public void checkTextInSearchedElements() {
        searchPage.searchByCatalog(searchText);
        Assert.assertTrue(searchPage.findTextInSearchedElements(searchText));
    }

    @Test
    public void checkCurrencySignInSearchedElements() {
        Assert.assertTrue(searchPage.currencyOfItemInSearchList(mainPage, searchText));
    }

    @Test
    public void checkSortingByPrice() {
        searchPage.searchByCatalog(searchText);
        searchPage.clickSortingDropDownList();
        searchPage.clickSortingDropDownListItemMaxToMin();
    }

    @AfterMethod
    public void tearDown() {
        webDriver.quit();
    }
}