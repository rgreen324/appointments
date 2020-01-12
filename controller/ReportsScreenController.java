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
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author richard
 */
public class ReportsScreenController implements Initializable {
    private Stage stage;
    private Parent scene;
    
    // Types button actions
    @FXML
    void typesButton(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/ReportsTypeScreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }
    
    // Schedules button actions
    @FXML
    void schedulesButton(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/ReportsScheduleScreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }
    
    // Contact buttons actions
    @FXML
    void contactButton(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/ReportsContactScreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }
    
    // Log button actions
    @FXML
    void userLogButton(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/ReportsLogScreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
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
    }    
    
}
