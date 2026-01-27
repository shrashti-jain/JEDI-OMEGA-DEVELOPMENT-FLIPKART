package com.flipfit.dao;

import com.flipfit.bean.GymCustomer;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for Customer operations
 * Handles all database operations related to Customers
 */
public class CustomerDAO {
    
    // --- C: Create (Insert) ---
    public void insertCustomer(int id, String name, String email, String contact) {
        String sql = "INSERT INTO Customer (id, Name, Email, Contact) VALUES (?, ?, ?, ?)";
        try (Connection conn = GetConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            pstmt.setString(2, name);
            pstmt.setString(3, email);
            pstmt.setString(4, contact);
            
            int rowsAffected = pstmt.executeUpdate();
            System.out.println(rowsAffected + " customer inserted successfully!");
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    // --- R: Read (Select) ---
    public List<GymCustomer> selectAllCustomers() {
        List<GymCustomer> customers = new ArrayList<>();
        String sql = "SELECT gc.*, u.name, u.email, u.phone FROM gym_customers gc JOIN users u ON gc.user_id = u.user_id";
        try (Connection conn = GetConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            while (rs.next()) {
                GymCustomer customer = new GymCustomer();
                customer.setUserId(rs.getString("user_id"));
                customer.setName(rs.getString("name"));
                customer.setEmail(rs.getString("email"));
                customer.setPhone(rs.getString("phone"));
                customers.add(customer);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customers;
    }
    
    // Get customer by ID
    public GymCustomer getCustomerById(String customerId) {
        String sql = "SELECT * FROM gym_customers WHERE customer_id = ?";
        try (Connection conn = GetConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, customerId);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                GymCustomer customer = new GymCustomer();
                customer.setUserId(rs.getString("user_id"));
                // Set other fields
                return customer;
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    // Get all customers
    public List<GymCustomer> getAllCustomers() {
        List<GymCustomer> customers = new ArrayList<>();
        String sql = "SELECT * FROM gym_customers";
        
        try (Connection conn = GetConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            while (rs.next()) {
                GymCustomer customer = new GymCustomer();
                customer.setUserId(rs.getString("user_id"));
                // Set other fields
                customers.add(customer);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customers;
    }
    
    // --- U: Update ---
    public void updateCustomer(int id, String name, String email, String contact) {
        String sql = "UPDATE Customer SET Name = ?, Email = ?, Contact = ? WHERE id = ?";
        try (Connection conn = GetConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, name);
            pstmt.setString(2, email);
            pstmt.setString(3, contact);
            pstmt.setInt(4, id);
            
            int rowsAffected = pstmt.executeUpdate();
            System.out.println(rowsAffected + " customer updated successfully!");
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    // --- D: Delete ---
    public void deleteCustomer(int id) {
        String sql = "DELETE FROM Customer WHERE id = ?";
        try (Connection conn = GetConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            
            int rowsAffected = pstmt.executeUpdate();
            System.out.println(rowsAffected + " customer deleted successfully!");
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
