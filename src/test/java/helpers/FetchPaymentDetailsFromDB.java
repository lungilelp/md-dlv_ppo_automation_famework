package helpers;

import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import config.ConfigReader;
import config.Settings;
import utils.extentreports.ExtentTestManager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import static Utilities.DatabaseUtil.*;
import static utils.extentreports.ExtentTestManager.codeLogsXML;

public class FetchPaymentDetailsFromDB {

    public String CorrelationId = null;
    public String CompletedTimestamp = null;

    public String SearchErrorCodeDescription(String errorCode) throws IOException {
        File f1 = new File(System.getProperty("user.dir") + Settings.ErrorCodeFilePath); //Creation of File Descriptor for input file
        FileReader fr = new FileReader(f1);  //Creation of File Reader object
        BufferedReader br = new BufferedReader(fr); //Creation of BufferedReader object
        String s;
        int count = 0;
        while ((s = br.readLine()) != null) {
            if (count == 1) {
                System.out.println("Error Code " + s);
                break;
            }
            if (s.contains(errorCode)) {
                count++;
            }
        }
        if (count == 0) {
            System.out.println("Error code description is not found in the file.");
            ExtentTestManager.getTest().log(Status.INFO, "Error code description is not found in the file.");
        }
        fr.close();
        return s;
    }


    public String fetchPurposeOfPaymentDescription(String ChannelId, String Country, String CategoryPurposeCode) throws IOException {
        String purposeOfPaymentDesc = null;
        //Retrieve the correlation_id from the Database using file name
        String strQueryGetValueFromDB = " select x.description from  mk_sfi_cbgw_proxy.refdata_txn_code x " +
                " where x.channel = '" + ChannelId + "'" +
                " and x.payment_purpose = '" + CategoryPurposeCode + "'" +
                " and country_code = '" + Country + "' ";

        System.out.println("strQueryGetValueFromDB :: " + strQueryGetValueFromDB);
        purposeOfPaymentDesc = GetValueFromPOCDatabase("description", strQueryGetValueFromDB);
        ExtentTestManager.getTest().log(Status.INFO, "Purpose Of Payment Description  " + "  :: " + purposeOfPaymentDesc);
        if (purposeOfPaymentDesc == null) {
            ExtentTestManager.getTest().log(Status.FAIL, "Info:: Unable to retrieve Description for category purpose code.");
        } else {
            if (purposeOfPaymentDesc.length() > 15) {
                purposeOfPaymentDesc = purposeOfPaymentDesc.substring(purposeOfPaymentDesc.length() - 15);
            }
        }
        return purposeOfPaymentDesc;
    }

    public void fetchAndVerifyCompletedTimeStamp() throws IOException {
        try{
            int i = 0;
            int intDatabaseWaitTimeIndex = 2;
            String strQueryGetValueFromDB = null;
            do{

                //Retrieve the correlation_id from the Database using file name
                strQueryGetValueFromDB = " select x.completed_timestamp from  mk_dlv_ops_ui_svc.bulk_file_entity x " +
                        " where x.correlation_id = '" + CorrelationId + "' ";

                System.out.println("strQueryGetValueFromDB :: " + strQueryGetValueFromDB);
                CompletedTimestamp = GetValueFromPOCDatabase("completed_timestamp", strQueryGetValueFromDB);

                if(CompletedTimestamp != null)
                {
                    System.out.println("CompletedTimestamp :: " + CompletedTimestamp);
                    ExtentTestManager.getTest().log(Status.INFO, "CompletedTimestamp  ::" + CompletedTimestamp);
                    ExtentTestManager.getTest().log(Status.PASS, "PASS:: Completed Timestamp is "+CompletedTimestamp);
                    break;
                }else {
                    ExtentTestManager.getTest().warning(MarkupHelper.createLabel("Warning:: No Records Where Found In The Database on the 1st attempt ,the script will attempt to retry in 20 seconds Completed TimeStamp", ExtentColor.AMBER));
                    Thread.sleep(20000);
                    i++;
                }

            }while (intDatabaseWaitTimeIndex <= i);

            if (CompletedTimestamp == null) {//TODO:: Investigate timestamp being null  - DONE
                ExtentTestManager.getTest().log(Status.FAIL, "Info:: Completed Timestamp is null for given payment message.");
            }

        }catch (Exception e){
            System.out.println(e.getMessage());
            ExtentTestManager.getTest().log(Status.FAIL, "Status=FATAL: No Records Where Found In The Database for Completed TimeStamp");
        }
    }

