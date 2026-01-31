package com.flipfit.business;

import com.flipfit.bean.Booking;
import com.flipfit.bean.GymCenter;
import com.flipfit.bean.Slot;
import com.flipfit.bean.Waitlist;
import com.flipfit.dao.*;
import com.flipfit.exception.BookingFailedException;
import com.flipfit.exception.FlipFitException;
import com.flipfit.exception.SlotNotAvailableException;
import com.flipfit.utility.DBConnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

/**
 * Implementation of customer-specific functionalities.
 * Fully synchronized with Integer-ID mapping and Atomic Transaction logic.
 */
public class GymCustomerImpl implements GymCustomerInterface {

    private final BookingDAO bookingDAO = new BookingDAOImpl();
    private final SlotDAO slotDAO = new SlotDAOImpl();
    private final GymCenterDAO gymCenterDAO = new GymCenterDAOImpl();
    private final UserDAO userDAO = new UserDAOImpl();
    private final WaitlistDAO waitlistDAO = new WaitlistDAOImpl();

    @Override
    public List<GymCenter> viewCenters(String city) {
        List<GymCenter> centers = gymCenterDAO.getApprovedCentersByCity(city);
        if (centers == null || centers.isEmpty()) {
            throw new FlipFitException("No approved gym centers found in city: " + city);
        }
        return centers;
    }

    @Override
    public List<Slot> viewSlotAvailability(int centerId, Date date) {
        // centerId is int to match DB primary key
        List<Slot> slots = slotDAO.getSlotsByCenterAndDate(centerId, date);
        if (slots == null || slots.isEmpty()) {
            throw new FlipFitException("No slots available for the selected center and date.");
        }
        return slots;
    }

    /**
     * Core logic for booking a slot.
     * Transactional: Ensures booking is created AND seat is decremented.
     */
    @Override
    public Booking bookSlot(int userId, int slotId, int centerId, Date date) {
        // 1. Fetch metadata needed for the Booking object
        Slot selectedSlot = slotDAO.getSlotById(slotId);
        if (selectedSlot == null) {
            throw new BookingFailedException("Invalid Slot ID.");
        }

        // 2. ATOMIC CHECK & UPDATE
        // Instead of checking seats and THEN updating (which causes race conditions),
        // we try to decrease the seat count directly in the DB.
        boolean seatSecured = slotDAO.decreaseAvailableSeats(slotId);

        if (seatSecured) {
            // 3. Construct the Booking object
            String bookingId = "B" + System.currentTimeMillis();
            String gymName = gymCenterDAO.getCenterNameById(centerId);
            String slotTime = selectedSlot.getStartTime() + " - " + selectedSlot.getEndTime();

            Booking newBooking = new Booking(
                    bookingId, userId, centerId, slotId, date, slotTime, "CONFIRMED", gymName
            );

            try {
                bookingDAO.createBooking(newBooking);
                return newBooking;
            } catch (Exception e) {
                // ROLLBACK: If the booking record fails to save, we MUST give the seat back
                slotDAO.increaseAvailableSeats(slotId);
                throw new BookingFailedException("System error while saving booking. Seat released.");
            }
        }

        // 4. If we reach here, it means decreaseAvailableSeats returned false (Gym was full)
        return null;
    }
    /**
     * Cancels a booking and restores the seat count to the gym inventory.
     */
    @Override
    public boolean cancelBooking(String bookingId) {
        try {
            // Fetch booking metadata
            Booking booking = bookingDAO.getBookingById(bookingId);
            if (booking == null) throw new FlipFitException("Booking record not found.");

            int slotId = booking.getSlotId();
            Date date = booking.getSlotDate();

            // 2. Delete the current booking
            boolean deleted = bookingDAO.deleteBooking(bookingId);

            if (deleted) {
                // 3. TRY TO PROMOTE SOMEONE
                Waitlist nextInLine = waitlistDAO.getNextInLine(slotId, date);

                if (nextInLine != null) {
                    // A person is waiting! Move them to bookings
                    promoteUser(nextInLine, booking);
                    // Note: We DO NOT increase available seats because the spot is filled
                    System.out.println("✅ Slot filled by waitlisted user.");
                } else {
                    // No one is waiting, return the seat to the gym pool
                    slotDAO.increaseAvailableSeats(slotId);
                    System.out.println("✅ Seat returned to public pool.");
                }
                return true;
            }

            // Only proceed if the booking is currently CONFIRMED
            if ("CONFIRMED".equalsIgnoreCase(booking.getStatus())) {

                // 1. Mark the current booking as CANCELLED
                bookingDAO.cancelBooking(bookingId);

                // 2. CHECK WAITLIST: Is someone waiting for this specific slot and date?
                Waitlist nextUser = waitlistDAO.getNextInLine(booking.getSlotId(), booking.getSlotDate());

                if (nextUser != null) {
                    // 3. AUTOMATIC PROMOTION
                    // Generate a new booking ID for the waitlisted user
                    String newBookingId = "B" + System.currentTimeMillis();

                    // Construct the promoted booking object
                    // Note: We reuse the date, slot, and center from the cancelled booking
                    Booking promotedBooking = new Booking(
                            newBookingId,
                            nextUser.getUserId(),
                            booking.getCenterId(),
                            booking.getSlotId(),
                            booking.getSlotDate(),
                            booking.getSlotTime(),
                            "CONFIRMED",
                            booking.getGymName()
                    );

                    // Create the booking for the new user
                    bookingDAO.createBooking(promotedBooking);

                    // Remove them from the waitlist table
                    waitlistDAO.removeFromWaitlist(nextUser.getWaitlistId());

                    System.out.println("📢 [WAITLIST] User ID " + nextUser.getUserId() +
                            " has been automatically promoted to this slot!");

                    // IMPORTANT: We DO NOT call increaseAvailableSeats() here
                    // because the seat was immediately filled by the waitlisted user.

                } else {
                    // 4. NO ONE WAITING: Only now do we increase the seat count for the public
                    slotDAO.increaseAvailableSeats(booking.getSlotId());
                    System.out.println("✅ Booking cancelled. Seat is now available for others.");
                }

                return true;
            }
        } catch (Exception e) {
            throw new FlipFitException("Cancellation failed: " + e.getMessage());
        }
        return false;
    }

