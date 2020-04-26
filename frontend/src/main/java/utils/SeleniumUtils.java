package utils;

import com.sun.istack.internal.NotNull;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

/**
 * Created by sweth on 4/14/2020.
 */
public class SeleniumUtils {


    public static boolean isElementDisplayed(WebElement webElement){
        for(int i=0; i<10; i++){
            try {
                if(webElement.isDisplayed())
                    return true;
            } catch (Exception e) {
                e.printStackTrace();
                waitForTime(2000);
            }
        }
        return false;
    }

    public static boolean isElementDisplayed(WebDriver driver, By by){
        for(int i=0; i<10; i++){
            try {
                WebElement webElement = driver.findElement(by);
                if(webElement.isDisplayed())
                    return true;
            } catch (Exception e) {
                e.printStackTrace();
                waitForTime(2000);
            }
        }
        return false;
    }

    public static void waitForTime(long sleepTime){
        try {
            Thread.sleep(sleepTime);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void listSelection(@NotNull WebDriver driver, String country, @NotNull List<WebElement> options) {
        if (options.size() == 1) {
            options.get(0).click();
        } else if (options.size() > 1) {
            if (country != null) {
                for (WebElement e : options) {
                    WebElement parent = e.findElement(By.xpath("./.."));
                    if (parent.getText().toLowerCase().contains(country)) {
                        e.click();
                        break;
                    }
                }
            } else {
                options.get(0).click();
            }
        } else {
            System.out.println("Given Destination value is not present");
        }

    }

    public static void totalCount(@NotNull WebDriver driver, @NotNull WebElement countUp, @NotNull WebElement countDown, @NotNull int count, WebElement actualValue) {
        Actions action = new Actions(driver);
        int actualCount = (int)Integer.parseInt(actualValue.getAttribute("value"));
        if (count < actualCount) {
           for (int i = count; i < actualCount; i++) {
                action.moveToElement(countDown).click().build().perform();
           }
        }
        else if (count > actualCount) {
           for (int i = count; i > actualCount; i--) {
                action.moveToElement(countUp).click().build().perform();
            }
       }
    }

   }
