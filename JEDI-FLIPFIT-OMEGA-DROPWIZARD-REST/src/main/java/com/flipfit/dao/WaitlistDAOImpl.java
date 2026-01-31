package com.flipfit.dao;

import com.flipfit.bean.Waitlist;
import com.flipfit.utility.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class WaitlistDAOImpl implements WaitlistDAO {

    @Override
    public boolean addToWaitlist(int userId, int slotId, Date date) {
        String sql = "INSERT INTO waitlist (user_id, slot_id, booking_date) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ps.setInt(2, slotId);
            ps.setDate(3, new java.sql.Date(date.getTime()));

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Waitlist getNextInLine(int slotId, Date date) {
        // We order by waitlist_id or a timestamp to get the first person who joined
        String sql = "SELECT * FROM waitlist WHERE slot_id = ? AND DATE(booking_date) = DATE(?) ORDER BY waitlist_id ASC LIMIT 1";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, slotId);
            ps.setDate(2, new java.sql.Date(date.getTime()));

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Waitlist entry = new Waitlist();
                entry.setWaitlistId(rs.getInt("waitlist_id"));
                entry.setUserId(rs.getInt("user_id"));
                entry.setSlotId(rs.getInt("slot_id"));
                entry.setBookingDate(rs.getDate("booking_date"));
                return entry;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean removeFromWaitlist(int waitlistId) {
        String sql = "DELETE FROM waitlist WHERE waitlist_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, waitlistId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // In WaitlistDAOImpl.java
    @Override
    public List<Waitlist> getWaitlistByUserId(int userId) {
        List<Waitlist> waitlistEntries = new ArrayList<>();
        // Basic query to fetch entries for the user
        // Use this SQL if you want to display Gym and Time details
        String sql = "SELECT w.waitlist_id, w.user_id, w.slot_id, w.booking_date, c.name, s.start_time " +
                "FROM waitlist w " +
                "JOIN slot s ON w.slot_id = s.slot_id " +
                "JOIN gym_center c ON s.center_id = c.center_id " +
                "WHERE w.user_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Waitlist entry = new Waitlist();

                    // Mapping DB columns to Bean properties
                    entry.setWaitlistId(rs.getInt("waitlist_id"));
                    entry.setUserId(rs.getInt("user_id"));
                    entry.setSlotId(rs.getInt("slot_id"));
                    entry.setCenterName(rs.getString("name"));
                    entry.setSlotTime(rs.getString("start_time"));

                    // Handling SQL Date to Java Date conversion
                    java.sql.Date dbDate = rs.getDate("booking_date");
                    if (dbDate != null) {
                        entry.setBookingDate(new java.util.Date(dbDate.getTime()));
                    }

                    waitlistEntries.add(entry);
                }
            }
        } catch (SQLException e) {
            System.err.println("❌ Error fetching waitlist for user " + userId + ": " + e.getMessage());
        }

        return waitlistEntries;
    }
}