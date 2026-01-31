package com.flipfit.business;

import com.flipfit.bean.User;
import com.flipfit.exception.NotificationFailedException;

/**
 * The Class NotificationImpl.
 * Handles the logic for sending alerts and messages to users.
 * Merges old simulation logic with new validation-driven architecture.
 * * @author Shreya / Krishna Nirvas (Integrated)
 * @ClassName "NotificationImpl"
 */
public class NotificationImpl implements NotificationInterface {

    /**
     * Sends a notification message to a user.
     * Validates inputs and simulates delivery via console output.
     *
     * @param user the user to whom the notification is sent
     * @param message the notification message
     * @throws NotificationFailedException if user is null or message is invalid
     */
    public void sendNotification(User user, String message) {

        // 1. New Logic: Strict Validation
        if (user == null) {
            throw new NotificationFailedException(
                    "Cannot send notification: Recipient user object is null."
            );
        }

        if (message == null || message.isBlank()) {
            throw new NotificationFailedException(
                    "Cannot send notification: Message content is empty or null."
            );
        }

        // 2. Old Logic: Simulated Notification Delivery
        // Provides visible feedback in the console as per the original project.
        System.out.println("\n[NOTIFICATION SYSTEM] Processing alert...");
        System.out.println("Message to " + user.getName() + " (" + user.getEmail() + "): " + message);
        System.out.println("[STATUS] Delivery Simulated Successfully.\n");

        // Note: Actual delivery mechanisms (Email/SMS API) can be integrated here in the future.
    }
}