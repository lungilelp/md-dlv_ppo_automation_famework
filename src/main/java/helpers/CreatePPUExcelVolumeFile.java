package helpers;

import com.jmt.framework.base.Base;
import com.jmt.framework.base.LocalDriverContext;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import Utilities.GeneralUtilities;

import java.io.*;
import java.util.Objects;

import static Utilities.ExcelUtil.currentDir;
import static java.lang.Thread.sleep;
import static Utilities.GeneralUtilities.randomFixedNumber;

public class CreatePPUExcelVolumeFile extends Base {
    public String ppufilename = null;
    public String ppufilepath = null;
    GeneralUtilities generic = new GeneralUtilities();
    File file;
    FileOutputStream fos;
    XSSFWorkbook workbook;
    XSSFSheet sheet;
    CreateMultiDebitMultiCreditPain001 createfile = new CreateMultiDebitMultiCreditPain001();
    double controlSum = 0.0;
    /** Methods*/


    public String[] createExcelVolumeFile(String TestCaseID, String TestName, String NumberOfTransactions, String MaximumNumberOfTransactions, String DebtorBankCode,
                                        String DebtorBranchID, String DebtorRegionCode, String DebtorCurrency, String DebtorAccountNumber,
                                        String DebtorAccountHolderName, String DebtorPurposeOfPayment, String DebtorPostingOption,
                                        String DebtorWaiveChargesOption, String DebtorForcePostingOption, String DebtorOriginationReference, String DebtorWorkflowReference, String strWorkflowReferenceUniqueTxt,
                                        String CreditorBankCode_1, String CreditorBranchId_1, String CreditorAccountRegionCode_1, String CreditorAccountNumber_1, String CreditorAccountHolder_1,
                                        String CreditorDestinationRef_1, String CreditorAmount_1, String CreditorAdditionalRemitanceInformation_1,
                                        String CreditorBankCode_2, String CreditorBranchId_2, String CreditorAccountRegionCode_2, String CreditorAccountNumber_2, String CreditorAccountHolder_2,
                                        String CreditorDestinationRef_2, String CreditorAmount_2, String CreditorAdditionalRemitanceInformation_2,
                                        String CreditorBankCode_3, String CreditorBranchId_3, String CreditorAccountRegionCode_3, String CreditorAccountNumber_3, String CreditorAccountHolder_3,
                                        String CreditorDestinationRef_3, String CreditorAmount_3, String CreditorAdditionalRemitanceInformation_3,
                                        String CreditorBankCode_4, String CreditorBranchId_4, String CreditorAccountRegionCode_4, String CreditorAccountNumber_4, String CreditorAccountHolder_4,
                                        String CreditorDestinationRef_4, String CreditorAmount_4, String CreditorAdditionalRemitanceInformation_4,
                                        String CreditorBankCode_5, String CreditorBranchId_5, String CreditorAccountRegionCode_5, String CreditorAccountNumber_5, String CreditorAccountHolder_5,
                                        String CreditorDestinationRef_5, String CreditorAmount_5, String CreditorAdditionalRemitanceInformation_5,
                                        String CreditorBankCode_6, String CreditorBranchId_6, String CreditorAccountRegionCode_6, String CreditorAccountNumber_6, String CreditorAccountHolder_6,
                                        String CreditorDestinationRef_6, String CreditorAmount_6, String CreditorAdditionalRemitanceInformation_6) throws IOException, FileNotFoundException {


        try {

            ppufilename = TestCaseID + "_" + TestName + "_" + DebtorPostingOption + "_" + strWorkflowReferenceUniqueTxt + "_" + getCurrentFormattedDate();
            ppufilepath = currentDir + "\\testOutputs\\ppuvolumefiles\\";
            generic.makeDirectory(ppufilepath);

            workbook = new XSSFWorkbook();
            sheet = workbook.createSheet("TestSheet");
            sheet.createRow(0);
            sheet.getRow(0).createCell(0).setCellValue("Transaction Number");
            sheet.getRow(0).createCell(1).setCellValue("Value Date");
            sheet.getRow(0).createCell(2).setCellValue("Originator Bank Code");
            sheet.getRow(0).createCell(3).setCellValue("Originator Branch Id");
            sheet.getRow(0).createCell(4).setCellValue("Originator Region Code");
            sheet.getRow(0).createCell(5).setCellValue("Originator Account Currency");
            sheet.getRow(0).createCell(6).setCellValue("Originator Account Number");
            //sheet.getRow(0).createCell(7).setCellValue("Originator Name");
            sheet.getRow(0).createCell(7).setCellValue("Purpose of Payment");
            sheet.getRow(0).createCell(8).setCellValue("Posting Option");
            sheet.getRow(0).createCell(9).setCellValue("Waive Charges");
            sheet.getRow(0).createCell(10).setCellValue("Force Post");
            sheet.getRow(0).createCell(11).setCellValue("Origination Reference");
            sheet.getRow(0).createCell(12).setCellValue("Destination Bank Code");
            sheet.getRow(0).createCell(13).setCellValue("Destination Branch Id");
            sheet.getRow(0).createCell(14).setCellValue("Destination Region Code");
            sheet.getRow(0).createCell(15).setCellValue("Destination Account Number");
            sheet.getRow(0).createCell(16).setCellValue("Destination Name");
            sheet.getRow(0).createCell(17).setCellValue("Destination Reference");
            sheet.getRow(0).createCell(18).setCellValue("Amount");
            sheet.getRow(0).createCell(19).setCellValue("Workflow Reference");
            sheet.getRow(0).createCell(20).setCellValue("Additional Remittance Information");
            String additional_rem = strWorkflowReferenceUniqueTxt + CreditorAdditionalRemitanceInformation_1;
            String date_num = getDateFormatForSettlementNotification();
            //create file
            String[] debtordetails = {DebtorBankCode, DebtorBranchID, DebtorRegionCode, DebtorCurrency, DebtorAccountNumber, //DebtorAccountHolderName,
                    DebtorPurposeOfPayment, DebtorPostingOption, DebtorWaiveChargesOption, DebtorForcePostingOption, strWorkflowReferenceUniqueTxt + DebtorOriginationReference};

            String[][] creditordetails = {
                    {CreditorBankCode_1, CreditorBranchId_1, CreditorAccountRegionCode_1, CreditorAccountNumber_1, CreditorAccountHolder_1,
                            strWorkflowReferenceUniqueTxt + CreditorDestinationRef_1, CreditorAmount_1, strWorkflowReferenceUniqueTxt, additional_rem + 1},
                    {CreditorBankCode_2, CreditorBranchId_2, CreditorAccountRegionCode_2, CreditorAccountNumber_2, CreditorAccountHolder_2,
                            strWorkflowReferenceUniqueTxt + CreditorDestinationRef_2, CreditorAmount_2, strWorkflowReferenceUniqueTxt, additional_rem + 2},
                    {CreditorBankCode_3, CreditorBranchId_3, CreditorAccountRegionCode_3, CreditorAccountNumber_3, CreditorAccountHolder_3,
                            strWorkflowReferenceUniqueTxt + CreditorDestinationRef_3, CreditorAmount_3, strWorkflowReferenceUniqueTxt, additional_rem + 3},
                    {CreditorBankCode_4, CreditorBranchId_4, CreditorAccountRegionCode_4, CreditorAccountNumber_4, CreditorAccountHolder_4,
                            strWorkflowReferenceUniqueTxt + CreditorDestinationRef_4, CreditorAmount_4, strWorkflowReferenceUniqueTxt, additional_rem + 4},
                    {CreditorBankCode_5, CreditorBranchId_5, CreditorAccountRegionCode_5, CreditorAccountNumber_5, CreditorAccountHolder_5,
                            strWorkflowReferenceUniqueTxt + CreditorDestinationRef_5, CreditorAmount_5, strWorkflowReferenceUniqueTxt, additional_rem + 5},
                    {CreditorBankCode_6, CreditorBranchId_6, CreditorAccountRegionCode_6, CreditorAccountNumber_6, CreditorAccountHolder_6,
                            strWorkflowReferenceUniqueTxt + CreditorDestinationRef_6, CreditorAmount_6, strWorkflowReferenceUniqueTxt, additional_rem + 6}
            };
            //Control Sum calculation
            controlSum = Double.parseDouble(CreditorAmount_1)+ Double.parseDouble(CreditorAmount_2)+ Double.parseDouble(CreditorAmount_3)+ Double.parseDouble(CreditorAmount_4)+ Double.parseDouble(CreditorAmount_5);

            for (int row = 1; row <= Integer.parseInt(NumberOfTransactions); row++) {
                //create row
                sheet.createRow(row);
                //set transaction no.
                sheet.getRow(row).createCell(0).setCellValue(row);
                //set value date
                sheet.getRow(row).createCell(1).setCellValue(date_num);
                //set debtor details
                for (int column = 0; column <= debtordetails.length - 1; column++) {
                    sheet.getRow(row).createCell(column + 2).setCellValue(debtordetails[column]);
                }
                // set creditor details
                for (int column = 0; column <= creditordetails[0].length - 1; column++) {

                    sheet.getRow(row).createCell(column + 12).setCellValue(creditordetails[row - 1][column]);
                }
            }

            String Input_String = ppufilepath + ppufilename + ".xlsx";
            file = new File(Input_String);
            fos = new FileOutputStream(file);
            workbook.write(fos);
            workbook.close();

            FileInputStream inputStream = new FileInputStream(Input_String);
            XSSFWorkbook wb_template = new XSSFWorkbook(inputStream);
            inputStream.close();

            SXSSFWorkbook wb = new SXSSFWorkbook(wb_template);
            wb.setCompressTempFiles(true);

            SXSSFSheet sh = (SXSSFSheet) wb.getSheetAt(0);
            sh.setRandomAccessWindowSize(100);// keep 100 rows in memory, exceeding rows will be flushed to disk

            //create unique debtor account numbers
            int max = Integer.parseInt(MaximumNumberOfTransactions) + 1;
            String[] uniqueAccountNumbers = new String[max];

            for (int i = 0; i < max; i++) {
                String temp = randomFixedNumber(DebtorAccountNumber);

                for (int j = 0; j < i; j++) {

                    if (Objects.equals(uniqueAccountNumbers[j], temp)) {

                        i = i - 1;

                    }
                }
                uniqueAccountNumbers[i] = temp;
            }

            //Iterate through creditor creditordetails[] declared above
            int count = 0;
            for (int rownum = Integer.parseInt(NumberOfTransactions) + 1; rownum < max; rownum++) {

                //Reset count iteration through
                if (count == 4) {
                                                 count = 0;
                                                };
                count += 1;

                //Create a new row
                Row row = sh.createRow(rownum);

                //Avoid using the same bank code for offus transactions
                String creditorBankID;
                if (!creditordetails[count][1].equals(DebtorBranchID)) {
                    creditorBankID = creditordetails[count][1];
                } else {
                    creditorBankID = "0" + (creditordetails[count][1] + 1);
                }


                String[] data_pol = {
                        String.valueOf(rownum),//Transaction Number
                        date_num,//Value Date
                        DebtorBankCode,//Originator Bank Code
                        DebtorBranchID,//Originator Branch Id
                        DebtorRegionCode,//Originator Branch Id
                        DebtorCurrency,//Originator Account Currency
                        DebtorAccountNumber,//Originator Account Number
                        DebtorPurposeOfPayment,
                        DebtorPostingOption,
                        DebtorWaiveChargesOption,
                        DebtorForcePostingOption,
                        strWorkflowReferenceUniqueTxt + DebtorOriginationReference,
                        "0" + (Integer.parseInt(creditordetails[count][0]) + 1),//Destination Bank Code
                        creditorBankID,//Destination Branch Id
                        creditordetails[count][2],//Destination Region Code
                        uniqueAccountNumbers[rownum],//Destination Account Number
                        DebtorAccountHolderName,//Destination Name
                        strWorkflowReferenceUniqueTxt + DebtorOriginationReference,//Destination Reference
                        creditordetails[count][6],//Amount
                        strWorkflowReferenceUniqueTxt,//Workflow Reference
                        additional_rem + rownum//Additional Remittance Information
                };
                controlSum += Double.parseDouble(creditordetails[count][6]);

                for (int cellnum = 0; cellnum < 21; cellnum++) {
                    Cell cell = row.createCell(cellnum);
                    String address = new CellReference(cell).formatAsString() + "cell" + cellnum;

                    int index = cellnum;
                    cell.setCellValue(data_pol[index]);

                }

            }


            FileOutputStream out = new FileOutputStream(Input_String);
            wb.write(out);
            out.close();
            //choose file
            LocalDriverContext.getRemoteWebDriver().findElement(By.id("file")).sendKeys(Input_String);
            sleep(10000);
        }
        catch(Exception e){
            System.out.println(e);
        }

        return new String[]{
                ppufilename,
                controlSum+"0"
        };
    }


