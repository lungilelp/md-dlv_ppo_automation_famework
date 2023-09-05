package Utilities;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.lang.reflect.Method;

/**
 * @author Jonathan Mbuyi Tshitenda [ ABJT439 ]
 * @date created 9/5/2019
 * @package com.jmt.framework.Utilities
 */
public class DataProviderUtil {

    /** This class holds the test-data information from the excel document.
     * You need to set the excel sheet for usage in the ppoTestData method
     * Once that is set,the test will call that excel sheet to load test data
     * For sheet name that holds more test data than the other,it is advice to use the sheet name with more data
     * If the data contained in the sheetname with less data is the same up to that point [SingleItemisedCreditTests has less data than MultipleItemisedCreditTests]
     * So to perform a tests that is common to both[example EboxTest] we can use the one with more data,to avoid testng data provider errors
     **/
    //**********************************************USE THIS DATA PROVIDER TO GET ALL TEST DATA IN THE EXCEL SHEET******
    ExcelUtil excelUtil = new ExcelUtil();


    @BeforeTest
    public void setupTestData ()  throws InterruptedException, IOException {
        //Set Test Data Excel and Sheet
        System.out.println("************Setup Test Level Data**********");
        ExcelUtil.setExcelFileSheet("LoginTests");
    }

    @Test(dataProvider = "PPOTestsData")
    public void dataProviderTester
            (String TestCaseID ,
             String ApplicationArea,
             String TestName,
             String Role,
             String TestCaseScenariotype,
             String TestTargetAccount,
             String TestDescription	,
             String TestResults,
             String TestExecutionDate,
             String AllowExecutioY,
             String AccountType,
             String ServiceAgentCommercialOperationsUsername,
             String ServiceAgentCommercialOperationsPassword,
             String UserRole,
             String Country,
             String EboxUsername,
             String EboxAgentPassword,
             String EboxDomain
            )
    {
        System.out.println(TestCaseID+ApplicationArea+TestName+Role+TestCaseScenariotype+TestTargetAccount+
                TestDescription+TestResults+ TestExecutionDate+AllowExecutioY+AccountType+ServiceAgentCommercialOperationsUsername+
                ServiceAgentCommercialOperationsPassword+UserRole+Country+EboxUsername+EboxAgentPassword+EboxDomain);
    }


