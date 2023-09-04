package pages;

/**
 * @author Awanti Birgade [ ABABA8T ]
 * @date created 10/30/2019
 * @package pages
 */
import com.aventstack.extentreports.Status;
import base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import utils.extentreports.ExtentTestManager;
import utils.logs.Log;

public class TeamPage extends BasePage {
    public TeamPage(WebDriver driver) {

        super(driver);
        Log.info("The current working class: "+this.getClass().getName());
    }
    //***********************************WEB ELEMENTS*******************************************************************
    //******************************************************************************************************************
    String createTeamTab = "create-team";
    String txtNewTeamName = "createteamname-textbox_text";
    String txtUpdateTeamName = "teamname-textbox_text";
    String btnCreateTeam = "createTeam-btn";
    String validationMessage = "//*[@id=\"createteamname-textbox\"]/div/sol-validation/div/p";
    String ddSelectTeamSearchBox = "//*[@id=\"selectTeamDropdown\"]/div/div[2]/span";
    String ddSelectTeamInput = "//*[@id=\"selectTeamDropdown\"]/div/input";
    String firstRecordInDD= "//*[@id=\"selectTeamDropdown\"]/div/ul/li/div/a/div";
    String btnDeleteTeam = "deleteTeam-btn";
    String btnReset = "reset-btn";
    String txtThreshold = "thresholdValue-textbox_text";
    String btnUpdateAll = "updateAll-btn";
    String btnHVLeftSelectAll = "/html/body/app-root/div/div/div/create-team/div/span/div[1]/div/dual-list/div/div[1]/div[2]/button[1]";
    String btnHVRightSelectAll = "/html/body/app-root/div/div/div/create-team/div/span/div[1]/div/dual-list/div/div[2]/div[2]/button[1]";
    String btnLVLeftSelectAll = "/html/body/app-root/div/div/div/create-team/div/span/div[2]/div/dual-list/div/div[1]/div[2]/button[1]";
    String btnLVRightSelectAll = "/html/body/app-root/div/div/div/create-team/div/span/div[2]/div/dual-list/div/div[2]/div[2]/button[1]";
    String btnCAPLeftSelectAll = "/html/body/app-root/div/div/div/create-team/div/span/div[3]/div/dual-list/div/div[1]/div[2]/button[1]";
    String btnCAPRightSelectAll = "/html/body/app-root/div/div/div/create-team/div/span/div[3]/div/dual-list/div/div[2]/div[2]/button[1]";
    String btnHVLeftDeSelectAll = "/html/body/app-root/div/div/div/create-team/div/span/div[1]/div/dual-list/div/div[1]/div[2]/button[2]";
    String btnHVRightDeSelectAll = "/html/body/app-root/div/div/div/create-team/div/span/div[1]/div/dual-list/div/div[2]/div[2]/button[2]";
    String btnLVLeftDeSelectAll = "/html/body/app-root/div/div/div/create-team/div/span/div[2]/div/dual-list/div/div[1]/div[2]/button[2]";
    String btnLVRightDeSelectAll = "/html/body/app-root/div/div/div/create-team/div/span/div[2]/div/dual-list/div/div[2]/div[2]/button[2]";
    String btnCAPLeftDeSelectAll = "/html/body/app-root/div/div/div/create-team/div/span/div[3]/div/dual-list/div/div[1]/div[2]/button[2]";
    String btnCAPRightDeSelectAll = "/html/body/app-root/div/div/div/create-team/div/span/div[3]/div/dual-list/div/div[2]/div[2]/button[2]";
    String btnHVAddToTeam = "/html/body/app-root/div/div/div/create-team/div/span/div[1]/div/dual-list/div/div[1]/button";
    String btnLVAddToTeam = "/html/body/app-root/div/div/div/create-team/div/span/div[2]/div/dual-list/div/div[1]/button";
    String btnCAPAddToTeam = "/html/body/app-root/div/div/div/create-team/div/span/div[3]/div/dual-list/div/div[1]/button";
    String btnHVRemoveFromTeam = "/html/body/app-root/div/div/div/create-team/div/span/div[1]/div/dual-list/div/div[2]/button";
    String btnLVRemoveFromTeam = "/html/body/app-root/div/div/div/create-team/div/span/div[2]/div/dual-list/div/div[2]/button";
    String btnCAPRemoveFromTeam = "/html/body/app-root/div/div/div/create-team/div/span/div[3]/div/dual-list/div/div[2]/button";
    String deleteTeamYes ="/html/body/ngb-modal-window/div/div/div[3]/button[1]";
    String deleteTeamNo ="/html/body/ngb-modal-window/div/div/div[3]/button[2]";


    //public ITestResult result;

    //******************************************************************************************************************
    /***4 Lines***/

    //***********************************STEPS**************************************************************************
    //******************************************************************************************************************
    /**"Click on create teams**/
    public TeamPage clickOnCreateTeams() throws InterruptedException {
        try{
            Thread.sleep(6000);
            click(By.id(createTeamTab));
            SetScriptTimeout();
            Assert.assertTrue((driver.findElement(By.id(btnReset)).isDisplayed()),"Check that the item exists");
            ExtentTestManager.getTest().log(Status.PASS, "Status=PASS:: Navigated to teams page successfully");
        }catch (Exception e){
            ExtentTestManager.getTest().log(Status.FAIL, "Info:: navigation to teams page is failed due to -"+e);
        }
        return this;}

