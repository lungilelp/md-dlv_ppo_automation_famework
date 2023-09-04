package pages;

import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import config.Settings;
import base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.AssertJUnit;
import utils.extentreports.ExtentTestManager;
import utils.logs.JSErrorLogs;
import utils.logs.Log;

import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertTrue;

public class LoginPage extends BasePage {
    /**
     * Constructor
     */
    public LoginPage(WebDriver driver) {
        
        super(driver);
        Log.info("The current working class: "+this.getClass().getName());
    }

    /**
     * Variables
     */
    String baseURL = Settings.AUT;
    /**
     * Web Elements
     */
//    By userNameId = By.id("email");
//    By passwordId = By.id("password");
//    By loginButtonId = By.id("loginButton");
    By errorMessageUsernameXpath = By.xpath("//*[@id=\"loginForm\"]/div[1]/div/div");
    By errorMessagePasswordXpath = By.xpath("//*[@id=\"loginForm\"]/div[2]/div/div");
    String usenameTxtBoxId = "username";
    String passwordTxtBoxId = "password";
    String loginButtonId = "kc-login";
    String paymentSearchTabId = "payment-search";
    String welcomeTabId = "welcome";
    String dashboardTabId = "dashboard";
    String paymentCaptureTabId = "payment-capture";
    String tasklistTabId = "task-list";
    String welcomePagePopUpErrorXpath="/html/body/ngb-modal-window/div/div/sol-modal-alert";
    String createTeamTab = "create-team";


    String welcomeTextXPATH="//*[@id=\"titleHeader\"]/div";
    String profileIconXPATH="/html/body/app-root/div/div/sol-header/div/sol-header-masthead/div[3]/div/div/ul/li/span";
    String userRoleContainerXPATH="//*[@id=\"logoutDropdown\"]/li/div/div[1]/div[2]";
    String logOutID="logOutButton";
    //******************************************** ADDED START **************************************************************
    String paymentWelcomeTabId = "welcome";
    String userLogoutIconXpath = "/html/body/app-root/div/div/sol-header/div/sol-header-masthead/div[3]/div/div/ul/li[1]/span";
    String reportsTabID="reports";
    String bulkFilesTabID="bulk-files"; String applicationEventsTabID="application-events"; String bulkPaymentSearchTabID="bulk-payment-search";
    String createTeamsTabID="create-team"; String taskListTabId = "task-list";  String rateUploadTabID="rates-upload"; String referenceDataTabID="reference-data";
    String welcomeToCountryTitleHeaderXpath="//*[@id=\"titleHeader\"]/div/div/span";
    String teamsTabXpath="//*[@id=\"create-team\"]";

    String paymentSearchHeardingId="titleHeader";
    String pageTitleHeaderXpath="//*[@id=\"titleHeader\"]/div/div/span";
    String createTeamsHeaderXpath="//*[@id=\"titleHeader\"]/div/div/span";

    /**
     * Page Methods
     */
    public LoginPage goToMakolaDLV() {
        Log.info("Opening Makola DLV");
        driver.get("https://celine-ui.celine-sit.cto-payments-makola.nonprod.caas.absa.co.za/");
        return this;
}
//
//    public LoginPage login(String username, String password) {
//        Log.info("Trying to login the N11."+this.getClass().getName());
//        writeText(userNameId, username);
//        writeText(passwordId, password);
//        click(loginButtonId);
//        return this;
//    return this;}
//
//    //Verify Username Condition
//    public LoginPage verifyLoginUserName(String expectedText) {
//        Log.info("Verifying login username.");
//        waitVisibility(errorMessageUsernameXpath);
//        assertEquals(readText(errorMessageUsernameXpath), expectedText);
//        return this;
//    return this;}
//
//    //Verify Password Condition
//    public LoginPage verifyLoginPassword(String expectedText) {
//        Log.info("Verifying login password.");
//        waitVisibility(errorMessagePasswordXpath);
//        assertEquals(readText(errorMessagePasswordXpath), expectedText);
//        return this;
//    return this;}

    //Verify Password Condition
    public LoginPage verifyLogError() {
        Log.info("Verifying javascript login errors.");
        AssertJUnit.assertTrue(JSErrorLogs.isLoginErrorLog(driver));

    return this;}

    public boolean verifyBasePageTitle () throws Exception {
        String name = new Object(){}.getClass().getEnclosingMethod().getName();
        Log.info(BasePage.getGenericMessage(name ));
        String expectedPageTitle = null;
        try {
            expectedPageTitle = "Payment Portal";
        }
        catch (Exception e) {
            /**EXCEPTION*/
            screenshotLoggerMethod("LandingPage", "Landing Page");
            Log.debug("Failed to obtain the correct title");
            ExtentTestManager.getTest().fail(MarkupHelper.createLabel("PPO error occurred,please check PopUp", ExtentColor.ORANGE));
        }
        return getPageTitle().contains(expectedPageTitle);
}


