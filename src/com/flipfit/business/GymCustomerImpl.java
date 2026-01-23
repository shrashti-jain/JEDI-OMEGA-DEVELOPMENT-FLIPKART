package com.flipfit.business;

import com.flipfit.bean.Booking;
import com.flipfit.bean.GymCenter;
import com.flipfit.bean.Slot;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GymCustomerImpl implements GymCustomerInterface {

    @Override
    public List<GymCenter> viewCenters(String city) {
        // In a real app, this fetches from a DB based on city
        List<GymCenter> centers = new ArrayList<>();
        centers.add(new GymCenter("C101", "FlipFit HSR", "HSR Layout", city));
        return centers;
    }

    @Override
    public List<Slot> viewSlotAvailability(String centerId, Date date) {
        List<Slot> slots = new ArrayList<>();
        slots.add(new Slot("S1", 20)); // Capacity 20
        return slots;
    }

    @Override
    public Booking bookSlot(String userId, String slotId, String centerId, Date date) {
        // Logic to verify slot capacity would happen here
        return new Booking("B" + System.currentTimeMillis(), userId, slotId, "Confirmed");
    }

    @Override
    public List<Booking> viewMyBookings(String userId) {
        return new ArrayList<>();
    }

    @Override
    public boolean cancelBooking(String bookingId) {
        System.out.println("Cancelling booking: " + bookingId);
        return true;
    }
}