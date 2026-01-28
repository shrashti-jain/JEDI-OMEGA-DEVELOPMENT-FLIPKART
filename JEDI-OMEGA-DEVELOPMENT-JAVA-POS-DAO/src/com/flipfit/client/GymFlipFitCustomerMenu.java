package com.flipfit.client;

import com.flipfit.bean.Booking;
import com.flipfit.bean.GymCenter;
import com.flipfit.bean.Slot;
import com.flipfit.business.GymCustomerInterface;
import com.flipfit.business.GymCustomerImpl;
import com.flipfit.business.GymOwnerImpl;
import com.flipfit.utils.ValidationUtils; // For the "Immediate" validation

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class GymFlipFitCustomerMenu {

    private static final GymCustomerInterface customerService = new GymCustomerImpl();

    public static void showCustomerMenu(Scanner scanner, String userId) {
        boolean exit = false;
        while (!exit) {
            System.out.println("\n========================================");
            System.out.println("      CUSTOMER DASHBOARD (" + userId + ") ");
            System.out.println("========================================");
            System.out.println("1. View Gym Centers by City");
            System.out.println("2. View Slot Availability");
            System.out.println("3. Book a Slot");
            System.out.println("4. View My Bookings");
            System.out.println("5. Cancel Booking");
            System.out.println("6. Logout");
            System.out.print("\nEnter choice: ");

            // Input validation for integer choice
            if (!scanner.hasNextInt()) {
                System.out.println("Invalid input. Please enter a number (1-6).");
                scanner.next();
                continue;
            }

            int choice = scanner.nextInt();
            scanner.nextLine(); // Clear buffer

            switch (choice) {
                case 1 -> viewGymsByCity(scanner);
                case 2 -> viewSlots(scanner);
                case 3 -> createBooking(scanner, userId);
                case 4 -> viewBookings(userId);
                case 5 -> cancelBooking(scanner, userId);
                case 6 -> { System.out.println("Logging out..."); exit = true; }
                default -> System.out.println("Invalid option. Please choose 1-6.");
            }
        }
    }

    private static void viewGymsByCity(Scanner scanner) {
        System.out.print("Enter City Name: ");
        String city = scanner.next();

        // Fetches only centers where isApproved = true from the DB
        List<GymCenter> centers = customerService.viewCenters(city);

        if (centers.isEmpty()) {
            System.out.println("No approved gyms found in " + city);
        } else {
            System.out.println("\n--- Approved Gyms in " + city + " ---");
            System.out.printf("%-10s | %-20s | %-15s\n", "ID", "Name", "Location");
            System.out.println("---------------------------------------------");
            centers.forEach(c -> System.out.printf("%-10s | %-20s | %-15s\n",
                    c.getCenterId(), c.getCenterName(), c.getLocation()));
        }
    }

    private static void viewSlots(Scanner scanner) {
        System.out.print("Enter Center ID: ");
        String centerId = scanner.next();
        System.out.print("Enter Date (yyyy-MM-dd): ");
        String dateStr = scanner.next();

        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd").parse(dateStr);
            List<Slot> slots = customerService.viewSlotAvailability(centerId, date);

            if (slots.isEmpty()) {
                System.out.println("No slots available for this center on " + dateStr);
            } else {
                System.out.println("\n--- Available Slots for " + dateStr + " ---");
                System.out.printf("%-10s | %-20s | %-15s\n", "Slot ID", "Time", "Seats Left");
                System.out.println("---------------------------------------------");
                slots.forEach(s -> System.out.printf("%-10s | %-20s | %-15s\n",
                        s.getSlotId(), s.getStartTime() + " - " + s.getEndTime(), s.getAvailableSeats()));
            }
        } catch (Exception e) {
            System.out.println("Error: Use yyyy-MM-dd format.");
        }
    }

    private static void createBooking(Scanner scanner, String userId) {
        System.out.print("Enter Center ID: ");
        String centerId = scanner.next();
        System.out.print("Enter Slot ID: ");
        String slotId = scanner.next();
        System.out.print("Enter Date (yyyy-MM-dd): ");
        String dateStr = scanner.next();

        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd").parse(dateStr);
            Slot selectedSlot = GymOwnerImpl.getSlotById(slotId);

            if (selectedSlot == null) {
                System.out.println("Error: Slot ID not found.");
                return;
            }

            // Check if slot has capacity
            if (selectedSlot.getAvailableSeats() <= 0) {
                System.out.println("Error: Slot is fully booked!");
                return;
            }

            String slotTime = selectedSlot.getStartTime() + " - " + selectedSlot.getEndTime();

            // Conflict Check
            Booking conflict = customerService.checkConflict(userId, date, slotTime);
            if (conflict != null) {
                System.out.println("\n[CONFLICT] You already have a booking at " + slotTime + " in " + conflict.getGymName());
                System.out.print("Replace existing booking? (yes/no): ");
                if (scanner.next().equalsIgnoreCase("yes")) {
                    customerService.cancelBooking(conflict.getBookingId());
                } else {
                    return;
                }
            }

            Booking booking = customerService.bookSlot(userId, slotId, centerId, date);
            if (booking != null) {
                System.out.println("\nSUCCESS! Booking Confirmed. ID: " + booking.getBookingId());
                System.out.println("Remaining seats updated in database.");
            }
        } catch (Exception e) {
            System.out.println("Invalid input. Booking failed.");
        }
    }

    private static void viewBookings(String email) {
        List<Booking> myBookings = customerService.viewBookings(email);
        if (myBookings.isEmpty()) {
            System.out.println("No bookings found.");
        } else {
            System.out.println("\n----------------------- YOUR BOOKINGS -----------------------");
            System.out.printf("%-15s | %-15s | %-12s | %-12s | %-10s\n", "Booking ID", "Gym", "Date", "Time", "Status");
            System.out.println("-------------------------------------------------------------");
            myBookings.forEach(b -> System.out.printf("%-15s | %-15s | %-12s | %-12s | %-10s\n",
                    b.getBookingId(), b.getGymName(), new SimpleDateFormat("dd-MM-yy").format(b.getSlotDate()), b.getSlotTime(), b.getStatus()));
        }
    }

    private static void cancelBooking(Scanner scanner, String userEmail) {
        viewBookings(userEmail);
        System.out.print("\nEnter Booking ID to cancel: ");
        String bid = scanner.next();

        if (customerService.cancelBooking(bid)) {
            System.out.println("Booking " + bid + " cancelled. Seat has been restored.");
        } else {
            System.out.println("Cancellation failed. Check the ID.");
        }
    }
}