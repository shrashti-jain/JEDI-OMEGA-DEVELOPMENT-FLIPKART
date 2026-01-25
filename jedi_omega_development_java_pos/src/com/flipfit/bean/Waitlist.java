/**
 * 
 */
package com.flipfit.bean;
import java.util.LinkedList;
import java.util.Queue;
/**
 * 
 */
public class Waitlist {
//
//	private Queue<GymCustomer> usersQueue;
//
//	public Waitlist() {
//		this.usersQueue = new LinkedList<>();
//	}
//	public Queue<GymCustomer> getUsersQueue(){
//		return usersQueue;
//	}
//
//	public void setUsersQueue(Queue<GymCustomer> usersQueue) {
//		this.usersQueue = usersQueue;
//	}


	private Queue<GymCustomer> userQueue = new LinkedList<>();
	public void addToWaitlist(GymCustomer user){
		userQueue.add(user);
	}
	public void removeUser(){
		userQueue.poll();
	}
	public GymCustomer releaseSeat(){
		return userQueue.poll();
	}

}
