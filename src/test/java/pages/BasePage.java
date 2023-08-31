package pages;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import config.Settings;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.Coordinates;
import org.openqa.selenium.interactions.Locatable;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import utils.extentreports.ExtentTestManager;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.net.MalformedURLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import static org.testng.Assert.fail;

public class BasePage {
    public WebDriver driver;
    public WebDriverWait wait;

    //Constructor
    public BasePage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    //Click Method
//    public void click(By by) {
//        waitVisibility(by).click();
//    }
//
//    //Write Text
//    public void writeText(By by, String text) {
//        waitVisibility(by).sendKeys(text);
//    }
//
//    //Read Text
//    public String readText(By by) {
//        return waitVisibility(by).getText();
//    }

    //Wait
    public WebElement waitVisibility(By by) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(by));
    }

    //Old

        String modalStyleForUpload = "//*[@id='progress-indicator']";

//*************************GENERIC METHODS FOR DOING COMMON ACTIONS ****************************************************

        /**
         * Check if Browser is Open/Alive
         */
        public Boolean isAlive() {
            try {
                driver.getCurrentUrl();//or driver.getTitle();
                return true;
            } catch (Exception ex) {
                return false;
            }
        }

        /**
         * Method to start the application under test
         */
//        public void startTheApplication() throws InterruptedException, MalformedURLException {
//            //Check the status of the browser
//            if (isAlive().equals(true)) {
//                //The Browser session is Open/Alive(Browser was open programmatically)
//                System.out.println("Info: The browser is open");
//
//                //Get the Url and pass it into the browser
//                driver.get(Settings.AUT);
//
//            } else if (isAlive().equals(false)) {
//                //The Browser session is not Open/Alive
//                ExtentTestManager.getTest().info(MarkupHelper.createLabel("Info: The browser was not found to be open ,when this test iteration started", ExtentColor.BLUE));
//                Settings.Logs.Write("Info: The browser was not found to be open ,when this test iteration started");
//                System.out.println("Info: The browser was not found to be open ,when this test iteration started");
//
//                //Start and Initialize the browser
//                InitializeBrowser(Settings.BrowserType);
//            }
//        }

        /**
         * Refresh the browser
         */
        public void refreshBrowser() {
            driver.navigate().refresh();
        }

        public void closeTheBrowser() {
            driver.quit();
        }

        //Get Page Title
        public String getPageTitle() {
            return driver.getTitle();
        }

        //Click Method
        public void click(By elementLocation, By clickElement) {
            int count = 0;
            long startTime = System.currentTimeMillis(); //fetch starting time
            while(true)
            {
                if (waitForElementToBeVisible(elementLocation))
                    try {
                        WebElement findElem = driver.findElement(elementLocation);
                        findElem.click();
                        break;
                    }
                    catch (NoSuchElementException e) {
                        driver.navigate().refresh();
                    }
                count+=1;
                if (count == 3) {break;};
            }
            waitForElementToBeDone(clickElement);




        }





        //Write Text
        public void writeText (By elementLocation, String text)
        {   waitForElementToBeVisible(elementLocation);
            driver.findElement(elementLocation).sendKeys(text);
        }

        //Read Text
        public String readText (By elementLocation) {
            waitForElementToBeVisible(elementLocation);
            return driver.findElement(elementLocation).getText();
        }
        //Select item dropdown
        public void selectItemDropdown (By elementLocation, String visibleText)

        {   //Declare webElement to select from dropdown
            waitForElementToBeVisible(elementLocation);
            WebElement ele = driver.findElement(elementLocation);
            Select dropDown = new Select(ele) ;
            dropDown .selectByVisibleText(visibleText);
        }
        //Read Attributes Title
        public String readAttribute (By elementLocation) {
            waitForElementToBeVisible(elementLocation);
            return driver.findElement(elementLocation).getAttribute("title");
        }

        //Read Attribute value
        public String readAttributeValue (By elementLocation) {
            waitForElementToBeVisible(elementLocation);
            return driver.findElement(elementLocation).getAttribute("value");
        }

        //Clear Method
        public void clearTextField (By elementLocation) {
            waitForElementToBeVisible(elementLocation);
            driver.findElement(elementLocation).clear();
        }

        public void mouseHoverMethod(String strElementXpathString) throws InterruptedException {

            //Declare webElement to hover over
            WebElement ele = driver.findElement(By.xpath(strElementXpathString));

            //Using actions class to do mouse hover
            Actions action = new Actions(driver);
            action.moveToElement(ele).build().perform();
            //////Thread.sleep(2000);
        }

        public void switchWindows()
        {
            /** Purpose:
             * This java method's part switches to the newest window. Also consider maximizing it, because sometimes tests
             * masses up in not maximized windows. Another point: waiting. Try to read here about different types of wait*/
            String winHandleBefore = driver.getWindowHandle();
            for(String winHandle : driver.getWindowHandles())
            {
                driver.switchTo().window(winHandle);
            }
        }

        /**Used to scroll the browser to the Top */
        public void scrollUp()
        {
            /**if the element is on Top.
             *Scroll vertically Up by 1000 pixels*/
            JavascriptExecutor jse = (JavascriptExecutor)driver;
            jse.executeScript("window.scrollBy(0,-1000)");
        }

        /**Used to scroll the browser to the Bottom */
        public void scrollDown()
        {
            /**if the element is on bottom.
             *Scroll vertically down by 1000 pixels*/
            JavascriptExecutor jse = (JavascriptExecutor)driver;
            jse.executeScript("window.scrollBy(0,1000)");
        }

        /**Used to scroll the browser to an Element Location */
        public void scrollElementIntoView(By elementLocation)
        {   waitForElementToBeVisible(elementLocation);
            JavascriptExecutor je = (JavascriptExecutor) driver;

            //Identify the WebElement which will appear after scrolling
            WebElement element = driver.findElement(elementLocation);

            // now execute query which actually will scroll until that element is appeared on page.
            je.executeScript("arguments[0].scrollIntoView(true);",element);
        }

        /**scroll Element Into View By Coordinates*/
        public void scrollElementIntoViewByCoordinates(By elementLocation) throws InterruptedException
        {   waitForElementToBeVisible(elementLocation);
            WebElement element = driver.findElement(elementLocation);
            Coordinates cor=((Locatable)element).getCoordinates();
            cor.inViewPort();
            //////Thread.sleep(8000);
        }

        /**Move To Focus On Element And Click*/
        public void moveToFocusOnElementAndClick(By elementLocation)
        {   waitForElementToBeVisible(elementLocation);
            Actions builder = new Actions(driver);
            WebElement element = driver.findElement(elementLocation);
            builder.moveToElement(element).build().perform();
            builder.moveToElement(element).click().perform();
            waitForElementToBeDone(elementLocation);
        }

        /**Create a reusable highlight the area*/
        //Creating a custom function
        public void highLighterMethod(By elementLocation)
        {   waitForElementToBeVisible(elementLocation);
            JavascriptExecutor jse = (JavascriptExecutor) driver;

            //Identify the WebElement
            WebElement element = driver.findElement(elementLocation);

            // now execute query
            jse.executeScript("arguments[0].setAttribute('style', 'background: orange; border: 2px solid red;');", element);
        }
        //*************************Used to click tab key********************************************************************
        public void clickTabKeyAfterElement (By elementAtFocus, By emptyPageSpaceXpath) {   waitForElementToBeVisible(elementAtFocus);
            Actions actions = new Actions(driver);
            actions.click((driver.findElement(elementAtFocus))).sendKeys(Keys.TAB).build().perform();
            driver.findElement((emptyPageSpaceXpath)).click();
            //////Thread.sleep(3000);
        }
        //***********************************Used to issue WAITS commands **************************************************
        public void SetScriptTimeout()
        {
            /** Purpose:
             * Sets the amount of time to wait for an asynchronous script to finish
             * execution before throwing an error.If the timeout is negative, then the
             * script will be allowed to run indefinitely
             *
             * Now since executeAsyncScript method doesn't block the execution of next line of code, it might be beneficial
             * to use driver.manage().timeouts().setScriptTimeout(30,SECONDS);so that our code can wait for specified amount
             * of time for an asynchronous script to finish execution before throwing an error.
             *
             * USE THIS instead of the Thread.Sleep
             * */
            driver.manage().timeouts().scriptTimeout(Duration.ofMinutes(1));
        }

        public void waitForElementToBeClickable(By elementLocation)
        {
            /** Purpose:
             *Explicit wait ;We can tell the tool to wait only till the Condition satisfy.*/
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(120));
            WebElement element = wait.until(ExpectedConditions.elementToBeClickable(elementLocation));
        }


        public boolean waitForElementToBeVisible(By elementLocation)
        {
            /** Purpose:
             *Explicit wait ;We can tell the tool to wait only till the Condition satisfy.*/
            int count =1000;

            long startTime = System.currentTimeMillis(); //fetch starting time
            while((System.currentTimeMillis() - startTime) < 300 * count)
            {

                if (isElementDisplayed(elementLocation)) {
                   // System.out.println(printElementLocationText(elementLocation, true ));
                    break;

                }

            }


            return true;

        }

        public void waitForElementToBeDone( By clickElement)
        {
            By elementLocation = By.xpath(modalStyleForUpload);
            /** Purpose:
             *Explicit wait ;We can tell the tool to wait only till the Condition satisfy.*/
            int count =1000;
            long startTime = System.currentTimeMillis(); //fetch starting time

            while ((System.currentTimeMillis() - startTime) < 300 * count) {

//                if (!isElementDisplayed(elementLocation)) {
//
//
//                        System.out.println("STYLE: "+printElementLocationText(elementLocation, true));
//                        break;
//                    }
                if (isEnabled(clickElement)) {


                    //System.out.println("ENABLED: "+printElementLocationText(elementLocation, true));
                    break;
                }
            }

        }

