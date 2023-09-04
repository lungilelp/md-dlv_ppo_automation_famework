package helpers;

import base.BasePage;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;
import utils.extentreports.ExtentTestManager;
import utils.logs.Log;

import java.io.IOException;
import static helpers.Validate1stPACS008Module.*;
import static helpers.Validate1stPAIN001Module.strPain001EndToEndIDs;
import static utils.extentreports.ExtentTestManager.codeLogsXML;

/**
 * @author Jonathan Mbuyi Tshitenda [ ABJT439 ]
 * @date created 9/10/2019
 * @package helpers
 */
public class CreatePacs002XML extends BasePage {

    public CreatePacs002XML(WebDriver driver) {

        super(driver);
        Log.info("The current working class: "+this.getClass().getName());
    }
    public String strPacs002FilePath = "./testOutputs/pacs002/";
    public static String strPacs002PayloadForSingleTxs = null;
    public static String strPacs002PayloadForMultipleTxs = null;

    String strPacs008EndToEndIDs1 = Validate1stPACS008Module.strPacs008EndToEndIdForOffUsArray[0];//Get EndToEndId from Pain001 validations
    String strPacs008EndToEndIDs2 = Validate1stPACS008Module.strPacs008EndToEndIdForOffUsArray[1];//Get EndToEndId from Pain001 validations

    @Test
    public void createPacs002XMLForSingleTxs(String strCurrentPaymentID ,String fileName,String ExpectedFirstPacs002FIToFIPmtStsRptTxInfAndStsTxSts,String ExpectedFirstPacs002FIToFIPmtStsRptTxInfAndStsStsRsnInfRsnCd) throws IOException
    {

        //*************************************************Declare the Know XML File Structure as a String and Pass variables in it*************************************
        strPacs002PayloadForSingleTxs=
                "<Document xmlns=\"urn:iso:std:iso:20022:tech:xsd:pacs.002.001.09\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n" +
                        "   <FIToFIPmtStsRpt>\n" +
                        "      <GrpHdr>\n" +
                        "         <MsgId>"+strPacs008OrgnlMsgId+"</MsgId>\n" +
                        "         <CreDtTm>2019-08-08T09:19:07</CreDtTm>\n" +
                        "      </GrpHdr>\n" +
                        "      <TxInfAndSts>\n" +
                        "         <OrgnlGrpInf>\n" +
                        "            <OrgnlMsgId>"+strPacs008OrgnlMsgId+"</OrgnlMsgId>\n" +
                        "            <OrgnlMsgNmId>pacs.008.001.07</OrgnlMsgNmId>\n" +
                        "            <OrgnlCreDtTm>"+strPacs008CreDtTm+"</OrgnlCreDtTm>\n" +
                        "         </OrgnlGrpInf>\n" +
                        "         <OrgnlEndToEndId>"+strPacs008EndToEndIDs+"</OrgnlEndToEndId>\n" +
                        "         <OrgnlTxId>"+strPain001EndToEndIDs+"</OrgnlTxId>\n" +
                        "         <TxSts>"+ExpectedFirstPacs002FIToFIPmtStsRptTxInfAndStsTxSts+"</TxSts>\n" +
                        "         <StsRsnInf>\n" +
                        "            <Rsn>\n" +
                        "               <Cd>"+ExpectedFirstPacs002FIToFIPmtStsRptTxInfAndStsStsRsnInfRsnCd+"</Cd>\n" +
                        "            </Rsn>\n" +
                        "            <AddtlInf>01</AddtlInf>\n" +
                        "         </StsRsnInf>\n" +
                        "      </TxInfAndSts>\n" +
                        "   </FIToFIPmtStsRpt>\n" +
                        "</Document>";
        //********************************************************************************************************************************************************

        //Save the PAYLOAD to a file
        writeTextToFile(strPacs002FilePath,fileName,strPacs002PayloadForSingleTxs);

        ExtentTestManager.getTest().log(Status.PASS, "Status=PASS:: Pacs002 XML Payload below");
        codeLogsXML(strPacs002PayloadForSingleTxs);
    }


