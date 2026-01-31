package com.flipfit.dao;

import com.flipfit.bean.Waitlist;
import java.util.Date;
import java.util.List;

public interface WaitlistDAO {
    /** Adds a user to the waitlist for a specific slot and date */
    boolean addToWaitlist(int userId, int slotId, Date date);

    /** Retrieves the oldest entry (first person in line) for a slot */
    Waitlist getNextInLine(int slotId, Date date);

    /** Removes a specific entry after they have been promoted to a booking */
    boolean removeFromWaitlist(int waitlistId);

    List<Waitlist> getWaitlistByUserId(int userId);
}