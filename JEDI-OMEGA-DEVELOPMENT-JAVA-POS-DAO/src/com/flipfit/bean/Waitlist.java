package com.flipfit.bean;
import java.util.Date;

public class Waitlist {
    private int waitlistId;
    private int userId;
    private int slotId;
    private Date bookingDate;
    private String centerName;
    private String slotTime;

    public Waitlist() {}

    // Getters and Setters
    public int getWaitlistId() { return waitlistId; }
    public void setWaitlistId(int waitlistId) { this.waitlistId = waitlistId; }
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
    public int getSlotId() { return slotId; }
    public void setSlotId(int slotId) { this.slotId = slotId; }
    public Date getBookingDate() { return bookingDate; }
    public void setBookingDate(Date bookingDate) { this.bookingDate = bookingDate; }
    public String getCenterName() { return centerName; }
    public void setCenterName(String centerName) { this.centerName = centerName; }

    public String getSlotTime() { return slotTime; }
    public void setSlotTime(String slotTime) { this.slotTime = slotTime; }
}