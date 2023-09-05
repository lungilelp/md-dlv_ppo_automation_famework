package pages;

/**
 * @author DLV Automation Team
 * @date created 10/30/2019
 * @package pages
 */

import Utilities.DatabaseUtil;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import base.BasePage;
import helpers.OutwardCreditHandlingModule;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import utils.extentreports.ExtentTestManager;
import utils.logs.Log;

import java.sql.SQLException;
import java.util.List;

public class BulkPaymentSearchPage extends BasePage {
    public BulkPaymentSearchPage(WebDriver driver) {

        super(driver);
        Log.info("The current working class: "+this.getClass().getName());
    }

    //***********************************WEB ELEMENTS*******************************************************************
    //******************************************************************************************************************

    String bulkPaymentSearchTab = "//*[@id=\"bulk-payment-search\"]";
    String bulkFilesTab = "//*[@id=\"bulk-files\"]";
    String bulkListDropdown = "//li[text() = 'SFI']";
    String selectChannelDropdown ="//div[@id=\"sol-custom-select-1\"]";
    String transactionRadioBtn = "/html/body/app-root/div/div/div/payment-search/div/div[1]/div/div/sol-radio[2]/label/span[2]";
    String correlationIdTxtBox = "//*[@id=\"correlation-id-textbox_text\"]";
    String instructionIdTxtBox = "//*[@id=\"instruction-id-textbox_text\"]";
    String submitBtn = "//*[@id=\"submit-transactions-btn\"]";
    String allValuesXpathPart1 = "/html/body/app-root/div/div/div/payment-search/div/ngx-datatable/div/datatable-body/datatable-selection/datatable-scroller/datatable-row-wrapper[";
    String transactionIdPart2 = "]/datatable-body-row/div[2]/datatable-body-cell[1]/div/span";
    String channelPart2 ="]/datatable-body-row/div[2]/datatable-body-cell[2]/div/span";
    String purposePart2 ="]/datatable-body-row/div[2]/datatable-body-cell[3]/div/span";
    String amountPart2 ="]/datatable-body-row/div[2]/datatable-body-cell[4]/div/span";
    String statusPart2 ="]/datatable-body-row/div[2]/datatable-body-cell[5]/div/span";
    String errorsPart2 ="]/datatable-body-row/div[2]/datatable-body-cell[6]/div";
    String PaymentInstructionStatusxpath = "/html/body/app-root/div/div/div/payment-search/div/ngx-datatable/div/datatable-body/datatable-selection/datatable-scroller/datatable-row-wrapper/datatable-body-row/div[2]/datatable-body-cell[9]/div/span";
    String getCorrelationIdSFI = "//span[contains(text(), '1_BotswanaMix_Itemised_AXzCrD_2021-10-18-22-15-03')]/ancestor :: div[@class ='datatable-row-center datatable-row-group']//a";
    String RefIDSFI = "//A[contains(text(), 'C')]";
    //******************************************************************************************************************

    int NoOfTransactions =0;
    String InstructionId=null;
    String CorrelationId=null;
    String correlationIDSFI = null;
    String InstructionIDSFI = null;


    OutwardCreditHandlingModule outwardCreditHandlingModule = new OutwardCreditHandlingModule(driver);
    //***********************************STEPS**************************************************************************
    //******************************************************************************************************************

    public String fetchCorrelationId(String filename) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException, InterruptedException {
        System.out.println("filename is"+filename);
        Thread.sleep(3000);
        String queryFetchCorrelationId="select correlation_id from mk_sfi_gateway.payments where filename like '"+filename+"%'";
        String queryFetchInstructionId="select  channel_reference_id from mk_sfi_gateway.payments where filename like '"+filename+"%'";
        CorrelationId = DatabaseUtil.databaseHandlerForPocDB(queryFetchCorrelationId,"correlation_Id");
        InstructionId = DatabaseUtil.databaseHandlerForPocDB(queryFetchInstructionId,"channel_reference_id");

        System.out.println("correlation id"+CorrelationId);
        System.out.println("instruction id"+InstructionId);

        return CorrelationId;
    }

