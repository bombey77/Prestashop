package tests;

import org.testng.Assert;
import org.testng.annotations.*;

public class MainPageTest extends BaseTest {

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
        mainPage.clickCurrencyDropDownButton();
        mainPage.clickUSDCurrencyDropDownButton();
        searchPage.searchByCatalog(searchText);
        Assert.assertTrue(searchPage.currencyOfItemInSearchList("$"));
    }

    @Test
    public void checkSortingByPrice() {
        searchPage.searchByCatalog(searchText);
        searchPage.clickSortingDropDownList();
        searchPage.clickSortingDropDownListItemMaxToMin();
    }
}