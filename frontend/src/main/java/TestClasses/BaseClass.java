package TestClasses;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.*;

import java.util.concurrent.TimeUnit;

/**
 * Created by sweth on 4/18/2020.
 */
public class BaseClass {
    WebDriver driver;
    String city="hyderabad";
    String checkinDate ="12/12/2020";
    String checkoutDate ="31/12/2020";
    int star = 3;
    String hotelsMethods = "hotelSearchPageDisplayed,verifyHotelRatingDisplay";

    @BeforeMethod(alwaysRun = true)
    public void driverInitilization(){
        /*if(method.getDeclaringClass().getSimpleName()=="Hotels_Tests" && hotelsMethods.contains(method.getName())){
            if(!Hotel_HomePage.hotelSearch(driver, city , "", checkinDate, checkoutDate, 4, 2))
                throw new SkipException("Login Failed Test execution will be failed");
        }*/
        System.setProperty("webdriver.gecko.driver", "D:\\Java\\geckodriver-v0.26.0-win64\\geckodriver.exe");
        driver = new FirefoxDriver();
        driver.get("https://www.phptravels.net/home");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(3000, TimeUnit.MILLISECONDS);

    }


    @AfterMethod(alwaysRun = true)
    public void driverQuit() {
      //  driver.close();
      //  driver.quit();
        driver=null;
    }
}
