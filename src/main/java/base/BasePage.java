package base;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import config.Settings;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.Coordinates;
import org.openqa.selenium.interactions.Locatable;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.w3c.dom.*;
import org.xml.sax.SAXException;
import utils.extentreports.ExtentTestManager;
import org.apache.commons.io.FileUtils;
import utils.logs.Log;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.xpath.XPathExpressionException;
import java.io.*;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.*;

import static org.testng.Assert.fail;

public class BasePage {



    /**
     * Create a Public Driver object that will be used across the entire framework when there is need to use the Selenium
     * Driver this class is responsible for bringing in our web-driver and browser class and all other globally used objects
     * Create a web-driver instance to run the scripts without the selenium-grid implementations*/
    public WebDriver driver = null ;

    public  WebDriverWait wait =null;
    private static Platform OS;
    public static String strPacs008FilePath = System.getProperty("user.dir") + "/testOutputs/pacs008/";
    public static String testArtifactName = null;
    public static String globalTestCaseScenarioType;//Can only be Positive or negative
    public static String systemReference; //UUID.randomUUID().toString();
    public static String paymentInfoID;

    //******************************************************************************************************************


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
        String name = new Object(){}.getClass().getEnclosingMethod().getName();
        Log.info(getGenericMessage(name ));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(by));
    }

    //Old

        String modalStyleForUpload = "//*[@id='progress-indicator']";



        /**
         * Check if Browser is Open/Alive
         */
        public Boolean isAlive() {
            String name = new Object(){}.getClass().getEnclosingMethod().getName();
            Log.info(getGenericMessage(name ));
            try {
                driver.getCurrentUrl();//or driver.getTitle();
                return true;
            } catch (Exception ex) {
                return false;
            }
        }


        /**
         * Refresh the browser
         */
        public void refreshBrowser() {
            String name = new Object(){}.getClass().getEnclosingMethod().getName();
            Log.info(getGenericMessage(name ));

            driver.navigate().refresh();
        }

        public void closeTheBrowser() {
            String name = new Object(){}.getClass().getEnclosingMethod().getName();
            Log.info(getGenericMessage(name ));

            driver.quit();
        }

        //Get Page Title
        public String getPageTitle() {
            String name = new Object(){}.getClass().getEnclosingMethod().getName();
            Log.info(getGenericMessage(name ));

            Log.debug("Trying to get the Page Title");

            return driver.getTitle();
        }
    public String trimLeadingCharacters(String textToTrim,int indexOfStartingPosition)
    {
        //Count the chars in the string
        int charCounter = textToTrim.length();

        //Trim preceding characters by starting from an index position to the max-length of the string

        return textToTrim.substring(indexOfStartingPosition,charCounter);
    }
        //Click Method
        public void click(By elementLocation, By clickElement) {
            String name = new Object(){}.getClass().getEnclosingMethod().getName();
            Log.info(getGenericMessage(name ));


            Log.debug("Trying to click to element: \n" +
                    "\ttarget locator:'"+ elementLocation+",\n"+
                    "\tconfirmation element :'"+ clickElement+"' ");
            int count = 0;
            long startTime = System.currentTimeMillis(); //fetch starting time
            while(true)
            {
                if (waitForElementToBeVisible(elementLocation))
                    try {
                        WebElement findElem = driver.findElement(elementLocation);
                        findElem.click();
                        Log.info(getMessage(elementLocation, true,name ));

                        break;
                    }
                    catch (NoSuchElementException e) {
                        Log.error(getMessage(elementLocation, false,name));
                                driver.navigate().refresh();
                    }
                count+=1;
                if (count == 3) {
                    Log.error(getMessage(elementLocation, false,name)+" after three attempts and refreshing");
                    break;};
            }
            waitForElementToBeDone(clickElement);




        }

    private static String getMessage(By elementLocation, boolean isSuccess, String operation) {

            if (isSuccess) {
                return operation + " element successful" +
                        "Element locator: '" + elementLocation + "'\n";
            }
            else {

                    return operation + " element NOT successful" +
                            "Element locator: '" + elementLocation + "'\n";

            }
    }

    public static String getGenericMessage(String operation) {

            return "Current method  : " +operation ;

    }

    public void click (By elementLocation) {
        String name = new Object(){}.getClass().getEnclosingMethod().getName();
        Log.info(getGenericMessage(name ));
        driver.findElement(elementLocation).click();
    }

    public boolean isElementDisplayed(String strXpath)
    { String name = new Object(){}.getClass().getEnclosingMethod().getName();
        Log.info(getGenericMessage(name ));
        boolean isElementDisplayed = false;
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5)/*timeout in seconds*/);
        try
        {
            /**ExpectedConditions.visibilityOfElementLocated - it ensures that:
             * a) element is present in DOM
             b) element is visible
             ExpectedConditions.visibilityOf it doesn't check that element is present in DOM.*/
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(strXpath))).isDisplayed();
            isElementDisplayed = true;
        } catch (TimeoutException ignored)
        {
        }
        return isElementDisplayed;
    }
    //Write Text
        public void writeText (By elementLocation, String text)
        {   String name = new Object(){}.getClass().getEnclosingMethod().getName();
            Log.info(getGenericMessage(name ));

            waitForElementToBeVisible(elementLocation);
            Log.info(getMessage(elementLocation,true,name ));
            driver.findElement(elementLocation).sendKeys(text);
            Log.info(getMessage(elementLocation,true,text ));
        }

        //Read Text
        public String readText (By elementLocation) {
            String name = new Object(){}.getClass().getEnclosingMethod().getName();
            Log.info(getGenericMessage(name ));
            waitForElementToBeVisible(elementLocation);
            Log.debug(getMessage(elementLocation,true,name ));

            return driver.findElement(elementLocation).getText();
        }
        //Select item dropdown
        public void selectItemDropdown (By elementLocation, String visibleText)

        {   String name = new Object(){}.getClass().getEnclosingMethod().getName();
            Log.info(getGenericMessage(name ));
            waitForElementToBeVisible(elementLocation);
            Log.debug(getMessage(elementLocation,true,name ));
            WebElement ele = driver.findElement(elementLocation);
            Select dropDown = new Select(ele) ;
            dropDown .selectByVisibleText(visibleText);
        }
        //Read Attributes Title
        public String readAttribute (By elementLocation) {
            String name = new Object(){}.getClass().getEnclosingMethod().getName();
            Log.info(getGenericMessage(name ));
            waitForElementToBeVisible(elementLocation);
            return driver.findElement(elementLocation).getAttribute("title");
        }

        //Read Attribute value
        public String readAttributeValue (By elementLocation) {
            String name = new Object(){}.getClass().getEnclosingMethod().getName();
            Log.info(getGenericMessage(name ));
            waitForElementToBeVisible(elementLocation);
            return driver.findElement(elementLocation).getAttribute("value");
        }

        //Clear Method
        public void clearTextField (By elementLocation) {
            String name = new Object(){}.getClass().getEnclosingMethod().getName();
            Log.info(getGenericMessage(name ));
            waitForElementToBeVisible(elementLocation);
            driver.findElement(elementLocation).clear();
        }

        public void mouseHoverMethod(String strElementXpathString) throws InterruptedException {
            String name = new Object(){}.getClass().getEnclosingMethod().getName();
            Log.info(getGenericMessage(name ));

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
            String name = new Object(){}.getClass().getEnclosingMethod().getName();
            Log.info(getGenericMessage(name ));
            String winHandleBefore = driver.getWindowHandle();
            for(String winHandle : driver.getWindowHandles())
            {
                driver.switchTo().window(winHandle);
            }
        }

        /**Used to scroll the browser to the Top */
        public void scrollUp()
        {String name = new Object(){}.getClass().getEnclosingMethod().getName();
            Log.info(getGenericMessage(name ));
            /**if the element is on Top.
             *Scroll vertically Up by 1000 pixels*/
            JavascriptExecutor jse = (JavascriptExecutor)driver;
            jse.executeScript("window.scrollBy(0,-1000)");
        }

        /**Used to scroll the browser to the Bottom */
        public void scrollDown()
        {String name = new Object(){}.getClass().getEnclosingMethod().getName();
            Log.info(getGenericMessage(name ));
            /**if the element is on bottom.
             *Scroll vertically down by 1000 pixels*/
            JavascriptExecutor jse = (JavascriptExecutor)driver;
            jse.executeScript("window.scrollBy(0,1000)");
        }

        /**Used to scroll the browser to an Element Location */
        public void scrollElementIntoView(By elementLocation)
        {   String name = new Object(){}.getClass().getEnclosingMethod().getName();
            Log.info(getGenericMessage(name ));
            waitForElementToBeVisible(elementLocation);
            JavascriptExecutor je = (JavascriptExecutor) driver;

            //Identify the WebElement which will appear after scrolling
            WebElement element = driver.findElement(elementLocation);

            // now execute query which actually will scroll until that element is appeared on page.
            je.executeScript("arguments[0].scrollIntoView(true);",element);
        }

        /**scroll Element Into View By Coordinates*/
        public void scrollElementIntoViewByCoordinates(By elementLocation) throws InterruptedException
        {  String name = new Object(){}.getClass().getEnclosingMethod().getName();
            Log.info(getGenericMessage(name ));
            waitForElementToBeVisible(elementLocation);
            WebElement element = driver.findElement(elementLocation);
            Coordinates cor=((Locatable)element).getCoordinates();
            cor.inViewPort();
            //////Thread.sleep(8000);
        }

        /**Move To Focus On Element And Click*/
        public void moveToFocusOnElementAndClick(By elementLocation)
        {   String name = new Object(){}.getClass().getEnclosingMethod().getName();
            Log.info(getGenericMessage(name ));
            waitForElementToBeVisible(elementLocation);
            Actions builder = new Actions(driver);
            WebElement element = driver.findElement(elementLocation);
            builder.moveToElement(element).build().perform();
            builder.moveToElement(element).click().perform();
            waitForElementToBeDone(elementLocation);
        }

        /**Create a reusable highlight the area*/
        //Creating a custom function
        public void highLighterMethod(By elementLocation)
        {String name = new Object(){}.getClass().getEnclosingMethod().getName();
            Log.info(getGenericMessage(name ));
            waitForElementToBeVisible(elementLocation);
            JavascriptExecutor jse = (JavascriptExecutor) driver;

            //Identify the WebElement
            WebElement element = driver.findElement(elementLocation);

            // now execute query
            jse.executeScript("arguments[0].setAttribute('style', 'background: orange; border: 2px solid red;');", element);
        }
        //*************************Used to click tab key********************************************************************
        public void clickTabKeyAfterElement (By elementAtFocus, By emptyPageSpaceXpath) {
            String name = new Object(){}.getClass().getEnclosingMethod().getName();
            Log.info(getGenericMessage(name ));
            waitForElementToBeVisible(elementAtFocus);
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
             *
             * */
            String name = new Object(){}.getClass().getEnclosingMethod().getName();
            Log.info(getGenericMessage(name ));
            driver.manage().timeouts().scriptTimeout(Duration.ofMinutes(1));
        }

        public void waitForElementToBeClickable(By elementLocation)
        {
            /** Purpose:
             *Explicit wait ;We can tell the tool to wait only till the Condition satisfy.*/
            String name = new Object(){}.getClass().getEnclosingMethod().getName();
            Log.info(getGenericMessage(name ));
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(120));
            WebElement element = wait.until(ExpectedConditions.elementToBeClickable(elementLocation));
        }


        public boolean waitForElementToBeVisible(By elementLocation)
        {
            /** Purpose:
             *Explicit wait ;We can tell the tool to wait only till the Condition satisfy.*/
            String name = new Object(){}.getClass().getEnclosingMethod().getName();
            Log.info(getGenericMessage(name ));
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
        {String name = new Object(){}.getClass().getEnclosingMethod().getName();
            Log.info(getGenericMessage(name ));
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
            String name = new Object(){}.getClass().getEnclosingMethod().getName();
            Log.info(getGenericMessage(name ));
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
        {String name = new Object(){}.getClass().getEnclosingMethod().getName();
            Log.info(getGenericMessage(name ));
            JavascriptExecutor jse = (JavascriptExecutor)driver;
            jse.executeScript("return document.readyState").equals("complete");
        }

        public void waitAndClickTab(By tabElement)  {
            String name = new Object(){}.getClass().getEnclosingMethod().getName();
            Log.info(getGenericMessage(name ));
            waitForElementToBeVisible(tabElement);
            try {

                click(tabElement, By.xpath("//*[@id='welcome']"));

            } catch (Exception e) {
                System.out.println(":::Tab Element is not visible or not clickable:::");
                ExtentTestManager.getTest().warning(MarkupHelper.createLabel("TabNotFoundForUserRole::  " + "::: Some tab(s) for this user are not found or User Role has changed:::", ExtentColor.ORANGE));
            }
        }

        public void waitForPageToBeInAnExpectedConditions()
        {String name = new Object(){}.getClass().getEnclosingMethod().getName();
            Log.info(getGenericMessage(name ));
            WebDriverWait wait = new WebDriverWait(driver,Duration.ofMinutes(2));
            wait.until(ExpectedConditions.jsReturnsValue("return document.readyState==\"complete\";"));
        }

        public void ImplicitWait()
        {String name = new Object(){}.getClass().getEnclosingMethod().getName();
            Log.info(getGenericMessage(name ));
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
        {String name = new Object(){}.getClass().getEnclosingMethod().getName();
            Log.info(getGenericMessage(name ));
            /** Purpose:
             * Sets the amount of time to wait for a page load to complete before
             * throwing an error. If the timeout is negative, page loads can be indefinite.*/
            driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
        }

        // *****************************THESE ARE METHODS TO ASSERTIONS *******************************************************
        /***Private method that simply looks for the element and determines if it is Present like this:*/
        public boolean isElementDisplayed(By elementLocator)
        {String name = new Object(){}.getClass().getEnclosingMethod().getName();
            Log.info(getGenericMessage(name ));
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
            String name = new Object(){}.getClass().getEnclosingMethod().getName();
            Log.info(getGenericMessage(name ));
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
            String name = new Object(){}.getClass().getEnclosingMethod().getName();
            Log.info(getGenericMessage(name ));
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
        {String name = new Object(){}.getClass().getEnclosingMethod().getName();
            Log.info(getGenericMessage(name ));
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
            String name = new Object(){}.getClass().getEnclosingMethod().getName();
            Log.info(getGenericMessage(name ));
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
            String name = new Object(){}.getClass().getEnclosingMethod().getName();
            Log.info(getGenericMessage(name ));
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
            String name = new Object(){}.getClass().getEnclosingMethod().getName();
            Log.info(getGenericMessage(name ));
            try {
                driver.getTitle();
                return false;
            } catch (UnhandledAlertException e) {
                // Modal dialog showed
                return true;
            }
        }

        public boolean isAttributePresent(By elementLocation,String strAttributeName){
            String name = new Object(){}.getClass().getEnclosingMethod().getName();
            Log.info(getGenericMessage(name ));

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
            String name = new Object(){}.getClass().getEnclosingMethod().getName();
            Log.info(getGenericMessage(name ));
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
            String name = new Object(){}.getClass().getEnclosingMethod().getName();
            Log.info(getGenericMessage(name ));

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
            String name = new Object(){}.getClass().getEnclosingMethod().getName();
            Log.info(getGenericMessage(name ));
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
        {String name = new Object(){}.getClass().getEnclosingMethod().getName();
            Log.info(getGenericMessage(name ));
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
            String name = new Object(){}.getClass().getEnclosingMethod().getName();
            Log.info(getGenericMessage(name ));
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
        {String name = new Object(){}.getClass().getEnclosingMethod().getName();
            Log.info(getGenericMessage(name ));
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
        {String name = new Object(){}.getClass().getEnclosingMethod().getName();
            Log.info(getGenericMessage(name ));

            // get all the rows on present on the table and store them in LIST
            List<WebElement> rows = driver.findElements(By.xpath(".//*[@id='taskListDatatable']/div/datatable-body/datatable-selection/datatable-scroller/datatable-row-wrapper/datatable-body-row"));
            //Loop through the LIST to find your search TEXT
            for (WebElement el : rows)
            {
                System.out.println(el.getText());
            }
        }

        public void handleStaticWebTableAndStoreAllContents()
        {String name = new Object(){}.getClass().getEnclosingMethod().getName();
            Log.info(getGenericMessage(name ));
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
        {String name = new Object(){}.getClass().getEnclosingMethod().getName();
            Log.info(getGenericMessage(name ));
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



    //*****************GLOBAL VARIABLE DECLARATIONS*********************************************************************
    //TODO
    public static String currentPath = System.getProperty("user.dir");
    //public static String strWorkflowReferenceUniqueTxt = setRandomString();
    //public static String strWorkflowReferenceUniqueTxt ;
    public static String strUniqueTransactionNarration;

    //Always Perform this method to generate the random string before the each iteration
    @BeforeMethod(alwaysRun = true)
    public static String setRandomString()
    {
        //Create Random String for each iteration
        String randomString = randomAlphaNumericV2();

        /*INFO*/
        //ExtentTestManager.getTest().log(Status.INFO, "Info: The reference ID is " + "  ::" + strWorkflowReferenceUniqueTxt);//TODO : INVESTIGATE WHY ITS NOT WORKING
        return randomString;
    }

    //*****************OPERATING SYSTEM FILE SEPARATOR *****************************************************************
    public  static String fileSeparator ()
    {
        //String fileSeparator = File.separator;
        String setfileSeparator = null;
        String strfileSeparator = null;
        String strfileSeparatorChar = null;
        String strfilePathSeparator = null;
        String strfilePathSeparatorChar = null;

        String operSys = System.getProperty("os.name").toLowerCase();
        if (operSys.contains("win"))
        {
            OS = Platform.WINDOWS;
            setfileSeparator ="\\";
            strfileSeparator = File.separator;
            strfileSeparatorChar = String.valueOf(File.separatorChar);
            strfilePathSeparator = File.pathSeparator;
            strfilePathSeparatorChar = String.valueOf(File.pathSeparatorChar);
        }
        else if (operSys.contains("mac"))
        {
            OS = Platform.MAC;
            setfileSeparator ="/";
            strfileSeparator = File.separator;
            strfileSeparatorChar = String.valueOf(File.separatorChar);
            strfilePathSeparator = File.pathSeparator;
            strfilePathSeparatorChar = String.valueOf(File.pathSeparatorChar);
        }
        else if(operSys.contains("linux"))
        {
            OS = Platform.LINUX;
            setfileSeparator ="/";
            strfileSeparator = File.separator;
            strfileSeparatorChar = String.valueOf(File.separatorChar);
            strfilePathSeparator = File.pathSeparator;
            strfilePathSeparatorChar = String.valueOf(File.pathSeparatorChar);
        }

        return setfileSeparator;
    }
    //*****************ALPHA_NUMERIC_STRING*****************************************************************************

    /**Generate a random alpha numeric string whose length is the number of characters specified.
     *Characters will be chosen from the set of alpha-numeric characters. Count is the length of)
     *random string to create.
     */
    private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789abcdefghijklmnopqrstuvwxyz";
    public static String randomAlphaNumeric(int count)
    {
        StringBuilder builder = new StringBuilder();
        while (count-- != 0) {
            int character = (int)(Math.random()*ALPHA_NUMERIC_STRING.length());
            builder.append(ALPHA_NUMERIC_STRING.charAt(character));
        }
        return builder.toString();
    }

    //******************************************************************************************************************
    /**Alpha numeric random string generator*/
    public static String randomAlphaNumericV2(){
        String WORKFLOWREFERENCECHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890abcdefghijklmnopqrstuvwxyz";
        StringBuilder WorkflowReferenceUniqueTxt = new StringBuilder();
        Random rnd = new Random();
        while (WorkflowReferenceUniqueTxt.length() < 6) { // length of the random string.
            int index = (int) (rnd.nextFloat() * WORKFLOWREFERENCECHARS.length());
            WorkflowReferenceUniqueTxt.append(WORKFLOWREFERENCECHARS.charAt(index));
        }
        String strWorkflowReferenceUniqueTxt = WorkflowReferenceUniqueTxt.toString();
        return strWorkflowReferenceUniqueTxt;
    }


    //***************Conditional Running Tests in TestNG based on "AllowExecution"variable in excel*********************
    /**Run test based on YES or NO condition of the AllowExecution variable*/
    public void testExecutionFlag(String AllowExecution)
    {
        if(AllowExecution.equalsIgnoreCase("Y"))
        {
        }else if (AllowExecution.equalsIgnoreCase("N"))
        {
            throw new SkipException("Test is skipped");
        }
    }

    //*************************GENERIC METHODS FOR DOING COMMON ACTIONS IN JAVA & JS************************************
    public boolean isFileDownloaded(String downloadPath, String fileName)
    {
        /*Purpose:
         method takes the download directory and the file name, which will check for the file name mention in the
         directory and will return 'True' if the document is available in the folder else 'false'. When we are sure of the
         file name, we can make use of this method to verify.*/
        boolean flag = false;
        File dir = new File(downloadPath);
        File[] dir_contents = dir.listFiles();

        for (int i = 0; i < dir_contents.length; i++)
        {
            if (dir_contents[i].getName().equals(fileName))
                return flag=true;
        }
        return flag;
    }



    /**Generic trim Leading And Trailing Characters*/
    public String trimLeadingAndTrailingCharacters(String textToTrim,int indexOfStartingPosition,int indexOfEndingPosition )
    {
        //Count the chars in the string
        int charCounter = textToTrim.length();

        //Trim preceding and trailing characters by starting and ending index position
        String trimmedString = StringUtils.substring(textToTrim,indexOfStartingPosition,indexOfEndingPosition);

        return trimmedString;
    }

    /**Trim text based on index position*/
    public String readTextAndTrim(WebDriver webDriver,By elementLocation,String strText,int startFromIndexPosition)
    {
        strText = webDriver.findElement(elementLocation).getText();

        //Count the chars in the string
        int charCounter = strText.length();

        //Trim preceding characters by starting(Position 12 of our string) from an index position to the max-length of the string
        String strTextTrimmed = strText.substring(startFromIndexPosition,charCounter);

        System.out.println("INFORMATION:: The trimmed text is "+strTextTrimmed);
        return strTextTrimmed;
    }

    /**To Create Dynamic file names based on Time Stamp*/
    public static String getCurrentFormattedDate()
    {
        // Create object of SimpleDateFormat class and decide the format
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");//dd/MM/yyyy
        //get current date time with Date()
        Date now = new Date();
        // Now format the date
        String strDate = sdfDate.format(now);

        return strDate;
    }

    /**To Create Dynamic file names based on Time Stamp*/
    public static String getDateFormatForSettlementNotification()
    {
        // Create object of SimpleDateFormat class and decide the format
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");//dd/MM/yyyy
        //get current date time with Date()
        Date now = new Date();
        // Now format the date
        String strDate = sdfDate.format(now);

        return strDate;
    }

    /**Set focus on element*/
    public void setFocus(By elementLocation){
        JavascriptExecutor js = null;
        //js.executeScript ("document.getElementById('x').focus()");
        js.executeScript("document.elementLocation.focus()");
    }

    /**Create a string with current date as part of it name*/
    public static String createNameWithCurrentDate(String TestName,String DebtorPostingOption, String BankCountry, String TestTransactionCategory, String DebtorCurrency, String DebtorPurposeOfPayment,  String TestCaseScenariotype, String strMessageName) throws InterruptedException {

        testArtifactName = null;
        //String strPrefix ="T";
        //String strTestNumber= row.getCell(1).toString();
        String strTestName = TestName;
        String strTestTransactionCategory = TestTransactionCategory;
        String strTestCaseScenarioType = TestCaseScenariotype;
        String strDateAndTime = new SimpleDateFormat("ddMMyyyy_HHmmss").format(Calendar.getInstance().getTime());
        String strBankCountry = BankCountry;
        String strPurposeOfPayment = DebtorPurposeOfPayment;
        String strDebtorCurrency = DebtorCurrency;
        String strDebtorPostingOption = DebtorPostingOption;

        testArtifactName = strTestName + "_" + DebtorPostingOption + "_" + strBankCountry + "_" + strTestTransactionCategory + "_" + strDebtorCurrency + "_" + strPurposeOfPayment + "_" + strTestCaseScenarioType + "_" + strDateAndTime  + "_" + strMessageName;
        System.out.println(testArtifactName);
        return testArtifactName;
    }


    public static String getPacs002PayloadForSingleTxs() throws IOException
    {
        String fileName = null;

        if(testArtifactName.contains("Pacs002"))
        {
            //Set the File Name to the current name string that has been created by the test from the "createNameWithCurrentDate" method
            fileName = testArtifactName;
        }

        //concatenate the File path location to the file name to get the full file path
        String strPacs002FilePath2 = "./testOutputs/pacs002/"+fileName;

        //Read all the content of the file(as xml)
        String strPacs002PayloadForSingleTxs = FileUtils.readFileToString(new File(strPacs002FilePath2), "UTF-8");

        //Return the content of the file as a string variable
        return strPacs002PayloadForSingleTxs;
    }



    public class linkDoesNotExistException extends Exception
    {
        public linkDoesNotExistException() {
            System.out.println("Link Does Not Exist!");
        }
    }


    /**Used to handle popup */
    public void handleErrorPopUp(String strXpath) throws Exception {

        try{
            Boolean welcomePop = isElementDisplayed(strXpath);

            if(welcomePop==true){
                ExtentTestManager.getTest().fail(MarkupHelper.createLabel("PPO error occurred,please check PopUp", ExtentColor.RED));
                screenshotLoggerMethod("PopUp screenshot","PPO error occurred,please check PopUp");
                stopCurrentTestSetResultsToFailedAndContinueWithNextTest();
            }
        }catch (Exception e){
            System.out.println("No Error Pop");
        }
    }

    /**Used to write to a file */
    public void writeTextToFile(String strFilePath,String strDynamicFileName,String strFileContents)
    {
        try
        {
            File file = new File(strFilePath+strDynamicFileName);
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(strFileContents);
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    //*********************************** THESE ARE METHODS TO PARSE XML(s) ********************************************
    public static void ParseKnownXMLStructure(String strInputFile) throws ParserConfigurationException, IOException, SAXException, XPathExpressionException
    {

        //Get Document Builder
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();

        //Build Document
        //Document document = builder.parse(new File("./test-artifacts/test_evidence_files/Login_Test_OnUs_Positive_06112018_132023_ZMB_PAIN001.xml"));
        Document document = builder.parse(new File(strInputFile));

        //Normalize the XML Structure; It's just too important !!
        document.getDocumentElement().normalize();


        //Here comes the root node
        Element root = document.getDocumentElement();
        System.out.println(root.getNodeName());

//    //From the XPath object we’ll access the expressions and execute them over our document to extract what we need from it
//    XPath xPath =  XPathFactory.newInstance().newXPath();
//    //Example String expression = "/Document/CstmrPmtStsRpt/GrpHdr";
//    String expression = "/employees/employee/firstName";
//    //We can compile an XPath expression passed as string and define what kind of data we are expecting to receive such a NODESET, NODE or String for example
//    NodeList nList = (NodeList) xPath.compile(expression).evaluate(document, XPathConstants.NODESET);

        //Get all employees
        NodeList nList = document.getElementsByTagName("employee");
        System.out.println("============================");

        for (int temp = 0; temp < nList.getLength(); temp++)
        {
            Node node = nList.item(temp);
            System.out.println("");    //Just a separator
            if (node.getNodeType() == Node.ELEMENT_NODE)
            {
                //Print each employee's detail
                Element eElement = (Element) node;
                System.out.println("Employee id : "    + eElement.getAttribute("id"));
                System.out.println("First Name : "  + eElement.getElementsByTagName("firstName").item(0).getTextContent());
                System.out.println("Last Name : "   + eElement.getElementsByTagName("lastName").item(0).getTextContent());
                System.out.println("LOcation : "    + eElement.getElementsByTagName("location").item(0).getTextContent());
            }
        }
    }


    public static void ParseUnknownXMLStructure() throws ParserConfigurationException, IOException, SAXException
    {

        //Get Docuemnt Builder
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();

        //Build Document
        Document document = builder.parse(new File("./test-artifacts/test_evidence_files/Login_Test_OnUs_Positive_06112018_132023_ZMB_PAIN001.xml"));

        //Normalize the XML Structure; It's just too important !!
        document.getDocumentElement().normalize();

        //Here comes the root node
        Element root = document.getDocumentElement();
        System.out.println(root.getNodeName());

        //Get all employees
        NodeList nList = document.getElementsByTagName("employee");
        System.out.println("============================");

        visitChildNodes(nList);
    }

    //This function is called recursively
    private static void visitChildNodes(NodeList nList)
    {
        for (int temp = 0; temp < nList.getLength(); temp++)
        {
            Node node = nList.item(temp);
            if (node.getNodeType() == Node.ELEMENT_NODE)
            {
                System.out.println("Node Name = " + node.getNodeName() + "; Value = " + node.getTextContent());
                //Check all attributes
                if (node.hasAttributes())
                {
                    // get attributes names and values
                    NamedNodeMap nodeMap = node.getAttributes();
                    for (int i = 0; i < nodeMap.getLength(); i++)
                    {
                        Node tempNode = nodeMap.item(i);
                        System.out.println("Attr name : " + tempNode.getNodeName()+ "; Value = " + tempNode.getNodeValue());
                    }
                    if (node.hasChildNodes())
                    {
                        //We got more childs; Let's visit them as well
                        visitChildNodes(node.getChildNodes());
                    }
                }
            }
        }
    }

    public static void ParseUnknownXMLStructure2() throws ParserConfigurationException, IOException, SAXException
    {
        try
        {

            File file = new File("./test-artifacts/test_evidence_files/Login_Test_OnUs_Positive_06112018_132023_ZMB_PAIN001.xml");
            DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = dBuilder.parse(file);
            System.out.println("Root element :" + doc.getDocumentElement().getNodeName());

            if (doc.hasChildNodes())
            {
                printNote(doc.getChildNodes());
            }
        } catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }

    private static void printNote(NodeList nodeList)
    {

        for (int count = 0; count < nodeList.getLength(); count++)
        {
            Node tempNode = nodeList.item(count);
            // make sure it's element node.
            if (tempNode.getNodeType() == Node.ELEMENT_NODE)
            {
                // get node name and value
                System.out.println("\nNode Name =" + tempNode.getNodeName() + " [OPEN]");
                System.out.println("Node Value =" + tempNode.getTextContent());
                if (tempNode.hasAttributes())
                {
                    // get attributes names and values
                    NamedNodeMap nodeMap = tempNode.getAttributes();
                    for (int i = 0; i < nodeMap.getLength(); i++)
                    {
                        Node node = nodeMap.item(i);
                        System.out.println("attr name : " + node.getNodeName());
                        System.out.println("attr value : " + node.getNodeValue());
                    }
                }

                if (tempNode.hasChildNodes())
                {
                    // loop again if has child nodes
                    printNote(tempNode.getChildNodes());
                }
                System.out.println("Node Name =" + tempNode.getNodeName() + " [CLOSE]");
            }
        }
    }

    public static void ParseUnknownXMLStructure3() throws ParserConfigurationException, IOException, SAXException
    {
        try (InputStream stream = new FileInputStream("./test-artifacts/test_evidence_files/Login_Test_OnUs_Positive_06112018_132023_ZMB_PAIN001.xml"))
        {
            XMLInputFactory inputFactory = XMLInputFactory.newFactory();
            inputFactory.setProperty(XMLInputFactory.IS_COALESCING, true);

            XMLStreamReader reader = inputFactory.createXMLStreamReader(stream);

            while (reader.hasNext())
            {
                switch (reader.next())
                {
                    case XMLStreamConstants.START_ELEMENT:
                        System.out.println("Start " + reader.getName());
                        for (int i = 0, count = reader.getAttributeCount(); i < count; i++)
                        {
                            System.out.println(reader.getAttributeName(i) + "=" + reader.getAttributeValue(i));
                        }
                        break;
                    case XMLStreamConstants.END_ELEMENT:
                        System.out.println("End " + reader.getName());
                        break;
                    case XMLStreamConstants.CHARACTERS:
                    case XMLStreamConstants.SPACE:
                        String text = reader.getText();
                        if (!text.trim().isEmpty())
                        {
                            System.out.println("text: " + text);
                        }
                        break;
                }
            }
        } catch (XMLStreamException e)
        {
            e.printStackTrace();
        }
    }
    //************************************Solve Captcha maths problem***************************************************
    // Solve the  Captcha question
    public void solveCaptchaCalculation(By captchaQuestionElementLocation,By captchaAnswerElementLocation) throws InterruptedException
    {
        try
        {
            //Get the actual string from the page
            String captchaQuestion = readText(captchaQuestionElementLocation);

            //Get the all the digits as strings
            String strFirstDigit = captchaQuestion.substring(0, captchaQuestion.indexOf(' '));//From the 0 index,get all characters before the occurrence of any WHITESPACES
            String strSecondDigit = captchaQuestion.substring(captchaQuestion.indexOf("+")+1).replace(" ","");//Get all characters after the + sign & remove all WHITESPACES

            //Convert the strings to integers
            int intFirstDigit = Integer.parseInt(strFirstDigit);
            int intSecondDigit = Integer.parseInt(strSecondDigit);

            //Compute the numbers as per the captcha question
            int intFinalAnswer = intFirstDigit + intSecondDigit;
            String strFinalAnswer = Integer.toString(intFinalAnswer);

            //Enter the answer in the text box on the page
            writeText((captchaAnswerElementLocation),strFinalAnswer);
            Thread.sleep(3000);
        }catch (Exception e)
        {
            ExtentTestManager.getTest().fail(MarkupHelper.createLabel("Failed to solve captcha maths "+e, ExtentColor.RED));
        }
    }

    //************************************Remove all text between XML node**********************************************
    public String removeTextOrValuesInBetweenXMLElements(String xmlToProcess)
    {
        /*The regular expression is used to replace all the node content(content between ><) and only leave the nodes tags,
         * because we want to compare the tags only*/
        String processedString = xmlToProcess.replaceAll(">.*?<", "><");
        return processedString;
    }


    public String removeNodesAndWhitespacesFromXML(String xmlToProcess)
    {
        /*The regular expression is used to replace all the node tags,whitespace,indentations and only leave the content,
         * because we want to compare the content only*/
        String processedString = xmlToProcess.replaceAll("\\s+<","<");
        return processedString;
    }

    //************** Get the AB Number or UserName from the machine running tests***************************************
    public static String computerUserName(){
        String computerUserName="";
        computerUserName=System.getProperty("user.name");

        return computerUserName;
    }

    /**system reference string generator*/
    public static String systemReferenceGenerator()
    {
        String CurrentIterationSystemReference = UUID.randomUUID().toString();
        systemReference = CurrentIterationSystemReference.toString();
        return systemReference;
    }

    /** Sender Reference generator **/
    public static String senderReference() {
        int len=11;
        String chars = "0123456789";

        Random rnd = new Random();
        StringBuilder sb = new StringBuilder(len);

        for (int i = 0; i < len; i++)
            sb.append(chars.charAt(rnd.nextInt(chars.length())));
        paymentInfoID = "SND_"+sb;

        return paymentInfoID;
    }

    //TODO:: Create a unique narration as per length specified - DONE
    /** Generate Unique Narration **/
    public static String TransactionNarration(String length)
    {
        if(length.equalsIgnoreCase("1Line")){
            strUniqueTransactionNarration = generateUniqueNarrationSingleLine();
        }else {
            strUniqueTransactionNarration = generateUniqueNarrationMultiLines();
        }
        return strUniqueTransactionNarration;
    }
    public static String generateUniqueNarrationSingleLine(){
        String narrationChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890abcdefghijklmnopqrstuvwxyz";
        StringBuilder NarrationUniqueTxt = new StringBuilder();
        Random rnd = new Random();

        while(NarrationUniqueTxt.length()<12){
            int index = (int) (rnd.nextFloat()*narrationChars.length());
            NarrationUniqueTxt.append(narrationChars.charAt(index));
        }
        String strNarrationUniqueTxt = NarrationUniqueTxt.toString();
        System.out.println(strNarrationUniqueTxt);
        return  strNarrationUniqueTxt;
    }
    public static String generateUniqueNarrationMultiLines(){
        String strNarrationUniqueTxt="";
        ArrayList narrationList = new ArrayList();
        String strNarration = "";
        String narrationChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890abcdefghijklmnopqrstuvwxyz";

        int maxNoOfLines = 11;
        int countLines = 0;
        do{
            StringBuilder NarrationUniqueTxt1 = new StringBuilder();
            StringBuilder NarrationUniqueTxt2 = new StringBuilder();
            StringBuilder NarrationUniqueTxt3 = new StringBuilder();
            Random rnd = new Random();

            while (NarrationUniqueTxt1.length() < 7) {
                int index = (int) (rnd.nextFloat() * narrationChars.length());
                NarrationUniqueTxt1.append(narrationChars.charAt(index));
            }
            while (NarrationUniqueTxt2.length() < 3) {
                int index = (int) (rnd.nextFloat() * narrationChars.length());
                NarrationUniqueTxt2.append(narrationChars.charAt(index));
            }
            while (NarrationUniqueTxt3.length() < 6) {
                int index = (int) (rnd.nextFloat() * narrationChars.length());
                NarrationUniqueTxt3.append(narrationChars.charAt(index));
            }
            strNarrationUniqueTxt = NarrationUniqueTxt1.toString() + " " + NarrationUniqueTxt2.toString() + " " + NarrationUniqueTxt3.toString()+"\n";

            narrationList.add(strNarrationUniqueTxt);
            strNarration = narrationList.toString().replaceAll("(^\\[|\\]$)", "").replaceAll("[$,]","");

            countLines++;

        }
        while (countLines < maxNoOfLines);
        System.out.println(strNarration);
        return strNarration;
    }


}


