package com.flipfit.dao;

import com.flipfit.bean.GymCenter;
import com.flipfit.constants.SQLConstants;
import com.flipfit.utils.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

//TODO: Auto-generated Javadoc
/**
* The Class GymCenterDAO.
* Data Access Object for handling all database operations related to Gym Centers.
* Includes functionality for adding centers, approval workflows, and fetching lists for different actors.
*
* @author Shrashti
* @ClassName GymCenterDAO
*/
public class GymCenterDAO {
    // Stage 4: Owner adds a new Gym Center for approval
	/**
     * Adds a new Gym Center to the database for Admin approval.
     * Sets the initial approval status to false.
     * Corresponds to Stage 4 of the owner onboarding flow.
     *
     * @param gym the gym center bean object containing details like location, city, and owner ID
     * @return true if the gym center was successfully added to the database
     */
    public boolean addGymCenter(GymCenter gym) {
        //String sql = "INSERT INTO GymCenter (centerId, centerName, location, city, pincode, gstNo, ownerEmail, isApproved) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(SQLConstants.GYM_CENTRE_ADD)) {
            pstmt.setString(1, gym.getCenterId());
            pstmt.setString(2, gym.getCenterName());
            pstmt.setString(3, gym.getLocation());
            pstmt.setString(4, gym.getCity());
            pstmt.setString(5, gym.getPincode());
            pstmt.setString(6, gym.getGstNo());
            pstmt.setString(7, gym.getOwnerEmail());
            pstmt.setBoolean(8, false);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Stage 5: Admin fetches pending gym centers to approve
    /**
     * Retrieves all Gym Centers that are currently pending approval.
     * Used by the Admin to view the list of centers needing verification (Stage 5).
     *
     * @return a List of GymCenter objects where isApproved is false
     */
    public List<GymCenter> getPendingGymCenters() {
        List<GymCenter> pendingList = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(SQLConstants.GYM_CENTRE_GET_ALL_PENDING)) {
            while (rs.next()) {
                pendingList.add(mapResultSetToGymCenter(rs));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return pendingList;
    }

    /**
     * Approves a specific Gym Center by updating its status in the database.
     * Once approved, the gym becomes visible to customers for booking.
     *
     * @param centerId the unique ID of the gym center to be approved
     * @return true if the update operation was successful
     */
    public boolean approveGymCenter(String centerId) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQLConstants.GYM_CENTRE_APPROVE)) {

            if (conn == null) throw new SQLException("Failed to establish database connection.");

            pstmt.setString(1, centerId);
            int rows = pstmt.executeUpdate();
            if (rows == 0) return false;
            return true;

        } catch (SQLException e) {
            System.err.println("Database Error during approval: " + e.getMessage());
            // Log error to a file in a real scenario
            return false;
        } catch (Exception e) {
            System.err.println("Unexpected Error: " + e.getMessage());
            return false;
        }
    }

    // Guard Check: Verify if a center is approved before allowing slot addition
    /**
     * Checks the current approval status of a specific Gym Center.
     * Used as a guard check to prevent operations (like adding slots) on unapproved gyms.
     *
     * @param centerId the unique ID of the gym center
     * @return true if the gym is approved, false otherwise
     */
    public boolean checkApprovalStatus(String centerId) {
        try (Connection conn = DBConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(SQLConstants.CHECK_APPROVE_STATUS)) {
            pstmt.setString(1, centerId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getBoolean("isApproved");
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    /**
     * Retrieves all Gym Centers registered under a specific Gym Owner.
     * Used to populate the Gym Owner's dashboard with their own centers.
     *
     * @param ownerEmail the email of the gym owner
     * @return a List of GymCenter objects owned by the specified email
     */
    // Fetch centers for a specific owner to display in their dashboard
    public List<GymCenter> getCentersByOwnerEmail(String ownerEmail) {
        List<GymCenter> ownerCenters = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(SQLConstants.GYM_CENTRE_GET_BY_OWNER)) {
            pstmt.setString(1, ownerEmail);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                ownerCenters.add(mapResultSetToGymCenter(rs));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return ownerCenters;
    }
    
    // Used by Customers to view only verified gyms in their city
    /**
     * Retrieves a list of approved Gym Centers in a specific city.
     * Used by Customers to find available and verified gyms in their location.
     *
     * @param city the city name to filter by
     * @return a List of approved GymCenter objects in that city
     */
    public List<GymCenter> getApprovedCentersByCity(String city) {
        List<GymCenter> cityCenters = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(SQLConstants.GYM_CENTRE_GET_BY_CITY)) {
            pstmt.setString(1, city);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                cityCenters.add(mapResultSetToGymCenter(rs));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return cityCenters;
    }

    // Delete a center permanently
    /**
     * Permanently deletes a Gym Center from the database based on its ID.
     *
     * @param centerId the unique ID of the gym center to remove
     * @return true if the deletion was successful
     */
    public boolean deleteCenter(String centerId) {
        try (Connection conn = DBConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(SQLConstants.Delete_GYM_CENTR_BY_ID)) {
            pstmt.setString(1, centerId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Retrieves the name of a Gym Center given its ID.
     * Useful for displaying the gym name in booking details or receipts.
     *
     * @param centerId the unique ID of the gym center
     * @return the name of the gym, or "Unknown Gym" if not found
     */
    public String getCenterNameById(String centerId) {
        try (Connection conn = DBConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(SQLConstants.GYM_CENTRE_GET_BY_ID)) {
            pstmt.setString(1, centerId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) return rs.getString("centerName");
        } catch (SQLException e) { e.printStackTrace(); }
        return "Unknown Gym";
    }

    // Helper method to reduce code duplication
    /**
     * Helper method to map a SQL ResultSet row to a GymCenter bean object.
     * Reduces code duplication across multiple fetch methods.
     *
     * @param rs the ResultSet cursor pointing to a valid row
     * @return the mapped GymCenter object
     * @throws SQLException if a column lookup fails
     */
    private GymCenter mapResultSetToGymCenter(ResultSet rs) throws SQLException {
        GymCenter gym = new GymCenter(
                rs.getString("centerId"),
                rs.getString("centerName"),
                rs.getString("location"),
                rs.getString("city"),
                rs.getString("pincode"),
                rs.getString("gstNo"),
                rs.getString("ownerEmail")
        );
        gym.setApproved(rs.getBoolean("isApproved"));
        return gym;
    }

    /**
     * Fetches all gym centers from the database regardless of approval status.
     * Used by the Admin service to perform in-memory filtering using Lambdas.
     * * @return List of all GymCenter objects in the database
     */
    public List<GymCenter> getAllGymCenters() {
        List<GymCenter> allCenters = new ArrayList<>();
        // Using the centralized connection utility
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM GymCenter")) {

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                GymCenter gym = new GymCenter(
                        rs.getString("centerId"),
                        rs.getString("centerName"),
                        rs.getString("location"),
                        rs.getString("city"),
                        rs.getString("pincode"),
                        rs.getString("gstNo"),
                        rs.getString("ownerEmail")
                );
                // Crucial: Set the approval status from the DB for the Lambda filter to work
                gym.setApproved(rs.getBoolean("isApproved"));
                allCenters.add(gym);
            }
        } catch (SQLException e) {
            System.err.println("Error fetching all gym centers: " + e.getMessage());
        }
        return allCenters;
    }
}