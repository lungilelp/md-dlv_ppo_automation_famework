package pages;

import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import Utilities.api.APIUtil;
import base.BasePage;
import helpers.OutwardCreditHandlingModule;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.asserts.Assertion;
import utils.extentreports.ExtentTestManager;
import utils.logs.Log;

/**
 * @author Collin Ngeleka [ ABCN530 ]
 * @date created 9/10/2019
 * @package pages
 */
public class PaymentSearchPage extends BasePage {
    public PaymentSearchPage(WebDriver driver) {

        super(driver);
        Log.info("The current working class: "+this.getClass().getName());
    }

    //***********************************WEB ELEMENTS*******************************************************************
    //******************************************************************************************************************
    public String CreditorTransferTotalAmount = null;
    public static String strPaymentStatus = null;
    public String strCurrentTransactionID=null;

    String welcomeTab = "welcome";
    String paymentSearchTabId = "payment-search";
    String paymentSearchTxtBox = "paymentId";
    String paymentSearchSearchBtnCSS = ".search-btn";
    String paymentSearchStatusColumnXpath = "/html/body/app-root/div/div/div/payment-search/div/div[2]/ngx-datatable/div/datatable-body/datatable-selection/datatable-scroller/datatable-row-wrapper/datatable-body-row/div[2]/datatable-body-cell[6]/div";
    String paymentSearchStatusColumnBeforeXpath = "/html/body/app-root/div/div/div/payment-search/div/div[2]/ngx-datatable/div/datatable-body/datatable-selection/datatable-scroller/datatable-row-wrapper[";
    String paymentSearchStatusColumnAfterXpath = "]/datatable-body-row/div[2]/datatable-body-cell[6]/div";

    String paymentSearchDateRangeSelectorXpath = "//*[@id=\"searchDate\"]/form/div/div/div/div/button";
    String paymentSearchDateRangeTodayXpath = "//*[@id=\"searchDate\"]/form/div/div/div/div/sol-date-range/div[1]/div[2]/div[2]/div/div/button[1]";

    String clearButtonXpath = "/html/body/app-root/div/div/div/payment-search/div/div[1]/form/div/div[2]/div[4]/div/span[1]/span";
    String smallFailureQuestionMark = "/html/body/app-root/div/div/div/payment-search/div/div[2]/ngx-datatable/div/datatable-body/datatable-selection/datatable-scroller/datatable-row-wrapper/datatable-body-row/div[2]/datatable-body-cell[6]/div/div/span/small";
    String reason = "//*[@id=\"ngb-popover-0\"]/h3";
    String reasonLabel = "//*[@id=\"ngb-popover-0\"]";
    String reasonTextContainer = "//*[@id=\"ngb-popover-0\"]/h3";
    String debtorAccountNumberTxtBoxID = "originationAccountNumber";
    String creditorAccountNumberTxtBoxID = "destinationAccountNumber";
    String paymentStatusDropDownXpath = "DROPDOWNpaymentSTATUS";
    String transactionReferenceTxtBoxID = "transactionReference";
    String workflowReferenceTxtBoxID = "workflowReference";

    String dateButtonXpath = "//*[@id=\"searchDate\"]/form/div/div/div/div/button";
    String todaysDateXpath = "//*[@id=\"searchDate\"]/form/div/div/div/div/sol-date-range/div[1]/div[2]/div[2]/div/div/button[1]";
    String yesterdaysXpath = "//*[@id=\"searchDate\"]/form/div/div/div/div/sol-date-range/div[1]/div[2]/div[2]/div/div/button[2]";
    String last7DaysXpath = "//*[@id=\"searchDate\"]/form/div/div/div/div/sol-date-range/div[1]/div[2]/div[2]/div/div/button[3]";
    String last30DaysXpath = "//*[@id=\"searchDate\"]/form/div/div/div/div/sol-date-range/div[1]/div[2]/div[2]/div/div/button[4]";
    String last60DaysXpath = "//*[@id=\"searchDate\"]/form/div/div/div/div/sol-date-range/div[1]/div[2]/div[2]/div/div/button[5]";
    String last90DaysXpath = "//*[@id=\"searchDate\"]/form/div/div/div/div/sol-date-range/div[1]/div[2]/div[2]/div/div/button[6]";
    String customeRangeDateXpath = "//*[@id=\"searchDate\"]/form/div/div/div/div/sol-date-range/div[1]/div[2]/div[2]/div/div/button[7]";
    String custumeRange_1 = "";
    String custumeRange_2 = "";
    String applyButton = "";
    String backDateArrow = "";
    String statusDropDownXpath = "//*[@id=\"statuses_select\"]";
    String searchResultsTableHeader = "/html/body/app-root/div/div/div/payment-search/div/div[2]/div[1]/div[1]/h4";


    //String customeRangeDateXpath="//*[@id=\"searchDate\"]/form/div/div/div/div/sol-date-range/div[1]/div[2]/div[2]/div/div/button[7]";
    String firstDateXpath = "//*[@id=\"searchDate\"]/form/div/div/div/div/sol-date-range/div[1]/div[2]/div[1]/ngb-datepicker/div[2]/div[1]/ngb-datepicker-month-view/div[3]/div[4]/span";
    //                                                                                                                        DIFFERENCE IS HERE 1&2                   DIFFERENCE IS HERE 3&5
    String lastDateXpath = "//*[@id=\"searchDate\"]/form/div/div/div/div/sol-date-range/div[1]/div[2]/div[1]/ngb-datepicker/div[2]/div[2]/ngb-datepicker-month-view/div[5]/div[4]/span";

    String dateTextboxXpath = "//div[@class=\"sol-date-range-group\"]";
    String applyButtonXpath = "//*[@id=\"searchDate\"]/form/div/div/div/div/sol-date-range/div[1]/div[2]/div[3]/button[2]";
    String backDateArrowXpath = "//*[@id=\"searchDate\"]/form/div/div/div/div/sol-date-range/div[1]/div[2]/div[1]/ngb-datepicker/div[1]/ngb-datepicker-navigation/button[1]";
    String forwardArrowXpath = "//*[@id=\"searchDate\"]/form/div/div/div/div/sol-date-range/div[1]/div[2]/div[1]/ngb-datepicker/div[1]/ngb-datepicker-navigation/button[2]";
    //String statusDropDownXpath="//*[@id=\"statuses_select\"]"; //*[@id="statuses_select"]
    //String searchResultsTableHeader="/html/body/app-root/div/div/div/payment-search/div/div[2]/div[1]/div[1]/h4";
    String searchFieldsContainerXpath = "//div[@class=\"card-block\"]";

