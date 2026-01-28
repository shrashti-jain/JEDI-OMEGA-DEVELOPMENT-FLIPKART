package com.flipfit.utils;

public class ValidationUtils {

    public static boolean isValidEmail(String email) {
        return email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
    }

    public static boolean isValidContact(String contact) {
        return contact.matches("\\d{10}");
    }

    public static boolean isFullName(String name) {
        return name.trim().contains(" ");
    }
}