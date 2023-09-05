package pages;

import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import base.BasePage;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import utils.extentreports.ExtentTestManager;
import utils.logs.Log;

/**
 * @author Collin Ngeleka [ ABCN530 ]
 * @date created 10/28/2019
 * @package pages
 */
public class UserAccessVerificationPage extends BasePage {

    public UserAccessVerificationPage(WebDriver driver) {

        super(driver);
        Log.info("The current working class: "+this.getClass().getName());
    }
    //***********************************WEB ELEMENTS*******************************************************************
    //******************************************************************************************************************
    String usenameTxtBoxId = "username";
    String passwordTxtBoxId = "password";
    String loginButtonId = "kc-login";
    String paymentSearchTabId = "payment-search";
    String welcomeTabId = "welcome";
    String dashboardTabId = "dashboard";
    String paymentCaptureTabId = "payment-capture";
    String tasklistTabId = "task-list";
    String welcomePagePopUpErrorXpath="/html/body/ngb-modal-window/div/div/sol-modal-alert";

    String welcomeTextXPATH="//*[@id=\"titleHeader\"]/div";
    String profileIconXPATH="/html/body/app-root/div/div/sol-header/div/sol-header-masthead/div[3]/div/div/ul/li/span";
    String userRoleContainerXPATH="//*[@id=\"logoutDropdown\"]/li/div/div[1]/div[2]";
    String logOutID="logOutButton";

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

    @FindBy(how= How.ID,using="FFFFF")
    public  static WebElement testElement;