    /**Validate Team Names**/
    public TeamPage teamNameValidation(String TeamNameValidationNeg1,String TeamNameValidationNeg2,String TeamNameValidationNeg3,
                                   String TeamNameValidationNeg4,String TeamNameValidationNeg5,String TeamNameValidationNeg6,
                                   String TeamNameValidationNeg7,String TeamNameValidationNeg8,String TeamNameValidationPos1) throws InterruptedException {
        ExtentTestManager.getTest().log(Status.INFO, "---------------------------------FIELD VALIDATION------------------------------------");
        try{
            //Enter Team name
            writeText(By.id(txtNewTeamName),TeamNameValidationNeg1);
            click(By.id(createTeamTab));
            String validationMessage1 = readText(By.xpath(validationMessage));
            if(validationMessage1.contains("name format invalid, only _ [space] - & alphanumeric allowed")) {
                ExtentTestManager.getTest().log(Status.PASS, "Info:: Team name cannot start with special character. '" + TeamNameValidationNeg1 + "' is not allowed");
            }
            else{
                ExtentTestManager.getTest().log(Status.FAIL, "Info:: Team name should not start with special character. '" + TeamNameValidationNeg1 + "' should not be allowed");
            }
            screenshotLoggerMethod("TeamNameValidationNeg1","TeamNameValidationNeg1");
            click(By.id(btnReset));
            Thread.sleep(1000);


            writeText(By.id(txtNewTeamName),TeamNameValidationNeg2);
            click(By.id(createTeamTab));
            validationMessage1 = readText(By.xpath(validationMessage));
            if(validationMessage1.contains("name format invalid, only _ [space] - & alphanumeric allowed")) {
                ExtentTestManager.getTest().log(Status.PASS, "Info:: Team name cannot end with special character. '" + TeamNameValidationNeg2 + "' is not allowed");
            }
            else{
                ExtentTestManager.getTest().log(Status.FAIL, "Info:: Team name should not end with special character. '" + TeamNameValidationNeg2 + "' should not be allowed");
            }
            screenshotLoggerMethod("TeamNameValidationNeg2","TeamNameValidationNeg2");
            click(By.id(btnReset));
            Thread.sleep(1000);

            writeText(By.id(txtNewTeamName),TeamNameValidationNeg3);
            click(By.id(createTeamTab));
            validationMessage1 = readText(By.xpath(validationMessage));
            if(validationMessage1.contains("name format invalid, only _ [space] - & alphanumeric allowed")) {
                ExtentTestManager.getTest().log(Status.PASS, "Info:: Team name cannot start with space. '" + TeamNameValidationNeg3 + "' is not allowed");
            }
            else{
                ExtentTestManager.getTest().log(Status.FAIL, "Info:: Team name should not start with space. '" + TeamNameValidationNeg3 + "' should not be allowed");
            }
            screenshotLoggerMethod("TeamNameValidationNeg3","TeamNameValidationNeg3");
            click(By.id(btnReset));
            Thread.sleep(1000);

            writeText(By.id(txtNewTeamName),TeamNameValidationNeg4);
            click(By.id(createTeamTab));
            validationMessage1 = readText(By.xpath(validationMessage));
            if(validationMessage1.contains("name format invalid, only _ [space] - & alphanumeric allowed")) {
                ExtentTestManager.getTest().log(Status.PASS, "Info:: Team name cannot end with special character. '" + TeamNameValidationNeg4 + "' is not allowed");
            }
            else{
                ExtentTestManager.getTest().log(Status.FAIL, "Info:: Team name should not end with special character. '" + TeamNameValidationNeg4 + "' should not be allowed");
            }
            screenshotLoggerMethod("TeamNameValidationNeg4","TeamNameValidationNeg4");
            click(By.id(btnReset));
            Thread.sleep(1000);

            writeText(By.id(txtNewTeamName),TeamNameValidationNeg5);
            click(By.id(createTeamTab));
            validationMessage1 = readText(By.xpath(validationMessage));
            if(validationMessage1.contains("name format invalid, only _ [space] - & alphanumeric allowed")) {
                ExtentTestManager.getTest().log(Status.PASS, "Info:: Team name cannot contain characters other than _ [space] - &. '" + TeamNameValidationNeg5 + "' is not allowed");
            }
            else{
                ExtentTestManager.getTest().log(Status.FAIL, "Info:: Team name should not contain characters other than _ [space] - &. '" + TeamNameValidationNeg5 + "' should not be allowed");
            }
            screenshotLoggerMethod("TeamNameValidationNeg5","TeamNameValidationNeg5");
            click(By.id(btnReset));
            Thread.sleep(1000);

            writeText(By.id(txtNewTeamName),TeamNameValidationNeg6);
            click(By.id(createTeamTab));
            validationMessage1 = readText(By.xpath(validationMessage));
            if(validationMessage1.contains("name format invalid, only _ [space] - & alphanumeric allowed")) {
                ExtentTestManager.getTest().log(Status.PASS, "Info:: Team name cannot contain only _ [space] - &. '" + TeamNameValidationNeg6 + "' is not allowed");
            }
            else{
                ExtentTestManager.getTest().log(Status.FAIL, "Info:: Team name should not contain only _ [space] - &: '" + TeamNameValidationNeg6 + "' should not be allowed");
            }
            screenshotLoggerMethod("TeamNameValidationNeg6","TeamNameValidationNeg6");
            click(By.id(btnReset));
            Thread.sleep(1000);

            writeText(By.id(txtNewTeamName),TeamNameValidationNeg7);
            click(By.id(createTeamTab));
            validationMessage1 = readText(By.xpath(validationMessage));
            if(validationMessage1.contains("name format invalid, only _ [space] - & alphanumeric allowed")) {
                ExtentTestManager.getTest().log(Status.PASS, "Info:: Team name cannot contain only 2 characters. '" + TeamNameValidationNeg7 + "' is not allowed");
            }
            else{
                ExtentTestManager.getTest().log(Status.FAIL, "Info:: Team name should not contain only 2 characters. '" + TeamNameValidationNeg7 + "' should not be allowed");
            }
            screenshotLoggerMethod("TeamNameValidationNeg7","TeamNameValidationNeg7");
            click(By.id(btnReset));
            Thread.sleep(1000);

            writeText(By.id(txtNewTeamName),TeamNameValidationNeg8);
            click(By.id(createTeamTab));
            validationMessage1 = readText(By.xpath(validationMessage));
            if(validationMessage1.contains("Validated successfully")) {
                click(By.id(txtNewTeamName));
                ExtentTestManager.getTest().log(Status.PASS, "Info:: Create new team textbox does not accept more than 30 characters. It truncates the extra characters. Data provided was-"+TeamNameValidationNeg8+". Create team text box is displayed as-"+readText(By.id(txtNewTeamName)));
            }
            else{
                ExtentTestManager.getTest().log(Status.FAIL, "Info:: Test case failed");
            }
            screenshotLoggerMethod("TeamNameValidationNeg8","TeamNameValidationNeg8");
            click(By.id(btnReset));
            Thread.sleep(1000);

            writeText(By.id(txtNewTeamName),TeamNameValidationPos1);
            click(By.id(createTeamTab));
            validationMessage1 = readText(By.xpath(validationMessage));
            if(validationMessage1.contains("Validated successfully")) {
                ExtentTestManager.getTest().log(Status.PASS, "Info:: This format is allowed -"+TeamNameValidationPos1);
            }
            else{
                ExtentTestManager.getTest().log(Status.FAIL, "Info:: Test case failed."+TeamNameValidationPos1+" should be accepted");
            }
            screenshotLoggerMethod("TeamNameValidationPos1","TeamNameValidationPos1");
            click(By.id(btnReset));
            Thread.sleep(1000);

        }catch (Exception e){
            ExtentTestManager.getTest().log(Status.FAIL, "Info:: Test case failed due to- "+e);
        }

        ExtentTestManager.getTest().log(Status.INFO, "-----------------------------------------------------------------------------------------");
        return this; }