    String recordSuccessStatusXpath1 = "/html/body/app-root/div/div/div/payment-search/div/div[2]/ngx-datatable/div/datatable-body/datatable-selection/datatable-scroller/datatable-row-wrapper[1]/datatable-body-row/div[2]/datatable-body-cell[6]/div/div/span";
    String recordSuccessStatusXpath = "//span[@class=\"pill pill-small pill-success\"]";
    String recordAllStatusXpath = "//datatable-body-cell[@class=\"datatable-body-cell sort-active\"][6][1]";
    String recordAllTheStatusXpath = "//datatable-body-cell[@class=\"datatable-body-cell sort-active\"][6]";
    String pendingProcessingStatusIcon = "/html/body/app-root/div/div/div/payment-search/div/div[2]/ngx-datatable/div/datatable-body/datatable-selection/datatable-scroller/datatable-row-wrapper[1]/datatable-body-row/div[2]/datatable-body-cell[6]/div/div/span";
    String processingFailedIcon = "/html/body/app-root/div/div/div/payment-search/div/div[2]/ngx-datatable/div/datatable-body/datatable-selection/datatable-scroller/datatable-row-wrapper[1]/datatable-body-row/div[2]/datatable-body-cell[6]/div/div/span";

    String fromDateTextBoxXpath = "//input[@name=\"fromDate\"]";
    String toDateTextBoxXpath = "//input[@name=\"toDate\"]";
    String toDateLabelXpath = "//label[@class=\"result-label-value\"][1]";
    String fromDateLabelXpath = "//label[@class=\"result-label-value\"][2]";

    String detailsIconDownXpath = "//a[@class=\"datatable-details-icon datatable-icon-down\"]";
    String detailsIconDownBeforeXPATH = "/html/body/app-root/div/div/div/payment-search/div/div[2]/ngx-datatable/div/datatable-body/datatable-selection/datatable-scroller/datatable-row-wrapper[";
    String detailsIconDownAfterXPATH = "]/datatable-body-row/div[2]/datatable-body-cell[7]/div/a";

    String detailsIconUpBeforeXPATH = "/html/body/app-root/div/div/div/payment-search/div/div[2]/ngx-datatable/div/datatable-body/datatable-selection/datatable-scroller/datatable-row-wrapper[";
    String detailsIconUpAfterXPATH = "]/datatable-body-row/div[2]/datatable-body-cell[7]/div/a";
    String detailsIconUpXpath = "//a[@class=\"datatable-details-icon datatable-icon-up\"]";
    //span[@title="1010603"]
    String debtorAccountNoXpath = "/html/body/app-root/div/div/div/payment-search/div/div[2]/ngx-datatable/div/datatable-body/datatable-selection/datatable-scroller/datatable-row-wrapper[1]/datatable-body-row/div[2]/datatable-body-cell[2]";

    //String transactionIDXPATH = "//div[@class=\"row datatable-row-detail-content\"]//div[3]";
    String transactionIDXPATH="(//div[@class=\"row datatable-row-detail-content\"]//div[3])[1]";

    String CreditorBankCodeBeforeXpath ="/html/body/app-root/div/div/div/payment-search/div/div[2]/ngx-datatable/div/datatable-body/datatable-selection/datatable-scroller/datatable-row-wrapper[";
    String CreditorBankCodeAfterXpath ="]/div/div/div[4]/div[3]/span";

    String TransactionIdBeforeXpath ="/html/body/app-root/div/div/div/payment-search/div/div[2]/ngx-datatable/div/datatable-body/datatable-selection/datatable-scroller/datatable-row-wrapper[";
    String TransactionIdAfterXpath ="]/div/div/div[2]/div[3]/span";

    String TransactionAmountBeforeXpath ="/html/body/app-root/div/div/div/payment-search/div/div[2]/ngx-datatable/div/datatable-body/datatable-selection/datatable-scroller/datatable-row-wrapper[";
    String TransactionAmountAfterXpath ="]/datatable-body-row/div[2]/datatable-body-cell[5]/div/div";

    String TransactionStatusBeforeXpath ="/html/body/app-root/div/div/div/payment-search/div/div[2]/ngx-datatable/div/datatable-body/datatable-selection/datatable-scroller/datatable-row-wrapper[";
    String TransactionStatusAfterXpath ="]/datatable-body-row/div[2]/datatable-body-cell[6]/div/div/span";

    /*****************Dynamic Xpath Elements**********************************/

    /***************************************************************************/

    //******************************************************************************************************************
    APIUtil apiUtil = new APIUtil(driver);
    OutwardCreditHandlingModule outwardCreditHandlingModule = new OutwardCreditHandlingModule(driver);

    //***********************************STEPS**************************************************************************

