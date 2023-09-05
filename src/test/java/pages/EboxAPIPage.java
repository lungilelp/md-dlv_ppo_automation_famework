package pages;

import base.BasePage;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import config.Settings;
import helpers.OutwardCreditHandlingModule;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.ssl.TrustStrategy;
import org.apache.http.util.EntityUtils;
import org.openqa.selenium.WebDriver;
import utils.extentreports.ExtentTestManager;
import utils.logs.Log;

import javax.net.ssl.SSLContext;
import java.io.File;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author DLV Automation Team
 * @date created 9/10/2019
 * @package pages
 */
public class EboxAPIPage extends BasePage {

    public EboxAPIPage(WebDriver driver) {

        super(driver);
        Log.info("The current working class: "+this.getClass().getName());
    }
    //class level variables
    String URL=null;
    GetMethod getMethod;
    HttpClient httpclient;
    int result=0;
    String pattern = "yyyy-MM-dd";
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
    String date = simpleDateFormat.format(new Date());
    boolean checkpoint1 = false;
    boolean checkpoint2 = false;
    boolean isTransactionDateCorrect = true;
    OutwardCreditHandlingModule outwardCreditHandlingModule = new OutwardCreditHandlingModule(driver);



    //***********************************STEPS**************************************************************************
    //******************************************************************************************************************


