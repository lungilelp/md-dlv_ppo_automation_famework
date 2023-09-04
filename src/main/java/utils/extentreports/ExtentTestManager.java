package utils.extentreports;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.markuputils.CodeLanguage;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import org.openqa.selenium.Platform;
import org.testng.ITestResult;
import org.testng.annotations.AfterSuite;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static utils.extentreports.ExtentManager.reportFileName;

/**
 * extentTestMap holds the information of thread ids and ExtentTest instances.
 * ExtentReports instance created by calling createExtentReports() method from ExtentManager.
 * At startTest() method, an instance of ExtentTest created and put into extentTestMap with current thread id.
 * At getTest() method, return ExtentTest instance in extentTestMap by using current thread id.
 */
public class ExtentTestManager {
    static Map<Integer, ExtentTest> extentTestMap = new HashMap<>();
    static ExtentReports extent = ExtentManager.createExtentReports();

    public static synchronized ExtentTest getTest() {
        return extentTestMap.get((int) Thread.currentThread().getId());
    }

    public static synchronized ExtentTest startTest(String testName, String desc) {
        ExtentTest test = extent.createTest(testName, desc);
        extentTestMap.put((int) Thread.currentThread().getId(), test);
        return test;
    }
    //_________________________________________________________________________________













    public static synchronized void endTest() {
        extent.flush();
    }


    public static synchronized ExtentTest startTest(String testName) {
        ExtentTest test = extent.createTest(testName);
        extentTestMap.put((int) (long) (Thread.currentThread().getId()), test);
        return test;
    }


    public static void codeLogsXML(String codeSnippet)
    {
        /**INFO*/
        Markup code = MarkupHelper.createCodeBlock(codeSnippet, CodeLanguage.XML);
        ExtentTestManager.getTest().pass(code);
    }

    public static void createTableLogs(ITestResult iTestResult)
    {
        String[][] data =
                {
                        { "Header1", "Header2", "Header3" },
                        { "Content.1.1", "Content.2.1", "Content.3.1" },
                        { "Content.1.2", "Content.2.2", "Content.3.2" },
                        { "Content.1.3", "Content.2.3", "Content.3.3" },
                        { "Content.1.4", "Content.2.4", "Content.3.4" }
                };
        Markup m = MarkupHelper.createTable(data);
        ExtentTestManager.getTest().fail(m);
    }

    public static void codeLogs(ITestResult iTestResult)
    {
        // xml
        String code = "<root>" +
                "\n    <Person>" +
                "\n        <Name>Joe Doe</Name>" +
                "\n        <StartDate>2007-01-01</StartDate>" +
                "\n        <EndDate>2009-01-01</EndDate>" +
                "\n        <Location>London</Location>" +
                "\n    </Person>                    " +
                "\n    <Person>" +
                "\n        <Name>John Smith</Name>" +
                "\n        <StartDate>2012-06-15</StartDate>" +
                "\n        <EndDate>2014-12-31</EndDate>" +
                "\n        <Location>Cardiff</Location>" +
                "\n    </Person>" +
                "\n</root>";
        Markup m2 = MarkupHelper.createCodeBlock(code, CodeLanguage.XML);
        ExtentTestManager.getTest().pass(m2);
    }


    //Open the report after each execution
    @AfterSuite(alwaysRun = true)
    public static void tearDown()
    {
        //Open the Report HTML after tests
        if (Desktop.isDesktopSupported())
        {
            try
            {
                //MAC or Windows Selection path
                String reportLocation = null;
                if (Platform.getCurrent().toString().equalsIgnoreCase("MAC"))
                {
                    //reportLocation = new String(System.getProperty("user.dir")+"//testReports//extentReports//"+reportFileName);
                } else if (Platform.getCurrent().toString().contains("WIN"))
                {
                    reportLocation = new String(System.getProperty("user.dir")+"\\testReports\\extentReports\\"+reportFileName);
                }
                Desktop.getDesktop().open(new File(reportLocation));
            } catch (IOException ex)
            {
            }
        }
    }

}