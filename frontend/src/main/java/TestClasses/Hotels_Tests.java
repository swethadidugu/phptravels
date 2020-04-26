package TestClasses;

import pageobjects.Hotel_BookingPage;
import pageobjects.Hotel_HomePage;
import pageobjects.Hotel_Details;
import pageobjects.Hotel_SearchResults;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.SeleniumUtils;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by sweth on 4/20/2020.
 */
public class Hotels_Tests extends BaseClass {
    String city="new delhi";
    String checkinDate ="12/12/2020";
    String checkoutDate ="31/12/2020";
    int star = 3;

    @Test
    public void hotelSearchPageDisplayed()  {
        if(Hotel_HomePage.hotelSearch(driver,city,"",checkinDate,checkoutDate,4,2)) {
            Assert.assertTrue(Hotel_SearchResults.searchCount(driver)>0, "No results are displayed for city..."+city+"  for dates  "+checkinDate+"  and  "+checkoutDate);
        }
        else{
            Assert.fail("Hotel Search Page have not displayed for city" + city + "during "+ checkinDate+ "and "+ checkoutDate );
        }
    }

    @Test
    public void verifyHotelRatingDisplay() {
        if(Hotel_HomePage.hotelSearch(driver,city,"",checkinDate,checkoutDate,2,1)){
            List<WebElement> searchCards = Hotel_SearchResults.filterByHotelRating(driver,star);

            if(!(Hotel_SearchResults.verifyFilterRatings(star,searchCards))){
                Assert.fail("Hotel search page have not displayed properly for the given input star"+ star);
            }
        }
        else {
            Assert.fail("Hotel Search Page have not displayed for city" + city + "during "+ checkinDate+ "and "+ checkoutDate );
        }

    }

    @Test
    public void modifyDestination() {
        String modifyCity = "New-york";
        if(Hotel_HomePage.hotelSearch(driver,city,"",checkinDate,checkoutDate,2,1)) {
            WebElement location = Hotel_SearchResults.modifyDestination(driver, "New York");
            Assert.assertTrue(location.getText().equalsIgnoreCase(modifyCity),"Hotel search have not displayed properly for the modified input city "+ modifyCity + "actual value is " + location.getText());
        }
        else{
            Assert.fail("Hotel Search Page have not displayed for city" + city + "during "+ checkinDate+ "and "+ checkoutDate );
        }
    }

    @Test
    public void adultCountVerification() {
        int count = 5;
        Assert.assertTrue(Hotel_HomePage.hotelSearch(driver, city, "", checkinDate, checkoutDate, count, 2), "Hotel Search Page have not displayed for city" + city + "during " + checkinDate + "and " + checkoutDate);
        Assert.assertTrue(Hotel_SearchResults.goToHotelDetails(driver, 3), "Details page have not loaded yet!!");
        Assert.assertTrue(Hotel_Details.verifyCountAdults(driver,count),"adult count doesnt matched input" + count + "but result "+(int)Integer.parseInt(driver.findElement(Hotel_Details.adultCount).getAttribute("value")));
    }

    @Test
    public void searchOrder() {
        String order ="asc";
        if (Hotel_HomePage.hotelSearch(driver, "new delhi", "", checkinDate, checkoutDate, 2, 1)) {
           // driver.manage().timeouts().implicitlyWait(3000, TimeUnit.MILLISECONDS);
            List<WebElement> orderSearch = Hotel_SearchResults.priceOrderSearch(driver,order);
            Assert.assertTrue(Hotel_SearchResults.verifyPriceOrder(driver,orderSearch,order), "Hotel search have not displayed properly for the given price order "+ order );
        }
        else{
            Assert.fail("Hotel Search Page have not displayed for city" + city + "during "+ checkinDate+ "and "+ checkoutDate );
        }
    }

    @Test
    public void priceFilter(){
        if (Hotel_HomePage.hotelSearch(driver, city , "", checkinDate, checkoutDate, 2, 0)) {
            driver.manage().timeouts().implicitlyWait(3000, TimeUnit.MILLISECONDS);
            List<WebElement> search = Hotel_SearchResults.draggingPriceLine(driver,55);
            Assert.assertTrue(Hotel_SearchResults.verifyPriceFilter(driver, search), "Price value are not expected as per the input");
        }
        else{
            Assert.fail("Hotel Search Page have not displayed for city" + city + "during "+ checkinDate+ "and "+ checkoutDate );
        }
    }

