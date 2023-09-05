package helpers;

import base.BasePage;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import org.openqa.selenium.WebDriver;
import org.xml.sax.SAXException;
import utils.extentreports.ExtentTestManager;
import utils.logs.Log;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import java.io.IOException;
import java.sql.SQLException;
import static Utilities.DatabaseUtil.databaseHandlerForPocDB;
import static utils.extentreports.ExtentTestManager.codeLogsXML;

public class ValidateInwardTransactionsOnDB extends BasePage {
    public ValidateInwardTransactionsOnDB(WebDriver driver) {

        super(driver);
        Log.info("The current working class: "+this.getClass().getName());
    }

    public String strCorrelationIDQuery = null;
    public String strCorrelationIDValue = null;
    public String strCorrelationValueOnDBColumnName_1 = "correlation_id";//Column name in the DB
    public String strWindowStatusQuery = null;
    public String strWindowStatusValue = null;
    public String strTransactionStatusValue = null;
    public String strCBSResponseQuery = null;
    public String strCBSResponseValue = null;

    public String strCBSResponseFilePath = "./testOutputs/cbs_response/";


    public void validateCorrelationID() throws ParserConfigurationException, SAXException, XPathExpressionException, IOException, InterruptedException, ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException, Exception
    {
        //TODO::Add condition to cater for payment received while window was closed & do followings with SystemRef,PaymentRequestWindow being input parameter:

        //*****************************Get,Validate and store the Correlation ID**************************************************************
        try {
            int i = 0;
            int intDatabaseWaitTimeIndex = 2;

            //Retry the SQL Query execution if there is no value in the DB on the first attempt
            try {
                do {

                    //fetching the correlation Id using the system ref
                    strCorrelationIDQuery = "select transaction_id, amount, correlation_id from  mk_seychelles_clearing.clearing_transaction_entity" +
                            " where system_reference ='"+systemReference+"'";

                    //Store the correlation Id in a string
                    strCorrelationIDValue = databaseHandlerForPocDB(strCorrelationIDQuery, strCorrelationValueOnDBColumnName_1);
                    System.out.println("System Reference:: " + systemReference);
                    System.out.println("Correlation ID:: " + strCorrelationIDValue);

                    //Write the value to a report
                    if (strCorrelationIDValue != null) {
                        /**INFO*/
                        ExtentTestManager.getTest().log(Status.PASS, "Status=PASS: Correlation ID below");
                        codeLogsXML(strCorrelationIDValue);

                        //Print out the PAYLOAD
                        System.out.println("=================================Correlation ID FROM DB =================================");
                        System.out.println("Info: Current Correlation ID " + strCorrelationIDValue);

                        break;

                    } else {
                        // ExtentTestManager.getTest().log(Status.WARNING, "Warning:: No Records Where Found In The Database on the 1st attempt ,the script will attempt to retry in 5 seconds");
                        //TODO: Marking the soft red flags / system poor bahavior in a report instead of above line use below
                        ExtentTestManager.getTest().warning(MarkupHelper.createLabel("Warning:: No Records Where Found In The Database on the 1st attempt ,the script will attempt to retry in 5 seconds for Correlation ID", ExtentColor.AMBER));
                        Thread.sleep(5000);
                        i++;
                    }
                } while (i <= intDatabaseWaitTimeIndex);


                /**Stop the test if there are no records in the DB after the allocated time*/
                if (strCorrelationIDValue == null & i > intDatabaseWaitTimeIndex) {
                    ExtentTestManager.getTest().fail(MarkupHelper.createLabel("No Records Where Found In The Database ,The test has been stopped", ExtentColor.RED));
                    stopCurrentTestSetResultsToFailedAndContinueWithNextTest();
                }

            } catch (Exception e) {
                ExtentTestManager.getTest().log(Status.FAIL, "Status=FATAL: No Records Where Found In The Database. Value not saved Correlation ID");
            }

        }
        catch (Exception e)
        {
            ExtentTestManager.getTest().log(Status.FAIL, "Status=FATAL: No Records Where Found In The Database. Correlation ID not saved");
        }

    }

