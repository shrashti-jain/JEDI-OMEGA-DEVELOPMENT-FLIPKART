package com.flipfit.business;

public interface GymOwnerInterface {
    public void addCenter(String name, String location, String city);
    public void addSlot(String centerID, String SlotID);
    public void updateSlotCapacity(String slotID, int capacity);
}
