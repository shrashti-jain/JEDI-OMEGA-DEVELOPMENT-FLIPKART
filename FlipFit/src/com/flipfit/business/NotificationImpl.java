package com.flipfit.business;
import com.flipfit.bean.User;

public class NotificationImpl {
    public void sendNotification(User user, String message) {
        System.out.println("Notification sent to " + user.getName() + ": " + message);
    }
}