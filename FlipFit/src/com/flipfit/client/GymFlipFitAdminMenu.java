package com.flipfit.client;

import com.flipfit.bean.GymCenter;
import com.flipfit.business.GymOwnerImpl;
import java.util.List;
import java.util.Scanner;

public class GymFlipFitAdminMenu {

    public static void showAdminMenu(Scanner scanner) {
        boolean exit = false;
        while (!exit) {
            System.out.println("\n<----- Admin Dashboard ----->");
            System.out.println("1. View Pending Gym Center Requests");
            System.out.println("2. Approve Gym Center");
            System.out.println("3. Remove Gym Center"); // New Option
            System.out.println("4. Logout");
            System.out.print("Enter choice: ");

            String choice1 = scanner.next();
            try{
                Integer.parseInt(choice1);
            }catch (Exception e){
                System.out.println("Invalid choice ");
            }
            int choice = Integer.parseInt(choice1);
            switch (choice) {
                case 1:
                    viewPendingRequests();
                    break;

                case 2:
                    System.out.print("Enter Center ID to approve: ");
                    String approveId = scanner.next();
                    if (GymOwnerImpl.approveCenter(approveId)) {
                        System.out.println("Center approved successfully!");
                    } else {
                        System.out.println("Center ID not found.");
                    }
                    break;

                case 3:
                    System.out.print("Enter Center ID to remove: ");
                    String removeId = scanner.next();
                    if (GymOwnerImpl.removeCenter(removeId)) {
                        System.out.println("Center " + removeId + " has been permanently removed.");
                    } else {
                        System.out.println("Error: Center ID not found.");
                    }
                    break;

                case 4:
                    System.out.println("Logging out from Admin...");
                    exit = true;
                    break;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private static void viewPendingRequests() {
        List<GymCenter> pending = GymOwnerImpl.getPendingCenters();
        if (pending.isEmpty()) {
            System.out.println("No pending requests.");
        } else {
            System.out.printf("%-10s | %-20s | %-20s\n", "ID", "Name", "Owner");
            pending.forEach(c -> System.out.printf("%-10s | %-20s | %-20s\n",
                    c.getCenterId(), c.getCenterName(), c.getOwnerEmail()));
        }
    }
}