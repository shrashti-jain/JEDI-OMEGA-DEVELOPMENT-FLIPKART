package com.flipfit.dao;

import com.flipfit.bean.GymCenter;
import com.flipfit.constants.SQLConstants;
import com.flipfit.utils.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GymCenterDAO {
    // Stage 4: Owner adds a new Gym Center for approval
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
     * Approves a gym center in the database.
     * @param centerId the ID of the gym
     * @return boolean true if updated
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
    public boolean deleteCenter(String centerId) {
        try (Connection conn = DBConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(SQLConstants.Delete_GYM_CENTR_BY_ID)) {
            pstmt.setString(1, centerId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public String getCenterNameById(String centerId) {
        try (Connection conn = DBConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(SQLConstants.GYM_CENTRE_GET_BY_ID)) {
            pstmt.setString(1, centerId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) return rs.getString("centerName");
        } catch (SQLException e) { e.printStackTrace(); }
        return "Unknown Gym";
    }

    // Helper method to reduce code duplication
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