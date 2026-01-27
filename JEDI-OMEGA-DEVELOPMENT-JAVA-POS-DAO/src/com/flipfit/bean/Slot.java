package com.flipfit.bean;

import java.time.LocalTime;
import java.util.Date;

public class Slot {
    private String slotId;
    private String centerId;
    private LocalTime startTime;
    private LocalTime endTime;
    private Date date;
    private int capacity;
    private int availableSeats;

    // Default constructor for DAO
    public Slot() {
    }

    public Slot(String slotId, String centerId, LocalTime startTime, LocalTime endTime, Date date, int totalSeats) {
        this.slotId = slotId;
        this.centerId = centerId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.capacity = totalSeats;
        this.date = date;
        this.availableSeats = totalSeats; // Initially, all seats are available
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getSlotId() {
        return slotId;
    }

    public void setSlotId(String slotId) {
        this.slotId = slotId;
    }

    public String getCenterId() {
        return centerId;
    }

    public void setCenterId(String centerId) {
        this.centerId = centerId;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getAvailableSeats() { return availableSeats; }

    public void setAvailableSeats(int availableSeats) {
        this.availableSeats = availableSeats;
    }
}