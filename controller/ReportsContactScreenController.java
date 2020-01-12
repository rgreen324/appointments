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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Customer;
import model.CustomerDB;

/**
 * FXML Controller class
 *
 * @author richard
 */
public class ReportsContactScreenController implements Initializable {
    private Stage stage;
    private Parent scene;


    @FXML
    private TableView<Customer> contactTable;

    @FXML
    private TableColumn<Customer, String> contactCustomer;

    @FXML
    private TableColumn<Customer, String> contactPhone;
    
    @FXML
    private TableColumn<Customer, String> contactCountry;
    
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
            
        // Generate customer info
        try { 
            Customer.clearAllCustomers();
            CustomerDB.generateCustomers();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
        
        // Populate customer table    
        contactTable.setItems(Customer.getAllCustomers());
        contactCustomer.setCellValueFactory(new PropertyValueFactory<>("name"));
        contactPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        contactCountry.setCellValueFactory(new PropertyValueFactory<>("country"));

    }    
    
}
