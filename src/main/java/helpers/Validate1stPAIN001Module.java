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
import javax.xml.xpath.*;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import static Utilities.DatabaseUtil.*;
import static utils.extentreports.ExtentTestManager.codeLogsXML;;

/**
 * @author Jonathan Mbuyi Tshitenda [ ABJT439 ]
 * @date created 9/10/2019
 * @package helpers
 */
public class Validate1stPAIN001Module extends BasePage {
    public Validate1stPAIN001Module(WebDriver driver) {

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
    public String strQueryGetPAIN001Payload = null;
    public String strPain001FilePath = "./testOutputs/pain001/";
    public String strPain001MsgId = null;
    public String strPain001PmtInfId = null;
    public String strPain001Payload = null;
    public static String strPain001EndToEndIDs = null;

    //Store the OffUs EndToEndIDs that will be retrieved,in a Global Variable that is in an Array in order use them dynamically
    public static String strPain001EndToEndIdForOffUs_1 = null;
    public static String strPain001EndToEndIdForOffUs_2 = null;
    public static String strPain001EndToEndIdForOffUsArray[] = {strPain001EndToEndIdForOffUs_1,strPain001EndToEndIdForOffUs_2};



    public void validate1stPAIN001Module(String paymentID,String fileName) throws ParserConfigurationException, SAXException, XPathExpressionException, IOException, InterruptedException, ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException
    {
        String BankCountry = Settings.BankCountry;
        /**Initialize config reader to read configs from Settings*/
        ConfigReader.PopulateSettings();
        String dbDriver = Settings.DBpocDriverJdbc;


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

                        //Retrieve the PAIN001 PAYLOAD from the Database using the unique message ID
                        strQueryGetPAIN001Payload = "select m.message_value,m.message_type, t.status ,t.status_reason, t.account_number,t.branch_code, " +
                                "convert_from(loread(lo_open(message_value::int, x'40000'::int), x'40000'::int), 'UTF8') " +
                                "from mk_"+BankCountry+"_payments_portal_sit.payment p " +
                                "join mk_"+BankCountry+"_payments_portal_sit.message m on p.payment_id = m.payment_id " +
                                "join mk_"+BankCountry+"_payments_portal_sit.instruction i on p.id = i.payment_id " +
                                "join mk_"+BankCountry+"_payments_portal_sit.transaction t on t.instruction_id = i.id " +
                                "where p.payment_id  = '" + paymentID + "' " +
                                "and convert_from(loread(lo_open(message_value::int, x'40000'::int), x'40000'::int), 'UTF8') like '%<ns2:Document xmlns:ns2=\"urn:iso:std:iso:20022:tech:xsd:pain.001.001.05\">%'";

                        //Store the PAYLOAD in a string
                        strPain001Payload = databaseHandlerForPocDB(strQueryGetPAIN001Payload, strPostgreSQLPayloadDBColumnName_1);

                    }
                    else if(dbDriver.contains("mysql") || dbDriver.contains("oracle"))
                    {
                        System.out.println("Using MySQL database");

                        //TODO {Some countries store they PAIN xml in the Audit DB ,we need to make a decision statement when dealing with those countries}

                        //Retrieve the PAIN001 PAYLOAD from the Database using the unique message ID
                        /**SQL query For Audit DB*/
                        //String strQueryGetPAIN001Payload = "SELECT * FROM TRANSACTION_AUDIT_EVENT WHERE CID = '" + paymentID + "' AND PAYLOAD LIKE '%<ns2:Document xmlns:ns2=\"urn:iso:std:iso:20022:tech:xsd:pain.001.001.05\">%'ORDER BY ID ASC";//Escaped quotes("") with \ (a backslash in front of the quotes)

                        /**SQL query For POC DB*/
                        strQueryGetPAIN001Payload = "SELECT * FROM MK_"+BankCountry+"_POC.MESSAGE WHERE PAYMENT_ID = '" + paymentID + "' AND MESSAGE_VALUE LIKE '%<ns2:Document xmlns:ns2=\"urn:iso:std:iso:20022:tech:xsd:pain.001.001.05\">%'ORDER BY ID ASC";


                        //Store the PAYLOAD in a string
                        strPain001Payload = databaseHandlerForAuditDB(strQueryGetPAIN001Payload, strMySQLPayloadDBColumnName_1);
                    }

                    //Write/Save the PAYLOAD to a file
                    if (strPain001Payload != null)
                    {
                        /**INFO*/
                        ExtentTestManager.getTest().log(Status.PASS, "Status=PASS: PAIN001 XML Payload below");
                        codeLogsXML(strPain001Payload);

                        //Print out the PAYLOAD
                        System.out.println("=================================PAIN001 PAYLOAD FROM DB =================================");
                        System.out.println("Info: Current PAYLOAD " +strPain001Payload);

                        //Write/Save the PAYLOAD to a file
                        writeTextToFile(strPain001FilePath, fileName, strPain001Payload);
                        break;

                    } else
                        {
                       // ExtentTestManager.getTest().log(Status.WARNING, "Warning:: No Records Where Found In The Database on the 1st attempt ,the script will attempt to retry in 20 seconds");
                            //TODO: Marking the soft red flags / system poor bahavior in a report instead of above line use below
                            ExtentTestManager.getTest().warning(MarkupHelper.createLabel("Warning:: No Records Where Found In The Database on the 1st attempt ,the script will attempt to retry in 20 seconds Pain001", ExtentColor.AMBER));
                            Thread.sleep(20000);
                        i++;
                    }
                }while (i <=intDatabaseWaitTimeIndex);


