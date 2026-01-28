package com.flipfit.dao;

import com.flipfit.bean.User;
import com.flipfit.constants.SQLConstants;
import com.flipfit.utils.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object (DAO) for User-related database operations.
 * Manages CRUD operations for Customers, Owners, and Admins in MySQL.
 */

public class UserDAO {


    /**
     * Unified registration for both Customers and Owners.
     * Parameters are mapped directly from the User bean.
     */
    public boolean registerUser(User user) {
        //String sql = "INSERT INTO User (userId, name, email, contact, password, role, identityNo, isApproved) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQLConstants.USER_REGISTER)) {

            pstmt.setString(1, user.getUserId());
            pstmt.setString(2, user.getName());
            pstmt.setString(3, user.getEmail());
            pstmt.setString(4, user.getContact());
            pstmt.setString(5, user.getPassword());
            pstmt.setString(6, user.getRole());
            pstmt.setString(7, user.getIdentityNo()); // null for customers
            pstmt.setBoolean(8, user.isApproved());   // true for customers, false for owners

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            if (e.getMessage().contains("chk_contact")) {
                System.out.println("Database Error: Contact must be exactly 10 digits!");
            } else if (e.getMessage().contains("chk_email")) {
                System.out.println("Database Error: Invalid Email format!");
            } else {
                e.printStackTrace();
            }
            return false;
        }
    }

    /**
     * Authenticates user and returns bean if credentials match.
     * Approval logic is handled in UserImpl.
     */
    public User authenticateUser(String email, String password) {
        //String sql = "SELECT * FROM User WHERE email = ? AND password = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQLConstants.USER_AUTHENTICATE)) {

            pstmt.setString(1, email);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new User(
                        rs.getString("userId"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("contact"),
                        rs.getString("password"),
                        rs.getString("role"),
                        rs.getString("identityNo"),
                        rs.getBoolean("isApproved")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Required by UserImpl for the "Change Password" feature.
     */
    public boolean updatePassword(String email, String newPassword) {
        //String sql = "UPDATE User SET password = ? WHERE email = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQLConstants.USER_UPDATE_PASSWORD)) {

            pstmt.setString(1, newPassword);
            pstmt.setString(2, email);

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Used by AdminImpl to view Stage 1 registration requests.
     */
    public List<User> getPendingOwners() {
        List<User> pendingOwners = new ArrayList<>();
        //String sql = "SELECT * FROM User WHERE role = 'OWNER' AND isApproved = false";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(SQLConstants.USER_GET_PENDING_OWNERS)) {

            while (rs.next()) {
                pendingOwners.add(new User(
                        rs.getString("userId"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("contact"),
                        rs.getString("password"),
                        rs.getString("role"),
                        rs.getString("identityNo"),
                        rs.getBoolean("isApproved")
                ));
            }
        } catch (SQLException e) {
            if (e.getMessage().contains("chk_contact")) {
                System.out.println("Database Error: Contact must be exactly 10 digits!");
            } else if (e.getMessage().contains("chk_email")) {
                System.out.println("Database Error: Invalid Email format!");
            } else {
                e.printStackTrace();
            }
        }
        return pendingOwners;
    }

    /**
     * Stage 2: Admin approves the Owner profile.
     */
    public boolean approveOwner(String email) {
        //String sql = "UPDATE User SET isApproved = true WHERE email = ? AND role = 'OWNER'";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQLConstants.USER_APPROVE_OWNER)) {

            pstmt.setString(1, email);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteUserByEmail(String email) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQLConstants.USER_DELETE_BY_EMAIL)) {
            pstmt.setString(1, email);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<User> getAllOwners() {
        List<User> owners = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(SQLConstants.GET_ALL_GYM_OWNERS)) {
            while (rs.next()) {
                owners.add(new User(rs.getString("userId"), rs.getString("name"), rs.getString("email"),
                        rs.getString("contact"), rs.getString("password"), rs.getString("role"),
                        rs.getString("identityNo"), rs.getBoolean("isApproved")));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return owners;
    }
}