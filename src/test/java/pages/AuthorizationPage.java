package pages;

import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import utils.extentreports.ExtentTestManager;

/**
 * @author DLV Automation Team
 * @date created 9/10/2019
 * @package pages
 */
public class AuthorizationPage extends BasePage {
    public AuthorizationPage(WebDriver driver) {
        super(driver);
    }

    //***********************************WEB ELEMENTS*******************************************************************
    //******************************************************************************************************************
    //String originatorAccountDetailsNameXpath = "/html/body/app-root/div/div/div/payment-authorise/div/div/div[2]/div[1]/div[1]/div/div[1]/h3";
    String originatorAccountDetailsNameXpath = "(.//div[@class=\"col-4\"])[2]";String paymentIDFieldId = "paymentId";
    String authorisePaymentCheckBoxId = "0-checkbox";
    String authoriseApproveButtonId = "approveBtn";
    String authoriseTotalTransfersId = "totalTransfers";
    //==================================================================================================================
    String purposeOfPaymentHeaderXpath = "//span[contains(text(),\"Purpose of payment\")]";
    String pageNumberDropdown = "//select[@class='page-number-dropdown']";
    String purposeOfPaymentXpath = "//*[@id=\"purposeOfPayment\"]";

    String postingOptionHeaderXpath="//span[contains(text(),\"Posting option\")]";
    String postingOptionXpath="//*[@id=\"itemisedProcessing\"]";

    String chargesFlagHeaderXpath="//span[contains(text(),\"Charges Waived\")]";
    //String chargesFlagXpath ="(//div[@class=\"col-3\"][3])[3]";
    String chargesFlagXpath="/html/body/app-root/div/div/div/payment-authorise/div/div/div[2]/div[2]/div[4]/div[3]/div";

    String forcePostHerderXpath="//span[contains(text(),\"Force Post\")]";
    //String forcepostFlagXpath="(//div[@class=\"col-3\"][4])[2]";
    String forcepostFlagXpath="/html/body/app-root/div/div/div/payment-authorise/div/div/div[2]/div[2]/div[4]/div[4]/div";



    /*****************Dynamic Xpath Elements**********************************/
    String strBeforeAuthorisePaymentCheckBoxId = "";
    String strAfterAuthorisePaymentCheckBoxId = "-checkbox";
    /*************************************************************************/

    @FindBy(how = How.XPATH, using = "/html/body/app-root/div/div/div/payment-capture/div/div/form[1]/div/div[2]/div[2]/div[2]/div/div[2]/sol-checkbox/label")
    public static WebElement chargesWaivedCheckBox;
    //******************************************************************************************************************
    /***4 Lines***/



    //***********************************STEPS**************************************************************************
    //******************************************************************************************************************
    /**Get the Payment ID*/
    public String getPaymentID() throws Exception
    {
        //start
        String expectedOgirinatorHeader="Originator account details";
        String actualOriginatorHeader=readText(By.xpath(originatorAccountDetailsNameXpath));
        if(actualOriginatorHeader.contains(expectedOgirinatorHeader))
        {
            // Using assert to verify the expected and actual value
            ExtentTestManager.getTest().log(Status.PASS, "Originator Account Details Header is Correct on Review");
            screenshotLoggerMethod("Originator Account Details on Authorization Page", "Originator Account Details on Authorization Page");

        }else {
            highLighterMethod(By.xpath(originatorAccountDetailsNameXpath));
            screenshotLoggerMethod("Originator Account Details on Authorization Page", "Originator Account Details on Authorization Page");
            ExtentTestManager.getTest().log(Status.FAIL, "Originator Account Details Header is Incorrect on Authorization Page");
        }
        //end

        String strPaymentIDTrimmed = null;
        try {
            waitForPageToBeReady_2();

            //Get string displayed on the web textfield
            Thread.sleep(2000);
            String strPaymentID = readText(By.id(paymentIDFieldId));
            if (strPaymentID.isEmpty())
            {
                ExtentTestManager.getTest().warning("STATUS=>FAIL:: The transaction is not displayed on the authorization list");
            }

            //Count the chars in the string
            int charCounter = strPaymentID.length();

            //Trim preceding characters by starting(Position 12 of our string) from an index position to the max-length of the string
            strPaymentIDTrimmed = strPaymentID.substring(12, charCounter);

            //System.out.println("INFORMATION:: The Payment ID is "+strPaymentIDTrimmed);

        } catch (Exception e)
        {
            ExtentTestManager.getTest().log(Status.FAIL, "INFORMATION:: "+e);
        }
        return strPaymentIDTrimmed;
    }

