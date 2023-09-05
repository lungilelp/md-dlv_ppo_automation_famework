package helpers;

import base.BasePage;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import Utilities.api.APIUtil;
import config.Settings;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.openqa.selenium.WebDriver;
import utils.extentreports.ExtentTestManager;
import utils.logs.Log;

import static Utilities.DatabaseUtil.databaseHandlerForPocDB;
import static utils.extentreports.ExtentTestManager.codeLogsXML;


/**
 * @author ABABA8T
 * @date created 9/10/2019
 * @package helpers
 */
public class OutwardCreditHandlingModule extends BasePage {
    public OutwardCreditHandlingModule (WebDriver driver) {

        super(driver);
        Log.info("The current working class: "+this.getClass().getName());
    }
    APIUtil apiUtil = new APIUtil(driver);
    /**Global Declarations
     /*Notes:required argumnets
     1. XML File location to test against and Name
     2. PaymentID to pass in the SQL query
     */
    //awanti----------
    String pacs008MessageId = null;
    String urlToGetPacs008Id = null;
    String urlToGetPacs008= null;
    String pacs008Message=null;
    String NACKmessage=null;
    String Pacs002message=null;
    String CBSsecondPositiveResponse=null;
    String CBSsecondNegativeResponse=null;
    String SettlementNotification=null;
    public String SystemRefNumber=null;
    public static int counterForPacs002StatusIncrement, k =0;
    int counterForResponse =2;
    public static int counterForCBSResponse=0;
    FetchPaymentDetailsFromDB gts = new FetchPaymentDetailsFromDB();

    /** Methods*/
    public void getPac008message (String BankCountry, String transactionIdUI,
                                  String ExpectedFirstPacs002FIToFIPmtStsRptTxInfAndStsTxSts_0,
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
                                  String ExpectedFirstPacs002FIToFIPmtStsRptTxInfAndStsStsRsnInfRsnCd_5){
        pacs008MessageId = null;
        pacs008Message=null;
        NACKmessage=null;
        Pacs002message=null;

        try{

            //Get pac008 messgae id
            urlToGetPacs008Id= Settings.sybrinStubDefaultEndPoint +BankCountry+"/message/search/by-e2e/"+transactionIdUI;
            GetMethod getMethod= new GetMethod(urlToGetPacs008Id);
            HttpClient httpclient = new HttpClient();

            do {
                int result = httpclient.executeMethod(getMethod);
                Thread.sleep(10000);
                System.out.println("response code-"+result);
                if(result==200){
                    pacs008MessageId = getMethod.getResponseBodyAsString();
                    Thread.sleep(3000);
                    break;
                }
                k++;
            }while (k <=counterForResponse);
            counterForResponse=0;

            //Get pacs008
            if(pacs008MessageId==null){
                ExtentTestManager.getTest().log(Status.FAIL, "Pacs008 is not generated for transaction-"+transactionIdUI);
            }
            else{
                urlToGetPacs008 = Settings.sybrinStubDefaultEndPoint+BankCountry+"/message?msgId="+pacs008MessageId;
                GetMethod getMethod1= new GetMethod(urlToGetPacs008);
                int result1 = httpclient.executeMethod(getMethod1);
                pacs008Message = getMethod1.getResponseBodyAsString();
                if(pacs008Message==null){
                    ExtentTestManager.getTest().log(Status.FAIL, "Pacs008 is not generated for transaction-"+transactionIdUI);
                }
                else{
                    ExtentTestManager.getTest().log(Status.PASS, "Pacs008 is generated for transaction-"+transactionIdUI);
                    ExtentTestManager.getTest().log(Status.INFO, "Pacs008 Id is-"+pacs008MessageId);
                    ExtentTestManager.getTest().info(MarkupHelper.createLabel("Pacs008 message for transaction id : "+pacs008MessageId +" is below", ExtentColor.TEAL));
                    codeLogsXML(pacs008Message);

                    //create array for all expected messages
                    String[] arrayOfExpectedpacs002errorstatus ={ExpectedFirstPacs002FIToFIPmtStsRptTxInfAndStsTxSts_0,
                            ExpectedFirstPacs002FIToFIPmtStsRptTxInfAndStsTxSts_1,
                            ExpectedFirstPacs002FIToFIPmtStsRptTxInfAndStsTxSts_2,
                            ExpectedFirstPacs002FIToFIPmtStsRptTxInfAndStsTxSts_3,
                            ExpectedFirstPacs002FIToFIPmtStsRptTxInfAndStsTxSts_4,
                            ExpectedFirstPacs002FIToFIPmtStsRptTxInfAndStsTxSts_5};
                    String[] arrayOfExpectedpacs002errorcode ={ExpectedFirstPacs002FIToFIPmtStsRptTxInfAndStsStsRsnInfRsnCd_0,
                            ExpectedFirstPacs002FIToFIPmtStsRptTxInfAndStsStsRsnInfRsnCd_1,
                            ExpectedFirstPacs002FIToFIPmtStsRptTxInfAndStsStsRsnInfRsnCd_2,
                            ExpectedFirstPacs002FIToFIPmtStsRptTxInfAndStsStsRsnInfRsnCd_3,
                            ExpectedFirstPacs002FIToFIPmtStsRptTxInfAndStsStsRsnInfRsnCd_4,
                            ExpectedFirstPacs002FIToFIPmtStsRptTxInfAndStsStsRsnInfRsnCd_5};

                    //----------------drop required messages---------------

                    //when NACK is received- it is common for all the transaction ids in pacs008
                    if(ExpectedFirstPacs002FIToFIPmtStsRptTxInfAndStsTxSts_0.contains("NACK")){
                        createNACKandDrop(pacs008MessageId, BankCountry);
                    }
                    else{
                        createPacs002andDrop (pacs008MessageId, arrayOfExpectedpacs002errorstatus[counterForPacs002StatusIncrement],
                                arrayOfExpectedpacs002errorcode[counterForPacs002StatusIncrement],
                                BankCountry,transactionIdUI);
                        counterForPacs002StatusIncrement++;
                    }
                }
            }
        }
        catch(Exception e1){
            System.out.println(e1);
        }
    }

