package com.flipfit.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//TODO: Auto-generated Javadoc
/**
* The Class DBConnection.
* Utility class for managing the connection to the MySQL database.
* Provides a centralized static method to establish and retrieve database connections
* using JDBC properties.
*
* @author Shrashti
* @ClassName DBConnection
*/
public class DBConnection {
    // Database credentials
    private static final String URL = "jdbc:mysql://localhost:3306/FlipFit_Schema";
    private static final String USER = "root";
    private static final String PASS = "shristi_SQL";

    /**
     * Gets the connection.
     * Static method to return a new Connection object.
     * Centralizing this allows you to change DB settings in one place.
     * Loads the MySQL JDBC driver and establishes a connection using the defined credentials.
     *
     * @return the active Connection object, or null if the connection fails
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