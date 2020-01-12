/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import com.mysql.jdbc.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import richardgreen_schedulingsystem.Database;

/**
 *
 * @author richard
 */
public class ReportDB {
    private static Connection conn = (Connection) Database.getConn();
    
    
    public static StringBuilder appointmentTypesMonth() throws SQLException {
        StringBuilder monthReport = new StringBuilder();
        try {
            LocalDate date = LocalDate.of(2000, 1, 1);
            int month = 1;
            
            while(month <= 12) {
                String monthName = date.getMonth().toString();
                monthReport.append("--- ").append(monthName).append(" ---\n");
                
                String selectTypes = "SELECT type, COUNT(*) FROM appointment \n"
                    + "WHERE MONTH(start) = " + month + " \n"
                    + "GROUP BY type;";
                Statement getTypes = conn.createStatement();
                ResultSet result = getTypes.executeQuery(selectTypes);
                
                if(result.isBeforeFirst()) {
                    while(result.next()) {
                        String type = result.getString("type");
                        int count = result.getInt("count(*)");
                        monthReport.append(type).append(" - ").append(count).append("\n");
                    }
                    monthReport.append("\n");
                }
                else {
                    monthReport.append("No appointments").append("\n").append("\n");
                }
                
                month ++;
                date = date.plusMonths(1);
            }
            
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
        
        return monthReport;
    }

    
}
