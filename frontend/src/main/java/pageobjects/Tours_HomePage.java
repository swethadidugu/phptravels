package pageobjects;

import org.openqa.selenium.By;

/**
 * Created by sweth on 4/20/2020.
 */
public class Tours_HomePage {
    //Tours Tab Elements Declaration
    public static By toursTab = By.xpath("//a[@data-name='tours']");
    public static By tourSearchBtn = By.xpath("//div[@id='tours']//button[@type='submit' and text()=' Search']");
    public static By tourDestn = By.xpath("//div[@id='tours']//span[text()='Search by City Name']");
    public static By tourStartDate = By.xpath("//div[@id='tours']//input[@id='DateTours' and @name='startDate']");
    public static By tourEndDate = By.xpath("//div[@id='tours']//input[@id='DateTours' and @name='startDate']");

}
