package helpers;

import base.BasePage;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import config.ConfigReader;
import config.Settings;
import org.openqa.selenium.WebDriver;
import utils.extentreports.ExtentTestManager;
import utils.logs.Log;

import java.io.IOException;
import java.util.List;
import static Utilities.DatabaseUtil.databaseHandlerForFlexDB;

/**
 * @author Jonathan Mbuyi Tshitenda [ ABJT439 ]
 * @date created 9/16/2019
 * @package helpers
 */


public class ValidateDebtorTransactionInFlex extends BasePage {

    public ValidateDebtorTransactionInFlex(WebDriver driver) {
        super(driver);
        Log.info("The current working class: "+this.getClass().getName());
    }

    public String strQueryGetDebtorTxnRecord = null;
    public static String strOracleRecordColumnName_1 = "DAT_TXN";//Column name in the DB
    public static String strOracleRecordColumnName_2 = "DAT_VALUE";//Column name in the DB
    public static String strOracleRecordColumnName_3 = "TXT_TXN_DESC";//Column name in the DB
    public static String strOracleRecordColumnName_4 = "TXT_TXN_USER_DESC";//Column name in the DB
    public static String strOracleRecordColumnName_5 = "REF_CHQ_NO";//Column name in the DB
    public static String strOracleRecordColumnName_6 = "COD_DRCR";//Column name in the DB
    public static String strOracleRecordColumnName_7 = "COD_CC_BRN_TXN";//Column name in the DB
    public static String strOracleRecordColumnName_8 = "AMT_TXN";//Column name in the DB
    public static String strOracleRecordColumnName_9 = "RAT_CCY";//Column name in the DB
    public static String strOracleRecordColumnName_10 = "REF_TXN_NO";//Column name in the DB
    public static String strOracleRecordColumnName_11 = "REF_USR_NO";//Column name in the DB
    public List<String> strDebtorTxnRecord = null;


