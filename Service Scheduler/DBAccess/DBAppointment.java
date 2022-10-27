/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DBAccess;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointment;
import model.Customer;
import model.Schedule;
import model.User;
import utils.TimeConverter;

import java.sql.*;
import java.time.LocalDateTime;

/**Uses the database connection to perform CRUD actions in the database for the appointments table.
 * @author ntsmi
 */
public class  DBAppointment {

    /** Queries the appointments table and populates a list of appointments when the program is launched.
     * @return a list of all appointments.
     */
    public static ObservableList<Appointment> getAllAppointments(){

        ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();
        try{
            String sql = "SELECT Appointment_ID, Title, Description, Location, Type, Start, End, Create_Date, Created_By, Last_Update, Last_Updated_By, "
                    + "Customer_ID, User_ID, Technician_ID FROM appointments";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                int appointmentID = rs.getInt("Appointment_ID");
                String title =rs.getString("Title");
                String desc = rs.getString("Description");
                String loc = rs.getString("Location");
                String type = rs.getString("Type");
                LocalDateTime startLDT = TimeConverter.utcToLocal(rs.getTimestamp("Start"));
                LocalDateTime endLDT = TimeConverter.utcToLocal(rs.getTimestamp("End"));
                int customerID = rs.getInt("Customer_ID");
                int userID = rs.getInt("User_ID");
                int techID = rs.getInt("Technician_ID");
                Appointment appointment = new Appointment(appointmentID, title, desc, loc, type, startLDT, endLDT,
                        customerID, userID, techID);
                allAppointments.add(appointment);
            }

        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return allAppointments;
    }

