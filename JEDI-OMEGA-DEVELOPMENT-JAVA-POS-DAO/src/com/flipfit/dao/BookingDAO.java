package com.flipfit.dao;

import com.flipfit.bean.Booking;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

/**
 * Data Access Object for Booking operations
 */
public class BookingDAO {
    
    // Create new booking
    public boolean createBooking(String bookingId, String userEmail, String centerId, 
                                String slotId, String gymName, Date slotDate, 
                                String slotTime, String status) {
        String sql = "INSERT INTO bookings (booking_id, user_email, center_id, slot_id, gym_name, slot_date, slot_time, status) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = GetConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, bookingId);
            pstmt.setString(2, userEmail);
            pstmt.setString(3, centerId);
            pstmt.setString(4, slotId);
            pstmt.setString(5, gymName);
            pstmt.setDate(6, new java.sql.Date(slotDate.getTime()));
            pstmt.setString(7, slotTime);
            pstmt.setString(8, status);
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // Get bookings by user email
    public List<Booking> getBookingsByUserEmail(String userEmail) {
        List<Booking> bookings = new ArrayList<>();
        String sql = "SELECT * FROM bookings WHERE user_email = ? AND status = 'CONFIRMED' ORDER BY slot_date DESC";
        
        try (Connection conn = GetConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, userEmail);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                Booking booking = new Booking(
                    rs.getString("booking_id"),
                    rs.getString("user_email"),
                    rs.getString("slot_id"),
                    rs.getString("gym_name"),
                    rs.getDate("slot_date"),
                    rs.getString("slot_time"),
                    rs.getString("status")
                );
                bookings.add(booking);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookings;
    }
    
    // Cancel booking
    public boolean cancelBooking(String bookingId) {
        String sql = "UPDATE bookings SET status = 'CANCELLED' WHERE booking_id = ?";
        try (Connection conn = GetConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, bookingId);
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // Check for time conflicts
    public boolean hasTimeConflict(String userEmail, Date slotDate, String slotTime) {
        String sql = "SELECT COUNT(*) as count FROM bookings " +
                    "WHERE user_email = ? AND slot_date = ? AND slot_time = ? AND status = 'CONFIRMED'";
        try (Connection conn = GetConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, userEmail);
            pstmt.setDate(2, new java.sql.Date(slotDate.getTime()));
            pstmt.setString(3, slotTime);
            
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt("count") > 0;
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    // Get booking by ID
    public Booking getBookingById(String bookingId) {
        String sql = "SELECT * FROM bookings WHERE booking_id = ?";
        try (Connection conn = GetConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, bookingId);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return new Booking(
                    rs.getString("booking_id"),
                    rs.getString("user_email"),
                    rs.getString("slot_id"),
                    rs.getString("gym_name"),
                    rs.getDate("slot_date"),
                    rs.getString("slot_time"),
                    rs.getString("status")
                );
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
