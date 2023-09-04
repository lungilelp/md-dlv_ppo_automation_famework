package pages;

import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import config.Settings;
import base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import utils.extentreports.ExtentTestManager;
import utils.logs.Log;

/**
 * @author Jonathan Mbuyi Tshitenda [ ABJT439 ]
 * @date created 9/9/2019
 * @package pages
 */
public class CapturePage extends BasePage {
    public CapturePage(WebDriver driver) {

        super(driver);
        Log.info("The current working class: "+this.getClass().getName());
    }

    //***********************************WEB ELEMENTS*******************************************************************
    //******************************************************************************************************************
    String originatorAccountDetailsNameXpath = "(.//div[@class=\"col-6\"])[1]";
    String emptyPageSpaceXpath="/html/body/app-root/div/div/div/payment-capture";
    String barclaysBankAccountbankCodeDropDownName = "bankCodeReference";
    String barclaysBankAccountbankXpath = "/html/body/app-root/div/div/div/payment-capture/div/div/form[1]/div/div[1]/div/div/div[2]/div/div[1]/div/div/span";
    String barclaysBranchIdTxtBoxName = "branchIdReference";
    String barclaysAccountHolderNameTxtBoxName = "accountHolderName";
    String barclaysAccountNumberTxtBoxId = "accountNumber";
    String barclaysPurposeOfPaymentdropDownId = "paymentPurpose_customselect";
    String barclaysControlSumTxtBoxId = "controlSum";
    String barclaysBanckAccountErrorXpath = "//*[@id=\"accountError\"]/span";
    String barclaysBanckAccountErrorToolTipAccountStatusXpath = "//*[@id=\"ngb-popover-3\"]/div/div/label[1]";
    String barclaysBanckAccountDetailsXpath = "//*[@id=\"accountDetail\"]/span";
    String barclaysBanckAccountDetailsToolTipAccountStatusXpath = "//*[@id=\"ngb-popover-4\"]/div/div/label[1]";
    String barclaysRegionCodeName="regionCodeReference";
    String instructionOriginationReferenceTxtBoxId = "instructionOriginationReference";
    String destinationReferenceTxtBoxId = "destinationReference";
    String workflowReferenceTxtBoxId = "workflowReference";
    String additionalRemittanceInfo = "additionalRemittance";
    String addRowButtonXpath2 = "/html/body/app-root/div/div/div/payment-capture/div/div/form[2]/div/div[2]/div/ngx-datatable/div/child::datatable-footer/descendant::button";

    String transferDetailsAccountHolderTxtBoxId0 = "accountHolder0";
    String transferDetailsAccountHolderTxtBoxXpath = "//*[@id=\"accountHolder0\"]";
    String transferDetailsBankCodeTxtBoxId0 = "bankCode0";
    String transferDetailsBranchTxtBoxId0 = "branchId0";
    String transferDetailsRegionCodeId = "regionCode0";
    String transferDetailsRegionCodeXpath = "//*[@id=\"regionCode0\"]";
    String transferDetailsAccountNumberTxtBoxId0 = "accountNumber0";
    String transferDetailsAccountNumberTxtBoxXpath0 = "//*[@id=\"accountNumber0\"]";
    String transferDetailsAmountTxtBoxId0 = "amount0";
    String transferDetailsAccountNumberToolTipAccountStatusXpath = "//*[@id=\"ngb-popover-10\"]";
    String transferDetailsAccountErrorXpath = "//*[@id=\"ngb-popover-10\"]";
    String transferDetailsOriginationReferenceTxtBoxId0 = "originationReference0";
    String transferDetailsDestinationReferenceTxtBoxId0 = "destinationReference0";

    String transferDetailsReviewButtonXpath = "/html/body/app-root/div/div/div/payment-capture/sol-footer/div/div/span[3]/footer-primary/button";
    String transferTotalAmountXpath = "/html/body/app-root/div/div/div/payment-capture/div/div/form[2]/div/div[3]/h4[3]/span";

    String itemisedRadioButtonXpath = "/html/body/app-root/div/div/div/payment-capture/div/div/form[1]/div/div[2]/div[2]/div[2]/div/div[1]/div/div/div/sol-radio[1]/label";
    String consolidatedRadioButtonXpath = "/html/body/app-root/div/div/div/payment-capture/div/div/form[1]/div/div[2]/div[2]/div[2]/div/div[1]/div/div/div/sol-radio[2]/label";

    String forcePostingOption = "/html/body/app-root/div/div/div/payment-capture/div/div/form[1]/div/div[2]/div[2]/div[2]/div/div[2]/div/div[2]/sol-checkbox/label";
    /*****************Dynamic Xpath Elements**********************************/
    String strBeforeTransferDetailsAccountHolderXpath = "//*[@id=\"accountHolder";
    String strAfterTransferAccountHolderXpath = "\"]";

    String strBeforeTransferBankCodeTxtBoxXpath = "//*[@id=\"bankCode";
    String strAfterTransferBankCodeTxtBoxXpath = "\"]";

    String strBeforeTransferRegionCodeTxtBoxXpath = "//*[@id=\"regionCode";
    String strAfterTransferRegionCodeTxtBoxXpath = "\"]";

    String strBeforeTransferBranchTxtBoxIdXpath = "//*[@id=\"branchId";
    String strAfterTransferBranchTxtBoxIdXpath = "\"]";

    String strBeforeTransferAccountNumberTxtBoxXpath = "//*[@id=\"accountNumber";
    String strAfterTransferAccountNumberTxtBoxXpath = "\"]";

    String strBeforeTransferAmountTxtBoxXpath = "//*[@id=\"amount";
    String strAfterTransferAmountTxtBoxIdXpath = "\"]";

    String strBeforeTransferDetailCheckBoxXpath = "(//input[@type='checkbox'])[";
    String strAfterTransferDetailCheckBoxXpath = "]";

    String barclaysAccountPaymentPurposeDropdownPart1Xpath = ".//*[@id=\"paymentPurpose_customselect\"]/div[2]/ul/sol-custom-option/li[contains(text(),'";
    String barclaysAccountPaymentPurposeDropdownPart2Xpath = "')]";

    String transferDetailsAccountNumberTxtBoxPart1Xpath = "//*[@id=\"accountNumber";
    String transferDetailsAccountNumberTxtBoxPart2Xpath = "\"]";

    String transferAddnRemiInfoPart1Xpath = "//*[@id=\"additionalRemittance";
    String transferAddnRemiInfoPart2Xpath = "\"]";

    String transferDetailsAccountNumberToolTipAccountStatusPart1Xpath = "//*[@id=\"ngb-popover-";
    String transferDetailsAccountNumberToolTipAccountStatusPart2Xpath = "\"]";

    String OriginationRefTransferSectionPart1Xpath = "//*[@id=\"originationReference";
    String OriginationRefTransferSectionPart2Xpath = "\"]";

    String DestinationRefTransferSectionPart1Xpath = "//*[@id=\"destinationReference";
    String DestinationRefTransferSectionPart2Xpath = "\"]";

    String transferDetailCheckBoxCss = ".datatable-checkbox > input";
    String chargesWaivedCheckBoxXpath = "/html/body/app-root/div/div/div/payment-capture/div/div/form[1]/div/div[2]/div[2]/div[2]/div/div[2]/div/div/sol-checkbox/label";
    /****************Relative Xpath Elements**********************************/
    String regionCodeTxtFieldXpath = ".//datatable-body/parent::*";
    String debtorHeaderFieldXpath = "(.//div[@class='card-header-titles'])[1]";
    /***************************************************************************/
    @FindBy(how = How.XPATH, using = "/html/body/app-root/div/div/div/payment-capture/div/div/form[1]/div/div[2]/div[2]/div[2]/div/div[2]/sol-checkbox/label")
    public static WebElement chargesWaivedCheckBox;

    @FindBy(how = How.XPATH, using = "(//input[@type='checkbox'])[3]")
    public static WebElement transferDetailCheckBox;

    //******************************************************************************************************************
    public String CreditorTransferTotalAmount = null;
    public String PostingOption = null;

    public String[] CreditorAccountHolder = new String[6];
    public String[] CreditorAccountStatus = new String[6];
    private String[] CreditorBankCode = new String[6];
    private String[] CreditorBranchId = new String[6];
    private String[] CreditorAccountRegionCode = new String[6];
    private String[] CreditorAccountNumber = new String[6];
    private String[] CreditorAmount = new String[6];
    public String[] CreditorAdditionalRemiInfo = new String[6];

    //***********************************STEPS**************************************************************************
    //******************************************************************************************************************

