package com.flipfit.bean;
import java.util.Date;

public class Booking {
    private String bookingId;
    private String userEmail;
    private String gymName;
    private Date slotDate;
    private String slotTime; // e.g., "09:00 - 10:00"
    private String status;

    public Booking(String bookingId, String userEmail, String gymName, Date slotDate, String slotTime, String status) {
        this.bookingId = bookingId;
        this.userEmail = userEmail;
        this.gymName = gymName;
        this.slotDate = slotDate;
        this.slotTime = slotTime;
        this.status = status;
    }

    // Getters for all fields
    public String getGymName() { return gymName; }
    public Date getSlotDate() { return slotDate; }
    public String getSlotTime() { return slotTime; }
    public String getBookingId() { return bookingId; }
    public String getStatus() { return status; }
    public String getUserEmail() { return userEmail; }
}