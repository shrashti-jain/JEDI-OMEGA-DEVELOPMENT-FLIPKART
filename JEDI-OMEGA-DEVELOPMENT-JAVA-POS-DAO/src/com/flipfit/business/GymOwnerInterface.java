package com.flipfit.business;

import com.flipfit.bean.GymCenter;
import com.flipfit.bean.Slot;

import java.util.List;

public interface GymOwnerInterface {
    void addCenter(String name, String ownerEmail, String location, String city, String pincode, String gst);

    boolean isCenterApproved(String centerId);

    void addSlot(String centerId, Slot newSlot);

    List<GymCenter> getCentersByOwner(String ownerEmail);
    // Adds a request for a new center

}