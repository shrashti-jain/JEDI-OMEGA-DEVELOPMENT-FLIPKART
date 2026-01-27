package com.flipfit.config;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Database Connection Manager - Singleton Pattern
 * Manages MySQL database connections for FlipFit application
 */
public class DBConnectionManager {
    
    private static DBConnectionManager instance;
    private String url;
    private String username;
    private String password;
    private String driver;
    
    // Private constructor for Singleton pattern
    private DBConnectionManager() {
        try {
            loadProperties();
            // Load MySQL JDBC Driver
            Class.forName(driver);
            System.out.println("✅ MySQL JDBC Driver loaded successfully!");
        } catch (ClassNotFoundException e) {
            System.err.println("❌ ERROR: MySQL JDBC Driver not found!");
            System.err.println("Please add mysql-connector-java to your classpath.");
            e.printStackTrace();
        }
    }
    
    /**
     * Get singleton instance of DBConnectionManager
     */
    public static DBConnectionManager getInstance() {
        if (instance == null) {
            synchronized (DBConnectionManager.class) {
                if (instance == null) {
                    instance = new DBConnectionManager();
                }
            }
        }
        return instance;
    }
    
    /**
     * Load database properties from config file
     */
    private void loadProperties() {
        Properties props = new Properties();
        try (InputStream input = getClass().getClassLoader()
                .getResourceAsStream("com/flipfit/config/db.properties")) {
            
            if (input == null) {
                System.err.println("❌ Unable to find db.properties");
                // Fallback to default values
                setDefaultProperties();
                return;
            }
            
            props.load(input);
            this.url = props.getProperty("db.url");
            this.username = props.getProperty("db.username");
            this.password = props.getProperty("db.password");
            this.driver = props.getProperty("db.driver");
            
            System.out.println("✅ Database configuration loaded successfully!");
            
        } catch (IOException e) {
            System.err.println("❌ Error loading database properties");
            e.printStackTrace();
            setDefaultProperties();
        }
    }
    
    /**
     * Set default properties if config file is not found
     */
    private void setDefaultProperties() {
        this.url = "jdbc:mysql://localhost:3306/flipfit_db?useSSL=false&serverTimezone=UTC";
        this.username = "root";
        this.password = "Sql@22";
        this.driver = "com.mysql.cj.jdbc.Driver";
    }
    
    /**
     * Get a database connection
     * @return Connection object
     * @throws SQLException if connection fails
     */
    public Connection getConnection() throws SQLException {
        try {
            Connection conn = DriverManager.getConnection(url, username, password);
            System.out.println("✅ Database connection established!");
            return conn;
        } catch (SQLException e) {
            System.err.println("❌ Failed to connect to database!");
            System.err.println("URL: " + url);
            System.err.println("Username: " + username);
            throw e;
        }
    }
    
    /**
     * Test database connection
     * @return true if connection is successful
     */
    public boolean testConnection() {
        try (Connection conn = getConnection()) {
            return conn != null && !conn.isClosed();
        } catch (SQLException e) {
            System.err.println("❌ Connection test failed: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Close database connection safely
     */
    public static void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
                System.out.println("✅ Database connection closed.");
            } catch (SQLException e) {
                System.err.println("❌ Error closing connection: " + e.getMessage());
            }
        }
    }
}
