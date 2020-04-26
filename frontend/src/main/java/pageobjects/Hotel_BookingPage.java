package pageobjects;

import com.sun.istack.internal.NotNull;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.SeleniumUtils;

import java.lang.String;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by sweth on 4/24/2020.
 */
public class Hotel_BookingPage {
    public static By header = By.xpath("//div[@class='container booking' and @id='thhotels']");
    public static By dateList = By.xpath("//li[@class='go-text-right']//span");
    public static By adultCount = By.xpath("//div[@class='body-inner']//span[@class='amount go-right']");
    public static By title = By.xpath("//div[@class='col-md-6']//select[@id='title']");
    public static By titleOption = By.xpath("//div[@class='col-md-6']//select[@id='title']//option");
    public static By firstName = By.xpath("//div[@class='col-md-6']//input[@type='text' and @id='first_name']");
    public static By lastName = By.xpath("//div[@class='col-md-6']//input[@type='text' and @id='last_name']");
    public static By email = By.xpath("//div[@class='col-md-6']//input[@type='email' and @id='email']");
    public static By country = By.xpath("//div[@id='country_code_chosen']//a[@class='chosen-single']//div");
    public static By countryOption = By.xpath("//div[@class='chosen-drop']//ul[@class='chosen-results']");
    public static By searchCountry = By.xpath("//div[@class='chosen-search']//input[@type='text']");
    public static By phoneNumber = By.xpath("//div[@class='col-md-6']//input[@type='text' and @id='phone_number']");
    public static By completeBooking = By.xpath("//form[@method='post']//button[@type='submit' and text()='Complete Booking']");

    public static Boolean verifyDates(@NotNull WebDriver driver,@NotNull String checkin,@NotNull String checkout) {
        List<String> inputCheckin= Arrays.asList(checkin.split("/"));
        List<String> inputCheckout=Arrays.asList(checkout.split("/"));
        List<String> actualCheckin=Arrays.asList(driver.findElements(dateList).get(0).getText().split("-"));
        List<String> actualCheckout=Arrays.asList(driver.findElements(dateList).get(1).getText().split("-"));
        Collections.reverse(actualCheckin);
        Collections.reverse(actualCheckout);
        for(int i=0; i< inputCheckin.size(); i++){
            if(!(inputCheckin.get(i).equals(actualCheckin.get(i)))) {
                return false;
            }
        }
        for(int i=0; i< inputCheckout.size(); i++){
            if(!(inputCheckout.get(i).equals(actualCheckout.get(i)))) {
                return false;
            }
        }
        return true;
    }

    public static Boolean verifyAdultCount(@NotNull WebDriver driver, @NotNull int count){
        String[] text = driver.findElement(adultCount).getText().split(" ");
        if((int)Integer.parseInt(text[1])!=count){
            return false;
        }
        return true;
    }

    public static Boolean bookingRoom(@NotNull WebDriver driver, String titles,@NotNull String firstN, @NotNull String lastN ,@NotNull String emailAddress, @NotNull String countryName,@NotNull long number) {
        Actions actions = new Actions(driver);
        WebDriverWait wait = new WebDriverWait(driver, 30);
        actions.moveToElement(driver.findElement(title)).click().build().perform();
        List<WebElement> titleSelect = driver.findElements(titleOption);
        for(WebElement e: titleSelect) {
        if(e.getText().equalsIgnoreCase(titles)) {
            e.click();
        }
        }
        driver.findElement(firstName).sendKeys(firstN);
        driver.findElement(lastName).sendKeys(lastN);
        wait.until(ExpectedConditions.elementToBeClickable(email));
        driver.findElement(email).sendKeys(emailAddress);
        wait.until(ExpectedConditions.visibilityOfElementLocated(country));
        actions.doubleClick(driver.findElement(country)).sendKeys(countryName).build().perform();
        List<WebElement> countries = driver.findElements(By.xpath("//div[@class='chosen-drop']//ul[@class='chosen-results']//li//em"));
        SeleniumUtils.listSelection(driver,"",countries);
        driver.findElement(phoneNumber).sendKeys(String.valueOf(number));
        driver.findElement(completeBooking).submit();
        wait.until(ExpectedConditions.urlContains("https://buy.paddle.com/checkout/"));
        return true;
    }



}
