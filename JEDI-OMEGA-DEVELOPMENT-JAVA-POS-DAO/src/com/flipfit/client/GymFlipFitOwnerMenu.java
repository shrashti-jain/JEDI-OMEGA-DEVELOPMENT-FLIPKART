package com.flipfit.client;

import com.flipfit.bean.GymCenter;
import com.flipfit.bean.Slot;
import com.flipfit.business.GymOwnerInterface;
import com.flipfit.business.GymOwnerImpl;

import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

//TODO: Auto-generated Javadoc
/**
* The Class GymFlipFitOwnerMenu.
* Handles the user interface and interactions for the Gym Owner.
* Provides a console-based dashboard for owners to add centers, manage slots,
* and check approval statuses.
*
* @author Mansa
* @ClassName GymFlipFitOwnerMenu
*/
public class GymFlipFitOwnerMenu {

    private static final GymOwnerInterface ownerService = new GymOwnerImpl();

    /**
     * Show owner menu.
     * Displays the main dashboard options for the Gym Owner and handles navigation.
     * Loops until the owner chooses to logout.
     *
     * @param scanner the shared Scanner instance for reading user input
     * @param ownerEmail the email of the logged-in owner
     */
    public static void showOwnerMenu(Scanner scanner, String ownerEmail) {
        boolean exit = false;
        while (!exit) {
            System.out.println("\n<----- Gym Owner Dashboard (" + ownerEmail + ") ----->");
            System.out.println("1. Add New Gym Center (Requires Admin Approval)");
            System.out.println("2. Add Slot to Approved Center");
            System.out.println("3. View My Centers (Status Check)");
            System.out.println("4. Logout");
            System.out.print("Enter choice: ");

            // Input validation
            if (!scanner.hasNextInt()) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next();
                continue;
            }

            int choice = scanner.nextInt();
            scanner.nextLine(); // Clear buffer

            switch (choice) {
                case 1:
                    System.out.println("\n--- Submit Gym Center Details ---");
                    System.out.print("Enter Gym Name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter Full Location/Address: ");
                    String location = scanner.nextLine();
                    System.out.print("Enter City: ");
                    String city = scanner.nextLine();
                    System.out.print("Enter Pincode: ");
                    String pincode = scanner.nextLine();
                    System.out.print("Enter GST Number: ");
                    String gst = scanner.nextLine();

                    // Updated call to service with new verification fields
                    ownerService.addCenter(name, ownerEmail, location, city, pincode, gst);
                    System.out.println("Waiting for Admin verification.");
                    break;

                case 2:
                    // Logic check: Only allow slot addition for approved centers
                    System.out.print("Enter Center ID to add slots: ");
                    String centerId = scanner.next();

                    // We verify if this center belongs to the owner AND is approved
                    if (!ownerService.isCenterApproved(centerId)) {
                        System.out.println("ALERT: You can only add slots to centers verified and APPROVED by Admin.");
                        break;
                    }

                    System.out.print("Enter Slot ID (e.g., S1): ");
                    String slotId = scanner.next();
                    System.out.print("Enter Date (yyyy-MM-dd): ");
                    String dateStr = scanner.next();
                    System.out.print("Enter Start Time (HH:mm): ");
                    String startStr = scanner.next();
                    System.out.print("Enter End Time (HH:mm): ");
                    String endStr = scanner.next();
                    System.out.print("Total Capacity (Seats): ");
                    int seats = scanner.nextInt();

                    try {
                        LocalTime start = LocalTime.parse(startStr);
                        LocalTime end = LocalTime.parse(endStr);
                        Date date = new SimpleDateFormat("yyyy-MM-dd").parse(dateStr);

                        Slot newSlot = new Slot(slotId, centerId, start, end, date, seats);
                        ownerService.addSlot(centerId, newSlot);
                    } catch (Exception e) {
                        System.out.println("Error: Invalid format provided for time or date.");
                    }
                    break;

                case 3:
                    System.out.println("\n--- Your Registered Centers & Verification Status ---");
                    List<GymCenter> myCenters = ownerService.getCentersByOwner(ownerEmail);

                    if (myCenters.isEmpty()) {
                        System.out.println("No gym centers registered under your account.");
                    } else {
                        System.out.println("----------------------------------------------------------------------------------");
                        System.out.printf("%-10s | %-15s | %-15s | %-15s | %-10s\n", "ID", "Name", "City", "GST No", "Status");
                        System.out.println("----------------------------------------------------------------------------------");

                        for (GymCenter c : myCenters) {
                            String status = c.isApproved() ? "APPROVED" : "PENDING";
                            System.out.printf("%-10s | %-15s | %-15s | %-15s | %-10s\n",
                                    c.getCenterId(), c.getCenterName(), c.getCity(), c.getGstNo(), status);
                        }
                    }
                    break;

                case 4:
                    System.out.println("Logging out...");
                    exit = true;
                    break;

                default:
                    System.out.println("Invalid choice. Select 1-4.");
            }
        }
    }
}