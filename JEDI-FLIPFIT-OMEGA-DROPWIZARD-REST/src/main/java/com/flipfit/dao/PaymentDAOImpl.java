package com.flipfit.dao;

import com.flipfit.bean.Payment;
import com.flipfit.utility.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.flipfit.constant.SQLConstants.*;

/**
 * Implementation of PaymentDAO for database operations.
 * Handles persistence and retrieval of transaction records.
 * * @author Shravya / Krishna Nirvas (Integrated)
 * @ClassName "PaymentDAOImpl"
 */
public class PaymentDAOImpl implements PaymentDAO {

    /**
     * Records a new payment transaction in the database.
     * Maps fields from the Payment bean to the INSERT_PAYMENT SQL constant.
     * * @param payment the Payment object containing transaction details
     * @return true if the record was successfully inserted
     */
    @Override
    public boolean makePayment(Payment payment) {

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(INSERT_PAYMENT)) {

            // Setting parameters based on the new Payment table structure
            ps.setString(1, payment.getPaymentId());
            ps.setString(2, payment.getBookingId());
            ps.setDouble(3, payment.getAmount());
            ps.setString(4, payment.getStatus());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error saving payment record for Booking ID " + payment.getBookingId() + ": " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Retrieves payment details using the unique booking ID.
     * Used for audit logs and verifying transaction status.
     * * @param bookingId the unique ID of the gym booking
     * @return Payment object if found, otherwise null
     */
    @Override
    public Payment getPaymentByBookingId(String bookingId) {

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(GET_PAYMENT_BY_BOOKING_ID)) {

            ps.setString(1, bookingId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                // Reconstructing the Payment bean from the database result set
                Payment payment = new Payment();
                payment.setPaymentId(rs.getString(PAYMENT_ID));
                payment.setBookingId(rs.getString(BOOKING_ID));
                payment.setAmount(rs.getDouble(AMOUNT));
                payment.setStatus(rs.getString(STATUS));
                return payment;
            }

        } catch (SQLException e) {
            System.err.println("Error fetching payment for Booking ID " + bookingId + ": " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
}