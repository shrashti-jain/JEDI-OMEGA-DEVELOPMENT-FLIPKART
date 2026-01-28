package com.flipfit.dao;

import com.flipfit.bean.Booking;
import com.flipfit.bean.Slot;
import com.flipfit.utils.DBConnection;
import com.flipfit.constants.SQLConstants;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Data Access Object for Booking related operations.
 * Handles persistence and retrieval of customer reservations.
 */
public class BookingDAO {

    /**
     * Inserts a new booking into the database and returns the populated Bean.
     * @param customerEmail user making the booking
     * @param slotId the slot being reserved
     * @param centerId used to fetch Gym Name for the bean
     * @param date the booking date
     * @return Booking bean with all 7 fields populated
     */
    public Booking createBooking(String customerEmail, String slotId, String centerId, Date date) {
        String bId = "B" + System.currentTimeMillis();
        String gymName = new GymCenterDAO().getCenterNameById(centerId);
        Slot slot = new SlotDAO().getSlotById(slotId);

        if (slot == null) return null;
        String timeRange = slot.getStartTime() + " - " + slot.getEndTime();

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQLConstants.BOOKING_CREATE)) {

            pstmt.setString(1, bId);
            pstmt.setString(2, slotId);
            pstmt.setString(3, customerEmail);
            pstmt.setString(4, "CONFIRMED");

            if (pstmt.executeUpdate() > 0) {
                return new Booking(bId, customerEmail, slotId, gymName, date, timeRange, "CONFIRMED");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Retrieves all bookings for a specific customer.
     * @param customerEmail the customer's unique email
     * @return List of Booking objects
     */
    public List<Booking> getBookingsByCustomer(String customerEmail) {
        List<Booking> bookings = new ArrayList<>();
        // Note: SQLConstants.BOOKING_GET_BY_CUSTOMER should use JOINs to get Gym Name and Slot details
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQLConstants.BOOKING_GET_BY_CUSTOMER)) {

            pstmt.setString(1, customerEmail);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                bookings.add(mapResultSetToBooking(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookings;
    }

    /**
     * Cancels a booking by updating its status in the database.
     * @param bookingId the ID of the booking to cancel
     * @return true if status was updated to CANCELLED
     */
    public boolean cancelBooking(String bookingId) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQLConstants.BOOKING_CANCEL)) {

            pstmt.setString(1, bookingId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Checks if a customer already has a confirmed booking for a specific date and time.
     * @param email customer email
     * @param date requested date
     * @param slotTime requested time range (e.g., "09:00 - 10:00")
     * @return Booking object if conflict exists, null otherwise
     */
    public Booking checkUserConflict(String email, java.util.Date date, String slotTime) {
        // SQL: SELECT * FROM Booking b JOIN Slot s ON b.slotId = s.slotId
        // WHERE b.customerEmail = ? AND s.slotDate = ? AND b.status = 'CONFIRMED'
        String sql = "SELECT b.*, s.startTime, s.endTime, s.slotDate, g.centerName " +
                "FROM Booking b " +
                "JOIN Slot s ON b.slotId = s.slotId " +
                "JOIN GymCenter g ON s.centerId = g.centerId " +
                "WHERE b.customerEmail = ? AND s.slotDate = ? AND b.status = 'CONFIRMED'";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, email);
            pstmt.setDate(2, new java.sql.Date(date.getTime()));
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String timeRange = rs.getString("startTime") + " - " + rs.getString("endTime");
                if (timeRange.equals(slotTime)) {
                    return mapResultSetToBooking(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Booking getBookingById(String bookingId) {
        String sql = "SELECT b.*, s.startTime, s.endTime, s.slotDate, g.centerName " +
                "FROM Booking b " +
                "JOIN Slot s ON b.slotId = s.slotId " +
                "JOIN GymCenter g ON s.centerId = g.centerId " +
                "WHERE b.bookingId = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, bookingId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) return mapResultSetToBooking(rs);
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    /**
     * Helper method to map a ResultSet row to a Booking bean.
     */
    private Booking mapResultSetToBooking(ResultSet rs) throws SQLException {
        return new Booking(
                rs.getString("bookingId"),
                rs.getString("customerEmail"),
                rs.getString("slotId"),
                rs.getString("centerName"),
                rs.getDate("slotDate"),
                rs.getString("startTime") + " - " + rs.getString("endTime"),
                rs.getString("status")
        );
    }
}