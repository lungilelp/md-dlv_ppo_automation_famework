package pages;

/**
 * @author Awanti Birgade [ ABABA8T ]
 * @date created 10/30/2019
 * @package pages
 */
import com.aventstack.extentreports.Status;
import base.BasePage;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.lang3.ArrayUtils;
import org.openqa.selenium.WebDriver;
import utils.extentreports.ExtentTestManager;
import utils.logs.Log;

import java.util.ArrayList;
import java.util.List;


public class ReferenceDataPage extends BasePage {
    public ReferenceDataPage(WebDriver driver) {

        super(driver);
        Log.info("The current working class: "+this.getClass().getName());
    }
    //***********************************WEB ELEMENTS*******************************************************************
    //******************************************************************************************************************


    //public ITestResult result;

    //******************************************************************************************************************
    /***4 Lines***/

    //***********************************CREATE A CONSTRUCTOR TO INITIALIZE OBJECTS*************************************
    //******************************************************************************************************************
    //public ReferenceDataPage(){
    //this.driver = localDriver;

    //This initElements method will create all WebElements
    //PageFactory.initElements(driver, this);
    // }
    //******************************************************************************************************************


    //***********************************STEPS**************************************************************************
    //******************************************************************************************************************

    /**To verify if the ref data is set up correctly**/
    public ReferenceDataPage verifyRefData(String TotalNoOfPOPs, String CountryCode,
                              String PurposeOfPayment_1, String CdOrPrprtry_1, String CtgryPrpsTag_1,
                              String PurposeOfPayment_2, String CdOrPrprtry_2, String CtgryPrpsTag_2,
                              String PurposeOfPayment_3, String CdOrPrprtry_3, String CtgryPrpsTag_3,
                              String PurposeOfPayment_4, String CdOrPrprtry_4, String CtgryPrpsTag_4,
                              String PurposeOfPayment_5, String CdOrPrprtry_5, String CtgryPrpsTag_5,
                              String PurposeOfPayment_6, String CdOrPrprtry_6, String CtgryPrpsTag_6,
                              String PurposeOfPayment_7, String CdOrPrprtry_7, String CtgryPrpsTag_7,
                              String PurposeOfPayment_8, String CdOrPrprtry_8, String CtgryPrpsTag_8,
                              String PurposeOfPayment_9, String CdOrPrprtry_9, String CtgryPrpsTag_9,
                              String PurposeOfPayment_10, String CdOrPrprtry_10, String CtgryPrpsTag_10,
                              String PurposeOfPayment_11, String CdOrPrprtry_11, String CtgryPrpsTag_11,
                              String PurposeOfPayment_12, String CdOrPrprtry_12, String CtgryPrpsTag_12,
                              String PurposeOfPayment_13, String CdOrPrprtry_13, String CtgryPrpsTag_13,
                              String PurposeOfPayment_14, String CdOrPrprtry_14, String CtgryPrpsTag_14,
                              String PurposeOfPayment_15, String CdOrPrprtry_15, String CtgryPrpsTag_15,
                              String PurposeOfPayment_16, String CdOrPrprtry_16, String CtgryPrpsTag_16,
                              String PurposeOfPayment_17, String CdOrPrprtry_17, String CtgryPrpsTag_17,
                              String PurposeOfPayment_18, String CdOrPrprtry_18, String CtgryPrpsTag_18,
                              String PurposeOfPayment_19, String CdOrPrprtry_19, String CtgryPrpsTag_19,
                              String PurposeOfPayment_20, String CdOrPrprtry_20, String CtgryPrpsTag_20,
                              String PurposeOfPayment_21, String CdOrPrprtry_21, String CtgryPrpsTag_21,
                              String PurposeOfPayment_22, String CdOrPrprtry_22, String CtgryPrpsTag_22,
                              String PurposeOfPayment_23, String CdOrPrprtry_23, String CtgryPrpsTag_23,
                              String PurposeOfPayment_24, String CdOrPrprtry_24, String CtgryPrpsTag_24,
                              String PurposeOfPayment_25, String CdOrPrprtry_25, String CtgryPrpsTag_25)
    {
        try {
            String[] arrayPOPs = {PurposeOfPayment_1, PurposeOfPayment_2, PurposeOfPayment_3, PurposeOfPayment_4, PurposeOfPayment_5,
                    PurposeOfPayment_6, PurposeOfPayment_7, PurposeOfPayment_8, PurposeOfPayment_9, PurposeOfPayment_10,
                    PurposeOfPayment_11, PurposeOfPayment_12, PurposeOfPayment_13, PurposeOfPayment_14, PurposeOfPayment_15,
                    PurposeOfPayment_16, PurposeOfPayment_17, PurposeOfPayment_18, PurposeOfPayment_19, PurposeOfPayment_20,
                    PurposeOfPayment_21, PurposeOfPayment_22, PurposeOfPayment_23, PurposeOfPayment_24, PurposeOfPayment_25};

            String[] arrayCdOrPrprtry = {CdOrPrprtry_1, CdOrPrprtry_2, CdOrPrprtry_3, CdOrPrprtry_4, CdOrPrprtry_5,
                    CdOrPrprtry_6, CdOrPrprtry_7, CdOrPrprtry_8, CdOrPrprtry_9, CdOrPrprtry_10,
                    CdOrPrprtry_11, CdOrPrprtry_12, CdOrPrprtry_13, CdOrPrprtry_14, CdOrPrprtry_15,
                    CdOrPrprtry_16, CdOrPrprtry_17, CdOrPrprtry_18, CdOrPrprtry_19, CdOrPrprtry_20,
                    CdOrPrprtry_21, CdOrPrprtry_22, CdOrPrprtry_23, CdOrPrprtry_24, CdOrPrprtry_25};

            String[] arrayCtgryPrpsTag = {CtgryPrpsTag_1, CtgryPrpsTag_2, CtgryPrpsTag_3, CtgryPrpsTag_4, CtgryPrpsTag_5,
                    CtgryPrpsTag_6, CtgryPrpsTag_7, CtgryPrpsTag_8, CtgryPrpsTag_9, CtgryPrpsTag_10,
                    CtgryPrpsTag_11, CtgryPrpsTag_12, CtgryPrpsTag_13, CtgryPrpsTag_14, CtgryPrpsTag_15,
                    CtgryPrpsTag_16, CtgryPrpsTag_17, CtgryPrpsTag_18, CtgryPrpsTag_19, CtgryPrpsTag_20,
                    CtgryPrpsTag_21, CtgryPrpsTag_22, CtgryPrpsTag_23, CtgryPrpsTag_24, CtgryPrpsTag_25};


            //String URL = "http://core-ref-data-store-makola-zambia-sit.apps.nonprod.ocp.absa.co.za/transaction-codes/"+CountryCode;
            String URL = "http://refdata-service.payment-processor-sit.cto-payments-makola.nonprod.caas.absa.co.za/transaction-codes/?country="+CountryCode+"&channel=PPO&active=true";
            //http://refdata-service.payment-processor-sit.cto-payments-makola.nonprod.caas.absa.co.za/transaction-codes/?country=MUS&channel=PPO
            GetMethod getMethod= new GetMethod(URL);
            HttpClient httpclient = new HttpClient();
            int noOfPOP = 0;

            int result = httpclient.executeMethod(getMethod);
            //response from service is changed to string in below java statement
            String[] PPOdataPOP = getMethod.getResponseBodyAsString().split("}");
            List<String> selectedDataFromSwaggerArrayList = new ArrayList<String>();
            int noOfPOPInTestData = Integer.parseInt(TotalNoOfPOPs);

            //below statement counts POP set up in reference data
            for(int i=0;i<=PPOdataPOP.length-1;i++) {
                if(PPOdataPOP[i].contains("channel\":\"PPO")&&PPOdataPOP[i].contains("\"country\":"+"\""+CountryCode+"\"")) {
                    noOfPOP++;
                    selectedDataFromSwaggerArrayList.add(PPOdataPOP[i]);
                }
            }

            int extraPOPs= noOfPOP-noOfPOPInTestData;

            //convert arraylist ino array
            String[] selectedPOPFromSwaggerArray = new String[selectedDataFromSwaggerArrayList.size()];
            selectedPOPFromSwaggerArray = selectedDataFromSwaggerArrayList.toArray(selectedPOPFromSwaggerArray);


            List<String> POPFound = new ArrayList<String>();
            List<String> POPNotFound = new ArrayList<String>();
            int logsFound=0;
            int logsNotFound=0;
            boolean isFound = false;
            int x=0, j=0;

            //call function to check no of POPs are correct


            for(x=0;x<noOfPOPInTestData;x++) {
                for (j = 0; j < selectedPOPFromSwaggerArray.length; j++) {
                    isFound = false;
                    if (selectedPOPFromSwaggerArray[j].contains("\"description\":\""+arrayPOPs[x]+"\"") && selectedPOPFromSwaggerArray[j].contains("\"categoryPurpose\":\""+arrayCdOrPrprtry[x]+"\"") && selectedPOPFromSwaggerArray[j].contains("\"paymentPurpose\":\""+arrayCtgryPrpsTag[x]+"\"")) {
                        ExtentTestManager.getTest().log(Status.PASS, arrayPOPs[x] + "-POP present with " + arrayCdOrPrprtry[x] + "-" + arrayCtgryPrpsTag[x]);
                        isFound = true;
                        logsFound = logsFound + 1;
                        POPFound.add(arrayPOPs[x]);
                        //duplicateSelectedPOPFromSwaggerArray= ArrayUtils.remove(duplicateSelectedPOPFromSwaggerArray, j);
                        selectedPOPFromSwaggerArray= ArrayUtils.remove(selectedPOPFromSwaggerArray, j);
                        break;
                    }
                }
                if (!isFound) {
                    logsNotFound = logsNotFound + 1;
                    POPNotFound.add(arrayPOPs[x]);
                    ExtentTestManager.getTest().log(Status.FAIL, "Expected values for POP "+arrayPOPs[x] + " : " + arrayCdOrPrprtry[x] + " - " + arrayCtgryPrpsTag[x]);
                }
            }

            if(POPNotFound.size()==0){
                ExtentTestManager.getTest().log(Status.PASS, "All POPs are present in swagger. Number of POPs not found= "+POPNotFound.size());
            }
            else{
                ExtentTestManager.getTest().log(Status.FAIL, "Number of POPs (not found+incorrect) = "+POPNotFound.size()+" . POPs to be added / corrected- "+POPNotFound);
            }

            boolean extraPOPsFlag=verifyNoOfPOP(noOfPOPInTestData,noOfPOP);
            if(!extraPOPsFlag){
                ExtentTestManager.getTest().log(Status.INFO, "-----------------Below "+extraPOPs+" POP/s  is/are  not relevant to PPO channel. It should be removed.------------------");
                for (String s : selectedPOPFromSwaggerArray) {
                    ExtentTestManager.getTest().log(Status.FAIL, s);
                }
            }
        }
        catch(Exception e){
            System.out.println(e);
        }
        finally {
            //    getMethod.releaseConnection();
        }

        return this;}

