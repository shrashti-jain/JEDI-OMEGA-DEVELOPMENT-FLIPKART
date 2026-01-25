package com.flipfit.bean;

public class GymOwner extends user{
    public GymOwner(){}

    public GymOwner(String userId, String name , String email , String phone){
        super(userId,name,email,phone);
    }
    public void addCenter(GymCenter center){}
    public void addSlot(String centerId,Slot slot){}
    public void updateSlotCapacity(String slotId,int capacity){}

}
