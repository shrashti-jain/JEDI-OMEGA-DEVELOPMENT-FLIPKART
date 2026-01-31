package com.flipfit.client;

import com.flipfit.bean.Booking;
import com.flipfit.bean.GymCenter;
import com.flipfit.bean.Slot;
import com.flipfit.bean.Waitlist;
import com.flipfit.business.GymCustomerInterface;
import com.flipfit.business.GymCustomerImpl;
import com.flipfit.business.UserInterface;
import com.flipfit.business.UserImpl;
import com.flipfit.exception.FlipFitException;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.text.ParseException;

/**
 * Enhanced Customer Dashboard Menu.
 * Fully synchronized with session-based userEmail and Integer-ID logic.
 */
public class GymFlipFitCustomerMenu {

    private static final GymCustomerInterface customerService = new GymCustomerImpl();
    private static final UserInterface userService = new UserImpl();
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * Displays the customer dashboard menu.
     * @param scanner Scanner for user input
     * @param userEmail The actual email of the logged-in user
     */
    public static void showCustomerMenu(Scanner scanner, String userEmail) {
        // Resolve email to userId once at the beginning of the session
        int userId = userService.getUserIdByEmail(userEmail);
        boolean exit = false;

        while (!exit) {
            System.out.println("\n========================================");
            System.out.println("      CUSTOMER DASHBOARD (" + userEmail + ") ");
            System.out.println("========================================");
            System.out.println("1. View Gym Centers by City");
            System.out.println("2. View Slot Availability");
            System.out.println("3. Book a Slot");
            System.out.println("4. View My Bookings");
            System.out.println("5. Cancel Booking");
            System.out.println("6. Logout");
            System.out.print("\nEnter choice: ");

            if (!scanner.hasNextInt()) {
                System.out.println("Invalid input. Please enter a number (1-6).");
                scanner.next();
                continue;
            }

            int choice = scanner.nextInt();
            scanner.nextLine(); // Clear buffer

            try {
                switch (choice) {
                    case 1 -> viewGymsByCity(scanner);
                    case 2 -> viewSlots(scanner);
                    case 3 -> createBooking(scanner, userId, userEmail); // Pass email for conflict check
                    case 4 -> viewBookings(userEmail); // Pass original email directly
                    case 5 -> cancelBooking(scanner, userId, userEmail);
                    case 6 -> {
                        System.out.println("Logging out...");
                        exit = true;
                    }
                    default -> System.out.println("Invalid option. Please choose 1-6.");
                }
            } catch (FlipFitException e) {
                System.out.println("❌ " + e.getMessage());
            } catch (Exception e) {
                System.out.println("❌ System Error: " + e.getMessage());
            }
        }
    }

    private static void viewGymsByCity(Scanner scanner) {
        System.out.print("Enter City Name: ");
        String city = scanner.next();

        List<GymCenter> centers = customerService.viewCenters(city);
        if (centers.isEmpty()) {
            System.out.println("No approved gyms found in " + city);
        } else {
            System.out.println("\n--- Approved Gyms in " + city + " ---");
            System.out.printf("%-10s | %-20s | %-15s\n", "ID", "Name", "Location");
            System.out.println("---------------------------------------------");
            centers.forEach(c -> System.out.printf("%-10d | %-20s | %-15s\n",
                    c.getCenterId(), c.getCenterName(), c.getLocation()));
        }
    }

    private static void viewSlots(Scanner scanner) {
        System.out.print("Enter Center ID: ");
        int centerId = scanner.nextInt();
        System.out.print("Enter Date (yyyy-MM-dd): ");
        String dateStr = scanner.next();

        try {
            Date date = sdf.parse(dateStr);
            List<Slot> slots = customerService.viewSlotAvailability(centerId, date);

            System.out.println("\n--- Available Slots for " + dateStr + " ---");
            System.out.printf("%-10s | %-20s | %-15s\n", "Slot ID", "Time", "Seats Left");
            System.out.println("---------------------------------------------");
            slots.forEach(s -> System.out.printf("%-10d | %-20s | %-15d\n",
                    s.getSlotId(), s.getStartTime() + " - " + s.getEndTime(), s.getAvailableSeats()));
        } catch (ParseException e) {
            System.out.println("Invalid date format. Please use yyyy-MM-dd");
        }
    }

