//package helpers;
//
//import com.aventstack.extentreports.Status;
//import com.aventstack.extentreports.markuputils.ExtentColor;
//import com.aventstack.extentreports.markuputils.Markup;
//import com.aventstack.extentreports.markuputils.MarkupHelper;
//import config.ConfigReader;
//import config.Settings;
//import org.apache.commons.lang3.StringUtils;
//import utils.extentreports.ExtentTestManager;
//
//import java.sql.*;
//import java.util.List;
//import static Utilities.DatabaseUtil.databaseHandlerForFlexDB;
//import static utils.extentreports.ExtentTestManager.codeLogsXML;
//
//
///**
// * @author Jonathan Mbuyi Tshitenda [ ABJT439 ]
// * @date created 9/17/2019
// * @package helpers
// */
//public class ValidateProcessingSuspenseAccount {
//
//    public String strQueryGetCreditorTxnRecord = null;
//    public String strQueryGetDebtorTxnRecord = null;
//    public List<String> strCreditorTxnRecord = null;
//    public List<String> strDebitedTxnRecord = null;
//
//    public static String strOracleRecordColumnName_1 = "DAT_TXN";//Column name in the DB
//    public static String strOracleRecordColumnName_2 = "DAT_VALUE";//Column name in the DB
//    public static String strOracleRecordColumnName_3 = "DAT_TXN_STR";//Column name in the DB
//    public static String strOracleRecordColumnName_4 = "DAT_VALUE_STR";//Column name in the DB
//    public static String strOracleRecordColumnName_5 = "COD_CC_BRN";//Column name in the DB
//    public static String strOracleRecordColumnName_6 = "COD_ACCT_NO";//Column name in the DB
//    public static String strOracleRecordColumnName_7 = "AMT_TXN_TCY";//Column name in the DB
//    public static String strOracleRecordColumnName_8 = "AMT_TXN_LCY";//Column name in the DB
//    public static String strOracleRecordColumnName_9 = "TXN_NRRTV";//Column name in the DB
//    public static String strOracleRecordColumnName_10 = "FLG_DRCR";//Column name in the DB
//    public static String strOracleRecordColumnName_11 = "REF_USR_NO";//Column name in the DB
//
//
//    //Flex Validations variables
//    public String strFlexTestValidations = null;//To set the status to Pass/Fail
//    String strSuspenseCreditAccountActualFeesDescription = "N/A";
//    String strSuspenseDebitAccountActualFeesDescription = "N/A";
//    String strDebitAccountActualFeesDescription = "N/A";
//    String strPNLCreditAccountActualFeesDescription = "N/A";
//    String strDifferentChargesDebitAccountActualFeesDescription = "N/A";
//    String doubleExpectedFeesAmountApplied = "N/A";
//    String doubleSuspenseCreditAccountActualFeesAmount = "N/A";
//    String doubleSuspenseDebitAccountActualFeesAmount = "N/A";
//    String doubleClearingAccountCreditAccountActualAmountTotal = "N/A";
//    String doublePNLCreditAccountFeesAmount = "N/A";
//    String doubleDifferentChargesDebitAccountFeesAmount = "N/A";
//    String strSuspenseCreditAccountFeesAmountAssertion = "N/A";
//    String strSuspenseDebitAccountFeesAmountAssertion = "N/A";
//    String strClearingAccountCreditAccountTransactionsAmountAssertion = "N/A";
//    String strPNLCreditAccountFeesAmountAssertion = "N/A";
//    String strDifferentChargesDebitAccountFeesAmountAssertion = "N/A";
//
//
//    //Credited suspense account variables
//    public double dbCrAmountTCY;
//    public double dbCrAmountTCYActualTotal;
//    public double dbCrAmountLCY;
//    public double dbCrAmountLCYActualTotal;
//    public double dbCrCreditorControlSum;
//    String strSuspenseAccountCreditedAmountValidation;
//    String strSuspenseAccountCreditTransactionFeesValidations;
//
//    //Debited suspense account variables
//    public double dbDbAmountTCY;
//    public double dbDbAmountTCYActualTotal;
//    public double dbDbAmountLCY;
//    public double dbDbAmountLCYActualTotal;
//    String strSuspenseAccountDebtorAmountValidation;
//    String strSuspenseAccountDebitATransactionFeesValidations;
//
//    //Clearing Account variables
//
//
//    //PNL Account variables
//    String strPNLCreditAccountTransactionFeesValidations;//TODO
//
//    //Different charges Account variables
//    String strDifferentChargesDebitAccountTransactionFeesValidations;//TODO
//
//
//    /**Check the transactions suspense account*/
//    public void validateCreditorProcessingSuspenseAccount(String ProcessingSuspenseAccount,String strCreditorAccountNumber,String CreditorControlSum,String strCurrentEndToEndId,String TestTransactionCategory,String DebtorWaiveChargesOption) throws Exception
//    {
//
//        /**Initialize config reader to read configs from Settings*/
//        ConfigReader.PopulateSettings();
//        String dbDriver = Settings.DBaFlexJdbc;
//
//        //Check the that the IAT transaction does'nt check the suspense account.
//        if(TestTransactionCategory.equalsIgnoreCase("IAT"))
//        {
//            ExtentTestManager.getTest().info(MarkupHelper.createLabel("This is an IAT transaction,therefore the Suspense Account will not be checked",ExtentColor.BLUE));
//        }
//        else
//        {
//            //**************************GET THE AMOUNT CREDITED TO THE SUSPENSE ACCOUNT*********************************
//            //**********************************************************************************************************
//            try {
//                int i = 0;
//                int intDatabaseWaitTimeInde = 2;
//
//                //Retry the SQL Query execution if there is no payload in the DB on the first attempt
//                try {
//
//                    do {
//                        /**=================Determine which database to use ==========================================*/
//                        if (dbDriver.contains("postgresql"))
//                        {
//                            System.out.println("Using Postgresql database");
//
//                            //Retrieve the transaction record from the Database using the debtor account number and EndToEndID
//                            strQueryGetCreditorTxnRecord = "";
//
//                            //Store the record in a string
//                            strCreditorTxnRecord = databaseHandlerForFlexDB(strQueryGetCreditorTxnRecord,
//                                    strOracleRecordColumnName_1, strOracleRecordColumnName_2, strOracleRecordColumnName_3, strOracleRecordColumnName_4,
//                                    strOracleRecordColumnName_5, strOracleRecordColumnName_6, strOracleRecordColumnName_7, strOracleRecordColumnName_8,
//                                    strOracleRecordColumnName_9, strOracleRecordColumnName_10, strOracleRecordColumnName_11);
//                        }
//                        else if (dbDriver.contains("mysql") || dbDriver.contains("oracle"))
//                        {
//                            System.out.println("Using oracle database");
//
//                            //Retrieve the transaction record from the Database using the debtor account number and EndToEndID
//                            ///**SQL query For Flex DB*/
//                            strQueryGetCreditorTxnRecord = "SELECT DAT_TXN,DAT_VALUE,DAT_TXN_STR,DAT_VALUE_STR,COD_CC_BRN,COD_ACCT_NO,AMT_TXN_TCY,AMT_TXN_LCY,TXN_NRRTV,FLG_DRCR,REF_USR_NO " +
//                                    "FROM XF_OL_ST_TXNLOG_CURRENT " +
//                                    "WHERE COD_FROM_ACCT_NO = '" + ProcessingSuspenseAccount + "' " +
//                                    "AND REF_USR_NO = '" + strCurrentEndToEndId + "'" +
//                                    "AND FLG_DRCR ='C' " +
//                                    "ORDER BY COD_FROM_ACCT_NO ASC";
//
//                            //Store the record in a string
//                            strCreditorTxnRecord = databaseHandlerForFlexDB(strQueryGetCreditorTxnRecord,
//                                    strOracleRecordColumnName_1, strOracleRecordColumnName_2, strOracleRecordColumnName_3, strOracleRecordColumnName_4,
//                                    strOracleRecordColumnName_5, strOracleRecordColumnName_6, strOracleRecordColumnName_7, strOracleRecordColumnName_8,
//                                    strOracleRecordColumnName_9, strOracleRecordColumnName_10, strOracleRecordColumnName_11);
//
//
//                        }
//
//                        //Save the record in an array
//                        if (strCreditorTxnRecord != null)
//                        {
//                            /**Iterate through the number of transaction found to get all the amounts and add them up*/
//                            for (int j = 0; j < strCreditorTxnRecord.size(); j++)
//                            {
//                                String strDBresults = String.valueOf(strCreditorTxnRecord.get(j));
//                                String[] splited = strDBresults.split("\\|"); //Use "|" as the separator to slipt the string //TODO GET TO SPLIT THE RETURNED TEXT IN AN ARRAY FROMAT
//
//                                //Store split records in array
//                                String strCrDat_Txn = splited[0];
//                                String strCrDat_Value = splited[1];
//                                String strCrDat_Txn_Str = splited[2];
//                                String strCrDat_Value_Txn_Str = splited[3];
//                                String strCrCod_CC_Brn = splited[4];
//                                String strCCrod_Acct_No = splited[5];
//                                String strCrAmt_Txn_tcy = splited[6].replaceAll("[^a-zA-Z0-9\\\\._-]", "");
//                                String strCrAmt_Txn_lcy = splited[7].replaceAll("[^a-zA-Z0-9\\\\._-]", "");//Remove the white spaces
//                                String strCrTxn_Nrrtv = splited[8];
//                                String strCrFlg_Drcr = splited[9];
//                                String strCrEndToEndId = splited[10];
//
//                                dbCrAmountTCY = Double.parseDouble(strCrAmt_Txn_tcy);
//                                dbCrAmountLCY = Double.parseDouble(strCrAmt_Txn_lcy);
//
//                                //Add the amount in each iteration to get the total sums
//                                dbCrAmountTCYActualTotal += dbCrAmountTCY;
//                                dbCrAmountLCYActualTotal += dbCrAmountLCY;
//                            }
//
//                            //Exist the main DO WHILE LOOP
//                            break;
//                        }
//                        else
//                            {
//                                ExtentTestManager.getTest().log(Status.WARNING, "Warning:: No Records Where Found In The Database on the 1st attempt ,the script will attempt to retry in 20 seconds");
//                                Thread.sleep(20000);
//                                i++;
//                            }
//                    } while (i <= intDatabaseWaitTimeInde);
//
//
//
//                /**Check the fees applied to this transaction*/
//                if (DebtorWaiveChargesOption.equalsIgnoreCase("NO"))
//                {
//
//                    //TODO
//                     /**Perform Fees validations if Fees are applied to the transaction*/
//                    //==================Check for Credit Suspense Account fees applied =================================
//                    if (DebtorWaiveChargesOption.equalsIgnoreCase("NO"))
//                    {
//                        /**Once Fees have been confirmed to be on the statement,the below validations must performed */
//                        //TODO
//                    }
//
//                    //==============================Fees Validations/Assertions=========================================
//                    /**Check if the correct fees description/reference is applied for the payment-purpose in question
//                     * And the fees amount are corresponding */
//                    //TODO
//
//                }
//
//                    /**Check for Credit Suspense Account transaction amounts*/
//                System.out.println("======================Flex Table Suspense Credit Account Validation================");
//
//                    /**Get totals debit amount (round-off to two decimal places) from ebox*
//                     *Convert string from excel to integer/double */
//                  dbCrCreditorControlSum = Double.parseDouble(CreditorControlSum);
//
//                  if(dbCrAmountLCYActualTotal==dbCrCreditorControlSum)
//                    {
//                        strSuspenseAccountCreditedAmountValidation = "PASS";
//                    }
//                  else
//                    {
//                        strSuspenseAccountCreditedAmountValidation = "FAIL";
//                    }
//
//                //Take Screenshot
//                //screenshotLoggerMethod("checkTransactionSuspenseAccountEnd", "Transaction Suspense Account Validations");
//
//            } catch (Exception e)
//                {
//                ExtentTestManager.getTest().log(Status.FAIL, "Exception=FATAL: Checking available balance on the suspense account failed due to ::  " + e.getMessage());
//                screenshotLoggerMethod("checkTransactionSuspenseAccount", "Transaction Suspense Account Validations Failure");
////                stopCurrentTestSetResultsToFailedAndContinueWithNextTest();
//            }
//            }catch (Exception e)
//            {
//                ExtentTestManager.getTest().log(Status.FAIL, "Exception=FATAL: Database data retrieval failed :: "+ e.getMessage());
//            }
//        }
//    }
//
//
//    public void validateDebtorProcessingSuspenseAccount(String ProcessingSuspenseAccount,String strCreditorAccountNumber,String CreditorControlSum,String strCurrentEndToEndId,String TestTransactionCategory,String DebtorWaiveChargesOption) throws Exception
//    {
//
//        /**Initialize config reader to read configs from Settings*/
//        ConfigReader.PopulateSettings();
//        String dbDriver = Settings.DBaFlexJdbc;
//
//        //Check the that the IAT transaction does'nt check the suspense account.
//        if(TestTransactionCategory.equalsIgnoreCase("IAT"))
//        {
//            ExtentTestManager.getTest().info(MarkupHelper.createLabel("This is an IAT transaction,therefore the Suspense Account will not be checked",ExtentColor.BLUE));
//        }
//        else
//        {
//            //**************************GET THE AMOUNT DEBITED TO THE SUSPENSE ACCOUNT*********************************
//            //**********************************************************************************************************
//            try {
//                int i = 0;
//                int intDatabaseWaitTimeInde = 2;
//
//                //Retry the SQL Query execution if there is no payload in the DB on the first attempt
//                try {
//
//                    do {
//                        /**=================Determine which database to use ==========================================*/
//                        if (dbDriver.contains("postgresql"))
//                        {
//                            System.out.println("Using Postgresql database");
//
//                            //Retrieve the transaction record from the Database using the debtor account number and EndToEndID
//                            strQueryGetDebtorTxnRecord = "";
//
//                            //Store the record in a string
//                            strDebitedTxnRecord = databaseHandlerForFlexDB(strQueryGetDebtorTxnRecord,
//                                    strOracleRecordColumnName_1, strOracleRecordColumnName_2, strOracleRecordColumnName_3, strOracleRecordColumnName_4,
//                                    strOracleRecordColumnName_5, strOracleRecordColumnName_6, strOracleRecordColumnName_7, strOracleRecordColumnName_8,
//                                    strOracleRecordColumnName_9, strOracleRecordColumnName_10, strOracleRecordColumnName_11);
//                        }
//                        else if (dbDriver.contains("mysql") || dbDriver.contains("oracle"))
//                        {
//                            System.out.println("Using oracle database");
//
//                            //Retrieve the transaction record from the Database using the debtor account number and EndToEndID
//                            ///**SQL query For Flex DB*/
//                            strQueryGetDebtorTxnRecord = "SELECT DAT_TXN,DAT_VALUE,DAT_TXN_STR,DAT_VALUE_STR,COD_CC_BRN,COD_ACCT_NO,AMT_TXN_TCY,AMT_TXN_LCY,TXN_NRRTV,FLG_DRCR,REF_USR_NO " +
//                                    "FROM XF_OL_ST_TXNLOG_CURRENT " +
//                                    "WHERE COD_FROM_ACCT_NO = '" + ProcessingSuspenseAccount + "' " +
//                                    "AND REF_USR_NO = '" + strCurrentEndToEndId + "'" +
//                                    "AND FLG_DRCR ='D' " +
//                                    "ORDER BY COD_FROM_ACCT_NO ASC";
//
//                            //Store the record in a string
//                            strDebitedTxnRecord = databaseHandlerForFlexDB(strQueryGetDebtorTxnRecord,
//                                    strOracleRecordColumnName_1, strOracleRecordColumnName_2, strOracleRecordColumnName_3, strOracleRecordColumnName_4,
//                                    strOracleRecordColumnName_5, strOracleRecordColumnName_6, strOracleRecordColumnName_7, strOracleRecordColumnName_8,
//                                    strOracleRecordColumnName_9, strOracleRecordColumnName_10, strOracleRecordColumnName_11);
//
//
//                        }
//
//                        //Save the record in an array
//                        if (strDebitedTxnRecord != null)
//                        {
//                            /**Iterate through the number of transaction found to get all the amounts and add them up*/
//                            for (int j = 0; j < strDebitedTxnRecord.size(); j++)
//                            {
//                                String strDBresults = String.valueOf(strDebitedTxnRecord.get(j));
//                                String[] splited = strDBresults.split("\\|"); //Use "|" as the separator to slipt the string //TODO GET TO SPLIT THE RETURNED TEXT IN AN ARRAY FROMAT
//
//                                //Store split records in array
//                                String strDbDat_Txn = splited[0];
//                                String strDbDat_Value = splited[1];
//                                String strDbDat_Txn_Str = splited[2];
//                                String strDbDat_Value_Txn_Str = splited[3];
//                                String strDbCod_CC_Brn = splited[4];
//                                String strDbrod_Acct_No = splited[5];
//                                String strDbAmt_Txn_tcy = splited[6].replaceAll("[^a-zA-Z0-9\\\\._-]", "");
//                                String strDbAmt_Txn_lcy = splited[7].replaceAll("[^a-zA-Z0-9\\\\._-]", "");//Remove the white spaces
//                                String strDbTxn_Nrrtv = splited[8];
//                                String strDbFlg_Drcr = splited[9];
//                                String strDbEndToEndId = splited[10];
//
//                                dbDbAmountTCY = Double.parseDouble(strDbAmt_Txn_tcy);
//                                dbDbAmountLCY = Double.parseDouble(strDbAmt_Txn_lcy);
//
//                                //Add the amount in each iteration to get the total sums
//                                dbDbAmountTCYActualTotal += dbDbAmountTCY;
//                                dbDbAmountLCYActualTotal += dbDbAmountLCY;
//                            }
//
//                            //Exist the main DO WHILE LOOP
//                            break;
//                        }
//                        else
//                        {
//                            ExtentTestManager.getTest().log(Status.WARNING, "Warning:: No Records Where Found In The Database on the 1st attempt ,the script will attempt to retry in 20 seconds");
//                            Thread.sleep(20000);
//                            i++;
//                        }
//                    } while (i <= intDatabaseWaitTimeInde);
//
//
//
//                    /**Check the fees applied to this transaction*/
//                    if (DebtorWaiveChargesOption.equalsIgnoreCase("NO"))
//                    {
//
//                        //TODO
//                        /**Perform Fees validations if Fees are applied to the transaction*/
//                        //==================Check for Credit Suspense Account fees applied =================================
//                        if (DebtorWaiveChargesOption.equalsIgnoreCase("NO"))
//                        {
//                            /**Once Fees have been confirmed to be on the statement,the below validations must performed */
//                            //TODO
//                        }
//
//                        //==============================Fees Validations/Assertions=========================================
//                        /**Check if the correct fees description/reference is applied for the payment-purpose in question
//                         * And the fees amount are corresponding */
//                        //TODO
//
//                    }
//
//                    /**Check for Credit Suspense Account transaction amounts*/
//                    System.out.println("======================Flex Table Suspense Credit Account Validation================");
//
//                    /**Get totals debit amount (round-off to two decimal places) from ebox*
//                     *Convert string from excel to integer/double */
//                    dbCrCreditorControlSum = Double.parseDouble(CreditorControlSum);
//
//                    if(dbDbAmountLCYActualTotal==dbCrCreditorControlSum)
//                    {
//                        strSuspenseAccountDebtorAmountValidation = "PASS";
//                    }
//                    else
//                    {
//                        strSuspenseAccountDebtorAmountValidation = "FAIL";
//                    }
//
//                } catch (Exception e)
//                {
//                    ExtentTestManager.getTest().log(Status.FAIL, "Exception=FATAL: Checking available balance on the suspense account failed due to ::  " + e.getMessage());
//                    screenshotLoggerMethod("checkTransactionSuspenseAccount", "Transaction Suspense Account Validations Failure");
//                    stopCurrentTestSetResultsToFailedAndContinueWithNextTest();
//                }
//            }catch (Exception e)
//            {
//                ExtentTestManager.getTest().log(Status.FAIL, "Exception=FATAL: Database data retrieval failed :: "+ e.getMessage());
//            }
//        }
//    }
//
//
//
//
//
//    /**Print the report in a table format*/
//    public void writeFlexValidationReport(String DebtorPurposeOfPayment,String strWorkflowReferenceUniqueTxt) {
//
//        //Create 2 strings to use in validations
//        strFlexTestValidations = strSuspenseAccountCreditedAmountValidation+strSuspenseAccountDebitATransactionFeesValidations+
//                strSuspenseAccountCreditTransactionFeesValidations+ strPNLCreditAccountTransactionFeesValidations+strDifferentChargesDebitAccountTransactionFeesValidations;
//
//        //If any status above fails or passes then print the results accordingly
//        //If the strEboxTestStatus string does not contain the string FAIL then all the Validations have PASSED
//        if(!strFlexTestValidations.contains("FAIL"))
//        {
//            //Print out the results
//            /**INFO*/
//            ExtentTestManager.getTest().pass(MarkupHelper.createLabel("Status=PASS:Transaction accounts validations from Flex DB below", ExtentColor.GREEN));
//
//            String[][] data =
//                    {
//                            //Headings
//                            {"Validation","Transaction Reference","Expected Fee Type","Actual Fee Type","Expected  Amounts","Actual Amounts","Status"},
//                            {"=========","======================","=================","===============","=================","==============","======"},
//
//                            //Contents
//                            {"Suspense Credit Account       ",strWorkflowReferenceUniqueTxt,DebtorPurposeOfPayment,strSuspenseCreditAccountActualFeesDescription,String.valueOf(dbCrCreditorControlSum),String.valueOf(dbCrAmountTCYActualTotal),strSuspenseAccountCreditedAmountValidation},
//                            {"Suspense Credit Account Fees  ",strWorkflowReferenceUniqueTxt,DebtorPurposeOfPayment,strSuspenseCreditAccountActualFeesDescription,String.valueOf(doubleExpectedFeesAmountApplied),String.valueOf(doubleSuspenseCreditAccountActualFeesAmount),strSuspenseCreditAccountFeesAmountAssertion},
//                            {"Suspense Debit Account        ",strWorkflowReferenceUniqueTxt,DebtorPurposeOfPayment,strSuspenseDebitAccountActualFeesDescription,String.valueOf(dbCrCreditorControlSum),String.valueOf(dbDbAmountTCYActualTotal),strSuspenseAccountDebtorAmountValidation},
//                            {"Suspense Debit Account Fees   ",strWorkflowReferenceUniqueTxt,DebtorPurposeOfPayment,strSuspenseDebitAccountActualFeesDescription,String.valueOf(doubleExpectedFeesAmountApplied),String.valueOf(doubleSuspenseDebitAccountActualFeesAmount),strSuspenseDebitAccountFeesAmountAssertion},
//                            {"Clearing Account              ",strWorkflowReferenceUniqueTxt,DebtorPurposeOfPayment,strDebitAccountActualFeesDescription,String.valueOf(dbCrCreditorControlSum),String.valueOf(doubleClearingAccountCreditAccountActualAmountTotal),strClearingAccountCreditAccountTransactionsAmountAssertion},
//                            {"PNL Credit Account Fees       ",strWorkflowReferenceUniqueTxt,DebtorPurposeOfPayment,strPNLCreditAccountActualFeesDescription,String.valueOf(doubleExpectedFeesAmountApplied),String.valueOf(doublePNLCreditAccountFeesAmount),strPNLCreditAccountFeesAmountAssertion},
//                            {"Different Charges Fees Account",strWorkflowReferenceUniqueTxt,DebtorPurposeOfPayment,strDifferentChargesDebitAccountActualFeesDescription,String.valueOf(doubleExpectedFeesAmountApplied),String.valueOf(doubleDifferentChargesDebitAccountFeesAmount),strDifferentChargesDebitAccountFeesAmountAssertion}};
//            Markup m = MarkupHelper.createTable(data);
//            ExtentTestManager.getTest().pass(m);
//
//        }else if(strFlexTestValidations.contains("FAIL") || strFlexTestValidations.contains("null"))
//        {
//
//            ExtentTestManager.getTest().fail(MarkupHelper.createLabel("Status=FAIL:Transaction accounts validations from Flex DB below", ExtentColor.RED));
//
//            String[][] data =
//                    {
//                            //Headings
//                            {"Validation","Transaction Reference","Expected Fee Type","Actual Fee Type","Expected  Amounts","Actual Amounts","Status"},
//                            {"=========","======================","=================","===============","=================","==============","======"},
//
//                            //Contents
//                            {"Suspense Credit Account       ",strWorkflowReferenceUniqueTxt,DebtorPurposeOfPayment,strSuspenseCreditAccountActualFeesDescription,String.valueOf(dbCrCreditorControlSum),String.valueOf(dbCrAmountTCYActualTotal),strSuspenseAccountCreditedAmountValidation},
//                            {"Suspense Credit Account Fees  ",strWorkflowReferenceUniqueTxt,DebtorPurposeOfPayment,strSuspenseCreditAccountActualFeesDescription,String.valueOf(doubleExpectedFeesAmountApplied),String.valueOf(doubleSuspenseCreditAccountActualFeesAmount),strSuspenseCreditAccountFeesAmountAssertion},
//                            {"Suspense Debit Account        ",strWorkflowReferenceUniqueTxt,DebtorPurposeOfPayment,strSuspenseDebitAccountActualFeesDescription,String.valueOf(dbCrCreditorControlSum),String.valueOf(dbDbAmountTCYActualTotal),strSuspenseAccountDebtorAmountValidation},
//                            {"Suspense Debit Account Fees   ",strWorkflowReferenceUniqueTxt,DebtorPurposeOfPayment,strSuspenseDebitAccountActualFeesDescription,String.valueOf(doubleExpectedFeesAmountApplied),String.valueOf(doubleSuspenseDebitAccountActualFeesAmount),strSuspenseDebitAccountFeesAmountAssertion},
//                            {"Clearing Account              ",strWorkflowReferenceUniqueTxt,DebtorPurposeOfPayment,strDebitAccountActualFeesDescription,String.valueOf(dbCrCreditorControlSum),String.valueOf(doubleClearingAccountCreditAccountActualAmountTotal),strClearingAccountCreditAccountTransactionsAmountAssertion},
//                            {"PNL Credit Account Fees       ",strWorkflowReferenceUniqueTxt,DebtorPurposeOfPayment,strPNLCreditAccountActualFeesDescription,String.valueOf(doubleExpectedFeesAmountApplied),String.valueOf(doublePNLCreditAccountFeesAmount),strPNLCreditAccountFeesAmountAssertion},
//                            {"Different Charges Fees Account",strWorkflowReferenceUniqueTxt,DebtorPurposeOfPayment,strDifferentChargesDebitAccountActualFeesDescription,String.valueOf(doubleExpectedFeesAmountApplied),String.valueOf(doubleDifferentChargesDebitAccountFeesAmount),strDifferentChargesDebitAccountFeesAmountAssertion}};
//            Markup m = MarkupHelper.createTable(data);
//            ExtentTestManager.getTest().fail(m);
//        }
//    }
//    public void fetchDebtorAccountPostingsFromFlex(String[] ReferenceUserNumber,String[] InstructedAmount,
//                                                   String DebtorBranchId,String DebtorAccountId) throws SQLException, ClassNotFoundException {
//
//        System.out.println("================================Debtor Account Statement===================================");
//        ExtentTestManager.getTest().info(MarkupHelper.createLabel("Debtor Account Statements", ExtentColor.PURPLE));
//        //ExtentTestManager.getTest().log(Status.INFO, "Debtor Account Postings : " + DebtorBranchId + "/" + DebtorAccountId);
//        for(int i=0;i<ReferenceUserNumber.length;i++) {
//            fetchAccountPostingsFromFlexDB(ReferenceUserNumber[i], InstructedAmount[i], DebtorBranchId, DebtorAccountId);
//        }
//        //ExtentTestManager.getTest().log(Status.INFO, "----------------------------------------------------------------------------------");
//    }
//    public void fetchCreditorAccountPostingsFromFlex(String DebtorBankId, String[] CreditorBankId, String[] ReferenceUserNumber,String[] InstructedAmount,
//                                                     String[] CreditorBranchId,String[] CreditorAccountId) throws SQLException, ClassNotFoundException {
//
//        System.out.println("================================Creditor Account Statement===================================");
//        ExtentTestManager.getTest().info(MarkupHelper.createLabel("Creditor Statements", ExtentColor.PURPLE));
//        for(int i=0;i<ReferenceUserNumber.length;i++) {
//
//            //Fetch Account postings for Creditor Account
//            if (DebtorBankId.equalsIgnoreCase(CreditorBankId[i])) {
//                fetchAccountPostingsFromFlexDB(ReferenceUserNumber[i], InstructedAmount[i], CreditorBranchId[i], CreditorAccountId[i]);
//            } else {
//                ExtentTestManager.getTest().log(Status.INFO, "Creditor is Off-Us : " + CreditorBranchId[i] + "/" + CreditorAccountId[i]);
//            }
//            //ExtentTestManager.getTest().log(Status.INFO, "Creditor Account Postings : " + CreditorBranchId + "/" + CreditorAccountId);
//
//        }
//        //ExtentTestManager.getTest().log(Status.INFO, "----------------------------------------------------------------------------------");
//    }
//    public void fetchProcessingSuspenseAccountPostingsFromFlex(String[] ReferenceUserNumber,String[] InstructedAmount,
//                                                               String ProcessingSuspenseAccountBranchId,String ProcessingSuspenseAccountId) throws SQLException, ClassNotFoundException {
//
//        System.out.println("================================Processing Suspense Account Statement===================================");
//        ExtentTestManager.getTest().info(MarkupHelper.createLabel("Processing Suspense Account Statements", ExtentColor.PURPLE));
//        //ExtentTestManager.getTest().log(Status.INFO, "Processing Suspense Account Postings : " + ProcessingSuspenseAccountBranchId + "/" + ProcessingSuspenseAccountId);
//        for(int i=0;i<ReferenceUserNumber.length;i++) {
////            ExtentTestManager.getTest().info(MarkupHelper.createLabel("Transaction Id :: " + i, ExtentColor.ORANGE));
//            //ExtentTestManager.getTest().log(Status.INFO, "Exception Suspense Account Postings : " + ExceptionSuspenseAccountBranchId + "/" + ExceptionSuspenseAccountId);
//            fetchAccountPostingsFromFlexDB(ReferenceUserNumber[i], InstructedAmount[i], ProcessingSuspenseAccountBranchId, ProcessingSuspenseAccountId);
//        }
//        //ExtentTestManager.getTest().log(Status.INFO, "----------------------------------------------------------------------------------");
//    }
//    public void fetchExceptionSuspenseAccountPostingsFromFlex(String[] ReferenceUserNumber,String[] InstructedAmount,
//                                                              String ExceptionSuspenseAccountBranchId,String ExceptionSuspenseAccountId) throws SQLException, ClassNotFoundException {
//
//        System.out.println("================================Exception Suspense Account Statement===================================");
//        ExtentTestManager.getTest().info(MarkupHelper.createLabel("Exception Suspense Account Statements", ExtentColor.PURPLE));
//        for(int i=0;i<ReferenceUserNumber.length;i++) {
////            ExtentTestManager.getTest().info(MarkupHelper.createLabel("Transaction Id :: " + i, ExtentColor.ORANGE));
//            //ExtentTestManager.getTest().log(Status.INFO, "Exception Suspense Account Postings : " + ExceptionSuspenseAccountBranchId + "/" + ExceptionSuspenseAccountId);
//            fetchAccountPostingsFromFlexDB(ReferenceUserNumber[i], InstructedAmount[i], ExceptionSuspenseAccountBranchId, ExceptionSuspenseAccountId);
//        }
//        //ExtentTestManager.getTest().log(Status.INFO, "----------------------------------------------------------------------------------");
//    }
//    public void publishFlexAccountPostingsReport(String ChannelId,String NumberOfTransaction,String[] ReferenceUserNumber,String PaymentInfoId,String ProcessingOption,
//                                                String ControlSum,String[] InstructedAmount,String DebtorBankId,String[] CreditorBankId,
//                                                String DebtorBranchId,String DebtorAccountId,String[] CreditorBranchId,String[] CreditorAccountId,
//                                                String ProcessingSuspenseAccountBranchID,String ProcessingSuspenseAccountId ,String ExceptionSuspenseAccountBranchId ,String ExceptionSuspenseAccountId ) throws SQLException, ClassNotFoundException {
//
//        if((ProcessingOption.equalsIgnoreCase("Consolidated")) || (ProcessingOption.equalsIgnoreCase("true"))) {
//
//            ExtentTestManager.getTest().info(MarkupHelper.createLabel("Consolidated Statement", ExtentColor.ORANGE));
//
//            //Fetch Account postings for Debtor Account -- Based on Payment Information Id or Channel reference Number
//            fetchDebtorAccountPostingsFromFlex(ReferenceUserNumber, InstructedAmount, DebtorBranchId, DebtorAccountId);
//
//            //Fetch Account postings for Debtor Account - Based on EndtoEndIds or TransactionIds
//            fetchAccountPostingsFromFlexDB(PaymentInfoId, ControlSum, DebtorBranchId, DebtorAccountId);
//
//            //Fetch Account postings for Processing Suspense Account -- Based on EndtoEndIds or TransactionIds
//            fetchProcessingSuspenseAccountPostingsFromFlex(ReferenceUserNumber, InstructedAmount, ProcessingSuspenseAccountBranchID, ProcessingSuspenseAccountId);
//
//            //Fetch Account postings for Processing Suspense Account -- Based on Payment Information Id or Channel reference Number
//            fetchAccountPostingsFromFlexDB(PaymentInfoId, ControlSum, ProcessingSuspenseAccountBranchID, ProcessingSuspenseAccountId);
//
//        }else {
//            ExtentTestManager.getTest().info(MarkupHelper.createLabel("Itemised Statement", ExtentColor.ORANGE));
//
//            //Fetch Account postings for Debtor Account
//            fetchDebtorAccountPostingsFromFlex(ReferenceUserNumber, InstructedAmount, DebtorBranchId, DebtorAccountId);
//
//            //Fetch Account postings for Processing Suspense Account
//            fetchProcessingSuspenseAccountPostingsFromFlex(ReferenceUserNumber, InstructedAmount, ProcessingSuspenseAccountBranchID, ProcessingSuspenseAccountId);
//        }
//            //Fetch Account postings for Exception Suspense Account
//            fetchExceptionSuspenseAccountPostingsFromFlex(ReferenceUserNumber, InstructedAmount, ExceptionSuspenseAccountBranchId, ExceptionSuspenseAccountId);
//
//            //Fetch Account postings for Creditor Account
//            fetchCreditorAccountPostingsFromFlex(DebtorBankId,CreditorBankId,ReferenceUserNumber, InstructedAmount, CreditorBranchId, CreditorAccountId);
//
//    }
//
//
//    public String fetchEntriesFrom_CH_NOBOOK_Table(String ReferenceUserNumber,String InstructedAmount,String AccountBranchId,String AccountId) throws SQLException, ClassNotFoundException, SQLException {
//        int rowcount = 0;
//        String ch_nobook_records = null;
//        ResultSet rs;
//        String debitCreditIndicator = null;
//
//        /* Verify the transactions in flex database */
//        Class.forName(Settings.DBaFlexJdbc);
//        Connection con = DriverManager.getConnection(
//                Settings.DBFlexUrl, Settings.DBFlexUserName, Settings.DBFlexPassword);
//
//        PreparedStatement ps = con.prepareStatement("alter session SET current_schema = kefcrh");
//        ps.executeQuery();
//
//        /* CH_NOBOOK Table : Verification of Credit & Debit Transactions only for CASA Accounts */
//        String str_ch_nobookRec = " Select * From Ch_nobook  " +
//                " where REF_USR_NO like '" + ReferenceUserNumber + "%'" +
//                " AND AMT_TXN = '" + InstructedAmount + "'" +
//                " AND COD_ACCT_NO = '" + AccountId + "'" +
//                " ORDER BY DAT_TXN ";
//
//        System.out.println("str_ch_nobookRec : " + str_ch_nobookRec);
//        ps = con.prepareStatement(str_ch_nobookRec);
//        rs = ps.executeQuery();
//        while (rs.next()) {
//            rowcount++;
//            if(rs.getRow() == 1) {
//                ch_nobook_records = "RECORD_NO : " + rs.getRow() + "\n\tDAT_TXN : " + rs.getString("DAT_TXN") + "\n\tCOD_CC_BRN_TXN : " + rs.getString("COD_CC_BRN_TXN") + "\n\tCOD_ACCT_NO : " + rs.getString("COD_ACCT_NO") + "\n\tCOD_DRCR : " + rs.getString("COD_DRCR") + "\n\tAMT_TXN : " + rs.getString("AMT_TXN") + "\n\tCOD_TXN_CCY : " + rs.getString("COD_TXN_CCY") + "\n\tREF_TXN_NO : " + rs.getString("REF_TXN_NO") + "\n\tREF_USR_NO : " + rs.getString("REF_USR_NO") + "\n\tTXT_TXN_DESC : " + rs.getString("TXT_TXN_DESC");
//                debitCreditIndicator = rs.getString("COD_DRCR");
//            }else{
//                ch_nobook_records = ch_nobook_records + "\nRECORD_NO : " + rs.getRow() + "\n\tDAT_TXN : " + rs.getString("DAT_TXN") + "\n\tCOD_CC_BRN_TXN : " + rs.getString("COD_CC_BRN_TXN") + "\n\tCOD_ACCT_NO : " + rs.getString("COD_ACCT_NO") + "\n\tCOD_DRCR : " + rs.getString("COD_DRCR") + "\n\tAMT_TXN : " + rs.getString("AMT_TXN") + "\n\tCOD_TXN_CCY : " + rs.getString("COD_TXN_CCY") + "\n\tREF_TXN_NO : " + rs.getString("REF_TXN_NO") + "\n\tREF_USR_NO : " + rs.getString("REF_USR_NO") + "\n\tTXT_TXN_DESC : " + rs.getString("TXT_TXN_DESC");
//                debitCreditIndicator = rs.getString("COD_DRCR");
//            }
//
//        }
//        // Retrieve number of records for given transaction from flex database.
//        System.out.println("Total Number of Records Present in CH_NOBOOK Table :: " + rowcount);
//        if(rowcount > 0) {
//            codeLogsXML("Table Name : CH_NOBOOK\nTransaction Id : " + ReferenceUserNumber + "\n" + ch_nobook_records);
//        }else {
//            System.out.println("CH_NOBOOK Table : Accounting Only For CASA Account with Successful Transaction");
//        }
//
//        rs.close();
//        ps.close();
//        con.close();
//
//        return debitCreditIndicator;
//    }
//
//    public void fetchEntriesFrom_XF_OL_TXNLOG_CURRENT_Table(String ReferenceUserNumber,String InstructedAmount,String AccountBranchId,String AccountId) throws SQLException, ClassNotFoundException, SQLException {
//        int rowcount = 0;
//        String xf_ol_st_txnlog_current_records = null;
//        ResultSet rs;
//
//        /* Verify the transactions in flex database */
//        Class.forName(Settings.DBaFlexJdbc);
//        Connection con = DriverManager.getConnection(
//                Settings.DBFlexUrl, Settings.DBFlexUserName, Settings.DBFlexPassword);
//
//        PreparedStatement ps = con.prepareStatement("alter session SET current_schema = kefcrh");
//        ps.executeQuery();
//
//        // Retrieve records from xf_ol_st_txnlog_current table
//        rowcount = 0;
//        String str_xf_ol_st_txnlog_current = " Select * From xf_ol_st_txnlog_current  " +
//                " where REF_USR_NO like '" + ReferenceUserNumber + "%'" +
//                " AND AMT_TXN_TCY = '" + InstructedAmount + "'" +
//                " AND COD_CC_BRN = '" + AccountBranchId + "'" +
//                " AND COD_ACCT_NO = '" + AccountId + "'" +
//                " ORDER BY DAT_TXN_STR ";
//
//        System.out.println("str_xf_ol_st_txnlog_current : " + str_xf_ol_st_txnlog_current);
//        ps = con.prepareStatement(str_xf_ol_st_txnlog_current);
//        rs = ps.executeQuery();
//
//        while (rs.next()) {
//            rowcount++;
//            if(rs.getRow() == 1){
//                xf_ol_st_txnlog_current_records = "RECORD_NO : " + rs.getRow() + "\n\tDAT_TXN_STR :" + rs.getString("DAT_TXN_STR") + "\n\tCOD_ACCT_NO : " + rs.getString("COD_ACCT_NO") + "\n\tCOD_TXN_CCY : " + rs.getString("COD_TXN_CCY") + "\n\tAMT_TXN_TCY : " + rs.getString("AMT_TXN_TCY") + "\n\tFLG_DRCR : " + rs.getString("FLG_DRCR") + "\n\tREF_USR_NO : " + rs.getString("REF_USR_NO") + "\n\tREF_TXN_NO : " + rs.getString("REF_TXN_NO") + "\n\tTXN_NRRTV : " + rs.getString("TXN_NRRTV") ;
//            }else{
//                xf_ol_st_txnlog_current_records = xf_ol_st_txnlog_current_records + "\nRECORD_NO : " + rs.getRow() + "\n\tDAT_TXN_STR :" + rs.getString("DAT_TXN_STR") + "\n\tCOD_ACCT_NO : " + rs.getString("COD_ACCT_NO") + "\n\tCOD_TXN_CCY : " + rs.getString("COD_TXN_CCY") + "\n\tAMT_TXN_TCY : " + rs.getString("AMT_TXN_TCY") + "\n\tFLG_DRCR : " + rs.getString("FLG_DRCR") + "\n\tREF_USR_NO : " + rs.getString("REF_USR_NO") + "\n\tREF_TXN_NO : " + rs.getString("REF_TXN_NO") + "\n\tTXN_NRRTV : " + rs.getString("TXN_NRRTV") ;
//            }
//
//        }
//        // Retrieve the number of records for given transaction from XF_OL_ST_TXNLOG_CURRENT table.
//        System.out.println("Total Number of Records Present in XF_OL_ST_TXNLOG_CURRENT Table :: " + rowcount);
//        if(rowcount > 0)
//            codeLogsXML("Table Name : XF_OL_ST_TXNLOG_CURRENT\nTransaction Id : " + ReferenceUserNumber + "\n" + xf_ol_st_txnlog_current_records);
//        else
//            System.out.println("XF_OL_ST_TXNLOG_CURRENT Table : No Accounting For Failed Transactions");
//
//        rs.close();
//        ps.close();
//        con.close();
//    }
//
//    public void fetchAccountPostingsFromFlexDB(String ReferenceUserNumber,String InstructedAmount,String AccountBranchId,String AccountId) throws SQLException, ClassNotFoundException {
//
//        if(ReferenceUserNumber != null) {
//            String creditDebitIndicator = fetchEntriesFrom_CH_NOBOOK_Table(ReferenceUserNumber, InstructedAmount, StringUtils.stripStart(AccountBranchId, "0"), AccountId);
//
//            String accountId = null;
//            if (creditDebitIndicator != null && (creditDebitIndicator.equalsIgnoreCase("D") || creditDebitIndicator.equalsIgnoreCase("C"))) {
//                //accountId = StringUtils.stripStart(AccountId, "0");
//                accountId = AccountId;
//            } else {
//                accountId = "0" + AccountBranchId + AccountId;
//            }
//            System.out.println("accountId :" + accountId);
//            fetchEntriesFrom_XF_OL_TXNLOG_CURRENT_Table(ReferenceUserNumber, InstructedAmount, StringUtils.stripStart(AccountBranchId, "0"), accountId);
//        }else{
//            ExtentTestManager.getTest().log(Status.INFO, "Transaction Id is blank, no account postings against such transaction");
//        }
//    }
//}