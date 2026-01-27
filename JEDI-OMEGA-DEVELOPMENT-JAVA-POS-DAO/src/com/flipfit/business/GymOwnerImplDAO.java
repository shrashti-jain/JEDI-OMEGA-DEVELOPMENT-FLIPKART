package com.flipfit.business;

import com.flipfit.bean.Slot;
import com.flipfit.bean.GymCenter;
import com.flipfit.dao.GymCenterDAO;
import com.flipfit.dao.SlotDAO;

import java.sql.Date;
import java.util.*;

public class GymOwnerImplDAO implements GymOwnerInterface {

    private final GymCenterDAO gymCenterDAO = new GymCenterDAO();
    private final SlotDAO slotDAO = new SlotDAO();

    @Override
    public void addCenter(String name, String ownerEmail, String city) {
        try {
            String id = "GYM" + UUID.randomUUID().toString().substring(0, 6).toUpperCase();
            boolean added = gymCenterDAO.addGymCenter(id, name, city, city, ownerEmail, "PENDING");
            
            if (added) {
                System.out.println("Request sent for Center: " + name + " (ID: " + id + ")");
            } else {
                System.out.println("Failed to add gym center!");
            }
        } catch (Exception e) {
            System.out.println("Error adding center: " + e.getMessage());
        }
    }

    @Override
    public void addSlot(String centerId, Slot slot) {
        try {
            java.util.Date utilDate = slot.getDate();
            boolean added = slotDAO.addSlot(
                slot.getSlotId(),
                centerId,
                slot.getStartTime(),
                slot.getEndTime(),
                slot.getTotalSeats(),
                utilDate // Pass java.util.Date
            );
            
            if (added) {
                System.out.println("Slot added successfully for Center: " + centerId);
            } else {
                System.out.println("Failed to add slot!");
            }
        } catch (Exception e) {
            System.out.println("Error adding slot: " + e.getMessage());
        }
    }

    /**
     * Get centers owned by a specific owner
     */
    public List<GymCenter> getCentersByOwner(String ownerEmail) {
        try {
            return gymCenterDAO.getCentersByOwner(ownerEmail);
        } catch (Exception e) {
            System.out.println("Error fetching centers: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    /**
     * Get slots by center and date
     */
    public List<Slot> getSlotsByCenter(String centerId, java.util.Date date) {
        try {
            java.sql.Date sqlDate = new java.sql.Date(date.getTime());
            return slotDAO.getSlotsByCenterAndDate(centerId, sqlDate);
        } catch (Exception e) {
            System.out.println("Error fetching slots: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}
