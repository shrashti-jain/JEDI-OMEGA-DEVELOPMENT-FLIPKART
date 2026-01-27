package com.flipfit.test;

import com.flipfit.dao.*;
import com.flipfit.bean.*;
import java.util.List;

/**
 * Test class to verify DAO operations
 */
public class TestDAOs {
    
    public static void main(String[] args) {
        System.out.println("=================================================");
        System.out.println("  FlipFit DAO Operations Test");
        System.out.println("=================================================\n");
        
        // Test User DAO
        testUserDAO();
        
        // Test Customer DAO
        testCustomerDAO();
        
        // Test Gym Center DAO
        testGymCenterDAO();
        
        // Test Slot DAO
        testSlotDAO();
        
        // Test Booking DAO
        testBookingDAO();
        
        System.out.println("\n=================================================");
        System.out.println("  All DAO Tests Complete!");
        System.out.println("=================================================");
    }
    
    private static void testUserDAO() {
        System.out.println("TEST 1: User DAO Operations");
        System.out.println("----------------------------------");
        
        UserDAO userDAO = new UserDAO();
        
        // Test authentication
        User user = userDAO.authenticateUser("admin@flipfit.com", "admin123");
        if (user != null) {
            System.out.println("✅ User authentication successful!");
            System.out.println("   User: " + user.getName() + " (" + user.getRole() + ")");
        } else {
            System.out.println("❌ User authentication failed!");
        }
        
        // Test get user by email
        user = userDAO.getUserByEmail("customer@flipfit.com");
        if (user != null) {
            System.out.println("✅ Get user by email successful!");
            System.out.println("   User: " + user.getName() + " - " + user.getEmail());
        }
        System.out.println();
    }
    
    private static void testCustomerDAO() {
        System.out.println("TEST 2: Customer DAO Operations");
        System.out.println("----------------------------------");
        
        CustomerDAO customerDAO = new CustomerDAO();
        
        // Test get all customers
        List<GymCustomer> customers = customerDAO.selectAllCustomers();
        System.out.println("✅ Found " + customers.size() + " customers in database");
        for (GymCustomer customer : customers) {
            System.out.println("   • " + customer.getName() + " (" + customer.getEmail() + ")");
        }
        System.out.println();
    }
    
    private static void testGymCenterDAO() {
        System.out.println("TEST 3: Gym Center DAO Operations");
        System.out.println("----------------------------------");
        
        GymCenterDAO gymDAO = new GymCenterDAO();
        
        // Test get centers by city
        List<GymCenter> centers = gymDAO.getCentersByCity("Delhi");
        System.out.println("✅ Found " + centers.size() + " gym(s) in Delhi:");
        for (GymCenter center : centers) {
            System.out.println("   • " + center.getCenterName() + " - " + center.getLocation());
        }
        
        // Test get pending centers
        List<GymCenter> pending = gymDAO.getPendingCenters();
        System.out.println("✅ Found " + pending.size() + " pending gym center(s)");
        System.out.println();
    }
    
    private static void testSlotDAO() {
        System.out.println("TEST 4: Slot DAO Operations");
        System.out.println("----------------------------------");
        
        SlotDAO slotDAO = new SlotDAO();
        
        // Test get slots by center - using today's date
        java.sql.Date testDate = java.sql.Date.valueOf("2026-01-27");
        List<Slot> slots = slotDAO.getSlotsByCenterAndDate("GYM001", testDate);
        System.out.println("✅ Found " + slots.size() + " slot(s) for Fitness Pro Delhi on 2026-01-27:");
        for (Slot slot : slots) {
            System.out.println("   • " + slot.getStartTime() + " - " + slot.getEndTime() + 
                             " (Available: " + slot.getAvailableSeats() + "/" + slot.getCapacity() + ")");
        }
        System.out.println();
    }
    
    private static void testBookingDAO() {
        System.out.println("TEST 5: Booking DAO Operations");
        System.out.println("----------------------------------");
        
        BookingDAO bookingDAO = new BookingDAO();
        
        // Test get bookings by user
        List<Booking> bookings = bookingDAO.getBookingsByUserEmail("customer@flipfit.com");
        System.out.println("✅ Found " + bookings.size() + " booking(s) for customer@flipfit.com");
        
        if (bookings.isEmpty()) {
            System.out.println("   (No bookings yet - customer can make bookings through the app)");
        } else {
            for (Booking booking : bookings) {
                System.out.println("   • " + booking.getGymName() + " - " + 
                                 booking.getSlotDate() + " " + booking.getSlotTime() + 
                                 " [" + booking.getStatus() + "]");
            }
        }
        System.out.println();
    }
}
