package com.flipfit.client;

import com.flipfit.bean.User;
import com.flipfit.business.UserImpl;
import com.flipfit.business.UserInterface;

import java.util.Scanner;

public class FlipFitApplication {

    private static UserInterface userService = new UserImpl();

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
            //scanner.nextLine();
            System.out.println(choice);

            // Using if-else
            if (choice.equals("login")) {
                //exit = true;
                login(scanner);
            }
            else if (choice.equals("owner")) {
                //exit = true;
                registerOwner(scanner);
            }
            else if (choice.equals("customer")) {
                //exit = true;
                registerCustomer(scanner);
            }
            else if (choice.equals("change password") || choice.equals("change pass")) {
                //exit = true;
                changePassword(scanner);
            }
            else if (choice.equals("exit")) {
                exit = true;
                System.out.println("Exiting FlipFit.. Goodbye!");
            }
            else {
                System.out.println("Invalid choice. Please type 'Login', 'Owner', 'Customer', Change Password', or 'Exit'.");
            }

        } while (!exit); // Loop continues until exit is true

        scanner.close();
    }

    private static void login(Scanner scanner) {
        System.out.print("Enter email: ");
        String email = scanner.next();
        System.out.print("Enter Password: ");
        String pass = scanner.next();

        scanner.nextLine();

        User user = userService.authenticate(email, pass);
        if(user != null){
            System.out.print("Enter Role: ");
            String role = scanner.next(); // takes role as input



            switch (role) {
                case "customer":
                    System.out.println("<----------------------------->");
                    System.out.println("Login Successful as Customer!");
                    GymFlipFitCustomerMenu.showCustomerMenu(scanner, email); //
                    break;

                case "owner":
                    System.out.println("<----------------------------->");
                    System.out.println("Login Successful as Gym Owner!");
                    GymFlipFitOwnerMenu.showOwnerMenu(scanner, email); //
                    break;

                case "admin":
                    System.out.println("<----------------------------->");
                    System.out.println("Login Successful as Admin!");
                    GymFlipFitAdminMenu.showAdminMenu(scanner); //
                    break;

                default:
                    System.out.println("<----------------------------->");
                    System.out.println("Invalid Role. Access Denied.");
                    break;
            }
        }
    }

    private static void registerCustomer(Scanner scanner) {
        System.out.println("\n--- Customer Registration ---");
        System.out.print("Full Name: "); String name = scanner.nextLine();
        System.out.print("Email: "); String email = scanner.next();
        System.out.print("Password: "); String pass = scanner.next();
        scanner.nextLine();
        System.out.print("Address: "); String addr = scanner.nextLine();
        System.out.print("City: "); String city = scanner.next();

        // Logic to save these details to GymCustomer bean goes here
        userService.registerCustomer(name, email, pass, addr, city);
        scanner.nextLine();
        //System.out.println("Customer registered successfully!");
    }

    private static void registerOwner(Scanner scanner) {
        System.out.println("\n--- Gym Owner Registration ---");
        System.out.print("Full Name: "); String name = scanner.nextLine();
        //scanner.nextLine();
        System.out.print("Email: "); String email = scanner.next();
        System.out.print("Password: "); String pass = scanner.next();

        // No longer asking for Gym Name/Address here!
        userService.registerOwner(name, email, pass, "", "", "");
        System.out.println("\nOwner account created successfully! Please Login to add your Gym.");
        scanner.nextLine();
    }

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
            userService.updatePassword(email,confirmPass);
            // Logic to update the user's bean would go here
            System.out.println("Password changed successfully for " + email);
        } else {
            System.out.println("Error: Passwords do not match. Please try again.");
        }
    }
}