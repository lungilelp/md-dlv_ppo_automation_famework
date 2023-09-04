package helpers;

import base.BasePage;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import config.ConfigReader;
import config.Settings;
import org.openqa.selenium.WebDriver;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import utils.extentreports.ExtentTestManager;
import utils.logs.Log;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import static Utilities.DatabaseUtil.*;
import static utils.extentreports.ExtentTestManager.codeLogsXML;

/**
 * @author Jonathan Mbuyi Tshitenda [ ABJT439 ]
 * @date created 9/10/2019
 * @package helpers
 */
public class Validate1stPAIN002Module extends BasePage {
    public Validate1stPAIN002Module(WebDriver driver) {

        super(driver);
        Log.info("The current working class: "+this.getClass().getName());
    }

    //Global Declarations
    /*Notes:required argumnets
        1. XML File location to test against and Name
        2. PaymentID to pass in the SQL query
    */
    public static String strMySQLPayloadDBColumnName_1 = "MESSAGE_VALUE";//Column name in the DB
    public static String strPostgreSQLPayloadDBColumnName_1 = "convert_from";//Column name in the DB
    public String strQueryGet1stPAIN002Payload = null;
    public String strPAIN002FilePath = "./testOutputs/pain002/";
    public String strOrgnlMsgId = null;
    public String strGrpSts = null;
    public String str1stPAIN002Payload=null;

