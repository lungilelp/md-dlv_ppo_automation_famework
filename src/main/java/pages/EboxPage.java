package pages;

import base.BasePage;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import config.Settings;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import utils.extentreports.ExtentTestManager;
import utils.logs.Log;

import java.text.DecimalFormat;

/**
 * @author Jonathan Mbuyi Tshitenda [ ABJT439 ]
 * @date created 9/10/2019
 * @package pages
 */
public class EboxPage extends BasePage {

    public EboxPage(WebDriver driver) {


        super(driver);
        Log.info("The current working class: "+this.getClass().getName());
    }
    //***********************************WEB ELEMENTS*******************************************************************
    //******************************************************************************************************************
    String eboxUsernameFieldName = "username";
    String eboxPasswordFieldName = "j_password";
    String eboxDomainDropdownName = "domain";
    String eboxBranchIdName = "cbBranchNumber";
    String eboxAccountNumberName = "cbAccountNumber";
    String eboxSearchStatement = "cbChangeCustomer";
    String eboxStatementEntriesTableId = "StatementForm";
    String eboxStatementEntriesTableColumnsXpath = "//*[@id=\"StatementForm\"]/div[4]/div/table/tbody/tr/th";
    String eboxStatementEntriesTableRowsTagName = "tr";


    String eboxStatementEntriesTableRowsXpath = "//*[@id=\"StatementForm\"]/div[4]/div/table/tbody/tr";
    String eboxStatementDeliveryDetailsXpath = "//*[@id=\"StatementForm\"]/div[2]/table/tbody/tr[1]";
    String eboxSwiftStatementTableXpath = "//*[@id=\"StatementForm\"]/div[4]";
    String eboxSwiftStatementTableHeaderXpath = "//*[@id=\"StatementForm\"]/h2[3]";
    String eboxStatementTableLastRow = "//*[@id=\"StatementForm\"]/div[4]/div/table/tbody/tr[position()=last()]";
    String eboxLoginButtonXpath = "/html/body/div[2]/form/div[2]/input";
    String eboxLogoXpath = "/html/body/div[2]/div[1]";
    String statementViewXpath = "//a[contains(text(),'Statement View')]";
    String statementViewXpath_OLD = "/html/body/div[2]/div[39]/a";
    String clearButtonID = "cbClearCustomer";

    //For pre table with customer addresses
    String tableXpath = "/html/body/div[3]/div[2]";
    String firstTableRowXpath = "//*[@id=\"customerList\"]/tbody/tr[2]";
    String secondTableRowXpath = "//*[@id=\"customerList\"]/tbody/tr[3]";

    //For amount location to highlight in accounting check up
    //String AmountValueXpath = "(.//div[contains(text(),'" + strWorkflowReferenceUniqueTxt + "')]/parent::td//following-sibling::td[";
    String rowValueXpath = "])[position()=";
    String debtorPath = "2])[position()=";
    int debtorPath2 = 2;
    String creditorPath = "3])[position()=";

//    String debtorAmountValuesXpath = "(.//div[contains(text(),'" + strWorkflowReferenceUniqueTxt + "')]/parent::td//following-sibling::td[2])[position()=";
//    String creditorAmountValuesXpath = "(.//div[contains(text(),'" + strWorkflowReferenceUniqueTxt + "')]/parent::td//following-sibling::td[3])[position()=";


    @FindBy(how = How.LINK_TEXT, using = "Statement View")
    public static WebElement statementViewLink;

    @FindBy(how = How.CLASS_NAME, using = "menu-title")
    public static WebElement eboxPage;
    /*****************Dynamic Xpath Elements**********************************/
    String before_StatementEntriesTableDescriptionColumnXpath = "//*[@id=\"StatementForm\"]/div[4]/div/table/tbody/tr[";
    String After_StatementEntriesTableDescriptionColumnXpath = "]/td[3]/div[3]";

    String before_StatementEntriesTableDebitAmountColumnXpath = "//*[@id=\"StatementForm\"]/div[4]/div/table/tbody/tr[";
    String After_StatementEntriesTableDebitAmountColumnXpath = "]/td[5]";

    String before_StatementEntriesTableCreditAmountColumnXpath = "//*[@id=\"StatementForm\"]/div[4]/div/table/tbody/tr[";
    String After_StatementEntriesTableCreditAmountColumnXpath = "]/td[6]";
    /***************************************************************************/
    DecimalFormat decimalFormat = new DecimalFormat("##.00");//Make new decimal format

    /**
     * Declare variable to hold all total values to be used globally
     * The Control-Sum is the total amount that is involved in the transaction which is debited from the debtor and credited to the creditor(s)
     */

    //To Hold the overall test status
    public String strEboxTestValidations = null;
    public String strDebitAccountTransactionsAmountValidations = null;
    public String strDebitAccountTransactionFeesValidations = null;
    public String strSuspenseCreditAccountTransactionsAmountValidations = null;
    public String strSuspenseCreditAccountTransactionFeesValidations = null;
    public String strSuspenseDebitAccountTransactionsAmountValidations = null;
    public String strSuspenseDebitAccountTransactionFeesValidations = null;
    public String strCreditAccountTransactionsAmountValidations = null;
    public String strCreditAccountTransactionFeesValidations = null;
    public String strPNLCreditAccountTransactionFeesValidations = null;
    public String strDifferentChargesDebitAccountTransactionFeesValidations = null;
    public String strClearingAccountCreditAccountTransactionsAmountValidations = null;


    //For the control sum
    public double doubleDecimalExpectedControlSum = 0;


    //For Fees
    public double doubleExpectedFeesAmountApplied = 0;
    public String DebtorPurposeOfPayment = null;
    public String strFeeLineItem = null;


    //For Debtor Account
    public double doubleDebitAccountActualAmount = 0;
    public double doubleDebitAccountActualTotal = 0;
    public double doubleDebitAccountActualFeesAmount = 0;
    public String strDebitAccountActualFeesDescription = null;
    public String strDebitAccountActualFeesTimeStamp = null;
    String strDebitAccountAmountAssertion = null;
    String strDebitAccountFeesDescriptionAssertion = null;
    String strDebitAccountFeesAmountAssertion = null;

    //For the Suspense Creditor Account
    public double doubleSuspenseCreditAccountActualAmount = 0;
    public double doubleSuspenseCreditAccountActualTotal = 0;
    public double doubleSuspenseCreditAccountActualFeesAmount = 0;
    public String strSuspenseCreditAccountActualFeesDescription = null;
    public String strSuspenseCreditAccountActualFeesTimeStamp = null;
    String strSuspenseCreditAccountTransactionAmountAssertion = null;
    String strSuspenseCreditAccountFeesAmountAssertion = null;
    String strSuspenseCreditAccountFeesDescriptionAssertoin = null;

    //For the Suspense Debtor Account
    public double doubleSuspenseDebitAccountActualAmount = 0;
    public double doubleSuspenseDebitAccountActualTotal = 0;
    public double doubleSuspenseDebitAccountActualFeesAmount = 0;
    public String strSuspenseDebitAccountActualFeesDescription = null;
    public String strSuspenseDebitAccountActualFeesTimeStamp = null;
    String strSuspenseDebitAccountTransactionAmountAssertion = null;
    String strSuspenseDebitAccountFeesDescriptionAssertion = null;
    String strSuspenseDebitAccountFeesAmountAssertion = null;


    //For the Creditor Account
    public double doubleCreditAccountActualAmount = 0;
    public double doubleCreditAccountActualTotal = 0;
    public String strCreditAccountActualFeesDescription = null;
    String strCreditAccountTransactionsAmountAssertion = null;

    //For the PNL Account
    public double doublePNLCreditAccountFeesAmount = 0;
    public String strPNLCreditAccountActualFeesTimeStamp = null;
    public String strPNLCreditAccountActualFeesDescription = null;
    public String strPNLCreditAccountActualOriginatorBranchID = null;
    public String strPNLCreditAccountActualOriginatorAccountNumber = null;
    String strPNLCreditAccountFeesDescriptionAssertion = null;
    String strPNLCreditAccountFeesTimeStampAssertion = null;
    String strPNLCreditAccountActualOriginatorAccountNumberAssertion = null;
    String strPNLCreditAccountFeesAmountAssertion = null;

    //For the Different Charges Account
    public double doubleDifferentChargesDebitAccountFeesAmount = 0;
    public String strDifferentChargesDebitAccountActualFeesDescription = null;
    public String strDifferentChargesDebitAccountFeesActualFeesTimeStamp = null;
    String strDifferentChargesDebitAccountFeesDescriptionAssertion = null;
    String strDifferentChargesDebitAccountFeesTimeStampAssertion = null;
    String strDifferentChargesDebitAccountFeesAmountAssertion = null;


    //For the Clearing Account
    public double doubleClearingAccountCreditAccountActualAmount = 0;
    public double doubleClearingAccountCreditAccountActualAmountTotal = 0;
    String strClearingAccountCreditAccountTransactionsAmountAssertion = null;


    //Count the number of transaction
    public int transactionCount = 0;
    public Markup label = MarkupHelper.createLabel("transactions", ExtentColor.BLUE);

    //For Inwards Narration from previous transaction [Unique]
    public String strPaymentRequestWindow="";
    public String strEboxNarrative="";

    public String EboxUsername = null;
    String EboxAgentPassword = "";
    String EboxDomain = "";
    //******************************************************************************************************************


    //***********************************STEPS**************************************************************************
    //******************************************************************************************************************
    //Go to Ebox Page

    /**
     * Open Ebox Url
     */
    public void goToEbox(String BankCountry) {
        driver.get(Settings.EboxURL);
    }

    public void selectPreStatement() {
        boolean preTableAvailable = isElementDisplayed(tableXpath);
        if (preTableAvailable) {
            try {
                if (driver.findElement(By.xpath(secondTableRowXpath)).isDisplayed()) {
                    click(By.xpath(secondTableRowXpath));
                }
            } catch ( Exception e) {
                ExtentTestManager.getTest().info(MarkupHelper.createLabel("Element not visible in a pre statement table", ExtentColor.WHITE));
            }
        } else {
            ExtentTestManager.getTest().info(MarkupHelper.createLabel("Pre statement table is not present for this account", ExtentColor.WHITE));
        }
    }

    /**
     * To Run this Test as stand-alone ,Unique reference must be define in the excel sheet
     */
    public String standAloneExecution(String DebtorOriginationReference, String DebtorWorkflowReference) {
        String strWorkflowReferenceUniqueTxt = null;
        //Check If the reference/narrative is given to run the scrip as standalone
        if (DebtorOriginationReference.equalsIgnoreCase("YES")) {
            System.out.println("Script is running as part of E2E tests");
        } else {
            System.out.println("Script is running as a standalone tests");
            return strWorkflowReferenceUniqueTxt = DebtorWorkflowReference;
        }
        return strWorkflowReferenceUniqueTxt;
    }

    /**
     * Match the Purpose of Payment on PPO with that of Ebox
     */
    public String setPurposeOfPayment(String strDebtorPurposeOfPaymentCheck) {

        //strGlobalDebtorPurposeOfPayment = strDataStoreDebtorPurposeOfPayment;

        //TODO ADD MORE PURPOSE OF PAYMENTS
        //Match Wages
        if (strDebtorPurposeOfPaymentCheck.equalsIgnoreCase("Wages")) {
            strDebtorPurposeOfPaymentCheck = "Wage Payment fee";
        }

        return strDebtorPurposeOfPaymentCheck;
    }

