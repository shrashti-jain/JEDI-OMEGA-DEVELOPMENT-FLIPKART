package com.flipfit.business;

import java.util.Date;

public interface GymCustomerInterface {
    public void viewCenter();
    public boolean viewSlotAvilability(String centerId, Date date);
    public boolean bookSlot(int slotId);
    public boolean cancelBooking(String bookingId);
    public void viewMyBookings();
}
