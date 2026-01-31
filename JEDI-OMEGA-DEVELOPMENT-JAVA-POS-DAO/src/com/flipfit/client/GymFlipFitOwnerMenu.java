package com.flipfit.client;

import com.flipfit.bean.GymCenter;
import com.flipfit.bean.Slot;
import com.flipfit.business.GymOwnerInterface;
import com.flipfit.business.GymOwnerImpl;
import com.flipfit.exception.FlipFitException;
import com.flipfit.utility.ValidationUtils;

import java.time.LocalTime;
import java.util.List;
import java.util.Scanner;

/**
 * Enhanced Gym Owner Dashboard.
 * Merges legacy validation logic with the new Integer-ID service architecture.
 *
 */
public class GymFlipFitOwnerMenu {

    private static final GymOwnerInterface ownerService = new GymOwnerImpl();

    /**
     * Displays the gym owner dashboard menu.
     *
     */
    public static void showOwnerMenu(Scanner scanner, int ownerId) {

        boolean exit = false;

        while (!exit) {
            System.out.println("\n<----- Gym Owner Dashboard (UID: " + ownerId + ") ----->");
            System.out.println("1. Add New Gym Center (Requires Admin Approval)");
            System.out.println("2. Add Slot to Center");
            System.out.println("3. View My Centers (Status Check)");
            System.out.println("4. Logout");
            System.out.print("Enter choice: ");

            if (!scanner.hasNextInt()) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next();
                continue;
            }

            int choice = scanner.nextInt();
            scanner.nextLine(); // Clear buffer

            try {
                switch (choice) {

                    case 1 -> {
                        System.out.println("\n--- Submit Gym Center Details ---");
                        String name, location, city, pincode, gst;

                        // MIGRATED FEATURE: Loop-based validation from legacy client
                        while (true) {
                            System.out.print("Enter Gym Name: ");
                            name = scanner.nextLine();
                            if (ValidationUtils.isFullName(name)) break;
                            System.out.println(">> Error: Please enter a valid Business Name.");
                        }

                        while (true) {
                            System.out.print("Enter Full Address: ");
                            location = scanner.nextLine();
                            System.out.print("Enter City: ");
                            city = scanner.nextLine();
                            if (!location.isEmpty() && !city.isEmpty()) break;
                            System.out.println(">> Error: Location and City cannot be blank.");
                        }

                        while (true) {
                            System.out.print("Enter Pincode: ");
                            pincode = scanner.nextLine();
                            if (ValidationUtils.isValidPincode(pincode)) break;
                            System.out.println(">> Error: Invalid Pincode (6 digits required).");
                        }

                        while (true) {
                            System.out.print("Enter GST Number: ");
                            gst = scanner.nextLine().toUpperCase();
                            if (ValidationUtils.isValidGST(gst)) break;
                            System.out.println(">> Error: Invalid GST format (e.g., 22AAAAA0000A1Z5).");
                        }

                        System.out.print("Enter Base Capacity: ");
                        int capacity = scanner.nextInt();
                        scanner.nextLine();

                        // Call service with enriched metadata
                        ownerService.addCenter(ownerId, name, location, city, pincode, gst, capacity);
                        System.out.println("SUCCESS: Gym center request submitted. Waiting for Admin verification.");
                    }

                    case 2 -> {
                        System.out.print("Enter Center ID to add slots: ");
                        int centerId = scanner.nextInt();

                        // GUARD 1: Ownership Check
                        if (!ownerService.isCenterOwnedByMe(ownerId, centerId)) {
                            System.out.println("❌ [SECURITY ALERT]: This center does not belong to your account.");
                            break;
                        }

                        // GUARD 2: Approval Check
                        if (!ownerService.isCenterApproved(centerId)) {
                            System.out.println("❌ [DENIED]: This center is pending Admin approval.");
                            break;
                        }

                        // 2. Only gather details if the check passes
                        System.out.print("Enter Start Time (HH:mm): ");
                        LocalTime startTime = LocalTime.parse(scanner.next());
                        System.out.print("Enter End Time (HH:mm): ");
                        LocalTime endTime = LocalTime.parse(scanner.next());
                        System.out.print("Total Capacity (Seats): ");
                        int slotCapacity = scanner.nextInt();

                        // Note: slotId is 0 because the DB handles AUTO_INCREMENT
                        Slot slot = new Slot(0, centerId, startTime, endTime, null, slotCapacity);
                        ownerService.addSlot(centerId, slot);
                    }

                    case 3 -> {
                        System.out.println("\n--- Your Registered Centers & Verification Status ---");
                        List<GymCenter> centers = ownerService.getCentersByOwner(ownerId);

                        if (centers.isEmpty()) {
                            System.out.println("No gym centers registered under your account.");
                        } else {
                            System.out.println("----------------------------------------------------------------------------------");
                            System.out.printf("%-10s | %-15s | %-15s | %-15s | %-10s\n", "ID", "Name", "City", "GST No", "Status");
                            System.out.println("----------------------------------------------------------------------------------");

                            for (GymCenter c : centers) {
                                String status = c.isApproved() ? "APPROVED" : "PENDING";
                                System.out.printf("%-10s | %-15s | %-15s | %-15s | %-10s\n",
                                        c.getCenterId(), c.getCenterName(), c.getCity(), c.getGstNo(), status);
                            }
                        }
                    }

                    case 4 -> {
                        exit = true;
                        System.out.println("Logged out. Returning to main menu.");
                    }

                    default -> System.out.println("Invalid choice. Select 1-4.");
                }

            } catch (FlipFitException e) {
                System.out.println("\n❌ [BUSINESS ERROR] " + e.getMessage());
            } catch (Exception e) {
                System.out.println("\n❌ [SYSTEM ERROR] " + e.getMessage());
                scanner.nextLine(); // Clear buffer on generic error
            }
        }
    }
}