    public  UserAccessVerificationPage verifyUserRoleAndCountry(String userRole, String country, String role) throws Exception
    {
        try {
            //click the profile icon
            waitForElementToBeVisible(By.xpath(profileIconXPATH));
            click(By.xpath(profileIconXPATH));
            ExtentTestManager.getTest().pass(MarkupHelper.createLabel(" logged in successfully:: " + "  Successfully logged in ", ExtentColor.GREEN));

            //Take Screenshot
            screenshotLoggerMethod("LoggedInUser", "LoggedInUser:");

            String userRoleCountryTextOnProfileIcon = driver.findElement(By.xpath(userRoleContainerXPATH)).getText(); //Here will be line to replace commas(,) in case user has multi roles
            String welcomeTextFromHeader = driver.findElement(By.xpath(welcomeToCountryTitleHeaderXpath)).getText();
            String userRoleFromProfileIcon = driver.findElement(By.xpath(userRoleContainerXPATH)).getText();
            boolean isContainUserRole = userRoleFromProfileIcon.contains(userRole);//below is option to ignore case inconsistency from ppo userRole naming
            boolean isContainUserRole1 = StringUtils.containsIgnoreCase(userRoleFromProfileIcon, userRole.trim());

            Thread.sleep(10000);
            int roleLength = userRole.length();
            int countryNameLength = country.length();
            int countryNameIndex = 27;
            int welcomeTextFromHeaderLength = welcomeTextFromHeader.length();

            //below I trim country name from title header
            //String countryNameFromHeader = trimLeadingCharacters(welcomeTextFromHeader,countryNameIndex);


            //**********************CHECKING IF THE CORRECT USER ROLE IS DISPLAYED *************************************
            if (isContainUserRole) {
                String roleOnProfile = userRoleFromProfileIcon.substring(0, roleLength);
                System.out.println("user role after some comparing and substring:: " + roleOnProfile);
                if (roleOnProfile.equalsIgnoreCase(userRole)) {
                    //highlight user role name
                    ExtentTestManager.getTest().pass(MarkupHelper.createLabel(" Correct User Logged In:: " + "  Correct user  and correct user role:: ", ExtentColor.GREEN));
                    ExtentTestManager.getTest().info("Expected User Role is:: " + userRole + "| and | Actual User Role is:: " + roleOnProfile);
                    highLighterMethod(By.xpath(userRoleContainerXPATH));
                    screenshotLoggerMethod("PassedUserRole", "User Role Successful::");

                    //**********************CHECKING IF THE EXPECTED TABS ARE AVAILABLE AND CLICKABLE***********************
                    try {
                        //TODO By Msa, Call the clickHighlightTabAndHeader method instead of all diff clickAndHigh.....
                        if (role.equalsIgnoreCase("SA")) {
                            //click payment search tab1
                            clickAndHighlightPaymentSearchTab();

                            //TODO By Msa, declararion or giving element names of tabs should be like 'testElement' example and learn this boy
/*
                            clickATab(userRole, testElement);
                            clickHighlightTabAndHeader(paymentSearchTabId,"Payment Search","Payment Search",pageTitleHeaderXpath);
*/

                            //Click report tab
                            clickAndHighlightReportsTab();

                            //Click bulk file tab
                            clickAndHighlightBulkFilesTabTab();

                            //click application events tab
                            clickAndHighlightApplicationEventsTab();

                            //click bulk payment search tab
                            clickAndHighlightBulkPaymentSearchTab();

                            //click welcome tab and check country name on page title
                            clickAndHighlightWelcomeTab(country);

                        } else if (role.equalsIgnoreCase("PO")) {
                            //click payment capture tab
                            clickAndHighlightPaymentCaptureTab();

                            //click payment search tab
                            clickAndHighlightPaymentSearchTab();

                            //click task list tab
                            clickAndHighlightTaskListTab();

                            //click welcome tab and check country name on page title
                            clickAndHighlightWelcomeTab(country);

                        } else if (role.equalsIgnoreCase("PS")) {
                            //click payment search tab
                            clickAndHighlightPaymentSearchTab();

                            //click task list tab
                            clickAndHighlightTaskListTab();

                            //click rate upload tab
                            clickAndHighlightRateUploadTab();

                            //click reference data tab
                            clickAndHighlightReferenceDataTab();

                            //click welcome tab and check country name on page title
                            clickAndHighlightWelcomeTab(country);

                        } else if (role.equalsIgnoreCase("RS")) {
                            //click payment search tab
                            clickAndHighlightPaymentSearchTab();

                            //click task list tab
                            clickAndHighlightTaskListTab();

                            //click welcome tab and check country name on page title
                            clickAndHighlightWelcomeTab(country);
                        } else if (role.equalsIgnoreCase("AD")) {
                            //click teams tab
                            clickAndHighlightCreateTeamsTab();

                            //click welcome tab and check country name on page title
                            clickAndHighlightWelcomeTab(country);
                        } else {
                            clickAndHighlightWelcomeTab(country);
                            ExtentTestManager.getTest().info(MarkupHelper.createLabel("UnknownUserRole:: " + "Not existing user role specified:", ExtentColor.ORANGE));
                        }
                    } catch (Exception e) {
                    }

                } else {
                    //highlight
                    ExtentTestManager.getTest().fail(MarkupHelper.createLabel(" User Role Not Found:: " + "  User Role Not Found:: ", ExtentColor.RED));
                    ExtentTestManager.getTest().warning("Expected User Role is:: " + userRole + "| and | Actual User Role is:: " + roleOnProfile);
                    screenshotLoggerMethod("FailedUserRole", "User Role Unsuccessful::");
                }
            }

        boolean isContainCountryName = userRoleFromProfileIcon.contains(country);
        String countryOnProfile = "";
        System.out.println("click profile icon again to check country name");
        scrollUp();
        scrollElementIntoView(By.xpath(profileIconXPATH));
        click(By.xpath(profileIconXPATH));
        if (isContainCountryName) {
            //int sss=roleLength+1;
            countryOnProfile = userRoleFromProfileIcon.substring(roleLength).trim();
            System.out.println("Country Name after compare and substring the userRole out:: " + countryOnProfile);
            //click the profile icon
            //click(By.xpath(profileIconXPATH));
            //highlight
            //highLighterMethod(By.xpath(welcomeTextFromHeader));

            ExtentTestManager.getTest().pass(MarkupHelper.createLabel(" Correct Country Logged Into:: " + "  Correct Country Name ", ExtentColor.GREEN));
            ExtentTestManager.getTest().info("Expected Country is:: " + country + "| and | Actual Country is:: " + countryOnProfile);
            screenshotLoggerMethod("PassedCountryName", "Successful Landed Into Correct Country::");
            click(By.id(welcomeTabId));
            Thread.sleep(1000);
        } else {
            //highlight
            ExtentTestManager.getTest().fail(MarkupHelper.createLabel(" Expected Country Not Found:: " + "  Expected Country Not Found::", ExtentColor.RED));
            ExtentTestManager.getTest().warning("Expected Country is:: " + country + "| and | Actual Country is:: " + countryOnProfile);
            screenshotLoggerMethod("FailedCountryName", "UnSuccessful Country Found::");
        } }
        catch (Exception ex) {
                ex.printStackTrace();
            }

        return this;}

// *********************************************ADDED START2**********************************************************************
        public UserAccessVerificationPage login (String username, String password) throws Exception
        {
            try {
                //Enter Username(Username)
                writeText(By.id(usenameTxtBoxId), username);
                //Enter Password
                writeText(By.id(passwordTxtBoxId), password);

                //Click Login Button
                click(By.id(loginButtonId));
                SetScriptTimeout();

            } catch (Exception e) {
            }
            return this;}