    /**
     * "Search the transaction on the payment search screen
     */
    public PaymentSearchPage paymentSearch(String strCurrentPaymentID, String TestCaseScenariotype, String ExpectedPaymentStatus) throws InterruptedException
    {
        try {
            Assertion hardAssert = new Assertion();

            Thread.sleep(1000);

            //Click on the payment search tab
            click(By.id(paymentSearchTabId));

            //Enter payment ID
            writeText(By.id(paymentSearchTxtBox), strCurrentPaymentID);


            //Click on the date range to select a date for the search
            click(By.xpath(paymentSearchDateRangeSelectorXpath));

            //Select Today as the date range
            click(By.xpath(paymentSearchDateRangeTodayXpath));

            //Click on the search button
            scrollDown();
            scrollDown();
            click(By.cssSelector(paymentSearchSearchBtnCSS));

            //Declare a number to control the retry period
            int intPaymentSearchPageWaitTimeIndex = 24;//(Repeat every 2500 milliseconds ,in a minute)

            /**=============Check if the transaction processing is complete within a given time frame=================*/
            Thread.sleep(2000);

            for (int i = 0; i <= intPaymentSearchPageWaitTimeIndex; i++) {

                //Click on the Search button to refresh the page again
                click(By.cssSelector(paymentSearchSearchBtnCSS));
                scrollDown();

                //Check if the status is still = PROCESSING IN PROGRESS
                strPaymentStatus = driver.findElement(By.xpath("/html/body/app-root/div/div/div/payment-search/div/div[2]/ngx-datatable/div/datatable-body/datatable-selection/datatable-scroller/child::datatable-row-wrapper[1]/descendant::div[10]")).getText();

                if (strPaymentStatus.equalsIgnoreCase("PENDING PROCESSING")) {
                    /**INFO*/
                    Thread.sleep(2500);
                    //If the the transaction status does not change in the given time,then fail and stop the test
                    if (i == intPaymentSearchPageWaitTimeIndex) {
                        ExtentTestManager.getTest().fail(MarkupHelper.createLabel("The transaction is pending processing,please investigate further", ExtentColor.RED));
                        //Take Screenshot
                        screenshotLoggerMethod("paymentSearch", "Payment Search results");

                        //Stop the test
                        stopCurrentTestSetResultsToFailedAndContinueWithNextTest();
                    }
                } else if (strPaymentStatus.equalsIgnoreCase("PROCESSING IN PROGRESS")) {
                    /**INFO*/
                    //test.log(Status.INFO, "Info: The transaction is being processed,please wait before trying again");
                    Thread.sleep(3000);

                    //If the the transaction status does not change in the given time,then fail and stop the test
                    if (i == intPaymentSearchPageWaitTimeIndex) {
                        ExtentTestManager.getTest().fail(MarkupHelper.createLabel("The transaction is taking too long in processing(MORE THAN 2 MINUTES)", ExtentColor.RED));
                        //Take Screenshot
                        screenshotLoggerMethod("paymentSearch", "Payment Search");

                        //Stop the test
                        stopCurrentTestSetResultsToFailedAndContinueWithNextTest();
                    }
                }
                //If the transaction has failed and it not a negative, then get the reason for failure and log it in the report. Stop and fail test
                else if (strPaymentStatus.equalsIgnoreCase("PROCESSING FAILED") && TestCaseScenariotype.equalsIgnoreCase("Positive")) {
                    //hover the payment status for the reason of failure
                    mouseHoverMethod(paymentSearchStatusColumnXpath);

                    //hover the small question mark for failure reason
                    mouseHoverMethod(smallFailureQuestionMark);

                    //now get the text/ reason in a pop up label
                    String reasonOfFailure = driver.findElement(By.xpath(reasonTextContainer)).getText();
                    System.out.println(reasonOfFailure);

                    //Take screen shot
                    screenshotLoggerMethod("Transaction status", "Transaction status");

                    //store the failure reason in a string variable
                    String errorCode = reasonOfFailure;

                    ExtentTestManager.getTest().fail(MarkupHelper.createLabel("The transaction has failed with: " + errorCode + " ", ExtentColor.RED));

                    //Stop the test
                    stopCurrentTestSetResultsToFailedAndContinueWithNextTest();
                }
                //If the the transaction status changes then exist the loop
                else if (!strPaymentStatus.equalsIgnoreCase("PROCESSING IN PROGRESS")&& TestCaseScenariotype.equalsIgnoreCase("Positive")) {
                    /**INFO*/
                    ExtentTestManager.getTest().log(Status.INFO, "Info: Transaction Processing is completed");
                    break;
                }
                else{
                    break;
                }
            }

            /**===============Get the rest of the transaction status for each individual transaction==================*/
            //Get the number of transaction on the table

            int intPaymentSearchTransactionCount = driver.findElements(By.xpath("/html/body/app-root/div/div/div/payment-search/div/div[2]/ngx-datatable/div/datatable-body/datatable-selection/datatable-scroller/child::*")).size();
            int x = 1;//NB: XPath starts counting at 1, so the second element is at position() 2

            do {
                //Get the transaction status of each transaction individually
                strPaymentStatus = driver.findElement(By.xpath("/html/body/app-root/div/div/div/payment-search/div/div[2]/ngx-datatable/div/datatable-body/datatable-selection/datatable-scroller/child::datatable-row-wrapper[" + x + "]/descendant::div[10]")).getText();
                x++;
                int y = x - 1;

                if (strPaymentStatus.contains("PROCESSING SUCCESSFUL")) {
                    ExtentTestManager.getTest().log(Status.PASS, "Status=PASS:: " + x + " The transaction has been successfuly processed");
                    //x++;
                    Thread.sleep(3000);
                    click(By.xpath(detailsIconDownBeforeXPATH + y + detailsIconDownAfterXPATH));

                    strCurrentTransactionID = readText(By.xpath(transactionIDXPATH));
                    //highLighterMethod(By.xpath(transactionIDXPATH));
                    System.out.println("Transaction ID for transaction"+" "+y+" ::"+strCurrentTransactionID);
                    scrollDown();
                    ExtentTestManager.getTest().log(Status.INFO, "Info: Transaction ID for transaction "+" "+y+"  ::" +strCurrentTransactionID);

                    //Take Screenshot
                    screenshotLoggerMethod("Transaction ID", "Transaction ID");

                    if (x == y + 1) {
                        click(By.xpath(detailsIconUpBeforeXPATH + y + detailsIconUpAfterXPATH));
                    }
                }
                else if (strPaymentStatus.contains("ACCEPTED FOR CLEARING"))
                {
                    ExtentTestManager.getTest().log(Status.PASS, "Status=PASS:: " + x + " The transaction has been successfully processed");
                    //x++;
                }
                else if (strPaymentStatus.contains("PROCESSING FAILED"))
                {
                    //==============Get the failure reason======================
                    //Hover over the icon to get the toolTip information
                    mouseHoverMethod(paymentSearchStatusColumnXpath);

                    //Get the transaction status of each transaction individually
                    String strReasonForFailure = driver.findElement(By.xpath(paymentSearchStatusColumnBeforeXpath+y+paymentSearchStatusColumnAfterXpath)).getText();

                    if (TestCaseScenariotype.equalsIgnoreCase("Negative"))
                    {
                        if(strReasonForFailure.contains(ExpectedPaymentStatus))
                        {
                            ExtentTestManager.getTest().info(MarkupHelper.createLabel("Failure Reason: " + strReasonForFailure + " Please investigate ", ExtentColor.BLUE));
                            ExtentTestManager.getTest().pass(MarkupHelper.createLabel(+x + " This transaction has failed to process " + " Negative Scenario", ExtentColor.GREEN));

                            //Take Screenshot
                            screenshotLoggerMethod("TransactionProcessingStatus", "Transaction Processing Status");

                            //Write result to report
                            ExtentTestManager.getTest().pass(MarkupHelper.createLabel("The negative scenario test has passed",ExtentColor.GREEN));

                            //This test should be marked as passed
                            stopCurrentTestSetResultsToPassedAndContinueWithNextTest();
                        }
                    }
                    else
                    {
                        ExtentTestManager.getTest().fail(MarkupHelper.createLabel(+x + " This transaction has failed to process", ExtentColor.RED));

                        //Take Screenshot
                        screenshotLoggerMethod("TransactionProcessingStatus", "Transaction Processing Status");

                        //This test should be marked as failed
                        stopCurrentTestSetResultsToFailedAndContinueWithNextTest();
                    }
                    //x++;
                }
                else if (strPaymentStatus.contains("PENDING PROCESSING"))
                {
                    ExtentTestManager.getTest().log(Status.INFO, +x + " This transaction is pending processing");
                    //x++;
                } else if (strPaymentStatus.contains("FAILED TO SUBMIT"))
                {
                    ExtentTestManager.getTest().log(Status.FAIL, "Status=FAIL: " + x + " The transaction has been successfuly processed");
                    //x++;
                }

            } while (x <= intPaymentSearchTransactionCount);

        } catch (Exception e) {

        }
        Thread.sleep(5000);
        return this;
    }
    //******************************************************************************************************************


    public PaymentSearchPage currentTransactionID() {
        //TODO By Msa..... Create a method that takes the TRANSACTION ID(s) from search screen, store it and it will be used to query on DB/REST back end!
        //Create an array to store id's in case of multiple payment
        return this;
    }

    //TODO:  THE NEW APPROACH TO IMPLEMENT THE SEARCH SCRIPT & LOGIC BELOW METHOD TO BE DONE
    //TODO: Whole point is that I must use 1 entering method and switch the id's or xpaths and the reading of data to be search by.