    public String validateWindowStatus(String WindowState, String PaymentRequestWindow, String SystemReference)throws Exception
    {
        if(PaymentRequestWindow.equalsIgnoreCase("Previous")){
            systemReference = SystemReference;
        }

        strWindowStatusQuery = "select id, system_reference, status, direction, creditor_account_number_enrichment, eligible_to_process_status from mk_seychelles_clearing.clearing_transaction_entity " +
                "where system_reference='"+systemReference+"'";

        strWindowStatusValue = databaseHandlerForPocDB(strWindowStatusQuery,"eligible_to_process_status");
        System.out.println("Window status:: "+ strWindowStatusValue);

        //Write the value to a report
        if (strWindowStatusValue != null) {

            /**INFO*/
            ExtentTestManager.getTest().log(Status.PASS, "Status=PASS: Current Window Status below");
            codeLogsXML(strWindowStatusValue);

            //Print out the PAYLOAD
            System.out.println("=================================Current Window Status FROM DB =================================");
            System.out.println("Info: Current Window Status " + strWindowStatusValue);

            if(WindowState.equalsIgnoreCase(strWindowStatusValue))
            {
                ExtentTestManager.getTest().log(Status.PASS, "Window State is as Expected");
            }else{
                ExtentTestManager.getTest().log(Status.FAIL, "Window State is NOT as Expected");
            }

        } else {
            // ExtentTestManager.getTest().log(Status.WARNING, "Warning:: No Records Where Found In The Database on the 1st attempt ,the script will attempt to retry in 5 seconds");
            //TODO: Marking the soft red flags / system poor bahavior in a report instead of above line use below
            ExtentTestManager.getTest().warning(MarkupHelper.createLabel("Warning:: No Records Where Found In The Database for Window Status", ExtentColor.AMBER));
            Thread.sleep(5000);
        }
        return strWindowStatusValue;
    }

    public String validateTransactionProcessingStatus(String PaymentRequestWindow, String SystemReference)throws Exception
    {
        if(PaymentRequestWindow.equalsIgnoreCase("Previous")){
            systemReference = SystemReference;
        }
        strWindowStatusQuery = "select id, system_reference, status, direction, creditor_account_number_enrichment, eligible_to_process_status from mk_seychelles_clearing.clearing_transaction_entity " +
                "where system_reference='"+systemReference+"'";

        strTransactionStatusValue = databaseHandlerForPocDB(strWindowStatusQuery,"status");
        System.out.println("Transaction Processing Status:: "+ strTransactionStatusValue);

        //Write the value to a report
        if (strTransactionStatusValue != null) {
            /**INFO*/
            ExtentTestManager.getTest().log(Status.PASS, "Status=PASS: Current Transaction Processing Status below");
            codeLogsXML(strTransactionStatusValue);

            //Print out the PAYLOAD
            System.out.println("=================================Current Window Status FROM DB =================================");
            System.out.println("Info: Current Transaction Processing Status " + strTransactionStatusValue);

        } else {
            // ExtentTestManager.getTest().log(Status.WARNING, "Warning:: No Records Where Found In The Database on the 1st attempt ,the script will attempt to retry in 5 seconds");
            //TODO: Marking the soft red flags / system poor bahavior in a report instead of above line use below
            ExtentTestManager.getTest().warning(MarkupHelper.createLabel("Warning:: No Records Where Found In The Database for Transaction Processing Status", ExtentColor.AMBER));
            Thread.sleep(5000);
        }
        return strTransactionStatusValue;
    }

    public String validateCBSResponse(String PaymentRequestWindow, String SystemReference, String fileName)throws Exception
    {
        if(PaymentRequestWindow.equalsIgnoreCase("Previous")){
            systemReference = SystemReference;
        }
        System.out.println(systemReference +"our system reference for payment to complete");
        try
        {
            int i = 0;
            int intDatabaseWaitTimeIndex = 2;

            do
            {
                strCBSResponseQuery = "select *, convert_from(loread(lo_open(message_value::int, x'40000'::int), x'40000'::int), 'UTF8') from mk_seychelles_clearing.clearing_message_entity\n" +
                        "where system_reference ='"+systemReference+"' and message_type = 'MQ_PAYMENT_RESPONSE_INWARD';";

                strCBSResponseValue = databaseHandlerForPocDB(strCBSResponseQuery,"convert_from");
                System.out.println("CBS Response:: \n"+ strCBSResponseValue);

                //Write the value to a report
                if (strCBSResponseValue != null) {
                    /**INFO*/
                    ExtentTestManager.getTest().log(Status.PASS, "Status=PASS: Current Transaction CBS Response below");
                    codeLogsXML(strCBSResponseValue);

                    //Print out the PAYLOAD
                    System.out.println("=================================Current Window Status FROM DB =================================");
                    System.out.println("Info: Current Transaction Processing Status " + strCBSResponseValue);

                    writeTextToFile(strCBSResponseFilePath,fileName,strCBSResponseValue);
                    break;

                } else {
                    // ExtentTestManager.getTest().log(Status.WARNING, "Warning:: No Records Where Found In The Database on the 1st attempt ,the script will attempt to retry in 5 seconds");
                    //TODO: Marking the soft red flags / system poor bahavior in a report instead of above line use below
                    ExtentTestManager.getTest().warning(MarkupHelper.createLabel("Warning:: No Records Where Found In The Database for Transaction Processing Status", ExtentColor.AMBER));
                    Thread.sleep(5000);
                }
                i++;

            }while (i<=intDatabaseWaitTimeIndex);

        }catch (Exception e)
        {
            ExtentTestManager.getTest().log(Status.FAIL, "Status=FATAL: No Records Where Found In The Database. CBS Response not Found / Saved");
        }
        return strCBSResponseValue;
    }


    //Write above database responses to file and Mark Tests accordingly
}