package tests;


import org.testng.Assert;
import org.testng.annotations.Test;

import java.lang.reflect.Method;

import static utils.extentreports.ExtentTestManager.startTest;

public class LoginTests extends BaseTest {
    @Test(priority = 0, description = "Invalid Login Scenario with wrong username and password.")
    public void invalidLoginTest_InvalidUserNameInvalidPassword(Method method) throws Exception {
        //ExtentReports Description
        startTest(method.getName(), "Invalid Login Scenario with invalid username and password.");

            loginPage
                    .goToMakolaDLV()
                    .login("ABZB10P", "P@ssword123");
            Assert.assertEquals(driver.getTitle(),"Log in to payments-portal");

    }

//    @Test(priority = 1, description = "Invalid Login Scenario with empty username and password.")
//    public void invalidLoginTest_EmptyUserEmptyPassword(Method method) {
//        //ExtentReports Description
//        startTest(method.getName(), "Invalid Login Scenario with empty username and password.");
//
//        homePage
//                .goToN11()
//                .goToLoginPage()
//                .loginToN11("", "")
//                .verifyLoginUserName("Lütfen e-posta adresinizi girin.")
//                .verifyLoginPassword("WRONG MESSAGE FOR FAILURE!");
//    }
}