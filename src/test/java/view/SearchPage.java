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

    private List<Double> unSortedPriceList;

    private List<Double> sortedPriceList;

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
        String[] countOfElementsText = countOfSearchedElements.getText().split(WHITE_SPACE);
        return Integer.valueOf(countOfElementsText[1].substring(0, countOfElementsText[1].length() -1));
    }

    public List<String> findTextInSearchedElements(String text) {
        List<String> list = new LinkedList<>();
        wait(listOfSearchedElements);
        listOfSearchedElements.forEach(textInElement -> {
            boolean validValue = textInElement.getText().toLowerCase().contains(text);
            System.out.println("Element found " + text + " = "
                    + textInElement.getText().toLowerCase().contains(text));
            if (validValue) list.add(text);
             else list.add(null);
        });
        return list;
    }

    public void sortingGoodsByPrice() {
        wait(listOfPrices);
        unSortedPriceList = new LinkedList<>();
        listOfPrices.forEach(element -> unSortedPriceList.add(Double.valueOf(element.getText()
                .substring(0, element.getText().length()-2).replace(COMMA,POINT))));
        sortedPriceList = new LinkedList<>(unSortedPriceList);
        sortedPriceList.stream().sorted(Collections.reverseOrder());
    }

    public void clickSortingDropDownList() {
        click(sortingDropDownList);
    }

    public void clickSortingDropDownListItemMaxToMin() {
        click(sortingGoodsFromMaxToMinPriceListItem);
    }

    public void findGoodsWithDiscount() {
        discountProducts = new HashMap<>();
        wait(listOfSearchedElements);

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String regularPriceClass = "regular-price";
        String priceClass = "price";
        String discountClass = "discount-percentage";

        listOfSearchedElements.forEach(element -> {
            try {
                wait(regularPriceClass,priceClass,discountClass);
                System.out.println(
                    "Regular price = " + element.findElement(By.className(regularPriceClass)).getText()
                            .replace(COMMA,POINT).split(WHITE_SPACE)[0] +
                            ", discount price = " + element.findElement(By.className(priceClass)).getText()
                            .replace(COMMA,POINT).split(WHITE_SPACE)[0] +
                            ", discount = " + element.findElement(By.className(discountClass)).getText()
                            .substring(1,element.findElement(By.className(discountClass)).getText().length()-1));
                discountProducts.put(Double.valueOf(element.findElement(By.className(discountClass)).getText()
                                .substring(1,element.findElement(By.className(discountClass)).getText().length()-1)),
                        new Price(Double.valueOf(element.findElement(By.className(regularPriceClass)).getText()
                                .replace(COMMA,POINT).split(WHITE_SPACE)[0]),
                                Double.valueOf(element.findElement(By.className(priceClass)).getText()
                                        .replace(COMMA,POINT).split(WHITE_SPACE)[0])));
            } catch (Exception e) {
                System.out.println("Found product without the discount");
            }
        });
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

    public List<WebElement> getListOfPrices() {
        wait(listOfPrices);
        return listOfPrices;
    }

    public List<Double> getUnSortedPriceList() {
        return unSortedPriceList;
    }

    public List<Double> getSortedPriceList() {
        return sortedPriceList;
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
}