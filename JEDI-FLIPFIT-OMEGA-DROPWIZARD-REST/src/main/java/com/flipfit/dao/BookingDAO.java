package com.flipfit.dao;

import com.flipfit.bean.Booking;

import java.util.Date;
import java.util.List;

public interface BookingDAO {

    void createBooking(Booking booking);

    boolean deleteBooking(String bookingId);

    void cancelBooking(String bookingId);

    List<Booking> getBookingsByUser(int userId);

    Booking findBookingConflict(int userId, Date date, String slotTime);

    Booking getBookingById(String bookingId);
}