    /**
     * Check that the fee line-item exist and it matches the expected Fee Description
     */
    public void feeLineItemValidation() throws Exception {


        if (strFeeLineItem.equalsIgnoreCase(null)) {
            ExtentTestManager.getTest().fail(MarkupHelper.createLabel("Fees were not found on the Ebox Statement ", ExtentColor.RED));
            screenshotLoggerMethod("DebitAccountActualFeesDescription", "Debit Account Actual Fees Description");
            stopCurrentTestSetResultsToFailedAndContinueWithNextTest(); //Stop the test at this point

        } else if (strFeeLineItem.equalsIgnoreCase(DebtorPurposeOfPayment)) {
            strFeeLineItem = "Fees Line-Item Exist on the statement";
            ExtentTestManager.getTest().pass(MarkupHelper.createLabel("Fees Line-Item Exist on the statement", ExtentColor.BLUE));
        }
    }

    /**
     * This helps us avoid changing the Dynamic xpath value whenever there is an unexpected table on the page
     */
    public int countTableDivs() {
        /**Get the number of table preceding the main statement table
         * This helps us avoid changing the Dynamic xpath value whenever there is an unexpected table on the page*/

        int intTableCount = driver.findElements(By.xpath(".//*[@id='StatementForm']/div")).size();
        return intTableCount;
    }

    /**
     * Login to Ebox
     */
    public void loginToEbox(String EboxUsername, String EboxAgentPassword, String EboxDomain) throws Exception {
        try {
            waitForPageToBeReady_2();
            SetScriptTimeout();
            //Enter Username(Username)
            writeText(By.name(eboxUsernameFieldName), EboxUsername);
            //Enter Password
            writeText(By.name(eboxPasswordFieldName), EboxAgentPassword);
            //Select Domain
            //Select Domain
            if(isElementPresent(By.name(eboxDomainDropdownName))){
                click(By.name(eboxDomainDropdownName));
                selectdropdownItemByVisibleText(By.name(eboxDomainDropdownName), EboxDomain);
            }
            //click(By.name(eboxDomainDropdownName));
            //selectdropdownItemByVisibleText(By.name(eboxDomainDropdownName), EboxDomain);

            //Click the login button
            click(By.xpath(eboxLoginButtonXpath));
            SetScriptTimeout();

            /**ASSERTIONS::Assert that all expected elements are visible on the page**/
            boolean isEboxLogoDisplayed = isElementDisplayed(eboxLogoXpath);
            if (isEboxLogoDisplayed) {

                ExtentTestManager.getTest().log(Status.PASS, "Status=PASS: Logged in Ebox application");
                //Take Screenshot
                screenshotLoggerMethod("loginToEbox", "Login to Ebox");
            } else {
                ExtentTestManager.getTest().log(Status.FAIL, "Status=FAIL: Ebox login not successful");
                stopCurrentTestSetResultsToFailedAndContinueWithNextTest();
            }

        } catch (Exception e) {
            screenshotLoggerMethod("loginToEbox", "Login to Ebox Failure");
            ExtentTestManager.getTest().log(Status.FAIL, "Status=FATAL: Login not successful" + e.getMessage());
        } catch (AssertionError e) {
            screenshotLoggerMethod("loginToEbox", "Login to Ebox Failure");
            ExtentTestManager.getTest().log(Status.FAIL, "Status=FATAL: Login not successful" + e.getMessage());
            stopCurrentTestSetResultsToFailedAndContinueWithNextTest();
        }
    }

    public void reLoginAndOpenStatement(String EboxUsername, String EboxAgentPassword, String EboxDomain) {
        //TODO Msa.
        // Every time before enter the branch id & account number on ebox to view a statement, first check
        // if you are still logged on to eBox, if not log in
        try {
            if (ElementIsPresent(By.name(eboxUsernameFieldName))) {
                loginToEbox(EboxUsername, EboxAgentPassword, EboxDomain);

                //Go to statement view
                click(By.xpath(statementViewXpath));
            }
        } catch (Exception e) {
        }
    }

    public void openAccountStatement(String BranchId, String AccountNumber) {

            waitForPageToBeReady_2();


            //Go to statement view
            click(By.xpath(statementViewXpath));

            //Enter Branch ID
            writeText(By.name(eboxBranchIdName), BranchId);

            //Enter Account Number
            writeText(By.name(eboxAccountNumberName), AccountNumber);

            //Click Search
            click(By.name(eboxSearchStatement));

            //For statement with pre statement
            selectPreStatement();

    }

    /**
     * Check Debtor's Debited Amount
     */
    public void checkDebtorDebitedAmount(String EboxUsername, String EboxAgentPassword, String EboxDomain, String DebtorBranchID, String DebtorAccountNumber, String DebtorControlSum, String DebtorWaiveChargesOption, String FeesApplied, String DebtorPurposeOfPayment, String DebtorOriginationReference, String DifferentChargeAccount, String DifferentChargeAccountBranchId, String DifferentChargeAccountNumber, String DebtorWorkflowReference,String strWorkflowReferenceUniqueTxt) throws Exception {
        try {
            //For Standalone Execution
            strWorkflowReferenceUniqueTxt = standAloneExecution(DebtorOriginationReference, DebtorWorkflowReference);

            //TODO Msa... The addition of reLogin on eBox
            reLoginAndOpenStatement(EboxUsername, EboxAgentPassword, EboxDomain);

            //Open the Account statement
            openAccountStatement(DebtorBranchID, DebtorAccountNumber);

            //Scroll to view element
            scrollElementIntoView(By.xpath(eboxStatementTableLastRow));
            //highLighterMethod(By.xpath(eboxStatementTableLastRow));

            /**
             * Get debtor Amount shown on table
             * Search the Ebox table to find the unique transaction reference string.
             * Use Xpath Axes with Relative-Xpath using contains,sibling,child,descendant and Ancestor**/
            System.out.println("======================Ebox Table Debit Validation=======================================");
            //Get the number of table/div preceding the main statement table
            int intTableCount = countTableDivs();

            //Get the count of transaction with our unique reference on the table
            transactionCount = driver.findElements(By.xpath(".//div[contains(text(),'" + strWorkflowReferenceUniqueTxt + "')]/parent::td//preceding-sibling::td//ancestor::tr")).size();
            //transactionCount = driver.findElements(By.xpath(".//*[@id='StatementForm']/div["+intTableCount+"]/child::div/descendant::div[contains(text(),'" + strWorkflowReferenceUniqueTxt + "')]")).size();

            /**Check the fees applied to this transaction*/
            if (DebtorWaiveChargesOption.equalsIgnoreCase("NO") && (DifferentChargeAccount.equalsIgnoreCase("no"))) {
                //Check postings
                /** REMOVE 2 IF THIS IS NOT WORKING AND CHANGE THE METHOD IMPLEMENTATION BY REMOVING THE int PARAMETER*/
                postingsAccountingCalculator(DebtorControlSum, 2,strWorkflowReferenceUniqueTxt);

                //Reset variable values
                transactionCount = 0;

                //Check to see if the immediate line-item hold fees for this payment
                String strDebtorFeeLineItem = driver.findElement(By.xpath(".//*[@id='StatementForm']/div[" + intTableCount + "]/child::div/descendant::div[contains(text(),'" + strWorkflowReferenceUniqueTxt + "')]//preceding-sibling::div//preceding-sibling::div/parent::td//ancestor::tr//following-sibling::*[1][self::tr]//td[3]//div[2]")).getText();
                highLighterMethod(By.xpath(".//*[@id='StatementForm']/div[" + intTableCount + "]/child::div/descendant::div[contains(text(),'" + strWorkflowReferenceUniqueTxt + "')]//preceding-sibling::div//preceding-sibling::div/parent::td//ancestor::tr//following-sibling::*[1][self::tr]//td[3]//div[2]"));
                strFeeLineItem = strDebtorFeeLineItem;

                //Set the Purpose of payment
                DebtorPurposeOfPayment = setPurposeOfPayment(DebtorPurposeOfPayment);

                //Check If the fee exist(//Ensure that fees are on the Statement form)
                feeLineItemValidation();

                /**Check the fees applied to this transaction*/
                /**Perform Fees validations if Fees are applied to the transaction*/
                //==================Check for fees applied =============================================================
                /**Once Fees have been confirmed to be on the statement,the below validations must performed */
                if (strFeeLineItem.equalsIgnoreCase(DebtorPurposeOfPayment)) {
                    //Get the timestamp
                    strDebitAccountActualFeesTimeStamp = driver.findElement(By.xpath(".//div[contains(text(),'" + strWorkflowReferenceUniqueTxt + "')]/parent::td//preceding-sibling::td//ancestor::tr//following-sibling::*[1][self::tr]//td[1]")).getText();
                    highLighterMethod((By.xpath(".//div[contains(text(),'" + strWorkflowReferenceUniqueTxt + "')]/parent::td//preceding-sibling::td//ancestor::tr//following-sibling::*[1][self::tr]//td[1]")));

                    //Get the Fees Description
                    strDebitAccountActualFeesDescription = driver.findElement(By.xpath(".//div[contains(text(),'" + strWorkflowReferenceUniqueTxt + "')]/parent::td//preceding-sibling::td//ancestor::tr//following-sibling::*[1][self::tr]//td[3]//div[2]")).getText();
                    highLighterMethod(By.xpath(".//div[contains(text(),'" + strWorkflowReferenceUniqueTxt + "')]/parent::td//preceding-sibling::td//ancestor::tr//following-sibling::*[1][self::tr]//td[3]//div[2]"));

                    //Get the Fees Amount from Ebox
                    //If it different fee account it fails here because of 1 flag is used as condition
                    String strDebitAccountActualFeesAmount = driver.findElement(By.xpath(".//div[contains(text(),'" + strWorkflowReferenceUniqueTxt + "')]/parent::td//preceding-sibling::td//ancestor::tr//following-sibling::*[1][self::tr]//td[5]")).getText();
                    highLighterMethod(By.xpath(".//div[contains(text(),'" + strWorkflowReferenceUniqueTxt + "')]/parent::td//preceding-sibling::td//ancestor::tr//following-sibling::*[1][self::tr]//td[5]"));

                    //Convert the Amount from Ebox from it string format to an integer or double format
                    doubleDebitAccountActualFeesAmount = Double.parseDouble(strDebitAccountActualFeesAmount);

                    /**Get Expected fee amounts from Excel and Convert string from excel to integer/double
                     * Use that value to Check if the fees amount applied corresponds to the expected amount*/
                    doubleExpectedFeesAmountApplied = Double.parseDouble(FeesApplied);
                }

                //Set validations results
                strDebitAccountTransactionFeesValidations = strDebitAccountFeesDescriptionAssertion + strDebitAccountFeesAmountAssertion;

                //Take Screenshot
                screenshotLoggerMethod("checkDebtorDebitedAmountEnd", "Debtor Debited Amount Validations");

                //Click on clear button
                scrollElementIntoView(By.id(String.valueOf(clearButtonID)));
                click(By.id(clearButtonID));

                //Reset variable values
                transactionCount = 0;

            } else if (DebtorWaiveChargesOption.equalsIgnoreCase("no") && (DifferentChargeAccount.equalsIgnoreCase("YES"))) {
                //Check how many transactions are on the statement
                if (transactionCount == 0) {
                    //Take Screenshot
                    screenshotLoggerMethod("checkDebtorDebitedAmount", "Debtor Debited Amount");
                }
                //Check postings
                postingsAccountingCalculator(DebtorControlSum, 2,strWorkflowReferenceUniqueTxt);

                //Set validations results
                strDebitAccountTransactionsAmountValidations = strDebitAccountAmountAssertion;

                //Take Screenshot
                screenshotLoggerMethod("checkDebtorDebitedAmountEnd", "Debtor Debited Amount Validations");

                //Click on clear button
                scrollElementIntoView(By.id(String.valueOf(clearButtonID)));
                click(By.id(clearButtonID));

                //Reset variable values
                transactionCount = 0;

                //Go to the account for fees
                checkDifferentChargesFeesDebitAccount(EboxUsername, EboxAgentPassword, EboxDomain, DifferentChargeAccountBranchId, DifferentChargeAccountNumber, DebtorAccountNumber, DebtorWaiveChargesOption, FeesApplied, DebtorPurposeOfPayment);
            } else {
                ExtentTestManager.getTest().info(MarkupHelper.createLabel("No Fees Scenario", ExtentColor.BLUE));
                postingsAccountingCalculator(DebtorControlSum, 2,strWorkflowReferenceUniqueTxt);
                // Thread.sleep(3000);

                //Set validations results
                strDebitAccountTransactionsAmountValidations = strDebitAccountAmountAssertion;

                //Take Screenshot
                screenshotLoggerMethod("checkDebtorDebitedAmountEnd", "Debtor Debited Amount Validations");

                //Click on clear button
                scrollElementIntoView(By.id(String.valueOf(clearButtonID)));
                click(By.id(clearButtonID));

                //Reset variable values
                transactionCount = 0;
            }
        } catch (Exception e) {
            ExtentTestManager.getTest().log(Status.FAIL, "Exception=FATAL: Checking available balance failed  ::" + e.getMessage());
            screenshotLoggerMethod("checkDebtorDebitedAmount", "Debtor Debited Amount Validations Failure");
            stopCurrentTestSetResultsToFailedAndContinueWithNextTest();
        }
    }

