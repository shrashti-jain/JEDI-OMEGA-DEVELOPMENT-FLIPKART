package com.flipfit.dao;

import com.flipfit.utility.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 * Implementation of GymCustomerDAO for database operations.
 * Handles customer-specific profile mapping within the relational schema.
 * * @author Shreya / Krishna Nirvas (Integrated)
 * @ClassName "GymCustomerDAOImpl"
 */
public class GymCustomerDAOImpl implements GymCustomerDAO {

    /**
     * Creates a gym customer entry in the database.
     * Maps user-specific details (address, city) to the primary User ID.
     * * @param userId the numeric user ID from the 'users' table
     * @param address the residential address of the customer
     * @param city the city of residence
     * @return true if the customer profile was successfully created
     */
    @Override
    public boolean createCustomer(int userId, String address, String city) {

        // SQL using the new lower-case table naming convention from your schema screenshot
        String sql = "INSERT INTO gym_customer (user_id, address, city) VALUES (?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            // Mapping parameters to the Prepared Statement
            ps.setInt(1, userId);
            ps.setString(2, address);
            ps.setString(3, city);

            // Execute the update and return success status
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            // Log the error for debugging during the integration phase
            System.err.println("Error creating customer profile for User ID " + userId + ": " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }
}