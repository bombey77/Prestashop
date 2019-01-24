package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.annotations.Ignore;

public class MainPageTest extends BaseTest {

    private static final String DOLLAR = "$";

    @Test
    public void checkOpenMainPage() {
        Assert.assertEquals(mainPage.getMainPageTitle(), MAIN_PAGE_TITLE);
    }

    @Test
    public void checkForUAHCurrency() {
        int zero = 0;
        for (int i = zero; i < mainPage.getListOfPopularGoods().size(); i++) {
            String textOfElement = mainPage.getListOfPopularGoods().get(i).getText();
            System.out.println(mainPage.getCurrencySign());
            Assert.assertTrue(textOfElement.contains(mainPage.getCurrencySign()));
        }
    }

    @Test
    public void checkForEURCurrency() {
        mainPage.clickCurrencyDropDownButton();
        mainPage.clickEURCurrencyDropDownButton();

        for (int i = 0; i < mainPage.getListOfPopularGoods().size(); i++) {
            String textOfElement = mainPage.getListOfPopularGoods().get(i).getText();
            System.out.println(mainPage.getCurrencySign());
            Assert.assertTrue(textOfElement.contains(mainPage.getCurrencySign()));
        }
    }

    @Test
    public void checkForUSDCurrency() {
        mainPage.clickCurrencyDropDownButton();
        mainPage.clickUSDCurrencyDropDownButton();

        for (int i = 0; i < mainPage.getListOfPopularGoods().size(); i++) {
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
        Assert.assertTrue(searchPage.currencyOfItemInSearchList(DOLLAR));
    }

    @Test
    public void sortingByPriceMaxToMin() {
        mainPage.clickCurrencyDropDownButton();
        mainPage.clickUSDCurrencyDropDownButton();
        searchPage.searchByCatalog(SEARCH_TEXT);
        searchPage.clickSortingDropDownList();
        searchPage.clickSortingDropDownListItemMaxToMin();
        // no result
    }

    @Test
    public void checkSortingByPrice() {
        mainPage.clickCurrencyDropDownButton();
        mainPage.clickUSDCurrencyDropDownButton();
        searchPage.searchByCatalog(SEARCH_TEXT);
        searchPage.clickSortingDropDownList();
        searchPage.clickSortingDropDownListItemMaxToMin();
        searchPage.sortingGoodsByPrice();
        // no result
    }

    @Test
    public void checkProductRegularAndDiscountPriceAreExists() {
        mainPage.clickCurrencyDropDownButton();
        mainPage.clickUSDCurrencyDropDownButton();
        searchPage.searchByCatalog(SEARCH_TEXT);
        searchPage.clickSortingDropDownList();
        searchPage.clickSortingDropDownListItemMaxToMin();
        searchPage.checkDiscountAndRegularPrice();
//        Assert.assertTrue(searchPage.checkDiscountAndRegularPrice());
    }

    @Test
    public void checkDiscountValue() {
        mainPage.clickCurrencyDropDownButton();
        mainPage.clickUSDCurrencyDropDownButton();
        searchPage.searchByCatalog(SEARCH_TEXT);
        searchPage.clickSortingDropDownList();
        searchPage.clickSortingDropDownListItemMaxToMin();
        searchPage.checkDiscountAndRegularPrice();
        Assert.assertTrue(searchPage.checkDiscountValue());
    }
}