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
public class Validate2ndPAIN002Module extends BasePage {

    public Validate2ndPAIN002Module(WebDriver driver) {

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
    public String strQueryGet2ndPAIN002Payload = null;
    public String strPain002FilePath = "./testOutputs/pain002/";
    public String str2ndPain002Payload = null;


    public void validate2ndPAIN002Module(String paymentID,String fileName) throws ParserConfigurationException, SAXException, XPathExpressionException, IOException, InterruptedException, ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException
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
                        strQueryGet2ndPAIN002Payload = "select m.message_value,m.message_type, t.status ,t.status_reason, t.account_number,t.branch_code, " +
                                "convert_from(loread(lo_open(message_value::int, x'40000'::int), x'40000'::int), 'UTF8') " +
                                "from mk_"+BankCountry+"_payments_portal_sit.payment p " +
                                "join mk_"+BankCountry+"_payments_portal_sit.message m on p.payment_id = m.payment_id " +
                                "join mk_"+BankCountry+"_payments_portal_sit.instruction i on p.id = i.payment_id " +
                                "join mk_"+BankCountry+"_payments_portal_sit.transaction t on t.instruction_id = i.id " +
                                "where p.payment_id  = '" + paymentID +"' " +
                                "and convert_from(loread(lo_open(message_value::int, x'40000'::int), x'40000'::int), 'UTF8') like '%<Document xmlns=\"urn:iso:std:iso:20022:tech:xsd:pain.002.001.08\">%' " +
                                "and convert_from(loread(lo_open(message_value::int, x'40000'::int), x'40000'::int), 'UTF8') like '%OrgnlPmtInfAndSts%' ORDER BY m.message_value ASC";
                                //"and convert_from(loread(lo_open(message_value::int, x'40000'::int), x'40000'::int), 'UTF8') not like '%pain.001.001.05%' ORDER BY m.message_value ASC";


                        //Store the PAYLOAD in a string
                        str2ndPain002Payload = databaseHandlerForPocDB(strQueryGet2ndPAIN002Payload, strPostgreSQLPayloadDBColumnName_1);

                    }else if(dbDriver.contains("mysql") || dbDriver.contains("oracle"))
                    {
                        System.out.println("Using MySQL database");

                        //TODO {Some countries store they PAIN xml in the Audit DB ,we need to make a decision statement when dealing with those countries}

                        //Retrieve the PAIN001 PAYLOAD from the Database using the unique message ID
                        /**SQL query For Audit DB*/
                        //strQueryGet2ndPAIN002Payload = "SELECT * FROM TRANSACTION_AUDIT_EVENT WHERE CID ='"+paymentID+"' AND PAYLOAD LIKE '%<Document xmlns=\"urn:iso:std:iso:20022:tech:xsd:pain.002.001.08\">%' AND PAYLOAD NOT LIKE '%pain.001.001.05%'ORDER BY ID ASC";

                        /**SQL query For POC DB*/
                        //strQueryGet2ndPAIN002Payload = "SELECT * FROM MK_"+BankCountry+"_POC.MESSAGE WHERE PAYMENT_ID ='" + paymentID + "' AND MESSAGE_VALUE LIKE '%<Document xmlns=\"urn:iso:std:iso:20022:tech:xsd:pain.002.001.08\">%' AND MESSAGE_VALUE NOT LIKE '%pain.001.001.05%'ORDER BY ID ASC";
                        strQueryGet2ndPAIN002Payload = "SELECT * FROM MK_"+BankCountry+"_POC.MESSAGE WHERE PAYMENT_ID ='" + paymentID + "' AND MESSAGE_VALUE LIKE '%<Document xmlns=\"urn:iso:std:iso:20022:tech:xsd:pain.002.001.08\">%' AND MESSAGE_VALUE LIKE '%<OrgnlPmtInfAndSts>%'ORDER BY ID ASC";

                        //Store the PAYLOAD in a string
                        str2ndPain002Payload = databaseHandlerForAuditDB(strQueryGet2ndPAIN002Payload, strMySQLPayloadDBColumnName_1);
                    }


                    //Write/Save the PAYLOAD to a file
                    if (str2ndPain002Payload != null)
                    {
                        //Print out the PAYLOAD
                        System.out.println("===============================2nd PAIN002 PAYLOAD FROM DB ===============================");
                        System.out.println("Info:: Current PAYLOAD " + str2ndPain002Payload);
                        /**INFO*/
                        ExtentTestManager.getTest().log(Status.PASS, "Status=PASS:: 2nd PAIN002 XML Payload below");
                        codeLogsXML(str2ndPain002Payload);

                        //Write/Save the PAYLOAD to a file
                        writeTextToFile(strPain002FilePath, fileName, str2ndPain002Payload);
                        break;
                    }
                    else {
                       // ExtentTestManager.getTest().log(Status.WARNING, "Warning:: No Records Where Found In The Database on the 1st attempt ,the script will attempt to retry in 20 seconds");
                        //TODO: Marking the soft red flags / system poor bahavior in a report instead of above line use below
                        ExtentTestManager.getTest().warning(MarkupHelper.createLabel("Warning:: No Records Where Found In The Database on the 1st attempt ,the script will attempt to retry in 20 seconds 2ndPain002", ExtentColor.AMBER));
                        Thread.sleep(20000);
                        i++;
                    }
                }while (i <=intDatabaseWaitTimeIndex);



                /**Stop the test if there are no records in the DB after the allocated time*/
                if(str2ndPain002Payload==null & i>intDatabaseWaitTimeIndex)
                {
                    ExtentTestManager.getTest().fail(MarkupHelper.createLabel("No Records Where Found In The Database ,The test has been stopped", ExtentColor.RED));
                    stopCurrentTestSetResultsToFailedAndContinueWithNextTest();
                }


            }catch (Exception e){
                ExtentTestManager.getTest().log(Status.FAIL, "Status=FATAL: No Records Where Found In The Database.File not created 2ndPain002");
            }
            //*****************************PARSE THE XML****************************************************************
            //Get Document Builder
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            //Build Document
            Document document = builder.parse(new File(strPain002FilePath + fileName));

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
                    System.out.println("==============================2nd PAIN002 XML Contents ===================================");
                    //Print each employee's detail
                    Element eElement = (Element) node;
                    System.out.println("OrgnlMsgId : " + eElement.getElementsByTagName("OrgnlMsgId").item(0).getTextContent());
                    System.out.println("GrpSts : " + eElement.getElementsByTagName("GrpSts").item(0).getTextContent());
                    System.out.println("==========================================================================================");
                }
            }

        }catch (Exception e){
            ExtentTestManager.getTest().log(Status.FAIL, "Exception=FATAL: Database data retrieval failed 2ndPain002 :: "+ e.getMessage());
        }
    }
}
