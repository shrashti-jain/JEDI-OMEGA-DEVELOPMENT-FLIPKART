package com.flipfit.business;

import com.flipfit.bean.User;

//TODO: Auto-generated Javadoc
/**
* The Interface UserInterface.
* Defines the contract for User operations in the FlipFit system.
* Includes methods for registration (Customer/Owner), authentication, and password management.
*
* @author Krishna Nirvas
* @ClassName UserInterface
*/
public interface UserInterface {
	/**
     * Register customer.
     * Registers a new customer with the provided details.
     *
     * @param name the customer's full name
     * @param email the customer's email address
     * @param pass the customer's password
     * @param contact the customer's contact number
     * @param addr the customer's address
     * @param city the customer's city
     */
    void registerCustomer(String name, String email, String pass, String contact, String addr, String city);

    /**
     * Register owner.
     * Registers a new gym owner with the provided details.
     *
     * @param name the owner's full name
     * @param email the owner's email address
     * @param pass the owner's password
     * @param contact the owner's contact number
     * @param idNo the owner's government identity proof number
     * @param owner the role string (expected "OWNER")
     */
    void registerOwner(String name, String email, String pass, String contact, String idNo, String owner);

    /**
     * Update password.
     * Updates the password for a specific user identified by email.
     *
     * @param email the user's email address
     * @param confirmPass the new password to set
     * @return true, if the password update was successful
     */
    boolean updatePassword(String email, String confirmPass);

    /**
     * Authenticate.
     * Verifies the user's credentials and returns the User object if valid.
     *
     * @param email the user's email address
     * @param pass the user's password
     * @return the authenticated User object, or null if authentication fails
     */
    User authenticate(String email, String pass);

    /**
     * Login.
     * A boolean check to verify if login credentials are correct.
     *
     * @param email the user's email
     * @param password the user's password
     * @return true, if login is successful
     */
    public boolean login(String email, String password);
    
    /**
     * Logout.
     * Terminates the user's current session.
     */
    public void logout();
    // General registration that returns a User object
   
}