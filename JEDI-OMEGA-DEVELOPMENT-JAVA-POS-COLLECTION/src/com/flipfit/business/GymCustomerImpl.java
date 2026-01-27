package com.flipfit.business;

import com.flipfit.bean.Booking;
import com.flipfit.bean.GymCenter;
import com.flipfit.bean.Slot;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class GymCustomerImpl implements GymCustomerInterface {

    private static final List<Booking> allBookings = new ArrayList<>();
    private static final Map<String, Integer> inventory = new HashMap<>();
    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public List<GymCenter> viewCenters(String city) {
        return GymOwnerImpl.getApprovedCentersByCity(city);
    }

    @Override
    public List<Slot> viewSlotAvailability(String centerId, Date date) {
        List<Slot> slots = GymOwnerImpl.getSlotsByCenter(centerId, date);
        for (Slot s : slots) {
            String inventoryKey = s.getSlotId() + "_" + sdf.format(date);
            int remaining = inventory.getOrDefault(inventoryKey, s.getCapacity());
            s.setAvailableSeats(remaining);
        }
        return slots;
    }

    @Override
    public Booking bookSlot(String userEmail, String slotId, String centerId, Date date) {
        String inventoryKey = slotId + "_" + sdf.format(date);

        List<Slot> allSlots = GymOwnerImpl.getSlotsByCenter(centerId, date);
        Slot selectedSlot = allSlots.stream()
                .filter(s -> s.getSlotId().equalsIgnoreCase(slotId))
                .findFirst()
                .orElse(null);

        if (selectedSlot == null) {
            System.out.println("Slot not found! " + slotId);
            return null;
        }

        inventory.putIfAbsent(inventoryKey, selectedSlot.getCapacity());

        int currentAvailable = inventory.get(inventoryKey);
        if (currentAvailable > 0) {
            inventory.put(inventoryKey, currentAvailable - 1);

            String bId = "B" + System.currentTimeMillis();
            String gymName = GymOwnerImpl.getCenterNameById(centerId);
            String timeRange = selectedSlot.getStartTime() + " - " + selectedSlot.getEndTime();

            // Ensure your Booking Constructor matches these 7 arguments
            Booking newBooking = new Booking(bId, userEmail, slotId, gymName, date, timeRange, "CONFIRMED");

            allBookings.add(newBooking);
            return newBooking;
        }
        System.out.println("Slot full!");
        return null;
    }

    @Override
    public boolean cancelBooking(String bookingId) {
        // Find the booking first to get details for inventory
        Booking bToCancel = allBookings.stream()
                .filter(b -> b.getBookingId().equals(bookingId))
                .findFirst()
                .orElse(null);

        if (bToCancel != null) {
            // Update Inventory
            String inventoryKey = bToCancel.getSlotId() + "_" + sdf.format(bToCancel.getSlotDate());
            inventory.put(inventoryKey, inventory.getOrDefault(inventoryKey, 0) + 1);

            // Remove from list
            allBookings.remove(bToCancel);
            System.out.println("Booking cancelled and seat returned.");
            return true;
        }
        return false;
    }

    @Override
    public Booking checkConflict(String userEmail, Date date, String slotTime) {
        String targetDate = sdf.format(date);
        for (Booking b : allBookings) {
            if (b.getUserEmail().equalsIgnoreCase(userEmail) &&
                    b.getStatus().equalsIgnoreCase("CONFIRMED") &&
                    sdf.format(b.getSlotDate()).equals(targetDate) &&
                    b.getSlotTime().equals(slotTime)) {
                return b;
            }
        }
        return null;
    }

    @Override
    public List<Booking> viewBookings(String userEmail) {
        return allBookings.stream()
                .filter(b -> b.getUserEmail().equalsIgnoreCase(userEmail))
                .collect(Collectors.toList());
    }
}