    /**
     * Check the transactions suspense account
     */
    public void checkTransactionSuspenseAccount(String EboxUsername, String EboxAgentPassword, String EboxDomain, String ProcessingSuspenseAccountBranchID, String ProcessingSuspenseAccount, String DebtorControlSum, String DebtorWaiveChargesOption, String FeesApplied, String DebtorPurposeOfPayment, String TestTransactionCategory,String strWorkflowReferenceUniqueTxt) throws Exception {
        //Check the that the IAT transaction does'nt check the suspense account.
        if (TestTransactionCategory.equalsIgnoreCase("Inter Account Transfer")) {
            ExtentTestManager.getTest().info(MarkupHelper.createLabel("This is an IAT transaction,therefore the Suspense Account will not be checked", ExtentColor.BLUE));
        } else {
            try {
                /**
                 * Get Creditor Amount shown on table
                 * Search the Ebox table to find the unique transaction reference string.
                 * Use Xpath Axes with Relative-Xpath using contains,sibling,child,descendant and Ancestor**/

                //TODO Msa... The addition of reLogin on eBox
                reLoginAndOpenStatement(EboxUsername, EboxAgentPassword, EboxDomain);

                //Open the Account statement
                openAccountStatement(ProcessingSuspenseAccountBranchID, ProcessingSuspenseAccount);

                //Scroll to view element
                scrollElementIntoView(By.xpath(eboxStatementTableLastRow));
                highLighterMethod(By.xpath(eboxStatementTableLastRow));

                /**Check for Credit Suspense Account transaction amounts*/
                System.out.println("======================Ebox Table Suspense Credit Account Validation================");
                //Get the number of table/div preceding the main statement table
                int intTableCount = countTableDivs();

                //Get the count of transaction with our unique reference on the table
                int transactionCount = driver.findElements(By.xpath(".//div[contains(text(),'" + strWorkflowReferenceUniqueTxt + "')]/parent::td//preceding-sibling::td//ancestor::tr")).size();

//BELOW IF STATEMENT MUST BE ALIGNED  TO THE ONE IN DEBTOR !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                /**Check the fees applied to this transaction*/
                if (DebtorWaiveChargesOption.equalsIgnoreCase("NO")) {


                    /**Check to see if the immediate line-item after the last line-item with our unique reference,holds fees for the payment*/
                    String strSupenseCreditorAccountFeeLineItem = driver.findElement(By.xpath(".//*[@id='StatementForm']/div[" + intTableCount + "]/child::div/descendant::div[contains(text(),'" + strWorkflowReferenceUniqueTxt + "')][" + transactionCount + "]//parent::td//ancestor::tr/following-sibling::*[1][self::tr]//td[3]//div[2]")).getText();
                    highLighterMethod(By.xpath(".//*[@id='StatementForm']/div[" + intTableCount + "]/child::div/descendant::div[contains(text(),'" + strWorkflowReferenceUniqueTxt + "')][" + transactionCount + "]//parent::td//ancestor::tr/following-sibling::*[1][self::tr]//td[3]//div[2]"));
                    strFeeLineItem = strSupenseCreditorAccountFeeLineItem;

                    //Set the Purpose of payment
                    DebtorPurposeOfPayment = setPurposeOfPayment(DebtorPurposeOfPayment);
                    //Check If the fee exist(//Ensure that fees are on the Statement form)
                    feeLineItemValidation();

                    /**Check the fees applied to this transaction
                     *Perform Fees validations if Fees are applied to the transaction*/
                    //==================Check for Credit Suspense Account fees applied =================================
                    if (DebtorWaiveChargesOption.equalsIgnoreCase("NO")) {
                        /**Once Fees have been confirmed to be on the statement,the below validations must performed */
                        if (strFeeLineItem.equalsIgnoreCase(DebtorPurposeOfPayment)) {

                            //Get the timestamp
                            strSuspenseCreditAccountActualFeesTimeStamp = driver.findElement(By.xpath(".//*[@id='StatementForm']/div[" + intTableCount + "]/child::div/descendant::div[contains(text(),'" + strWorkflowReferenceUniqueTxt + "')][" + transactionCount + "]//parent::td//ancestor::tr/following-sibling::*[1][self::tr]//td[1]")).getText();
                            highLighterMethod(By.xpath(".//*[@id='StatementForm']/div[" + intTableCount + "]/child::div/descendant::div[contains(text(),'" + strWorkflowReferenceUniqueTxt + "')][" + transactionCount + "]//parent::td//ancestor::tr/following-sibling::*[1][self::tr]//td[1]"));

                            //Get the Fees Description
                            strSuspenseCreditAccountActualFeesDescription = driver.findElement(By.xpath(".//*[@id='StatementForm']/div[" + intTableCount + "]/child::div/descendant::div[contains(text(),'" + strWorkflowReferenceUniqueTxt + "')][" + transactionCount + "]//parent::td//ancestor::tr/following-sibling::*[1][self::tr]//td[3]//div[2]")).getText();
                            highLighterMethod(By.xpath(".//*[@id='StatementForm']/div[" + intTableCount + "]/child::div/descendant::div[contains(text(),'" + strWorkflowReferenceUniqueTxt + "')][" + transactionCount + "]//parent::td//ancestor::tr/following-sibling::*[1][self::tr]//td[3]//div[2]"));

                            //Get the Fees Amount from Ebox
                            String strSuspenseCreditAccountActualFeesAmount = driver.findElement(By.xpath(".//*[@id='StatementForm']/div[" + intTableCount + "]/child::div/descendant::div[contains(text(),'" + strWorkflowReferenceUniqueTxt + "')][" + transactionCount + "]//parent::td//ancestor::tr/following-sibling::*[1][self::tr]//td[6]")).getText();
                            highLighterMethod(By.xpath(".//*[@id='StatementForm']/div[" + intTableCount + "]/child::div/descendant::div[contains(text(),'" + strWorkflowReferenceUniqueTxt + "')][" + transactionCount + "]//parent::td//ancestor::tr/following-sibling::*[1][self::tr]//td[6]"));

                            //Convert the Amount from Ebox from it string format to an integer or double format
                            doubleSuspenseCreditAccountActualFeesAmount = Double.parseDouble(strSuspenseCreditAccountActualFeesAmount);


                            /**Get Expected fee amounts from Excel and Convert string from excel to integer/double
                             * Use that value to Check if the fees amount applied corresponds to the expected amount*/
                            doubleExpectedFeesAmountApplied = Double.parseDouble(FeesApplied);
                        }
                    }

                    //==============================Fees Validations/Assertions=========================================
                    /**Check if the correct fees description/reference is applied for the payment-purpose in question
                     * And the fees amount are corresponding */
                    if (strSuspenseCreditAccountActualFeesDescription.contains(DebtorPurposeOfPayment)) {
                        strSuspenseCreditAccountFeesDescriptionAssertoin = "PASS";
                    } else { //Compare the expected fee amount with whats currently on the statement form
                        strSuspenseCreditAccountFeesDescriptionAssertoin = "FAIL";
                    }

                    //Compare the expected fee amount with whats currently on the statement form
                    if (doubleSuspenseCreditAccountActualFeesAmount == doubleExpectedFeesAmountApplied) {
                        strSuspenseCreditAccountFeesAmountAssertion = "PASS";
                    } else {
                        strSuspenseCreditAccountFeesAmountAssertion = "FAIL";
                    }

                    //Set validations results
                    strSuspenseCreditAccountTransactionFeesValidations = strSuspenseCreditAccountFeesDescriptionAssertoin + strSuspenseCreditAccountFeesAmountAssertion;

                }


                /**Iterate through the number of transaction found to get all the amounts and add them up*/
                //==================Check for transaction amounts=======================================================
                int i = 1; //NB: XPath starts counting at 1, so the second element is at position() 2

                //      postingsAccountingCalculator(DebtorControlSum,3);

                //Loop through the Ebox table to get the total amount for the creditor(s)
                do {
                    WebElement eboxSuspenseAccountCreditXpathAxis = driver.findElement(By.xpath("(.//div[contains(text(),'" + strWorkflowReferenceUniqueTxt + "')]/parent::td//following-sibling::td[2])[position()=" + i + "]"));
                    highLighterMethod(By.xpath("(.//div[contains(text(),'" + strWorkflowReferenceUniqueTxt + "')]/parent::td//following-sibling::td[2])[position()=" + i + "]"));

                    //Get the creditor amount from Ebox and remove non-numeric characters
                    String strSuspenseAccountCreditAmount = eboxSuspenseAccountCreditXpathAxis.getText().replaceAll("[^a-zA-Z0-9\\\\._-]", "");

                    //Check if the String is empty
                    if (strSuspenseAccountCreditAmount.isEmpty()) {
                        //System.out.println("The string is currently empty for this iteration "+i);

                    } else {
                        //Get the amount from Ebox and convert it from it string format to an integer or double format
                        doubleSuspenseCreditAccountActualAmount = Double.parseDouble(strSuspenseAccountCreditAmount);

                        //Print the amount
                        System.out.println("The " + i + " Suspense Credit Amount is  " + doubleSuspenseCreditAccountActualAmount);

                        //Add the amount found in every iteration
                        Math.round(doubleSuspenseCreditAccountActualTotal += doubleSuspenseCreditAccountActualAmount);

                    }
                    //Increment index count
                    i++;

                } while (i <= transactionCount);


                //==========-----------------=========== THE END OF SIMILARITY ON CALCULATOR -----================---------------

                /**Get totals debit amount (round-off to two decimal places) from ebox*/
                /**Convert string from excel to integer/double */
                doubleDecimalExpectedControlSum = Double.parseDouble(DebtorControlSum);

                //Print the total amount from Ebox
                System.out.println("The toatl Ebox Debit Amount is " + doubleSuspenseCreditAccountActualTotal);

                //==================Perform Suspense Account Credit Validations/Assertions==============================
                if (doubleDecimalExpectedControlSum == doubleSuspenseCreditAccountActualTotal) {
                    strSuspenseCreditAccountTransactionAmountAssertion = "PASS";
                } else {
                    strSuspenseCreditAccountTransactionAmountAssertion = "FAIL";
                }

                System.out.println("======================Ebox Table Suspense Debit Account Validation==================");
                if (TestTransactionCategory.equalsIgnoreCase("Off_Us")) {
                    ExtentTestManager.getTest().info(MarkupHelper.createLabel("Off-Us Transaction doesnt debit the suspense account", ExtentColor.BLUE));
                } else {
                    /**Check the fees applied to this transaction*/
                    if (DebtorWaiveChargesOption.equalsIgnoreCase("NO")) {
                        /**Check to see if the immediate line-item after the last line-item with our unique reference,holds fees for the payment*/
                        String strSupenseDebtorAccountFeeLineItem = driver.findElement(By.xpath(".//*[@id='StatementForm']/div[" + intTableCount + "]/child::div/descendant::div[contains(text(),'" + strWorkflowReferenceUniqueTxt + "')][" + transactionCount + "]//parent::td//ancestor::tr/following-sibling::*[2][self::tr]//td[3]//div[2]")).getText();
                        highLighterMethod(By.xpath(".//*[@id='StatementForm']/div[" + intTableCount + "]/child::div/descendant::div[contains(text(),'" + strWorkflowReferenceUniqueTxt + "')][" + transactionCount + "]//parent::td//ancestor::tr/following-sibling::*[2][self::tr]//td[3]//div[2]"));
                        strFeeLineItem = strSupenseDebtorAccountFeeLineItem;

                        //Check If the fee exist(//Ensure that fees are on the Statement form)
                        feeLineItemValidation();

                        /**Check the fees applied to this transaction
                         *Perform Fees validations if Fees are applied to the transaction*/
                        //==================Check for Debit Suspense Account fees applied =============================================
                        if (DebtorWaiveChargesOption.equalsIgnoreCase("NO")) {
                            /**Once Fees have been confirmed to be on the statement,the below validations must performed */
                            if (strFeeLineItem.equalsIgnoreCase(DebtorPurposeOfPayment)) {

                                //Get the timestamp
                                strSuspenseDebitAccountActualFeesTimeStamp = driver.findElement(By.xpath(".//*[@id='StatementForm']/div[" + intTableCount + "]/child::div/descendant::div[contains(text(),'" + strWorkflowReferenceUniqueTxt + "')][" + transactionCount + "]//parent::td//ancestor::tr/following-sibling::*[2][self::tr]//td[1]")).getText();
                                highLighterMethod(By.xpath(".//*[@id='StatementForm']/div[" + intTableCount + "]/child::div/descendant::div[contains(text(),'" + strWorkflowReferenceUniqueTxt + "')][" + transactionCount + "]//parent::td//ancestor::tr/following-sibling::*[2][self::tr]//td[1]"));

                                //Get the Fees Description
                                strSuspenseDebitAccountActualFeesDescription = driver.findElement(By.xpath(".//*[@id='StatementForm']/div[" + intTableCount + "]/child::div/descendant::div[contains(text(),'" + strWorkflowReferenceUniqueTxt + "')][" + transactionCount + "]//parent::td//ancestor::tr/following-sibling::*[2][self::tr]//td[3]//div[2]")).getText();
                                highLighterMethod(By.xpath(".//*[@id='StatementForm']/div[" + intTableCount + "]/child::div/descendant::div[contains(text(),'" + strWorkflowReferenceUniqueTxt + "')][" + transactionCount + "]//parent::td//ancestor::tr/following-sibling::*[2][self::tr]//td[3]//div[2]"));

                                //Get the Fees Amount from Ebox
                                String strSuspenseDebitAccountActualFeesAmount = driver.findElement(By.xpath(".//*[@id='StatementForm']/div[" + intTableCount + "]/child::div/descendant::div[contains(text(),'" + strWorkflowReferenceUniqueTxt + "')][" + transactionCount + "]//parent::td//ancestor::tr/following-sibling::*[2][self::tr]//td[5]")).getText();
                                highLighterMethod(By.xpath(".//*[@id='StatementForm']/div[" + intTableCount + "]/child::div/descendant::div[contains(text(),'" + strWorkflowReferenceUniqueTxt + "')][" + transactionCount + "]//parent::td//ancestor::tr/following-sibling::*[2][self::tr]//td[5]"));

                                //Convert the Amount from Ebox from it string format to an integer or double format
                                doubleSuspenseDebitAccountActualFeesAmount = Double.parseDouble(strSuspenseDebitAccountActualFeesAmount);

                                /**Get Expected fee amounts from Excel and Convert string from excel to integer/double
                                 * Use that value to Check if the fees amount applied corresponds to the expected amount*/
                                doubleExpectedFeesAmountApplied = Double.parseDouble(FeesApplied);
                            }

                            //==============================Fees Validations/Assertions=================================================
                            /**Check if the correct fees description/reference is applied for the payment-purpose in question
                             * And the fees amount are corresponding */
                            if (strSuspenseDebitAccountActualFeesDescription.contains(DebtorPurposeOfPayment)) {
                                strSuspenseDebitAccountFeesDescriptionAssertion = "PASS";
                            } else { //Compare the expected fee amount with whats currently on the statement form
                                strSuspenseDebitAccountFeesDescriptionAssertion = "FAIL";
                            }

                            //Compare the expected fee amount with whats currently on the statement form
                            if (doubleSuspenseDebitAccountActualFeesAmount == doubleExpectedFeesAmountApplied) {
                                strSuspenseDebitAccountFeesAmountAssertion = "PASS";
                            } else {
                                strSuspenseDebitAccountFeesAmountAssertion = "FAIL";
                            }

                            //Set validations results
                            strSuspenseDebitAccountTransactionFeesValidations = strSuspenseDebitAccountFeesDescriptionAssertion + strSuspenseDebitAccountFeesAmountAssertion;
                        }
                    }


                    /**REMOVE BELOW DEBTOR ACCOUNTING BLOCK BY CALLING OF POSTING_CALCULATOR LIKE BELOW UNTIL AFTER CLEARING BUTTON IS CLICKED*/
                    //postingsAccountingCalculator(DebtorControlSum,2);


                    /**Iterate through the number of transaction found to get all the amounts and add them up*/
                    //==================Check for Debit Suspense Account transaction amounts========================================
                    i = 1; //NB: XPath starts counting at 1, so the second element is at position() 2

                    //Loop through the Ebox table to get the total amount for the Debtors(s)
                    do {
                        WebElement eboxSuspenseAccountDebitXpathAxis = driver.findElement(By.xpath("(.//div[contains(text(),'" + strWorkflowReferenceUniqueTxt + "')]/parent::td//following-sibling::td[2])[position()=" + i + "]"));
                        highLighterMethod(By.xpath("(.//div[contains(text(),'" + strWorkflowReferenceUniqueTxt + "')]/parent::td//following-sibling::td[2])[position()=" + i + "]"));

                        //Get the Debtor's amount from Ebox and remove non-numeric characters
                        String strSuspenseAccountDebitAmount = eboxSuspenseAccountDebitXpathAxis.getText().replaceAll("[^a-zA-Z0-9\\\\._-]", "");

                        //Check if the String is empty
                        if (strSuspenseAccountDebitAmount.isEmpty()) {
                            //System.out.println("The string is currently empty for this iteration "+i);

                        } else {
                            //Get the amount from Ebox and convert it from it string format to an integer or double format
                            doubleSuspenseDebitAccountActualAmount = Double.parseDouble(strSuspenseAccountDebitAmount);

                            //Print the amount
                            System.out.println("The " + i + " Suspense Debit Amount is " + doubleSuspenseDebitAccountActualAmount);

                            //Add the amount found in every iteration
                            Math.round(doubleSuspenseDebitAccountActualTotal += doubleSuspenseDebitAccountActualAmount);
                        }
                        //Increment index count
                        i++;

                    } while (i <= transactionCount);


                    /**Get totals debit amount (round-off to two decimal places) from ebox*/
                    /**Convert string from excel to integer/double */
                    doubleDecimalExpectedControlSum = Double.parseDouble(DebtorControlSum);

                    //Print the total amount from Ebox
                    System.out.println("The toatl Ebox Debit Amount is " + doubleSuspenseDebitAccountActualTotal);

                    //==================Perform Suspense Account Debtor Validations/Assertions==================================
                    if (doubleDecimalExpectedControlSum == doubleSuspenseDebitAccountActualTotal) {
                        strSuspenseDebitAccountTransactionAmountAssertion = "PASS";
                    } else {
                        strSuspenseDebitAccountTransactionAmountAssertion = "FAIL";
                    }


                }


                //Set validations results
                strSuspenseCreditAccountTransactionsAmountValidations = strSuspenseCreditAccountTransactionAmountAssertion;
                strSuspenseDebitAccountTransactionsAmountValidations = strSuspenseDebitAccountTransactionAmountAssertion;

                //Take Screenshot
                screenshotLoggerMethod("checkTransactionSuspenseAccountEnd", "Transaction Suspense Account Validations");
//                                                                                                                    Transaction Suspense Account Validations
                //Click on clear button
                click(By.id(clearButtonID));


            } catch (Exception e) {
                //ExtentTestManager.getTest().log(Status.FAIL, "Exception=FATAL: Checking available balance on the suspense account failed due to ::  " + e.getMessage());
                ExtentTestManager.getTest().log(Status.FAIL, "Exception=FAIL: Checking available balance on the suspense account failed due to ::  " + e.getMessage());
                screenshotLoggerMethod("checkTransactionSuspenseAccount", "Transaction Suspense Account Validations Failure");
                stopCurrentTestSetResultsToFailedAndContinueWithNextTest();
            }
        }
    }