    /**Click on create teams**/
    public TeamPage createTeam(String TeamName) throws InterruptedException {
        ExtentTestManager.getTest().log(Status.INFO, "-----------------------------------CREATE TEAM--------------------------------------");
        try{
            writeText(By.id(txtNewTeamName),TeamName);
            click(By.id(createTeamTab));
            click(By.id(btnCreateTeam));
            Thread.sleep(2000);
            selectTeam(TeamName);
            screenshotLoggerMethod("Team","Team");
            Assert.assertTrue((driver.findElement(By.id(btnDeleteTeam)).isDisplayed()),"Navigated to team successfully");
            ExtentTestManager.getTest().log(Status.PASS, "Status=PASS:: Navigated to team successfully");
        }catch (Exception e){
            ExtentTestManager.getTest().log(Status.FAIL, "Info:: navigation to teams is failed due to -"+e);
        }
        ExtentTestManager.getTest().log(Status.INFO, "-----------------------------------------------------------------------------------------");
        return this; }

    /**Click on reset team button**/
    public TeamPage clickOnResetButton() throws InterruptedException {

        try{
            Thread.sleep(2000);
            click(By.id(btnReset));
            Thread.sleep(1000);
            Assert.assertTrue((driver.findElement(By.id("createteamname-textbox_text")).getText().isEmpty()),"Screen is reset");
            ExtentTestManager.getTest().log(Status.PASS, "Status=PASS:: Screen is reset successfully");
        }catch (Exception e){
            ExtentTestManager.getTest().log(Status.FAIL, "Info:: Screen reset is failed due to -"+e);
        }
        return this;}

    /**Validate if creating duplicate Team is not allowed**/
    public TeamPage duplicateTeamNameValidation(String DuplicateName1,String DuplicateName2) throws InterruptedException {
        ExtentTestManager.getTest().log(Status.INFO, "-----------------------DUPLICATE TEAM NAME VALIDATION--------------------------");
        try {
            writeText(By.id(txtNewTeamName),DuplicateName1);
            click(By.id(createTeamTab));
            Thread.sleep(1000);
            Assert.assertTrue((readText(By.xpath(validationMessage)).contains("already exists")),"Duplicate is not allowed");
            screenshotLoggerMethod("DuplicateName1","DuplicateName1");
            ExtentTestManager.getTest().log(Status.PASS, "Status=PASS:: Duplicate is not allowed");

            clearTextField(By.id(txtNewTeamName));

            writeText(By.id(txtNewTeamName),DuplicateName2);
            click(By.id(createTeamTab));
            Thread.sleep(1000);
            Assert.assertTrue((readText(By.xpath(validationMessage)).contains("already exists")),"Duplicate is not allowed");
            screenshotLoggerMethod("DuplicateName2","DuplicateName2");
            ExtentTestManager.getTest().log(Status.PASS, "Status=PASS:: Duplicate is not allowed");

        } catch (Exception e) {
            ExtentTestManager.getTest().log(Status.FAIL, "Status=FAIL:: System allows duplicate team names");
        }
        ExtentTestManager.getTest().log(Status.INFO, "-----------------------------------------------------------------------------------------");
        return this;}

