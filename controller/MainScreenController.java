/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import model.Appointment;



/**
 *
 * @author richard
 */
public class MainScreenController implements Initializable {
    private Stage stage;
    private Parent scene;

    // Alert if appointment scheduled within 15 minutes of login
    public void setAppointmentAlert(Appointment appointmentAlert) {
        if(appointmentAlert != null) {
            String customer = appointmentAlert.getCustomerName();
            String time = appointmentAlert.getReportStart();
            
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Appointment Alert");
            alert.setHeaderText("Upcoming Appointment");
            alert.setContentText("You have an appointment with " + customer + " at " + time);

            alert.showAndWait();
        }
    }
    
    // Appointment button actions
    @FXML
    void appointmentsButton(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/AppointmentsScreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    // Customers button actions
    @FXML
    void customersButton(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/CustomersScreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }
    
    // Calendar button actions
    @FXML
    void calendarButton(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/CalendarScreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    // Reports button actions
    @FXML
    void reportsButton(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/ReportsScreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }
    
    // Exit button actions
    @FXML
    void exitButton(ActionEvent event) {
        Platform.exit();
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    // TODO
        
    }    
    
}
