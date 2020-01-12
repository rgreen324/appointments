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
import javafx.scene.control.Label;
import javafx.stage.Stage;
import model.Appointment;
import model.Customer;


/**
 * FXML Controller class
 *
 * @author richard
 */
public class ViewCustomerScreenController implements Initializable {
    private Stage stage;
    private Parent scene;
    private Customer selectedCustomer;
    private int appointmentId = 0;


    @FXML
    private Label nameField;

    @FXML
    private Label addressField;

    @FXML
    private Label address2Field;

    @FXML
    private Label cityField;

    @FXML
    private Label countryField;

    @FXML
    private Label postalCodeField;

    @FXML
    private Label phoneField;

    
    // Populate fields with selectedCustomer data
    public void setSelectedCustomer(Customer selectedCustomer, int appointmentId) {
        this.selectedCustomer = selectedCustomer;
        this.appointmentId = appointmentId;
        nameField.setText(selectedCustomer.getName());
        addressField.setText(selectedCustomer.getAddress());
        address2Field.setText(selectedCustomer.getAddress2());
        cityField.setText(selectedCustomer.getCity());
        countryField.setText(selectedCustomer.getCountry());
        postalCodeField.setText(selectedCustomer.getPostalCode());
        phoneField.setText(selectedCustomer.getPhone());
    }
    
    
    // Edit button actions
    @FXML
    void editButton(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/EditCustomerScreen.fxml"));
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = loader.load();

        EditCustomerScreenController controller = loader.getController();
        controller.setSelectedCustomer(selectedCustomer, appointmentId);

        stage.setScene(new Scene(scene));
        stage.show();
    }

    
    
    // Back button actions
    @FXML
    void backButton(ActionEvent event) throws IOException {
        // Go back to customers screen
        if(appointmentId == 0) {
            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/CustomersScreen.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
        // Go back to appointment screen
        else {
            Appointment appointment = Appointment.getAppointment(appointmentId);
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/ViewAppointmentScreen.fxml"));
            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            scene = loader.load();

            ViewAppointmentScreenController controller = loader.getController();
            controller.setSelectedAppointment(appointment);

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
