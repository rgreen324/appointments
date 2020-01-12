/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import model.ReportDB;

/**
 * FXML Controller class
 *
 * @author richard
 */
public class ReportsTypeScreenController implements Initializable {
    private Stage stage;
    private Parent scene;
    
    @FXML
    private TextArea reportArea;
    
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
            
        // Populate text area
        try {
            reportArea.setText(ReportDB.appointmentTypesMonth().toString());
        } catch (SQLException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }    
    
}
