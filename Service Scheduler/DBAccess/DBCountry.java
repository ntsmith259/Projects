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
import model.Country;
import model.Division;

/**Uses the database connection to query the countries table.
 * @author ntsmi
 */
public class DBCountry {

    /** Queries the countries table and populates a list of countries when the program is launched.
     * Each country has its own list of divisions.
     * @return a list of countries.
     */
    public static ObservableList<Country> getAllCountries(){
        ObservableList<Country> allCountries = FXCollections.observableArrayList();

        try{
            String sql = "SELECT * FROM countries";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                int countryID = rs.getInt("Country_ID");
                String countryName = rs.getString("Country");
                ObservableList<Division> divisonList = getDivisions(countryID);
                Country c = new Country(countryID, countryName, divisonList);
                allCountries.add(c);
            }

        }
        catch(SQLException throwables){
            throwables.printStackTrace();
        }
        return allCountries;
    }

    /**Queries the division table for each country from the countries table.
     * @param countryID the country ID
     * @return a list of divisions for the matching country ID
     */
    public static ObservableList<Division> getDivisions(int countryID){
        ObservableList<Division> divisions = FXCollections.observableArrayList();

        try{
            String sql = "SELECT Division_ID, Division from first_level_divisions WHERE first_level_divisions.COUNTRY_ID=?";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ps.setInt(1, countryID);
            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                int divisionID = rs.getInt("Division_ID");
                String divisionName = rs.getString("Division");
                Division d = new Division(divisionID, divisionName);
                divisions.add(d);
            }
        }
        catch(SQLException throwables){
            throwables.printStackTrace();
        }
        return divisions;
    }
}
