package com.flipfit.business;

import com.flipfit.bean.Slot;
import com.flipfit.bean.GymCenter;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class GymOwnerImpl implements GymOwnerInterface {

    // Static lists act as our database for the session
    private static final List<Slot> allSlots = new ArrayList<>();
    private static final List<GymCenter> allCenters = new ArrayList<>();
    private static final Map<String, List<Slot>> centerSlotsMap = new HashMap<>();


    @Override
    public void addCenter(String name, String ownerEmail, String city) {
        // In the client call, we'll pass city as location for simplicity or add a field
        String id = "C" + (allCenters.size() + 1);
        GymCenter newCenter = new GymCenter(id, name, "Default Location", city, ownerEmail);
        allCenters.add(newCenter);
        System.out.println("Request sent for Center: " + name + " (ID: " + id + ")");
    }

    @Override
    public void addSlot(String centerId, Slot slot) {
        allSlots.add(slot);
        System.out.println("Slot added successfully for Center: " + centerId);
    }

    // Static helper for Customer Service to fetch slots
    public static List<Slot> getSlotsByCenter(String centerId, Date date) {
        List<Slot> centerSlots = new ArrayList<>();
        for (Slot s : allSlots) {
            // Use a helper to compare only the Year-Month-Day, ignoring time
            if (s.getCenterId().equalsIgnoreCase(centerId) && isSameDay(s.getDate(), date)) {
                centerSlots.add(s);
            }
        }
        return centerSlots;
    }

    // Helper method to compare dates without time
    private static boolean isSameDay(Date d1, Date d2) {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd");
        return fmt.format(d1).equals(fmt.format(d2));
    }

    // --- HELPER METHODS FOR OTHER SERVICES ---

    // Used by Admin to see what needs approval
    public static List<GymCenter> getPendingCenters() {
        return allCenters.stream().filter(c -> !c.isApproved()).collect(Collectors.toList());
    }

    // Used by Admin to approve
    public static boolean approveCenter(String centerId) {
        for (GymCenter c : allCenters) {
            if (c.getCenterId().equalsIgnoreCase(centerId)) {
                c.setApproved(true);
                return true; // Success!
            }
        }
        return false; // ID not found
    }
    public static boolean removeCenter(String centerId) {
        // removeIf returns true if an element was removed
        boolean isRemoved = allCenters.removeIf(c -> c.getCenterId().equalsIgnoreCase(centerId));

        if (isRemoved) {
            // Also clean up any slots associated with this center
            centerSlotsMap.remove(centerId);
        }

        return isRemoved;
    }


    public static List<GymCenter> getCentersByOwner(String ownerEmail) {
        // We stream through ALL centers in the system
        return allCenters.stream()
                .filter(center -> center.getOwnerEmail().equalsIgnoreCase(ownerEmail))
                .collect(Collectors.toList());
    }

    // Used by Customer to see only approved gyms in their city
    public static List<GymCenter> getApprovedCentersByCity(String city) {
        return allCenters.stream()
                .filter(c -> c.isApproved() && c.getCity().equalsIgnoreCase(city))
                .collect(Collectors.toList());
    }

    public static Slot getSlotById(String slotId) {
        return allSlots.stream()
                .filter(s -> s.getSlotId().equalsIgnoreCase(slotId))
                .findFirst()
                .orElse(null);
    }

    public static String getCenterNameById(String centerId) {
        return allCenters.stream()
                .filter(c -> c.getCenterId().equalsIgnoreCase(centerId))
                .map(GymCenter::getCenterName)
                .findFirst()
                .orElse("Unknown Gym");
    }
}