    //TODO: Trim the accepted parameters to have no space... List to be used
    public CapturePage trimExcelData(String DebtorBranchID,String DebtorRegionCode, String DebtorAccountNumber, String DebtorAccountHolderName, String DebtorPurposeOfPayment, String DebtorPostingOption, String DebtorWaiveChargesOption, String DebtorForcePostingOption, String BankCountry)
    {
        String trimmedPOP = DebtorPurposeOfPayment.trim();
        DebtorPurposeOfPayment=trimmedPOP;

        System.out.println("Trimed POP IS:: "+DebtorPurposeOfPayment);
    return this;}

    /***Capture Bank Account Details*/
    public CapturePage captureDebtorDetails(String DebtorBranchID,String DebtorRegionCode, String DebtorAccountNumber, String DebtorAccountHolderName, String DebtorPurposeOfPayment, String DebtorPostingOption, String DebtorWaiveChargesOption, String DebtorForcePostingOption, String BankCountry, String DebtorAdditionalRemitanceInformation,String strWorkflowReferenceUniqueTxt) throws InterruptedException {
        try {

          //  trimExcelData(DebtorBranchID,DebtorRegionCode,DebtorAccountNumber,DebtorAccountHolderName,DebtorPurposeOfPayment,DebtorPostingOption,DebtorWaiveChargesOption,DebtorForcePostingOption,BankCountry);

            //Make sure the page is ready for usage
            waitForPageToBeReady_2();

            //start
            String expectedOgirinatorHeader="Originator account details";
            String actualOriginatorHeader=readText(By.xpath(originatorAccountDetailsNameXpath));
            if(actualOriginatorHeader.contains(expectedOgirinatorHeader))
            {
                // Using assert to verify the expected and actual value
                ExtentTestManager.getTest().log(Status.PASS, "Originator Account Details Header is Correct on Capture");
                screenshotLoggerMethod("Originator Account Details on Capture", "Originator Account Details on Capture");

            }else {
                highLighterMethod(By.xpath(originatorAccountDetailsNameXpath));
                screenshotLoggerMethod("Originator Account Details on Capture", "Originator Account Details on Capture");
                ExtentTestManager.getTest().log(Status.FAIL, "Originator Account Details Header is Incorrect on Capture");
            }
            //end

            //Select Bank Code
            SetScriptTimeout();
            click(By.name(barclaysBankAccountbankCodeDropDownName));
            click(By.xpath(barclaysBankAccountbankXpath));

            //Enter the Branch ID
            //writeText(By.name(barclaysBranchIdTxtBoxName),row.getCell(39).toString());
            writeText(By.name(barclaysBranchIdTxtBoxName), DebtorBranchID);

            //Enter Region Code
            /**Check if the region code text field is displayed*/
            String strDebtorHeaderFieldXpath = driver.findElement(By.xpath((debtorHeaderFieldXpath))).getText().toUpperCase();
            if(strDebtorHeaderFieldXpath.contains("REGION"))
            {
                //Enter Region Code
                click(By.name(barclaysRegionCodeName));
                clearTextField(By.name(barclaysRegionCodeName));
                writeText(By.name(barclaysRegionCodeName),DebtorRegionCode);
            }
            else {
                ExtentTestManager.getTest().log(Status.INFO, "Info:: Region code text field is not available");
            }

            //Enter Account Number
            writeText(By.id(barclaysAccountNumberTxtBoxId), DebtorAccountNumber);
            //Thread.sleep(2000);
            //webDriver.findElement(By.id(String.valueOf(barclaysAccountNumberTxtBoxId))).sendKeys(Keys.ENTER);
            //webDriver.findElement(By.id(String.valueOf(barclaysAccountNumberTxtBoxId))).sendKeys(Keys.TAB);
            //Thread.sleep(2000);

            //Get Account Lookup Amount from PPO TODO


            //Enter the Account holder name
//            highLighterMethod(By.id(String.valueOf(barclaysAccountHolderNameTxtBoxName)));
//            //writeText(By.id(String.valueOf(barclaysAccountHolderNameTxtBoxName)), DebtorAccountHolderName);
//            Thread.sleep(2000);

            //Enter the Account holder namel
            if(BankCountry.equalsIgnoreCase("SYC")){
                clearTextField(By.id(barclaysAccountHolderNameTxtBoxName));
                writeText(By.id(String.valueOf(barclaysAccountHolderNameTxtBoxName)), DebtorAccountHolderName);
            }
            //else {
            //  clickTabKeyAfterElement(By.id(barclaysAccountHolderNameTxtBoxName), By.xpath(emptyPageSpaceXpath));
            //highLighterMethod(By.id(String.valueOf(barclaysAccountHolderNameTxtBoxName)));
            //}


            //Select the Purpose of Payment By Value
            selectdropdownItemByVisibleTextOnUlAndli(By.id(String.valueOf(barclaysPurposeOfPaymentdropDownId)), barclaysAccountPaymentPurposeDropdownPart1Xpath, DebtorPurposeOfPayment, barclaysAccountPaymentPurposeDropdownPart2Xpath);

            PostingOption=DebtorPostingOption;
            //Pick the correct Posting Option
            if (DebtorPostingOption.equalsIgnoreCase("Itemised")) {
                //Click on Itemised Option
                click(By.xpath(itemisedRadioButtonXpath));
            } else if (DebtorPostingOption.equalsIgnoreCase("Consolidated")) {
                //Click on Consolidate Option
                click(By.xpath(consolidatedRadioButtonXpath));
            }


            //Decide on Charges Options
            if (DebtorWaiveChargesOption.equalsIgnoreCase("YES")) {
                //Waive the charegs
                click(By.xpath(chargesWaivedCheckBoxXpath));
                waitForElementToBeClickable(By.xpath(String.valueOf(chargesWaivedCheckBoxXpath)));
            } else if (DebtorWaiveChargesOption.equalsIgnoreCase("NO")) {
                //Dont Waive Charges
                /**INFO*/
                ExtentTestManager.getTest().log(Status.INFO, "Info: Charges are not being waive");
                waitForElementToBeClickable(By.xpath(String.valueOf(chargesWaivedCheckBoxXpath)));
            }


            //Decide on Force Posting Options
            if (DebtorForcePostingOption.equalsIgnoreCase("YES")) {
                //Force Post
                click(By.xpath(forcePostingOption));
            } else if (DebtorForcePostingOption.equalsIgnoreCase("NO")) {
                //Don't Force Post
                ExtentTestManager.getTest().log(Status.INFO, "Info: Force Posting is not applied");
            }

            //OriginationRef append with randomString
            String OriginationReferenceInstr = strWorkflowReferenceUniqueTxt + "-/.InsOR" ;

            //Enter Origination reference(Optional)
            //writeText(By.id(instructionOriginationReferenceTxtBoxId), strWorkflowReferenceUniqueTxt);
            writeText(By.id(instructionOriginationReferenceTxtBoxId), OriginationReferenceInstr);

            //DestinationRef append with randomString
            String DestinationReferenceInstr = strWorkflowReferenceUniqueTxt + "-/.InsDR" ;

            //Enter Destination reference(Optional)
            //writeText(By.id(destinationReferenceTxtBoxId), strWorkflowReferenceUniqueTxt);
            writeText(By.id(destinationReferenceTxtBoxId), DestinationReferenceInstr);

            //Enter Workflow reference(Optional)
            //This will be used random Alphanumeric field will be used by other classes
            writeText(By.id(workflowReferenceTxtBoxId), strWorkflowReferenceUniqueTxt);

            //Enter Additional remittance information
           writeText(By.id(additionalRemittanceInfo), DebtorAdditionalRemitanceInformation);

            /**INFO*/
            ExtentTestManager.getTest().log(Status.INFO, "Info: Debtor Details Captured successfully");

            //Take Screenshot
            screenshotLoggerMethod("captureDebtorDetails", "Captured Debtor Details");

        } catch (Exception e) {
            ExtentTestManager.getTest().log(Status.FAIL, "Info:: Unable to capture Debtor details" + e);
        }
        return this;}

