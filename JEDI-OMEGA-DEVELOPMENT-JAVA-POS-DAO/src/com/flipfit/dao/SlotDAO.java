package com.flipfit.dao;

import com.flipfit.bean.Slot;

import java.util.Date;
import java.util.List;

public interface SlotDAO {

    boolean addSlot(Slot slot);

    // ✅ ADD THIS
    Slot getSlotById(int slotId);
    List<Slot> getSlotsByCenterAndDate(int centerId, Date date);
    boolean decreaseAvailableSeats(int slotId);

    boolean increaseAvailableSeats(int slotId);
}
