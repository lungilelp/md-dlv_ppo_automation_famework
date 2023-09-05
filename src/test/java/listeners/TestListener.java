package listeners;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.*;
import tests.BaseTest;
import utils.extentreports.ExtentManager;
import utils.logs.Log;

import java.util.Objects;

import static utils.extentreports.ExtentTestManager.getTest;

public class TestListener extends BaseTest implements ITestListener {
    private static String getTestMethodName(ITestResult iTestResult) {
        return iTestResult.getMethod().getConstructorOrMethod().getName();
    }

    @Override
    public void onStart(ITestContext iTestContext) {
        Log.info("I am in onStart method " + iTestContext.getName());
        iTestContext.setAttribute("WebDriver", this.driver);
    }

    @Override
    public void onFinish(ITestContext iTestContext) {
        Log.info("I am in onFinish method " + iTestContext.getName());
        //Do tier down operations for ExtentReports reporting!
        ExtentManager.extentReports.flush();
    }

    @Override
    public void onTestStart(ITestResult iTestResult) {
        Log.info(getTestMethodName(iTestResult) + " test is starting.");
    }

    @Override
    public void onTestSuccess(ITestResult iTestResult) {
        Log.info(getTestMethodName(iTestResult) + " test is succeed.");
        //ExtentReports log operation for passed tests.
        getTest().log(Status.PASS, "Test passed");
    }

    @Override
    public void onTestFailure(ITestResult iTestResult) {
        Log.info(getTestMethodName(iTestResult) + " test is failed.");

        //Get driver from BaseTest and assign to local webdriver variable.
        Object testClass = iTestResult.getInstance();
        WebDriver driver = ((BaseTest) testClass).getDriver();

        //Take base64Screenshot screenshot for extent reports
        String base64Screenshot =
                "data:image/png;base64," + ((TakesScreenshot) Objects.requireNonNull(driver)).getScreenshotAs(OutputType.BASE64);

        //ExtentReports log and screenshot operations for failed tests.
        getTest().log(Status.FAIL, "Test Failed",
                getTest().addScreenCaptureFromBase64String(base64Screenshot).getModel().getMedia().get(0));
    }

