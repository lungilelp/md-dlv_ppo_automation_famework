package config;

//import com.jmt.framework.Utilities.LogUtil;

import java.sql.Connection;

/**
 * @author Jonathan Mbuyi Tshitenda [ ABJT439 ]
 * @date created 7/26/2019
 * @package com.jmt.framework.controls.internals
 */

public class Settings {
    /**Hold all the values from the Global config file into the below variables*/

    //For Application Backend
    public static Connection AUTConnection;

    //For Third-Party Reporting
    public static Connection ReportingConnection;

    //For Application backend connection string
    public static String AUTConnectionString;

    //DB connection for the reporting tool
    public static String ReportingConnectionString;

    //Log Path for framework
    public static String LogPath;

    //Driver Type for SQL Server connectivity
    public static String DriverType;

    //Path to the excel book
    public static String ExcelSheetPath;

    //Get Static Test Dataf
    public static String OffUsBankCode_1;
    public static String OffUsBankCode_2;
    public static String OffUsBankAccounNumber_1;
    public static String OffUsBankAccounNumber_2;

    //Application under test
    public static String AUT;

    //Flex application URL
    public static String Flex;

    //Browser type
   // public static com.jmt.framework.base.BrowserType BrowserType;

    //Path to logs folder location
   // public static LogUtil Logs;

    //For cucumber features
    public static String FeatureContext;

    //For cucumber scenarios
    public static String ScenarioContext;

    //Selenium grid IP address
    public static String SeleniumGridHub;

    //Flag for Historical report
    public static String HistoricalReport;

    //Flag for CI or Non-CI execution
    public static String CIgrid;

    //POC Database connection string
    public static String DBpocUrl;

    //POC Database driver JDBC
    public static String DBpocDriverJdbc;

    //POC Database username
    public static String DBpocUserName;

    //POC Database passoword
    public static String DBpocPassword;

    //Audit Database connection string
    public static String DBauditUrl;

    //Audit Database driver JDBC
    public static String DBauditJdbc;

    //Audit Database username
    public static String DBauditUserName;

    //Audit Database passoword
    public static String DBauditPassword;

    //Flex Database connection string
    public static String DBFlexUrl;

    //Flex Database driver JDBC
    public static String DBaFlexJdbc;

    //Flex Database username
    public static String DBFlexUserName;

    //Flex Database passoword
    public static String DBFlexPassword;

    //Ebox URL
    public static String EboxURL;

    //SybrinStubDefault EndPoint
    public static String sybrinStubDefaultEndPoint;

    //SybrinStubMessageSearchEndPoint
    public static String sybrinStubMessageSearchEndPoint;

    //SybrinStubMessageMsgIDEndPoint
    public static String sybrinStubMessageMsgIDEndPoint;

    //Country code to be used in database pain messages and more
    public  static String BankCountry;

    //OnUs bank code
    public static String OnUsBankCode;

    //CBSStub
    public static String CBSstub;

    //EboxStatement
    public static String fetchStetementEndPoint;

    //Bulk Generator
    public static String bulkGenerator;

    public static String connectTimeOut;
    public static String readTimeOut;
    public static String certAlias;
    public static String commonNameCheck;
    public static String keystoreResource;
    public static String keystoreType;
    public static String keystorePassword;
    public static String clientKeyPassword;
    public static String keystoreClient ;
    public static String truststoreResource ;
    public static String trustStoreType;
    public static String truststorePassword;

    //Channel gate way End point
    public static String channelGateWayEndPoint;
    public static String channelGatewayPain001FromEbox;
    public static String restToMQEndPoint;
    public static String chargesInquiry;

    //Payment Message Related Terms
    public static String directDebitMessage;

    //Error Code File
    public static String ErrorCodeFilePath;
}
