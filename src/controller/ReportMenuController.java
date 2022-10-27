package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import utils.NavigationInterface;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**FXML Controller class for the Report Menu screen. */
public class ReportMenuController implements Initializable, NavigationInterface {


    /**Loads the AppointmentReport Screen
     * @param event mouse click on AppointmentReport button.
     * @throws IOException when fxml is not found
     */
    @FXML
     void onActionApptReport(ActionEvent event) throws IOException {
        nextScreen("/ReportObjects/AppointmentReportScreen.fxml", event);
    }

    /**Returns to the previous screen.
     * @param event mouse click on back button.
     * @throws IOException when fxml is not found
     */
    @FXML
    void onActionBack(ActionEvent event) throws IOException {
        nextScreen("/view/MainMenu.fxml", event);
    }
    /**Loads the ContactReport Screen
     * @param event mouse click on ContactReport button.
     * @throws IOException when fxml is not found
     */
    @FXML
    void onActionContactReport(ActionEvent event) throws IOException {
        nextScreen("/ReportObjects/ContactScheduleReport.fxml", event);
    }
    /**Loads the CustomerReport Screen
     * @param event mouse click on CustomerReport button.
     * @throws IOException when fxml is not found
     */
    @FXML
    void onActionCustomerReport(ActionEvent event) throws IOException {
        nextScreen("/ReportObjects/CustomerReportScreen.fxml", event);
    }


    /**Initializes the ReportMenuController class.
     * @param url The report Menu.
     * @param rb resource bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }


}
