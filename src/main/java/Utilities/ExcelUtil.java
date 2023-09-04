package Utilities;

import java.lang.*;

import config.Settings;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.Platform;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


/**
 * @author Jonathan Mbuyi Tshitenda [ ABJT439 ]
 * @date created 9/5/2019
 * @package com.jmt.framework.Utilities
 */
public class ExcelUtil {

    //Main Directory of the project
    public static final String currentDir = System.getProperty("user.dir");

    //Location of Test data excel file
    public static String testDataExcelPath = null;

    //Excel WorkBook
    private static XSSFWorkbook excelWBook;

    //Excel Sheet
    private static XSSFSheet excelWSheet;

    //Excel cell
    private static XSSFCell cell;

    //Excel row
    private static XSSFRow row;

    //Row Number
    public static int rowNumber;

    //Column Number
    public static int columnNumber;

    //Setter and Getters of row and columns
    public static void setRowNumber(int pRowNumber) {
        rowNumber = pRowNumber;
    }

    public static int getRowNumber() {
        return rowNumber;
    }

    public static void setColumnNumber(int pColumnNumber)
    {
        columnNumber = pColumnNumber;
    }

    public static int getColumnNumber() {
        return columnNumber;
    }

    // This method has two parameters: "Test data excel file name" and "Excel sheet name"
    // It creates FileInputStream and set excel file and excel sheet to excelWBook and excelWSheet variables.
    public static void setExcelFileSheet(String sheetName)
    {
        //MAC or Windows Selection for excel path
        if (Platform.getCurrent().toString().equalsIgnoreCase("MAC"))
        {
            testDataExcelPath = currentDir + "//src//test//resources//xls//";
        } else if (Platform.getCurrent().toString().contains("WIN"))
        {
            //testDataExcelPath = currentDir + "\\src\\test\\resources\\test_data\\PPO_Tests_Data_Zambia.xlsx";
            testDataExcelPath = currentDir+Settings.ExcelSheetPath;
        }
        try
        {
            // Open the Excel file
            FileInputStream ExcelFile = new FileInputStream(currentDir+Settings.ExcelSheetPath);
            excelWBook = new XSSFWorkbook(ExcelFile);
            excelWSheet = excelWBook.getSheet(sheetName);
        } catch (Exception e)
        {
            try
            {
                throw (e);
            } catch (IOException e1)
            {
                e1.printStackTrace();
            }
        }
    }

    //This method reads the test data from the Excel cell.
    //We are passing row number and column number as parameters.
    public static String getCellData(int RowNum, int ColNum)
    {
        try
        {
            cell = excelWSheet.getRow(RowNum).getCell(ColNum);
            DataFormatter formatter = new DataFormatter();
            String cellData = formatter.formatCellValue(cell);
            return cellData;
        } catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println(e.getCause());
            e.printStackTrace();
            throw (e);
        }
    }

    //This method gets the sheetname and return the row count
    public int getRowCount(String sheetName)
    {
        int rowCount = 0;
        try
        {
            excelWSheet = excelWBook.getSheet(sheetName);
            rowCount = excelWSheet.getLastRowNum();
            /**PRINT EXCEL NUMBER OF ROWS ON CONSOLE*/
            System.out.println("Number of rows in the excel sheet : "+rowCount);
        }catch (Exception exp)
        {
            System.out.println(exp.getMessage());
            System.out.println(exp.getCause());
            exp.printStackTrace();
        }
        return rowCount;
    }

    //This method gets the sheetname and return the column count
    public  int getColumnCount(String sheetName)
    {
        int colCount = 0;
        try{
            excelWSheet = excelWBook.getSheet(sheetName);
            row = excelWSheet.getRow(0);
            colCount = row.getLastCellNum();
            /**PRINT EXCEL NUMBER OF COLUMNS ON CONSOLE*/
            System.out.println("Number of Columns in the excel sheet : "+colCount);

        }catch (Exception exp)
        {
            System.out.println(exp.getMessage());
            System.out.println(exp.getCause());
            exp.printStackTrace();
        }
        return colCount;
    }


    public static int getRowUsed() throws Exception
    {
        try
        {
            int RowCount = excelWSheet.getLastRowNum();
            return RowCount;
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
            throw (e);
        }
    }

    //This method gets excel file, row and column number and set a value to the that cell.
    public static void setCellData(String value, int RowNum, int ColNum)
    {
        try
        {
            row = excelWSheet.getRow(RowNum);
            cell = row.getCell(ColNum);
            if (cell == null)
            {
                cell = row.createCell(ColNum);
                cell.setCellValue(value);
            } else
            {
                cell.setCellValue(value);
            }
            // Constant variables Test Data path and Test Data file name
            //FileOutputStream fileOut = new FileOutputStream(currentPath+getConfig().getTestDataSheet());
            FileOutputStream fileOut = new FileOutputStream(Settings.ExcelSheetPath);
            excelWBook.write(fileOut);
            fileOut.flush();
            fileOut.close();
        } catch (Exception e)
        {
            try
            {
                throw (e);
            } catch (IOException e1)
            {
                e1.printStackTrace();
            }
        }
    }

    //This method takes row number as a parameter and returns the data of given row number.
    public static XSSFRow getRowData(int RowNum)
    {
        try
        {
            row = excelWSheet.getRow(RowNum);
            return row;
        } catch (Exception e)
        {
            throw (e);
        }
    }

    //get my Row which is equal that colNum
    public static XSSFCell getCurrentRow()
    {
        //row = excelWSheet.getRow(rowNumber);
        cell = excelWSheet.getRow(rowNumber).getCell(columnNumber);
        //Set Result is pass in that cell number
        cell.setCellValue("");
        return cell;
    }

    public static Object[][] getTableArray(String FilePath, String SheetName, int iTestCaseRow)    throws Exception
    {
        String[][] tabArray = null;
        try
        {
            FileInputStream ExcelFile = new FileInputStream(FilePath);
            // Access the required test data sheet
            excelWBook = new XSSFWorkbook(ExcelFile);
            excelWSheet = excelWBook.getSheet(SheetName);
            int startCol = 1;
            int ci=0,cj=0;
            int totalRows = 1;
            int totalCols = 2;
            tabArray=new String[totalRows][totalCols];
            for (int j=startCol;j<=totalCols;j++, cj++)
            {
                tabArray[ci][cj]=getCellData(iTestCaseRow,j);
                System.out.println(tabArray[ci][cj]);
            }
        } catch (FileNotFoundException e)
        {
            System.out.println("Could not read the Excel sheet");
            e.printStackTrace();
        } catch (IOException e)
        {
            System.out.println("Could not read the Excel sheet");
            e.printStackTrace();
        }
        return(tabArray);
    }

    public static String getTestCaseName(String sTestCase)throws Exception
    {
        String value = sTestCase;
        try
        {
            int posi = value.indexOf("@");
            value = value.substring(0, posi);
            posi = value.lastIndexOf(".");
            value = value.substring(posi + 1);
            return value;

        }catch (Exception e)
        {
            throw (e);
        }
    }

    public static int getRowContains(String sTestCaseName, int colNum) throws Exception
    {
        int i;
        try
        {
            int rowCount = ExcelUtil.getRowUsed();
            for ( i=0 ; i<rowCount; i++)
            {
                if  (ExcelUtil.getCellData(i,colNum).equalsIgnoreCase(sTestCaseName))
                {
                    break;
                }
            }
            return i;
        }catch (Exception e)
        {
            throw(e);
        }
    }
}