    /**Capture Transfer Details*/
    public CapturePage captureCreditorDetailsForASingleAccount(int numOfCreditors, String ControlSum, String CreditorAccountHolder_1, String CreditorBankCode_1, String CreditorBranchId_1,String CreditorAccountRegionCode_1, String CreditorAccountNumber_1, String CreditorAmount_1,String TestTransactionCategory,String BankCountry) throws InterruptedException {
        try {
            //Scroll down to make the element visible
            scrollDown();scrollDown();


            //Get the number of transaction to be captured create web element rows according to that number(get Number Of Transactions And Create Rows)
            if (numOfCreditors == 1) {
                //****************************captureCreditorDetails_Single***********************************************

                //Enter Bank Code
                scrollDown();
                if(CreditorBankCode_1.contains("ConfigData")){ CreditorBankCode_1= Settings.OffUsBankCode_1;}

                //Enter Bank Code
                writeText(By.id(transferDetailsBankCodeTxtBoxId0), CreditorBankCode_1);

                //Enter Branch ID
                scrollDown();
                writeText(By.id(transferDetailsBranchTxtBoxId0), CreditorBranchId_1);

                //Enter Region Code
                /**Check if the region code text field is displayed*/
                scrollDown();
                String strRegionCodeTxtField = driver.findElement(By.xpath((regionCodeTxtFieldXpath))).getText();
                if(strRegionCodeTxtField.contains("REGION"))
                {
                    /**Check if the region code text field is enabled before entering text*/
                    boolean boolRegionCodeTxtField = isElementEnabled(transferDetailsRegionCodeXpath);
                    if(boolRegionCodeTxtField==true)
                    {
                        //Enter Region Code
                        click(By.id(transferDetailsRegionCodeId));
                        clearTextField(By.id(transferDetailsRegionCodeId));
                        writeText(By.id(transferDetailsRegionCodeId),CreditorAccountRegionCode_1);
                    }
                    else{
                        ExtentTestManager.getTest().log(Status.INFO, "Info:: Region code text field is not enabled");
                    }
                }
                else {
                    ExtentTestManager.getTest().log(Status.INFO, "Info:: Region code text field is not available");
                }


                //Enter Account Number
                scrollDown();
                if(CreditorAccountNumber_1.contains("ConfigData")){ CreditorAccountNumber_1=Settings.OffUsBankAccounNumber_1;}
                 writeText(By.id(transferDetailsAccountNumberTxtBoxId0), CreditorAccountNumber_1);
                //SetScriptTimeout();


                //Enter Account Holder
                /**Check if the region code text field is enabled before entering text
                 * - The account holder text fields is loading in a "disabled state" even when it is not(This must be fixed)
                 * - So we need to click on it to active it ,therefore we will perform a click function before checking it status
                 */
//                click(By.id(transferDetailsAccountHolderTxtBoxId0));
//                boolean boolAccountHolderTxtField = isElementEnabled(transferDetailsAccountHolderTxtBoxXpath);
//                if(boolAccountHolderTxtField==true)
//                {
//
//                    writeText(By.id(transferDetailsAccountHolderTxtBoxId0), CreditorAccountHolder_1);
//                }
//                else{
//                    ExtentTestManager.getTest().log(Status.INFO, "Info:: Account Holder text field is not enabled");
//                }

                scrollDown();
                if(TestTransactionCategory.equalsIgnoreCase("Off_Us"))
                {
                    clickTabKeyAfterElement(By.id(transferDetailsAccountNumberTxtBoxId0), By.xpath(emptyPageSpaceXpath));
                    writeText(By.id(transferDetailsAccountHolderTxtBoxId0), CreditorAccountHolder_1);
                }
                else{
                    if(BankCountry.equalsIgnoreCase("SYC")){

                        clickTabKeyAfterElement(By.id(transferDetailsAccountNumberTxtBoxId0), By.xpath(emptyPageSpaceXpath));
                        clearTextField(By.id(transferDetailsAccountHolderTxtBoxId0));
                        writeText(By.id(transferDetailsAccountHolderTxtBoxId0), CreditorAccountHolder_1);
                    }else {
                        clickTabKeyAfterElement(By.id(barclaysAccountHolderNameTxtBoxName), By.xpath(emptyPageSpaceXpath));
                    }
                }

                //Enter Amount
                scrollDown();
                writeText(By.id(transferDetailsAmountTxtBoxId0), CreditorAmount_1);
                //SetScriptTimeout();
                scrollDown();

                //Check the transfer details Checkbox
                click(By.cssSelector(transferDetailCheckBoxCss));
                // SetScriptTimeout();
                scrollDown();
                //Get the transfer Totals
                CreditorTransferTotalAmount = readText(By.xpath(transferTotalAmountXpath));

                //Take Screenshot
                scrollDown();scrollDown();scrollDown();

                screenshotLoggerMethod("captureCreditorDetails", "Capture Creditor Details");
                /**INFO*/
                ExtentTestManager.getTest().log(Status.INFO, "Info: Creditor Details Captured successfully");
            }
        } catch (Exception e) {
            ExtentTestManager.getTest().log(Status.FAIL, "INFORMATION:: Unable to capture Creditor details" + e);
        }
        return this;}

