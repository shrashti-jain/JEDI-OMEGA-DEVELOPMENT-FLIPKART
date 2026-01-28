package com.flipfit.bean;

// TODO: Auto-generated Javadoc
/**
 * The Class GymCustomer.
 * Represents a specialized User entity for customers in the FlipFit system.
 * Customers are users who can browse gyms, book slots, and manage their reservations.
 * This class extends the User class and sets the role explicitly to "CUSTOMER".
 *
 * @author Shreya
 * @ClassName GymCustomer
 */
public class GymCustomer extends User {

    /**
     * Instantiates a new Gym Customer.
     * Calls the super constructor of User with the role set to "CUSTOMER".
     * Customers are auto-approved by default (isApproved = true) and do not require identity proof.
     *
     * @param userId the unique user identifier
     * @param name the full name of the customer
     * @param email the email address
     * @param phone the contact number
     * @param password the login password
     */
    public GymCustomer(String userId, String name, String email, String phone, String password) {
        // Must pass 8 arguments to match the updated User bean
        super(userId, name, email, phone, password, "CUSTOMER", null, true);
    }
}