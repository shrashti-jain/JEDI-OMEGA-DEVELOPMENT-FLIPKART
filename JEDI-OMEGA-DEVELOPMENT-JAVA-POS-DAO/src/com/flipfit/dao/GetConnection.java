package com.flipfit.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Database Connection Utility Class
 * Manages MySQL database connections
 */
public class GetConnection {
    
    // Database connection details
    private static final String DB_URL = "jdbc:mysql://localhost:3306/flipfit_db";
    private static final String USER = "root"; // Replace with your MySQL username
    private static final String PASS = "Sql@22"; // Replace with your MySQL password
    
    /**
     * Method to get a database connection
     * @return Connection object
     * @throws SQLException if connection fails
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, USER, PASS);
    }
}
