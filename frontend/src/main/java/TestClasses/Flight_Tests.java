package TestClasses;

import pageobjects.Flights_HomePage;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Created by sweth on 4/26/2020.
 */
public class Flight_Tests extends BaseClass {

    @Test
    public void displaySearchResults() {
        Assert.assertTrue(Flights_HomePage.flightSearch(driver,"round","first","delhi","boston","2020-12-21","2020-12-31",5,2,1),"Homepage haven't displayed yet!!");
    }
}