    public void createNACKandDrop (String pacs008MessageId,
                                   String BankCountry){
        try{
            //create NACK
            NACKmessage = "<Document xmlns=\"urn:transcontrolmessage.makola.bison.absa.co.za\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema  \" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance  \">\n" +
                    "   <CntrlMsg>\n" +
                    "      <MsgId>"+pacs008MessageId+"</MsgId>\n" +
                    "      <MsgType>pacs.008.001.07</MsgType>\n" +
                    "      <CntrlMsgType>NACK</CntrlMsgType>\n" +
                    "      <Result>The 'urn:iso:std:iso:20022:tech:xsd:pacs.008.001.07:PhneNb' element is invalid - The value '+260211366100' is invalid according to its datatype 'urn:iso:std:iso:20022:tech:xsd:pacs.008.001.07:PhoneNumber' - The Pattern constraint failed.\n" +
                    "The 'urn:iso:std:iso:20022:tech:xsd:pacs.008.001.07:MobNb' element is invalid - The value '+260211366100' is invalid according to its datatype 'urn:iso:std:iso:20022:tech:xsd:pacs.008.001.07:PhoneNumber' - The Pattern constraint failed.\n" +
                    "</Result>\n" +
                    "   </CntrlMsg>\n" +
                    "</Document>";

            apiUtil.genericDropPayload(NACKmessage, BankCountry);
            ExtentTestManager.getTest().info(MarkupHelper.createLabel("NACK message is below-", ExtentColor.TEAL));
            codeLogsXML(NACKmessage);
        }
        catch (Throwable e2){

        }
    }

    public void createPacs002andDrop (String pacs008MessageId, String ExpectedFirstPacs002FIToFIPmtStsRptTxInfAndStsTxSts,
                                      String ExpectedFirstPacs002FIToFIPmtStsRptTxInfAndStsStsRsnInfRsnCd,
                                      String BankCountry,String transactionIdUI){

        try{
            //create pacs002
            Pacs002message = "<?xml version=\"1.0\" encoding=\"us-ascii\"?>\n" +
                    "<Document xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns=\"urn:iso:std:iso:20022:tech:xsd:pacs.002.001.09\">\n" +
                    "  <FIToFIPmtStsRpt>\n" +
                    "    <GrpHdr>\n" +
                    "      <MsgId>"+pacs008MessageId+"</MsgId>\n" +
                    "      <CreDtTm>2020-02-21T06:34:00</CreDtTm>\n" +
                    "    </GrpHdr>\n" +
                    "    <TxInfAndSts>\n" +
                    "      <OrgnlGrpInf>\n" +
                    "        <OrgnlMsgId>"+pacs008MessageId+"</OrgnlMsgId>\n" +
                    "        <OrgnlMsgNmId>pacs.008.001.07</OrgnlMsgNmId>\n" +
                    "        <OrgnlCreDtTm>2020-02-21T06:34:00</OrgnlCreDtTm>\n" +
                    "      </OrgnlGrpInf>\n" +
                    "      <OrgnlEndToEndId>"+transactionIdUI+"</OrgnlEndToEndId>\n" +
                    "      <OrgnlTxId>"+transactionIdUI+"</OrgnlTxId>\n" +
                    "      <TxSts>"+ExpectedFirstPacs002FIToFIPmtStsRptTxInfAndStsTxSts+"</TxSts>\n" +
                    "      <StsRsnInf>\n" +
                    "        <Rsn>\n" +
                    "          <Cd>"+ExpectedFirstPacs002FIToFIPmtStsRptTxInfAndStsStsRsnInfRsnCd+"</Cd>\n" +
                    "        </Rsn>\n" +
                    "        <AddtlInf>3</AddtlInf>\n" +
                    "      </StsRsnInf>\n" +
                    "    </TxInfAndSts>\n" +
                    "  </FIToFIPmtStsRpt>\n" +
                    "</Document>";

            apiUtil.genericDropPayload(Pacs002message, BankCountry);
            ExtentTestManager.getTest().info(MarkupHelper.createLabel("Pacs002 message is below-", ExtentColor.TEAL));
            codeLogsXML(Pacs002message);

        }
        catch (Throwable e2){
            System.out.println(e2);
        }
    }

