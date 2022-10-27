package ReportObjects;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Technician;
import model.Schedule;
import utils.NavigationInterface;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/** FXML Controller class for the Customer Report screen. Displays the number of appointments between a selected
  contact and each customer. */
public class CustomerReportController implements Initializable, NavigationInterface {

    /**Table view for the Customer report. */
    public TableView ApptTableView;
    /**Customer name column in the ApptTableView.. */
    public TableColumn customerCol;
    /**Appointment number column in the ApptTableView.. */
    public TableColumn appointmentNumCol;
    /**Combo box of all contacts. */
    public ComboBox<Technician> techComboBox;

    /**Returns to the previous screen. */
    public void onActionMainMenu(ActionEvent event) throws IOException {
        nextScreen("/view/ReportMenu.fxml", event);
    }

    /**Creates a list of Customer reports for the selected contact. The list is passed into the
     * populateAppointments() method to display in the table.
     * @param actionEvent user selects contact from the combo box
     */
    public void onActionSelectTech(ActionEvent actionEvent) {
        int techID = techComboBox.getValue().getTechID();
        ObservableList<CustomerReport> customerReports = FXCollections.observableArrayList();

        for (CustomerReport c: DBCustomerReport.getAllCustomerReports()){
            if ( (c.getTechID() == techID)  ){
                customerReports.add(c);
            }
        }
        populateAppointments(customerReports);
    }

    /**Sets the report table and its columns to the desired filtered list of appointments. .
     * The onActionSelectContact() method calls this method with a list of appointments specific to the selected user.
     * @param customerReports a filtered appointment list based on the user selected filter.
     */
    public void populateAppointments(ObservableList<CustomerReport> customerReports){
        ApptTableView.setItems(customerReports);
        customerCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        appointmentNumCol.setCellValueFactory(new PropertyValueFactory<>("appointmentNum"));
    }
    /**Initializes the CustomerReport Controller class. The combo box for the contacts is populated. */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        techComboBox.setItems(Schedule.getAllTechs());
    }
}
