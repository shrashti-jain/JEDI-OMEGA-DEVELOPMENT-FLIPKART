package com.flipfit.client;

import com.flipfit.bean.GymCenter;
import com.flipfit.bean.User;
import com.flipfit.business.AdminImpl;
import com.flipfit.business.AdminInterface;
import com.flipfit.business.UserImpl;
import com.flipfit.business.UserInterface;
import com.flipfit.exception.FlipFitException;

import java.util.List;
import java.util.Scanner;

/**
 * Enhanced Admin Dashboard Menu.
 * Handles approvals and directory management with integrated table formatting.
 * * @author Shreya / Mansa (Integrated)
 * @ClassName "GymFlipFitAdminMenu"
 */
public class GymFlipFitAdminMenu {

    private static final AdminInterface adminService = new AdminImpl();
    private static final UserInterface userService = new UserImpl();

    /**
     * Displays the admin dashboard menu.
     */
    public static void showAdminMenu(Scanner scanner) {

        boolean exit = false;

        while (!exit) {
            System.out.println("\n========================================");
            System.out.println("            ADMIN DASHBOARD             ");
            System.out.println("========================================");
            System.out.println("1. View Directories (Owners/Centers)");
            System.out.println("2. Approve Gym Owner (by User ID)");
            System.out.println("3. Approve Gym Center (by Center ID)");
            System.out.println("4. Remove User (Owner/Customer)");
            System.out.println("5. Remove Gym Center"); // 👈 NEW OPTION
            System.out.println("6. Logout");
            System.out.print("Enter choice: ");

            int choice;
            try {
                choice = Integer.parseInt(scanner.next());
            } catch (Exception e) {
                System.out.println("Invalid choice. Please enter a number.");
                continue;
            }

            try {
                switch (choice) {
                    case 1:
                        showDirectoryMenu(scanner);
                        break;

                    case 2:
                        System.out.print("Enter Owner Email to approve: ");
                        String ownerEmail = scanner.next();

                        try {
                            // Step 1: Resolve Email to User ID
                            int userId = userService.getUserIdByEmail(ownerEmail);

                            // Step 2: Call the approval logic
                            adminService.configureUser(String.valueOf(userId));

                            System.out.println("✅ Owner profile (" + ownerEmail + ") approved successfully.");
                        } catch (FlipFitException e) {
                            System.out.println("❌ Error: " + e.getMessage());
                        }
                        break;

                    case 3:
                        System.out.print("Enter Center ID to approve: ");
                        String centerId = scanner.next();
                        adminService.validateCenter(centerId);
                        System.out.println("✅ Gym Center approved successfully.");
                        break;

                    case 4:
                        List<User> allUsers = adminService.getAllUsers();

                        System.out.println("\n" + "=".repeat(85));
                        System.out.printf("%-10s | %-20s | %-25s | %-15s\n", "ID", "Name", "Email", "Role");
                        System.out.println("-".repeat(85));

                        for (User u : allUsers) {
                            // Map roleId to a String for better UI
                            String roleName = switch (u.getRoleId()) {
                                case 1 -> "Customer";
                                case 2 -> "Gym Owner";
                                case 3 -> "Admin";
                                default -> "Unknown";
                            };

                            System.out.printf("%-10d | %-20s | %-25s | %-15s\n",
                                    u.getUserId(), u.getName(), u.getEmail(), roleName);
                        }
                        System.out.println("=".repeat(85) + "\n");

                        System.out.print("Enter User ID to remove: ");
                        int removeId = scanner.nextInt(); // Note: Changed to int to match your User class

                        if (adminService.removeUser(removeId)) {
                            System.out.println("✅ User removed.");
                        } else {
                            System.out.println("❌ Error: Could not remove user. ID not found or Check if they have active bookings");
                        }
                        break;

                    case 5:
                        System.out.print("Enter Gym Center ID to remove: ");
                        String centerIdToRemove = scanner.next();
                        if (adminService.removeGymCenter(centerIdToRemove)) {
                            System.out.println("✅ Gym Center " + centerIdToRemove + " has been permanently removed.");
                        } else {
                            System.out.println("❌ Error: Gym Center ID not found or cannot be deleted.");
                        }
                        break;

                    case 6:
                        System.out.println("Logging out from Admin...");
                        scanner.nextLine();
                        exit = true;
                        break;

                    default:
                        System.out.println("Invalid choice. Please select 1-5.");
                }
            } catch (FlipFitException e) {
                System.out.println("❌ [ADMIN ERROR] " + e.getMessage());
            }
        }
    }

    /**
     * MIGRATED FEATURE: Directory selection for filtering approved vs pending.
     */
    private static void showDirectoryMenu(Scanner scanner) {
        System.out.println("\n--- Directory Selection ---");
        System.out.println("1. All Approved Owners");
        System.out.println("2. All Pending Owners");
        System.out.println("3. All Approved Centers");
        System.out.println("4. All Pending Centers");
        System.out.print("Selection: ");

        int subChoice;
        try {
            subChoice = scanner.nextInt();
        } catch (Exception e) {
            scanner.next(); // clear buffer
            return;
        }

        switch (subChoice) {
            case 1:
                displayOwners(adminService.getOwnersByStatus(true), "Approved Owners");
                break;
            case 2:
                displayOwners(adminService.getOwnersByStatus(false), "Pending Owners");
                break;
            case 3:
                displayCenters(adminService.getGymCentersByStatus(true), "Approved Centers");
                break;
            case 4:
                displayCenters(adminService.getGymCentersByStatus(false), "Pending Centers");
                break;
        }
    }

    /**
     * MIGRATED FEATURE: Formatted table for Owner display.
     */
    private static void displayOwners(List<User> owners, String title) {
        if (owners.isEmpty()) {
            System.out.println("No records found for " + title);
        } else {
            System.out.println("\n--- " + title + " ---");
            System.out.printf("%-10s | %-15s | %-25s | %-15s | %-15s\n",
                    "User ID", "Name", "Email", "Contact No", "Identity No");
            System.out.println("-".repeat(95));

            owners.forEach(o -> System.out.printf("%-10d | %-15s | %-25s | %-15s | %-15s\n",
                    o.getUserId(),
                    o.getName(),
                    o.getEmail(),
                    o.getPhone(),
                    o.getIdentityNo()));
        }
    }

    /**
     * MIGRATED FEATURE: Formatted table for Center display.
     */
    private static void displayCenters(List<GymCenter> centers, String title) {
        if (centers.isEmpty()) {
            System.out.println("No records found for " + title);
        } else {
            System.out.println("\n--- " + title + " ---");
            System.out.printf("%-10s | %-15s | %-15s | %-15s\n", "ID", "Name", "City", "GST No");
            System.out.println("-".repeat(70));
            centers.forEach(c -> System.out.printf("%-10d | %-15s | %-15s | %-15s\n",
                    c.getCenterId(), c.getCenterName(), c.getCity(), c.getGstNo()));
        }
    }
}