//    public void waitForElementToBeVisible(By elementLocation)
//    {
//        /** Purpose:
//         *Explicit wait ;We can tell the tool to wait only till the Condition satisfy.*/
//        WebDriverWait wait = new WebDriverWait(driver, 120);
//        //WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.id("someid")));
//        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(elementLocation));
//    }

        public void waitForPageToBeReady_2()
        {   /** Purpose:
         *This loop will rotate for 100 times to check If page Is ready after every 1 second.
         *You can modify it if you wants to Increase or decrease wait time.*/
            JavascriptExecutor js = (JavascriptExecutor)driver;
            for (int i=0; i<400; i++)
            {
                try
                {
                    Thread.sleep(10);
                }catch (InterruptedException e) {}
                //To check page ready state.
                if (js.executeScript("return document.readyState").toString().equals("complete"))
                {
                    break;
                }
            }
        }

        public void waitForPageToBeReadyState_1()
        {
            JavascriptExecutor jse = (JavascriptExecutor)driver;
            jse.executeScript("return document.readyState").equals("complete");
        }

        public void waitAndClickTab(By tabElement)  {
            waitForElementToBeVisible(tabElement);
            try {

                click(tabElement, By.xpath("//*[@id='welcome']"));

            } catch (Exception e) {
                System.out.println(":::Tab Element is not visible or not clickable:::");
                ExtentTestManager.getTest().warning(MarkupHelper.createLabel("TabNotFoundForUserRole::  " + "::: Some tab(s) for this user are not found or User Role has changed:::", ExtentColor.ORANGE));
            }
        }

        public void waitForPageToBeInAnExpectedConditions()
        {
            WebDriverWait wait = new WebDriverWait(driver,Duration.ofMinutes(2));
            wait.until(ExpectedConditions.jsReturnsValue("return document.readyState==\"complete\";"));
        }

        public void ImplicitWait()
        {
            /** Purpose:
             *Implicit wait is to tell webdriver to wait for a certain amount of time when trying
             * to find an element or elements if they are not immediately available*/
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        }

