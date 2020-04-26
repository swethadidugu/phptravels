package pageobjects;

import com.sun.istack.internal.NotNull;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.SeleniumUtils;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by sweth on 4/18/2020.
 */
public class Hotel_HomePage {

    //Hotel Tab Elements Declaration
    public static By hotelTab = By.xpath("//a[@data-name='hotels']");
    public static By hotelSearchBtn = By.xpath("//form[@name='HOTELS']//div[@class='form-inner']//button[@type='submit']");
    public static By destination = By.xpath("//div[@class='form-group']//span[@class='select2-chosen']");
    public static By checkIn = By.xpath("//input[@type='text' and @id='checkin']");
    public static By checkOut = By.xpath("//input[@type='text' and @id='checkout']");
    public static By adultCountUp = By.xpath("//div[@class='col o2']//button[@type='button' and text()='+']");
    public static By adultCountDown = By.xpath("//div[@class='col o2']//button[@type='button' and text()='-']");
    public static By childCountUp = By.xpath("//div[@class='col 01']//button[@type='button' and text()='+']");
    public static By childCountDown = By.xpath("//div[@class='col 01']//button[@type='button' and text()='-']");
    public static By NoOfAdults = By.xpath("//div[@class='col o2']//input[@type='text' and @name='adults']");
    public static By NoOfChildren = By.xpath("//div[@class='col 01']//input[@type='text' and @name='children']");

    public static boolean hotelSearch(@NotNull WebDriver driver, @NotNull String city, String country, @NotNull String checkin, @NotNull String checkout, @NotNull int adultCount, int childCount) {

        WebDriverWait wait = new WebDriverWait(driver, 10);
        driver.manage().timeouts().implicitlyWait(3000, TimeUnit.MILLISECONDS);
        wait.until(ExpectedConditions.visibilityOfElementLocated(hotelTab));
        driver.findElement(hotelTab).click();
        Hotel_HomePage.login(driver,city,"",checkin,checkout,adultCount,childCount);
        Hotel_SearchResults nextPage = new Hotel_SearchResults();
        wait.until(ExpectedConditions.visibilityOfElementLocated(Hotel_SearchResults.hotelsHeader));
        return driver.findElement(Hotel_SearchResults.hotelsHeader).isDisplayed();
        }
    public static void login(@NotNull WebDriver driver, @NotNull String city, String country,@NotNull String checkin,@NotNull String checkout, int adultCount, int childCount) {
        WebDriverWait wait = new WebDriverWait(driver, 30);
        Actions action = new Actions(driver);
        wait.until(ExpectedConditions.visibilityOfElementLocated(destination));
        action.doubleClick(driver.findElement(destination)).sendKeys(city).build().perform();
        List<WebElement> locations = driver.findElements(By.xpath("//ul[@class='select2-result-sub']//span[@class='select2-match' and contains(text(),'" + city + "')]"));
        SeleniumUtils.listSelection(driver, country, locations);
        action.sendKeys(Keys.RETURN).build().perform();
        driver.findElement(checkIn).clear();
        driver.findElement(checkIn).sendKeys(checkin);
        driver.findElement(checkOut).clear();
        driver.findElement(checkOut).sendKeys(checkout);
        action.sendKeys(Keys.ESCAPE).build().perform();
        wait.until(ExpectedConditions.visibilityOfElementLocated(adultCountUp));
        SeleniumUtils.totalCount(driver, driver.findElement(adultCountUp),driver.findElement(adultCountDown),adultCount,driver.findElement(NoOfAdults));
        SeleniumUtils.totalCount(driver, driver.findElement(childCountUp),driver.findElement(childCountDown), childCount,driver.findElement(NoOfChildren));
        driver.findElement(hotelSearchBtn).submit();
    }


}