    @Test
    public void AvailableRooms(){
        if (Hotel_HomePage.hotelSearch(driver, city , "", checkinDate, checkoutDate, 4, 2)) {
            if(Hotel_SearchResults.goToHotelDetails(driver, 3)){
                Assert.assertTrue(Hotel_Details.availableRoomsCount(driver)>0, "No rooms available "+ Hotel_Details.availableRoomsCount(driver) );
            }
            else {
                Assert.fail("Details page have not loaded yet!!");
            }
        }
        else{
            Assert.fail("Hotel Search Page have not displayed for city" + city + "during "+ checkinDate+ "and "+ checkoutDate );
        }
    }

    @Test
    public void changeSearchResults() {
        WebDriverWait wait = new WebDriverWait(driver, 30);
        String modifyCity = "New Delhi";
        if(Hotel_HomePage.hotelSearch(driver,city,"",checkinDate,checkoutDate,2,1)) {
            if(Hotel_SearchResults.goToHotelDetails(driver, 3)){
                if(Hotel_Details.modifyInputs(driver,modifyCity,"20/12/2020","31/12/2020",4,0)) {
                  Assert.assertTrue(driver.findElement(Hotel_SearchResults.modifyCity).getText().equalsIgnoreCase("New-delhi"), "Search page not loaded with given input city "+modifyCity+"but found" +driver.findElement(Hotel_SearchResults.modifyCity).getText());
                }
                else {
                   Assert.fail("After changing inputs search results have not displayed for modified city "+modifyCity );
                }
            }
            else {
                Assert.fail("Details page have not loaded yet!!");
            }
        }
        else{
            Assert.fail("Hotel Search Page have not displayed for city" + city + "during "+ checkinDate+ "and "+ checkoutDate );
        }
    }

    @Test
    public void bookTheRoom() {
        int index = 2;
        Assert.assertTrue(Hotel_HomePage.hotelSearch(driver, city, "", checkinDate, checkoutDate, 4, 2), "Hotel Search Page have not displayed for city" + city + "during " + checkinDate + "and " + checkoutDate);
        SeleniumUtils.waitForTime(3000);
        Assert.assertTrue(Hotel_SearchResults.goToHotelDetails(driver, 3), "Details page have not loaded yet!!");
        Assert.assertTrue(Hotel_Details.bookTheRoom(driver, 2), " Button Book have not clicked.. Booking page have not loaded yet");

    }

    @Test
    public void verifyInputDates() {
        int index=1;
        Assert.assertTrue(Hotel_HomePage.hotelSearch(driver, city, "", checkinDate, checkoutDate, 4, 2), "Hotel Search Page have not displayed for city" + city + "during " + checkinDate + "and " + checkoutDate);
        Assert.assertTrue(Hotel_SearchResults.goToHotelDetails(driver, 3), "Details page have not loaded yet!!");
        Assert.assertTrue(Hotel_Details.bookTheRoom(driver, 2), " Button Book have not clicked.. Booking page have not loaded yet");
        Assert.assertTrue(Hotel_BookingPage.verifyDates(driver,checkinDate,checkoutDate), "input dates and expected dates are not matching");
    }

    @Test
    public void verifyInputAdultCount() {
        int index=1;
        int count=4;
        Assert.assertTrue(Hotel_HomePage.hotelSearch(driver, city, "", checkinDate, checkoutDate, count, 2), "Hotel Search Page have not displayed for city" + city + "during " + checkinDate + "and " + checkoutDate);
        Assert.assertTrue(Hotel_SearchResults.goToHotelDetails(driver, 3), "Details page have not loaded yet!!");
        Assert.assertTrue(Hotel_Details.bookTheRoom(driver, 2), " Button Book have not clicked.. Booking page have not loaded yet");
        Assert.assertTrue(Hotel_BookingPage.verifyAdultCount(driver,count), "input adult count and expected adult count are not matching");
    }

    @Test
    public void completeBooking() {
        int index = 1;
        int count = 4;
        Assert.assertTrue(Hotel_HomePage.hotelSearch(driver, city, "", checkinDate, checkoutDate, count, 2), "Hotel Search Page have not displayed for city" + city + "during " + checkinDate + "and " + checkoutDate);
        Assert.assertTrue(Hotel_SearchResults.goToHotelDetails(driver, 3), "Details page have not loaded yet!!");
        Assert.assertTrue(Hotel_Details.bookTheRoom(driver, 2), " Button Book have not clicked.. Booking page have not loaded yet");
        Assert.assertTrue(Hotel_BookingPage.bookingRoom(driver, "Mrs","indu", "kindu","ffgg@gmail.com", "", 7777777777L), "Booking Failed");
    }
}
