package helpers;

import base.BasePage;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import config.Settings;
import org.openqa.selenium.WebDriver;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import utils.extentreports.ExtentTestManager;
import utils.logs.Log;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;
import java.io.File;
import static Utilities.DatabaseUtil.*;
import static utils.extentreports.ExtentTestManager.codeLogsXML;

/**
 * @author Jonathan Mbuyi Tshitenda [ ABJT439 ]
 * @date created 9/10/2019
 * @package helpers
 */
public class Validate1stPACS008Module extends BasePage {

    public Validate1stPACS008Module(WebDriver driver) {

        super(driver);
        Log.info("The current working class: "+this.getClass().getName());
    }



    /**Global Declarations
     /*Notes:required argumnets
     1. XML File location to test against and Name
     2. PaymentID to pass in the SQL query
     */
    public static String strMySQLPayloadDBColumnName_1 = "PAYLOAD";//Column name in the DB
    public static String strPostgreSQLPayloadDBColumnName_1 = "convert_from";//Column name in the DB
    //public String dbDriver = getConfig().getDatabaseDriver();//TODO MUST WORK FROM SETTINGS
    public String dbDriver = Settings.DBpocDriverJdbc;//TODO MUST WORK FROM SETTINGS
    public String strQueryGetPacs008Payload = null;
    public static String strPacs008OrgnlMsgId = null;
    public static String strPacs008CreDtTm = null;
    public static String strPacs008TxId = null;
    public static String strPacs008EndToEndIDs = null;
    public String strPacs008Payload = null;


    //Store the OffUs EndToEndIDs that will be retrieved,in a Global Variable that is in an Array in order use them dynamically
    public static String strPacs008EndToEndIdForOffUs_1 = null;
    public static String strPacs008EndToEndIdForOffUs_2 = null;
    public static String strPacs008EndToEndIdForOffUsArray[] = {strPacs008EndToEndIdForOffUs_1,strPacs008EndToEndIdForOffUs_2};