        //TODO By Msa, Make the clickAndHighlightTabs method one and re-Use it. It must accept Tab-ID, Header-Title / Heading text, titleXpath
        public UserAccessVerificationPage clickHighlightTabAndHeader (By tabElement, String tabName, String headerText, By headerElement) throws
        Exception
        {
            waitAndClickTab((tabElement));
            Thread.sleep(1000);
            highLighterMethod((tabElement));
            ExtentTestManager.getTest().pass(MarkupHelper.createLabel(tabName + " Tab Passed Clicking:", ExtentColor.GREEN));
            screenshotLoggerMethod(tabName + " Clickable", tabName + " tab passed the clicking::");

            if (readText((headerElement)).equalsIgnoreCase(headerText)) {
                ExtentTestManager.getTest().info(MarkupHelper.createLabel("Correct Heading Displayed:: " + "The page has correct heading after clicking:", ExtentColor.BLUE));
                highLighterMethod((headerElement));
                screenshotLoggerMethod(headerText + " TitleHeader", "Correct Page Title Header Displayed");
            } else {
                ExtentTestManager.getTest().warning(MarkupHelper.createLabel(headerText + " Not Visible:: " + "The page don't show expected heading:", ExtentColor.ORANGE));
            }
            return this;}

        public UserAccessVerificationPage clickAndHighlightTaskListTab () throws Exception
        {
            waitAndClickTab(By.id(tasklistTabId));
            Thread.sleep(1000);
            highLighterMethod(By.id(tasklistTabId));
            ExtentTestManager.getTest().pass(MarkupHelper.createLabel("TaskListTab: " + "Task List Tab Passed Clicking:", ExtentColor.GREEN));
            screenshotLoggerMethod("PassedTabClick", "Task List tab passed the clicking::");

            if (readText(By.xpath(pageTitleHeaderXpath)).equalsIgnoreCase("Task List")) {
                ExtentTestManager.getTest().info(MarkupHelper.createLabel("Correct Heading Displayed:: " + "The page has correct heading after clicking:", ExtentColor.BLUE));
                highLighterMethod(By.xpath(pageTitleHeaderXpath));
                screenshotLoggerMethod("PageTitleHeader", "Correct Page Title Header Displayed");
            } else {
                ExtentTestManager.getTest().warning(MarkupHelper.createLabel("Heading Not Visible:: " + "The page don't show expected heading:", ExtentColor.ORANGE));
            }
            return this;}

        public UserAccessVerificationPage clickAndHighlightBulkPaymentSearchTab () throws Exception
        {
            waitAndClickTab(By.id(bulkPaymentSearchTabID));
            Thread.sleep(1500);
            highLighterMethod(By.id(bulkPaymentSearchTabID));
            ExtentTestManager.getTest().pass(MarkupHelper.createLabel("BulkPaymentSearchTab: " + "BulkPaymentSearch Tab Passed Clicking:", ExtentColor.GREEN));
            screenshotLoggerMethod("PassedTabClick", "BulkPaymentSearch tab passed the clicking::");

            if (readText(By.xpath(pageTitleHeaderXpath)).equalsIgnoreCase("Bulk Payment Search")) {
                ExtentTestManager.getTest().info(MarkupHelper.createLabel("Correct Heading Displayed:: " + "The page has correct heading after clicking:", ExtentColor.BLUE));
                highLighterMethod(By.xpath(pageTitleHeaderXpath));
                screenshotLoggerMethod("PageTitleHeader", "Correct Page Title Header Displayed");
            } else {
                ExtentTestManager.getTest().warning(MarkupHelper.createLabel("Heading Not Visible:: " + "The page don't show expected heading:", ExtentColor.ORANGE));
            }
            return this;}

