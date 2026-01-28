package com.flipfit.bean;
import java.util.Queue;
import java.util.LinkedList;

//TODO: Auto-generated Javadoc
/**
* The Class Waitlist.
* Represents a waitlist mechanism for gym slots.
* Manages a queue of GymCustomer objects who are waiting for a slot to become available.
*
* @author Shreya
* @ClassName Waitlist
*/
public class Waitlist {
    // Note: Diagram specifies a Queue of GymCustomer
    private Queue<GymCustomer> usersQueue = new LinkedList<>();

    /**
     * Gets the users queue.
     * Retrieves the current queue of customers waiting for a slot.
     *
     * @return the queue of GymCustomer objects
     */
    public Queue<GymCustomer> getUsersQueue() { return usersQueue; }
    
    /**
     * Sets the users queue.
     * Updates the waitlist queue with a new set of waiting customers.
     *
     * @param usersQueue the new queue of GymCustomer objects
     */
    public void setUsersQueue(Queue<GymCustomer> usersQueue) { this.usersQueue = usersQueue; }
}