    private void promoteUser(Waitlist wl, Booking oldBooking) {
        String newBookingId = "B" + System.currentTimeMillis();

        // Create new booking using details from the waitlist entry
        Booking promotedBooking = new Booking(
                newBookingId,
                wl.getUserId(),
                oldBooking.getCenterId(),
                wl.getSlotId(),
                wl.getBookingDate(),
                oldBooking.getSlotTime(),
                "CONFIRMED",
                oldBooking.getGymName()
        );

        // Atomic Swap
        bookingDAO.createBooking(promotedBooking);
        boolean isRemoved = waitlistDAO.removeFromWaitlist(wl.getWaitlistId());

        if (isRemoved) {
            System.out.println("Waitlist record cleared.");
        } else {
            System.out.println("⚠️ Warning: Booking created but waitlist entry WL-" + wl.getWaitlistId() + " remains.");
        }
    }

    @Override
    public List<Booking> viewBookings(String userEmail) {
        // 1. Resolve email to ID
        int userId = userDAO.getUserIdByEmail(userEmail);

        // 2. Fetch bookings from DAO
        List<Booking> bookings = bookingDAO.getBookingsByUser(userId);

        // 3. REMOVED the exception throw.
        // If the list is null, return an empty list so the menu can still function.
        if (bookings == null) {
            return new ArrayList<>();
        }

        return bookings;
    }

    @Override
    public Booking checkConflict(int userId, Date date, String slotTime) {

        // 3. Ask DAO to check for ANY booking at this time across ALL gyms
        return bookingDAO.findBookingConflict(userId, date, slotTime);
    }

    @Override
    public Slot getSlotById(int slotId) {
        Slot slot = slotDAO.getSlotById(slotId);
        if (slot == null) {
            throw new SlotNotAvailableException("The requested Slot was not found.");
        }
        return slot;
    }

    @Override
    public boolean addWaitlist(int userId, int slotId, Date date) {
        // Optional: Check if user is already on the waitlist for this day
        return waitlistDAO.addToWaitlist(userId, slotId, date);
    }

    @Override
    public List<Waitlist> viewWaitlist(String email) {
        // 1. Get userId from email (since waitlist table uses userId)
        int userId = userDAO.getUserIdByEmail(email);
        // 2. Fetch from WaitlistDAO
        return waitlistDAO.getWaitlistByUserId(userId);
    }
}