    /**Capture Transfer Details for multiple accounts*/
    public CapturePage captureCreditorDetailsForMultipleAccounts(
            int numOfCreditors, String ControlSum,String strWorkflowReferenceUniqueTxt,
            String CreditorAccountHolder_1, String CreditorBankCode_1, String CreditorBranchId_1,String CreditorAccountRegionCode_1, String CreditorAccountNumber_1, String CreditorAdditionalRemitanceInformation_1,String CreditorAmount_1,
            String CreditorAccountHolder_2, String CreditorBankCode_2, String CreditorBranchId_2,String CreditorAccountRegionCode_2,String CreditorAccountNumber_2, String CreditorAdditionalRemitanceInformation_2,String CreditorAmount_2,
            String CreditorAccountHolder_3, String CreditorBankCode_3, String CreditorBranchId_3,String CreditorAccountRegionCode_3,String CreditorAccountNumber_3, String CreditorAdditionalRemitanceInformation_3,String CreditorAmount_3,
            String CreditorAccountHolder_4, String CreditorBankCode_4, String CreditorBranchId_4,String CreditorAccountRegionCode_4,String CreditorAccountNumber_4, String CreditorAdditionalRemitanceInformation_4,String CreditorAmount_4,
            String CreditorAccountHolder_5, String CreditorBankCode_5, String CreditorBranchId_5,String CreditorAccountRegionCode_5,String CreditorAccountNumber_5, String CreditorAdditionalRemitanceInformation_5,String CreditorAmount_5,
            String CreditorAccountHolder_6, String CreditorBankCode_6, String CreditorBranchId_6,String CreditorAccountRegionCode_6,String CreditorAccountNumber_6, String CreditorAdditionalRemitanceInformation_6,String CreditorAmount_6, String TestTransactionCategory, String BankCountry) throws InterruptedException {
        try {

            //****************************captureCreditorDetails_Multiple***********************************************
            if (numOfCreditors > 1)
            {
                int x = 0;

                do
                {
                    //This makes the browser to always scroll down in each iteration ,to keep the ADD button visible
                    scrollDown();

                    //Enter Bank Code
                    //Get the Static data from the Config file
                    if(CreditorBankCode_1.contains("ConfigData")){ CreditorBankCode_1=Settings.OffUsBankCode_1;}
                    if(CreditorBankCode_2.contains("ConfigData")){ CreditorBankCode_2=Settings.OffUsBankCode_2;}
                    if(CreditorBankCode_3.contains("ConfigData")){ CreditorBankCode_3=Settings.OffUsBankCode_1;}
                    if(CreditorBankCode_4.contains("ConfigData")){ CreditorBankCode_4=Settings.OffUsBankCode_2;}
                    if(CreditorBankCode_5.contains("ConfigData")){ CreditorBankCode_5=Settings.OffUsBankCode_1;}
                    if(CreditorBankCode_6.contains("ConfigData")){ CreditorBankCode_6=Settings.OffUsBankCode_2;}

                    CreditorBankCode[0] = CreditorBankCode_1;
                    CreditorBankCode[1] = CreditorBankCode_2;
                    CreditorBankCode[2] = CreditorBankCode_3;
                    CreditorBankCode[3] = CreditorBankCode_4;
                    CreditorBankCode[4] = CreditorBankCode_5;
                    CreditorBankCode[5] = CreditorBankCode_6;
                    writeText(By.xpath(strBeforeTransferBankCodeTxtBoxXpath + x + strAfterTransferBankCodeTxtBoxXpath), CreditorBankCode[x]);

                    //Enter Branch ID
                    CreditorBranchId[0] = CreditorBranchId_1;
                    CreditorBranchId[1] = CreditorBranchId_2;
                    CreditorBranchId[2] = CreditorBranchId_3;
                    CreditorBranchId[3] = CreditorBranchId_4;
                    CreditorBranchId[4] = CreditorBranchId_5;
                    CreditorBranchId[5] = CreditorBranchId_6;
                    writeText(By.xpath(strBeforeTransferBranchTxtBoxIdXpath + x + strAfterTransferBranchTxtBoxIdXpath), CreditorBranchId[x]);

                    //Enter Region Code
                    /**Check if the region code text field is displayed*/
                    String strRegionCodeTxtField = driver.findElement(By.xpath((regionCodeTxtFieldXpath))).getText();
                    if(strRegionCodeTxtField.contains("REGION"))
                    {
                        /**Check if the region code text field is enabled before entering text*/
                        boolean boolRegionCodeTxtField = isElementEnabled(transferDetailsRegionCodeXpath);
                        if(boolRegionCodeTxtField==true)
                        {
                            //Enter Region Code
                            click(By.xpath(strBeforeTransferRegionCodeTxtBoxXpath + x + strAfterTransferRegionCodeTxtBoxXpath));
                            clearTextField(By.xpath(strBeforeTransferRegionCodeTxtBoxXpath + x + strAfterTransferRegionCodeTxtBoxXpath));
                            CreditorAccountRegionCode[0] = CreditorAccountRegionCode_1;
                            CreditorAccountRegionCode[1] = CreditorAccountRegionCode_2;
                            CreditorAccountRegionCode[2] = CreditorAccountRegionCode_3;
                            CreditorAccountRegionCode[3] = CreditorAccountRegionCode_4;
                            CreditorAccountRegionCode[4] = CreditorAccountRegionCode_5;
                            CreditorAccountRegionCode[5] = CreditorAccountRegionCode_6;
                            writeText(By.xpath(strBeforeTransferRegionCodeTxtBoxXpath + x + strAfterTransferRegionCodeTxtBoxXpath), CreditorAccountRegionCode[x]);
                        }
                        else{
                            ExtentTestManager.getTest().log(Status.INFO, "Info:: Region code text field is not enabled");
                        }
                    }
                    else {
                        ExtentTestManager.getTest().log(Status.INFO, "Info:: Region code text field is not available");
                    }

                    //Enter Account Number
                    //Get the Static data from the Config file
                    if(CreditorAccountNumber_1.contains("ConfigData")){ CreditorAccountNumber_1=Settings.OffUsBankAccounNumber_1;}
                    if(CreditorAccountNumber_2.contains("ConfigData")){ CreditorAccountNumber_2=Settings.OffUsBankAccounNumber_2;}
                    if(CreditorAccountNumber_3.contains("ConfigData")){ CreditorAccountNumber_3=Settings.OffUsBankAccounNumber_1;}
                    if(CreditorAccountNumber_4.contains("ConfigData")){ CreditorAccountNumber_4=Settings.OffUsBankAccounNumber_2;}
                    if(CreditorAccountNumber_5.contains("ConfigData")){ CreditorAccountNumber_5=Settings.OffUsBankAccounNumber_1;}
                    if(CreditorAccountNumber_6.contains("ConfigData")){ CreditorAccountNumber_6=Settings.OffUsBankAccounNumber_2;}

                    CreditorAccountNumber[0] = CreditorAccountNumber_1;
                    CreditorAccountNumber[1] = CreditorAccountNumber_2;
                    CreditorAccountNumber[2] = CreditorAccountNumber_3;
                    CreditorAccountNumber[3] = CreditorAccountNumber_4;
                    CreditorAccountNumber[4] = CreditorAccountNumber_5;
                    CreditorAccountNumber[5] = CreditorAccountNumber_6;
                    writeText(By.xpath(strBeforeTransferAccountNumberTxtBoxXpath + x + strAfterTransferAccountNumberTxtBoxXpath), CreditorAccountNumber[x]);
                    SetScriptTimeout();

                    //Enter Account Holder
                    /**Check if the region code text field is enabled before entering text
                     * - The account holder text fields is loading in a "disabled state" even when it is not(This must be fixed)
                     * - So we need to click on it to active it ,therefore we will perform a click function before checking it status
                     */
                    click(By.xpath(strBeforeTransferDetailsAccountHolderXpath+ x+strAfterTransferAccountHolderXpath));

                    //Check if the Account holder Textbox field is Enabled or not and perform related function
                    if( driver.findElement(By.xpath(strBeforeTransferDetailsAccountHolderXpath+ x +strAfterTransferAccountHolderXpath)).isEnabled()==false)//Check if disabled
                    {
                        ExtentTestManager.getTest().log(Status.INFO, "Info:: Account Holder text field is not enabled");
                    }
                    else
                    {
                        //Enter Account Holder
                        /**concatenate the before_xpath to the i(index value) and the after_xpath to form your dynamic xpath
                         * Create an array for each required variable to store the values of the Variable that we will use dynamically
                         * in the loop to populate the form*/
                        CreditorAccountHolder[0] = CreditorAccountHolder_1;
                        CreditorAccountHolder[1] = CreditorAccountHolder_2;
                        CreditorAccountHolder[2] = CreditorAccountHolder_3;
                        CreditorAccountHolder[3] = CreditorAccountHolder_4;
                        CreditorAccountHolder[4] = CreditorAccountHolder_5;
                        CreditorAccountHolder[5] = CreditorAccountHolder_6;
                        //Clear the textbox
                        clearTextField(By.xpath(strBeforeTransferDetailsAccountHolderXpath + x + strAfterTransferAccountHolderXpath));
                        //Enter text
                        writeText(By.xpath(strBeforeTransferDetailsAccountHolderXpath + x + strAfterTransferAccountHolderXpath), CreditorAccountHolder[x]);
                    }

                    //Enter Additional remittance information
                    CreditorAdditionalRemiInfo[0]=CreditorAdditionalRemitanceInformation_1;
                    CreditorAdditionalRemiInfo[1]=CreditorAdditionalRemitanceInformation_2;
                    CreditorAdditionalRemiInfo[2]=CreditorAdditionalRemitanceInformation_3;
                    CreditorAdditionalRemiInfo[3]=CreditorAdditionalRemitanceInformation_4;
                    CreditorAdditionalRemiInfo[4]=CreditorAdditionalRemitanceInformation_5;
                    CreditorAdditionalRemiInfo[5]=CreditorAdditionalRemitanceInformation_6;
                    if(CreditorAdditionalRemiInfo[x]!=null) {
                        //clear field
                        clearTextField(By.xpath(transferAddnRemiInfoPart1Xpath + x + transferAddnRemiInfoPart2Xpath));
                        //enter text
                        writeText(By.xpath(transferAddnRemiInfoPart1Xpath + x + transferAddnRemiInfoPart2Xpath), CreditorAdditionalRemiInfo[x]);
                        SetScriptTimeout();
                    }

                    //Origination Reference at transfer section
                    String OriginationReferenceTransfer = strWorkflowReferenceUniqueTxt + "-/.TrnOR" + x ;
                    //Enter Origination reference at transfer section
                    if(PostingOption.contains("Itemised")) {
                        clearTextField(By.xpath(OriginationRefTransferSectionPart1Xpath + x + OriginationRefTransferSectionPart2Xpath));
                        writeText(By.xpath(OriginationRefTransferSectionPart1Xpath + x + OriginationRefTransferSectionPart2Xpath), OriginationReferenceTransfer);
                        SetScriptTimeout();
                    }

                    //Destination Reference at transfer section
                    String DestinationReferenceTransfer = strWorkflowReferenceUniqueTxt + "-/.TrnDR" + x ;
                    clearTextField(By.xpath(DestinationRefTransferSectionPart1Xpath + x + DestinationRefTransferSectionPart2Xpath));
                    writeText(By.xpath(DestinationRefTransferSectionPart1Xpath + x + DestinationRefTransferSectionPart2Xpath), DestinationReferenceTransfer);
                    SetScriptTimeout();

                    //Enter Amount
                    CreditorAmount[0] = CreditorAmount_1;
                    CreditorAmount[1] = CreditorAmount_2;
                    CreditorAmount[2] = CreditorAmount_3;
                    CreditorAmount[3] = CreditorAmount_4;
                    CreditorAmount[4] = CreditorAmount_5;
                    CreditorAmount[5] = CreditorAmount_6;
                    writeText(By.xpath(strBeforeTransferAmountTxtBoxXpath + x + strAfterTransferAmountTxtBoxIdXpath), CreditorAmount[x]);
                    SetScriptTimeout();

                    //Check the transfer details Checkbox
                    /*(int chk =x+2;) the element index(x=0) of the checkbox starts from 2*/
                    int chk = x + 3;
                    click(By.xpath(strBeforeTransferDetailCheckBoxXpath + chk + strAfterTransferDetailCheckBoxXpath));
                    SetScriptTimeout();

                    //Add more creditor rows
                    /*(numOfCreditors-1) so that the number of rows created matches the number of transactions specified in excel since the element index(x=0) starts from 0*/
                    if (x == numOfCreditors - 1) {
                        System.out.println("The number of required rows is reached");

                        //Get the transfer Totals
                        CreditorTransferTotalAmount = readText(By.xpath(transferTotalAmountXpath));

                        //Enter the control Sum
                        writeText(By.id(barclaysControlSumTxtBoxId), ControlSum);

                        //TODO : Do a validation to comparet that the total control sum is equal to the expected control sum from excel
                    } else {
                        //Add Rows accordingly on the web page
                        moveToFocusOnElementAndClick(By.xpath(addRowButtonXpath2));
                        //click(By.xpath(addRowButtonXpath));
                    }

                    //Increment Index
                    x++;

                } while ((numOfCreditors) > x);

                //Take Screenshot
                screenshotLoggerMethod("captureCreditorDetailsForMultipleAccounts", "Capture Creditor Details For MultipleAccounts");
                /**INFO*/
                ExtentTestManager.getTest().log(Status.INFO, "Info: Creditor Details Captured successfully");
            }
        } catch (Exception e) {
            ExtentTestManager.getTest().log(Status.FAIL, "INFORMATION:: Unable to capture Creditor details" + e);
        }
        return this; }


