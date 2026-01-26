package com.flipfit.business;

import com.flipfit.bean.Slot;

public interface GymOwnerInterface {
    // Adds a request for a new center
    void addCenter(String name, String ownerEmail, String city);

    // Adds a specific slot to an existing center
    void addSlot(String centerId, Slot slot);
}