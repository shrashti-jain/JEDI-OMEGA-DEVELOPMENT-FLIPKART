package com.flipfit.client;

import com.flipfit.bean.GymCenter;
import com.flipfit.bean.User;
import com.flipfit.business.GymOwnerImpl;
import com.flipfit.business.AdminInterface;
import com.flipfit.business.AdminImpl;

import java.util.List;
import java.util.Scanner;

//TODO: Auto-generated Javadoc
/**
* The Class GymFlipFitAdminMenu.
* Handles the user interface and interactions for the Admin user.
* Provides a console-based dashboard to manage approvals for gym owners and centers,
* as well as viewing system reports.
*
* @author Mansa
* @ClassName GymFlipFitAdminMenu
*/
public class GymFlipFitAdminMenu {

    private static final AdminInterface adminService = new AdminImpl();

    /**
     * Show admin menu.
     * Displays the main dashboard options for the Admin and handles navigation.
     * Loops until the admin chooses to logout.
     *
     * @param scanner the shared Scanner instance for reading user input
     */
    public static void showAdminMenu(Scanner scanner) {
        boolean exit = false;
        while (!exit) {
            System.out.println("\n========================================");
            System.out.println("            ADMIN DASHBOARD             ");
            System.out.println("========================================");
            System.out.println("1. View Pending Owner Profile Requests");
            System.out.println("2. Approve Owner Profile (Identity Check)");
            System.out.println("3. Remove Gym Owner"); // New Option
            System.out.println("4. View Pending Gym Center Requests");
            System.out.println("5. Approve Gym Center (GST/Location Check)");
            System.out.println("6. Remove Gym Center");
            System.out.println("7. View All Approved Owners");
            System.out.println("8. View All Approved Gym Centers");
            System.out.println("9. Logout");
            System.out.print("Enter choice: ");

            int choice;
            try {
                choice = Integer.parseInt(scanner.next());
            } catch (Exception e) {
                System.out.println("Invalid choice. Please enter a number.");
                continue;
            }

            switch (choice) {
                case 1:
                    viewPendingOwners();
                    break;

                case 2:
                    System.out.print("Enter Owner Email to approve: ");
                    String ownerEmail = scanner.next();
                    if (adminService.approveOwner(ownerEmail)) {
                        System.out.println("Owner profile approved! They can now login.");
                    } else {
                        System.out.println("Error: Owner email not found or already approved.");
                    }
                    break;

                case 3: {
                    System.out.print("Enter Owner Email to remove: ");
                    String removeEmail = scanner.next();
                    if (adminService.removeOwner(removeEmail)) {
                        System.out.println("Owner " + removeEmail + " and their associated gyms have been removed.");
                    } else {
                        System.out.println("Error: Owner not found.");
                    }
                }

                case 4:
                    viewPendingGymRequests();
                    break;

                case 5:
                    System.out.print("Enter Gym Center ID to approve: ");
                    String approveId = scanner.next();
                    if (adminService.approveCenter(approveId)) {
                        System.out.println("Gym Center approved! Owner can now add slots.");
                    } else {
                        System.out.println("Error: Center ID not found or already approved.");
                    }
                    break;

                case 6:
                    System.out.print("Enter Center ID to remove: ");
                    String removeId = scanner.next();
                    if (adminService.removeCenter(removeId)) {
                        System.out.println("Center " + removeId + " permanently removed.");
                    } else {
                        System.out.println("Error: Center ID not found.");
                    }
                    break;

                case 7: // New Option: View All Approved Owners
                    List<User> approvedOwners = adminService.getOwnersByStatus(true);
                    System.out.println("--- Approved Owners ---");
                    approvedOwners.forEach(o -> System.out.println(o.getName() + " (" + o.getEmail() + ")"));
                    break;

                case 8: // New Option: View All Approved Centers
                    List<GymCenter> approvedGyms = adminService.getGymCentersByStatus(true);
                    System.out.println("--- Approved Gym Centers ---");
                    approvedGyms.forEach(g -> System.out.println(g.getCenterName() + " - " + g.getCity()));
                    break;

                case 9:
                    System.out.println("Logging out from Admin...");
                    exit = true;
                    break;

                default:
                    System.out.println("Invalid choice. Please select 1-6.");
            }
        }
    }

    /**
     * View pending owners.
     * Retrieves and displays the list of Gym Owners waiting for identity verification.
     * Helps the admin identify which accounts need approval.
     */
    private static void viewPendingOwners() {
        // Stage 1 Verification: Check Identity Numbers
        List<User> pendingOwners = adminService.getPendingOwners();
        if (pendingOwners.isEmpty()) {
            System.out.println("No pending owner profile requests.");
        } else {
            System.out.println("\n--- Pending Owner Profiles ---");
            System.out.printf("%-25s | %-15s | %-15s\n", "Email", "Name", "Identity No");
            System.out.println("------------------------------------------------------------");
            pendingOwners.forEach(o -> System.out.printf("%-25s | %-15s | %-15s\n",
                    o.getEmail(), o.getName(), o.getIdentityNo()));
        }
    }

    /**
     * View pending gym requests.
     * Retrieves and displays the list of Gym Centers waiting for GST and location verification.
     * Helps the admin identify which centers need approval.
     */
    private static void viewPendingGymRequests() {
        // Stage 4 Verification: Check GST and Pincode
        List<GymCenter> pending = adminService.getPendingCenters();
        if (pending.isEmpty()) {
            System.out.println("No pending gym center requests.");
        } else {
            System.out.println("\n--- Pending Gym Centers ---");
            System.out.printf("%-10s | %-15s | %-15s | %-15s\n", "ID", "Name", "GST No", "Pincode");
            System.out.println("----------------------------------------------------------------------");
            pending.forEach(c -> System.out.printf("%-10s | %-15s | %-15s | %-15s\n",
                    c.getCenterId(), c.getCenterName(), c.getGstNo(), c.getPincode()));
        }
    }
}