                /**Stop the test if there are no records in the DB after the allocated time*/
                if(strPain001Payload==null & i>intDatabaseWaitTimeIndex)
                {
                    ExtentTestManager.getTest().fail(MarkupHelper.createLabel("No Records Where Found In The Database ,The test has been stopped", ExtentColor.RED));
                    stopCurrentTestSetResultsToFailedAndContinueWithNextTest();
                }

            }catch (Exception e)
            {
                ExtentTestManager.getTest().log(Status.FAIL, "Status=FATAL: No Records Where Found In The Database.File not created Pain001");
            }

            //*****************************PARSE THE XML AND USE XPATH TO GET LEMENTS***********************************
            Document document = null;
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            docFactory.setNamespaceAware(true);// never forget this!
            DocumentBuilder builder=null;
            builder = docFactory.newDocumentBuilder();

            // Load XML document
            document = builder.parse(new File(strPain001FilePath +fileName));

            //Normalize the XML Structure; It's just too important !!
            document.getDocumentElement().normalize();

            // Create XPath
            XPathFactory factory = XPathFactory.newInstance();
            XPath xpath = factory.newXPath();

            /***************************Get attribute values using xpath to search the XML******************************/


                               /********Get the OffUs EndToEnd IDs from the XML*************/
            //Store the OffUsBankCode in an array to use them dynamically
            String OffUsBankCode[] = {Settings.OffUsBankCode_1, Settings.OffUsBankCode_2};

            //Iterate through the number of off-Us account to create the XML artifacts
            int j = 0;
            int number_Of_Off_US_Account = OffUsBankCode.length;
            System.out.println("==============================PAIN001 XML Contents========================================");
            do{
                //XPathExpression expr_1 = xpath.compile("//*[local-name()='CstmrCdtTrfInitn']/*[local-name()='PmtInf']/*[local-name()='CdtTrfTxInf']/*[local-name()='CdtrAgt']/*[local-name()='FinInstnId']/*[local-name()='ClrSysMmbId']/*[local-name()='MmbId' and .="+Settings.OffUsBankCode_1+"]/preceding::*[3]");
                XPathExpression expr = xpath.compile("//*[local-name()='CstmrCdtTrfInitn']/*[local-name()='PmtInf']/*[local-name()='CdtTrfTxInf']/*[local-name()='CdtrAgt']/*[local-name()='FinInstnId']/*[local-name()='ClrSysMmbId']/*[local-name()='MmbId' and .="+OffUsBankCode[j]+"]/preceding::*[3]");

                /** Below line is sufficient if there is no namespace associated to the document.
                 *  XPathExpression expr = xpath.compile("/college/department[@name = 'science']/student/firstName");
                 */
                NodeList result_1 =(NodeList) expr.evaluate(document, XPathConstants.NODESET);
                for (int K=0; K < result_1.getLength();K++)
                {
                    System.out.println("OffUs Account EndToEndIDs from the PAIN001 XML:"+result_1.item(K).getTextContent());//store them into List

                    //Store the message ID into the Global variable
                    strPain001EndToEndIDs = String.valueOf(result_1.item(K).getTextContent());

                    //Store Each strPain001EndToEndIDs into a different strPain001EndToEndIdForOffUs
                    strPain001EndToEndIdForOffUsArray[j] = strPain001EndToEndIDs;
                }
                //System.out.println("These are the OffUs Account EndToEndIDs from the PAIN001 XML:"+ strPain001EndToEndIdForOffUsArray[j]);
                j++;

            }while(j <number_Of_Off_US_Account);
            System.out.println("==========================================================================================");

                            /********Get the rest of the specific content data from the XML*************/
            //Here comes the root node
            Element root = document.getDocumentElement();
            System.out.println(root.getNodeName());

            //Get all Contents
            NodeList nList = document.getElementsByTagName("ns2:CstmrCdtTrfInitn");

            for (int temp = 0; temp < nList.getLength(); temp++)
            {
                Node node = nList.item(temp);
                System.out.println("");    //Just a separator
                if (node.getNodeType() == Node.ELEMENT_NODE)
                {
                    System.out.println("==============================PAIN001 XML Contents========================================");
                    //Print each employee's detail
                    Element eElement = (Element) node;
                    System.out.println("MsgId : "  + eElement.getElementsByTagName("ns2:MsgId").item(0).getTextContent());
                    strPain001MsgId = eElement.getElementsByTagName("ns2:MsgId").item(0).getTextContent();
                    /**INFO*/
                    ExtentTestManager.getTest().log(Status.INFO, "Info: Message ID is"+"  ::" +strPain001MsgId);

                    System.out.println("PmtInfId : "  + eElement.getElementsByTagName("ns2:PmtInfId").item(0).getTextContent());
                    strPain001PmtInfId =  eElement.getElementsByTagName("ns2:PmtInfId").item(0).getTextContent();
                    /**INFO*/
                    ExtentTestManager.getTest().log(Status.INFO, "Info: Payment Information ID is"+"  ::" +strPain001PmtInfId);

                    System.out.println("EndToEndId for OnUs :" +eElement.getElementsByTagName("ns2:EndToEndId").item(0).getTextContent());
                    strPain001EndToEndIDs = eElement.getElementsByTagName("ns2:EndToEndId").item(0).getTextContent();
                    /**INFO*/
                    ExtentTestManager.getTest().log(Status.INFO, "Info: End to End ID is"+"  ::" +strPain001EndToEndIDs);

                    //System.out.println("EndToEndId for OffUs :" +eElement.getElementsByTagName("ns2:EndToEndId").item(4).getTextContent());
                    //strPain001EndToEndIdForOffUs_1 = eElement.getElementsByTagName("ns2:EndToEndId").item(4).getTextContent();
                    /**INFO*/
                    //ExtentTestManager.getTest().log(Status.INFO, "Info: End to End ID is"+"  ::" +strPain001EndToEndIdForOffUs_1);
                    System.out.println("==========================================================================================");
                }
            }
        }catch (Exception e)
        {
            ExtentTestManager.getTest().log(Status.FAIL, "Exception=FATAL: Database data retrieval failed Pain001 :: "+ e.getMessage());
        }
    }

    public static List<String> evaluateXPath(Document document, String xpathExpression) throws Exception
    {
        // Create XPathFactory object
        XPathFactory xpathFactory = XPathFactory.newInstance();

        // Create XPath object
        XPath xpath = xpathFactory.newXPath();

        List<String> values = new ArrayList<>();
        try
        {
            // Create XPathExpression object
            XPathExpression expr = xpath.compile(xpathExpression);

            // Evaluate expression result on XML document
            NodeList nodes = (NodeList) expr.evaluate(document, XPathConstants.NODESET);

            for (int i = 0; i < nodes.getLength(); i++) {
                values.add(nodes.item(i).getNodeValue());
            }

        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }
        return values;
    }

    public void fetchPain001 (String paymentid) {

        try{
            String fetchPain001query = "select convert_from(loread(lo_open(message_value::int, x'40000'::int), x'40000'::int), 'UTF8') from \n" +
                    "makola_payments_portal_sit.message where payment_id ='"+paymentid+"' and message_type='PAIN.001'";
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
