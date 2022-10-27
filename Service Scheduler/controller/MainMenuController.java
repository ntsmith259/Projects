/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import utils.NavigationInterface;

/**FXML Controller class for the main menu.  */
public class MainMenuController implements Initializable, NavigationInterface {

    /**Loads the Appointments screen.
     * @param event mouse click on appointments button
     * @throws IOException if fxml file not found
     */
    @FXML
    void onActionAppointments(ActionEvent event) throws IOException {
        nextScreen("/view/Appointments.fxml", event);
    }
    /**Loads the Customers screen.
     * @param event mouse click on customers button
     * @throws IOException if fxml file not found
     */
    @FXML
    void onActionCustomers(ActionEvent event) throws IOException {
        nextScreen("/view/CustomerScreen.fxml", event);
    }
    /**Loads the Reports screen.
     * @param event mouse click on reports button
     * @throws IOException if fxml file not found
     */
    @FXML
    void onActionReports(ActionEvent event) throws IOException {
        nextScreen("/view/ReportMenu.fxml", event);
    }

    /**Closes the program.
     * @param event mouse click on close button
     */
    @FXML
    void onActionExit(ActionEvent event) {
        System.out.println("Closing Application");
        System.exit(0);
    }


    /**Initializes the MainMenuController class.
     * @param url The main Menu url.
     * @param rb resource bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }



}
