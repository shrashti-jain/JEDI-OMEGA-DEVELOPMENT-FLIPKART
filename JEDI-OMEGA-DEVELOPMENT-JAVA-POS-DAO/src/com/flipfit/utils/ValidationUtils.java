package com.flipfit.utils;

//TODO: Auto-generated Javadoc
/**
* The Class ValidationUtils.
* A utility class providing static methods for validating user input fields
* such as email addresses, contact numbers, and full names.
* Ensures data integrity before processing or persisting to the database.
*
* @author Shrashti
* @ClassName ValidationUtils
*/
public class ValidationUtils {

	/**
     * Checks if the provided email string follows a valid format.
     * Uses a regular expression to verify standard email patterns (e.g., user@domain.com).
     *
     * @param email the email address to validate
     * @return true if the email format is valid, false otherwise
     */
    public static boolean isValidEmail(String email) {
        return email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
    }

    /**
     * Checks if the provided contact number is valid.
     * Validates that the string consists of exactly 10 numeric digits.
     *
     * @param contact the contact number to validate
     * @return true if the contact number is exactly 10 digits, false otherwise
     */
    public static boolean isValidContact(String contact) {
        return contact.matches("\\d{10}");
    }

    /**
     * Checks if the provided name is a full name.
     * Validates by ensuring the string contains at least one space character,
     * implying a separation between First Name and Last Name.
     *
     * @param name the name string to validate
     * @return true if the name contains a space, indicating a full name
     */
    public static boolean isFullName(String name) {
        return name.trim().contains(" ");
    }
}