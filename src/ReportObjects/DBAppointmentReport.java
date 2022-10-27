/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ReportObjects;

import DBAccess.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Month;

/**Performs database queries for the appointment report class.  */
public class DBAppointmentReport {
    public static ObservableList<AppointmentReport> getAllAppointmentReports(){
        ObservableList<AppointmentReport> allAppointmentReports = FXCollections.observableArrayList();

        try{
            String sql = "select type, month(start) as month, Year(start) AS year, count(*) AS total from appointments group by type, YEAR, MONTH ORDER BY YEAR, MONTH";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                String type = rs.getString("type");
                int month = rs.getInt("month");
                String year = rs.getString("year");
                int total = rs.getInt("total");

                String monthName = Month.of(month).toString();

                AppointmentReport a = new AppointmentReport(type, monthName, year, total);
                allAppointmentReports.add(a);
            }
        }
        catch(SQLException throwables){
            throwables.printStackTrace();
        }
        return allAppointmentReports;
    }

}