//    public void fluentWait()
//    {
//        /** Purpose:
//         *Waiting 30 seconds for an element to be present on the page, checking
//         *for its presence once every 5 seconds..*/
//        Wait wait = new FluentWait(driver)
//                .withTimeout(30, SECONDS)
//                .pollingEvery(5, SECONDS)
//                .ignoring(NoSuchElementException.class);
//    }

        public void pageLoadTimeout ()
        {
            /** Purpose:
             * Sets the amount of time to wait for a page load to complete before
             * throwing an error. If the timeout is negative, page loads can be indefinite.*/
            driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
        }

        // *****************************THESE ARE METHODS TO ASSERTIONS *******************************************************
        /***Private method that simply looks for the element and determines if it is Present like this:*/
        public boolean isElementDisplayed(By elementLocator)
        {
            WebDriverWait wait = new WebDriverWait(driver,Duration.ofMillis(200));
            try
            {
                /**ExpectedConditions.visibilityOfElementLocated - it ensures that:
                 * a) element is present in DOM
                 b) element is visible
                 ExpectedConditions.visibilityOf it doesn't check that element is present in DOM.*/


                return  wait.until(ExpectedConditions.visibilityOfElementLocated(elementLocator)).isDisplayed();

            } catch (TimeoutException eTO)
            {return false;
            }

        }


        public boolean isDisplayed(String strXpath) {
            boolean isElementDisplayed = false;
            try {
                /**ExpectedConditions.visibilityOfElementLocated - it ensures that:
                 * a) element is present in DOM
                 b) element is visible
                 ExpectedConditions.visibilityOf it doesn't check that element is present in DOM.*/
                driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
                isElementDisplayed = true;
                if (isElementDisplayed == false) {
                    ExtentTestManager.getTest().info(MarkupHelper.createLabel("Element not present", ExtentColor.BLUE));
                }
            } catch (TimeoutException eTO) {
                isElementDisplayed = false;
            } finally {
                driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
            }
            return isElementDisplayed;
        }


        public boolean isEnabled(By elementLocation) {
            WebDriverWait wait = new WebDriverWait(driver,Duration.ofMillis(500));
            try {


                /**ExpectedConditions.visibilityOfElementLocated - it ensures that:
                 * a) element is present in DOM
                 b) element is visible
                 ExpectedConditions.visibilityOf it doesn't check that element is present in DOM.*/
                return  wait.until(ExpectedConditions.elementToBeClickable(elementLocation)).isEnabled();
            }
            catch(TimeoutException e)
            {

                ExtentTestManager.getTest().info(MarkupHelper.createLabel("Element not present", ExtentColor.BLUE));
                return false;
            }



        }

        public boolean isShowing(By elementLocator)
        {

            try
            {
                /**ExpectedConditions.visibilityOfElementLocated - it ensures that:
                 * a) element is present in DOM
                 b) element is visible
                 ExpectedConditions.visibilityOf it doesn't check that element is present in DOM.*/


                return driver.findElement(elementLocator).isDisplayed();

            } catch (org.openqa.selenium.NoSuchElementException e)
            {return false;
            }

        }


        /***Private method that simply looks for the element and determines if it is Enabled like this:*/
        public boolean isElementEnabled(String strXpath)
        {
            return driver.findElement(By.xpath(strXpath)).isEnabled();
        }


        /***Private method that checks if a certain element is found / present without waiting longer:*/
        public boolean ElementIsPresent(By elementLocation)
        {
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            try
            {
                WebElement element = driver.findElement(elementLocation);
                System.out.println("Element "+ element +" is shown");
                return true;
            }catch(NoSuchElementException e)
            {
                return false;
            }
            finally {
                driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
            }
        }


        /***Method that simply check if a textbox is Enabled:*/
        public void isReadOnly(By elementLocation)
        {
            //Check if the Textbox field is Enabled
            if(!driver.findElement(elementLocation).isEnabled())
            {
                System.out.println("field is disabled");
            }
        }



        /***Private method that simply looks for the element and determines if it is Selected like this:*/
        public boolean isElementSelected(String strXpath)
        {
            boolean Selected = driver.findElement(By.xpath(strXpath)).isSelected();
            return Selected;
        }

        public boolean isElementPresent(By elementLocation)
        {waitForElementToBeVisible(elementLocation);
            try
            {
                WebElement element = driver.findElement(elementLocation);
                WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(10));
                wait.until(ExpectedConditions.visibilityOf(element));
                return true;
            }catch(Exception e)
            {
                return false;
            }
        }

        public boolean isAlertPresent(){
            boolean foundAlert = false;
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            try
            {
                wait.until(ExpectedConditions.alertIsPresent());
                foundAlert = true;
            } catch (TimeoutException ignored)
            {
            }
            return foundAlert;
        }

        public void isAlertPresent2() throws Exception {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            if(wait.until(ExpectedConditions.alertIsPresent())==null)
            {
                System.out.println("alert was not present");
            }else
            {
                System.out.println("alert was present");
                ExtentTestManager.getTest().fail(MarkupHelper.createLabel("PPO error occurred,please check PopUp", ExtentColor.RED));
                screenshotLoggerMethod("PopUp screenshot","PPO error occurred,please check PopUp");
                stopCurrentTestSetResultsToFailedAndContinueWithNextTest();
            }
        }

        public boolean isDialogPresent(WebDriver driver) {
            try {
                driver.getTitle();
                return false;
            } catch (UnhandledAlertException e) {
                // Modal dialog showed
                return true;
            }
        }

        public boolean isAttributePresent(By elementLocation,String strAttributeName){

            waitForElementToBeVisible(elementLocation);
            try{

                boolean checkExistenceOfAttribute = driver.findElement(elementLocation).getAttribute(strAttributeName).isEmpty();//Check if the attribute name is not present(isEmpty)
                return false;//Attribute is present
            }
            catch (UnhandledAlertException e)
            {
                return true;//Attribute is not present
            }
        }

        // **********************************Used to Stop test during runtime **************************************************
        public void appendFinalHTMLReport(ITestResult result){
            if(result.getStatus() == ITestResult.FAILURE)
            {
                driver.close();
            }
        }

        Thread targetThread = Thread.currentThread();
        public void stopCurrentTestSetResultsToPassedAndContinueWithNextTest() throws Exception
        {
            /** 1. Because there is no proper way to stop a running test and not FAIL it within in Java for now
             *  2. We have to create a way to modify the test based on our own conditions
             *  3. Therefore if a negative tests is stop by means of a HardAssertion or Thread.currentThread().interrupt()
             *  4. Instead of the test to show a FAILED outcome in the report we have to change that to a PASS outcome/result
             * */

            //Close current Browser
            driver.close();

            //Pass the test with an assert to stop it
            //targetThread.interrupt();
            //Assert.assertNull("Test is stopped but has passed");
            Assert.fail();

            //targetThread.interrupt();
            //Assert.assertNull("Test is stopped but has passed");
            Assert.fail();

            //Assert.assertNotNull("");
            //Assert.assertNull("Test is stopped but has passed");
            Assert.fail();

            System.out.println("");

            /**Stop the thread*/
            Thread.currentThread().stop();
        }

        public void stopCurrentTestSetResultsToFailedAndContinueWithNextTest() throws Exception
        {
            /** 1. Because there is no proper way to stop a running test and not FAIL it within in Java for now
             *  2. We have to create a way to modify the test based on our own conditions
             *  3. Therefore if a negative tests is stop by means of a HardAssertion or Thread.currentThread().interrupt()
             *  4. Instead of the test to show a FAILED outcome in the report we have to change that to a PASS outcome/result
             * */

            //Close current Browser
            driver.close();

            //Fail the test with an assert to stop it
            fail("This test has been stopped!");
            //Assert.fail("This test has been stopped");

            /**Stop the thread*/
            Thread.currentThread().stop();
        }


        public void haltScript(){

            targetThread.interrupt();
        }

