package pages;

import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import Utilities.api.APIUtil;
import base.BasePage;
import helpers.CreateSingleInwardSettlementNotification;
import helpers.ValidateInwardTransactionsOnDB;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utils.extentreports.ExtentTestManager;
import utils.logs.Log;

public class InwardCreditsPage extends BasePage
{
    public InwardCreditsPage(WebDriver driver) {

        super(driver);
        Log.info("The current working class: "+this.getClass().getName());
    }
    //***********************************WEB ELEMENTS*******************************************************************
    //******************************************************************************************************************
    String inwardsCreditsTabId = "inward-credits";
    String inwardsCreditsTabXpath = "//*[@id=\"inward-credits\"]";
    String showEntrieslDropdownId="sol-custom-select-0";
    String showEntriesDropdownXpath="//div[@id=\"sol-custom-select-0\"]";
    String searchBySystemReferenceTextBoxId = "search_text";
    String searchBySystemReferenceTextBoxXpath = "//input[@id=\"search_text\"]";
    String searchBySystemReferenceButtonId="search-btn";
    String searchBySystemReferenceButtonXpath="//button[@id=\"search-btn\"]";
    String systemReferenceLinkXpath="";

    /*****************Dynamic Xpath Elements**********************************/
    String before_systemReferenceXpath="//span[text()='";
    String after_systemReferenceXpath="']";
    String dataTableXpath = "//datatable-body[@class=\"datatable-body\"]";
    String currentTransactionRowXpath = "//datatable-body[@class=\"datatable-body\"]//datatable-row-wrapper";
    String currentTransactionRowBeforeXpath = "//datatable-body[@class=\"datatable-body\"]//datatable-row-wrapper[contains(.,'";
    String currentTransactionRowAfterXpath = "')]";
    String currentTxnStatusBeforeXpath = "//datatable-row-wrapper[contains(.,'";
    String currentTxnStatusAfterXpath = "')]//datatable-body-cell[8]//div//span[contains(.,'')]";
    String currentTxnProcessingStatus1Xpath = "//datatable-row-wrapper[contains(.,'";
    String currentTxnProcessingStatus2Xpath = "')]//datatable-body-cell[9]//div//span[contains(.,'')]";
    String currentTxnDetailsButton1Xpath = "//datatable-row-wrapper[contains(.,'";
    String currentTxnDetailsButton2Xpath = "')]//datatable-body-cell[10]//div//div//a";
    String successTxnXpath = "//datatable-row-wrapper//datatable-body-row//div//datatable-body-cell//div//span[@class='pill pill-large pill-success']";
    String failedTxnXpath = "//datatable-row-wrapper//datatable-body-row//div//datatable-body-cell//div//span[@class='pill pill-large pill-danger']";
    String totalTransactionRowsXpath = "//datatable-row-wrapper";
    String dynamicRowPosition1Xpath = "//datatable-row-wrapper[";
    String dynamicRowPosition2Xpath = "]";  // "]//datatable-body-row//div//datatable-body-cell//div//span)[9]";
    String transactionStatus1Xpath = "(//datatable-row-wrapper//datatable-body-row//div//datatable-body-cell//div//span[contains(.,'')])[9]";
    String transactionMSA1Xpath = "(//datatable-row-wrapper[";
    String transactionMSA2Xpath = "]//datatable-body-row//div//datatable-body-cell//div//span[contains(.,'')])[9]";
    String toolTip1Xpath = "(//span[@class=\"ico-info popover-icon\"])[";
    String toolTip2Xpath = "]";
    String inwardCreditTransactionDetailsPageXpath = "/html/body/app-root/div/div/div";

    /**GLOBAL VARIABLES*/
    public String strWindowStateOnDB = null;
    public String strCBSResponse = null;
    public String strTranProcessingStts = "";
    public String retrySettlementFlag = "No";

