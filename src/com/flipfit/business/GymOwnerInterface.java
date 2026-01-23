package com.flipfit.business;

public interface GymOwnerInterface {
    void addCenter(String name, String location, String city);
    void addSlot(String centerId, int capacity);
    void updateSlotCapacity(String slotId, int newCapacity);
}