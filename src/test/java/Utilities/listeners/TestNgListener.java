package Utilities.listeners;

import config.ConfigReader;
import config.Settings;
import org.testng.*;

/**
 * @author Jonathan Mbuyi Tshitenda [ ABJT439 ]
 * @date created 8/5/2019
 * @package runner
 */
public class TestNgListener implements IInvokedMethodListener
{
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
//
//    /**testNG - changing test results dynamically*/
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
}