    //TODO:: Testing steps verifications must stop here and mark the test as fail accordingly for below instances
    /** This is to prevent further going to eBox for rejected positive tests OR
     *  Non processed payments OR Unknown status received **/
    /** NOTE:: This must happen after all other verifications & artifacts are gathered, just before going eBox / next step **/
    //after database status & settlement have been executed, now stopTestAsFailed hence the below variable made global and used as condition at test level
    public String currentTranProcessingStatus="";

    ValidateInwardTransactionsOnDB validateInwardTransactionsOnDB = new ValidateInwardTransactionsOnDB(driver);
    CreateSingleInwardSettlementNotification createSingleInwardSettlementNotification = new CreateSingleInwardSettlementNotification(driver);

    APIUtil apiUtil = new APIUtil(driver);

    //***********************************Logic Methods**************************************************************************
    //******************************************************************************************************************

    public InwardCreditsPage verifyInwardPaymentUIStatus(String CurrentSystemReference, String TestCaseScenarioType, String WindowState,
                                            String PaymentRequestWindow, String CreditorAccountNumber_1, String ExpectedErrorCode)
    {//AccountNumberDigitsLength = 7 + Positive Test = Retry Branches [010 & 050]
        WindowState.toUpperCase();
        try{
            waitForPageToBeReady_2();
            click(By.id(inwardsCreditsTabId));

            //check the payment table is present
            if(isElementPresent(By.xpath(dataTableXpath)))
            {
                //if our payment is on the table list then pass test, else
                if(isElementPresent(By.xpath(currentTransactionRowBeforeXpath+CurrentSystemReference+currentTransactionRowAfterXpath)))
                {
                    //highlight & take screenshot of transaction
                    scrollElementIntoView(By.xpath(currentTransactionRowBeforeXpath+CurrentSystemReference+currentTransactionRowAfterXpath));
                    highLighterMethod(By.xpath(currentTransactionRowBeforeXpath+CurrentSystemReference+currentTransactionRowAfterXpath));

                    //screenshot of payment
                    screenshotLoggerMethod("Current Transaction","Current Transaction(s) on Inward Payment Table");
                    ExtentTestManager.getTest().log(Status.PASS, "Current Transaction(s) on Inward Payment Table");

                    //Verify payment statuses based on test type
                    String currentTranStatus = readText(By.xpath(currentTxnStatusBeforeXpath+CurrentSystemReference+currentTxnStatusAfterXpath));
                    currentTranProcessingStatus = readText(By.xpath(currentTxnProcessingStatus1Xpath+CurrentSystemReference+currentTxnProcessingStatus2Xpath));


                    //Determine if statuses are correct or not to pass/fail the test using WindowState and testType[level for negatives]
                    switch (WindowState.toUpperCase())
                    {
                        case "OPEN":
                        case "open":
                            if(currentTranStatus.equalsIgnoreCase("ACCEPTED")& TestCaseScenarioType.equalsIgnoreCase("Positive"))
                            {
                                ExtentTestManager.getTest().log(Status.PASS, "Current Payment Status is as Expected");


                                if(currentTranProcessingStatus.equalsIgnoreCase("SUCCESS"))
                                {
                                    ExtentTestManager.getTest().log(Status.PASS, "Payment processed successfully, See all transaction on Details");
                                }else if(currentTranProcessingStatus.equalsIgnoreCase("IN_PROGRESS"))
                                {
                                    //this is allowed based on which PaymentRequestWindow the payment is from
                                    if(PaymentRequestWindow.equalsIgnoreCase("Current")&& WindowState.equalsIgnoreCase("Open")){
                                        ExtentTestManager.getTest().log(Status.WARNING, "Payment still awaits for Settlement to complete processing");
                                    }else if(PaymentRequestWindow.equalsIgnoreCase("Previous")&& WindowState.equalsIgnoreCase("Open")){
                                        ExtentTestManager.getTest().log(Status.PASS,"Payment to receive Settlement & complete processing");
                                    }else {
                                        ExtentTestManager.getTest().log(Status.INFO,"Payment Request Window & Window State Don't Exist, Please Investigate");
                                    }
                                }else if(currentTranProcessingStatus.equalsIgnoreCase("FAILED"))
                                {
                                    ExtentTestManager.getTest().log(Status.FAIL, "Payment Failed Processing, See all transaction on Details");
                                }
                                else if(currentTranProcessingStatus.equalsIgnoreCase("NO_STATUS"))
                                {
                                    ExtentTestManager.getTest().log(Status.WARNING, "Payment NOT Processed, Check Window State");
                                }
                                else
                                {
                                    ExtentTestManager.getTest().log(Status.WARNING, "unknown processing status. please investigate on the system");
                                    System.out.println("unknown processing status. please investigate on the system");
                                }

                            }else if(currentTranStatus.equalsIgnoreCase("ACCEPTED")& TestCaseScenarioType.equalsIgnoreCase("Negative"))
                            {
                                ExtentTestManager.getTest().log(Status.PASS, "Current Payment Status is as Expected");


                                if(currentTranProcessingStatus.equalsIgnoreCase("FAILED"))
                                {
                                    ExtentTestManager.getTest().log(Status.PASS, "Payment processed successfully, See all transaction on Details");
                                }else if(currentTranProcessingStatus.equalsIgnoreCase("SUCCESS"))
                                {
                                    ExtentTestManager.getTest().log(Status.FAIL, "Payment processed successfully, This is Negative Test");
                                }
                                else if(currentTranProcessingStatus.equalsIgnoreCase("IN_PROGRESS"))
                                {
                                    ExtentTestManager.getTest().log(Status.FAIL, "Payment is waiting to complete processing, This is Negative Test");
                                }
                                else if(currentTranProcessingStatus.equalsIgnoreCase("NO_STATUS"))
                                {
                                    ExtentTestManager.getTest().log(Status.WARNING, "Payment NOT Processed, Check Window State");
                                }
                                else
                                {
                                    ExtentTestManager.getTest().log(Status.WARNING, "unknown processing status. please investigate on the system");
                                    System.out.println("unknown processing status. please investigate on the system");
                                }

                            }else if(currentTranStatus.equalsIgnoreCase("REJECTED")& TestCaseScenarioType.equalsIgnoreCase("Negative"))
                            {
                                ExtentTestManager.getTest().log(Status.PASS, "Current Payment Status is as Expected");

                                if(currentTranProcessingStatus.equalsIgnoreCase("FAILED"))
                                {
                                    ExtentTestManager.getTest().log(Status.PASS, "Payment processed successfully, See all transaction on Details");

                                }else if(currentTranProcessingStatus.equalsIgnoreCase("SUCCESS"))
                                {
                                    ExtentTestManager.getTest().log(Status.FAIL, "Payment processed successfully, This is Negative Test");
                                }
                                else if(currentTranProcessingStatus.equalsIgnoreCase("IN_PROGRESS"))
                                {
                                    ExtentTestManager.getTest().log(Status.FAIL, "Payment is waiting to complete processing, This is Negative Test");
                                }
                                else if(currentTranProcessingStatus.equalsIgnoreCase("NO_STATUS"))
                                {
                                    ExtentTestManager.getTest().log(Status.WARNING, "Payment NOT Processed, Check Window State");
                                }
                                else
                                {
                                    ExtentTestManager.getTest().log(Status.WARNING, "unknown processing status. please investigate on the system");
                                    System.out.println("unknown processing status. please investigate on the system");
                                }

                            }else if(currentTranStatus.equalsIgnoreCase("REJECTED")& TestCaseScenarioType.equalsIgnoreCase("Positive"))
                            {
                                ExtentTestManager.getTest().log(Status.FAIL, "Current Payment Status is NOT as Expected, See all transaction on Details");

                                if(currentTranProcessingStatus.equalsIgnoreCase("FAILED"))
                                {
                                    ExtentTestManager.getTest().log(Status.FAIL, "Payment processed After Rejected, This is a Positive Test");
                                }else if(currentTranProcessingStatus.equalsIgnoreCase("SUCCESS"))
                                {
                                    ExtentTestManager.getTest().log(Status.FAIL, "Payment processed After Rejected, This is a Positive Test");
                                }
                                else if(currentTranProcessingStatus.equalsIgnoreCase("IN_PROGRESS"))
                                {
                                    ExtentTestManager.getTest().log(Status.FAIL, "Payment processed After Rejected, This is a Positive Test");
                                }
                                else if(currentTranProcessingStatus.equalsIgnoreCase("NO_STATUS"))
                                {
                                    ExtentTestManager.getTest().log(Status.WARNING, "Payment Rejected & NOT Processed Check Window State, This is a Positive Test");
                                }
                                else
                                {
                                    ExtentTestManager.getTest().log(Status.WARNING, "unknown processing status. please investigate on the system");
                                    System.out.println("unknown processing status. please investigate on the system");
                                }
                            }

                            else
                            {
                                ExtentTestManager.getTest().log(Status.FAIL, "NOT Expected Payment Status in Current Window State, Please Investigate");
                            }
                            break;

                        case "CLOSE":
                        case "close":
                            if(currentTranStatus.equalsIgnoreCase("RECEIVED")& TestCaseScenarioType.equalsIgnoreCase("Positive"))
                            {
                                ExtentTestManager.getTest().log(Status.PASS, "Current Payment Status is as Expected");

                                if(currentTranProcessingStatus.equalsIgnoreCase("NO_STATUS"))
                                {
                                    ExtentTestManager.getTest().log(Status.PASS, "Payment to Process when Window Opens");
                                }else
                                {
                                    ExtentTestManager.getTest().log(Status.FAIL, "Payment is Processing on Closed Window");
                                }
                            }
                            if(currentTranStatus.equalsIgnoreCase("RECEIVED")& TestCaseScenarioType.equalsIgnoreCase("Negative"))
                            {
                                ExtentTestManager.getTest().log(Status.PASS, "Current Payment Status is as Expected");

                                if(currentTranProcessingStatus.equalsIgnoreCase("NO_STATUS"))
                                {
                                    ExtentTestManager.getTest().log(Status.PASS, "Payment to Process when Window Opens");
                                }else
                                {
                                    ExtentTestManager.getTest().log(Status.FAIL, "Payment is Processing on Closed Window");
                                }
                            }
                            break;
                        default:
                            System.out.println("Unknown Window State Provided");
                    }


                    //click Details button to verify all transactions
                    click(By.xpath(currentTxnDetailsButton1Xpath+CurrentSystemReference+currentTxnDetailsButton2Xpath));
                    //Thread.sleep(2000);

                    //Check if there are transactions to be looped for processing status
                    if(currentTranProcessingStatus.equalsIgnoreCase("NO_STATUS"))
                    {
                        System.out.println("No Transactions to be Processed");
                        //screenshot of inward transaction details page
                        screenshotLoggerMethod("No Transaction Page", "No Transaction(s) Page");
                        ExtentTestManager.getTest().log(Status.PASS, "No Transaction(s) Page");
                    }else {

                        //Find each transaction processing status
                        //screenshot of payment with all/each transaction
                        screenshotLoggerMethod("Processing Transaction Status", "Processing Status of Each Transaction(s)");
                        ExtentTestManager.getTest().log(Status.PASS, "Processing Status of Each Transaction(s)");

                        scrollDown();

                        int i = 1;
                        int totalTransactions = driver.findElements(By.xpath(totalTransactionRowsXpath)).size();
                        String statusOfEachTransaction = "";

                        do {
                            statusOfEachTransaction = readText(By.xpath(transactionMSA1Xpath+i+transactionMSA2Xpath));
                            highLighterMethod(By.xpath(dynamicRowPosition1Xpath + i + dynamicRowPosition2Xpath));

                            if (statusOfEachTransaction.equalsIgnoreCase("SUCCESSFUL")) {
                                //screenshot of payment with each transaction
                                screenshotLoggerMethod("Transaction Status No. " + i, "Processing Status for Transaction No. " + i);
                                ExtentTestManager.getTest().log(Status.PASS, "Processing Status for Transaction No. " + i);
                                System.out.println("Nothing to hover on the txn is successful");

                            }else if(statusOfEachTransaction.equalsIgnoreCase("NEW")){
                                //TODO:: cater for a "NEW" status, this happens sometimes when processing is not happening as expected. this is not a "NEW" of payments received out-of-window - DONE
                                //TODO:: To ReTry the Settlement-Notification for this to be processed. maybe there's a delay - DONE

                                System.out.println("ReTrying the Settlement Notification....");
                                retrySettlementFlag = "Yes";
                                ExtentTestManager.getTest().info(MarkupHelper.createLabel("ReTrying the Settlement Notification....",ExtentColor.WHITE));
                                screenshotLoggerMethod("NEW status","NEW Status & Retrying Settlement");

                            }
                            else if (statusOfEachTransaction.equalsIgnoreCase("FAILED")) { //TODO:: Cater for a first failure on a scenarios with 7 digits account number for positive test to be pass - DONE

                                String accountNo = "123456789";
                                int AccountNumberDigitsLength=CreditorAccountNumber_1.length();
                                if(AccountNumberDigitsLength==7&&TestCaseScenarioType.equalsIgnoreCase("Positive")&&totalTransactions>1)
                                {
                                    scrollDown();

                                    //String statusOfEachTransaction2 ="";
                                    do {
                                        //statusOfEachTransaction2 = readText(By.xpath(transactionMSA1Xpath+i+transactionMSA2Xpath));
                                        highLighterMethod(By.xpath(dynamicRowPosition1Xpath + i + dynamicRowPosition2Xpath));

                                        if(statusOfEachTransaction.equalsIgnoreCase("FAILED")){
                                            //Mouse over the tooltip to get the error code
                                            mouseHoverMethod((toolTip1Xpath + i + toolTip2Xpath));
                                            //screenshot of payment with each transaction
                                            screenshotLoggerMethod("Failure Reason for Transaction No. " + i, "Failure Reason for Transaction No. " + i);

                                            //Get the error code
                                            String errorCode = driver.findElement(By.xpath("//div[@class=\"popover-content\"]")).getText();
                                            String strErrorCode = errorCode;

                                            //Now assert the error code for failure to be what is expected
                                            if (strErrorCode.contains(ExpectedErrorCode)) {
                                                //ExtentTestManager.getTest().log(Status.INFO, "Processing attempt FAILED due to BranchID, To ReTry with another Branch ID");
                                                ExtentTestManager.getTest().info(MarkupHelper.createLabel("Processing Attempt "+i+" FAILED. ReTrying with another Branch ID...",ExtentColor.WHITE));

                                                click(By.xpath("/html/body/app-root/div/div/div/inward-credit-details/div/div[3]/div/div/div[2]"));
                                            }else{
                                                ExtentTestManager.getTest().log(Status.FAIL, "Incorrect Error Code for Payment Failure, Please Investigate");
                                            }
                                        }
                                        else if(statusOfEachTransaction.equalsIgnoreCase("NEW")){
                                            //TODO:: To ReTry the Settlement-Notification for this to be processed. maybe there's a delay - DONE
                                            System.out.println("ReTrying the Settlement Notification....");
                                            retrySettlementFlag = "Yes";
                                        }else{
                                            //screenshot of payment with each transaction
                                            screenshotLoggerMethod("Transaction Status No. " + i, "Processing Status for Transaction No. " + i);
                                            ExtentTestManager.getTest().log(Status.PASS, "Processing Status for Transaction No. " + i);
                                            System.out.println("Nothing to hover on the txn is successful");
                                        }

                                        i++;
                                    }while (i <= totalTransactions && statusOfEachTransaction.equalsIgnoreCase("Failed"));
                                }
                                else{
                                    //Mouse over the tooltip to get the error code
                                    mouseHoverMethod((toolTip1Xpath + i + toolTip2Xpath));
                                    //screenshot of payment with each transaction
                                    screenshotLoggerMethod("Failure Reason for Transaction No. " + i, "Failure Reason for Transaction No. " + i);

                                    //TODO:: False Pass Fix for AC03 Scenarios by AB07/MS03 - Done
                                    //TODO:: Assert the failure to AC03, to avoid false pass when getting AB07/MS03 - Done

                                    //Get the error code
                                    String errorCode = driver.findElement(By.xpath("//div[@class=\"popover-content\"]")).getText();
                                    String strErrorCode = errorCode;

                                    //Now assert the error code for failure to be what is expected
                                    if(strErrorCode.contains(ExpectedErrorCode))
                                    {
                                        ExtentTestManager.getTest().log(Status.PASS, "Payment processed successfully, See all transaction on Details");}
                                    else {
                                        ExtentTestManager.getTest().log(Status.FAIL,"Incorrect Error Code for Payment Failure, Please Investigate");}
                                    ExtentTestManager.getTest().log(Status.PASS, "Failure Reason for Transaction No. " + i);
                                }
                            }
                            else {
                                System.out.println("Unknown Status on Transaction");
                            }

                            i++;
                        } while (i <= totalTransactions);
                    }
                }else
                {
                    System.out.println("Current Payment System Reference Not Visible on the Inward Payment List");
                    ExtentTestManager.getTest().log(Status.FAIL, "Current Payment System Reference Not Visible on the Inward Payment List. Please Investigate");
                }

            }else
            {
                System.out.println("Inward payments table is empty");
                ExtentTestManager.getTest().log(Status.FAIL, "Inward payments table is empty, Please Investigate");
            }

        }catch (Exception e)
        {
        }
        return this;}




