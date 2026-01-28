package com.flipfit.exception;

//TODO: Auto-generated Javadoc
/**
* The Class UserNotFoundException.
* Custom exception thrown when a specific user (Customer, Gym Owner, or Admin)
* cannot be found in the database during login, retrieval, or verification processes.
*
* @author Shrashti
* @ClassName UserNotFoundException
*/
public class UserNotFoundException extends Exception {
	
	/**
     * Instantiates a new User not found exception.
     *
     * @param message the specific error message describing the missing user scenario
     */
    public UserNotFoundException(String message) {
        super(message);
    }
}