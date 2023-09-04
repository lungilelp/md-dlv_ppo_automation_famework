package helpers;

import base.BasePage;
import com.aventstack.extentreports.Status;
import Utilities.api.APIUtil;
import org.openqa.selenium.WebDriver;
import utils.extentreports.ExtentTestManager;
import utils.logs.Log;

import java.sql.SQLException;
import java.util.regex.Pattern;

import static Utilities.DatabaseUtil.databaseHandlerForPocDB;
import static utils.extentreports.ExtentTestManager.codeLogsXML;

public class ValidateResponeMessages extends BasePage {
    public ValidateResponeMessages(WebDriver driver) {

        super(driver);
        Log.info("The current working class: "+this.getClass().getName());
    }
    /* LOCAL VARIABLES */
    APIUtil apiUtil = new APIUtil(driver);
    CreateCBSMessages createCBSMessages = new CreateCBSMessages(driver);

    public ValidateResponeMessages fetchAndValidatePain002Message(String MessageId,String strWorkflowReferenceUniqueTxt ) throws SQLException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        String queryForPain002MessageReceivedAtDLV = "select convert_from(loread(lo_open(message_value::int, x'40000'::int), x'40000'::int), 'UTF8'), message_type from mk_sfi_channel_gateway.ebox_message_entity where \n" +
                "convert_from(loread(lo_open(message_value::int, x'40000'::int), x'40000'::int), 'UTF8') like '%DLV has received message.%' and " +
                "convert_from(loread(lo_open(message_value::int, x'40000'::int), x'40000'::int), 'UTF8') like '%" + MessageId + strWorkflowReferenceUniqueTxt + "%'";
        String Pain002_DLVReceivedMessage = databaseHandlerForPocDB(queryForPain002MessageReceivedAtDLV, "convert_from");
        if (Pain002_DLVReceivedMessage != null) {
            ExtentTestManager.getTest().log(Status.INFO, "Pain002 is below");
            codeLogsXML(Pain002_DLVReceivedMessage);

            //Verifications

            //1. OrgnlMsgId
            if (Pain002_DLVReceivedMessage.contains("OrgnlMsgId>" + MessageId + strWorkflowReferenceUniqueTxt + "<")
            ) {
                ExtentTestManager.getTest().log(Status.PASS, "Verification 1 : OrgnlMsgId is same as Pain001 Message id");
            } else {
                ExtentTestManager.getTest().log(Status.FAIL, "Verification 1 : OrgnlMsgId is not same as Pain001 Message id");
            }

            //2. OrgnlPmtInfId
            if (Pain002_DLVReceivedMessage.contains("OrgnlPmtInfId>" + MessageId + strWorkflowReferenceUniqueTxt + "<")) {
                ExtentTestManager.getTest().log(Status.PASS, "Verification 2 : OrgnlPmtInfId is same as Pain001 Message id");
            } else {
                ExtentTestManager.getTest().log(Status.FAIL, "Verification 2 : OrgnlPmtInfId is not same as Pain001 Message id");
            }

            //3. PmtInfSts ACTC
            if (Pain002_DLVReceivedMessage.contains("PmtInfSts>ACTC<")) {
                ExtentTestManager.getTest().log(Status.PASS, "Verification 3 : PmtInfSts is ACTC");
            } else {
                ExtentTestManager.getTest().log(Status.FAIL, "Verification 3 : PmtInfSts is not ACTC");
            }

            //4. Status Reason code MRCV
            if (Pain002_DLVReceivedMessage.contains("Cd>MRCV<")) {
                ExtentTestManager.getTest().log(Status.PASS, "Verification 4 : Status Reason code is MRCV");
            } else {
                ExtentTestManager.getTest().log(Status.FAIL, "Verification 4 : Status Reason code is MRCV");
            }

            //5. OrgnlEndToEndId
            if (Pain002_DLVReceivedMessage.contains("OrgnlEndToEndId>" + MessageId + strWorkflowReferenceUniqueTxt + "<")) {
                ExtentTestManager.getTest().log(Status.PASS, "Verification 5 : OrgnlEndToEndId is same as Pain001 Message id");
            } else {
                ExtentTestManager.getTest().log(Status.FAIL, "Verification 5 : OrgnlEndToEndId is not same as Pain001 Message id");
            }
        } else {
            ExtentTestManager.getTest().log(Status.FAIL, "Pain002 is not generated");
        }

