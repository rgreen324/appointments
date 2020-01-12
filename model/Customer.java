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
public class Customer {
    private static ObservableList<Customer> customers = FXCollections.observableArrayList();

    
    private int customerId;
    private String name;
    private int addressId;
    private String address;
    private String address2;
    private String postalCode;
    private String phone;
    private String city;
    private String country;

    // Customer constructor
    public Customer(int customerId) {
        this.customerId = customerId;
    }

    // customerId Getter/Setter
    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    // name Getter/Setter
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // addressId Getter/Setter
    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    // address Getter/Setter
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    // address Getter/Setter
    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    // address Getter/Setter
    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    // phone Getter/Setter
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    // city Getter/Setter
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    // country Getter/Setter
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
    
    // Add customer to customers list
    public static void addCustomer(Customer customer) {
        customers.add(customer);
    }
    
    // Remove customer from customers list
    public static void removeCustomer(Customer customer) {
        customers.remove(customer);
    }
    
    // Get customers list
    public static ObservableList<Customer> getAllCustomers() {
        return customers;
    }
    
    // Get customer from customerId
    public static Customer getCustomer(int customerId) {
        Customer customer = null;
        for(Customer c: customers){
            if (c.customerId == customerId) {
                customer = c;
            }
        }
        return customer;
    }
    
    // Print all customers
    public static void printAllCustomers() {
        for(Customer c: customers){
            System.out.println(c.getName());
        }
    }
    
    // Clear all customers
    public static void clearAllCustomers(){
        customers.clear();
    }
    
}
