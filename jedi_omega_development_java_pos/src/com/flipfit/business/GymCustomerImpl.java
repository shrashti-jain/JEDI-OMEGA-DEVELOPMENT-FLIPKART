package com.flipfit.business;

import java.util.Date;

public class GymCustomerImpl implements GymCustomerInterface {
    @Override
    public void viewCenter() {

    }

    @Override
    public boolean viewSlotAvilability(String centerId, Date date) {
        return false;
    }

    @Override
    public boolean bookSlot(int slotId) {
        return false;
    }

    @Override
    public boolean cancelBooking(String bookingId) {
        return false;
    }

    @Override
    public void viewMyBookings() {

    }
}
