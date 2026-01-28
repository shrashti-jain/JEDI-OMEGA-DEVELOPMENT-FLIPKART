package com.flipfit.bean;

import java.time.LocalTime;
import java.util.Date;

//TODO: Auto-generated Javadoc
/**
* The Class Slot.
* Represents a specific time interval available for booking at a Gym Center.
* Tracks the timing, date, total capacity, and currently available seats.
*
* @author Shreya
* @ClassName Slot
*/
public class Slot {
    private String slotId;
    private String centerId;
    private LocalTime startTime;
    private LocalTime endTime;
    private Date date;
    private int capacity;       // Total capacity of the slot
    private int availableSeats; // Seats remaining for booking

    /**
     * Instantiates a new Slot.
     * Initializes the slot with total capacity. Initially, available seats equal the total capacity.
     *
     * @param slotId the unique identifier for the slot
     * @param centerId the identifier of the gym center offering this slot
     * @param startTime the start time of the slot
     * @param endTime the end time of the slot
     * @param date the date of the slot
     * @param totalSeats the total number of users allowed in this slot
     */
    public Slot(String slotId, String centerId, LocalTime startTime, LocalTime endTime, Date date, int totalSeats) {
        this.slotId = slotId;
        this.centerId = centerId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.date = date;
        this.capacity = totalSeats;
        this.availableSeats = totalSeats; // Initially, all seats are available
    }

    /**
     * Gets the seats.
     * Retrieves the total capacity of the slot.
     * Note: This method is primarily used by the DAO and Business layers.
     *
     * @return the total capacity
     */
    public int getSeats() {
        return this.capacity;
    }

    /**
     * Sets the seats.
     * Updates the total capacity of the slot.
     *
     * @param capacity the new total capacity
     */
    public void setSeats(int capacity) {
        this.capacity = capacity;
    }

    /**
     * Gets the date.
     *
     * @return the date of the slot
     */
    public Date getDate() {
        return date;
    }

    /**
     * Sets the date.
     *
     * @param date the new date for the slot
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * Gets the slot id.
     *
     * @return the unique slot id
     */
    public String getSlotId() {
        return slotId;
    }

    /**
     * Sets the slot id.
     *
     * @param slotId the new slot id
     */
    public void setSlotId(String slotId) {
        this.slotId = slotId;
    }

    /**
     * Gets the center id.
     *
     * @return the center id associated with this slot
     */
    public String getCenterId() {
        return centerId;
    }

    /**
     * Sets the center id.
     *
     * @param centerId the new center id
     */
    public void setCenterId(String centerId) {
        this.centerId = centerId;
    }

    /**
     * Gets the start time.
     *
     * @return the start time
     */
    public LocalTime getStartTime() {
        return startTime;
    }

    /**
     * Sets the start time.
     *
     * @param startTime the new start time
     */
    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    /**
     * Gets the end time.
     *
     * @return the end time
     */
    public LocalTime getEndTime() {
        return endTime;
    }

    /**
     * Sets the end time.
     *
     * @param endTime the new end time
     */
    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    /**
     * Gets the capacity.
     *
     * @return the total capacity
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     * Sets the capacity.
     *
     * @param capacity the new total capacity
     */
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    /**
     * Gets the available seats.
     *
     * @return the number of seats currently available for booking
     */
    public int getAvailableSeats() {
        return availableSeats;
    }

    /**
     * Sets the available seats.
     *
     * @param availableSeats the new count of available seats
     */
    public void setAvailableSeats(int availableSeats) {
        this.availableSeats = availableSeats;
    }
}