package pages;


import config.Settings;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utils.extentreports.ExtentTestManager;
import utils.logs.Log;

/**
 * @author Jonathan Mbuyi Tshitenda [ ABJT439 ]
 * @date created 9/9/2019
 * @package pages
 */
public class WelcomePage extends BasePage {
    /**
     * Constructor
     */
    public WelcomePage(WebDriver driver) {
        super(driver);
    }



    /**
     * Web Elements
     */
    String paymentCaptureTabId = "payment-capture";
    String paymentWelcomeTabId = "welcome";
    String paymentSearchPaymentTabId = "payment-search";
    String userLogoutIconXpath = "/html/body/app-root/div/div/sol-header/div/sol-header-masthead/div[3]/div/div/ul/li[1]/span";
    String userLogoutButton = "logOutButton";

    /**
     * Page Methods
     */

    public void verifyPresenceOfMenuTabs() throws InterruptedException
    {

        //Make sure the page is ready for usage
        //Thread.sleep(5000);

        click(By.id(paymentCaptureTabId), By.xpath("//*[text()='Clear All']"));
    }

    public void verifyPresenceOfMenuTabs2() throws InterruptedException
    {

        //Make sure the page is ready for usage
        if(ElementIsPresent(By.id(paymentCaptureTabId)))
        {

            click(By.id(paymentCaptureTabId), By.xpath("//*[text()='Clear All']"));
        }else{
            refreshBrowser();

            click(By.id(paymentCaptureTabId), By.xpath("//*[text()='Clear All']"));
        }
    }

    public void logout() throws Exception
    {

        try
        {
            //Click on the icon to focus on it
            //Thread.sleep(5000);
            // SetScriptTimeout();
            scrollElementIntoViewByCoordinates(By.xpath(userLogoutIconXpath));
            click(By.xpath(userLogoutIconXpath), By.id(userLogoutButton));
            //Thread.sleep(5000);

            //Click on the Logout Button after gaining focus
            // SetScriptTimeout();
            click(By.id(userLogoutButton), By.xpath("//*[@id='kc-login']"));
        }
        catch (Exception e)
        {
            //ExtentTestManager.getTest().fatal("EXCEPTION:: Login not successful  "+ e.getMessage());
           // screenshotLoggerMethod("logout","Welcome Page logout process");
            stopCurrentTestSetResultsToFailedAndContinueWithNextTest();
        }
    }
}