    public PaymentSearchPage searchByGivenData2(String GivenData, By element) throws Exception {

        Thread.sleep(1000);

        //Click on the payment search tab
        click(By.id(paymentSearchTabId));
        SetScriptTimeout();

        //switch between the data to search by, per test case


        //Write data to search by
        writeText(element, GivenData);

        //click search button
        click(By.cssSelector(paymentSearchSearchBtnCSS));


        //Check PopUp window
        boolean ErrorEccuredPop = isAlertPresent();
        if (ErrorEccuredPop == true) {
            ExtentTestManager.getTest().fail(MarkupHelper.createLabel("PPO error occurred,please check PopUp", ExtentColor.RED));
            screenshotLoggerMethod("PopUp screenshot", "PPO error occurred,please check PopUp");
            stopCurrentTestSetResultsToFailedAndContinueWithNextTest();
        } else
        {
            ExtentTestManager.getTest().pass(MarkupHelper.createLabel("Search was successful", ExtentColor.BLUE));
        }
        return this;}

    /**
     * Search By The Given Data
     */
    public PaymentSearchPage searchByGivenData(String DebtorAccountNumber, String CreditorAccountNumber, String PaymentID, String DebtorReference, String CreditorReference, String WorkflowReference) throws Exception {

        Thread.sleep(1000);

        //Click on the payment search tab
        click(By.id(paymentSearchTabId));

        //Enter account number and click search button
        SetScriptTimeout();
        //Debtor account text box
        writeText(By.id(debtorAccountNumberTxtBoxID), DebtorAccountNumber);
        //Creditor account text box
        writeText(By.id(creditorAccountNumberTxtBoxID), CreditorAccountNumber);
        //Workflow reference text box
        writeText(By.id(paymentSearchTxtBox), PaymentID);
        //Reference (debtor) text box
        writeText(By.id(transactionReferenceTxtBoxID), DebtorReference);
        //Reference (creditor) text box
        writeText(By.id(transactionReferenceTxtBoxID), CreditorReference);
        //Workflow Reference text box
        writeText(By.id(workflowReferenceTxtBoxID), WorkflowReference);

        //click search button
        click(By.cssSelector(paymentSearchSearchBtnCSS));


        //click ok or remove pop up if it appears
        //TODO By Msawakhe
        //Check PopUp window
        boolean ErrorEccuredPop = isAlertPresent();
        if (ErrorEccuredPop == true) {
            ExtentTestManager.getTest().fail(MarkupHelper.createLabel("PPO error occurred,please check PopUp", ExtentColor.RED));
            screenshotLoggerMethod("PopUp screenshot", "PPO error occurred,please check PopUp");
            stopCurrentTestSetResultsToFailedAndContinueWithNextTest();
        } else
        {
            ExtentTestManager.getTest().pass(MarkupHelper.createLabel("Search was successful", ExtentColor.BLUE));


            //replace below code by the reportingTest method
            //reportingTest();

            /*        //Open the table for the record(s) found
        click(By.xpath(detailsIconDownXpath));

        boolean elementSearchResultsTableHeader = isDisplayed(searchResultsTableHeader);
        boolean statusContainerVisibleBool=isDisplayed((recordAllStatusXpath));

        //boolean statusContainerVisibleBool=checkElementPresenceOnDynamicTable(By.xpath(recordAllStatusXpath));
        String fromDateTextBoxString = readText(By.xpath(fromDateLabelXpath)).trim();
        String toDateTextBoxString=readText(By.xpath(toDateLabelXpath)).trim();

        String fromDateLabelTextString = readText(By.xpath(fromDateLabelXpath)).trim();
        String toDateLabelTextString=readText(By.xpath(toDateLabelXpath)).trim();

        int totalRecordsFound=recordAllStatusXpath.length();*/

            //TODO MSA UNCOMMENT THE ABOVE BLOCK TO CONT.... DEEP VALIDATION


            //Check if the first table of any records is returned, if yes the pass the test if not then fail

            WebElement element = driver.findElement(By.xpath("/html/body/app-root/div/div/div/payment-search/div/div[2]/div[1]/div[1]/h4"));
            try {

                if (element.isDisplayed()) {
                    scrollDown();
                    Thread.sleep(2000);

                    //take a screen shot
                    screenshotLoggerMethod("Search results", "Search Results");

                    //pass the test on the report
                    ExtentTestManager.getTest().pass(MarkupHelper.createLabel("Search Test Passed: " + "Search Test Passed: ", ExtentColor.GREEN));

                } else {
                    //take a screen shot
                    screenshotLoggerMethod("Search results", "Search Results");

                    //pass the test on the report
                    ExtentTestManager.getTest().pass(MarkupHelper.createLabel("Search Test Failed: " + "Search Test Failed: ", ExtentColor.RED));

                }

                //click the clear button
                click(By.xpath(clearButtonXpath));
                Thread.sleep(1000);

            } catch (Exception e) {

            }
        }
        return this;}

    /**
     * Search By The Creditor Account Number
     */
    public PaymentSearchPage searchByCreditorAccountNumber(String CreditorAccountNumber) throws InterruptedException {
        //Enter account number and click search button
        SetScriptTimeout();
        writeText(By.id(creditorAccountNumberTxtBoxID), CreditorAccountNumber);
        click(By.cssSelector(paymentSearchSearchBtnCSS));
        return this; }

    /**
     * Search By The Payment ID
     */
    public PaymentSearchPage searchByPaymentID(String paymentID)
    {
        try{
            click(By.id(welcomeTab));
        }
        catch (Exception e){

        }
        return this; }

