package com.flipfit.business;

import com.flipfit.bean.Payment;
import com.flipfit.dao.PaymentDAO;
import com.flipfit.dao.PaymentDAOImpl;
import com.flipfit.exception.FlipFitException;

/**
 * Enhanced implementation of Payment operations.
 * Manages transaction processing and payment record retrieval.
 * * @author Shravya / Krishna Nirvas (Integrated)
 * @ClassName "PaymentImpl"
 */
public class PaymentImpl implements PaymentInterface {

    /** The PaymentDAO instance - following interface-based implementation */
    private final PaymentDAO paymentDAO;

    /**
     * Instantiates a new PaymentImpl.
     */
    public PaymentImpl() {
        this.paymentDAO = new PaymentDAOImpl();
    }

    /**
     * Processes a payment for a specific gym booking.
     * MIGRATED FEATURE: Added logging and status verification logic.
     * * @param payment the Payment object containing amount, mode, and bookingId
     * @return true if payment is successfully recorded in the database
     */
    @Override
    public boolean makePayment(Payment payment) {
        // Validation check for payment data
        if (payment == null || payment.getBookingId() == null || payment.getAmount() <= 0) {
            System.err.println("[Payment Error] Invalid payment details provided.");
            return false;
        }

        System.out.println("Processing transaction of ₹" + payment.getAmount() + " for Booking: " + payment.getBookingId());

        // Interacting with the new DAO implementation
        boolean isProcessed = paymentDAO.makePayment(payment);

        if (isProcessed) {
            System.out.println("[SUCCESS] Payment recorded with ID: " + payment.getPaymentId());
        } else {
            System.err.println("[FAILURE] Payment record could not be saved to database.");
        }

        return isProcessed;
    }

    /**
     * Retrieves detailed payment records using the unique booking ID.
     * * @param bookingId the unique string ID of the booking
     * @return Payment bean containing timestamp and status
     * @throws FlipFitException if the record retrieval fails
     */
    @Override
    public Payment viewPaymentByBookingId(String bookingId) {
        if (bookingId == null || bookingId.isBlank()) {
            return null;
        }

        Payment record = paymentDAO.getPaymentByBookingId(bookingId);

        if (record == null) {
            System.out.println("No payment history found for Booking ID: " + bookingId);
        }

        return record;
    }
}