package helpers;


import base.BasePage;
import org.openqa.selenium.WebDriver;
import utils.logs.Log;

public class CreateCBSMessages extends BasePage {
    public CreateCBSMessages(WebDriver driver) {

        super(driver);
        Log.info("The current working class: "+this.getClass().getName());
    }
    String MQRequestFromCBS = null;
    String SettlementNotiFromCBS= null;
    String ReversalNotiFromCBS = null;
    String NACKResponseFromCBS = null;

    /** Methods*/
    public String createMQRequestFromCBS(String SystemRefNumber){
        try{
            MQRequestFromCBS = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                    "<Request xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns=\"http://www.progressoft.com/EFTRequest\">\n" +
                    "   <MessageInfo>\n" +
                    "      <MessageVersion>2</MessageVersion>\n" +
                    "   </MessageInfo>\n" +
                    "   <Sender>\n" +
                    "      <AccountNumber>DUMMY VALUE</AccountNumber>\n" +
                    "      <Name>DUMMY VALUE</Name>\n" +
                    "      <BankCode>1</BankCode>\n" +
                    "      <BranchCode>10</BranchCode>\n" +
                    "      <CustomerId>DUMMY VALUE</CustomerId>\n" +
                    "   </Sender>\n" +
                    "   <Receiver>\n" +
                    "      <AccountNumber>DUMMY VALUE</AccountNumber>\n" +
                    "      <Name>DUMMY VALUE</Name>\n" +
                    "      <BankCode>6</BankCode>\n" +
                    "      <BranchCode>01</BranchCode>\n" +
                    "   </Receiver>\n" +
                    "   <TransactionReference>\n" +
                    "      <SenderReference>DUMMY VALUE</SenderReference>\n" +
                    "      <SystemReference>"+SystemRefNumber+"</SystemReference>\n" +
                    "      <Source>WS</Source>\n" +
                    "   </TransactionReference>\n" +
                    "   <TransactionInfo>\n" +
                    "      <Amount>DUMMY VALUE</Amount>\n" +
                    "      <Currency>DUMMY VALUE</Currency>\n" +
                    "      <Purpose>DUMMY VALUE</Purpose>\n" +
                    "      <ValueDate>2021-06-15T00:00:00</ValueDate>\n" +
                    "      <TransactionCode>DUMMY VALUE</TransactionCode>\n" +
                    "      <Narration>DUMMY VALUE</Narration>\n" +
                    "      <InitiatedBy>DUMMY VALUE</InitiatedBy>\n" +
                    "      <InitiatedOn>2021-06-15T11:10:31.733</InitiatedOn>\n" +
                    "      <CheckedOn xsi:nil=\"true\" />\n" +
                    "   </TransactionInfo>\n" +
                    "</Request>";

        }
        catch(Exception e){
            System.out.println(e);
        }

        return MQRequestFromCBS;
    }

    public String createSettlementNotification(String SystemRefNumber){
        try{
            SettlementNotiFromCBS = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                    "<SettlementDateNotification xmlns=\"http://www.progressoft.com/EFTSDNotification\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n" +
                    "   <MessageInfo>\n" +
                    "      <MessageVersion>2</MessageVersion>\n" +
                    "   </MessageInfo>\n" +
                    "   <SystemReference>"+SystemRefNumber+"</SystemReference>\n" +
                    "   <SettlementDate>"+getDateFormatForSettlementNotification()+"T00:00:00</SettlementDate>\n" +
                    "</SettlementDateNotification>";
        }
        catch(Exception e){
            System.out.println(e);
        }

        return SettlementNotiFromCBS;
    }

    public String createReversalNotification(String SystemRefNumber, String CreditorBankRejectsRequestMessageReversalCode, String DestFinancialInstitution,
                                             String Amount, String Currency,String AddRmtInf,String OriginatorName,String OriginatorId ,
                                             String OriginatorAccountNo,String DestinationName,
                                             String DestinationAccountNumber){
        try{
            ReversalNotiFromCBS = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                    "<Reversal xmlns=\"http://www.progressoft.com/EFTReversal\">\n" +
                    "    <MessageInfo xmlns=\"http://www.progressoft.com/EFTReversal\">\n" +
                    "        <MessageVersion>2</MessageVersion>\n" +
                    "    </MessageInfo>\n" +
                    "    <Sender xmlns=\"http://www.progressoft.com/EFTReversal\">\n" +
                    "        <AccountNumber>"+OriginatorAccountNo+"</AccountNumber>\n" +
                    "        <Name>"+OriginatorName+"</Name>\n" +
                    "        <BankCode>1</BankCode>\n" +
                    "        <BranchCode>DUMMY VALUE</BranchCode>\n" +
                    "        <CustomerId>"+OriginatorId+"</CustomerId>\n" +
                    "    </Sender>\n" +
                    "    <Receiver xmlns=\"http://www.progressoft.com/EFTReversal\">\n" +
                    "        <AccountNumber>"+DestinationAccountNumber+"</AccountNumber>\n" +
                    "        <Name>"+DestinationName+"</Name>\n" +
                    "        <BankCode>"+DestFinancialInstitution+"</BankCode>\n" +
                    "        <BranchCode>DUMMY VALUE</BranchCode>\n" +
                    "    </Receiver>\n" +
                    "    <TransactionReference xmlns=\"http://www.progressoft.com/EFTReversal\">\n" +
                    "        <SenderReference>"+AddRmtInf+"</SenderReference>\n" +
                    "        <SystemReference>"+SystemRefNumber+"</SystemReference>\n" +
                    "        <Source>WS</Source>\n" +
                    "    </TransactionReference>\n" +
                    "    <TransactionInfo xmlns=\"http://www.progressoft.com/EFTReversal\">\n" +
                    "        <Amount>"+Amount+"</Amount>\n" +
                    "        <Currency>"+Currency+"</Currency>\n" +
                    "        <Purpose>DUMMY VALUE</Purpose>\n" +
                    "        <ValueDate>"+getDateFormatForSettlementNotification()+"T00:00:00</ValueDate>\n" +
                    "        <TransactionCode>DUMMY VALUE</TransactionCode>\n" +
                    "        <Narration>DUMMY VALUE</Narration>\n" +
                    "        <InitiatedBy>DUMMY VALUE</InitiatedBy>\n" +
                    "        <InitiatedOn>DUMMY VALUE</InitiatedOn>\n" +
                    "        <CheckedOn nil=\"&quot;true&quot;\"></CheckedOn>\n" +
                    "    </TransactionInfo>\n" +
                    "    <ReversalReason>"+CreditorBankRejectsRequestMessageReversalCode+"</ReversalReason>\n" +
                    "</Reversal>";
        }
        catch(Exception e){
            System.out.println(e);
        }
        return ReversalNotiFromCBS;
    }

}
