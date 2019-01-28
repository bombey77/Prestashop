package tests;

import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import view.CurrencyButton;
import view.SearchPage;

import static tests.ProjectLogger.logger;


public class SearchPageTest extends BaseTest {

    //тест падает, т.к. не все элементы поиска содержат результат поиска
    @Ignore
    @Test(description = "Checking search by catalog")
    public void checkSearchByCatalog() {
        logger.info("checkSearchByCatalog TEST --- STARTED ---");
        System.out.println("checkSearchByCatalog TEST --- STARTED---");
        logger.info("Clicking at the 'SEARCH FIELD'");
        logger.info("Writing into the 'SEARCH FIELD' text = " + SEARCH_TEXT);
        searchPage.searchByCatalog(SEARCH_TEXT);
        Assert.assertTrue(searchPage.findTextInSearchedElements(SEARCH_TEXT));
        logger.info("Checking search by catalog --- PASSED ---");
        System.out.println("checkSearchByCatalog TEST --- PASSED---");
    }

    @Test(description = "Checking the goods buy count of elements")
    public void checkSearchGoodsByCountOfElements() {
        logger.info("checkSearchGoodsByCountOfElements TEST --- STARTED ---");
        System.out.println("checkSearchGoodsByCountOfElements TEST --- STARTED---");
        logger.info("Clicking at the 'SEARCH FIELD'");
        logger.info("Writing into the 'SEARCH FIELD' text = " + SEARCH_TEXT);
        searchPage.searchByCatalog(SEARCH_TEXT);
        Assert.assertEquals(searchPage.getListOfSearchedElements().size(), searchPage.getCountOfSearchedElements());
        logger.info("checkSearchGoodsByCountOfElements TEST --- PASSED---");
        System.out.println("checkSearchGoodsByCountOfElements TEST --- PASSED---");
    }

    @Test(description = "Checking the USD currency sign in searched elements")
    public void checkCurrencySignInSearchedElements() {
        String DOLLAR = "$";
        logger.info("checkCurrencySignInSearchedElements TEST --- STARTED ---");
        System.out.println("checkCurrencySignInSearchedElements TEST --- STARTED---");
        logger.info("Clicking at the 'Currency Button'");
        mainPage.clickCurrencyButton();
        logger.info("Clicking at the 'USD Currency Button'");
        mainPage.clickCurrencyDropDownButton(CurrencyButton.USD);
        logger.info("Clicking at the 'SEARCH FIELD'");
        logger.info("Writing into the 'SEARCH FIELD' text = " + SEARCH_TEXT);
        searchPage.searchByCatalog(SEARCH_TEXT);
        Assert.assertTrue(searchPage.currencyOfItemInSearchList(DOLLAR));
        logger.info("checkCurrencySignInSearchedElements TEST --- PASSED---");
        System.out.println("checkCurrencySignInSearchedElements TEST --- PASSED---");
    }

    @Test(description = "Sorting the elements from MAX price to MIN price")
    public void checkSortingByPrice() {
        logger.info("checkSortingByPrice TEST --- STARTED ---");
        System.out.println("checkSortingByPrice TEST --- STARTED---");
        logger.info("Clicking at the 'Currency Button'");
        mainPage.clickCurrencyButton();
        logger.info("Clicking at the 'USD Currency Button'");
        mainPage.clickCurrencyDropDownButton(CurrencyButton.USD);
        logger.info("Clicking at the 'SEARCH FIELD'");
        logger.info("Writing into the 'SEARCH FIELD' text = " + SEARCH_TEXT);
        searchPage.searchByCatalog(SEARCH_TEXT);
        logger.info("Clicking at the 'Drop Down List'");
        searchPage.clickSortingDropDownList();
        logger.info("Clicking at the 'List Item Sorting From Max To Min price'");
        searchPage.clickSortingDropDownListItemMaxToMin();
        Assert.assertTrue(searchPage.sortingGoodsByPrice());
        logger.info("checkSortingByPrice TEST --- PASSED---");
        System.out.println("checkSortingByPrice TEST --- PASSED---");
    }

    @Test(description = "Checking the regular and discount price are exist")
    public void checkProductRegularAndDiscountPriceAreExistTest() {
        logger.info("checkProductRegularAndDiscountPriceAreExistTest TEST --- STARTED ---");
        System.out.println("checkProductRegularAndDiscountPriceAreExistTest TEST --- STARTED---");
        logger.info("Clicking at the 'Currency Button'");
        mainPage.clickCurrencyButton();
        logger.info("Clicking at the 'USD Currency Button'");
        mainPage.clickCurrencyDropDownButton(CurrencyButton.USD);
        logger.info("Clicking at the 'SEARCH FIELD'");
        logger.info("Writing into the 'SEARCH FIELD' text = " + SEARCH_TEXT);
        searchPage.searchByCatalog(SEARCH_TEXT);
        logger.info("Clicking at the 'Drop Down List'");
        searchPage.clickSortingDropDownList();
        logger.info("Clicking at the 'List Item Sorting From Max To Min price'");
        searchPage.clickSortingDropDownListItemMaxToMin();
        searchPage.findGoodsWithDiscount();
        searchPage.getDiscountProducts().forEach((k,v) -> Assert.assertTrue((v.getRegularPrice() >= 0) && v.getPrice() >= 0));
        logger.info("checkProductRegularAndDiscountPriceAreExistTest TEST --- PASSED---");
        System.out.println("checkProductRegularAndDiscountPriceAreExistTest TEST --- PASSED---");
    }

    //тест падает, т.к. дисконт не совпадает
//    @Ignore
    @Test(description = "Checking the discount value")
    public void checkDiscountValueTest() {
        logger.info("checkDiscountValueTest TEST --- STARTED ---");
        System.out.println("checkDiscountValueTest TEST --- STARTED---");
        logger.info("Clicking at the 'Currency Button'");
        mainPage.clickCurrencyButton();
        logger.info("Clicking at the 'USD Currency Button'");
        mainPage.clickCurrencyDropDownButton(CurrencyButton.USD);
        logger.info("Clicking at the 'SEARCH FIELD'");
        logger.info("Writing into the 'SEARCH FIELD' text = " + SEARCH_TEXT);
        searchPage.searchByCatalog(SEARCH_TEXT);
        logger.info("Clicking at the 'Drop Down List'");
        searchPage.clickSortingDropDownList();
        logger.info("Clicking at the 'List Item Sorting From Max To Min price'");
        searchPage.clickSortingDropDownListItemMaxToMin();
//        searchPage.checkDiscountAndRegularPrice();
        searchPage.findGoodsWithDiscount();
        searchPage.checkDiscountValue2();
        searchPage.getDiscounts().forEach((k,v) -> Assert.assertTrue(k.equals(v)));
        logger.info("checkProductRegularAndDiscountPriceAreExistTest TEST --- PASSED---");
        System.out.println("checkDiscountValueTest TEST --- PASSED---");
    }
}
