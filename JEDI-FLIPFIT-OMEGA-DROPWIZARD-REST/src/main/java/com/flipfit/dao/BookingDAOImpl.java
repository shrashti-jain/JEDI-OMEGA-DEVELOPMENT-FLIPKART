package com.flipfit.dao;

import com.flipfit.bean.Booking;
import com.flipfit.utility.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.flipfit.constant.SQLConstants.*;

/**
 * Implementation of BookingDAO for database operations.
 * Merges legacy JOIN-based retrieval with new Integer-ID structure.
 * * @author Shreya / Shrashti (Integrated)
 * @ClassName "BookingDAOImpl"
 */
public class BookingDAOImpl implements BookingDAO {

    /**
     * Inserts a new booking record into the database.
     * Logic uses Integer mapping for userId and slotId as per new schema.
     */
    @Override
    public void createBooking(Booking booking) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(INSERT_BOOKING)) {

            ps.setString(1, booking.getBookingId());
            ps.setInt(2, booking.getUserId());
            ps.setInt(3, booking.getSlotId());
            ps.setDate(4, new java.sql.Date(booking.getSlotDate().getTime()));
            if (booking.getSlotDate() != null) {
                ps.setDate(4, new java.sql.Date(booking.getSlotDate().getTime()));
            } else {
                throw new SQLException("Booking Date is required.");
            }
            ps.setString(5, booking.getStatus());

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace(); // THIS WILL TELL YOU WHY IT ISN'T SAVING
            throw new RuntimeException("Database error: " + e.getMessage());
        }
    }

    /**
     * Cancels a booking by updating its status.
     */
    @Override
    public void cancelBooking(String bookingId) {
        String sql = "UPDATE booking SET status = 'CANCELLED' WHERE booking_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, bookingId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean deleteBooking(String bookingId) {
        String sql = "DELETE FROM booking WHERE booking_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, bookingId); // Use setString for String IDs

            int rowsDeleted = ps.executeUpdate();
            return rowsDeleted > 0;

        } catch (SQLException e) {
            System.err.println("❌ SQL Error deleting booking: " + e.getMessage());
            return false;
        }
    }

    /**
     * Retrieves all bookings for a user with full metadata (Gym Name, Slot Time).
     * MIGRATED FEATURE: Uses JOINs to populate display-ready Booking beans.
     */
    @Override
    public List<Booking> getBookingsByUser(int userId) {
        List<Booking> bookings = new ArrayList<>();
        // Improved SQL with JOINs to match the 'mapResultSetToBooking' logic from old project

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(GET_BOOKING_DETAILS_BY_USER_ID)) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                bookings.add(mapResultSetToBooking(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return bookings;
    }

    /**
     * Checks for existing confirmed bookings at the same time and date.
     * MIGRATED FEATURE: Precise conflict detection using SQL JOINs.
     */
    @Override
    public Booking findBookingConflict(int userId, Date date, String slotTime) {
        // This query finds ANY confirmed booking for this user at this specific time
        String sql = "SELECT b.booking_id, gc.name FROM booking b " +
                "JOIN slot s ON b.slot_id = s.slot_id " +
                "JOIN gym_center gc ON s.center_id = gc.center_id " +
                "WHERE b.user_id = ? AND b.booking_date = ? " +
                "AND s.start_time = ? AND b.status = 'CONFIRMED'";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ps.setDate(2, new java.sql.Date(date.getTime()));
            ps.setString(3, slotTime);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Booking conflict = new Booking();
                conflict.setBookingId(rs.getString("booking_id"));
                conflict.setGymName(rs.getString("name"));
                return conflict;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Booking getBookingById(String bookingId) {

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(GET_BOOKING_BY_BOOKING_ID)) {
            ps.setString(1, bookingId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return mapResultSetToBooking(rs);
        } catch (Exception e) { e.printStackTrace(); }
        return null;
    }

    /**
     * Helper method to map a ResultSet row to a Booking bean.
     */
    private Booking mapResultSetToBooking(ResultSet rs) throws SQLException {
        Booking booking = new Booking();
        booking.setBookingId(rs.getString("booking_id"));
        booking.setSlotDate(rs.getDate("booking_date"));
        booking.setUserId(rs.getInt("user_id"));
        booking.setSlotId(rs.getInt("slot_id"));
        booking.setStatus(rs.getString("status"));

        if (hasColumn(rs, "gym_name")) {
            booking.setGymName(rs.getString("gym_name"));
        }

        // Safely build slotTime only if both columns are present
        if (hasColumn(rs, "start_time") && hasColumn(rs, "end_time")) {
            booking.setSlotTime(rs.getString("start_time") + " - " + rs.getString("end_time"));
        } else if (hasColumn(rs, "start_time")) {
            booking.setSlotTime(rs.getString("start_time"));
        }

        return booking;
    }

    // Helper method to prevent the "Column not found" crash
    private boolean hasColumn(ResultSet rs, String columnName) throws SQLException {
        ResultSetMetaData rsmd = rs.getMetaData();
        int columns = rsmd.getColumnCount();
        for (int x = 1; x <= columns; x++) {
            if (columnName.equalsIgnoreCase(rsmd.getColumnName(x))) {
                return true;
            }
        }
        return false;
    }
}