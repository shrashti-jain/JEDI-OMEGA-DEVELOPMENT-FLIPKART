package com.flipfit.dao;

import com.flipfit.bean.GymCenter;
import com.flipfit.bean.User;
import com.flipfit.utility.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.flipfit.constant.SQLConstants.*;

/**
 * Implementation of AdminDAO for database operations.
 * Handles approval, retrieval, and deletion of users and centers.
 * * @author Shreya / Krishna Nirvas (Integrated)
 * @ClassName "AdminDAOImpl"
 */
public class AdminDAOImpl implements AdminDAO {

    /**
     * Approves a gym owner using their unique Integer User ID.
     * @param userId the numeric user ID
     * @return true if the users table was updated
     */
    @Override
    public boolean approveOwner(int userId) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(APPROVE_GYM_OWNER)) {

            ps.setInt(1, userId);
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Approves a gym center using its unique Integer Center ID.
     * @param centerId the numeric gym center ID
     * @return true if the gym_center table was updated
     */
    @Override
    public boolean approveGymCenter(int centerId) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(APPROVE_GYM_CENTER)) {

            ps.setInt(1, centerId);
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Fetches all users from the database to support business layer filtering.
     * Necessary for getOwnersByStatus logic.
     * @return List of all User objects
     */
    @Override
    public List<User> getAllOwners() {
        List<User> owners = new ArrayList<>();
        // Uses role_id = 2 for Owners as seen in the schema screenshot
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(GET_ALL_OWNERS)) {

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                User user = new User();
                user.setUserId(rs.getInt("user_id"));
                user.setName(rs.getString("username"));
                user.setEmail(rs.getString("email"));
                user.setApproved(rs.getInt("approved") == 1);
                owners.add(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return owners;
    }

    /**
     * Fetches all gym centers for directory viewing.
     * @return List of all GymCenter objects
     */
    @Override
    public List<GymCenter> getAllGymCenters() {
        List<GymCenter> centers = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(GET_ALL_GYM_CENTER)) {

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                // Mapping logic based on your gym_center table structure
                GymCenter center = new GymCenter();
                center.setCenterId(rs.getInt("center_id"));
                center.setCenterName(rs.getString("name"));
                center.setCity(rs.getString("city"));      // 👈 Must add this
                center.setGstNo(rs.getString("gstNo"));
                center.setApproved(rs.getInt("approved") == 1);
                centers.add(center);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return centers;
    }

    /**
     * Deletes a user record by their ID.
     * @param userId the ID to delete
     * @return true if deletion successful
     */
    @Override
    public boolean deleteUser(int userId) {
        try (Connection conn = DBConnection.getConnection()) {
            // 1. Delete from child tables first
            String deleteCustomer = "DELETE FROM gym_customer WHERE user_id = ?";
            try (PreparedStatement ps1 = conn.prepareStatement(deleteCustomer)) {
                ps1.setInt(1, userId);
                ps1.executeUpdate();
            }

            // 2. Now delete from parent 'users' table
            String deleteUser = "DELETE FROM users WHERE user_id = ?";
            try (PreparedStatement ps2 = conn.prepareStatement(deleteUser)) {
                ps2.setInt(1, userId);
                return ps2.executeUpdate() > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // In AdminDAOImpl.java
    @Override
    public boolean deleteGymCenter(int centerId) {
        // Correct table name 'gym_center' from your schema
        String sql = "DELETE FROM gym_center WHERE center_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, centerId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}