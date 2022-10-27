/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DBAccess;

import java.sql.*;
import java.time.LocalDateTime;
import static java.time.LocalDateTime.now;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customer;

import model.Schedule;
import utils.TimeConverter;

/**Uses the database connection to perform CRUD actions in the database for the customers table.
  * @author ntsmi
 */
public class DBCustomer {


    /**Queries the appointments table and populates a list of customers when the program is launched.
     * @return the list of customers
     */
    public static ObservableList<Customer> getAllCustomers() {
        ObservableList<Customer> allCustomers = FXCollections.observableArrayList();

        try {
            String sql = "SELECT Customer_ID, Customer_Name, Address, Postal_Code, Phone, c.Create_Date, c.Created_By, c.Last_Update, c.Last_Updated_By, "
                    + "c.Division_ID, Country_ID from customers c  JOIN first_level_divisions d ON c.Division_ID=d.Division_ID";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int customer_ID = rs.getInt("Customer_ID");
                String name = rs.getString("Customer_Name");
                String address = rs.getString("Address");
                String postalCode = rs.getString("Postal_Code");
                String phone = rs.getString("Phone");
                LocalDateTime createDate = TimeConverter.utcToLocal(rs.getTimestamp("Create_Date"));
                String createdBy = rs.getString("Created_By");
                LocalDateTime lastUpdate = TimeConverter.utcToLocal(rs.getTimestamp("Last_Update"));
                String lastUpdateBy = rs.getString("Last_Updated_By");
                int divisionID = rs.getInt("Division_ID");
                int countryID = rs.getInt("Country_ID");
                Customer customer = new Customer(customer_ID, name, address, postalCode, phone,  createdBy, lastUpdateBy, divisionID, countryID);
                allCustomers.add(customer);
            }

        } catch (SQLException e) {
            e.printStackTrace();        }

        return allCustomers;
    }

    // returns a customer that is used to update the allCustomers list after updating a customer
    public static Customer getUpdatedCustomer(int id){
        try {
            String sql = "SELECT Customer_ID, Customer_Name, Address, Postal_Code, Phone, c.Create_Date, c.Created_By, c.Last_Update, c.Last_Updated_By, "
                    + "c.Division_ID, Country_ID from customers c  JOIN first_level_divisions d ON c.Division_ID=d.Division_ID WHERE Customer_ID=?";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

             rs.next();
             int customer_ID = rs.getInt("Customer_ID");
             String name = rs.getString("Customer_Name");
             String address = rs.getString("Address");
             String postalCode = rs.getString("Postal_Code");
             String phone = rs.getString("Phone");
             String createdBy = rs.getString("Created_By");
             String lastUpdateBy = rs.getString("Last_Updated_By");
             int divisionID = rs.getInt("Division_ID");
             int countryID = rs.getInt("Country_ID");
             Customer customer = new Customer(customer_ID, name, address, postalCode, phone,  createdBy,  lastUpdateBy, divisionID, countryID);
             return customer;


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // used by the AddCustomerController to add a customer to the database
    public static void addCustomerRecord(String name, String address, String postalCode, String phoneNumber, String currentUser,
                                   int divisionID, int countryID) throws SQLException {

        String sql = "INSERT INTO customers VALUES ( NULL, ?, ? , ? , ? , ? , ? , ? , ? , ? )";
        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

        LocalDateTime createDT = LocalDateTime.now();
        Timestamp createTS = TimeConverter.localToUTC(createDT);

        ps.setString(1, name);
        ps.setString(2, address);
        ps.setString(3, postalCode);
        ps.setString(4, phoneNumber);
        ps.setTimestamp(5, createTS);
        ps.setString(6, currentUser);
        ps.setTimestamp(7, createTS);
        ps.setString(8, currentUser);
        ps.setInt(9, divisionID);
        ps.execute();

        ResultSet rs = ps.getGeneratedKeys();
        rs.next();
        int customerID = rs.getInt(1);
        Customer customer = new Customer(customerID, name, address, postalCode, phoneNumber,  currentUser,  currentUser, divisionID, countryID);
        Schedule.addCustomer(customer);

    }

    // used by the EditCustomerController to edit a customer in the databsae
    public static void editCustomerRecord(int customerID, String name, String address, String postalCode, String phoneNumber,
                                          String currentUser, int divisionID, int countryID) throws SQLException {
        String sql = "UPDATE customers SET Customer_Name =?, Address = ?, Postal_Code = ?,"
                + "Phone = ?, Last_Update = ?, Last_Updated_By = ?, Division_ID = ? WHERE Customer_ID = ? "  ;
        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

        LocalDateTime lastUpdateLDT = LocalDateTime.now();
        Timestamp lastUpdateTS = TimeConverter.localToUTC(lastUpdateLDT);

        ps.setString(1, name);
        ps.setString(2, address);
        ps.setString(3, postalCode);
        ps.setString(4, phoneNumber);
        ps.setTimestamp(5, lastUpdateTS);
        ps.setString(6, currentUser);
        ps.setInt(7, divisionID);
        ps.setInt(8, customerID);
        ps.executeUpdate();

    }

    public static boolean deleteCustomer(Customer selectedCustomer) {
        try {
            int customerID = selectedCustomer.getCustomerID();

            String custSQL = "DELETE FROM customers WHERE Customer_ID = ?";
            PreparedStatement ps2 = DBConnection.getConnection().prepareStatement(custSQL);
            ps2.setInt(1, customerID);
            ps2.execute();

        } catch (SQLException e){
            System.out.println(e.getStackTrace());
        }
        for (Customer customer : Schedule.getAllCustomers()) {
            if ((customer.getCustomerID()) == (selectedCustomer.getCustomerID())) {
                Schedule.getAllCustomers().remove(customer);
                return true;
            }
        }
        return false;
    }


    public DBCustomer() {
    }
}
