package tests;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import view.CurrencyButton;

import static tests.ProjectLogger.logger;

@Listeners(CustomListener.class)
public class SearchPageTest extends BaseTest {

    //тест падает, т.к. не все элементы поиска содержат результат поиска
//    @Ignore
    @Test(description = "Checking search by catalog")
    public void checkTitleTextInSearchedElement() {
        logger.info("checkTitleTextInSearchedElement TEST --- STARTED ---");
        System.out.println("checkTitleTextInSearchedElement TEST --- STARTED---");
        logger.info("Clicking at the 'SEARCH FIELD'");
        logger.info("Writing into the 'SEARCH FIELD' text = " + SEARCH_TEXT);
        searchPage.searchByCatalog(SEARCH_TEXT);
        searchPage.findTextInSearchedElements(SEARCH_TEXT).forEach(text ->
            Assert.assertEquals(text, SEARCH_TEXT));
        logger.info("Checking search by catalog --- PASSED ---");
        System.out.println("checkTitleTextInSearchedElement TEST --- PASSED---");
    }

    @Test(description = "Checking the goods by count of elements")
    public void checkNumberOfGoodsAreEqualFoundedGoods() {
        logger.info("checkNumberOfGoodsAreEqualFoundedGoods TEST --- STARTED ---");
        System.out.println("checkNumberOfGoodsAreEqualFoundedGoods TEST --- STARTED---");
        logger.info("Clicking at the 'SEARCH FIELD'");
        logger.info("Writing into the 'SEARCH FIELD' text = " + SEARCH_TEXT);
        searchPage.searchByCatalog(SEARCH_TEXT);
        Assert.assertEquals(searchPage.getListOfSearchedElements().size(), searchPage.getCountOfSearchedElements());
        logger.info("checkNumberOfGoodsAreEqualFoundedGoods TEST --- PASSED---");
        System.out.println("checkNumberOfGoodsAreEqualFoundedGoods TEST --- PASSED---");
    }

    @Test(description = "Checking the USD currency sign in searched elements")
    public void checkCurrencySignInSearchedElements() {
        String DOLLAR = "$";
        logger.info("checkCurrencySignInSearchedElements TEST --- STARTED ---");
        System.out.println("checkCurrencySignInSearchedElements TEST --- STARTED---");
        logger.info("Clicking at the 'Currency Button'");
        mainPage.clickCurrencyDropDownButton();
        logger.info("Clicking at the 'USD Currency Button'");
        mainPage.clickCurrencyButton(CurrencyButton.USD);
        logger.info("Clicking at the 'SEARCH FIELD'");
        logger.info("Writing into the 'SEARCH FIELD' text = " + SEARCH_TEXT);
        searchPage.searchByCatalog(SEARCH_TEXT);
        searchPage.findCurrencySignInSearchedElement(DOLLAR, searchPage.getListOfPrices())
                .forEach(sign -> Assert.assertEquals(sign, DOLLAR));
        logger.info("checkCurrencySignInSearchedElements TEST --- PASSED---");
        System.out.println("checkCurrencySignInSearchedElements TEST --- PASSED---");
    }

    @Test(description = "Sorting the elements from MAX price to MIN price")
    public void checkSortingGoodsFromMaxToMinPrice() {
        logger.info("checkSortingGoodsFromMaxToMinPrice TEST --- STARTED ---");
        System.out.println("checkSortingGoodsFromMaxToMinPrice TEST --- STARTED---");
        logger.info("Clicking at the 'Currency Button'");
        mainPage.clickCurrencyDropDownButton();
        logger.info("Clicking at the 'USD Currency Button'");
        mainPage.clickCurrencyButton(CurrencyButton.USD);
        logger.info("Clicking at the 'SEARCH FIELD'");
        logger.info("Writing into the 'SEARCH FIELD' text = " + SEARCH_TEXT);
        searchPage.searchByCatalog(SEARCH_TEXT);
        logger.info("Clicking at the 'Drop Down List'");
        searchPage.clickSortingDropDownList();
        logger.info("Clicking at the 'List Item Sorting From Max To Min price'");
        searchPage.clickSortingDropDownListItemMaxToMin();
        searchPage.sortingGoodsByPrice();
        Assert.assertEquals(searchPage.getUnSortedPriceList(), searchPage.getSortedPriceList());
        logger.info("checkSortingGoodsFromMaxToMinPrice TEST --- PASSED---");
        System.out.println("checkSortingGoodsFromMaxToMinPrice TEST --- PASSED---");
    }

    @Test(description = "Checking the regular and discount price are exist")
    public void checkProductRegularAndDiscountPriceAreExistTest() {
        logger.info("checkProductRegularAndDiscountPriceAreExistTest TEST --- STARTED ---");
        System.out.println("checkProductRegularAndDiscountPriceAreExistTest TEST --- STARTED---");
        logger.info("Clicking at the 'Currency Button'");
        mainPage.clickCurrencyDropDownButton();
        logger.info("Clicking at the 'USD Currency Button'");
        mainPage.clickCurrencyButton(CurrencyButton.USD);
        logger.info("Clicking at the 'SEARCH FIELD'");
        logger.info("Writing into the 'SEARCH FIELD' text = " + SEARCH_TEXT);
        searchPage.searchByCatalog(SEARCH_TEXT);
        logger.info("Clicking at the 'Drop Down List'");
        searchPage.clickSortingDropDownList();
        logger.info("Clicking at the 'List Item Sorting From Max To Min price'");
        searchPage.clickSortingDropDownListItemMaxToMin();
        searchPage.findGoodsWithDiscount();
        searchPage.getDiscountProducts().forEach((k,v) ->
                Assert.assertTrue((v.getRegularPrice() >= 0) && v.getPrice() >= 0));
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
        mainPage.clickCurrencyDropDownButton();
        logger.info("Clicking at the 'USD Currency Button'");
        mainPage.clickCurrencyButton(CurrencyButton.USD);
        logger.info("Clicking at the 'SEARCH FIELD'");
        logger.info("Writing into the 'SEARCH FIELD' text = " + SEARCH_TEXT);
        searchPage.searchByCatalog(SEARCH_TEXT);
        logger.info("Clicking at the 'Drop Down List'");
        searchPage.clickSortingDropDownList();
        logger.info("Clicking at the 'List Item Sorting From Max To Min price'");
        searchPage.clickSortingDropDownListItemMaxToMin();
        searchPage.findGoodsWithDiscount();
        searchPage.checkDiscountValue();
        searchPage.getDiscounts().forEach((k,v) -> Assert.assertEquals(k, v));
        logger.info("checkProductRegularAndDiscountPriceAreExistTest TEST --- PASSED---");
        System.out.println("checkDiscountValueTest TEST --- PASSED---");
    }
}
