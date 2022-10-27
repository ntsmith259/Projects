/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import DBAccess.DBCustomer;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import model.*;
import utils.CustomerChecker;
import utils.NavigationInterface;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

/**FXML Controller Class for the EditCustomer screen. */
public class EditCustomerController implements Initializable, NavigationInterface, CustomerChecker {


    /**Disabled text field to display the customer ID. */
    @FXML
    private TextField customerIDTxt;

    /**Text field for the user to edit the customer name. */
    @FXML
    private TextField customerNameTxt;
    /**Text field for the user to edit the customer address. */
    @FXML
    private TextField AddressTxt;
    /**Text field for the user to edit the customer postal code. */
    @FXML
    private TextField PostalCodeTxt;
    /**Text field for the user to edit the customer phone. */
    @FXML
    private TextField phoneNumberTxt;
    /**Combo box for the user to edit the country. */
    @FXML
    private ComboBox<Country> countryCombo ;
    /**Combo box for the user to edit the division. */
    @FXML
    private ComboBox<Division> divisionCombo;


    /**Method called by the CustomerScreenController to populate the text fields with
     * the information of the selected customer from the customer table.
     * @param customer the customer selected on the CustomerScreen
     */
    public void sendCustomer(Customer customer){
        customerIDTxt.setText(String.valueOf(customer.getCustomerID()));
        customerNameTxt.setText(String.valueOf(customer.getCustomerName()));
        AddressTxt.setText(String.valueOf(customer.getAddress()));
        PostalCodeTxt.setText(String.valueOf(customer.getPostalCode()));
        phoneNumberTxt.setText(String.valueOf(customer.getPhone()));
        countryCombo.setValue(customer.getCountry());
        divisionCombo.setValue(customer.getDivision());
        divisionCombo.setItems(customer.getCountry().getDivisions());

    }

    /**Returns the index of the customer in the allCustomers list. Used when updating a customer.
     * @param id customer ID
     * @return index of the customer object in the allCustomers list.
     */
    private int getIndex(int id) {
        int index = -1;
        for (Customer c : Schedule.getAllCustomers()) {
            index++;
            if (c.getCustomerID() == id) {
                return index;
            }
        }
        return -1;
    }

    /**This method edits customers in the customer list. Information entered by the user it checked for nulls before
     * calling DBCustomer.editCustomerRecord method which updates the customer in the datbase and updates the allCustomers list.
     * The appointments which reference the customer are also updated.
     * @throws IOException if nulls are present.  */
    @FXML
    void onActionEditCustomer(ActionEvent event) throws IOException {
        String checkFields = "";
        try{
            int customerID = Integer.parseInt(customerIDTxt.getText().trim());
            String name = customerNameTxt.getText().trim();
            String address = AddressTxt.getText().trim();
            String postalCode = PostalCodeTxt.getText().trim();
            String phone = phoneNumberTxt.getText().trim();

            checkFields = checkCustomerFields(name, address, postalCode, phone, divisionCombo.getValue(), countryCombo.getValue());
            if (checkFields.length() > 1){
                throw new IllegalArgumentException();
            }

            String currentUser = Schedule.getCurrentUser().getUserName();
            int divisionID = divisionCombo.getValue().getDivisionID();
            int countryID = countryCombo.getValue().getCountryID();

            DBCustomer.editCustomerRecord(customerID, name, address, postalCode, phone, currentUser, divisionID, countryID);
            int index = getIndex(customerID);
            Schedule.updateCustomer(index, DBCustomer.getUpdatedCustomer(customerID));

            //Updates the appointment to point to the new customer.
            for (Appointment appointment: Schedule.getAllAppointments()){
                if (appointment.getCustomerID() == customerID){
                    appointment.setCustomer(Schedule.getAllCustomers().get(index));            }
            }

            nextScreen("/view/CustomerScreen.fxml", event);

        }catch (IllegalArgumentException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Warning");
            alert.setContentText("Please complete the following fields:" + checkFields);
            alert.showAndWait();
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }

    }

    /**Returns to the previous screen. */
    @FXML
    void onActionBack(ActionEvent event)throws IOException {
        nextScreen("/view/CustomerScreen.fxml", event);
    }


    /**Populates the division combo box based on the selected country. */
    @FXML
    void onActionSelectCountry(ActionEvent event) {
        Country selectedCountry = countryCombo.getValue();
        divisionCombo.setItems(selectedCountry.getDivisions());
    }

    @FXML
    void onActionSelectDivision(ActionEvent event) {
    }

    /**Initializes the AddCustomerController class. The country combo box is populated with countries list. */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        countryCombo.setItems(Schedule.getAllCountries());

        // TODO
    }



}