    // Here we Create the DataProvider to use the Excel data stored in the array
    @DataProvider(name = "PPOTestsData",parallel=false)
    public Object[][] ppoTestData(Method m) {//use Method parameter to change the behavior of the DataProvider based on different Tests

        Object[][] data = new Object[0][];
        if (m.getName().equalsIgnoreCase("dataProviderTester")) {//Specify the test name
            //The number of times data is repeated, test will be executed the same number of times
            data = getTestDataFromExcel("LoginTests");
            /*return data;*/
        }
        else if(m.getName().equalsIgnoreCase("ppoLoingTest"))
        {
            data = getTestDataFromExcel("LoginTests");}

        else if(m.getName().equalsIgnoreCase("singleCreditItemisedTests"))
        {
            data = getTestDataFromExcel("SingleCreditItemisedTests");
        }

        else if(m.getName().equalsIgnoreCase("PPOCaptureMultipleCreditTests"))
        {
            data = getTestDataFromExcel("PPOCaptureMultipleCreditTests");
        }

        else if(m.getName().equalsIgnoreCase("eboxTests"))
        {
            data = getTestDataFromExcel("EboxTests");
        }

        else if(m.getName().equalsIgnoreCase("smokeTests"))
        {
            data = getTestDataFromExcel("SmokeTests");
        }
        else if(m.getName().equalsIgnoreCase("paymentSearchTests"))
        {
            data = getTestDataFromExcel("PaymentSearchTests");
        }
        else if(m.getName().equalsIgnoreCase("flexTests"))
        {
            data = getTestDataFromExcel("FlexTests");
        }
        else if(m.getName().equalsIgnoreCase("SeleniumGridExecutionWithZaleniumandDocker"))
        {
            data = getTestDataFromExcel("LoginTests");
        }
        else if(m.getName().equalsIgnoreCase("referenceDataPop")){
            data = getTestDataFromExcel("ReferenceDataPOP");}

        else if(m.getName().equalsIgnoreCase("teamsTest")){//method name
            data = getTestDataFromExcel("Teams");}

        else if(m.getName().equalsIgnoreCase("referenceDataBankBranch")){
            data = getTestDataFromExcel("ReferenceDataBankBranch");}

        else if(m.getName().equalsIgnoreCase("sanityTests")){
            data = getTestDataFromExcel("SanityAllCountries");}

        else if(m.getName().equalsIgnoreCase("sfiTest")){
            data = getTestDataFromExcel("SFI");}

        else if(m.getName().equalsIgnoreCase("sfiMultipleCreditTest")){
            data = getTestDataFromExcel("SFIMultipleCreditTests");}

        else if(m.getName().equalsIgnoreCase("InwardTests")){
            data = getTestDataFromExcel("InwardTests");
        }

        else if(m.getName().equalsIgnoreCase("Pain001BIRMultipleCreditTests")){
            data = getTestDataFromExcel("Pain001BIRMultipleCreditTests");
        }

        else if(m.getName().equalsIgnoreCase("Pain001SAPSanityTest")){
            data = getTestDataFromExcel("Pain001SAPSanityTests");
        }

        else if(m.getName().equalsIgnoreCase("PPUMultipleCreditTests")){
            data = getTestDataFromExcel("PPUMultipleCreditTests");
        }

        else if(m.getName().equalsIgnoreCase("PPUVolumeMultipleCreditTests")){
            data = getTestDataFromExcel("PPUVolumeMultipleCreditTests");
        }

        else if(m.getName().equalsIgnoreCase("SEFTPhase2Tests")){
            data = getTestDataFromExcel("SEFTP2");
        }

        else if(m.getName().equalsIgnoreCase("Pain008SAPSanityTests")){
            data = getTestDataFromExcel("Pain008SAPSanityTests");
        }
        else if(m.getName().equalsIgnoreCase("Pain001AMBCreditTests")){
            data = getTestDataFromExcel("Pain001AMBCreditTests");
        }
        else if(m.getName().equalsIgnoreCase("MultiDebitMultiCreditPain001")){
            data = getTestDataFromExcel("MultiDebitMultiCreditPain001");
        }
        else if(m.getName().equalsIgnoreCase("singleCreditInwardTests")){
            data = getTestDataFromExcel("SingleCreditInwardTests");
        }
        else if(m.getName().equalsIgnoreCase("PPUFileValidation")){
            data = getTestDataFromExcel("PPUFileValidation");
        }
        else if(m.getName().equalsIgnoreCase("AAOAAHPain001SingleCreditTest")){
            data = getTestDataFromExcel("AAOAAHPain001SingleCreditTest");
        }

        return data;
    }


    //****************************************************************************************************************************************************

    //***********************************************USE THIS DATA PROVIDER TO GET TEST DATA BY TEST-NAME COLUMN******************************************

    //****************************************************************************************************************************************************
    public Object[][] getTestDataFromExcel(String sheetName)
    {
        Object[][] excelData = null;
        excelUtil = new ExcelUtil();
        int rows = excelUtil.getRowCount(sheetName);
        int columns = excelUtil.getColumnCount(sheetName);

        /**Specify how many HEADER-ROWS to be skipped before data is read(EXAMPLE :: -3 will skip 3 rows from the top of the excel sheet)and use that number in the for-loop
         *Specify how many preceding/leading COLUMNS to be skipped before data is read (EXAMPLE :: -1 will skip 1 row from the left-side of the excel sheet) and use that number in the for-loop**/
        excelData = new Object[rows-3][columns-1];

        //Use header-rows that are skipped from the above in this loop
        for(int i=3; i<rows; i++)
        {
            //Specify how many preceding/leading COLUMNS to be skipped before data is read
            for(int j=1; j<columns; j++)
            {
                //Use HEADER-ROWS and COLUMNS that are skipped from the above in this loop
                excelData[i-3][j-1] = excelUtil.getCellData(i,j);

                /**PRINT EXCEL DATA ON CONSOLE*/
                //System.out.print(excelData+ " | ");
            }

            /**PRINT EXCEL DATA ON CONSOLE*/
            //System.out.println();
        }
        return excelData;
    }
}
