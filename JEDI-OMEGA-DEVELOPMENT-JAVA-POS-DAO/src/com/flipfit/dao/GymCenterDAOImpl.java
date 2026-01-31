package com.flipfit.dao;

import com.flipfit.bean.GymCenter;
import com.flipfit.utility.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.flipfit.constant.SQLConstants.*;

/**
 * Implementation of GymCenterDAO for database operations.
 * Merges detailed verification fields with new relational Integer-ID structure.
 * * @author Shreya / Shrashti (Integrated)
 * @ClassName "GymCenterDAOImpl"
 */
public class GymCenterDAOImpl implements GymCenterDAO {

    /**
     * Adds a new Gym Center for approval.
     * MIGRATED FEATURE: Includes metadata like city, pincode, and gstNo.
     */
    @Override
    public boolean addGymCenter(GymCenter center) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(INSERT_GYM_CENTER)) {

            // Mapping based on integrated Bean parameters
            ps.setInt(1, center.getOwnerId());
            ps.setString(2, center.getCenterName());
            ps.setString(3, center.getLocation());
            ps.setString(4, center.getCity());
            ps.setString(5, center.getPincode());
            ps.setString(6, center.getGstNo());
            ps.setBoolean(7, false);
            ps.setInt(8, center.getCapacity());// Default: Pending approval

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Fetches all gym centers owned by a specific owner ID.
     */
    @Override
    public List<GymCenter> getCentersByOwner(int ownerId) {
        List<GymCenter> list = new ArrayList<>();
        String sql = "SELECT * FROM gym_center WHERE owner_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, ownerId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapResultSetToGymCenter(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * MIGRATED FEATURE: Guard check to prevent slot addition to unapproved gyms.
     */

    @Override
    public boolean checkApprovalStatus(int centerId) {
        String sql = "SELECT approved FROM gym_center WHERE center_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, centerId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getBoolean("approved");
        } catch (Exception e) { e.printStackTrace(); }
        return false;
    }

    /**
     * Fetches approved centers in a city for customer browsing.
     */
    @Override
    public List<GymCenter> getApprovedCentersByCity(String city) {
        List<GymCenter> centers = new ArrayList<>();
        String sql = "SELECT * FROM gym_center WHERE city = ? AND approved = 1";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, city);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                centers.add(mapResultSetToGymCenter(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return centers;
    }

    /**
     * MIGRATED FEATURE: Helper to map ResultSet to Bean, reducing duplication.
     */
    private GymCenter mapResultSetToGymCenter(ResultSet rs) throws SQLException {
        GymCenter gym = new GymCenter();
        gym.setCenterId(rs.getInt("center_id"));
        gym.setOwnerId(rs.getInt("owner_id"));
        gym.setCenterName(rs.getString("name"));
        gym.setLocation(rs.getString("location"));
        gym.setCity(rs.getString("city"));
        gym.setPincode(rs.getString("pincode"));
        gym.setGstNo(rs.getString("gstNo"));
        gym.setApproved(rs.getBoolean("approved"));
        return gym;
    }

    /**
     * Retrieves center name by ID for display purposes.
     */
    @Override
    public String getCenterNameById(int centerId) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT name FROM gym_center WHERE center_id = ?")) {
            ps.setInt(1, centerId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getString("name");
        } catch (Exception e) { e.printStackTrace(); }
        return "Unknown Gym";
    }
}