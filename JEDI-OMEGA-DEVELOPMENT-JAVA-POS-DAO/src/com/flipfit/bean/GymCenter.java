package com.flipfit.bean;

/**
 * The Class GymCenter.
 * Represents a physical gym facility registered within the FlipFit system.
 * Merges legacy verification fields (GST, Pincode) with new relational IDs.
 * * @author Shreya / Krishna Nirvas (Integrated)
 * @ClassName "GymCenter"
 */
public class GymCenter {

    // Relational and Numeric Identifiers
    private int centerId;
    private int ownerId;
    private int capacity;

    // Commercial and Verification Details
    private String centerName;
    private String location; // Detailed address
    private String city;
    private String pincode;
    private String gstNo;

    // Status
    private boolean approved;

    /**
     * Default Constructor
     */
    public GymCenter() {}

    /**
     * Instantiates a new GymCenter with all necessary registration details.
     * Merges legacy verification fields with new numeric owner mapping.
     *
     * @param ownerId the numeric ID of the owner
     * @param centerName commercial name of the gym
     * @param location detailed physical address
     * @param city city location
     * @param pincode postal code
     * @param gstNo tax registration number
     * @param capacity maximum seat capacity
     */
    public GymCenter(int ownerId, String centerName, String location, String city, String pincode, String gstNo, int capacity) {
        this.ownerId = ownerId;
        this.centerName = centerName;
        this.location = location;
        this.city = city;
        this.pincode = pincode;
        this.gstNo = gstNo;
        this.capacity = capacity;
        this.approved = false; // Default: Pending Admin Verification
    }

    // --- Getters and Setters ---

    public int getCenterId() { return centerId; }
    public void setCenterId(int centerId) { this.centerId = centerId; }

    public int getOwnerId() { return ownerId; }
    public void setOwnerId(int ownerId) { this.ownerId = ownerId; }

    public String getCenterName() { return centerName; }
    public void setCenterName(String centerName) { this.centerName = centerName; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String getPincode() { return pincode; }
    public void setPincode(String pincode) { this.pincode = pincode; }

    public String getGstNo() { return gstNo; }
    public void setGstNo(String gstNo) { this.gstNo = gstNo; }

    public int getCapacity() { return capacity; }
    public void setCapacity(int capacity) { this.capacity = capacity; }

    public boolean isApproved() { return approved; }
    public void setApproved(boolean approved) { this.approved = approved; }
}