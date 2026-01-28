package com.flipfit.client;

import com.flipfit.bean.User;
import com.flipfit.business.UserImpl;
import com.flipfit.business.UserInterface;
import com.flipfit.utils.ValidationUtils;

import java.util.Scanner;

//TODO: Auto-generated Javadoc
/**
* The Class FlipFitApplication.
* The main entry point for the FlipFit application.
* Manages the initial landing menu, user authentication, and routing to specific dashboards
* based on the user's role (Customer, Gym Owner, or Admin).
*
* @author Mansa
* @ClassName FlipFitApplication
*/
public class FlipFitApplication {

    private static UserInterface userService = new UserImpl();

    /**
     * The main method.
     * Executes the primary application loop, offering options to login, register, or exit.
     * Handles global exception handling and scanner resource management.
     *
     * @param args the command line arguments
     */
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

            // Input validation for Integer
            if (!scanner.hasNextInt()) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next(); // Clear invalid input
                continue;
            }

            int choice = scanner.nextInt();
            scanner.nextLine(); // Clear the buffer

            switch (choice) {
                case 1:
                    login(scanner);
                    break;
                case 2:
                    registerOwner(scanner);
                    break;
                case 3:
                    registerCustomer(scanner);
                    break;
                case 4:
                    changePassword(scanner);
                    break;
                case 5:
                    exit = true;
                    System.out.println("Exiting FlipFit.. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please select between 1 and 5.");
            }

        } while (!exit);

        scanner.close();
    }

    /**
     * Login.
     * Handles the user authentication process.
     * Collects credentials, verifies them via the service layer, and routes the user
     * to the appropriate menu based on their role.
     * Includes a check for Gym Owner approval status.
     *
     * @param scanner the shared Scanner instance for user input
     */
    private static void login(Scanner scanner) {
        System.out.print("Enter Email: ");
        String email = scanner.next();
        System.out.print("Enter Password: ");
        String pass = scanner.next();
        scanner.nextLine();

        User user = userService.authenticate(email, pass);

        if (user != null) {
            // Check for Owner Approval Status
            if (user.getRole().equalsIgnoreCase("owner") && !user.isApproved()) {
                System.out.println("\n[NOTIFICATION]: Your profile is pending Admin approval. You cannot login yet.");
                return;
            }

            String role = user.getRole().toLowerCase();
            System.out.println("<----------------------------->");
            System.out.println("Login Successful as " + role + "!");

            switch (role) {
                case "customer":
                    GymFlipFitCustomerMenu.showCustomerMenu(scanner, email);
                    break;
                case "owner":
                    GymFlipFitOwnerMenu.showOwnerMenu(scanner, email);
                    break;
                case "admin":
                    GymFlipFitAdminMenu.showAdminMenu(scanner);
                    break;
                default:
                    System.out.println("Error: Unknown Role Assigned.");
            }
        } else {
            System.out.println("Invalid Email or Password.");
        }
    }

    /**
     * Register customer.
     * Handles the UI interaction for registering a new customer.
     * Collects all necessary fields and calls the service layer to create the account.
     *
     * @param scanner the shared Scanner instance
     */
    private static void registerCustomer(Scanner scanner) {
        System.out.println("\n--- Customer Registration ---");
        System.out.print("Full Name: "); String name = scanner.nextLine();
        System.out.print("Email: "); String email = scanner.next();
        System.out.print("Password: "); String pass = scanner.next();
        System.out.print("Contact No: "); String contact = scanner.next();
        scanner.nextLine();
        System.out.print("Address: "); String addr = scanner.nextLine();
        System.out.print("City: "); String city = scanner.next();

        userService.registerCustomer(name, email, pass, contact, addr, city);
        System.out.println("Customer registered successfully!");
    }

    /**
     * Register owner.
     * Handles the UI interaction for registering a new gym owner.
     * Includes strict validation loops for Name, Email, and Contact to ensure data integrity
     * before sending the request to the service layer.
     *
     * @param scanner the shared Scanner instance
     */
    private static void registerOwner(Scanner scanner) {
        System.out.println("\n--- Gym Owner Registration ---");

        // 1. Validate Name Right Away
        String name;
        while (true) {
            System.out.print("Full Name: ");
            name = scanner.nextLine();
            if (ValidationUtils.isFullName(name)) break;
            System.out.println(">> Error: Please enter your First and Last name.");
        }

        // 2. Validate Email Right Away
        String email;
        while (true) {
            System.out.print("Email: ");
            email = scanner.next();
            if (ValidationUtils.isValidEmail(email)) break;
            System.out.println(">> Error: Invalid email format (e.g., name@mail.com).");
        }

        // 3. Validate Contact Right Away
        String contact;
        while (true) {
            System.out.print("Contact No (10 digits): ");
            contact = scanner.next();
            if (ValidationUtils.isValidContact(contact)) break;
            System.out.println(">> Error: Contact must be exactly 10 numeric digits.");
        }

        // Only after all individual loops are cleared, do we call the service
        System.out.print("Password: ");
        String pass = scanner.next();
        System.out.print("Identity No (Aadhar/PAN): ");
        String idNo = scanner.next();

        // Sending request with isApproved = false
        userService.registerOwner(name, email, pass, contact, idNo, "OWNER");
        System.out.println("\nRegistration request sent! Please wait for Admin to approve your profile before logging in.");
    }

    /**
     * Change password.
     * Handles the UI flow for a user to update their password.
     * Verifies that the new password matches the confirmation password before updating.
     *
     * @param scanner the shared Scanner instance
     */
    private static void changePassword(Scanner scanner) {
        System.out.println("\n--- Change Password ---");
        System.out.print("Enter Email: ");
        String email = scanner.next();
        System.out.print("Enter New Password: ");
        String newPass = scanner.next();
        System.out.print("Confirm New Password: ");
        String confirmPass = scanner.next();
        scanner.nextLine();

        if (newPass.equals(confirmPass)) {
            userService.updatePassword(email, confirmPass);
            System.out.println("Password changed successfully.");
        } else {
            System.out.println("Error: Passwords do not match.");
        }
    }
}