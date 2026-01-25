package com.flipfit.bean;

public class GymCustomer extends User{
    public GymCustomer(){}

    public GymCustomer(String userId, String name, String email, String phone){
        super(userId,name,email,phone);
    }

    public void viewCenters(){}
    public void viewSlotAvailability(){}
    public void bookSlot(){}
    public void cancelBooking(){}
    public void viewMyBooking(){}
}
