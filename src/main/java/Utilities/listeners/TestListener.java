package Utilities.listeners;

import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import org.testng.ITestListener;
import org.testng.ITestContext;
import org.testng.ITestResult;

import com.aventstack.extentreports.Status;
import org.testng.Reporter;
import utils.extentreports.ExtentTestManager;

import static base.BasePage.globalTestCaseScenarioType;

/**
 * @author Jonathan Mbuyi Tshitenda [ ABJT439 ]
 * @date created 9/4/2019
 * @package com.jmt.framework.Utilities.listeners
 */
public class TestListener implements ITestListener
{
    SeleniumCustomControls seleniumCustomControls = new SeleniumCustomControls();

    public void onStart(ITestContext context)
    {
        System.out.println("*** Test Suite " + context.getName() + " started ***");
        //ExtentTestManager.startTest(context.getName());
    }

    public void onFinish(ITestContext context) {
        System.out.println(("*** Test Suite " + context.getName() + " ending ***"));
        ExtentTestManager.endTest();         //Perform end functionalities
        ExtentManager.getInstance().flush(); //Write Extent Report
        ExtentTestManager.tearDown();        //Open Extent Report
    }

    public void onTestStart(ITestResult result) {
        System.out.println(("*** Running test method " + result.getMethod().getMethodName() + "..."));
        //ExtentTestManager.startTest(result.getMethod().getMethodName());
    }

    public void onTestSuccess(ITestResult result) {
        System.out.println("*** Executed " + result.getMethod().getMethodName() + " test successfully...");
        ExtentTestManager.getTest().log(Status.PASS, "Test passed");
    }

    public void onTestFailure(ITestResult result)
    {
        /**Based on the test logic the decision to fail or pass the test is applied here*/
        if(globalTestCaseScenarioType.equalsIgnoreCase("Positive"))
        {
            System.out.println("*** Test execution " + result.getMethod().getMethodName() + " failed...");

            //Customize the appearance of the log
            ExtentTestManager.getTest().fail(MarkupHelper.createLabel(result.getMethod().getMethodName()+"::The Test Has Failed", ExtentColor.RED));


            //Get the Entire StackTrace of the failure to see the problem/issue
            ExtentTestManager.getTest().fail(result.getThrowable());

            //Capture Screenshot and log information
            try
            {
                seleniumCustomControls.screenshotLoggerMethod("Failed Test Screenshot","Test Has Failed");
            } catch (Exception e) {
                e.printStackTrace();
            }

            /**Update the test result on excel sheet*/
            //ExcelHelpers.setCellData("FAILED", ExcelHelpers.getRowNumber(), ExcelHelpers.getColumnNumber());
        }
        else if(globalTestCaseScenarioType.equalsIgnoreCase("Negative")) //Perform this step only if this is a negative test
        {
            /** 1. Because there is no proper way to stop a running test and not FAIL it within in Java for now
             *  2. We have to create a way to modify the test based on our own conditions
             *  3. Therefore if a negative tests is stop by means of a HardAssertion or Thread.currentThread().interrupt()
             *  4. Instead of the test to show a FAILED outcome in the report we have to change that to a PASS outcome/result
             * */

            /**testNG - changing test results dynamically*/

            /**Change Failed to Passed based on test logic/conditions*/
            if (result.getMethod().isTest())//You should always wrap your decisions in the if condition below otherwise you will end up with configuration errors as well.
            {
                if (result.getStatus() == ITestResult.FAILURE)
                {
                    if (result.getThrowable() != null)
                    {
                        if (result.getThrowable().getStackTrace() != null)
                        {
                            Reporter.getCurrentTestResult().setStatus(ITestResult.SUCCESS);// make all FAILED tests a SUCCESS
                            ExtentTestManager.getTest().log(Status.PASS, "Negative Test has passed");
                        }
                    }
                }
            }
        }
    }

    public void onTestSkipped(ITestResult result) {
        System.out.println("*** Test " + result.getMethod().getMethodName() + " skipped...");
        ExtentTestManager.getTest().log(Status.SKIP, "Test Skipped");
    }

    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        System.out.println("*** Test failed but within percentage % " + result.getMethod().getMethodName());
    }

}
