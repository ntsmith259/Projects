package ReportObjects;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import utils.NavigationInterface;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


/**FXML Controller class for the AppointmentReport screen. */
public class AppointmentReportController implements Initializable, NavigationInterface {

    /**Tableview for the Appointment Report. */
    @FXML
    private TableView<AppointmentReport> ApptTableView;

    /**Appointment type column for the Appointment Report. */
    @FXML
    private TableColumn<AppointmentReport, String> typeCol;
    /**Appointment year column for the Appointment Report. */
    @FXML
    private TableColumn<AppointmentReport, String> yearCol;
    /**Appointment month column for the Appointment Report. */
    @FXML
    private TableColumn<AppointmentReport, String> monthCol;
    /**Appointment total column for the Appointment Report. */
    @FXML
    private TableColumn<AppointmentReport, Integer> totalCol;

    /**Returns to the previous screen.
     * @param event mouse click on back button
     * @throws IOException if fxml file not found
     */
    public void onActionMainMenu(ActionEvent event) throws IOException {
        nextScreen("/view/ReportMenu.fxml", event);
    }

    /**Initializes the AppointmentReport Controller class.
     * Populates report table with the DBAppointmentReprt.allAppointmentReport list. */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ApptTableView.setItems(DBAppointmentReport.getAllAppointmentReports());
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        yearCol.setCellValueFactory(new PropertyValueFactory<>("year"));
        monthCol.setCellValueFactory(new PropertyValueFactory<>("month"));
        totalCol.setCellValueFactory(new PropertyValueFactory<>("total"));
    }
}
