package com.flipfit.bean;

// TODO: Auto-generated Javadoc
/**
 * The Class GymOwner.
 *
 * This class represents a gym owner in the FlipFit system.
 * A GymOwner extends the {@link User} class and is assigned
 * the OWNER role.
 *
 * Gym owners must be approved by an admin before they can
 * add gym centers and slots.
 *
 * @author Shreya
 * @ClassName "GymOwner"
 */
public class GymOwner extends User {

    /** Approval status of the gym owner */
    private boolean approved;

    /**
     * Instantiates a new GymOwner.
     *
     * The role ID is automatically set to 2
     * which represents the OWNER role.
     *
     * @param userId   the unique user ID
     * @param name     the gym owner's full name
     * @param email    the gym owner's email address
     * @param phone    the gym owner's phone number
     * @param password the gym owner's login password
     */
    public GymOwner(int userId, String name, String email,
                    String phone, String password) {

        // role_id = 2 → OWNER
        super(userId, name, email, phone, password, 2);
        this.approved = false;
    }

    /**
     * Checks if the gym owner is approved.
     *
     * @return true if approved, false otherwise
     */
    public boolean isApproved() {
        return approved;
    }

    /**
     * Sets the approval status of the gym owner.
     *
     * @param approved the approval status
     */
    public void setApproved(boolean approved) {
        this.approved = approved;
    }
}
