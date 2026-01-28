package com.flipfit.business;

import com.flipfit.bean.GymCenter;
import com.flipfit.bean.User;
import com.flipfit.dao.UserDAO;
import com.flipfit.dao.GymCenterDAO;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of Admin operations for the FlipFit system.
 * This class handles the approval of gym owners and centers,
 * as well as user management tasks.
 * * @author YourName
 * @version 1.0
 */
public class AdminImpl implements AdminInterface {


    private UserDAO userDAO = new UserDAO();
    private GymCenterDAO gymCenterDAO = new GymCenterDAO();

    @Override
    public List<User> getPendingOwners() {
        // Fetches owners where isApproved = false from User table
        return userDAO.getPendingOwners();
    }

    /**
     * Approves a pending gym owner based on their email.
     * * @param email The unique email address of the gym owner
     * @return boolean True if approval was successful, false otherwise
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

    @Override
    public List<GymCenter> getPendingCenters() {
        // Stage 5: Fetches centers where isApproved = false
        return gymCenterDAO.getPendingGymCenters();
    }

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
    @Override
    public boolean removeOwner(String email) {
        // This should call UserDAO to delete the user record
        return userDAO.deleteUserByEmail(email);
    }

    @Override
    public boolean removeCenter(String centerId) {
        // Permanently deletes a center from the database
        return gymCenterDAO.deleteCenter(centerId);
    }

    /**
     * Retrieves owners based on approval status using Lambda filter.
     * @param approved Boolean status to filter by
     * @return List of Gym Owners
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
     * @param approved Boolean status to filter by
     * @return List of Gym Centers
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