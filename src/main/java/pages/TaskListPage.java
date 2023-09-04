package pages;

import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utils.extentreports.ExtentTestManager;
import utils.logs.Log;

/**
 * @author Jonathan Mbuyi Tshitenda [ ABJT439 ]
 * @date created 9/10/2019
 * @package pages
 */
public class TaskListPage extends BasePage {
    public TaskListPage(WebDriver driver) {

        super(driver);
        Log.info("The current working class: "+this.getClass().getName());
    }

    //***********************************WEB ELEMENTS*******************************************************************
    //******************************************************************************************************************
    String taskListTabId = "task-list";
    String transactionTableXpath = "//*[@id='taskListDatatable']/div/datatable-body/datatable-selection/datatable-scroller/datatable-row-wrapper/datatable-body-row";
    String taskListTableXpath = ".//*[@id='taskListDatatable']/div/datatable-body/datatable-selection/datatable-scroller/datatable-row-wrapper/datatable-body-row";
    String taskListTablePagerXpath = "//*[@id=\"taskListDatatable\"]/div/datatable-footer/div/datatable-pager/ul";
    String taskListTablePagerSkipIconCSS = ".datatable-icon-skip";
    String manualUploadTasklistButton ="//div[contains(text(),'Manual Upload Task List')]";
    //******************************************************************************************************************

    /*****************Dynamic Xpath Elements**********************************/
    String before_taskListChevronButtonXpath = ".//*[@id='taskListDatatable']/div/datatable-body/datatable-selection/datatable-scroller/datatable-row-wrapper[";
    String after_taskListChevronButtonXpath = "]/datatable-body-row/div[2]/datatable-body-cell[6]/div/div/div";
    String dueDateHeaderXpath = "//*[@id='taskListDatatable']/div/datatable-header/div/div[2]/datatable-header-cell[3]/div/span";
    String before_skipToLastPageButtonXpath = "//*[@id=\"taskListDatatable\"]/div/datatable-footer/div/datatable-pager/ul/li[";
    String after_skipToLastPageButtonXpath = "]/a/i";
    /*************************************************************************/
    public String strSearchString = null;


    //***********************************STEPS**************************************************************************
    //******************************************************************************************************************
    /**Navigate the tasklisk-Table to find the page where the transaction is located*/
    public TaskListPage navigateToLastPageOnTaskList(String TechnicalChannel) throws InterruptedException
    {
        try
        {
            //Make sure the page is ready for usage
            Thread.sleep(5000);
            click(By.id(taskListTabId));
            SetScriptTimeout();
            Thread.sleep(10000);


            if(TechnicalChannel.contains("PPU")){
                click(By.xpath("/html/body/app-root/div/div/div/task-list/div/div/div[1]/div[1]/span/button"));
                Thread.sleep(5000);
            }

            //Click on the Skip button to go to the last page
            scrollDown();
            scrollDown();
            Thread.sleep(5000);

            //Check the page count
            String strPageCount = driver.findElement(By.xpath("//*[@id=\"taskListDatatable\"]/div/datatable-footer/div/div")).getText();
            if(strPageCount.equalsIgnoreCase("0 total") )
            {
                ExtentTestManager.getTest().fail(MarkupHelper.createLabel("The are no items on the tasklist ", ExtentColor.RED));
                //Take Screenshot
                screenshotLoggerMethod("navigateToLastPageOnTaskList","Navigate To Last Page On Task List");

                //Stop the test
                stopCurrentTestSetResultsToFailedAndContinueWithNextTest();
            }
            else
            {
                boolean pageNavigations = isElementDisplayed(".//*[@id='taskListDatatable']/child::div/descendant::datatable-footer/descendant::div/descendant::datatable-pager/parent::div/child::datatable-pager/descendant::i[4]");
                if(pageNavigations)
                {
                    screenshotLoggerMethod("PopUp screenshot","PPO error occurred,please check PopUp");
                    click(By.xpath(".//*[@id='taskListDatatable']/child::div/descendant::datatable-footer/descendant::div/descendant::datatable-pager/parent::div/child::datatable-pager/descendant::i[4]"));
                }else
                {
                    ExtentTestManager.getTest().pass(MarkupHelper.createLabel("The task list has no extra pages", ExtentColor.BLUE));
                }
            }

        }catch (Exception e)
        {
            ExtentTestManager.getTest().log(Status.FAIL, "INFORMATION:: "+e);
        }

        return this; }



    /**Search for Workflow Reference on the Task-List Table and open that specific transaction and authorize it*/
    public TaskListPage searchTheTransactionToBeAuthorized(String strWorkflowReferenceUniqueTxt) throws Exception
    {
        Thread.sleep(1500);

        //Verify that the String is present on the task list
        boolean SearchString = isElementDisplayed("//div[contains(text(),'"+strWorkflowReferenceUniqueTxt+"')]");

        if(SearchString)
        {
            //Get the text string on the table
            strSearchString = driver.findElement(By.xpath("//div[contains(text(),'"+strWorkflowReferenceUniqueTxt+"')]")).getText();
            int i = 0;
            int intTaskListPageWaitTimeIndex = 2;

            try
            {
                //Retry the execution if the string is not found on the first attempt
                do
                {
                    if (strSearchString != null)
                    {
                        /**INFO*/
                        ExtentTestManager.getTest().log(Status.PASS, "Status=PASS: Transaction is displayed ont the task-list");

                        //Search the Task List table for our transaction and click on the chevron button to authorize the transaction
                        click(By.xpath("//div[contains(text(),'"+strWorkflowReferenceUniqueTxt+"')]/ancestor::div/parent::datatable-body-row/child::div[2]/descendant::div[11]"));//(Ancestor/Parent/Child/descendant)
                        SetScriptTimeout();
                        break;

                    } else
                    {
                        ExtentTestManager.getTest().log(Status.WARNING, "Warning:: the string was not found on the first attempt ,the script will attempt to retry in 30 seconds");
                        Thread.sleep(2000);
                        i++;
                    }
                }while (i <=intTaskListPageWaitTimeIndex);


                /**Stop the test if current transaction is not found on the task-list*/
                if(strSearchString==null & i>intTaskListPageWaitTimeIndex)
                {
                    ExtentTestManager.getTest().fail(MarkupHelper.createLabel("Transaction not found on Task-list,The test has been stopped", ExtentColor.RED));
                    screenshotLoggerMethod("Task-List screenshot","Transaction not found on Task-list");
                    stopCurrentTestSetResultsToFailedAndContinueWithNextTest();
                }

            }catch (Exception e)
            {
                ExtentTestManager.getTest().log(Status.FAIL, "INFORMATION:: "+e);
            }

        }else
        {
            ExtentTestManager.getTest().fail(MarkupHelper.createLabel("Transaction is not visible on the task list", ExtentColor.RED));
            screenshotLoggerMethod("Tasklist_Transaction screenshot","Tasklist_Transaction is not visible");
            stopCurrentTestSetResultsToFailedAndContinueWithNextTest();
        }

        return this;}

    //Click on manual upload task list button
    public TaskListPage clickManualTaskListButton(){
        click(By.xpath(manualUploadTasklistButton));

        return this;}

}
