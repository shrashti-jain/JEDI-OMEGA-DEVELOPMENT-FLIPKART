package com.flipfit.bean;

public class GymCenter {
    private String centerId;
    private String centerName;
    private String location;
    private String city;
    private String ownerEmail; // Crucial for linking gyms to owners
    private boolean isApproved;

    // Default constructor for DAO
    public GymCenter() {
    }

    public GymCenter(String centerId, String centerName, String location, String city, String ownerEmail) {
        this.centerId = centerId;
        this.centerName = centerName;
        this.location = location;
        this.city = city;
        this.ownerEmail = ownerEmail;
        this.isApproved = false; // Default is pending
    }

    // Getters
    public String getCenterId() { return centerId; }
    public String getCenterName() { return centerName; }
    public String getCity() { return city; }
    public String getOwnerEmail() { return ownerEmail; }
    public boolean isApproved() { return isApproved; }
    public String getLocation() { return location; }

    // Setters for DAO
    public void setCenterId(String centerId) { this.centerId = centerId; }
    public void setCenterName(String centerName) { this.centerName = centerName; }
    public void setLocation(String location) { this.location = location; }
    public void setCity(String city) { this.city = city; }
    public void setOwnerEmail(String ownerEmail) { this.ownerEmail = ownerEmail; }
    public void setApproved(boolean approved) { isApproved = approved; }
}