    public void CBSmessages (String transactionIdUI, String amountUI, String BankCountry,
                             String ExpectedFirstPacs002FIToFIPmtStsRptTxInfAndStsTxSts_0,
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
                             String ExpectedFirstPacs002FIToFIPmtStsRptTxInfAndStsStsRsnInfRsnCd_5){
        try{

            //create array for all expected messages
            String[] arrayOfExpectedpacs002errorstatus ={ExpectedFirstPacs002FIToFIPmtStsRptTxInfAndStsTxSts_0,
                    ExpectedFirstPacs002FIToFIPmtStsRptTxInfAndStsTxSts_1,
                    ExpectedFirstPacs002FIToFIPmtStsRptTxInfAndStsTxSts_2,
                    ExpectedFirstPacs002FIToFIPmtStsRptTxInfAndStsTxSts_3,
                    ExpectedFirstPacs002FIToFIPmtStsRptTxInfAndStsTxSts_4,
                    ExpectedFirstPacs002FIToFIPmtStsRptTxInfAndStsTxSts_5};

            String[] arrayOfExpectedpacs002errorcode ={ExpectedFirstPacs002FIToFIPmtStsRptTxInfAndStsStsRsnInfRsnCd_0,
                    ExpectedFirstPacs002FIToFIPmtStsRptTxInfAndStsStsRsnInfRsnCd_1,
                    ExpectedFirstPacs002FIToFIPmtStsRptTxInfAndStsStsRsnInfRsnCd_2,
                    ExpectedFirstPacs002FIToFIPmtStsRptTxInfAndStsStsRsnInfRsnCd_3,
                    ExpectedFirstPacs002FIToFIPmtStsRptTxInfAndStsStsRsnInfRsnCd_4,
                    ExpectedFirstPacs002FIToFIPmtStsRptTxInfAndStsStsRsnInfRsnCd_5};

            //database connection method
            //fetch system ref number using transaction id
            String fetchSysRefQuery = "select system_reference, amount, transaction_id, correlation_id from  mk_seychelles_clearing.clearing_transaction_entity \n" +
                    "where transaction_id ='"+transactionIdUI+"'";
            SystemRefNumber = databaseHandlerForPocDB(fetchSysRefQuery,"system_reference");
            System.out.println("sys ref-"+SystemRefNumber);

            //fetch correlation id number using transaction id
            String fetchCorrelationIdQuery = "select system_reference, amount, transaction_id, correlation_id from  mk_seychelles_clearing.clearing_transaction_entity \n" +
                    "where transaction_id ='"+transactionIdUI+"'";
            String CorrelationId = databaseHandlerForPocDB(fetchCorrelationIdQuery,"correlation_id");
            System.out.println("corr id-"+CorrelationId);

            //fetch -request to CBS

            if(SystemRefNumber!=null && CorrelationId!=null){
                String fetchCBSRequest = "select system_reference, correlation_id, message_value, message_type, \n" +
                        "convert_from(loread(lo_open(message_value::int, x'40000'::int), x'40000'::int), 'UTF8') \n" +
                        "from mk_seychelles_clearing.clearing_message_entity where correlation_id ='"+CorrelationId+"' and message_type = 'SEFT_PAYMENT_REQUEST_OUTWARD'  \n" +
                        "and convert_from(loread(lo_open(message_value::int, x'40000'::int), x'40000'::int), 'UTF8')  like '%<amount>"+amountUI+"</amount>%'";

                String CBSRequest = databaseHandlerForPocDB(fetchCBSRequest,"convert_from");

                ExtentTestManager.getTest().info(MarkupHelper.createLabel("Request to CBS- SEFT_PAYMENT_REQUEST_OUTWARD", ExtentColor.PURPLE));
                if(CBSRequest!=null){
                    codeLogsXML(CBSRequest);
                    ExtentTestManager.getTest().log(Status.PASS, "CBS Request is generated");
                }
                else{
                    ExtentTestManager.getTest().log(Status.FAIL, "CBS Request is not generated");
                }

                String fetchCBSFirstResponse = "select system_reference, correlation_id, message_value, message_type, \n" +
                        "convert_from(loread(lo_open(message_value::int, x'40000'::int), x'40000'::int), 'UTF8') \n" +
                        "from mk_seychelles_clearing.clearing_message_entity where correlation_id ='"+CorrelationId+"' and message_type = 'SEFT_PAYMENT_RESPONSE_OUTWARD'\n" +
                        "and system_reference = '"+SystemRefNumber+"'";

                String CBSFirstResponse = databaseHandlerForPocDB(fetchCBSFirstResponse,"convert_from");

                ExtentTestManager.getTest().info(MarkupHelper.createLabel("First Response From CBS (auto-generated)-SEFT_PAYMENT_RESPONSE_OUTWARD", ExtentColor.PURPLE));
                if(CBSFirstResponse!=null){
                    codeLogsXML(CBSFirstResponse);
                    ExtentTestManager.getTest().log(Status.PASS, "CBS First Response is received");
                }
                else{
                    ExtentTestManager.getTest().log(Status.FAIL, "CBS First Response is not received");
                }

                //handle second response
                //when ACSP is present in test data, create and drop positive response
                if(arrayOfExpectedpacs002errorstatus[counterForCBSResponse].contains("ACSP")){
                    createCBSsecondResponsePositiveandDrop(transactionIdUI, SystemRefNumber,BankCountry);
                    //createSettlementNotificationFromCBSandDrop(SystemRefNumber,BankCountry);
                }
                //else create and drop reversal response
                else{
                    createCBSsecondResponseNegativeandDrop(SystemRefNumber,BankCountry,arrayOfExpectedpacs002errorcode[counterForCBSResponse]);
                }
                counterForCBSResponse++;
            }
            else{
                ExtentTestManager.getTest().log(Status.FAIL, "System reference is not present in database for this transaction");
            }
        }
        catch(Exception e){
            System.out.println(e);
        }
    }

