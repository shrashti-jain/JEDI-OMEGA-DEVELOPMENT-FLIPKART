package com.flipfit.bean;

// TODO: Auto-generated Javadoc
/**
 * The Class GymOwner.
 * Represents a specialized User who owns and manages gym centers.
 * This class extends the generic User entity and includes specific flags
 * for administrative approval, which is required before the owner can perform operations.
 *
 * @author Shreya
 * @ClassName GymOwner
 */
public class GymOwner extends User {

    /** The is approved flag. */
    private boolean isApproved; // For Admin validation logic in diagram

    /**
     * Instantiates a new Gym Owner.
     * Sets the user role to "OWNER" and initializes the approval status to false.
     *
     * @param userId the unique user identifier
     * @param name the full name of the owner
     * @param email the email address
     * @param phone the contact number
     * @param password the login password
     * @param identityNo the government identity proof number
     */
    public GymOwner(String userId, String name, String email, String phone, String password, String identityNo) {
        super(userId, name, email, phone, password, "OWNER", identityNo, false);
        this.isApproved = false;
    }

    /**
     * Checks if is approved.
     * Returns the current approval status of the gym owner.
     *
     * @return true, if the owner is approved by an admin
     */
    @Override
    public boolean isApproved() {
        return isApproved;
    }

    /**
     * Sets the approved.
     * Updates the approval status of the gym owner.
     * Typically called by the Admin service after verification.
     *
     * @param approved the new approval status
     */
    @Override
    public void setApproved(boolean approved) {
        isApproved = approved;
    }
}