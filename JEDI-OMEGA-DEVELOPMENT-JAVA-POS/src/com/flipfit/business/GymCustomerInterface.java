package com.flipfit.business;

import com.flipfit.bean.Booking;
import com.flipfit.bean.GymCenter;
import com.flipfit.bean.Slot;
import java.util.Date;
import java.util.List;

public interface GymCustomerInterface {

    List<GymCenter> viewCenters(String city);

    List<Slot> viewSlotAvailability(String centerId, Date date);

    Booking bookSlot(String userId, String slotId, String centerId, Date date);

    boolean cancelBooking(String bookingId);

    List<Booking> viewBookings(String userId);

    Booking checkConflict(String userEmail, Date date, String slotTime);
}