    /**Validate if the buttons are working fine**/
    public TeamPage verifyButtons(String Threshold, String TeamName, String EditTeam) throws Exception {
        ExtentTestManager.getTest().log(Status.INFO, "---------------VERIFY IF BUTTONS ARE FUNCTIONING PROPERLY------------------");
        try {
            //---------High value auth section-------------

            //open team
            selectTeam(TeamName);
            Thread.sleep(1000);

            //edit threshold
            clearTextField(By.id(txtThreshold));
            writeText(By.id(txtThreshold),Threshold);
            driver.findElement(By.xpath("/html/body/app-root/div/div/div/create-team/div/span/div[2]/div/label")).click();
            String lableThreshold1 = driver.findElement(By.xpath("/html/body/app-root/div/div/div/create-team/div/span/div[2]/div/label")).getText();
            screenshotLoggerMethod("Threshold","Threshold");
            if(lableThreshold1.contains(Threshold)){
                ExtentTestManager.getTest().log(Status.PASS, "Status=PASS:: Threshold is set correctly");
            }
            else{
                ExtentTestManager.getTest().log(Status.FAIL, "Status=PASS:: Threshold is not set correctly");
            }

            //click select all HV left
            Thread.sleep(2000);
            click(By.xpath(btnHVLeftSelectAll));

            //verify if add to team button is enabled
            if(driver.findElement(By.xpath(btnHVAddToTeam)).isEnabled()){
                ExtentTestManager.getTest().log(Status.PASS, "Status=PASS:: Select All button is working fine in High Value authorizer section");
                screenshotLoggerMethod("Select All Button in High Level Authorizer Left panel","Select All Button in High Level Authorizer Left panel");
            }
            else{
                ExtentTestManager.getTest().log(Status.FAIL, "Status=FAIL:: Select All button is not working in High Value authorizer section");
                screenshotLoggerMethod("Select All Button in High Level Authorizer Left panel","Select All Button in High Level Authorizer Left panel");
            }

            //click deselect all HV left
            Thread.sleep(2000);
            click(By.xpath(btnHVLeftDeSelectAll));

            //verify if add to team HV left is disabled
            if(driver.findElement(By.xpath(btnHVAddToTeam)).isEnabled()){
                ExtentTestManager.getTest().log(Status.FAIL, "Status=FAIL:: DeSelect All button is not working High Value authorizer section");
                screenshotLoggerMethod("Deselect All Button in High Level Authorizer Left panel","Deselect All Button in High Level Authorizer Left panel");
            }
            else{
                ExtentTestManager.getTest().log(Status.PASS, "Status=PASS:: DeSelect All button is working fine High Value authorizer section");
                screenshotLoggerMethod("Deselect All Button in High Level Authorizer Left panel","Deselect All Button in High Level Authorizer Left panel");
            }

            //click select all HV left
            Thread.sleep(2000);
            click(By.xpath(btnHVLeftSelectAll));

            //verify if HV right side is empty
            String HVrightSideUsers = driver.findElement(By.xpath("/html/body/app-root/div/div/div/create-team/div/span/div[1]/div/dual-list/div/div[2]/div[1]/ul")).getText();
            if(HVrightSideUsers.isEmpty()){
                ExtentTestManager.getTest().log(Status.PASS, "Status=PASS:: Users are not present in the High  Level Authorizer's list");
                screenshotLoggerMethod("HVrightSideUsers","HVrightSideUsers");
            }
            else{
                ExtentTestManager.getTest().log(Status.FAIL, "Status=FAIL:: Users are present in High  Level Authorizer's list");
                screenshotLoggerMethod("HVrightSideUsers","HVrightSideUsers");
            }

            //add to team
            Thread.sleep(2000);
            click(By.xpath(btnHVAddToTeam));

            //verify if HV right side is not empty
            HVrightSideUsers = driver.findElement(By.xpath("/html/body/app-root/div/div/div/create-team/div/span/div[1]/div/dual-list/div/div[2]/div[1]/ul")).getText();
            if(HVrightSideUsers.isEmpty()){
                ExtentTestManager.getTest().log(Status.FAIL, "Status=FAIL:: All users are not moved to team"+HVrightSideUsers);
                screenshotLoggerMethod("HVrightSideUsers","HVrightSideUsers");
            }
            else{
                ExtentTestManager.getTest().log(Status.PASS, "Status=PASS:: All users are moved to team");
                screenshotLoggerMethod("HVrightSideUsers","HVrightSideUsers");
            }

            //verify if HV left side is empty
            String HVleftSideUsers = driver.findElement(By.xpath("/html/body/app-root/div/div/div/create-team/div/span/div[1]/div/dual-list/div/div[1]/div[1]/ul")).getText();
            if(HVleftSideUsers.isEmpty()){
                ExtentTestManager.getTest().log(Status.PASS, "Status=PASS:: All users are moved to team");
            }
            else{
                ExtentTestManager.getTest().log(Status.FAIL, "Status=FAIL:: All users are not moved to team");
            }


            //---------------------------------------------
            //---------Low value auth section--------------

            //click select all LV left
            Thread.sleep(2000);
            click(By.xpath(btnLVLeftSelectAll));

            //verify if add to team button is enabled
            if(driver.findElement(By.xpath(btnLVAddToTeam)).isEnabled()){
                ExtentTestManager.getTest().log(Status.PASS, "Status=PASS:: Select All button is working fine in Low Value authorizer section");
                screenshotLoggerMethod("Select All Button in Low Level Authorizer Left panel","Select All Button in Low Level Authorizer Left panel");
            }
            else{
                ExtentTestManager.getTest().log(Status.FAIL, "Status=FAIL:: Select All button is not working in Low Value authorizer section");
                screenshotLoggerMethod("Select All Button in Low Level Authorizer Left panel","Select All Button in Low Level Authorizer Left panel");
            }

            //click deselect all LV left
            Thread.sleep(2000);
            click(By.xpath(btnLVLeftDeSelectAll));

            //verify if add to team LV left is disabled
            if(driver.findElement(By.xpath(btnLVAddToTeam)).isEnabled()){
                ExtentTestManager.getTest().log(Status.FAIL, "Status=FAIL:: DeSelect All button is not working Low Value authorizer section");
                screenshotLoggerMethod("Deselect All Button in Low Level Authorizer Left panel","Deselect All Button in Low Level Authorizer Left panel");
            }
            else{
                ExtentTestManager.getTest().log(Status.PASS, "Status=PASS:: DeSelect All button is working fine Low Value authorizer section");
                screenshotLoggerMethod("Deselect All Button in Low Level Authorizer Left panel","Deselect All Button in Low Level Authorizer Left panel");
            }

            //click select all LV left
            Thread.sleep(2000);
            click(By.xpath(btnLVLeftSelectAll));

            //verify if LV right side is empty
            String LVrightSideUsers = driver.findElement(By.xpath("/html/body/app-root/div/div/div/create-team/div/span/div[2]/div/dual-list/div/div[2]/div[1]/ul")).getText();
            if(LVrightSideUsers.isEmpty()){
                ExtentTestManager.getTest().log(Status.PASS, "Status=PASS:: Users are not present in the Low Level Authorizer's list");
                screenshotLoggerMethod("LVrightSideUsers","LVrightSideUsers");
            }
            else{
                ExtentTestManager.getTest().log(Status.FAIL, "Status=FAIL:: Users are present in Low Level Authorizer's list");
                screenshotLoggerMethod("LVrightSideUsers","LVrightSideUsers");
            }

            //add to team
            Thread.sleep(2000);
            click(By.xpath(btnLVAddToTeam));

            //verify if LV right side is not empty
            LVrightSideUsers = driver.findElement(By.xpath("/html/body/app-root/div/div/div/create-team/div/span/div[2]/div/dual-list/div/div[2]/div[1]/ul")).getText();
            if(LVrightSideUsers.isEmpty()){
                ExtentTestManager.getTest().log(Status.FAIL, "Status=FAIL:: All users are not moved to team");
                screenshotLoggerMethod("LVrightSideUsers","LVrightSideUsers");
            }
            else{
                ExtentTestManager.getTest().log(Status.PASS, "Status=PASS:: All users are moved to team");
                screenshotLoggerMethod("LVrightSideUsers","LVrightSideUsers");
            }

            //verify if LV left side is empty
            String LVleftSideUsers = driver.findElement(By.xpath("/html/body/app-root/div/div/div/create-team/div/span/div[2]/div/dual-list/div/div[1]/div[1]/ul")).getText();
            if(LVleftSideUsers.isEmpty()){
                ExtentTestManager.getTest().log(Status.PASS, "Status=PASS:: All users are moved to team");
            }
            else{
                ExtentTestManager.getTest().log(Status.FAIL, "Status=FAIL:: All users are not moved to team");
            }

            //---------------------------------------------
            //---------Capturer section--------------------

            //click select all CAP left
            Thread.sleep(2000);
            click(By.xpath(btnCAPLeftSelectAll));

            //verify if add to team button is enabled
            if(driver.findElement(By.xpath(btnCAPAddToTeam)).isEnabled()){
                ExtentTestManager.getTest().log(Status.PASS, "Status=PASS:: Select All button is working fine in Capturer section");
                screenshotLoggerMethod("Select All Button in Capturer Left panel","Select All Button in Capturer Left panel");
            }
            else{
                ExtentTestManager.getTest().log(Status.FAIL, "Status=FAIL:: Select All button is not working in Capturer section");
                screenshotLoggerMethod("Select All Button in Capturer Left panel","Select All Button in Capturer Left panel");
            }

            //click deselect all CAP left
            Thread.sleep(2000);
            click(By.xpath(btnCAPLeftDeSelectAll));

            //verify if add to team CAP left is disabled
            if(driver.findElement(By.xpath(btnCAPAddToTeam)).isEnabled()){
                ExtentTestManager.getTest().log(Status.FAIL, "Status=FAIL:: DeSelect All button is not working Capturer section");
                screenshotLoggerMethod("Deselect All Button in Capturer Left panel","Deselect All Button in Capturer Left panel");
            }
            else{
                ExtentTestManager.getTest().log(Status.PASS, "Status=PASS:: DeSelect All button is working fine Capturer section");
                screenshotLoggerMethod("Deselect All Button in Capturer Left panel","Deselect All Button in Capturer Left panel");
            }

            //click select all CAP left
            Thread.sleep(2000);
            click(By.xpath(btnCAPLeftSelectAll));

            //verify if CAP right side is empty
            String CAPrightSideUsers = driver.findElement(By.xpath("/html/body/app-root/div/div/div/create-team/div/span/div[3]/div/dual-list/div/div[2]/div[1]/ul")).getText();
            if(CAPrightSideUsers.isEmpty()){
                ExtentTestManager.getTest().log(Status.PASS, "Status=PASS:: Users are not present in the Capturer's list");
                screenshotLoggerMethod("CAPrightSideUsers","CAPrightSideUsers");
            }
            else{
                ExtentTestManager.getTest().log(Status.FAIL, "Status=FAIL:: Users are present in Capturer's list");
                screenshotLoggerMethod("CAPrightSideUsers","CAPrightSideUsers");
            }

            //add to team
            Thread.sleep(2000);
            click(By.xpath(btnCAPAddToTeam));

            //verify if CAP right side is not empty
            CAPrightSideUsers = driver.findElement(By.xpath("/html/body/app-root/div/div/div/create-team/div/span/div[3]/div/dual-list/div/div[2]/div[1]/ul")).getText();
            if(CAPrightSideUsers.isEmpty()){
                ExtentTestManager.getTest().log(Status.FAIL, "Status=FAIL:: All users are not moved to team");
                screenshotLoggerMethod("CAPrightSideUsers","CAPrightSideUsers");
            }
            else{
                ExtentTestManager.getTest().log(Status.PASS, "Status=PASS:: All users are moved to team");
                screenshotLoggerMethod("CAPrightSideUsers","CAPrightSideUsers");
            }

            //verify if CAP left side is empty
            String CAPleftSideUsers = driver.findElement(By.xpath("/html/body/app-root/div/div/div/create-team/div/span/div[3]/div/dual-list/div/div[1]/div[1]/ul")).getText();
            if(CAPleftSideUsers.isEmpty()){
                ExtentTestManager.getTest().log(Status.PASS, "Status=PASS:: All users are moved to team");
            }
            else{
                ExtentTestManager.getTest().log(Status.FAIL, "Status=FAIL:: All users are not moved to team");
            }
            ExtentTestManager.getTest().log(Status.INFO, "-----------------------EDIT TEAM NAME & REMOVE ALL USERS------------------------");
            //add code for editing team name
            clearTextField(By.id(txtUpdateTeamName));
            writeText(By.id(txtUpdateTeamName),EditTeam);
            click(By.id(createTeamTab));

            //Click Update All button
            Thread.sleep(2000);
            click(By.id(btnUpdateAll));

            //reset for no reason
            clickOnResetButton();

            //open team
            selectTeam(EditTeam);

            //--------------------------------------------------
            //------------------HV auth right side--------------

            //click select all HV right left
            Thread.sleep(2000);
            click(By.xpath(btnHVRightSelectAll));

            //verify if remove from team button is enabled
            if (driver.findElement(By.xpath(btnHVRemoveFromTeam)).isEnabled()) {
                ExtentTestManager.getTest().log(Status.PASS, "Status=PASS:: Select All button is working fine in HV auth section");
                screenshotLoggerMethod("btnHVRemoveFromTeam","btnHVRemoveFromTeam");
            } else {
                ExtentTestManager.getTest().log(Status.FAIL, "Status=FAIL:: Select All button is not working in HV auth section");
                screenshotLoggerMethod("btnHVRemoveFromTeam","btnHVRemoveFromTeam");
            }

            //click deselect all HV right
            Thread.sleep(2000);
            click(By.xpath(btnHVRightDeSelectAll));

            //verify if remove from team HV right is disabled
            if (driver.findElement(By.xpath(btnHVRemoveFromTeam)).isEnabled()) {
                ExtentTestManager.getTest().log(Status.FAIL, "Status=FAIL:: DeSelect All button is not working HV right section");
                screenshotLoggerMethod("btnHVRemoveFromTeam","btnHVRemoveFromTeam");
            } else {
                ExtentTestManager.getTest().log(Status.PASS, "Status=PASS:: DeSelect All button is working fine HV right section");
                screenshotLoggerMethod("btnHVRemoveFromTeam","btnHVRemoveFromTeam");
            }

            //click select all HV Right
            Thread.sleep(2000);
            click(By.xpath(btnHVRightSelectAll));

            //verify if HV left side is empty
            String HVLeftSideUsers = driver.findElement(By.xpath("/html/body/app-root/div/div/div/create-team/div/span/div[1]/div/dual-list/div/div[1]/div[1]/ul")).getText();
            if (HVLeftSideUsers.isEmpty()) {
                ExtentTestManager.getTest().log(Status.PASS, "Status=PASS:: Users are not present in the High value auth available users' list");
                screenshotLoggerMethod("HVLeftSideUsers","HVLeftSideUsers");
            } else {
                ExtentTestManager.getTest().log(Status.FAIL, "Status=FAIL:: Users are present in High value auth available users' list");
                screenshotLoggerMethod("HVLeftSideUsers","HVLeftSideUsers");
            }

            //remove from team
            Thread.sleep(2000);
            click(By.xpath(btnHVRemoveFromTeam));

            //verify if HV left side is not empty
            HVLeftSideUsers = driver.findElement(By.xpath("/html/body/app-root/div/div/div/create-team/div/span/div[1]/div/dual-list/div/div[1]/div[1]/ul")).getText();
            if (HVLeftSideUsers.isEmpty()) {
                ExtentTestManager.getTest().log(Status.FAIL, "Status=FAIL:: All users are not removed from team");
                screenshotLoggerMethod("HVLeftSideUsers","HVLeftSideUsers");
            } else {
                ExtentTestManager.getTest().log(Status.PASS, "Status=PASS:: All users are removed from team");
                screenshotLoggerMethod("HVLeftSideUsers","HVLeftSideUsers");
            }

            //verify if HV right side is empty
            String HVRightSideUsers = driver.findElement(By.xpath("/html/body/app-root/div/div/div/create-team/div/span/div[1]/div/dual-list/div/div[2]/div[1]/ul")).getText();
            if (HVRightSideUsers.isEmpty()) {
                ExtentTestManager.getTest().log(Status.PASS, "Status=PASS:: All users are removed from team");
            } else {
                ExtentTestManager.getTest().log(Status.FAIL, "Status=FAIL:: All users are not removed from team");
            }

            //------------------------------------------------
            //---------------Low value auth-------------------

            //click select all LV right left
            Thread.sleep(2000);
            click(By.xpath(btnLVRightSelectAll));

            //verify if remove from team button is enabled
            if (driver.findElement(By.xpath(btnLVRemoveFromTeam)).isEnabled()) {
                ExtentTestManager.getTest().log(Status.PASS, "Status=PASS:: Select All button is working fine in LV auth section");
                screenshotLoggerMethod("btnLVRemoveFromTeam","btnLVRemoveFromTeam");
            } else {
                ExtentTestManager.getTest().log(Status.FAIL, "Status=FAIL:: Select All button is not working in LV auth section");
                screenshotLoggerMethod("btnLVRemoveFromTeam","btnLVRemoveFromTeam");
            }

            //click deselect all LV right
            Thread.sleep(2000);
            click(By.xpath(btnLVRightDeSelectAll));

            //verify if remove from team LV right is disabled
            if (driver.findElement(By.xpath(btnLVRemoveFromTeam)).isEnabled()) {
                ExtentTestManager.getTest().log(Status.FAIL, "Status=FAIL:: DeSelect All button is not working LV right section");
                screenshotLoggerMethod("btnLVRemoveFromTeam","btnLVRemoveFromTeam");
            } else {
                ExtentTestManager.getTest().log(Status.PASS, "Status=PASS:: DeSelect All button is working fine LV right section");
                screenshotLoggerMethod("btnLVRemoveFromTeam","btnLVRemoveFromTeam");
            }

            //click select all HV Right
            Thread.sleep(2000);
            click(By.xpath(btnLVRightSelectAll));

            //verify if LV left side is empty
            String LVLeftSideUsers = driver.findElement(By.xpath("/html/body/app-root/div/div/div/create-team/div/span/div[2]/div/dual-list/div/div[1]/div[1]/ul")).getText();
            if (LVLeftSideUsers.isEmpty()) {
                ExtentTestManager.getTest().log(Status.PASS, "Status=PASS:: Users are not present in the Low value auth available users' list");
                screenshotLoggerMethod("LVLeftSideUsers","LVLeftSideUsers");
            } else {
                ExtentTestManager.getTest().log(Status.FAIL, "Status=FAIL:: Users are present in Low value auth available users' list");
                screenshotLoggerMethod("LVLeftSideUsers","LVLeftSideUsers");
            }

            //remove from team
            Thread.sleep(2000);
            click(By.xpath(btnLVRemoveFromTeam));

            //verify if LV left side is not empty
            LVLeftSideUsers = driver.findElement(By.xpath("/html/body/app-root/div/div/div/create-team/div/span/div[2]/div/dual-list/div/div[1]/div[1]/ul")).getText();
            if (LVLeftSideUsers.isEmpty()) {
                ExtentTestManager.getTest().log(Status.FAIL, "Status=FAIL:: All users are not removed from team");
                screenshotLoggerMethod("LVLeftSideUsers","LVLeftSideUsers");
            } else {
                ExtentTestManager.getTest().log(Status.PASS, "Status=PASS:: All users are removed from team");
                screenshotLoggerMethod("LVLeftSideUsers","LVLeftSideUsers");
            }

            //verify if LV right side is empty
            String LVRightSideUsers = driver.findElement(By.xpath("/html/body/app-root/div/div/div/create-team/div/span/div[2]/div/dual-list/div/div[2]/div[1]/ul")).getText();
            if (LVRightSideUsers.isEmpty()) {
                ExtentTestManager.getTest().log(Status.PASS, "Status=PASS:: All users are removed from team");
            } else {
                ExtentTestManager.getTest().log(Status.FAIL, "Status=FAIL:: All users are not removed from team");
            }

            //------------------------------------------------
            //---------------Capturer Right section-------------------

            //click select all CAP right left
            Thread.sleep(2000);
            click(By.xpath(btnCAPRightSelectAll));

            //verify if remove from team button is enabled
            if (driver.findElement(By.xpath(btnCAPRemoveFromTeam)).isEnabled()) {
                ExtentTestManager.getTest().log(Status.PASS, "Status=PASS:: Select All button is working fine in CAP right section");
                screenshotLoggerMethod("btnCAPRemoveFromTeam","btnCAPRemoveFromTeam");
            } else {
                ExtentTestManager.getTest().log(Status.FAIL, "Status=FAIL:: Select All button is not working in CAP right section");
                screenshotLoggerMethod("btnCAPRemoveFromTeam","btnCAPRemoveFromTeam");
            }

            //click deselect all CAP right
            Thread.sleep(2000);
            click(By.xpath(btnCAPRightDeSelectAll));

            //verify if remove from team CAP right is disabled
            if (driver.findElement(By.xpath(btnCAPRemoveFromTeam)).isEnabled()) {
                ExtentTestManager.getTest().log(Status.FAIL, "Status=FAIL:: DeSelect All button is not working CAP right section");
                screenshotLoggerMethod("btnCAPRemoveFromTeam","btnCAPRemoveFromTeam");
            } else {
                ExtentTestManager.getTest().log(Status.PASS, "Status=PASS:: DeSelect All button is working fine CAP right section");
                screenshotLoggerMethod("btnCAPRemoveFromTeam","btnCAPRemoveFromTeam");
            }

            //click select all CAP Right
            Thread.sleep(2000);
            click(By.xpath(btnCAPRightSelectAll));

            //verify if CAP left side is empty
            String CAPLeftSideUsers = driver.findElement(By.xpath("/html/body/app-root/div/div/div/create-team/div/span/div[3]/div/dual-list/div/div[1]/div[1]/ul")).getText();
            if (CAPLeftSideUsers.isEmpty()) {
                ExtentTestManager.getTest().log(Status.PASS, "Status=PASS:: Users are not present in the CAP available users' list");
                screenshotLoggerMethod("CAPLeftSideUsers","CAPLeftSideUsers");
            } else {
                ExtentTestManager.getTest().log(Status.FAIL, "Status=FAIL:: Users are present in CAP available users' list");
                screenshotLoggerMethod("CAPLeftSideUsers","CAPLeftSideUsers");
            }

            //remove from team
            Thread.sleep(2000);
            click(By.xpath(btnCAPRemoveFromTeam));

            //verify if CAP left side is not empty
            CAPLeftSideUsers = driver.findElement(By.xpath("/html/body/app-root/div/div/div/create-team/div/span/div[3]/div/dual-list/div/div[1]/div[1]/ul")).getText();
            if (CAPLeftSideUsers.isEmpty()) {
                ExtentTestManager.getTest().log(Status.FAIL, "Status=FAIL:: All users are not removed from team");
                screenshotLoggerMethod("CAPLeftSideUsers","CAPLeftSideUsers");
            } else {
                ExtentTestManager.getTest().log(Status.PASS, "Status=PASS:: All users are removed from team");
                screenshotLoggerMethod("CAPLeftSideUsers","CAPLeftSideUsers");
            }

            //verify if CAP right side is empty
            String CAPRightSideUsers = driver.findElement(By.xpath("/html/body/app-root/div/div/div/create-team/div/span/div[3]/div/dual-list/div/div[2]/div[1]/ul")).getText();
            if (CAPRightSideUsers.isEmpty()) {
                ExtentTestManager.getTest().log(Status.PASS, "Status=PASS:: All users are removed from team");
            } else {
                ExtentTestManager.getTest().log(Status.FAIL, "Status=FAIL:: All users are not removed from team");
            }

            //Click Update All button
            Thread.sleep(2000);
            click(By.id(btnUpdateAll));

            ExtentTestManager.getTest().log(Status.INFO, "----------------ERROR MESSAGE WHEN USERS ARE NOT ADDED IN TEAM----------------");

            //read toast message on screen
            String errorMsgForMandatoryUsers = driver.findElement(By.className("toast-message")).getText();
            if(errorMsgForMandatoryUsers.contains("Please make sure you have at least ONE user in each of the user categories below")){
                ExtentTestManager.getTest().log(Status.PASS, "Status=PASS:: Message is displayed for mandatory users in each section- "+errorMsgForMandatoryUsers);
                screenshotLoggerMethod("errorMsgForMandatoryUsers","errorMsgForMandatoryUsers");
            }
            else{
                ExtentTestManager.getTest().log(Status.FAIL, "Status=FAIL:: Mandatory user message is not displayed");
                screenshotLoggerMethod("errorMsgForMandatoryUsers","errorMsgForMandatoryUsers");
            }
            Thread.sleep(6000);
        }
        catch (Exception e) {
            ExtentTestManager.getTest().log(Status.FAIL, "Status=FAIL:: Buttons are not verified"+e);
        }
        ExtentTestManager.getTest().log(Status.INFO, "-----------------------------------------------------------------------------------");
        return this;}