    /**
     * Check Creditor's Credited Amount
     */
    public void checkCreditorCreditedAmount(String EboxUsername, String EboxAgentPassword, String EboxDomain, String CreditorBranchId_1, String CreditorAccountNumber_1, String DebtorControlSum, String DebtorWaiveChargesOption, String FeesApplied, String DebtorPurposeOfPayment,String strWorkflowReferenceUniqueTxt) throws Exception {
        try {
            /**
             * Get Creditor Amount shown on table
             * Search the Ebox table to find the unique transaction reference string.
             * Use Xpath Axes with Relative-Xpath using contains,sibling,child,descendant and Ancestor**/

            //TODO Msa... The addition of reLogin on eBox
            reLoginAndOpenStatement(EboxUsername, EboxAgentPassword, EboxDomain);

            //Open the Account statement
            openAccountStatement(CreditorBranchId_1, CreditorAccountNumber_1);

            //Scroll to view element
            scrollElementIntoView(By.xpath(eboxStatementTableLastRow));

            /**Check for Credit  Account transaction amounts*/
            System.out.println("======================Ebox Table Credit Account Validation==============================");
            //Get the number of table/div preceding the main statement table
            int intTableCount = countTableDivs();

            //Get the count of transaction with our unique reference on the table
            transactionCount = driver.findElements(By.xpath(".//div[contains(text(),'" + strWorkflowReferenceUniqueTxt + "')]/parent::td//preceding-sibling::td//ancestor::tr")).size();

            //Set the Purpose of payment
            DebtorPurposeOfPayment = setPurposeOfPayment(DebtorPurposeOfPayment);
            strCreditAccountActualFeesDescription = DebtorPurposeOfPayment;

            /**Iterate through the number of transaction found to get all the amounts and add them up*/
            postingsAccountingCalculator(DebtorControlSum, 3,strWorkflowReferenceUniqueTxt);

            //Set validations results
            strCreditAccountTransactionsAmountValidations = strCreditAccountTransactionsAmountAssertion;

            //Take Screenshot
            screenshotLoggerMethod("checkCreditorCreditedAmountEnd", "Creditor Credited Amount Validations");

            //Click on clear button
            click(By.id(clearButtonID));

            //Reset variable values
            transactionCount = 0;

            System.out.println("=======================================================================================");

        } catch (Exception e) {
            ExtentTestManager.getTest().log(Status.FAIL, "Exception=FATAL: Checking available balance failed  due to ::  " + e.getMessage());
            screenshotLoggerMethod("checkCreditorCreditedAmount", "Creditor Credited Amount Validations Failure");
            stopCurrentTestSetResultsToFailedAndContinueWithNextTest();
        }
    }

