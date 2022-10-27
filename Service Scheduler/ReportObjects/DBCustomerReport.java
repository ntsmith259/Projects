/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ReportObjects;

import DBAccess.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


/**Performs database queries for the CustomerReport class.  */
public class DBCustomerReport {
    /**Queries the database and creates a list of report objects which count the number of appointments
     * between each customer and contact combination.
     * @return a list of allCustomerReport objects
     */
    public static ObservableList<CustomerReport> getAllCustomerReports(){
        ObservableList<CustomerReport> allCustomerReports = FXCollections.observableArrayList();

        try{
            String sql = "select o.Technician_ID, Technician_Name, customer_Name, count(*) as appnt  from technicians o inner join\n" +
                    "appointments a on o.Technician_ID= a.Technician_ID inner join customers c on a.Customer_ID = c.Customer_ID \n" +
                    "group by Technician_Name, Customer_Name order by Technician_Name, customer_Name";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                int techID = rs.getInt(1);
                String techName = rs.getString(2);
                String customerName = rs.getString(3);
                int appointmentNum = rs.getInt(4);
                CustomerReport c = new CustomerReport(techID, techName, customerName, appointmentNum );
                allCustomerReports.add(c);
            }
        }
        catch(SQLException throwables){
            throwables.printStackTrace();
        }
        return allCustomerReports;
    }

}
