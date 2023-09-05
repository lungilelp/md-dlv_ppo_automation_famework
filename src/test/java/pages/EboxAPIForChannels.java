package pages;

import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import config.Settings;
import helpers.FetchPaymentDetailsFromDB;
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
import utils.extentreports.ExtentTestManager;

import javax.net.ssl.SSLContext;
import java.io.File;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static utils.extentreports.ExtentTestManager.codeLogsXML;


/** DLV Automation Team created 10/08/2021 */

public class EboxAPIForChannels{
    //class level variables
    String URL=null;
    String pattern = "yyyy-MM-dd";
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
    String date = simpleDateFormat.format(new Date());
    boolean isTransactionDateCorrect = true;
    String [] allStatements;
    FetchPaymentDetailsFromDB fetchPaymentDetailsFromDB = new FetchPaymentDetailsFromDB();

    public void fetchSSLCertificateDetails(String BankCountry,String BranchID,String AccountNumber ){
        try {
            SSLContextBuilder SSLBuilder = SSLContexts.custom();
            InputStream keyStoreInputStream = getClass().getClassLoader().getResourceAsStream("keystore.jks");
            KeyStore keystore = KeyStore.getInstance(Settings.keystoreType);
            keystore.load(keyStoreInputStream, Settings.keystorePassword.toCharArray());
            ClassLoader classLoader = getClass().getClassLoader();
            File file = new File(classLoader.getResource("truststore.jks").getFile());
            SSLBuilder = SSLBuilder
                    .loadTrustMaterial(file, Settings.truststorePassword.toCharArray())
                    .loadKeyMaterial(keystore, Settings.clientKeyPassword.toCharArray());
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

            //URL = "https://cbgw-api-gateway.cbgw-uat.cto-payments-makola.270-nonprod.caas.absa.co.za/makola/core-banking-gateway/"+BankCountry+"/branches/"+BranchID+"/accounts/"+AccountNumber+"/statement?msgId=1&transactionId=1&sourceChannel=PPO";
            URL =Settings.fetchStetementEndPoint+BankCountry+"/branches/"+BranchID+"/accounts/"+AccountNumber+"/statement?msgId=1&transactionId=1&sourceChannel=PPO";

            System.out.println(URL);
            HttpGet httpGet = new HttpGet(URL);
            CloseableHttpResponse res = httpClient.execute(httpGet);
            String json = EntityUtils.toString(res.getEntity());
            String[] outputFromQuery = json.split("statementLineItems");
            allStatements =outputFromQuery[1].split("}");

        }catch (Exception e){
            System.out.println(e);
        }
    }