    //create invalid file
//    public String createInvalidExcelVolumeFile(String TestCaseID,String TestName,String NumberOfTransactions, String DebtorBankCode,
//                                         String DebtorBranchID,String DebtorRegionCode, String DebtorCurrency, String DebtorAccountNumber,
//                                         String DebtorAccountHolderName, String DebtorPurposeOfPayment, String DebtorPostingOption,
//                                         String DebtorWaiveChargesOption, String DebtorForcePostingOption, String DebtorOriginationReference, String DebtorWorkflowReference,String strWorkflowReferenceUniqueTxt,
//                                         String CreditorBankCode_1,String CreditorBranchId_1,String CreditorAccountRegionCode_1,String CreditorAccountNumber_1,String CreditorAccountHolder_1,
//                                         String CreditorDestinationRef_1,String CreditorAmount_1,String CreditorAdditionalRemitanceInformation_1,
//                                         String CreditorBankCode_2,String CreditorBranchId_2,String CreditorAccountRegionCode_2,String CreditorAccountNumber_2,String CreditorAccountHolder_2,
//                                         String CreditorDestinationRef_2,String CreditorAmount_2,String CreditorAdditionalRemitanceInformation_2,
//                                         String CreditorBankCode_3,String CreditorBranchId_3,String CreditorAccountRegionCode_3,String CreditorAccountNumber_3,String CreditorAccountHolder_3,
//                                         String CreditorDestinationRef_3,String CreditorAmount_3,String CreditorAdditionalRemitanceInformation_3,
//                                         String CreditorBankCode_4,String CreditorBranchId_4,String CreditorAccountRegionCode_4,String CreditorAccountNumber_4,String CreditorAccountHolder_4,
//                                         String CreditorDestinationRef_4,String CreditorAmount_4,String CreditorAdditionalRemitanceInformation_4,
//                                         String CreditorBankCode_5,String CreditorBranchId_5,String CreditorAccountRegionCode_5,String CreditorAccountNumber_5,String CreditorAccountHolder_5,
//                                         String CreditorDestinationRef_5,String CreditorAmount_5,String CreditorAdditionalRemitanceInformation_5,
//                                         String CreditorBankCode_6,String CreditorBranchId_6,String CreditorAccountRegionCode_6,String CreditorAccountNumber_6,String CreditorAccountHolder_6,
//                                         String CreditorDestinationRef_6,String CreditorAmount_6,String CreditorAdditionalRemitanceInformation_6) throws IOException, FileNotFoundException {
//        try{
//
//            ppufilename = TestCaseID+"_"+TestName+"_"+DebtorPostingOption+"_" +strWorkflowReferenceUniqueTxt +"_"+ getCurrentFormattedDate();
//            ppufilepath = currentDir+"\\testOutputs\\ppufiles\\";
//
//            workbook = new XSSFWorkbook();
//            sheet = workbook.createSheet("TestSheet");
//            sheet.createRow(0);
//            sheet.getRow(0).createCell(0).setCellValue("Transaction Number");
//            sheet.getRow(0).createCell(1).setCellValue("Value Date");
//            sheet.getRow(0).createCell(2).setCellValue("Originator Bank Code");
//            sheet.getRow(0).createCell(3).setCellValue("Originator Branch Id");
//            sheet.getRow(0).createCell(4).setCellValue("Originator Region Code");
//            sheet.getRow(0).createCell(5).setCellValue("Originator Account Currency");
//            sheet.getRow(0).createCell(6).setCellValue("Originator Account Number");
//            //sheet.getRow(0).createCell(7).setCellValue("Originator Name");
//            sheet.getRow(0).createCell(7).setCellValue("Purpose of Payment");
//            sheet.getRow(0).createCell(8).setCellValue("Posting Option");
//            sheet.getRow(0).createCell(9).setCellValue("Waive Charges");
//            sheet.getRow(0).createCell(10).setCellValue("Force Post");
//            sheet.getRow(0).createCell(11).setCellValue("Origination Reference");
//            sheet.getRow(0).createCell(12).setCellValue("Destination Bank Code");
//            sheet.getRow(0).createCell(13).setCellValue("Destination Branch Id");
//            sheet.getRow(0).createCell(14).setCellValue("Destination Region Code");
//            sheet.getRow(0).createCell(15).setCellValue("Destination Account Number");
//            sheet.getRow(0).createCell(16).setCellValue("Destination Name");
//            sheet.getRow(0).createCell(17).setCellValue("Destination Reference");
//            sheet.getRow(0).createCell(18).setCellValue("Amount");
//            sheet.getRow(0).createCell(19).setCellValue("Workflow Reference");
//            sheet.getRow(0).createCell(20).setCellValue("Additional Remittance Information");
//
//            //create file
//            String[] debtordetails = {createfile.setTagValue(DebtorBankCode),
//                    createfile.setTagValue(DebtorBranchID),
//                    createfile.setTagValue(DebtorRegionCode),
//                    createfile.setTagValue(DebtorCurrency),
//                    createfile.setTagValue(DebtorAccountNumber), //DebtorAccountHolderName,
//                    createfile.setTagValue(DebtorPurposeOfPayment),
//                    createfile.setTagValue(DebtorPostingOption),
//                    createfile.setTagValue(DebtorWaiveChargesOption),
//                    createfile.setTagValue(DebtorForcePostingOption),
//                    createfile.setTagValue(DebtorOriginationReference)};
//
//            String[][] creditordetails ={
//                    {createfile.setTagValue(CreditorBankCode_1), createfile.setTagValue(CreditorBranchId_1), createfile.setTagValue(CreditorAccountRegionCode_1), createfile.setTagValue(CreditorAccountNumber_1), createfile.setTagValue(CreditorAccountHolder_1),
//                            createfile.setTagValue(CreditorDestinationRef_1), createfile.setTagValue(CreditorAmount_1),createfile.setTagValue(DebtorWorkflowReference), createfile.setTagValue(CreditorAdditionalRemitanceInformation_1)},
//
//                    {createfile.setTagValue(CreditorBankCode_2),createfile.setTagValue(CreditorBranchId_2),createfile.setTagValue(CreditorAccountRegionCode_2),createfile.setTagValue(CreditorAccountNumber_2),createfile.setTagValue(CreditorAccountHolder_2),
//                            createfile.setTagValue(CreditorDestinationRef_2), createfile.setTagValue(CreditorAmount_2),createfile.setTagValue(DebtorWorkflowReference), createfile.setTagValue(CreditorAdditionalRemitanceInformation_2)},
//
//                    {createfile.setTagValue(CreditorBankCode_3),createfile.setTagValue(CreditorBranchId_3),createfile.setTagValue(CreditorAccountRegionCode_3),createfile.setTagValue(CreditorAccountNumber_3),createfile.setTagValue(CreditorAccountHolder_3),
//                            createfile.setTagValue(CreditorDestinationRef_3), createfile.setTagValue(CreditorAmount_3),createfile.setTagValue(DebtorWorkflowReference), createfile.setTagValue(CreditorAdditionalRemitanceInformation_3)},
//
//                    {createfile.setTagValue(CreditorBankCode_4),createfile.setTagValue(CreditorBranchId_4),createfile.setTagValue(CreditorAccountRegionCode_4),createfile.setTagValue(CreditorAccountNumber_4),createfile.setTagValue(CreditorAccountHolder_4),
//                            createfile.setTagValue(CreditorDestinationRef_4), createfile.setTagValue(CreditorAmount_4),createfile.setTagValue(DebtorWorkflowReference), createfile.setTagValue(CreditorAdditionalRemitanceInformation_4)},
//
//                    {createfile.setTagValue(CreditorBankCode_5),createfile.setTagValue(CreditorBranchId_5),createfile.setTagValue(CreditorAccountRegionCode_5),createfile.setTagValue(CreditorAccountNumber_5),createfile.setTagValue(CreditorAccountHolder_5),
//                            createfile.setTagValue(CreditorDestinationRef_5), createfile.setTagValue(CreditorAmount_5),createfile.setTagValue(DebtorWorkflowReference), createfile.setTagValue(CreditorAdditionalRemitanceInformation_5)},
//
//                    {createfile.setTagValue(CreditorBankCode_6),createfile.setTagValue(CreditorBranchId_6),createfile.setTagValue(CreditorAccountRegionCode_6),createfile.setTagValue(CreditorAccountNumber_6),createfile.setTagValue(CreditorAccountHolder_6),
//                            createfile.setTagValue(CreditorDestinationRef_6), createfile.setTagValue(CreditorAmount_6),createfile.setTagValue(DebtorWorkflowReference), createfile.setTagValue(CreditorAdditionalRemitanceInformation_6)}
//            };
//
//            for(int row = 1; row <= 100000; row++) {
//                //create row
//                sheet.createRow(row);
//                //set transaction no.
//                sheet.getRow(row).createCell(0).setCellValue(row);
//                //set value date
//                sheet.getRow(row).createCell(1).setCellValue(getDateFormatForSettlementNotification());
//                //set debtor details
//                for (int column = 0; column <=debtordetails.length-1; column++) {
//                    sheet.getRow(row).createCell(column+2).setCellValue(debtordetails[column]);
//                }
//                //set creditor details
//                for (int column = 0; column <=creditordetails[0].length-1; column++) {
//                    sheet.getRow(row).createCell(column+12).setCellValue(creditordetails[row-1][column]);
//                }
//            }
//
//
//            String file_stream = ppufilepath+ppufilename+".xlsx";
//            file = new File(file_stream);
//            fos = new FileOutputStream(file);
//            workbook.write(fos);
//            workbook.close();
//
//            FileInputStream inputStream = new FileInputStream(file_stream);
//            XSSFWorkbook wb_template = new XSSFWorkbook(inputStream);
//            inputStream.close();
//
//            SXSSFWorkbook wb = new SXSSFWorkbook(wb_template);
//            wb.setCompressTempFiles(true);
//
//            SXSSFSheet sh = (SXSSFSheet) wb.getSheetAt(0);
//            sh.setRandomAccessWindowSize(100);// keep 100 rows in memory, exceeding rows will be flushed to disk
//
//            for(int rownum = 1; rownum < 100000; rownum++){
//                Row row = sh.createRow(rownum);
//                for(int cellnum = 13; cellnum < 22; cellnum++){
//                    Cell cell = row.createCell(cellnum-1);
//                    String address = new CellReference(cell).formatAsString()+"cell"+cellnum;
//                    String [] data_pol = {
//                            ""+randomFixedNumber(DebtorBankCode ),//Destination Bank Code
//                            ""+randomFixedNumber(DebtorBranchID ),//Destination Branch Id
//                            ""+randomFixedNumber(DebtorRegionCode ),//Destination Region Code
//                            ""+randomFixedNumber(DebtorAccountNumber ),//Destination Account Number
//                            "AccountNameOfCredi",//Destination Name
//                            "Destination Reference",//Destination Reference
//                            ""+randomFixedNumber(DebtorBankCode ),//Amount
//                            "//Workflow Reference",//Workflow Reference
//                            "Additional"//Additional Remittance Information
//                    };
//                    int index = cellnum-13;
//                    cell.setCellValue(data_pol[index]);
//
//                }
//
//            }
//
//
//            FileOutputStream out = new FileOutputStream("tempsxssf.xlsx");
//            wb.write(out);
//            out.close();
//            //choose file
//            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("file")));
//            LocalDriverContext.getRemoteWebDriver().findElement(By.id("file")).sendKeys(ppufilepath+ppufilename+".xlsx");
//
//        }
//        catch(Exception e){
//            System.out.println(e);
//        }
//
//        return ppufilename;
//    }

}
