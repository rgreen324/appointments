/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author richard
 */
public class ReportsLogScreenController implements Initializable {
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
            
        // Get data from log file and populate textarea
        try {
            File log = new File("src/log.txt");
            Scanner input = new Scanner(log);
            StringBuilder logInfo = new StringBuilder();
            
            while (input.hasNext()) {
                logInfo.append(input.nextLine()).append("\n");
            }
            
            input.close();
            
            reportArea.setText(logInfo.toString());
            
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }    
    
}
