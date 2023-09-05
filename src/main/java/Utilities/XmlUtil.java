package Utilities;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.service.ExtentTestManager;
import base.BasePage;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.input.BOMInputStream;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;
import org.custommonkey.xmlunit.DetailedDiff;
import org.custommonkey.xmlunit.XMLUnit;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.xml.sax.SAXException;
import org.xmlunit.builder.DiffBuilder;
import org.xmlunit.diff.*;
import org.xmlunit.diff.Diff;
import utils.logs.Log;

import java.io.IOException;

import static org.custommonkey.xmlunit.XMLAssert.assertXMLEqual;


/**
 * @author Jonathan Mbuyi Tshitenda [ ABJT439 ]
 * @date created 9/10/2019
 * @package com.jmt.framework.Utilities
 */
public class XmlUtil extends BasePage {

    public XmlUtil(WebDriver driver) {

        super(driver);
        Log.info("The current working class: "+this.getClass().getName());
    }
    //Global variables
    public String strPacs002FilePath = "./test-artifacts/test_evidence_files/pacs002/";
    public String strPacs008FilePath = System.getProperty("user.dir")+"/testOutputs/pacs008/";
    public String strXMLPath = System.getProperty("user.dir")+"/testData/xml/";
    public String strXMLStructurePath = System.getProperty("user.dir")+"/testData/xml/";
    public static SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");


    /**
     * Create XML file with a defined structure
     */
    //@Test
    public void createPacs002XMLForSingleTxs(String strCurrentPaymentID ,String fileName) throws IOException
    {
        //*************************************************Declare the Know XML File Structure as a String and Pass variables in it*************************************
        String strPacs002ForSingleTxs=
                "<Document xmlns=\"urn:iso:std:iso:20022:tech:xsd:pacs.002.001.09\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n" +
                        "   <FIToFIPmtStsRpt>\n" +
                        "      <GrpHdr>\n" +
                        "         <MsgId>"+strCurrentPaymentID+"</MsgId>\n" +
                        "         <CreDtTm>2019-08-08T09:19:07</CreDtTm>\n" +
                        "      </GrpHdr>\n" +
                        "      <TxInfAndSts>\n" +
                        "         <OrgnlGrpInf>\n" +
                        "            <OrgnlMsgId>%%Actual/Pacs008/FIToFICstmrCdtTrf/CdtTrfTxInf/GrpHdr/MsgId%%</OrgnlMsgId>\n" +
                        "            <OrgnlMsgNmId>pacs.008.001.07</OrgnlMsgNmId>\n" +
                        "            <OrgnlCreDtTm>%%GroupHeader/CreationDateTime%%</OrgnlCreDtTm>\n" +
                        "         </OrgnlGrpInf>\n" +
                        "         <OrgnlEndToEndId>%%PaymentInformation/TransactionDetail/DestinationDetails/TransactionId%%</OrgnlEndToEndId>\n" +
                        "         <OrgnlTxId>%%PaymentInformation/TransactionDetail/DestinationDetails/TransactionId%%</OrgnlTxId>\n" +
                        "         <TxSts>%%Expected/FirstPacs002/FIToFIPmtStsRpt/TxInfAndSts/TxSts%%</TxSts>\n" +
                        "         <StsRsnInf>\n" +
                        "            <Rsn>\n" +
                        "               <Cd>%%Expected/FirstPacs002/FIToFIPmtStsRpt/TxInfAndSts/StsRsnInf/Rsn/Cd%%</Cd>\n" +
                        "            </Rsn>\n" +
                        "            <AddtlInf>01</AddtlInf>\n" +
                        "         </StsRsnInf>\n" +
                        "      </TxInfAndSts>\n" +
                        "   </FIToFIPmtStsRpt>\n" +
                        "</Document>";
        //********************************************************************************************************************************************************

        //Save the PAYLOAD to a file
        writeTextToFile(strPacs002FilePath,fileName,strPacs002ForSingleTxs);
    }

    /**
     * Write XML to file
     */
    public void writeXMLFromFile(String name) throws IOException
    {
        File file = new File("./test-artifacts/test_evidence_files/pacs002/myXML.xml"  );
        FileWriter fileWriter = new FileWriter(file);
        fileWriter.write(strPacs002FilePath);
        fileWriter.flush();
        fileWriter.close();
    }

    /**
     * Read XML from file and returns a string
     */
    public String readXMLFromFile(String name) throws IOException
    {
        InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(name);
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        return br.lines().collect(Collectors.joining());
    }


    /**
     * Read XML from file and get its data to return as a  string
     */
    public String readXMLFromFileAndGetData(String name) throws IOException
    {
        String xmlOutput=null;
        try
        {
            InputStream outFileInputStream = new BOMInputStream(new FileInputStream(name));
            xmlOutput = getData(outFileInputStream);
        }catch (Exception e)
        {

        }
        return xmlOutput;
    }

