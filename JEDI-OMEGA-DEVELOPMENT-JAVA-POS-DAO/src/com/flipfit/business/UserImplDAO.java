package com.flipfit.business;

import com.flipfit.bean.*;
import com.flipfit.dao.UserDAO;
import com.flipfit.dao.CustomerDAO;
import java.util.UUID;

public class UserImplDAO implements UserInterface {
    private final UserDAO userDAO = new UserDAO();
    private final CustomerDAO customerDAO = new CustomerDAO();

    @Override
    public void registerCustomer(String name, String email, String password, String address, String city) {
        try {
            // Generate unique user ID
            String userId = "U" + UUID.randomUUID().toString().substring(0, 8);
            
            // Register user in users table with CUSTOMER role
            boolean userRegistered = userDAO.registerUser(userId, name, email, password, "0000000000", "CUSTOMER");
            
            if (userRegistered) {
                System.out.println("Customer registered successfully! User ID: " + userId);
                System.out.println("You can now login with email: " + email);
            } else {
                System.out.println("Failed to register customer.");
            }
        } catch (Exception e) {
            System.out.println("Registration failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void registerOwner(String name, String email, String password, String gymName, String address, String city) {
        try {
            String userId = "U" + UUID.randomUUID().toString().substring(0, 8);
            
            // Register as OWNER
            boolean registered = userDAO.registerUser(userId, name, email, password, "0000000000", "OWNER");
            
            if (registered) {
                System.out.println("Gym Owner registered successfully! Pending approval.");
            } else {
                System.out.println("Failed to register owner.");
            }
        } catch (Exception e) {
            System.out.println("Registration failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public User authenticate(String email, String password) {
        try {
            User user = userDAO.authenticateUser(email, password);
            
            if (user != null) {
                return user;
            } else {
                System.out.println("User not found.");
                return null;
            }
        } catch (Exception e) {
            System.out.println("Authentication failed: " + e.getMessage());
            return null;
        }
    }

    @Override
    public boolean updatePassword(String email, String newPassword) {
        try {
            boolean updated = userDAO.updatePassword(email, newPassword);
            if (updated) {
                System.out.println("Password updated successfully!");
                return true;
            } else {
                System.out.println("Failed to update password.");
                return false;
            }
        } catch (Exception e) {
            System.out.println("Password update failed: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean login(String email, String password) {
        User user = authenticate(email, password);
        return user != null;
    }

    @Override
    public void logout() {
        System.out.println("User logged out successfully.");
    }
}