    /**
     * Check Clearing account Credited Amount
     */
    public void checkClearingCreditedAmount(String EboxUsername, String EboxAgentPassword, String EboxDomain, String ClearingAccountBranchId, String ClearingAccountNumber, String DebtorControlSum, String DebtorPurposeOfPayment,String strWorkflowReferenceUniqueTxt) throws Exception {
        try {
            /**
             * Get  Amount shown on table
             * Search the Ebox table to find the unique transaction reference string.
             * Use Xpath Axes with Relative-Xpath using contains,sibling,child,descendant and Ancestor**/

            //TODO Msa... The addition of reLogin on eBox
            reLoginAndOpenStatement(EboxUsername, EboxAgentPassword, EboxDomain);

            //Open the Account statement
            openAccountStatement(ClearingAccountBranchId, ClearingAccountNumber);

            /**BELOW LINE TO REPLACE ABOVE BLOCK*/
            //openAccountStatement(ClearingAccountBranchId, ClearingAccountNumber);

            //Scroll to view element
            scrollElementIntoView(By.xpath(eboxStatementTableLastRow));


            /**Check for Credit  Account transaction amounts*/
            System.out.println("======================Ebox Table Credit Account Validation==============================");
            //Get the number of table/div preceding the main statement table
            int intTableCount = countTableDivs();

            //Get the count of transaction with our unique reference on the table
            transactionCount = driver.findElements(By.xpath(".//div[contains(text(),'" + strWorkflowReferenceUniqueTxt + "')]/parent::td//preceding-sibling::td//ancestor::tr")).size();

            //Set the Purpose of payment
            DebtorPurposeOfPayment = setPurposeOfPayment(DebtorPurposeOfPayment);
            strCreditAccountActualFeesDescription = DebtorPurposeOfPayment;

            postingsAccountingCalculator(DebtorControlSum, 3,strWorkflowReferenceUniqueTxt);
            /*

             */
/**Iterate through the number of transaction found to get all the amounts and add them up*//*

            //==================Check for transaction amounts===========================================================
            int i = 1; //NB: XPath starts counting at 1, so the second element is at position() 2

            //Loop through the Ebox table to get the total amount for the creditor(s)
            do
            {
                WebElement eboxClearingAccountCreditAccountXpathAxis = driver.findElement(By.xpath("(.//div[contains(text(),'" + strWorkflowReferenceUniqueTxt + "')]/parent::td//following-sibling::td[3])[position()=" + i + "]"));
                highLighterMethod(By.xpath("(.//div[contains(text(),'" + strWorkflowReferenceUniqueTxt + "')]/parent::td//following-sibling::td[3])[position()=" + i + "]"));

                //Get the creditor amount from Ebox and remove non-numeric characters
                String strClearingAccountCreditAccountAmount = eboxClearingAccountCreditAccountXpathAxis.getText().replaceAll("[^a-zA-Z0-9\\\\._-]", "");


                //Check if the String is empty
                if (strClearingAccountCreditAccountAmount.isEmpty())
                {
                    //System.out.println("The string is currently empty for this iteration "+i);

                } else
                {
                    //Get the amount from Ebox and convert it from it string format to an integer or double format
                    doubleClearingAccountCreditAccountActualAmount = Double.parseDouble(strClearingAccountCreditAccountAmount);

                    //Print the amount
                    System.out.println("The "+i+ " Ebox Credit Amount is  " + doubleClearingAccountCreditAccountActualAmount);

                    //Add the amount found in every iteration
                    Math.round(doubleClearingAccountCreditAccountActualAmountTotal += doubleClearingAccountCreditAccountActualAmount);

                }
                //Increment index count
                i++;

            } while (i <= transactionCount);


            */
/**Get totals debit amount (round-off to two decimal places) from ebox*
 *Convert string from excel to integer/double *//*

            doubleDecimalExpectedControlSum = Double.parseDouble(DebtorControlSum);

            //Print the total amount
            System.out.println("The toatl Ebox Clearing Account Amount is " + doubleClearingAccountCreditAccountActualAmountTotal);


            //==================Perform Debtor Account Validations/Assertions===========================================
            */
/**The total creditor(s) amount must equal the original Control sum amount*//*

            if(doubleDecimalExpectedControlSum==doubleClearingAccountCreditAccountActualAmountTotal)
            {
                //Set the Status for reporting
                strClearingAccountCreditAccountTransactionsAmountAssertion= "PASS";
            }else
            {
                //Set the Status for reporting
                strClearingAccountCreditAccountTransactionsAmountAssertion= "FAIL";
            }
*/

            //Set validations results
            strClearingAccountCreditAccountTransactionsAmountValidations = strClearingAccountCreditAccountTransactionsAmountAssertion;

            //Take Screenshot
            screenshotLoggerMethod("checkCreditorCreditedAmountEnd", "Clearing Account Credited Amount Validations");

            //Click on clear button
            click(By.id(clearButtonID));

            //Reset variable values
            transactionCount = 0;

            System.out.println("=======================================================================================");


        } catch (Exception e) {
            ExtentTestManager.getTest().log(Status.FAIL, "Exception=FATAL: Checking available balance failed  due to ::  " + e.getMessage());
            screenshotLoggerMethod("checkClearingAccountCreditorCreditedAmount", "Clearing Account Credited Amount Validations Failure");
            stopCurrentTestSetResultsToFailedAndContinueWithNextTest();
        }
    }

    /**
     * Check Creditor's Credited Amount
     */
    public void checkPNLCreditAccountForFees(String EboxUsername, String EboxAgentPassword, String EboxDomain, String PNLAccountBranchID, String PNLAccountNumber, String DebtorAccountNumber, String DebtorWaiveChargesOption, String FeesApplied, String DebtorPurposeOfPayment) throws Exception {
        if (DebtorWaiveChargesOption.equalsIgnoreCase("NO")) {
            try {
                //TODO Msa... The addition of reLogin on eBox
                reLoginAndOpenStatement(EboxUsername, EboxAgentPassword, EboxDomain);

                //Open the Account statement
                openAccountStatement(PNLAccountBranchID, PNLAccountNumber);

                //Scroll to view element
                scrollElementIntoView(By.xpath(eboxStatementTableLastRow));
                highLighterMethod(By.xpath(eboxStatementTableLastRow));


                /**Check for fees on PNL Credit Account transaction amounts*/
                System.out.println("======================Ebox Table PNL Credit Account Fees Validation====================");
                //Get the number of table/div preceding the main statement table
                int intTableCount = countTableDivs();

                //Get the count of transaction with our unique reference on the table
                int statementLineItemCount = driver.findElements(By.xpath(".//*[@id='StatementForm']/div[4]/child::div/descendant::tr")).size();
                int lastRow = statementLineItemCount;

                //Set the Purpose of payment
                DebtorPurposeOfPayment = setPurposeOfPayment(DebtorPurposeOfPayment);

                /**Once Fees have been confirmed to be on the statement,the below validations must performed */
                if (strFeeLineItem.equalsIgnoreCase(DebtorPurposeOfPayment)) {
                    //The will be on the last line-item

                    //Get the timestamp
                    strPNLCreditAccountActualFeesTimeStamp = driver.findElement(By.xpath(".//*[@id='StatementForm']/div[" + intTableCount + "]/child::div/descendant::tr[" + lastRow + "]/td[1]")).getText();
                    highLighterMethod((By.xpath(".//*[@id='StatementForm']/div[" + intTableCount + "]/child::div/descendant::tr[" + lastRow + "]/td[1]")));

                    //Get the Fees Description
                    strPNLCreditAccountActualFeesDescription = driver.findElement(By.xpath(".//*[@id='StatementForm']/div[" + intTableCount + "]/child::div/descendant::tr[" + lastRow + "]/td[3]/div[2]")).getText();
                    highLighterMethod(By.xpath((".//*[@id='StatementForm']/div[" + intTableCount + "]/child::div/descendant::tr[" + lastRow + "]/td[3]/div[2]")));

                    //Get the Originator Branch ID
                    strPNLCreditAccountActualOriginatorBranchID = driver.findElement(By.xpath(".//*[@id='StatementForm']/div[" + intTableCount + "]/child::div/descendant::tr[" + lastRow + "]/td[3]/div[3]")).getText();
                    String strBranchIDTrim = trimLeadingAndTrailingCharacters(strPNLCreditAccountActualOriginatorBranchID, 0, 3);
                    strPNLCreditAccountActualOriginatorBranchID = "0" + strBranchIDTrim;
                    highLighterMethod(By.xpath((".//*[@id='StatementForm']/div[" + intTableCount + "]/child::div/descendant::tr[" + lastRow + "]/td[3]/div[3]")));

                    //Get the Originator Account Number
                    strPNLCreditAccountActualOriginatorAccountNumber = driver.findElement(By.xpath(".//*[@id='StatementForm']/div[" + intTableCount + "]/child::div/descendant::tr[" + lastRow + "]/td[3]/div[3]")).getText();
                    String strAccountNumberTrim = trimLeadingCharacters(strPNLCreditAccountActualOriginatorAccountNumber, 3);
                    strPNLCreditAccountActualOriginatorAccountNumber = strAccountNumberTrim;
                    highLighterMethod(By.xpath((".//*[@id='StatementForm']/div[" + intTableCount + "]/child::div/descendant::tr[" + lastRow + "]/td[3]/div[3]")));

                    //Get the Fees Amount from Ebox
                    String strPNLCreditccountActualFeesAmount = driver.findElement(By.xpath(".//*[@id='StatementForm']/div[" + intTableCount + "]/child::div/descendant::tr[" + lastRow + "]/td[6]")).getText();
                    highLighterMethod(By.xpath(".//*[@id='StatementForm']/div[" + intTableCount + "]/child::div/descendant::tr[" + lastRow + "]/td[6]"));

                    //Convert the Amount from Ebox from it string format to an integer or double format
                    doublePNLCreditAccountFeesAmount = Double.parseDouble(strPNLCreditccountActualFeesAmount);

                    /**Get Expected fee amounts from Excel and Convert string from excel to integer/double
                     * Use that value to Check if the fees amount applied corresponds to the expected amount*/
                    doubleExpectedFeesAmountApplied = Double.parseDouble(FeesApplied);
                }
                //==============================Fees Validations/Assertions=============================================
                /**Check if the correct fees description/reference is applied for the payment-purpose in question
                 * And the fees amount are corresponding */
                if (strPNLCreditAccountActualFeesDescription.contains(DebtorPurposeOfPayment)) {
                    strPNLCreditAccountFeesDescriptionAssertion = "PASS";
                } else {
                    //Compare the expected fee amount with whats currently on the statement form
                    strPNLCreditAccountFeesDescriptionAssertion = "FAIL";
                }

                //Compare the date with that of the Debtor Account
                if (strDebitAccountActualFeesTimeStamp.equalsIgnoreCase(strPNLCreditAccountActualFeesTimeStamp)) {
                    strPNLCreditAccountFeesTimeStampAssertion = "PASS";
                } else {
                    strPNLCreditAccountFeesTimeStampAssertion = "FAIL";
                }

                if (strPNLCreditAccountActualOriginatorAccountNumber.equalsIgnoreCase(DebtorAccountNumber)) {
                    strPNLCreditAccountActualOriginatorAccountNumberAssertion = "PASS";
                } else {
                    strPNLCreditAccountActualOriginatorAccountNumberAssertion = "FAIL";
                }

                if (doublePNLCreditAccountFeesAmount == doubleExpectedFeesAmountApplied) {//Compare the expected fee amount with whats currently on the statement form
                    strPNLCreditAccountFeesAmountAssertion = "PASS";
                } else {
                    strPNLCreditAccountFeesAmountAssertion = "FAIL";
                }

                //Set validations results
                strPNLCreditAccountTransactionFeesValidations = strPNLCreditAccountFeesDescriptionAssertion + strPNLCreditAccountFeesTimeStampAssertion + strPNLCreditAccountActualOriginatorAccountNumberAssertion + strPNLCreditAccountFeesAmountAssertion;

                //Take Screenshot
                screenshotLoggerMethod("checkPNLCreditAccountForFeesEnd", "PNLCredit Account For Fees Validations");

                //Click on clear button
                click(By.id(clearButtonID));

                //Reset variable values
                transactionCount = 0;

                System.out.println("=======================================================================================");

            } catch (Exception e) {
                ExtentTestManager.getTest().log(Status.FAIL, "Exception=FATAL: Checking available balance failed  due to ::  " + e.getMessage());
                screenshotLoggerMethod("checkCreditorCreditedAmount", "PNLCredit Account For Fees Validations Failure");
                stopCurrentTestSetResultsToFailedAndContinueWithNextTest();
            }
        }
    }

