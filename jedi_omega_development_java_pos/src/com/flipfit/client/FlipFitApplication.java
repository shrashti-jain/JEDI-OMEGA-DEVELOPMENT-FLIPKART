///**
// *
// */
//package com.flipfit.client;
//
//import com.flipfit.bean.User;
//import com.flipfit.bean.GymCustomer;
//import com.flipfit.bean.GymOwner;
//import java.util.Scanner;
//
///**
// *
// */
//public class FlipFitApplication {
//
//	/**
//	 * @param args
//	 */
//	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//
//		Scanner scanner = new Scanner(System.in);
//		boolean exit = false;
//
//		while(!exit) {
//			System.out.println("Welcome to FlipFit Application");
//			System.out.println("1. Login");
//			System.out.println("2. Registration of Gym Owner");
//			System.out.println("3. Registration of Gym Customer");
//			System.out.println("4. Exit");
//
//			System.out.println("Enter your choice: ");
//
//			int choice = scanner.nextInt();
//
//			switch (choice) {
//				case 1: System.out.println("Login logic.."); //customer/owner/admin menu shown
//						break;
//
//				case 2: System.out.println("Registering Gym Owner..");
//						break;
//
//				case 3: System.out.println("Registering Gym Customer..");
//						break;
//
//				case 4: exit=true;
//						System.out.println("Exiting FlipFit..");
//						break;
//
//			    default: System.out.println("Invalid choice. Please try again..");
//			}
//		}
//
//	}
//
//	private static void login(Scanner scanner) {
//        System.out.print("Enter Username: ");
//        String username = scanner.next();
//        System.out.print("Enter Password: ");
//        String password = scanner.next();
//
//        // MOCK AUTHENTICATION LOGIC
//        System.out.println("Authenticating...");
//
//        // Assume we determined the role based on DB response:
//        // for demo taking customer
//        String role = "Customer"; // Change this to "Owner" or "Admin" to test other flows
//
//        if (role.equalsIgnoreCase("Customer")) {
//            System.out.println("Login Successful as Customer!");
//            GymFlipFitCustomerMenu.showCustomerMenu(scanner, "user_123");
//        } else if (role.equalsIgnoreCase("Owner")) {
//            System.out.println("Login Successful as Gym Owner!");
//            GymFlipFitOwnerMenu.showOwnerMenu(scanner);
//        } else if (role.equalsIgnoreCase("Admin")) {
//            System.out.println("Login Successful as Admin!");
//            GymFlipFitAdminMenu.showAdminMenu(scanner);
//        } else {
//            System.out.println("Invalid Credentials.");
//        }
//    }
//
//    private static void registerCustomer(Scanner scanner) {
//        System.out.println("Redirecting to Customer Registration...");
//        // Call Customer Business Service to register
//    }
//
//    private static void registerOwner(Scanner scanner) {
//        System.out.println("Redirecting to Owner Registration...");
//        // Call Owner Business Service to register
//    }
//
//}
package com.flipfit.client;

import java.util.Scanner;

public class FlipFitApplication {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n=================================");
            System.out.println("Welcome to the FlipFit Application for GYM");
            System.out.println("=================================");
            System.out.println("1. Login");
            System.out.println("2. Registration of the GymCustomer");
            System.out.println("3. Registration of the GymOwner");
            System.out.println("4. Change Password");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");

            choice = scanner.nextInt();

            if (choice == 1) {
                login(scanner);
            }
            else if (choice == 2) {
                registerGymCustomer(scanner);
            }
            else if (choice == 3) {
                registerGymOwner(scanner);
            }
            else if (choice == 4) {
                changePassword(scanner);
            }
            else if (choice == 5) {
                System.out.println("Thank you for using FlipFit!");
            }
            else {
                System.out.println("Invalid choice. Please try again.");
            }

        } while (choice != 5);

        scanner.close();
    }

    // ---------------- LOGIN ----------------
    private static void login(Scanner scanner) {

        System.out.print("Enter Username: ");
        String username = scanner.next();

        System.out.print("Enter Password: ");
        String password = scanner.next();

        System.out.println("Select Role:");
        System.out.println("1. GymOwner");
        System.out.println("2. GymCustomer");
        System.out.println("3. GymAdmin");
        System.out.print("Enter role choice: ");

        int roleChoice = scanner.nextInt();

        switch (roleChoice) {
            case 1:
                System.out.println("Login Successful as Gym Owner");
                GymFlipFitOwnerMenu.showOwnerMenu(scanner);
                break;

            case 2:
                System.out.println("Login Successful as Gym Customer");
                GymFlipFitCustomerMenu.showCustomerMenu(scanner, username);
                break;

            case 3:
                System.out.println("Login Successful as Gym Admin");
                GymFlipFitAdminMenu.showAdminMenu(scanner);
                break;

            default:
                System.out.println("Invalid role selected.");
        }
    }

    // ---------------- REGISTRATION ----------------
    private static void registerGymCustomer(Scanner scanner) {
        System.out.println("Gym Customer Registration");
        System.out.print("Enter Name: ");
        scanner.next();
        System.out.print("Enter Email: ");
        scanner.next();
        System.out.print("Enter Phone: ");
        scanner.next();
        System.out.println("Customer Registered Successfully!");
    }

    private static void registerGymOwner(Scanner scanner) {
        System.out.println("Gym Owner Registration");
        System.out.print("Enter Name: ");
        scanner.next();
        System.out.print("Enter Email: ");
        scanner.next();
        System.out.print("Enter Phone: ");
        scanner.next();
        System.out.println("Gym Owner Registered Successfully!");
    }

    // ---------------- CHANGE PASSWORD ----------------
    private static void changePassword(Scanner scanner) {
        System.out.print("Enter Username: ");
        scanner.next();
        System.out.print("Enter Old Password: ");
        scanner.next();
        System.out.print("Enter New Password: ");
        scanner.next();
        System.out.println("Password changed successfully!");
    }
}
