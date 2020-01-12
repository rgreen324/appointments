/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package richardgreen_schedulingsystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author richard
 */
public class Database {
    // DB details
    private static final String DRIVER = "com.mysql.jdbc.Driver";
    private static final String DB = "***";
    private static final String URL = "jdbc:mysql://52.206.157.109/" + DB;
    private static final String USER = "***";
    private static final String PASS = "***";
    static Connection conn = null;
    
    // Connect to DB
    public static void connect() throws ClassNotFoundException, SQLException, Exception { 
        Class.forName(DRIVER);
        conn = DriverManager.getConnection(URL, USER, PASS);
    }
    
    // Disconnect from DB
    public static void disconnect() throws ClassNotFoundException, SQLException, Exception { 
        conn.close();
    }
    
    // Get DB connection
    public static Connection getConn() {
        return conn;
    }
    
}
