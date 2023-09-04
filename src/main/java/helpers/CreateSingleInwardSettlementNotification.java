package helpers;

import base.BasePage;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;
import utils.extentreports.ExtentTestManager;
import utils.logs.Log;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static utils.extentreports.ExtentTestManager.codeLogsXML;

public class CreateSingleInwardSettlementNotification extends BasePage
{    public CreateSingleInwardSettlementNotification (WebDriver driver) {

    super(driver);
    Log.info("The current working class: "+this.getClass().getName());
}

    public String strSettlementNotificationFilePath = "./testOutputs/cbs_settlement/";
    public String strSettlementNotificationForSingleTxs = null;

    LocalDateTime now = LocalDateTime.now();
    DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
    public String formatDateTime = now.format(format);

    @Test
    public void createSettlementNotificationSingleTxn(String fileName)
    {
        //TODO: move this logic to helpers
        strSettlementNotificationForSingleTxs = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "<SettlementDateNotification xmlns=\"http://www.progressoft.com/EFTSDNotification\">\n" +
                "    <MessageInfo xmlns=\"http://www.progressoft.com/EFTSDNotification\">\n" +
                "        <MessageVersion>2</MessageVersion>\n" +
                "    </MessageInfo>\n" +
                "    <SystemReference>"+systemReference+"</SystemReference>\n" +
                "    <SettlementDate>"+formatDateTime+"</SettlementDate>\n" +
                "</SettlementDateNotification>";

        //Save the PAYLOAD to a file
        writeTextToFile(strSettlementNotificationFilePath,fileName,strSettlementNotificationForSingleTxs);

        ExtentTestManager.getTest().log(Status.PASS, "Status=PASS:: Successful Settlement Notification below");
        codeLogsXML(strSettlementNotificationForSingleTxs);
    }
}