    //Search payment using the payment status from list of available statuses on dropdown
    public PaymentSearchPage searchByPaymentStatus(String status) throws Exception {
        try {
            //Click on the payment search tab
            click(By.id(paymentSearchTabId));
            Thread.sleep(1000);

            selectdropdownItemByValueIndex(By.xpath(statusDropDownXpath), status);

            //click search button
            click(By.cssSelector(paymentSearchSearchBtnCSS));

            //reportingTest(status);
            waitForPageToBeReady_2();

            boolean elementSearchResultsTableHeader = isDisplayed(searchResultsTableHeader);
            boolean statusContainerVisibleBool = isDisplayed((recordAllStatusXpath));

            //boolean statusContainerVisibleBool=checkElementPresenceOnDynamicTable(By.xpath(recordAllStatusXpath));
            String textOnStatusContainerString = readText(By.xpath(recordAllStatusXpath));
            String textOnStatusContainerString1 = readText(By.xpath(processingFailedIcon));

            int totalRecordsFound = recordAllStatusXpath.length();

            try {
                if (elementSearchResultsTableHeader == true) {
                    scrollElementIntoView(By.xpath(searchFieldsContainerXpath));

                    //pass the test on the report
                    ExtentTestManager.getTest().pass(MarkupHelper.createLabel("Search Test Passed: " + "Search Test Passed: ", ExtentColor.GREEN));
                    waitForPageToBeReady_2();

                    //take a screen shot
                    screenshotLoggerMethod("Search Functionality Works", "Search Functionality Is Working");

                    if (statusContainerVisibleBool == true) {
                        //Check the status of returned search results IF the are any records found
                        scrollElementIntoView(By.xpath(recordAllStatusXpath));
                        ExtentTestManager.getTest().info(MarkupHelper.createLabel("There is total of" + " " + totalRecordsFound + " " + "records found: " + " Searching matches", ExtentColor.BLUE));
                        screenshotLoggerMethod("Records Found", "The status of returned records by the search");

                        if (StringUtils.containsIgnoreCase(textOnStatusContainerString1, status.trim()) || textOnStatusContainerString.equalsIgnoreCase(status.trim())) {
                            scrollElementIntoView(By.xpath(recordAllStatusXpath));
                            ExtentTestManager.getTest().info(MarkupHelper.createLabel("There is total of" + " " + totalRecordsFound + " " + "records found: " + " Searching matches", ExtentColor.BLUE));
                            screenshotLoggerMethod("Status Match", "The Status of Payment Matches The Search");


                            //Records Matches The Searched: Expected Record(s) of search input is found in returned records
                            //Check for account numbers, references, dates
                            //TODO BY MSA
                        } else {
                            ExtentTestManager.getTest().info(MarkupHelper.createLabel("There is 0 records found: " + " Searching matches", ExtentColor.BLUE));
                        }
                    } else {
                        ExtentTestManager.getTest().info(MarkupHelper.createLabel("No records found matches: " + " Payments Table Does Not Have Any Records For Above Specified", ExtentColor.AMBER));

                        //take a screen shot
                        screenshotLoggerMethod("No Records Found", "Search Found No Records");
                    }
                } else {
                    //When nothing is returned
                    //click the searching input field and highlight it.
                    //The 2 below lines probable need to go under a BaseClassHelper on the selectdropdownItemByValueIndex with a boolean....
                    click(By.xpath(statusDropDownXpath));
                    highLighterMethod(By.xpath(statusDropDownXpath));

                    //take a screen shot
                    screenshotLoggerMethod("Search Not functioning", "Search Functionality Is Broken");

                    //pass the test on the report
                    ExtentTestManager.getTest().fail(MarkupHelper.createLabel("Search Test Failed: " + "Search Test Failed: ", ExtentColor.RED));
                }
                SetScriptTimeout();
            } catch (Exception e) {
                ExtentTestManager.getTest().warning(MarkupHelper.createLabel("NOT FOUNDING THE STATUS TEXT OR CONTAINER", ExtentColor.BLUE));
            }

        } catch (Exception e) {
            try {
                scrollElementIntoView(By.xpath(searchFieldsContainerXpath));
                waitForPageToBeReady_2();

                //take a screen shot
                screenshotLoggerMethod("Search Functionality Works", "Search Functionality Is Working");
                ExtentTestManager.getTest().info(MarkupHelper.createLabel("No records found matches: " + " Payments Table Does Not Have Any Records For Above Specified", ExtentColor.AMBER));

                //take a screen shot
                screenshotLoggerMethod("No Records Found", "Search Found No Records");
            } catch (Exception ignored) {

            }
        }
        return this;}


    //Search payment using the payment status from list of available statuses on dropdown

    /**
     * Search By The Payment Status Processing In Progress Status
     */
    public PaymentSearchPage searchByProcessingInProgressStatus() {

        return this;}
    //Search payment using the payment status from list of available statuses on dropdown

    /**
     * Search By The Payment Status Processing Failed Status
     */
    public PaymentSearchPage searchByProcessingFailedStatus() {

        return this;}


    //Search payment using the reference from debtor or creditor in a text box

    /**
     * Search By The Reference/Narrative From Debtor or Creditor
     */
    public PaymentSearchPage searchByReferenceDebtorOrCreditor(String DebtorOriginationReference) throws InterruptedException {

        //Enter account number and click search button
        SetScriptTimeout();
        writeText(By.id(transactionReferenceTxtBoxID), DebtorOriginationReference);
        click(By.cssSelector(paymentSearchSearchBtnCSS));
        return this;}

    //Search payment using the  workflow reference in a text box
    /**
     * Search By The Work Reference
     */
    public PaymentSearchPage searchByWorkflowReference(String DebtorWorkflowReference) throws InterruptedException {
        //Enter account number and click search button
        SetScriptTimeout();
        writeText(By.id(workflowReferenceTxtBoxID), DebtorWorkflowReference);
        click(By.cssSelector(paymentSearchSearchBtnCSS));
        return this;}

    //Search payment using the submitted date in a text box
    public PaymentSearchPage reportingTest(String statusOfPayment) {

        boolean elementSearchResultsTableHeader = isElementDisplayed(searchResultsTableHeader);
        boolean statusContainerVisibleBool = isElementDisplayed(recordAllStatusXpath);
        String textOnStatusContainerString = readText(By.xpath(recordAllStatusXpath));

        try {
            if (elementSearchResultsTableHeader) {
                scrollElementIntoView(By.xpath(searchFieldsContainerXpath));
                //scrollDown();
                Thread.sleep(1000);

                //take a screen shot
                screenshotLoggerMethod("Search results", "Search Results");

                //pass the test on the report
                ExtentTestManager.getTest().pass(MarkupHelper.createLabel("Search Test Passed: " + "Search Test Passed: ", ExtentColor.GREEN));

                if (statusContainerVisibleBool) {
                    //Check the status of returned search results IF the are any records found
                    scrollElementIntoView(By.xpath(recordAllStatusXpath));
                    ExtentTestManager.getTest().info(MarkupHelper.createLabel("There is one or more records found: " + " Searching matches", ExtentColor.BLUE));
                    screenshotLoggerMethod("Records Found", "The returned records status for the search");

                    if (textOnStatusContainerString.equalsIgnoreCase(statusOfPayment.trim())) //Must be dynamic, accepted as parameter of expected instead of "Processing Successful"
                    {
                        scrollElementIntoView(By.xpath(recordAllStatusXpath));
                        ExtentTestManager.getTest().info(MarkupHelper.createLabel("Records Matches The Searched: " + " Expected Record(s) Status Is Found", ExtentColor.BLUE));
                        screenshotLoggerMethod("Status Match", "The Status of Payment Matches The Search");

                        // Processing Failed Status
                        //Records Matches The Searched: Expected Record(s) Status Is Found
                        //The Status of Payment Matches The Search
                        //ABOVE ARE MISSING BECAUSE FAILED STATUS HAS DIFFERENT LOOK, SO AS XPATH
                        //TODO BY MSA
                    }


                } else {
                    //
                    stopCurrentTestSetResultsToPassedAndContinueWithNextTest();
                    ExtentTestManager.getTest().info(MarkupHelper.createLabel("No records found: " + " No match for search", ExtentColor.BLUE));

                }

            } else {
                //When nothing is returned
                //click the searching input field and highlight it.
                //The 2 below lines probable need to go under a BaseClassHelper on the selectdropdownItemByValueIndex with a boolean....
                click(By.xpath(statusDropDownXpath));
                highLighterMethod(By.xpath(statusDropDownXpath));

                //take a screen shot
                screenshotLoggerMethod("Search results ", "Search results ");

                //pass the test on the report
                ExtentTestManager.getTest().fail(MarkupHelper.createLabel("Search Test Failed: " + "Search Test Failed: ", ExtentColor.RED));
            }
            SetScriptTimeout();
        } catch (Exception e) {
        }
        return this; }

