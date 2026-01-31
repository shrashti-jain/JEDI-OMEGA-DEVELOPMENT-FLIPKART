package com.flipfit.utility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static final String URL =
            "jdbc:mysql://localhost:3306/flipfit";
    private static final String USER = "root";
    private static final String PASSWORD = "shristi_SQL";

    public static Connection getConnection() {
        Connection conn = null;
        try {
            // Step 1: Force load driver (this triggers your current error if JAR is missing)
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Step 2: Establish connection
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            System.err.println("JDBC Driver not found! Add the connector JAR to your classpath.");
        } catch (SQLException e) {
            System.err.println("Connection Failed! Check URL, username, and password.");
        }
        return conn;
    }
}
