package com.flipfit.client;

import com.flipfit.business.UserImpl;
import java.util.Scanner;

public class FlipFitApplication {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            System.out.println("      Welcome to FlipFit Application    ");
            System.out.println("Type one of the following options:");
            System.out.println(" > LOGIN (To access your dashboard)");
            System.out.println(" > OWNER (Registration for Gym Owners)");
            System.out.println(" > CUSTOMER (Registration for Customers)");
            System.out.println(" > EXIT");
            System.out.print("\nEnter your choice: ");

            // Using nextLine() and toLowerCase() prevents InputMismatchException
            String choice = scanner.next().toLowerCase();

            switch (choice) {
                case "login":
                    login(scanner);
                    break;

                case "owner":
                    registerOwner(scanner);
                    break;

                case "customer":
                    registerCustomer(scanner);
                    break;

                case "exit":
                    exit = true;
                    System.out.println("Exiting FlipFit.. Goodbye!");
                    break;

                default:
                    System.out.println("Invalid choice. Please type 'Login', 'Owner', 'Customer', or 'Exit'.");
            }
        }
        scanner.close();
    }

    private static void login(Scanner scanner) {
        System.out.println("\n--- Login Page ---");
        System.out.print("Enter Email: ");
        String email = scanner.next();
        System.out.print("Enter Password: ");
        String password = scanner.next();

        // MOCK AUTHENTICATION (In real app, call userService.login(email, password))
        System.out.println("Authenticating " + email + "...");

        // This is where you decide which menu to show based on the user role
        System.out.print("Enter Role for Testing (Admin/Owner/Customer): ");
        String role = scanner.next();

        if (role.equalsIgnoreCase("Customer")) {
            System.out.println("Login Successful!");
            GymFlipFitCustomerMenu.showCustomerMenu(scanner, email); // Passes email as userId
        } else if (role.equalsIgnoreCase("Owner")) {
            System.out.println("Login Successful!");
            GymFlipFitOwnerMenu.showOwnerMenu(scanner); //
        } else if (role.equalsIgnoreCase("Admin")) {
            System.out.println("Login Successful!");
            GymFlipFitAdminMenu.showAdminMenu(scanner); //
        } else {
            System.out.println("Invalid Role. Access Denied.");
        }
    }

    private static void registerCustomer(Scanner scanner) {
        System.out.println("\n--- Customer Registration ---");
        System.out.print("Enter Full Name: ");
        String name = scanner.next();
        // Here you would call your business service: customerService.register(...)
        System.out.println("Registration Successful for " + name + "! You can now Login.");
    }

    private static void registerOwner(Scanner scanner) {
        System.out.println("\n--- Gym Owner Registration ---");
        System.out.print("Enter Gym Name: ");
        String gymName = scanner.next();
        // Here you would call your business service: ownerService.register(...)
        System.out.println("Registration Successful for " + gymName + "! Waiting for Admin Approval.");
    }
}