    public void createCBSsecondResponsePositiveandDrop (String transactionIdUI, String SystemRefNumber, String BankCountry){

        try{
            //create second CBS response
            CBSsecondPositiveResponse = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
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

            apiUtil.genericDropPayload(CBSsecondPositiveResponse, BankCountry);
            ExtentTestManager.getTest().info(MarkupHelper.createLabel("MQ Request from CBS (simulated)-MQ_PAYMENT_REQUEST_OUTWARD", ExtentColor.PURPLE));
            codeLogsXML(CBSsecondPositiveResponse);
            //verify if the status of transaction is accepted for clearing from database
            String status= gts.SycOutwardCreditStatusAfterSecondResponse(transactionIdUI);

            if (status.contains("ACCEPTED_FOR_CLEARING")){
                ExtentTestManager.getTest().info(MarkupHelper.createLabel("Status of payment after dropping MQ request from CBS", ExtentColor.PURPLE));
                ExtentTestManager.getTest().log(Status.INFO, status);
            }
            else{
                ExtentTestManager.getTest().info(MarkupHelper.createLabel("Status of transaction is not correct", ExtentColor.PURPLE));
                ExtentTestManager.getTest().log(Status.INFO, status);
            }

            //fetch the response for MQ Request
            String fetchMQResponsetoCBS = "select convert_from(loread(lo_open(message_value::int, x'40000'::int), x'40000'::int), 'UTF8') " +
                    " from mk_seychelles_clearing.clearing_message_entity where " +
                    "system_reference = '"+SystemRefNumber+"' and message_type ='MQ_PAYMENT_RESPONSE_OUTWARD'";

            if(fetchMQResponsetoCBS!=null){
                ExtentTestManager.getTest().info(MarkupHelper.createLabel("MQ Response sent to CBS-MQ_PAYMENT_RESPONSE_OUTWARD", ExtentColor.PURPLE));
                codeLogsXML(fetchMQResponsetoCBS);
                ExtentTestManager.getTest().log(Status.PASS, "MQ Response is sent to CBS");
            }
            else{
                ExtentTestManager.getTest().log(Status.FAIL, "MQ Response is not sent to CBS");
            }
            createSettlementNotificationFromCBSandDrop(SystemRefNumber,BankCountry);
        }
        catch (Throwable e2){
            System.out.println(e2);
        }
    }