        public UserAccessVerificationPage clickAndHighlightWelcomeTab (String country) throws Exception
        {
            waitAndClickTab(By.id(welcomeTabId));
            highLighterMethod(By.id(welcomeTabId));
            ExtentTestManager.getTest().pass(MarkupHelper.createLabel("WelcomeTab: " + "welcome Tab Passed Clicking:", ExtentColor.GREEN));
            screenshotLoggerMethod("PassedTabClick", "welcome tab passed the clicking::");

            if (readText(By.xpath(pageTitleHeaderXpath)).equalsIgnoreCase("Welcome to Payments Portal " + country)) {
                ExtentTestManager.getTest().info(MarkupHelper.createLabel("Correct Heading Displayed:: " + "The page has correct heading after clicking:", ExtentColor.BLUE));
                highLighterMethod(By.xpath(pageTitleHeaderXpath));
                screenshotLoggerMethod("PageTitleHeader", "Correct Page Title Header Displayed");
            } else if (!readText(By.xpath(pageTitleHeaderXpath)).equalsIgnoreCase(country)) {
                if (country.equalsIgnoreCase("Tanzania BBT")) {
                    highLighterMethod(By.xpath(welcomeTextXPATH));
                    Thread.sleep(1000);
                    ExtentTestManager.getTest().warning(MarkupHelper.createLabel(" Partial Correct Country Logged Into:: " + " Country Name Not In Full Name ", ExtentColor.ORANGE));
                    ExtentTestManager.getTest().warning("Expected Country is:: " + country + "| and | Country Name From PPO is:: " + readText(By.xpath(pageTitleHeaderXpath)).substring(27).trim());
                    screenshotLoggerMethod("PartiallyPassedCountryHeaderName", "Welcomed To Partial Correct Country::");
                } else {
                    ExtentTestManager.getTest().fail(MarkupHelper.createLabel("Wrong country name displayed:::" + ":::THE COUNTRY NAME ON WELCOME MSG IS WRONG:::", ExtentColor.RED));
                }
            } else {
                ExtentTestManager.getTest().warning(MarkupHelper.createLabel("Heading Not Visible:: " + "The page don't show expected heading:", ExtentColor.ORANGE));
            }
            return this;}

        public UserAccessVerificationPage clickAndHighlightPaymentCaptureTab ()throws Exception
        {
            waitAndClickTab(By.id(paymentCaptureTabId));
            highLighterMethod(By.id(paymentCaptureTabId));
            ExtentTestManager.getTest().pass(MarkupHelper.createLabel("PaymentCaptureTab: " + "Payment Capture Tab Passed Clicking:", ExtentColor.GREEN));
            screenshotLoggerMethod("PassedTabClick", "Payment Capture tab passed the clicking::");

            if (readText(By.xpath(pageTitleHeaderXpath)).equalsIgnoreCase("Payment Capture")) {
                ExtentTestManager.getTest().info(MarkupHelper.createLabel("Correct Heading Displayed:: " + "The page has correct heading after clicking:", ExtentColor.BLUE));
                highLighterMethod(By.xpath(pageTitleHeaderXpath));
                screenshotLoggerMethod("PageTitleHeader", "Correct Page Title Header Displayed");
            } else {
                ExtentTestManager.getTest().warning(MarkupHelper.createLabel("Heading Not Visible:: " + "The page don't show expected heading:", ExtentColor.ORANGE));
            }
            return this;}

