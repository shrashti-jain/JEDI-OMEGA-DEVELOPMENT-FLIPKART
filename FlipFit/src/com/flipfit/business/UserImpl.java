package com.flipfit.business;

public class UserImpl implements UserInterface {

    @Override
    public void register(String userId, String name, String email, String password, String roleName) {
        // Logic: 1. Create a User entry
        // 2. Based on roleName, create entries in specific tables/lists
        System.out.println("User " + name + " registered successfully as " + roleName);

        if (roleName.equalsIgnoreCase("Customer")) {
            System.out.println("Creating Customer Profile...");
        } else if (roleName.equalsIgnoreCase("GymOwner")) {
            System.out.println("Creating Gym Owner Profile (Pending Admin Approval)...");
        }
    }

    @Override
    public boolean login(String email, String password) {
        System.out.println("Checking credentials for: " + email);
        return true; // Simplified for now
    }

    @Override
    public void logout() {
        System.out.println("User logged out.");
    }
}