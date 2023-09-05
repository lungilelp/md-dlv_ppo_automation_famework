package helpers;


import Utilities.api.APIUtil;
import base.BasePage;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import org.openqa.selenium.WebDriver;
import utils.extentreports.ExtentTestManager;
import utils.logs.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import static utils.extentreports.ExtentTestManager.codeLogsXML;

public class ValidatePaymentMessageFields  extends BasePage  {

    public ValidatePaymentMessageFields(WebDriver driver) {

        super(driver);
        Log.info("The current working class: "+this.getClass().getName());
    }

    /*Global Varible declaration */
    public String chargeEnquiryRequest = null;
    private ThreadLocal<String> testName = new ThreadLocal<>();

    APIUtil apiUtil = new APIUtil(driver);
//    loginPage = new LoginPage(driver);
//    welcomePage = new WelcomePage(driver);
//    bulkPaymentSearchPage = new BulkPaymentSearchPage();

    //Future Date,Present Date or Past Date formation - Requested Execution Date,Requested Collection Date
    public static String formattingInputDate(String inputDate, String dateFormat) {
        Calendar calendar = Calendar.getInstance();

        if (inputDate.contains("FutureDate"))
        {
            calendar.add(Calendar.DAY_OF_YEAR, 1);
            return new SimpleDateFormat(dateFormat).format(calendar.getTime());

        } else if (inputDate.contains("PastDate"))
        {
            calendar.add(Calendar.DAY_OF_YEAR, -1);
            return new SimpleDateFormat(dateFormat).format(calendar.getTime());
        } else if (inputDate.contains("InvalidDate"))
        {
            return "0000-00-00";
        }else
        {
            return new SimpleDateFormat(dateFormat).format(calendar.getTime());
        }
    }
//    /* Message Identification tag Structure Validation */
    public String validateMsgIdStructure(String messageIdentification,String name,
                                         String transactionId,String date) {

        if(messageIdentification.contains("DUP")) {
            return messageIdentification.replaceAll("DUP","") + name + "/" + date + "/" + date + "000";
        }else if(messageIdentification.contains("InvalidSourceId")){
            return messageIdentification.replaceAll("InvalidSourceId","INV") +  "/" + date + "/" + date + "000";
        }else if(messageIdentification.contains("InvalidCreationDate")){
            return messageIdentification.replaceAll("InvalidCreationDate","") + name + "/" + "000000" + "/" + date + "000";
        }else if(messageIdentification.contains("InvalidUniqueID")){
            return messageIdentification.replaceAll("InvalidUniqueID","") + name + "/" + date + "/" + date;
        }else {
            return messageIdentification + name + "/" + date + "/" + transactionId;
        }
    }
//
//    /* Verify fees Setup in teller */
    public void verifyFeesSetup(String AccountCurrency, String AccountNumber,
                                String AccountType, String BranchNumber, String BankCountry,
                                String ItemCount, String PaymentMethod, String ChannelId, String ChannelReference,
                                String TransactionAmount, String TransactionCurrency, String PurposeOfPayment) throws Throwable {

        //Create Charge Enquiry Request
        chargeEnquiryRequest = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<ChargeEnquiryRequest>\n" +
                "    <accountCurrency>" + AccountCurrency + "</accountCurrency>\n" +
                "    <accountNumber>" + AccountNumber + "</accountNumber>\n" +
                "    <accountType>" + AccountType + "</accountType>\n" +
                "    <branchNumber>" + BranchNumber + "</branchNumber>\n" +
                "    <country>" + BankCountry + "</country>\n" +
                "    <itemCount>" + ItemCount + "</itemCount>\n" +
                "    <methodOfPayment>" + PaymentMethod + "</methodOfPayment>\n" +
                "    <sourceChannel>" + ChannelId + "</sourceChannel>\n" +
                "    <sourceChannelReference>" + ChannelReference + "</sourceChannelReference>\n" +
                "    <transactionAmount>" + TransactionAmount + "</transactionAmount>\n" +
                "    <transactionCurrency>" + TransactionCurrency + "</transactionCurrency>\n" +
                "    <transactionType>" + PurposeOfPayment + "</transactionType>\n" +
                "</ChargeEnquiryRequest>";

        ExtentTestManager.getTest().info(MarkupHelper.createLabel("Charge Inquiry Request", ExtentColor.TEAL));
        codeLogsXML(chargeEnquiryRequest);

        ExtentTestManager.getTest().info(MarkupHelper.createLabel("Charge Inquiry Response", ExtentColor.TEAL));
        apiUtil.PostXmlRequestPayload(chargeEnquiryRequest, BankCountry);

    }
//
//    public void UIVerificationForChannelPayments(String ServiceAgentCommercialOperationsUsername,String ServiceAgentCommercialOperationsPassword,
//                                                 String messageId,String correlationId,String[] paymentInfoId,String fileStatus,String[] processingStatus) throws Exception {
//        //Login to PPO as Service Agent
//        loginPage.loginToPPOAsServiceAgent(ServiceAgentCommercialOperationsUsername,
//                ServiceAgentCommercialOperationsPassword);
//        Thread.sleep(2000);
//
//        //Navigate to Bulk File Listing Screen
//        click(By.id("bulk-files"));
//        Thread.sleep(10000);
//
//        //Find & Highlight the Payment Message File Id
//        waitForElementToBeVisible(By.xpath("//*[text()='"+messageId.replaceAll("/", "")+"']"));
//        driver.findElement(By.xpath("//*[text()='" + messageId.replaceAll("/", "") + "']"));
//        highLighterMethod(By.xpath("//*[text()='" + messageId.replaceAll("/", "") + "']"));
//
//        //Validate transactions present inside payment instruction file
//        bulkPaymentSearchPage.validatePaymentInstructionBulkPaymentSearch(correlationId,paymentInfoId, fileStatus,processingStatus);
//
//        //Logout of PPO Application
//        welcomePage.logout();
//
//        driver.close();
//    }
//
//    @Override
//    public String getTestName()
//    {
//        return testName.get();
//    }
}
