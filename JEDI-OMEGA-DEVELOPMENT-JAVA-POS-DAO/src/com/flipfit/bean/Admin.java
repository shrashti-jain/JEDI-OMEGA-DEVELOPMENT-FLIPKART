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
     *
     * The role ID is automatically set to 3
     * which represents the ADMIN role.
     *
     * @param userId   the unique user ID
     * @param name     the admin's full name
     * @param email    the admin's email address
     * @param phone    the admin's phone number
     * @param password the admin's login password
     */
    public Admin(int userId, String name, String email,
                 String phone, String password) {

        // role_id = 3 → ADMIN
        super(userId, name, email, phone, password, 3);
    }
}