        public UserAccessVerificationPage clickAndHighlightBulkFilesTabTab ()throws Exception
        {
            waitAndClickTab(By.id(bulkFilesTabID));
            Thread.sleep(1500);
            highLighterMethod(By.id(bulkFilesTabID));
            ExtentTestManager.getTest().pass(MarkupHelper.createLabel("BulkFilesTab: " + "BulkFiles Tab Passed Clicking:", ExtentColor.GREEN));
            screenshotLoggerMethod("PassedTabClick", "BulkFiles tab passed the clicking::");

            if (readText(By.xpath(pageTitleHeaderXpath)).equalsIgnoreCase("Bulk File Listing")) {
                ExtentTestManager.getTest().info(MarkupHelper.createLabel("Correct Heading Displayed:: " + "The page has correct heading after clicking:", ExtentColor.BLUE));
                highLighterMethod(By.xpath(pageTitleHeaderXpath));
                screenshotLoggerMethod("PageTitleHeader", "Correct Page Title Header Displayed");
            } else {
                ExtentTestManager.getTest().warning(MarkupHelper.createLabel("Heading Not Visible:: " + "The page don't show expected heading:", ExtentColor.ORANGE));
            }
            return this;}

        public UserAccessVerificationPage clickAndHighlightReportsTab ()throws Exception
        {
            waitAndClickTab(By.id(reportsTabID));
            Thread.sleep(1500);
            highLighterMethod(By.id(reportsTabID));
            ExtentTestManager.getTest().pass(MarkupHelper.createLabel("ReportsTab: " + "Reports Tab Passed Clicking:", ExtentColor.GREEN));
            screenshotLoggerMethod("PassedTabClick", "Reports tab passed the clicking::");

            if (readText(By.xpath(pageTitleHeaderXpath)).equalsIgnoreCase("Reports")) {
                ExtentTestManager.getTest().info(MarkupHelper.createLabel("Correct Heading Displayed:: " + "The page has correct heading after clicking:", ExtentColor.BLUE));
                highLighterMethod(By.xpath(pageTitleHeaderXpath));
                screenshotLoggerMethod("PageTitleHeader", "Correct Page Title Header Displayed");
            } else {
                ExtentTestManager.getTest().warning(MarkupHelper.createLabel("Heading Not Visible:: " + "The page don't show expected heading:", ExtentColor.ORANGE));
            }
            return this;}

        public UserAccessVerificationPage clickAndHighlightApplicationEventsTab ()throws Exception
        {
            waitAndClickTab(By.id(applicationEventsTabID));
            Thread.sleep(1500);
            highLighterMethod(By.id(applicationEventsTabID));
            ExtentTestManager.getTest().pass(MarkupHelper.createLabel("ApplicationEventsTab: " + "ApplicationEvents Tab Passed Clicking:", ExtentColor.GREEN));
            screenshotLoggerMethod("PassedTabClick", "ApplicationEvents tab passed the clicking::");

            if (readText(By.xpath(pageTitleHeaderXpath)).equalsIgnoreCase("Application Event Search")) {
                ExtentTestManager.getTest().info(MarkupHelper.createLabel("Correct Heading Displayed:: " + "The page has correct heading after clicking:", ExtentColor.BLUE));
                highLighterMethod(By.xpath(pageTitleHeaderXpath));
                screenshotLoggerMethod("PageTitleHeader", "Correct Page Title Header Displayed");
            } else {
                ExtentTestManager.getTest().warning(MarkupHelper.createLabel("Heading Not Visible:: " + "The page don't show expected heading:", ExtentColor.ORANGE));
            }
            return this;}

        public UserAccessVerificationPage clickAndHighlightRateUploadTab ()throws Exception
        {
            waitAndClickTab(By.id(rateUploadTabID));
            Thread.sleep(1500);
            highLighterMethod(By.id(rateUploadTabID));
            ExtentTestManager.getTest().pass(MarkupHelper.createLabel("RatesUploadTab: " + "Rates Upload Tab Passed Clicking:", ExtentColor.GREEN));
            screenshotLoggerMethod("PassedTabClick", "Rates Upload tab passed the clicking::");

            if (readText(By.xpath(pageTitleHeaderXpath)).equalsIgnoreCase("Daily Rates File Upload")) {
                ExtentTestManager.getTest().info(MarkupHelper.createLabel("Correct Heading Displayed:: " + "The page has correct heading after clicking:", ExtentColor.BLUE));
                highLighterMethod(By.xpath(pageTitleHeaderXpath));
                screenshotLoggerMethod("PageTitleHeader", "Correct Page Title Header Displayed");
            } else {
                ExtentTestManager.getTest().warning(MarkupHelper.createLabel("Heading Not Visible:: " + "The page don't show expected heading:", ExtentColor.ORANGE));
            }
            return this; }

