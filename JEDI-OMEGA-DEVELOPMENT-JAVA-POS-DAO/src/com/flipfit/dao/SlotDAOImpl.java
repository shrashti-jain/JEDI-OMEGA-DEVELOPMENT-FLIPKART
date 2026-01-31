package com.flipfit.dao;

import com.flipfit.bean.Slot;
import com.flipfit.utility.DBConnection;
import java.sql.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static com.flipfit.constant.SQLConstants.*;

/**
 * Implementation of SlotDAO for database operations.
 * Manages gym time slots, inventory levels, and capacity updates.
 * * @author Shreya / Shrashti (Integrated)
 * @ClassName "SlotDAOImpl"
 */
public class SlotDAOImpl implements SlotDAO {

    /**
     * Adds a new slot for a gym center.
     */
    @Override
    public boolean addSlot(Slot slot) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(INSERT_SLOT)) {

            ps.setInt(1, slot.getCenterId());
            ps.setTime(2, Time.valueOf(slot.getStartTime())); // Maps LocalTime to SQL Time
            ps.setTime(3, Time.valueOf(slot.getEndTime()));
            ps.setInt(4, slot.getCapacity());
            ps.setInt(5, slot.getCapacity());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Fetches slot details using slot ID.
     * MIGRATED FEATURE: Precise mapping of time fields to LocalTime objects.
     */
    @Override
    public Slot getSlotById(int slotId) {
        String sql = "SELECT * FROM slot WHERE slot_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, slotId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Slot slot = new Slot();
                slot.setSlotId(rs.getInt("slot_id"));

                // 🔥 THE FIX: Make sure you are setting the StartTime!
                // If your DB column is a TIME type, use getObject or getTime
                java.sql.Time time = rs.getTime("start_time");
                if (time != null) {
                    slot.setStartTime(time.toLocalTime());
                }

                // Also ensure you set EndTime and AvailableSeats
                java.sql.Time endTime = rs.getTime("end_time");
                if (endTime != null) {
                    slot.setEndTime(endTime.toLocalTime());
                }
                slot.setAvailableSeats(rs.getInt("available_seats"));

                return slot;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Retrieves all slots for a center.
     * MIGRATED FEATURE: Updates the AvailableSeats field for live display.
     */
    @Override
    public List<Slot> getSlotsByCenterAndDate(int centerId, java.util.Date date) {
        List<Slot> slots = new ArrayList<>();
        // It is best practice to use specific column names in your query
        String sql = "SELECT * FROM slot WHERE center_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, centerId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Slot slot = new Slot(
                        rs.getInt("slot_id"),
                        rs.getInt("center_id"),
                        rs.getTime("start_time").toLocalTime(), // Matching your DB column name
                        rs.getTime("end_time").toLocalTime(),   // Matching your DB column name
                        null,
                        rs.getInt("capacity")
                );

                // FIX: Pull the actual live seats from the database
                slot.setAvailableSeats(rs.getInt("available_seats"));
                slots.add(slot);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return slots;
    }

    /**
     * Decrements the available seats in a slot after a booking.
     */
    @Override
    public boolean decreaseAvailableSeats(int slotId) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(DECREASE_AVAILABLE_SEATS)) {

            ps.setInt(1, slotId);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * MIGRATED FEATURE: Restores seats to the slot after a cancellation.
     * Essential for the 'cancelBooking' business logic.
     */
    @Override
    public boolean increaseAvailableSeats(int slotId) {
        // FIX: Change 'availableSeats' to 'available_seats' to match your schema
        String sql = "UPDATE slot SET available_seats = available_seats + 1 WHERE slot_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, slotId);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}