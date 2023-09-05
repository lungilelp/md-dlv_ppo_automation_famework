//package pages;
//
///**
// * @author Awanti Birgade [ ABABA8T ]
// * @date created 10/30/2019
// * @package pages
// */
//import Utilities.ExcelUtil;
//import com.aventstack.extentreports.Status;
//import com.aventstack.extentreports.markuputils.ExtentColor;
//import com.aventstack.extentreports.markuputils.MarkupHelper;
//import config.Settings;
//import base.BasePage;
//import org.apache.commons.io.FileUtils;
//import org.apache.http.client.methods.CloseableHttpResponse;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.impl.client.CloseableHttpClient;
//import org.apache.http.util.EntityUtils;
//import org.openqa.selenium.WebDriver;
//import utils.extentreports.ExtentTestManager;
//import utils.logs.Log;
//
//import java.io.*;
//
//
//public class SFIPage extends BasePage {
//    public SFIPage(WebDriver driver) {
//
//        super(driver);
//        Log.info("The current working class: "+this.getClass().getName());
//    }
//    //***********************************WEB ELEMENTS*******************************************************************
//    //******************************************************************************************************************
//
//    //public ITestResult result;
//    public String sfifilename = null;
//    public String filepath = null;
//
//    //******************************************************************************************************************
//
//    //***********************************STEPS**************************************************************************
//    //******************************************************************************************************************
//    // with new bulk generator
//    public void createSFIfileMix(String TestCaseID, String TestName, String BankCountry,String ShortDate,String ValueDate,String ProcessingOption,
//                                 String ForcePost,String PaymentPurpose,
//                                 String OriginatingSortCode,String OriginatingAccountNumber, String OriginatorAccountCurrency,
//                                 String OriginatorReference, String OriginatorTransactionCode,
//                                 String DestinationSortCode_1, String DestinationAccountNumber_1,String TransferCurrency_1,String TransferAmount_1,
//                                 String DestinationReference_1,String DestinationTransactionCode_1,
//                                 String DestinationSortCode_2, String DestinationAccountNumber_2,String TransferCurrency_2,String TransferAmount_2,
//                                 String DestinationReference_2, String DestinationTransactionCode_2,
//                                 String DestinationSortCode_3, String DestinationAccountNumber_3,String TransferCurrency_3,String TransferAmount_3,
//                                 String DestinationReference_3, String DestinationTransactionCode_3,
//                                 String DestinationSortCode_4, String DestinationAccountNumber_4,String TransferCurrency_4,String TransferAmount_4,
//                                 String DestinationReference_4, String DestinationTransactionCode_4,
//                                 String DestinationSortCode_5, String DestinationAccountNumber_5,String TransferCurrency_5,String TransferAmount_5,
//                                 String DestinationReference_5, String DestinationTransactionCode_5,
//                                 String DestinationSortCode_6, String DestinationAccountNumber_6,String TransferCurrency_6,String TransferAmount_6,
//                                 String DestinationReference_6, String DestinationTransactionCode_6,
//                                 String PaymentMethod,String NoOfTransactions,String FileID) throws InterruptedException {
//
//        //Create Random String for each iteration
//        String strWorkflowReferenceUniqueTxt = randomAlphaNumericV2();
//
//        String fetchfileurl = null;
//        String fetchfileurlPart2= null;
//
//        String[] destsortcodearray = {DestinationSortCode_1, DestinationSortCode_2, DestinationSortCode_3, DestinationSortCode_4, DestinationSortCode_5, DestinationSortCode_6};
//        String[] destrefarray = {DestinationReference_1, DestinationReference_2, DestinationReference_3, DestinationReference_4, DestinationReference_5, DestinationReference_6};
//        String[] destrefacctnumber = {DestinationAccountNumber_1, DestinationAccountNumber_2, DestinationAccountNumber_3, DestinationAccountNumber_4, DestinationAccountNumber_5, DestinationAccountNumber_6};
//        String[] destrefccy = {TransferCurrency_1, TransferCurrency_2, TransferCurrency_3, TransferCurrency_4, TransferCurrency_5, TransferCurrency_6};
//        String[] destreftrancode = {DestinationTransactionCode_1, DestinationTransactionCode_2, DestinationTransactionCode_3, DestinationTransactionCode_4, DestinationTransactionCode_5, DestinationTransactionCode_6};
//        String[] destrefamount = {TransferAmount_1, TransferAmount_2, TransferAmount_3, TransferAmount_4, TransferAmount_5, TransferAmount_6};
//
//        int i;
//        String suffixURL = "destinationNarrative=Destination_Narrative&usingOnOffCombinations=No&paymentMethod=TRF&transactionCount="+NoOfTransactions+"&fileID="+FileID+"&includeTransactionFile=No" +
//                "&generateBatchLayout=No&_generateBatchSafely=on&action=Generate&file=";
//
//        int TransactionCount = Integer.parseInt(NoOfTransactions);
//
//        String fetchfileurlSingle = Settings.bulkGenerator + "midCountry=" + BankCountry + "&shortDate=" + ShortDate + "&creationDateTime=2021-01-04T15%3A41%3A50.410%2B02%3A00&valueDate=" + ValueDate +
//                "&processingOption=" + ProcessingOption + "&forcePost=" + ForcePost + "&paymentPurpose=" + PaymentPurpose + "&country=" + BankCountry +
//                "&debtorSortCode=" + OriginatingSortCode + "&debtorAccountNumber=" + OriginatingAccountNumber + "&debtorAccountCurrency=" + OriginatorAccountCurrency +
//                "&originatorTransactionCode=" + OriginatorTransactionCode + "&originatorReference=" + strWorkflowReferenceUniqueTxt+OriginatorReference + "&creditorTestObjects%5B0%5D.rowNumber=1" +
//                "&creditorTestObjects%5B0%5D.accountNumber=" + DestinationAccountNumber_1 + "&creditorTestObjects%5B0%5D.sortCode=" + DestinationSortCode_1 +
//                "&creditorTestObjects%5B0%5D.currency=" + TransferCurrency_1 + "&creditorTestObjects%5B0%5D.transferAmount=" + TransferAmount_1 +
//                "&creditorTestObjects%5B0%5D.transactionCode=" + DestinationTransactionCode_1 + "&creditorTestObjects%5B0%5D.transactionReference=" +
//                strWorkflowReferenceUniqueTxt+DestinationReference_1 + "&deleteRowID=&";
//
//        if(TransactionCount==1){
//            ExtentTestManager.getTest().info(MarkupHelper.createLabel("SFI File with single creditor", ExtentColor.CYAN));
//            fetchfileurl = fetchfileurlSingle + suffixURL;
//                    System.out.println(fetchfileurlSingle);
//        }
//        else if(TransactionCount>1 && TransactionCount<=6){
//            ExtentTestManager.getTest().info(MarkupHelper.createLabel("SFI File with multiple creditors", ExtentColor.CYAN));
//
//            String URLtoAppend;
//            for(i=1; i<=TransactionCount; i++){
//                int j=i+1;
//
//                URLtoAppend = "creditorTestObjects%5B"+i+"%5D.rowNumber="+ j +"&creditorTestObjects%5B"+i+"%5D.accountNumber="+destrefacctnumber[i]+
//                    "&creditorTestObjects%5B"+i+"%5D.sortCode="+destsortcodearray[i]+"&creditorTestObjects%5B"+i+"%5D.currency="+destrefccy[i]+
//                    "&creditorTestObjects%5B"+i+"%5D.transferAmount="+destrefamount[i]+"&creditorTestObjects%5B"+i+"%5D.transactionCode="+destreftrancode[i]+
//                    "&creditorTestObjects%5B"+i+"%5D.transactionReference="+strWorkflowReferenceUniqueTxt+destrefarray[i]+"&deleteRowID=&";
//
//                //assert false;
//                fetchfileurlPart2 = fetchfileurlPart2 + URLtoAppend;
//            }
//            fetchfileurl =  fetchfileurlSingle + fetchfileurlPart2 +suffixURL;
//            System.out.println(fetchfileurl);
//        }
//        else {
//            ExtentTestManager.getTest().fail(MarkupHelper.createLabel("Transaction Count is not correct", ExtentColor.RED));
//        }
//
//        try{
//            //fetch file
//            HttpPost httpPost = new HttpPost(fetchfileurl);
//            CloseableHttpClient httpClient = getCloseableHttpClient();
//            CloseableHttpResponse res = httpClient.execute(httpPost);
//            String sfifile = EntityUtils.toString(res.getEntity());
//            if(sfifile!=null&&res.getStatusLine().getStatusCode()==200) {
//                ExtentTestManager.getTest().pass(MarkupHelper.createLabel("SFI File is generated", ExtentColor.GREEN));
//            }
//            else{
//                ExtentTestManager.getTest().fail(MarkupHelper.createLabel("SFI File is not generated", ExtentColor.RED));
//            }
//            ExtentTestManager.codeLogsXML(sfifile);
//            // call method to create file
//            savefileinfolder(TestCaseID,TestName,ProcessingOption,sfifile,strWorkflowReferenceUniqueTxt);
//
//        }catch (Exception e){
//            ExtentTestManager.getTest().log(Status.FAIL, "test fail "+e);
//        }
//    }
//
//
//    public void savefileinfolder(String TestCaseID,String TestName,String ProcessingOption,String sfifile,String strWorkflowReferenceUniqueTxt){
//        try{
//            //create file name
//            sfifilename = TestCaseID+"_"+TestName+"_"+ProcessingOption+"_"+strWorkflowReferenceUniqueTxt +"_"+ getCurrentFormattedDate();
//            filepath = ExcelUtil.currentDir+"\\testOutputs\\sfifile\\CalcHashTotal\\source\\";
//
//            //create file in location
//            File myObj = new File(filepath+sfifilename+".xml");
//            if (myObj.createNewFile()) {
//                System.out.println("File created: " + myObj.getName());
//                FileWriter myWriter = new FileWriter(filepath+sfifilename+".xml");
//                myWriter.write(sfifile);
//                myWriter.close();
//                ExtentTestManager.getTest().info(MarkupHelper.createLabel("SFI File "+sfifilename+" is placed in folder testOutputs->sfifile", ExtentColor.TRANSPARENT));
//                System.out.println("sfifilename :: " + sfifilename);
//            } else {
//                System.out.println("File already exists.");
//            }
//        }
//        catch(Exception e){
//            ExtentTestManager.getTest().log(Status.FAIL, "test fail "+e);
//        }
//    }
//
//    public void runtimeCMDforAPI(String country) throws IOException
//    {
//        Runtime rt = Runtime.getRuntime();
//        Process p=null;
//        try{
//            rt = Runtime.getRuntime();
//            String command = "java -jar sfi-mq-utility-0.0.1.jar  MAK."+ country + ".SFI.SIT.IN "+sfifilename+".xml";
//            rt.exec("java -jar sfi-mq-utility-0.0.1-SNAPSHOT.jar  MAK."+ country + ".SFI.SIT.IN "+sfifilename+".xml");
//            p =rt.exec("cmd /c cd "+ filepath + " && "+ command);
//            //p.exitValue();
//        }
//        catch(Exception e){
//            System.out.println(e);
//        }
//        finally{
//            //System.exit(1);
//            //rt.exec("taskkill /f /im cmd.exe");
//            //p.destroy();
//        }
//    }
//
//    public void runtimeCMD(String country,String filepath,String filename) throws IOException
//    {
//        try{
//            Runtime rt = Runtime.getRuntime();
//            String command = "java -jar sfi-mq-utility-0.0.1.jar  MAK."+ country + ".SFI.SIT.IN "+filename+"";
//            rt.exec("java -jar sfi-mq-utility-0.0.1-SNAPSHOT.jar  MAK."+ country + ".SFI.SIT.IN "+filename+"");
//            Process p =rt.exec("cmd /c cd "+ filepath + " && "+ command);
//           /* Thread.sleep(4000);
//            p.destroy();
//            p.waitFor();
//            System.out.println(p.getErrorStream());
//            System.out.println(p.getInputStream());
//            System.out.println(p.getOutputStream());
//
//
//
//            if(p.isAlive() == false) {
//                ExtentTestManager.getTest().pass(MarkupHelper.createLabel("SFI File is dropped", ExtentColor.GREEN));
//            }
//
//            */
//
//
//        }
//        catch(Exception e){
//            System.out.println(e);
//        }
//    }
//
//    public void CalculateHashTotalCMD(String filepath) throws IOException
//    {
//        try{
//            Runtime rt = Runtime.getRuntime();
//            String command = "java CalcHashTotal source";
//            rt.exec(command);
//            Process p =rt.exec("cmd /c cd "+ filepath + " && "+ command);
//        }
//        catch(Exception e){
//            System.out.println(e);
//        }
//    }
//
//    public void calcHashTotal() throws IOException {
//
//       ProcessBuilder builder = new ProcessBuilder(
//                "cmd.exe", "/c", "cd " + System.getProperty("user.dir") + "./testOutputs/sfifile/CalcHashTotal/" + "&&" +
//                "java CalcHashTotal source");
//        builder.redirectErrorStream(true);
//        Process p = builder.start();
//        BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
//        String line;
//        while (true) {
//            line = r.readLine();
//            if (line == null) {
//                break;
//            }
//            System.out.println(line);
//        }
//    }
//    public void dropSFIFilesOverMQ(String country, String fileName,String SFIXMLFileDropFileUtilityPath) throws IOException {
//
//        ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/c", "cd " + System.getProperty("user.dir") +
//                SFIXMLFileDropFileUtilityPath + "&&" + "java -jar sfi-mq-utility-0.0.1-SNAPSHOT.jar MAK." + country + ".SFI.SIT.IN " + fileName);
//        builder.redirectErrorStream(true);
//        Process p = builder.start();
//        BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
//        String line;
//        while (true) {
//            line = r.readLine();
//            if (line == null) {
//                break;
//            }
//            System.out.println(line);
//        }
//    }
//    public void copyFromSourceToDestination(String fileName,String srcFilePath,String destFilePath) throws IOException {
//        File sourcePath = new File(srcFilePath + fileName);
//        File destPath = new File(destFilePath + fileName);
//
//        try {
//            FileUtils.copyFile(sourcePath, destPath);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//}