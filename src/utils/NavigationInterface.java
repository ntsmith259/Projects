/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;


/**
 * This interface is used to navigate to different screens throughout the project.
 * @author ntsmi
 */
public interface NavigationInterface {

    /** The nextScreen method loads the next screen that is specified by the fxml file.
     * @param event The mouse click
     * @param fxmlfile the path of the screen to load
     * */
    default void nextScreen(String fxmlfile, ActionEvent event) throws IOException {
        Stage stage;
        Parent scene;
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource(fxmlfile));
        stage.setScene(new Scene(scene));
        stage.show();
    }





}
