package com.flipfit.business;

import com.flipfit.bean.Booking;
import com.flipfit.bean.GymCenter;
import com.flipfit.bean.Slot;
import com.flipfit.bean.Waitlist;

import java.util.Date;
import java.util.List;

public interface GymCustomerInterface {

    List<GymCenter> viewCenters(String city);

    List<Slot> viewSlotAvailability(int centerId, Date date);

    Booking bookSlot(int userId, int slotId, int centerId, Date date);

    boolean cancelBooking(String bookingId);

    List<Booking> viewBookings(String userEmail);

    Booking checkConflict(int userId, Date date, String slotTime);

    Slot getSlotById(int slotId);

    List<Waitlist> viewWaitlist(String email);

    boolean addWaitlist(int userId, int slotId, Date date);

    //void promoteUser(Waitlist wl, Booking oldBooking);
}