    public void validateDebtorTransactionInFlexSingleTxn(String strDebtorAccountNumber, String strCurrentEndToEndId, String DebtorControlSum) throws IOException
    {

        /**Initialize config reader to read configs from Settings*/
        ConfigReader.PopulateSettings();
        String dbDriver = Settings.DBaFlexJdbc;


        //*****************************Get,Validate and store the PAIN001 XML**************************************************************
        try {
            int i = 0;
            int intDatabaseWaitTimeIndex = 2;

            //Retry the SQL Query execution if there is no payload in the DB on the first attempt
            try{
                do
                {
                    /**=================Determine which database to use ======================================*/
                    if(dbDriver.contains("postgresql"))
                    {
                        System.out.println("Using Postgresql database");

                        //Retrieve the transaction record from the Database using the debtor account number and EndToEndID
                        strQueryGetDebtorTxnRecord = "";

                        //Store the record in a string
                        strDebtorTxnRecord = databaseHandlerForFlexDB(strQueryGetDebtorTxnRecord,
                                strOracleRecordColumnName_1,strOracleRecordColumnName_2,strOracleRecordColumnName_3,strOracleRecordColumnName_4,
                                strOracleRecordColumnName_5,strOracleRecordColumnName_6,strOracleRecordColumnName_7,strOracleRecordColumnName_8,
                                strOracleRecordColumnName_9,strOracleRecordColumnName_10,strOracleRecordColumnName_11);
                    }
                    else if(dbDriver.contains("mysql") || dbDriver.contains("oracle"))
                    {
                        System.out.println("Using oracle database");

                        //Retrieve the transaction record from the Database using the debtor account number and EndToEndID
                        ///**SQL query For Flex DB*/
                        strQueryGetDebtorTxnRecord = "SELECT DAT_TXN,DAT_VALUE,TXT_TXN_DESC,TXT_TXN_USER_DESC,REF_CHQ_NO,COD_DRCR,COD_CC_BRN_TXN,AMT_TXN,RAT_CCY,REF_TXN_NO,REF_USR_NO " +
                                "FROM Ch_nobook " +
                                "WHERE Cod_acct_no = '"+strDebtorAccountNumber+"' " +
                                "AND REF_USR_NO = '"+strCurrentEndToEndId+"' " +
                                "AND REF_USR_NO IS NOT NULL ORDER BY 1 DESC";

                        //Store the record in a string
                        strDebtorTxnRecord = databaseHandlerForFlexDB(strQueryGetDebtorTxnRecord,
                                strOracleRecordColumnName_1,strOracleRecordColumnName_2,strOracleRecordColumnName_3,strOracleRecordColumnName_4,
                                strOracleRecordColumnName_5,strOracleRecordColumnName_6,strOracleRecordColumnName_7,strOracleRecordColumnName_8,
                                strOracleRecordColumnName_9,strOracleRecordColumnName_10,strOracleRecordColumnName_11);
                    }

                    //Save the record to a file
                    if (strDebtorTxnRecord != null)
                    {
                        String string = String.valueOf(strDebtorTxnRecord);
                        String[] splited = string.split("\\|"); //Use "|" as the separator to slipt the string
                        String strValidationStatus = null;  //To set the status to Pass/Fail

                        //Store split records in array
                        String strTxn_Date      =splited[0];
                        String strValue_Data       =splited[1];
                        String strDescription      =splited[2];
                        String strUser_Description =splited[3];
                        String strCheque_No        =splited[4];
                        String strDr_Cr            =splited[5];
                        String strOrig_Brn         =splited[6];
                        String strAmount           =splited[7].replaceAll("[^a-zA-Z0-9\\\\._-]", "");//Remove the white spaces
                        String strRunning_Total    =splited[8];
                        String strRef_Txn_No       =splited[9];
                        String strEndToEndId       =splited[10];

                        double dbAmount = Double.parseDouble(strAmount);
                        double dbDebtorControlSum = Double.parseDouble(DebtorControlSum);


                        //Perform validations
                        if(dbAmount == dbDebtorControlSum)
                        {
                            strValidationStatus = "PASS";


                            //Print out the results
                            /**INFO*/
                            ExtentTestManager.getTest().pass(MarkupHelper.createLabel("Status=PASS: Debtor Transaction Record from Flex DB below",ExtentColor.GREEN));

                            String[][] data =
                                    {
                                            //Headings
                                            {"Txn_Date","Value_Data","Acc#","Description","User_Description","Cheque_No","Dr/Cr","Orig.Brn","Amount","Running_Total","REF_TXN_NO","EndToEndId","Status"},
                                            {"=========","========","=====","===========","==============","=========","=====","========","======","======","=============","==========","=========="},
                                            //Contents
                                            {strTxn_Date,strValue_Data,strDebtorAccountNumber,strDescription,strUser_Description,strCheque_No,strDr_Cr,strOrig_Brn,strAmount,strRunning_Total,strRef_Txn_No,strEndToEndId,strValidationStatus}};

                            Markup m = MarkupHelper.createTable(data);
                            ExtentTestManager.getTest().pass(m);
                        }
                        else{
                            strValidationStatus = "FAIL";

                            //Print out the results
                            /**INFO*/
                            ExtentTestManager.getTest().pass(MarkupHelper.createLabel("Status=FAILED: Debtor Transaction Amount is not equal to expected,see transaction from Flex DB below",ExtentColor.RED));

                            String[][] data =
                                    {
                                            //Headings
                                            {"Txn_Date","Value_Data","Acc#","Description","User_Description","Cheque_No","Dr/Cr","Orig.Brn","Amount","Running_Total","REF_TXN_NO","EndToEndId","Status"},
                                            {"=========","========","=====","===========","==============","=========","=====","========","======","======","=============","==========","=========="},
                                            //Contents
                                            {strTxn_Date,strValue_Data,strDebtorAccountNumber,strDescription,strUser_Description,strCheque_No,strDr_Cr,strOrig_Brn,strAmount,strRunning_Total,strRef_Txn_No,strEndToEndId,strValidationStatus}};

                            Markup m = MarkupHelper.createTable(data);
                            ExtentTestManager.getTest().fail(m);
                        }



                        //Print out the PAYLOAD
                        System.out.println("==========Debtor Transaction Record FROM DB ==================================");
                        System.out.println("Info: Current Debtor Transaction Record " +strDebtorTxnRecord);

                        break;

                    }
                    else
                    {
                        ExtentTestManager.getTest().log(Status.WARNING, "Warning:: No Records Where Found In The Database on the 1st attempt ,the script will attempt to retry in 20 seconds");
                        Thread.sleep(20000);
                        i++;
                    }
                }while (i <=intDatabaseWaitTimeIndex);


                /**Stop the test if there are no records in the DB after the allocated time*/
                if(strDebtorTxnRecord==null & i>intDatabaseWaitTimeIndex)
                {
                    ExtentTestManager.getTest().fail(MarkupHelper.createLabel("No Records Where Found In The Database ,The test has been stopped", ExtentColor.RED));
                    stopCurrentTestSetResultsToFailedAndContinueWithNextTest();
                }

            }catch (Exception e)
            {
                ExtentTestManager.getTest().log(Status.FAIL, "Status=FATAL: No Records Where Found In The Database.File not created");
            }
        }catch (Exception e)
        {
            ExtentTestManager.getTest().log(Status.FAIL, "Exception=FATAL: Database data retrieval failed :: "+ e.getMessage());
        }
    }
}