    //Search payment using the date's specif range by selecting on calender

    /**
     * Search By The Date Range
     */
    public PaymentSearchPage searchBySpecifiedDateRange(String date) throws InterruptedException {
        Thread.sleep(1000);

        //Click on the payment search tab
        click(By.id(paymentSearchTabId));

        try {
            //Select dates and click search button
            if (date.equalsIgnoreCase("Today")) {
                click(By.xpath(dateButtonXpath));
                click(By.xpath(todaysDateXpath));
            }

            if (date.equalsIgnoreCase("Yesterday")) {
                click(By.xpath(dateButtonXpath));
                click(By.xpath(yesterdaysXpath));
            }
            if (date.equalsIgnoreCase("Last 7 days")) {
                click(By.xpath(dateButtonXpath));
                click(By.xpath(last7DaysXpath));
            }
            if (date.equalsIgnoreCase("Last 30 days")) {
                click(By.xpath(dateButtonXpath));
                click(By.xpath(last30DaysXpath));
            }
            if (date.equalsIgnoreCase("Last 60 days")) {
                click(By.xpath(dateButtonXpath));
                click(By.xpath(last60DaysXpath));
            }
            if (date.equalsIgnoreCase("Last 90 days")) {
                click(By.xpath(dateButtonXpath));
                click(By.xpath(last90DaysXpath));
            }
            if (date.equalsIgnoreCase("Custome Range")) {
                click(By.xpath(dateButtonXpath));
                click(By.xpath(customeRangeDateXpath));


                //click on calender month back arrow
                click(By.xpath(backDateArrowXpath));
                //click on the first date
                click(By.xpath(firstDateXpath));
                //click on calender month forward arrow
                click(By.xpath(forwardArrowXpath));
                //click on the last date
                click(By.xpath(lastDateXpath));

                //take a screen shot
                screenshotLoggerMethod("SelectedCustomRange", "Custome range selection");

                //click apply button for specified range
                click(By.xpath(applyButtonXpath));
                Thread.sleep(1000);
            }

            //click search button
            click(By.cssSelector(paymentSearchSearchBtnCSS));

            //replace below with reportingTest method
            //reportingTest();


            boolean elementSearchResultsTableHeader = isDisplayed(searchResultsTableHeader);
            boolean statusContainerVisibleBool = isDisplayed((recordAllStatusXpath));

            //boolean statusContainerVisibleBool=checkElementPresenceOnDynamicTable(By.xpath(recordAllStatusXpath));
            String fromDateTextBoxString = readText(By.xpath(fromDateLabelXpath)).trim();
            String toDateTextBoxString = readText(By.xpath(toDateLabelXpath)).trim();

            String fromDateLabelTextString = readText(By.xpath(fromDateLabelXpath)).trim();
            String toDateLabelTextString = readText(By.xpath(toDateLabelXpath)).trim();

            int totalRecordsFound = recordAllStatusXpath.length();

            //Check if result table is visible, then pass the test
            WebElement element = driver.findElement(By.xpath("/html/body/app-root/div/div/div/payment-search/div/div[2]/div[1]/div[1]/h4"));

            if (elementSearchResultsTableHeader) {
                scrollElementIntoView(By.xpath(searchFieldsContainerXpath));

                //pass the test on the report
                ExtentTestManager.getTest().pass(MarkupHelper.createLabel("Search Test Passed: " + "Search Test Passed: ", ExtentColor.GREEN));
                waitForPageToBeReady_2();

                //take a screen shot
                screenshotLoggerMethod("Search Functionality Works", "Search Functionality Is Working");


                if (fromDateTextBoxString.equalsIgnoreCase(fromDateLabelTextString) && toDateTextBoxString.equalsIgnoreCase(toDateLabelTextString)) {
                    scrollElementIntoView(By.xpath(recordAllStatusXpath));
                    ExtentTestManager.getTest().info(MarkupHelper.createLabel("There is total of" + " " + totalRecordsFound + " " + "records found: " + " Searching matches", ExtentColor.BLUE));
                    screenshotLoggerMethod("Date Range Match", "The Date Range Results Matches The Search");

                    //TODO BY MSA
                    //Records Matches The Searched: Expected Record(s) of search input is found in returned records
                    //Check for account numbers, references, dates
                } else {
                    ExtentTestManager.getTest().info(MarkupHelper.createLabel("There is 0 records found: " + " Searching matches", ExtentColor.BLUE));
                }
            } else {
                //If nothing is returned, click the searching input field and highlight it.
                click(By.xpath(dateTextboxXpath));
                highLighterMethod(By.xpath(dateTextboxXpath));

                //take a screen shot
                screenshotLoggerMethod("Search Not functioning", "Search Functionality Is Broken");

                //fail the test on the report
                ExtentTestManager.getTest().fail(MarkupHelper.createLabel("Search Test Failed: " + "Search Test Failed: ", ExtentColor.RED));
            }

            SetScriptTimeout();

        } catch (Exception e) {
            try {
                scrollElementIntoView(By.xpath(searchResultsTableHeader));
                waitForPageToBeReady_2();

                //take a screen shot
                screenshotLoggerMethod("Search Functionality Works", "Search Functionality Is Working");
                ExtentTestManager.getTest().info(MarkupHelper.createLabel("No records found matches: " + " Payments Table Does Not Have Any Records For Above Specified", ExtentColor.AMBER));

                //take a screen shot
                screenshotLoggerMethod("No Records Found", "Search Found No Records");
            } catch (Exception ex) {

            }
        }
        //******************************************************************************************************************
        return this;}

