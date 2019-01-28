package view;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import tests.BaseTest;
import java.math.BigDecimal;
import java.util.*;

public class SearchPage extends BaseTest {

    private static final String WHITE_SPACE = " ";
    private static final String COMMA = ",";
    private static final String POINT = ".";


    @FindBy(css = ".ui-autocomplete-input")
    private WebElement searchField;

    @FindBy(xpath = "//*[@type='submit']/i")
    private WebElement serchButton;

    @FindBy(xpath = "//*[@class='products row']/*")
    private List<WebElement> listOfSearchedElements;

    @FindBy(xpath = "//*[@id='js-product-list-top']//p")
    private WebElement countOfSearchedElements;

    @FindBy(css = ".select-title")
    private WebElement sortingDropDownList;

    @FindBy(xpath = "//*[@id='js-product-list-top']//a[5]")
    private WebElement sortingGoodsFromMaxToMinPriceListItem;

//    @FindBy(xpath = "//*[@id='js-product-list']/div[1]/*")
//    private List<WebElement> priceList;

    private Map<Double,Price> discountProducts;

    private Map<Double,Double> discounts;

    public SearchPage(WebDriver webDriver) {
        this.webDriver = webDriver;
        PageFactory.initElements(webDriver, this);
    }

    public void searchByCatalog(String text) {
        new WebDriverWait(webDriver, 30)
                .until(ExpectedConditions.visibilityOfElementLocated(
                        By.cssSelector(".ui-autocomplete-input")));
        searchField.clear();
        searchField.sendKeys(text.toLowerCase());
        new WebDriverWait(webDriver, 30)
                .until(ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//*[@type='submit']/i")));
        serchButton.click();
    }

    public List<WebElement> getListOfSearchedElements() {
        new WebDriverWait(webDriver, 30)
                .until(ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//*[@class='products row']/*")));
        return listOfSearchedElements;
    }

    public int getCountOfSearchedElements() {
        new WebDriverWait(webDriver, 30)
                .until(ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//*[@id='js-product-list-top']//p")));
        String[] countOfElementsText = countOfSearchedElements.getText().split(" ");
        int element = Integer.valueOf(countOfElementsText[1].substring(0, countOfElementsText[1].length() -1));
        return element;
    }

    public boolean findTextInSearchedElements(String text) {
        boolean containsText = true;
        List<WebElement> listOfSearchedElements = getListOfSearchedElements();
        List<String> listOfTitle = new ArrayList<>();

        for (int i = 0; i < getListOfSearchedElements().size(); i++) {
            listOfTitle.add((listOfSearchedElements.get(i).getText()).toLowerCase());
        }

        for (int i = 0; i < listOfTitle.size(); i++) {
            System.out.println(i + " element found " + text + " = " + listOfTitle.get(i).contains(text));
            if (!listOfTitle.get(i).contains(text)) containsText = false;
        }
        return containsText;
    }

    public boolean sortingGoodsByPrice() {

        new WebDriverWait(webDriver, 30)
                .until(ExpectedConditions.refreshed(ExpectedConditions.visibilityOfAllElementsLocatedBy(
                        By.xpath("//*[@class='products row']/*"))));
        List<WebElement> webElementList = webDriver.findElements(By.xpath("//*[@class='products row']/*"));

        String regularPriceClass = "regular-price";
        String priceClass = "price";
        List<Double> unSortedPriceList = new LinkedList<>();
        List<Double> sortedPriceList = new LinkedList<>();

        for (int i = 0; i < webElementList.size(); i++) {
            try{
                new WebDriverWait(webDriver,30).until(ExpectedConditions.visibilityOfElementLocated(
                        By.className(regularPriceClass)));
                WebElement regularPrice = webElementList.get(i).findElement(By.className(regularPriceClass));
                if (!regularPrice.equals(null)) {
                    unSortedPriceList.add(divider(regularPrice.getText()));
                    sortedPriceList.add(divider(regularPrice.getText()));
                }
            } catch (Exception e) {
                new WebDriverWait(webDriver,30).until(ExpectedConditions.visibilityOfElementLocated(
                        By.className(priceClass)));
                WebElement price = webElementList.get(i).findElement(By.className(priceClass));
                unSortedPriceList.add(divider(price.getText()));
                sortedPriceList.add(divider(price.getText()));
            }
        }
        Collections.sort(sortedPriceList);
        Collections.reverse(sortedPriceList);
        return unSortedPriceList.equals(sortedPriceList);
    }

    private static double divider(String text) {
        String[] lines = text.split(WHITE_SPACE);
        return Double.valueOf(lines[0].replace(COMMA, POINT));

    }

    public boolean checkDiscountAndRegularPrice() {
        discountProducts = new HashMap<>();
        double discount;
        boolean priceExisting = false;
        String dollar = "%";

        new WebDriverWait(webDriver, 30)
                .until(ExpectedConditions.refreshed(ExpectedConditions.visibilityOfAllElementsLocatedBy(
                        By.xpath("//*[@class='products row']/*"))));

        for (int i = 0; i < listOfSearchedElements.size(); i++) {
            new WebDriverWait(webDriver, 30)
                    .until(ExpectedConditions.refreshed(ExpectedConditions.visibilityOfElementLocated(
                            By.xpath("//*[@class='product-miniature js-product-miniature']"))));
            String[] masElements = listOfSearchedElements.get(i).getText().split("\n");
            if (masElements[2].contains(dollar)) {
                String[] masLines = masElements[2].split("\n");
                discount = Double.valueOf(masLines[0].substring(1, masLines[0].length()-1));
                String[] regularPrice = masElements[1].split(WHITE_SPACE);
                String[] price = masElements[3].split(WHITE_SPACE);
                discountProducts.put(discount, new Price(divider(regularPrice[0]),
                        divider(price[0])));
                priceExisting = (divider(regularPrice[0]) >= 0 && divider(price[0]) >= 0);
            }
        }
        discountProducts.forEach((k,v) -> System.out.println("Discount = " + k + ", regularPrice = "
                + v.getRegularPrice() + ", price = " + v.getPrice()));
        return priceExisting;
    }

    public boolean checkDiscountValue() {
        BigDecimal regularPrice;
        BigDecimal price;
        boolean percentageEquality = true;

        for (Double discount : discountProducts.keySet()) {
            regularPrice = new BigDecimal(discountProducts.get(discount).getRegularPrice());
            price = new BigDecimal(discountProducts.get(discount).getPrice());

            BigDecimal percentage = ((price.subtract(regularPrice))).multiply(new BigDecimal(100));

            Formatter formatter = new Formatter();
            formatter.format("%.2f", percentage.negate());
            String stringPercentage = formatter.toString();

            String result = stringPercentage.substring(0, stringPercentage.length()-3);
            double actualDiscount = Double.valueOf(result);
            if (actualDiscount != discount) {
                System.out.println("Discount is correct = " + (actualDiscount == discount));
                percentageEquality = false;
            }
        }
        return percentageEquality;
    }

    public boolean currencyOfItemInSearchList(String text) {
        return findTextInSearchedElements(text);
    }

    public void clickSortingDropDownList() {
        new WebDriverWait(webDriver, 30)
                .until(ExpectedConditions.visibilityOfElementLocated(
                        By.cssSelector(".select-title")));
        sortingDropDownList.click();
    }

    public void clickSortingDropDownListItemMaxToMin() {
        new WebDriverWait(webDriver, 30)
                .until(ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//*[@id='js-product-list-top']//a[5]")));
        sortingGoodsFromMaxToMinPriceListItem.click();
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

        new WebDriverWait(webDriver, 30)
                .until(ExpectedConditions.refreshed(ExpectedConditions.visibilityOfAllElementsLocatedBy(
                        By.xpath("//*[@class='products row']/*"))));
        List<WebElement> webElementList = webDriver.findElements(By.xpath("//*[@class='products row']/*"));

        String regularPriceClass = "regular-price";
        String priceClass = "price";
        String discountClass = "discount-percentage";

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

                System.out.println(discountProducts.size());

            } catch (Exception e) {
                System.out.println("Found product without discount");
            }
        }
    }

    public Map<Double,Price> getDiscountProducts() {
        return discountProducts;
    }

    public void checkDiscountValue2() {
        BigDecimal regularPrice;
        BigDecimal price;
        boolean percentageEquality = true;

        for (Double discount : discountProducts.keySet()) {

            System.out.println(discountProducts.get(discount).getRegularPrice()+ " --- " +discountProducts.get(discount).getPrice());
            regularPrice = new BigDecimal(discountProducts.get(discount).getRegularPrice());
            price = new BigDecimal(discountProducts.get(discount).getPrice());

            BigDecimal percentage = ((price.subtract(regularPrice))).multiply(new BigDecimal(100));

            Formatter formatter = new Formatter();
            formatter.format("%.2f", percentage.negate());
            String stringPercentage = formatter.toString();

            String result = stringPercentage.substring(0, stringPercentage.length()-3);
            double actualDiscount = Double.valueOf(result);

            System.out.println("Actual Discount = " + actualDiscount + ", discount = " + discount);
            if (actualDiscount != discount) {
                System.out.println("Discount is correct = " + (actualDiscount == discount));
                percentageEquality = false;
            }
            System.out.println("put actualDiscount = " + actualDiscount + " discount = " + discount);
            discounts.put(actualDiscount, discount);
        }
    }

    public Map<Double, Double> getDiscounts() {
        return discounts;
    }
}