package tests;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import view.CurrencyButton;
import static tests.ProjectLogger.logger;

@Listeners(CustomListener.class)
public class MainPageTest extends BaseTest {

    @Test(description = "Checking for UAH currency sign on items")
    public void checkForUAHCurrencySign() {
        logger.info("checkForUAHCurrencySign TEST --- STARTED ---");
        System.out.println("checkForUAHCurrencySign TEST --- STARTED---");
        for (int i = 0; i < mainPage.getListOfPopularGoods().size(); i++) {
            String textOfElement = mainPage.getListOfPopularGoods().get(i).getText();
            logger.info("Element " + i + " has sign = " + mainPage.getCurrencySign());
            System.out.println("Element " + i + " has sign = " + mainPage.getCurrencySign());
            Assert.assertTrue(textOfElement.contains(mainPage.getCurrencySign()));
//            Assert.assertEquals(mainPage.getCurrencySign(), "₴");
        }
        logger.info("checkForUAHCurrencySign TEST --- PASSED---");
        System.out.println("checkForUAHCurrencySign TEST --- PASSED---");
    }

    @Test(description = "Checking for EUR currency sign on items")
    public void checkForEURCurrencySign() {
        logger.info("checkForUAHCurrencySign TEST --- STARTED ---");
        System.out.println("checkForEURCurrencySign TEST --- STARTED---");
        logger.info("Clicking at the 'Currency Button'");
        mainPage.clickCurrencyButton();
        logger.info("Clicking at the 'EUR Currency Button'");
        mainPage.clickCurrencyDropDownButton(CurrencyButton.EUR);

        for (int i = 0; i < mainPage.getListOfPopularGoods().size(); i++) {
            String textOfElement = mainPage.getListOfPopularGoods().get(i).getText();
            System.out.println("Element " + i + " has sign = " + mainPage.getCurrencySign());
            Assert.assertTrue(textOfElement.contains(mainPage.getCurrencySign()));
        }
        logger.info("checkForUAHCurrencySign TEST --- PASSED---");
        System.out.println("checkForEURCurrencySign TEST --- PASSED---");
    }

    @Test(description = "Checking for USD currency sign on items")
    public void checkForUSDCurrencySign() {
        logger.info("checkForUSDCurrencySign TEST --- STARTED ---");
        System.out.println("checkForUSDCurrencySign TEST --- STARTED---");
        logger.info("Clicking at the 'Currency Button'");
        mainPage.clickCurrencyButton();
        logger.info("Clicking at the 'USD Currency Button'");
        mainPage.clickCurrencyDropDownButton(CurrencyButton.USD);

        for (int i = 0; i < mainPage.getListOfPopularGoods().size(); i++) {
            String textOfElement = mainPage.getListOfPopularGoods().get(i).getText();
            System.out.println("Element " + i + " has sign = " + mainPage.getCurrencySign());
            Assert.assertTrue(textOfElement.contains(mainPage.getCurrencySign()));
        }
        logger.info("checkForUSDCurrencySign TEST --- PASSED---");
        System.out.println("checkForUSDCurrencySign TEST --- PASSED---");
    }

    @Test(description = "Clicking that the USD currency drop-down button was clicked")
    public void checkClickUSDCurrencyDropDownButton() {
        logger.info("checkClickUSDCurrencyDropDownButton TEST --- STARTED ---");
        System.out.println("checkClickUSDCurrencyDropDownButton TEST --- STARTED---");
        logger.info("Clicking at the 'Currency Button'");
        mainPage.clickCurrencyButton();
        logger.info("Clicking at the 'USD Currency Button'");
        mainPage.clickCurrencyDropDownButton(CurrencyButton.USD);
        String textOfElement = mainPage.getCurrencySignButton().getText();
        Assert.assertTrue(textOfElement.contains(mainPage.getCurrencySign()));
        logger.info("checkClickUSDCurrencyDropDownButton TEST --- PASSED---");
        System.out.println("checkClickUSDCurrencyDropDownButton TEST --- PASSED---");
    }
}