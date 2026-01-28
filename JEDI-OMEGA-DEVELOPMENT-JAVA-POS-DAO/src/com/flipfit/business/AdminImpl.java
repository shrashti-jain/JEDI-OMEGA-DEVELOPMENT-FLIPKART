package com.flipfit.business;

import com.flipfit.bean.GymCenter;
import com.flipfit.bean.User;
import com.flipfit.dao.UserDAO;
import com.flipfit.dao.GymCenterDAO;

import java.util.List;
import java.util.stream.Collectors;

//TODO: Auto-generated Javadoc
/**
* The Class AdminImpl.
* Implementation of Admin operations for the FlipFit system.
* This class handles the approval of gym owners and centers,
* as well as user management tasks using DAO integration.
*
* @author Krishna Nirvas
* @ClassName AdminImpl
*/
public class AdminImpl implements AdminInterface {


    private UserDAO userDAO = new UserDAO();
    private GymCenterDAO gymCenterDAO = new GymCenterDAO();

    /**
     * Gets the pending owners.
     * Fetches a list of gym owners who have registered but are not yet approved.
     *
     * @return the list of pending owners
     */
    @Override
    public List<User> getPendingOwners() {
        // Fetches owners where isApproved = false from User table
        return userDAO.getPendingOwners();
    }

    /**
     * Approves a pending gym owner based on their email.
     * Validates the owner's request and updates their status in the database.
     *
     * @param email The unique email address of the gym owner
     * @return true, if successful
     */
    @Override
    public boolean approveOwner(String email) {
        // Stage 2: Admin verifies Identity No and flips the flag
        if (userDAO.approveOwner(email)) {
            System.out.println("[Admin] Owner profile for " + email + " has been approved.");
            return true;
        }
        return false;
    }

    /**
     * Gets the pending centers.
     * Retrieves all gym centers that are currently waiting for admin validation.
     *
     * @return the list of pending gym centers
     */
    @Override
    public List<GymCenter> getPendingCenters() {
        // Stage 5: Fetches centers where isApproved = false
        return gymCenterDAO.getPendingGymCenters();
    }

    /**
     * Approves center.
     * Verifies the Gym Center details (GST, Location) and activates it in the system.
     *
     * @param centerId the center id to approve
     * @return true, if approval was successful
     */
    @Override
    public boolean approveCenter(String centerId) {
        // Admin verifies GST and Location details
        if (gymCenterDAO.approveGymCenter(centerId)) {
            System.out.println("[Admin] Gym Center " + centerId + " is now verified and active.");
            return true;
        }
        return false;
    }

    // In AdminImpl.java
    /**
     * Remove owner.
     * Permanently deletes a gym owner's account from the system.
     *
     * @param email the email of the owner to remove
     * @return true, if the user was successfully deleted
     */
    @Override
    public boolean removeOwner(String email) {
        // This should call UserDAO to delete the user record
        return userDAO.deleteUserByEmail(email);
    }

    /**
     * Remove center.
     * Permanently deletes a gym center from the database.
     *
     * @param centerId the center id to delete
     * @return true, if the center was successfully removed
     */
    @Override
    public boolean removeCenter(String centerId) {
        // Permanently deletes a center from the database
        return gymCenterDAO.deleteCenter(centerId);
    }

    /**
     * Retrieves owners based on approval status using Lambda filter.
     * Utilizes Java Streams to filter the full list of owners in memory.
     *
     * @param approved Boolean status to filter by (true for approved, false for pending)
     * @return List of Gym Owners matching the status
     */
    public List<User> getOwnersByStatus(boolean approved) {
        // 1. Fetch all users with role 'OWNER' from DB
        List<User> allOwners = userDAO.getAllOwners();

        // 2. Use Lambda expression to filter by isApproved status
        return allOwners.stream()
                .filter(owner -> owner.isApproved() == approved)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves gym centers based on approval status using Lambda filter.
     * Utilizes Java Streams to filter the full list of centers in memory.
     *
     * @param approved Boolean status to filter by (true for approved, false for pending)
     * @return List of Gym Centers matching the status
     */
    public List<GymCenter> getGymCentersByStatus(boolean approved) {
        // 1. Fetch every gym center from the database
        List<GymCenter> allCenters = gymCenterDAO.getAllGymCenters();

        // 2. Use Lambda expression to filter
        return allCenters.stream()
                .filter(center -> center.isApproved() == approved)
                .collect(Collectors.toList());
    }
}