package pages;


import base.BasePage;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import utils.logs.Log;

import java.util.Set;

/**
 * @author Jonathan Mbuyi Tshitenda [ ABJT439 ]
 * @date created 9/10/2019
 * @package pages
 */
public class FlexPage extends BasePage {
    public FlexPage(WebDriver driver) {

        super(driver);
        Log.info("The current working class: "+this.getClass().getName());
    }

    //***********************************WEB ELEMENTS*******************************************************************
    //**************************************************************
    // ****************************************************
    String usenameTxtBoxId = "fldUserID";
    String passwordTxtBoxId = "password";


    @FindBy(how = How.XPATH, using = "/html/body/table/tbody/tr/td/div/table/tbody/tr[3]/td/table/tbody/tr/td[2]")
    public WebElement buttonRetail;

    @FindBy(how = How.ID, using = "bullet1")
    public WebElement buttonRetail3;

    @FindBy(how = How.ID, using = "fldUserID")
    public static WebElement usenameTxtBoxId2;


    @FindBy(how = How.ID, using = "fldUserID")
    public static WebElement usenameTxtBoxName;
    //******************************************************************************************************************
    /***4 Lines***/

    //***********************************STEPS**************************************************************************
    //******************************************************************************************************************
    public FlexPage clickOnRetailButton() throws InterruptedException {

        Thread.sleep(3000);

        // Get current window handle
        String parentWindowHandle = driver.getWindowHandle();//Return a string of alphanumeric window handle


        //Click on the button
        buttonRetail3.click();

        //============ Switch to the new window========================================================================

        // Get the window handles of all open windows
        Thread.sleep(3000);
        Set<String> winHandles = driver.getWindowHandles();////Return a set of window handle
        System.out.println(winHandles);


        for(String handle: winHandles){
            if(!handle.equals(parentWindowHandle)){

                driver.switchTo().window(handle);
                Thread.sleep(12000);

                // Switching to Alert
                Alert alert = driver.switchTo().alert();

                // Accepting alert
                alert.accept();

                driver.manage().window().maximize();
            }
        }
        return this;}

    public FlexPage LoginIntoFlex() throws InterruptedException
    {
        Thread.sleep(2000);
        driver.switchTo().defaultContent();


        //Enter User Name
        usenameTxtBoxName.sendKeys("TELLER");
        //driver.findElement(By.id(String.valueOf(usenameTxtBoxName))).sendKeys("(222)222-2222");


        WebElement wb = driver.findElement(By.name(String.valueOf(usenameTxtBoxName)));
        JavascriptExecutor jse = (JavascriptExecutor)driver;
        jse.executeScript("arguments[0].value='(222)222-2222';", wb);
        jse.executeScript("document.getElementById('fldUserID').value='555-55-5555';");
        return this;}
}
