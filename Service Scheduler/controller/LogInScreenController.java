/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.Appointment;
import model.Schedule;
import model.User;
import utils.NavigationInterface;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.function.Function;

/**FXML Controller class for the Login Screen.
 *Logic to handle valid login attempts, form translation and appointment alerts are included in this class.
 */
public class LogInScreenController implements Initializable, NavigationInterface {

    /**Function lambda which simplifies the code to perform translations using the ResourceBundle. */
    Function<String, String> translate = a-> Schedule.getResourceBundle().getString(a);

    /**Displays "Language" in the current language.*/
    public Label currentLanguageLabel;
    /**Displays "User ID" in the current language.*/
    public Label userIDLabel;
    /**Displays "Time Zone" in the current language.*/
    public Label timeZoneLabel;
    /**Displays "Title" in the current language.*/
    public Label titleLabel;
    /**Displays "Password" in the current language.*/
    public Label passwordLabel;
    /**Displays the current language.*/
    public Label languageLabel;
    /**Displays the current zone.*/
    public Label zoneLabel;
    /**Displays the exitButton in the current language. */
    @FXML
    public Button exitButton;
    /**Displays the LoginButton in the current language. */
    @FXML
    private Button LogInButton;
    /**Text field for the user to enter their username. */
    @FXML
    private TextField userNameText;
    /**Text field for the user to enter their password. */
    @FXML
    private TextField PasswordText;

    /**Compares the user name and password entered by the user to valid combinations.
     * @param userName username provided by the user
     * @param password password provided by the user
     * @return true if a valid username and password are found in the allUsers list
     */
    private boolean attemptLogin( String userName, String password){
        for(User user: Schedule.getAllUsers()){
            if (  user.getUserName().equals(userName) && user.getPassword().equals(password)  ){
                Schedule.setCurrentUser(user);
                return true;
            }
        }
        return false;
    }

    /** Finds appointments for the current user and returns a customized alert message.
     * @param user the current user
     * @return a customized message about upcoming appointments.  */
    private String findAppointments( User user ) {
        StringBuilder meeting = new StringBuilder();
        boolean hasMeetings = false;
        boolean needsAlert = false;
        LocalDateTime alertWindow = LocalDateTime.ofInstant(Instant.now(),Schedule.getUserZoneId()).plusMinutes(15);

        for (Appointment appointment : Schedule.getAllAppointments()) {

            long min = Duration.between(appointment.getStart(), alertWindow).toMinutes();

            DateTimeFormatter time = DateTimeFormatter.ofPattern("HH:mm a");
            DateTimeFormatter date = DateTimeFormatter.ofPattern("MM/dd/yy");
            String meetingTime = time.format(appointment.getStart());
            String meetingDate = date.format(appointment.getStart());

            if (appointment.getUserID() == user.getUserID()) {
                hasMeetings = true;
                if (min <= 15 && min >= 0) {
                    int minRemain = 15- (int)min;
                    meeting.append("\n" + "You have an appointment scheduled in approximately " + minRemain + " minutes."
                            + "\n" + "\n" + "Appointment ID: " + appointment.getAppointmentID() + " is scheduled for "
                            + meetingDate + " at " + meetingTime + "\n");
                    needsAlert = true;
                }
            }
        }

        if(needsAlert)
            return meeting.toString();
        else if (hasMeetings)
            return "You have no upcoming meetings";
        else
            return "You have no meetings scheduled";
    }

    /** Attempts to login with the username and password entered by the user.
     * Nulls are checked and then the methods attemptLogin() and findAppointments() are called.
     * Uses the lambda translate to translate messages to the default language.
     * @param event mouse click on the Login Button
     * @throws IOException
     */
    @FXML
    private void onActionLogIn(ActionEvent event) throws IOException{
        String userName = userNameText.getText();
        String password = PasswordText.getText();

        //get TimeStamp of login attempt into string format for the login.txt file
        ZonedDateTime startZDT = ZonedDateTime.of(LocalDateTime.now() ,Schedule.getUserZoneId());
        Instant startGMTInstant = startZDT.toInstant();
        LocalDateTime  now = LocalDateTime.ofInstant(startGMTInstant, ZoneOffset.UTC);
        DateTimeFormatter time = DateTimeFormatter.ofPattern("HH:mm:ss");
        DateTimeFormatter date = DateTimeFormatter.ISO_LOCAL_DATE;
        String timeString = time.format(now);
        String dateString = date.format(now);
        String validLogin = "Invalid Login";

        String invalidTitle = translate.apply("invalid");
        String missingField = translate.apply("missingField");
        String invalidLogin = translate.apply("invalidLogin");

        //check for nulls first
        if(userName.equals("")|| password.equals("")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(invalidTitle);
            alert.setContentText(missingField);
            alert.showAndWait();
            userName = "none";
        }
        else if (attemptLogin(userName, password)){
            String alertMessage = findAppointments(Schedule.getCurrentUser());
            Alert inform = new Alert(Alert.AlertType.INFORMATION, "Logged in as: " + Schedule.getCurrentUser().getUserName()
                                                                        + "\n" + alertMessage);

            inform.showAndWait();
            nextScreen("/view/MainMenu.fxml", event);
            validLogin = "Successful Login"; // used to write to file

        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(invalidTitle);
            alert.setContentText(invalidLogin);
            alert.showAndWait();

        }

        String filename = "login_activity.txt", loginAttempt;
        FileWriter fwriter = new FileWriter(filename, true);
        PrintWriter outputFile = new PrintWriter(fwriter);
        loginAttempt = userName +"              " + dateString + "        " + timeString
                + "       " + Schedule.getServerZoneId() + "       " + validLogin;
        outputFile.println(loginAttempt);

        //close file item
        outputFile.close();
    }

    /**Closes the application.
     * @param actionEvent mouse click on Exit button.
     */
    public void onActionExit(ActionEvent actionEvent) {
        System.out.println("Closing Application");
        System.exit(0);
    }

    /**Initializes the Login Screen and translates the form based on the default language setting.
     * @param url The Login Screen
     * @param rb The ResourceBundle set by the default location
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        titleLabel.setText(rb.getString("title"));
        userIDLabel.setText(rb.getString("user"));
        passwordLabel.setText(rb.getString("password"));
        languageLabel.setText(rb.getString("language"));
        zoneLabel.setText(rb.getString("zone"));
        LogInButton.setText(rb.getString("Login"));
        exitButton.setText(rb.getString("exit"));
        timeZoneLabel.setText(Schedule.getUserZoneId().toString());
        currentLanguageLabel.setText(rb.getString("currentLanguage"));
    }
}
