package com.flipfit.config;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Test Database Connection
 * Run this to verify MySQL connectivity
 */
public class TestDBConnection {
    
    public static void main(String[] args) {
        System.out.println("=================================================");
        System.out.println("  FlipFit Database Connection Test");
        System.out.println("=================================================\n");
        
        DBConnectionManager dbManager = DBConnectionManager.getInstance();
        
        // Test 1: Connection Test
        System.out.println("TEST 1: Testing Database Connection");
        System.out.println("----------------------------------");
        boolean isConnected = dbManager.testConnection();
        if (isConnected) {
            System.out.println("✅ SUCCESS: Database connection is working!\n");
        } else {
            System.out.println("❌ FAILED: Could not connect to database");
            System.out.println("\n📋 Troubleshooting Steps:");
            System.out.println("1. Make sure MySQL server is running");
            System.out.println("2. Verify credentials in db.properties");
            System.out.println("3. Ensure flipfit_db database exists");
            System.out.println("4. Run the schema.sql file to create tables\n");
            return;
        }
        
        // Test 2: Query Database
        System.out.println("TEST 2: Querying Database Tables");
        System.out.println("----------------------------------");
        try (Connection conn = dbManager.getConnection();
             Statement stmt = conn.createStatement()) {
            
            // Check tables
            String query = "SHOW TABLES";
            ResultSet rs = stmt.executeQuery(query);
            
            System.out.println("📊 Tables in flipfit_db:");
            int tableCount = 0;
            while (rs.next()) {
                tableCount++;
                System.out.println("  " + tableCount + ". " + rs.getString(1));
            }
            
            if (tableCount == 0) {
                System.out.println("⚠️  No tables found. Please run schema.sql first!");
            } else {
                System.out.println("\n✅ Found " + tableCount + " tables");
            }
            
            rs.close();
            
        } catch (SQLException e) {
            System.err.println("❌ Error querying database: " + e.getMessage());
            e.printStackTrace();
        }
        
        // Test 3: Check Sample Data
        System.out.println("\nTEST 3: Checking Sample Data");
        System.out.println("----------------------------------");
        try (Connection conn = dbManager.getConnection();
             Statement stmt = conn.createStatement()) {
            
            // Count users
            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) as count FROM users");
            if (rs.next()) {
                int userCount = rs.getInt("count");
                System.out.println("👥 Users: " + userCount);
            }
            rs.close();
            
            // Count gym centers
            rs = stmt.executeQuery("SELECT COUNT(*) as count FROM gym_centers");
            if (rs.next()) {
                int centerCount = rs.getInt("count");
                System.out.println("🏋️  Gym Centers: " + centerCount);
            }
            rs.close();
            
            // Count slots
            rs = stmt.executeQuery("SELECT COUNT(*) as count FROM slots");
            if (rs.next()) {
                int slotCount = rs.getInt("count");
                System.out.println("📅 Slots: " + slotCount);
            }
            rs.close();
            
            // List gym centers
            System.out.println("\n📍 Available Gym Centers:");
            rs = stmt.executeQuery("SELECT center_name, city, status FROM gym_centers");
            while (rs.next()) {
                String name = rs.getString("center_name");
                String city = rs.getString("city");
                String status = rs.getString("status");
                System.out.println("  • " + name + " (" + city + ") - " + status);
            }
            rs.close();
            
            System.out.println("\n✅ Database is ready with sample data!");
            
        } catch (SQLException e) {
            System.err.println("❌ Error checking sample data: " + e.getMessage());
            System.err.println("⚠️  Please run schema.sql to create tables and insert sample data");
        }
        
        System.out.println("\n=================================================");
        System.out.println("  Connection Test Complete!");
        System.out.println("=================================================");
    }
}