    //awanti-searchPaymentAfterAuthorization
    public PaymentSearchPage searchPaymentAfterAuth(String strCurrentPaymentID, String NumberOfTransactions){
        int numOfCreditors = Integer.valueOf(NumberOfTransactions);

        try{
            Assertion hardAssert = new Assertion();
            click(By.id(welcomeTab));
            Thread.sleep(4000);

            //Click on the payment search tab
            click(By.id(paymentSearchTabId));
            Thread.sleep(3000);
            //Enter payment ID
            writeText(By.id(paymentSearchTxtBox), strCurrentPaymentID);

            //Click on the search button
            scrollDown();
            scrollDown();
            click(By.cssSelector(paymentSearchSearchBtnCSS));
            Thread.sleep(3000);

            scrollDown();
            scrollDown();

            screenshotLoggerMethod("Status of payment", "Status of payment");

            for(int i=1; i<=numOfCreditors; i++){
                String strPaymentStatusXpath = "/html/body/app-root/div/div/div/payment-search/div/div[2]/ngx-datatable/div/datatable-body/datatable-selection/datatable-scroller/datatable-row-wrapper[" + i + "]/datatable-body-row/div[2]/datatable-body-cell[6]/div/div/span";
                strPaymentStatus = driver.findElement(By.xpath("/html/body/app-root/div/div/div/payment-search/div/div[2]/ngx-datatable/div/datatable-body/datatable-selection/datatable-scroller/datatable-row-wrapper[" + i + "]/datatable-body-row/div[2]/datatable-body-cell[6]/div/div/span")).getText();
                if(strPaymentStatus.contains("PROCESSING FAILED")){
                    //Hover over the icon to get the toolTip information
                    mouseHoverMethod(strPaymentStatusXpath);
                    screenshotLoggerMethod("Failed Transaction error", "Failed Transaction error");
                }
            }
        }
        catch(Exception e){
            System.out.println(e);
        }
        return this;}

