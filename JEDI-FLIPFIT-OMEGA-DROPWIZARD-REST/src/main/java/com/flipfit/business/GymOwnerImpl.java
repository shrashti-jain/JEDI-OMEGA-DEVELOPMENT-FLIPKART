package com.flipfit.business;

import com.flipfit.bean.GymCenter;
import com.flipfit.bean.Slot;
import com.flipfit.dao.GymCenterDAO;
import com.flipfit.dao.GymCenterDAOImpl;
import com.flipfit.dao.GymOwnerDAO;
import com.flipfit.dao.GymOwnerDAOImpl;
import com.flipfit.dao.SlotDAO;
import com.flipfit.dao.SlotDAOImpl;
import com.flipfit.exception.ApprovalPendingException;
import com.flipfit.exception.FlipFitException;

import java.util.Date;
import java.util.List;

/**
 * Enhanced Gym Owner Business Logic.
 * Manages gym center and slot lifecycles with strict approval checks.
 * * @author Shreya / Krishna Nirvas (Integrated)
 * @ClassName "GymOwnerImpl"
 */
public class GymOwnerImpl implements GymOwnerInterface {

    private final GymCenterDAO gymCenterDAO = new GymCenterDAOImpl();
    private final SlotDAO slotDAO = new SlotDAOImpl();
    private final GymOwnerDAO gymOwnerDAO = new GymOwnerDAOImpl();

    /**
     * Adds a new gym center for an owner.
     * MIGRATED LOGIC: Validates owner approval status before submission.
     */
    /**
     * Adds a new gym center for an owner.
     * Synchronized with the 7-parameter interface method and Integer-ID architecture.
     */
    @Override
    public void addCenter(int userId, String name, String location, String city, String pincode, String gstNo, int capacity) {
        // 1. Fetch internal OwnerId based on the logged-in UserId
        int ownerId = gymOwnerDAO.getOwnerIdByUserId(userId);

        if (ownerId == -1) {
            throw new ApprovalPendingException("Gym Owner profile is not yet approved by Admin. Center submission blocked.");
        }

        // 2. Map to Bean using the 7-parameter constructor
        // The constructor order is: ownerId, centerName, location, city, pincode, gstNo, capacity
        GymCenter newCenter = new GymCenter(ownerId, name, location, city, pincode, gstNo, capacity);

        // 3. Persist to database via DAO
        if (gymCenterDAO.addGymCenter(newCenter)) {
            System.out.println("Gym Center request submitted successfully for: " + name + " (Capacity: " + capacity + ")");
        } else {
            throw new FlipFitException("Database Error: Failed to submit Gym Center details.");
        }
    }
    /**
     * Adds a time slot to a specific gym center.
     * MIGRATED FEATURE: Verifies if the SPECIFIC center is approved before adding slots.
     */
    @Override
    public void addSlot(int centerId, Slot slot) {
        // 1. Fetch approval status directly from the DB
        boolean approved = gymCenterDAO.checkApprovalStatus(centerId);

        if (!approved) {
            // 2. Throw exception to stop the process
            throw new ApprovalPendingException("Access Denied: Center ID " + centerId + " is not yet approved by Admin.");
        }

        // 3. Only proceed if approved is true
        if (slotDAO.addSlot(slot)) {
            System.out.println("Slot successfully added to Center: " + centerId);
        } else {
            throw new FlipFitException("Failed to save slot to database.");
        }
    }
    /**
     * Retrieves all gym centers owned by a gym owner.
     */
    @Override
    public List<GymCenter> getCentersByOwner(int userId) {
        int ownerId = gymOwnerDAO.getOwnerIdByUserId(userId);

        if (ownerId == -1) {
            throw new ApprovalPendingException("Gym Owner is not approved or does not exist.");
        }

        return gymCenterDAO.getCentersByOwner(ownerId);
    }

    /**
     * Verifies if the gym center belongs to the specified user.
     */
    @Override
    public boolean isCenterOwnedByMe(int userId, int centerId) {
        // 1. Get the internal Owner ID for the logged-in user
        int ownerId = gymOwnerDAO.getOwnerIdByUserId(userId);

        // 2. Fetch the list of centers belonging to this owner
        List<GymCenter> myCenters = gymCenterDAO.getCentersByOwner(ownerId);

        // 3. Check if the requested centerId exists in their list
        return myCenters.stream().anyMatch(c -> c.getCenterId() == centerId);
    }

    /**
     * MIGRATED FEATURE: Checks approval status of a specific center.
     */
    @Override
    public boolean isCenterApproved(int centerId) {
        return gymCenterDAO.checkApprovalStatus(centerId);
    }

    // --- STATIC HELPERS (Used by Customer Business Layer) ---

    public static List<Slot> getSlotsByCenter(int centerId, Date date) {
        SlotDAO sDAO = new SlotDAOImpl();
        return sDAO.getSlotsByCenterAndDate(centerId, date);
    }

    public static String getCenterNameById(int centerId) {
        GymCenterDAO gDAO = new GymCenterDAOImpl();
        return gDAO.getCenterNameById(centerId);
    }
}