    public void validatePacs008Module(String strPain001EndToEndId_1,String strPain001EndToEndId_2, String fileName,String paymentID)
    {
        String BankCountry = Settings.BankCountry;
        //*****************************Get,Validate and store the PAIN001 XML**************************************************************
        try {
            int i = 0;
            int intDatabaseWaitTimeIndex = 2;

            //Retry the SQL Query execution if there is no payload in the DB on the first attempt
            try{
                do
                {
                    /**=================Determine which database to use ======================================*/
                    if(dbDriver.contains("postgresql"))
                    {
                        System.out.println("Using Postgresql database");

                        //Retrieve the PACS008 PAYLOAD from the Database using the unique message ID
                        strQueryGetPacs008Payload = "select m.message_value,m.message_type, t.status ,t.status_reason, t.account_number,t.branch_code, " +
                                "convert_from(loread(lo_open(message_value::int, x'40000'::int), x'40000'::int), 'UTF8') " +
                                "from mk_"+BankCountry+"_payments_portal_sit.payment p " +
                                "join mk_"+BankCountry+"_payments_portal_sit.message m on p.payment_id = m.payment_id " +
                                "join mk_"+BankCountry+"_payments_portal_sit.instruction i on p.id = i.payment_id " +
                                "join mk_"+BankCountry+"_payments_portal_sit.transaction t on t.instruction_id = i.id " +
                                "where p.payment_id  = '" + paymentID +"' " +
                                "and convert_from(loread(lo_open(message_value::int, x'40000'::int), x'40000'::int), 'UTF8') like '%<Document xmlns=\"urn:iso:std:iso:20022:tech:xsd:pain.002.001.08\">%' " +
                                "and convert_from(loread(lo_open(message_value::int, x'40000'::int), x'40000'::int), 'UTF8') not like '%pain.001.001.05%' ORDER BY m.message_value ASC";


                        //Store the PAYLOAD in a string
                        strPacs008Payload = databaseHandlerForPocDB(strQueryGetPacs008Payload, strPostgreSQLPayloadDBColumnName_1);

                    }else if(dbDriver.contains("mysql"))
                    {
                        System.out.println("Using MySQL database");

                        //Retrieve the PACS008 PAYLOAD from the Database using the unique message ID
                        strQueryGetPacs008Payload = "SELECT * FROM TRANSACTION_AUDIT_EVENT WHERE PAYLOAD LIKE '%"+ strPain001EndToEndId_1 +"%' AND TRANSACTION_TYPE='OUTWARD_CREDIT' ORDER BY TRANSACTION_DATE DESC";//Escaped quotes("") with \ (a backslash in front of the quotes)

                        //Store the PAYLOAD in a string
                        strPacs008Payload = databaseHandlerForAuditDB(strQueryGetPacs008Payload, strMySQLPayloadDBColumnName_1);
                    }


                    //Write/Save the PAYLOAD to a file
                    if (strPacs008Payload !=null)
                    {
                        //Print out the PAYLOAD
                        System.out.println("===================================PACS008 PAYLOAD FROM DB================================");
                        System.out.println("Info: Current PAYLOAD " +strPacs008Payload);
                        /**INFO*/
                        ExtentTestManager.getTest().log(Status.PASS, "Info: Pacs008 XML Payload below");
                        codeLogsXML(strPacs008Payload);

                        //Write/Save the PAYLOAD to a file
                        writeTextToFile(strPacs008FilePath,fileName,strPacs008Payload);
                        break;
                    }
                    else {
                        //ExtentTestManager.getTest().log(Status.WARNING, "Warning:: No Records Where Found In The Database on the 1st attempt ,the script will attempt to retry in 20 seconds");
                        //TODO: Marking the soft red flags / system poor bahavior in a report instead of above line use below
                        ExtentTestManager.getTest().warning(MarkupHelper.createLabel("Warning:: No Records Where Found In The Database on the 1st attempt ,the script will attempt to retry in 20 seconds Pacs008", ExtentColor.AMBER));
                        Thread.sleep(20000);
                        i++;
                    }
                }while (i <=intDatabaseWaitTimeIndex);

                /**Stop the test if there are no records in the DB after the allocated time*/
                if(strPacs008Payload==null & i>intDatabaseWaitTimeIndex)
                {
                    ExtentTestManager.getTest().fail(MarkupHelper.createLabel("No Records Where Found In The Database ,The test has been stopped Pacs008", ExtentColor.RED));
                    stopCurrentTestSetResultsToFailedAndContinueWithNextTest();
                }


            }catch (Exception e){
                ExtentTestManager.getTest().log(Status.FAIL, "Status=FATAL: No Records Where Found In The Database.File not created Pacs008");
            }

            //*****************************PARSE THE XML**************************************************************************************
            //Get Document Builder
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            //Build Document
            Thread.sleep(3000);
            Document document = builder.parse(new File(strPacs008FilePath+fileName));

            //Normalize the XML Structure; It's just too important !!
            document.getDocumentElement().normalize();

            //Here comes the root node
            Element root = document.getDocumentElement();

            //Get all Contents
            NodeList nList = document.getElementsByTagName("FIToFICstmrCdtTrf");


            for (int temp = 0; temp < nList.getLength(); temp++)
            {
                Node node = nList.item(temp);
                System.out.println("");    //Just a separator
                if (node.getNodeType() == Node.ELEMENT_NODE)
                {
                    System.out.println("============================== PACS008 XML Contents ======================================");
                    System.out.println(root.getNodeName());

                    //Print each employee's detail
                    Element eElement = (Element) node;
                    System.out.println("Pacs008 MsgId : "  + eElement.getElementsByTagName("MsgId").item(0).getTextContent());
                    strPacs008OrgnlMsgId = eElement.getElementsByTagName("MsgId").item(0).getTextContent();
                    /**INFO*/
                    ExtentTestManager.getTest().log(Status.INFO, "Info: Pacs008 Message ID is"+"  ::" +strPacs008OrgnlMsgId);

                    System.out.println("Pacs008 CreDtTm: "  + eElement.getElementsByTagName("CreDtTm").item(0).getTextContent());
                    strPacs008CreDtTm =  eElement.getElementsByTagName("CreDtTm").item(0).getTextContent();
                    /**INFO*/
                    ExtentTestManager.getTest().log(Status.INFO, "Info:: Pacs008 Creation Time is"+"  ::"+ strPacs008CreDtTm);

                    System.out.println("Pacs008 EndToEndId: "  + eElement.getElementsByTagName("EndToEndId").item(0).getTextContent());
                    strPacs008EndToEndIDs =  eElement.getElementsByTagName("EndToEndId").item(0).getTextContent();
                    /**INFO*/
                    ExtentTestManager.getTest().log(Status.INFO, "Info:: Pacs008 End To EndId is"+"  ::"+ strPacs008EndToEndIDs);

                    System.out.println("Pacs008 TxId :" +eElement.getElementsByTagName("TxId").item(0).getTextContent());
                    strPacs008TxId = eElement.getElementsByTagName("TxId").item(0).getTextContent();
                    /**INFO*/
                    ExtentTestManager.getTest().log(Status.INFO, "Info: Pacs008 Txid is"+"  ::" +strPacs008TxId);
                    System.out.println("==========================================================================================");
                }
            }}catch (Exception e){
            ExtentTestManager.getTest().log(Status.FAIL, "Exception=FATAL: Database data retrieval failed :: "+ e.getMessage());
        }
    }
    //******************************************************************************************************************

