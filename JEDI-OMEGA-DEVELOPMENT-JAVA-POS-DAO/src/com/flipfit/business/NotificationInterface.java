package com.flipfit.business;

import com.flipfit.bean.User;

public interface NotificationInterface {
    /**
     * Sends a notification to the specified user.
     */
    void sendNotification(User user, String message);
}