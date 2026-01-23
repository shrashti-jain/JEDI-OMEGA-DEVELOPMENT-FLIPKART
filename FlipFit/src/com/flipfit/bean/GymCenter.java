package com.flipfit.bean;

public class GymCenter {
    private String centerId;
    private String centerName; // Matches your client call: c.getCenterName()
    private String location;
    private String city;

    public GymCenter(String centerId, String centerName, String location, String city) {
        this.centerId = centerId;
        this.centerName = centerName;
        this.location = location;
        this.city = city;
    }

    public String getCenterId() { return centerId; }
    public String getCenterName() { return centerName; }
    public String getCity() { return city; }
}