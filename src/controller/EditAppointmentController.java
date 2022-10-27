package controller;

import DBAccess.DBAppointment;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import model.*;
import utils.AppointmentChecker;
import utils.NavigationInterface;
import utils.TimeConverter;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

/**FXML Controller class for the EditAppointment.fxml file.
 This screen  is used to edit appointments for the C195 Schedule Application.
 */
public class EditAppointmentController implements Initializable, NavigationInterface, AppointmentChecker {
    /**Disabled text field to display appointment ID. */
    @FXML
    public TextField appointmentIDTxt;
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

    /**Sets the fields on the EditAppointmentController with the values from the selected appointment.
     * Called by the AppointmentController.onActionEditAppt().
     * @param appointment the selected appointment from the Appointments screen.
     */
    public void sendAppointment(Appointment appointment){
        appointmentIDTxt.setText(String.valueOf(appointment.getAppointmentID()));
        titleTxt.setText(String.valueOf(appointment.getAppointmentTitle()));
        descTxt.setText(String.valueOf(appointment.getDescription()));
        locTxt.setText(String.valueOf(appointment.getLocation()));
        typeTxt.setText(String.valueOf(appointment.getType()));
        customerCombo.setValue(appointment.getCustomer());
        userCombo.setValue(appointment.getUser());
        techCombo.setValue(appointment.getTech());
        startDatePicker.setValue(appointment.getStartDate());
        endDatePicker.setValue(appointment.getEndDate());
        startTimeCombo.setValue(appointment.getStartTimeString());
        endTimeCombo.setValue(appointment.getEndTimeString());
    }

    /**Gets the index of the appointment in the allAppointments list. The values returned by this method is used
     * to determine which appointment to update in the allAppointments list after editing an appointment.
     * @param id appointment ID
     * @return index of the appointment in the allAppointments list.
     */
    private int getIndex(int id) {
        int index = -1;
        for (Appointment a  : Schedule.getAllAppointments()) {
            index++;
            if (a.getAppointmentID() == id) {
                return index;
            }
        }
        return -1;
    }


    /**Edits an appointment. User entered information is checked for nulls, overlapping appointments
     and appointments outside office hours.
     @param event mouse click on Schedule Appointment button.  */
    @FXML
    void onActionEditAppt(ActionEvent event) {
        String checkFields ="";
        String errorMessage ="";

        try{
            int appointmentID = Integer.parseInt(appointmentIDTxt.getText().trim());
            String title = titleTxt.getText().trim();
            String type = typeTxt.getText().trim();
            String desc = descTxt.getText().trim();
            String location = locTxt.getText().trim();
            String currentUser = Schedule.getCurrentUser().getUserName();

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


            checkFields = checkAppointmentTimes(startLDT, endLDT, customerID, appointmentID);
            if(checkFields.length() > 1){
                errorMessage = checkFields;
                throw new IllegalArgumentException();
            }

            DBAppointment.editAppointmentRecord(appointmentID, title,desc, location, type, startLDT, endLDT, currentUser,customerID, userID, techID);
            int index = getIndex(appointmentID);
            Schedule.updateAppointment(index, DBAppointment.getUpdatedAppointment(appointmentID));
            nextScreen("/view/Appointments.fxml", event);

        }catch (IllegalArgumentException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Warning");
            alert.setContentText(errorMessage);
            alert.showAndWait();
        }catch (Exception e){
            System.out.println(e.getStackTrace());
        }

    }

    @FXML
    void onActionSelectTech(ActionEvent event) {
    }

    @FXML
    void onActionSelectCustomer(ActionEvent event) {
    }

    @FXML
    void onActionSelectUser(ActionEvent event) {
    }


    /**Returns to the previous screen.
     * @param event mouse click on back button
     * @throws IOException if fxml file not found
     */
    @FXML
    void onActionBack(ActionEvent event) throws IOException {
        nextScreen("/view/Appointments.fxml", event);
    }

    /**Initializes the EditAppointment controller class. Populates the combo boxes on the form. */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        userCombo.setItems(Schedule.getAllUsers());
        techCombo.setItems(Schedule.getAllTechs());
        customerCombo.setItems(Schedule.getAllCustomers());
        startTimeCombo.setItems(TimeConverter.getTimeList());
        endTimeCombo.setItems(TimeConverter.getTimeList());
    }

}