    public AuthorizationPage verifyCapturedData(String DebtorPurposeOfPayment)
    {
        try {
            highLighterMethod(By.xpath(purposeOfPaymentHeaderXpath));
            String strPurposeOfPayment = readText(By.xpath(purposeOfPaymentXpath));

            if(strPurposeOfPayment.equals(DebtorPurposeOfPayment))
            {
                highLighterMethod(By.xpath(purposeOfPaymentXpath));
                ExtentTestManager.getTest().log(Status.PASS,"The purpose of payment is per expectation on Authorization page");
                ExtentTestManager.getTest().pass(MarkupHelper.createLabel("Authorization Page Captured Details", ExtentColor.GREEN));
                screenshotLoggerMethod("Purpose Of Payment at Authorization Page","Authorization Page Captured Details");
            }else
            {
                highLighterMethod(By.xpath(purposeOfPaymentXpath));
                ExtentTestManager.getTest().log(Status.FAIL,"Incorrect purpose of payment on Authorization page");
                ExtentTestManager.getTest().fail(MarkupHelper.createLabel("Authorization Page Captured Details", ExtentColor.RED));
                screenshotLoggerMethod("Incorrect PurposeOfPayment at Authorization Page","Authorization Page Captured Details");

                ExtentTestManager.getTest().info(MarkupHelper.createLabel( "Actual Purpose of Payment:: "+strPurposeOfPayment, ExtentColor.TEAL));
                ExtentTestManager.getTest().info(MarkupHelper.createLabel( "Expected Purpose of Payment:: " +DebtorPurposeOfPayment,ExtentColor.TEAL));

                stopCurrentTestSetResultsToFailedAndContinueWithNextTest();
            }

        }catch (Exception e)
        {
            ExtentTestManager.getTest().log(Status.FAIL, "INFORMATION:: "+e);
        }
        return this; }
    /**
     * The common function to verify the captured data on Authorization Screen
     * */

    public AuthorizationPage CommonVerifyFunction(String ExpectedData, By element, By elementHeader)
    {
        try
        {
            String strScreenValuePPO = readText((element));
            String strScreenValueHeaderPPO=readText((elementHeader));

            if(strScreenValuePPO.equals(ExpectedData))
            {
                ExtentTestManager.getTest().log(Status.PASS,"The"+" "+strScreenValueHeaderPPO+" "+"is correct on Authorization page");
                ExtentTestManager.getTest().pass(MarkupHelper.createLabel("At Authorization Page "+strScreenValueHeaderPPO+" is CORRECT:: "+strScreenValuePPO, ExtentColor.GREEN));

                screenshotLoggerMethod("CORRECT "+strScreenValueHeaderPPO+" "+"at Authorization Page","Authorization Page"+" "+strScreenValueHeaderPPO);
                ExtentTestManager.getTest().log(Status.INFO, "Info: "+ strScreenValueHeaderPPO+ " is " + "  ::" + strScreenValuePPO);
                System.out.println(strScreenValueHeaderPPO +" is::"+ strScreenValuePPO);
            }else {
                highLighterMethod((elementHeader));
                highLighterMethod((element));
                ExtentTestManager.getTest().log(Status.FAIL,"Incorrect "+" "+strScreenValueHeaderPPO+" "+ "on Authorization page");
                ExtentTestManager.getTest().fail(MarkupHelper.createLabel("Authorization Page "+strScreenValueHeaderPPO+" is INCORRECT::", ExtentColor.RED));
                screenshotLoggerMethod("Incorrect" +" "+strScreenValueHeaderPPO+" "+"at Authorization Page","Authorization Page"+" "+strScreenValueHeaderPPO);

                ExtentTestManager.getTest().info(MarkupHelper.createLabel( "Actual"+" "+strScreenValueHeaderPPO+" is ::"+strScreenValuePPO+" But Expected "+ExpectedData, ExtentColor.AMBER));
                ExtentTestManager.getTest().log(Status.INFO, "Info: "+ strScreenValueHeaderPPO+ " is " + "  ::" + strScreenValuePPO);
                System.out.println(strScreenValueHeaderPPO +" is::"+ strScreenValuePPO);
                //stopCurrentTestSetResultsToFailedAndContinueWithNextTest();
            }

        }catch(Exception e)
        {
            ExtentTestManager.getTest().log(Status.FAIL, "INFORMATION:: "+e);
        }
        return this; }

