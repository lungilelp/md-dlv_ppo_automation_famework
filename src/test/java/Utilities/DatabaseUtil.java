package Utilities;

import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.service.ExtentTestManager;
import config.Settings;

import java.sql.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;

import static org.testng.Assert.fail;

/**
 * @author Jonathan Mbuyi Tshitenda [ ABJT439 ]
 * @date created 7/29/2019
 * @package com.jmt.framework.controls.internals
 */

public class DatabaseUtil
{


    //*************POC Database Handler ********************************************************************************
    public static String databaseHandlerForPocDB(String strSQLquery, String strColumnName_1) throws ClassNotFoundException, SQLException, IllegalAccessException, InstantiationException
    {
        /**DATA BASE PROCESS FOR POC DB*/

        //TODO : Make sure database communication works with the below settings
        String record_1 = null;

        //Connection URL Syntax: "jdbc:mysql://ipaddress:portnumber/db_name"
        String dbUrl = Settings.DBpocUrl;


        //Define what type of driver is needed(get from property file)
        String dbDriver = Settings.DBpocDriverJdbc;

        //Database Username
        String username = Settings.DBpocUserName;

        //Database Password
        String password = Settings.DBpocPassword;

        try
        {
            Class.forName("org.postgresql.Driver");
        }
        catch (ClassNotFoundException e) {
            System.out.println("Postgres JDBC Driver not found !!");
        }

        //Load the type of driver here(mysql/jdbc/postgres)
        //Class.forName("oracle.jdbc.OracleDriver");

        //Test the DB connection before proceeding
        try{
            Connection con2 = DriverManager.getConnection(dbUrl, username, password);
            System.out.println("INFORMATION:: Database Connected successfully");

            //Query to Execute
            String sqlQuery = strSQLquery;

            //Create Statement Object
            Statement stmt = con2.createStatement();

            //Execute the SQL Query. Store results in ResultSet
            ResultSet rs = stmt.executeQuery(sqlQuery);
            Thread.sleep(3000);

            //While Loop to iterate through all data and print results
            if ( rs.next() )
            {
                //Get data by column name/label instead of index
                record_1 = rs.getString(strColumnName_1);
            }
            else
            {
                System.out.print("No Records Where Found In The Database  =>");
                return null;
            }
            //closing DB Connection
            con2.close();
        }catch (Exception e){
            System.out.println("Unable to make connection with DB" +e);
            e.printStackTrace();
            ExtentTestManager.getTest().log(Status.FAIL, "INFORMATION:: Unable to make connection with DB" +e);
            fail("This test has been stopped!");
        }

        //Return the records from the Database
        return record_1;
    }

    //*************Audit Database Handler ******************************************************************************
    public static String databaseHandlerForAuditDB(String strSQLquery, String strColumnName_1) throws ClassNotFoundException, SQLException, IllegalAccessException, InstantiationException
    {
        /**DATA BASE PROCESS FOR AUDIT DB*/

        //TODO : Make sure database communication works with the below settings
        String record_1 = null;

        //Connection URL Syntax: "jdbc:mysql://ipaddress:portnumber/db_name"
        String dbUrl = Settings.DBauditUrl;

        //Define what type of driver is needed(get from property file)
        String dbDriver = Settings.DBauditJdbc;

        //Database Username
        String username =Settings.DBauditUserName;

        //Database Password
        String password =Settings.DBauditPassword;

        //Load the type of driver here(mysql jdbc)
        Class.forName("oracle.jdbc.driver.OracleDriver").newInstance();

        //Test the DB connection before proceeding
        try
        {
            //Create Connection to DB
            //System.out.println(getConfig().getDatabaseUrl2());
            Connection con = DriverManager.getConnection(dbUrl, username, password);
            System.out.println("INFORMATION:: Database Connected successfully");

            //Query to Execute
            String sqlQuery = strSQLquery;

            //Create Statement Object
            Statement stmt = con.createStatement();

            //Execute the SQL Query. Store results in ResultSet
            ResultSet rs = stmt.executeQuery(sqlQuery);

            //While Loop to iterate through all data and print results
            if ( rs.next() )
            {
                //Get data by column name/label instead of index
                record_1 = rs.getString(strColumnName_1);
            }
            else
            {
                System.out.print("No Records Where Found In The Database  =>");
                return null;
            }
            //closing DB Connection
            con.close();
        }
        catch (Exception e)
        {
            System.out.println("Unable to make connection with DB" +e);
            e.printStackTrace();
            ExtentTestManager.getTest().log(Status.FAIL, "INFORMATION:: Unable to make connection with DB" +e);
            fail("This test has been stopped!");
        }
        return record_1;
    }

