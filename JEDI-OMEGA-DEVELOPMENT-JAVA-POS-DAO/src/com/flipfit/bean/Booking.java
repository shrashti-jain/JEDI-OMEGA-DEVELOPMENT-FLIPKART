package com.flipfit.bean;

import java.util.Date;

// TODO: Auto-generated Javadoc
/**
 * The Class Booking.
 * Represents a confirmed reservation made by a user for a specific gym slot.
 * Stores essential details such as the booking ID, user email, gym information,
 * slot timing, and the current status of the booking.
 *
 * @author Shreya
 * @ClassName Booking
 */
public class Booking {
    private String bookingId;
    private String userEmail;
    private String gymName;
    private String slotId;
    private Date slotDate;
    private String slotTime; // e.g., "09:00 - 10:00"
    private String status;

    /**
     * Instantiates a new Booking.
     * Initializes a booking record with all necessary details.
     *
     * @param bookingId the unique identifier for the booking
     * @param userEmail the email of the user making the booking
     * @param slotId the unique identifier of the booked slot
     * @param gymName the name of the gym where the slot is booked
     * @param slotDate the date of the workout session
     * @param slotTime the time range of the slot (e.g., "09:00 - 10:00")
     * @param status the current status of the booking (e.g., "CONFIRMED", "CANCELLED")
     */
    public Booking(String bookingId, String userEmail, String slotId, String gymName, Date slotDate, String slotTime, String status) {
        this.bookingId = bookingId;
        this.userEmail = userEmail;
        this.gymName = gymName;
        this.slotId = slotId;
        this.slotDate = slotDate;
        this.slotTime = slotTime;
        this.status = status;
    }

    /**
     * Sets the status.
     * Updates the current status of the booking.
     *
     * @param status the new status (e.g., "CANCELLED")
     */
    public void setStatus(String status) {
        this.status = status;
    }

    // Getters for all fields

    /**
     * Gets the gym name.
     *
     * @return the name of the gym associated with this booking
     */
    public String getGymName() { return gymName; }

    /**
     * Gets the slot date.
     *
     * @return the date of the booked slot
     */
    public Date getSlotDate() { return slotDate; }

    /**
     * Gets the slot time.
     *
     * @return the formatted time range string of the slot
     */
    public String getSlotTime() { return slotTime; }

    /**
     * Gets the booking id.
     *
     * @return the unique booking identifier
     */
    public String getBookingId() { return bookingId; }

    /**
     * Gets the status.
     *
     * @return the current status of the booking
     */
    public String getStatus() { return status; }

    /**
     * Gets the slot id.
     *
     * @return the unique slot identifier
     */
    public String getSlotId() {
        return slotId;
    }

    /**
     * Sets the slot id.
     *
     * @param slotId the new slot identifier
     */
    public void setSlotId(String slotId) {
        this.slotId = slotId;
    }

    /**
     * Gets the user email.
     *
     * @return the email of the user who made the booking
     */
    public String getUserEmail() { return userEmail; }
}