    @Override
    public void onTestSkipped(ITestResult iTestResult) {
        Log.info(getTestMethodName(iTestResult) + " test is skipped.");
        //ExtentReports log operation for skipped tests.
        getTest().log(Status.SKIP, "Test Skipped");
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {
        Log.info("Test failed but it is in defined success ratio " + getTestMethodName(iTestResult));
    }
    //_______________________________________________________________________________________________

//    @Override
//    public void beforeInvocation(IInvokedMethod iInvokedMethod, ITestResult iTestResult)
//    {
//
//        if (!iInvokedMethod.isTestMethod()) {
//            //Initialize Config
//            try {
//                ConfigReader.PopulateSettings();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//            //Logging
//            Settings.Logs = new LogUtil();
//            try {
//                Settings.Logs.CreateLogFile();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            Settings.Logs.Write("Framework Initialize");
//
//            //Create Test Cycle for Reporting
//            Settings.ReportingConnection = DatabaseUtil.Open(Settings.ReportingConnectionString);
//            ReportingUtil.CreateTestCycle(Settings.ReportingConnection);
//
//            Settings.Logs.Write("Test Cycle Created");
//        }
//    }

    /**testNG - changing test results dynamically*/
//    @Override
//    public void afterInvocation(IInvokedMethod method, ITestResult result)
//    {
//        if (result.getMethod().isTest()) //You should always wrap your decisions in the if condition below otherwise you will end up with configuration errors as well.
//        {
//            //Change Failed to Skipped based on exception text
//            if (result.getStatus() == ITestResult.FAILURE)
//            {
//                if (result.getThrowable() != null) {
//                    if (result.getThrowable().getStackTrace() != null) {
//                        StringWriter sw = new StringWriter();
//                        result.getThrowable().printStackTrace(new PrintWriter(sw));
//                        if (sw.toString().contains("visible")) {
//                            ITestContext tc = Reporter.getCurrentTestResult().getTestContext();
//                            tc.getFailedTests().addResult(result, Reporter.getCurrentTestResult().getMethod());
//                            tc.getFailedTests().getAllMethods().remove(Reporter.getCurrentTestResult().getMethod());
//                            Reporter.getCurrentTestResult().setStatus(ITestResult.SKIP);
//                            tc.getSkippedTests().addResult(result, Reporter.getCurrentTestResult().getMethod());
//                        }
//                    }
//                }
//            }
//
//
//            //Change Pass to failure and throw custom exception error
//            if (result.getStatus() == ITestResult.SUCCESS) {
//                ITestContext tc = Reporter.getCurrentTestResult().getTestContext();
//                tc.getPassedTests().addResult(result, Reporter.getCurrentTestResult().getMethod());
//                tc.getPassedTests().getAllMethods().remove(Reporter.getCurrentTestResult().getMethod());
//                Reporter.getCurrentTestResult().setStatus(ITestResult.FAILURE);
//                Reporter.getCurrentTestResult().setThrowable(new Exception("test Fail"));
//                tc.getSkippedTests().addResult(result, Reporter.getCurrentTestResult().getMethod());
//            }
//
//
//            //Change skip to fail with custom failure
//            if (result.getStatus() == ITestResult.SKIP) {
//                ITestContext tc = Reporter.getCurrentTestResult().getTestContext();
//                tc.getSkippedTests().addResult(result, Reporter.getCurrentTestResult().getMethod());
//                tc.getSkippedTests().getAllMethods().remove(Reporter.getCurrentTestResult().getMethod());
//                Reporter.getCurrentTestResult().setStatus(ITestResult.FAILURE);
//                Reporter.getCurrentTestResult().setThrowable(new Exception("test Fail"));
//                tc.getFailedTests().addResult(result, Reporter.getCurrentTestResult().getMethod());
//            }
//        }
//    }


//    public void onStart(ITestContext context)
//    {
//        System.out.println("*** Test Suite " + context.getName() + " started ***");
//        //ExtentTestManager.startTest(context.getName());
//    }

//    public void onFinish(ITestContext context) {
//        System.out.println(("*** Test Suite " + context.getName() + " ending ***"));
//        ExtentTestManager.endTest();         //Perform end functionalities
//        ExtentManager.getInstance().flush(); //Write Extent Report
//        ExtentTestManager.tearDown();        //Open Extent Report
//    }

//    public void onTestStart(ITestResult result) {
//        System.out.println(("*** Running test method " + result.getMethod().getMethodName() + "..."));
//        //ExtentTestManager.startTest(result.getMethod().getMethodName());
//    }

//    public void onTestSuccess(ITestResult result) {
//        System.out.println("*** Executed " + result.getMethod().getMethodName() + " test successfully...");
//        ExtentTestManager.getTest().log(Status.PASS, "Test passed");
//    }

//    public void onTestFailure(ITestResult result)
//    {
//        /**Based on the test logic the decision to fail or pass the test is applied here*/
//        if(globalTestCaseScenarioType.equalsIgnoreCase("Positive"))
//        {
//            System.out.println("*** Test execution " + result.getMethod().getMethodName() + " failed...");
//
//            //Customize the appearance of the log
//            ExtentTestManager.getTest().fail(MarkupHelper.createLabel(result.getMethod().getMethodName()+"::The Test Has Failed", ExtentColor.RED));
//
//
//            //Get the Entire StackTrace of the failure to see the problem/issue
//            ExtentTestManager.getTest().fail(result.getThrowable());
//
//            //Capture Screenshot and log information
//            try
//            {
//                screenshotLoggerMethod("Failed Test Screenshot","Test Has Failed");
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//            /**Update the test result on excel sheet*/
//            //ExcelHelpers.setCellData("FAILED", ExcelHelpers.getRowNumber(), ExcelHelpers.getColumnNumber());
//        }
//        else if(globalTestCaseScenarioType.equalsIgnoreCase("Negative")) //Perform this step only if this is a negative test
//        {
//            /** 1. Because there is no proper way to stop a running test and not FAIL it within in Java for now
//             *  2. We have to create a way to modify the test based on our own conditions
//             *  3. Therefore if a negative tests is stop by means of a HardAssertion or Thread.currentThread().interrupt()
//             *  4. Instead of the test to show a FAILED outcome in the report we have to change that to a PASS outcome/result
//             * */
//
//            /**testNG - changing test results dynamically*/
//
//            /**Change Failed to Passed based on test logic/conditions*/
//            if (result.getMethod().isTest())//You should always wrap your decisions in the if condition below otherwise you will end up with configuration errors as well.
//            {
//                if (result.getStatus() == ITestResult.FAILURE)
//                {
//                    if (result.getThrowable() != null)
//                    {
//                        if (result.getThrowable().getStackTrace() != null)
//                        {
//                            Reporter.getCurrentTestResult().setStatus(ITestResult.SUCCESS);// make all FAILED tests a SUCCESS
//                            ExtentTestManager.getTest().log(Status.PASS, "Negative Test has passed");
//                        }
//                    }
//                }
//            }
//        }
//    }



}