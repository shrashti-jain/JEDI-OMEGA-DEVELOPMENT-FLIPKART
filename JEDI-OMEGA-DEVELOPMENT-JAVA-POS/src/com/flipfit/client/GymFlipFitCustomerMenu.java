package com.flipfit.client;

import com.flipfit.bean.Booking;
import com.flipfit.bean.GymCenter;
import com.flipfit.bean.Slot;
import com.flipfit.business.GymCustomerInterface;
import com.flipfit.business.GymCustomerImpl;
import com.flipfit.business.GymOwnerImpl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class GymFlipFitCustomerMenu {

    // Added 'final' to resolve warning
    private static final GymCustomerInterface customerService = new GymCustomerImpl();

    public static void showCustomerMenu(Scanner scanner, String userId) {
        boolean exit = false;
        while (!exit) {
            System.out.println("\n<----- Customer Dashboard (" + userId + ") ----->");
            System.out.println("1. View Gym Centers by City\n2. View Slot Availability\n3. Book a Slot\n4. View My Bookings\n5. Cancel Booking\n6. Logout");
            System.out.print("Enter choice: ");

            int choice = scanner.nextInt();
            switch (choice) {
                case 1 -> viewGymsByCity(scanner);
                case 2 -> viewSlots(scanner);
                case 3 -> createBooking(scanner, userId);
                case 4 -> viewBookings(userId);
                case 5 -> cancelBooking(scanner,userId);
                case 6 -> { System.out.println("Logging out..."); exit = true; }
                default -> System.out.println("Invalid option.");
            }
        }
    }

    private static void viewGymsByCity(Scanner scanner) {
        System.out.print("Enter City Name: ");
        String city = scanner.next();
        List<GymCenter> centers = customerService.viewCenters(city);
        System.out.println("<-----Available Gyms for city- " + city + "----->");
        centers.forEach(c -> System.out.println("ID: " + c.getCenterId() + " | Name: " + c.getCenterName()));
    }

    private static void viewSlots(Scanner scanner) {
        System.out.print("Enter Center ID: ");
        String centerId = scanner.next();
        System.out.print("Enter Date (yyyy-MM-dd): ");
        String dateStr = scanner.next();

        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd").parse(dateStr); // Use dateStr
            List<Slot> slots = customerService.viewSlotAvailability(centerId, date);
            System.out.println("<-----Available slots for date- " + date + "----->");
            slots.forEach(s -> System.out.println("SLOT ID- " + s.getSlotId() + " Time: " + s.getStartTime() + " - " + s.getEndTime() +
                    " | Remaining Seats: " + s.getAvailableSeats()));
        } catch (Exception e) {
            System.out.println("Invalid date format.");
        }
    }

    private static void createBooking(Scanner scanner, String userId) {
        System.out.print("Enter Center ID: ");
        String centerId = scanner.next();
        System.out.print("Enter Slot ID: ");
        String slotId = scanner.next();
        System.out.print("Enter Date for booking (yyyy-MM-dd): ");
        String dateStr = scanner.next();

        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd").parse(dateStr);
            // A. Get the Slot details first to know the time
            Slot selectedSlot = GymOwnerImpl.getSlotById(slotId);
            if (selectedSlot == null) {
                System.out.println("Slot not found.");
                return;
            }
            String slotTime = selectedSlot.getStartTime() + " - " + selectedSlot.getEndTime();

            // B. Check for Conflict
            Booking conflict = customerService.checkConflict(userId, date, slotTime);

            if (conflict != null) {
                System.out.println("\n[CONFLICT] You already have a booking at " + slotTime + " in " + conflict.getGymName());
                System.out.print("Would you like to cancel the previous booking and proceed? (yes/no): ");
                String confirm = scanner.next().toLowerCase();
                scanner.nextLine();

                if (confirm.equals("yes")) {
                    customerService.cancelBooking(conflict.getBookingId());
                    System.out.println("Previous booking cancelled.");
                } else {
                    System.out.println("New booking aborted.");
                    return;
                }
            }

            // C. Finally, call the actual booking method
            Booking booking = customerService.bookSlot(userId, slotId, centerId, date);
            if (booking != null) {
                System.out.println("Booking Success! ID: " + booking.getBookingId());
            }
        } catch (Exception e) {
            System.out.println("Invalid date format. Use yyyy-MM-dd");
        }
    }

    private static void viewBookings(String email) {
        List<Booking> myBookings = customerService.viewBookings(email);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");

        if (myBookings.isEmpty()) {
            System.out.println("No active bookings found for: " + email);
        } else {
            System.out.println("\n----------------------- MY BOOKINGS -----------------------");
            System.out.printf("%-12s | %-15s | %-12s | %-15s | %-10s\n",
                    "ID", "Gym Name", "Date", "Time", "Status");
            System.out.println("-----------------------------------------------------------");

            for (Booking b : myBookings) {
                System.out.printf("%-12s | %-15s | %-12s | %-15s | %-10s\n",
                        b.getBookingId(),
                        b.getGymName(),
                        dateFormat.format(b.getSlotDate()),
                        b.getSlotTime(),
                        b.getStatus());
            }
        }
    }

    private static void cancelBooking(Scanner scanner, String userEmail) {
        // 1. Show the user their current bookings so they know the IDs
        List<Booking> myBookings = customerService.viewBookings(userEmail);

        if (myBookings.isEmpty()) {
            System.out.println("You have no active bookings to cancel.");
            return;
        }

        System.out.println("\n--- Your Active Bookings ---");
        myBookings.forEach(b -> System.out.println("ID: " + b.getBookingId() + " | Gym: " + b.getGymName() + " | Status: " + b.getStatus()));

        // 2. Ask for the ID to cancel
        System.out.print("\nEnter the Booking ID you wish to cancel: ");
        String bid = scanner.next();
        scanner.nextLine(); // Clear the buffer

        // 3. Call the service
        boolean isCancelled = customerService.cancelBooking(bid);

        if (isCancelled) {
            System.out.println("SUCCESS: Your booking has been cancelled and the seat is now available for others.");
        } else {
            System.out.println("FAILURE: Could not cancel booking. Please check the ID and try again.");
        }
    }
}