    /**Capture Transfer Details for multiple accounts*/
    public CapturePage captureCreditorDetailsForMultipleAccountsAdhoc(
            int numOfCreditors, String ControlSum, String CreditorAccountHolder_1, String CreditorBankCode_1, String CreditorBranchId_1, String CreditorAccountNumber_1, String CreditorAmount_1) throws InterruptedException {
        //****************************captureCreditorDetails_Multiple***********************************************
        //Enter Account Holder
        writeText(By.id(transferDetailsAccountHolderTxtBoxId0), CreditorAccountHolder_1);

        //Enter Bank Code
        writeText(By.id(transferDetailsBankCodeTxtBoxId0), CreditorBankCode_1);

        //Enter Branch ID
        writeText(By.id(transferDetailsBranchTxtBoxId0), CreditorBranchId_1);

        //Enter Account Number
        writeText(By.id(transferDetailsAccountNumberTxtBoxId0), CreditorAccountNumber_1);
        SetScriptTimeout();

        //Enter Amount
        writeText(By.id(transferDetailsAmountTxtBoxId0), CreditorAmount_1);
        SetScriptTimeout();

        //Check the transfer details Checkbox
        transferDetailCheckBox.click();
        SetScriptTimeout();

        int y = 1;
        if (numOfCreditors > 1) {
            do {

                /**This makes the browser to always scroll down in each iteration ,to keep the ADD button visible*/
                scrollDown();
                moveToFocusOnElementAndClick(By.xpath(addRowButtonXpath2));

                //Enter Account Holder
                writeText(By.xpath(strBeforeTransferDetailsAccountHolderXpath + y + strAfterTransferAccountHolderXpath), CreditorAccountHolder_1);

                //Enter Bank Code
                writeText(By.xpath(strBeforeTransferBankCodeTxtBoxXpath + y + strAfterTransferBankCodeTxtBoxXpath), CreditorBankCode_1);

                //Enter Branch ID
                writeText(By.xpath(strBeforeTransferBranchTxtBoxIdXpath + y + strAfterTransferBranchTxtBoxIdXpath), CreditorBranchId_1);

                //Enter Account Number
                writeText(By.xpath(strBeforeTransferAccountNumberTxtBoxXpath + y + strAfterTransferAccountNumberTxtBoxXpath), CreditorAccountNumber_1);
                SetScriptTimeout();

                //Enter Amount
                writeText(By.xpath(strBeforeTransferAmountTxtBoxXpath + y + strAfterTransferAmountTxtBoxIdXpath), CreditorAmount_1);
                SetScriptTimeout();

                //Check the transfer details Checkbox
                /*(int chk =x+2;) the element index(x=0) of the checkbox starts from 2*/
                int chk = y + 3;
                click(By.xpath(strBeforeTransferDetailCheckBoxXpath + chk + strAfterTransferDetailCheckBoxXpath));
                SetScriptTimeout();
                //Increment Index
                y++;
            } while (y <= numOfCreditors);
        }
        return this;}

    /**Verify all debtor captured details*/
    public CapturePage verifyCapturedTransactionForDebtor(String TestCaseScenariotype, String DebtorAccountStatus)
    {
        if (!getReviewButtonStatus())
        {
            try
            {
                //===========================Debtor Side Capturing validations =============================================
                if(getDebtorAccountNumberFieldStatus())
                {
                    if (!getReviewButtonStatus())
                    {
                        /**If the scenario is a negative scenario then the Error is expected
                         * and therefore the test should be past*/
                        //TODO: Adding the account number status to this check before passing the scenario without doing the refresh ON DEBTOR && account is not open
                        if (TestCaseScenariotype.equalsIgnoreCase("Negative")&& !DebtorAccountStatus.equalsIgnoreCase("Open"))
                        {
                            scrollUp();

                            //Hover over the icon to get the toolTip information
                            mouseHoverMethod(barclaysBanckAccountErrorXpath);

                            // Get the Tool Tip Text that holds the failure reasons(account status)
                            String strToolTipInfo = driver.findElement(By.xpath(barclaysBanckAccountErrorToolTipAccountStatusXpath)).getText();// use to get the first tooltip
                            String strAccountStatus = strToolTipInfo;

                            //Validation : Compare the account status found on PPO against the account status expected in the Excel Data-sheet
                            if (strAccountStatus.equalsIgnoreCase("Account Not Found"))
                            {
                                if (DebtorAccountStatus.equalsIgnoreCase(strAccountStatus))
                                {
                                    screenshotLoggerMethod("AccountStatus", "Account status on account capture");
                                    ExtentTestManager.getTest().info(MarkupHelper.createLabel("Negative Scenario", ExtentColor.GREEN));
                                    ExtentTestManager.getTest().info(MarkupHelper.createLabel( "Actual PPO Account Status:: "+strAccountStatus, ExtentColor.TEAL));
                                    ExtentTestManager.getTest().info(MarkupHelper.createLabel( "Expected PPO Account status :: " +DebtorAccountStatus,ExtentColor.TEAL));
                                    screenshotLoggerMethod("AccountStatus", "Account status on account capture");

                                    //This test should be marked as passed
                                    stopCurrentTestSetResultsToPassedAndContinueWithNextTest();
                                }
                                else
                                {
                                    screenshotLoggerMethod("AccountStatus", "Account status on account capture");

                                    // Using assert to verify the expected and actual value
                                    ExtentTestManager.getTest().warning(MarkupHelper.createLabel("Account Status is :: " + strAccountStatus +" but expected :: "+DebtorAccountStatus, ExtentColor.AMBER));

                                    ExtentTestManager.getTest().info(MarkupHelper.createLabel("Negative Scenario", ExtentColor.GREEN));

                                    //This test should be marked as failed
                                    stopCurrentTestSetResultsToFailedAndContinueWithNextTest();
                                }

                            }
                            else if (strAccountStatus.contains(DebtorAccountStatus))
                            {
                                // Using assert to verify the expected and actual value
                                ExtentTestManager.getTest().pass(MarkupHelper.createLabel("Account Status is :: " + strAccountStatus, ExtentColor.BLUE));

                                ExtentTestManager.getTest().info(MarkupHelper.createLabel("Negative Scenario", ExtentColor.GREEN));
                                screenshotLoggerMethod("AccountStatus", "Account status on account capture");

                                //This test should be marked as passed
                                stopCurrentTestSetResultsToPassedAndContinueWithNextTest();
                            }
                            else
                            {
                                // Using assert to verify the expected and actual value
                                ExtentTestManager.getTest().fail(MarkupHelper.createLabel("Account Status is :: " + strAccountStatus, ExtentColor.BLUE));

                                ExtentTestManager.getTest().info(MarkupHelper.createLabel("Negative Scenario", ExtentColor.GREEN));
                                screenshotLoggerMethod("AccountStatus", "Account status on account capture");

                                //This test should be marked as failed
                                stopCurrentTestSetResultsToFailedAndContinueWithNextTest();
                            }
                        }
                        else if (TestCaseScenariotype.equalsIgnoreCase("Negative")&& DebtorAccountStatus.equalsIgnoreCase("Open"))
                        {
                            /**If the scenario is a Negative scenario and the account status is expected to be open then there could be a problem with the account lookup
                             * and therefore do the refresh !!!!!*/
                            scrollUp();

                            //Refresh that specific textbox field ,by clicking in it and then clicking outside out it
                            if (!barclaysAccountNumberTxtBoxId.contains(DebtorAccountStatus))
                            {
                                click(By.id(barclaysAccountNumberTxtBoxId));
                                clickTabKeyAfterElement(By.id(barclaysAccountNumberTxtBoxId), By.xpath(emptyPageSpaceXpath));
                            }
                        }
                        /**If the scenario is a positive scenario and the account status is expected to be open then there could be a problem with the account lookup
                         * and therefore do the refresh */
                        else if (TestCaseScenariotype.equalsIgnoreCase("Positive") && DebtorAccountStatus.equalsIgnoreCase("Open"))
                        {
                            scrollUp();

                            //Hover over the icon to get the toolTip information
                            mouseHoverMethod(barclaysBanckAccountErrorXpath);

                            //Refresh that specific textbox field ,by clicking in it and then clicking outside out it
                            if (!barclaysAccountNumberTxtBoxId.contains(DebtorAccountStatus))
                            {
                                click(By.id(barclaysAccountNumberTxtBoxId));
                                clickTabKeyAfterElement(By.id(barclaysAccountNumberTxtBoxId), By.xpath(emptyPageSpaceXpath));
                            }
                        }
                    }
                }
                else if (getDebtorAccountNumberFieldStatus() == true)
                {
                    if (getReviewButtonStatus() == false)
                    {
                        /**If the scenario is a positve scenario then there could be a problem with the account lookup
                         * and therefore the test should be failes*/
                        if (TestCaseScenariotype.equalsIgnoreCase("Positive"))
                        {
                            scrollUp();

                            //Hover over the icon to get the toolTip information
                            mouseHoverMethod(barclaysBanckAccountErrorXpath);

                            // Get the Tool Tip Text that holds the failure reasons(account status)
                            String strTooltip = driver.findElement(By.xpath(barclaysBanckAccountErrorToolTipAccountStatusXpath)).getText();// use to get the first tooltip
                            String strAccountStatus = strTooltip;

                            //Validation : Compare the account status found on PPO against the account status expected in the Excel Data-sheet
                            if (strAccountStatus.contains(DebtorAccountStatus))
                            {
                                // Using assert to verify the expected and actual value
                                ExtentTestManager.getTest().fail(MarkupHelper.createLabel("Account Status is :: " + strAccountStatus, ExtentColor.BLUE));
                                ExtentTestManager.getTest().info(MarkupHelper.createLabel( "Actual PPO Account Status:: "+strAccountStatus, ExtentColor.TEAL));
                                ExtentTestManager.getTest().info(MarkupHelper.createLabel( "Expected PPO Account status :: " +DebtorAccountStatus,ExtentColor.TEAL));
                                ExtentTestManager.getTest().info(MarkupHelper.createLabel("Positive Scenario", ExtentColor.GREEN));

                                screenshotLoggerMethod("AccountStatus", "Account status on account capture");

                                //This test should be marked as failed
                                stopCurrentTestSetResultsToFailedAndContinueWithNextTest();
                            }
                        }
                    }
                }
                else if (getDebtorAccountNumberFieldStatus() == false)
                {
                    //Report
                    ExtentTestManager.getTest().info(MarkupHelper.createLabel("No errors in capturing the transaction",ExtentColor.BLUE));
                }
            } catch (Exception e)
            {
                ExtentTestManager.getTest().log(Status.FAIL, "Info:: Unable to send transaction for review  " + e);
            }
        }

        return this;}