//*******************To capture screenshots*****************************************************************************
        /**Write reusable method to capture screenshots*/
        public  String captureScreenshot(String screenshotName) throws Exception
        {
            String imgPath = null;
            String timeStamp = new SimpleDateFormat("dd-MM-yyyy-HH-mm-ss").format(new Date());
            screenshotName = screenshotName+ "-" + timeStamp + ".png";

            try
            {
                // Get viewable area's screenshot
                File sourceFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

                //File finalDestination = new File(destination);
                //imgPath = "./testOutputs/screenshots/" + screenshotName + ".png";
                imgPath = "./testOutputs/screenshots/" + screenshotName;
                File path = new File(imgPath);

                FileUtils.copyFile(sourceFile, path);
            } catch (NoSuchSessionException e)
            {
                System.out.println(e);
            }
            return imgPath;
        }

        /**Create a method to append the screenshot taken onto the report*/
        public static Object screenCapture(String logDetails,String imagePath) throws Exception
        {
            //Log Screenshots on the Report
            Object test = ExtentTestManager.getTest().log(Status.INFO,logDetails, MediaEntityBuilder.createScreenCaptureFromPath(imagePath).build());
            return test;
        }

        public Object screenCapture2(String logDetails, String imagePath, By element) throws Exception
        {
            boolean elementPath = ElementIsPresent(element);
            Object test="";
            try
            {
                if(!elementPath)
                {
                    //Log Screenshots on the Report
                    test = ExtentTestManager.getTest().log(Status.INFO,logDetails, MediaEntityBuilder.createScreenCaptureFromPath(imagePath).build());
                }else {
                    scrollElementIntoView(element);
                }


            }catch (Exception e)
            {

            }
            return test;

        }


        /**Create a reusable Method to call the ExtentReport screenshot logger,by making use of the CaptureScreenshot Method
         * This Method will called whenever we need a screenshot taken and appended to the report*/
        public void screenshotLoggerMethod(String name, String description) throws Exception
        {
            String screenshotName = captureScreenshot(name);
            screenCapture(description,screenshotName);
            //screenCapture("Screenshots",screenshotName);
        }

        public void screenshotLoggerMethod2(String name, String description, By element) throws Exception
        {
            String screenshotName = captureScreenshot(name);
            screenCapture2(description,screenshotName,element);
            //screenCapture("Screenshots",screenshotName);
        }


