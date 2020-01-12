/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import com.mysql.jdbc.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import richardgreen_schedulingsystem.Database;

/**
 *
 * @author richard
 */
public class UserDB {
    
    private static User currentUser = null;
    
    
    // Check user table for active matching userName and password
    public static boolean ValidateUser(String userName, String password) throws Exception {
        
        Connection conn = (Connection) Database.getConn();
        boolean valid = false;
        
        // Check for userName match and get user data
        try {
            String getUser = "SELECT * FROM user WHERE userName = ? AND active = 1";
            PreparedStatement validateUser = conn.prepareStatement(getUser);
            validateUser.setString(1, userName);
            ResultSet result = validateUser.executeQuery();

            // if userName match, check password
            if (result.next()) {
                // validate user and create currentUser
                if (password.equals(result.getString("password"))) {
                    valid = true;
                    currentUser = new User(result.getInt("userId"), userName);
                }
                // No password match
                else {
//                    controller.LogInScreenController.alert("password");
                    throw new IllegalArgumentException("Incorrect password.");
                }
            }
            // No userName match
            else {
//                controller.LogInScreenController.alert("userName");
                throw new NullPointerException("Username not found.");
            }

        } 
        // If incorrect userName
        catch (NullPointerException ex) {
            System.out.println("Error: " + ex.getMessage());
            controller.LogInScreenController.alert("userName");
        }
        // If incorrect password
        catch (IllegalArgumentException ex) {
            System.out.println("Error: " + ex.getMessage());
            controller.LogInScreenController.alert("password");
        }
        
        catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
        
        return valid;
    }
    
    // Get currentUser
    public static User getCurrentUser(){
        return currentUser;
    }
    
    // Get currentUserId
    public static int getCurrentUserId(){
        return currentUser.getUserId();
    }
    
    // Generate users
    public static void generateUsers() {
        try{
            Connection conn = (Connection) Database.getConn();
            
            String getUsers = "SELECT userId, userName FROM user WHERE active = 1";
            Statement generateUsers = conn.createStatement();
            ResultSet result = generateUsers.executeQuery(getUsers);
            
            while(result.next()){
                int userId = result.getInt("userId");
                String userName = result.getString("userName");
                
                User newUser = new User(userId, userName);
                User.users.add(newUser);
                User.userNames.add(newUser.getUserName());
            }
        }
        catch(Exception ex){
            System.out.println("Error: " + ex.getMessage());
        }
    }
}