    @Test
    public void createPacs002XMLForMultiplexs(String strCurrentPaymentID ,String fileName,String ExpectedFirstPacs002FIToFIPmtStsRptTxInfAndStsTxSts,String ExpectedFirstPacs002FIToFIPmtStsRptTxInfAndStsStsRsnInfRsnCd) throws IOException
    {

        //*************************************************Declare the Know XML File Structure as a String and Pass variables in it*************************************
        strPacs002PayloadForMultipleTxs=
                "<Document xmlns=\"urn:iso:std:iso:20022:tech:xsd:pacs.002.001.09\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n" +
                        "   <FIToFIPmtStsRpt>\n" +
                        "      <GrpHdr>\n" +
                        "         <MsgId>"+strPacs008OrgnlMsgId+"</MsgId>\n" +
                        "         <CreDtTm>2019-08-08T09:19:07</CreDtTm>\n" +
                        "      </GrpHdr>\n" +
                        "      <TxInfAndSts>\n" +
                        "         <OrgnlGrpInf>\n" +
                        "            <OrgnlMsgId>"+strPacs008OrgnlMsgId+"</OrgnlMsgId>\n" +
                        "            <OrgnlMsgNmId>pacs.008.001.07</OrgnlMsgNmId>\n" +
                        "            <OrgnlCreDtTm>"+strPacs008CreDtTm+"</OrgnlCreDtTm>\n" +
                        "         </OrgnlGrpInf>\n" +
                        "         <OrgnlEndToEndId>"+strPacs008EndToEndIdForOffUsArray[0]+"</OrgnlEndToEndId>\n" +
                        "         <OrgnlTxId>"+strPacs008EndToEndIdForOffUsArray[0]+"</OrgnlTxId>\n" +
                        "         <TxSts>"+ExpectedFirstPacs002FIToFIPmtStsRptTxInfAndStsTxSts+"</TxSts>\n" +
                        "         <StsRsnInf>\n" +
                        "            <Rsn>\n" +
                        "               <Cd>"+ExpectedFirstPacs002FIToFIPmtStsRptTxInfAndStsStsRsnInfRsnCd+"</Cd>\n" +
                        "            </Rsn>\n" +
                        "            <AddtlInf>01</AddtlInf>\n" +
                        "         </StsRsnInf>\n" +
                        "      </TxInfAndSts>\n" +
                        "      <TxInfAndSts>\n" +
                        "         <OrgnlGrpInf>\n" +
                        "            <OrgnlMsgId>"+strPacs008OrgnlMsgId+"</OrgnlMsgId>\n" +
                        "            <OrgnlMsgNmId>pacs.008.001.07</OrgnlMsgNmId>\n" +
                        "            <OrgnlCreDtTm>"+strPacs008CreDtTm+"</OrgnlCreDtTm>\n" +
                        "         </OrgnlGrpInf>\n" +
                        "         <OrgnlEndToEndId>"+strPacs008EndToEndIdForOffUsArray[1]+"</OrgnlEndToEndId>\n" +
                        "         <OrgnlTxId>"+strPacs008EndToEndIdForOffUsArray[1]+"</OrgnlTxId>\n" +
                        "         <TxSts>"+ExpectedFirstPacs002FIToFIPmtStsRptTxInfAndStsTxSts+"</TxSts>\n" +
                        "         <StsRsnInf>\n" +
                        "            <Rsn>\n" +
                        "               <Cd>"+ExpectedFirstPacs002FIToFIPmtStsRptTxInfAndStsStsRsnInfRsnCd+"</Cd>\n" +
                        "            </Rsn>\n" +
                        "            <AddtlInf>01</AddtlInf>\n" +
                        "         </StsRsnInf>\n" +
                        "      </TxInfAndSts>\n" +
                        "   </FIToFIPmtStsRpt>\n" +
                        "</Document>";
        //********************************************************************************************************************************************************

        //Save the PAYLOAD to a file
        writeTextToFile(strPacs002FilePath,fileName,strPacs002PayloadForMultipleTxs);

        ExtentTestManager.getTest().log(Status.PASS, "Status=PASS:: Pacs002 XML Payload below");
        codeLogsXML(strPacs002PayloadForMultipleTxs);
    }
}
