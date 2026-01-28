package com.flipfit.business;
import com.flipfit.bean.User;

//TODO: Auto-generated Javadoc
/**
* The Class NotificationImpl.
* Implementation of the notification system for the FlipFit application.
* Handles the logic for sending alerts and messages to users (Customers, Owners, Admins).
* Currently simulates notifications via console output.
*
* @author Krishna Nirvas
* @ClassName NotificationImpl
*/
public class NotificationImpl {
	
	/**
     * Send notification.
     * Simulates sending a notification (e.g., Email or SMS) to a specific user.
     * Prints the message to the console for demonstration purposes.
     *
     * @param user the user object to whom the notification is being sent
     * @param message the actual content of the notification message
     */
    public void sendNotification(User user, String message) {
        System.out.println("Notification sent to " + user.getName() + ": " + message);
    }
}