/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointment;
import model.AppointmentDB;


/**
 * FXML Controller class
 *
 * @author richard
 */
public class AppointmentsScreenController implements Initializable {
    private Stage stage;
    private Parent scene;
    private Appointment selectedAppointment;
    
    @FXML
    private TableView<Appointment> appointmentsTable;
    
    @FXML
    private TableColumn<Appointment, LocalDateTime> appointmentsStart;

    @FXML
    private TableColumn<Appointment, LocalDateTime> appointmentsDate;

    @FXML
    private TableColumn<Appointment, String> appointmentsTitle;

    @FXML
    private TableColumn<Appointment, String> appointmentsCustomer;
    
    
    // Add button actions
    @FXML
    void addButton(ActionEvent event)  throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/AddAppointmentScreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }
    
    // View button actions
    @FXML
    void viewButton(ActionEvent event) throws IOException {
        selectedAppointment = appointmentsTable.getSelectionModel().getSelectedItem();
        if (selectedAppointment != null) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/ViewAppointmentScreen.fxml"));
            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            scene = loader.load();
            
            ViewAppointmentScreenController controller = loader.getController();
            controller.setSelectedAppointment(selectedAppointment);
            
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }
    
    
    // Delete button actions
    @FXML
    void deleteButton(ActionEvent event) throws IOException, SQLException {
        selectedAppointment = appointmentsTable.getSelectionModel().getSelectedItem();
        if (selectedAppointment != null) {
            // Confirm customer delete
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete Appointment");
            alert.setHeaderText("Confirm Appointment Delete");
            alert.setContentText("Are you sure you want to delete this appointment?");

            Optional<ButtonType> result = alert.showAndWait();
            
            if (result.get() == ButtonType.OK){
                // Delete customer from database
                int appointmentId = selectedAppointment.getAppointmentId();
                AppointmentDB.deleteAppointment(appointmentId);
                Appointment.removeAppointment(selectedAppointment);
            } else {
                // ... user chose CANCEL or closed the dialog
            }
        }
    }
    
    // Main menu button actions
    @FXML
    void mainMenuButton(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }
    
    
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        // Generate appointment lists
        Appointment.generateLists();
        
        // Populate appointments table
        appointmentsTable.setItems(Appointment.getAllAppointments());
        appointmentsStart.setCellValueFactory(new PropertyValueFactory<>("localStart"));
        appointmentsDate.setCellValueFactory(new PropertyValueFactory<>("reportDate"));
        appointmentsTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        appointmentsCustomer.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        
        appointmentsTable.getSortOrder().add(appointmentsStart);
        

        
        

    }    
    
}
