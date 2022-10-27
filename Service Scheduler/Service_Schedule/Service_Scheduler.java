/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service_Schedule;

import DBAccess.DBAppointment;
import DBAccess.DBTechnician;
import DBAccess.DBCountry;
import DBAccess.DBCustomer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Schedule;
import DBAccess.DBConnection;
import DBAccess.DBUsers;
import java.time.ZoneId;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.TimeZone;

/**
 *This class is the main method of the C195 scheduling application.
 * @author ntsmi
 */
public class Service_Scheduler extends Application {

    /** This method launches loads the Login screen.
     @param stage the stage for the Login screen.*/
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/view/LogInScreen.fxml"), Schedule.getResourceBundle());
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    /** This method closes the application*/
    @Override
    public void stop(){
        System.out.println("Closing Application");
    }


    /**This in the main method of the C195 Schedule Application.
     This is the first method called when running the Java Program.
     The startConnection for the database and the setter methods are called for the time zones and
     the lists of objects from the database.
     @param args the command line arguments
     */
    public static void main(String[] args) {
        //Locale.setDefault(new Locale("fr"));
        DBConnection.startConnection();

        // set the time zones
        Schedule.setUserZoneId(ZoneId.of(TimeZone.getDefault().getID()));
        Schedule.setServerZoneId(ZoneId.of("UTC"));
        Schedule.setCompanyZoneId(ZoneId.of("America/New_York"));


        // populate the main lists of the application
        Schedule.setAllUsers(DBUsers.getAllUsers());
        Schedule.setAllTechs(DBTechnician.getAllTechs());
        Schedule.setAllCountries(DBCountry.getAllCountries());
        Schedule.setAllCustomers(DBCustomer.getAllCustomers());
        Schedule.setAllAppointments(DBAppointment.getAllAppointments());


       //ZoneId.getAvailableZoneIds().stream().filter(c->c.contains("America")).forEach(System.out::println);
       //System.out.println(Schedule.getServerZoneId());


        try {
            ResourceBundle rb = ResourceBundle.getBundle("utils/Nat", Locale.getDefault());
            Schedule.setResourceBundle(rb);

        } catch (Exception e){
        }
        launch(args); // calls init() and start() and returns when the application is closed
        DBConnection.closeConnection();
    }

}
