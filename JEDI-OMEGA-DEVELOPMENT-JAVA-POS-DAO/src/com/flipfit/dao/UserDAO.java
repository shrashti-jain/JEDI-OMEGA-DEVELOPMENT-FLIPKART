package com.flipfit.dao;

import com.flipfit.bean.User;
import com.flipfit.constants.SQLConstants;
import com.flipfit.utils.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

//TODO: Auto-generated Javadoc
/**
 * Data Access Object (DAO) for User-related database operations.
 * Manages CRUD operations for Customers, Owners, and Admins in MySQL.
 * 
 * @author Shrashti
 * @ClassName UserDAO
 */

public class UserDAO {


	/**
     * Unified registration for both Customers and Owners.
     * Parameters are mapped directly from the User bean.
     * Handles specific constraints like contact length and email format via SQL exceptions.
     *
     * @param user the User bean object containing registration details
     * @return true if the user was successfully registered in the database
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
     * Authenticates a user and returns their profile bean if credentials match.
     * Checks email and password against the database records.
     * Approval logic (checking isApproved) is typically handled in the Service layer (UserImpl).
     *
     * @param email the user's email address
     * @param password the user's password
     * @return the User object if authentication is successful, otherwise null
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
     * Updates the password for a specific user.
     * Required by UserImpl for the "Change Password" feature.
     *
     * @param email the email of the user requesting the password change
     * @param newPassword the new password to set
     * @return true if the password was successfully updated
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
     * Retrieves a list of Gym Owners who are pending approval.
     * Used by AdminImpl to view Stage 1 registration requests.
     *
     * @return a List of User objects representing pending gym owners
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
     * Approves a Gym Owner's profile.
     * Corresponds to Stage 2 of the workflow where Admin validates the owner.
     *
     * @param email the email of the gym owner to approve
     * @return true if the owner was successfully approved
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

    /**
     * Deletes a user account permanently by their email.
     * Useful for account cleanup or removing rejected applications.
     *
     * @param email the email of the user to delete
     * @return true if the user was successfully deleted
     */
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

    /**
     * Retrieves all Gym Owners currently in the system regardless of approval status.
     * Used for administrative reporting or management.
     *
     * @return a List of all User objects with role 'OWNER'
     */
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