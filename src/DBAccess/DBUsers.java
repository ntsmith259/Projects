/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DBAccess;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.User;

/**Uses the database connection to query the users table.
 * @author ntsmi
 */
public class DBUsers {

    /**Queries the users table and populates a list of users when the program is launched.
     * @return a list of all users.
     */
    public static ObservableList<User> getAllUsers(){
        ObservableList<User> allUsers = FXCollections.observableArrayList();

        try{
            String sql = "SELECT User_ID, User_Name, Password FROM users";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                int userID = rs.getInt("User_ID");
                String userName = rs.getString("User_Name");
                String password = rs.getString("Password");
                User user = new User(userID, userName, password);
                allUsers.add(user);
            }
        }
        catch(SQLException throwables){
            throwables.printStackTrace();
        }
        return allUsers;
    }

}