    public PaymentSearchPage statusValidationGetTransactionId(String BankCountry, String NumberOfTransactions, String DebtorBankCode,String strCurrentPaymentID,String ExpectedFirstPacs002FIToFIPmtStsRptTxInfAndStsTxSts_0,
                                                 String ExpectedFirstPacs002FIToFIPmtStsRptTxInfAndStsStsRsnInfRsnCd_0,
                                                 String ExpectedFirstPacs002FIToFIPmtStsRptTxInfAndStsTxSts_1,
                                                 String ExpectedFirstPacs002FIToFIPmtStsRptTxInfAndStsStsRsnInfRsnCd_1,
                                                 String ExpectedFirstPacs002FIToFIPmtStsRptTxInfAndStsTxSts_2,
                                                 String ExpectedFirstPacs002FIToFIPmtStsRptTxInfAndStsStsRsnInfRsnCd_2,
                                                 String ExpectedFirstPacs002FIToFIPmtStsRptTxInfAndStsTxSts_3,
                                                 String ExpectedFirstPacs002FIToFIPmtStsRptTxInfAndStsStsRsnInfRsnCd_3,
                                                 String ExpectedFirstPacs002FIToFIPmtStsRptTxInfAndStsTxSts_4,
                                                 String ExpectedFirstPacs002FIToFIPmtStsRptTxInfAndStsStsRsnInfRsnCd_4,
                                                 String ExpectedFirstPacs002FIToFIPmtStsRptTxInfAndStsTxSts_5,
                                                 String ExpectedFirstPacs002FIToFIPmtStsRptTxInfAndStsStsRsnInfRsnCd_5) throws Throwable {
        int numOfCreditors = Integer.valueOf(NumberOfTransactions);
        String creditorBankCodeUI, transactionIdUI, amountUI=null;

        try{
            //get text from search screen
            for(int i=1;i<=numOfCreditors;i++){
                //click on expand
                click(By.xpath(detailsIconDownBeforeXPATH + i + detailsIconDownAfterXPATH));
                //get creditor bank code
                creditorBankCodeUI = readText(By.xpath(CreditorBankCodeBeforeXpath + i + CreditorBankCodeAfterXpath));
                //get payment status
                strPaymentStatus = readText(By.xpath(TransactionStatusBeforeXpath + i + TransactionStatusAfterXpath));
                //get transaction id
                transactionIdUI = readText(By.xpath(TransactionIdBeforeXpath + i + TransactionIdAfterXpath));
                //get amount
                amountUI = readText(By.xpath(TransactionAmountBeforeXpath + i + TransactionAmountAfterXpath));

                if(creditorBankCodeUI.contains(DebtorBankCode)){
                    //this is an onus payment
                    //check status
                   if(strPaymentStatus.contains("PROCESSING SUCCESSFUL")){
                        ExtentTestManager.getTest().log(Status.INFO, "On-Us transaction is successful");
                    }
                    else if(strPaymentStatus.contains("PROCESSING IN PROGRESS")){
                        //ExtentTestManager.getTest().log(Status.FAIL, "On-Us transaction is stuck in Processing In Progress");
                    }
                    else if(strPaymentStatus.contains("PENDING PROCESSING")){
                        ExtentTestManager.getTest().log(Status.FAIL, "On-Us transaction is stuck in Pending Processing");
                    }
                    else if(strPaymentStatus.contains("PENDING AUTHORISATION")){
                        ExtentTestManager.getTest().log(Status.FAIL, "On-Us transaction is not authorised yet");
                    }
                    /*
                    else if(strPaymentStatus.contains("PROCESSING FAILED")){
                        ExtentTestManager.getTest().log(Status.FAIL, "On-Us transaction is Failed");
                        //Hover over the icon to get the toolTip information
                        mouseHoverMethod(strPaymentStatus);
                        screenshotLoggerMethod("Failed Transaction error", "Failed Transaction error");
                    }
                    else{
                        //Please check screenshot
                        ExtentTestManager.getTest().log(Status.FAIL, "This status is not applicable to On-Us transaction");
                        screenshotLoggerMethod("Unknown Status", "Unknown Status" );
                    }*/
                }
                //bank code is different
                else if(!creditorBankCodeUI.contains(DebtorBankCode) && (strPaymentStatus.contains("PROCESSING IN PROGRESS" )|| strPaymentStatus.contains("SENT FOR CLEARING" ) ))

                {
                    //check if the country is MUS, if yes write message that the status will change at clearing time

                    if(!BankCountry.contains("MUS")&&!BankCountry.contains("SYC")) {
                        //check if pacs008 is created. if created-> fetch it , drop ACSP, RJCT or NACK
                        outwardCreditHandlingModule.getPac008message(BankCountry, transactionIdUI,
                                ExpectedFirstPacs002FIToFIPmtStsRptTxInfAndStsTxSts_0,
                                ExpectedFirstPacs002FIToFIPmtStsRptTxInfAndStsStsRsnInfRsnCd_0,
                                ExpectedFirstPacs002FIToFIPmtStsRptTxInfAndStsTxSts_1,
                                ExpectedFirstPacs002FIToFIPmtStsRptTxInfAndStsStsRsnInfRsnCd_1,
                                ExpectedFirstPacs002FIToFIPmtStsRptTxInfAndStsTxSts_2,
                                ExpectedFirstPacs002FIToFIPmtStsRptTxInfAndStsStsRsnInfRsnCd_2,
                                ExpectedFirstPacs002FIToFIPmtStsRptTxInfAndStsTxSts_3,
                                ExpectedFirstPacs002FIToFIPmtStsRptTxInfAndStsStsRsnInfRsnCd_3,
                                ExpectedFirstPacs002FIToFIPmtStsRptTxInfAndStsTxSts_4,
                                ExpectedFirstPacs002FIToFIPmtStsRptTxInfAndStsStsRsnInfRsnCd_4,
                                ExpectedFirstPacs002FIToFIPmtStsRptTxInfAndStsTxSts_5,
                                ExpectedFirstPacs002FIToFIPmtStsRptTxInfAndStsStsRsnInfRsnCd_5);
                    }
                    else if(BankCountry.contains("MUS")){
                        ExtentTestManager.getTest().log(Status.INFO, "For Mauritius, status of payment will change at clearing time. " +
                                "In SIT, clearing time is set up every 10 mins.");
                    }
                    else if(BankCountry.contains("SYC")){

                        outwardCreditHandlingModule.CBSmessages(transactionIdUI,amountUI,BankCountry,ExpectedFirstPacs002FIToFIPmtStsRptTxInfAndStsTxSts_0,
                                ExpectedFirstPacs002FIToFIPmtStsRptTxInfAndStsStsRsnInfRsnCd_0,
                                ExpectedFirstPacs002FIToFIPmtStsRptTxInfAndStsTxSts_1,
                                ExpectedFirstPacs002FIToFIPmtStsRptTxInfAndStsStsRsnInfRsnCd_1,
                                ExpectedFirstPacs002FIToFIPmtStsRptTxInfAndStsTxSts_2,
                                ExpectedFirstPacs002FIToFIPmtStsRptTxInfAndStsStsRsnInfRsnCd_2,
                                ExpectedFirstPacs002FIToFIPmtStsRptTxInfAndStsTxSts_3,
                                ExpectedFirstPacs002FIToFIPmtStsRptTxInfAndStsStsRsnInfRsnCd_3,
                                ExpectedFirstPacs002FIToFIPmtStsRptTxInfAndStsTxSts_4,
                                ExpectedFirstPacs002FIToFIPmtStsRptTxInfAndStsStsRsnInfRsnCd_4,
                                ExpectedFirstPacs002FIToFIPmtStsRptTxInfAndStsTxSts_5,
                                ExpectedFirstPacs002FIToFIPmtStsRptTxInfAndStsStsRsnInfRsnCd_5);
                        /*

                        //database connection method
                        //fetch system ref number using transaction id
                        String fetchSysRefQuery = "select system_reference, amount, transaction_id, correlation_id from  mk_seychelles_clearing.clearing_transaction_entity \n" +
                                "where transaction_id ='"+transactionIdUI+"'";
                        String SystemRefNumber = databaseHandlerForPocDB(fetchSysRefQuery,"system_reference");
                        System.out.println(SystemRefNumber);

                        //fetch correlation id number using transaction id
                        String fetchCorrelationIdQuery = "select system_reference, amount, transaction_id, correlation_id from  mk_seychelles_clearing.clearing_transaction_entity \n" +
                                "where transaction_id ='"+transactionIdUI+"'";
                        String CorrelationId = databaseHandlerForPocDB(fetchCorrelationIdQuery,"correlation_id");
                        System.out.println(CorrelationId);

                        //fetch -request to CBS

                        if(SystemRefNumber!=null && CorrelationId!=null){
                            String fetchCBSRequest = "select system_reference, correlation_id, message_value, message_type, \n" +
                                    "convert_from(loread(lo_open(message_value::int, x'40000'::int), x'40000'::int), 'UTF8') \n" +
                                    "from mk_seychelles_clearing.clearing_message_entity where correlation_id ='"+CorrelationId+"' and message_type = 'SEFT_PAYMENT_REQUEST'  \n" +
                                    "and convert_from(loread(lo_open(message_value::int, x'40000'::int), x'40000'::int), 'UTF8')  like '%<amount>"+amountUI+"</amount>%'";

                            String CBSRequest = databaseHandlerForPocDB(fetchCBSRequest,"convert_from");

                            ExtentTestManager.getTest().info(MarkupHelper.createLabel("Request to CBS", ExtentColor.PURPLE));
                            if(CBSRequest!=null){
                                codeLogsXML(CBSRequest);
                                ExtentTestManager.getTest().log(Status.PASS, "CBS Request is generated");
                            }
                            else{
                                ExtentTestManager.getTest().log(Status.FAIL, "CBS Request is not generated");
                            }

                            String fetchCBSFirstResponse = "select system_reference, correlation_id, message_value, message_type, \n" +
                                    "convert_from(loread(lo_open(message_value::int, x'40000'::int), x'40000'::int), 'UTF8') \n" +
                                    "from mk_seychelles_clearing.clearing_message_entity where correlation_id ='"+CorrelationId+"' and message_type = 'SEFT_PAYMENT_RESPONSE'\n" +
                                    "and system_reference = '"+SystemRefNumber+"'";

                            String CBSFirstResponse = databaseHandlerForPocDB(fetchCBSFirstResponse,"convert_from");

                            ExtentTestManager.getTest().info(MarkupHelper.createLabel("First Response From CBS", ExtentColor.PURPLE));
                            if(CBSFirstResponse!=null){
                                codeLogsXML(CBSFirstResponse);
                                ExtentTestManager.getTest().log(Status.PASS, "CBS Response is received");
                            }
                            else{
                                ExtentTestManager.getTest().log(Status.FAIL, "CBS Response is not received");
                            }

                            //Create second response

                            //Drop second response
                        }
                        else{
                            ExtentTestManager.getTest().log(Status.FAIL, "System reference is not present in database for this transaction");
                        }

                        */

                    }
                    //searchPaymentAfterAuth(strCurrentPaymentID,NumberOfTransactions);

                    /*
                    if(strPaymentStatus.contains("ACCEPTED FOR CLEARING")){
                        ExtentTestManager.getTest().log(Status.PASS, "Off-Us transaction is successful");
                    }
                    else if(strPaymentStatus.contains("PROCESSING IN PROGRESS")){
                        ExtentTestManager.getTest().log(Status.FAIL, "Off-Us transaction is stuck in Processing In Progress");
                    }
                    else if(strPaymentStatus.contains("PENDING PROCESSING")){
                        ExtentTestManager.getTest().log(Status.FAIL, "Off-Us transaction is stuck in Pending Processing");
                    }
                    else if(strPaymentStatus.contains("PENDING AUTHORISATION")){
                        ExtentTestManager.getTest().log(Status.FAIL, "Off-Us transaction is not authorised yet");
                    }
                    else if(strPaymentStatus.contains("PROCESSING FAILED")){
                        ExtentTestManager.getTest().log(Status.FAIL, "Off-Us transaction is Failed");
                        //Hover over the icon to get the toolTip information
                        mouseHoverMethod(strPaymentStatus);
                        screenshotLoggerMethod("Failed Transaction error", "Failed Transaction error");
                    }
                    else{
                        //Please check screenshot
                        ExtentTestManager.getTest().log(Status.FAIL, "This status is not applicable to Off-Us transaction");
                        screenshotLoggerMethod("Unknown Status", "Unknown Status" );
                    }*/
                }
            }
        }
        catch(Exception e){
            System.out.println(e);
        }
        return this;}


}