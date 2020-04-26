package pageobjects;


import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.SeleniumUtils;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by sweth on 4/20/2020.
 */
public class Flights_HomePage {
    //Flights Tab Elements Declaration
    public static By flightsTab = By.xpath("//a[@data-name='flights']");
    public static By flightSearchBtn = By.xpath("//div[@id='flights']//button[@type='submit' and text()='\n" +
            "                        Search                        ']");
    public static By oneWayTrip = By.xpath("//input[@type='radio' and @value='oneway']/..//label[text()='One Way']");
    public static By roundTrip = By.xpath("//input[@type='radio' and @value='round']/..//label[text()='Round Trip']");
    public static By flightType = By.xpath("//div[@id='flights']//a[@class='chosen-single']//span[text()='Economy']");
    public static By flightTypeoptions = By.xpath("//div[@id='flights']//ul[@class='chosen-results']//li");
    public static By flightFrom = By.xpath("//div[@class='form-group']//span[text()='NYC']");
    public static By flightTo = By.xpath("//div[@class='form-group']//input[@type='text' and @value='MIA']");
    public static By flightDepart = By.xpath("//div[@class='form-icon-left']//input[@name='departure_date' and @id='FlightsDateStart']");
    public static By flightArrival = By.xpath("//div[@class='form-icon-left']//input[@name='reture_date' and @id='FlightsDateEnd']");
    public static By flightAdultCountUp = By.xpath("//input[@type='text' and @name='fadults']/..//button[@type='button' and text()='+']");
    public static By flightAdultCountDown = By.xpath("//input[@type='text' and @name='fadults']/..//button[@type='button' and text()='-']");
    public static By adultActualCount = By.xpath("//div[@class='col-4']//input[@type='text' and @name='fadults']");
    public static By flightChildCountDown = By.xpath("//input[@type='text' and @name='fchildren']/..//button[@type='button' and text()='-']");
    public static By childActualCount = By.xpath("//div[@class='col-4']//input[@type='text' and @name='fchildren']");
    public static By flightChildCountUp = By.xpath("//input[@type='text' and @name='fchildren']/..//button[@type='button' and text()='+']");
    public static By flightInfantCountUp = By.xpath("//input[@type='text' and @name='finfant']/..//button[@type='button' and text()='+']");
    public static By flightInfantCountDown = By.xpath("//input[@type='text' and @name='finfant']/..//button[@type='button' and text()='-']");
    public static By infantActualCount = By.xpath("//div[@class='col-4']//input[@type='text' and @name='finfant']");


    public static Boolean flightSearch(@NotNull WebDriver driver, @NotNull String tripType, @NotNull String flightClass, @NotNull String from, @NotNull String to, @NotNull String dateFrom, String dateTo, int adultCount, int childCount, int infantCount) {
        WebDriverWait wait = new WebDriverWait(driver,30);
        Actions action = new Actions(driver);
        driver.findElement(flightsTab).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(oneWayTrip));
        if(tripType.equalsIgnoreCase("one way")){
            driver.findElement(oneWayTrip).click();
        }
        else {
            driver.findElement(roundTrip).click();
        }
        driver.findElement(flightType).click();
        if("first class".contains(flightClass.toLowerCase())) {
            driver.findElements(flightTypeoptions).get(0).click();
        }
        else if("business class".contains(flightClass.toLowerCase())) {
            driver.findElements(flightTypeoptions).get(1).click();
        }
        else {
            driver.findElements(flightTypeoptions).get(2).click();
        }
        action.sendKeys(Keys.TAB);
        action.sendKeys(driver.findElement(flightFrom),from).build().perform();
        SeleniumUtils.waitForTime(3000);
        List<WebElement> locations = driver.findElements(By.xpath("//ul[@class='select2-results']//li"));
        SeleniumUtils.listSelection(driver,"", locations);
        action.sendKeys(Keys.TAB);
        action.sendKeys(driver.findElement(flightTo),to).build().perform();
        SeleniumUtils.waitForTime(3000);
        List<WebElement> location = driver.findElements(By.xpath("//div[@id='select2-drop']//ul[@class='select2-results']//li"));
        SeleniumUtils.listSelection(driver,"", location);
        action.sendKeys(Keys.TAB);
        ((JavascriptExecutor)driver).executeScript ("document.getElementById('FlightsDateStart').removeAttribute('readonly',0);"); // Enables the from date box
        driver.findElement(flightDepart).clear();
        driver.findElement(flightDepart).sendKeys(dateFrom);
        action.sendKeys(Keys.ESCAPE);
        if("round trip".contains(tripType.toLowerCase())) {
            ((JavascriptExecutor)driver).executeScript ("document.getElementById('FlightsDateEnd').removeAttribute('readonly',0);"); // Enables the from date box
            driver.findElement(flightArrival).clear();
            driver.findElement(flightArrival).sendKeys(dateTo);
            action.sendKeys(Keys.ESCAPE);
           // action.sendKeys(Keys.TAB);
        }
        SeleniumUtils.totalCount(driver,driver.findElement(flightAdultCountUp),driver.findElement(flightAdultCountDown),adultCount,driver.findElement(adultActualCount));
        SeleniumUtils.totalCount(driver,driver.findElement(flightChildCountUp),driver.findElement(flightChildCountDown),childCount,driver.findElement(childActualCount));
        SeleniumUtils.totalCount(driver,driver.findElement(flightInfantCountUp),driver.findElement(flightInfantCountDown),infantCount,driver.findElement(infantActualCount));
        driver.findElement(flightSearchBtn).submit();
        wait.until(ExpectedConditions.visibilityOfElementLocated(Flights_SearchResults.header));
        return driver.findElement(Flights_SearchResults.header).isDisplayed();

    }
}
