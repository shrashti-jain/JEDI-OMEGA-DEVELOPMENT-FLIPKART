package com.flipfit.bean;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalTime;
import java.util.Date;

// TODO: Auto-generated Javadoc
/**
 * The Class Slot.
 *
 * This class represents a time slot for a gym center
 * in the FlipFit system.
 *
 * A slot defines the start time, end time, date,
 * capacity, and available seats for booking.
 *
 * @author Shravya
 * @ClassName "Slot"
 */
public class Slot {

    /** The unique slot ID */
    private int slotId;

    /** The gym center ID to which this slot belongs */
    private int centerId;

    /** The start time of the slot */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    private LocalTime startTime;

    /** The end time of the slot */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    private LocalTime endTime;

    /** The date on which the slot is available */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date date;

    /** The total capacity of the slot */
    private int capacity;

    /** The available seats in the slot */
    private int availableSeats;

    /**
     * Instantiates a new Slot.
     *
     * Initially, all seats are available.
     *
     * @param slotId the slot ID
     * @param centerId the gym center ID
     * @param startTime the start time
     * @param endTime the end time
     * @param date the slot date
     * @param totalSeats the total seat capacity
     */
    public Slot(int slotId, int centerId,
                LocalTime startTime, LocalTime endTime,
                Date date, int totalSeats) {

        this.slotId = slotId;
        this.centerId = centerId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.capacity = totalSeats;
        this.date = date;
        this.availableSeats = totalSeats;
    }

    public Slot(){};

    /**
     * Gets the slot date.
     *
     * @return the slot date
     */
    public Date getDate() {
        return date;
    }

    /**
     * Sets the slot date.
     *
     * @param date the slot date
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * Gets the slot ID.
     *
     * @return the slot ID
     */
    public int getSlotId() {
        return slotId;
    }

    /**
     * Sets the slot ID.
     *
     * @param slotId the slot ID
     */
    public void setSlotId(int slotId) {
        this.slotId = slotId;
    }

    /**
     * Gets the gym center ID.
     *
     * @return the center ID
     */
    public int getCenterId() {
        return centerId;
    }

    /**
     * Sets the gym center ID.
     *
     * @param centerId the center ID
     */
    public void setCenterId(int centerId) {
        this.centerId = centerId;
    }

    /**
     * Gets the start time of the slot.
     *
     * @return the start time
     */
    public LocalTime getStartTime() {
        return startTime;
    }

    /**
     * Sets the start time of the slot.
     *
     * @param startTime the start time
     */
    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    /**
     * Gets the end time of the slot.
     *
     * @return the end time
     */
    public LocalTime getEndTime() {
        return endTime;
    }

    /**
     * Sets the end time of the slot.
     *
     * @param endTime the end time
     */
    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    /**
     * Gets the slot capacity.
     *
     * @return the capacity
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     * Sets the slot capacity.
     *
     * @param capacity the capacity
     */
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    /**
     * Gets the available seats.
     *
     * @return the available seats
     */
    public int getAvailableSeats() {
        return availableSeats;
    }

    /**
     * Sets the available seats.
     *
     * @param availableSeats the available seats
     */
    public void setAvailableSeats(int availableSeats) {
        this.availableSeats = availableSeats;
    }
}
