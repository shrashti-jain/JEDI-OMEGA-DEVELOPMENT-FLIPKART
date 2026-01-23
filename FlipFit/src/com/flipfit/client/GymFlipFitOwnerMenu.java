/**
 *
 */
package com.flipfit.client;

import java.util.Scanner;

/**
 *
 */
public class GymFlipFitOwnerMenu {

    public static void showOwnerMenu(Scanner scanner) {
        boolean exit = false;
        while (!exit) {
            System.out.println("\n<----- Gym Owner Dashboard ----->");
            System.out.println("1. Add New Gym Center");
            System.out.println("2. Add Slot to Center");
            System.out.println("3. View My Centers");
            System.out.println("4. Remove Center");
            System.out.println("5. Logout");
            System.out.print("Enter choice: ");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("Adding Gym Center...");
                    // Call GymOwnerService.addCenter()
                    break;
                case 2:
                    System.out.println("Adding Slot...");
                    // Call GymOwnerService.addSlot()
                    break;
                case 3:
                    System.out.println("Fetching your centers...");
                    // Call GymOwnerService.viewMyCenters()
                    break;
                case 4:
                    System.out.println("Removing Center...");
                    // Call GymOwnerService.removeCenter()
                    break;
                case 5:
                    System.out.println("Logging out...");
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }

}
