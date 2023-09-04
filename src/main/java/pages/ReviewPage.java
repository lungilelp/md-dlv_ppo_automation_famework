package pages;

import com.aventstack.extentreports.Status;
import base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utils.extentreports.ExtentTestManager;
import utils.logs.Log;

/**
 * @author Jonathan Mbuyi Tshitenda [ ABJT439 ]
 * @date created 9/10/2019
 * @package pages
 */
public class ReviewPage extends BasePage {
    public ReviewPage(WebDriver driver) {

        super(driver);
        Log.info("The current working class: "+this.getClass().getName());
    }

    //***********************************WEB ELEMENTS*******************************************************************
    //******************************************************************************************************************
    //String originatorAccountDetailsNameXpath = "/html/body/app-root/div/div/div/payment-capture/div/div/form[1]/div/div[1]/div/div/div[1]/span";
    //String originatorAccountDetailsNameXpath = "(.//span[@class=\"card-title\"])[1]";
    String originatorAccountDetailsNameXpath = "/html/body/app-root/div/div/div/payment-review/div/div/form[1]/div/div[1]/div/div/div[1]";

    String paymentReviewCheckBoxCss = ".datatable-header-cell input";
    String paymentReviewSubmitForAuthButtonXpath = "/html/body/app-root/div/div/div/payment-review/sol-footer/div/div/span[3]/footer-primary/button";

    //******************************************************************************************************************


    //***********************************STEPS**************************************************************************
    //******************************************************************************************************************
    /**Review the transaction and send it for authorization*/
    public ReviewPage paymentReviewAndAuthorization() throws InterruptedException
    {
        try
        {
            //start
            String expectedOgirinatorHeader="Originator account details";
            String actualOriginatorHeader=readText(By.xpath(originatorAccountDetailsNameXpath));
            if(actualOriginatorHeader.contains(expectedOgirinatorHeader))
            {
                // Using assert to verify the expected and actual value
                scrollElementIntoView(By.xpath(originatorAccountDetailsNameXpath));
                screenshotLoggerMethod("Originator Account Details Header on Review", "Originator Account Details Header on Review");
                ExtentTestManager.getTest().log(Status.PASS, "Originator Account Details Header is Correct on Capture");
            }else {
                highLighterMethod(By.xpath(originatorAccountDetailsNameXpath));
                screenshotLoggerMethod("Originator Account Details Header on Review", "Originator Account Details Header on Review");
                ExtentTestManager.getTest().log(Status.FAIL, "Originator Account Details Header is Incorrect on Review");
            }
            //end

            //Click the Review Checkbox
            scrollDown();
            scrollDown();
            click(By.cssSelector(paymentReviewCheckBoxCss));

            screenshotLoggerMethod("Originator Account Details on Review", "Originator Account Details on Review");

            //Submit for Authorization
            click(By.xpath(paymentReviewSubmitForAuthButtonXpath));
            //SetScriptTimeout();
            Thread.sleep(12000);

        }catch (Exception e)
        {
            ExtentTestManager.getTest().log(Status.FAIL, "Info:: Unable to review and authorise payment due to "+e);
        }

        return this;}
    //TODO:
    //**************************Assertions******************************************************************************
}
