package com.flipfit.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    // Database credentials
    private static final String URL = "jdbc:mysql://localhost:3306/FlipFit_Schema";
    private static final String USER = "root";
    private static final String PASS = "shristi_SQL";

    /**
     * Static method to return a new Connection object.
     * Centralizing this allows you to change DB settings in one place.
     */
    public static Connection getConnection() {
        Connection conn = null;
        try {
            // Load the driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Establish connection
            conn = DriverManager.getConnection(URL, USER, PASS);
        } catch (ClassNotFoundException e) {
            System.err.println("MySQL Driver not found: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Database Connection Failed: " + e.getMessage());
        }
        return conn;
    }
}