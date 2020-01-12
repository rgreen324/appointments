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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
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
public class CustomersScreenController implements Initializable {
    private Stage stage;
    private Parent scene;
    private Customer selectedCustomer;
    
    
    @FXML
    private TableView<Customer> customersTable;

    @FXML
    private TableColumn<Customer, String> customersName;

    @FXML
    private TableColumn<Customer, String> customersCity;

    @FXML
    private TableColumn<Customer, String> customersPhone;
    
    
    // Delete customer button actions
    @FXML
    void deleteCustomerButton(ActionEvent event) throws Exception {
        selectedCustomer = customersTable.getSelectionModel().getSelectedItem();
        if (selectedCustomer != null) {
            // Confirm customer delete
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Delete Customer");
            alert.setHeaderText("Confirm Customer Delete");
            alert.setContentText("Are you sure you want to delete this customer?");
            
            // Lambda expression used to improve readability of alert response code
            alert.showAndWait().ifPresent((response -> {
                if (response == ButtonType.OK) {
                    try {
                        // Delete customer from database
                        int customerId = selectedCustomer.getCustomerId();
                        int addressId = selectedCustomer.getAddressId();
                        CustomerDB.deleteCustomer(customerId, addressId);
                        Customer.removeCustomer(selectedCustomer);
                    } catch (Exception ex) {
                        System.out.println("Error: " + ex.getMessage());
                    }
                }
            }));
            
            
        }
    }

    // View customer button actions
    @FXML
    void viewCustomerButton(ActionEvent event) throws IOException {
        selectedCustomer = customersTable.getSelectionModel().getSelectedItem();
        if (selectedCustomer != null) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/ViewCustomerScreen.fxml"));
            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            scene = loader.load();
            
            ViewCustomerScreenController controller = loader.getController();
            controller.setSelectedCustomer(selectedCustomer, 0);
            
            stage.setScene(new Scene(scene));
            stage.show();
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
    
    // Add customer button actions
    @FXML
    void addCustomerButton(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/AddCustomerScreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)  {
        // TODO
        
        // Refresh customer objects from database
        try {
            Customer.clearAllCustomers();
            CustomerDB.generateCustomers();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
            
        // Customers TableView
        customersTable.setItems(Customer.getAllCustomers());
        customersName.setCellValueFactory(new PropertyValueFactory<>("name"));
        customersCity.setCellValueFactory(new PropertyValueFactory<>("city"));
        customersPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));

        
    }    
    
}
