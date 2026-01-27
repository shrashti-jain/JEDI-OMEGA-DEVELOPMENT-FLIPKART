package com.flipfit.bean;

public class GymCenter {
    private String centerId;
    private String centerName;
    private String location;
    private String city;
    private String ownerEmail; // Crucial for linking gyms to owners
    private boolean isApproved;

    public GymCenter(String centerId, String centerName, String location, String city, String ownerEmail) {
        this.centerId = centerId;
        this.centerName = centerName;
        this.location = location;
        this.city = city;
        this.ownerEmail = ownerEmail;
        this.isApproved = false; // Default is pending
    }

    // Getters and Setters
    public String getCenterId() { return centerId; }
    public String getCenterName() { return centerName; }
    public String getCity() { return city; }
    public String getOwnerEmail() { return ownerEmail; }
    public boolean isApproved() { return isApproved; }
    public void setApproved(boolean approved) { isApproved = approved; }
}