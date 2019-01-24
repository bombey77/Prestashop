package view;

import com.google.common.util.concurrent.AtomicDouble;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import tests.BaseTest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class SearchPage extends BaseTest {

    @FindBy(xpath = "//*[@id='search_widget']/form/input[2]")
    private WebElement searchField;

    @FindBy(xpath = "//*[@id='search_widget']/form/button/i")
    private WebElement serchButton;

    @FindBy(xpath = "(//*[@id='js-product-list']/div/*)")
    private WebElement fieldOfSearchedElements;

    @FindBy(xpath = "//*[@id='js-product-list']/div[1]/*")
    private List<WebElement> listOfSearchedElements;

    @FindBy(xpath = "//*[@id='js-product-list-top']/div[1]/p")
    private WebElement countOfSearchedElements;

    @FindBy(xpath = "//*[@id='js-product-list-top']/div[2]/div/div/a")
    private WebElement sortingDropDownList;

    @FindBy(xpath = "//*[@id='js-product-list-top']/div[2]/div/div/div/a[5]")
    private WebElement sortingDropDownListItemMaxToMin;

    @FindBy(xpath = "//*[@id='js-product-list']/div[1]")
    private List<WebElement> priceList;

//    @FindBy(xpath = "//*[@id='js-product-list']/div[1]/article[4]/div/div[1]/div/*")
//    private List<WebElement> discount;

    @FindBy(xpath = "//*[@class='product-miniature js-product-miniature']")
    private List<WebElement> webElements;

    private Map<Double, Price> discountProducts;
    private static String PERCENTAGE = "%";
    private static int ZERO = 0;
    private static int ONE = 1;

    public SearchPage(WebDriver webDriver) {
        this.webDriver = webDriver;
        PageFactory.initElements(webDriver, this);
    }

    public void searchByCatalog(String text) {
        searchField.clear();
        searchField.sendKeys(text.toLowerCase());
        serchButton.click();
        new WebDriverWait(webDriver, 30).until(ExpectedConditions
                .visibilityOfElementLocated(By.xpath("//*[@id='js-product-list']/div[1]/*")));
    }

    public WebElement getFieldOfSearchedElements() {
        fieldOfSearchedElements = new WebDriverWait(webDriver, 30)
                .until(ExpectedConditions.presenceOfElementLocated(By.xpath("(//*[@id='js-product-list']/div/*)")));
        return fieldOfSearchedElements;
    }

    public List<WebElement> getListOfSearchedElements() {
        return listOfSearchedElements;
    }

    public int getCountOfSearchedElements() {
        countOfSearchedElements = new WebDriverWait(webDriver, 30)
                .until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='js-product-list-top']/div[1]/p")));
        String[] countOfElementsText = countOfSearchedElements.getText().split(" ");
        int elements = Integer.valueOf(countOfElementsText[1].substring(ZERO, countOfElementsText[ONE].length() -ONE));
        return elements;
    }

    public boolean findTextInSearchedElements(String text) {
        boolean contains = true;
        List<WebElement> listOfSearchedElements = getListOfSearchedElements();
        List<String> listOfTitles = new ArrayList<>();

        for (int i = ZERO; i < getListOfSearchedElements().size(); i++) {
            listOfTitles.add((listOfSearchedElements.get(i).getText()).toLowerCase());
        }

        for (int i = ZERO; i < listOfTitles.size(); i++) {
            System.out.println(i + " element found " + text + " = " + listOfTitles.get(i).contains(text));
            if (!listOfTitles.get(i).contains(text)) contains = false;
        }
        return contains;
    }

    public void sortingGoodsByPrice() {
        new WebDriverWait(webDriver, 30).until(ExpectedConditions
                .presenceOfElementLocated(By.xpath("//*[@id='js-product-list']/div[1]/*")));
        for (int i = ZERO; i < priceList.size(); i++) {
            System.out.println(priceList.get(i).getText());
            System.out.println("=======================");
        }
//        List<Double> price = new ArrayList<>();
//
//        List<String> elements = new ArrayList<>();
//        for (int i = 0; i < priceList.size(); i++) {
//            elements.add(priceList.get(i).getText());
//            String[] strings = elements.get(i).split("\n");
//            String[] res = strings[1].split(" ");
//            String s = res[0].replace(",", ".");
//            price.add(Double.valueOf(s));
//        }
//
//        Collections.sort(price);
//
//        for (int i = 0; i < price.size(); i++) {
//            System.out.println("res " + i + " = " + price.get(i));
//        }
    }

    public boolean checkDiscountAndRegularPrice() {
        discountProducts = new HashMap<>();
        double discount;
        boolean priceExisting = false;

        new WebDriverWait(webDriver, 30).until(ExpectedConditions
                .presenceOfElementLocated(By.xpath("//*[@id='js-product-list']/div[1]")));

        for (int i = ZERO; i < priceList.size(); i++) {
            String[] mas = priceList.get(i).getText().split("\n");
            int TWO = 2;
            if (mas[TWO].contains(PERCENTAGE)) {
                String[] s = mas[TWO].split("\n");
                discount = Double.valueOf(s[ZERO].substring(ONE, s[ZERO].length()-ONE));
                String[] regularPrice = mas[ONE].split(" ");
                int THREE = 3;
                String[] price = mas[THREE].split(" ");
                discountProducts.put(discount, new Price(Double.valueOf(regularPrice[ZERO]
                        .replace(",", ".")),
                        Double.valueOf(price[ZERO].replace(",", "."))));
                priceExisting = (Double.valueOf(regularPrice[ZERO]
                        .replace(",", ".")) > ZERO &&
                        Double.valueOf(price[ZERO].replace(",", ".")) > ZERO);
            }
        }
        discountProducts.forEach((k,v) -> System.out.println("Discount = " + k + ", regularPrice = "
                + v.getRegularPrice() + ", price = " + v.getPrice()));
        return priceExisting;
    }

    public boolean checkDiscountValue() {
        BigDecimal regularPrice;
        BigDecimal price;
        BigDecimal resultWithDiscount;

        boolean percentageEquality = true;

        for (Double discountValue : discountProducts.keySet()) {
            System.out.println(discountValue);
            regularPrice = new BigDecimal(discountProducts.get(discountValue).getRegularPrice());
            price = new BigDecimal(discountProducts.get(discountValue).getPrice());

            int HUNDRED = 100;
            resultWithDiscount = new BigDecimal((ONE - discountValue / HUNDRED));
            resultWithDiscount.multiply(regularPrice);

            System.out.println("ResultDiscount - " + resultWithDiscount);

            if (resultWithDiscount == price) {
                System.out.println("Discount is correct = " + (resultWithDiscount == price));
                percentageEquality = false;
            }
        }
        return percentageEquality;
    }

    public boolean currencyOfItemInSearchList(String text) {
        return findTextInSearchedElements(text);
    }

    public void clickSortingDropDownList() {
        sortingDropDownList = new WebDriverWait(webDriver, 30)
                .until(ExpectedConditions.presenceOfElementLocated(
                        By.xpath("//*[@id='js-product-list-top']/div[2]/div/div/a")));
        sortingDropDownList.click();
    }

    public void clickSortingDropDownListItemMaxToMin() {
        new WebDriverWait(webDriver, 30)
                .until(ExpectedConditions.presenceOfElementLocated(
                        By.xpath("//*[@id='js-product-list-top']/div[2]/div/div/div/a[5]")));
        sortingDropDownListItemMaxToMin.click();
    }

    private static class Price {

        private double regularPrice;
        private double price;

        private Price(double regularPrice, double price) {
            this.regularPrice = regularPrice;
            this.price = price;
        }

        private double getRegularPrice() {
            return regularPrice;
        }

        private double getPrice() {
            return price;
        }
    }

    public void test() {
        for (int i = 0; i < webElements.size(); i++) {
            try{
                WebElement webElement = webElements.get(i).findElement(By.className("discount-percentage"));

            } catch (Exception e) {

            }
        }

    }
}