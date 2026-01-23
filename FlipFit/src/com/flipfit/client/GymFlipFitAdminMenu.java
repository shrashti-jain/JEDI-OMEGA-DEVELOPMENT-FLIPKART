/**
 *
 */
package com.flipfit.client;

import java.util.Scanner;

/**
 *
 */
public class GymFlipFitAdminMenu {

    public static void showAdminMenu(Scanner scanner) {
        boolean exit = false;
        while (!exit) {
            System.out.println("\n<----- Admin Dashboard ----->");
            System.out.println("1. View Pending Gym Requests");
            System.out.println("2. Approve Gym Center");
            System.out.println("3. Remove Gym Center");
            System.out.println("4. Logout");
            System.out.print("Enter choice: ");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("Fetching pending requests...");
                    // Call AdminService.viewPending()
                    break;
                case 2:
                    System.out.println("Approving Gym...");
                    // Call AdminService.approveGym()
                    break;
                case 3:
                    System.out.println("Removing Gym...");
                    // Call AdminService.removeGym()
                    break;
                case 4:
                    System.out.println("Logging out...");
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }

}