    /**
     * Check Different charges account fee Amount
     */
    public void checkDifferentChargesFeesDebitAccount(String EboxUsername, String EboxAgentPassword, String EboxDomain, String DifferentChargeAccountBranchId, String DifferentChargeAccountNumber, String DebtorAccountNumber, String DebtorWaiveChargesOption, String FeesApplied, String DebtorPurposeOfPayment) throws Exception {
        if (DebtorWaiveChargesOption.equalsIgnoreCase("NO") && !DifferentChargeAccountNumber.equalsIgnoreCase("N/A")) {
            try {
                //TODO Msa... The addition of reLogin on eBox
                reLoginAndOpenStatement(EboxUsername, EboxAgentPassword, EboxDomain);

                //Open the Account statement
                openAccountStatement(DifferentChargeAccountBranchId, DifferentChargeAccountNumber);

                //Scroll to view element
                scrollElementIntoView(By.xpath(eboxStatementTableLastRow));
                highLighterMethod(By.xpath(eboxStatementTableLastRow));


                /**Check for fees on Different Charges Account Debit Account transaction amounts*/
                System.out.println("======================Ebox Table Different Charges Account Fees Validation=============");
                //Get the number of table/div preceding the main statement table
                int intTableCount = countTableDivs();

                //Go to the last row on the transaction statement table
                int statementLineItemCount = driver.findElements(By.xpath(".//*[@id='StatementForm']/div[4]/child::div/descendant::tr")).size();
                int lastRow = statementLineItemCount;

                //Set the Purpose of payment
                DebtorPurposeOfPayment = setPurposeOfPayment(DebtorPurposeOfPayment);

                /**Once Fees have been confirmed to be on the statement,the below validations must performed */
                if (strFeeLineItem.equalsIgnoreCase(DebtorPurposeOfPayment)) {
                    //The will be on the last line-item
                    //Get the timestamp
                    strDifferentChargesDebitAccountFeesActualFeesTimeStamp = driver.findElement(By.xpath(".//*[@id='StatementForm']/div[" + intTableCount + "]/child::div/descendant::tr[" + lastRow + "]/td[1]")).getText();
                    highLighterMethod((By.xpath(".//*[@id='StatementForm']/div[" + intTableCount + "]/child::div/descendant::tr[" + lastRow + "]/td[1]")));

                    //Get the Fees Description
                    strDifferentChargesDebitAccountActualFeesDescription = driver.findElement(By.xpath(".//*[@id='StatementForm']/div[" + intTableCount + "]/child::div/descendant::tr[" + lastRow + "]/td[3]/div[2]")).getText();
                    highLighterMethod(By.xpath((".//*[@id='StatementForm']/div[" + intTableCount + "]/child::div/descendant::tr[" + lastRow + "]/td[3]/div[2]")));

                    //Get the Fees Amount from Ebox
                    String strDifferentChargesDebitAccountFeesActualFeesAmount = driver.findElement(By.xpath(".//*[@id='StatementForm']/div[" + intTableCount + "]/child::div/descendant::tr[" + lastRow + "]/td[5]")).getText();
                    highLighterMethod(By.xpath(".//*[@id='StatementForm']/div[" + intTableCount + "]/child::div/descendant::tr[" + lastRow + "]/td[5]"));

                    //Convert the Amount from Ebox from it string format to an integer or double format
                    doubleDifferentChargesDebitAccountFeesAmount = Double.parseDouble(strDifferentChargesDebitAccountFeesActualFeesAmount);

                    /**Get Expected fee amounts from Excel and Convert string from excel to integer/double
                     * Use that value to Check if the fees amount applied corresponds to the expected amount*/
                    doubleExpectedFeesAmountApplied = Double.parseDouble(FeesApplied);
                }
                //==============================Fees Validations/Assertions=============================================
                /**Check if the correct fees description/reference is applied for the payment-purpose in question
                 * And the fees amount are corresponding */
                if (strDifferentChargesDebitAccountActualFeesDescription.contains(DebtorPurposeOfPayment)) {
                    strDifferentChargesDebitAccountFeesDescriptionAssertion = "PASS";
                } else {
                    //Compare the expected fee amount with whats currently on the statement form
                    strDifferentChargesDebitAccountFeesDescriptionAssertion = "FAIL";
                }

                //Compare the date with that of the Debtor Account
                if (strDebitAccountActualFeesTimeStamp.equalsIgnoreCase(strDifferentChargesDebitAccountFeesActualFeesTimeStamp)) {
                    strDifferentChargesDebitAccountFeesTimeStampAssertion = "PASS";
                } else {
                    strDifferentChargesDebitAccountFeesTimeStampAssertion = "FAIL";
                }

                if (doubleDifferentChargesDebitAccountFeesAmount == doubleExpectedFeesAmountApplied) {//Compare the expected fee amount with whats currently on the statement form
                    strDifferentChargesDebitAccountFeesAmountAssertion = "PASS";
                } else {
                    strDifferentChargesDebitAccountFeesAmountAssertion = "FAIL";
                }

                //Set validations results
                strDifferentChargesDebitAccountTransactionFeesValidations = strPNLCreditAccountFeesDescriptionAssertion + strPNLCreditAccountFeesTimeStampAssertion + strPNLCreditAccountActualOriginatorAccountNumberAssertion + strPNLCreditAccountFeesAmountAssertion;

                //Take Screenshot
                screenshotLoggerMethod("checkDifferentChargesFeesDebitAccountEnd", "Different Charges Fees Debit Account Validations");

                //Click on clear button
                click(By.id(clearButtonID));

                //Reset variable values
                transactionCount = 0;

                System.out.println("=======================================================================================");

            } catch (Exception e) {
                ExtentTestManager.getTest().log(Status.FAIL, "Exception=FATAL: Checking available balance failed  due to ::  " + e.getMessage());
                screenshotLoggerMethod("checkCreditorCreditedAmount", "Different Charges Fees Debit Account Validations Failure");
                stopCurrentTestSetResultsToFailedAndContinueWithNextTest();
            }
        }
    }

    /**
     * Check Inward Suspense Amount
     */
    public void checkInwardClearingSuspenseAccount(String EboxUsername, String EboxAgentPassword, String EboxDomain, String DebtorBranchID, String DebtorAccountNumber, String DebtorControlSum, String DebtorWaiveChargesOption, String FeesApplied, String DebtorPurposeOfPayment, String DebtorOriginationReference, String DifferentChargeAccount, String DifferentChargeAccountBranchId, String DifferentChargeAccountNumber, String DebtorWorkflowReference) throws Exception {
        try {
            //For Standalone Execution
            standAloneExecution(DebtorOriginationReference, DebtorWorkflowReference);

            //TODO Msa... The addition of reLogin on eBox
            reLoginAndOpenStatement(EboxUsername, EboxAgentPassword, EboxDomain);

            //Open the Account statement
            openAccountStatement(DebtorBranchID, DebtorAccountNumber);

            //Scroll to view element
            scrollElementIntoView(By.xpath(eboxStatementTableLastRow));
            //highLighterMethod(By.xpath(eboxStatementTableLastRow));

            /**
             * Get debtor Amount shown on table
             * Search the Ebox table to find the unique transaction reference string.
             * Use Xpath Axes with Relative-Xpath using contains,sibling,child,descendant and Ancestor**/
            System.out.println("======================Ebox Table Debit Validation=======================================");
            //Get the number of table/div preceding the main statement table
            int intTableCount = countTableDivs();

            transactionCount = driver.findElements(By.xpath(".//div[contains(text(),'" + systemReference + "')]/parent::td//preceding-sibling::td//ancestor::tr")).size();
            //transactionCount = driver.findElements(By.xpath(".//*[@id='StatementForm']/div["+intTableCount+"]/child::div/descendant::div[contains(text(),'" + strWorkflowReferenceUniqueTxt + "')]")).size();

            //Check postings
            postingsAccountingCalculatorInwards(DebtorControlSum, 2, systemReference);

            //Set validations results
            strDebitAccountTransactionsAmountValidations = strDebitAccountAmountAssertion;

            //Take Screenshot
            screenshotLoggerMethod("InwardClearingStatement", "Inward Clearing Transfer Amount");

            //Click on clear button
            scrollElementIntoView(By.id(String.valueOf(clearButtonID)));
            click(By.id(clearButtonID));

            //Reset variable values
            transactionCount = 0;

        } catch (Exception e) {
            ExtentTestManager.getTest().log(Status.FAIL, "Exception=FATAL: Checking available balance failed ::" + e.getMessage());
            screenshotLoggerMethod("checkInwardClearingDebitedAmount", "Inward Clearing Debited Amount Validations Failure");
            stopCurrentTestSetResultsToFailedAndContinueWithNextTest();
        }
    }


