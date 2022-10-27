/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DBAccess;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


/** Establishes the database connection
 *
 */
public class DBConnection {

    //JDBC URL

    /**The protocol for the database connection. */
    private static final String protocol = "jdbc";
    /**The vendor name for the database connection. */
    private static final String vendorName = ":mysql:";
    /**The ip address for the database connection. */
    private static final String idAddress = "//localhost:3306/";
    /**The name of the database for the database connection. */
    private static final String dbName = "service_schedule";

    //JDBC URL
    /**The complete JDBC URL. */
    private static final String jdbcURL = protocol + vendorName + idAddress+dbName;

    //Driver and Connection interface reference
    /**The JDBC driver. */
    private static final String MYSQLJDBCDriver = "com.mysql.cj.jdbc.Driver";
    /**The name of the connection */
    private static Connection conn = null;

    /**The database username. */
    private static final String userName = "sqlUser";
    /**The database password. */
    private static final String password = "Passw0rd!";

    /** Starts the database connection.
     * @return the database connection.
     */
    public static Connection startConnection(){
        try{
            Class.forName(MYSQLJDBCDriver);
            conn = DriverManager.getConnection(jdbcURL, userName, password);
            System.out.println("Connection Successful");
        }
        catch(ClassNotFoundException e){
            e.printStackTrace();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return conn;

    }

    /** Getter for the established database connection.
     * @return the database connection.
     */
    public static Connection getConnection(){
        return conn;
    }


    /**Closes the database connection.
     * This method is called when closing the application.
     */
    public static void closeConnection() {

        try{
            conn.close();
            System.out.println("Database Connection Closed");}
        catch(SQLException e){
            // do nothing, exception is not a problem
        }

    }



}