    public void createCBSsecondResponseNegativeandDrop (String SystemRefNumber, String BankCountry, String expectedReverseCode){

        try{

            //create second CBS response
            CBSsecondNegativeResponse = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                    "<Reversal xmlns=\"http://www.progressoft.com/EFTReversal\">\n" +
                    "    <MessageInfo xmlns=\"http://www.progressoft.com/EFTReversal\">\n" +
                    "        <MessageVersion>2</MessageVersion>\n" +
                    "    </MessageInfo>\n" +
                    "    <Sender xmlns=\"http://www.progressoft.com/EFTReversal\">\n" +
                    "        <AccountNumber>DUMMY VALUE</AccountNumber>\n" +
                    "        <Name>DUMMY VALUE</Name>\n" +
                    "        <BankCode>DUMMY VALUE</BankCode>\n" +
                    "        <BranchCode>DUMMY VALUE</BranchCode>\n" +
                    "        <CustomerId>DUMMY VALUE</CustomerId>\n" +
                    "    </Sender>\n" +
                    "    <Receiver xmlns=\"http://www.progressoft.com/EFTReversal\">\n" +
                    "        <AccountNumber>DUMMY VALUE</AccountNumber>\n" +
                    "        <Name>DUMMY VALUE</Name>\n" +
                    "        <BankCode>DUMMY VALUE</BankCode>\n" +
                    "        <BranchCode>DUMMY VALUE</BranchCode>\n" +
                    "    </Receiver>\n" +
                    "    <TransactionReference xmlns=\"http://www.progressoft.com/EFTReversal\">\n" +
                    "        <SenderReference>DUMMY VALUE</SenderReference>\n" +
                    "        <SystemReference>"+SystemRefNumber+"</SystemReference>\n" +
                    "        <Source>WS</Source>\n" +
                    "    </TransactionReference>\n" +
                    "    <TransactionInfo xmlns=\"http://www.progressoft.com/EFTReversal\">\n" +
                    "        <Amount>DUMMY VALUE</Amount>\n" +
                    "        <Currency>DUMMY VALUE</Currency>\n" +
                    "        <Purpose>DUMMY VALUE</Purpose>\n" +
                    "        <ValueDate>DUMMY VALUE</ValueDate>\n" +
                    "        <TransactionCode>DUMMY VALUE</TransactionCode>\n" +
                    "        <Narration>DUMMY VALUE</Narration>\n" +
                    "        <InitiatedBy>DUMMY VALUE</InitiatedBy>\n" +
                    "        <InitiatedOn>DUMMY VALUE</InitiatedOn>\n" +
                    "        <CheckedOn nil=\"&quot;true&quot;\"></CheckedOn>\n" +
                    "    </TransactionInfo>\n" +
                    "    <ReversalReason>"+expectedReverseCode+"</ReversalReason>\n" +
                    "</Reversal>";

            apiUtil.genericDropPayload(CBSsecondNegativeResponse, BankCountry);
            ExtentTestManager.getTest().info(MarkupHelper.createLabel("MQ Reversal Request from CSB-MQ_PAYMENT_REVERSAL_OUTWARD", ExtentColor.PURPLE));
            codeLogsXML(CBSsecondNegativeResponse);

        }
        catch (Throwable e2){
            System.out.println(e2);
        }

    }

    public void createSettlementNotificationFromCBSandDrop (String SystemRefNumber, String BankCountry){
        try{
            //create settlement notification response
            SettlementNotification = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                    "<SettlementDateNotification xmlns=\"http://www.progressoft.com/EFTSDNotification\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n" +
                    "   <MessageInfo>\n" +
                    "      <MessageVersion>2</MessageVersion>\n" +
                    "   </MessageInfo>\n" +
                    "   <SystemReference>"+SystemRefNumber+"</SystemReference>\n" +
                    "   <SettlementDate>"+getDateFormatForSettlementNotification()+"T00:00:00</SettlementDate>\n" +
                    "</SettlementDateNotification>";
            apiUtil.genericDropPayload(SettlementNotification, BankCountry);
            ExtentTestManager.getTest().info(MarkupHelper.createLabel("Settlement Notification Received from CBS(simulated)-MQ_PAYMENT_SETTLEMENT_OUTWARD", ExtentColor.PURPLE));
            codeLogsXML(SettlementNotification);
        }
        catch (Throwable e2){
            System.out.println(e2);
        }
    }

}