    public void getFieldsFromPacs008XML(String fileName) {
        try
        {
            //*****************************PARSE THE XML AND USE XPATH TO GET LEMENTS***********************************
            Document document = null;
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            docFactory.setNamespaceAware(true);// never forget this!
            DocumentBuilder builder=null;
            builder = docFactory.newDocumentBuilder();

            // Load XML document
            document = builder.parse(new File(strPacs008FilePath+fileName));

            //Normalize the XML Structure; It's just too important !!
            document.getDocumentElement().normalize();

            // Create XPath
            XPathFactory factory = XPathFactory.newInstance();
            XPath xpath = factory.newXPath();

            /********Get the OffUs EndToEnd IDs from the XML*************/
            //Store the OffUsBankCode in an array to use them dynamically
            String OffUsBankCode[] = {Settings.OffUsBankCode_1, Settings.OffUsBankCode_2};

            //Iterate through the number of off-Us account to create the XML artifacts
            int J = 0; //This is the Index for the XPath Search
            int L = 0; //This is the Index for storing the result in the array

            int number_Of_Off_US_Account = OffUsBankCode.length;
            System.out.println("==============================PACS008 XML Contents========================================");
            do{
                J++;
                //Get the EndToEndId
                XPathExpression expr = xpath.compile("//*[local-name()='FIToFICstmrCdtTrf']/*[local-name()='CdtTrfTxInf']["+J+"]/*[local-name()='PmtId']/*[local-name()='EndToEndId']");

                NodeList result_1 =(NodeList) expr.evaluate(document, XPathConstants.NODESET);
                for (int K=0; K < result_1.getLength();K++)
                {
                    System.out.println("OffUs Account EndToEndIDs from the PACS008 XML:"+result_1.item(K).getTextContent());//store them into List

                    //Store the message ID into the Global variable
                    strPacs008EndToEndIDs = String.valueOf(result_1.item(K).getTextContent());

                    //Store Each strPACS008 1EndToEndIDs into a different strPain001EndToEndIdForOffUs
                    strPacs008EndToEndIdForOffUsArray[L] = strPacs008EndToEndIDs;
                    L++;
                }
            }while(number_Of_Off_US_Account > J);

            //*****************************PARSE THE XML****************************************************************
                            /********Get the rest of the specific content data from the XML*************/
            //Get all Contents
            NodeList nList = document.getElementsByTagName("FIToFICstmrCdtTrf");


            for (int temp = 0; temp < nList.getLength(); temp++)
            {
                Node node = nList.item(temp);
                System.out.println("");    //Just a separator
                if (node.getNodeType() == Node.ELEMENT_NODE)
                {
                    //System.out.println("============================== PACS008 XML Contents ======================================");
                    //Print each transaction detail
                    Element eElement = (Element) node;
                    System.out.println("Pacs008 MsgId : "  + eElement.getElementsByTagName("MsgId").item(0).getTextContent());
                    strPacs008OrgnlMsgId = eElement.getElementsByTagName("MsgId").item(0).getTextContent();
                    /**INFO*/
                    ExtentTestManager.getTest().log(Status.INFO, "Info: Pacs008 Message ID is"+"  ::" +strPacs008OrgnlMsgId);

                    System.out.println("Pacs008 CreDtTm: "  + eElement.getElementsByTagName("CreDtTm").item(0).getTextContent());
                    strPacs008CreDtTm =  eElement.getElementsByTagName("CreDtTm").item(0).getTextContent();
                    /**INFO*/
                    ExtentTestManager.getTest().log(Status.INFO, "Info:: Pacs008 Creation Time is"+"  ::"+ strPacs008CreDtTm);

                    System.out.println("Pacs008 TxId :" +eElement.getElementsByTagName("TxId").item(0).getTextContent());
                    strPacs008TxId = eElement.getElementsByTagName("TxId").item(0).getTextContent();
                    /**INFO*/
                    ExtentTestManager.getTest().log(Status.INFO, "Info: Pacs008 Txid is"+"  ::" +strPacs008TxId);
                    System.out.println("==========================================================================================");
                }
            }
        }
        catch (Exception e)
        {
            ExtentTestManager.getTest().log(Status.FAIL, "Exception=FATAL: Database data retrieval failed Pacs008 :: "+ e.getMessage());
        }
    }
}
