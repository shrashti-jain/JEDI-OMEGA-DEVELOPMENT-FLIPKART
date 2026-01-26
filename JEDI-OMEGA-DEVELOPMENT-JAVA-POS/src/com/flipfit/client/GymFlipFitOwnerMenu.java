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

public class GymFlipFitOwnerMenu {

    // Instantiate the Business Service
    private static final GymOwnerInterface ownerService = new GymOwnerImpl();

    public static void showOwnerMenu(Scanner scanner, String ownerEmail) {
        boolean exit = false;
        while (!exit) {
            System.out.println("\n<----- Gym Owner Dashboard (" + ownerEmail + ") ----->");
            System.out.println("1. Add New Gym Center");
            System.out.println("2. Add Slot to Center");
            System.out.println("3. View My Centers (Status Check)");
            System.out.println("4. Logout");
            System.out.print("Enter choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Clear buffer

            switch (choice) {
                case 1:
                    System.out.print("Enter Gym Center Name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter City: ");
                    String city = scanner.nextLine();

                    // Call the Business Service
                    ownerService.addCenter(name, ownerEmail, city);
                    break;

                case 2:
                    System.out.print("Enter Center ID (e.g., C1): ");
                    String centerId = scanner.next();
                    System.out.print("Enter Slot ID (e.g., S1): ");
                    String slotId = scanner.next();
                    System.out.print("Enter Date (yyyy-MM-dd) : ");
                    String dateStr = scanner.next();
                    System.out.print("Enter Start Time (HH:mm): ");
                    LocalTime start = LocalTime.parse(scanner.next());
                    System.out.print("Enter End Time (HH:mm): ");
                    LocalTime end = LocalTime.parse(scanner.next());
                    System.out.print("Total Seats: ");
                    int seats = scanner.nextInt();

                    try {
                        Date date = new SimpleDateFormat("yyyy-MM-dd").parse(dateStr);
                        Slot newSlot = new Slot(slotId, centerId, start, end, date, seats);
                        ownerService.addSlot(centerId, newSlot);
                    } catch (Exception e) {
                        System.out.println("Invalid date format.");
                    }
                    break;

                case 3:
                    System.out.println("\n--- Your Registered Centers ---");
                    // Call the logic using the ownerEmail passed from login
                    List<GymCenter> myCenters = GymOwnerImpl.getCentersByOwner(ownerEmail);

                    if (myCenters.isEmpty()) {
                        System.out.println("You haven't registered any centers yet.");
                    } else {
                        System.out.println("----------------------------------------------------------------");
                        System.out.printf("%-10s | %-20s | %-15s | %-10s\n", "ID", "Name", "City", "Status");
                        System.out.println("----------------------------------------------------------------");

                        for (GymCenter c : myCenters) {
                            // Check the boolean status and convert to text
                            String status = c.isApproved() ? "APPROVED" : "PENDING";

                            System.out.printf("%-10s | %-20s | %-15s | %-10s\n",
                                    c.getCenterId(), c.getCenterName(), c.getCity(), status);
                        }
                    }
                    break;

                case 4:
                    System.out.println("Logging out...");
                    exit = true;
                    break;

                default:
                    System.out.println("Invalid option. Please enter a number between 1-4.");
            }
        }
    }
}