    /** Purpose of payment verification */
    public AuthorizationPage PurposeOfPaymentVerification(String DebtorPurposeOfPayment)
    {
        CommonVerifyFunction(DebtorPurposeOfPayment,By.xpath(purposeOfPaymentXpath),By.xpath(purposeOfPaymentHeaderXpath));
        return this;}
    /** Posting Option Verification */
    public AuthorizationPage PostingOptionVerification(String DebtorPostingOption)
    {
        CommonVerifyFunction(DebtorPostingOption,By.xpath(postingOptionXpath),By.xpath(postingOptionHeaderXpath));
        return this;}

    /** Charges Waiving Flag Verification */
    public AuthorizationPage ChargesFlagVerification(String DebtorWaiveChargesOption)
    {
        CommonVerifyFunction(DebtorWaiveChargesOption,By.xpath(chargesFlagXpath),By.xpath(chargesFlagHeaderXpath));
        return this;}

    /** ForcePost Flag Verification */
    public AuthorizationPage ForcePostFlagVerification(String DebtorForcePostingOption)
    {
        CommonVerifyFunction(DebtorForcePostingOption,By.xpath(forcepostFlagXpath),By.xpath(forcePostHerderXpath));
        return this; }


    /** Actual Verification Function to switch as per object to verify on a page*/
    public AuthorizationPage verifyTheCapturedDataBeforeAuthorize(String DebtorPurposeOfPayment, String DebtorPostingOption,String DebtorWaiveChargesOption,String DebtorForcePostingOption)
    {
        PurposeOfPaymentVerification(DebtorPurposeOfPayment);
        PostingOptionVerification(DebtorPostingOption);
        ChargesFlagVerification(DebtorWaiveChargesOption);
        ForcePostFlagVerification(DebtorForcePostingOption);
        return this;
    }

    /**Authorise and Approve the transaction(s)*/
    public AuthorizationPage authoriseAndApprove() throws InterruptedException
    {
        //Get string displayed on the web textfield
        Thread.sleep(3000);
        String strAuthoriseTableRowsCount = readText(By.id(authoriseTotalTransfersId));

        //Count the chars in the string
        int charCounter2 = strAuthoriseTableRowsCount.length();

        //Trim preceding characters by starting(Position 12 of our string) from an index position to the max-length of the string
        String strstrAuthoriseTableRowsCountTrimmed = strAuthoriseTableRowsCount.substring(17,charCounter2);
        System.out.println("INFORMATION:: The Number of transaction to be approved  is "+strstrAuthoriseTableRowsCountTrimmed);
        //Convert the string Number to an integer
        int numAuthoriseTableRowsCount = Integer.valueOf(strstrAuthoriseTableRowsCountTrimmed);
        selectItemDropdown (By.xpath(pageNumberDropdown), "100");
        Thread.sleep(4000);
        //Select the transactions to be approved
        int k=0;
        for (k=0;numAuthoriseTableRowsCount>k;k++)
        {

            //click(By.id(authorisePaymentCheckBoxId));
            scrollDown();
            click(By.id(strBeforeAuthorisePaymentCheckBoxId+k+strAfterAuthorisePaymentCheckBoxId));
            SetScriptTimeout();
        }

        scrollDown();
        //awanti
        Thread.sleep(2000);
        click(By.id(authoriseApproveButtonId));
        SetScriptTimeout();
        return this;}
}
