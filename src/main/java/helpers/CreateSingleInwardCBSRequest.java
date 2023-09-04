package helpers;

import base.BasePage;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;
import utils.extentreports.ExtentTestManager;
import utils.logs.Log;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static utils.extentreports.ExtentTestManager.codeLogsXML;

public class CreateSingleInwardCBSRequest extends BasePage {

    public CreateSingleInwardCBSRequest (WebDriver driver) {

        super(driver);
        Log.info("The current working class: "+this.getClass().getName());
    }
    public String strCBSRequestFilePath = "./testOutputs/cbs_request/";
    public String strCBSRequestForSingleTxs = null;


    //Generate System Ref
    //public String systemReference = UUID.randomUUID().toString();

    //Generate Sender Ref
    //Random rnd = new Random();
    //long randPmtInfId = 11000000000L + rnd.nextInt(500000000);

    LocalDateTime now = LocalDateTime.now();
    DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
    public String formatDateTime = now.format(format);

    @Test
    public void createCBSRequestForSingleTxns(String SenderBankCode,String SenderName,String SenderBranchID, String ReceiverAccountNumber,String ReceiverName,String ReceiverBankCode,
                                              String Amount, String Currency, String SenderPurposeOfPayment, String NarrationLength, String fileName)
    {
        systemReference = systemReferenceGenerator();
        paymentInfoID = senderReference();
        strUniqueTransactionNarration = TransactionNarration(NarrationLength);

        strCBSRequestForSingleTxs = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "<Request xmlns=\"http://www.progressoft.com/EFTRequest\">\n" +
                "    <MessageInfo xmlns=\"http://www.progressoft.com/EFTRequest\">\n" +
                "        <MessageVersion>2</MessageVersion>\n" +
                "    </MessageInfo>\n" +
                "    <Sender xmlns=\"http://www.progressoft.com/EFTRequest\">\n" +
                "        <AccountNumber>01002400303099</AccountNumber>\n" +
                "        <BankCode>"+SenderBankCode+"</BankCode>\n" +
                "        <Name>"+SenderName+"</Name>\n" +
                "        <BranchCode>"+SenderBranchID+"</BranchCode>\n" +
                "    </Sender>\n" +
                "    <Receiver xmlns=\"http://www.progressoft.com/EFTRequest\">\n" +
                "        <AccountNumber>"+ReceiverAccountNumber+"</AccountNumber>\n" +
                "        <Name>"+ReceiverName+"</Name>\n" +
                "        <BankCode>"+ReceiverBankCode+"</BankCode>\n" +
                "    </Receiver>\n" +
                "    <TransactionReference xmlns=\"http://www.progressoft.com/EFTRequest\">\n" +
                "        <SenderReference>"+paymentInfoID+"</SenderReference>\n" +
                "        <SystemReference>"+systemReference+"</SystemReference>\n" +
                "    </TransactionReference>\n" +
                "    <TransactionInfo xmlns=\"http://www.progressoft.com/EFTRequest\">\n" +
                "        <Amount>"+Amount+"</Amount>\n" +
                "        <Currency>"+Currency+"</Currency>\n" +
                "        <Purpose>"+SenderPurposeOfPayment+"</Purpose>\n" +
                "        <ValueDate>"+formatDateTime+"</ValueDate>\n" +
                "        <TransactionCode>5566</TransactionCode>\n" +
                "        <Narration>"+strUniqueTransactionNarration+"</Narration>\n" +
                "        <CheckedOn nil=\"&quot;true&quot;\"></CheckedOn>\n" +
                "    </TransactionInfo>\n" +
                "</Request>";

        //Save the PAYLOAD to a file
        writeTextToFile(strCBSRequestFilePath,fileName,strCBSRequestForSingleTxs);

        ExtentTestManager.getTest().log(Status.PASS, "Status=PASS:: Inward Payment XML Payload below");
        codeLogsXML(strCBSRequestForSingleTxs);
    }
}