    public boolean verifyNoOfPOP(int noOfPOPInRefData, int noOfPOP){

        boolean extraPOPsFlag;
        if(noOfPOPInRefData == noOfPOP){
            System.out.println("Number of POPs-"+noOfPOP+" is correctly set up in ref data");
            ExtentTestManager.getTest().log(Status.PASS, "No. of POPs in test data is- "+noOfPOPInRefData+" .No. of POPs in reference data is- "+noOfPOP);
            extraPOPsFlag = true;
            return extraPOPsFlag;
        }
        else{
            System.out.println("Number of POPs-"+noOfPOP+" is not correctly set up in ref data");
            ExtentTestManager.getTest().log(Status.FAIL, "No. of POPs in test data is- "+noOfPOPInRefData+". No. of POPs in reference data is- "+noOfPOP);
            extraPOPsFlag = false;
            return extraPOPsFlag;
        }
    }

    public ReferenceDataPage verifyRefDataBankBranch(String CountryCode, String BankCode, String NoOfBranches,
                                        String Branch_1,String Branch_2,String Branch_3,String Branch_4,String Branch_5,String Branch_6,String Branch_7,String Branch_8,String Branch_9,String Branch_10,
                                        String Branch_11,String Branch_12,String Branch_13,String Branch_14,String Branch_15,String Branch_16,String Branch_17,String Branch_18,String Branch_19,String Branch_20,
                                        String Branch_21,String Branch_22,String Branch_23,String Branch_24,String Branch_25,String Branch_26,String Branch_27,String Branch_28,String Branch_29,String Branch_30,
                                        String Branch_31,String Branch_32,String Branch_33,String Branch_34,String Branch_35,String Branch_36,String Branch_37,String Branch_38,String Branch_39,String Branch_40,
                                        String Branch_41,String Branch_42,String Branch_43,String Branch_44,String Branch_45,String Branch_46,String Branch_47,String Branch_48,String Branch_49,String Branch_50,
                                        String Branch_51,String Branch_52,String Branch_53,String Branch_54,String Branch_55,String Branch_56,String Branch_57,String Branch_58,String Branch_59,String Branch_60,
                                        String Branch_61,String Branch_62,String Branch_63,String Branch_64,String Branch_65,String Branch_66,String Branch_67,String Branch_68,String Branch_69,String Branch_70,
                                        String Branch_71,String Branch_72,String Branch_73,String Branch_74,String Branch_75,String Branch_76,String Branch_77,String Branch_78,String Branch_79,String Branch_80,
                                        String Branch_81,String Branch_82,String Branch_83,String Branch_84,String Branch_85,String Branch_86,String Branch_87,String Branch_88,String Branch_89,String Branch_90,
                                        String Branch_91,String Branch_92,String Branch_93,String Branch_94,String Branch_95,String Branch_96,String Branch_97,String Branch_98,String Branch_99,String Branch_100,
                                        String Branch_101,String Branch_102,String Branch_103,String Branch_104,String Branch_105,String Branch_106,String Branch_107,String Branch_108,String Branch_109,String Branch_200,
                                        String Branch_111,String Branch_112,String Branch_113,String Branch_114,String Branch_115,String Branch_116,String Branch_117,String Branch_118,String Branch_119,String Branch_120,
                                        String Branch_121,String Branch_122,String Branch_123,String Branch_124,String Branch_125,String Branch_126,String Branch_127,String Branch_128,String Branch_129,String Branch_130,
                                        String Branch_131,String Branch_132,String Branch_133,String Branch_134,String Branch_135,String Branch_136,String Branch_137,String Branch_138,String Branch_139,String Branch_140,
                                        String Branch_141,String Branch_142,String Branch_143,String Branch_144,String Branch_145,String Branch_146,String Branch_147,String Branch_148,String Branch_149,String Branch_150)
    {
        try {
            //List<String> arrayOfBranches = new ArrayList<String>();
            int noOfbranchesInTestData = Integer.parseInt(NoOfBranches);

            String[] arrayOfBranches = {Branch_1, Branch_2, Branch_3, Branch_4, Branch_5, Branch_6, Branch_7, Branch_8, Branch_9, Branch_10,
                    Branch_11, Branch_12, Branch_13, Branch_14, Branch_15, Branch_16, Branch_17, Branch_18, Branch_19, Branch_20,
                    Branch_21, Branch_22, Branch_23, Branch_24, Branch_25, Branch_26, Branch_27, Branch_28, Branch_29, Branch_30,
                    Branch_31, Branch_32, Branch_33, Branch_34, Branch_35, Branch_36, Branch_37, Branch_38, Branch_39, Branch_40,
                    Branch_41, Branch_42, Branch_43, Branch_44, Branch_45, Branch_46, Branch_47, Branch_48, Branch_49, Branch_50,
                    Branch_51, Branch_52, Branch_53, Branch_54, Branch_55, Branch_56, Branch_57, Branch_58, Branch_59, Branch_60,
                    Branch_61, Branch_62, Branch_63, Branch_64, Branch_65, Branch_66, Branch_67, Branch_68, Branch_69, Branch_70,
                    Branch_71, Branch_72, Branch_73, Branch_74, Branch_75, Branch_76, Branch_77, Branch_78, Branch_79, Branch_80,
                    Branch_81, Branch_82, Branch_83, Branch_84, Branch_85, Branch_86, Branch_87, Branch_88, Branch_89, Branch_90,
                    Branch_91, Branch_92, Branch_93, Branch_94, Branch_95, Branch_96, Branch_97, Branch_98, Branch_99, Branch_100,
                    Branch_101, Branch_102, Branch_103, Branch_104, Branch_105, Branch_106, Branch_107, Branch_108, Branch_109, Branch_200,
                    Branch_111, Branch_112, Branch_113, Branch_114, Branch_115, Branch_116, Branch_117, Branch_118, Branch_119, Branch_120,
                    Branch_121, Branch_122, Branch_123, Branch_124, Branch_125, Branch_126, Branch_127, Branch_128, Branch_129, Branch_130,
                    Branch_131, Branch_132, Branch_133, Branch_134, Branch_135, Branch_136, Branch_137, Branch_138, Branch_139, Branch_140,
                    Branch_141, Branch_142, Branch_143, Branch_144, Branch_145, Branch_146, Branch_147, Branch_148, Branch_149,Branch_150};

            ExtentTestManager.getTest().log(Status.INFO, "--------------------Reference data verification for bank code '" + BankCode + "' for the country '" + CountryCode + "'-------------------");

            //String URL = "http://core-ref-data-store-makola-mauritius-sit.apps.nonprod.ocp.absa.co.za/branches/" + CountryCode;
            String URL = "http://refdata-service.payment-processor-sit.cto-payments-makola.nonprod.caas.absa.co.za/branches/?country=" + CountryCode+"&active=true";
            GetMethod getMethod = new GetMethod(URL);
            HttpClient httpclient = new HttpClient();
            int noOfBranches = 0;

            int result = httpclient.executeMethod(getMethod);
            //response from service is changed to string in below java statement
            String[] allSwaggerResponseArray = getMethod.getResponseBodyAsString().split("}");

            List<String> selectedDataFromSwaggerArrayList = new ArrayList<String>();

            //below statement counts branches set up in reference data
            for (int j = 0; j <= allSwaggerResponseArray.length - 1; j++) {
                if (allSwaggerResponseArray[j].contains("\"bankCode\":" + "\"" + BankCode + "\"")) {
                    noOfBranches++;
                    selectedDataFromSwaggerArrayList.add(allSwaggerResponseArray[j]);
                }
            }

            //convert arraylist ino array
            String[] selectedDataFromSwaggerArray = new String[selectedDataFromSwaggerArrayList.size()];
            selectedDataFromSwaggerArray = selectedDataFromSwaggerArrayList.toArray(selectedDataFromSwaggerArray);

            ExtentTestManager.getTest().log(Status.INFO, "Number of branches present in swagger is " + selectedDataFromSwaggerArray.length);
            ExtentTestManager.getTest().log(Status.INFO, "Number of branches present in test data is " + noOfbranchesInTestData);

            List<String> branchFound = new ArrayList<String>();
            List<String> branchNotFound = new ArrayList<String>();
            int logsFound = 0;
            int logsNotFound = 0;
            boolean isFound = false;
            int x = 0, i = 0;

            if(noOfbranchesInTestData!=0) {
                for (i = 0; i < arrayOfBranches.length; i++) {
                    for (x = 0; x < selectedDataFromSwaggerArray.length; x++) {
                        isFound = false;
                        if (selectedDataFromSwaggerArray[x].contains("\"branchCode\":\"" + arrayOfBranches[i])) {
                            isFound = true;
                            logsFound = logsFound + 1;
                            branchFound.add(arrayOfBranches[i]);
                            System.out.println("Bank-"+BankCode+" , Branch-"+arrayOfBranches[i]);
                            break;
                        }
                    }
                    if (isFound == false) {
                        logsNotFound = logsNotFound + 1;
                        branchNotFound.add(arrayOfBranches[i]);
                    }
                }
            }

            if(branchNotFound.size()==0){
                ExtentTestManager.getTest().log(Status.PASS, "All branches are present in swagger. Number of branches not found= "+branchNotFound.size());
            }
            else{
                ExtentTestManager.getTest().log(Status.FAIL, "Number of branches not found = "+branchNotFound.size()+" .Branches to be added- "+branchNotFound);
            }
        }
        catch(Exception e){
            System.out.println(e);
        }

        return this; }

}