    public void validate1stPAIN002Module(String paymentID,String fileName) throws ParserConfigurationException, SAXException, XPathExpressionException, IOException, InterruptedException, ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException
    {
        String BankCountry = Settings.BankCountry;
        /**Initialize config reader to read configs from Settings*/
        ConfigReader.PopulateSettings();
        String dbDriver = Settings.DBpocDriverJdbc;

        //*****************************Get,Validate and store the PAIN001 XML**************************************************************
        try
        {
            int i = 0;
            int intDatabaseWaitTimeIndex = 2;

            //Retry the SQL Query execution if there is no payload in the DB on the first attempt
            try
            {
                do
                {
                    /**=================Determine which database to use ======================================*/
                    if(dbDriver.contains("postgresql"))
                    {
                        System.out.println("Using Postgresql database");

                        //Retrieve the PAIN002 PAYLOAD from the Database using the unique message ID
                        strQueryGet1stPAIN002Payload = "select m.message_value,m.message_type, t.status ,t.status_reason, t.account_number,t.branch_code, " +
                                "convert_from(loread(lo_open(message_value::int, x'40000'::int), x'40000'::int), 'UTF8') " +
                                "from mk_"+BankCountry+"_payments_portal_sit.payment p " +
                                "join mk_"+BankCountry+"_payments_portal_sit.message m on p.payment_id = m.payment_id " +
                                "join mk_"+BankCountry+"_payments_portal_sit.instruction i on p.id = i.payment_id " +
                                "join mk_"+BankCountry+"_payments_portal_sit.transaction t on t.instruction_id = i.id " +
                                "where p.payment_id  = '"+paymentID+"' " +
                                "and convert_from(loread(lo_open(message_value::int, x'40000'::int), x'40000'::int), 'UTF8') like '%<Document xmlns=\"urn:iso:std:iso:20022:tech:xsd:pain.002.001.08\">%' " +
                                "and convert_from(loread(lo_open(message_value::int, x'40000'::int), x'40000'::int), 'UTF8') not like '%OrgnlPmtInfAndSts%' ORDER BY m.message_value ASC";
                                //"and convert_from(loread(lo_open(message_value::int, x'40000'::int), x'40000'::int), 'UTF8') like '%pain.001.001.05%' ORDER BY m.message_value ASC";

                        //Store the PAYLOAD in a string
                        str1stPAIN002Payload = databaseHandlerForPocDB(strQueryGet1stPAIN002Payload, strPostgreSQLPayloadDBColumnName_1);

                    }else if(dbDriver.contains("mysql") || dbDriver.contains("oracle"))
                    {
                        System.out.println("Using MySQL database");

                        //TODO {Some countries store they PAIN xml in the Audit DB ,we need to make a decision statement when dealing with those countries}

                        //Retrieve the PAIN001 PAYLOAD from the Database using the unique message ID

                        /**SQL query For Audit DB*/
                        //strQueryGet1stPAIN002Payload = "SELECT * FROM TRANSACTION_AUDIT_EVENT WHERE CID ='"+paymentID+"' AND PAYLOAD LIKE '%<Document xmlns=\"urn:iso:std:iso:20022:tech:xsd:pain.002.001.08\">%' AND PAYLOAD LIKE '%pain.001.001.05%' ORDER BY ID ASC";

                        /**SQL query For POC DB*/
                        //String strQueryGet1stPAIN002Payload = "SELECT * FROM MK_"+BankCountry+"_POC.MESSAGE WHERE PAYMENT_ID ='"+paymentID+"' AND MESSAGE_VALUE LIKE '%<Document xmlns=\"urn:iso:std:iso:20022:tech:xsd:pain.002.001.08\">%' AND MESSAGE_VALUE LIKE '%pain.001.001.05%' ORDER BY ID ASC";
                        String strQueryGet1stPAIN002Payload = "SELECT * FROM MK_"+BankCountry+"_POC.MESSAGE WHERE PAYMENT_ID ='"+paymentID+"' AND MESSAGE_VALUE LIKE '%<Document xmlns=\"urn:iso:std:iso:20022:tech:xsd:pain.002.001.08\">%' ORDER BY ID ASC";

                        //Store the PAYLOAD in a string
                        str1stPAIN002Payload = databaseHandlerForAuditDB(strQueryGet1stPAIN002Payload, strMySQLPayloadDBColumnName_1);
                    }

                    //Write/Save the PAYLOAD to a file
                    if (str1stPAIN002Payload !=null)
                    {
                        //Print out the PAYLOAD
                        System.out.println("==============================1ST PAIN002 PAYLOAD FROM DB ================================");
                        System.out.println("Info: Current PAYLOAD ::" +str1stPAIN002Payload);
                        /**INFO*/
                        ExtentTestManager.getTest().log(Status.PASS, "Status=PASS:: PAIN002 XML Payload below");
                        codeLogsXML(str1stPAIN002Payload);

                        //Write/Save the PAYLOAD to a file
                        writeTextToFile(strPAIN002FilePath,fileName,str1stPAIN002Payload);
                        break;
                    }
                    else
                        {
                      //  ExtentTestManager.getTest().log(Status.WARNING, "Warning:: No Records Where Found In The Database on the 1st attempt ,the script will attempt to retry in 20 seconds");
                            //TODO: Marking the soft red flags / system poor bahavior in a report instead of above line use below
                            ExtentTestManager.getTest().warning(MarkupHelper.createLabel("Warning:: No Records Where Found In The Database on the 1st attempt ,the script will attempt to retry in 20 seconds 1stPain002", ExtentColor.AMBER));
                            Thread.sleep(20000);
                        i++;
                    }
                }
                while (i <=intDatabaseWaitTimeIndex);

                /**Stop the test if there are no records in the DB after the allocated time*/
                if(str1stPAIN002Payload==null & i>intDatabaseWaitTimeIndex)
                {
                    ExtentTestManager.getTest().fail(MarkupHelper.createLabel("No Records Where Found In The Database ,The test has been stopped", ExtentColor.RED));
                    stopCurrentTestSetResultsToFailedAndContinueWithNextTest();
                }

            }catch (Exception e){
                ExtentTestManager.getTest().log(Status.FAIL, "Status=FATAL: No Records Where Found In The Database.File not created 1stPain002");
            }



            //*****************************PARSE THE XML**************************************************************************************
            //Get Document Builder
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            //Build Document
            Document document = builder.parse(new File(strPAIN002FilePath+fileName));

            //Normalize the XML Structure; It's just too important !!
            document.getDocumentElement().normalize();

            //Here comes the root node
            Element root = document.getDocumentElement();
            System.out.println(root.getNodeName());


            //Get all Contents
            NodeList nList = document.getElementsByTagName("CstmrPmtStsRpt");


            for (int temp = 0; temp < nList.getLength(); temp++)
            {
                Node node = nList.item(temp);
                System.out.println("");    //Just a separator
                if (node.getNodeType() == Node.ELEMENT_NODE)
                {
                    System.out.println("==============================1st PAIN002 XML Contents====================================");
                    //Print each detail
                    Element eElement = (Element) node;
                    System.out.println("OrgnlMsgId : "  + eElement.getElementsByTagName("OrgnlMsgId").item(0).getTextContent());
                    strOrgnlMsgId = eElement.getElementsByTagName("OrgnlMsgId").item(0).getTextContent();
                    /**INFO*/
                    ExtentTestManager.getTest().log(Status.INFO, "Info: Origination Message ID"+"  ::"+strOrgnlMsgId);


                    System.out.println("GrpSts : "  + eElement.getElementsByTagName("GrpSts").item(0).getTextContent());
                    strGrpSts = eElement.getElementsByTagName("GrpSts").item(0).getTextContent();
                    /**INFO*/
                    ExtentTestManager.getTest().log(Status.INFO, "Info: Group Status"+"  ::"+strGrpSts);
                    System.out.println("==========================================================================================");

                }
            }}catch (Exception e){
            ExtentTestManager.getTest().log(Status.FAIL, "Exception=FATAL: Database data retrieval failed 1stPain002 :: "+ e.getMessage());
        }
    }

    public void fetchRCVD (String paymentid) {

        try{
            String fetchPain001query = "select convert_from(loread(lo_open(message_value::int, x'40000'::int), x'40000'::int), 'UTF8') from \n" +
                    "makola_payments_portal_sit.message where payment_id ='"+paymentid+"' and message_type='PAIN.002'";
            String Pain001message = databaseHandlerForPocDB(fetchPain001query,"convert_from");

            ExtentTestManager.getTest().info(MarkupHelper.createLabel("Pain001 message", ExtentColor.PURPLE));
            if (Pain001message!=null) {
                codeLogsXML(Pain001message);
                ExtentTestManager.getTest().log(Status.PASS, "Pain001 is generated");
            }
            else{
                ExtentTestManager.getTest().log(Status.FAIL, "Pain001 is not generated");
            }
        }
        catch(Exception e){

        }

    }
    //******************************************************************************************************************
}