    /**Check debtor statements using API service */
    public void fetchItemDebtorPostingsEboxAPI(String BankCountry,String NumberOfTransactions, String DebtorBranchID,
                                               String DebtorAccountNumber,String[] arrayOfCreditorsAmount,String EboxNarrative,
                                               String TransactionDescription,String[] arrayOfCreditorName,String CreditorOriginationRef,
                                               String CreditorDestinationRef)
    {
        try
        {
            fetchSSLCertificateDetails(BankCountry,DebtorBranchID,DebtorAccountNumber);

            int noOfCreditors = Integer.parseInt(NumberOfTransactions);
            int noOfTimesDebitRefIsPresent =0;
            int noOfTimesCreditRefIsPresent =0;
            int noOfTimesRefIsPresent =0;

            List<String> collectDataForNoOfTimesUniqueRefIsPresent = new ArrayList<>();
            List<String> collectDataForNoOfTimesRefIsPresent = new ArrayList<>();

            //below code is to store debit entries in array list
            ExtentTestManager.getTest().info(MarkupHelper.createLabel("Debtor(Itemised) Account Statements ", ExtentColor.PURPLE));
            for(int a=0;a<=allStatements.length-1;a++) {
                if(allStatements[a].contains("\"narrative\":\"" + CreditorOriginationRef )) {
                    noOfTimesRefIsPresent++;
                    collectDataForNoOfTimesUniqueRefIsPresent.add(allStatements[a]);
                    //ExtentTestManager.getTest().log(Status.INFO,  allStatements[a]);
                    ExtentTestManager.codeLogsXML(allStatements[a].replace(",","\n"));
                    if (allStatements[a].contains("\"postingType\":\"DEBIT\"")){
                        if(allStatements[a].contains("\"transactionDescription\":\"" + TransactionDescription)){
                            if(allStatements[a].contains("\"subTransactionDescription\":\"" + EboxNarrative + "\"")){
                                if(allStatements[a].contains("\"extendedNarrative\":\"\"")){
                                    if(!BankCountry.contains("SYC")&& allStatements[a].contains("\"counterPartyName\":\"\"") ||
                                            (BankCountry.contains("SYC")&& allStatements[a].contains("\"counterPartyName\":\"" + CreditorOriginationRef ))){
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
                                    /* Counter party validation in progress*/
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

            if(!isTransactionDateCorrect) {
                ExtentTestManager.getTest().log(Status.WARNING, "Transaction date is not correct in Ebox. Issue to be raised with Ebox team." );
            }

            //convert array list into array- only debit entries
            String[] arrayOfDebitEntries = new String[collectDataForNoOfTimesRefIsPresent.size()];
            arrayOfDebitEntries = collectDataForNoOfTimesRefIsPresent.toArray(arrayOfDebitEntries);
            //display statements in report
            ExtentTestManager.getTest().info(MarkupHelper.createLabel("Debit Entries", ExtentColor.TEAL));
            for(int f=0; f<noOfTimesDebitRefIsPresent;f++) {
                // int g=f+1;
               // ExtentTestManager.getTest().log(Status.PASS, arrayOfDebitEntries[f]);
                ExtentTestManager.codeLogsXML(arrayOfDebitEntries[f].replace(",","\n"));
            }

            //below case is the scenario where all the transactions are passed and there is no correction
            if(noOfTimesDebitRefIsPresent==noOfCreditors&&noOfTimesDebitRefIsPresent<=noOfTimesRefIsPresent) {
                ExtentTestManager.getTest().log(Status.PASS, "Count of debit entries is correct "+noOfTimesDebitRefIsPresent);
            }
            else if(noOfTimesDebitRefIsPresent<=noOfCreditors&&noOfTimesDebitRefIsPresent<noOfTimesRefIsPresent) {
                //below case is the scenario where all the transactions are NOT passed and there is correction
                ExtentTestManager.getTest().info(MarkupHelper.createLabel("Correction Statements", ExtentColor.TEAL));
                //code to check correction
                for (int e = 0; e <= allStatements.length - 1; e++) {
                    if (allStatements[e].contains("\"postingType\":\"CREDIT\"")
                            && allStatements[e].contains("\"narrative\":\"" + CreditorDestinationRef)
                            && (((allStatements[e].contains("\"transactionDescription\":\"CORRECTION\"") && !BankCountry.contains("TZA")) ||
                            (allStatements[e].contains("\"transactionDescription\":\"REVERSAL\"") && BankCountry.contains("TZA"))))){
                        noOfTimesCreditRefIsPresent++;
                        //ExtentTestManager.getTest().log(Status.INFO, "Statement " + allStatements[e]);
                        ExtentTestManager.codeLogsXML("Correction Statement " + allStatements[e].replace(",","\n"));
                    }
                }
                if (noOfTimesCreditRefIsPresent != 0) {
                    ExtentTestManager.getTest().log(Status.INFO, "Correction happened for " + noOfTimesCreditRefIsPresent + " time/s");
                }
            }

            //below condition checks if debit happened but correction happened later
            else if(noOfTimesDebitRefIsPresent==noOfCreditors&&noOfTimesDebitRefIsPresent>noOfTimesCreditRefIsPresent){
                //  else if(noOfTimesDebitRefIsPresent==noOfCreditors&&noOfTimesDebitRefIsPresent>noOfTimesRefIsPresent){
                ExtentTestManager.getTest().log(Status.PASS, "Amount is debited from debtor and reversal happened.");
            }

            //--------------------------------validating if the amounts are correct----------------------------------
            ExtentTestManager.getTest().info(MarkupHelper.createLabel("Amounts", ExtentColor.TEAL));

            List<String> ListamountsInTestData = new ArrayList<>();
            for(int a=0; a<noOfCreditors;a++){
                ListamountsInTestData.add(arrayOfCreditorsAmount[a]);
            }
            //ListamountsInTestData this list has all the amounts in test data.
            String[] ArrayamountsInTestData = new String[noOfCreditors];
            ArrayamountsInTestData=ListamountsInTestData.toArray(ArrayamountsInTestData);

            //now create arraylist and array for amount not found
            List<String> amountNotPresent = new ArrayList<>();

            boolean isFound = false;
            int logsNotFound = 0;
            int a1, a2;

            for(a1=0;a1<ArrayamountsInTestData.length;a1++){
                for (a2=0;a2<arrayOfDebitEntries.length;a2++){
                    isFound=false;
                    if(arrayOfDebitEntries[a2].contains(ArrayamountsInTestData[a1])){
                        ExtentTestManager.getTest().log(Status.PASS, "Amount " + ArrayamountsInTestData[a1] + " is present in statement");
                        isFound=true;
                        break;
                    }
                }
                if(!isFound){
                    amountNotPresent.add(ArrayamountsInTestData[a1]);
                    logsNotFound= logsNotFound+1;
                }
            }
            //convert amount not found in array
            String[] amountNotPresentArray = new String[logsNotFound];
            amountNotPresentArray = amountNotPresent.toArray(amountNotPresentArray);
            for(int h=0; h<logsNotFound;h++){
                ExtentTestManager.getTest().log(Status.INFO, "Amount " + amountNotPresentArray[h] + " is not present in statement");
            }
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }

    /**Check debtor statements using API service */
    public void fetchDebtorConsoPostingsEboxAPI(String BankCountry,String DebtorBranchID,String DebtorAccountNumber,
                                                String DebtorControlSum,String EboxNarrative,String TransactionDescription,
                                                String CreditorOriginationRef,String CreditorDestinationRef)
    {
        try
        {
            fetchSSLCertificateDetails(BankCountry,DebtorBranchID,DebtorAccountNumber);

            int noOfTimesDebitRefIsPresent =0;
            int noOfTimesCreditRefIsPresent =0;

            List<String> collectDataForNoOfTimesRefIsPresent = new ArrayList<String>();

            //below code is to store debit entries in array list
            ExtentTestManager.getTest().info(MarkupHelper.createLabel("Debtor(Consolidated) Account Statements ", ExtentColor.PURPLE));
            for(int a=0;a<=allStatements.length-1;a++) {
                if(allStatements[a].contains("\"narrative\":\"" + CreditorOriginationRef )) {
                   // ExtentTestManager.getTest().log(Status.INFO,  allStatements[a]);
                    ExtentTestManager.codeLogsXML(allStatements[a].replace(",","\n"));
                    if (allStatements[a].contains("\"postingType\":\"DEBIT\"")){
                        if(allStatements[a].contains("\"transactionDescription\":\"" + TransactionDescription)){
                            if(allStatements[a].contains("\"subTransactionDescription\":\"" + EboxNarrative + "\"")){
                                if(allStatements[a].contains("\"amount\":"+DebtorControlSum)){
                                    if(!BankCountry.contains("SYC") && (allStatements[a].contains("\"counterPartyName\":\"\""))) {
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
                                    else if //(BankCountry.contains("SYC")){
                                    ((BankCountry.contains("SYC")) && (allStatements[a].contains("\"counterPartyName\":\"" + "Multiple Beneficiaries" + "\""))){
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

            if(!isTransactionDateCorrect) {
                ExtentTestManager.getTest().log(Status.WARNING, "Transaction date is not correct in Ebox. Issue to be raised with Ebox team." );
            }
            //convert array list into array- only debit entries
            String[] arrayOfDebitEntries = new String[collectDataForNoOfTimesRefIsPresent.size()];
            arrayOfDebitEntries = collectDataForNoOfTimesRefIsPresent.toArray(arrayOfDebitEntries);
            //display statements in report
            ExtentTestManager.getTest().info(MarkupHelper.createLabel("Debit Entries ", ExtentColor.TEAL));
            for(int f=0; f<noOfTimesDebitRefIsPresent;f++) {
                // int g=f+1;
                //ExtentTestManager.getTest().log(Status.PASS, "Debit Statement "+  arrayOfDebitEntries[f]);
                ExtentTestManager.codeLogsXML("Debit Statement \n"+ arrayOfDebitEntries[f].replace(",","\n"));
            }

            //------------check if the no. of debit entries are correct---------
            if(noOfTimesDebitRefIsPresent==1){
                ExtentTestManager.getTest().log(Status.PASS, "Count of debit entries is correct "+noOfTimesDebitRefIsPresent);
            }

            //check if the workflow ref is present more than no of creditors
            else if(noOfTimesDebitRefIsPresent>=1) {
                ExtentTestManager.getTest().info(MarkupHelper.createLabel("Correction Statements", ExtentColor.TEAL));
                for (int e = 0; e <= allStatements.length - 1; e++) {
                    if (allStatements[e].contains("\"postingType\":\"CREDIT\"")
                            && allStatements[e].contains("\"narrative\":\"" + CreditorOriginationRef)
                            && allStatements[e].contains("\"transactionDescription\":\"CORRECTION\"")
                            // && allStatements[e].contains("\"transactionDate\":\"" + date + "\"")
                            && allStatements[e].contains("\"extendedNarrative\":\"\"")
                            && allStatements[e].contains("\"counterPartyName\":\"\"")) {
                        noOfTimesCreditRefIsPresent++;
                        //ExtentTestManager.getTest().log(Status.INFO, "Correction Statement " + allStatements[e]);
                        ExtentTestManager.codeLogsXML("Correction Statement \n" + allStatements[e].replace(",","\n"));
                    }
                }
                if (noOfTimesCreditRefIsPresent != 0) {
                    ExtentTestManager.getTest().log(Status.WARNING, "Correction happened for " + noOfTimesCreditRefIsPresent + " time/s");
                }
                else{
                    //Check if the no of debits are more without correction
                    ExtentTestManager.getTest().log(Status.FAIL, "No. of debits are more than expected without correction.");
                }
            }
            else if(noOfTimesDebitRefIsPresent<1){
                //No. of debits are less, check if any transaction is failed.
                ExtentTestManager.getTest().log(Status.FAIL, "Debit statements are less than expected. Please check if any transaction is failed for consolidated. Or the statements are not correct.");
            }
            else{
                ExtentTestManager.getTest().log(Status.FAIL, "This condition is unknown and not handled.");
            }
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }

    /**Check processing suspense account statements using API service */
    public void fetchItemSuspenseAcctPostingsEboxAPI(String BankCountry,String DebtorBranchID,
                                                     String DebtorAccountNumber,String strPurposeOfPaymentDesc,
                                                     String ProcessingSuspenseAccountBranchID,String ProcessingSuspenseAccount,String PaymentInfoId)
    {
        try
        {
            fetchSSLCertificateDetails(BankCountry,ProcessingSuspenseAccountBranchID,ProcessingSuspenseAccount);

            int noOfTimesDebitRefIsPresent =0;

            //below code is to store debit entries in array list
            ExtentTestManager.getTest().info(MarkupHelper.createLabel("Processing Suspense (Itemised) Account statements", ExtentColor.PURPLE));
            for(int a=0;a<=allStatements.length-1;a++) {
                if(allStatements[a].contains("\"narrative\":\"" + strPurposeOfPaymentDesc )) {
                    if(allStatements[a].contains("\"transactionDescription\":\"\"")){
                        if(allStatements[a].contains("\"subTransactionDescription\":\"\"")){
                            if(allStatements[a].contains("\"extendedNarrative\":\"" + PaymentInfoId)){
                                if(allStatements[a].contains("\"counterPartyName\":\""+DebtorBranchID+"/"+DebtorAccountNumber)){
                                    if(allStatements[a].contains("\"transactionDate\":\"" + date + "\"")){
                                        noOfTimesDebitRefIsPresent++;
                                        //ExtentTestManager.getTest().log(Status.INFO, allStatements[a]);
                                        ExtentTestManager.codeLogsXML(allStatements[a].replace(",","\n"));
                                    }
                                    else{
                                        noOfTimesDebitRefIsPresent++;
                                        //ExtentTestManager.getTest().log(Status.INFO, allStatements[a]);
                                        ExtentTestManager.codeLogsXML(allStatements[a].replace(",","\n"));
                                        isTransactionDateCorrect=false;
                                    }
                                }
                            }
                        }
                    }
                }
            }
            if(!isTransactionDateCorrect) {
                ExtentTestManager.getTest().log(Status.WARNING, "Transaction date is not correct in Ebox. Issue to be raised with Ebox team." );
            }
//            if(noOfTimesDebitRefIsPresent<=0){
//                ExtentTestManager.getTest().log(Status.FAIL, "Statements are not as expected");
//            }
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }

    /**Check processing suspense account statements using API service for consolidated*/
    public void fetchConsoSuspenseAcctPostingsEboxAPI(String BankCountry,String DebtorBranchID,String DebtorAccountNumber,
                                                      String strPurposeOfPaymentDesc,String ProcessingSuspenseAccountBranchID,
                                                      String ProcessingSuspenseAccount,String PaymentInfoId,String ChannelRef) throws Exception
    {
        try
        {
            fetchSSLCertificateDetails(BankCountry,ProcessingSuspenseAccountBranchID,ProcessingSuspenseAccount);

            int noOfTimesDebitRefIsPresent =0;

            //below code is to store debit entries in array list
            ExtentTestManager.getTest().info(MarkupHelper.createLabel("Processing Suspense (Consolidated) Account statements", ExtentColor.PURPLE));
            for(int a=0;a<=allStatements.length-1;a++) {
                if(allStatements[a].contains("\"narrative\":\"" + strPurposeOfPaymentDesc )) {
                    if(allStatements[a].contains("\"transactionDescription\":\"\"")){
                        if(allStatements[a].contains("\"subTransactionDescription\":\"\"")){
                            if((allStatements[a].contains("\"extendedNarrative\":\"" + PaymentInfoId )) ||
                                    (allStatements[a].contains("\"extendedNarrative\":\"" + (ChannelRef + PaymentInfoId)))){
                                if(allStatements[a].contains("\"counterPartyName\":\""+DebtorBranchID+"/"+DebtorAccountNumber)){
                                    if(allStatements[a].contains("\"transactionDate\":\"" + date + "\"")){
                                        noOfTimesDebitRefIsPresent++;
                                        //ExtentTestManager.getTest().log(Status.INFO, allStatements[a]);
                                        ExtentTestManager.codeLogsXML(allStatements[a].replace(",","\n"));
                                    }
                                    else{
                                        noOfTimesDebitRefIsPresent++;
                                        //ExtentTestManager.getTest().log(Status.INFO, allStatements[a]);
                                        ExtentTestManager.codeLogsXML(allStatements[a].replace(",","\n"));
                                        isTransactionDateCorrect=false;
                                    }
                                }
                            }
                        }
                    }
                }
            }
            if(!isTransactionDateCorrect) {
                ExtentTestManager.getTest().log(Status.WARNING, "Transaction date is not correct in Ebox. Issue to be raised with Ebox team." );
            }

            if(noOfTimesDebitRefIsPresent<=0) {
                ExtentTestManager.getTest().log(Status.FAIL, "Statements are not as expected");
            }
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }

    /**Check exception suspense account statements using API service */
    public void fetchExceptionAcctPostingsEboxAPI(String BankCountry,String DebtorBranchID,String DebtorAccountNumber,
                                                  String strPurposeOfPaymentDesc,String ExceptionSuspenseAccountBranchId,
                                                  String ExceptionSuspenseAccountNumber,String PaymentInfoId)
    {
        try
        {
            fetchSSLCertificateDetails(BankCountry,ExceptionSuspenseAccountBranchId,ExceptionSuspenseAccountNumber);

            ExtentTestManager.getTest().info(MarkupHelper.createLabel("Exception Suspense Account statements", ExtentColor.PURPLE));
            for(int a=0;a<=allStatements.length-1;a++) {
                if(allStatements[a].contains("\"narrative\":\"" + strPurposeOfPaymentDesc )) {
                    if(allStatements[a].contains("\"counterPartyName\":\""+DebtorBranchID+"/"+DebtorAccountNumber)) {
                        if(allStatements[a].contains("\"transactionDescription\":\"CORRECTION\"")){
                            if (allStatements[a].contains("\"extendedNarrative\":\"" + PaymentInfoId)) {
                                if (allStatements[a].contains("\"subTransactionDescription\":\"\"")) {
                                    if(allStatements[a].contains("\"transactionDate\":\"" + date + "\"")){
                                      //  ExtentTestManager.getTest().log(Status.INFO, allStatements[a]);
                                        ExtentTestManager.codeLogsXML(allStatements[a].replace(",","\n"));
                                    }
                                    else{
                                        //ExtentTestManager.getTest().log(Status.INFO, allStatements[a]);
                                        ExtentTestManager.codeLogsXML(allStatements[a].replace(",","\n"));
                                        isTransactionDateCorrect=false;
                                    }
                                }
                            }
                        }
                    }
                }
            }
            if(!isTransactionDateCorrect) {
                ExtentTestManager.getTest().log(Status.WARNING, "Transaction date is not correct in Ebox. Issue to be raised with Ebox team." );
            }
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }

    /**Check credit account statements using API service */
    public void fetchCreditorPostingsEboxAPI(String BankCountry,String NumberOfTransactions,String DebtorBankCode,String DebtorPurposeOfPayment,
                                             String[] arrayOfCreditorsBankCodes,String[] arrayOfCreditorsBranchCodes,String[] arrayOfCreditorsAccountNumber,
                                             String[] arrayOfCreditorsAmount, String[] arrayOfCreditorsEndToEndId,String EboxNarrative,
                                             String ClearingAccountBranchId, String ClearingAccountNumber,String DebtorBranchID, String DebtorAccountNumber,
                                             String PurposeOfPaymentDesc,String PaymentInfoId,String TransactionDescription,String CreditorDestRef,String CreditorOrigRef)

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
            int b;

            String [] allStatements;
            ExtentTestManager.getTest().info(MarkupHelper.createLabel("Creditors statements", ExtentColor.PURPLE));

            for(int a=0;a<noOfCreditors;a++){
                if(arrayOfCreditorsBankCodes[a].equals(DebtorBankCode)){
                    URL = Settings.fetchStetementEndPoint+BankCountry+"/branches/"+arrayOfCreditorsBranchCodes[a]+"/accounts/"+arrayOfCreditorsAccountNumber[a]+"/statement?msgId=1&transactionId=1&sourceChannel=PPO";
                    HttpGet httpGet = new HttpGet(URL);
                    CloseableHttpResponse res = httpClient.execute(httpGet);
                    String json = EntityUtils.toString(res.getEntity());
                    String[] outputFromQuery = json.split("statementLineItems");
                    allStatements =outputFromQuery[1].split("}");
                    for(b=0;b<=allStatements.length-1;b++) {
                        if(allStatements[b].contains("\"amount\":"+arrayOfCreditorsAmount[a])
                                && allStatements[b].contains("\"narrative\":\""+CreditorDestRef)
                                && allStatements[b].contains("\"extendedNarrative\":\"" + DebtorPurposeOfPayment)
                                && allStatements[b].contains("\"counterPartyName\":\"\"")
                                && allStatements[b].contains("\"transactionDescription\":\"" + TransactionDescription)
                                && allStatements[b].contains("\"subTransactionDescription\":\"" + EboxNarrative + "\"")
                                && allStatements[b].contains("\"postingType\":\"CREDIT\"")) {
                            //ExtentTestManager.getTest().log(Status.INFO, allStatements[b]+" for creditor "+arrayOfCreditorsBankCodes[a]+"/"+arrayOfCreditorsBranchCodes[a]+"/"+arrayOfCreditorsAccountNumber[a]+", amount "+arrayOfCreditorsAmount[a]+" is credited");
                            ExtentTestManager.codeLogsXML(allStatements[b].replace(",","\n")+" for creditor "+arrayOfCreditorsBankCodes[a]+"/"+arrayOfCreditorsBranchCodes[a]+"/"+arrayOfCreditorsAccountNumber[a]+", amount "+arrayOfCreditorsAmount[a]+" is credited");
                            ExtentTestManager.getTest().log(Status.INFO, "-------------------------------------------------------------------------------------------------------------------------------------");
                        }
                    }
                }
                else{
                    ExtentTestManager.getTest().log(Status.INFO, arrayOfCreditorsBankCodes[a]+"/"+arrayOfCreditorsBranchCodes[a]+"/"+arrayOfCreditorsAccountNumber[a] +" Creditor is offus");
                    ExtentTestManager.getTest().log(Status.INFO, "-------------------------------------------------------------------------------------------------------------------------------------");

                    //if country is MUS check clearing account
                    if(BankCountry.contains("MUS")){
                        ExtentTestManager.getTest().info(MarkupHelper.createLabel("Clearing Suspense Account Applicable only to MUS", ExtentColor.PURPLE));
                        URL = Settings.fetchStetementEndPoint+BankCountry+"/branches/"+ClearingAccountBranchId+"/accounts/"+ClearingAccountNumber+"/statement?msgId=1&transactionId=1&sourceChannel=PPO";
                        HttpGet httpGet = new HttpGet(URL);
                        CloseableHttpResponse res = httpClient.execute(httpGet);
                        String json = EntityUtils.toString(res.getEntity());
                        String[] outputFromQuery = json.split("statementLineItems");
                        allStatements =outputFromQuery[1].split("}");

                        for(b=0;b<=allStatements.length-1;b++) {
                            if(allStatements[b].contains("\"amount\":"+arrayOfCreditorsAmount[a])
                                    && allStatements[b].contains("\"narrative\":\""+CreditorOrigRef)
                                    && allStatements[b].contains("\"extendedNarrative\":\""+ arrayOfCreditorsEndToEndId[a])
                                    && allStatements[b].contains("\"counterPartyName\":\""+DebtorBranchID+"/"+DebtorAccountNumber)
                                    && allStatements[b].contains("\"transactionDescription\":\"\"")
                                    && allStatements[b].contains("\"subTransactionDescription\":\"\"")
                                    && allStatements[b].contains("\"postingType\":\"CREDIT\"")) {
                                ExtentTestManager.getTest().info(MarkupHelper.createLabel("Clearing Suspense Account Statements", ExtentColor.TEAL));
                                //ExtentTestManager.getTest().log(Status.INFO, allStatements[b]+" for clearing suspense account  "+ DebtorBankCode +"/"+ClearingAccountBranchId+"/"+ ClearingAccountNumber +", amount "+arrayOfCreditorsAmount[a]+" is credited");
                                ExtentTestManager.codeLogsXML(allStatements[b].replace(",","\n")+" for clearing suspense account  "+ DebtorBankCode +"/"+ClearingAccountBranchId+"/"+ ClearingAccountNumber +", amount "+arrayOfCreditorsAmount[a]+" is credited");
                            }
                            else{
                                //System.out.println("clearing statement-"+allStatements[a]);
                            }
                        }
                    }
                }
            }
            if(!isTransactionDateCorrect) {
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
                        public boolean isTrusted(X509Certificate[] arg0, String arg1)
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


    /**Check debtor statements using API service */
    public void fetchItemDebtorPostingsEboxAPIForSFI(String BankCountry,String NumberOfTransactions, String DebtorBranchID,
                                               String DebtorAccountNumber,String[] arrayOfCreditorsAmount,String OriginatorTransactionCodeDesc,
                                               String CreditorOriginationRef,String CreditorDestinationRef)
    {
        try
        {
            fetchSSLCertificateDetails(BankCountry,DebtorBranchID,DebtorAccountNumber);

            int noOfCreditors = Integer.parseInt(NumberOfTransactions);
            int noOfTimesDebitRefIsPresent =0;
            int noOfTimesCreditRefIsPresent =0;
            int noOfTimesRefIsPresent =0;

            List<String> collectDataForNoOfTimesUniqueRefIsPresent = new ArrayList<>();
            List<String> collectDataForNoOfTimesRefIsPresent = new ArrayList<>();

            //below code is to store debit entries in array list
            ExtentTestManager.getTest().info(MarkupHelper.createLabel("Debtor(Itemised) Account Statements ", ExtentColor.PURPLE));
            for(int a=0;a<=allStatements.length-1;a++) {
                if(allStatements[a].contains("\"narrative\":\"" + CreditorOriginationRef.substring(0,6))) {
                    noOfTimesRefIsPresent++;
                    collectDataForNoOfTimesUniqueRefIsPresent.add(allStatements[a]);
                   // ExtentTestManager.getTest().log(Status.INFO,  allStatements[a]);
                    ExtentTestManager.codeLogsXML(allStatements[a].replace(",","\n"));
                    if (allStatements[a].contains("\"postingType\":\"DEBIT\"")){
                        if(allStatements[a].contains("\"transactionDescription\":\"" + OriginatorTransactionCodeDesc)){
                            if(allStatements[a].contains("\"subTransactionDescription\":\"" )){
                                if(allStatements[a].contains("\"extendedNarrative\":\"\"")){
                                    if(!BankCountry.contains("SYC")&& allStatements[a].contains("\"counterPartyName\":\"\"") ||
                                            (BankCountry.contains("SYC")&& allStatements[a].contains("\"counterPartyName\":\"" + CreditorOriginationRef ))){
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
                                    /* Counter party validation in progress*/
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

            if(!isTransactionDateCorrect) {
                ExtentTestManager.getTest().log(Status.WARNING, "Transaction date is not correct in Ebox. Issue to be raised with Ebox team." );
            }

            //convert array list into array- only debit entries
            String[] arrayOfDebitEntries = new String[collectDataForNoOfTimesRefIsPresent.size()];
            arrayOfDebitEntries = collectDataForNoOfTimesRefIsPresent.toArray(arrayOfDebitEntries);
            //display statements in report
            ExtentTestManager.getTest().info(MarkupHelper.createLabel("Debit Entries", ExtentColor.TEAL));
            for(int f=0; f<noOfTimesDebitRefIsPresent;f++) {
                // int g=f+1;
                //ExtentTestManager.getTest().log(Status.PASS, arrayOfDebitEntries[f]);
                ExtentTestManager.codeLogsXML(arrayOfDebitEntries[f].replace(",","\n"));
            }

            //below case is the scenario where all the transactions are passed and there is no correction
            if(noOfTimesDebitRefIsPresent==noOfCreditors&&noOfTimesDebitRefIsPresent<=noOfTimesRefIsPresent) {
                ExtentTestManager.getTest().log(Status.PASS, "Count of debit entries is correct "+noOfTimesDebitRefIsPresent);
            }
            else if(noOfTimesDebitRefIsPresent<=noOfCreditors && noOfTimesDebitRefIsPresent<noOfTimesRefIsPresent) {
                //below case is the scenario where all the transactions are NOT passed and there is correction
                ExtentTestManager.getTest().info(MarkupHelper.createLabel("Correction Statements", ExtentColor.TEAL));
                //code to check correction
                for (int e = 0; e <= allStatements.length - 1; e++) {
                    if (allStatements[e].contains("\"postingType\":\"CREDIT\"")
                            && allStatements[e].contains("\"narrative\":\"" + CreditorDestinationRef.substring(0,6))
                            && ((allStatements[e].contains("\"transactionDescription\":\"CORRECTION\"") && !BankCountry.contains("TZA")) ||
                            (allStatements[e].contains("\"transactionDescription\":\"REVERSAL\"") && BankCountry.contains("TZA")))){
                        noOfTimesCreditRefIsPresent++;
                       // ExtentTestManager.getTest().log(Status.INFO, "Statement " + allStatements[e]);
                        ExtentTestManager.codeLogsXML("Statement " + allStatements[e].replace(",","\n"));
                    }
                }
                if (noOfTimesCreditRefIsPresent != 0) {
                    ExtentTestManager.getTest().log(Status.INFO, "Correction happened for " + noOfTimesCreditRefIsPresent + " time/s");
                }
            }

            //below condition checks if debit happened but correction happened later
            else if(noOfTimesDebitRefIsPresent==noOfCreditors&&noOfTimesDebitRefIsPresent>noOfTimesCreditRefIsPresent){
                //  else if(noOfTimesDebitRefIsPresent==noOfCreditors&&noOfTimesDebitRefIsPresent>noOfTimesRefIsPresent){
                ExtentTestManager.getTest().log(Status.PASS, "Amount is debited from debtor and reversal happened.");
            }

            //--------------------------------validating if the amounts are correct----------------------------------
            ExtentTestManager.getTest().info(MarkupHelper.createLabel("Amounts", ExtentColor.TEAL));

            List<String> ListamountsInTestData = new ArrayList<>();
            for(int a=0; a<noOfCreditors;a++){
                ListamountsInTestData.add(arrayOfCreditorsAmount[a]);
            }
            //ListamountsInTestData this list has all the amounts in test data.
            String[] ArrayamountsInTestData = new String[noOfCreditors];
            ArrayamountsInTestData=ListamountsInTestData.toArray(ArrayamountsInTestData);

            //now create arraylist and array for amount not found
            List<String> amountNotPresent = new ArrayList<>();

            boolean isFound = false;
            int logsNotFound = 0;
            int a1, a2;

            for(a1=0;a1<ArrayamountsInTestData.length;a1++){
                for (a2=0;a2<arrayOfDebitEntries.length;a2++){
                    isFound=false;
                    if(arrayOfDebitEntries[a2].contains(ArrayamountsInTestData[a1])){
                        ExtentTestManager.getTest().log(Status.PASS, "Amount " + ArrayamountsInTestData[a1] + " is present in statement");
                        isFound=true;
                        break;
                    }
                }
                if(!isFound){
                    amountNotPresent.add(ArrayamountsInTestData[a1]);
                    logsNotFound= logsNotFound+1;
                }
            }
            //convert amount not found in array
            String[] amountNotPresentArray = new String[logsNotFound];
            amountNotPresentArray = amountNotPresent.toArray(amountNotPresentArray);
            for(int h=0; h<logsNotFound;h++){
                ExtentTestManager.getTest().log(Status.INFO, "Amount " + amountNotPresentArray[h] + " is not present in statement");
            }
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }

    /**Check credit account statements using API service */
    public void fetchCreditorPostingsEboxAPIForSFI(String BankCountry,String NumberOfTransactions,String DebtorBankCode,
                                                   String[] arrayOfCreditorsBankCodes,String[] arrayOfCreditorsBranchCodes,String[] arrayOfCreditorsAccountNumber,
                                                   String[] arrayOfCreditorsAmount, String[] arrayOfCreditorsEndToEndId,String ApplicationArea,
                                                   String ClearingAccountBranchId, String ClearingAccountNumber,String DebtorBranchID, String DebtorAccountNumber,
                                                   String[] DestinationTransactionCodeArr,String CreditorDestRef,String CreditorOrigRef,String[] DestinationSubTransactionCodeArr)

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
            int b;

            String [] allStatements;
            ExtentTestManager.getTest().info(MarkupHelper.createLabel("Creditors statements", ExtentColor.PURPLE));

            for(int a=0;a<noOfCreditors;a++){
                if(arrayOfCreditorsBankCodes[a].equals(DebtorBankCode)){
                    String DestTranCodeDesc = fetchPaymentDetailsFromDB.fetchPurposeOfPaymentDescriptionForSFI(ApplicationArea, BankCountry, DestinationTransactionCodeArr[a].substring(1,3),DestinationSubTransactionCodeArr[a]);
                    if(DestTranCodeDesc != null) {
                        System.out.println("DestTranCodeDesc :: " + DestTranCodeDesc);
                        String[] DestTranCodeDescArr = DestTranCodeDesc.split(",");

                        URL = Settings.fetchStetementEndPoint + BankCountry + "/branches/" + arrayOfCreditorsBranchCodes[a] + "/accounts/" + arrayOfCreditorsAccountNumber[a].replaceAll("^0+(?!$)", "") + "/statement?msgId=1&transactionId=1&sourceChannel=PPO";
                        HttpGet httpGet = new HttpGet(URL);
                        CloseableHttpResponse res = httpClient.execute(httpGet);
                        String json = EntityUtils.toString(res.getEntity());
                        String[] outputFromQuery = json.split("statementLineItems");
                        allStatements = outputFromQuery[1].split("}");
                        for (b = 0; b <= allStatements.length - 1; b++) {
                            if (allStatements[b].contains("\"amount\":" + arrayOfCreditorsAmount[a])
                                    && allStatements[b].contains("\"narrative\":\"" + CreditorDestRef)
                                    && allStatements[b].contains("\"extendedNarrative\":\"")
                                    && allStatements[b].contains("\"counterPartyName\":\"\"")
                                    && allStatements[b].contains("\"transactionDescription\":\"" + DestTranCodeDescArr[0])
                                    && allStatements[b].contains("\"subTransactionDescription\":\"")
                                    && allStatements[b].contains("\"postingType\":\"CREDIT\"")) {
//                            ExtentTestManager.getTest().log(Status.INFO, allStatements[b]+" for creditor "+arrayOfCreditorsBankCodes[a]+"/"+arrayOfCreditorsBranchCodes[a]+"/"+arrayOfCreditorsAccountNumber[a]+", amount "+arrayOfCreditorsAmount[a]+" is credited");
                                ExtentTestManager.codeLogsXML(allStatements[b].replace(",", "\n") + " for creditor " + arrayOfCreditorsBankCodes[a] + "/" + arrayOfCreditorsBranchCodes[a] + "/" + arrayOfCreditorsAccountNumber[a] + ", amount " + arrayOfCreditorsAmount[a] + " is credited");
                                ExtentTestManager.getTest().log(Status.INFO, "-------------------------------------------------------------------------------------------------------------------------------------");
                            }
                        }
                    }
                }
                else{
                    ExtentTestManager.getTest().log(Status.INFO, arrayOfCreditorsBankCodes[a]+"/"+arrayOfCreditorsBranchCodes[a]+"/"+arrayOfCreditorsAccountNumber[a] +" Creditor is offus");
                    ExtentTestManager.getTest().log(Status.INFO, "-------------------------------------------------------------------------------------------------------------------------------------");

                    //if country is MUS check clearing account
                    if(BankCountry.contains("MUS")){
                        ExtentTestManager.getTest().info(MarkupHelper.createLabel("Clearing Suspense Account Applicable only to MUS", ExtentColor.PURPLE));
                        URL = Settings.fetchStetementEndPoint+BankCountry+"/branches/"+ClearingAccountBranchId+"/accounts/"+ClearingAccountNumber+"/statement?msgId=1&transactionId=1&sourceChannel=PPO";
                        HttpGet httpGet = new HttpGet(URL);
                        CloseableHttpResponse res = httpClient.execute(httpGet);
                        String json = EntityUtils.toString(res.getEntity());
                        String[] outputFromQuery = json.split("statementLineItems");
                        allStatements =outputFromQuery[1].split("}");

                        for(b=0;b<=allStatements.length-1;b++) {
                            if(allStatements[b].contains("\"amount\":"+arrayOfCreditorsAmount[a])
                                    && allStatements[b].contains("\"narrative\":\""+CreditorOrigRef)
                                    && allStatements[b].contains("\"extendedNarrative\":\""+ arrayOfCreditorsEndToEndId[a])
                                    && allStatements[b].contains("\"counterPartyName\":\""+DebtorBranchID+"/"+DebtorAccountNumber)
                                    && allStatements[b].contains("\"transactionDescription\":\"\"")
                                    && allStatements[b].contains("\"subTransactionDescription\":\"\"")
                                    && allStatements[b].contains("\"postingType\":\"CREDIT\"")) {
                                ExtentTestManager.getTest().info(MarkupHelper.createLabel("Clearing Suspense Account Statements", ExtentColor.TEAL));
                                //ExtentTestManager.getTest().log(Status.INFO, allStatements[b]+" for clearing suspense account  "+ DebtorBankCode +"/"+ClearingAccountBranchId+"/"+ ClearingAccountNumber +", amount "+arrayOfCreditorsAmount[a]+" is credited");
                                ExtentTestManager.codeLogsXML(allStatements[b].replace(",","\n") +" for clearing suspense account  "+ DebtorBankCode +"/"+ClearingAccountBranchId+"/"+ ClearingAccountNumber +", amount "+arrayOfCreditorsAmount[a]+" is credited");
                            }
                            else{
                                //System.out.println("clearing statement-"+allStatements[a]);
                            }
                        }
                    }
                }
            }
            if(!isTransactionDateCorrect) {
                ExtentTestManager.getTest().log(Status.WARNING, "Transaction date is not correct in Ebox. Issue to be raised with Ebox team." );
            }
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }
    /**Check debtor statements using API service */
    public void fetchDebtorConsoPostingsEboxAPIForSFI(String BankCountry,String DebtorBranchID,String DebtorAccountNumber,
                                                String DebtorControlSum,String OrigTransactionCodeDesc,
                                                String CreditorOrigRef,String CreditorDestRef)
    {
        try
        {
            fetchSSLCertificateDetails(BankCountry,DebtorBranchID,DebtorAccountNumber);

            int noOfTimesDebitRefIsPresent =0;
            int noOfTimesCreditRefIsPresent =0;
            int noOfTimesRefIsPresent =0;

            List<String> collectDataForNoOfTimesRefIsPresent = new ArrayList<String>();

            //below code is to store debit entries in array list
            ExtentTestManager.getTest().info(MarkupHelper.createLabel("Debtor(Consolidated) Account Statements ", ExtentColor.PURPLE));
            for(int a=0;a<=allStatements.length-1;a++) {
                if((allStatements[a].contains("\"narrative\":\"" + CreditorOrigRef.substring(0,6)) && !BankCountry.contains("TZA")) ||
                            (allStatements[a].contains("\"narrative\":\"" + CreditorOrigRef.substring(0,6)) && BankCountry.contains("TZA"))){
                    noOfTimesRefIsPresent++;
                    //ExtentTestManager.getTest().log(Status.INFO,  allStatements[a]);
                    ExtentTestManager.codeLogsXML(allStatements[a].replace(",","\n"));
                    if (allStatements[a].contains("\"postingType\":\"DEBIT\"")){
                        if(allStatements[a].contains("\"transactionDescription\":\"" + OrigTransactionCodeDesc)){
                            if(allStatements[a].contains("\"subTransactionDescription\":\"")){
                                if(allStatements[a].contains("\"amount\":"+DebtorControlSum)){
                                    if(!BankCountry.contains("SYC") && (allStatements[a].contains("\"counterPartyName\":\"\""))) {
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
                                    else if //(BankCountry.contains("SYC")){
                                    ((BankCountry.contains("SYC")) && (allStatements[a].contains("\"counterPartyName\":\"" + "Multiple Beneficiaries" + "\""))){
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

            if(!isTransactionDateCorrect) {
                ExtentTestManager.getTest().log(Status.WARNING, "Transaction date is not correct in Ebox. Issue to be raised with Ebox team." );
            }
            //convert array list into array- only debit entries
            String[] arrayOfDebitEntries = new String[collectDataForNoOfTimesRefIsPresent.size()];
            arrayOfDebitEntries = collectDataForNoOfTimesRefIsPresent.toArray(arrayOfDebitEntries);
            //display statements in report
            ExtentTestManager.getTest().info(MarkupHelper.createLabel("Debit Entries ", ExtentColor.TEAL));
            for(int f=0; f<noOfTimesDebitRefIsPresent;f++) {
                // int g=f+1;
                //ExtentTestManager.getTest().log(Status.PASS, "Debit Statement "+  arrayOfDebitEntries[f]);
                ExtentTestManager.codeLogsXML("Debit Statement "+ arrayOfDebitEntries[f].replace(",","\n"));
            }

            System.out.println("noOfTimesDebitRefIsPresent : : " + noOfTimesDebitRefIsPresent);
            System.out.println("noOfTimesRefIsPresent : : " + noOfTimesRefIsPresent);
            //------------check if the no. of debit entries are correct---------
            if(noOfTimesDebitRefIsPresent==1){
                ExtentTestManager.getTest().log(Status.PASS, "Count of debit entries is correct "+noOfTimesDebitRefIsPresent);
            }
            else if(noOfTimesDebitRefIsPresent< noOfTimesRefIsPresent) {
                //check if the workflow ref is present more than no of creditors
                ExtentTestManager.getTest().info(MarkupHelper.createLabel("Correction Statements", ExtentColor.TEAL));
                System.out.println("CreditorDestRef : " + CreditorDestRef.substring(0,6));
                for (int e = 0; e <= allStatements.length - 1; e++) {
                    if (allStatements[e].contains("\"postingType\":\"CREDIT\"")
                            && allStatements[e].contains("\"narrative\":\"" + CreditorDestRef.substring(0,6))
                            && ((allStatements[e].contains("\"transactionDescription\":\"CORRECTION\"") && !BankCountry.contains("TZA")) ||
                            (allStatements[e].contains("\"transactionDescription\":\"REVERSAL\"") && BankCountry.contains("TZA")))
                            // && allStatements[e].contains("\"transactionDate\":\"" + date + "\"")
                            && allStatements[e].contains("\"extendedNarrative\":\"\"")
                            && allStatements[e].contains("\"counterPartyName\":\"\"")) {
                        noOfTimesCreditRefIsPresent++;
                        //ExtentTestManager.getTest().log(Status.INFO, "Correction Statement " + allStatements[e]);
                        ExtentTestManager.codeLogsXML("Correction Statement " + allStatements[e].replace(",","\n"));
                    }
                }
                if (noOfTimesCreditRefIsPresent != 0) {
                    ExtentTestManager.getTest().log(Status.WARNING, "Correction happened for " + noOfTimesCreditRefIsPresent + " time/s");
                }
                else{
                    //Check if the no of debits are more without correction
                    ExtentTestManager.getTest().log(Status.FAIL, "No. of debits are more than expected without correction.");
                }
            }
            else if(noOfTimesDebitRefIsPresent<1){
                //No. of debits are less, check if any transaction is failed.
                ExtentTestManager.getTest().log(Status.FAIL, "Debit statements are less than expected. Please check if any transaction is failed for consolidated. Or the statements are not correct.");
            }
            else{
                ExtentTestManager.getTest().log(Status.FAIL, "This condition is unknown and not handled.");
            }
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }

    /**Check processing suspense account statements using API service */
    public void fetchItemSuspenseAcctPostingsEboxAPIForSFI(String BankCountry,String DebtorBranchID,
                                                     String DebtorAccountNumber,String strPurposeOfPaymentDesc,
                                                     String ProcessingSuspenseAccountBranchID,String ProcessingSuspenseAccount,String PaymentInfoId)
    {
        try
        {
            fetchSSLCertificateDetails(BankCountry,ProcessingSuspenseAccountBranchID,ProcessingSuspenseAccount);

            int noOfTimesDebitRefIsPresent =0;

            //below code is to store debit entries in array list
            ExtentTestManager.getTest().info(MarkupHelper.createLabel("Processing Suspense (Itemised) Account statements", ExtentColor.PURPLE));
            for(int a=0;a<=allStatements.length-1;a++) {
                if(allStatements[a].contains("\"narrative\":\"" + strPurposeOfPaymentDesc)) {
                    if(allStatements[a].contains("\"transactionDescription\":\"\"")){
                        if(allStatements[a].contains("\"subTransactionDescription\":\"\"")){
                            if(allStatements[a].contains("\"extendedNarrative\":\"" + PaymentInfoId)){
                                if(allStatements[a].contains("\"counterPartyName\":\""+DebtorBranchID+"/"+DebtorAccountNumber)){
                                    if(allStatements[a].contains("\"transactionDate\":\"" + date + "\"")){
                                        noOfTimesDebitRefIsPresent++;
                                        //ExtentTestManager.getTest().log(Status.INFO, allStatements[a]);
                                        ExtentTestManager.codeLogsXML(allStatements[a].replace(",","\n"));
                                    }
                                    else{
                                        noOfTimesDebitRefIsPresent++;
                                        //ExtentTestManager.getTest().log(Status.INFO, allStatements[a]);
                                        ExtentTestManager.codeLogsXML(allStatements[a].replace(",","\n"));
                                        isTransactionDateCorrect=false;
                                    }
                                }
                            }
                        }
                    }
                }
            }
            if(!isTransactionDateCorrect) {
                ExtentTestManager.getTest().log(Status.WARNING, "Transaction date is not correct in Ebox. Issue to be raised with Ebox team." );
            }
//            if(noOfTimesDebitRefIsPresent<=0){
//                ExtentTestManager.getTest().log(Status.FAIL, "Statements are not as expected");
//            }
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }

    /**Check processing suspense account statements using API service for consolidated*/
    public void fetchConsoSuspenseAcctPostingsEboxAPIForSFI(String BankCountry,String DebtorBranchID,String DebtorAccountNumber,
                                                      String CreditorOrigRef,String ProcessingSuspenseAccountBranchID,
                                                      String ProcessingSuspenseAccount,String PaymentInfoId) throws Exception
    {
        try
        {
            fetchSSLCertificateDetails(BankCountry,ProcessingSuspenseAccountBranchID,ProcessingSuspenseAccount);

            int noOfTimesDebitRefIsPresent =0;

            //below code is to store debit entries in array list
            ExtentTestManager.getTest().info(MarkupHelper.createLabel("Processing Suspense (Consolidated) Account statements", ExtentColor.PURPLE));
            for(int a=0;a<=allStatements.length-1;a++) {
                if(allStatements[a].contains("\"narrative\":\"" + CreditorOrigRef )) {
                    if(allStatements[a].contains("\"transactionDescription\":\"\"")){
                        if(allStatements[a].contains("\"subTransactionDescription\":\"\"")){
                            if(allStatements[a].contains("\"extendedNarrative\":\"" + PaymentInfoId)){
                                if(allStatements[a].contains("\"counterPartyName\":\""+DebtorBranchID+"/"+DebtorAccountNumber)){
                                    if(allStatements[a].contains("\"transactionDate\":\"" + date + "\"")){
                                        noOfTimesDebitRefIsPresent++;
                                      //  ExtentTestManager.getTest().log(Status.INFO, allStatements[a]);
                                        ExtentTestManager.codeLogsXML(allStatements[a].replace(",","\n"));
                                    }
                                    else{
                                        noOfTimesDebitRefIsPresent++;
                                        //ExtentTestManager.getTest().log(Status.INFO, allStatements[a]);
                                        ExtentTestManager.codeLogsXML(allStatements[a].replace(",","\n"));
                                        isTransactionDateCorrect=false;
                                    }
                                }
                            }
                        }
                    }
                }
            }
            if(!isTransactionDateCorrect) {
                ExtentTestManager.getTest().log(Status.WARNING, "Transaction date is not correct in Ebox. Issue to be raised with Ebox team." );
            }

            if(noOfTimesDebitRefIsPresent<=0) {
                ExtentTestManager.getTest().log(Status.FAIL, "Statements are not as expected");
            }
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }

    /**Check exception suspense account statements using API service */
    public void fetchExceptionAcctPostingsEboxAPIForSFI(String BankCountry,String DebtorBranchID,String DebtorAccountNumber,
                                                  String CreditorOrigRef,String ExceptionSuspenseAccountBranchId,
                                                  String ExceptionSuspenseAccountNumber,String PaymentInfoId)
    {
        try
        {
            fetchSSLCertificateDetails(BankCountry,ExceptionSuspenseAccountBranchId,ExceptionSuspenseAccountNumber);

            ExtentTestManager.getTest().info(MarkupHelper.createLabel("Exception Suspense Account statements", ExtentColor.PURPLE));
            for(int a=0;a<=allStatements.length-1;a++) {
                if(allStatements[a].contains("\"narrative\":\"" + CreditorOrigRef)) {
                    if(allStatements[a].contains("\"counterPartyName\":\""+DebtorBranchID+"/"+DebtorAccountNumber)) {
                        if((allStatements[a].contains("\"transactionDescription\":\"CORRECTION\"") && !BankCountry.contains("TZA") ||
                                (allStatements[a].contains("\"transactionDescription\":\"REVERSAL\"") && BankCountry.contains("TZA")))){
                            if (allStatements[a].contains("\"extendedNarrative\":\"" + PaymentInfoId)) {
                                if (allStatements[a].contains("\"subTransactionDescription\":\"\"")) {
                                    if(allStatements[a].contains("\"transactionDate\":\"" + date + "\"")){
                                       // ExtentTestManager.getTest().log(Status.INFO, allStatements[a]);
                                        ExtentTestManager.codeLogsXML(allStatements[a].replace(",","\n"));
                                    }
                                    else{
                                      //  ExtentTestManager.getTest().log(Status.INFO, allStatements[a]);
                                        ExtentTestManager.codeLogsXML(allStatements[a].replace(",","\n"));
                                        isTransactionDateCorrect=false;
                                    }
                                }
                            }
                        }
                    }
                }
            }
            if(!isTransactionDateCorrect) {
                ExtentTestManager.getTest().log(Status.WARNING, "Transaction date is not correct in Ebox. Issue to be raised with Ebox team." );
            }
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }
}