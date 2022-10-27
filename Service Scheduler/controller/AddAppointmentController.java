/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.net.URL;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.ResourceBundle;

import DBAccess.DBAppointment;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import model.Technician;
import model.Customer;
import model.Schedule;
import model.User;
import utils.AppointmentChecker;
import utils.NavigationInterface;
import utils.TimeConverter;

/**FXML Controller class for the AddAppointment.fxml file.
 This screen  is used to add appointments to the C195 Schedule Application.
 */
public class AddAppointmentController implements Initializable, NavigationInterface, AppointmentChecker {

    /**Text field for the user to enter the appointment title. */
    @FXML
    private TextField titleTxt;

    /**Text field for the user to enter the appointment description. */
    @FXML
    private TextField descTxt;

    /**Text field for the user to enter the appointment location. */
    @FXML
    private TextField locTxt;

    /**Text field for the user to enter the appointment type. */
    @FXML
    private TextField typeTxt;

    /**Combo box for customer objects. */
    @FXML
    private ComboBox<Customer> customerCombo;

    /**Combo box for user objects. */
    @FXML
    private ComboBox<User> userCombo;

    /**Combo box for contact objects. */
    @FXML
    private ComboBox<Technician> techCombo;

    /**Date picker to allow the user to select a start date for the meeting. */
    @FXML
    private DatePicker startDatePicker;

    /**Combo box to allow the user to select a start time for the meeting. */
    @FXML
    private ComboBox<String> startTimeCombo;

    /**Date picker to allow the user to select a end date for the meeting. */
    @FXML
    private DatePicker endDatePicker;

    /**Combo box to allow the user to select an end time for the meeting. */
    @FXML
    private ComboBox<String> endTimeCombo;



    /** This method adds an appointment to the schedule. User entered information is checked for nulls, overlapping appointments
      and appointments outside office hours.
     @param event mouse click on Schedule Appointment button.
     */
   @FXML
    void onActionAddAppt(ActionEvent event){
        String checkFields ="";
        String errorMessage ="";

        try{
            String title = titleTxt.getText().trim();
            String type = typeTxt.getText().trim();
            String desc = descTxt.getText().trim();
            String location = locTxt.getText().trim();
            String currentUser = Schedule.getCurrentUser().getUserName();

            // perform the check for nulls
            checkFields = checkAppointmentFields(title, type, desc, location, customerCombo.getValue(), userCombo.getValue(), techCombo.getValue(),
                    startDatePicker.getValue(), startTimeCombo.getValue(), endDatePicker.getValue(), endTimeCombo.getValue());
            if(checkFields.length() > 1){
                errorMessage = "Please complete the following fields:" + checkFields;
                throw new IllegalArgumentException();
            }

            int customerID = customerCombo.getValue().getCustomerID();
            int userID = userCombo.getValue().getUserID();
            int techID = techCombo.getValue().getTechID();

            DateTimeFormatter timeStringDTF = DateTimeFormatter.ofPattern("hh:mm a");
            LocalDate startDate = startDatePicker.getValue();
            LocalTime startTime = LocalTime.parse(startTimeCombo.getValue(),timeStringDTF) ;
            LocalDate endDate = endDatePicker.getValue();
            LocalTime endTime = LocalTime.parse(endTimeCombo.getValue(), timeStringDTF);

            LocalDateTime startLDT = LocalDateTime.of(startDate, startTime);
            LocalDateTime endLDT = LocalDateTime.of(endDate, endTime);
            // perform the check for appointment conflicts
            checkFields = checkAppointmentTimes(startLDT, endLDT, customerID, -1);
            if(checkFields.length() > 1){
                errorMessage = checkFields;
                throw new IllegalArgumentException();
            }
            // add appointment to the database and allAppointments list
            DBAppointment.addAppointmentRecord(title,desc, location, type, startLDT, endLDT, currentUser,customerID, userID, techID);
            nextScreen("/view/Appointments.fxml", event);

        }catch (IllegalArgumentException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Warning");
            alert.setContentText(errorMessage);
            alert.showAndWait();
        }catch (Exception e){
            System.out.println(Arrays.toString(e.getStackTrace()));
        }

    }

    /**Returns to the previous screen without saving changes.
     * @param event mouse click on back button.
     * @throws IOException when fxml is not found
      */
    @FXML
    void onActionBack(ActionEvent event) throws IOException {
        nextScreen("/view/Appointments.fxml", event);

    }

    /**Initializes the AddAppointmentController class. The combo boxes that appear on the form are populated using the
     * appropriate lists. */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        userCombo.setItems(Schedule.getAllUsers());
        techCombo.setItems(Schedule.getAllTechs());
        customerCombo.setItems(Schedule.getAllCustomers());
        startTimeCombo.setItems(TimeConverter.getTimeList());
        endTimeCombo.setItems(TimeConverter.getTimeList());
    }

}
