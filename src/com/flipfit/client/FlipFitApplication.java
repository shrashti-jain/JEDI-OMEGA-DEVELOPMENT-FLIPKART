package com.flipfit.client;

import com.flipfit.business.UserImpl;
import java.util.Scanner;

public class FlipFitApplication {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        do {
            System.out.println("\n========================================");
            System.out.println("      Welcome to FlipFit Application    ");
            System.out.println("========================================");
            System.out.println("Type one of the following options:");
            System.out.println(" > LOGIN    (To access your dashboard)");
            System.out.println(" > OWNER    (Registration for Gym Owners)");
            System.out.println(" > CUSTOMER (Registration for Customers)");
            System.out.println(" > CHANGE PASSWORD ");
            System.out.println(" > EXIT");
            System.out.print("\nEnter your choice: ");

            String choice = scanner.nextLine().toLowerCase();
            System.out.println(choice);

            // Using if-else
            if (choice.equals("login")) {
                login(scanner);
            }
            else if (choice.equals("owner")) {
                registerOwner(scanner);
            }
            else if (choice.equals("customer")) {
                registerCustomer(scanner);
            }
            else if (choice.equals("change password") || choice.equals("change pass")) {
                changePassword(scanner);
            }
            else if (choice.equals("exit")) {
                exit = true;
                System.out.println("Exiting FlipFit.. Goodbye!");
            }
            else {
                System.out.println("Invalid choice. Please type 'Login', 'Owner', 'Customer', or 'Exit'.");
            }

        } while (!exit); // Loop continues until exit is true

        scanner.close();
    }

    private static void login(Scanner scanner) {
        System.out.print("Enter Username: ");
        String username = scanner.next();
        System.out.print("Enter Password: ");
        String password = scanner.next();
        System.out.print("Enter Role: ");
        String role = scanner.next(); // takes role as input

        switch (role) {
            case "customer":
                System.out.println("Login Successful as Customer!");
                GymFlipFitCustomerMenu.showCustomerMenu(scanner, username); //
                break;

            case "owner":
                System.out.println("Login Successful as Gym Owner!");
                GymFlipFitOwnerMenu.showOwnerMenu(scanner); //
                break;

            case "admin":
                System.out.println("Login Successful as Admin!");
                GymFlipFitAdminMenu.showAdminMenu(scanner); //
                break;

            default:
                System.out.println("Invalid Role. Access Denied.");
                break;
        }
    }

    private static void registerCustomer(Scanner scanner) {
        System.out.println("\n--- Customer Registration ---");
        System.out.print("Full Name: "); String name = scanner.next();
        System.out.print("Email: "); String email = scanner.next();
        System.out.print("Password: "); String pass = scanner.next();
        System.out.print("Address: "); String addr = scanner.next();
        System.out.print("City: "); String city = scanner.next();

        // Logic to save these details to GymCustomer bean goes here
        System.out.println("Customer registered successfully!");
    }

    private static void registerOwner(Scanner scanner) {
        System.out.println("\n--- Gym Owner Registration ---");
        System.out.print("Full Name: "); String name = scanner.next();
        System.out.print("Email: "); String email = scanner.next();
        System.out.print("Password: "); String pass = scanner.next();
        System.out.print("Gym Center Name: "); String gymName = scanner.next();
        System.out.print("Gym Address: "); String addr = scanner.next();
        System.out.print("City: "); String city = scanner.next();

        // Logic to save these details to GymOwner bean goes here
        System.out.println("Owner registered successfully!");


        //Enter mail, previous pass---> forgot pass, new pass, confirm pass.
                                            //new pass, confirm pass
    }

    private static void changePassword(Scanner scanner) {
        System.out.println("\n--- Change Password ---");
        System.out.print("Enter User Name (Email): ");
        String userName = scanner.next();

        System.out.print("Enter New Password: ");
        String newPass = scanner.next();

        System.out.print("Confirm New Password: ");
        String confirmPass = scanner.next();

        if (newPass.equals(confirmPass)) {
            // Logic to update the user's bean/DB would go here
            System.out.println("Password changed successfully for " + userName);
        } else {
            System.out.println("Error: Passwords do not match. Please try again.");
        }
    }
}