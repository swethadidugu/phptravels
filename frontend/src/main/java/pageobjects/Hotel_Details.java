package pageobjects;

import com.sun.istack.internal.NotNull;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


import java.util.List;

/**
 * Created by sweth on 4/22/2020.
 */
public class Hotel_Details {
    public static By pageHeader = By.xpath("//div[@class='container']//h3[@class='heading-title']//span[text()='Available Rooms']");
    public static By hotelSearchCards = By.xpath("//div[@class='room-item']");
    public static By bookRoom = By.xpath("//div[@class='room-item']//form[@method='post']//button[@type='submit']");
    public static By adultCount = By.xpath("//div[@class='col-12']//input[@type='text' and @name='adults']");

    public static int availableRoomsCount(@NotNull WebDriver driver){
       List<WebElement> roomCount = driver.findElements(hotelSearchCards);
       if(roomCount.size() > 0) {
           return roomCount.size();
       }
       else
       return 0;
   }

   public static Boolean verifyCountAdults(WebDriver driver, int count) {
       if((int)Integer.parseInt(driver.findElement(adultCount).getAttribute("value"))!=count) {
           return  false;
       }
       return true;
   }

   public static Boolean modifyInputs(@NotNull WebDriver driver,@NotNull String destination,String checkin, String checkout, int adultCount, int childCount ) {
       WebDriverWait wait = new WebDriverWait(driver, 10);
       Hotel_HomePage.login(driver,destination, "",checkin,checkout,adultCount,childCount);
       Hotel_SearchResults results = new Hotel_SearchResults();
       wait.until(ExpectedConditions.visibilityOfElementLocated(Hotel_SearchResults.hotelsHeader));
       return driver.findElement(Hotel_SearchResults.hotelsHeader).isDisplayed();

   }

   public static Boolean bookTheRoom(WebDriver driver, int index) {
       if(driver.findElements(bookRoom).size()>index) {
           driver.findElements(bookRoom).get(index).click();
           return driver.findElement(Hotel_BookingPage.header).isDisplayed();
       }
       return false;
   }
}
