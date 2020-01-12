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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import model.Appointment;
import model.AppointmentDB;
import model.Customer;


/**
 * FXML Controller class
 *
 * @author richard
 */
public class EditAppointmentScreenController implements Initializable {

    private Stage stage;
    private Parent scene;
    private Appointment selectedAppointment;
    
    
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


    
    // Populate fields with selectedAppointment data
    public void setSelectedAppointment(Appointment selectedAppointment) {
        this.selectedAppointment = selectedAppointment;

        titleField.setText(selectedAppointment.getTitle());
        descriptionField.setText(selectedAppointment.getDescription());
        locationField.setText(selectedAppointment.getLocation());
        contactField.setText(selectedAppointment.getContact());
        typeCB.getSelectionModel().select(selectedAppointment.getType());
        urlField.setText(selectedAppointment.getUrl());
        customerCB.getSelectionModel().select(Customer.getCustomer(selectedAppointment.getCustomerId()));
        startCB.getSelectionModel().select(selectedAppointment.getReportStart());
        endCB.getSelectionModel().select(selectedAppointment.getReportEnd());
        datePicker.setValue(selectedAppointment.getLocalStart().toLocalDate());
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
    
    
    // Save button actions
    @FXML
    void saveButton(ActionEvent event) throws Exception {
        // Get form values
        int customerId = customerCB.getSelectionModel().getSelectedItem().getCustomerId();
        String title = titleField.getText();
        String description = descriptionField.getText();
        String location = locationField.getText();
        String contact = contactField.getText();
        String type = typeCB.getSelectionModel().getSelectedItem();
        String url = urlField.getText();
        LocalDate apptDate = datePicker.getValue();
        String start = startCB.getValue();
        String end = endCB.getValue();
        
        // Check for empty field values
        StringJoiner fields = new StringJoiner(", ");
        
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
        if(apptDate.toString().trim().length() == 0) {
            fields.add("Date");
        }
        if(start.trim().length() == 0) {
            fields.add("Start");
        }
        if(end.trim().length() == 0) {
            fields.add("End");
        }
        // Show alert if empty fields
        if(fields.length() > 0) {
            String empty = fields.toString();
            Alert alert = new Alert(AlertType.ERROR);
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
            int apptId = selectedAppointment.getAppointmentId();
            boolean noOverlap = Appointment.isValidTimeSlot(apptId, dbStartDateTime, dbEndDateTime);
            if(!noOverlap) {
                throw new IllegalArgumentException("Time conflicts with existing appointment.");
            }
            
            // Update appointment values
            selectedAppointment.setCustomerId(customerId);
            selectedAppointment.setTitle(title);
            selectedAppointment.setDescription(description);
            selectedAppointment.setLocation(location);
            selectedAppointment.setContact(contact);
            selectedAppointment.setType(type);
            selectedAppointment.setUrl(url);
            selectedAppointment.setStart(dbStartDateTime);
            selectedAppointment.setEnd(dbEndDateTime);
            
            // Send updated values to database
            AppointmentDB.updateAppointment(selectedAppointment);

            // Go back to customers screen
            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/AppointmentsScreen.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
            
        }
        // Show alert if schedule error
        catch(Exception ex){ 
            System.out.println("Error: " + ex.getMessage());
            
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Schedule Error");
            alert.setHeaderText("Appointment cannot be saved!");
            alert.setContentText(ex.getMessage());

            alert.showAndWait();
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
