package config;


import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


/**
 * @author Jonathan Mbuyi Tshitenda [ ABJT439 ]
 * @date created 7/26/2019
 * @package com.jmt.framework.controls.internals
 */

/** This class is responsible for reading the config information from the config file
 * and setting the values for the variables*/

public class ConfigReader {

    public static  void PopulateSettings() throws IOException {
        ConfigReader reader = new ConfigReader();
        reader.ReadProperty();
    }

    private void ReadProperty() throws IOException {
        //Create Property Object
        Properties properties = new Properties();

        //Load the Property file available in same package
        //InputStream inputStream = new FileInputStream("src/main/java/com/jmt/framework/config/GlobalConfig.properties");
        InputStream inputStream = new FileInputStream("./target/classes/GlobalConfig.properties");
        properties.load(inputStream);

        //Get AUTConnection String
        Settings.AUTConnectionString = properties.getProperty("AUTConnectionString");

        //Get Reporting String
        Settings.ReportingConnectionString = properties.getProperty("ReportingConnectionString");

        //Get LogPath
        Settings.LogPath = properties.getProperty("LogPath");

        //Get DriverType
        Settings.DriverType = properties.getProperty("DriverType");

        //Get ExcelSheetPath
        Settings.ExcelSheetPath = properties.getProperty("ExcelSheetPath");

        //Get Static Test Data
        Settings.OffUsBankCode_1 = properties.getProperty("OffUsBankCode_1");
        Settings.OffUsBankCode_2 = properties.getProperty("OffUsBankCode_2");
        Settings.OffUsBankAccounNumber_1 = properties.getProperty("OffUsBankAccounNumber_1");
        Settings.OffUsBankAccounNumber_2 = properties.getProperty("OffUsBankAccounNumber_2");

        //Get AUT
        Settings.AUT = properties.getProperty("AUT");

        //Browser Type
       // BrowserType = BrowserType.valueOf(properties.getProperty("BrowserType"));

        //Hub
        //Settings.SeleniumGridHub = properties.getProperty("SeleniumGrid");

        //Historical Report
        Settings.HistoricalReport=properties.getProperty("HistoricalReport");

        //CI or Grid execution
        Settings.CIgrid = properties.getProperty("CIgrid");

        //Get Flex
        Settings.Flex = properties.getProperty("Flex");

        //POC Database connection string
        Settings.DBpocUrl = properties.getProperty("dbPoc.url");

        //POC Database driver JDBC
        Settings.DBpocDriverJdbc = properties.getProperty("dbPoc.driver.jdbc");

        //POC Database username
        Settings.DBpocUserName = properties.getProperty("dbPoc.user");

        //POC Database passoword
        Settings.DBpocPassword = properties.getProperty("dbPoc.pass");

        //Audit Database connection string
        Settings.DBauditUrl = properties.getProperty("dbAudit.url");

        //Audit Database driver JDBC
        Settings.DBauditJdbc = properties.getProperty("dbAudit.driver.jdbc");

        //Audit Database username
        Settings.DBauditUserName = properties.getProperty("dbAudit.user");

        //Audit Database passoword
        Settings.DBauditPassword = properties.getProperty("dbAudit.pass");

        //Country code
        Settings.BankCountry = properties.getProperty("BankCountry");

        //Flex Database connection string
        Settings.DBFlexUrl = properties.getProperty("dbFlex.url");

        //Flex Database driver JDBC
        Settings.DBaFlexJdbc = properties.getProperty("dbFlex.driver.jdbc");

        //Flex Database username
        Settings.DBFlexUserName = properties.getProperty("dbFlex.user");

        //Flex Database passoword
        Settings.DBFlexPassword = properties.getProperty("dbFlex.pass");

        //Ebox URL
        Settings.EboxURL = properties.getProperty("EboxUrl");

        //EndPoints sybrinStubDefault
        Settings.sybrinStubDefaultEndPoint = properties.getProperty("endPoint.sybrinStubDefault");

        //EndPoints sybrinStubMessageSearchEndPoint
        Settings.sybrinStubMessageSearchEndPoint = properties.getProperty("endPoint.sybrinStubMessageSearchEndPoint");

        //EndPoints sybrinStubMessageMsgIDEndPoint
        Settings.sybrinStubMessageMsgIDEndPoint = properties.getProperty("endPotin.sybrinStubMessageMsgIDEndPoint");

        //The OnUs Bank Code for the country
        Settings.OnUsBankCode = properties.getProperty("OnUsBankCode");

        //EndPoints CBSstub
        Settings.CBSstub = properties.getProperty("endPoint.CBSstub");

        //EndPoint Bulk Generator
        Settings.bulkGenerator = properties.getProperty("endPoint.bulkGenerator");

        //Endpoints Channel Gateway
        Settings.channelGateWayEndPoint = properties.getProperty("endPoint.channelGateWayEndPoint");

        //Channel Gateway Pain001 from ebox
        Settings.channelGatewayPain001FromEbox = properties.getProperty("endPoint.channelGatewayPain001FromEbox");

        //REST To MQ Endpoint
        Settings.restToMQEndPoint = properties.getProperty("endPoint.restToMQEndPoint");

        //Fetch statement from ebox
        Settings.fetchStetementEndPoint = properties.getProperty("endPoint.FetchEboxStatements");

        //Settings.channelGateWayEndPoint = properties.getProperty("endPoint.channelGateWayEndPoint");
        Settings.connectTimeOut = properties.getProperty("timeouts.connectTimeOut");
        Settings.readTimeOut = properties.getProperty("timeouts.read-timeout-millis");
        Settings.certAlias = properties.getProperty("tls.client-cert-alias");
        Settings.commonNameCheck = properties.getProperty("tls.disable-common-name-check");
        Settings.keystoreResource = properties.getProperty("tls.key-store.resource");
        Settings.keystoreType = properties.getProperty("tls.key-store.type");
        Settings.keystorePassword = properties.getProperty("tls.key-store.password");
        Settings.clientKeyPassword =properties.getProperty("tls.key-store.client-key-password");
        Settings.keystoreClient = properties.getProperty("tls.key-store.client");
        Settings.truststoreResource = properties.getProperty("tls.trust-store.resource");
        Settings.trustStoreType = properties.getProperty("tls.trust-store.type");
        Settings.truststorePassword = properties.getProperty("tls.trust-store.password");

        //Payment Message Related Terms
        Settings.directDebitMessage = properties.getProperty("pain.directDebitMessage");

        //Get Error Code File Path
        Settings.ErrorCodeFilePath = properties.getProperty("ErrorCodeFilePath");

        //Charge Inquiry Endpoint
        Settings.chargesInquiry = properties.getProperty("chargesInquiry");
    }
}
