package com.flipfit.client;

import com.flipfit.bean.Booking;
import com.flipfit.bean.GymCustomer;
import com.flipfit.bean.GymCenter;
import com.flipfit.bean.Slot;
import com.flipfit.business.GymCustomerInterface;
import com.flipfit.business.GymCustomerInterfaceImpl;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class GymFlipFitCustomerMenu {

    // Service Layer
    private static GymCustomerInterface customerService = new GymCustomerInterfaceImpl();

    /**
     * Displays the menu for the Customer.
     * @param scanner Shared scanner instance
     * @param userId The ID of the currently logged-in user
     */
    public static void showCustomerMenu(Scanner scanner, String userId) {
        boolean exit = false;
        
        while (!exit) {
            System.out.println("\nCustomer Dashboard: (" + userId + ")");
            System.out.println("1. View Gym Centers by City");
            System.out.println("2. View Slot Availability");
            System.out.println("3. Book a Slot");
            System.out.println("4. View My Bookings");
            System.out.println("5. Cancel Booking");
            System.out.println("6. Logout");
            System.out.print("Enter choice: ");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    viewGymsByCity(scanner);
                    break;
                case 2:
                    viewSlots(scanner);
                    break;
                case 3:
                    createBooking(scanner, userId);
                    break;
                case 4:
                    viewMyBookings(userId);
                    break;
                case 5:
                    cancelBooking(scanner);
                    break;
                case 6:
                    System.out.println("Logging out...");
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    // Helper Methods for switch case

    private static void viewGymsByCity(Scanner scanner) {
        System.out.print("Enter City Name: ");
        String city = scanner.next();
        List<GymCenter> centers = customerService.viewCenters(city);
        
        // display logic
        if (centers.isEmpty()) {
            System.out.println("No centers found in " + city);
        } else {
            System.out.println("Centers in " + city + ":");
            for (GymCenter c : centers) {
                System.out.println("ID: " + c.getCenterId() + " | Name: " + c.getCenterName()); // Assuming getters exist
            }
        }
    }

    private static void viewSlots(Scanner scanner) {
        System.out.print("Enter Gym Center ID: ");
        String centerId = scanner.next();
        System.out.print("Enter Date (e.g., 2026-01-20): ");
        String dateStr = scanner.next(); 
        Date date = new Date();

        List<Slot> slots = customerService.viewSlotAvailability(centerId, date);
        System.out.println("Available Slots:");
        for(Slot s : slots) {
            System.out.println("Slot ID: " + s.getSlotId() + " | Seats: " + s.getAvailableSeats()); // Assuming getAvailableSeats exists
        }
    }

    private static void createBooking(Scanner scanner, String userId) {
        System.out.print("Enter Gym Center ID: ");
        String centerId = scanner.next();
        System.out.print("Enter Slot ID: ");
        String slotId = scanner.next();
        System.out.print("Enter Date (yyyy-mm-dd): ");
        String dateStr = scanner.next();
        Date date = new Date(); 

        Booking booking = customerService.bookSlot(userId, slotId, centerId, date);
        
        if (booking != null) {
            System.out.println("Booking Successful! Booking ID: " + booking.getBookingId());
        } else {
            System.out.println("Booking Failed. Slot might be full.");
        }
    }

    private static void viewMyBookings(String userId) {
        List<Booking> bookings = customerService.viewMyBookings(userId);
        System.out.println("\nYour Bookings:");
        for(Booking b : bookings) {
            System.out.println("Booking ID: " + b.getBookingId() + " | Status: " + b.getStatus()); // Assuming getStatus exists
        }
    }

    private static void cancelBooking(Scanner scanner) {
        System.out.print("Enter Booking ID to Cancel: ");
        String bookingId = scanner.next();
        boolean success = customerService.cancelBooking(bookingId);
        
        if (success) {
            System.out.println("Booking Cancelled Successfully.");
        } else {
            System.out.println("Cancellation Failed. Invalid Booking ID.");
        }
    }
}