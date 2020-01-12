/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.StringJoiner;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.CustomerDB;


/**
 * FXML Controller class
 *
 * @author richard
 */
public class AddCustomerScreenController implements Initializable {
    private Stage stage;
    private Parent scene;
    
    @FXML
    private TextField nameField;

    @FXML
    private TextField addressField;

    @FXML
    private TextField address2Field;

    @FXML
    private TextField cityField;

    @FXML
    private TextField countryField;

    @FXML
    private TextField postalCodeField;

    @FXML
    private TextField phoneField;

    
    
    // Cancel button actions
    @FXML
    void cancelButton(ActionEvent event) throws IOException {
        // Go back to customers screen
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/CustomersScreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    // Save button actions
    @FXML
    void saveButton(ActionEvent event) {
        // Get values from fields
        String country = countryField.getText();
        String city = cityField.getText();
        String address = addressField.getText();
        String address2 = address2Field.getText();
        String postalCode = postalCodeField.getText();
        String phone = phoneField.getText();
        String name = nameField.getText();
        
        // Check for empty field values
        StringJoiner fields = new StringJoiner(", ");
        
        if(name.trim().length() == 0) {
            fields.add("Name");
        }
        if(address.trim().length() == 0) {
            fields.add("Address");
        }
        if(address2.trim().length() == 0) {
            fields.add("Address 2");
        }
        if(city.trim().length() == 0) {
            fields.add("City");
        }
        if(country.trim().length() == 0) {
            fields.add("Country");
        }
        if(postalCode.trim().length() == 0) {
            fields.add("Postal Code");
        }
        if(phone.trim().length() == 0) {
            fields.add("Phone");
        }

        
        try {
            if(fields.length() > 0) {
                throw new IllegalArgumentException("All fields required.");
            }
            
            // Send data to CustomerDB to populate new customer
            CustomerDB.newCustomer(country, city, address, address2, postalCode, phone, name);

            // Go back to customers screen
            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/CustomersScreen.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
  
        }
        // Empty field detected
        catch(IllegalArgumentException ex){ 
            System.out.println("Error: " + ex.getMessage());
            String empty = fields.toString();
            
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Customer Error");
            alert.setHeaderText("All fields are required!");
            alert.setContentText("Please complete the following fields: " + empty);

            alert.showAndWait();
        }
        catch(Exception ex){ 
                System.out.println("Error: " + ex.getMessage());
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
