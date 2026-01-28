package com.flipfit.exception;

//TODO: Auto-generated Javadoc
/**
* The Class SlotFullException.
* Custom exception thrown when a user attempts to book a slot that has reached its maximum capacity.
* Ensures that the number of bookings does not exceed the total seats available in a gym slot.
*
* @author Shrashti
* @ClassName SlotFullException
*/
public class SlotFullException extends Exception {
	
	/**
     * Instantiates a new Slot full exception.
     *
     * @param message the specific error message describing the full slot condition
     */
    public SlotFullException(String message) {
        super(message);
    }
}