    //Delete Team
    /**Delete teams**/
    public TeamPage deleteTeam(String EditTeam) throws InterruptedException {
        ExtentTestManager.getTest().log(Status.INFO, "--------------------------------DELETE TEAM-----------------------------------");
        try{
            selectTeam(EditTeam);
            Thread.sleep(1000);
            click(By.id(btnDeleteTeam));
            screenshotLoggerMethod("btnDeleteTeam","btnDeleteTeam");
            click(By.xpath(deleteTeamYes));
            ExtentTestManager.getTest().log(Status.PASS, "Status=PASS:: Team is deleted");
        }catch (Exception e){
            ExtentTestManager.getTest().log(Status.FAIL, "Info:: team is not deleted due to -"+e);
        }
        ExtentTestManager.getTest().log(Status.INFO, "-----------------------------------------------------------------------------------");
        return this; }

    //select team
    /**"click on teams**/
    public TeamPage selectTeam(String TeamName) throws InterruptedException {
        try{
            click(By.xpath(ddSelectTeamSearchBox));
            writeText(By.xpath(ddSelectTeamInput), TeamName);
            click(By.xpath(firstRecordInDD));
            ExtentTestManager.getTest().log(Status.PASS, "Status=PASS:: clicked");
        }catch (Exception e){
            ExtentTestManager.getTest().log(Status.FAIL, "Info:: not clicked -"+e);
        }
        return this;}

}