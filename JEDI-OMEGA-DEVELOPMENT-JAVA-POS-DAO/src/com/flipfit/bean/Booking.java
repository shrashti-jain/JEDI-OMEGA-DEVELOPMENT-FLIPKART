package com.flipfit.bean;

import java.util.Date;

/**
 * Represents a booking made by a customer
 * for a gym slot in the FlipFit system.
 */
public class Booking {

    private String bookingId;
    private int userId;
    private int centerId;
    private int slotId;
    private Date slotDate;
    private String slotTime;
    private String status;
    private String gymName; // 👈 NEW FIELD

    // Update constructor to include gymName
    public Booking(String bookingId,
                   int userId,
                   int centerId,
                   int slotId,
                   Date slotDate,
                   String slotTime,
                   String status,
                   String gymName) { // 👈 ADDED PARAMETER

        this.bookingId = bookingId;
        this.userId = userId;
        this.centerId = centerId;
        this.slotId = slotId;
        this.slotDate = slotDate;
        this.slotTime = slotTime;
        this.status = status;
        this.gymName = gymName; // 👈 INITIALIZE
    }

    // Default constructor for DAO mapping
    public Booking() {
    }

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getCenterId() {
        return centerId;
    }

    public void setCenterId(int centerId) {
        this.centerId = centerId;
    }

    public int getSlotId() {
        return slotId;
    }

    public void setSlotId(int slotId) {
        this.slotId = slotId;
    }

    public Date getSlotDate() {
        return slotDate;
    }

    public void setSlotDate(Date slotDate) {
        this.slotDate = slotDate;
    }

    public String getSlotTime() {
        return slotTime;
    }

    public void setSlotTime(String slotTime) {
        this.slotTime = slotTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getGymName() {
        return gymName;
    }

    public void setGymName(String gymName) {
        this.gymName = gymName;
    }
}
