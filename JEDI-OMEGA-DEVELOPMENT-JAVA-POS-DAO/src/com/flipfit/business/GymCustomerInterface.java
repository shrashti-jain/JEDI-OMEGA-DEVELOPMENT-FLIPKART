package com.flipfit.business;

import com.flipfit.bean.Booking;
import com.flipfit.bean.GymCenter;
import com.flipfit.bean.Slot;
import java.util.Date;
import java.util.List;

//TODO: Auto-generated Javadoc
/**
* The Interface GymCustomerInterface.
* Defines the operations available to a Gym Customer in the FlipFit system.
* Includes capabilities for browsing gyms, checking slot availability, and managing booking lifecycles.
*
* @author Krishna Nirvas
* @ClassName GymCustomerInterface
*/
public interface GymCustomerInterface {

	/**
     * View centers.
     * Retrieves a list of approved gym centers in a specific city.
     *
     * @param city the city name to filter by
     * @return the list of approved gym centers
     */
    List<GymCenter> viewCenters(String city);

    /**
     * View slot availability.
     * Fetches the list of slots and their current seat availability for a specific gym and date.
     *
     * @param centerId the unique ID of the gym center
     * @param date the date for which availability is requested
     * @return the list of slots with availability details
     */
    List<Slot> viewSlotAvailability(String centerId, Date date);

    /**
     * Book slot.
     * Initiates a booking request for a specific slot.
     *
     * @param userId the ID of the user making the booking
     * @param slotId the ID of the slot to book
     * @param centerId the ID of the gym center
     * @param date the date of the booking
     * @return the booking object if successful, null otherwise
     */
    Booking bookSlot(String userId, String slotId, String centerId, Date date);

    /**
     * Cancel booking.
     * Cancels an existing booking and releases the reserved seat.
     *
     * @param bookingId the unique ID of the booking to cancel
     * @return true, if cancellation was successful
     */
    boolean cancelBooking(String bookingId);

    /**
     * View bookings.
     * Retrieves the history of bookings made by a specific user.
     *
     * @param userId the ID of the user
     * @return the list of bookings associated with the user
     */
    List<Booking> viewBookings(String userId);

    Booking checkConflict(String userEmail, Date date, String slotTime);
}