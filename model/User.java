/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author richard
 */
public class User {
    private int userId;
    private String userName;
    public static ObservableList<User> users = FXCollections.observableArrayList();
    public static ObservableList<String> userNames = FXCollections.observableArrayList();

    // User constructor
    public User(int userId, String userName) {
        this.userId = userId;
        this.userName = userName;
    }

    // Get userId
    public int getUserId() {
        return userId;
    }

    // Get userName
    public String getUserName() {
        return userName;
    }

    // Get usernames list
    public static ObservableList<String> getUserNames() {
        userNames.clear();
        userNames.add("All Users");
        UserDB.generateUsers();
        return userNames;
    }
    
    // Get userId from userName
    public static int userNameToId(String userName) {
        int userId = 0;
            for(User u: users){
                if (u.userName.equals(userName)) {
                    userId = u.userId;
                }
            }
        return userId;
    }
    
    
    
    
}
