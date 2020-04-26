package pageobjects;

import org.openqa.selenium.By;

/**
 * Created by sweth on 4/20/2020.
 */
public class Cars_HomePage {
    //Cars Tab Elements Declaration
    public static By carsTab = By.xpath("//a[@data-name='cars']");
    public static By carSearchBtn = By.xpath("//div[@id='cars']//button[@type='submit' and text()='\n" +
            "          Search          ']");
    public static By carPickUp = By.xpath("//div[@id='cars']//span[text()='Pick up']");
    public static By carDropOff = By.xpath("//div[@id='cars']//span[text()='Drop off']");
    public static By carDepartDate = By.xpath("//div[@id='cars']//input[@name='pickupdate']");
    public static By carReturnDate = By.xpath("//div[@id='cars']//input[@name='dropoffdate']");
    public static By carDepartTime = By.xpath("//div[@id='cars']//select[@name='timeDepart']");
    public static By carReturnTime = By.xpath("//div[@id='cars']//select[@name='timeReturn']");

}
