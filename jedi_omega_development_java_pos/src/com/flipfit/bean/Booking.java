/**
 * 
 */
package com.flipfit.bean;
import java.util.Date;

/**
 * 
 */
public class Booking {
	private String bookingId;
	private Date bookingDate;
	private String status;

	public Booking(){}

	public Booking(String bookingId, Date bookingDate, String status){
		this.bookingId=bookingId;
		this.bookingDate=bookingDate;
		this.status=status;
	}

	public void confirmBooking(){}
	public void cancelBooking(){}


	public String getBookingId() {
		return bookingId;
	}
	public Date getBookingDate() {
		return bookingDate;
	}
	public String getStatus() {
		return status;
	}
}