    public String fetchCorrelationId(String name, String messageId, String creationDateTime) throws IOException {
        try {
            int i = 0;
            int intDatabaseWaitTimeIndex = 2;
            String strQueryGetValueFromDB = null;
            do {
                //Retrieve the correlation_id from the Database using file name
                strQueryGetValueFromDB = " select x.correlation_id from  mk_dlv_ops_ui_svc.bulk_file_entity x " +
                        " where x.channel = '" + name + "'" +
                        " and x.filename = '" + messageId + "' ;";

                System.out.println("strQueryGetValueFromDB :: " + strQueryGetValueFromDB);
                CorrelationId = GetValueFromPOCDatabase("correlation_id", strQueryGetValueFromDB);
                System.out.println("CorrelationId 2:: " + CorrelationId);
                ExtentTestManager.getTest().log(Status.INFO, "Correlation Id  " + "  :: " + CorrelationId);


                if (CorrelationId != null) {//TODO:: Investigate Unable to retrieve correlation ID - DONE
                    fetchAndVerifyCompletedTimeStamp();
                    break;
                } else {
                    ExtentTestManager.getTest().warning(MarkupHelper.createLabel("Warning:: No Records Where Found In The Database on the 1st attempt ,the script will attempt to retry in 20 seconds Correlation ID", ExtentColor.AMBER));
                    Thread.sleep(20000);
                    i++;
                }
            } while (intDatabaseWaitTimeIndex <= i);

            if (CorrelationId == null) {
                ExtentTestManager.getTest().log(Status.FAIL, "Info:: Unable to retrieve correlation Id for given payment message.");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            ExtentTestManager.getTest().log(Status.FAIL, "Status=FATAL: No Records Where Found In The Database for Correlation Id");
        }
        return CorrelationId;
    }


    public String fetchPaymentMessageFileStatusFromDB(String name,String messageId,String ExpectedFileStatus) throws IOException {
        String fileStatus = null;
        try
        {
            int i = 0;
            int intDatabaseWaitTimeIndex = 2;

            String strQueryGetValueFromDB = null;

            do {
                strQueryGetValueFromDB = " select x.status from  mk_dlv_ops_ui_svc.bulk_file_entity x " +
                        " where x.channel = '" + name + "'" +
                        " and x.filename = '" + messageId+ "' ;";
                System.out.println("strQueryGetValueFromDB :: " + strQueryGetValueFromDB);
                fileStatus = GetValueFromPOCDatabase("status", strQueryGetValueFromDB);

                if(fileStatus != null && ExpectedFileStatus.equalsIgnoreCase(fileStatus)){
                    ExtentTestManager.getTest().log(Status.PASS, "Payment Message File Status  " + "  :: " + fileStatus);
                    break;
                }else if(fileStatus != null && fileStatus.equalsIgnoreCase("REJECTED")) {
                    strQueryGetValueFromDB = " select a.reject_reason_codes  from mk_dlv_ops_ui_svc.bulk_file_reject_reason_code a, " +
                            " mk_dlv_ops_ui_svc.bulk_file_entity b  where a.bulk_file_id =b.id " +
                            " and b.channel = '" + name + "' " +
                            " and b.filename = '" + messageId + "'";

                    System.out.println("strQueryGetValueFromDB :: " + strQueryGetValueFromDB);
                    String PaymentMessageRejectReasonCode = GetValueFromPOCDatabase("reject_reason_codes", strQueryGetValueFromDB);
                    if(PaymentMessageRejectReasonCode == null){
                        ExtentTestManager.getTest().log(Status.FAIL, "Info:: Unable to retrieve Payment message Reject reason code.");
                    }else {
                        String ErrorCodeDescription = SearchErrorCodeDescription(PaymentMessageRejectReasonCode);
                        codeLogsXML("Payment Message File Failure Reason code  " + "  :: " + PaymentMessageRejectReasonCode + "\nError " + ErrorCodeDescription);
                    }
                    break;
                }else {
                    ExtentTestManager.getTest().warning(MarkupHelper.createLabel("Warning:: No Records Where Found In The Database on the 1st attempt ,the script will attempt to retry in 20 seconds for Payment Message File Status", ExtentColor.AMBER));
                    Thread.sleep(20000);
                    i++;
                }
            }while (intDatabaseWaitTimeIndex <= i);

            if (fileStatus == null) { //TODO:: Investigate payment message being null - DONE
                ExtentTestManager.getTest().log(Status.FAIL, "Payment Message File Status is incorrect,expected status is  " + "  :: " + ExpectedFileStatus + ".     "  + "Actual Status is   :: " + fileStatus);
            }

        }catch (Exception e){
            System.out.println(e.getMessage());
            ExtentTestManager.getTest().log(Status.FAIL, "Status=FATAL: No Records Where Found In The Database for Payment Message File Status");
        }

        return fileStatus;
    }

    public String fetchProcessingStatusFromDB(String name,String paymentInfoId,String creationDateTime,String fileStatus ) throws IOException {

        /* Retrieve the accounting status of message file from the Database using file name */
        String processingStatus = null;
        if(fileStatus != null && (!fileStatus.equalsIgnoreCase("REJECTED"))) {
            String strQueryGetValueFromDB = " select x.status from  mk_dlv_ops_ui_svc.payment_instruction_entity x " +
                    " where x.channel = '" + name + "'" +
                    " and x.channel_reference_id = '" + paymentInfoId + "' ;";

            System.out.println("strQueryGetValueFromDB :: " + strQueryGetValueFromDB);
            processingStatus = GetValueFromPOCDatabase("status", strQueryGetValueFromDB);
            codeLogsXML("Payment Instruction Id :: " + paymentInfoId + "\nPayment Instruction Status :: " + processingStatus);

            if (processingStatus != null && processingStatus.equalsIgnoreCase("FAILED")) {
                String paymentInstrErrorCode = null;
                /* Retrieve the Payment instruction failure reason code from the database using payment instruction id */
                strQueryGetValueFromDB = " select b.errors from mk_dlv_ops_ui_svc.payment_instruction_entity a," +
                        " mk_dlv_ops_ui_svc.payment_errors b  where a.channel = '" + name + "' " +
                        " and a.id = b.payment_id and a.channel_reference_id = '" + paymentInfoId + "';" ;

                System.out.println("strQueryGetValueFromDB :: " + strQueryGetValueFromDB);
                paymentInstrErrorCode = GetValueFromPOCDatabase("errors", strQueryGetValueFromDB);
                if(paymentInstrErrorCode == null){
                    ExtentTestManager.getTest().log(Status.FAIL, "Info:: Unable to retrieve Payment Instruction Failure Reject reason code.");
                }else {

                    String ErrorCodeDescription = SearchErrorCodeDescription(paymentInstrErrorCode);
                    codeLogsXML("Payment Instruction Failure Reason code  " + " :: " + paymentInstrErrorCode + "\nError " + ErrorCodeDescription);
                }
            }
        }
        return processingStatus;
    }

    public String fetchTransactionStatusFromDB(String name,String paymentInfoId,String endtoendId,String creationDateTime,String processingStatus) throws IOException, InterruptedException {
        /* Retrieve the transaction status of payment instruction */
        String transactionStatus = null;
        if (processingStatus != null && (!processingStatus.equalsIgnoreCase("REJECTED")) &&
                (!processingStatus.equalsIgnoreCase("FAILED"))) {

            String strQueryGetValueFromDB = " select x.status from  mk_dlv_ops_ui_svc.transaction_entity x " +
                    " where x.channel = '" + name + "'" +
                    " and x.instruction_reference_id = '" + paymentInfoId + "'" +
                    " and x.channel_reference_id = '"+ endtoendId + "';";

            transactionStatus = GetValueFromPOCDatabase("status",strQueryGetValueFromDB);

            System.out.println("strQueryGetValueFromDB :: " + strQueryGetValueFromDB);
            codeLogsXML("Transaction Id  " + " :: " + endtoendId + "\nTransaction Status  " + "  :: " + transactionStatus);
            if(transactionStatus != null && transactionStatus.equalsIgnoreCase("FAILED")){
                strQueryGetValueFromDB = " select y.errors from  mk_dlv_ops_ui_svc.transaction_entity x," +
                        " mk_dlv_ops_ui_svc.trans_errors y " +
                        " where x.channel = '" + name + "'" +
                        " and x.id =y.transaction_id " +
                        " and x.instruction_reference_id = '" + paymentInfoId + "'" +
                        " and x.channel_reference_id = '"+ endtoendId  + "';";

                String transactionErrorCode = GetValueFromPOCDatabase("errors",strQueryGetValueFromDB);
                System.out.println("transactionErrorCode :: " + transactionErrorCode);
                System.out.println("strQueryGetValueFromDB :: " + strQueryGetValueFromDB);
                Thread.sleep(5000);
                if(transactionErrorCode == null){
                    ExtentTestManager.getTest().log(Status.FAIL, "Info:: Unable to retrieve Transaction Failure Reject reason code.");
                }else {
                    String ErrorCodeDescription = SearchErrorCodeDescription(transactionErrorCode);
                    codeLogsXML("Transaction Failure Reason Code " + " :: " + transactionErrorCode + "\nError " + ErrorCodeDescription);
                }
            }
        }
        return transactionStatus;
    }

    public String GetValueFromPOCDatabase(String ColumnName1, String sqlQueryStatement) throws IOException {

        /*Initialize config reader to read configs from Settings*/
        ConfigReader.PopulateSettings();
        Connection connection;
        Statement stmt;
        String columnValue1 = null;

        try {
            int i = 0;
            int intDatabaseWaitTimeIndex = 2;

            //Retry the SQL Query execution if there is no payload in the DB on the first attempt
            try {
                do {
                    Class.forName(Settings.DBpocDriverJdbc);
                    connection = DriverManager
                            .getConnection(Settings.DBpocUrl, Settings.DBpocUserName, Settings.DBpocPassword);
                    connection.setAutoCommit(false);
                    System.out.println("Opened database successfully");

                    stmt = connection.createStatement();
                    ResultSet rs = stmt.executeQuery(sqlQueryStatement);
                    while (rs.next()) {
                        if(rs.getRow() == 1) {
                            columnValue1 = rs.getString(ColumnName1);
                        }else{
                            columnValue1 = columnValue1 + "," + rs.getString(ColumnName1);
                        }
                    }
                    if (columnValue1 != null) {
                        System.out.println("Value of Column : " + ColumnName1 + " :: "  + columnValue1);
                        break;

                    } else {
                        System.out.println("Warning:: No Records were Found ");
                        ExtentTestManager.getTest().warning(MarkupHelper.createLabel("No Records were Found In The Database on the 1st attempt ,the script will attempt to retry in 20 seconds", ExtentColor.AMBER));
                        Thread.sleep(20000);
                        i++;
                    }
                } while (i <= intDatabaseWaitTimeIndex);

                /* If there are no records in the DB after the allocated time*/
                if (columnValue1 == null & i > intDatabaseWaitTimeIndex) {
                    ExtentTestManager.getTest().info(MarkupHelper.createLabel("No Records Found In The Database. ", ExtentColor.RED));
                }

            } catch (Exception e) {
                ExtentTestManager.getTest().log(Status.INFO, "No Records Found In The Database.");
            }
        } catch (Exception e) {
            ExtentTestManager.getTest().log(Status.INFO, "Database data retrieval failed :: " + e.getMessage());
        }

        return columnValue1;

    }

    public String SycOutwardCreditStatusAfterSecondResponse (String transactionIdUI) {

        String status=null;
        try{
            String fetchstatus = "select status\n" +
                    "from mk_seychelles_clearing.clearing_transaction_entity\n" +
                    "where transaction_id= '"+transactionIdUI+"'";
            status = databaseHandlerForPocDB(fetchstatus,"status");

            /*ExtentTestManager.getTest().info(MarkupHelper.createLabel("Status of payment after dropping MQ request from CBS", ExtentColor.PURPLE));
            if (status!=null) {
                ExtentTestManager.getTest().log(Status.PASS, "status of transaction is present");
            }
            else{
                ExtentTestManager.getTest().log(Status.FAIL, "status of transaction is not present");
            }*/
        }

        catch(Exception e){

        }
        return status;
    }

    public String fetchSFICorrelationId(String FileName,String ChannelId) throws IOException {
        //Retrieve the correlation_id from the Database using file name
        String strQueryGetValueFromDB = " select correlation_id from mk_sfi_gateway.payments p " +
                " where p.channel = '" + ChannelId + "'" +
                " and p.filename  like '"+FileName+"%'";

        System.out.println("strQueryGetValueFromDB :: " + strQueryGetValueFromDB);
        String correlationId = GetValueFromPOCDatabase("correlation_id",strQueryGetValueFromDB);
        ExtentTestManager.getTest().log(Status.INFO, "Correlation Id  " + "  :: " + correlationId);
        if(correlationId == null){
            ExtentTestManager.getTest().log(Status.FAIL, "Info:: Unable to retrieve Correlation Id.");
        }
        return correlationId;
    }
    public String fetchSFIInstructionId(String FileName,String ChannelId) throws IOException, InterruptedException {
        //Retrieve the correlation_id from the Database using file name
        String strQueryGetValueFromDB = " select channel_reference_id from mk_sfi_gateway.payments p " +
                " where p.channel = '" + ChannelId + "'" +
                " and p.filename  like '"+FileName+"%'";

        System.out.println("strQueryGetValueFromDB :: " + strQueryGetValueFromDB);
        String instructionId = GetValueFromPOCDatabase("channel_reference_id",strQueryGetValueFromDB);
        Thread.sleep(5000);
        ExtentTestManager.getTest().log(Status.INFO, "Channel Reference Id  " + "  :: " + instructionId);
        if(instructionId == null){
            ExtentTestManager.getTest().log(Status.FAIL, "Info:: Unable to retrieve Channel Reference Id.");
        }
        return instructionId;
    }

    public String fetchPurposeOfPaymentDescriptionForSFI(String ChannelId,String Country,String TransactionCode,String SubTransactionCode) throws IOException {
        String purposeOfPaymentDesc = null;
        //Retrieve the correlation_id from the Database using file name
        String strQueryGetValueFromDB = " select description from mk_sfi_refdata.transaction_code_ref_data_entity x " +
                " where x.channel = '" + ChannelId + "'" +
                " and x.transaction_code = '" + TransactionCode + "'" +
                " and x.sub_transaction_code = '" + SubTransactionCode + "'" +
                " and x.country = '" + Country + "' ";

        System.out.println("strQueryGetValueFromDB :: " + strQueryGetValueFromDB);
        purposeOfPaymentDesc = GetValueFromPOCDatabase("description",strQueryGetValueFromDB);
        if(purposeOfPaymentDesc == null){
            ExtentTestManager.getTest().log(Status.FAIL, "Info:: Unable to retrieve Description for transaction code.");
        }
        return purposeOfPaymentDesc;
    }
}