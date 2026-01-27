package com.flipfit.business;

import com.flipfit.bean.Admin;
import com.flipfit.bean.GymCustomer;
import com.flipfit.bean.GymOwner;
import com.flipfit.bean.User;

import java.util.ArrayList;
import java.util.List;

public class UserImpl implements UserInterface {
    // Static list acts as a temporary database
    private static final List<User> users = new ArrayList<>();

    static {
        // Adding a default Admin for testing
        users.add(new Admin("A1", "Super Admin", "admin@flipfit.com", "1234567890", "pass123"));
    }

    @Override
    public void registerCustomer(String name, String email, String password, String address, String city) {
        String id = "C" + (users.size() + 1);
        users.add(new GymCustomer(id, name, email, "0000000000", password));
        System.out.println("Customer registered successfully in system!");
    }

    @Override
    public void registerOwner(String name, String email, String password, String gymName, String address, String city) {
        String id = "O" + (users.size() + 1);
        users.add(new GymOwner(id, name, email, "0000000000", password));
        System.out.println("Gym Owner registered successfully! Pending approval.");
    }

    @Override
    public User authenticate(String email, String password) {
        for (User u : users) {
            // use equalsIgnoreCase for email, but equals for password
            if (u.getEmail().equalsIgnoreCase(email)) {
                if (u.getPassword().equals(password)) {
                    return u;
                } else {
                    System.out.println("Invalid Password!");
                    return null;
                }
            }
        }
        System.out.println("User not found.");
        return null;
    }

    @Override
    public boolean updatePassword(String email, String newPassword) {
        for (User u : users) {
            if (u.getEmail().equalsIgnoreCase(email)) {
                // ACTUALLY UPDATE THE DATA
                u.setPassword(newPassword);
                System.out.println("Success: Password updated in system for: " + email);
                return true;
            }
        }
        System.out.println("Error: User with email " + email + " not found.");
        return false;
    }
    @Override
    public boolean login(String email, String password) {
        System.out.println("Checking credentials for: " + email);
        return true; // Simplified for now
    }

    @Override
    public void logout() {
        System.out.println("User logged out.");
    }
}