    //*************Flex Database Handler *******************************************************************************
//    public static String databaseHandlerForFlexDB(String strSQLquery,String strColumnName_1,String strColumnName_2,String strColumnName_3,
//                                                  String strColumnName_4,String strColumnName_5, String strColumnName_6,String strColumnName_7,
//                                                  String strColumnName_8,String strColumnName_9, String strColumnName_10,String strColumnName_11)


    public static List<String> databaseHandlerForFlexDB(String strSQLquery,String strColumnName_1,String strColumnName_2,String strColumnName_3,
                                                        String strColumnName_4,String strColumnName_5, String strColumnName_6,String strColumnName_7,
                                                        String strColumnName_8,String strColumnName_9, String strColumnName_10,String strColumnName_11) throws SQLException {
        /**DATA BASE PROCESS FOR AUDIT DB*/

        //TODO : Make sure database communication works with the below settings
        String strSelectedFieldsInRecords = null;

        //Connection URL Syntax: "jdbc:mysql://ipaddress:portnumber/db_name"
        String dbUrl = Settings.DBFlexUrl;

        //Define what type of driver is needed(get from property file)
        String dbDriver = Settings.DBaFlexJdbc;

        //Database Username
        String username =Settings.DBFlexUserName;

        //Database Password
        String password =Settings.DBFlexPassword;

        //Create an array list to store the multiple records that get returned from the database
        List<String> results =new ArrayList<String>();


        //Create Connection to DB
        //System.out.println(getConfig().getDatabaseUrl2());
        Connection conn = DriverManager.getConnection(dbUrl, username, password);
        System.out.println("INFORMATION:: Database Connected successfully");

        //Alter the table to set the variables
        conn.createStatement().execute("alter session set current_schema = kefcrh");

        //Query to Execute
        String sqlQuery = strSQLquery;


        //Create Statement Object
        Statement stmt = conn.createStatement();

        //Execute the SQL Query. Store results in ResultSet
        ResultSet resultSet = stmt.executeQuery(sqlQuery);


        try
        {
            //Iterate the result set and return all the records(Multiple records returned)
            while (resultSet.next())
            {
                //Return the records per Column Name
                strSelectedFieldsInRecords =
                        (resultSet.getString(strColumnName_1) + " | " + resultSet.getString(strColumnName_2) + " | " +
                                resultSet.getString(strColumnName_3) + " | " + resultSet.getString(strColumnName_4) + " | " +
                                resultSet.getString(strColumnName_5) + " | " + resultSet.getString(strColumnName_6) + " | " +
                                resultSet.getString(strColumnName_7) + " | " + resultSet.getString(strColumnName_8) + " | " +
                                resultSet.getString(strColumnName_9) + " | " + resultSet.getString(strColumnName_10) + " | " +
                                resultSet.getString(strColumnName_11));

                //Store the records returned from the DB in the list row per row.
                results.add(strSelectedFieldsInRecords);
            }

            System.out.println("Number of records found in the database is  :"+results.size());


        }
        catch (Exception e)
        {
            System.out.println("Unable to make connection with DB" +e);
            e.printStackTrace();
            ExtentTestManager.getTest().log(Status.FAIL, "INFORMATION:: Unable to make connection with DB" +e);
            fail("This test has been stopped!");
        }
        finally
        {
            //closing DB Connection
            conn.close();
            resultSet.close();
        }
        return results;
    }

