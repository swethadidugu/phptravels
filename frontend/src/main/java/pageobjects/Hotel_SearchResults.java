package pageobjects;

import com.sun.istack.internal.NotNull;
import java.lang.String;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.SeleniumUtils;
import java.util.List;

/**
 * Created by sweth on 4/20/2020.
 */
public class Hotel_SearchResults {

    public static By hotelsHeader = By.xpath("//h3[@class='heading-title']//span[text()='Hotels     ']");
    public static By searchCards = By.xpath("//ul[@id='LIST']//li[@class='loadAll' and @style='display: list-item;']");
    public static String ratingXpath = "//label[@class='custom-control-label' and @for='var']";
    public static By modifyButton = By.xpath("//div[@class='container']//button[text()='Modify']");
    public static By modifyDestn = By.xpath("//div[@id='s2id_autogen1']//span");
    public static By modifySearch = By.xpath("//div[@id='change-search']//button[@type='submit']");
    public static By modifyCity = By.xpath("//div[@class='container']//span[@class='text-primary']");
    public static By hotelDetails = By.xpath("//ul[@id='LIST']//li[@class='loadAll' and @style='display: list-item;']//button[@type='submit']");
    public static By cityOptions = By.xpath("//ul[@class='select2-result-sub']");
    public static By priceAscOrder = By.xpath("//div//label[@for='priceOrderAsc']");
    public static By priceDescOrder = By.xpath("//div//label[@for='priceOrderDes']");
    public static By viewMore = By.xpath("//button[@id='loadMore' and text()='View More']");
    public static String TargetPriceLine = "//div[@class='box-content']//span[@class='irs-slider from' and @style='left: var%;']";
    public static By InitialPriceLine =By.xpath("//div[@class='box-content']//span[@class='irs-slider from' and @style='left: 0%;']");
    public static By priceFrom = By.xpath("//div[@class='box-content']//span[@class='irs']//span[@class='irs-from']");
    public static By priceTo = By.xpath("//div[@class='box-content']//span[@class='irs']//span[@class='irs-to']");



    //Finding the number of search results
    public static int searchCount(@NotNull WebDriver driver) {
        //SeleniumUtils.isElementDisplayed(driver.findElement(searchCards));
        List<WebElement> searchPages = driver.findElements(searchCards);
        return searchPages.size();
    }

    //Filtering the search results according to the given rating
    public static List<WebElement> filterByHotelRating(@NotNull WebDriver driver,@NotNull int rating) {
        SeleniumUtils.isElementDisplayed(driver.findElement(searchCards));

        WebElement reqRating = driver.findElement(By.xpath(ratingXpath.replace("var", String.valueOf(rating))));

        reqRating.click();

        return driver.findElements(searchCards);
    }

    //Verifying the search result page have displayed properly for the given input star rating
    public static boolean verifyFilterRatings(@NotNull int star,@NotNull List<WebElement> ratingSearchCards) {
        for (WebElement rating : ratingSearchCards) {
            if (!(rating.getAttribute("data-stars").equals(String.valueOf(star)))) {
                return false;
            }
        }
        return true;
    }

    //Getting Search Results for the modified input city (change)
    public static WebElement modifyDestination(@NotNull WebDriver driver, @NotNull String dest) {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        driver.findElement(modifyButton).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(modifyDestn));
        Actions action = new Actions(driver);
        action.moveToElement(driver.findElement(modifyDestn)).click().sendKeys(dest).build().perform();
        SeleniumUtils.listSelection(driver, "", driver.findElements(cityOptions));
        wait.until(ExpectedConditions.elementToBeClickable(modifySearch));
        action.moveToElement(driver.findElement(modifySearch)).click().build().perform();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(modifySearch));
        return driver.findElement(modifyCity);
    }

    //Getting Search Results for the given price order(High to low/Low to high)
    public static List<WebElement> priceOrderSearch(@NotNull WebDriver driver,@NotNull String order) {
        WebDriverWait wait = new WebDriverWait(driver, 30);
        if ("ascending".contains(order)) {
            driver.findElement(priceAscOrder).click();
            SeleniumUtils.waitForTime(3000);
            return driver.findElements(searchCards);
        } else if ("descending".contains(order)) {
            driver.findElement(priceDescOrder).click();
            SeleniumUtils.waitForTime(3000);
            return driver.findElements(searchCards);
        } else {
            System.out.println("input could be either ascending/descending but the given input is " + order);
        }
        return null;
    }

    //Verifying Search Results for given price order
    public static boolean verifyPriceOrder(@NotNull WebDriver driver,@NotNull List<WebElement> searchResult, @NotNull String order) {
        if ("ascending".contains(order)) {
            for (int i = 1; i < searchResult.size(); i++) {
                double a = Double.parseDouble(searchResult.get(i - 1).getAttribute("data-price"));
                double b = Double.parseDouble(searchResult.get(i).getAttribute("data-price"));
                if (a - b > 0) {
                    return false;
                }
            }
            return true;
        }
            if ("descending".contains(order)) {
                for (int i = 1; i < searchResult.size(); i++) {
                    double a = Double.parseDouble(searchResult.get(i - 1).getAttribute("data-price"));
                    double b = Double.parseDouble(searchResult.get(i).getAttribute("data-price"));
                    if (a - b < 0) {
                        return false;
                    }
                }
                return true;
            }
            System.out.println("input could be either ascending/descending but the given input is " + order);
            return false;
        }

    //Dragging the price filter (change)
    public static List<WebElement> draggingPriceLine(@NotNull WebDriver driver, @NotNull double targetPercentile) {
        Actions action = new Actions(driver);
        WebElement source = driver.findElement(InitialPriceLine);
        action.clickAndHold(source);
        action.moveByOffset(10,0).release();
        action.build().perform();
        /*WebElement target = driver.findElement(By.xpath(TargetPriceLine.replace("var",String.valueOf(targetPercentile))));
        action.dragAndDrop(source,target);*/
        SeleniumUtils.waitForTime(15000);
        return driver.findElements(searchCards);
    }

    //Verifying the price drag filter search
    public static Boolean verifyPriceFilter(@NotNull WebDriver driver,@NotNull List<WebElement> result) {
        int min = (int) Double.parseDouble(driver.findElement(priceFrom).getText());
        int max = (int) Double.parseDouble(driver.findElement(priceTo).getText());
        for(WebElement e : result) {
            int actualPrice = (int) Double.parseDouble(e.getAttribute("data-price"));
            if(actualPrice<min && actualPrice>max) {
                return false;
            }
        }
        return true;
        }

    //Navigating to next page
    public static Boolean goToHotelDetails(@NotNull WebDriver driver,@NotNull int index) {
        WebDriverWait wait = new WebDriverWait(driver, 30);
        SeleniumUtils.waitForTime(3000);
        driver.findElements(hotelDetails).get(index).submit();
        Hotel_Details nextPage = new Hotel_Details();
        wait.until(ExpectedConditions.visibilityOfElementLocated(nextPage.pageHeader));
        return driver.findElement(nextPage.pageHeader).isDisplayed();
    }

}