//*******************To perform selection on web elements **************************************************************
        /**Used to select dropdown items by Sending keys */
        public void selectdropdownItemBySendKeys(By elementLocation,String text)
        {waitForElementToBeVisible(elementLocation);


            //To perform web action
            Actions actions = new Actions(driver);
            driver.findElement(elementLocation).click();

            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            actions.sendKeys(Keys.DOWN).build().perform();//press down arrow key
            actions = new Actions(driver);
            actions.sendKeys(text).click();
            actions = new Actions(driver);
            actions.sendKeys(Keys.ENTER).build().perform();//press enter
        }

        /**Used to select/hover on dropdown items or invisible elements*/
        public void selectdropdownItemByHoverMove(int itemPosition)
        {
            for(int i = 0; i <= itemPosition; i++)
            {
                Actions actions = new Actions(driver);
                actions.sendKeys(Keys.DOWN).build().perform();//press down arrow key
                actions = new Actions(driver);
                actions.sendKeys(Keys.ENTER).build().perform();//press enter
            }
        }

        /**Used select dropdown item by Name */
        public void selectdropdownItemByNameValue(By elementLocation,String text) throws InterruptedException
        {waitForElementToBeVisible(elementLocation);
            //"select by value"
            ////////Thread.sleep(5000);
            Select selectByValue = new Select(driver.findElement(elementLocation));
            selectByValue.selectByValue(text);
        }

        /**Used select dropdown item by visible text */
        public void selectdropdownItemByVisibleText(By elementLocation,String text) throws InterruptedException
        {
            //"select by visible text"
            waitForElementToBeVisible(elementLocation);
            Select selectByVisibleText = new Select(driver.findElement(elementLocation));
            selectByVisibleText.selectByVisibleText(text);
        }

        /**selectdropdown Item By Visible Text On Ul And li*/
        public void selectdropdownItemByVisibleTextOnUlAndli(By elementLocation,String dropdownSearchText1Xpath,String text,String dropdownSearchText) throws InterruptedException
        {   waitForElementToBeVisible(elementLocation);
            scrollElementIntoView(elementLocation);

            WebElement dropdown = driver.findElement(elementLocation);

            //Click on dropdown
            click(elementLocation, By.xpath(dropdownSearchText1Xpath+text+dropdownSearchText));

            //Select item by text
            click(By.xpath(dropdownSearchText1Xpath+text+dropdownSearchText), By.xpath("//*[@id='welcome']"));
        }

        /**Used select dropdown item by index */
        public void selectdropdownItemByIndex(By elementLocation,Integer index) throws InterruptedException
        {
            //"select by visible text" and Index
            waitForElementToBeVisible(elementLocation);
            Select selectByIndex = new Select(driver.findElement(elementLocation));
            selectByIndex.selectByVisibleText(String.valueOf(index));
        }

        //**************Used to select dropdown item by Name************
        public void selectdropdownItemByValueIndex(By elementLocation, String text) throws Exception {
            //"select by value"
            ////////Thread.sleep(5000);
            waitForElementToBeVisible(elementLocation);
            try {
                Select selectByIndex = new Select(driver.findElement(elementLocation));
                int index = 0;
                for (WebElement options : selectByIndex.getOptions()) {
                    if (options.getAttribute("value").equalsIgnoreCase(text.trim()))
                        break;
                    index++;
                }
                selectByIndex.selectByIndex(index);
            } catch (Exception e) {
                ExtentTestManager.getTest().fail(MarkupHelper.createLabel("The Option Specified Is Not Found On DropDown, please investigate", ExtentColor.RED));
                stopCurrentTestSetResultsToFailedAndContinueWithNextTest();
            }
        }
        //*************************************************THESE ARE METHODS TO HANDLE DYNAMIC WEB TABLES*******************
        //Declare global string to be used when calling this method
        DecimalFormat decimalFormat = new DecimalFormat("##.00");//Make new decimal format
        public String  strActualDebitAmount , strActualCreditAmount ;
        public double intTotalActualDebitAmount , intTotalActualCrediAmount;


        /**By finding a pattern within the table ,we can create a dynamic xpath to handle the table and perform actions on it:
         * Example of such a pattern below:
         * //*[@id='taskListDatatable']/div/datatable-body/datatable-selection/datatable-scroller/datatable-row-wrapper/datatable-body-row/div[2]/datatable-body-cell[2]/div/div
         * //*[@id='taskListDatatable']/div/datatable-body/datatable-selection/datatable-scroller/datatable-row-wrapper/datatable-body-row/div[3]/datatable-body-cell[2]/div/div
         * //*[@id='taskListDatatable']/div/datatable-body/datatable-selection/datatable-scroller/datatable-row-wrapper/datatable-body-row/div[4]/datatable-body-cell[2]/div/div
         * //*[@id='taskListDatatable']/div/datatable-body/datatable-selection/datatable-scroller/datatable-row-wrapper/datatable-body-row/div[5]/datatable-body-cell[2]/div/div
         *
         * In the above Example everything remains constant apart from the datatable-row-wrapper[] value that changes
         * */
        public void handleDynamicWebTableWithDynamicXpaths()
        {
            //Split the xpath into two parts
            String before_xpath = ".//*[@id='taskListDatatable']/div/datatable-body/datatable-selection/datatable-scroller/datatable-row-wrapper/datatable-body-row/div[";
            String after_Xpath = "]/datatable-body-cell[2]/div/div";

            for (int i=2; i<=11; i++)
            {
                /**concatenate the before_xpath to the i(index value) and the after_xpath to form your dynamic xpath and store it in a String*/
                String expectedTxt = driver.findElement(By.xpath(before_xpath+i+after_Xpath)).getText();
                System.out.println(expectedTxt);

                /**Compare the text in the table to the expected text*/
                if(expectedTxt == "Search Text")
                {
                    System.out.println("INFORMATION:: Search Text");
                }

                /**Search for the expected text and when found ,use its row location to perform action on that row(click,getText etc...)*/
                if (expectedTxt.contains("Search Text"))
                {
                    driver.findElement(By.xpath("//*[@id='taskListDatatable']/div/datatable-body/datatable-selection/datatable-scroller/datatable-row-wrapper/datatable-body-row/div["+i+"]/datatable-body-cell[2]/div/div")).click();
                    driver.findElement(By.xpath("//*[@id='taskListDatatable']/div/datatable-body/datatable-selection/datatable-scroller/datatable-row-wrapper["+i+"]/datatable-body-row/div[2]/datatable-body-cell[6]/div/div/div")).click();
                }
            }
        }


        public void handleDynamicWebTableWithListSearchDuplicates(By tableElementLocation,By columnsElementLocation ,By rowsElementLocation,String beforeXpath_1,String afterXpath_1,
                                                                  String beforeXpath_2,String afterXpath_2,String beforeXpath_3,String afterXpath_3,String strWorkflowReferenceUniqueTxt)
        {
            try
            {
                // Locate the table
                WebElement Table = driver.findElement(tableElementLocation);

                /**Printing table header of a web table assuming first row as header
                 *Get number of columns In table*/
                List<WebElement> Allcolumns = driver.findElements(columnsElementLocation);

                System.out.println("======================Ebox Table==============================================================");
                System.out.println("Headers in table are below:");
                System.out.println("Total headers found: " + Allcolumns.size());
                int numOfCol = Allcolumns.size();


                //Print all the content table headers
            /*for(WebElement headers:Allcolumns)
            {
                System.out.println(headers.getText());
            }*/

                /**Printing table header of a web table assuming first row as header
                 *Get number of rows In table.
                 * Finding number of rows in a web table. We need to exclude header to get actual number of data rows*/
                List<WebElement> Allrows = Table.findElements(rowsElementLocation);
                System.out.println("The number of rows in the table is :" + (Allrows.size() - 1));
                int numOfrows = Allrows.size();
                //Print all the content table rows
            /*for(WebElement rows: Allrows)  {
                System.out.println(rows.getText());
            }*/

                System.out.println("======================Search for Transaction Reference in Ebox===============================");
                /**Find all rows that contains our search string and print out their index and other information*/
                /**The row index in this table starts from 2*/
                int i = 2;
                intTotalActualDebitAmount =0.00;
                intTotalActualCrediAmount=0.00;


                while (i <= numOfrows)
                {
                    {
                        int staticRowCount = numOfrows;

                        /**Find the Column(s) that will hold the string*/
                        //WebElement columntToSearchForText = driver.findElement(By.xpath("//*[@id=\"StatementForm\"]/div[4]/div/table/tbody/tr["+i+"]/td[3]/div[3]"));
                        WebElement columntToSearchForText = driver.findElement(By.xpath(beforeXpath_1 + i + afterXpath_1));

                        //String WorkflowReferenceUniqueTxt = "5ZSIW3I";//The search string/word
                        //String WorkflowReferenceUniqueTxt = CapturePage.strWorkflowReferenceUniqueTxt;//The search string/word TODO: CHECK THAT THIS WORKS
                        String WorkflowReferenceUniqueTxt = strWorkflowReferenceUniqueTxt; //TODO :CHECK THAT THIS WORKS


                        //Declare and initialize the values
                        double intActualDebitAmount = 0.00;
                        double intActualCreditAmount = 0.00;

                        if (columntToSearchForText.getText().toLowerCase().equalsIgnoreCase(WorkflowReferenceUniqueTxt))
                        {

                            /**Print the string that is found as well as it location in the table and other information*/

                            //Get the string on the webpage for Debtor
                            strActualDebitAmount = driver.findElement(By.xpath(beforeXpath_2 + i + afterXpath_2)).getText();
                            //Convert string to integer
                            if(strActualDebitAmount.isEmpty())
                            {
                                //System.out.println("String is null");
                            }else{
                                intActualDebitAmount = Double.parseDouble(strActualDebitAmount);
                            }

                            //Get the string on the webpage for Creditor
                            strActualCreditAmount = driver.findElement(By.xpath(beforeXpath_3 + i + afterXpath_3)).getText();
                            //Convert string to integer
                            if(strActualCreditAmount.isEmpty())
                            {
                                //System.out.println("String is null");
                            }else{
                                intActualCreditAmount = Double.parseDouble(strActualCreditAmount);
                            }

                            //Print the row number where the search string exists
                            System.out.println("INFORMATION::Our transaction reference " + columntToSearchForText.getText() + " is found on row index " + i + " in the table");

                            //Add or sum the amount on each iteration (round-off to two decimal places)
                            Math.round( intTotalActualDebitAmount +=intActualDebitAmount);
                            Math.round( intTotalActualCrediAmount +=intActualCreditAmount);

                            //Print out findings
                            System.out.println("INFORMATION::The Actual Debit Amount is   :" + strActualDebitAmount);
                            System.out.println("INFORMATION::The Actual Credit Amount is  :" + strActualCreditAmount);
                            System.out.println("====================================================================================");

                            //Show information from Ebox and Excel in a table format on extent report
                            String[][] data =
                                    {
                                            {"Transaction Number", "Transaction Reference", "Debtor", "Creditor"},
                                            {String.valueOf(i - 1), columntToSearchForText.getText(), String.valueOf(intActualDebitAmount), String.valueOf(intActualCreditAmount)},
                                    };
                            Markup m = MarkupHelper.createTable(data);
                            ExtentTestManager.getTest().info(m);


                            //Exist the loop when visible table reaches the end
                            i++;
                            if(driver.findElements(By.xpath((beforeXpath_1 + i + afterXpath_1))).size() != 0)
                            {
                            }else { break; }

                        } else if(!columntToSearchForText.getText().toLowerCase().equalsIgnoreCase(WorkflowReferenceUniqueTxt))
                        {
                            i++;
                        }
                    }
                    /**Print out the Sumed-up the total values of credit amount and debit amount (round-off to two decimal places)*/
                    //System.out.println("INFORMATION::The Actual Totatl Debit Amount is   :"  +decimalFormat.format(intTotalActualDebitAmount));
                    //System.out.println("INFORMATION::The Actual Totatl Credit Amount is   :" +decimalFormat.format(intTotalActualCrediAmount));
                }
            } catch (Exception e)
            {
                ExtentTestManager.getTest().warning("Status=Warning: The transaction is not displayed on the authorization list");
                System.out.println("Info: The expected reference Text was not found\n" + e.getMessage());
            }
        }


        public void handleDynamicWebTableWithListWebElementsAndDynamicXpaths(By elementLocation,String beforeXpath,String afterXpath,String strWorkflowReferenceUniqueTxt) throws InterruptedException
        {
            try
            {
                //Get the Row Count
                //List<WebElement> rowsCount = driver.findElements(By.xpath(".//*[@id='taskListDatatable']/div/datatable-body/datatable-selection/datatable-scroller/datatable-row-wrapper/datatable-body-row"));
                List<WebElement> rowsCount = driver.findElements(elementLocation);
                System.out.println("INFORMATION:: The table contains : " + rowsCount.size() + " columns");

                //Peform a loop to search the rows and get our searchtext
                for (int i = 0; i < rowsCount.size(); i++)
                {
                    //if (rowsCount.get(i).getText().contains(CapturePage.strWorkflowReferenceUniqueTxt)) { //Call the random Alphanumeric field from the CapturePage class
                    if (rowsCount.get(i).getText().contains(strWorkflowReferenceUniqueTxt)) { //Call the random Alphanumeric field from the CapturePage class//TODO :CHECK THAT THIS WORKS
                        System.out.println("INFORMATION:: The row containing our searchtext is number " + i + " and contains: " + rowsCount.get(i).getText());

                        //Verify again that the text found is what is expected
                        //if (rowsCount.get(i).getText().contains(CapturePage.strWorkflowReferenceUniqueTxt))//TODO :CHECK THAT THIS WORKS
                        if (rowsCount.get(i).getText().contains(strWorkflowReferenceUniqueTxt))
                        {
                            /**The row index in this table starts from 2,so we have to SET "i" to 2 when our searchtext is found*/
                            i = 1;
                            //i=i+2;
                            /**Click on the row that contains our searchtex
                             *concatenate the before_xpath to the i(index value) and the after_xpath to form your dynamic xpath and store it in a String
                             *driver.findElement(By.xpath("//*[@id='taskListDatatable']/div/datatable-body/datatable-selection/datatable-scroller/datatable-row-wrapper["+i+"]/datatable-body-row/div[2]/datatable-body-cell[6]/div/div/div")).click();*/
                            driver.findElement(By.xpath(beforeXpath + i + afterXpath)).click();

                            /**Break out of the loop when the text is found*/
                            break;
                        } else {
                            System.out.println("INFORMATION:: A new item has been added to the tasklist,we need to research the tasklist again\n");
                            i = 0;
                            //continue;
                        }
                    } else {
                        ExtentTestManager.getTest().warning("STATUS=>FAIL:: The transaction is not displayed on the authorization list");
                        System.out.println("INFORMATION:: The expected reference Text was not found\n");
                    }
                }
            }catch (Exception e){
                ExtentTestManager.getTest().log(Status.FAIL, "EXCEPTION:: Table search was not successful" + e.getMessage());
            }
        }

        public void handleDynamicWebTableWithListWebElementsAndTagName(By elementLocation,String beforeXpath_1,String afterXpath_1,String beforeXpath_2,String afterXpath_2,String beforeXpath_3,String afterXpath_3,String strWorkflowReferenceUniqueTxt)
        {   waitForElementToBeVisible(elementLocation);
            try
            {
                // Locate the table with
                WebElement Table = driver.findElement(elementLocation);
                List<WebElement> rows = Table.findElements(By.tagName("tr"));
                int rows_count = rows.size();

                System.out.println("The number of rows in the table is :" + rows_count);
                int i =2;//The row index in this table starts from 2,so we have to increment "i" by 2 when our searchtext is found
                while (i < rows_count)
                {
                    //String WorkflowreferenceUniqueTxt = CapturePage.strWorkflowReferenceUniqueTxt;//The search string/word
                    String searchDescription = driver.findElement(By.xpath(beforeXpath_1+i+afterXpath_1)).getText();

                    if (searchDescription.contains(strWorkflowReferenceUniqueTxt))
                    {
                        System.out.println("INFORMATION::The row containing our searchtext is number " + i + " and contains: " + searchDescription);

                        //concatenate the before_xpath to the i(index value) and the after_xpath to form your dynamic xpath and store it in a String
                        strActualDebitAmount = driver.findElement(By.xpath(beforeXpath_2 + i + afterXpath_2)).getText();
                        strActualCreditAmount = driver.findElement(By.xpath(beforeXpath_3 + i + afterXpath_3)).getText();

                        System.out.println("INFORMATION::The Actual Debit Amount is  :" + strActualDebitAmount);
                        System.out.println("INFORMATION::The Actual Credit Amount is  :" + strActualCreditAmount);
                        //ExtentTestManagerJMT.getTest().log(Status.INFO, "INFORMATION:: Transaction sent for review successfully" + strActualAmount2);

                        //Break out of the loop when the text is found
                        break;
                    } else
                    {
                        ExtentTestManager.getTest().warning("STATUS=>FAIL:: The transaction is not displayed on the authorization list");
                        System.out.println("INFORMATION:: The expected reference Text was not found\n");
                    }
                    i++;
                }
            }
            catch (Exception e)
            {
                ExtentTestManager.getTest().log(Status.FAIL, "EXCEPTION:: Table search was not successful" + e.getMessage());
            }
        }

        public void handleDynamicWebTableWithListWebElementsToGetAllColumnValues()
        {
            List<WebElement> columVal = driver.findElements(By.xpath(".//*[@id='taskListDatatable']/div/datatable-body/datatable-selection/datatable-scroller/datatable-row-wrapper/datatable-body-row"));
            // count the size of the list to match with the size of the column state
            System.out.println("INFORMATION:: Size of the contents in the column state is : " +columVal.size());

            // now for matching one of the content and then performing some action please
            // start a for loop

            String oneVal = "Search Text";
            for(int i=0;i<columVal.size();i++)
            {
                System.out.println("INFORMATION:: Content text is : " + columVal.get(i).getText());
                // match the content here in the if loop
                if(columVal.get(i).getText().equals(oneVal))
                {
                    // perform action
                    columVal.get(i).click();
                }
            }
        }
        public void handleDynamicWebTableWithListWebElements()
        {

            // get all the rows on present on the table and store them in LIST
            List<WebElement> rows = driver.findElements(By.xpath(".//*[@id='taskListDatatable']/div/datatable-body/datatable-selection/datatable-scroller/datatable-row-wrapper/datatable-body-row"));
            //Loop through the LIST to find your search TEXT
            for (WebElement el : rows)
            {
                System.out.println(el.getText());
            }
        }

        public void handleStaticWebTableAndStoreAllContents()
        {
            // get all the rows on present on the table and store them in LIST
            WebElement Table = driver.findElement(By.xpath("//*[@id=\"StatementForm\"]/div[4]/div/table/tbody"));
            WebElement cell = driver.findElement(By.xpath("//*[@id=\"StatementForm\"]/div[4]/div/table/tbody/tr[2]/td[5]"));
            System.out.println("Data for Row 2 and Column 5 is "+cell.getText()+".");
            List<WebElement> rows = Table.findElements(By.tagName("tr"));
            int rows_count = rows.size();

            System.out.println(rows_count);
            Iterator itr=rows.iterator();
            int index = 2;
            while(itr.hasNext())
            {
                index++;
                System.out.println("The current index is :"+index);

                WebElement values=(WebElement)itr.next();
                List<WebElement> lstTd = values.findElements(By.tagName("td"));

                Iterator subItr = lstTd.iterator();
                while (subItr.hasNext())
                {
                    WebElement temp = (WebElement)subItr.next();
                    System.out.print(temp.getText()+ " ");
                }
                System.out.println();
            }
        }


        // *******************************The below Code is for working on weblist items ***********************************
        /**Your purpose is to get all li within a ul. So it needs to locate ul first then all li in that ul. You can do by
         * using the following Selenium Java code:
         * How to check all elements in a <ul> list using Selenium WebDriver?
         * */
        public int getNumberOfUlItemsOnLiList(By elementLocation)
        {
            WebElement ul_element = driver.findElement(elementLocation);
            List<WebElement> li_All = ul_element.findElements(By.tagName("li"));
            //System.out.println(li_All.size());
            int intTotalItem = li_All.size();

            for(int i = 0; i < li_All.size(); i++)
            {
                //System.out.println(li_All.get(i).getText());
            }
            //OR
            for(WebElement element : li_All)
            {
                //System.out.println(element.getText());
            }
            return intTotalItem;
        }
    }