    //*************Flex Database Handler return multiple records********************************************************


    //*************Microsoft Database Handler **************************************************************************
    //Open DB connection
    public static Connection Open(String connectionString)
    {
        //Import microsoft class
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver").newInstance();
            return DriverManager.getConnection(connectionString);
        }
        catch (Exception e)
        {

        }
        return null;
    }

    //Close DB connection
    public static  void Close()
    {
        //
    }

    //Execute DB query
    public static void ExecuteQuery(String query, Connection connection)
    {
        Statement statement = null;
        try
        {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
        }
        catch (Exception e)
        {

        }
    }

    public static void ExecuteStoredProc(String procedureName, Hashtable parameters, Connection connection)
    {
        try {

            int paramterLength = parameters.size();
            String paraAppender = null;
            StringBuilder builder = new StringBuilder();
            // Build the paramters list to be passed in the stored proc
            for (int i = 0; i < parameters.size(); i++) {
                builder.append("?,");
            }

            paraAppender = builder.toString();
            paraAppender = paraAppender.substring(0,
                    paraAppender.length() - 1);

            CallableStatement stmt = connection.prepareCall("{Call "
                    + procedureName + "(" + paraAppender + ")}");

            // Creates Enumeration for getting the keys for the parameters
            Enumeration params = parameters.keys();

            // Iterate in all the Elements till there is no keys
            while (params.hasMoreElements()) {
                // Get the Key from the parameters
                String paramsName = (String) params.nextElement();
                // Set Paramters name and Value
                stmt.setString(paramsName, parameters.get(paramsName)
                        .toString());
            }

            // Execute Query
            stmt.execute();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


    //-------------multiple results--------------awanti -----------

    public static List<String> databaseHandlerForPocDBMultiple(String strSQLquery, String strColumnName_1) throws ClassNotFoundException, SQLException, IllegalAccessException, InstantiationException
    {
        /**DATA BASE PROCESS FOR POC DB*/

        //TODO : Make sure database communication works with the below settings
        String record_1 = null;

        //Connection URL Syntax: "jdbc:mysql://ipaddress:portnumber/db_name"
        String dbUrl = Settings.DBpocUrl;


        //Define what type of driver is needed(get from property file)
        String dbDriver = Settings.DBpocDriverJdbc;

        //Database Username
        String username = Settings.DBpocUserName;

        //Database Password
        String password = Settings.DBpocPassword;

        try
        {
            Class.forName("org.postgresql.Driver");
        }
        catch (ClassNotFoundException e) {
            System.out.println("Postgres JDBC Driver not found !!");
        }

        //Load the type of driver here(mysql/jdbc/postgres)
        //Class.forName("oracle.jdbc.OracleDriver");

        //Test the DB connection before proceeding
        List<String> lst = new ArrayList<String>();
        try{
            Connection con2 = DriverManager.getConnection(dbUrl, username, password);
            System.out.println("INFORMATION:: Database Connected successfully");

            //Query to Execute
            String sqlQuery = strSQLquery;

            //Create Statement Object
            Statement stmt = con2.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

            //Execute the SQL Query. Store results in ResultSet
            ResultSet rs = stmt.executeQuery(sqlQuery);
            Thread.sleep(3000);

            ResultSet res = stmt.executeQuery(sqlQuery);
            res.last();
            int noOfRowsInResult = res.getRow();
            System.out.println(noOfRowsInResult);

            do {
                    lst.add(res.getString(strColumnName_1));
                    System.out.print(res.getString(strColumnName_1));
            }
            while (res.previous());

            //closing DB Connection
            con2.close();
        }catch (Exception e){
            System.out.println("Unable to make connection with DB" +e);
            e.printStackTrace();
            ExtentTestManager.getTest().log(Status.FAIL, "INFORMATION:: Unable to make connection with DB" +e);
            fail("This test has been stopped!");
        }

        //Return the records from the Database
        return lst;
    }





    //******************************************************************************************************************
}
