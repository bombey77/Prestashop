package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import static tests.ProjectLogger.logger;

public class MainPageTest extends BaseTest {

//    @Test(description = "Open the Mane Page")
//    public void checkOpenMainPage() {
//        Assert.assertEquals(mainPage.getMainPageTitle(), MAIN_PAGE_TITLE);
//        logger.info("checkOpenMainPage TEST --- PASSED---");
//        System.out.println("checkOpenMainPage TEST --- PASSED---");
//    }

    @Test(description = "Checking for UAH currency sign on items")
    public void checkForUAHCurrency() {
        logger.info("checkForUAHCurrency TEST --- STARTED ---");
        System.out.println("checkForUAHCurrency TEST --- STARTED---");
        for (int i = 0; i < mainPage.getListOfPopularGoods().size(); i++) {
            String textOfElement = mainPage.getListOfPopularGoods().get(i).getText();
            logger.info("Element " + i + " has sign = " + mainPage.getCurrencySign());
            System.out.println("Element " + i + " has sign = " + mainPage.getCurrencySign());
            Assert.assertTrue(textOfElement.contains(mainPage.getCurrencySign()));
        }
        logger.info("checkForUAHCurrency TEST --- PASSED---");
        System.out.println("checkForUAHCurrency TEST --- PASSED---");
    }

    @Test(description = "Checking for EUR currency sign on items")
    public void checkForEURCurrency() {
        logger.info("checkForUAHCurrency TEST --- STARTED ---");
        System.out.println("checkForEURCurrency TEST --- STARTED---");
        logger.info("Clicking at the 'Currency Button'");
        mainPage.clickCurrencyDropDownButton();
        logger.info("Clicking at the 'EUR Currency Button'");
        mainPage.clickEURCurrencyDropDownButton();

        for (int i = 0; i < mainPage.getListOfPopularGoods().size(); i++) {
            String textOfElement = mainPage.getListOfPopularGoods().get(i).getText();
            System.out.println("Element " + i + " has sign = " + mainPage.getCurrencySign());
            Assert.assertTrue(textOfElement.contains(mainPage.getCurrencySign()));
        }
        logger.info("checkForUAHCurrency TEST --- PASSED---");
        System.out.println("checkForEURCurrency TEST --- PASSED---");
    }

    @Test(description = "Checking for USD currency sign on items")
    public void checkForUSDCurrency() {
        logger.info("checkForUSDCurrency TEST --- STARTED ---");
        System.out.println("checkForUSDCurrency TEST --- STARTED---");
        logger.info("Clicking at the 'Currency Button'");
        mainPage.clickCurrencyDropDownButton();
        logger.info("Clicking at the 'USD Currency Button'");
        mainPage.clickUSDCurrencyDropDownButton();

        for (int i = 0; i < mainPage.getListOfPopularGoods().size(); i++) {
            String textOfElement = mainPage.getListOfPopularGoods().get(i).getText();
            System.out.println("Element " + i + " has sign = " + mainPage.getCurrencySign());
            Assert.assertTrue(textOfElement.contains(mainPage.getCurrencySign()));
        }
        logger.info("checkForUSDCurrency TEST --- PASSED---");
        System.out.println("checkForUSDCurrency TEST --- PASSED---");
    }

    @Test(description = "Clicking that the USD currency drop-down button was clicked")
    public void checkClickUSDCurrencyDropDownButton() {
        logger.info("checkClickUSDCurrencyDropDownButton TEST --- STARTED ---");
        System.out.println("checkClickUSDCurrencyDropDownButton TEST --- STARTED---");
        logger.info("Clicking at the 'Currency Button'");
        mainPage.clickCurrencyDropDownButton();
        logger.info("Clicking at the 'USD Currency Button'");
        mainPage.clickUSDCurrencyDropDownButton();
        String textOfElement = mainPage.getCurrencySignButton().getText();
        Assert.assertTrue(textOfElement.contains(mainPage.getCurrencySign()));
        logger.info("checkClickUSDCurrencyDropDownButton TEST --- PASSED---");
        System.out.println("checkClickUSDCurrencyDropDownButton TEST --- PASSED---");
    }
}