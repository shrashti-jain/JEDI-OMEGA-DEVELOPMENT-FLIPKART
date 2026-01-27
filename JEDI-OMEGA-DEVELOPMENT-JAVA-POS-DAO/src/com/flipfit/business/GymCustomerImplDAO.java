package com.flipfit.business;

import com.flipfit.bean.Booking;
import com.flipfit.bean.GymCenter;
import com.flipfit.bean.Slot;
import com.flipfit.dao.BookingDAO;
import com.flipfit.dao.GymCenterDAO;
import com.flipfit.dao.SlotDAO;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class GymCustomerImplDAO implements GymCustomerInterface {

    private final GymCenterDAO gymCenterDAO = new GymCenterDAO();
    private final SlotDAO slotDAO = new SlotDAO();
    private final BookingDAO bookingDAO = new BookingDAO();
    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public List<GymCenter> viewCenters(String city) {
        try {
            return gymCenterDAO.getCentersByCity(city);
        } catch (Exception e) {
            System.out.println("Error fetching gym centers: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    @Override
    public List<Slot> viewSlotAvailability(String centerId, java.util.Date date) {
        try {
            java.sql.Date sqlDate = new java.sql.Date(date.getTime());
            return slotDAO.getSlotsByCenterAndDate(centerId, sqlDate);
        } catch (Exception e) {
            System.out.println("Error fetching slots: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    @Override
    public Booking bookSlot(String userEmail, String slotId, String centerId, java.util.Date date) {
        try {
            java.sql.Date sqlDate = new java.sql.Date(date.getTime());
            
            // Get slot details
            List<Slot> slots = slotDAO.getSlotsByCenterAndDate(centerId, sqlDate);
            Slot selectedSlot = null;
            for (Slot s : slots) {
                if (s.getSlotId().equals(slotId)) {
                    selectedSlot = s;
                    break;
                }
            }

            if (selectedSlot == null) {
                System.out.println("Slot not found!");
                return null;
            }

            // Check availability
            int availableSeats = slotDAO.getAvailableSeats(slotId, sqlDate);
            if (availableSeats <= 0) {
                System.out.println("Slot is full!");
                return null;
            }

            // Check for time conflicts
            String slotTime = selectedSlot.getStartTime() + " - " + selectedSlot.getEndTime();
            if (bookingDAO.hasTimeConflict(userEmail, sqlDate, slotTime)) {
                System.out.println("You already have a booking at this time!");
                return null;
            }

            // Get gym name
            List<GymCenter> centers = gymCenterDAO.getCentersByCity("");
            String gymName = "";
            for (GymCenter c : centers) {
                if (c.getCenterId().equals(centerId)) {
                    gymName = c.getCenterName();
                    break;
                }
            }

            // Create booking
            String bookingId = "B" + UUID.randomUUID().toString().substring(0, 8);
            boolean created = bookingDAO.createBooking(bookingId, userEmail, centerId, slotId, gymName, date, slotTime, "CONFIRMED");

            if (created) {
                // Update available seats - decrease by 1
                slotDAO.updateSlotCapacity(slotId, sqlDate, -1);
                
                // Return booking object
                Booking booking = new Booking(bookingId, userEmail, slotId, gymName, date, slotTime, "CONFIRMED");
                System.out.println("Booking successful! Booking ID: " + bookingId);
                return booking;
            } else {
                System.out.println("Booking failed!");
                return null;
            }
        } catch (Exception e) {
            System.out.println("Error booking slot: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean cancelBooking(String bookingId) {
        try {
            // Get booking details first
            Booking booking = bookingDAO.getBookingById(bookingId);
            if (booking == null) {
                System.out.println("Booking not found!");
                return false;
            }

            // Cancel booking
            boolean cancelled = bookingDAO.cancelBooking(bookingId);
            
            if (cancelled) {
                // Increase available seats by 1
                java.sql.Date sqlDate = new java.sql.Date(booking.getSlotDate().getTime());
                slotDAO.updateSlotCapacity(booking.getSlotId(), sqlDate, 1);
                
                System.out.println("Booking cancelled successfully!");
                return true;
            } else {
                System.out.println("Failed to cancel booking!");
                return false;
            }
        } catch (Exception e) {
            System.out.println("Error cancelling booking: " + e.getMessage());
            return false;
        }
    }

    @Override
    public Booking checkConflict(String userEmail, java.util.Date date, String slotTime) {
        try {
            java.sql.Date sqlDate = new java.sql.Date(date.getTime());
            
            List<Booking> bookings = bookingDAO.getBookingsByUserEmail(userEmail);
            for (Booking b : bookings) {
                if (b.getStatus().equals("CONFIRMED") &&
                    b.getSlotDate().equals(sqlDate) &&
                    b.getSlotTime().equals(slotTime)) {
                    return b;
                }
            }
            return null;
        } catch (Exception e) {
            System.out.println("Error checking conflict: " + e.getMessage());
            return null;
        }
    }

    @Override
    public List<Booking> viewBookings(String userEmail) {
        try {
            return bookingDAO.getBookingsByUserEmail(userEmail);
        } catch (Exception e) {
            System.out.println("Error fetching bookings: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}