    /**Verify all creditor captured details*/
    public CapturePage verifyCapturedTransactionForSingleCreditor(String TestCaseScenariotype, String DebtorAccountStatus, String CreditorAccountStatus_1)
    {
        try
        {
            if(!getDebtorAccountNumberFieldStatus())
            {
                if (!getReviewButtonStatus())
                {
                    String strCreditorAccountNumberErrorBorderDisplayed = getCreditorAccountNumberFieldStatusForSingles();
                    //===========================Creditor Side Capturing validations ===================================
                    if (strCreditorAccountNumberErrorBorderDisplayed.contains("show-error-border"))
                    {
                        /**If the scenario is a negative scenario then the Error is expected
                         * and therefore the test should be past*/
                        //TODO: Adding the account number status to this check before passing the scenario without doing the refresh
                        if (TestCaseScenariotype.equalsIgnoreCase("Negative")&& !CreditorAccountStatus_1.equalsIgnoreCase("Open"))//TODO (Cater for multiples)
                        {
                            //Check if the account captured has thrown an error in the field
                            String strAccountStatusValidation = getCreditorAccountNumberFieldStatusForSingles();

                            // Get the Tool Tip Text that holds the failure reasons(account status)
                            String strCreditorAccountNumberTooltipInfo = driver.findElement(By.xpath("/html/body/app-root/div/div/div/payment-capture/div/div/form[2]/div/div[2]/div/ngx-datatable/div/datatable-body/datatable-selection/datatable-scroller/datatable-row-wrapper/datatable-body-row/div[2]/datatable-body-cell[5]/div/div")).getText();// use to get the first tooltip
                            String strCreditorAccountStatus = strCreditorAccountNumberTooltipInfo.toLowerCase();

                            //Validation : Compare the account status found on PPO against the account status expected in the Excel Data-sheet
                            if (strAccountStatusValidation.toLowerCase().contains("show-error-border"))
                            {
                                if (strCreditorAccountStatus.contains(CreditorAccountStatus_1.toLowerCase()))
                                {
                                    ExtentTestManager.getTest().pass(MarkupHelper.createLabel("Negative Scenario", ExtentColor.GREEN));
                                    ExtentTestManager.getTest().info(MarkupHelper.createLabel( "Actual PPO Account Status:: "+strCreditorAccountStatus, ExtentColor.TEAL));
                                    ExtentTestManager.getTest().info(MarkupHelper.createLabel( "Expected PPO Account status :: " +CreditorAccountStatus_1,ExtentColor.TEAL));
                                    screenshotLoggerMethod("AccountStatus", "Account status on account capture");

                                    //This test should be marked as passed
                                    stopCurrentTestSetResultsToPassedAndContinueWithNextTest();
                                }
                                else
                                {
                                    // Using assert to verify the expected and actual value
                                    ExtentTestManager.getTest().warning(MarkupHelper.createLabel("Account Status is :: " + strCreditorAccountStatus +" but expected :: "+CreditorAccountStatus_1, ExtentColor.AMBER));

                                    ExtentTestManager.getTest().info(MarkupHelper.createLabel("Negative Scenario", ExtentColor.GREEN));
                                    screenshotLoggerMethod("AccountStatus", "Account status on account capture");

                                    //This test should be marked as failed
                                    stopCurrentTestSetResultsToFailedAndContinueWithNextTest();
                                }
                            }
                            else if (strCreditorAccountStatus.contains(CreditorAccountStatus_1))
                            {
                                // Using assert to verify the expected and actual value
                                ExtentTestManager.getTest().pass(MarkupHelper.createLabel("Account Status is :: " + strCreditorAccountStatus, ExtentColor.BLUE));

                                ExtentTestManager.getTest().info(MarkupHelper.createLabel("Negative Scenario", ExtentColor.GREEN));
                                ExtentTestManager.getTest().info(MarkupHelper.createLabel( "Actual PPO Account Status:: "+strCreditorAccountStatus, ExtentColor.TEAL));
                                ExtentTestManager.getTest().info(MarkupHelper.createLabel( "Expected PPO Account status :: " +CreditorAccountStatus_1,ExtentColor.TEAL));
                                screenshotLoggerMethod("AccountStatus", "Account status on account capture");

                                //This test should be marked as passed
                                stopCurrentTestSetResultsToPassedAndContinueWithNextTest();
                            }
                            else if (!TestCaseScenariotype.equalsIgnoreCase("Negative"))
                            {
                                ExtentTestManager.getTest().fail(MarkupHelper.createLabel("Transaction capturing error occurred ", ExtentColor.RED));
                                screenshotLoggerMethod("AccountError", "Account Status");
                                stopCurrentTestSetResultsToFailedAndContinueWithNextTest();
                            }
                            else
                            {
                                // Using assert to verify the expected and actual value
                                ExtentTestManager.getTest().fail(MarkupHelper.createLabel("Account Status is :: " + strCreditorAccountStatus, ExtentColor.BLUE));

                                ExtentTestManager.getTest().info(MarkupHelper.createLabel("Negative Scenario", ExtentColor.GREEN));
                                screenshotLoggerMethod("AccountStatus", "Account status on account capture");

                                //This test should be marked as failed
                                stopCurrentTestSetResultsToFailedAndContinueWithNextTest();
                            }
                        }
                        else if (TestCaseScenariotype.equalsIgnoreCase("Negative")&& CreditorAccountStatus_1.equalsIgnoreCase("Open"))
                        {
                            //TODO: Wait for review button to be enabled after last account refreshing...... Singles
                            //Refresh that specific textbox field ,by clicking in it and then clicking outside out it
                            if (strCreditorAccountNumberErrorBorderDisplayed.toLowerCase().contains("show-error-border"))
                            {
                                click(By.id(transferDetailsAccountNumberTxtBoxId0));
                                clickTabKeyAfterElement(By.id(transferDetailsAccountNumberTxtBoxId0), By.xpath(emptyPageSpaceXpath));
                            }
                        }
                        else {
                            //TODO: Wait for review button to be enabled after last account refreshing...... Singles
                            //Refresh that specific textbox field ,by clicking in it and then clicking outside out it
                            if (strCreditorAccountNumberErrorBorderDisplayed.toLowerCase().contains("show-error-border"))
                            {
                                click(By.id(transferDetailsAccountNumberTxtBoxId0));
                                clickTabKeyAfterElement(By.id(transferDetailsAccountNumberTxtBoxId0), By.xpath(emptyPageSpaceXpath));
                            }
                        }
                    }
                    else if (getReviewButtonStatus() == true)
                    {
                        if (TestCaseScenariotype.equalsIgnoreCase("Positive"))//TODO
                        {
                            scrollUp();

                            //Hover over the icon to get the toolTip information
                            mouseHoverMethod(barclaysBanckAccountDetailsXpath);

                            // Get the Tool Tip Text that holds the failure reasons(account status)
                            String strTooltipInfo = driver.findElement(By.xpath(barclaysBanckAccountDetailsToolTipAccountStatusXpath)).getText();// use to get the first tooltip

                            //Trim
                            String strAccountStatusTrim = trimLeadingCharacters(strTooltipInfo, 16);
                            String strAccountStatus = strAccountStatusTrim;

                            //Validation : Compare the account status found on PPO against the account status expected in the Excel Data-sheet
                            if (DebtorAccountStatus.equalsIgnoreCase(strAccountStatus))
                            {
                                // Using assert to verify the expected and actual value
                                ExtentTestManager.getTest().pass(MarkupHelper.createLabel("Account Status is :: " + strAccountStatus, ExtentColor.BLUE));
                                screenshotLoggerMethod("AccountError", "Account Status");
                            }

                        }
                    }
                }
            }
        } catch (Exception e) {
            ExtentTestManager.getTest().log(Status.FAIL, "Info:: Unable to send transaction for review  " + e);
        }
        return this;}