        return this; }

    public String fetchAndValidateRequestSentToCBS(String Correlation_Id,String OriginatorName,String OriginatorId,
                                                 String strWorkflowReferenceUniqueTxt,String OriginatorAccountNo,
                                                   String OriginatorBranchId,String Amount,String DestFinancialInstitution,
                                                   String DestinationBrnchId,String DestinationName,String DestinationAccountNumber,
                                                   String ServiceLevel, String AddRmtInf,String PurpOfPay,String Currency) throws SQLException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        String QueryToFetchCBSRequest = "select convert_from(loread(lo_open(message_value::int, x'40000'::int), x'40000'::int), 'UTF8') from mk_seychelles_clearing.clearing_message_entity \n" +
                "where correlation_id ='" + Correlation_Id + "' and message_type='SEFT_PAYMENT_REQUEST_OUTWARD'";
        String CBSRequestSEFTP2 = databaseHandlerForPocDB(QueryToFetchCBSRequest, "convert_from");
        if (CBSRequestSEFTP2 != null) {
            ExtentTestManager.getTest().log(Status.PASS, "CBS Request is generated");
            codeLogsXML(CBSRequestSEFTP2);

            //Verifications
            if (CBSRequestSEFTP2.contains("<valuedate>" + getDateFormatForSettlementNotification() + "</valuedate>")) {
                ExtentTestManager.getTest().log(Status.PASS, "Verification 1 : valuedate is correct");
            } else {
                ExtentTestManager.getTest().log(Status.FAIL, "Verification 1 : valuedate is not correct");
            }
            if (CBSRequestSEFTP2.contains("<senderCustName>" + OriginatorName + "</senderCustName>")) {
                ExtentTestManager.getTest().log(Status.PASS, "Verification 2 : Sender cust name is same as Debtor name in pain001");
            } else {
                ExtentTestManager.getTest().log(Status.FAIL, "Verification 2 : Sender cust name is not same as Debtor name in pain001");
            }
            if (CBSRequestSEFTP2.contains("<customerID>" + OriginatorId + strWorkflowReferenceUniqueTxt + "</customerID>")) {
                ExtentTestManager.getTest().log(Status.PASS, "Verification 3 : Customer id is same as Debtor Id in pain001");
            } else {
                ExtentTestManager.getTest().log(Status.FAIL, "Verification 3 : Customer id is not same as Debtor Id in pain001");
            }
            if (CBSRequestSEFTP2.contains("<senderCustAccount>" + OriginatorAccountNo + "</senderCustAccount>")) {
                ExtentTestManager.getTest().log(Status.PASS, "Verification 4 : Sender cust account is same as Debtor account Id in pain001");
            } else {
                ExtentTestManager.getTest().log(Status.FAIL, "Verification 4 : Sender cust account is not same as Debtor account Id in pain001");
            }
            if (CBSRequestSEFTP2.contains("<sendingBank>\n" +
                    "                <code>01</code>\n" +
                    "            </sendingBank>")) {
                ExtentTestManager.getTest().log(Status.PASS, "Verification 5 : Sender bank is 01");
            } else {
                ExtentTestManager.getTest().log(Status.FAIL, "Verification 5 : Sender bank is not 01");
            }

            //DMT-4197 if OriginatorBranchId is single digit, 0 needs to be padded in front
            String NewOriginatorBranchId;
            boolean b1 = Pattern.matches("^[1-9]$", OriginatorBranchId);
            if (b1){
                ExtentTestManager.getTest().log(Status.INFO, "DMT-4197 : Origination branch id is prefixed with 0");
                NewOriginatorBranchId="0"+OriginatorBranchId;
            }
            else{
                NewOriginatorBranchId=OriginatorBranchId;
                ExtentTestManager.getTest().log(Status.INFO, "DMT-4197 : Origination branch id is not prefixed with 0");
            }

            if (CBSRequestSEFTP2.contains("<senderCustBranchCode>" + NewOriginatorBranchId + "</senderCustBranchCode>")) {
                ExtentTestManager.getTest().log(Status.PASS, "Verification 6 : Sender cust branch code is same as Debtor Agt branch Id in pain001");
            } else {
                ExtentTestManager.getTest().log(Status.FAIL, "Verification 6 : Sender cust branch code is not same as Debtor Agt branch Id in pain001");
            }
            if (CBSRequestSEFTP2.contains("<amount>" + Amount + "</amount>")) {
                ExtentTestManager.getTest().log(Status.PASS, "Verification 7 : Amount is same as amount in pain001");
            } else {
                ExtentTestManager.getTest().log(Status.FAIL, "Verification 7 : Amount is not same as amount in pain001");
            }

            //DMT-4197 if DestFinancialInstitution is single digit, 0 needs to be padded in front
            String NewDestFinancialInstitution;
            boolean b2 = Pattern.matches("^[1-9]$", DestFinancialInstitution);
            if (b2){
                ExtentTestManager.getTest().log(Status.INFO, "DMT-4197 : DestFinancialInstitution id is prefixed with 0");
                NewDestFinancialInstitution="0"+DestFinancialInstitution;
            }
            else{
                NewDestFinancialInstitution=DestFinancialInstitution;
                ExtentTestManager.getTest().log(Status.INFO, "DMT-4197 : DestFinancialInstitution id is not prefixed with 0");
            }

            if (CBSRequestSEFTP2.contains("<receivingBank>\n" +
                    "                <code>" + NewDestFinancialInstitution + "</code>\n" +
                    "            </receivingBank>")) {
                ExtentTestManager.getTest().log(Status.PASS, "Verification 8 : Receiver bank is same as Dest Financial Institution in pain001");
            } else {
                ExtentTestManager.getTest().log(Status.FAIL, "Verification 8 : Receiver bank is not same as Dest Financial Institution in pain001");
            }

            //DMT-4197 if OriginatorBranchId is single digit, 0 needs to be padded in front
            String NewDestinationBrnchId;
            boolean b3 = Pattern.matches("^[1-9]$", DestinationBrnchId);
            if (b3){
                ExtentTestManager.getTest().log(Status.INFO, "DMT-4197 : DestinationBrnchId is prefixed with 0");
                NewDestinationBrnchId="0"+DestinationBrnchId;
            }
            else{
                NewDestinationBrnchId=DestinationBrnchId;
                ExtentTestManager.getTest().log(Status.INFO, "DMT-4197 : DestinationBrnchId is not prefixed with 0");
            }
            if (CBSRequestSEFTP2.contains("<receiverCustBranchCode>" + NewDestinationBrnchId + "</receiverCustBranchCode>")) {
                ExtentTestManager.getTest().log(Status.PASS, "Verification 9 : Receiver Branch code is same as Creditor Agnt Branch in pain001");
            } else {
                ExtentTestManager.getTest().log(Status.FAIL, "Verification 9 : Receiver Branch code is not same as Creditor Agnt Branch in pain001");
            }

            if (CBSRequestSEFTP2.contains("<receiverCustName>" + DestinationName + "</receiverCustName>")) {
                ExtentTestManager.getTest().log(Status.PASS, "Verification 10 : Receiver cust name is same as Creditor name in pain001");
            } else {
                ExtentTestManager.getTest().log(Status.FAIL, "Verification 10 : Receiver cust name is not same as Creditor name in pain001");
            }
            if (CBSRequestSEFTP2.contains("<receiverCustAccount>" + DestinationAccountNumber + "</receiverCustAccount>")) {
                ExtentTestManager.getTest().log(Status.PASS, "Verification 11 : Receiver cust account is same as Creditor account in pain001");
            } else {
                ExtentTestManager.getTest().log(Status.FAIL, "Verification 11 : Receiver cust account is not same as Creditor account in pain001");
            }
            if (CBSRequestSEFTP2.contains("<purpose>" + ServiceLevel + "</purpose>")) {
                ExtentTestManager.getTest().log(Status.PASS, "Verification 12 : Purpose of payment is same as Service Level in pain001");
            } else {
                ExtentTestManager.getTest().log(Status.FAIL, "Verification 12 : Purpose of payment is not same as Service Level in pain001");
            }
            if (CBSRequestSEFTP2.contains("<senderReference>" + AddRmtInf + "</senderReference>")) {
                ExtentTestManager.getTest().log(Status.PASS, "Verification 13 : Sender ref is same as RmtInfo in pain001");
            } else {
                ExtentTestManager.getTest().log(Status.FAIL, "Verification 13 : Sender ref is not same as RmtInfo in pain001");
            }
            if (CBSRequestSEFTP2.contains("<narration>" + PurpOfPay + "</narration>")) {
                ExtentTestManager.getTest().log(Status.PASS, "Verification 14 : Narration is same as PurpOfPay in pain001");
            } else {
                ExtentTestManager.getTest().log(Status.FAIL, "Verification 14 : Narration is not same as PurpOfPay in pain001");
            }
            if (CBSRequestSEFTP2.contains("<currency>\n" +
                    "                <code>" + Currency + "</code>\n" +
                    "            </currency>")) {
                ExtentTestManager.getTest().log(Status.PASS, "Verification 15 : Currency code is same as currency in pain001");
            } else {
                ExtentTestManager.getTest().log(Status.FAIL, "Verification 15 : Currency code is not same as currency in pain001");
            }

        } else {
            ExtentTestManager.getTest().log(Status.FAIL, "CBS Request is not generated");
        }

        return CBSRequestSEFTP2;
    }

    public String  fetchAndValidate1stResponseFromCBS(String Correlation_Id) throws SQLException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        String QueryToFetchSysRef = "select system_reference from mk_seychelles_clearing.clearing_message_entity \n" +
                "where correlation_id ='" + Correlation_Id + "' and message_type='SEFT_PAYMENT_RESPONSE_OUTWARD'";
        String SystemRefSEFTP2 = databaseHandlerForPocDB(QueryToFetchSysRef, "system_reference");
        String QueryToFetchFirstCBSResponse = "select system_reference, correlation_id, message_value, message_type, \n" +
                "convert_from(loread(lo_open(message_value::int, x'40000'::int), x'40000'::int), 'UTF8') \n" +
                "from mk_seychelles_clearing.clearing_message_entity where correlation_id ='" + Correlation_Id + "' and message_type = 'SEFT_PAYMENT_RESPONSE_OUTWARD'\n" +
                "and system_reference = '" + SystemRefSEFTP2 + "'";
        String FirstResponseFromCBS = databaseHandlerForPocDB(QueryToFetchFirstCBSResponse, "convert_from");

        if (FirstResponseFromCBS != null) {
            ExtentTestManager.getTest().log(Status.INFO, "System reference number is: " + SystemRefSEFTP2);
            ExtentTestManager.getTest().log(Status.PASS, "First response from CBS is below");
            codeLogsXML(FirstResponseFromCBS);
        } else {
            ExtentTestManager.getTest().log(Status.FAIL, "First response from CBS with system ref is not received");
        }
        return SystemRefSEFTP2;
    }

    public ValidateResponeMessages dropMQRequestFromCBS(String SystemRefSEFTP2,String BankCountry) throws Throwable {
        String MQRequestFromCBS = createCBSMessages.createMQRequestFromCBS(SystemRefSEFTP2);
        if (MQRequestFromCBS != null) {
            apiUtil.genericDropPayload(MQRequestFromCBS, BankCountry);
            ExtentTestManager.getTest().log(Status.PASS, "MQ Request is generated and dropped");
            codeLogsXML(MQRequestFromCBS);
            Thread.sleep(2000);
            String queryToFetchMQResponse = "select convert_from(loread(lo_open(message_value::int, x'40000'::int), x'40000'::int), 'UTF8') " +
                    " from mk_seychelles_clearing.clearing_message_entity where " +
                    "system_reference = '" + SystemRefSEFTP2 + "' and message_type ='MQ_PAYMENT_RESPONSE_OUTWARD'";
            String MQResponseToCBS = databaseHandlerForPocDB(queryToFetchMQResponse, "convert_from");
            if (MQResponseToCBS != null) {
                ExtentTestManager.getTest().log(Status.PASS, "MQ Response to CBS is below");
                codeLogsXML(MQResponseToCBS);
            } else {
                ExtentTestManager.getTest().log(Status.INFO, "MQ Response to CBS is not sent");
            }
        } else {
            ExtentTestManager.getTest().log(Status.FAIL, "MQ Request is not generated and dropped");
        }
        return this;}

    public ValidateResponeMessages validateCBSACKPain002FromDLVToEbox(String MessageId,String PaymentInfoId,String strWorkflowReferenceUniqueTxt) throws SQLException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        String queryForPain002MessageReceivedBySEFT = "select convert_from(loread(lo_open(message_value::int, x'40000'::int), x'40000'::int), 'UTF8'), message_type from mk_sfi_channel_gateway.ebox_message_entity where \n" +
                "convert_from(loread(lo_open(message_value::int, x'40000'::int), x'40000'::int), 'UTF8') like '%Message received by SEFT.%' and " +
                "convert_from(loread(lo_open(message_value::int, x'40000'::int), x'40000'::int), 'UTF8') like '%" + MessageId + strWorkflowReferenceUniqueTxt + "%'";
        String Pain002_MessageReceivedBySEFT = databaseHandlerForPocDB(queryForPain002MessageReceivedBySEFT, "convert_from");
        System.out.println("Pain002_MessageReceivedBySEFT :: " + Pain002_MessageReceivedBySEFT);
        if (Pain002_MessageReceivedBySEFT != null) {
            ExtentTestManager.getTest().log(Status.INFO, "Pain002 (Received by SEFT) is below");
            codeLogsXML(Pain002_MessageReceivedBySEFT);

            //Verifications

            //1. OrgnlMsgId
            if (Pain002_MessageReceivedBySEFT.contains("OrgnlMsgId>" + MessageId + strWorkflowReferenceUniqueTxt + "<")
            ) {
                ExtentTestManager.getTest().log(Status.PASS, "Verification 1 : OrgnlMsgId is same as Pain001 Message id");
            } else {
                ExtentTestManager.getTest().log(Status.FAIL, "Verification 1 : OrgnlMsgId is not same as Pain001 Message id");
            }

            //2. OrgnlPmtInfId
            if (Pain002_MessageReceivedBySEFT.contains("OrgnlPmtInfId>" + PaymentInfoId + strWorkflowReferenceUniqueTxt + "<")) {
                ExtentTestManager.getTest().log(Status.PASS, "Verification 2 : OrgnlPmtInfId is same as Pain001 Payment info id");
            } else {
                ExtentTestManager.getTest().log(Status.FAIL, "Verification 2 : OrgnlPmtInfId is not same as Pain001 Payment info id");
            }

            //3. PmtInfSts ACSP
            if (Pain002_MessageReceivedBySEFT.contains("PmtInfSts>ACSP<")) {
                ExtentTestManager.getTest().log(Status.PASS, "Verification 3 : PmtInfSts is ACSP");
            } else {
                ExtentTestManager.getTest().log(Status.FAIL, "Verification 3 : PmtInfSts is not ACSP");
            }

            //4. Status Reason code SRCV
            if (Pain002_MessageReceivedBySEFT.contains("Cd>SRCV<")) {
                ExtentTestManager.getTest().log(Status.PASS, "Verification 4 : Status Reason code is SRCV");
            } else {
                ExtentTestManager.getTest().log(Status.FAIL, "Verification 4 : Status Reason code is SRCV");
            }

            //5. OrgnlEndToEndId
            if (Pain002_MessageReceivedBySEFT.contains("OrgnlEndToEndId>" + MessageId + strWorkflowReferenceUniqueTxt + "<")) {
                ExtentTestManager.getTest().log(Status.PASS, "Verification 5 : OrgnlEndToEndId is same as Pain001 Message id");
            } else {
                ExtentTestManager.getTest().log(Status.FAIL, "Verification 5 : OrgnlEndToEndId is not same as Pain001 Message id");
            }
        } else {
            ExtentTestManager.getTest().log(Status.FAIL, "Pain002 (Received by SEFT) is not generated");
        }
        return this;}

    public ValidateResponeMessages fetchAndValidateSettlementPain002FromDLVToEbox(String MessageId,String strWorkflowReferenceUniqueTxt) throws SQLException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        String queryForSettlementPain002 = "select convert_from(loread(lo_open(message_value::int, x'40000'::int), x'40000'::int), 'UTF8'), message_type from mk_sfi_channel_gateway.ebox_message_entity where \n" +
                "convert_from(loread(lo_open(message_value::int, x'40000'::int), x'40000'::int), 'UTF8') like '%SENO%' and " +
                "convert_from(loread(lo_open(message_value::int, x'40000'::int), x'40000'::int), 'UTF8') like '%" + MessageId + strWorkflowReferenceUniqueTxt + "%'";
        String Pain002_Settlement = databaseHandlerForPocDB(queryForSettlementPain002, "convert_from");
        System.out.println("Pain002_Settlement :: " + Pain002_Settlement);
        if (Pain002_Settlement != null) {
            ExtentTestManager.getTest().log(Status.INFO, "Pain002 (Settlement) is below");
            codeLogsXML(Pain002_Settlement);

            //Verifications

            //1. OrgnlMsgId
            if (Pain002_Settlement.contains("OrgnlMsgId>" + MessageId + strWorkflowReferenceUniqueTxt + "<")
            ) {
                ExtentTestManager.getTest().log(Status.PASS, "Verification 1 : OrgnlMsgId is same as Pain001 Message id");
            } else {
                ExtentTestManager.getTest().log(Status.FAIL, "Verification 1 : OrgnlMsgId is not same as Pain001 Message id");
            }

            //2. OrgnlPmtInfId
            if (Pain002_Settlement.contains("OrgnlPmtInfId>" + MessageId + strWorkflowReferenceUniqueTxt + "<")) {
                ExtentTestManager.getTest().log(Status.PASS, "Verification 2 : OrgnlPmtInfId is same as Pain001 Message id");
            } else {
                ExtentTestManager.getTest().log(Status.FAIL, "Verification 2 : OrgnlPmtInfId is not same as Pain001 Message id");
            }

            //3. PmtInfSts ACSC
            if (Pain002_Settlement.contains("PmtInfSts>ACSC<")) {
                ExtentTestManager.getTest().log(Status.PASS, "Verification 3 : PmtInfSts is ACSC");
            } else {
                ExtentTestManager.getTest().log(Status.FAIL, "Verification 3 : PmtInfSts is not ACSC");
            }

            //4. Status Reason code SENO
            if (Pain002_Settlement.contains("Cd>SENO<")) {
                ExtentTestManager.getTest().log(Status.PASS, "Verification 4 : Status Reason code is SENO");
            } else {
                ExtentTestManager.getTest().log(Status.FAIL, "Verification 4 : Status Reason code is SENO");
            }

            //5. OrgnlEndToEndId
            if (Pain002_Settlement.contains("OrgnlEndToEndId>" + MessageId + strWorkflowReferenceUniqueTxt + "<")) {
                ExtentTestManager.getTest().log(Status.PASS, "Verification 5 : OrgnlEndToEndId is same as Pain001 Message id");
            } else {
                ExtentTestManager.getTest().log(Status.FAIL, "Verification 5 : OrgnlEndToEndId is not same as Pain001 Message id");
            }
        } else {
            ExtentTestManager.getTest().log(Status.FAIL, "Pain002 (Settlement) is not generated");
        }
        return this; }

    public ValidateResponeMessages fetchAndValidatePain007FromDLVToEbox(String Correlation_Id,String MessageId,String NoOfTransactions,String PaymentInfoId,String Currency,
                     String Amount,String AddRmtInf,String OriginatorName,String OriginatorId,String OriginatorAccountNo,
                     String DestinationName,String DestinationAccountNumber,String strWorkflowReferenceUniqueTxt) throws SQLException, ClassNotFoundException, IllegalAccessException, InstantiationException {

        String queryForReversalPain007 = "select convert_from(loread(lo_open(message_value::int, x'40000'::int), x'40000'::int), 'UTF8'), message_type from mk_sfi_channel_gateway.ebox_message_entity where \n" +
                " message_type='PAIN_007' and correlation_id='" + Correlation_Id + "'";
        String Pain007_Reversal = databaseHandlerForPocDB(queryForReversalPain007, "convert_from");
        if (Pain007_Reversal != null) {
            ExtentTestManager.getTest().log(Status.INFO, "Pain007 (Reversal) is below");
            codeLogsXML(Pain007_Reversal);

            //Verifications

            //1. OrgnlMsgId
            if (Pain007_Reversal.contains("OrgnlMsgId>" + MessageId + strWorkflowReferenceUniqueTxt + "<")
            ) {
                ExtentTestManager.getTest().log(Status.PASS, "Verification 1 : OrgnlMsgId is same as Pain001 Message id");
            } else {
                ExtentTestManager.getTest().log(Status.FAIL, "Verification 1 : OrgnlMsgId is not same as Pain001 Message id");
            }

            //2. OrgnlPmtInfId
            if (Pain007_Reversal.contains("OrgnlPmtInfId>" + MessageId + strWorkflowReferenceUniqueTxt + "<")) {
                ExtentTestManager.getTest().log(Status.PASS, "Verification 2 : OrgnlPmtInfId is same as Pain001 Message id");
            } else {
                ExtentTestManager.getTest().log(Status.FAIL, "Verification 2 : OrgnlPmtInfId is not same as Pain001 Message id");
            }

            //3. Reason code 704
            if (Pain007_Reversal.contains("RvslRsnInf>\n" +
                    "                    <ns2:Rsn>\n" +
                    "                        <ns2:Cd>704<")) {
                ExtentTestManager.getTest().log(Status.PASS, "Verification 3 : Reason code is 704");
            } else {
                ExtentTestManager.getTest().log(Status.FAIL, "Verification 3 : Reason code is not 704");
            }

            //4. Reason description Rejected By Creditor Bank
            if (Pain007_Reversal.contains("Rsn>\n" +
                    "                    <ns2:AddtlInf>Rejected By Creditor Bank<")) {
                ExtentTestManager.getTest().log(Status.PASS, "Verification 4 : Additional Info on reason code is 'Rejected By Creditor Bank'");
            } else {
                ExtentTestManager.getTest().log(Status.FAIL, "Verification 4 : Additional Info on reason code is not 'Rejected By Creditor Bank'");
            }

            //5. OrgnlEndToEndId
            if (Pain007_Reversal.contains("OrgnlEndToEndId>" + MessageId + strWorkflowReferenceUniqueTxt + "<")) {
                ExtentTestManager.getTest().log(Status.PASS, "Verification 5 : OrgnlEndToEndId is same as Pain001 Message id");
            } else {
                ExtentTestManager.getTest().log(Status.FAIL, "Verification 5 : OrgnlEndToEndId is not same as Pain001 Message id");
            }

            //6. No of transactions
            if (Pain007_Reversal.contains("NbOfTxs>" + NoOfTransactions + "<")) {
                ExtentTestManager.getTest().log(Status.PASS, "Verification 6 : No of transactions is same as in Pain001");
            } else {
                ExtentTestManager.getTest().log(Status.FAIL, "Verification 6 : No of transactions is not same as in Pain001");
            }

            //7. Sender Bank - BBS is considered as 1
            if (Pain007_Reversal.contains("DbtrAgt>\n" +
                    "                <ns2:FinInstnId>\n" +
                    "                    <ns2:Nm>1<")) {
                ExtentTestManager.getTest().log(Status.PASS, "Verification 7 : Sender Bank is as expected");
            } else {
                ExtentTestManager.getTest().log(Status.FAIL, "Verification 7 : Sender Bank is not as expected");
            }

            //8. Receiver bank
            if (Pain007_Reversal.contains("DbtrAgt>\n" +
                    "                <ns2:FinInstnId>\n" +
                    "                    <ns2:Nm>1<")) {
                ExtentTestManager.getTest().log(Status.PASS, "Verification 8 : Receiver Bank is as expected");
            } else {
                ExtentTestManager.getTest().log(Status.FAIL, "Verification 8 : Receiver Bank is not as expected");
            }

            //9. Message name
            if (Pain007_Reversal.contains("OrgnlMsgNmId>pain.007.001.04<")) {
                ExtentTestManager.getTest().log(Status.PASS, "Verification 9 : Message name is as expected");
            } else {
                ExtentTestManager.getTest().log(Status.FAIL, "Verification 9 : Message name is not as expected");
            }

            //10. RvslPmtInfId
            if (Pain007_Reversal.contains("RvslPmtInfId>" + PaymentInfoId + strWorkflowReferenceUniqueTxt + "<")) {
                ExtentTestManager.getTest().log(Status.PASS, "Verification 10 : RvslPmtInfId is same as pmtinfoid in pain001");
            } else {
                ExtentTestManager.getTest().log(Status.FAIL, "Verification 10 : RvslPmtInfId is not same as pmtinfoid in pain001");
            }

            //11. OrgnlInstdAmt
            if (Pain007_Reversal.contains("OrgnlInstdAmt Ccy=\"" + Currency + "\">" + Amount + "<")) {
                ExtentTestManager.getTest().log(Status.PASS, "Verification 11 : OrgnlInstdAmt Amount and currency are as expected");
            } else {
                ExtentTestManager.getTest().log(Status.FAIL, "Verification 11 : OrgnlInstdAmt Amount and currency are not as expected");
            }

            //12. RvsdInstdAmt
            if (Pain007_Reversal.contains("RvsdInstdAmt Ccy=\"" + Currency + "\">" + Amount + "<")) {
                ExtentTestManager.getTest().log(Status.PASS, "Verification 12 : RvsdInstdAmt Amount and currency are as expected");
            } else {
                ExtentTestManager.getTest().log(Status.FAIL, "Verification 12 : RvsdInstdAmt Amount and currency are not as expected");
            }

            //13. Value date
            if (Pain007_Reversal.contains("ReqdColltnDt>" + getDateFormatForSettlementNotification() + "<")) {
                ExtentTestManager.getTest().log(Status.PASS, "Verification 13 : Value date is as expected");
            } else {
                ExtentTestManager.getTest().log(Status.FAIL, "Verification 13 : Value date is not as expected");
            }

            //14. Service level
            if (Pain007_Reversal.contains("SvcLvl>\n" +
                    "                            <ns2:Cd>0<")) {
                ExtentTestManager.getTest().log(Status.PASS, "Verification 14 : Service level is as expected");
            } else {
                ExtentTestManager.getTest().log(Status.FAIL, "Verification 14 : Service level is not as expected");
            }

            //15. Sender Reference
            if (Pain007_Reversal.contains("RmtInf>\n" +
                    "                        <ns2:Ustrd>" + AddRmtInf + "<")) {
                ExtentTestManager.getTest().log(Status.PASS, "Verification 15 : Sender ref is as expected");
            } else {
                ExtentTestManager.getTest().log(Status.FAIL, "Verification 15 : Sender ref is not as expected");
            }

            //16. Sender name
            if (Pain007_Reversal.contains("Dbtr>\n" +
                    "                        <ns2:Nm>" + OriginatorName + "<")) {
                ExtentTestManager.getTest().log(Status.PASS, "Verification 16 : Sender name is as expected");
            } else {
                ExtentTestManager.getTest().log(Status.FAIL, "Verification 16 : Sender name is not as expected");
            }

            //17. Sender cust id
            if (Pain007_Reversal.contains("OrgId>\n" +
                    "                                <ns2:Othr>\n" +
                    "                                    <ns2:Id>" + OriginatorId + strWorkflowReferenceUniqueTxt + "<")) {
                ExtentTestManager.getTest().log(Status.PASS, "Verification 17 : Sender cust id is as expected");
            } else {
                ExtentTestManager.getTest().log(Status.FAIL, "Verification 17 : Sender cust id is not as expected");
            }

            //18. Sender account number
            if (Pain007_Reversal.contains("DbtrAcct>\n" +
                    "                        <ns2:Id>\n" +
                    "                            <ns2:Othr>\n" +
                    "                                <ns2:Id>" + OriginatorAccountNo + "<")) {
                ExtentTestManager.getTest().log(Status.PASS, "Verification 18 : Sender account number is as expected");
            } else {
                ExtentTestManager.getTest().log(Status.FAIL, "Verification 18 : Sender account number is not as expected");
            }

            //19. Receiver name
            if (Pain007_Reversal.contains("Cdtr>\n" +
                    "                        <ns2:Nm>" + DestinationName + "<")) {
                ExtentTestManager.getTest().log(Status.PASS, "Verification 19 : Receiver name is as expected");
            } else {
                ExtentTestManager.getTest().log(Status.FAIL, "Verification 19 : Receiver name is not as expected");
            }

            //20. Receiver account number
            if (Pain007_Reversal.contains("CdtrAcct>\n" +
                    "                        <ns2:Id>\n" +
                    "                            <ns2:Othr>\n" +
                    "                                <ns2:Id>" + DestinationAccountNumber + "<")) {
                ExtentTestManager.getTest().log(Status.PASS, "Verification 20 : Receiver account number is as expected");
            } else {
                ExtentTestManager.getTest().log(Status.FAIL, "Verification 20 : Receiver account number is not as expected");
            }
        } else {
            ExtentTestManager.getTest().log(Status.FAIL, "Pain007 (Reversal) is not generated");
        }
        return this; }

    public ValidateResponeMessages fetchAndValidateNACKFroDLVToEbox(String MessageId,String PaymentInfoId,
                                                 String Correlation_Id,String strWorkflowReferenceUniqueTxt) throws SQLException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        String queryToFetchRJCTpain002 = "select convert_from(loread(lo_open(message_value::int, x'40000'::int), x'40000'::int), 'UTF8'), message_type from mk_sfi_channel_gateway.ebox_message_entity where \n" +
                " message_type='PAIN_002' and convert_from(loread(lo_open(message_value::int, x'40000'::int), x'40000'::int), 'UTF8') like '%RJCT%' and " +
                "convert_from(loread(lo_open(message_value::int, x'40000'::int), x'40000'::int), 'UTF8') like '%" + MessageId + strWorkflowReferenceUniqueTxt + "%' and correlation_id='" + Correlation_Id + "'";
        String NACKPain002_RJCT = databaseHandlerForPocDB(queryToFetchRJCTpain002, "convert_from");
        if (NACKPain002_RJCT != null) {
            ExtentTestManager.getTest().log(Status.INFO, "NACK - RJCT pain002 is below");
            codeLogsXML(NACKPain002_RJCT);

            //Verifications
            //1. OrgnlMsgId
            if (NACKPain002_RJCT.contains("OrgnlMsgId>" + MessageId + strWorkflowReferenceUniqueTxt + "<")
            ) {
                ExtentTestManager.getTest().log(Status.PASS, "Verification 1 : OrgnlMsgId is same as Pain001 Message id");
            } else {
                ExtentTestManager.getTest().log(Status.FAIL, "Verification 1 : OrgnlMsgId is not same as Pain001 Message id");
            }

            //2. OrgnlPmtInfId
            if (NACKPain002_RJCT.contains("OrgnlPmtInfId>" + PaymentInfoId + strWorkflowReferenceUniqueTxt + "<")) {
                ExtentTestManager.getTest().log(Status.PASS, "Verification 2 : OrgnlPmtInfId is same as Pain001 Payment info id");
            } else {
                ExtentTestManager.getTest().log(Status.FAIL, "Verification 2 : OrgnlPmtInfId is not same as Pain001 Payment info id");
            }

            //3. PmtInfSts RJCT
            if (NACKPain002_RJCT.contains("PmtInfSts>RJCT<")) {
                ExtentTestManager.getTest().log(Status.PASS, "Verification 3 : PmtInfSts is RJCT");
            } else {
                ExtentTestManager.getTest().log(Status.FAIL, "Verification 3 : PmtInfSts is not RJCT");
            }

            //4. Status Reason code SRCV
            if (NACKPain002_RJCT.contains("Cd>SRCV<")) {
                ExtentTestManager.getTest().log(Status.PASS, "Verification 4 : Status Reason code is SRCV");
            } else {
                ExtentTestManager.getTest().log(Status.FAIL, "Verification 4 : Status Reason code is SRCV");
            }

            //5. OrgnlEndToEndId
            if (NACKPain002_RJCT.contains("OrgnlEndToEndId>" + MessageId + strWorkflowReferenceUniqueTxt + "<")) {
                ExtentTestManager.getTest().log(Status.PASS, "Verification 5 : OrgnlEndToEndId is same as Pain001 Message id");
            } else {
                ExtentTestManager.getTest().log(Status.FAIL, "Verification 5 : OrgnlEndToEndId is not same as Pain001 Message id");
            }

            //5. Message
            if (NACKPain002_RJCT.contains("AddtlInf>Invalid Request Parameters<") ||
                    NACKPain002_RJCT.contains("AddtlInf>The Sender SEFT ID should not be specified when initiating a payment using the sending customer full info. Please empty the sender SEFT ID field. <")) {
                ExtentTestManager.getTest().log(Status.PASS, "Verification 6 : Error message is as expected");
            } else {
                ExtentTestManager.getTest().log(Status.FAIL, "Verification 6 : Error message is not as expected");
            }
        } else {
            ExtentTestManager.getTest().log(Status.FAIL, "NACK - RJCT pain002 is not generated");
        }
        return this; }
}
