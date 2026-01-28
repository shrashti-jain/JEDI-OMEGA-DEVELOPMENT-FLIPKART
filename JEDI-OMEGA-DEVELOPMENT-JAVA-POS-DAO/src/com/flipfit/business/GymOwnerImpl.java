package com.flipfit.business;

import com.flipfit.bean.Slot;
import com.flipfit.bean.GymCenter;
import com.flipfit.dao.GymCenterDAO; // New DAO for database interaction
import com.flipfit.dao.SlotDAO;      // New DAO for slot persistence

import java.util.*;

//TODO: Auto-generated Javadoc
/**
* The Class GymOwnerImpl.
* Implementation of the Gym Owner Interface.
* Manages the lifecycle of gym centers and slots for a specific gym owner.
* Utilizes DAOs to persist data to the MySQL database.
*
* @author Krishna Nirvas
* @ClassName GymOwnerImpl
*/
public class GymOwnerImpl implements GymOwnerInterface {

    // Replacing static lists with DAOs
    private GymCenterDAO gymCenterDAO = new GymCenterDAO();
    private SlotDAO slotDAO = new SlotDAO();

    /**
     * Adds a new Gym Center to the system.
     * Generates a unique Center ID and sets the initial state to pending approval.
     * Corresponds to Stage 4 of the owner onboarding flow.
     *
     * @param name the name of the gym center
     * @param ownerEmail the email of the owner registering the center
     * @param location the physical location/address
     * @param city the city where the gym is located
     * @param pincode the postal code
     * @param gstNo the GST registration number for tax verification
     */
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

    /**
     * Adds a time slot to a specific gym center.
     * Performs a strict check to ensure the center is approved before allowing slot creation.
     *
     * @param centerId the unique ID of the gym center
     * @param slot the slot object containing timing and capacity details
     */
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
    /**
     * Checks if a gym center is approved by the admin.
     * Used as a helper method for verification before sensitive operations.
     *
     * @param centerId the unique ID of the gym center
     * @return true if the center is approved, false otherwise
     */
    @Override
    public boolean isCenterApproved(String centerId) {
        return gymCenterDAO.checkApprovalStatus(centerId);
    }

    /**
     * Retrieves all gym centers owned by a specific owner.
     * Fetches live data from the database via the DAO.
     *
     * @param ownerEmail the email of the gym owner
     * @return List of GymCenter objects belonging to the owner
     */
    @Override
    public List<GymCenter> getCentersByOwner(String ownerEmail) {
        // Fetches directly from the MySQL database via DAO
        return gymCenterDAO.getCentersByOwnerEmail(ownerEmail);
    }

    // --- STATIC HELPERS FOR CUSTOMER SERVICE ---

    /**
     * Static helper to get slots for a specific center and date.
     * Used primarily by the Customer Service layer to view availability.
     *
     * @param centerId the unique ID of the gym center
     * @param date the date for which slots are requested
     * @return List of available Slot objects
     */
    public static List<Slot> getSlotsByCenter(String centerId, Date date) {
        // Calls the database instead of local list allSlots
        SlotDAO slotDAO = new SlotDAO();
        return slotDAO.getSlotsByCenterAndDate(centerId, date);
    }

    /**
     * Static helper to retrieve a specific slot by its ID.
     * Used internally for booking verification.
     *
     * @param slotId the unique ID of the slot
     * @return the Slot object if found
     */
    public static Slot getSlotById(String slotId) {
        // We instantiate the DAO to fetch the specific slot from MySQL
        SlotDAO slotDAO = new SlotDAO();
        return slotDAO.getSlotById(slotId);
    }

    /**
     * Static helper to get all approved centers in a city.
     * Used by the Customer Service layer for gym browsing.
     *
     * @param city the city name
     * @return List of approved GymCenter objects
     */
    public static List<GymCenter> getApprovedCentersByCity(String city) {
        // Used by Customers to view verified gyms
        GymCenterDAO gymCenterDAO = new GymCenterDAO();
        return gymCenterDAO.getApprovedCentersByCity(city);
    }

    /**
     * Static helper to get the name of a center by its ID.
     * Useful for display purposes in booking confirmations.
     *
     * @param centerId the unique ID of the gym center
     * @return the name of the gym center
     */
    public static String getCenterNameById(String centerId) {
        GymCenterDAO gymCenterDAO = new GymCenterDAO();
        return gymCenterDAO.getCenterNameById(centerId);
    }
}