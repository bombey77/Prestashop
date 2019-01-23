package view;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;
import tests.BaseTest;
import java.util.*;

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

    @FindBy(xpath = "//*[@id='js-product-list']/div[1]/*")
    private List<WebElement> priceList;

    @FindBy(xpath = "//*[@id=\"js-product-list\"]/div[1]/article[4]/div/div[1]/div/*")
    private List<WebElement> discount;

    @FindBy(xpath = "//*[@class=\"product-miniature js-product-miniature\"]")
    private List<WebElement> webElements;

    private Map<Double, Price> discountProducts;

    public SearchPage(WebDriver webDriver) {
        this.webDriver = webDriver;
        PageFactory.initElements(webDriver, this);
    }

    public void searchByCatalog(String text) {
        searchField.clear();
        searchField.sendKeys(text.toLowerCase());
        serchButton.click();
    }

    public WebElement getFieldOfSearchedElements() {
        fieldOfSearchedElements = (new WebDriverWait(webDriver, 30))
                .until(ExpectedConditions.presenceOfElementLocated(By.xpath("(//*[@id=\"js-product-list\"]/div/*)")));
        return fieldOfSearchedElements;
    }

    public List<WebElement> getListOfSearchedElements() {
        return listOfSearchedElements;
    }

    public int getCountOfSearchedElements() {
        String[] countOfElementsText = countOfSearchedElements.getText().split(" ");
        int elements = Integer.valueOf(countOfElementsText[1].substring(0, countOfElementsText[1].length() -1));
        return elements;
    }

    public boolean findTextInSearchedElements(String text) {
        boolean contains = true;
        List<WebElement> listOfSearchedElements = getListOfSearchedElements();
        List<String> listOfTitle = new ArrayList<String>();

        for (int i = 0; i < getListOfSearchedElements().size(); i++) {
            listOfTitle.add((listOfSearchedElements.get(i).getText()).toLowerCase());
        }

        for (int i = 0; i < listOfTitle.size(); i++) {
            System.out.println(i + " element found " + text + " = " + listOfTitle.get(i).contains(text));
            if (!listOfTitle.get(i).contains(text)) contains = false;
        }
        return contains;
    }

    public void sortingGoodsByPrice() {
        for (int i = 0; i < priceList.size(); i++) {
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

//    public void checkDiscountAndRegularPrice() {
//        for (int i = 0; i < discount.size(); i++) {
//            System.out.println(discount.get(i).getText());
//            System.out.println("=======================");
//        }
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
//    }

    public boolean checkDiscountAndRegularPrice() {
        discountProducts = new HashMap<>();
        double discount;
        boolean priceExisting = false;

        for (int i = 0; i < priceList.size(); i++) {
            String[] mas = priceList.get(i).getText().split("\n");
            if (mas[2].contains("%")) {
                String[] s = mas[2].split("\n");
                discount = Double.valueOf(s[0].substring(1, s[0].length()-1));
                String[] regularPrice = mas[1].split(" ");
                String[] price = mas[3].split(" ");
                discountProducts.put(discount, new Price(Double.valueOf(regularPrice[0]
                        .replace(",", ".")),
                        Double.valueOf(price[0].replace(",", "."))));
                priceExisting = (Double.valueOf(regularPrice[0]
                        .replace(",", ".")) > 0 && Double.valueOf(price[0].replace(",", ".")) > 0);
            }
        }
        discountProducts.forEach((k,v) -> System.out.println("Discount = " + k + ", regularPrice = "
                + v.getRegularPrice() + ", price = " + v.getPrice()));
        return priceExisting;
    }

    public boolean checkDiscountValue() {
        double regularPrice = 0;
        double price = 0;
        double resultWithDiscount = 0;

        boolean percentageEquality = true;

        for (Double d : discountProducts.keySet()) {
            System.out.println(d);
            regularPrice = discountProducts.get(d).getRegularPrice();
            price = discountProducts.get(d).getPrice();

            resultWithDiscount = regularPrice * (1 - d / 100);
            if (resultWithDiscount != price) {
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
        sortingDropDownList.click();
    }

    public SearchPage clickSortingDropDownListItemMaxToMin() {
        sortingDropDownListItemMaxToMin.click();
        return new SearchPage(webDriver);
    }

    private static class Price {

        private double regularPrice;
        private double price;

        public Price(double regularPrice, double price) {
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

    public void test() {
        for (int i = 0; i < webElements.size(); i++) {
            try{
                WebElement webElement = webElements.get(i).findElement(By.className("discount-percentage"));

            } catch (Exception e) {

            }
        }

    }
}