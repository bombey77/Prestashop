package tests;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import view.CurrencyButton;
import static tests.ProjectLogger.logger;

@Listeners(CustomListener.class)
public class MainPageTest extends BaseTest {

    @Test(description = "Checking for UAH currency sign on items")
    public void checkForUAHCurrencySignOnItem() {
        logger.info("checkForUAHCurrencySignOnItem TEST --- STARTED ---");
        System.out.println("checkForUAHCurrencySignOnItem TEST --- STARTED---");
//        for (int i = 0; i < mainPage.getListOfPopularGoods().size(); i++) {
//            String textOfElement = mainPage.getListOfPopularGoods().get(i).getText();
//            logger.info("Element " + i + " has sign = " + mainPage.getCurrencySign());
//            System.out.println("Element " + i + " has sign = " + mainPage.getCurrencySign());
//            Assert.assertTrue(textOfElement.contains(mainPage.getCurrencySign()));
//        }
        mainPage.findCurrencySignInSearchedElement(mainPage.getCurrencySign(), mainPage.getListOfPrices())
                .forEach(sign -> Assert.assertEquals(sign, mainPage.getCurrencySign()));
        logger.info("checkForUAHCurrencySignOnItem TEST --- PASSED---");
        System.out.println("checkForUAHCurrencySignOnItem TEST --- PASSED---");
    }

    @Test(description = "Checking for EUR currency sign on items")
    public void checkForEURCurrencySignOnItem() {
        logger.info("checkForUAHCurrencySignOnItem TEST --- STARTED ---");
        System.out.println("checkForEURCurrencySignOnItem TEST --- STARTED---");
        logger.info("Clicking at the 'Currency Button'");
        mainPage.clickCurrencyDropDownButton();
        logger.info("Clicking at the 'EUR Currency Button'");
        mainPage.clickCurrencyButton(CurrencyButton.EUR);

//        for (int i = 0; i < mainPage.getListOfPopularGoods().size(); i++) {
//            String textOfElement = mainPage.getListOfPopularGoods().get(i).getText();
//            System.out.println("Element " + i + " has sign = " + mainPage.getCurrencySign());
//            Assert.assertTrue(textOfElement.contains(mainPage.getCurrencySign()));
//        }
        mainPage.findCurrencySignInSearchedElement(mainPage.getCurrencySign(), mainPage.getListOfPrices())
                .forEach(sign -> Assert.assertEquals(sign, mainPage.getCurrencySign()));
        logger.info("checkForUAHCurrencySignOnItem TEST --- PASSED---");
        System.out.println("checkForEURCurrencySignOnItem TEST --- PASSED---");
    }

    @Test(description = "Checking for USD currency sign on items")
    public void checkForUSDCurrencySign() {
        logger.info("checkForUSDCurrencySign TEST --- STARTED ---");
        System.out.println("checkForUSDCurrencySign TEST --- STARTED---");
        logger.info("Clicking at the 'Currency Button'");
        mainPage.clickCurrencyDropDownButton();
        logger.info("Clicking at the 'USD Currency Button'");
        mainPage.clickCurrencyButton(CurrencyButton.USD);

//        for (int i = 0; i < mainPage.getListOfPopularGoods().size(); i++) {
//            String textOfElement = mainPage.getListOfPopularGoods().get(i).getText();
//            System.out.println("Element " + i + " has sign = " + mainPage.getCurrencySign());
//            Assert.assertTrue(textOfElement.contains(mainPage.getCurrencySign()));
//        }

        mainPage.findCurrencySignInSearchedElement(mainPage.getCurrencySign(), mainPage.getListOfPrices())
                .forEach(sign -> Assert.assertEquals(sign, mainPage.getCurrencySign()));
        //======================================
//        mainPage.getListOfPopularGoods().forEach((t) -> {
//            System.out.println("Element has sign = " + mainPage.getCurrencySign());
//            Assert.assertTrue(t.getText().contains(mainPage.getCurrencySign()));
//        });
        //======================================

        logger.info("checkForUSDCurrencySign TEST --- PASSED---");
        System.out.println("checkForUSDCurrencySign TEST --- PASSED---");
    }

    @Test(description = "Clicking that the USD currency drop-down button was clicked")
    public void checkClickUSDCurrencyDropDownButton() {
        logger.info("checkClickUSDCurrencyDropDownButton TEST --- STARTED ---");
        System.out.println("checkClickUSDCurrencyDropDownButton TEST --- STARTED---");
        logger.info("Clicking at the 'Currency Button'");
        mainPage.clickCurrencyDropDownButton();
        logger.info("Clicking at the 'USD Currency Button'");
        mainPage.clickCurrencyButton(CurrencyButton.USD);
        String textOfElement = mainPage.getCurrencySignButton().getText();
        Assert.assertTrue(textOfElement.contains(mainPage.getCurrencySign()));
        logger.info("checkClickUSDCurrencyDropDownButton TEST --- PASSED---");
        System.out.println("checkClickUSDCurrencyDropDownButton TEST --- PASSED---");
    }
}