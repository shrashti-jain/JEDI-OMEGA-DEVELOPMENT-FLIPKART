/**
 * 
 */
package com.flipfit.bean;
//import com.flipfit.bean.GymCustomer;
import java.util.LinkedList;
import java.util.Queue;

/**
 * 
 */
public class Waitlist {
	
	private Queue<GymCustomer> usersQueue;
	
	public Waitlist() {
		this.usersQueue = new LinkedList<>();
	}
	public Queue<GymCustomer> getUsersQueue(){
		return usersQueue;
	}
	
	public void setUsersQueue(Queue<GymCustomer> usersQueue) {
		this.usersQueue = usersQueue;
	}

}
