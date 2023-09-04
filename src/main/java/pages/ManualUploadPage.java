package pages;

/**
 * @author DLV Automation Team
 * @date created 10/30/2019
 * @package pages
 */

import com.aventstack.extentreports.Status;
import base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import utils.extentreports.ExtentTestManager;
import utils.logs.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ManualUploadPage extends BasePage {

    public ManualUploadPage(WebDriver driver) {

        super(driver);
        Log.info("The current working class: "+this.getClass().getName());
    }
    //***********************************WEB ELEMENTS*******************************************************************
    //******************************************************************************************************************

    String manualUploadButton = "/html/body/app-root/div/div/div/payment-capture/div/div/div[1]/div[1]/span/button";
    String chooseFileButton = "file";
    String uploadButton = "/html/body/ngb-modal-window/div/div/div[2]/form/div/div/div[3]/button[1]";
    String uploadResult ="//textarea[@name=\"uploadResults\"]";
    String previewButton = "/html/body/ngb-modal-window/div/div/div[2]/form/div/div/div[3]/button[2]";
    String closeModalDialogue ="//*[@aria-label=\"Close\"]";
    String bulkPaymentCaptureTitle = "//span[text()='Bulk Payment Capture']";
    String manualUploadPaymentReviewTitle = "//span[text()='Manual Upload Payment Review']";
    String submitForAuthButton = "/html/body/ngb-modal-window/div/div/div[1]/button";
    String creditorStatusDropdown = "/html/body/app-root/div/div/div/payment-manual-upload-review/div/div/form[2]/div/div[1]/div/div[2]/div[2]/div/div/div/select";
    String fieldsWithErrorCreditorSection = "//input[contains(@class,'show-error-border')]";
    String accountHolderField ="//*[@id=\"accountHolder0\"]";
    String reviewButton="//button[contains(text(),\"Review\")]";
    String submitForAuthButton2="//button[contains(text(),\"Submit for authorisation\")]";

    //******************************************************************************************************************

    //***********************************STEPS**************************************************************************
    //******************************************************************************************************************


    public ManualUploadPage clickOnManualUploadButton() {
        try{

            click(By.xpath(manualUploadButton), By.id(chooseFileButton));
            //SetScriptTimeout();


            Assert.assertTrue((driver.findElement(By.id(chooseFileButton)).isDisplayed()),"Check that the item exists");
            ExtentTestManager.getTest().log(Status.PASS, "Status=PASS:: Navigated to upload screen successfully");

        }catch (Exception e){
            ExtentTestManager.getTest().log(Status.FAIL, "Info:: Navigation to upload screen is failed due to -"+e);
        }
        return this;}

    public ManualUploadPage clickUploadButton() {
        try{
            click(By.xpath(uploadButton), By.id(chooseFileButton));
            Assert.assertTrue((driver.findElement(By.xpath(uploadResult)).isDisplayed()),"Check that the item exists");
            //wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(closeModalDialogue)));
            ExtentTestManager.getTest().log(Status.PASS, "Status=PASS:: File Uploaded successfully");


        }catch (Exception e){
            ExtentTestManager.getTest().log(Status.FAIL, "Info:: File Upload failed due to-"+e);
        }
        return this; }

    public ManualUploadPage  clickReviewButton(){
        try{
            //Thread.sleep(6000);
            ExtentTestManager.getTest().log(Status.INFO, "Inside clickReviewButton");
            click(By.xpath(reviewButton), By.xpath(manualUploadButton));

            //SetScriptTimeout();

            Assert.assertTrue((driver.findElement(By.xpath(manualUploadPaymentReviewTitle)).isDisplayed()),"Check that the item exists");
            ExtentTestManager.getTest().log(Status.PASS, "Status=PASS:: Navigated to review screen successfully");

        }catch (Exception e){
            ExtentTestManager.getTest().log(Status.FAIL, "Info:: Navigation to review screen failed due to-"+e);
        }
        return this; }

    public ManualUploadPage clickPreviewButton(String BankCountry) throws InterruptedException {
        try{
            //Thread.sleep(6000);
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(previewButton)));
            click(By.xpath(previewButton));
            //SetScriptTimeout();
            if(!BankCountry.contains("UGA")) {
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(bulkPaymentCaptureTitle)));
                Assert.assertTrue((driver.findElement(By.xpath(bulkPaymentCaptureTitle)).isDisplayed()), "Check that the item exists");
            }
            else{
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(manualUploadPaymentReviewTitle)));
                Assert.assertTrue((driver.findElement(By.xpath(manualUploadPaymentReviewTitle)).isDisplayed()), "Check that the item exists");
            }
            ExtentTestManager.getTest().log(Status.PASS, "Status=PASS:: Navigated to review screen successfully");
        }catch (Exception e){
            ExtentTestManager.getTest().log(Status.FAIL, "Info:: Navigation to review screen failed due to-"+e);
        }
        return this; }

    public ManualUploadPage clickCloseButton() throws InterruptedException {
        try{
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(submitForAuthButton)));
            click(By.xpath(submitForAuthButton));
            //SetScriptTimeout();
        }catch (Exception e){
            ExtentTestManager.getTest().log(Status.FAIL, "Info:: Navigation to review screen failed due to-"+e);
        }
        return this;}

    public ManualUploadPage clickSubmitForAuthButton() throws InterruptedException {
        try{wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(submitForAuthButton2)));
            click(By.xpath(submitForAuthButton2));
            //SetScriptTimeout();
        }catch (Exception e){
            ExtentTestManager.getTest().log(Status.FAIL, "Info:: Navigation to review screen failed due to-"+e);
        }
        return this;}

    public ManualUploadPage selectFailedOptionFromDropdown() throws InterruptedException {
        try{wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(creditorStatusDropdown)));
            selectdropdownItemByVisibleText(By.xpath(creditorStatusDropdown), "Failed");
            //SetScriptTimeout();
            //Thread.sleep(4000);
            screenshotLoggerMethod("Filtered rows", "Review screen creditors after filtering 'Failed' rows");
        }catch (Exception e){
            ExtentTestManager.getTest().log(Status.FAIL, "Scenario failed due to"+e);
        }
        return this; }

    public ManualUploadPage upfrontScenarioHandling(String ExpectedErrorText_0) throws InterruptedException {
        try{
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(closeModalDialogue)));
            String errorMessage=readAttributeValue(By.xpath(uploadResult));
            screenshotLoggerMethod("Upload Result", "Upload result is displayed");

            //compare with expected message
            if(errorMessage.contentEquals(ExpectedErrorText_0)){
                ExtentTestManager.getTest().log(Status.PASS, "Upload Result is same as expected");
                ExtentTestManager.codeLogsXML("Expected error message is- "+ExpectedErrorText_0
                        +"\nActual error message is- "+errorMessage);
            }
            else{
                ExtentTestManager.getTest().log(Status.FAIL, "Upload Result is not same as expected");
                ExtentTestManager.codeLogsXML("Expected error message is- "+ExpectedErrorText_0
                        +"\nActual error message is- "+errorMessage);
            }
            //close manual upload screen
            clickCloseButton();
        }catch (Exception e){
            ExtentTestManager.getTest().log(Status.FAIL, "Info:: Navigation to review screen failed due to-"+e);
        }
        return this;}

    public ManualUploadPage reviewScreenScenarioHandling(String BankCountry,String DebtorControlSum, String ExpectedNoOfErrors,
                                             String ExpectedErrorField_0,
                                             String ExpectedErrorField_1,
                                             String ExpectedErrorField_2,
                                             String ExpectedErrorFields_3,
                                             String ExpectedErrorField_4,
                                             String ExpectedErrorField_5,
                                             String ExpectedErrorText_0,
                                             String ExpectedErrorText_1,
                                             String ExpectedErrorText_2,
                                             String ExpectedErrorText_3,
                                             String ExpectedErrorText_4,
                                             String ExpectedErrorText_5) throws InterruptedException {
        try{
            if(!BankCountry.contains("UGA")){
                clickPreviewButton(BankCountry);

                screenshotLoggerMethod("Preview screen", "Preview screen debtor section");
                scrollDown();
                scrollDown();
                screenshotLoggerMethod("Preview screen", "Preview screen all creditors");
            }
            else {

                //click on 'Review'
                clickReviewButton();
                //Thread.sleep(5000);

                //enter control sum
                writeText(By.id("controlSum"), DebtorControlSum);
                //Take Screenshot
                screenshotLoggerMethod("Review screen", "Review screen debtor section");
                scrollDown();
                scrollDown();
                screenshotLoggerMethod("Review screen", "Review screen all creditors");

                //select 'Failed' option from dropdown
                selectFailedOptionFromDropdown();
                // Thread.sleep(3000);
                screenshotLoggerMethod("Review screen", "Review screen Failed creditors");

                //temporary mouse hover on account holder
                mouseHoverMethod(accountHolderField);
                //Thread.sleep(3000);
                screenshotLoggerMethod("Account holder mouse hover", "Account holder mouse hover");

                //find all the elements with red border
                List<WebElement> elementWithErrorsList = driver.findElements(By.xpath(fieldsWithErrorCreditorSection));
                int noOfelementWithErrors = elementWithErrorsList.size();
                System.out.println("no of error on page " + noOfelementWithErrors);

                //verify if the result is not zero
                if (noOfelementWithErrors == 0 || Integer.parseInt(ExpectedNoOfErrors) != noOfelementWithErrors) {
                    ExtentTestManager.getTest().log(Status.FAIL, "Errors are not present on UI OR No. of errors present on screen is not same as expected number of errors");
                } else {
                    ExtentTestManager.getTest().log(Status.PASS, "Errors are present on UI");

                    String[] arrayOfExpectedErrorField = {ExpectedErrorField_0,
                            ExpectedErrorField_1,
                            ExpectedErrorField_2,
                            ExpectedErrorFields_3,
                            ExpectedErrorField_4,
                            ExpectedErrorField_5};
                    String[] arrayOfExpectedErrorText = {ExpectedErrorText_0,
                            ExpectedErrorText_1,
                            ExpectedErrorText_2,
                            ExpectedErrorText_3,
                            ExpectedErrorText_4,
                            ExpectedErrorText_5};

                    //convert list into array
                    String[] elementWithErrorsArray = new String[elementWithErrorsList.size()];

                    for (int i = 0; i < elementWithErrorsList.size(); i++) {
                        elementWithErrorsArray[i] = elementWithErrorsList.get(i).getAttribute("id");
                    }
                    System.out.println(Arrays.toString(elementWithErrorsArray));

                    boolean found = false;
                    List<String> elementWithErrorNotFoundList = new ArrayList<String>();
                    ;
                    int j = 0;

                    for (int i = 0; i < noOfelementWithErrors; i++) {
                        found = false;
                        for (j = i; j < noOfelementWithErrors; j++) {
                            if (elementWithErrorsArray[j].contains(arrayOfExpectedErrorField[i])) {
                                ExtentTestManager.getTest().log(Status.PASS, "Expected error on the field '" + arrayOfExpectedErrorField[i] + "' is present on UI");
                                //ExtentTestManager.getTest().log(Status.INFO, "Value in error field -'"+arrayOfExpectedpacs002errorstatus[i]);
                                found = true;
                                break;
                            }
                        }
                        if (found == false) {
                            elementWithErrorNotFoundList.add(arrayOfExpectedErrorField[i]);
                            ExtentTestManager.getTest().log(Status.FAIL, "Expected error on the field '" + arrayOfExpectedErrorField[i] + "' not present on UI");
                        }
                    }
                }
            }
        }catch (Exception e){
            ExtentTestManager.getTest().log(Status.FAIL, "Scenario failed due to"+e);
        }
        return this; }

}