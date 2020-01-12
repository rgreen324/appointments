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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointment;
import model.User;

/**
 * FXML Controller class
 *
 * @author richard
 */
public class ReportsScheduleScreenController implements Initializable {
    private Stage stage;
    private Parent scene;
    
    @FXML
    private TableView<Appointment> scheduleTable;
    
    @FXML
    private TableColumn<Appointment, LocalDateTime> scheduleStart;

    @FXML
    private TableColumn<Appointment, LocalDateTime> scheduleDate;

    @FXML
    private TableColumn<Appointment, String> scheduleTitle;

    @FXML
    private TableColumn<Appointment, String> scheduleCustomer;
    
    @FXML
    private ComboBox<String> userCB;
    

    // User combobox select actions
    @FXML
    void userCBSelect(ActionEvent event) {
        String userName = userCB.getSelectionModel().getSelectedItem();
        int userId = User.userNameToId(userName);
        
        scheduleTable.setItems(Appointment.getReportAppointments(userId));
        scheduleTable.getSortOrder().add(scheduleStart);
    }
    
    // Back button actions
    @FXML
    void backButton(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/ReportsScreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }
    
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        // Populate schedule table
        scheduleTable.setItems(Appointment.getReportAppointments(0));
        scheduleStart.setCellValueFactory(new PropertyValueFactory<>("localStart"));
        scheduleDate.setCellValueFactory(new PropertyValueFactory<>("reportDate"));
        scheduleTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        scheduleCustomer.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        
        scheduleTable.getSortOrder().add(scheduleStart);
        
        userCB.setItems(User.getUserNames());
        userCB.getSelectionModel().select("All Users");
    }    
    
}
