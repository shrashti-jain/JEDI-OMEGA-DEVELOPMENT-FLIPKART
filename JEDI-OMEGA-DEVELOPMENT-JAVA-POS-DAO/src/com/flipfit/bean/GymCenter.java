package com.flipfit.bean;

public class GymCenter {
    private String centerId;
    private String centerName;
    private String location; // Detailed address
    private String city;
    private String pincode;  // New
    private String gstNo;    // New
    private String ownerEmail;
    private boolean isApproved;

    // Updated Constructor
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    // Getters and Setters
    public String getCenterId() { return centerId; }
    public String getCenterName() { return centerName; }
    public String getLocation() { return location; }
    public String getPincode() { return pincode; }
    public String getGstNo() { return gstNo; }
    public String getOwnerEmail() { return ownerEmail; }
    public boolean isApproved() { return isApproved; }
    public void setApproved(boolean approved) { isApproved = approved; }
}