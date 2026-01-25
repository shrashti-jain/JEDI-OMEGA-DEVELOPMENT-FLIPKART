package com.flipfit.bean;

public class Notification {
	
	private String notificationId;
	private String message;
	private String type;


	public Notification(){}

	public Notification(String notificationId, String message, String type){
		this.notificationId=notificationId;
		this.message=message;
		this.type=type;
	}
	public void sendNotification(User user){}

	public String getNotificationId() {
		return notificationId;
	}
	public String getMessage() {
		return message;
	}
	public String getType() {
		return type;
	}
}
