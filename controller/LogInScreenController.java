/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Appointment;
import model.UserDB;


/**
 *
 * @author richard
 */
public class LogInScreenController implements Initializable {
    private Stage stage;
    private Parent scene;
    
    private static String loginErrorTitle;
    private static String loginErrorHeaderUsername;
    private static String loginErrorContentUsername;
    private static String loginErrorHeaderPassword;
    private static String loginErrorContentPassword;
    private static final Logger LOG = Logger.getLogger("src/log.txt");

    @FXML
    private Label loginTitle;
    
    @FXML
    private Label loginHeader;
    
    @FXML
    private Label loginUsername;
    
    @FXML
    private Label loginPassword;
    
    @FXML
    private Button loginButton;
    
    @FXML
    private TextField userNameField;

    @FXML
    private PasswordField passwordField;
    
    
    // Initate log file
    public static void intiateLog() throws Exception {
        try {
                FileHandler fh = new FileHandler("src/log.txt", true);
                SimpleFormatter sf = new SimpleFormatter();
                fh.setFormatter(sf);
                LOG.addHandler(fh);
            } catch (Exception ex) {
                System.out.println("Error: " + ex.getMessage());
            }
    }
    
    // Show failed login alert
    public static void alert(String reason) {
        // if incorrect username
        if(reason.equals("userName")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(loginErrorTitle);
            alert.setHeaderText(loginErrorHeaderUsername);
            alert.setContentText(loginErrorContentUsername);
            alert.showAndWait();
        }
        // if incorrect password
        if(reason.equals("password")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(loginErrorTitle);
            alert.setHeaderText(loginErrorHeaderPassword);
            alert.setContentText(loginErrorContentPassword);
            alert.showAndWait();
        }
    }
    
    @FXML
    private void logInButtonClick(ActionEvent event) throws Exception { 
        // Get login field values
        String userName = userNameField.getText();
        String password = passwordField.getText();
        
        // Validate user
        boolean isValidUser = UserDB.ValidateUser(userName, password);
        
        if (isValidUser) {
            Appointment appointmentAlert = Appointment.loginAlert(UserDB.getCurrentUserId());
            
            // Record user login to log file
            LOG.log(Level.INFO, "{0} logged into the system", userName);

            // Go to main screen
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/MainScreen.fxml"));
            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            scene = loader.load();
            
            MainScreenController controller = loader.getController();
            controller.setAppointmentAlert(appointmentAlert);
            
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        // Initiate log
        try {
            intiateLog();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
        
        // Language resource bundle
        rb = ResourceBundle.getBundle("language_files/rb");

        loginTitle.setText(rb.getString("loginTitle"));
        loginHeader.setText(rb.getString("loginHeader"));
        loginUsername.setText(rb.getString("loginUsername"));
        loginPassword.setText(rb.getString("loginPassword"));
        loginButton.setText(rb.getString("loginButton"));
        loginErrorTitle = rb.getString("loginErrorTitle");
        loginErrorHeaderUsername = rb.getString("loginErrorHeaderUsername");
        loginErrorContentUsername = rb.getString("loginErrorContentUsername");
        loginErrorHeaderPassword = rb.getString("loginErrorHeaderPassword");
        loginErrorContentPassword = rb.getString("loginErrorContentPassword");

    }    
    
}
