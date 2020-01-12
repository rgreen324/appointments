/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import model.Appointment;
import model.AppointmentDB;
import model.Customer;
import model.UserDB;


/**
 * FXML Controller class
 *
 * @author richard
 */
public class AddAppointmentScreenController implements Initializable {

    private Stage stage;
    private Parent scene;
    
    
    @FXML
    private TextField titleField;
    
    @FXML
    private TextField descriptionField;

    @FXML
    private TextField locationField;

    @FXML
    private TextField contactField;
    
    @FXML
    private TextField urlField;
    
    @FXML
    private ComboBox<Customer> customerCB;
    
    @FXML
    private ComboBox<String> typeCB;
    
    @FXML
    private DatePicker datePicker;
    
    @FXML
    private ComboBox<String> startCB;

    @FXML
    private ComboBox<String> endCB;


    
    // Cancel button actions
    @FXML
    void cancelButton(ActionEvent event) throws IOException {
        // Go back to appointments screen
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/AppointmentsScreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }


    // Save button actions
    @FXML
    void saveButton(ActionEvent event) {
        boolean emptyFields = false;
        int customerId = 0;
        String type = " ";
        LocalDate apptDate = LocalDate.of(0001, 01, 01);
        String start = " ";
        String end = " ";
        String empty = " ";
        
        // Get form values
        int userId = UserDB.getCurrentUser().getUserId();
        String title = titleField.getText();
        String description = descriptionField.getText();
        String location = locationField.getText();
        String contact = contactField.getText();
        String url = urlField.getText();

        if(!customerCB.getSelectionModel().isEmpty()) {
            customerId = customerCB.getSelectionModel().getSelectedItem().getCustomerId();
        }
        
        if(!typeCB.getSelectionModel().isEmpty()) {
            type = typeCB.getSelectionModel().getSelectedItem();
        }
        
        if(datePicker.getValue() != null) {
            apptDate = datePicker.getValue();
        }
        
        if(!startCB.getSelectionModel().isEmpty()) {
            start = startCB.getValue();
        }
        
        if(!endCB.getSelectionModel().isEmpty()) {
            end = endCB.getValue();
        }

        
        // Check for empty field values
        StringJoiner fields = new StringJoiner(", ");
        if(customerId <= 0) {
            fields.add("Customer");
        }
        if(title.trim().length() == 0) {
            fields.add("Title");
        }
        if(description.trim().length() == 0) {
            fields.add("Description");
        }
        if(location.trim().length() == 0) {
            fields.add("Location");
        }
        if(contact.trim().length() == 0) {
            fields.add("Contact");
        }
        if(type.trim().length() == 0) {
            fields.add("Type");
        }
        if(url.trim().length() == 0) {
            fields.add("URL");
        }
        if(apptDate.isEqual(LocalDate.of(0001, 01, 01))) {
            fields.add("Date");
        }
        if(start.trim().length() == 0) {
            fields.add("Start");
        }
        if(end.trim().length() == 0) {
            fields.add("End");
        }

        if(fields.length() > 0) {
            emptyFields = true;
            empty = fields.toString();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Appointment Error");
            alert.setHeaderText("All fields are required!");
            alert.setContentText("Please complete the following fields: " + empty);

            alert.showAndWait();
        }

        try{
            // Create UTC database datetime entries
            LocalDateTime dbStartDateTime = Appointment.ldtFormatter(apptDate, start);
            LocalDateTime dbEndDateTime = Appointment.ldtFormatter(apptDate, end);
            
            // Validate time sequence
            boolean sequence = Appointment.isValidSequence(dbStartDateTime, dbEndDateTime);
            if(!sequence) {
                throw new IllegalArgumentException("Appointment start time must be before end time.");
            }
            
            // Validate business hours
            boolean businessHours = Appointment.isValidBusinessHours(apptDate, dbStartDateTime, dbEndDateTime);
            if(!businessHours) {
                throw new IllegalArgumentException("Appointment is outside of business hours.");
            }
            
            // Validate appointment overlap
            int apptId = 0;
            boolean noOverlap = Appointment.isValidTimeSlot(apptId, dbStartDateTime, dbEndDateTime);
            if(!noOverlap) {
                throw new IllegalArgumentException("Time conflicts with existing appointment.");
            }
            
            // Create new appointment object
            Appointment newAppointment = new Appointment(customerId, userId, title, description, 
                    location, contact, type, url, dbStartDateTime, dbEndDateTime);
            
            // Send new appointment object to database
            AppointmentDB.newAppointment(newAppointment);
            
            // Go back to appointments screen
            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/AppointmentsScreen.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
        catch(Exception ex){ 
            System.out.println("Error: " + ex.getMessage());
            
            if(!emptyFields) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Schedule Error");
                alert.setHeaderText("Appointment cannot be saved!");
                alert.setContentText(ex.getMessage());

                alert.showAndWait();
            }
        }
    }
    
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        // Populate customers combobox
        customerCB.setItems(Customer.getAllCustomers());
        
        // Display customer names in combobox
        customerCB.setConverter(new StringConverter<Customer>() {
            @Override
            public String toString(Customer object) {
                return object.getName();
            }

            @Override
            // Lambda expression used to increase readability and reduce code length
            public Customer fromString(String string) {
                return customerCB.getItems().stream().filter(c -> 
                    c.getName().equals(string)).findFirst().orElse(null);
            }
        });
        
        // Populate types combobox
        typeCB.setItems(Appointment.getTypes());
        
        // Populate times comboboxes
        startCB.setItems(Appointment.getStartTimes());
        endCB.setItems(Appointment.getEndTimes());

    }    
    
}
