package com.flipfit.bean;
import java.util.Date;

public class Booking {
    private String bookingId;
    private String userEmail;
    private String gymName;
    private String slotId;
    private Date slotDate;
    private String slotTime; // e.g., "09:00 - 10:00"
    private String status;

    public void setStatus(String status) {
        this.status = status;
    }

    public Booking(String bookingId, String userEmail, String slotId, String gymName, Date slotDate, String slotTime, String status) {
        this.bookingId = bookingId;
        this.userEmail = userEmail;
        this.gymName = gymName;
        this.slotId = slotId;
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

    public String getSlotId() {
        return slotId;
    }

    public void setSlotId(String slotId) {
        this.slotId = slotId;
    }

    public String getUserEmail() { return userEmail; }


}