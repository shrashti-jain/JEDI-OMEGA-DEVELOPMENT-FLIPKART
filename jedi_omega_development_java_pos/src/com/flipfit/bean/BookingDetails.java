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
		
		public String getBookingId() {
			return bookingId;
		}
		public void setBookingId(String bookingId) {
			this.bookingId = bookingId;
		}
		public String getUserId() {
			return userId;
		}
		public void setUserId(String userId) {
			this.userId = userId;
		}
		public String getCenterId() {
			return centerId;
		}
		public void setCenterId(String centerId) {
			this.centerId = centerId;
		}
		public String getSlotId() {
			return slotId;
		}
		public void setSlotId(String slotId) {
			this.slotId = slotId;
		}
		public LocalDateTime getBookingTime() {
			return bookingTime;
		}
		public void setBookingTime(LocalDateTime bookingTime) {
			this.bookingTime = bookingTime;
		}
		
		
	}
