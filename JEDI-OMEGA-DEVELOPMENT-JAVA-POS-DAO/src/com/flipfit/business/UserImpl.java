package com.flipfit.business;

import com.flipfit.bean.User;
import com.flipfit.dao.UserDAO; // Importing your new DAO

public class UserImpl implements UserInterface {

    // Instead of a static list, we use the DAO for database operations
    private UserDAO userDAO = new UserDAO();

    @Override
    public void registerCustomer(String name, String email, String password, String contact, String address, String city) {
        // Generate a unique ID
        String id = "C" + System.currentTimeMillis();

        // Customers are approved by default in our flow
        // Parameters: id, name, email, contact, password, role, identityNo, isApproved
        // Parameters: id, name, email, contact, password, role, identityNo, isApproved
        User newCustomer = new User(id, name, email, contact, password, "CUSTOMER", null, true); // 8 args

        if (userDAO.registerUser(newCustomer)) {
            System.out.println("Customer registered successfully in the database!");
        } else {
            System.out.println("Error: Customer registration failed.");
        }
    }

    @Override
    public void registerOwner(String name, String email, String password, String contact, String identityNo, String role) {
        // Generate a unique ID
        String id = "O" + System.currentTimeMillis();

        // Stage 1: Owners are registered as NOT approved (isApproved = false)
        // Parameters: id, name, email, contact, password, role, identityNo, isApproved
        User ownerRequest = new User(id, name, email, contact, password, "OWNER", identityNo, false); // 8 args

        if (userDAO.registerUser(ownerRequest)) {
            System.out.println("Gym Owner registration request sent! Pending Admin approval.");
        } else {
            System.out.println("Error: Owner registration failed.");
        }
    }

    @Override
    public User authenticate(String email, String password) {
        // Calls DAO to check credentials against the MySQL database
        User user = userDAO.authenticateUser(email, password);

        if (user == null) {
            System.out.println("Invalid Email or Password.");
            return null;
        }

        // Stage 2: Guard check for Owners. If not approved, deny login
        if (user.getRole().equalsIgnoreCase("OWNER") && !user.isApproved()) {
            System.out.println("Notification: Your profile is still pending Admin approval.");
            return null;
        }

        return user;
    }

    @Override
    public boolean updatePassword(String email, String newPassword) {
        // Logic to update password in the database via DAO
        return userDAO.updatePassword(email, newPassword);
    }

    @Override
    public boolean login(String email, String password) {
        // This is handled via authenticate in the current flow
        return authenticate(email, password) != null;
    }

    @Override
    public void logout() {
        System.out.println("User session ended.");
    }
}