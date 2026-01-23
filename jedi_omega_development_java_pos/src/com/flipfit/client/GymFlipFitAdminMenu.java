/**
 * 
 */
package com.flipfit.client;

import com.flipfit.bean.Admin;
import java.util.Scanner;

/**
 * 
 */
public class GymFlipFitAdminMenu {

	/**
	 * @param args
	 */
	public static void showAdminMenu(Scanner scanner) {
        boolean exit = false;
        while (!exit) {
            System.out.println("\nAdmin Dashboard: ");
            System.out.println("1. View Pending Gym Requests");
            System.out.println("2. Approve Gym Center");
            System.out.println("3. Remove Gym Center");
            System.out.println("4. Configure/Validate Users");
            System.out.println("5. Logout");
            System.out.print("Enter choice: ");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("Fetching pending requests...");
                    // Call AdminService.viewPending()
                    break;
                case 2:
                    System.out.println("Approving Gym...");
                    // Call AdminService.addGym()
                    break;
                case 3:
                    System.out.println("Removing Gym...");
                    // Call AdminService.removeGym()
                    break;
                case 4:
                    System.out.println("Validating User...");
                    // Call AdminService.configureUser()
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
