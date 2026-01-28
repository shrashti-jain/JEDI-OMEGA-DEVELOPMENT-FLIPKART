package com.flipfit.business;

import com.flipfit.bean.Booking;
import com.flipfit.bean.GymCenter;
import com.flipfit.bean.Slot;
import com.flipfit.dao.BookingDAO;
import com.flipfit.dao.GymCenterDAO;
import com.flipfit.dao.SlotDAO;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Implementation of customer-specific functionalities.
 * Manages gym browsing, slot availability checks, and booking lifecycles via DAOs.
 */
public class GymCustomerImpl implements GymCustomerInterface {

    private final SlotDAO slotDAO = new SlotDAO();
    private final BookingDAO bookingDAO = new BookingDAO();
    private final GymCenterDAO gymCenterDAO = new GymCenterDAO();
    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * Retrieves all approved gym centers in a specific city.
     * * @param city the city to filter gyms
     * @return List of approved GymCenter objects
     */
    @Override
    public List<GymCenter> viewCenters(String city) {
        return gymCenterDAO.getApprovedCentersByCity(city);
    }

    /**
     * Checks slot availability by fetching live data from the database.
     * * @param centerId the ID of the gym center
     * @param date the date for which slots are requested
     * @return List of slots with updated seat counts
     */
    @Override
    public List<Slot> viewSlotAvailability(String centerId, Date date) {
        // Fetching directly from DB via SlotDAO instead of local memory
        return slotDAO.getSlotsByCenterAndDate(centerId, date);
    }

    /**
     * Core logic for booking a slot. Updates the Booking table and decrements Slot capacity.
     * * @param userEmail customer email
     * @param slotId targeted slot ID
     * @param centerId targeted center ID
     * @param date date of the workout
     * @return Populated Booking object if successful, null otherwise
     */
    @Override
    public Booking bookSlot(String userEmail, String slotId, String centerId, Date date) {
        try {
            Slot selectedSlot = slotDAO.getSlotById(slotId);

            if (selectedSlot == null) {
                System.err.println("Error: Slot does not exist.");
                return null;
            }

            if (selectedSlot.getAvailableSeats() > 0) {
                // Create booking record
                Booking newBooking = bookingDAO.createBooking(userEmail, slotId, centerId, date);

                if (newBooking != null) {
                    // Decrement seats in DB
                    boolean isInventoryUpdated = slotDAO.updateSlotCapacity(slotId, selectedSlot.getAvailableSeats() - 1);

                    if (isInventoryUpdated) {
                        return newBooking;
                    } else {
                        // Rollback: delete booking if capacity update fails
                        bookingDAO.cancelBooking(newBooking.getBookingId());
                        System.err.println("Inventory update failed. Booking rolled back.");
                    }
                }
            } else {
                System.out.println("Booking failed: Slot is full.");
            }
        } catch (Exception e) {
            System.err.println("System Error during booking: " + e.getMessage());
        }
        return null;
    }

    /**
     * Cancels a booking and restores the seat to the slot capacity in the database.
     * * @param bookingId the ID of the booking to be cancelled
     * @return true if cancellation and seat restoration succeeded
     */
    @Override
    public boolean cancelBooking(String bookingId) {
        try {
            // 1. Fetch booking details to find the associated slot
            Booking bToCancel = bookingDAO.getBookingById(bookingId);

            if (bToCancel != null && !bToCancel.getStatus().equalsIgnoreCase("CANCELLED")) {
                // 2. Update Booking Status in DB
                if (bookingDAO.cancelBooking(bookingId)) {
                    // 3. Increment available seats in the Slot table
                    Slot s = slotDAO.getSlotById(bToCancel.getSlotId());
                    slotDAO.updateSlotCapacity(s.getSlotId(), s.getAvailableSeats() + 1);

                    System.out.println("Success: Booking cancelled and seat restored.");
                    return true;
                }
            }
        } catch (Exception e) {
            System.err.println("Error during cancellation: " + e.getMessage());
        }
        return false;
    }

    /**
     * Checks if a user already has a booking at the same time on the same date.
     * * @param userEmail the customer email
     * @param date requested date
     * @param slotTime requested time range
     * @return Existing Booking if a conflict is found, null otherwise
     */
    @Override
    public Booking checkConflict(String userEmail, Date date, String slotTime) {
        // Calls DAO to search for existing active bookings for this user/time
        return bookingDAO.checkUserConflict(userEmail, date, slotTime);
    }

    /**
     * Fetches all bookings for a specific customer from the database.
     * * @param userEmail customer email
     * @return List of all bookings associated with the email
     */
    @Override
    public List<Booking> viewBookings(String userEmail) {
        return bookingDAO.getBookingsByCustomer(userEmail);
    }
}