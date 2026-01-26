package com.flipfit.business;

import com.flipfit.bean.Booking;
import com.flipfit.bean.GymCenter;
import com.flipfit.bean.Slot;

import java.util.*;
import java.util.stream.Collectors;

public class GymCustomerImpl implements GymCustomerInterface {

    // STEP 1: Create a static list to act as a database for the session
    private static final List<Booking> allBookings = new ArrayList<>();
    private static final Map<String, Integer> inventory = new HashMap<>();

    @Override
    public List<GymCenter> viewCenters(String city) {
        // Instead of creating a new list, call the helper method in GymOwnerImpl
        // This ensures customers only see APPROVED gyms in their specific city
        return GymOwnerImpl.getApprovedCentersByCity(city);
    }

    public List<Slot> viewSlotAvailability(String centerId, Date date) {
        List<Slot> slots = GymOwnerImpl.getSlotsByCenter(centerId, date);

        for (Slot s : slots) {
            String inventoryKey = s.getSlotId() + "_" + date.toString();
            // If no one has booked yet, show total capacity, otherwise show remaining
            int remaining = inventory.getOrDefault(inventoryKey, s.getCapacity());
            s.setAvailableSeats(remaining);
        }
        return slots;
    }

    @Override
    public Booking bookSlot(String userEmail, String slotId, String centerId, Date date) {
        String inventoryKey = slotId + "_" + date.toString();

        List<Slot> allSlots = GymOwnerImpl.getSlotsByCenter(centerId, date);
        Slot selectedSlot = allSlots.stream()
                .filter(s -> s.getSlotId().equalsIgnoreCase(slotId))
                .findFirst()
                .orElse(null);

        if (selectedSlot == null) {
            System.out.println("Slot not found! " + slotId);
            return null;
        }

        // Check for time slot conflicts with existing bookings
        String timeRange = selectedSlot.getStartTime() + " - " + selectedSlot.getEndTime();
        if (hasTimeConflict(userEmail, date, timeRange)) {
            System.out.println("ERROR: You already have a booking at this time slot!");
            System.out.println("Cannot book overlapping time slots across different gyms.");
            return null;
        }

        inventory.putIfAbsent(inventoryKey, selectedSlot.getCapacity());

        int currentAvailable = inventory.get(inventoryKey);
        String gymName = GymOwnerImpl.getCenterNameById(centerId);
        if (currentAvailable > 0) {
            inventory.put(inventoryKey, currentAvailable - 1);

            String bId = "B" + System.currentTimeMillis();

            // Save ALL details into the booking object
            Booking newBooking = new Booking(bId, userEmail, gymName, date, timeRange, "CONFIRMED");

            allBookings.add(newBooking);
            return newBooking;
        } else {
            System.out.println("Slot is fully booked for this date!");
            return null;
        }
    }

    /**
     * Helper method to check if the user already has a booking at the same time slot
     * on the same date, regardless of gym location.
     * 
     * @param userEmail The email of the user
     * @param date The date of the booking
     * @param timeRange The time range (e.g., "09:00 - 10:00")
     * @return true if there's a conflict, false otherwise
     */
    private boolean hasTimeConflict(String userEmail, Date date, String timeRange) {
        return allBookings.stream()
                .filter(b -> b.getUserEmail().equalsIgnoreCase(userEmail))
                .filter(b -> b.getStatus().equalsIgnoreCase("CONFIRMED"))
                .filter(b -> isSameDate(b.getSlotDate(), date))
                .anyMatch(b -> b.getSlotTime().equals(timeRange));
    }

    /**
     * Helper method to compare if two dates are the same day
     * 
     * @param date1 First date
     * @param date2 Second date
     * @return true if both dates are on the same day
     */
    private boolean isSameDate(Date date1, Date date2) {
        if (date1 == null || date2 == null) return false;
        return date1.toString().equals(date2.toString());
    }

    @Override
    public List<Booking> viewBookings(String userEmail) {
        return allBookings.stream()
                .filter(b -> b.getUserEmail().equalsIgnoreCase(userEmail))
                .collect(Collectors.toList());
    }

    @Override
    public boolean cancelBooking(String bookingId) {
        // STEP 4: Remove the booking from the static list based on ID
        boolean removed = allBookings.removeIf(b -> b.getBookingId().equals(bookingId));
        if (removed) {
            System.out.println("Booking " + bookingId + " cancelled successfully.");
        }
        return removed;
    }
}