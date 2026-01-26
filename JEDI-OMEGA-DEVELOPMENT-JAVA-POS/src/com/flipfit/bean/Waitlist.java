package com.flipfit.bean;
import java.util.Queue;
import java.util.LinkedList;

public class Waitlist {
    // Note: Diagram specifies a Queue of GymCustomer
    private Queue<GymCustomer> usersQueue = new LinkedList<>();

    public Queue<GymCustomer> getUsersQueue() { return usersQueue; }
    public void setUsersQueue(Queue<GymCustomer> usersQueue) { this.usersQueue = usersQueue; }
}