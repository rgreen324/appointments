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
import java.time.LocalDateTime;
import static java.time.LocalDateTime.now;
import richardgreen_schedulingsystem.Database;

/**
 *
 * @author richard
 */
public class AppointmentDB {
    private static Connection conn = (Connection) Database.getConn();
    private static String userName = UserDB.getCurrentUser().getUserName();
    
    // Add new appointment to database
    public static void newAppointment(Appointment newAppointment) throws SQLException {
        try {        
            int customerId = newAppointment.getCustomerId();
            int userId = newAppointment.getUserId();
            String title = newAppointment.getTitle();
            String description = newAppointment.getDescription();
            String location = newAppointment.getLocation();
            String contact = newAppointment.getContact();
            String type = newAppointment.getType();
            String url = newAppointment.getUrl();
            LocalDateTime start = newAppointment.getStart();
            LocalDateTime end = newAppointment.getEnd();

            String addAppointment = "INSERT INTO appointment(customerId, userId, title, description, "
                    + "location, contact, type, url, start, end, createDate, "
                    + "createdBy, lastUpdate, lastUpdateBy)"
                        + "VALUES("
                        + "'" + customerId + "', "
                        + "'" + userId + "', "
                        + "'" + title + "', "
                        + "'" + description + "', "
                        + "'" + location + "', "
                        + "'" + contact + "', "
                        + "'" + type + "', "
                        + "'" + url + "', "
                        + "'" + start + "', "
                        + "'" + end + "', "
                        + "'" + now() + "', "
                        + "'" + userName + "', "
                        + "'" + now() + "', "
                        + "'" + userName + "')";
                Statement createAppointment = conn.createStatement();
                createAppointment.executeUpdate(addAppointment);

        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }
    
    
    // Generate appointments from DB
    public static void generateAppointments() throws SQLException {
        try {
            String selectAppointments = "SELECT appointment.appointmentId, appointment.userId, \n"
                    + "appointment.title, appointment.description, appointment.location, \n"
                    + "appointment.contact, appointment.type, appointment.url, appointment.start, \n"
                    + "appointment.end, appointment.customerId, customer.customerName \n" 
                    + "FROM appointment \n"
                    + "JOIN customer ON appointment.customerId = customer.customerId;";
            Statement getAppointments = conn.createStatement();
            ResultSet result = getAppointments.executeQuery(selectAppointments);
            
            while(result.next()) {
                int appointmentId = result.getInt("appointmentId");
                int customerId = result.getInt("customerId");
                int userId = result.getInt("userId");
                String title = result.getString("title");
                String description = result.getString("description");
                String location = result.getString("location");
                String contact = result.getString("contact");
                String type = result.getString("type");
                String url = result.getString("url");
                LocalDateTime start = result.getTimestamp("start").toLocalDateTime();
                LocalDateTime end = result.getTimestamp("end").toLocalDateTime();
                String customerName = result.getString("customerName");
                LocalDateTime localStart = Appointment.dbToLocalDT(start);
                LocalDateTime localEnd = Appointment.dbToLocalDT(end);
                String reportStart = Appointment.reportTime(localStart);
                String reportEnd = Appointment.reportTime(localEnd);
                String reportDate = Appointment.reportDate(start);
                String reportTime = reportStart + " - " + reportEnd;

                
                Appointment newAppointment = new Appointment(appointmentId);
                newAppointment.setCustomerId(customerId);
                newAppointment.setUserId(userId);
                newAppointment.setTitle(title);
                newAppointment.setDescription(description);
                newAppointment.setLocation(location);
                newAppointment.setContact(contact);
                newAppointment.setType(type);
                newAppointment.setUrl(url);
                newAppointment.setStart(start);
                newAppointment.setEnd(end);
                newAppointment.setCustomerName(customerName);
                newAppointment.setLocalStart(localStart);
                newAppointment.setLocalEnd(localEnd);
                newAppointment.setReportStart(reportStart);
                newAppointment.setReportEnd(reportEnd);
                newAppointment.setReportDate(reportDate);
                newAppointment.setReportTime(reportTime);
                
                Appointment.addAppointment(newAppointment);
            }
            
            
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }
    
    
    // Update appointment
    public static void updateAppointment(Appointment appointment) throws SQLException {
        try {
            String editAppointment = "UPDATE appointment SET "
                        + "customerId = " + "'" + appointment.getCustomerId() + "', "
                        + "title = " + "'" + appointment.getTitle() + "', "
                        + "description = " + "'" + appointment.getDescription() + "', "
                        + "location = " + "'" + appointment.getLocation() + "', "
                        + "contact = " + "'" + appointment.getContact() + "', "
                        + "type = " + "'" + appointment.getType() + "', "
                        + "url = " + "'" + appointment.getUrl() + "', "
                        + "start = " + "'" + appointment.getStart() + "', "
                        + "end = " + "'" + appointment.getEnd() + "', "
                        + "lastUpdate = " + "'" + now() + "', "
                        + "lastUpdateBy = " + "'" + userName + "'"
                        + "WHERE appointmentId = " + appointment.getAppointmentId();
            Statement updateAppointment = conn.createStatement();
            updateAppointment.executeUpdate(editAppointment);
            
            } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }
    
    // Delete appointment
    public static void deleteAppointment(int appointmentId) throws SQLException {
        try {
            String selectAppointment = "DELETE FROM appointment WHERE appointmentId = " + appointmentId;
            Statement deleteAppointment = conn.createStatement();
            deleteAppointment.executeUpdate(selectAppointment);
            
            } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }
    
}
