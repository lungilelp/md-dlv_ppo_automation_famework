package base;

/**
 * @author Jonathan Mbuyi Tshitenda [ ABJT439 ]
 * @date created 10/4/2019
 * @package com.jmt.framework.base
 */
public class DriverContext
{

    /**
     * Create a Public Driver object that will be used across the entire framework when there is need to use the Selenium Driver
     * This class is responsible for bringing in our web-driver and browser class and all other globally used objects
     **/


    /*******************Wrapper Methods ***********************************************************************************
     /**Wrapper method to perform opening browser and go to url*/
    public static void GoToUrl(String url)
    {
        LocalDriverContext.getRemoteWebDriver().get(url);
    }

    public static void QuitDriver(){
        LocalDriverContext.getRemoteWebDriver().quit();
    }


    /**THE BELOW CODE WORKS ON THE NEW JAVA 12 AND 1.7 COMPILER,THIS MUST BE SETUP IN INTELLIJ BEFORE RUNNING THE TEST*/
//    public static void WaitForPageToLoad(){
//        var wait= new WebDriverWait(LocalDriverContext.getRemoteWebDriver(), 30);
//        var jsExecutor = LocalDriverContext.getRemoteWebDriver();
//
//        ExpectedCondition<Boolean> jsLoad = webDriver ->  (LocalDriverContext.getRemoteWebDriver())
//                .executeScript("return document.readyState").toString().equals("complete");
//
//        //Get JS Ready
//        boolean jsReady = jsExecutor.executeScript("return document.readyState").toString().equals("complete");
//
//        if(!jsReady)
//            wait.until(jsLoad);
//        else
//            Settings.Logs.Write("Page is ready !");
//    }
//
//    public  static  void WaitForElementVisible(final WebElement elementFindBy){
//        WebDriverWait wait= new WebDriverWait(LocalDriverContext.getRemoteWebDriver(), 30);
//        wait.until(ExpectedConditions.visibilityOf(elementFindBy));
//    }
//
//    public static void WaitForElementTextVisible(final WebElement elementFindBy, String text){
//        WebDriverWait wait= new WebDriverWait(LocalDriverContext.getRemoteWebDriver(), 30);
//        wait.until(ExpectedConditions.textToBePresentInElement(elementFindBy, text));
//    }
//
//    public static void WaitUntilTextDisplayed(final By element, String text){
//        WebDriverWait wait = new WebDriverWait(LocalDriverContext.getRemoteWebDriver(),30);
//        wait.until(textDisplayed(element, text));
//    }
//
//    private static ExpectedCondition<Boolean> textDisplayed (final By elementFindBy, final String text){
//        return webDriver -> webDriver.findElement(elementFindBy).getText().contains(text);
//    }
//
//    public static void WaitElementEnabled(final By elementFindBy){
//        WebDriverWait wait = new WebDriverWait(LocalDriverContext.getRemoteWebDriver(),30);
//        wait.until(webDriver -> webDriver.findElement(elementFindBy).isEnabled());
//    }
}
