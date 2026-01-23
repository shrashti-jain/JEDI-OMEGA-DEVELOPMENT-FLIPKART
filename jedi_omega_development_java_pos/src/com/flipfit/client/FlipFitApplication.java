/**
 * 
 */
package com.flipfit.client;

import com.flipfit.bean.User;
import com.flipfit.bean.GymCustomer;
import com.flipfit.bean.GymOwner;
import java.util.Scanner;

/**
 * 
 */
public class FlipFitApplication {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Scanner scanner = new Scanner(System.in);
		boolean exit = false;
		
		while(!exit) {
			System.out.println("Welcome to FlipFit Application");
			System.out.println("1. Login");
			System.out.println("2. Registration of Gym Owner");
			System.out.println("3. Registration of Gym Customer");
			System.out.println("4. Exit");
			
			System.out.println("Enter your choice: ");
			
			int choice = scanner.nextInt();
			
			switch (choice) {
				case 1: System.out.println("Login logic.."); //customer/owner/admin menu shown
						break;
					
				case 2: System.out.println("Registering Gym Owner..");
						break;
						
				case 3: System.out.println("Registering Gym Customer..");
						break;
						
				case 4: exit=true;
						System.out.println("Exiting FlipFit..");
						break;
						
			    default: System.out.println("Invalid choice. Please try again..");
			}
		}

	}
	
	private static void login(Scanner scanner) {
        System.out.print("Enter Username: ");
        String username = scanner.next();
        System.out.print("Enter Password: ");
        String password = scanner.next();

        // MOCK AUTHENTICATION LOGIC
        System.out.println("Authenticating...");
        
        // Assume we determined the role based on DB response:
        // for demo taking customer
        String role = "Customer"; // Change this to "Owner" or "Admin" to test other flows

        if (role.equalsIgnoreCase("Customer")) {
            System.out.println("Login Successful as Customer!");
            GymFlipFitCustomerMenu.showCustomerMenu(scanner, "user_123");
        } else if (role.equalsIgnoreCase("Owner")) {
            System.out.println("Login Successful as Gym Owner!");
            GymFlipFitOwnerMenu.showOwnerMenu(scanner);
        } else if (role.equalsIgnoreCase("Admin")) {
            System.out.println("Login Successful as Admin!");
            GymFlipFitAdminMenu.showAdminMenu(scanner);
        } else {
            System.out.println("Invalid Credentials.");
        }
    }

    private static void registerCustomer(Scanner scanner) {
        System.out.println("Redirecting to Customer Registration...");
        // Call Customer Business Service to register
    }

    private static void registerOwner(Scanner scanner) {
        System.out.println("Redirecting to Owner Registration...");
        // Call Owner Business Service to register
    }

}