    /**
     * Check the transactions suspense account
     */
    public void checkInwardSuspenseAccount(String EboxUsername, String EboxAgentPassword, String EboxDomain, String ProcessingSuspenseAccountBranchID, String ProcessingSuspenseAccount, String DebtorControlSum, String DebtorWaiveChargesOption, String FeesApplied, String DebtorPurposeOfPayment, String TestTransactionCategory) throws Exception {
        try {
            /**
             * Get Creditor Amount shown on table
             * Search the Ebox table to find the unique transaction reference string.
             * Use Xpath Axes with Relative-Xpath using contains,sibling,child,descendant and Ancestor**/

            //TODO Msa... The addition of reLogin on eBox
            reLoginAndOpenStatement(EboxUsername, EboxAgentPassword, EboxDomain);

            //Open the Account statement
            openAccountStatement(ProcessingSuspenseAccountBranchID, ProcessingSuspenseAccount);

            //Scroll to view element
            scrollElementIntoView(By.xpath(eboxStatementTableLastRow));
            highLighterMethod(By.xpath(eboxStatementTableLastRow));

            /**Check for Credit Suspense Account transaction amounts*/
            System.out.println("======================Ebox Table Suspense Credit Account Validation================");
            //Get the number of table/div preceding the main statement table
            int intTableCount = countTableDivs();

            //Get the count of transaction with our unique reference on the table
            int transactionCount = driver.findElements(By.xpath(".//div[contains(text(),'" + systemReference + "')]/parent::td//preceding-sibling::td//ancestor::tr")).size();

            /**Iterate through the number of transaction found to get all the amounts and add them up*/
            //==================Check for transaction amounts=======================================================
            int i = 1; //NB: XPath starts counting at 1, so the second element is at position() 2

            // postingsAccountingCalculator(DebtorControlSum,3);

            //Loop through the Ebox table to get the total amount for the creditor(s)
            do {
                WebElement eboxSuspenseAccountCreditXpathAxis = driver.findElement(By.xpath("(.//div[contains(text(),'" + systemReference + "')]/parent::td//following-sibling::td[2])[position()=" + i + "]"));
                highLighterMethod(By.xpath("(.//div[contains(text(),'" + systemReference + "')]/parent::td//following-sibling::td[2])[position()=" + i + "]"));

                //Get the creditor amount from Ebox and remove non-numeric characters
                String strSuspenseAccountCreditAmount = eboxSuspenseAccountCreditXpathAxis.getText().replaceAll("[^a-zA-Z0-9\\\\._-]", "");

                //Check if the String is empty
                if (strSuspenseAccountCreditAmount.isEmpty()) {
                    //System.out.println("The string is currently empty for this iteration "+i);
                } else {
                    //Get the amount from Ebox and convert it from it string format to an integer or double format
                    doubleSuspenseCreditAccountActualAmount = Double.parseDouble(strSuspenseAccountCreditAmount);

                    //Print the amount
                    System.out.println("The " + i + " Suspense Credit Amount is " + doubleSuspenseCreditAccountActualAmount);

                    //Add the amount found in every iteration
                    Math.round(doubleSuspenseCreditAccountActualTotal += doubleSuspenseCreditAccountActualAmount);
                }
                //Increment index count
                i++;
            } while (i <= transactionCount);

            //==========-----------------=========== THE END OF SIMILARITY ON CALCULATOR -----================---------------
            /**Get totals debit amount (round-off to two decimal places) from ebox*/
            /**Convert string from excel to integer/double */
            doubleDecimalExpectedControlSum = Double.parseDouble(DebtorControlSum);

            //Print the total amount from Ebox
            System.out.println("The toatl Ebox Debit Amount is " + doubleSuspenseCreditAccountActualTotal);

            //==================Perform Suspense Account Credit Validations/Assertions==============================
            if (doubleDecimalExpectedControlSum == doubleSuspenseCreditAccountActualTotal) {
                strSuspenseCreditAccountTransactionAmountAssertion = "PASS";
            } else {
                strSuspenseCreditAccountTransactionAmountAssertion = "FAIL";
            }

            //Set validations results
            strSuspenseCreditAccountTransactionsAmountValidations = strSuspenseCreditAccountTransactionAmountAssertion;
            strSuspenseDebitAccountTransactionsAmountValidations = strSuspenseDebitAccountTransactionAmountAssertion;

            //Take Screenshot
            screenshotLoggerMethod("checkTransactionSuspenseAccountEnd", "Transaction Suspense Account Validations");
            // Transaction Suspense Account Validations
            //Click on clear button
            click(By.id(clearButtonID));

        } catch (Exception e) {
            //ExtentTestManager.getTest().log(Status.FAIL, "Exception=FATAL: Checking available balance on the suspense account failed due to :: " + e.getMessage());
            ExtentTestManager.getTest().log(Status.FAIL, "Exception=FAIL: Checking available balance on the suspense account failed due to :: " + e.getMessage());
            screenshotLoggerMethod("checkTransactionSuspenseAccount", "Transaction Suspense Account Validations Failure");
            stopCurrentTestSetResultsToFailedAndContinueWithNextTest();
        }
    }


    public void postingsAccountingCalculatorInwards(String DebtorControlSum, int colomnValue, String Narrative) throws Exception {

        /**Iterate through the number of transaction found to get all the amounts and add them up*/
        //==================Check for transaction amounts===============================================================
        int i = 1; //NB: XPath starts counting at 1, so the second element is at position() 2

        //Loop through the Ebox table to get the total amount for the Debtors(s)
        do
        {
            //WebElement eboxDebitAccountXpathAxis = driver.findElement(By.xpath("(.//div[contains(text(),'" + Narrative + "')]/parent::td//following-sibling::td["+colomnValue+"])[position()=" + i + "]"));
            //highLighterMethod(By.xpath("(.//div[contains(text(),'" + Narrative + "')]/parent::td//following-sibling::td["+colomnValue+"])[position()=" + i + "]"));

            String NarrativeAs1Line = Narrative.replace("\n", "").replace("\r", "");
            WebElement eboxDebitAccountXpathAxis = driver.findElement(By.xpath("(.//div[contains(text(),'" + NarrativeAs1Line + "')]/parent::td//following-sibling::td["+colomnValue+"])"));
            highLighterMethod(By.xpath("(.//div[contains(text(),'" + NarrativeAs1Line + "')]/parent::td//following-sibling::td["+colomnValue+"])"));

            //Get the Debtor's amount from Ebox and remove non-numeric characters
            String strDebitAmount = eboxDebitAccountXpathAxis.getText().replaceAll("[^a-zA-Z0-9\\\\._-]", "");

            //Check if the String is empty
            if (strDebitAmount.isEmpty()) {
                //System.out.println("The string is currently empty for this iteration "+i);
            } else {
                //Get the amount from Ebox and convert it from it string format to an integer or double format
                doubleDebitAccountActualAmount = Double.parseDouble(strDebitAmount);

                //Print the amount
                System.out.println("The " + i + " Ebox Debit Amount is " + doubleDebitAccountActualAmount);

                //Add the amount found in every iteration
                Math.round(doubleDebitAccountActualTotal += doubleDebitAccountActualAmount);
            }
            //Increment index count
            i++;

        } while (i <= transactionCount);

        /**Get totals debit amount (round-off to two decimal places) from ebox*
         *Convert string from excel to integer/double */
        doubleDecimalExpectedControlSum = Double.parseDouble(DebtorControlSum);

        //Print the total amount from Ebox
        System.out.println("The toatl Ebox Debit Amount is " + doubleDebitAccountActualTotal);

        //==================Perform Debtor Account Validations/Assertions===========================================
        /**The total creditor(s) amount must equal the original Control sum amount*/
        if (doubleDecimalExpectedControlSum == doubleDebitAccountActualTotal)
        {
            //Set the Status for reporting
            strDebitAccountAmountAssertion = "PASS";
        } else {
            //Set the Status for reporting
            strDebitAccountAmountAssertion = "FAIL";
        }
    }


    /**Check Creditor's Credited Amount*/
    public void checkInwardCreditorCreditedAmount(String EboxUsername, String EboxAgentPassword, String EboxDomain, String CreditorBranchId_1, String CreditorAccountNumber_1,String DebtorAccountHolderName, String DebtorControlSum,String DebtorWaiveChargesOption, String FeesApplied,String DebtorPurposeOfPayment, String CreditorDestinationRef_1) throws Exception
    {
        String strCreditorAccountNumber_1="";
        if(CreditorAccountNumber_1.length()==7)
        {
            strCreditorAccountNumber_1 = CreditorAccountNumber_1;
        }else if(CreditorAccountNumber_1.length()>7){
            strCreditorAccountNumber_1 = CreditorAccountNumber_1.substring(CreditorAccountNumber_1.length()-7);
        }else {
            System.out.println("E-Box Accepts accounts with 7 chars, This appears to be less then allowed length");
            ExtentTestManager.getTest().log(Status.INFO, "INFO:: E-Box Accepts accounts with 7 chars, This appears to be less then allowed length");
        }
        CreditorAccountNumber_1 = strCreditorAccountNumber_1;

        try
        {
            /**
             * Get Creditor Amount shown on table
             * Search the Ebox table to find the unique transaction reference string.
             * Use Xpath Axes with Relative-Xpath using contains,sibling,child,descendant and Ancestor**/

            //TODO Msa... The addition of reLogin on eBox
            reLoginAndOpenStatement(EboxUsername,EboxAgentPassword,EboxDomain);

            //Open the Account statement
            openAccountStatement("", CreditorAccountNumber_1);

            //Scroll to view element
            scrollElementIntoView(By.xpath(eboxStatementTableLastRow));

            /**Check for Credit  Account transaction amounts*/
            System.out.println("======================Ebox Table Credit Account Validation==============================");
            //Get the number of table/div preceding the main statement table
            int intTableCount = countTableDivs();




            //TODO:: Fix the Creditor / Receiver statement validation using unique narration from correct window when the payments request was received - DONE
            //Get the count of transaction with our unique reference on the table
            if(strPaymentRequestWindow.equalsIgnoreCase("Current")){
                transactionCount = driver.findElements(By.xpath(".//div[contains(text(),'" + strUniqueTransactionNarration +"')]/parent::td//preceding-sibling::td//ancestor::tr")).size();

                /**Iterate through the number of transaction found to get all the amounts and add them up*/
                if(CreditorDestinationRef_1.equalsIgnoreCase("1Line")){
                    postingsAccountingCalculatorInwards(DebtorControlSum,3,strUniqueTransactionNarration);
                }else {
                    postingsAccountingCalculatorInwards(DebtorControlSum,3,strUniqueTransactionNarration.substring(0,146));}

            }else{
                transactionCount = driver.findElements(By.xpath(".//div[contains(text(),'" + strEboxNarrative +"')]/parent::td//preceding-sibling::td//ancestor::tr")).size();

                /**Iterate through the number of transaction found to get all the amounts and add them up*/
                if(CreditorDestinationRef_1.equalsIgnoreCase("1Line")){
                    postingsAccountingCalculatorInwards(DebtorControlSum,3,strEboxNarrative);
                }else {
                    postingsAccountingCalculatorInwards(DebtorControlSum,3,strEboxNarrative.substring(0,146));}
            };


            //Get the count of transaction with our unique reference on the table
//            transactionCount = driver.findElements(By.xpath(".//div[contains(text(),'" + DebtorAccountHolderName +"')]/parent::td//preceding-sibling::td//ancestor::tr")).size();


            /**Iterate through the number of transaction found to get all the amounts and add them up*/
//            if(CreditorDestinationRef_1.equalsIgnoreCase("1Line")){
//                postingsAccountingCalculatorInwards(DebtorControlSum,3,strUniqueTransactionNarration);
//            }else {
//                postingsAccountingCalculatorInwards(DebtorControlSum,3,strUniqueTransactionNarration.substring(0,146));}
            //postingsAccountingCalculatorInwards(DebtorControlSum,3,"iAmNarration");

            //postingsAccountingCalculatorInwards(DebtorControlSum,3,"4611880 SCR 109.83 4611881 SCR 156.98 4611882 SCR 414.86 4611883 SCR 226.26 4611884 SCR 313.95 4611885 SCR 252.71 4611888 SCR 558.04 461188");





            //Set validations results
            strCreditAccountTransactionsAmountValidations = strCreditAccountTransactionsAmountAssertion;

            //Take Screenshot
            screenshotLoggerMethod("checkCreditorCreditedAmountEnd","Creditor Credited Amount Validations");

            //Click on clear button
            click(By.id(clearButtonID));

            //Reset variable values
            transactionCount = 0;

            System.out.println("=======================================================================================");

        }catch (Exception e){
            ExtentTestManager.getTest().log(Status.FAIL, "Exception=FATAL: Checking available balance failed  due to ::  "+ e.getMessage());
            screenshotLoggerMethod("checkCreditorCreditedAmount","Creditor Credited Amount Validations Failure");
            stopCurrentTestSetResultsToFailedAndContinueWithNextTest();
        }
    }