    /**
     * Get XML data
     */
    private String getData(InputStream in) throws IOException {
        StringWriter writer = new StringWriter();
        IOUtils.copy(in, writer, "UTF-8");
        return writer.toString();
    }


    /**
     * Checks that 2 xml are equal in Content and Data
     */
    public void assertXmlEquality_Solution_1(String actual,String expected) throws IOException, SAXException
    {
        assertXMLEqual(actual,expected);
    }


    /**
     * Checks that 2 xml are equal in Content and Data
     */
    public void assertXmlEquality_Solution_2(String actual,String  expected) throws IOException, SAXException
    {
        DetailedDiff xmlDiff = new DetailedDiff(XMLUnit.compareXML(expected, actual));

        List<?> allDifferences = xmlDiff.getAllDifferences();
        System.out.println(allDifferences);
        Assert.assertEquals("Differences found: "+ xmlDiff.toString(), 0, allDifferences.size());
        ExtentTestManager.getTest().pass(MarkupHelper.createLabel("XML diff check completed", ExtentColor.BLUE));

    }


    /**
     * Checks that 2 xml are equal in Content and Data
     */
    public void assertXmlEquality_Solution_3(String actual,String  expected)
    {
        Diff xmlDiff = DiffBuilder.compare(expected).withTest(actual)
                .withNodeMatcher(new DefaultNodeMatcher(ElementSelectors.byNameAndText))
                .withNodeMatcher(new DefaultNodeMatcher(ElementSelectors.and(ElementSelectors.byNameAndText, ElementSelectors.byNameAndAllAttributes)))
                .ignoreWhitespace()//ignore whitespace differences
                .normalizeWhitespace()
                .ignoreElementContentWhitespace()
                .ignoreComments()
                .checkForSimilar() // a different order is always 'similar' not equals.
                .withComparisonController(ComparisonControllers.Default)
                .build();


        if (xmlDiff.hasDifferences()) {
            System.out.println(">>>it has difference");
        } else {
            System.out.println(">>>No difference");
        }
    }

    /**
     * Checks that 2 xml are equal in Content and Data
     */
    public void assertXmlEquality_Solution_4(String actual,String  expected) throws IOException, SAXException
    {
        org.custommonkey.xmlunit.Diff diff = XMLUnit.compareXML(actual,expected);
        StringBuffer sb = new StringBuffer();
        diff.appendMessage(sb);
        Assert.assertTrue(sb.toString(), diff.identical());
    }



    public void compareXmlWithJava() throws IOException {
        String actual = readXMLFromFile(System.getProperty("user.dir") + "/testData/xml/base-Pacs008.xml");
        String expected = "";

        //Proper but tedious comparison
        /**The regular expression is used to replace all the node tags,whitespace,indentations and only leave the content,
         * because we want to compare the content only*/
        String expectedProcessed = removeNodesAndWhitespacesFromXML(expected);
        String actualProcessed = removeNodesAndWhitespacesFromXML(actual);

        System.out.println(actualProcessed);
        Assert.assertEquals(expectedProcessed,actualProcessed);

        //Assert.assertEquals(expected,actual);//This will fail because of string to string comparison while they are different
    }


    public void compareXmlWithXmlUnitLibrary(String strPacs008File) throws IOException, SAXException, InterruptedException
    {
        /**Get/read the XML*/
        String actual =readXMLFromFileAndGetData(strXMLPath+"base-Pacs008.xml");
        String expected =readXMLFromFileAndGetData(strPacs008FilePath+strPacs008File);
        //String expected =readXMLFromFileAndGetData(strXMLPathLocation+"Pacs008-Negative.xml");


        /**The regular expression is used to replace all the node content(content between ><) and only leave the nodes tags,
         * because we want to compare the tags only*/
        String actualProcessed = removeTextOrValuesInBetweenXMLElements(actual);
        writeTextToFile(strXMLStructurePath,"base-Pacs008Skeleton"+sdf+".xml",actualProcessed);
        String expectedProcessed = removeTextOrValuesInBetweenXMLElements(expected);

        /**Print out the stripped down version of the XMLs*/
        System.out.println(actualProcessed);
        System.out.println(expectedProcessed);

        /**Set XML properties*/
        XMLUnit.setNormalize(true);
        XMLUnit.setIgnoreComments(true);
        XMLUnit.setIgnoreWhitespace(true);
        XMLUnit.setNormalizeWhitespace(true);
        XMLUnit.setIgnoreAttributeOrder(true);

        //Proper xml comparison
        /**
         * Checks that 2 xml are equal in Content and Data
         */
        assertXmlEquality_Solution_2(actualProcessed,expectedProcessed);
    }
}
