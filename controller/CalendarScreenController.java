/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointment;

/**
 * FXML Controller class
 *
 * @author richard
 */
public class CalendarScreenController implements Initializable {
    private Stage stage;
    private Parent scene;
    
    @FXML
    private TableView<Appointment> calendarTable;

    @FXML
    private TableColumn<Appointment, LocalDateTime> appointmentsStart;
    
    @FXML
    private TableColumn<Appointment, String> appointmentsDate;

    @FXML
    private TableColumn<Appointment, String> appointmentsTime;

    @FXML
    private TableColumn<Appointment, String> appointmentsTitle;
    
    @FXML
    private ToggleGroup calendarSelect;
    
    @FXML
    private RadioButton monthRB;

    @FXML
    private RadioButton weekRB;

    // Month radio button selected
    @FXML
    void monthSelected(ActionEvent event) {
        calendarTable.setItems(Appointment.getMonthAppointments());
        calendarTable.getSortOrder().add(appointmentsStart);
    }

    // Week radio button selected
    @FXML
    void weekSelected(ActionEvent event) {
        calendarTable.setItems(Appointment.getWeekAppointments());
        calendarTable.getSortOrder().add(appointmentsStart);
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
        
        // Populate calendar table
        calendarTable.setItems(Appointment.getMonthAppointments());
        appointmentsStart.setCellValueFactory(new PropertyValueFactory<>("localStart"));
        appointmentsDate.setCellValueFactory(new PropertyValueFactory<>("reportDate"));
        appointmentsTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        appointmentsTime.setCellValueFactory(new PropertyValueFactory<>("reportTime"));
        
        calendarTable.getSortOrder().add(appointmentsStart);
        
        calendarSelect.selectToggle(monthRB);
    }    
    
}
