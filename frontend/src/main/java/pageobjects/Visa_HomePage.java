package pageobjects;

import org.openqa.selenium.By;

/**
 * Created by sweth on 4/20/2020.
 */
public class Visa_HomePage {
    //Visa Tab Elements Declaration
    public static By visaTab = By.xpath("//a[@data-name='visa']");
    public static By visaSubmitBtn = By.xpath("//div[@id='visa']//button[@type='submit' and text()='Submit']");
    public static By visaFrom = By.xpath("//div[@id='visa']//select[@type='search' and @name='nationality_country']");
    public static By visaTo = By.xpath("//div[@id='visa']//select[@name='destination_country']");
    public static By visaDate = By.xpath("//div[@id='visa']//input[@type='search' and @name='date']");

}
