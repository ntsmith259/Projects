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
import model.Technician;

/**Uses the database connection to query contacts table.
 * @author ntsmi
 */
public class DBTechnician {

    /**Queries the contacts table and populates a list of contacts when the program is launched.
     * @return a list of all users.
     */
    public static ObservableList<Technician> getAllTechs(){
        ObservableList<Technician> allTechnicians = FXCollections.observableArrayList();

        try{
            String sql = "SELECT Technician_ID, Technician_Name FROM technicians";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                int techID = rs.getInt("Technician_ID");
                String techName = rs.getString("Technician_Name");

                Technician t = new Technician(techID, techName);
                allTechnicians.add(t);
            }
        }
        catch(SQLException throwables){
            throwables.printStackTrace();
        }
        return allTechnicians;
    }

}
