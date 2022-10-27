/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.ZoneId;
import java.util.ResourceBundle;

/**Schedule class model. Contain lists of the other objects, zoneID, and resource bundles.  */
public class Schedule {

    /**the current user. */
    private static User currentUser;
    /** the complete list of customers. */
    private static ObservableList<Customer> allCustomers = FXCollections.observableArrayList();
    /** the complete list of users. */
    private static ObservableList<User> allUsers = FXCollections.observableArrayList();
    /** the complete list of Techs. */
    private static ObservableList<Technician> allTechnicians = FXCollections.observableArrayList();
    /** the complete list of countries. */
    private static ObservableList<Country> allCountries = FXCollections.observableArrayList();
    /** the complete list of appointments. */
    private static ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();
    /** the resource bundle based on default language. */
    private static ResourceBundle resourceBundle;

    /**The system default zone ID of the user. */
    private static ZoneId userZoneId;
    /**The server zone ID set to UTC. */
    private static ZoneId serverZoneId;
    /**The company zone ID set to America/New_York */
    private static ZoneId companyZoneId;

    /**Constructor for the Schedule Class. */
    public Schedule() {
    }

    /**Adds a customer to the allCustomers list.
     * @param newCustomer the new customer
     */
    public static void addCustomer(Customer newCustomer) {
        allCustomers.add(newCustomer);
    }

    /**Updates a customer in the allCustomers list.
     * @param index of the customer.
     * @param selectedCustomer the customer object
     */
    public static void updateCustomer(int index, Customer selectedCustomer) {
        getAllCustomers().set(index, selectedCustomer);
    }

    /**Getter method the allCustomers list. Used to populate tables and combo boxes.
     * @return the allCustomers list.
     */
    public static ObservableList<Customer> getAllCustomers() {
        return allCustomers;
    }

    /**Setter method for the allCustomers list.
     * @param allCustomers the list of customers from the DBCustomers class.
     */
    public static void setAllCustomers(ObservableList<Customer> allCustomers) {
        Schedule.allCustomers = allCustomers;
    }

    /**Adds an appointment to the allAppointments list.
     * @param newAppointment the new appointment.
     */
    public static void addAppointment(Appointment newAppointment) {
        allAppointments.add(newAppointment);
    }
    /**Updates an appointment in the allCustomers list.
     * @param index of the appointment.
     * @param selectedAppointment the customer appointment.
     */
    public static void updateAppointment(int index, Appointment selectedAppointment) {
        getAllAppointments().set(index, selectedAppointment); // set method takes in index of the item in the list and the item

    }

    /**Getter method for the allAppointments list.
     * @return the allAppointments list.
     */
    public static ObservableList<Appointment> getAllAppointments() {
        return allAppointments;
    }
    /**Setter method for the allAppointments list.
     * @param allAppointments the list of appointments from the DBAppointments class.
     */
    public static void setAllAppointments(ObservableList<Appointment> allAppointments) {
        Schedule.allAppointments = allAppointments;
    }

    /**Getter method for the allUsers list.
     * @return the allUsers list.
     */
    public static ObservableList<User> getAllUsers() {
        return allUsers;
    }

    /**Setter method for allUsers list.
     * @param allUsers the list of users from the DBUsers class.
     */
    public static void setAllUsers(ObservableList<User> allUsers) {
        Schedule.allUsers = allUsers;
    }

    /**Getter for allTechnicians list.
     * @return the allTechnicians list.
     */
    public static ObservableList<Technician> getAllTechs() {
        return allTechnicians;
    }

    /**Setter method for allTechnicians list.
     * @param allTechnicians the list of contacts from the DBContacts class.
     */
    public static void setAllTechs(ObservableList<Technician> allTechnicians) {
        Schedule.allTechnicians = allTechnicians;
    }

    /**Getter method for the currentUser.
     * @return the current user.
     */
    public static User getCurrentUser() {
        return currentUser;
    }

    /**Setter method for the currentUser.
     * @param currentUser the use who logged into the system.
     */
    public static void setCurrentUser(User currentUser) {
        Schedule.currentUser = currentUser;
    }

    /**Getter for allCountries list.
     * @return the allCountries list.
     */
    public static ObservableList<Country> getAllCountries() {
        return allCountries;
    }

    /**Setter method for allCountries list.
     * @param allCountries the list of countries from the DBCounties class.
     */
    public static void setAllCountries(ObservableList<Country> allCountries) {
        Schedule.allCountries = allCountries;
    }

    /**Getter for the userZoneID.
     * @return users Zone ID.
     */
    public static ZoneId getUserZoneId() {
        return userZoneId;
    }

    /**Setter for userZone ID
     * @param userZoneId the system default zone.
     */
    public static void setUserZoneId(ZoneId userZoneId) {
        Schedule.userZoneId = userZoneId;
    }

    /**Getter for server zone ID.
     * @return server zone ID. Coded as UTC.
     */
    public static ZoneId getServerZoneId() {
        return serverZoneId;
    }

    /**Setter for server zone ID.
     * @param serverZoneId the server zone ID. Set to UTC.
     */
    public static void setServerZoneId(ZoneId serverZoneId) {
        Schedule.serverZoneId = serverZoneId;
    }

    /**Getter for company zone ID.
     * @return the company zone ID. Set as America/New York.
     */
    public static ZoneId getCompanyZoneId() {
        return companyZoneId;
    }

    /**Setter for company zone ID.
     * @param companyZoneId set to America/New York.
     */
    public static void setCompanyZoneId(ZoneId companyZoneId) {
        Schedule.companyZoneId = companyZoneId;
    }

    /**Getter for the resource bundle.
     * @return resource bundle for the current language. Used to translate the Login form.
     */
    public static ResourceBundle getResourceBundle() {
        return resourceBundle;
    }

    /**Setter for the resource bundle.
     * @param resourceBundle resource bundle determined by the system default language.
     */
    public static void setResourceBundle(ResourceBundle resourceBundle) {
        Schedule.resourceBundle = resourceBundle;
    }
}