       public BulkPaymentSearchPage fetchAndProcess(String NumberOfTransactions, String BankCountry,
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
                                                           String ExpectedFirstPacs002FIToFIPmtStsRptTxInfAndStsStsRsnInfRsnCd_5
                                                           ) {

      try{
            click(By.xpath(bulkPaymentSearchTab));
            Thread.sleep(5000);
            click(By.xpath(transactionRadioBtn));
            writeText(By.xpath(correlationIdTxtBox), CorrelationId);
            writeText(By.xpath(instructionIdTxtBox), InstructionId);
            Thread.sleep(2000);
            click(By.xpath(submitBtn));
            Thread.sleep(3000);
            click(By.xpath(submitBtn));
            Thread.sleep(3000);

          for(int i=1;i<=Integer.parseInt(NumberOfTransactions);i++){
              scrollElementIntoView(By.xpath(allValuesXpathPart1+i+transactionIdPart2));
              String transactionId = readText(By.xpath(allValuesXpathPart1+i+transactionIdPart2));
              highLighterMethod(By.xpath(allValuesXpathPart1+i+transactionIdPart2));

              scrollElementIntoView(By.xpath(allValuesXpathPart1+i+statusPart2));
              String status = readText(By.xpath(allValuesXpathPart1+i+statusPart2));
              highLighterMethod(By.xpath(allValuesXpathPart1+i+statusPart2));

              if(status.contains("SCHEDULED_FOR_CLEARING")){
                  Thread.sleep(5000);
                  //waitForElementToBeVisible();
                  click(By.xpath(submitBtn));
                  Thread.sleep(2000);
              }

              if(status.contains("SENT_FOR_CLEARING")){
                  outwardCreditHandlingModule.getPac008message(BankCountry, transactionId,
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
          }

          click(By.xpath(submitBtn));
          Thread.sleep(3000);
          scrollDown();
          screenshotLoggerMethod("Bulk Application Search-Transactions", "Bulk Application Search-Transactions");

        }
        catch(Exception e){
            System.out.println("exception from class fetchAndDisplayStatusOfTransactions-"+e);
        }

           return this;}

    public BulkPaymentSearchPage fetchAndProcessSFI(String NumberOfTransactions, String BankCountry,
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
                                   String ExpectedFirstPacs002FIToFIPmtStsRptTxInfAndStsStsRsnInfRsnCd_5
    ) {

        try{
            Thread.sleep(1000);

            for(int i=1;i<=Integer.parseInt(NumberOfTransactions);i++){
                scrollElementIntoView(By.xpath(allValuesXpathPart1+i+transactionIdPart2));
                String transactionId = readText(By.xpath(allValuesXpathPart1+i+transactionIdPart2));
                highLighterMethod(By.xpath(allValuesXpathPart1+i+transactionIdPart2));

                scrollElementIntoView(By.xpath(allValuesXpathPart1+i+statusPart2));
                String status = readText(By.xpath(allValuesXpathPart1+i+statusPart2));
                highLighterMethod(By.xpath(allValuesXpathPart1+i+statusPart2));

                if(status.contains("SCHEDULED_FOR_CLEARING")){
                    Thread.sleep(2000);
                    //waitForElementToBeVisible();
                    click(By.xpath(submitBtn));
                    Thread.sleep(2000);
                }

                if(status.contains("SENT_FOR_CLEARING")){
                    outwardCreditHandlingModule.getPac008message(BankCountry, transactionId,
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
            }

            click(By.xpath(submitBtn));
            Thread.sleep(3000);
            scrollDown();
            screenshotLoggerMethod("Bulk Application Search-Transactions", "Bulk Application Search-Transactions");

        }
        catch(Exception e){
            System.out.println("exception from class fetchAndDisplayStatusOfTransactions-"+e);
        }

        return this;}

    public BulkPaymentSearchPage fetchStatements(String NumberOfTransactions, String DebtorPostingOption, String DebtorControlSum, String PaymentId, String ApplicationArea, String BankCountry){
        try{

            if(DebtorPostingOption.contains("C") && ApplicationArea.contains("PPU")){
                String instructionIdReplaced = InstructionId.replace("-","_");
                String RemoveDecimalDebtorControlSum = DebtorControlSum.replace(".00","");
                String fetchStatement = "select content from mk_sfi_event_store.event_log_entity " +
                        "where  correlation_id='"+CorrelationId+"' and event_type ='account-posting-ready-event' and content::text like '%"+instructionIdReplaced+"%' " +
                        "and content::text like '%"+RemoveDecimalDebtorControlSum+"%'";
                String statement= DatabaseUtil.databaseHandlerForPocDB(fetchStatement, "content");
                ExtentTestManager.getTest().info(MarkupHelper.createLabel("Consolidated amount transfer : "+InstructionId +" is below", ExtentColor.BROWN));
                ExtentTestManager.codeLogsXML(statement);

            }
            for(int i=1;i<=Integer.parseInt(NumberOfTransactions);i++){

                String transactionId = readText(By.xpath(allValuesXpathPart1+i+transactionIdPart2));
                String status = readText(By.xpath(allValuesXpathPart1+i+statusPart2));
                String errorcodemessage = readText(By.xpath(allValuesXpathPart1+i+errorsPart2));

                String fetchStatement = "select content from mk_sfi_event_store.event_log_entity " +
                        "where  correlation_id='"+CorrelationId+"' and event_type ='account-posting-ready-event' and content::text like '%"+transactionId+"%'";
                String statement= DatabaseUtil.databaseHandlerForPocDB(fetchStatement, "content");
                ExtentTestManager.getTest().info(MarkupHelper.createLabel("Statement for Transaction ID : "+transactionId +" with status : "+
                        status+" with error : "+errorcodemessage+" is below", ExtentColor.BROWN));
                ExtentTestManager.codeLogsXML(statement);
            }

            if(DebtorPostingOption.contains("I")&& ApplicationArea.contains("PPO")){

                // try and error

            }
            else{
                System.out.println("Posting option or application area undefined");
            }

        }
        catch(Exception e){
            System.out.println("exception from class fetchStatements-"+e);
        }

        return this; }

    public BulkPaymentSearchPage fetchFlexStatementItemised( String PaymentId, String BankCountry){

        String uniqueIdAfterSubmittingPayment = null;
        List<String> TransactionId = null;
        String dataFromTransactionTableQuery = null;
        String dataFromEventLogEntityTableQuery = null;
        int NoOfRowsTranIds =0;
        String fetchStatusOfReversedTransactionQuery=null;

        try{
            //fetch unique id linked with transactions ids using payment id
            String fetchUniqueIdAfterSubmittingPayment = "select instruction_id from makola_payments_portal_sit.message where " +
                    "payment_id='"+PaymentId+"' " +
                    "and message_type ='PAIN.001'";
            uniqueIdAfterSubmittingPayment= DatabaseUtil.databaseHandlerForPocDB(fetchUniqueIdAfterSubmittingPayment, "instruction_id");
            //---------------------------------------------------------------------------------------------------------------------------

            //now fetch the transaction ids and store result
            String fetchTransactionIds = "select transaction_id from makola_payments_portal_sit.transaction " +
                    "where instruction_id='"+uniqueIdAfterSubmittingPayment+"'";

            TransactionId= DatabaseUtil.databaseHandlerForPocDBMultiple(fetchTransactionIds, "transaction_id");
            NoOfRowsTranIds = TransactionId.size();
            //---------------------------------------------------------------------------------------------------------------------------


            //fetch the accounting logs for transactions ids one by one using for loop
            for(int i=0 ; i<NoOfRowsTranIds; i++){

                //query for transaction table
                dataFromTransactionTableQuery = "select * from makola_payments_portal_sit.transaction where" +
                        " transaction_id='"+TransactionId.get(i)+"'";

                //query for event log entity table
                dataFromEventLogEntityTableQuery = "select * from mk_sfi_event_store.event_log_entity where country='"+BankCountry+"' " +
                        "and event_type ='account-posting-ready-event'" +
                        "and content::text like '%"+TransactionId.get(i)+"%'";

                //query for event log entity table for reversal
                fetchStatusOfReversedTransactionQuery ="select content from mk_sfi_event_store.event_log_entity where country='"+BankCountry+"' " +
                        "and event_type ='account-posting-ready-event'" +
                        "and content::text like '%"+TransactionId.get(i)+"R"+"%'";

                //run query for ACPT and ACSP
                if(DatabaseUtil.databaseHandlerForPocDB(dataFromTransactionTableQuery, "status").contains("ACPT")
                || DatabaseUtil.databaseHandlerForPocDB(dataFromTransactionTableQuery, "status").contains("ACSP")){
                    //run query for account logs
                    ExtentTestManager.getTest().info(MarkupHelper.createLabel("Transaction id : "+TransactionId.get(i) +" accounting", ExtentColor.BROWN));

                    //verify accounting for success transactions
                    if(DatabaseUtil.databaseHandlerForPocDB(dataFromEventLogEntityTableQuery, "content")==null){
                        ExtentTestManager.getTest().log(Status.FAIL, "Accounting did not happen for successful transaction");
                    }
                    else{
                        ExtentTestManager.getTest().log(Status.PASS, "Accounting happened for successful transaction");
                        ExtentTestManager.codeLogsXML(DatabaseUtil.databaseHandlerForPocDB(dataFromEventLogEntityTableQuery, "content"));
                    }

                    //verify that there is no reversal for successful transactions
                    if(DatabaseUtil.databaseHandlerForPocDB(fetchStatusOfReversedTransactionQuery, "content")==null){
                        ExtentTestManager.getTest().log(Status.PASS, "Reversal not present for successful transaction");
                    }
                    else{
                        ExtentTestManager.getTest().log(Status.FAIL, "Reversal is present for successful transaction");
                        ExtentTestManager.codeLogsXML(DatabaseUtil.databaseHandlerForPocDB(fetchStatusOfReversedTransactionQuery, "content"));
                    }
                }
                //run query for rejected transaction
                else if(DatabaseUtil.databaseHandlerForPocDB(dataFromTransactionTableQuery, "status").contains("RJCT")){
                    //fetch error reason
                    ExtentTestManager.getTest().info(MarkupHelper.createLabel("Transaction id : "+TransactionId.get(i) +" failed with error code "
                            + DatabaseUtil.databaseHandlerForPocDB(dataFromTransactionTableQuery, "status_reason"), ExtentColor.BROWN));

                    //verify accounting for failed transactions
                    if(DatabaseUtil.databaseHandlerForPocDB(dataFromEventLogEntityTableQuery, "content")==null){
                        ExtentTestManager.getTest().log(Status.PASS, "Accounting is not present for failed itemised transaction");
                    }
                    else{
                        ExtentTestManager.getTest().log(Status.FAIL, "Accounting happened for failed itemised transaction");
                        ExtentTestManager.codeLogsXML(DatabaseUtil.databaseHandlerForPocDB(dataFromEventLogEntityTableQuery, "content"));
                        //verify that there is no reversal for failed itemised transactions
                        if(DatabaseUtil.databaseHandlerForPocDB(fetchStatusOfReversedTransactionQuery, "content")==null){
                            ExtentTestManager.getTest().log(Status.FAIL, "Phase 1 accounting happened. " +
                                    "Reversal is not present for failed itemised transaction");
                        }
                        else{
                            ExtentTestManager.getTest().log(Status.PASS, "Reversal happened for failed itemised transaction");
                            ExtentTestManager.codeLogsXML(DatabaseUtil.databaseHandlerForPocDB(fetchStatusOfReversedTransactionQuery, "content"));
                        }
                    }
                }
                else{
                    ExtentTestManager.getTest().log(Status.INFO, "Status of transaction id is "+ DatabaseUtil.databaseHandlerForPocDB(dataFromTransactionTableQuery, "status"));
                }
            }
        }
        catch(Exception e){
            System.out.println("exception from class fetchStatements-"+e);
        }

        return this;}

    public BulkPaymentSearchPage fetchFlexStatementConsolidated( String PaymentId, String BankCountry){

        String uniqueIdAfterSubmittingPayment = null;
        String InstructionId =null;
        String StatusOfInstructionId=null;
        String ErrorCodeInstructionId=null;
        List<String> TransactionId = null;
        String dataFromTransactionTableQuery = null;
        String dataFromEventLogEntityTableQuery = null;
        int NoOfRowsTranIds =0;
        String fetchStatusOfReversedTransactionQuery=null;

        try{
            //fetch unique id linked with transactions ids using payment id
            String fetchUniqueIdAfterSubmittingPayment = "select instruction_id from makola_payments_portal_sit.message where " +
                    "payment_id='"+PaymentId+"' " +
                    "and message_type ='PAIN.001'";
            uniqueIdAfterSubmittingPayment= DatabaseUtil.databaseHandlerForPocDB(fetchUniqueIdAfterSubmittingPayment, "instruction_id");
            //---------------------------------------------------------------------------------------------------------------------------

            //fetch instruction id and status
            String fetchInstructionId = "select * from makola_payments_portal_sit.instruction where " +
                    "payment_id='"+uniqueIdAfterSubmittingPayment+"' ";
            InstructionId= DatabaseUtil.databaseHandlerForPocDB(fetchInstructionId, "instruction_id");
            StatusOfInstructionId= DatabaseUtil.databaseHandlerForPocDB(fetchInstructionId, "status");
            ErrorCodeInstructionId= DatabaseUtil.databaseHandlerForPocDB(fetchInstructionId, "status_reason");
            //now fetch the transaction ids and store result
            String fetchTransactionIds = "select transaction_id from makola_payments_portal_sit.transaction " +
                    "where instruction_id='"+uniqueIdAfterSubmittingPayment+"'";

            TransactionId= DatabaseUtil.databaseHandlerForPocDBMultiple(fetchTransactionIds, "transaction_id");
            NoOfRowsTranIds = TransactionId.size();
            //---------------------------------------------------------------------------------------------------------------------------

            if(StatusOfInstructionId!="RJCT") {

                //fetch accounting logs for total amount debited from debtor
                dataFromEventLogEntityTableQuery = "select content from mk_sfi_event_store.event_log_entity where country='" + BankCountry + "' and " +
                        "event_type ='account-posting-ready-event'" +
                        "and content::text like '%" + InstructionId + "%'  order by timestamp limit 1";
                ExtentTestManager.getTest().info(MarkupHelper.createLabel("Phase 1 accounting for consolidated control sum. Instruction id: " + InstructionId,
                        ExtentColor.BROWN));

                if (DatabaseUtil.databaseHandlerForPocDB(dataFromEventLogEntityTableQuery, "content") == null) {
                    ExtentTestManager.getTest().log(Status.FAIL, "Phase 1 accounting did not happen for control sum");
                } else {
                    ExtentTestManager.getTest().log(Status.PASS, "Phase 1 accounting happened for control sum");
                    ExtentTestManager.codeLogsXML(DatabaseUtil.databaseHandlerForPocDB(dataFromEventLogEntityTableQuery, "content"));
                }

                //------------------------------------------------------------------------------------------------------------------------------

                //fetch the accounting logs for transactions ids one by one using for loop
                for (int i = 0; i < NoOfRowsTranIds; i++) {

                    //query for transaction table
                    dataFromTransactionTableQuery = "select * from makola_payments_portal_sit.transaction where" +
                            " transaction_id='" + TransactionId.get(i) + "'";

                    //query for event log entity table
                    dataFromEventLogEntityTableQuery = "select * from mk_sfi_event_store.event_log_entity where country='" + BankCountry + "' " +
                            "and event_type ='account-posting-ready-event'" +
                            "and content::text like '%" + TransactionId.get(i) + "%'";

                    //query for event log entity table for reversal
                    fetchStatusOfReversedTransactionQuery = "select content from mk_sfi_event_store.event_log_entity where country='" + BankCountry + "' " +
                            "and event_type ='account-posting-ready-event'" +
                            "and content::text like '%" + TransactionId.get(i) + "R" + "%'";

                    //run query for ACPT and ACSP
                    if (DatabaseUtil.databaseHandlerForPocDB(dataFromTransactionTableQuery, "status").contains("ACPT")
                            || DatabaseUtil.databaseHandlerForPocDB(dataFromTransactionTableQuery, "status").contains("ACSP")) {
                        //run query for account logs
                        ExtentTestManager.getTest().info(MarkupHelper.createLabel("Transaction id : " + TransactionId.get(i) + " accounting", ExtentColor.BROWN));

                        //verify accounting for success transactions
                        if (DatabaseUtil.databaseHandlerForPocDB(dataFromEventLogEntityTableQuery, "content") == null) {
                            ExtentTestManager.getTest().log(Status.FAIL, "Phase 2 accounting did not happen for successful transaction");
                        } else {
                            ExtentTestManager.getTest().log(Status.PASS, "Phase 2 accounting happened for successful transaction");
                            ExtentTestManager.codeLogsXML(DatabaseUtil.databaseHandlerForPocDB(dataFromEventLogEntityTableQuery, "content"));
                        }

                        //verify that there is no reversal for successful transactions
                        if (DatabaseUtil.databaseHandlerForPocDB(fetchStatusOfReversedTransactionQuery, "content") == null) {
                            ExtentTestManager.getTest().log(Status.PASS, "Reversal not present for successful transaction");
                        } else {
                            ExtentTestManager.getTest().log(Status.FAIL, "Reversal is present for successful transaction");
                            ExtentTestManager.codeLogsXML(DatabaseUtil.databaseHandlerForPocDB(fetchStatusOfReversedTransactionQuery, "content"));
                        }
                    }
                    //run query for rejected transaction
                    else if (DatabaseUtil.databaseHandlerForPocDB(dataFromTransactionTableQuery, "status").contains("RJCT")) {
                        //fetch error reason
                        ExtentTestManager.getTest().info(MarkupHelper.createLabel("Transaction id : " + TransactionId.get(i) + " failed with error code "
                                + DatabaseUtil.databaseHandlerForPocDB(dataFromTransactionTableQuery, "status_reason"), ExtentColor.BROWN));

                        //verify reversal for failed transactions
                        if (DatabaseUtil.databaseHandlerForPocDB(fetchStatusOfReversedTransactionQuery, "content") == null) {
                            ExtentTestManager.getTest().log(Status.INFO, "Phase 1 accounting happened. " +
                                    "Reversal is not present for failed transaction");
                        } else {
                            ExtentTestManager.getTest().log(Status.PASS, "Reversal for failed transaction");
                            ExtentTestManager.codeLogsXML(DatabaseUtil.databaseHandlerForPocDB(fetchStatusOfReversedTransactionQuery, "content"));
                        }

                    } else {
                        ExtentTestManager.getTest().log(Status.FAIL, "Status of the transaction id is "+ DatabaseUtil.databaseHandlerForPocDB(dataFromTransactionTableQuery, "status"));
                    }
                }

                //----------close instruction id if loop here
            }
            else{
                ExtentTestManager.getTest().log(Status.FAIL, "Payment failed on Instruction level with error code "+ErrorCodeInstructionId);
            }

        }
        catch(Exception e){
            System.out.println("exception from class fetchStatements-"+e);
        }

        return this;}

    public BulkPaymentSearchPage validatePaymentInstructionBulkPaymentSearch(String correlationId,String[] paymentInfoId,String fileStatus,String[] processingStatus) throws Exception {

        System.out.println("Inside validatePaymentInstructionBulkPaymentSearch");

        //Navigate to Bulk Payment Search Screen by clicking correlation Id link of Payment Message File
        Actions action = new Actions(driver);
        action.moveToElement(driver.findElement(By.xpath("//a/span[.='"+ correlationId + "']"))).build().perform();
        highLighterMethod(By.xpath("//a/span[.='"+ correlationId + "']"));
        screenshotLoggerMethod("BulkFileListing","Payment Message File Details");
        Thread.sleep(2000);

        if(!fileStatus.equalsIgnoreCase("REJECTED")) {
            waitForElementToBeClickable(By.xpath("//a/span[.='"+ correlationId + "']"));
            ((JavascriptExecutor) driver).executeScript("arguments[0].click()", driver.findElement(By.xpath("//a/span[.='" + correlationId + "']")));

            Thread.sleep(2000);
            scrollDown();
            int j=1;
            for(int i=0;i<paymentInfoId.length;i++) {
                if(processingStatus[i] != null) {
                    String paymentInstructionxpath = "/html/body/app-root/div/div/div/payment-search/div/ngx-datatable/div/datatable-body/datatable-selection/datatable-scroller/datatable-row-wrapper[" + j + "]/datatable-body-row/div[2]/datatable-body-cell[3]/div/a";
                    waitForElementToBeVisible(By.xpath(paymentInstructionxpath));
                    String paymentInstructionStatusXpath = "/html/body/app-root/div/div/div/payment-search/div/ngx-datatable/div/datatable-body/datatable-selection/datatable-scroller/datatable-row-wrapper[" + j + "]/datatable-body-row/div[2]/datatable-body-cell[9]/div/span[1]";
                    String paymentInstructionStatus = readText(By.xpath(paymentInstructionStatusXpath));
                    ExtentTestManager.getTest().log(Status.INFO, "Payment Instruction " + j + " :: " + paymentInfoId[i] + " :: " + paymentInstructionStatus);
                    System.out.println("paymentInstructionStatus :: " + paymentInstructionStatus);

                    //waitForElementToBeVisible(By.xpath("//a[@href='null' and text() = '" + paymentInfoId[i] + "']"));
                    action.moveToElement(driver.findElement(By.xpath("//a[@href='null' and text() = '" + paymentInfoId[i] + "']"))).build().perform();
                    highLighterMethod(By.xpath("//a[@href='null' and text() = '" + paymentInfoId[i] + "']"));


                    if (paymentInstructionStatus.equalsIgnoreCase("FAILED")) {
                        String FailureReasonCodeInfo = "/html/body/app-root/div/div/div/payment-search/div/ngx-datatable/div/datatable-body/datatable-selection/datatable-scroller/datatable-row-wrapper[" + j + "]/datatable-body-row/div[2]/datatable-body-cell[9]/div/span[2]/a/span";
                        //Hover the mouse over the small information mark for failure reason
                        mouseHoverMethod(FailureReasonCodeInfo);
                        highLighterMethod(By.xpath(FailureReasonCodeInfo));
                    }
                    screenshotLoggerMethod("BulkPaymentSearchInstructions ", "Payment Instruction Details");
                    ((JavascriptExecutor) driver).executeScript("arguments[0].click()", driver.findElement(By.xpath("//a[@href='null' and text() = '" + paymentInfoId[i] + "']")));
                    Thread.sleep(5000);
                    scrollDown();
                    screenshotLoggerMethod("BulkPaymentSearchTransactions ", "Payment Transactions Details");
                    Thread.sleep(2000);
                    click(By.xpath("/html/body/app-root/div/div/div/payment-search/div/div[1]/div/div/sol-radio[1]/label"));
                    j++;
                }
                Thread.sleep(2000);
            }
        }

        return this; }


    public BulkPaymentSearchPage getCorrelationIdSFI(String filename) throws InterruptedException
    {
        Thread.sleep(10000);
        click(By.xpath(bulkFilesTab));
        Thread.sleep(30000);
        //System.out.println("filename is"+filename);
        isElementDisplayed("//span[contains(text(), '"+filename +"')]");
        String SFIfilename = readText(By.xpath("//span[contains(text(), '"+filename +"')]"));

        correlationIDSFI = readText(By.xpath("//span[contains(text(), '"+filename +"')]/ancestor :: div[@class ='datatable-row-center datatable-row-group']//a"));

        click(By.xpath("//span[contains(text(), '"+filename +"')]/ancestor :: div[@class ='datatable-row-center datatable-row-group']//a"));

        Thread.sleep(1000);

        InstructionIDSFI = readText(By.xpath(RefIDSFI));

        click(By.xpath(RefIDSFI));


        System.out.println("Retrieved file name is - " + SFIfilename);

        System.out.println("Retrieved CorrelationId is - " + correlationIDSFI);

        System.out.println("Retrieved InstructionId is - " + InstructionIDSFI);


        return this;}

    public BulkPaymentSearchPage selectChannelFromBulkListing() throws Exception
    {
        Thread.sleep(10000);
        click(By.xpath(bulkFilesTab));
        Thread.sleep(40000);
        screenshotLoggerMethod("Bulk File Listing", "Bulk File Listings ALL");
        click(By.xpath(selectChannelDropdown));
        click(By.xpath("//li[text() = 'PPO']"));
        Thread.sleep(20000);
        screenshotLoggerMethod("Bulk File Listing", "Bulk File Listings PPO");
        click(By.xpath(selectChannelDropdown));
        click(By.xpath("//li[text() = 'SFI']"));
        click(By.xpath("//li[text() = 'SFI']"));
        Thread.sleep(20000);
        screenshotLoggerMethod("Bulk File Listing", "Bulk File Listings SFI");
        click(By.xpath(selectChannelDropdown));
        click(By.xpath("//li[text() = 'BIR']"));
        Thread.sleep(20000);
        screenshotLoggerMethod("Bulk File Listing", "Bulk File Listings BIR");
        click(By.xpath(selectChannelDropdown));
        click(By.xpath("//li[text() = 'AOB']"));
        Thread.sleep(20000);
        screenshotLoggerMethod("Bulk File Listing", "Bulk File Listings AOB");
        click(By.xpath(selectChannelDropdown));
        click(By.xpath("//li[text() = 'SAP']"));
        Thread.sleep(20000);
        screenshotLoggerMethod("Bulk File Listing", "Bulk File Listings SAP");
        click(By.xpath(selectChannelDropdown));
        click(By.xpath("//li[text() = 'AMB']"));
        Thread.sleep(20000);
        screenshotLoggerMethod("Bulk File Listing", "Bulk File Listings AMB");
        click(By.xpath(selectChannelDropdown));
        click(By.xpath("//li[text() = 'H2H']"));
        Thread.sleep(20000);
        screenshotLoggerMethod("Bulk File Listing", "Bulk File Listings H2H");
        click(By.xpath(selectChannelDropdown));
        click(By.xpath("//li[text() = 'PPU']"));
        Thread.sleep(20000);
        screenshotLoggerMethod("Bulk File Listing", "Bulk File Listings PPU");



        return this;}

    public String convertToJson(String message){
        String str=null;
        try{
            //under construction
            str=message;
        }
        catch(Exception e){
            System.out.println("exception-"+e);
        }
        return str;
    }
}