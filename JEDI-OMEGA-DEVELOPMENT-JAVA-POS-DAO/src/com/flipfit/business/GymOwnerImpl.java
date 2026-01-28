package com.flipfit.business;

import com.flipfit.bean.Slot;
import com.flipfit.bean.GymCenter;
import com.flipfit.dao.GymCenterDAO; // New DAO for database interaction
import com.flipfit.dao.SlotDAO;      // New DAO for slot persistence

import java.util.*;

public class GymOwnerImpl implements GymOwnerInterface {

    // Replacing static lists with DAOs
    private GymCenterDAO gymCenterDAO = new GymCenterDAO();
    private SlotDAO slotDAO = new SlotDAO();

    @Override
    public void addCenter(String name, String ownerEmail, String location, String city, String pincode, String gstNo) {
        // Generate a unique Center ID
        String id = "GC" + System.currentTimeMillis();

        // Stage 4: Create gym center with mandatory verification fields
        GymCenter newCenter = new GymCenter(id, name, location, city, pincode, gstNo, ownerEmail);

        if (gymCenterDAO.addGymCenter(newCenter)) {
            System.out.println("Gym Center request submitted successfully for: " + name);
            System.out.println("Your GymCenter ID: " + id);
        } else {
            System.out.println("Error: Failed to submit Gym Center request.");
        }
    }

    @Override
    public void addSlot(String centerId, Slot slot) {
        // Check if the center is approved before adding slots
        if (isCenterApproved(centerId)) {
            if (slotDAO.addSlot(slot)) {
                System.out.println("Slot added successfully for Center: " + centerId);
            } else {
                System.out.println("Error: Could not save slot to database.");
            }
        } else {
            System.out.println("Access Denied: Slots can only be added to APPROVED centers.");
        }
    }

    // New verification helper for the Owner Menu
    @Override
    public boolean isCenterApproved(String centerId) {
        return gymCenterDAO.checkApprovalStatus(centerId);
    }

    @Override
    public List<GymCenter> getCentersByOwner(String ownerEmail) {
        // Fetches directly from the MySQL database via DAO
        return gymCenterDAO.getCentersByOwnerEmail(ownerEmail);
    }

    // --- STATIC HELPERS FOR CUSTOMER SERVICE ---

    public static List<Slot> getSlotsByCenter(String centerId, Date date) {
        // Calls the database instead of local list allSlots
        SlotDAO slotDAO = new SlotDAO();
        return slotDAO.getSlotsByCenterAndDate(centerId, date);
    }

    // Add this to GymOwnerImpl.java
    public static Slot getSlotById(String slotId) {
        // We instantiate the DAO to fetch the specific slot from MySQL
        SlotDAO slotDAO = new SlotDAO();
        return slotDAO.getSlotById(slotId);
    }

    public static List<GymCenter> getApprovedCentersByCity(String city) {
        // Used by Customers to view verified gyms
        GymCenterDAO gymCenterDAO = new GymCenterDAO();
        return gymCenterDAO.getApprovedCentersByCity(city);
    }

    public static String getCenterNameById(String centerId) {
        GymCenterDAO gymCenterDAO = new GymCenterDAO();
        return gymCenterDAO.getCenterNameById(centerId);
    }
}