        public UserAccessVerificationPage clickAndHighlightReferenceDataTab ()throws Exception
        {
            waitAndClickTab(By.id(referenceDataTabID));
            Thread.sleep(1500);
            highLighterMethod(By.id(referenceDataTabID));
            ExtentTestManager.getTest().pass(MarkupHelper.createLabel("ReferenceDataTab: " + "Reference Data Tab Passed Clicking:", ExtentColor.GREEN));
            screenshotLoggerMethod("PassedTabClick", "Reference Data tab passed the clicking::");

/*        if(readText(By.xpath(pageTitleHeaderXpath)).equalsIgnoreCase("Reference Data"))
        {
            ExtentTestManager.getTest().info(MarkupHelper.createLabel("Correct Heading Displayed:: "+"The page has correct heading after clicking:",ExtentColor.BLUE));
            highLighterMethod(By.xpath(pageTitleHeaderXpath));
            screenshotLoggerMethod("PageTitleHeader","Correct Page Title Header Displayed");
        }else {
            ExtentTestManager.getTest().warning(MarkupHelper.createLabel("Heading Not Visible:: "+"The page don't show expected heading:",ExtentColor.ORANGE));
        }*/
            return this;}

        public UserAccessVerificationPage clickAndHighlightCreateTeamsTab () throws Exception {
            waitAndClickTab(By.id(createTeamsTabID));
            Thread.sleep(1500);
            highLighterMethod(By.id(createTeamsTabID));
            ExtentTestManager.getTest().pass(MarkupHelper.createLabel("TeamsTab: " + "Create Teams Tab Passed Clicking:", ExtentColor.GREEN));
            screenshotLoggerMethod("PassedTabClick", "Create Teams tab passed the clicking::");

            if (readText(By.xpath(createTeamsHeaderXpath)).equalsIgnoreCase("Create Team")) {
                ExtentTestManager.getTest().info(MarkupHelper.createLabel("Correct Heading Displayed:: " + "The page has correct heading after clicking:", ExtentColor.BLUE));
                highLighterMethod(By.xpath(pageTitleHeaderXpath));
                screenshotLoggerMethod("PageTitleHeader", "Correct Page Title Header Displayed");
            } else {
                ExtentTestManager.getTest().warning(MarkupHelper.createLabel("Heading Not Visible:: " + "The page don't show expected heading:", ExtentColor.ORANGE));
            }
            return this;}

// *********************************************ADDED END2**********************************************************************

    public UserAccessVerificationPage clickAndHighlightPaymentSearchTab() throws Exception
    {
        waitAndClickTab(By.id(paymentSearchTabId));
        highLighterMethod(By.id(paymentSearchTabId));
        ExtentTestManager.getTest().pass(MarkupHelper.createLabel("SearchTab: " + "Search Tab Passed Clicking:", ExtentColor.GREEN));
        screenshotLoggerMethod("PassedTabClick", "Payment search tab passed the clicking::");

        //   checking the page title header
        //if(driver.findElement(By.id(paymentSearchHeardingId)).isDisplayed())
        if (readText(By.xpath(pageTitleHeaderXpath)).equalsIgnoreCase("Payment Search")) {
            ExtentTestManager.getTest().info(MarkupHelper.createLabel("Correct Heading Displayed:: " + "The page has correct heading after clicking:", ExtentColor.BLUE));
            highLighterMethod(By.xpath(pageTitleHeaderXpath));
            screenshotLoggerMethod("PageTitleHeader", "Correct Page Title Header Displayed");
        } else {
            ExtentTestManager.getTest().warning(MarkupHelper.createLabel("Heading Not Visible:: " + "The page don't show expected heading:", ExtentColor.ORANGE));
        }
        return this;}
}