package com.flipfit.business;

public class GymOwnerImpl implements GymOwnerInterface {
    @Override
    public void addCenter(String name, String location, String city) {
        System.out.println("New Gym Center '" + name + "' added at " + location + ", " + city);
    }

    @Override
    public void addSlot(String centerId, int capacity) {
        System.out.println("Created a new slot with " + capacity + " seats for center: " + centerId);
    }

    @Override
    public void updateSlotCapacity(String slotId, int newCapacity) {
        System.out.println("Slot " + slotId + " capacity updated to " + newCapacity);
    }
}