package com.flipfit.dao;

import com.flipfit.bean.User;
import com.flipfit.utility.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.flipfit.constant.SQLConstants.*;

/**
 * Implementation of UserDAO for database operations.
 * Merges legacy error handling and admin logic with the new relational schema.
 * * @author Shreya / Shrashti (Integrated)
 * @ClassName "UserDAOImpl"
 */
public class UserDAOImpl implements UserDAO {

    /**
     * Registers a new user.
     * MIGRATED FEATURE: Catches specific SQL constraint violations for phone and email.
     */
    @Override
    public boolean registerUser(User user) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(INSERT_USER)) {

            stmt.setString(1, user.getName());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getPhone());
            stmt.setInt(5, user.getRoleId());
            stmt.setInt(6, 1); // Sets is_active to true by default

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            // Migrated error handling logic from old UserDAO
            if (e.getMessage().contains("chk_contact")) {
                System.err.println("Database Error: Phone must be exactly 10 digits!");
            } else if (e.getMessage().contains("chk_email")) {
                System.err.println("Database Error: Invalid Email format!");
            } else {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * Authenticates user and returns a populated Bean.
     */
    @Override
    public User authenticate(String email, String password) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(AUTHENTICATE_USER)) {

            ps.setString(1, email);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                User user = new User();
                user.setUserId(rs.getInt("user_id"));
                user.setName(rs.getString("username"));
                user.setEmail(rs.getString("email"));
                user.setRoleId(rs.getInt("role_id"));

                // Logic for Approval Status
                int roleId = rs.getInt("role_id");
                if (roleId == 2) { // Role 2 = Owner
                    // Fetch 'approved' from the joined gym_owner table
                    user.setApproved(rs.getInt("approved") == 1);
                } else {
                    // Customers and Admins are approved by default
                    user.setApproved(true);
                }

                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Updates the password for a specific user email.
     */
    @Override
    public boolean updatePassword(String email, String newPassword) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(UPDATE_PASSWORD)) {

            ps.setString(1, newPassword);
            ps.setString(2, email);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Utility to resolve email to integer ID for cross-table references.
     */
    @Override
    public int getUserIdByEmail(String email) {
        String sql = "SELECT user_id FROM users WHERE email = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt("user_id");

        } catch (Exception e) {
            e.printStackTrace();
        }
        throw new RuntimeException("User not found for email: " + email);
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        // Using a simple SELECT. You can add WHERE isActive = 1 if you only want active users.

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(GET_ALL_USERS)) {

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                User user = new User();
                user.setUserId(rs.getInt("user_id"));
                user.setName(rs.getString("username"));
                user.setEmail(rs.getString("email"));
                user.setPhone(rs.getString("phone"));
                user.setRoleId(rs.getInt("role_id"));

                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    /**
     * MIGRATED FEATURE: Retrieves pending owners for admin approval requests.
     * Uses role_id = 2 to match the "Owner" role in the new schema.
     */
    public List<User> getPendingOwners() {
        List<User> pendingOwners = new ArrayList<>();
        // role_id 2 = OWNER; is_active 0 = Pending
        String sql = "SELECT u.* FROM users u JOIN gym_owner o ON u.user_id = o.user_id \n" +
                "//      WHERE u.role_id = 2 AND o.approved = 0";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                User user = new User();
                user.setUserId(rs.getInt("user_id"));
                user.setName(rs.getString("username"));
                user.setEmail(rs.getString("email"));
                user.setApproved(false);
                pendingOwners.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pendingOwners;
    }
}