package com.flipfit.bean;

// TODO: Auto-generated Javadoc
/**
 * The Class Admin.
 * Represents an Administrator user in the FlipFit system.
 * Admins have the highest level of access, capable of approving gym owners,
 * verifying gym centers, and overseeing the entire platform.
 *
 * @author Shreya
 * @ClassName Admin
 */
public class Admin extends User {

    /**
     * Instantiates a new Admin.
     * Initializes the Admin user with the necessary personal and security details.
     * Calls the superclass User constructor to set the base attributes.
     *
     * @param userId the unique identifier for the admin
     * @param name the full name of the admin
     * @param email the email address
     * @param phone the contact number
     * @param password the login password
     * @param identityNo the identity proof number (if required for admin verification)
     */
    public Admin(String userId, String name, String email, String phone, String password, String identityNo) {
        // Note: The role is currently passed as "OWNER" here based on the snippet provided.
        // In a standard implementation, this might expected to be "ADMIN".
        super(userId, name, email, phone, password, "OWNER", identityNo, false);
    }
}