package com.flipfit.bean;

// TODO: Auto-generated Javadoc
/**
 * The Class GymCenter.
 * Represents a physical gym facility registered within the FlipFit system.
 * Contains comprehensive details about the center, including location,
 * verification details (GST, Pincode), ownership, and approval status.
 *
 * @author Shreya
 * @ClassName GymCenter
 */
public class GymCenter {
    private String centerId;
    private String centerName;
    private String location; // Detailed address
    private String city;
    private String pincode;  // New
    private String gstNo;    // New
    private String ownerEmail;
    private boolean isApproved;

    /**
     * Instantiates a new Gym Center.
     * Initializes the gym center with all necessary details for registration.
     * The initial approval status is set to false, requiring Admin verification.
     *
     * @param centerId the unique identifier for the gym center
     * @param centerName the commercial name of the gym
     * @param location the detailed physical address
     * @param city the city where the gym is located
     * @param pincode the postal code for the location
     * @param gstNo the GST registration number for tax compliance
     * @param ownerEmail the email address of the gym owner
     */
    public GymCenter(String centerId, String centerName, String location, String city, String pincode, String gstNo, String ownerEmail) {
        this.centerId = centerId;
        this.centerName = centerName;
        this.location = location;
        this.city = city;
        this.pincode = pincode;
        this.gstNo = gstNo;
        this.ownerEmail = ownerEmail;
        this.isApproved = false; // Default: Pending Admin Verification
    }

    /**
     * Gets the city.
     *
     * @return the city
     */
    public String getCity() {
        return city;
    }

    /**
     * Sets the city.
     *
     * @param city the new city
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * Gets the center id.
     *
     * @return the center id
     */
    public String getCenterId() { return centerId; }

    /**
     * Gets the center name.
     *
     * @return the center name
     */
    public String getCenterName() { return centerName; }

    /**
     * Gets the location.
     *
     * @return the location
     */
    public String getLocation() { return location; }

    /**
     * Gets the pincode.
     *
     * @return the pincode
     */
    public String getPincode() { return pincode; }

    /**
     * Gets the gst no.
     *
     * @return the gst no
     */
    public String getGstNo() { return gstNo; }

    /**
     * Gets the owner email.
     *
     * @return the owner email
     */
    public String getOwnerEmail() { return ownerEmail; }

    /**
     * Checks if is approved.
     *
     * @return true, if is approved
     */
    public boolean isApproved() { return isApproved; }

    /**
     * Sets the approved.
     *
     * @param approved the new approved status
     */
    public void setApproved(boolean approved) { isApproved = approved; }
}