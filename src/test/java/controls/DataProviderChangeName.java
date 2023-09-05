package controls;

import org.testng.ITestContext;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.lang.reflect.Method;

import static org.testng.Assert.assertTrue;

/**
 * @author Jonathan Mbuyi Tshitenda [ ABJT439 ]
 * @date created 9/10/2019
 * @package com.jmt.framework.controls.internals
 */
public class DataProviderChangeName {


    private ThreadLocal<String> testName = new ThreadLocal<>();

    @DataProvider(name = "role")
    public static Object[][] roles()
    {
        return new Object[][]{{"Developer"}, {"Team Lead"}, {"QA"}, {"Business Analyst"}, {"DevOps Eng"}, {"PMO"}};
    }

    @DataProvider( name = "dp" )
    public Object[][] getTestData()
    {
        Object[][] data = new Object[][]
                {
                        { "Onus","TestCase1", "Sample test 1" },
                        { "BE","TestCase2", "Sample test 2" },
                        { "CONFIDENT","TestCase3", "Sample test 3" },
                        { "IN THE ","TestCase4", "Sample test 4" },
                        { "LORD","TestCase5", "Sample test 5" }
                };
        return data;
    }




    /**Test With Dynamic Parameterized Data from Excell Data Providers*/
    @Test( dataProvider = "dp" )
    public void testSample1( String num, String name, String desc, ITestContext ctx )
    {
        ctx.setAttribute("testName", name);
        ctx.setAttribute("testDesc", desc);
        ctx.setAttribute("testNum", num);
        assertTrue( true );
    }


    /**Test With Static Data*/
    // 1. Verify create staff invitations (Doctor) with valid authToken and parameters (Positive scenario)
    @Test(dataProvider = "role")
    public void createUser(String role)
    {

    }





    /**Method to change test names based on data Providers/Excel*/
    @BeforeMethod
    public void BeforeMethod(Method method, Object[] testData, ITestContext ctx) {
        if (testData.length > 0) {
            testName.set(method.getName() + "_" + testData[0]);
            ctx.setAttribute("testName", testName.get());
        } else
            ctx.setAttribute("testName", method.getName());
    }

    /**Method to change overrid the default Testng name getName method*/
    //@Override
    public String getTestName() {
        return testName.get();
    }
}
