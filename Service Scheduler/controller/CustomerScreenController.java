/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import DBAccess.DBAppointment;
import DBAccess.DBCustomer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Customer;
import model.Schedule;
import utils.NavigationInterface;

/** FXML Controller class for the Customer Screen.
 * @author ntsmi
 */
public class CustomerScreenController implements Initializable, NavigationInterface {

    /**The customer table. */
    @FXML
    private TableView<Customer> CustomerTableView;
    /**The customer ID column of the customer table. */
    @FXML
    private TableColumn<Customer, Integer> customerIDCol;
    /**The customer name column of the customer table. */
    @FXML
    private TableColumn<Customer, String> customerNameCol;
    /**The address column of the customer table. */
    @FXML
    private TableColumn<Customer, String> addressCol;
    /**The postal code of the customer table. */
    @FXML
    private TableColumn<Customer, String> postalCodeCol;
    /**The first level division column of the customer table. */
    @FXML
    private TableColumn<Customer, String> firstLevelDivisionCol;
    /**The country column of the customer table. */
    @FXML
    private TableColumn<Customer, String> countryCol;
    /**The phone column of the customer table. */
    @FXML
    private TableColumn<Customer, String> phoneCol;


    /**Loads the AddCustomer screen.
     * @param event mouse click on the AddCustomer button.
     * @throws IOException if fxml file not found.
     */
    @FXML
    void onActionAddCustomer(ActionEvent event) throws IOException{
        nextScreen("/view/AddCustomer.fxml", event);
    }

    /**Deletes a selected customer. The appointments for the customer are deleted followed by the customer.
     * @param event mouse click on deleteCustomer button
     */
    @FXML
    void onActionDeleteCustomer(ActionEvent event) {
        try {
            Customer delCustomer = CustomerTableView.getSelectionModel().getSelectedItem();
            if (delCustomer == null)
                throw new NullPointerException();

            String customerName = delCustomer.getCustomerName();

            // confirm delete
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Customer " + customerName + " and their appointments will be deleted. Do you want to continue?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                DBAppointment.deleteCustomerDBAppointments(delCustomer);
                DBCustomer.deleteCustomer(delCustomer);
                Alert inform = new Alert(Alert.AlertType.INFORMATION, customerName + " and their appointments have been deleted");
                inform.showAndWait();
            }
        }catch (NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No Customer Selected");
            alert.setContentText("Please a customer to delete.");
            alert.showAndWait();
        }
    }

    /**Loads the EditCustomer screen populated with the data from the selected customer.
     * @param event mouse click on the EditCustomer button and a customer selected.
     * @throws IOException if fxml file not found
     */
    @FXML
    void onActionEditCustomer(ActionEvent event)throws IOException {
        //  everything below is from my C492 project

        try {
            FXMLLoader loader = new FXMLLoader(); // creates object
            loader.setLocation(getClass().getResource("/view/EditCustomer.fxml")); // lets its know which view to use
            loader.load();
            EditCustomerController editCustomer = loader.getController();
            editCustomer.sendCustomer(CustomerTableView.getSelectionModel().getSelectedItem());

            Stage stage;
            //Parent scene;
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show(); // anything after this code will not display


        } catch (NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No Customer Selected");
            alert.setContentText("Please a customer to edit.");
            alert.showAndWait();
        }
    }

    /**Returns to the MainMenuScreen.
     * @param event mouse click on the Back button
     * @throws IOException if fxml file not found
     */
    @FXML
    void onActionMainMenu(ActionEvent event) throws IOException {
        nextScreen("/view/MainMenu.fxml", event);
    }

    /**Initializes the CustomerScreenController class.
     * Populates the CustomerTableView. */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        CustomerTableView.setItems(Schedule.getAllCustomers());
        customerIDCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        customerNameCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        postalCodeCol.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
        firstLevelDivisionCol.setCellValueFactory(new PropertyValueFactory<>("divisionName"));
        countryCol.setCellValueFactory(new PropertyValueFactory<>("countryName"));
    }



}
