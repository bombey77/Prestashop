package tests;

import org.testng.Assert;
import org.testng.annotations.*;

public class MainPageTest extends BaseTest {

    @Test
    public void checkOpenMainPage() {
        Assert.assertEquals(mainPage.getMainPageTitle(), MAIN_PAGE_TITLE);
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
        searchPage.searchByCatalog(SEARCH_TEXT);
        Assert.assertTrue(searchPage.getFieldOfSearchedElements().isDisplayed());
    }

    @Test
    public void checkSearchGoodsByCountOfElements() {
        searchPage.searchByCatalog(SEARCH_TEXT);
        Assert.assertEquals(searchPage.getListOfSearchedElements().size(), searchPage.getCountOfSearchedElements());
    }

    //тест падает, т.к. не все элементы поска содержат результат поиска
    @Ignore
    @Test
    public void checkTextInSearchedElements() {
        searchPage.searchByCatalog(SEARCH_TEXT);
        Assert.assertTrue(searchPage.findTextInSearchedElements(SEARCH_TEXT));
    }

    @Test
    public void checkCurrencySignInSearchedElements() {
        mainPage.clickCurrencyDropDownButton();
        mainPage.clickUSDCurrencyDropDownButton();
        searchPage.searchByCatalog(SEARCH_TEXT);
        Assert.assertTrue(searchPage.currencyOfItemInSearchList("$"));
    }

    @Test
    public void sortingByPriceMaxToMin() {
        mainPage.clickCurrencyDropDownButton();
        mainPage.clickUSDCurrencyDropDownButton();
        searchPage.searchByCatalog(SEARCH_TEXT);
        searchPage.clickSortingDropDownList();
        searchPage.clickSortingDropDownListItemMaxToMin();
    }

    @Test
    public void checkSortingByPrice() {
        mainPage.clickCurrencyDropDownButton();
        mainPage.clickUSDCurrencyDropDownButton();
        searchPage.searchByCatalog(SEARCH_TEXT);
        searchPage.clickSortingDropDownList();
        searchPage = searchPage.clickSortingDropDownListItemMaxToMin();
        searchPage.sortingGoodsByPrice();
    }

    @Test
    public void checkDiscountGoodsRegularAndDiscountPriceExist() {
        mainPage.clickCurrencyDropDownButton();
        mainPage.clickUSDCurrencyDropDownButton();
        searchPage.searchByCatalog(SEARCH_TEXT);
        searchPage.clickSortingDropDownList();
        searchPage.clickSortingDropDownListItemMaxToMin();
        //some logic
        searchPage.test();
    }
}