    public InwardCreditsPage completeInwardPayment(String WindowState, String BankCountry,String TestCaseScenarioType, String PaymentRequestWindow, String SystemReference, String SettlementNotificationFileName, String fileNameCBSResponse) throws Throwable
    {
        switch (WindowState.toUpperCase())
        {
            case "OPEN":
            case "open":
                strWindowStateOnDB = validateInwardTransactionsOnDB.validateWindowStatus(WindowState, PaymentRequestWindow, SystemReference);
                if(strWindowStateOnDB.equalsIgnoreCase("OPEN") && PaymentRequestWindow.equalsIgnoreCase("Current"))
                {
                    System.out.println("Pass:: Window is indeed opened, Now Check CBS Response, Do Settlement & Verify UI");
                    ExtentTestManager.getTest().log(Status.PASS, "Pass:: Window is indeed opened, Now Check CBS Response, Do Settlement & Verify UI");

                    //use the flag [negative or positive] to determine whether to check cbs response {start}
                    if(TestCaseScenarioType.equalsIgnoreCase("Positive"))
                    {
                        //TODO:: Add condition to cater for payment received while window was closed & do followings with SystemRef,PaymentRequestWindow being input parameter:
                        /**
                         * 1. Get Inward request
                         * 2. Get Response [exist below]
                         * 3. Validate Status [exist below]
                         * 4. Do Settlement [exist below]
                         * 5. Validate Status again [exist below]
                         * **/

                        strCBSResponse = validateInwardTransactionsOnDB.validateCBSResponse(PaymentRequestWindow, SystemReference, fileNameCBSResponse);
                        if(strCBSResponse!=null)
                        {
                            System.out.println("Pass:: CBS Responded, Now Do Settlement");
                            ExtentTestManager.getTest().log(Status.PASS, "Pass:: CBS Responded, Now Do Settlement");

                            strTranProcessingStts = validateInwardTransactionsOnDB.validateTransactionProcessingStatus(PaymentRequestWindow, SystemReference);

                            createSingleInwardSettlementNotification.createSettlementNotificationSingleTxn(SettlementNotificationFileName);
                            String inwardSuccessNotification = createSingleInwardSettlementNotification.strSettlementNotificationForSingleTxs;
                            apiUtil.genericDropPayload(inwardSuccessNotification,BankCountry);

                            strTranProcessingStts = validateInwardTransactionsOnDB.validateTransactionProcessingStatus(PaymentRequestWindow, SystemReference);

                            //Todo:: Assert the settlement status and mark the test accordingly
                            /**
                             * 1. SETTLEMENT_FAILED = Fails the test
                             * 2. SETTLEMENT_SUCCESSFUL = Pass the test
                             * 3. PENDING = Info the test  i.e window is closed
                             * 4. ACCEPTED_FOR_CLEARING & SETTLEMENT_RECEIVED = We must ReTry after some wait before mark fail. they happens before success
                             * **/

                            try{
                                int WaitTime=2;
                                int i=0;
                                do{
                                    if(strTranProcessingStts.equalsIgnoreCase("PENDING")){
                                        ExtentTestManager.getTest().pass(MarkupHelper.createLabel("Status:: Settlement Status is PENDING, To Complete Next Window",ExtentColor.WHITE));
                                    }else if(strTranProcessingStts.equalsIgnoreCase("SETTLEMENT_SUCCESSFUL")){
                                        ExtentTestManager.getTest().pass(MarkupHelper.createLabel("Status:: Settlement Status is SUCCESS, Payment Completed",ExtentColor.GREEN));
                                        //strTranProcessingStts = validateInwardTransactionsOnDB.validateTransactionProcessingStatus(PaymentRequestWindow, SystemReference);

                                        /**INFO*/
                                        ExtentTestManager.getTest().log(Status.PASS, "Status=PASS: Current Transaction Processing Status below");
                                        ExtentTestManager.codeLogsXML(validateInwardTransactionsOnDB.strTransactionStatusValue);

                                    }else if(strTranProcessingStts.equalsIgnoreCase("SETTLEMENT_FAILED")){
                                        ExtentTestManager.getTest().pass(MarkupHelper.createLabel("Status:: Settlement Status is FAILED, Please Investigate",ExtentColor.RED));
                                    }else if(strTranProcessingStts.equalsIgnoreCase("ACCEPTED_FOR_CLEARING")){
                                        ExtentTestManager.getTest().info(MarkupHelper.createLabel("Status:: Settlement Status, "+strTranProcessingStts+" To ReCheck in 3 Seconds for Success Status",ExtentColor.WHITE));
                                    }else if(strTranProcessingStts.equalsIgnoreCase("SETTLEMENT_RECEIVED")){
                                        ExtentTestManager.getTest().info(MarkupHelper.createLabel("Status:: Settlement Status, "+strTranProcessingStts+" To ReCheck in 3 Seconds for Success Status",ExtentColor.WHITE));
                                    }else {
                                        //ExtentTestManager.getTest().log(Status.WARNING,"Status:: Unknown Settlement Status Found, Please Investigate");
                                        ExtentTestManager.getTest().warning(MarkupHelper.createLabel("Status:: Unknown Settlement Status Found, Please Investigate",ExtentColor.AMBER));
                                    }
                                    Thread.sleep(5000);
                                    i++;
                                }while(i<=WaitTime);

                            }catch (Exception e){
                                //ExtentTestManager.getTest().log(Status.FATAL,"Status:: FATAL FAILURE on SETTLEMENT_STATUS DB QUERY, Please Investigate");
                                ExtentTestManager.getTest().warning(MarkupHelper.createLabel("Status:: Unknown Settlement Status Found, Please Investigate",ExtentColor.AMBER));
                            }

                        }else
                        {
                            System.out.println("Fail:: No CBS Response Found. Please investigate");
                            ExtentTestManager.getTest().log(Status.FAIL, "Fail:: No CBS Response Found. Please investigate");
                        }

                    }else //use the flag [negative or positive] to determine whether to check cbs response {end}
                    {
                        strCBSResponse = validateInwardTransactionsOnDB.validateCBSResponse(PaymentRequestWindow, SystemReference, fileNameCBSResponse);
                        System.out.println("Pass:: CBS Response Found, No Settlement Needed");
                        ExtentTestManager.getTest().log(Status.PASS, "Pass:: CBS Response Found, No Settlement Needed");
                    }
                }

                else if(strWindowStateOnDB.equalsIgnoreCase("OPEN") && PaymentRequestWindow.equalsIgnoreCase("Previous"))
                {
                    System.out.println("Pass:: Window is indeed opened, Now Check CBS Response, Do Settlement & Verify UI");
                    ExtentTestManager.getTest().log(Status.PASS, "Pass:: Window is indeed opened, Now Check CBS Response, Do Settlement & Verify UI");

                    //use the flag [negative or positive] to determine whether to check cbs response {start}
                    if(TestCaseScenarioType.equalsIgnoreCase("Positive"))
                    {
                        //TODO:: Add condition to cater for payment received while window was closed & do followings with SystemRef,PaymentRequestWindow being input parameter:
                        /**
                         * 1. Get Inward request
                         * 2. Get Response [exist below]
                         * 3. Validate Status [exist below]
                         * 4. Do Settlement [exist below]
                         * 5. Validate Status again [exist below]
                         * **/

                        strCBSResponse = validateInwardTransactionsOnDB.validateCBSResponse(PaymentRequestWindow, SystemReference, fileNameCBSResponse);
                        if(strCBSResponse!=null)
                        {
                            System.out.println("Pass:: CBS Responded, Now Do Settlement");
                            ExtentTestManager.getTest().log(Status.PASS, "Pass:: CBS Responded, Now Do Settlement");

                            strTranProcessingStts = validateInwardTransactionsOnDB.validateTransactionProcessingStatus(PaymentRequestWindow, SystemReference);

                            createSingleInwardSettlementNotification.createSettlementNotificationSingleTxn(SettlementNotificationFileName);
                            String inwardSuccessNotification = createSingleInwardSettlementNotification.strSettlementNotificationForSingleTxs;
                            apiUtil.genericDropPayload(inwardSuccessNotification,BankCountry);

                            strTranProcessingStts = validateInwardTransactionsOnDB.validateTransactionProcessingStatus(PaymentRequestWindow, SystemReference);
                        }else
                        {
                            System.out.println("Fail:: No CBS Response Found. Please investigate");
                            ExtentTestManager.getTest().log(Status.FAIL, "Fail:: No CBS Response Found. Please investigate");
                        }

                    }else //use the flag [negative or positive] to determine whether to check cbs response {end}
                    {
                        strCBSResponse = validateInwardTransactionsOnDB.validateCBSResponse(PaymentRequestWindow, SystemReference, fileNameCBSResponse);
                        System.out.println("Pass:: CBS Response Found, No Settlement Needed");
                        ExtentTestManager.getTest().log(Status.PASS, "Pass:: CBS Response Found, No Settlement Needed");
                    }
                }

                else
                {
                    System.out.println("Fail:: Window is not found OPEN as expected, Please investigate");
                    ExtentTestManager.getTest().log(Status.FAIL, "Fail:: Window is not found OPEN as expected, Please investigate");
                }
                break;

            case "CLOSED":
            case "closed":
                strWindowStateOnDB = validateInwardTransactionsOnDB.validateWindowStatus(WindowState, PaymentRequestWindow, SystemReference);
                if(strWindowStateOnDB.equalsIgnoreCase("CLOSED"))
                {
                    System.out.println("window is closed, Keep system reference to complete processing on next window & Verify UI ");
                    strTranProcessingStts = validateInwardTransactionsOnDB.validateTransactionProcessingStatus(PaymentRequestWindow, SystemReference);
                }

                break;
            default:
                System.out.println("NonExisting Window State");
        }
        return this;}
}
