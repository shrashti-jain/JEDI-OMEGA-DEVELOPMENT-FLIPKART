package com.flipfit.bean;

// TODO: Auto-generated Javadoc
/**
 * The Class Admin.
 *
 * This class represents an Admin user in the FlipFit system.
 * It extends the {@link User} class and assigns a fixed role ID
 * corresponding to ADMIN privileges.
 *
 * Admin users are responsible for approving gym owners
 * and gym centers in the system.
 *
 * @author Shreya
 * @ClassName "Admin"
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
