package com.flipfit.dao;

import com.flipfit.bean.GymCenter;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for Gym Center operations
 */
public class GymCenterDAO {
    
    // Add new gym center
    public boolean addGymCenter(String centerId, String ownerId, String centerName, 
                               String address, String city, String location) {
        String sql = "INSERT INTO gym_centers (center_id, owner_id, center_name, address, city, location, status) " +
                    "VALUES (?, ?, ?, ?, ?, ?, 'PENDING')";
        try (Connection conn = GetConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, centerId);
            pstmt.setString(2, ownerId);
            pstmt.setString(3, centerName);
            pstmt.setString(4, address);
            pstmt.setString(5, city);
            pstmt.setString(6, location);
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // Get gym centers by city
    public List<GymCenter> getCentersByCity(String city) {
        List<GymCenter> centers = new ArrayList<>();
        String sql = "SELECT * FROM gym_centers WHERE city = ? AND status = 'APPROVED'";
        
        try (Connection conn = GetConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, city);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                GymCenter center = new GymCenter();
                center.setCenterId(rs.getString("center_id"));
                center.setCenterName(rs.getString("center_name"));
                center.setLocation(rs.getString("address"));
                center.setCity(rs.getString("city"));
                center.setOwnerEmail(rs.getString("owner_id"));
                centers.add(center);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return centers;
    }
    
    // Get gym centers by owner
    public List<GymCenter> getCentersByOwnerId(String ownerId) {
        List<GymCenter> centers = new ArrayList<>();
        String sql = "SELECT * FROM gym_centers WHERE owner_id = ?";
        
        try (Connection conn = GetConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, ownerId);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                GymCenter center = new GymCenter();
                center.setCenterId(rs.getString("center_id"));
                center.setCenterName(rs.getString("center_name"));
                center.setLocation(rs.getString("address"));
                center.setCity(rs.getString("city"));
                center.setOwnerEmail(rs.getString("owner_id"));
                centers.add(center);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return centers;
    }
    
    // Get center name by ID
    public String getCenterNameById(String centerId) {
        String sql = "SELECT center_name FROM gym_centers WHERE center_id = ?";
        try (Connection conn = GetConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, centerId);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return rs.getString("center_name");
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    // Get all pending centers (for admin approval)
    public List<GymCenter> getPendingCenters() {
        List<GymCenter> centers = new ArrayList<>();
        String sql = "SELECT * FROM gym_centers WHERE status = 'PENDING'";
        
        try (Connection conn = GetConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            while (rs.next()) {
                GymCenter center = new GymCenter();
                center.setCenterId(rs.getString("center_id"));
                center.setCenterName(rs.getString("center_name"));
                center.setLocation(rs.getString("address"));
                center.setCity(rs.getString("city"));
                center.setOwnerEmail(rs.getString("owner_id"));
                centers.add(center);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return centers;
    }
    
    // Approve or reject gym center
    public boolean updateCenterStatus(String centerId, String status) {
        String sql = "UPDATE gym_centers SET status = ? WHERE center_id = ?";
        try (Connection conn = GetConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, status);
            pstmt.setString(2, centerId);
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
