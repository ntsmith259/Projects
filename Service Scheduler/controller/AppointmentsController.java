/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.Optional;
import java.util.ResourceBundle;

import DBAccess.DBAppointment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointment;
import model.Schedule;
import utils.NavigationInterface;

/**FXML Controller class for the Appointments.fxml file.
 It displays all appointments in a table form and can filter by week/month.
 @author ntsmi
 */
public class AppointmentsController implements Initializable, NavigationInterface {

    /**Label that indicates if all appointment, week or month appointments are displayed. */
    @FXML
    private Label AppointmentLabel;
    /**The appointment table. */
    @FXML
    private TableView<Appointment> ApptTableView;
    /**The appointmentID column of the appointment table. */
    @FXML
    private TableColumn<Appointment, Integer> apptIDCol;
    /**The appointment title column of the appointment table. */
    @FXML
    private TableColumn<Appointment, String> apptTitleCol;
    /**The appointment description column of the appointment table. */
    @FXML
    private TableColumn<Appointment, String> apptDecsCol;
    /**The appointment location column of the appointment table. */
    @FXML
    private TableColumn<Appointment, String> apptLocCol;
    /**The appointment type column of the appointment table. */
    @FXML
    private TableColumn<Appointment, String> apptTypeCol;
    /**The appointment start date column of the appointment table. */
    @FXML
    private TableColumn<Appointment, LocalDate> startDateCol;
    /**The appointment start time column of the appointment table. */
    @FXML
    private TableColumn<Appointment, String> startTimeCol;
    /**The appointment end date column of the appointment table. */
    @FXML
    private TableColumn<Appointment, LocalDate> endDateCol;
    /**The appointment end time column of the appointment table. */
    @FXML
    private TableColumn<Appointment, String> endTimeCol;
    /**The tech of the appointment table. */
    @FXML
    private TableColumn<Appointment, String> techCol;
    /**The customer ID of the appointment table. */
    @FXML
    private TableColumn<Appointment, Integer> customerIDCol1;
    /**The customer name of the appointment table. */
    @FXML
    private TableColumn<Appointment, String> customerCol;
    /**The user ID of the appointment table. */
    @FXML
    private TableColumn<Appointment, Integer> userCol;

    // Radio Buttons and Toggle Group for filtering appointsments
    /**Toggle group of the appointment filtering radio buttons. */
    @FXML
    private ToggleGroup appointmentFilter;
    /**Displays all appointments. */
    @FXML
    private RadioButton allApptsRBtn;
    /**Displays appointments for the month. */
    @FXML
    private RadioButton monthApptsRBtn;
    /**Displays appointments for the week. */
    @FXML
    private RadioButton weekApptsRBtn;

    /**Displays all appointments. This is the default setting.
     * @param event select allApptsRBtn
     */
    @FXML
    void onActionAllAppts(ActionEvent event) {
        populateAppointments(Schedule.getAllAppointments());
        AppointmentLabel.setText("Appointments");
    }

    /**Filters appointments for the current month.
     * @param event select monthApptsRBtn
     */
    @FXML
    void onActionMonthAppts(ActionEvent event) {
        ObservableList<Appointment> apptsThisMonth = FXCollections.observableArrayList();
        Month thisMonth = LocalDateTime.now().getMonth();

        for (Appointment appt: Schedule.getAllAppointments()){
            if ( (appt.getStart().getMonth().equals(thisMonth))  ){
                apptsThisMonth.add(appt);
            }
        }
        populateAppointments(apptsThisMonth);
        AppointmentLabel.setText("Appointments this Month");
    }
    /**Filters appointments for the current week which is set as the current day plus 1 week.
     * @param event select weekApptsRBtn
     */
    @FXML
    void onActionWeekAppts(ActionEvent event) {
        ObservableList<Appointment> apptsByWeek = FXCollections.observableArrayList();
        LocalDateTime plusWeekLDT = LocalDateTime.now().plusWeeks(1);
        LocalDateTime today = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);

