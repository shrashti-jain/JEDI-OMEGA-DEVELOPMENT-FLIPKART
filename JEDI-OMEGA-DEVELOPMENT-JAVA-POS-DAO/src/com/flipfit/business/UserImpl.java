package com.flipfit.business;

import com.flipfit.bean.User;
import com.flipfit.dao.UserDAO; // Importing your new DAO

//TODO: Auto-generated Javadoc
/**
* The Class UserImpl.
* Implementation of the User Interface.
* Manages user lifecycle operations including registration, authentication, and password updates.
* Acts as the business logic layer interacting with the UserDAO for database persistence.
*
* @author Krishna Nirvas
* @ClassName UserImpl
*/
public class UserImpl implements UserInterface {

    // Instead of a static list, we use the DAO for database operations
    private UserDAO userDAO = new UserDAO();

    /**
     * Registers a new customer in the system.
     * Generates a unique Customer ID based on the current timestamp.
     * Customers are automatically approved upon registration.
     *
     * @param name the full name of the customer
     * @param email the email address
     * @param password the secure password
     * @param contact the contact number
     * @param address the residential address (currently not stored in User bean but passed for future extension)
     * @param city the city of residence (currently not stored in User bean but passed for future extension)
     */
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

    /**
     * Registers a new Gym Owner in the system.
     * Generates a unique Owner ID based on the current timestamp.
     * New owners are set to 'Pending Approval' (isApproved = false) until validated by an Admin.
     *
     * @param name the full name of the owner
     * @param email the email address
     * @param password the secure password
     * @param contact the contact number
     * @param identityNo the government identity proof number (e.g., Aadhar/PAN)
     * @param role the role string, expected to be "OWNER"
     */
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

    /**
     * Authenticates a user based on email and password.
     * Includes a guard check to prevent unapproved Gym Owners from logging in.
     *
     * @param email the user's email address
     * @param password the user's password
     * @return the User object if authentication is successful and account is active/approved, otherwise null
     */
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

    /**
     * Update password.
     * Updates the password for an existing user in the database.
     *
     * @param email the email of the user
     * @param newPassword the new password to set
     * @return true, if the update was successful
     */
    @Override
    public boolean updatePassword(String email, String newPassword) {
        // Logic to update password in the database via DAO
        return userDAO.updatePassword(email, newPassword);
    }

    /**
     * Login.
     * A boolean wrapper around the authentication method.
     * Used where a simple true/false success status is required instead of the full User object.
     *
     * @param email the email
     * @param password the password
     * @return true, if login is successful
     */
    @Override
    public boolean login(String email, String password) {
        // This is handled via authenticate in the current flow
        return authenticate(email, password) != null;
    }

    /**
     * Logout.
     * Handles user session termination.
     * Currently prints a confirmation message to the console.
     */
    @Override
    public void logout() {
        System.out.println("User session ended.");
    }
}