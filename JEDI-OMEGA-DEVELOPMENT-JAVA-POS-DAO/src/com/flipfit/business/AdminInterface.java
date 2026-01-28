package com.flipfit.business;

import com.flipfit.bean.GymCenter;
import com.flipfit.bean.User;

import java.util.List;

//TODO: Auto-generated Javadoc
/**
* The Interface AdminInterface.
* Defines the administrative operations available in the FlipFit system,
* including approval workflows for owners and gym centers.
*
* @author Krishna Nirvas
* @ClassName AdminInterface
*/
public interface AdminInterface {

	/**
     * Gets the pending owners.
     * Retrieves a list of gym owners awaiting administrative approval.
     *
     * @return the list of pending owners
     */
    List<User> getPendingOwners();

    /**
     * Gets the pending centers.
     * Retrieves a list of gym centers awaiting administrative approval.
     *
     * @return the list of pending centers
     */
    List<GymCenter> getPendingCenters();

    /**
     * Removes the center.
     * Permanently deletes a gym center from the system.
     *
     * @param removeId the ID of the center to remove
     * @return true, if successful
     */
    boolean removeCenter(String removeId);

    /**
     * Approve center.
     * Validates and approves a gym center, making it available for customers.
     *
     * @param approveId the ID of the center to approve
     * @return true, if successful
     */
    boolean approveCenter(String approveId);

    /**
     * Approve owner.
     * Validates and approves a gym owner's account.
     *
     * @param ownerEmail the email of the owner to approve
     * @return true, if successful
     */
    boolean approveOwner(String ownerEmail);

    /**
     * Remove owner.
     * Permanently deletes a gym owner's account from the system.
     *
     * @param ownerEmail the email of the owner to remove
     * @return true, if successful
     */
    boolean removeOwner(String ownerEmail);

    /**
     * Gets the owners by status.
     * Filters the list of gym owners based on their approval status.
     *
     * @param b the approval status (true for approved, false for pending)
     * @return the list of users (owners) matching the status
     */
    List<User> getOwnersByStatus(boolean b);

    /**
     * Gets the gym centers by status.
     * Filters the list of gym centers based on their approval status.
     *
     * @param b the approval status (true for approved, false for pending)
     * @return the list of gym centers matching the status
     */
    List<GymCenter> getGymCentersByStatus(boolean b);
}