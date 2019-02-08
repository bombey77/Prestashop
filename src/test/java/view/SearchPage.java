package view;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import tests.BaseTest;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class SearchPage extends BasePage {

    private static final String WHITE_SPACE = " ";
    private static final String COMMA = ",";
    private static final String POINT = ".";

    @FindBy(css = ".ui-autocomplete-input")
    private WebElement searchField;

    @FindBy(xpath = "//*[@type='submit']/i")
    private WebElement searchButton;

    @FindBy(xpath = "//*[@class='products row']/*")
    private List<WebElement> listOfSearchedElements;

    @FindBy(xpath = "//*[@id='js-product-list-top']//p")
    private WebElement countOfSearchedElements;

    @FindBy(css = ".select-title")
    private WebElement sortingDropDownList;

    @FindBy(xpath = "//*[@id='js-product-list-top']//a[5]")
    private WebElement sortingGoodsFromMaxToMinPriceListItem;

    @FindBy(xpath = "//*[@id='js-product-list']//div[1]/div/span[1]")
    private List<WebElement> listOfPrices;

    private Map<Double,Price> discountProducts;

    private Map<Double,Double> discounts;

    public SearchPage(WebDriver webDriver) {
        this.webDriver = webDriver;
        PageFactory.initElements(webDriver, this);
    }

    public void searchByCatalog(String text) {
        clear(searchField);
        searchField.sendKeys(text.toLowerCase());
        click(searchButton);
    }

    public List<WebElement> getListOfSearchedElements() {
        wait(listOfSearchedElements);
        return listOfSearchedElements;
    }

    public int getCountOfSearchedElements() {
        wait(countOfSearchedElements);
        String[] countOfElementsText = countOfSearchedElements.getText().split(" ");
        return Integer.valueOf(countOfElementsText[1].substring(0, countOfElementsText[1].length() -1));
    }

    public boolean findTextInSearchedElements(String text) {

        wait(listOfSearchedElements);
        listOfSearchedElements.forEach(t -> System.out.println("Element found " + text + " = " + t.getText().toLowerCase().contains(text)));

        Predicate<WebElement> predicateText = webElement -> webElement.getText().toLowerCase().contains(text);
        return listOfSearchedElements.stream().allMatch(predicateText);
    }

    public List<String> findCurrencySignInSearchedElement(String text) {
        wait(listOfPrices);
        List<String> list = new LinkedList<>();
        listOfPrices.forEach(t -> System.out.println("Element found " + text + " = " + t.getText().toLowerCase().contains(text)));
        listOfPrices.forEach(t -> list.add(t.getText().toLowerCase().substring(t.getText().length()-1)));
        return list;
    }

    public boolean sortingGoodsByPrice() {
        wait(listOfPrices);
        List<Double> unSortedPriceList = new LinkedList<>();
        listOfPrices.forEach(w -> unSortedPriceList.add(Double.valueOf(w.getText()
                .substring(0, w.getText().length()-2).replace(",","."))));

        List<Double> sortedPriceList = new LinkedList<>(unSortedPriceList);

        sortedPriceList.stream().sorted(Collections.reverseOrder());
        return unSortedPriceList.equals(sortedPriceList);
    }

    private static double divider(String text) {
        String[] lines = text.split(WHITE_SPACE);
        return Double.valueOf(lines[0].replace(COMMA, POINT));

    }

    public void clickSortingDropDownList() {
        click(sortingDropDownList);
    }

    public void clickSortingDropDownListItemMaxToMin() {
        click(sortingGoodsFromMaxToMinPriceListItem);
    }

    public static class Price {

        private double regularPrice;
        private double price;

        private Price(double regularPrice, double price) {
            this.regularPrice = regularPrice;
            this.price = price;
        }

        public double getRegularPrice() {
            return regularPrice;
        }

        public double getPrice() {
            return price;
        }
    }

    public void findGoodsWithDiscount() {

        discountProducts = new HashMap<>();
        wait(listOfSearchedElements);

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        List<WebElement> webElementList = webDriver.findElements(By.xpath("//*[@class='products row']/*"));

        String regularPriceClass = "regular-price";
        String priceClass = "price";
        String discountClass = "discount-percentage";

//        System.out.println("=================================");
//        webElementList.forEach(w -> {
//            try {
//                System.out.println(
//                        "Regular price = " + w.findElement(By.className(regularPriceClass)).getText() +
//                                ", discount price = " + w.findElement(By.className(priceClass)).getText() +
//                                ", discount = " + w.findElement(By.className(discountClass)).getText());
//            } catch (ArithmeticException e) {
//                System.out.println("--- Found product without discount ---");
//            }
//        });
//        System.out.println("=================================");

        for (int i = 0; i < webElementList.size(); i++) {
            try{
                new WebDriverWait(webDriver,30).until(ExpectedConditions.visibilityOfElementLocated(
                        By.className(regularPriceClass)));
                new WebDriverWait(webDriver,30).until(ExpectedConditions.visibilityOfElementLocated(
                        By.className(priceClass)));
                new WebDriverWait(webDriver,30).until(ExpectedConditions.visibilityOfElementLocated(
                        By.className(discountClass)));

                WebElement regularPrice = webElementList.get(i).findElement(By.className(regularPriceClass));
                WebElement price = webElementList.get(i).findElement(By.className(priceClass));
                WebElement discount = webElementList.get(i).findElement(By.className(discountClass));

                System.out.println("Regular price = " + divider(regularPrice.getText()) + ", discount price = " + divider(price.getText())
                + ", discount = " + discount.getText());

                String result = discount.getText().substring(1, discount.getText().length()-1);
                double actualDiscount = Double.valueOf(result);

                discountProducts.put(actualDiscount,new Price(divider(regularPrice.getText()),divider(price.getText())));
            } catch (Exception e) {
                System.out.println("Found product without discount");
            }
        }
    }

    public Map<Double,Price> getDiscountProducts() {
        return discountProducts;
    }

    public void checkDiscountValue() {
        BigDecimal regularPrice;
        BigDecimal price;
        discounts = new HashMap<>();

        for (Double discount : discountProducts.keySet()) {
            regularPrice = new BigDecimal(discountProducts.get(discount).getRegularPrice());
            price = new BigDecimal(discountProducts.get(discount).getPrice());

            BigDecimal percentage = ((price.subtract(regularPrice))).multiply(new BigDecimal(100));
            String stPercentage = String.format("%.2f", percentage.negate());
            double actualDiscount = Double.valueOf(stPercentage.substring(0, stPercentage.length()-3));
            discounts.put(actualDiscount, discount);

            System.out.println("Actual Discount = " + actualDiscount + ", discount = " + discount);
            if (actualDiscount != discount) {
                System.out.println("Discount is correct = " + (actualDiscount == discount));
            }
            System.out.println("put actualDiscount = " + actualDiscount + " discount = " + discount);
        }
    }

    public Map<Double, Double> getDiscounts() {
        return discounts;
    }
}