    /**Check debtor statements using API service */
    public void postingsItemEboxAPIDebtor(String BankCountry,String NumberOfTransactions, String DebtorBranchID,String DebtorAccountNumber,String DebtorPurposeOfPayment,String DebtorOriginationReference,String strWorkflowReferenceUniqueTxt,
                                          String CreditorAmount_1,String CreditorAmount_2,String CreditorAmount_3,String CreditorAmount_4,String CreditorAmount_5,String CreditorAmount_6,
                                          String CreditorAccountHolder_1,String CreditorAccountHolder_2,String CreditorAccountHolder_3,String CreditorAccountHolder_4,String CreditorAccountHolder_5,String CreditorAccountHolder_6,String EboxNarrative) throws Exception
    {
        try
        {

            //-------------------temporary-------------------------

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
            getCloseableHttpClient();

            //ExtentTestManager.getTest().log(Status.INFO, "-----------------------------------Itemised Debtor Statements------------------------------------");

            //---------------------------------------------------------------------------------------------------

            String[] debitAmounts ={CreditorAmount_1,CreditorAmount_2,CreditorAmount_3,CreditorAmount_4,CreditorAmount_5,CreditorAmount_6};

            int noOfCreditors = Integer.parseInt(NumberOfTransactions);
            int noOfTimesDebitRefIsPresent =0;
            int noOfTimesCreditRefIsPresent =0;
            int noOfTimesRefIsPresent =0;


            //URL = "https://cbgw-api-gateway.cbgw-uat.cto-payments-makola.270-nonprod.caas.absa.co.za/makola/core-banking-gateway/"+BankCountry+"/branches/"+DebtorBranchID+"/accounts/"+DebtorAccountNumber+"/statement?msgId=1&transactionId=1&sourceChannel=PPO";
            URL= Settings.fetchStetementEndPoint+BankCountry+"/branches/"+DebtorBranchID+"/accounts/"+DebtorAccountNumber+"/statement?msgId=1&transactionId=1&sourceChannel=PPO";

            HttpGet httpGet = new HttpGet(URL);
            //CloseableHttpClient httpClient = getCloseableHttpClient();
            CloseableHttpResponse res = httpClient.execute(httpGet);
            String json = EntityUtils.toString(res.getEntity());
            String[] outputFromQuery = json.split("statementLineItems");
            String [] allStatements =outputFromQuery[1].split("}");
            //System.out.println("statements->"+Arrays.toString(allStatements));

            List<String> collectDataForNoOfTimesUniqueRefIsPresent = new ArrayList<String>();
            List<String> collectDataForNoOfTimesRefIsPresent = new ArrayList<String>();
            List<String> arrayListForDebitAmounts = new ArrayList<String>();

            //below code is to store debit entries in array list
            ExtentTestManager.getTest().info(MarkupHelper.createLabel("Debtor Itemised", ExtentColor.PURPLE));
            ExtentTestManager.getTest().info(MarkupHelper.createLabel("All statements with unique workflow reference", ExtentColor.TEAL));
            for(int a=0;a<=allStatements.length-1;a++) {
                if(allStatements[a].contains("\"narrative\":\"" + strWorkflowReferenceUniqueTxt )) {
                    noOfTimesRefIsPresent++;
                    collectDataForNoOfTimesUniqueRefIsPresent.add(allStatements[a]);
                    ExtentTestManager.getTest().log(Status.INFO,  allStatements[a]);
                    if (allStatements[a].contains("\"postingType\":\"DEBIT\"")){
                           if(allStatements[a].contains("\"transactionDescription\":\"EBOX\"")){
                              if(allStatements[a].contains("\"subTransactionDescription\":\"" + EboxNarrative + "\"")){
                                  if(allStatements[a].contains("\"extendedNarrative\":\"\"")){
                                      if(!BankCountry.contains("SYC")&& allStatements[a].contains("\"counterPartyName\":\"\"")){
                                          if(allStatements[a].contains("\"transactionDate\":\"" + date + "\"")){
                                             noOfTimesDebitRefIsPresent++;
                                             collectDataForNoOfTimesRefIsPresent.add(allStatements[a]);
                                          }
                                          else{
                                              noOfTimesDebitRefIsPresent++;
                                              collectDataForNoOfTimesRefIsPresent.add(allStatements[a]);
                                              isTransactionDateCorrect=false;
                                          }
                                      }
                                      //counter party validation in progress
                                      else if(BankCountry.contains("SYC")){
                                          if(allStatements[a].contains("\"transactionDate\":\"" + date + "\"")){
                                              noOfTimesDebitRefIsPresent++;
                                              collectDataForNoOfTimesRefIsPresent.add(allStatements[a]);
                                          }
                                          else{
                                              noOfTimesDebitRefIsPresent++;
                                              collectDataForNoOfTimesRefIsPresent.add(allStatements[a]);
                                              isTransactionDateCorrect=false;
                                          }
                                      }
                                  }
                              }
                           }
                    }
                }
            }

            if(isTransactionDateCorrect==false) {
                ExtentTestManager.getTest().log(Status.WARNING, "Transaction date is not correct in Ebox. Issue to be raised with Ebox team." );
            }

            //convert array list into array- only debit entries
            String[] arrayOfDebitEntries = new String[collectDataForNoOfTimesRefIsPresent.size()];
            arrayOfDebitEntries = collectDataForNoOfTimesRefIsPresent.toArray(arrayOfDebitEntries);
            //display statements in report
            ExtentTestManager.getTest().info(MarkupHelper.createLabel("Debit Entries", ExtentColor.TEAL));
            for(int f=0; f<noOfTimesDebitRefIsPresent;f++) {
                int g=f+1;
                ExtentTestManager.getTest().log(Status.PASS, arrayOfDebitEntries[f]);
            }

            //------------check if the no. of debit entries are correct---------

            //below case is the scenario where all the transactions are passed and there is no correction
            if(noOfTimesDebitRefIsPresent==noOfCreditors&&noOfTimesDebitRefIsPresent==noOfTimesRefIsPresent) {
                ExtentTestManager.getTest().log(Status.PASS, "Count of debit entries is correct "+noOfTimesDebitRefIsPresent);
            }

            //below case is the scenario where all the transactions are NOT passed and there is correction
            else if(noOfTimesDebitRefIsPresent<noOfCreditors&&noOfTimesDebitRefIsPresent<noOfTimesRefIsPresent) {
                ExtentTestManager.getTest().info(MarkupHelper.createLabel("Correction Statements", ExtentColor.TEAL));
                //code to check correction
                for (int e = 0; e <= allStatements.length - 1; e++) {
                    if (allStatements[e].contains("\"postingType\":\"CREDIT\"") && allStatements[e].contains("\"narrative\":\"" + strWorkflowReferenceUniqueTxt)
                    && ((allStatements[e].contains("\"transactionDescription\":\"CORRECTION\"") && !BankCountry.contains("TZA")) ||
                            (allStatements[e].contains("\"transactionDescription\":\"REVERSAL\"") && BankCountry.contains("TZA"))) ){
                        noOfTimesCreditRefIsPresent++;
                        System.out.println("correction statements" + allStatements[e]);
                        ExtentTestManager.getTest().log(Status.INFO, "Statement " + allStatements[e]);
                    }
                }
                System.out.println("no of records for correction-" + noOfTimesCreditRefIsPresent);
                if (noOfTimesCreditRefIsPresent != 0) {
                    ExtentTestManager.getTest().log(Status.INFO, "Correction happened for " + noOfTimesCreditRefIsPresent + " time/s");
                }
                //below else will check if the no of unique ref are more without correction
                else{
                    ExtentTestManager.getTest().log(Status.FAIL, "No. of unique ref are more than expected without correction.");
                }
            }

            //below case is the scenario where debit did not happen because of failure of transaction and no of unique ref and debit ref is same
            else if(noOfTimesDebitRefIsPresent<noOfCreditors&&noOfTimesDebitRefIsPresent==noOfTimesRefIsPresent){
                    ExtentTestManager.getTest().log(Status.WARNING, "Amount is not debited from debtor because transaction/s failed");
                }

            //below condition checks if debit happened but correction happened later
            else if(noOfTimesDebitRefIsPresent==noOfCreditors&&noOfTimesDebitRefIsPresent>noOfTimesRefIsPresent){
                System.out.println("no of creditors-"+noOfCreditors);
                System.out.println("no of unique ref -"+noOfTimesRefIsPresent);
                System.out.println("no of debit ref-"+noOfTimesDebitRefIsPresent);
                ExtentTestManager.getTest().log(Status.PASS, "Amount is debited from debtor and reversal happened.");
            }

             //--------------------------------validating if the amounts are correct----------------------------------
            ExtentTestManager.getTest().info(MarkupHelper.createLabel("Amounts", ExtentColor.TEAL));
            //  i have noOfCreditors- create new array list using it

            List<String> ListamountsInTestData = new ArrayList<String>();
            for(int a=0; a<noOfCreditors;a++){
                ListamountsInTestData.add(debitAmounts[a]);
            }
            //ListamountsInTestData this list has all the amounts in test data.
            //convert this list into array
            String[] ArrayamountsInTestData = new String[noOfCreditors];
            ArrayamountsInTestData=ListamountsInTestData.toArray(ArrayamountsInTestData);

            //we have array of all unique reference entries- arrayOfDebitEntries
            //convert it to array
            String[] ArrayamountsInUniqueRefStatements = new String[noOfCreditors];
            ArrayamountsInUniqueRefStatements=collectDataForNoOfTimesUniqueRefIsPresent.toArray(ArrayamountsInUniqueRefStatements);

            //now create arraylist and array for amount not found
            List<String> amountNotPresent = new ArrayList<String>();

            boolean isFound = false;
            int logsFound = 0;
            int logsNotFound = 0;
            int a1, a2=0;

            for(a1=0;a1<ArrayamountsInTestData.length;a1++){
                for (a2=0;a2<arrayOfDebitEntries.length;a2++){
                    isFound=false;
                    if(arrayOfDebitEntries[a2].contains(ArrayamountsInTestData[a1])){
                        //System.out.println("amount "+ArrayamountsInTestData[a1]+" is present");
                        ExtentTestManager.getTest().log(Status.PASS, "Amount " + ArrayamountsInTestData[a1] + " is present in statement");
                        isFound=true;
                        break;
                    }
                }
                if(isFound==false){
                    amountNotPresent.add(ArrayamountsInTestData[a1]);
                    //System.out.println("amount "+ArrayamountsInTestData[a1]+" is not present");
                    logsNotFound= logsNotFound+1;
                    amountNotPresent.add(ArrayamountsInTestData[a1]);
                }
            }
            //convert amount not found in array
            String[] amountNotPresentArray = new String[logsNotFound];
            amountNotPresentArray = amountNotPresent.toArray(amountNotPresentArray);
            for(int h=0; h<logsNotFound;h++){
                ExtentTestManager.getTest().log(Status.FAIL, "Amount " + amountNotPresentArray[h] + " is not present in statement");
            }

             //code to test fees- TO DO

            ExtentTestManager.getTest().log(Status.INFO, "-------------------------------------------------------------------------------------------------");
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }

    /**Check debtor statements using API service */
    public void postingsConsoEboxAPIDebtor(String BankCountry,String NumberOfTransactions, String DebtorBranchID,String DebtorAccountNumber,String DebtorPurposeOfPayment,String DebtorOriginationReference,String strWorkflowReferenceUniqueTxt,
                                          String CreditorAmount_1,String CreditorAmount_2,String CreditorAmount_3,String CreditorAmount_4,String CreditorAmount_5,String CreditorAmount_6,
                                          String DebtorControlSum,String EboxNarrative) throws Exception
    {
        try
        {

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
            getCloseableHttpClient();

            //ExtentTestManager.getTest().log(Status.INFO, "-----------------------------------Consolidated Debtor Statements------------------------------------");
            String[] debitAmounts ={CreditorAmount_1,CreditorAmount_2,CreditorAmount_3,CreditorAmount_4,CreditorAmount_5,CreditorAmount_6};

            int noOfCreditors = Integer.parseInt(NumberOfTransactions);
            int noOfTimesDebitRefIsPresent =0;
            int noOfTimesCreditRefIsPresent =0;
            int noOfTimesRefIsPresent =0;

            URL = Settings.fetchStetementEndPoint+BankCountry+"/branches/"+DebtorBranchID+"/accounts/"+DebtorAccountNumber+"/statement?msgId=1&transactionId=1&sourceChannel=PPO";

            HttpGet httpGet = new HttpGet(URL);
            //CloseableHttpClient httpClient = getCloseableHttpClient();
            CloseableHttpResponse res = httpClient.execute(httpGet);
            String json = EntityUtils.toString(res.getEntity());
            String[] outputFromQuery = json.split("statementLineItems");
            String [] allStatements =outputFromQuery[1].split("}");

            List<String> collectDataForNoOfTimesRefIsPresent = new ArrayList<String>();
            List<String> arrayListForDebitAmounts = new ArrayList<String>();

            //below code is to store debit entries in array list
            ExtentTestManager.getTest().info(MarkupHelper.createLabel("Debtor Consolidated", ExtentColor.PURPLE));
            ExtentTestManager.getTest().info(MarkupHelper.createLabel("All statements with unique workflow refernces", ExtentColor.TEAL));
            for(int a=0;a<=allStatements.length-1;a++) {
                if(allStatements[a].contains("\"narrative\":\"" + strWorkflowReferenceUniqueTxt )) {
                    noOfTimesRefIsPresent++;
                    ExtentTestManager.getTest().log(Status.INFO,  allStatements[a]);
                    if (allStatements[a].contains("\"postingType\":\"DEBIT\"")){
                            if(allStatements[a].contains("\"transactionDescription\":\"EBOX\"")){
                                if(allStatements[a].contains("\"subTransactionDescription\":\"" + EboxNarrative + "\"")){
                                    if(allStatements[a].contains("\"amount\":"+DebtorControlSum)){
                                        if(!BankCountry.contains("SYC")&& allStatements[a].contains("\"counterPartyName\":\"\"")){
                                            if(allStatements[a].contains("\"transactionDate\":\"" + date + "\"")){
                                                noOfTimesDebitRefIsPresent++;
                                                collectDataForNoOfTimesRefIsPresent.add(allStatements[a]);
                                            }
                                            else{
                                                noOfTimesDebitRefIsPresent++;
                                                collectDataForNoOfTimesRefIsPresent.add(allStatements[a]);
                                                isTransactionDateCorrect=false;
                                            }
                                        }
                                        //counter party validation in progress
                                        else if(BankCountry.contains("SYC")){
                                            if(allStatements[a].contains("\"transactionDate\":\"" + date + "\"")){
                                                noOfTimesDebitRefIsPresent++;
                                                collectDataForNoOfTimesRefIsPresent.add(allStatements[a]);
                                            }
                                            else{
                                                noOfTimesDebitRefIsPresent++;
                                                collectDataForNoOfTimesRefIsPresent.add(allStatements[a]);
                                                isTransactionDateCorrect=false;
                                            }
                                        }
                                    }
                                }
                            }
                    }
                }
            }
            System.out.println("no of times workref is present "+noOfTimesRefIsPresent);
            System.out.println("no of times debit is present "+noOfTimesDebitRefIsPresent);

            if(isTransactionDateCorrect==false) {
                ExtentTestManager.getTest().log(Status.WARNING, "Transaction date is not correct in Ebox. Issue to be raised with Ebox team." );
            }
            //convert array list into array- only debit entries
            String[] arrayOfDebitEntries = new String[collectDataForNoOfTimesRefIsPresent.size()];
            arrayOfDebitEntries = collectDataForNoOfTimesRefIsPresent.toArray(arrayOfDebitEntries);
            //display statements in report
            ExtentTestManager.getTest().info(MarkupHelper.createLabel("Debit Entries ", ExtentColor.TEAL));
            for(int f=0; f<noOfTimesDebitRefIsPresent;f++) {
                int g=f+1;
                ExtentTestManager.getTest().log(Status.PASS, "Debit Statement "+  arrayOfDebitEntries[f]);
            }

            //------------check if the no. of debit entries are correct---------
            if(noOfTimesDebitRefIsPresent==1){
                ExtentTestManager.getTest().log(Status.PASS, "Count of debit entries is correct "+noOfTimesDebitRefIsPresent);
            }

            //check if the workflow ref is present more than no of creditors
            else if(noOfTimesDebitRefIsPresent>1) {
                ExtentTestManager.getTest().info(MarkupHelper.createLabel("Correction Statements", ExtentColor.TEAL));
                //code to check correction
                for (int e = 0; e <= allStatements.length - 1; e++) {
                    if (allStatements[e].contains("\"postingType\":\"CREDIT\"") && allStatements[e].contains("\"narrative\":\"" + strWorkflowReferenceUniqueTxt)
                            && allStatements[e].contains("\"transactionDescription\":\"CORRECTION\"")
                           // && allStatements[e].contains("\"transactionDate\":\"" + date + "\"")
                            && allStatements[e].contains("\"extendedNarrative\":\"\"")
                            && allStatements[e].contains("\"counterPartyName\":\"\"")) {
                        noOfTimesCreditRefIsPresent++;
                        System.out.println("correction statements" + allStatements[e]);
                        ExtentTestManager.getTest().log(Status.INFO, "Correction Statement " + allStatements[e]);
                    }
                }
                System.out.println("no of records for correction-" + noOfTimesCreditRefIsPresent);
                if (noOfTimesCreditRefIsPresent != 0) {
                    ExtentTestManager.getTest().log(Status.WARNING, "Correction happened for " + noOfTimesCreditRefIsPresent + " time/s");
                }
                //below else will check if the no of debits are more without correction
                else{
                    ExtentTestManager.getTest().log(Status.FAIL, "No. of debits are more than expected without correction.");
                }
            }
            //below code will tell you if the no. of debits are less. So you can check if the transaction is failed.
            else if(noOfTimesDebitRefIsPresent<1){
                ExtentTestManager.getTest().log(Status.FAIL, "Debit statements are less than expected. Please check if any transaction is failed for consolidated. Or the statements are not correct.");
            }
            else{
                ExtentTestManager.getTest().log(Status.FAIL, "This condition is unknown and not handled.");
            }

            //code to test fees- TO DO

            ExtentTestManager.getTest().log(Status.INFO, "-------------------------------------------------------------------------------------------------");
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }

    /**Check processing suspense account statements using API service */
    public void postingsItemEboxAPISuspense(String BankCountry,String NumberOfTransactions, String DebtorBranchID,String DebtorAccountNumber,String DebtorPurposeOfPayment,String DebtorOriginationReference, String CreditorAmount_1,String CreditorAmount_2,String CreditorAmount_3,String CreditorAmount_4,String CreditorAmount_5,String CreditorAmount_6,String strWorkflowReferenceUniqueTxt,String ProcessingSuspenseAccountBranchID,
                                            String ProcessingSuspenseAccount) throws Exception
    {
        try
        {

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
            getCloseableHttpClient();

            //just return for now
            //ExtentTestManager.getTest().log(Status.INFO, "-----------------------------------Itemised Processing Suspense Account Statements------------------------------------");
            String[] debitAmounts ={CreditorAmount_1,CreditorAmount_2,CreditorAmount_3,CreditorAmount_4,CreditorAmount_5,CreditorAmount_6};

            int noOfCreditors = Integer.parseInt(NumberOfTransactions);
            int noOfTimesDebitRefIsPresent =0;
            int noOfTimesCreditRefIsPresent =0;
            int noOfTimesRefIsPresent =0;

            URL = Settings.fetchStetementEndPoint+BankCountry+"/branches/"+ProcessingSuspenseAccountBranchID+"/accounts/"+ProcessingSuspenseAccount+"/statement?msgId=1&transactionId=1&sourceChannel=PPO";

            HttpGet httpGet = new HttpGet(URL);
            //CloseableHttpClient httpClient = getCloseableHttpClient();
            CloseableHttpResponse res = httpClient.execute(httpGet);
            String json = EntityUtils.toString(res.getEntity());
            String[] outputFromQuery = json.split("statementLineItems");
            String [] allStatements =outputFromQuery[1].split("}");
            //System.out.println("statements->"+Arrays.toString(allStatements));

            List<String> collectDataForNoOfTimesRefIsPresent = new ArrayList<String>();
            List<String> arrayListForDebitAmounts = new ArrayList<String>();

            //below code is to store debit entries in array list
            ExtentTestManager.getTest().info(MarkupHelper.createLabel("Processing Suspense (Itemised) Account statements", ExtentColor.PURPLE));
            ExtentTestManager.getTest().info(MarkupHelper.createLabel("All statements for current test scenario", ExtentColor.TEAL));
            for(int a=0;a<=allStatements.length-1;a++) {
                if(allStatements[a].contains("\"narrative\":\"" + strWorkflowReferenceUniqueTxt )) {
                    //ExtentTestManager.getTest().log(Status.INFO, "All Statement "+ a + allStatements[a]);
                            if(allStatements[a].contains("\"transactionDescription\":\"\"")){
                                if(allStatements[a].contains("\"subTransactionDescription\":\"\"")){
                                    if(allStatements[a].contains("\"extendedNarrative\":\"PPO-")){
                                        if(allStatements[a].contains("\"counterPartyName\":\""+DebtorBranchID+"/"+DebtorAccountNumber)){
                                            if(allStatements[a].contains("\"transactionDate\":\"" + date + "\"")){
                                                noOfTimesDebitRefIsPresent++;
                                                ExtentTestManager.getTest().log(Status.INFO, allStatements[a]);
                                            }
                                            else{
                                                noOfTimesDebitRefIsPresent++;
                                                ExtentTestManager.getTest().log(Status.INFO, allStatements[a]);
                                                isTransactionDateCorrect=false;
                                            }
                                        }
                                    }
                                }
                            }

                    //noOfTimesRefIsPresent++;
                    //ExtentTestManager.getTest().log(Status.INFO, "All Statement "+ a + allStatements[a]);
                    //&& allStatements[a].contains("\"extendedNarrative\":\"\""))
                }
            }
            if(isTransactionDateCorrect==false) {
                ExtentTestManager.getTest().log(Status.WARNING, "Transaction date is not correct in Ebox. Issue to be raised with Ebox team." );
            }
            if(noOfTimesDebitRefIsPresent<=0){
                ExtentTestManager.getTest().log(Status.FAIL, "Statements are not as expected");
            }
            //check if debtor bank code and creditor bank code is same
            //and verify DEBIT and CREDIT statements

            //check if debtor bank code and creditor bank code are different
            //and verify DEBIT statements

        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }

    /**Check processing suspense account statements using API service for consolidated*/
    public void postingsConsoEboxAPISuspense(String BankCountry,String NumberOfTransactions, String DebtorBranchID,String DebtorAccountNumber,String DebtorPurposeOfPayment,String DebtorOriginationReference, String CreditorAmount_1,String CreditorAmount_2,String CreditorAmount_3,String CreditorAmount_4,String CreditorAmount_5,String CreditorAmount_6,String strWorkflowReferenceUniqueTxt,String ProcessingSuspenseAccountBranchID,
                                             String ProcessingSuspenseAccount) throws Exception
    {
        try
        {
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
            getCloseableHttpClient();

            //just return for now
            //ExtentTestManager.getTest().log(Status.INFO, "-----------------------------------Consolidated Processing Suspense Account Statements------------------------------------");
            String[] debitAmounts ={CreditorAmount_1,CreditorAmount_2,CreditorAmount_3,CreditorAmount_4,CreditorAmount_5,CreditorAmount_6};

            int noOfCreditors = Integer.parseInt(NumberOfTransactions);
            int noOfTimesDebitRefIsPresent =0;
            int noOfTimesCreditRefIsPresent =0;
            int noOfTimesRefIsPresent =0;

            URL = Settings.fetchStetementEndPoint+BankCountry+"/branches/"+ProcessingSuspenseAccountBranchID+"/accounts/"+ProcessingSuspenseAccount+"/statement?msgId=1&transactionId=1&sourceChannel=PPO";

            HttpGet httpGet = new HttpGet(URL);
            //CloseableHttpClient httpClient = getCloseableHttpClient();
            CloseableHttpResponse res = httpClient.execute(httpGet);
            String json = EntityUtils.toString(res.getEntity());
            String[] outputFromQuery = json.split("statementLineItems");
            String [] allStatements =outputFromQuery[1].split("}");
            //System.out.println("statements->"+Arrays.toString(allStatements));

            List<String> collectDataForNoOfTimesRefIsPresent = new ArrayList<String>();
            List<String> arrayListForDebitAmounts = new ArrayList<String>();

            //below code is to store debit entries in array list
            ExtentTestManager.getTest().info(MarkupHelper.createLabel("Processing Suspense (Consolidated) Account statements", ExtentColor.PURPLE));
            ExtentTestManager.getTest().info(MarkupHelper.createLabel("All statements for current test scenario", ExtentColor.TEAL));
            for(int a=0;a<=allStatements.length-1;a++) {
                if(allStatements[a].contains("\"narrative\":\"" + strWorkflowReferenceUniqueTxt )) {
                    noOfTimesRefIsPresent++;
                    //ExtentTestManager.getTest().log(Status.INFO, "Statement "+ a + allStatements[a]);
                        if(allStatements[a].contains("\"transactionDescription\":\"\"")){
                            if(allStatements[a].contains("\"subTransactionDescription\":\"\"")){
                                if(allStatements[a].contains("\"extendedNarrative\":\"PPO-")){
                                    if(allStatements[a].contains("\"counterPartyName\":\""+DebtorBranchID+"/"+DebtorAccountNumber)){
                                        if(allStatements[a].contains("\"transactionDate\":\"" + date + "\"")){
                                            noOfTimesDebitRefIsPresent++;
                                            ExtentTestManager.getTest().log(Status.INFO, allStatements[a]);
                                        }
                                        else{
                                            noOfTimesDebitRefIsPresent++;
                                            ExtentTestManager.getTest().log(Status.INFO, allStatements[a]);
                                            isTransactionDateCorrect=false;
                                        }
                                    }
                                }
                            }
                        }
                }
            }
            if(isTransactionDateCorrect==false) {
                ExtentTestManager.getTest().log(Status.WARNING, "Transaction date is not correct in Ebox. Issue to be raised with Ebox team." );
            }

            if(noOfTimesDebitRefIsPresent<=0){
                ExtentTestManager.getTest().log(Status.FAIL, "Statements are not as expected");
            }
            //check if debtor bank code and creditor bank code is same
            //and verify DEBIT and CREDIT statements

            //check if debtor bank code and creditor bank code are different
            //and verify DEBIT statements

        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }

    /**Check exception suspense account statements using API service */
    public void postingsEboxAPIException(String BankCountry,String NumberOfTransactions, String DebtorBranchID,String DebtorAccountNumber,String DebtorPurposeOfPayment,String DebtorOriginationReference, String CreditorAmount_1,String CreditorAmount_2,String CreditorAmount_3,String CreditorAmount_4,String CreditorAmount_5,String CreditorAmount_6,String strWorkflowReferenceUniqueTxt,String ExceptionSuspenseAccountBranchId,
                                            String ExceptionSuspenseAccountNumber) throws Exception
    {
        try
        {
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
            getCloseableHttpClient();

            //just return for now
            //ExtentTestManager.getTest().log(Status.INFO, "-----------------------------------Itemised Exception Suspense Account Statements------------------------------------");
            String[] debitAmounts ={CreditorAmount_1,CreditorAmount_2,CreditorAmount_3,CreditorAmount_4,CreditorAmount_5,CreditorAmount_6};

            int noOfCreditors = Integer.parseInt(NumberOfTransactions);
            int noOfTimesDebitRefIsPresent =0;
            int noOfTimesCreditRefIsPresent =0;
            int noOfTimesRefIsPresent =0;

            URL = Settings.fetchStetementEndPoint+BankCountry+"/branches/"+ExceptionSuspenseAccountBranchId+"/accounts/"+ExceptionSuspenseAccountNumber+"/statement?msgId=1&transactionId=1&sourceChannel=PPO";

            HttpGet httpGet = new HttpGet(URL);
            //CloseableHttpClient httpClient = getCloseableHttpClient();
            CloseableHttpResponse res = httpClient.execute(httpGet);
            String json = EntityUtils.toString(res.getEntity());
            String[] outputFromQuery = json.split("statementLineItems");
            String [] allStatements =outputFromQuery[1].split("}");
            //System.out.println("statements->"+Arrays.toString(allStatements));

            List<String> collectDataForNoOfTimesRefIsPresent = new ArrayList<String>();
            List<String> arrayListForDebitAmounts = new ArrayList<String>();

            //below code is to store debit entries in array list
            ExtentTestManager.getTest().info(MarkupHelper.createLabel("Exception Account statements", ExtentColor.PURPLE));
            ExtentTestManager.getTest().info(MarkupHelper.createLabel("Below statements are present for current test scenario", ExtentColor.TEAL));
            for(int a=0;a<=allStatements.length-1;a++) {
                if(allStatements[a].contains("\"narrative\":\"" + strWorkflowReferenceUniqueTxt )) {
                        if(allStatements[a].contains("\"counterPartyName\":\""+DebtorBranchID+"/"+DebtorAccountNumber)) {
                            if((allStatements[a].contains("\"transactionDescription\":\"CORRECTION\"") && !BankCountry.contains("TZA")) ||
                                    (allStatements[a].contains("\"transactionDescription\":\"REVERSAL\"") && BankCountry.contains("TZA"))){
                                    if (allStatements[a].contains("\"extendedNarrative\":\"PPO-")) {
                                        if (allStatements[a].contains("\"subTransactionDescription\":\"\"")) {
                                            if(allStatements[a].contains("\"transactionDate\":\"" + date + "\"")){
                                                ExtentTestManager.getTest().log(Status.INFO, allStatements[a]);
                                            }
                                            else{
                                                ExtentTestManager.getTest().log(Status.INFO, allStatements[a]);
                                                isTransactionDateCorrect=false;
                                            }
                                        }
                                    }
                            }
                        }
                    noOfTimesRefIsPresent++;
                }
            }
            if(isTransactionDateCorrect==false) {
                ExtentTestManager.getTest().log(Status.WARNING, "Transaction date is not correct in Ebox. Issue to be raised with Ebox team." );
            }

            //check if debtor bank code and creditor bank code is same
            //and verify DEBIT and CREDIT statements

            //check if debtor bank code and creditor bank code are different
            //and verify DEBIT statements
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }

    /**Check credit account statements using API service */
    public void postingsEboxAPICreditors(String BankCountry,String NumberOfTransactions,String DebtorBankCode,String DebtorPurposeOfPayment,String strWorkflowReferenceUniqueTxt,
                                         String CreditorBankCode_1,String CreditorBranchId_1,String CreditorAccountNumber_1,String CreditorAmount_1,
                                         String CreditorBankCode_2, String CreditorBranchId_2,String CreditorAccountNumber_2,String CreditorAmount_2,
                                         String CreditorBankCode_3,String CreditorBranchId_3,String CreditorAccountNumber_3,String CreditorAmount_3,
                                         String CreditorBankCode_4,String CreditorBranchId_4,String CreditorAccountNumber_4,String CreditorAmount_4,
                                         String CreditorBankCode_5,String CreditorBranchId_5, String CreditorAccountNumber_5,String CreditorAmount_5,
                                         String CreditorBankCode_6,String CreditorBranchId_6,String CreditorAccountNumber_6,String CreditorAmount_6,
                                         String EboxNarrative, String ClearingAccountBranchId, String ClearingAccountNumber, String DebtorBranchID, String DebtorAccountNumber) throws Exception
    {
        try
        {
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
            getCloseableHttpClient();

            int noOfCreditors = Integer.parseInt(NumberOfTransactions);
            int noOfOnUsCreditors = 0;int b=0;

            String[] arrayOfCreditorsBankCodes = {CreditorBankCode_1, CreditorBankCode_2, CreditorBankCode_3, CreditorBankCode_4, CreditorBankCode_5, CreditorBankCode_6};
            String[] arrayOfCreditorsBranchCodes = {CreditorBranchId_1, CreditorBranchId_2, CreditorBranchId_3, CreditorBranchId_4, CreditorBranchId_5, CreditorBranchId_6};
            String[] arrayOfCreditorsAccountNumber = {CreditorAccountNumber_1, CreditorAccountNumber_2, CreditorAccountNumber_3, CreditorAccountNumber_4, CreditorAccountNumber_5, CreditorAccountNumber_6};
            String[] arrayOfCreditorsAmount = {CreditorAmount_1, CreditorAmount_2, CreditorAmount_3, CreditorAmount_4, CreditorAmount_5, CreditorAmount_6};
            String [] allStatements=null;
            ExtentTestManager.getTest().info(MarkupHelper.createLabel("Creditors statements", ExtentColor.PURPLE));
            ExtentTestManager.getTest().info(MarkupHelper.createLabel("Creditors statements below", ExtentColor.TEAL));
            for(int a=0;a<noOfCreditors;a++){
                if(arrayOfCreditorsBankCodes[a].equals(DebtorBankCode)){
                    URL = Settings.fetchStetementEndPoint+BankCountry+"/branches/"+arrayOfCreditorsBranchCodes[a]+"/accounts/"+arrayOfCreditorsAccountNumber[a]+"/statement?msgId=1&transactionId=1&sourceChannel=PPO";
                    HttpGet httpGet = new HttpGet(URL);
                    //CloseableHttpClient httpClient = getCloseableHttpClient();
                    CloseableHttpResponse res = httpClient.execute(httpGet);
                    String json = EntityUtils.toString(res.getEntity());
                    String[] outputFromQuery = json.split("statementLineItems");
                    allStatements =outputFromQuery[1].split("}");
                    for(b=0;b<=allStatements.length-1;b++) {
                        if(allStatements[b].contains("\"postingType\":\"CREDIT\"")
                                && allStatements[b].contains("\"narrative\":\""+strWorkflowReferenceUniqueTxt)
                                && allStatements[b].contains("\"amount\":"+arrayOfCreditorsAmount[a])
                                && allStatements[b].contains("\"transactionDescription\":\"EBOX\"")
                                && allStatements[b].contains("\"subTransactionDescription\":\"" + EboxNarrative + "\"")
                                && ((allStatements[b].contains("\"extendedNarrative\":\"RemiTran")) || (allStatements[b].contains("\"extendedNarrative\":\"" + strWorkflowReferenceUniqueTxt + "RemiTran")))
                                && allStatements[b].contains("\"counterPartyName\":\"\"")) {
                            //System.out.println(allStatements[b]+" for creditor "+arrayOfCreditorsBankCodes[a]+"/"+arrayOfCreditorsBranchCodes[a]+"/"+arrayOfCreditorsAccountNumber[a]+", amount "+arrayOfCreditorsAmount[a]+" is credited");
                            ExtentTestManager.getTest().log(Status.INFO, allStatements[b]+" for creditor "+arrayOfCreditorsBankCodes[a]+"/"+arrayOfCreditorsBranchCodes[a]+"/"+arrayOfCreditorsAccountNumber[a]+", amount "+arrayOfCreditorsAmount[a]+" is credited");
                        }
                    }
                }
                else{
                    ExtentTestManager.getTest().log(Status.INFO, arrayOfCreditorsBankCodes[a]+" Creditor is offus");

                    //if country is MUS or SYC, check clearing account
                    if(BankCountry.contains("MUS")||BankCountry.contains("SYC")){
                        ExtentTestManager.getTest().info(MarkupHelper.createLabel("Clearing Suspense Account (Applicable only to MUS and SYC)", ExtentColor.PURPLE));
                        ExtentTestManager.getTest().info(MarkupHelper.createLabel("Clearing Suspense Account statements below", ExtentColor.TEAL));
                        URL = Settings.fetchStetementEndPoint+BankCountry+"/branches/"+ClearingAccountBranchId+"/accounts/"+ClearingAccountNumber+"/statement?msgId=1&transactionId=1&sourceChannel=PPO";
                        HttpGet httpGet = new HttpGet(URL);
                       // CloseableHttpClient httpClient = getCloseableHttpClient();
                        CloseableHttpResponse res = httpClient.execute(httpGet);
                        String json = EntityUtils.toString(res.getEntity());
                        String[] outputFromQuery = json.split("statementLineItems");
                        allStatements =outputFromQuery[1].split("}");

                        for(b=0;b<=allStatements.length-1;b++) {
                            if(BankCountry.contains("MUS") && allStatements[b].contains("\"postingType\":\"CREDIT\"")&&allStatements[b].contains("\"narrative\":\""+strWorkflowReferenceUniqueTxt)&&allStatements[b].contains("\"amount\":"+arrayOfCreditorsAmount[a])
                                    &&allStatements[b].contains("\"transactionDescription\":\"\"")&& allStatements[b].contains("\"subTransactionDescription\":\"\"")
                                    && allStatements[b].contains("\"extendedNarrative\":\"PPO-") && allStatements[b].contains("\"counterPartyName\":\""+DebtorBranchID+"/"+DebtorAccountNumber)) {
                                //System.out.println(allStatements[b]+" for creditor "+arrayOfCreditorsBankCodes[a]+"/"+arrayOfCreditorsBranchCodes[a]+"/"+arrayOfCreditorsAccountNumber[a]+", amount "+arrayOfCreditorsAmount[a]+" is credited");
                                ExtentTestManager.getTest().log(Status.INFO, allStatements[b]+" for clearing suspense account  "+arrayOfCreditorsBankCodes[a]+"/"+arrayOfCreditorsBranchCodes[a]+"/"+arrayOfCreditorsAccountNumber[a]+", amount "+arrayOfCreditorsAmount[a]+" is credited");
                            }
                            else if(BankCountry.contains("SYC") && allStatements[b].contains("\"postingType\":\"CREDIT\"")&&allStatements[b].contains("\"narrative\":\""+strWorkflowReferenceUniqueTxt)&&allStatements[b].contains("\"amount\":"+arrayOfCreditorsAmount[a])
                                    &&allStatements[b].contains("\"transactionDescription\":\"EBOX\"")&& allStatements[b].contains("\"subTransactionDescription\":\"" + EboxNarrative + "\""))
                                    //&& allStatements[b].contains("\"extendedNarrative\":\""+outwardCreditHandlingModule.SystemRefNumber+"\""))
                            {
                                ExtentTestManager.getTest().log(Status.INFO, allStatements[b]+" for outward clearing suspense account  "+arrayOfCreditorsBankCodes[a]+"/"+arrayOfCreditorsBranchCodes[a]+"/"+arrayOfCreditorsAccountNumber[a]+", amount "+arrayOfCreditorsAmount[a]+" is credited");
                            }

                            else{
                            System.out.println("clearing statement-"+allStatements[a]);
                            }
                        }
                    }
                }
            }
            if(isTransactionDateCorrect==false) {
                ExtentTestManager.getTest().log(Status.WARNING, "Transaction date is not correct in Ebox. Issue to be raised with Ebox team." );
            }
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }

    public static CloseableHttpClient getCloseableHttpClient()
    {
        CloseableHttpClient httpClient = null;
        try {
            httpClient = HttpClients.custom().
                    setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE).
                    setSSLContext(new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy()
                    {
                        public boolean isTrusted(X509Certificate[] arg0, String arg1) throws CertificateException
                        {
                            return true;
                        }
                    }).build()).build();
        } catch (KeyManagementException e1) {
            e1.printStackTrace();
        } catch (NoSuchAlgorithmException e2) {
            e2.printStackTrace();
        } catch (KeyStoreException e3) {
            e3.printStackTrace();
        }
        return httpClient;
    }

}