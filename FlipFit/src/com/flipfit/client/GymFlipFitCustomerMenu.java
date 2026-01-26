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
                case 5 -> cancelBooking(scanner);
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

    private static void cancelBooking(Scanner scanner) {
        System.out.print("Enter Booking ID: ");
        String bid = scanner.next();
        if (customerService.cancelBooking(bid)) System.out.println("Cancelled successfully.");
    }
}