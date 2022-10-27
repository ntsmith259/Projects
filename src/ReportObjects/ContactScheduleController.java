/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ReportObjects;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Appointment;
import model.Technician;
import model.Schedule;
import utils.NavigationInterface;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

/**FXML Controller class for the ContactSchedule report. */
public class ContactScheduleController implements Initializable, NavigationInterface {

    /**Table view for the Technician Schedule report. */
    @FXML
    private TableView<Appointment> ApptTableView;
    /**Appointment ID column in the ApptTableView for the Technician Schedule report. */
    @FXML
    private TableColumn<Appointment, Integer> apptIDCol;
    /**Appointment title column in the ApptTableView for the Technician Schedule report. */
    @FXML
    private TableColumn<Appointment, String> apptTitleCol;
    /**Appointment description column in the ApptTableView for the Technician Schedule report. */
    @FXML
    private TableColumn<Appointment, String> apptDecsCol;
    /**Appointment location column in the ApptTableView for the Technician Schedule report. */
    @FXML
    private TableColumn<Appointment, String> apptLocCol;
    /**Appointment type column in the ApptTableView for the Technician Schedule report. */
    @FXML
    private TableColumn<Appointment, String> apptTypeCol;
    /**Appointment start date column in the ApptTableView for the Technician Schedule report. */
    @FXML
    private TableColumn<Appointment, LocalDate> startDateCol;
    /**Appointment start time column in the ApptTableView for the Technician Schedule report. */
    @FXML
    private TableColumn<Appointment, String> startTimeCol;
    /**Appointment end date column in the ApptTableView for the Technician Schedule report. */
    @FXML
    private TableColumn<Appointment, LocalDate> endDateCol;
    /**Appointment end time column in the ApptTableView for the Technician Schedule report. */
    @FXML
    private TableColumn<Appointment, String> endTimeCol;
    /**Appointment customer ID column in the ApptTableView for the Technician Schedule report. */
    @FXML
    private TableColumn<Appointment, Integer> customerIDCol1;
    /**Appointment location column in the ApptTableView for the Technician Schedule report. */
    @FXML
    private TableColumn<Appointment, String> customerCol;
    /**Combo box of contacts to filter the ApptTableView based on contact selected. */
    public ComboBox<Technician> techComboBox;

    /**Filters the ApptTableView based on the contact selected in the combo box. This method compares contact ID
     * to the appointment contact ID to create a list of appointments.
     * @param actionEvent user selects a contact
     */
    public void onActionSelectTech(ActionEvent actionEvent)  {
        int techID = techComboBox.getValue().getTechID();
        ObservableList<Appointment> apptsbyTech = FXCollections.observableArrayList();

        for (Appointment appt: Schedule.getAllAppointments()){
            if ( (appt.getTechID() == techID)  ){
                apptsbyTech.add(appt);
            }
        }
        populateAppointments(apptsbyTech);
    }

    /**Sets the AppointmentsTable and its columns to the desired filtered list of appointments. .
     * The onActionSelectContact() method calls this method with a list of appointments specific to the selected user.
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
        customerCol.setCellValueFactory(new PropertyValueFactory<>("CustomerName"));
        customerIDCol1.setCellValueFactory(new PropertyValueFactory<>("customerID"));
    }

    /**Returns to the previous screen. */
    @FXML
    void onActionMainMenu(ActionEvent event) throws IOException {
        nextScreen("/view/ReportMenu.fxml", event);
    }

    /**Initializes the ContactScheduleController class. Sets the contact combo box with the allContacts list. */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        techComboBox.setItems(Schedule.getAllTechs());
    }

}
