package Utilities.api;
import com.aventstack.extentreports.Status;
import config.Settings;
import base.BasePage;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;
import org.openqa.selenium.WebDriver;
import utils.extentreports.ExtentTestManager;
import utils.logs.Log;

import javax.net.ssl.SSLContext;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;



/**
 * @author Jonathan Mbuyi Tshitenda [ ABJT439 ]
 * @date created 9/10/2019
 * @package com.jmt.framework.Utilities.api
 */
public class APIUtil extends BasePage {
    public APIUtil(WebDriver driver) {

        super(driver);
        Log.info("The current working class: "+this.getClass().getName());
    }
    public static String getPacs008MessageID(String BankCountry, String strCurrentEndToEndId) throws IOException, InterruptedException {
        System.out.println("====================== PACS008 MessageID Retrieval From API Payload ======================");


        int i = 0;
        int intAPIWaitTimeIndex = 6;
        String apiResponseMessageID = null;

        //CloseableHttpClient httpClient = HttpClients.createDefault();
        //HttpClient httpClient = HttpClients.custom().setDefaultRequestConfig(RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD).build()).build();
        HttpClient httpClient = HttpClientBuilder.create().setDefaultRequestConfig(RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD).build()).build();

        try {
            //Define a postRequest request
            String endPointURI = Settings.sybrinStubDefaultEndPoint + BankCountry + "/message/search/by-e2e/" + strCurrentEndToEndId;
            System.out.println("endPointURI :" + endPointURI);
            //HttpGet getRequest = new HttpGet(endPointURI + strCurrentEndToEndId); //Parse in the Message ID
            HttpGet getRequest = new HttpGet(endPointURI);
            HttpResponse responseBody;
            Thread.sleep(4000);

            //Set the API media type in http content-type header
            getRequest.addHeader("Content-type", "application/xml");
            getRequest.addHeader("Accept", "application/xml");
            getRequest.addHeader("Set-Cookie:", "9b4c5c7b358030ffb21c1082ef09cc11=28e53073aab8086bc35faf47a7f26906");
            getRequest.addHeader("x-api-key", "989F1CBB-F491-48EC-98DA-F1A7CAABC88C");

            do { //To perform retries

                //Send the request; It will immediately return the response in HttpResponse object if any
                responseBody = httpClient.execute(getRequest);

                //Print out the response
                System.out.println("This is the API response received when getting the MessageID " + responseBody);

                //verify the valid error code first
                int statusCode = responseBody.getStatusLine().getStatusCode();
                if (statusCode >= 200 && statusCode < 300) {
                    HttpEntity entity = responseBody.getEntity();
                    break;
                } else {
                    i++;
                    ExtentTestManager.getTest().log(Status.WARNING, "Warning:: No Records Where Found on the 1st attempt ,the script will attempt to retry in 10 seconds");
                    Thread.sleep(10000);
                    //throw new ClientProtocolException("Unexpected response status: " + statusCode);
                }
            }
            while (i <= intAPIWaitTimeIndex);


            //Now pull back the response object
            HttpEntity httpEntity = responseBody.getEntity();
            apiResponseMessageID = EntityUtils.toString(httpEntity);

            //Stop the test if the response is null
            if (apiResponseMessageID.equalsIgnoreCase("")) {
                ExtentTestManager.getTest().fail("The API response is null");
                //stopCurrentTestSetResultsToFailedAndContinueWithNextTest();
            }

            //Check results from API
            System.out.println("This is the MessageID received " + apiResponseMessageID);
            ExtentTestManager.getTest().log(Status.PASS, "PASS :: Pacs008 Message ID "+"  :: " +apiResponseMessageID);
            System.out.println("==========================================================================================");

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //Important: Close the connect
            //httpClient.close();
        }
        return apiResponseMessageID;
    }


    public void getPacs008XMLFromAPI(String strPacs008MessageID, String fileName, String BankCountry) throws Exception {
        System.out.println("====================== PACS008 Payload Retrieval From API ================================");

        int i = 0;
        int intAPIWaitTimeIndex = 10;
        String apiResponsePacs008 = null;

        //HttpClient httpClient = HttpClientBuilder.create().setDefaultRequestConfig(RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD).build()).build();
        CloseableHttpClient httpClient = HttpClients.createDefault();

        try {
            //Define a postRequest request
            String endPointURI = "http://" + Settings.sybrinStubMessageMsgIDEndPoint + BankCountry + "/message?msgId=" + strPacs008MessageID;


            HttpGet getRequest = new HttpGet(endPointURI); //Parse in the Message ID
            HttpResponse responseBody;
            Thread.sleep(3000);

            //Set the API media type in http content-type header
            getRequest.addHeader("Content-type", "application/xml");
            getRequest.addHeader("Accept", "application/xml");
            getRequest.addHeader("Set-Cookie:", "9b4c5c7b358030ffb21c1082ef09cc11=28e53073aab8086bc35faf47a7f26906");
            getRequest.addHeader("x-api-key", "989F1CBB-F491-48EC-98DA-F1A7CAABC88C");

            do {
                //Send the request; It will immediately return the response in HttpResponse object if any
                responseBody = httpClient.execute(getRequest);

                //Print out the response
                System.out.println("Pacs008 API Response is " + responseBody);

                //verify the valid error code first
                int statusCode = responseBody.getStatusLine().getStatusCode();
                if (statusCode >= 200 && statusCode < 300) {
                    HttpEntity entity = responseBody.getEntity();
                    break;
                } else {
                    i++;
                    ExtentTestManager.getTest().log(Status.WARNING, "Warning:: No Records Where Found on the 1st attempt ,the script will attempt to retry in 10 seconds");
                    Thread.sleep(10000);
                    //throw new ClientProtocolException("Unexpected response status: " + statusCode);
                }
            } while (i <= intAPIWaitTimeIndex);

            //Now pull back the response object
            HttpEntity httpEntity = responseBody.getEntity();
            apiResponsePacs008 = EntityUtils.toString(httpEntity);

            //Stop the test if the response is null
            if (apiResponsePacs008.equalsIgnoreCase("")) {
                ExtentTestManager.getTest().fail("The API response is null");
                //stopCurrentTestSetResultsToFailedAndContinueWithNextTest();
            }


            //Write/Save the PAYLOAD to a file
            if (apiResponsePacs008 != null) {
                //Print out the PAYLOAD
                System.out.println("==================================PACS008 PAYLOAD FROM API================================");
                System.out.println("Info: This is the PACS008 payload received " + apiResponsePacs008);

                ExtentTestManager.getTest().log(Status.PASS, "Info: Pacs008 XML Payload below");
                ExtentTestManager.codeLogsXML(apiResponsePacs008);

                //Write/Save the PAYLOAD to a file
                writeTextToFile(strPacs008FilePath, fileName, apiResponsePacs008);
                System.out.println("==========================================================================================");
            } else {
                ExtentTestManager.getTest().log(Status.WARNING, "Warning:: No Records Where Found");
                Thread.sleep(3000);
            }

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            //Important: Close the connect
            httpClient.close();
        }
    }

    //-----------------------------------------------------------------------------------------------------------------------------------------------
    //-------------generic drop payload method- ababa8t--------------------------
    public void genericDropPayload(String message,String BankCountry) throws Throwable
    {
        int i = 0;
        int intAPIWaitTimeIndex = 10;
        HttpResponse responseBody = null;
        int statusCode;
        String results;

        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost postRequest=null;

        String sybrinURL = Settings.sybrinStubDefaultEndPoint+"api/sybrin/"+BankCountry;
        String cbsURL = Settings.CBSstub+"?correlationId=corrId";

            if(!BankCountry.contains("SYC")) {
                postRequest = new HttpPost(sybrinURL);
            }
            else {
                postRequest = new HttpPost(cbsURL);
            }

        //Add headers
        postRequest.addHeader("content-type", "application/xml");
        postRequest.addHeader("Accept", "application/xml");
        postRequest.addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
        postRequest.addHeader("Accept-Language", "en-US,en;q=0.5");
        postRequest.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");

        try {
            do
            {//To perform retries

                //Set the request post body xml that we created
                StringEntity xmlBodyEntity = new StringEntity(message);
                System.out.println(xmlBodyEntity);

                System.out.println("Executing request " + xmlBodyEntity);
                postRequest.setEntity(xmlBodyEntity);

                //Send the request; It will immediately return the response in HttpResponse object if any
                responseBody = httpClient.execute(postRequest);

                //verify the valid error code first
                statusCode = responseBody.getStatusLine().getStatusCode();
                if (statusCode >= 200 && statusCode < 300)
                {
                    HttpEntity entity = responseBody.getEntity();
                    results = "Passing";
                    break;
                }
                else
                {
                    i++;
                    ExtentTestManager.getTest().log(Status.WARNING, "Warning:: No Records Where Found on the 1st attempt ,the script will attempt to retry in 10 seconds");
                    Thread.sleep(10000);
                    results = "Failing";
                }
            } while (i <=intAPIWaitTimeIndex);


            //Stop the test if the response is null
            if(results.equals("Failing"))
            {
                ExtentTestManager.getTest().fail("The API response is null");
                stopCurrentTestSetResultsToFailedAndContinueWithNextTest();
            }

            //Now pull back the response object
            HttpEntity httpEntity = responseBody.getEntity();

            //Check results from API
            System.out.println("This is the response received after posting the payload "+httpEntity);
            System.out.println("==========================================================================================");

        }
        catch(Exception e){
            System.out.println("Exception-"+e);
        }
        finally
        {
            //Important: Close the connect
            httpClient.close();
        }
    }

    public void DropEboxPainMsgPayload(String message) throws Throwable {
        int i = 0;
        int intAPIWaitTimeIndex = 10;
        HttpResponse responseBody;
        int statusCode;
        String results;

        //Creating SSLContextBuilder object
        SSLContextBuilder SSLBuilder = SSLContexts.custom();

        InputStream keyStoreInputStream = getClass().getClassLoader().getResourceAsStream("keystore.jks");
        KeyStore keystore = KeyStore.getInstance(Settings.keystoreType);
        keystore.load(keyStoreInputStream, Settings.keystorePassword.toCharArray());

        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("truststore.jks").getFile());

        SSLBuilder = SSLBuilder
                .loadTrustMaterial(file, Settings.truststorePassword.toCharArray())
                .loadKeyMaterial(keystore, Settings.clientKeyPassword.toCharArray() );

        //Building the SSLContext usiong the build() method
        SSLContext sslcontext = SSLBuilder.build();

        //Creating SSLConnectionSocketFactory object
        SSLConnectionSocketFactory sslConSocFactory = new SSLConnectionSocketFactory(sslcontext, new NoopHostnameVerifier());

        //Creating HttpClientBuilder
        HttpClientBuilder clientBuilder = HttpClients.custom();

        //Setting the SSLConnectionSocketFactory
        clientBuilder = clientBuilder.setSSLSocketFactory(sslConSocFactory);

        //Building the CloseableHttpClient
        CloseableHttpClient httpClient = clientBuilder.build();

        String channelgatewayEboxPain001URL = Settings.channelGatewayPain001FromEbox;
        System.out.println("channelgatewayEboxPain001URL : " + channelgatewayEboxPain001URL);
        HttpPost postRequest = new HttpPost(channelgatewayEboxPain001URL);

        //Add headers
        postRequest.addHeader("content-type", "application/xml");
        postRequest.addHeader("Accept", "application/xml");
        postRequest.addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
        postRequest.addHeader("Accept-Language", "en-US,en;q=0.5");
        postRequest.addHeader("content-type", "application/json; charset=UTF-8");

        try {
            do {//To perform retries

                //Set the request post body xml that we created
                StringEntity xmlBodyEntity = new StringEntity(message);

                System.out.println("Executing request :" + xmlBodyEntity);
                postRequest.setEntity(xmlBodyEntity);
                responseBody = httpClient.execute(postRequest);

                //verify the valid error code first
                statusCode = responseBody.getStatusLine().getStatusCode();

                System.out.println("statusCode :" + statusCode);
                if (statusCode >= 200 && statusCode < 300) {
                    HttpEntity entity = responseBody.getEntity();
                    results = "Passing";
                    ExtentTestManager.getTest().log(Status.PASS, "Response is successful");
                    break;
                } else {
                    i++;
                    ExtentTestManager.getTest().log(Status.INFO, "Warning:: No Records were Found on the 1st attempt ,the script will attempt to retry in 10 seconds");
                    Thread.sleep(10000);
                    results = "Failing";
                }
            } while (i <= intAPIWaitTimeIndex);


            //Stop the test if the response is null
            if (results.equals("Failing")) {
                ExtentTestManager.getTest().log(Status.FAIL, "FAIL:: The API response is null");
                stopCurrentTestSetResultsToFailedAndContinueWithNextTest();
            }

            //Now pull back the response object
            HttpEntity httpEntity = responseBody.getEntity();

            //Check results from API
            System.out.println("This is the response received after posting the payload " + httpEntity);
            System.out.println("==========================================================================================");



        } finally {
            //Important: Close the connect
            httpClient.close();
        }
    }
    public void postXMLPayload() throws Throwable
    {
        int i = 0;
        int intAPIWaitTimeIndex = 10;
        HttpResponse responseBody = null;
        int statusCode = 0;
        String results = null;

        //Declare HttpClient
        CloseableHttpClient httpClient = HttpClients.createDefault();

        //Define a postRequest request
        String endPointURI = Settings.sybrinStubDefaultEndPoint;
        HttpPost postRequest = new HttpPost(endPointURI);

        //Add headers
        postRequest.addHeader("content-type", "application/xml");
        postRequest.addHeader("Accept", "application/xml");
        postRequest.addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
        postRequest.addHeader("Accept-Language", "en-US,en;q=0.5");
        postRequest.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");

        try {
            do
            {//To perform retries

                //Set the request post body xml that we created
                String strPacs002PayloadForSingleTxs = getPacs002PayloadForSingleTxs();
                StringEntity xmlBodyEntity = new StringEntity(strPacs002PayloadForSingleTxs);
                System.out.println(xmlBodyEntity);

                System.out.println("Executing request " + xmlBodyEntity);
                postRequest.setEntity(xmlBodyEntity);

                //Send the request; It will immediately return the response in HttpResponse object if any
                responseBody = httpClient.execute(postRequest);

                //verify the valid error code first
                statusCode = responseBody.getStatusLine().getStatusCode();
                if (statusCode >= 200 && statusCode < 300)
                {
                    HttpEntity entity = responseBody.getEntity();
                    results = "Passing";
                    break;
                }
                else
                {
                    i++;
                    ExtentTestManager.getTest().log(Status.WARNING, "Warning:: No Records Found in the 1st attempt ,the script will attempt to retry");
                    Thread.sleep(5000);
                    results = "Failing";
                }
            } while (i <=intAPIWaitTimeIndex);


            //Stop the test if the response is null
            if(results.equals("Failing"))
            {
                ExtentTestManager.getTest().fail("The API response is null");
                stopCurrentTestSetResultsToFailedAndContinueWithNextTest();
            }

            //Now pull back the response object
            HttpEntity httpEntity = responseBody.getEntity();

            //Check results from API
            System.out.println("This is the response received after posting the payload "+httpEntity);
            System.out.println("==========================================================================================");

        }
        finally
        {
            //Important: Close the connect
            httpClient.close();
        }
    }
    public void PostXmlRequestPayload(String RequestXmlPayLoad, String BankCountry) throws Throwable {
        int i = 0;
        int intAPIWaitTimeIndex = 10;
        HttpResponse responseBody;
        int statusCode;
        String results;
        String URL = null;

        //Creating SSLContextBuilder object
        SSLContextBuilder SSLBuilder = SSLContexts.custom();

        InputStream keyStoreInputStream = getClass().getClassLoader().getResourceAsStream("keystore.jks");
        KeyStore keystore = KeyStore.getInstance(Settings.keystoreType);
        keystore.load(keyStoreInputStream, Settings.keystorePassword.toCharArray());

        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("truststore.jks").getFile());

        SSLBuilder = SSLBuilder
                .loadTrustMaterial(file, Settings.truststorePassword.toCharArray())
                .loadKeyMaterial(keystore, Settings.clientKeyPassword.toCharArray() );

        //Building the SSLContext usiong the build() method
        SSLContext sslcontext = SSLBuilder.build();

        //Creating SSLConnectionSocketFactory object
        SSLConnectionSocketFactory sslConSocFactory = new SSLConnectionSocketFactory(sslcontext, new NoopHostnameVerifier());

        //Creating HttpClientBuilder
        HttpClientBuilder clientBuilder = HttpClients.custom();

        //Setting the SSLConnectionSocketFactory
        clientBuilder = clientBuilder.setSSLSocketFactory(sslConSocFactory);

        //Building the CloseableHttpClient
        CloseableHttpClient httpClient = clientBuilder.build();

        //Derive the Request URL based on xml payload
        //Derive the RestToMQURL based on message type
        if(RequestXmlPayLoad.contains(Settings.directDebitMessage)){
            URL = Settings.restToMQEndPoint + "PAIN_008";
        }else if(RequestXmlPayLoad.contains("CstmrCdtTrfInitn")){
            URL = Settings.restToMQEndPoint + "PAIN_001";
        }else if(RequestXmlPayLoad.contains("ChargeEnquiryRequest")) {
            URL = Settings.chargesInquiry + BankCountry + "/charge-enquiries/";
        }else{
            ExtentTestManager.getTest().log(Status.INFO, "RequestXmlPayLoad Contains Invalid Value,No URL cannot be formed " );
        }

        HttpPost postRequest = new HttpPost(URL);

        //Add headers
        postRequest.addHeader("content-type", "application/xml");
        postRequest.addHeader("Accept", "application/xml");
        postRequest.addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
        postRequest.addHeader("Accept-Language", "en-US,en;q=0.5");
        postRequest.addHeader("content-type", "application/json; charset=UTF-8");

        try {
            do {//To perform retries

                //Set the request post body xml that we created
                StringEntity xmlBodyEntity = new StringEntity(RequestXmlPayLoad);

                System.out.println("Executing request :" + xmlBodyEntity);
                postRequest.setEntity(xmlBodyEntity);
                responseBody = httpClient.execute(postRequest);

                //verify the valid error code first
                statusCode = responseBody.getStatusLine().getStatusCode();
                System.out.println("statusCode :" + statusCode);

                if (statusCode >= 200 && statusCode < 300) {
                    HttpEntity entity = responseBody.getEntity();
                    String json = EntityUtils.toString(entity);
                    ExtentTestManager.codeLogsXML(json);
                    results = "Passing";
                    break;
                } else {
                    i++;
                    ExtentTestManager.getTest().log(Status.INFO, "No Response Received in 1st attempt,the script will attempt to retry");
                    Thread.sleep(2000);
                    results = "Failing";
                }
            } while (i <= intAPIWaitTimeIndex);

            //No Response Received for given Request
            if (results.equals("Failing")) {
                ExtentTestManager.getTest().log(Status.FAIL, "API Response is Null");
            }
        } finally {
            //Close the connection
            httpClient.close();
        }
    }
}
