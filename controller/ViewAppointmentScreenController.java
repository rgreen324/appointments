/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import model.Appointment;
import model.Customer;



/**
 * FXML Controller class
 *
 * @author richard
 */
public class ViewAppointmentScreenController implements Initializable {

    private Stage stage;
    private Parent scene;
    private Appointment selectedAppointment;
    
    
    @FXML
    private Label titleField;

    @FXML
    private Hyperlink customerField;

    @FXML
    private Label descriptionField;

    @FXML
    private Label locationField;

    @FXML
    private Label contactField;

    @FXML
    private Label typeField;

    @FXML
    private Label urlField;

    @FXML
    private Label dateField;

    @FXML
    private Label startField;

    @FXML
    private Label endField;


    
    // Populate fields with selectedAppointment data
    public void setSelectedAppointment(Appointment selectedAppointment) {
        this.selectedAppointment = selectedAppointment;

        titleField.setText(selectedAppointment.getTitle());
        descriptionField.setText(selectedAppointment.getDescription());
        locationField.setText(selectedAppointment.getLocation());
        contactField.setText(selectedAppointment.getContact());
        typeField.setText(selectedAppointment.getType());
        urlField.setText(selectedAppointment.getUrl());
        customerField.setText(selectedAppointment.getCustomerName());
        startField.setText(selectedAppointment.getReportStart());
        endField.setText(selectedAppointment.getReportEnd());
        dateField.setText(selectedAppointment.getReportDate());
    }
    
    // Customer link action
    @FXML
    void customerLink(ActionEvent event)  throws IOException {
        int customerId = selectedAppointment.getCustomerId();
        int appointmentId = selectedAppointment.getAppointmentId();
        Customer selectedCustomer = Customer.getCustomer(customerId);

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/ViewCustomerScreen.fxml"));
        stage = (Stage)((Hyperlink)event.getSource()).getScene().getWindow();
        scene = loader.load();

        ViewCustomerScreenController controller = loader.getController();
        controller.setSelectedCustomer(selectedCustomer, appointmentId);

        stage.setScene(new Scene(scene));
        stage.show();
    }
    
    
    // Cancel button actions
    @FXML
    void cancelButton(ActionEvent event) throws IOException {
        // Go back to appointments screen
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/AppointmentsScreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    
    // Edit button actions
    @FXML
    void editButton(ActionEvent event) throws IOException {
        if (selectedAppointment != null) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/EditAppointmentScreen.fxml"));
            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            scene = loader.load();
            
            EditAppointmentScreenController controller = loader.getController();
            controller.setSelectedAppointment(selectedAppointment);
            
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }
    
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

    }    
    
}
