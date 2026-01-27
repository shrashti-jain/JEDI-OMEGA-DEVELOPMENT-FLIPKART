package com.flipfit.dao;

import com.flipfit.bean.Slot;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalTime;
import java.util.Date;

/**
 * Data Access Object for Slot operations
 */
public class SlotDAO {
    
    // Add new slot
    public boolean addSlot(String slotId, String centerId, LocalTime startTime, 
                          LocalTime endTime, int capacity, Date date) {
        String sql = "INSERT INTO slots (slot_id, center_id, start_time, end_time, capacity, available_seats, slot_date) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = GetConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, slotId);
            pstmt.setString(2, centerId);
            pstmt.setTime(3, Time.valueOf(startTime));
            pstmt.setTime(4, Time.valueOf(endTime));
            pstmt.setInt(5, capacity);
            pstmt.setInt(6, capacity); // Initially all seats are available
            pstmt.setDate(7, new java.sql.Date(date.getTime()));
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // Get slots by center and date
    public List<Slot> getSlotsByCenterAndDate(String centerId, Date date) {
        List<Slot> slots = new ArrayList<>();
        String sql = "SELECT * FROM slots WHERE center_id = ? AND slot_date = ?";
        
        try (Connection conn = GetConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, centerId);
            pstmt.setDate(2, new java.sql.Date(date.getTime()));
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                Slot slot = new Slot();
                slot.setSlotId(rs.getString("slot_id"));
                slot.setCenterId(rs.getString("center_id"));
                slot.setStartTime(rs.getTime("start_time").toLocalTime());
                slot.setEndTime(rs.getTime("end_time").toLocalTime());
                slot.setCapacity(rs.getInt("capacity"));
                slot.setAvailableSeats(rs.getInt("available_seats"));
                slot.setDate(date);
                slots.add(slot);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return slots;
    }
    
    // Update slot capacity (when booking/cancelling)
    public boolean updateSlotCapacity(String slotId, Date date, int change) {
        String sql = "UPDATE slots SET available_seats = available_seats + ? " +
                    "WHERE slot_id = ? AND slot_date = ? AND available_seats + ? >= 0";
        try (Connection conn = GetConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, change);
            pstmt.setString(2, slotId);
            pstmt.setDate(3, new java.sql.Date(date.getTime()));
            pstmt.setInt(4, change);
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // Check slot availability
    public int getAvailableSeats(String slotId, Date date) {
        String sql = "SELECT available_seats FROM slots WHERE slot_id = ? AND slot_date = ?";
        try (Connection conn = GetConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, slotId);
            pstmt.setDate(2, new java.sql.Date(date.getTime()));
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt("available_seats");
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
