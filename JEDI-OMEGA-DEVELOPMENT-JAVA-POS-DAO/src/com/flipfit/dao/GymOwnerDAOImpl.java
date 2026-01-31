package com.flipfit.dao;

import com.flipfit.utility.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import static com.flipfit.constant.SQLConstants.*;

/**
 * Implementation of GymOwnerDAO for database operations.
 * Manages the lifecycle and ID mapping of Gym Owners in the relational schema.
 * * @author Shreya / Krishna Nirvas (Integrated)
 * @ClassName "GymOwnerDAOImpl"
 */
public class GymOwnerDAOImpl implements GymOwnerDAO {

    /**
     * Fetches the internal gym owner ID using the base user ID.
     * Used by the business layer to verify if an owner is approved before adding centers.
     * * @param userId the user ID from the 'users' table
     * @return owner ID if found and approved, otherwise -1
     */
    @Override
    public int getOwnerIdByUserId(int userId) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(GET_OWNER_ID_BY_USER_ID)) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                // Returns the auto-incremented primary key from the gym_owner table
                return rs.getInt("owner_id");
            }

        } catch (Exception e) {
            System.err.println("Error fetching owner ID for User ID " + userId + ": " + e.getMessage());
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * Approves a gym owner in the database.
     * Updates the status so the owner can begin managing gym centers.
     * * @param userId the user ID of the gym owner
     * @return true if the update was successful
     */
    @Override
    public boolean approveGymOwner(int userId) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(APPROVE_GYM_OWNER)) {

            ps.setInt(1, userId);
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Creates a new gym owner record linked to a user account.
     * This is the second step of the owner registration process.
     * * @param userId the numeric user ID
     * @return true if the gym_owner entry was created successfully
     */
    @Override
    public boolean createOwner(int userId, String identityNo) {
        // Ensure column name matches your ALTER TABLE statement exactly
        String sql = "INSERT INTO gym_owner (user_id, identity_no, approved) VALUES (?, ?, 0)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ps.setString(2, identityNo); // 👈 Save the Aadhar/PAN here

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}