package com.flipfit.business;

import com.flipfit.bean.GymCenter;
import com.flipfit.bean.Slot;

import java.util.List;

//TODO: Auto-generated Javadoc
/**
* The Interface GymOwnerInterface.
* Defines the operations available to a Gym Owner in the FlipFit system.
* Includes capabilities for registering gym centers, managing time slots, and viewing owned centers.
*
* @author Krishna Nirvas
* @ClassName GymOwnerInterface
*/
public interface GymOwnerInterface {
	
	/**
     * Adds the center.
     * Registers a new gym center request in the system for admin approval.
     *
     * @param name the name of the gym center
     * @param ownerEmail the email of the gym owner
     * @param location the physical address of the gym
     * @param city the city where the gym is located
     * @param pincode the postal code of the area
     * @param gst the GST identification number for tax verification
     */
    void addCenter(String name, String ownerEmail, String location, String city, String pincode, String gst);

    /**
     * Checks if is center approved.
     * Verifies if a specific gym center has been approved by an administrator.
     *
     * @param centerId the unique ID of the gym center
     * @return true, if the center is approved
     */
    boolean isCenterApproved(String centerId);

    /**
     * Adds the slot.
     * Creates a new time slot for a specific gym center.
     * This operation typically requires the center to be approved first.
     *
     * @param centerId the unique ID of the gym center
     * @param newSlot the slot object containing timing and capacity details
     */
    void addSlot(String centerId, Slot newSlot);

    /**
     * Gets the centers by owner.
     * Retrieves a list of all gym centers registered under a specific owner's email.
     *
     * @param ownerEmail the email of the gym owner
     * @return the list of gym centers owned by the user
     */
    List<GymCenter> getCentersByOwner(String ownerEmail);
    // Adds a request for a new center

}