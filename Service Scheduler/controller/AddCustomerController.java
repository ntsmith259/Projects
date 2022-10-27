/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import DBAccess.DBCustomer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import model.Country;
import model.Division;
import model.Schedule;
import utils.CustomerChecker;
import utils.NavigationInterface;


/**FXML controller class for for the AddCustomer.fxml file.
 Adds customers to the C195 Schedule Application.
  @author ntsmi
 */
public class AddCustomerController implements Initializable, NavigationInterface, CustomerChecker {

    /**Text field for the user to enter the new customer name. */
    @FXML
    private TextField customerNametxt;
    /**Text field for the user to enter the new customer address. */
    @FXML
    private TextField Addresstxt;
    /**Text field for the user to enter the new customer postal code. */
    @FXML
    private TextField PostalCodetxt;
    /**Text field for the user to enter the new customer phone number. */
    @FXML
    private TextField phoneNumbertxt;
    /**Combo box for countries. Populated by the allCounties list. Selecting a country will populate the division combo box. */
    @FXML
    private ComboBox<Country> countryCombo ;
    /**Combo box for divisions. Populated by the divisions specific to the country from the country combo box. */
    @FXML
    private ComboBox<Division> divisionCombo;

    /**This method adds customers to the customer list. Information entered by the user it checked for nulls before
     * calling DBCustomer.addCustomerRecord method which adds the customer to the datbase and updates the allCustomers list.
     * @throws IOException if nulls are present.  */
    @FXML
    void onActionAddCustomer(ActionEvent event) throws IOException {
        String checkFields = "";
        try{
            String name = customerNametxt.getText().trim();
            String address = Addresstxt.getText().trim();
            String postalCode = PostalCodetxt.getText().trim();
            String phone = phoneNumbertxt.getText().trim();


            checkFields = checkCustomerFields(name, address, postalCode, phone, divisionCombo.getValue(), countryCombo.getValue());
            if (checkFields.length() > 1){
                throw new IllegalArgumentException();
            }

            String currentUser = Schedule.getCurrentUser().getUserName();
            int divisionID = divisionCombo.getValue().getDivisionID();
            int countryID = countryCombo.getValue().getCountryID();

            DBCustomer.addCustomerRecord(name, address, postalCode, phone, currentUser ,divisionID, countryID);
            nextScreen("/view/CustomerScreen.fxml", event);

        }catch (IllegalArgumentException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Warning");
            alert.setContentText("Please complete the following fields:" + checkFields);
            alert.showAndWait();
        }
        catch (SQLException e){
            System.out.println(e.getStackTrace());
        }

    }

    @FXML
    /**Returns to the previous screen. */
    void onActionBack(ActionEvent event)throws IOException {
        nextScreen("/view/CustomerScreen.fxml", event);
    }

    @FXML
    /**Populates the division combo box based on the selected country. */
    void onActionSelectCountry(ActionEvent event) {
        Country selectedCountry = countryCombo.getValue();
        divisionCombo.setItems(selectedCountry.getDivisions());
    }

      /**Initializes the AddCustomerController class. The country combo box is populated with countries list. */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        countryCombo.setItems(Schedule.getAllCountries());
    }





}
