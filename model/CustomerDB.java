/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import com.mysql.jdbc.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import static java.time.LocalDateTime.now;
import richardgreen_schedulingsystem.Database;

/**
 *
 * @author richard
 */
public class CustomerDB {
//    private static ObservableList<Customer> customers = FXCollections.observableArrayList();
    
    private static Connection conn = (Connection) Database.getConn();
    private static String userName = UserDB.getCurrentUser().getUserName();
    private static int countryId;
    private static int cityId;
    private static int addressId;
    
    
    // Check for matching country in database and return countryId
    public static int customerCountry(String country) throws Exception {
        try {
            String getCountry = "SELECT countryId FROM country WHERE country = ?";
            PreparedStatement customerCountry = conn.prepareStatement(getCountry);
            customerCountry.setString(1, country);
            ResultSet result = customerCountry.executeQuery();
            
            if(result.next()) {
                countryId = result.getInt("countryId");
            }
            // if no match found, add new country to database
            else {
                countryId = 0;
                addCountry(country);
            }
            if(countryId == 0){
                customerCountry(country);
            }
            
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
        
        return countryId;
    }
    
    // Add new country to database
    public static void addCountry(String country) throws SQLException {
        try {        
            String newCountry = "INSERT INTO country(country, createDate, createdBy, lastUpdate, lastUpdateBy)"
                            + "VALUES("
                            + "'" + country + "', "
                            + "'" + now() + "', "
                            + "'" + userName + "', "
                            + "'" + now() + "', "
                            + "'" + userName + "')";
            Statement addCountry = conn.createStatement();
            addCountry.executeUpdate(newCountry);
            
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }
    
    // Check for matching city in database and return cityId
    public static int customerCity(String city, int countryId) throws Exception {
        try {
            String getCity = "SELECT cityId FROM city WHERE city = ? AND countryId = ?";
            PreparedStatement customerCity = conn.prepareStatement(getCity);
            customerCity.setString(1, city);
            customerCity.setInt(2, countryId);
            ResultSet result = customerCity.executeQuery();
            
            if(result.next()) {
                cityId = result.getInt("cityId");
            }
            // if no match found, add new city to database
            else {
                cityId = 0;
                addCity(city, countryId);
            }
            if(cityId == 0) {
                customerCity(city, countryId);
            }
            
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
        return cityId;
    }
    
    // Add new city to database
    public static void addCity(String city, int countryId) throws SQLException {
        try {        
                String newCity = "INSERT INTO city(city, countryId, createDate, createdBy, lastUpdate, lastUpdateBy)"
                        + "VALUES("
                        + "'" + city + "', "
                        + "'" + countryId + "', "
                        + "'" + now() + "', "
                        + "'" + userName + "', "
                        + "'" + now() + "', "
                        + "'" + userName + "')";
                Statement addCity = conn.createStatement();
                addCity.executeUpdate(newCity);
            
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }
    
    
    // Add new address to database
    public static int customerAddress(int cityId, String address, String address2, String postalCode, String phone) throws SQLException {
        try {        
                String newAddress = "INSERT INTO address(address, address2, cityId, postalCode, phone, createDate, createdBy, lastUpdate, lastUpdateBy)"
                        + "VALUES("
                        + "'" + address + "', "
                        + "'" + address2 + "', "
                        + "'" + cityId + "', "
                        + "'" + postalCode + "', "
                        + "'" + phone + "', "
                        + "'" + now() + "', "
                        + "'" + userName + "', "
                        + "'" + now() + "', "
                        + "'" + userName + "')";
                Statement addAddress = conn.createStatement();
                addAddress.executeUpdate(newAddress);
                
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
        
        // Get addressId from database
        try {
            String getAddress = "SELECT addressId FROM address WHERE address = ? AND phone = ?";
            PreparedStatement customerAddress = conn.prepareStatement(getAddress);
            customerAddress.setString(1, address);
            customerAddress.setString(2, phone);
            ResultSet result = customerAddress.executeQuery();
            
            if(result.next()) {
                addressId = result.getInt("addressId");
            }

        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
        
        return addressId;
    }
    
    
    // Add new customer to database
    public static void customerName(int addressId, String name) throws SQLException {
        try {        
                String newName = "INSERT INTO customer(customerName, addressId, active, createDate, createdBy, lastUpdate, lastUpdateBy)"
                        + "VALUES("
                        + "'" + name + "', "
                        + "'" + addressId + "', "
                        + "'" + 1 + "', "
                        + "'" + now() + "', "
                        + "'" + userName + "', "
                        + "'" + now() + "', "
                        + "'" + userName + "')";
                Statement addName = conn.createStatement();
                addName.executeUpdate(newName);
                
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }
    
    
    // Populate database with new customer data
    public static void newCustomer(String country, String city, String address, String address2, String postalCode, String phone, String name) throws Exception{
        // country table
        int newCountryId = customerCountry(country);
        // city table
        int newCityId = customerCity(city, newCountryId);
        // address table
        int newAddressId = customerAddress(newCityId, address, address2, postalCode, phone);
        // customer table
        customerName(newAddressId, name);
    }
    
    // Update customer data
    public static void updateCustomer(int customerId, int addressId, String country, String city, String address, String address2, String postalCode, String phone, String name) throws Exception{
        try {        
                String newName = "UPDATE customer SET "
                        + "customerName = " + "'" + name + "', "
                        + "lastUpdate = " + "'" + now() + "', "
                        + "lastUpdateBy = " + "'" + userName + "'"
                        + "WHERE customerId = " + customerId;
                Statement updateName = conn.createStatement();
                updateName.executeUpdate(newName);
                
                int updateCountryId = customerCountry(country);
                int updateCityId = customerCity(city, updateCountryId);
                
                String newAddress = "UPDATE address SET "
                        + "address = " + "'" + address + "', "
                        + "address2 = " + "'" + address2 + "', "
                        + "cityId = " + "'" + updateCityId + "', "
                        + "postalCode = " + "'" + postalCode + "', "
                        + "phone = " + "'" + phone + "', "
                        + "lastUpdate = " + "'" + now() + "', "
                        + "lastUpdateBy = " + "'" + userName + "'"
                        + "WHERE addressId = " + addressId;
                Statement updateAddress = conn.createStatement();
                updateAddress.executeUpdate(newAddress);

        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }
    
    // Delete customer data
    public static void deleteCustomer(int customerId, int addressId) throws Exception{
        try {        
            String deleteCustomerRecord = "DELETE FROM customer WHERE customerId = " + customerId;
            Statement deleteCustomer = conn.createStatement();
            deleteCustomer.executeUpdate(deleteCustomerRecord);
            
            String deleteAddressRecord = "DELETE FROM address WHERE addressId = " + addressId;
            Statement deleteAddress = conn.createStatement();
            deleteAddress.executeUpdate(deleteAddressRecord);

        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }
    
    // Generate customers from database and add to observable list
    public static void generateCustomers() throws SQLException {
        try {        
                String selectCustomers = "SELECT customer.customerId, customer.customerName,\n" +
                        "address.addressId, address.address, address.address2, address.postalCode, address.phone,\n" +
                        "city.city, country.country\n" +
                        "FROM customer\n" +
                        "JOIN address ON customer.addressId = address.addressId\n" +
                        "JOIN city ON address.cityId = city.cityId\n" +
                        "JOIN country ON city.countryId = country.countryId\n" +
                        "ORDER BY customer.customerName;";
                Statement getCustomers = conn.createStatement();
                ResultSet result = getCustomers.executeQuery(selectCustomers);
                
                while(result.next()) {
                    int newCustomerId = result.getInt("customerId");
                    String newCustomerName = result.getString("customerName");
                    int newAddressId = result.getInt("addressId");
                    String newAddress = result.getString("address");
                    String newAddress2 = result.getString("address2");
                    String newPostalCode = result.getString("postalCode");
                    String newPhone = result.getString("phone");
                    String newCity = result.getString("city");
                    String newCountry = result.getString("country");
                    
                    Customer newCustomer = new Customer(newCustomerId);
                    newCustomer.setName(newCustomerName);
                    newCustomer.setAddressId(newAddressId);
                    newCustomer.setAddress(newAddress);
                    newCustomer.setAddress2(newAddress2);
                    newCustomer.setPostalCode(newPostalCode);
                    newCustomer.setPhone(newPhone);
                    newCustomer.setCity(newCity);
                    newCustomer.setCountry(newCountry);
                    Customer.addCustomer(newCustomer);
            }
            
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }

    
    
}
