package com.flipfit.business;

import com.flipfit.bean.GymCenter;
import com.flipfit.bean.User;
import com.flipfit.dao.*;
import com.flipfit.exception.ApprovalPendingException;
import com.flipfit.exception.FlipFitException;
import com.flipfit.utility.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of Admin operations for the FlipFit system.
 * Handles approvals, directory filtering using Lambdas, and user management.
 * * @author Shreya / Krishna Nirvas (Integrated)
 * @ClassName AdminImpl
 */
public class AdminImpl implements AdminInterface {

    private final AdminDAO adminDAO = new AdminDAOImpl();
    private final GymOwnerDAO gymOwnerDAO = new GymOwnerDAOImpl();
    private final UserDAO userDAO = new UserDAOImpl();

    /**
     * Approves a gym owner based on their unique User ID.
     * @param userId the numeric user ID as a String
     * @return true if approval is successful
     * @throws ApprovalPendingException if the database update fails
     */
    /**
     * Approves a gym owner using the resolved Integer User ID.
     * Fixed to prevent redundant email lookups.
     */
    @Override
    public boolean configureUser(String userIdStr) {
        try {
            // Convert the String ID back to int
            int uid = Integer.parseInt(userIdStr);

            // Directly call the DAO with the integer ID
            boolean approved = adminDAO.approveOwner(uid);

            if (!approved) {
                throw new FlipFitException("Gym Owner approval failed. ID might be invalid or already approved.");
            }

            return true;
        } catch (NumberFormatException e) {
            throw new FlipFitException("Invalid User ID format.");
        }
    }
    /**
     * Validates and activates a gym center based on its Center ID.
     * @param centerId the numeric center ID as a String
     * @return true if approval is successful
     * @throws ApprovalPendingException if validation fails
     */
    @Override
    public boolean validateCenter(String centerId) {
        try {
            int cid = Integer.parseInt(centerId);
            boolean approved = adminDAO.approveGymCenter(cid);

            if (!approved) {
                throw new ApprovalPendingException("Gym Center approval failed for Center ID: " + cid);
            }
            System.out.println("[Admin] Gym Center " + cid + " is now verified and active.");
            return true;
        } catch (NumberFormatException e) {
            System.err.println("Error: Center ID must be a numeric value.");
            return false;
        }
    }

    /**
     * MIGRATED FEATURE: Retrieves owners based on approval status using Lambda filter.
     * @param status true for approved owners, false for pending
     * @return List of Users matching the status
     */
    @Override
    public List<User> getOwnersByStatus(boolean status) {
        List<User> owners = new ArrayList<>();
        // JOIN is required to see the 'approved' column from the owner table
        // Updated SQL to include identity_no from gym_owner and phone from users
        String sql = "SELECT u.user_id, u.username, u.email, u.phone, o.identity_no " +
                "FROM users u JOIN gym_owner o ON u.user_id = o.user_id " +
                "WHERE u.role_id = 2 AND o.approved = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, status ? 1 : 0);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                User user = new User();
                user.setUserId(rs.getInt("user_id"));
                user.setName(rs.getString("username"));
                user.setEmail(rs.getString("email"));
                user.setPhone(rs.getString("phone"));
                user.setIdentityNo(rs.getString("identity_no"));
                // This ensures the directory actually respects the DB status
                user.setApproved(status);
                owners.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return owners;
    }

    /**
     * MIGRATED FEATURE: Retrieves gym centers based on approval status using Lambda filter.
     * @param approved true for verified centers, false for pending
     * @return List of Gym Centers matching the status
     */
    public List<GymCenter> getGymCentersByStatus(boolean approved) {
        List<GymCenter> allCenters = adminDAO.getAllGymCenters();

        return allCenters.stream()
                .filter(center -> center.isApproved() == approved)
                .collect(Collectors.toList());
    }

    /**
     * MIGRATED FEATURE: Permanently removes a user from the system.
     * @param userId the numeric ID of the user
     * @return true if deletion was successful
     */
    @Override
    public boolean removeUser(int userId) {
        try {
            return adminDAO.deleteUser(userId);
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    public List<User> getAllUsers() {
        return userDAO.getAllUsers();
    }

    // In AdminImpl.java
    @Override
    public boolean removeGymCenter(String centerId) {
        try {
            int cid = Integer.parseInt(centerId);
            return adminDAO.deleteGymCenter(cid); // Calls the new DAO method
        } catch (NumberFormatException e) {
            System.err.println("Invalid Center ID format.");
            return false;
        }
    }
}