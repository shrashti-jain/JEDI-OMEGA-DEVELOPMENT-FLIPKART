package com.flipfit.dao;
import com.flipfit.bean.Slot;
import com.flipfit.constants.SQLConstants;
import com.flipfit.utils.DBConnection;

import java.sql.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//TODO: Auto-generated Javadoc
/**
* The Class SlotDAO.
* Data Access Object for handling Slot-related database operations.
* Manages the lifecycle of gym time slots including creation, retrieval, and capacity updates.
*
* @author Shrashti
* @ClassName SlotDAO
*/
public class SlotDAO {

	/**
     * Adds a new slot to an approved gym center.
     * Verified by the business layer before calling this method to ensure the center is approved.
     *
     * @param slot the slot object containing timing, date, and capacity details
     * @return true if the slot was successfully inserted into the database
     */
    public boolean addSlot(Slot slot) {
        //String sql = "INSERT INTO Slot (slotId, centerId, startTime, endTime, slotDate, capacity) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(SQLConstants.SLOT_ADD)) {
            pstmt.setString(1, slot.getSlotId());
            pstmt.setString(2, slot.getCenterId());
            pstmt.setString(3, slot.getStartTime().toString()); // Stores as HH:mm:ss
            pstmt.setString(4, slot.getEndTime().toString());
            pstmt.setDate(5, new java.sql.Date(slot.getDate().getTime()));
            pstmt.setInt(6, slot.getSeats());

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Fetches all slots for a specific center on a specific date.
     * Used by customers to browse available timings for workout sessions.
     *
     * @param centerId the unique ID of the gym center
     * @param date the date for which slots are requested
     * @return a List of Slot objects available on the given date and center
     */
    public List<Slot> getSlotsByCenterAndDate(String centerId, Date date) {
        List<Slot> slots = new ArrayList<>();
        //String sql = "SELECT * FROM Slot WHERE centerId = ? AND slotDate = ?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(SQLConstants.SLOT_GET_BY_CENTER_AND_DATE)) {
            pstmt.setString(1, centerId);
            pstmt.setDate(2, new java.sql.Date(date.getTime()));

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Slot slot = new Slot(
                        rs.getString("slotId"),
                        rs.getString("centerId"),
                        LocalTime.parse(rs.getString("startTime")),
                        LocalTime.parse(rs.getString("endTime")),
                        rs.getDate("slotDate"),
                        rs.getInt("capacity")
                );
                slots.add(slot);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return slots;
    }

    /**
     * Updates the capacity of a slot after a booking or cancellation is made.
     * Decrements seat count for bookings and increments for cancellations.
     *
     * @param slotId the unique ID of the slot
     * @param newCapacity the updated number of available seats
     * @return true if the capacity was successfully updated
     */
    public boolean updateSlotCapacity(String slotId, int newCapacity) {
        //String sql = "UPDATE Slot SET capacity = ? WHERE slotId = ?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(SQLConstants.SLOT_UPDATE_CAPACITY)) {
            pstmt.setInt(1, newCapacity);
            pstmt.setString(2, slotId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Retrieves a specific slot by its unique ID.
     * Used internally to validate slot existence before creating a booking.
     *
     * @param slotId the unique ID of the slot
     * @return the Slot object if found, otherwise null
     */
    public Slot getSlotById(String slotId) {
        //String sql = "SELECT * FROM Slot WHERE slotId = ?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(SQLConstants.SLOT_GET_BY_ID)) {
            pstmt.setString(1, slotId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Slot(
                        rs.getString("slotId"),
                        rs.getString("centerId"),
                        LocalTime.parse(rs.getString("startTime")),
                        LocalTime.parse(rs.getString("endTime")),
                        rs.getDate("slotDate"),
                        rs.getInt("capacity")
                );
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }
}