    public void postingsAccountingCalculator(String DebtorControlSum, int colomnValue,String strWorkflowReferenceUniqueTxt) throws Exception {

        /**Iterate through the number of transaction found to get all the amounts and add them up*/
        //==================Check for transaction amounts===============================================================
        int i = 1; //NB: XPath starts counting at 1, so the second element is at position() 2

        //Loop through the Ebox table to get the total amount for the Debtors(s)
        do {
            WebElement eboxDebitAccountXpathAxis = driver.findElement(By.xpath("(.//div[contains(text(),'" + strWorkflowReferenceUniqueTxt + "')]/parent::td//following-sibling::td[" + colomnValue + "])[position()=" + i + "]"));
            highLighterMethod(By.xpath("(.//div[contains(text(),'" + strWorkflowReferenceUniqueTxt + "')]/parent::td//following-sibling::td[" + colomnValue + "])[position()=" + i + "]"));

            //Get the Debtor's amount from Ebox and remove non-numeric characters
            String strDebitAmount = eboxDebitAccountXpathAxis.getText().replaceAll("[^a-zA-Z0-9\\\\._-]", "");

            //Check if the String is empty
            if (strDebitAmount.isEmpty()) {
                //System.out.println("The string is currently empty for this iteration "+i);
            } else {
                //Get the amount from Ebox and convert it from it string format to an integer or double format
                doubleDebitAccountActualAmount = Double.parseDouble(strDebitAmount);

                //Print the amount
                System.out.println("The " + i + " Ebox Debit Amount is " + doubleDebitAccountActualAmount);

                //Add the amount found in every iteration
                Math.round(doubleDebitAccountActualTotal += doubleDebitAccountActualAmount);
            }
            //Increment index count
            i++;

        } while (i <= transactionCount);

        /**Get totals debit amount (round-off to two decimal places) from ebox*
         *Convert string from excel to integer/double */
        doubleDecimalExpectedControlSum = Double.parseDouble(DebtorControlSum);

        //Print the total amount from Ebox
        System.out.println("The toatl Ebox Debit Amount is " + doubleDebitAccountActualTotal);

        //==================Perform Debtor Account Validations/Assertions===========================================
        /**The total creditor(s) amount must equal the original Control sum amount*/
        if (doubleDecimalExpectedControlSum == doubleDebitAccountActualTotal) {
            //Set the Status for reporting
            strDebitAccountAmountAssertion = "PASS";
        } else {
            //Set the Status for reporting
            strDebitAccountAmountAssertion = "FAIL";
        }
    }

    /**
     * Print the report in a table format
     */
    public void writeEboxAccountReport(String DebtorPurposeOfPayment,String strWorkflowReferenceUniqueTxt) {
        //TODO
        //Create 2 strings to use in validations
        strEboxTestValidations = strDebitAccountTransactionsAmountValidations + strDebitAccountTransactionFeesValidations + strSuspenseDebitAccountTransactionsAmountValidations + strSuspenseDebitAccountTransactionFeesValidations +
                strSuspenseCreditAccountTransactionsAmountValidations + strSuspenseCreditAccountTransactionFeesValidations + strCreditAccountTransactionsAmountValidations + strCreditAccountTransactionFeesValidations +
                strPNLCreditAccountTransactionFeesValidations + strDifferentChargesDebitAccountTransactionFeesValidations;

        //If any status above fails or passes then print the results accordingly
        //If the strEboxTestStatus string does not contain the string FAIL then all the Validations have PASSED
        if (!strEboxTestValidations.contains("FAIL")) {
            String[][] data =
                    {
                            //Headings
                            {"Validation", "Transaction Reference", "Expected Fee Type", "Actual Fee Type", "Expected  Amounts", "Actual Amounts", "Status"},

                            //Contents
                            {"Debit Account                 ", strWorkflowReferenceUniqueTxt, DebtorPurposeOfPayment, strDebitAccountActualFeesDescription, String.valueOf(doubleDecimalExpectedControlSum), String.valueOf(doubleDebitAccountActualTotal), strDebitAccountAmountAssertion},
                            {"Debit Account Fees            ", strWorkflowReferenceUniqueTxt, DebtorPurposeOfPayment, strDebitAccountActualFeesDescription, String.valueOf(doubleExpectedFeesAmountApplied), String.valueOf(doubleDebitAccountActualFeesAmount), strDebitAccountFeesAmountAssertion},
                            {"Suspense Credit Account       ", strWorkflowReferenceUniqueTxt, DebtorPurposeOfPayment, strSuspenseCreditAccountActualFeesDescription, String.valueOf(doubleDecimalExpectedControlSum), String.valueOf(doubleSuspenseCreditAccountActualTotal), strSuspenseCreditAccountTransactionAmountAssertion},
                            {"Suspense Credit Account Fees  ", strWorkflowReferenceUniqueTxt, DebtorPurposeOfPayment, strSuspenseCreditAccountActualFeesDescription, String.valueOf(doubleExpectedFeesAmountApplied), String.valueOf(doubleSuspenseCreditAccountActualFeesAmount), strSuspenseCreditAccountFeesAmountAssertion},
                            {"Suspense Debit Account        ", strWorkflowReferenceUniqueTxt, DebtorPurposeOfPayment, strSuspenseDebitAccountActualFeesDescription, String.valueOf(doubleDecimalExpectedControlSum), String.valueOf(doubleSuspenseDebitAccountActualTotal), strSuspenseDebitAccountTransactionAmountAssertion},
                            {"Suspense Debit Account Fees   ", strWorkflowReferenceUniqueTxt, DebtorPurposeOfPayment, strSuspenseDebitAccountActualFeesDescription, String.valueOf(doubleExpectedFeesAmountApplied), String.valueOf(doubleSuspenseDebitAccountActualFeesAmount), strSuspenseDebitAccountFeesAmountAssertion},
                            {"Creditor Account              ", strWorkflowReferenceUniqueTxt, DebtorPurposeOfPayment, strCreditAccountActualFeesDescription, String.valueOf(doubleDecimalExpectedControlSum), String.valueOf(doubleCreditAccountActualTotal), strCreditAccountTransactionsAmountAssertion},
                            {"Clearing Account              ", strWorkflowReferenceUniqueTxt, DebtorPurposeOfPayment, strDebitAccountActualFeesDescription, String.valueOf(doubleDecimalExpectedControlSum), String.valueOf(doubleClearingAccountCreditAccountActualAmountTotal), strClearingAccountCreditAccountTransactionsAmountAssertion},
                            {"PNL Credit Account Fees       ", strWorkflowReferenceUniqueTxt, DebtorPurposeOfPayment, strPNLCreditAccountActualFeesDescription, String.valueOf(doubleExpectedFeesAmountApplied), String.valueOf(doublePNLCreditAccountFeesAmount), strPNLCreditAccountFeesAmountAssertion},
                            {"Different Charges Fees Account", strWorkflowReferenceUniqueTxt, DebtorPurposeOfPayment, strDifferentChargesDebitAccountActualFeesDescription, String.valueOf(doubleExpectedFeesAmountApplied), String.valueOf(doubleDifferentChargesDebitAccountFeesAmount), strDifferentChargesDebitAccountFeesAmountAssertion}};
            Markup m = MarkupHelper.createTable(data);
            ExtentTestManager.getTest().pass(m);

            /**Reset all the runtime variables  */
            doubleDebitAccountActualAmount = 0;
            doubleDebitAccountActualTotal = 0;
            doubleSuspenseCreditAccountActualAmount = 0;
            doubleSuspenseCreditAccountActualTotal = 0;
            doubleSuspenseDebitAccountActualAmount = 0;
            doubleSuspenseDebitAccountActualTotal = 0;
            doubleCreditAccountActualAmount = 0;
            doubleCreditAccountActualTotal = 0;

        } else if (strEboxTestValidations.contains("FAIL") || strEboxTestValidations.contains("null")) {
            String[][] data =
                    {
                            //Headings
                            {"Validation", "Transaction Reference", "Expected Fee Type", "Actual Fee Type", "Expected  Amounts", "Actual Amounts", "Status"},

                            //Contents
                            {"Debit Account                 ", strWorkflowReferenceUniqueTxt, DebtorPurposeOfPayment, strDebitAccountActualFeesDescription, String.valueOf(doubleDecimalExpectedControlSum), String.valueOf(doubleDebitAccountActualTotal), strDebitAccountAmountAssertion},
                            {"Debit Account Fees            ", strWorkflowReferenceUniqueTxt, DebtorPurposeOfPayment, strDebitAccountActualFeesDescription, String.valueOf(doubleExpectedFeesAmountApplied), String.valueOf(doubleDebitAccountActualFeesAmount), strDebitAccountFeesAmountAssertion},
                            {"Suspense Credit Account       ", strWorkflowReferenceUniqueTxt, DebtorPurposeOfPayment, strSuspenseCreditAccountActualFeesDescription, String.valueOf(doubleDecimalExpectedControlSum), String.valueOf(doubleSuspenseCreditAccountActualTotal), strSuspenseCreditAccountTransactionAmountAssertion},
                            {"Suspense Credit Account Fees  ", strWorkflowReferenceUniqueTxt, DebtorPurposeOfPayment, strSuspenseCreditAccountActualFeesDescription, String.valueOf(doubleExpectedFeesAmountApplied), String.valueOf(doubleSuspenseCreditAccountActualFeesAmount), strSuspenseCreditAccountFeesAmountAssertion},
                            {"Suspense Debit Account        ", strWorkflowReferenceUniqueTxt, DebtorPurposeOfPayment, strSuspenseDebitAccountActualFeesDescription, String.valueOf(doubleDecimalExpectedControlSum), String.valueOf(doubleSuspenseDebitAccountActualTotal), strSuspenseDebitAccountTransactionAmountAssertion},
                            {"Suspense Debit Account Fees   ", strWorkflowReferenceUniqueTxt, DebtorPurposeOfPayment, strSuspenseDebitAccountActualFeesDescription, String.valueOf(doubleExpectedFeesAmountApplied), String.valueOf(doubleSuspenseDebitAccountActualFeesAmount), strSuspenseDebitAccountFeesAmountAssertion},
                            {"Creditor Account              ", strWorkflowReferenceUniqueTxt, DebtorPurposeOfPayment, strCreditAccountActualFeesDescription, String.valueOf(doubleDecimalExpectedControlSum), String.valueOf(doubleCreditAccountActualTotal), strCreditAccountTransactionsAmountAssertion},
                            {"Clearing Account              ", strWorkflowReferenceUniqueTxt, DebtorPurposeOfPayment, strDebitAccountActualFeesDescription, String.valueOf(doubleDecimalExpectedControlSum), String.valueOf(doubleClearingAccountCreditAccountActualAmountTotal), strClearingAccountCreditAccountTransactionsAmountAssertion},
                            {"PNL Credit Account Fees       ", strWorkflowReferenceUniqueTxt, DebtorPurposeOfPayment, strPNLCreditAccountActualFeesDescription, String.valueOf(doubleExpectedFeesAmountApplied), String.valueOf(doublePNLCreditAccountFeesAmount), strPNLCreditAccountFeesAmountAssertion},
                            {"Different Charges Fees Account", strWorkflowReferenceUniqueTxt, DebtorPurposeOfPayment, strDifferentChargesDebitAccountActualFeesDescription, String.valueOf(doubleExpectedFeesAmountApplied), String.valueOf(doubleDifferentChargesDebitAccountFeesAmount), strDifferentChargesDebitAccountFeesAmountAssertion}};
            Markup m = MarkupHelper.createTable(data);
            ExtentTestManager.getTest().fail(m);

            /**Reset all the runtime variables  */
            doubleDebitAccountActualAmount = 0;
            doubleDebitAccountActualTotal = 0;
            doubleSuspenseCreditAccountActualAmount = 0;
            doubleSuspenseCreditAccountActualTotal = 0;
            doubleSuspenseDebitAccountActualAmount = 0;
            doubleSuspenseDebitAccountActualTotal = 0;
            doubleCreditAccountActualAmount = 0;
            doubleCreditAccountActualTotal = 0;
        }
    }

    public void closeEboxBrowser() {
        /**It closes the the browser window on which the focus is set.*/
        driver.close();
    }
}