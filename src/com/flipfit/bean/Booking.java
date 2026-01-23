package com.flipfit.bean;

public class Booking {
    private String bookingId;
    private String userId;
    private String slotId;
    private String status; // "Confirmed" or "Cancelled"

    public Booking(String bookingId, String userId, String slotId, String status) {
        this.bookingId = bookingId;
        this.userId = userId;
        this.slotId = slotId;
        this.status = status;
    }

    public String getBookingId() { return bookingId; }
    public String getStatus() { return status; }
}