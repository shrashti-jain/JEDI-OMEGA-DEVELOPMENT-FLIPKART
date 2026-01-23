package com.flipfit.business;

import com.flipfit.bean.Booking;
import com.flipfit.bean.GymCenter;
import com.flipfit.bean.Slot;
import java.util.Date;
import java.util.List;

public interface GymCustomerInterface {
    // Fixed: Now accepts 'city' and returns a List (Incompatible types fix)
    List<GymCenter> viewCenters(String city);

    // Fixed: Now returns List<Slot> instead of void
    List<Slot> viewSlotAvailability(String centerId, Date date);

    // Fixed: Now accepts 4 arguments to match createBooking() in client
    Booking bookSlot(String userId, String slotId, String centerId, Date date);

    // Fixed: Now accepts 1 argument and returns boolean
    boolean cancelBooking(String bookingId);

    // Fixed: Now accepts userId
    List<Booking> viewMyBookings(String userId);
}