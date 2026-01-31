package com.flipfit.business;

import com.flipfit.bean.GymCenter;
import com.flipfit.bean.Slot;
import java.util.List;

public interface GymOwnerInterface {


    public void addSlot(int centerId, Slot slot);

    void addCenter(int userId, String name, String location, String city, String pincode, String gstNo, int capacity);

    List<GymCenter> getCentersByOwner(int userId);

    boolean isCenterOwnedByMe(int userId, int centerId);

    boolean isCenterApproved(int centerId);
}
