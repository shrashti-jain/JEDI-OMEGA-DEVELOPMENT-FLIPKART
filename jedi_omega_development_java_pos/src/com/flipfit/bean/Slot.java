package com.flipfit.bean;

public class Slot {
	private String slotId;
	private int capacity;
	private int bookedSeats;

	public Slot(){}

	public Slot(String slotId, int capacity){
		this.slotId=slotId;
		this.capacity=capacity;
		this.bookedSeats=0;
	}

	public boolean checkAvailability(){
		return bookedSeats < capacity;
	}
	public void bookSeat(){
		if(checkAvailability()){
			bookedSeats++;
		}
	}
	public void releaseSeat(){
		if(bookedSeats>0){
			bookedSeats--;
		}
	}
	
	public String getSlotId() {
		return slotId;
	}
	public int getCapacity() {
		return capacity;
	}
	public int getBookedSeat() {
		return bookedSeat;
	}
}