    /**"login To PPO As ServiceAgent"*/
    public LoginPage loginToPPOAsServiceAgent (String ServiceAgentCommercialOperationsUsername , String ServiceAgentCommercialOperationsPassword) throws Exception
    {   String name = new Object(){}.getClass().getEnclosingMethod().getName();
        Log.info(BasePage.getGenericMessage(name ));
        try
        {
            //Enter Username(Username)
            SetScriptTimeout();
            writeText(By.id(usenameTxtBoxId), ServiceAgentCommercialOperationsUsername);

            //Enter Password
            writeText(By.id(passwordTxtBoxId), ServiceAgentCommercialOperationsPassword);

            //Click Login Button
            click(By.id(loginButtonId), By.id(paymentWelcomeTabId));
            SetScriptTimeout();

            /**ASSERTIONS::Assert that all expected elements are visible on the page**/
            Assert.assertTrue(driver.findElement(By.id(paymentSearchTabId)).isDisplayed());
            Assert.assertTrue(driver.findElement(By.id(welcomeTabId)).isDisplayed());
            ExtentTestManager.getTest().log(Status.PASS, "Status=PASS:: Correct menus are availble for Payment Officer Commercial Agent");

            //Check PopUp window
            Boolean welcomePop = isAlertPresent();
            if(welcomePop)
            {
                ExtentTestManager.getTest().fail(MarkupHelper.createLabel("PPO error occurred,please check PopUp", ExtentColor.RED));
                screenshotLoggerMethod("PopUp screenshot","PPO error occurred,please check PopUp");
                stopCurrentTestSetResultsToFailedAndContinueWithNextTest();
            }else
            {
                ExtentTestManager.getTest().pass(MarkupHelper.createLabel("PPO ready for capture", ExtentColor.BLUE));
            }

            //Take Screenshot to confirm successful login
            screenshotLoggerMethod("loginToPPOAsServiceAgent","PPO Login Screenshot");

        }
        catch (Exception e)
        {
            /**EXCEPTION*/
            screenshotLoggerMethod("Login Page","Login Page");
            ExtentTestManager.getTest().fail("EXCEPTION:: Login not successful" + e.getMessage());
        }
    return this; }


    /**Login To PPO As Payment Officer Repair Officer Commercial Operations*/
    public LoginPage PaymentOfficerRepairOfficerCommercialOperations (String PaymentOfficerRepairOfficerCommercialOperationsUsername , String PaymentOfficerRepairOfficerCommercialOperationsPassword) throws Exception {
        String name = new Object(){}.getClass().getEnclosingMethod().getName();
        Log.info(BasePage.getGenericMessage(name ));
        try {
            //Enter Username(Username)
            writeText(By.id(usenameTxtBoxId), PaymentOfficerRepairOfficerCommercialOperationsUsername);
            //Enter Password
            writeText(By.id(passwordTxtBoxId), PaymentOfficerRepairOfficerCommercialOperationsPassword);

            //Click Login Button
            click(By.id(loginButtonId), By.id(paymentWelcomeTabId));
            SetScriptTimeout();

            //Check PopUp window
            Boolean welcomePop = isAlertPresent();
            if (welcomePop) {
                ExtentTestManager.getTest().fail(MarkupHelper.createLabel("PPO error occurred,please check PopUp", ExtentColor.RED));
                screenshotLoggerMethod("PopUp screenshot", "PPO error occurred,please check PopUp");
                stopCurrentTestSetResultsToFailedAndContinueWithNextTest();
            } else {
                ExtentTestManager.getTest().pass(MarkupHelper.createLabel("PPO ready for capture", ExtentColor.BLUE));
            }

            //Take Screenshot to confirm successful login
            screenshotLoggerMethod("PaymentOfficerRepairOfficerCommercialOperations", "Payment Officer Repair Officer Commercial Operations");

       } catch (Exception e) {
            screenshotLoggerMethod("Login Page", "Login Page");
            ExtentTestManager.getTest().fail("EXCEPTION:: Login not successful" + e.getMessage());
        }
    return this;}

    //awanti added this user
    /**login To PPO As SystemAdministrator**/
    public LoginPage loginToPPOAsSystemAdministrator (String SystemAdministratorUsername , String SystemAdministratorPassword) throws Exception {
        String name = new Object(){}.getClass().getEnclosingMethod().getName();
        Log.info(BasePage.getGenericMessage(name ));
        try
            {
                //Enter Username(Username)
                SetScriptTimeout();
                writeText(By.id(usenameTxtBoxId), SystemAdministratorUsername);
                //Enter Password
                writeText(By.id(passwordTxtBoxId), SystemAdministratorPassword);
                //Click Login Button
                click(By.id(loginButtonId), By.id(paymentWelcomeTabId));
                SetScriptTimeout();

                /**ASSERTIONS::Assert that all expected elements are visible on the page**/
                Assert.assertTrue((driver.findElement(By.id(createTeamTab)).isDisplayed()),"Check that the item exists");
                ExtentTestManager.getTest().log(Status.PASS, "Status=PASS:: Correct menus are available for System Administrator");

                //Take Screenshot
                screenshotLoggerMethod("loginToPPOAsSystemAdministrator","loginToPPOAsSystemAdministrator");
            }
            catch (Exception e)
            {
                /**EXCEPTION*/
                ExtentTestManager.getTest().fail("EXCEPTION:: Login not successful" + e.getMessage());
            }
        return this;}

    public LoginPage login (String username , String password)
    {
        String name = new Object(){}.getClass().getEnclosingMethod().getName();
        Log.info(BasePage.getGenericMessage(name ));
        try {
            //Enter Username(Username)
            writeText(By.id(usenameTxtBoxId), username);
            //Enter Password
            writeText(By.id(passwordTxtBoxId), password);

            //Click Login Button
            click(By.id(loginButtonId), By.id(paymentWelcomeTabId));
            SetScriptTimeout();

        ;}catch(Exception e){}

    return this;}


    /**Close the session*/
    public LoginPage closeBrowser()
    {String name = new Object(){}.getClass().getEnclosingMethod().getName();
        Log.info(BasePage.getGenericMessage(name ));
        driver.close();
    return this;}


    /**Close the session*/
    public LoginPage endWebdriverSession()
    {String name = new Object(){}.getClass().getEnclosingMethod().getName();
        Log.info(BasePage.getGenericMessage(name ));
        /**It basically calls driver.dispose method which in turn closes all the browser windows and ends the WebDriver session gracefully.*/
        driver.quit();

return this;
    }
}