    /** Returns an appointment object that was recently added to the database to update the allAppointments list.
     * Used by the EditAppointmentController.
     * @param id the appointmentID
     * @return the updated appointment
     */
    // returns a appointment that is used to update the allAppointments list
    public static Appointment getUpdatedAppointment(int id){
        try {
            String sql = "SELECT Appointment_ID, Title, Description, Location, Type, Start, End, Create_Date, Created_By, Last_Update, Last_Updated_By, "
                    + "Customer_ID, User_ID, Technician_ID FROM appointments WHERE Appointment_ID = ?";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            rs.next();
            int appointmentID = rs.getInt("Appointment_ID");
            String title =rs.getString("Title");
            String desc = rs.getString("Description");
            String loc = rs.getString("Location");
            String type = rs.getString("Type");
            LocalDateTime startLDT = TimeConverter.utcToLocal(rs.getTimestamp("Start"));
            LocalDateTime endLDT = TimeConverter.utcToLocal(rs.getTimestamp("End"));
            int customerID = rs.getInt("Customer_ID");
            int userID = rs.getInt("User_ID");
            int techID = rs.getInt("Technician_ID");
            Appointment appointment = new Appointment(appointmentID, title, desc, loc, type, startLDT, endLDT, customerID, userID, techID);
            return appointment;


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /** Adds an appointment to the database and then creates a new appointment in the allAppointments list.
     * The appointment is added to the database first to autogenerate the appointmentID before adding
     * the appointment to the allAppointmentsList.
     * @param title appointment title
     * @param desc appointment description
     * @param location appointment location
     * @param type appointment type
     * @param startLDT LocalDateTime for the start of the appointment
     * @param endLDT LocalDateTime for the end of the appointment
     * @param currentUser the used logged into the system
     * @param customerID customer ID for the appointment
     * @param userID user customer ID for the appointment
     * @param tech ID tech ID for the appointment
     * @throws SQLException
     */
    public static void addAppointmentRecord(String title, String desc, String location, String type, LocalDateTime startLDT, LocalDateTime endLDT,  String currentUser,
                                            int customerID, int userID, int techID) throws SQLException {
        String sql = "INSERT INTO appointments VALUES ( NULL, ?, ? , ? , ? , ? , ? , ? , ? , ?, ?, ?, ?, ? )";
        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

        Timestamp startTS = TimeConverter.localToUTC(startLDT);
        Timestamp endTS = TimeConverter.localToUTC(endLDT);
        Timestamp createTS = TimeConverter.localToUTC(LocalDateTime.now());

        ps.setString(1, title);
        ps.setString(2, desc);
        ps.setString(3, location);
        ps.setString(4, type);
        ps.setTimestamp(5, startTS);
        ps.setTimestamp(6, endTS);
        ps.setTimestamp(7, createTS);
        ps.setString(8, currentUser);
        ps.setTimestamp(9, createTS);
        ps.setString(10, currentUser);
        ps.setInt(11, customerID);
        ps.setInt(12, userID);
        ps.setInt(13, techID);
        ps.execute();

        ResultSet rs = ps.getGeneratedKeys();
        rs.next();
        int appointmentID = rs.getInt(1);

            /*update the appointment list with the newly created appointment from the database.
            I chose to update the list afterwards so there is an SQLException it will be thrown before adding to the list*/
        Appointment appointment =  new Appointment(appointmentID, title, desc,location, type, startLDT, endLDT,
                  customerID, userID, techID);
        Schedule.addAppointment(appointment);
    }

    /** Edits the appointment in the database.
     * @param title appointment title
     * @param desc appointment description
     * @param location appointment location
     * @param type appointment type
     * @param startLDT LocalDateTime for the start of the appointment
     * @param endLDT LocalDateTime for the end of the appointment
     * @param currentUser the used logged into the system
     * @param customerID customer ID for the appointment
     * @param userID user customer ID for the appointment
     * @param techID tech ID for the appointment
     * @throws SQLException
     */
    public static void editAppointmentRecord(int appointmentID, String title, String desc, String location, String type, LocalDateTime startLDT, LocalDateTime endLDT,
                                                String currentUser, int customerID, int userID, int techID) throws SQLException  {

        String sql = "UPDATE appointments SET Title =?, Description = ?, Location = ?,"
                + "Type = ?, Last_Update = ?, Last_Updated_By = ?, Customer_ID=?, User_ID= ?, Technician_ID = ?, Start = ?, End = ? WHERE Appointment_ID = ? "  ;
        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

        LocalDateTime lastUpdateLDT = LocalDateTime.now();
        Timestamp lastUpdateTS = TimeConverter.localToUTC(lastUpdateLDT);
        Timestamp startTS = TimeConverter.localToUTC(startLDT);
        Timestamp endTS = TimeConverter.localToUTC(endLDT);

        ps.setString(1, title);
        ps.setString(2, desc);
        ps.setString(3, location);
        ps.setString(4, type);
        ps.setTimestamp(5, lastUpdateTS);
        ps.setString(6, currentUser);
        ps.setInt(7, customerID);
        ps.setInt(8, userID);
        ps.setInt(9, techID);
        ps.setTimestamp(10, startTS);
        ps.setTimestamp(11, endTS);
        ps.setInt(12, appointmentID);
        ps.executeUpdate();

    }

    /**Deletes all appointments for a selected customer from the database and from the allAppointments list.
     * Only the appointments are deleted during this step due to FK constraints.
     * @param delCustomer the selected customer to delete.
     * @return true if the appointments for the customer were successfully deleted.
     */
    public static boolean deleteCustomerDBAppointments(Customer delCustomer){
        try {
            int customerID = delCustomer.getCustomerID();

            String apptSQL = "DELETE FROM appointments WHERE Customer_ID = ?";
            PreparedStatement ps1 = DBConnection.getConnection().prepareStatement(apptSQL);
            ps1.setInt(1, customerID);
            ps1.execute();

        } catch (SQLException e){
            System.out.println(e.getStackTrace());
        }

        for (Appointment appointment: Schedule.getAllAppointments()){
            if(appointment.getCustomerID() == (delCustomer.getCustomerID())){
                Schedule.getAllAppointments().remove(appointment);
                return true;
            }
        }
        return false;

    }

    /**Deletes a selected appointment.
     * @param selectedAppointment the appointment to be deleted
     * @return true if the appointment was successfully deleted.
     */
    public static boolean deleteAppointment(Appointment selectedAppointment){
        try{
            int apptID = selectedAppointment.getAppointmentID();
            String apptSQL = "DELETE FROM appointments WHERE Appointment_ID = ?";
            PreparedStatement ps1 = DBConnection.getConnection().prepareStatement(apptSQL);
            ps1.setInt(1, apptID);
            ps1.execute();
        } catch (SQLException e){
            System.out.println(e.getStackTrace());
        }

        for (Appointment appointment: Schedule.getAllAppointments()){
            if(appointment.getAppointmentID() == (selectedAppointment.getAppointmentID())){
                Schedule.getAllAppointments().remove(appointment);
                return true;
            }
        }
        return false;

    }







}
