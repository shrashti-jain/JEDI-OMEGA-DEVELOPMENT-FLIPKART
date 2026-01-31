package com.flipfit.business;

import com.flipfit.bean.GymCustomer;
import com.flipfit.bean.GymOwner;
import com.flipfit.bean.User;
import com.flipfit.dao.*;
import com.flipfit.exception.RegistrationNotCompletedException;
import com.flipfit.exception.UserNotFoundException;

/**
 * Implementation of User operations.
 * Merges legacy approval logic with new relational database structure.
 * * @author Shreya / Krishna Nirvas (Integrated)
 * @ClassName "UserImpl"
 */
public class UserImpl implements UserInterface {

    private final UserDAO userDAO = new UserDAOImpl();
    private final GymOwnerDAO gymOwnerDAO = new GymOwnerDAOImpl();

    /**
     * Registers a new customer.
     * Logic: Inserts into 'users' table, fetches ID, then inserts into 'gym_customer'.
     */
    @Override
    public void registerCustomer(String name, String email, String password, String contact, String address, String city) {
        // Step 1: Create User Bean (Defaulted to Customer Role)
        User customer = new GymCustomer(0, name, email, contact, password, address, city);

        boolean userCreated = userDAO.registerUser(customer);
        if (!userCreated) {
            throw new RegistrationNotCompletedException("Customer registration failed at user creation.");
        }

        // Step 2: Retrieve the newly created User to get the DB-generated Integer ID
        User createdUser = userDAO.authenticate(email, password);
        if (createdUser == null) {
            throw new UserNotFoundException("Customer account created but could not be verified.");
        }

        // Step 3: Create the profile entry in gym_customer table
        int userId = createdUser.getUserId();
        GymCustomerDAO gymCustomerDAO = new GymCustomerDAOImpl();
        boolean profileCreated = gymCustomerDAO.createCustomer(userId, address, city);

        if (!profileCreated) {
            throw new RegistrationNotCompletedException("User created, but customer profile mapping failed.");
        }

        System.out.println("Customer registered successfully! You can now log in.");
    }

    /**
     * Registers a gym owner.
     * Logic: New owners are registered as NOT approved until Admin validation.
     */
    @Override
    public void registerOwner(String name, String email, String password, String contact, String identityNo) {
        // Step 1: Create Owner Bean
        User owner = new GymOwner(0, name, email, contact, password);

        boolean userCreated = userDAO.registerUser(owner);
        if (!userCreated) {
            throw new RegistrationNotCompletedException("Gym Owner registration failed.");
        }

        // Step 2: Fetch generated ID
        User createdUser = userDAO.authenticate(email, password);
        if (createdUser == null) {
            throw new UserNotFoundException("Owner account created but retrieval failed.");
        }

        // Step 3: Insert into gym_owner table with 'Pending' status
        int userId = createdUser.getUserId();
        boolean ownerEntryCreated = gymOwnerDAO.createOwner(userId, identityNo);

        if (!ownerEntryCreated) {
            throw new RegistrationNotCompletedException("Owner registered but approval tracking failed.");
        }

        System.out.println("Registration request sent! Your profile is pending Admin approval.");
    }

    @Override
    public int getUserIdByEmail(String userEmail) {
        return userDAO.getUserIdByEmail(userEmail);
    }

    /**
     * Authenticates a user.
     * MIGRATED FEATURE: Prevents unapproved owners from accessing the system.
     */
    @Override
    public User authenticate(String email, String password) {
        User user = userDAO.authenticate(email, password);

        if (user == null) {
            throw new UserNotFoundException("Invalid email or password.");
        }

        // Legacy Guard Check: Deny login if owner is not approved
        // Note: role_id 2 corresponds to OWNER in your new schema
        if (user.getRoleId() == 2 && !user.isApproved()) {
            System.out.println("\n[ACCESS DENIED] Your profile is still pending Admin approval.");
            return null;
        }

        return user;
    }

    @Override
    public boolean updatePassword(String email, String newPassword) {
        return userDAO.updatePassword(email, newPassword);
    }

    @Override
    public boolean login(String email, String password) {
        return authenticate(email, password) != null;
    }

    @Override
    public void logout() {
        System.out.println("User session ended. Thank you for using FlipFit!");
    }
}