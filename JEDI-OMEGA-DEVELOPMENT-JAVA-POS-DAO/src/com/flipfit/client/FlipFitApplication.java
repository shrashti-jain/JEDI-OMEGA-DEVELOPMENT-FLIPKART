package com.flipfit.client;

import com.flipfit.bean.User;
import com.flipfit.business.UserImpl;
import com.flipfit.business.UserInterface;
import com.flipfit.utility.ValidationUtils;
import com.flipfit.exception.FlipFitException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

/**
 * Main Entry Point for the FlipFit Application.
 * Merges legacy authentication flow and banners with new Integer-ID routing.
 * * @author Shreya / Mansa (Integrated)
 * @ClassName "FlipFitApplication"
 */
public class FlipFitApplication {

    private static final UserInterface userService = new UserImpl();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        do {
            System.out.println("\n========================================");
            System.out.println("      Welcome to FlipFit Application    ");
            System.out.println("========================================");
            System.out.println("1. Login");
            System.out.println("2. Registration for Gym Owner");
            System.out.println("3. Registration for Customer");
            System.out.println("4. Change Password");
            System.out.println("5. Exit");
            System.out.print("\nEnter your choice (1-5): ");

            if (!scanner.hasNextInt()) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next();
                continue;
            }

            int choice = scanner.nextInt();
            scanner.nextLine();

            try {
                switch (choice) {
                    case 1 -> login(scanner);
                    case 2 -> registerOwner(scanner);
                    case 3 -> registerCustomer(scanner);
                    case 4 -> changePassword(scanner);
                    case 5 -> {
                        exit = true;
                        System.out.println("Exiting FlipFit.. Goodbye!");
                    }
                    default -> System.out.println("Invalid choice. Please select between 1 and 5.");
                }
            } catch (FlipFitException e) {
                System.out.println("❌ " + e.getMessage());
            } catch (Exception e) {
                System.out.println("❌ System Error: " + e.getMessage());
            }

        } while (!exit);

        scanner.close();
    }

    /**
     * Authenticates user and routes to menu using Integer IDs.
     *
     */
    private static void login(Scanner scanner) {
        System.out.print("Enter Email: ");
        String email = scanner.next();
        if (!ValidationUtils.isValidEmail(email)) {
            System.out.println(">> Error: Invalid email format.");
            return;
        }
        System.out.print("Enter Password: ");
        String pass = scanner.next();
        scanner.nextLine();

        User user = userService.authenticate(email, pass);

        if (user != null) {
            // MIGRATED FEATURE: Approval Guard for Owners
            // Assuming RoleID 2 = Owner based on your schema
            if (user.getRoleId() == 2 && !user.isApproved()) {
                System.out.println("\n[NOTIFICATION]: Your profile is pending Admin approval.");
                return;
            }

            printWelcomeBanner(user.getName());

            // Map Role IDs to Menus
            int roleId = user.getRoleId();
            switch (roleId) {
                case 1 -> GymFlipFitCustomerMenu.showCustomerMenu(scanner, user.getEmail());
                case 2 -> GymFlipFitOwnerMenu.showOwnerMenu(scanner, user.getUserId());
                case 3 -> GymFlipFitAdminMenu.showAdminMenu(scanner);
                default -> System.out.println("Error: Unknown Role Assigned.");
            }
        }
    }

    /**
     * MIGRATED FEATURE: Enhanced Owner Registration with Validation Loops
     */
    private static void registerOwner(Scanner scanner) {
        System.out.println("\n--- Gym Owner Registration ---");

        String name, email, contact;
        while (true) {
            System.out.print("Full Name: ");
            name = scanner.nextLine();
            if (ValidationUtils.isFullName(name)) break;
            System.out.println(">> Error: Please enter valid First and Last name.");
        }

        while (true) {
            System.out.print("Email: ");
            email = scanner.next();
            if (ValidationUtils.isValidEmail(email)) break;
            System.out.println(">> Error: Invalid email format.");
        }

        while (true) {
            System.out.print("Contact No (10 digits): ");
            contact = scanner.next();
            if (ValidationUtils.isValidContact(contact)) break;
            System.out.println(">> Error: Contact must be exactly 10 digits.");
        }

        System.out.print("Password: ");
        String pass = scanner.next();
        System.out.print("Identity No (Aadhar/PAN): ");
        String idNo = scanner.next();
        scanner.nextLine();

        userService.registerOwner(name, email, pass, contact, idNo);
        System.out.println("\nRegistration successful! Pending Admin approval.");
    }

    private static void registerCustomer(Scanner scanner) {
        System.out.println("\n--- Customer Registration ---");
        String name, email, contact;
        while (true) {
            System.out.print("Full Name: ");
            name = scanner.nextLine();
            if (ValidationUtils.isFullName(name)) break;
            System.out.println(">> Error: Please enter valid First and Last name.");
        }

        while (true) {
            System.out.print("Email: ");
            email = scanner.next();
            if (ValidationUtils.isValidEmail(email)) break;
            System.out.println(">> Error: Invalid email format.");
        }


        System.out.print("Password: "); String pass = scanner.next();
        while (true) {
            System.out.print("Contact No (10 digits): ");
            contact = scanner.next();
            if (ValidationUtils.isValidContact(contact)) break;
            System.out.println(">> Error: Contact must be exactly 10 digits.");
        }
        scanner.nextLine();
        System.out.print("Address: "); String addr = scanner.nextLine();
        System.out.print("City: "); String city = scanner.next();

        userService.registerCustomer(name, email, pass, contact, addr, city);
        System.out.println("Customer registered successfully!");
    }

    private static void changePassword(Scanner scanner) {
        System.out.println("\n--- Change Password ---");
        System.out.print("Enter Email: ");
        String email = scanner.next();
        System.out.print("New Password: ");
        String newPass = scanner.next();
        System.out.print("Confirm Password: ");
        String confirmPass = scanner.next();

        if (newPass.equals(confirmPass)) {
            userService.updatePassword(email, confirmPass);
            System.out.println("Password changed successfully.");
        } else {
            System.out.println("Error: Passwords do not match.");
        }
    }

    /**
     * MIGRATED FEATURE: Professional Welcome Banner
     */
    public static void printWelcomeBanner(String userName) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy HH:mm:ss");
        String loginTime = "Login Time: " + now.format(formatter);

        int consoleWidth = 80;
        String welcomeMsg = "Welcome, " + userName;
        int remainingWidth = consoleWidth - welcomeMsg.length();

        System.out.println("\n" + "*".repeat(consoleWidth));
        System.out.printf("%s%" + remainingWidth + "s%n", welcomeMsg, loginTime);
        System.out.print("*".repeat(consoleWidth) + "\n");
    }
}