        for (Appointment appt: Schedule.getAllAppointments()){
            if ( (appt.getStart().isBefore(plusWeekLDT)) && appt.getStart().isAfter(today)){
                apptsByWeek.add(appt);
            }
        }
        populateAppointments(apptsByWeek);
        AppointmentLabel.setText("Appointments this Week");
    }

    /**Sets the AppointmentsTable and its columns to the desired filtered list of appointments.
     * The onActionAllAppts(), onActionMonthAppts() and onActionWeekAppts() call this method to set the
     * appointments table according to the filtered appointments list inside each method.
     * @param appointmentList a filtered appointment list based on the user selected filter.
     */
    public void populateAppointments(ObservableList<Appointment> appointmentList){
        ApptTableView.setItems(appointmentList);
        apptIDCol.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        apptTitleCol.setCellValueFactory(new PropertyValueFactory<>("appointmentTitle"));
        apptDecsCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        apptLocCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        apptTypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        startDateCol.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        startTimeCol.setCellValueFactory(new PropertyValueFactory<>("startTimeString"));
        endDateCol.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        endTimeCol.setCellValueFactory(new PropertyValueFactory<>("endTimeString"));
        userCol.setCellValueFactory(new PropertyValueFactory<>("userID"));
        techCol.setCellValueFactory(new PropertyValueFactory<>("techName"));
        customerCol.setCellValueFactory(new PropertyValueFactory<>("CustomerName"));
        customerIDCol1.setCellValueFactory(new PropertyValueFactory<>("customerID"));
    }

    /** Loads the AddAppointment screen
     * @param event mouse click on the Add Appointment button.
     * @throws IOException
     */
    @FXML
    void onActionAddAppt(ActionEvent event) throws IOException {
        nextScreen("/view/AddAppointment.fxml", event);
    }

    /**Deletes an appointment from the database.
     * The method first checks to make sure an appointment is selected. The user then must confirm the deletion and the
     * appointment is then based to the DBAppointment class to complete the deletion.
     * @param event mouse click on the delete AppointmentButton
     */
    @FXML
    void onActionDeleteAppt(ActionEvent event) {

        try {
            Appointment delAppt = ApptTableView.getSelectionModel().getSelectedItem();
            if (delAppt == null)
                throw new NullPointerException();

            String apptInfo =   "Appointment ID: " + delAppt.getAppointmentID() +
                               "\n" +"Type: " + delAppt.getType() +
                               "\n" + "Title: " + delAppt.getAppointmentTitle();

            // confirm delete
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "The following appointment will be cancelled. Do you want to continue?" +"\n" +"\n"+apptInfo);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                DBAppointment.deleteAppointment(delAppt);
                Alert inform = new Alert(Alert.AlertType.INFORMATION,  "The following appointment has been cancelled." +"\n" +"\n"+ apptInfo );
                inform.showAndWait();
            }

        }catch (NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No Appointment Selected.");
            alert.setContentText("Please an appointment to cancel.");
            alert.showAndWait();
        }

    }

    /**Loads the EditAppointment screen populated with selected appointment information.
     * The editAppointment.sendAppointment() method is called for the selected appointment from the appointments table.
     * @param event mouse click on Edit appointment button
     * @throws IOException is thrown if EditAppointment.fxml is not found.
     */
    @FXML
    void onActionEditAppt(ActionEvent event) throws IOException {

        try {
            FXMLLoader loader = new FXMLLoader(); // creates object
            loader.setLocation(getClass().getResource("/view/EditAppointment.fxml")); // lets its know which view to use
            loader.load();
            EditAppointmentController editAppointment = loader.getController();
            editAppointment.sendAppointment(ApptTableView.getSelectionModel().getSelectedItem());

            Stage stage;
            //Parent scene;
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show(); // anything after this code will not display


        } catch (NullPointerException | IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No Appointment Selected.");
            alert.setContentText("Please an appointment to edit.");
            alert.showAndWait();
        }


    }

    /**Returns to the previous screen. */
    @FXML
    void onActionMainMenu(ActionEvent event) throws IOException {
        nextScreen("/view/MainMenu.fxml", event);
    }

    /**Initializes the AppointmentsController class.
     * The populateAppointment method is called to populate the appointments table with allAppointments list.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        populateAppointments(Schedule.getAllAppointments());
        AppointmentLabel.setText("Appointments");
    }
}