    /**Verify all creditor captured details for multiple*/
    public CapturePage verifyCapturedTransactionForMultipleCreditors( int numOfCreditors,String TestCaseScenariotype, String DebtorAccountStatus, String CreditorAccountStatus_1, String CreditorAccountStatus_2, String CreditorAccountStatus_3,
                                                               String CreditorAccountStatus_4, String CreditorAccountStatus_5, String CreditorAccountStatus_6) throws Exception
    {
        try
        {
            //Declare variable array
            /**Create an array for each required variable to store the values of the Variable that we will use dynamically
             * in the loop to populate the form*/
            CreditorAccountStatus[0] = CreditorAccountStatus_1;
            CreditorAccountStatus[1] = CreditorAccountStatus_2;
            CreditorAccountStatus[2] = CreditorAccountStatus_3;
            CreditorAccountStatus[3] = CreditorAccountStatus_4;
            CreditorAccountStatus[4] = CreditorAccountStatus_5;
            CreditorAccountStatus[5] = CreditorAccountStatus_6;

            if (numOfCreditors > 1)
            {
                int x = 0;//Keeps count of the Account Number textbox field
                int y = 11; //Keeps count of the tooltip ,which's index starts from 11
                int i = 8;//To keep count of how many times the comparison matches(We initiate to 8 instead of 6 because the tooltip index starts from 2 )
                int numOfCreditorsExcel=numOfCreditors-1;

                /**Check if the review button is enables*/
                if (getReviewButtonStatus() == false)
                {
                    /**If the review button is not enabled*/

                    /**Check the scenario type*/
                    /**======================================Handling a positive scenario=============================*/
                    //TODO: Adding the account number status to this check before passing the scenario without doing the refresh... multiples
                    if (TestCaseScenariotype.equalsIgnoreCase("Positive")&& CreditorAccountStatus[x].equalsIgnoreCase("Open"))
                    {
                        /**Check where the error is*/
                        do {
                            scrollDown();
                            String strCreditorAccountNumberErrorBorderDisplayed = getCreditorAccountNumberFieldStatusForMultiple(transferDetailsAccountNumberTxtBoxPart1Xpath + x + transferDetailsAccountNumberTxtBoxPart2Xpath);

                            if (strCreditorAccountNumberErrorBorderDisplayed.contains("show-error-border"))
                            {
                                //TODO: Wait for review button to be enabled after last account refreshing.... Multiples
                                if(x==numOfCreditorsExcel)
                                {
                                    //Refresh that specific textbox field ,by clicking in it and then clicking outside out it
                                    click(By.xpath(transferDetailsAccountNumberTxtBoxPart1Xpath + x + transferDetailsAccountNumberTxtBoxPart2Xpath));
                                    clickTabKeyAfterElement(By.xpath(transferDetailsAccountNumberTxtBoxPart1Xpath + x + transferDetailsAccountNumberTxtBoxPart2Xpath),By.xpath(strBeforeTransferAmountTxtBoxXpath + x + strAfterTransferAmountTxtBoxIdXpath));
                                    SetScriptTimeout();
                                    scrollDown();
                                }
                                else
                                {
                                    //Refresh that specific textbox field ,by clicking in it and then clicking outside out it
                                    click(By.xpath(transferDetailsAccountNumberTxtBoxPart1Xpath + x + transferDetailsAccountNumberTxtBoxPart2Xpath));
                                    click(By.xpath(barclaysBankAccountbankXpath));
                                }
                            }
                            //Increment Index
                            x++;
                            //y=y+2;//The tooltip index,increments by 2
                        } while ((numOfCreditors) > x);
                    }
                    /**======================================Handling a negative scenario=============================*/
                    else if (TestCaseScenariotype.equalsIgnoreCase("Negative")&& !CreditorAccountStatus[x].equalsIgnoreCase("Open"))
                    {
                        /**If the scenario is a negative scenario then the Error is expected
                         * and therefore the test should be past*/

                        do {

                            /**Find the account textbox field with the error*/
                            String strCreditorAccountNumberErrorBorderDisplayed = getCreditorAccountNumberFieldStatusForMultiple(transferDetailsAccountNumberTxtBoxPart1Xpath + x + transferDetailsAccountNumberTxtBoxPart2Xpath);
                            if (strCreditorAccountNumberErrorBorderDisplayed.contains("show-error-border"))
                            {
                                //Hover over the icon to get the toolTip information
                                /**concatenate the before_xpath to the i(index value) and the after_xpath to form your dynamic xpath*/
                                scrollDown();
                                mouseHoverMethod(transferDetailsAccountNumberTxtBoxPart1Xpath+ x +transferDetailsAccountNumberTxtBoxPart2Xpath);

                                // Get the Tool Tip Text that holds the failure reasons(account status)
                                /**concatenate the before_xpath to the i(index value) and the after_xpath to form your dynamic xpath*/
                                String strCreditorAccountNumberTooltipInfo = driver.findElement(By.xpath(transferDetailsAccountNumberToolTipAccountStatusPart1Xpath + y + transferDetailsAccountNumberToolTipAccountStatusPart2Xpath)).getText();// use to get the first tooltip
                                String strCreditorAccountStatus = strCreditorAccountNumberTooltipInfo;

                                if (strCreditorAccountStatus.toLowerCase().contains(CreditorAccountStatus[x].toLowerCase()))
                                {
                                    ExtentTestManager.getTest().info(MarkupHelper.createLabel("Negative Scenario", ExtentColor.GREEN));
                                    ExtentTestManager.getTest().info(MarkupHelper.createLabel("Actual PPO Account Status:: "+strCreditorAccountStatus , ExtentColor.TEAL));
                                    ExtentTestManager.getTest().info(MarkupHelper.createLabel("Expected PPO Account status :: "+CreditorAccountStatus[x], ExtentColor.TEAL));
                                    screenshotLoggerMethod("AccountStatus", "Account status on account capture");

                                    /**Only Stop the test once all the transaction have been compared*/
                                    if(x>i)
                                    {
                                        //This test should be marked as passed
                                        stopCurrentTestSetResultsToPassedAndContinueWithNextTest();
                                    }
                                    i--;//Decrement I by 1
                                }
                                else if(strCreditorAccountStatus.contains("Account Not Found"))
                                {
                                    screenshotLoggerMethod("AccountStatus", "Account status on account capture");

                                    // Using assert to verify the expected and actual value
                                    ExtentTestManager.getTest().warning(MarkupHelper.createLabel("Account Status is :: " + strCreditorAccountStatus +" but expected :: "+CreditorAccountStatus[x], ExtentColor.AMBER));
                                    ExtentTestManager.getTest().info(MarkupHelper.createLabel("Negative Scenario", ExtentColor.GREEN));

                                    /**Only Stop the test once all the transaction have been compared*/
                                    if(x>i)
                                    {
                                        //This test should be marked as failed
                                        stopCurrentTestSetResultsToFailedAndContinueWithNextTest();
                                    }
                                    i--;//Decrement I by 1
                                }
                                else
                                {
                                    // Using assert to verify the expected and actual value
                                    ExtentTestManager.getTest().fail(MarkupHelper.createLabel("Account Status is :: " + strCreditorAccountStatus, ExtentColor.BLUE));

                                    ExtentTestManager.getTest().info(MarkupHelper.createLabel("Negative Scenario", ExtentColor.GREEN));
                                    screenshotLoggerMethod("AccountStatus", "Account status on account capture");

                                    //This test should be marked as failed
                                    stopCurrentTestSetResultsToFailedAndContinueWithNextTest();
                                }
                            }
                            //Increment Index
                            x++;
                            y=y+2;//The tooltip index,increments by 2
                        } while ((numOfCreditors) > x);
                    }
                    //TODO: Adding the account number status to this check before passing the scenario without doing the refresh... multiples
                    else if (TestCaseScenariotype.equalsIgnoreCase("Negative")&& CreditorAccountStatus[x].equalsIgnoreCase("Open"))
                    {
                        /**Check where the error is*/
                        do {
                            scrollDown();

                            String strCreditorAccountNumberErrorBorderDisplayed = getCreditorAccountNumberFieldStatusForMultiple(transferDetailsAccountNumberTxtBoxPart1Xpath + x + transferDetailsAccountNumberTxtBoxPart2Xpath);
                            if (strCreditorAccountNumberErrorBorderDisplayed.contains("show-error-border"))
                            {
                                //TODO: Wait for review button to be enabled after last account refreshing.... Multiples
                                if(x==numOfCreditorsExcel)
                                {
                                    //Refresh that specific textbox field ,by clicking in it and then clicking outside out it
                                    click(By.xpath(transferDetailsAccountNumberTxtBoxPart1Xpath + x + transferDetailsAccountNumberTxtBoxPart2Xpath));
                                    clickTabKeyAfterElement(By.xpath(transferDetailsAccountNumberTxtBoxPart1Xpath + x + transferDetailsAccountNumberTxtBoxPart2Xpath),By.xpath(strBeforeTransferAmountTxtBoxXpath + x + strAfterTransferAmountTxtBoxIdXpath));
                                    SetScriptTimeout();
                                    scrollDown();
                                }
                                else
                                {
                                    //Refresh that specific textbox field ,by clicking in it and then clicking outside out it
                                    click(By.xpath(transferDetailsAccountNumberTxtBoxPart1Xpath + x + transferDetailsAccountNumberTxtBoxPart2Xpath));
                                    click(By.xpath(barclaysBankAccountbankXpath));
                                }
                            }
                            //Increment Index
                            x++;
                            //y=y+2;//The tooltip index,increments by 2
                        } while ((numOfCreditors) > x);
                    }
                }
                else if (getReviewButtonStatus() == true)
                {
                    /**If the review button is enabled*/

                    if (TestCaseScenariotype.equalsIgnoreCase("Positive"))
                    {
                        scrollUp();

                        //Hover over the icon to get the toolTip information
                        mouseHoverMethod(barclaysBanckAccountDetailsXpath);

                        // Get the Tool Tip Text that holds the failure reasons(account status)
                        String strTooltipInfo = driver.findElement(By.xpath(barclaysBanckAccountDetailsToolTipAccountStatusXpath)).getText();// use to get the first tooltip

                        //Trim
                        String strAccountStatusTrim = trimLeadingCharacters(strTooltipInfo, 16);
                        String strAccountStatus = strAccountStatusTrim;

                        //Validation : Compare the account status found on PPO against the account status expected in the Excel Data-sheet
                        if (DebtorAccountStatus.equalsIgnoreCase(strAccountStatus))
                        {
                            // Using assert to verify the expected and actual value
                            ExtentTestManager.getTest().pass(MarkupHelper.createLabel("Account Status is :: " + strAccountStatus, ExtentColor.BLUE));
                            screenshotLoggerMethod("AccountError", "Account Status");
                        }
                    }
                }
            }
        }
        catch (Exception e)
        {
            ExtentTestManager.getTest().log(Status.FAIL, "Info:: Unable to send transaction for review  " + e);
        }
        return this;}

