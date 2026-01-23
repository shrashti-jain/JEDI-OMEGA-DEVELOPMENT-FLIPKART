package com.flipfit.bean;

public class Slot {
    private String slotId;
    private int capacity;
    private int bookedSeats;

    public Slot(String slotId, int capacity) {
        this.slotId = slotId;
        this.capacity = capacity;
        this.bookedSeats = 0;
    }

    public String getSlotId() { return slotId; }

    // Matches your client call: s.getAvailableSeats()
    public int getAvailableSeats() {
        return capacity - bookedSeats;
    }

    public void setBookedSeats(int bookedSeats) { this.bookedSeats = bookedSeats; }
}