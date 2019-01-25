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

    @FindBy(css = ".ui-autocomplete-input")
    private WebElement searchField;

    @FindBy(xpath = "//*[@type='submit']/i")
    private WebElement serchButton;

    @FindBy(xpath = "//*[@id='js-product-list']/div[1]/*")
    private List<WebElement> listOfSearchedElements;

    @FindBy(xpath = "//*[@id='js-product-list-top']//p")
    private WebElement countOfSearchedElements;

    @FindBy(xpath = "//*[@id='js-product-list-top']/div[2]/div/div/a")
    private WebElement sortingDropDownList;

    @FindBy(xpath = "//*[@id='js-product-list-top']/div[2]/div/div/div/a[5]")
    private WebElement sortingDropDownListItemMaxToMin;

    @FindBy(xpath = "//*[@id='js-product-list']/div[1]/*")
    private List<WebElement> priceList;

    private Map<Double, Price> discountProducts;

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
                        By.xpath("//*[@id='js-product-list']/div[1]/*")));
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

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        List<WebElement> webElementList = webDriver.findElements(By.xpath("//*[@id='js-product-list']/div[1]/*"));

        String regularPriceClass = "regular-price";
        String priceClass = "price";
        List<Double> unSortedPriceList = new LinkedList<>();
        List<Double> sortedPriceList = new LinkedList<>();

        for (int i = 0; i < webElementList.size(); i++) {
            try{
                WebElement regularPrice = webElementList.get(i).findElement(By.className(regularPriceClass));
                if (!regularPrice.equals(null)) {
                    unSortedPriceList.add(divider(regularPrice.getText()));
                    sortedPriceList.add(divider(regularPrice.getText()));
                }
            } catch (Exception e) {
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
        String whiteSpace = " ";
        String comma = ",";
        String point = ".";
        String[] lines = text.split(whiteSpace);
        double result = Double.valueOf(lines[0].replace(comma, point));
        return result;
    }

    public boolean checkDiscountAndRegularPrice() {
        discountProducts = new HashMap<>();
        double discount;
        boolean priceExisting = false;

        new WebDriverWait(webDriver, 30)
                .until(ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//*[@id='js-product-list']/div[1]/*")));

        for (int i = 0; i < priceList.size(); i++) {
            String[] masElements = priceList.get(i).getText().split("\n");
            if (masElements[2].contains("%")) {
                String[] masLines = masElements[2].split("\n");
                discount = Double.valueOf(masLines[0].substring(1, masLines[0].length()-1));
                String[] regularPrice = masElements[1].split(" ");
                String[] price = masElements[3].split(" ");
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
                        By.xpath("//*[@id='js-product-list-top']/div[2]/div/div/a")));
        sortingDropDownList.click();
    }

    public void clickSortingDropDownListItemMaxToMin() {
        new WebDriverWait(webDriver, 30)
                .until(ExpectedConditions.visibilityOfElementLocated(
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
}