    /**Send Transaction for Review*/
    public CapturePage sendTransactionForReviewForSingles()
    {
        try
        {
            //Click the Review Button
            click(By.xpath(transferDetailsReviewButtonXpath));
            //SetScriptTimeout();

            /**INFO*/
            ExtentTestManager.getTest().log(Status.INFO, "Info: Transaction sent for review successfully");

        } catch (Exception e)
        {
            ExtentTestManager.getTest().log(Status.FAIL, "Info:: Unable to send transaction for review  " + e);
        }
        return this;}

    public boolean getDebtorAccountNumberFieldStatus()
    {
        /**Verify ToolTip information if they exist*/
        //Check if an Error has occurred on the account capturing process
        boolean boolDebtorAccountErrorDisplayed = isElementDisplayed(barclaysBanckAccountErrorXpath);
        return boolDebtorAccountErrorDisplayed;
    }

    public boolean getReviewButtonStatus()
    {
        /**Verify the status of the review button*/
        Boolean boolReviewButtonEnabled = isElementEnabled(transferDetailsReviewButtonXpath);
        return boolReviewButtonEnabled;
    }

    /**To get the error border of the account number text field*/
    public  String getDebtorAccountNumberFieldStatusForDebtor() throws Exception
    {
        String strDebtorAccountNumberErrorBorderDisplayed;

        //Hover over the icon to get the toolTip information
        mouseHoverMethod(barclaysAccountNumberTxtBoxId);

        //Determine if there are any errors with the account
        strDebtorAccountNumberErrorBorderDisplayed = driver.findElement(By.xpath(".//*[@id='accountNumber']//parent::div//child::input")).getAttribute("class");

        if(!strDebtorAccountNumberErrorBorderDisplayed.contains("invalid form-control"))
        {
            ExtentTestManager.getTest().info("The error border is not displayed on Debtor Account Field");
        }
        else
        {
            ExtentTestManager.getTest().info("The error border is displayed on Debtor Account Field");
        }
        return strDebtorAccountNumberErrorBorderDisplayed;
    }

    /**To get the error border of the account number text field*/
    public  String getCreditorAccountNumberFieldStatusForSingles() throws Exception
    {
        String strCreditorAccountNumberErrorBorderDisplayed;

        //Hover over the icon to get the toolTip information
        mouseHoverMethod(transferDetailsAccountNumberTxtBoxXpath0);

        //Determine if there are any errors with the account
        strCreditorAccountNumberErrorBorderDisplayed = driver.findElement(By.xpath(".//*[@id='accountNumber0']//parent::div//child::input")).getAttribute("class");
        //strCreditorAccountNumberErrorBorderDisplayed = webDriver.findElement(By.xpath("/html/body/app-root/div/div/div/payment-capture/div/div/form[2]/div/div[2]/div/ngx-datatable/div/datatable-body/datatable-selection/datatable-scroller/datatable-row-wrapper[1]/datatable-body-row/div[2]/datatable-body-cell[5]/div/div/div/child::*")).getAttribute("class");


        if(!strCreditorAccountNumberErrorBorderDisplayed.contains("show-error-border"))
        {
            ExtentTestManager.getTest().info("The error border is not displayed");
        }
        else
        {
            ExtentTestManager.getTest().info("The error border is displayed");
        }
        return strCreditorAccountNumberErrorBorderDisplayed;
    }

    /**To get the error border of the account number text field*/
    public  String getCreditorAccountNumberFieldStatusForMultiple(String dynamicXpathLocation) throws Exception
    {
        String strCreditorAccountNumberErrorBorderDisplayed;

        //Hover over the icon to get the toolTip information
        mouseHoverMethod(dynamicXpathLocation);

        //Determine if there are any errors with the account
        strCreditorAccountNumberErrorBorderDisplayed = driver.findElement(By.xpath(dynamicXpathLocation)).getAttribute("class");
        //strCreditorAccountNumberErrorBorderDisplayed = webDriver.findElement(By.xpath(".//*[@id='accountNumber0']//parent::div//child::input")).getAttribute("class");
        //strCreditorAccountNumberErrorBorderDisplayed = webDriver.findElement(By.xpath("/html/body/app-root/div/div/div/payment-capture/div/div/form[2]/div/div[2]/div/ngx-datatable/div/datatable-body/datatable-selection/datatable-scroller/datatable-row-wrapper[1]/datatable-body-row/div[2]/datatable-body-cell[5]/div/div/div/child::*")).getAttribute("class");


        if(!strCreditorAccountNumberErrorBorderDisplayed.contains("show-error-border"))
        {
            ExtentTestManager.getTest().info("The error border is not displayed");
        }
        else
        {
            ExtentTestManager.getTest().info("The error border is displayed");
        }
        return strCreditorAccountNumberErrorBorderDisplayed;
    }
}
