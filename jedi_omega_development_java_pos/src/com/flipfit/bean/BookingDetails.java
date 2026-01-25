package com.flipfit.bean;

	import java.time.LocalDateTime;

	/**
	 * 
	 */
	public class BookingDetails {
		private String bookingId;
		private String userId;
		private String centerId;
		private String slotId;
		private LocalDateTime bookingTime;

		public BookingDetails(String bookingId, String userId, String centerId, String slotId){
			this.bookingId=bookingId;
			this.userId=userId;
			this.centerId=centerId;
			this.slotId=slotId;
			this.bookingTime=LocalDateTime.now();
		}
		
		public String getBookingId() {
			return bookingId;
		}
		public String getUserId() {
			return userId;
		}
		public String getCenterId() {
			return centerId;
		}
		public String getSlotId() {
			return slotId;
		}
		public LocalDateTime getBookingTime() {
			return bookingTime;
		}
	}
