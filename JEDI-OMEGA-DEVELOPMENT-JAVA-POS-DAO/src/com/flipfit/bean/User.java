package com.flipfit.bean;

//TODO: Auto-generated Javadoc
/**
* The Class User.
* Represents a generic user entity in the FlipFit system.
* This bean stores core details common to all roles (Customer, Gym Owner, Admin),
* including authentication credentials and approval status.
*
* @author Shreya
* @ClassName User
*/
public class User {
    private String userId;
    private String name;
    private String email;
    private String contact;

    /**
     * Instantiates a new User.
     *
     * @param userId the unique user identifier
     * @param name the full name of the user
     * @param email the email address
     * @param contact the contact number
     * @param password the login password
     * @param role the role of the user (e.g., CUSTOMER, OWNER, ADMIN)
     * @param identityNo the government identity proof number (for owners)
     * @param isApproved the approval status flag
     */
    public User(String userId, String name, String email, String contact, String password, String role, String identityNo, boolean isApproved) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.contact = contact;
        this.password = password;
        this.role = role;
        this.identityNo = identityNo;
        this.isApproved = isApproved;
    }

    private String password;
    private String role;

    /**
     * Gets the user id.
     *
     * @return the user id
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Sets the user id.
     *
     * @param userId the new user id
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * Gets the name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name.
     *
     * @param name the new name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the email.
     *
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email.
     *
     * @param email the new email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the contact.
     *
     * @return the contact
     */
    public String getContact() {
        return contact;
    }

    /**
     * Sets the contact.
     *
     * @param contact the new contact
     */
    public void setContact(String contact) {
        this.contact = contact;
    }

    /**
     * Gets the password.
     *
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password.
     *
     * @param password the new password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets the role.
     *
     * @return the role
     */
    public String getRole() {
        return role;
    }

    /**
     * Sets the role.
     *
     * @param role the new role
     */
    public void setRole(String role) {
        this.role = role;
    }

    /**
     * Gets the identity no.
     *
     * @return the identity no
     */
    public String getIdentityNo() {
        return identityNo;
    }

    /**
     * Sets the identity no.
     *
     * @param identityNo the new identity no
     */
    public void setIdentityNo(String identityNo) {
        this.identityNo = identityNo;
    }

    /**
     * Checks if is approved.
     *
     * @return true, if is approved
     */
    public boolean isApproved() {
        return isApproved;
    }

    /**
     * Sets the approved.
     *
     * @param approved the new approved status
     */
    public void setApproved(boolean approved) {
        isApproved = approved;
    }

    private String identityNo; // New Field
    private boolean isApproved;


}