    private static void createBooking(Scanner scanner, int userId, String userEmail) {
        System.out.print("Enter Center ID: ");
        int centerId = scanner.nextInt();
        System.out.print("Enter Slot ID: ");
        int slotId = scanner.nextInt();
        System.out.print("Enter Date (yyyy-MM-dd): ");
        String dateStr = scanner.next();

        try {
            Date date = sdf.parse(dateStr);

            // 1. FRESH DATA CHECK
            Slot selectedSlot = customerService.getSlotById(slotId);
            if (selectedSlot == null) {
                System.out.println("❌ Error: Slot ID " + slotId + " does not exist.");
                return;
            }

            // 2. CONFLICT CHECK
            String slotTimeStr = selectedSlot.getStartTime().toString();
            Booking conflict = customerService.checkConflict(userId, date, slotTimeStr);

            if (conflict != null) {
                System.out.println("\n[CONFLICT] You are already booked at " + slotTimeStr + " in " + conflict.getGymName());
                System.out.print("Replace existing booking? (yes/no): ");
                if (scanner.next().equalsIgnoreCase("yes")) {
                    customerService.cancelBooking(conflict.getBookingId());
                } else {
                    return;
                }
            }

            // 3. WAITLIST LOGIC (Handled here in the Menu)
            if (selectedSlot.getAvailableSeats() <= 0) {
                System.out.println("\n⚠️ This slot is currently FULL.");
                System.out.print("Join Waitlist? (yes/no): ");
                if (scanner.next().equalsIgnoreCase("yes")) {
                    customerService.addWaitlist(userId, slotId, date);
                    System.out.println("✅ Added to Waitlist!");
                }
                return;
            }

            // 4. CALL SERVICE (Now safe because we checked seats)
            Booking booking = customerService.bookSlot(userId, slotId, centerId, date);
            if (booking != null) {
                System.out.println("\nSUCCESS! Booking Confirmed. ID: " + booking.getBookingId());
            } else {
                // This only triggers if a race condition happened (someone took the last seat 1ms before you)
                System.out.println("❌ Failed to secure seat. It may have just filled up.");
            }

        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }

    private static void viewBookings(String userEmail) {
        // 1. Fetch Confirmed Bookings
        List<Booking> myBookings = customerService.viewBookings(userEmail);

        // 2. Fetch Waitlist Entries
        // (Ensure your Service/DAO returns an empty list, not null, if none exist)
        List<Waitlist> myWaitlist = customerService.viewWaitlist(userEmail);

        // 3. Check if EVERYTHING is empty
        if (myBookings.isEmpty() && myWaitlist.isEmpty()) {
            System.out.println("❌ No current bookings or waitlist entries found for " + userEmail);
            return;
        }

        // --- DISPLAY CONFIRMED SECTION ---
        if (!myBookings.isEmpty()) {
            System.out.println("\n----------------------- YOUR CONFIRMED BOOKINGS -----------------------");
            System.out.printf("%-15s | %-15s | %-12s | %-12s | %-10s\n", "Booking ID", "Gym", "Date", "Time", "Status");
            System.out.println("-".repeat(71));
            myBookings.forEach(b -> System.out.printf("%-15s | %-15s | %-12s | %-12s | %-10s\n",
                    b.getBookingId(), b.getGymName(), new SimpleDateFormat("dd-MM-yy").format(b.getSlotDate()), b.getSlotTime(), b.getStatus()));
        }

        // --- DISPLAY WAITLIST SECTION ---
        if (!myWaitlist.isEmpty()) {
            System.out.println("\n----------------------- YOUR WAITLIST (PENDING) -----------------------");
            System.out.printf("%-15s | %-15s | %-12s | %-10s\n", "Waitlist ID", "Slot ID", "Date", "Status");
            System.out.println("-".repeat(71));
            myWaitlist.forEach(w -> System.out.printf("%-15s | %-15s | %-12s | %-10s\n",
                    "WL-" + w.getWaitlistId(),
                    w.getCenterName(),
                    new SimpleDateFormat("dd-MM-yy").format(w.getBookingDate()),
                    w.getSlotTime()));
        }
        System.out.println("-----------------------------------------------------------------------");
    }

    private static void cancelBooking(Scanner scanner, int userId, String userEmail) {
        viewBookings(userEmail);
        System.out.print("\nEnter Booking ID to cancel: ");
        String bid = scanner.next();

        if (customerService.cancelBooking(bid)) {
            System.out.println("Booking cancelled. Seat restored.");
        } else {
            System.out.println("Cancellation failed. Check ID.");
        }
    }
}