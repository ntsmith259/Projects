/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**Appointment class model. */
public class Appointment {

    /** formats strings into hh:mm with am or pm times. */
    DateTimeFormatter am = DateTimeFormatter.ofPattern("hh:mm a");

    /**Appointment ID. */
    private int appointmentID;
    /**Appointment Title. */
    private String appointmentTitle;
    /**Appointment Description. */
    private String description;
    /**Appointment Location. */
    private String location;
    /**Appointment Type. */
    private String type;
    /**Start date and time of the appointment. */
    private LocalDateTime start;
    /**End date and time of the appointment. */
    private LocalDateTime end;


    /**Customer object in the allCustomers list.*/
    private Customer customer;
    /**User object in the allUsers list.*/
    private User user;
    /**Technician object in the allContacts list.*/
    private Technician technician;

    /**Constructor for the Appointment object. The constructor converts LocalDateTimes into LocalDates, LocalTimes and
     * uses a DateTimeFormatter to create Strings for the time to better display in the appointment table. */
    public Appointment(int appointmentID, String appointmentTitle, String description, String location, String type, LocalDateTime start, LocalDateTime end,
                        int customerID, int userID, int techID) {
        this.appointmentID = appointmentID;
        this.appointmentTitle = appointmentTitle;
        this.description = description;
        this.location = location;
        this.type = type;
        this.start = start;
        this.end = end;

        for (Customer c: Schedule.getAllCustomers()){
            if (customerID == c.getCustomerID()){
                this.customer = c;}
        }

        for (Technician t: Schedule.getAllTechs()){
            if (techID == t.getTechID()){
                this.technician = t;}
        }

        for (User u: Schedule.getAllUsers()){
            if (userID == u.getUserID()){
                this.user = u;}
        }
    }

    /**Getter method for the appointmentID
     * @return appointmentID
     */
    public int getAppointmentID() {
        return appointmentID;
    }

    /**Setter method for the appointmentID
     * @param appointmentID appointment ID
     */
    public void setAppointmentID(int appointmentID) {
        this.appointmentID = appointmentID;
    }

    /**Getter method for the
     * @return appointmentTitle.
     */
    public String getAppointmentTitle() {
        return appointmentTitle;
    }


    /**Setter method for the appointment title.
     * @param appointmentTitle apointment title
     */
    public void setAppointmentTitle(String appointmentTitle) {
        this.appointmentTitle = appointmentTitle;
    }

    /**Getter method for the appointment description.
     * @return the description.
     */
    public String getDescription() {
        return description;
    }

    /**Setter method for the appointment description.
     * @param description the appointment description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**Getter method for the appointment location.
     * @return the location.
     */
    public String getLocation() {
        return location;
    }

    /**Setter method for the appointment location.
     * @param location the appointment location.
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**Getter method for the appointment type.
     * @return the appointment type.
     */
    public String getType() {
        return type;
    }

    /**Setter method for the appointment type.
     * @param type the appointment type.
     */
    public void setType(String type) {
        this.type = type;
    }

    /**Getter for the LocalDateTime for the start of the appointment.
     * @return appointment start in LocalDateTime.
     */
    public LocalDateTime getStart() {
        return start;
    }

    /**Setter for the start of the meeting.
     * @param start appointment start in LocalDateTime.
     */
    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    /**Getter for the LocalDateTime for the end of the appointment.
     * @return appointment end in LocalDateTime.
     */
    public LocalDateTime getEnd() {
        return end;
    }

    /**Setter for the end of the meeting.
     * @param end appointment start in LocalDateTime.
     */
    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    /**Getter method for customer ID.
     * @return customer ID.
     */
    public int getCustomerID() {
        return customer.getCustomerID();
    }

    /**Getter for user ID.
     * @return use ID
     */
    public int getUserID() {return user.getUserID();    }

    /**Getter for technician ID.
     * @return technician ID
     */
    public int getTechID() {
        return technician.getTechID();
    }

    /**Getter for customer name.
     * @return customer name.
     */
    public String getCustomerName() {
        return customer.getCustomerName();
    }

    /**Getter for User name.
     * @return user name.
     */
    public String getUserName() {
        return user.getUserName();
    }

    /**Getter for technician name.
     * @return technician name.
     */
    public String getTechName() {
        return technician.getTechName();
    }

    /**Getter for the customer.
     * @return customer object.
     */
    public Customer getCustomer() {
        return customer;
    }

    /**Setter for the customer.
     * @param customer customer object.
     */
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    /**Getter for user object.
     * @return user object.
     */
    public User getUser() {
        return user;
    }

    /**Setter for user object.
     * @param user use object.
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**Getter for technician object.
     * @return technician object.
     */
    public Technician getTech() {
        return technician;
    }

    /**Setter for technician object.
     * @param technician technician object.
     */
    public void setTech(Technician technician) {
        this.technician = technician;
    }

    /**Getter for start date.
     * @return start date.
     */
    public LocalDate getStartDate() {
        return start.toLocalDate();
    }

    /**Getter for end date.
     * @return end date.
     */
    public LocalDate getEndDate() {
        return end.toLocalDate();
    }

    /**Getter for a string of the appointment starttime. .
     * @return start time formatted as a string. .
     */
    public String getStartTimeString() {
        return am.format(start.toLocalTime());
    }

    /**Getter for a string of the appointment end time.
     * @return end time formatted as a string.
     */
    public String getEndTimeString() {
        return am.format(end.toLocalTime());
    }

}





