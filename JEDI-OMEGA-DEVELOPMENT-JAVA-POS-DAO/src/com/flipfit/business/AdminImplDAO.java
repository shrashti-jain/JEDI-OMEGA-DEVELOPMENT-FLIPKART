package com.flipfit.business;

import com.flipfit.bean.GymCenter;
import com.flipfit.dao.GymCenterDAO;

import java.util.List;

public class AdminImplDAO implements AdminInterface {

    private final GymCenterDAO gymCenterDAO = new GymCenterDAO();

    @Override
    public void configureUser(String userId) {
        System.out.println("[Admin] Configuring profile and permissions for User ID: " + userId);
    }

    @Override
    public void validateCenter(String centerId) {
        try {
            System.out.println("[Admin] Verifying gym center credentials for ID: " + centerId);
            
            // Get pending centers
            List<GymCenter> pendingCenters = gymCenterDAO.getPendingCenters();
            
            // Check if center exists and is pending
            boolean found = false;
            for (GymCenter center : pendingCenters) {
                if (center.getCenterId().equals(centerId)) {
                    found = true;
                    break;
                }
            }
            
            if (!found) {
                System.out.println("Center not found or already approved!");
                return;
            }
            
            // Approve the center
            boolean approved = gymCenterDAO.updateCenterStatus(centerId, "APPROVED");
            
            if (approved) {
                System.out.println("✓ Gym center approved successfully!");
            } else {
                System.out.println("Failed to approve center!");
            }
        } catch (Exception e) {
            System.out.println("Error validating center: " + e.getMessage());
        }
    }

    /**
     * Get all pending centers for approval
     */
    public List<GymCenter> getPendingCenters() {
        try {
            return gymCenterDAO.getPendingCenters();
        } catch (Exception e) {
            System.out.println("Error fetching pending centers: " + e.getMessage());
            return List.of();
        }
    }

    /**
     * Approve a gym center
     */
    public boolean approveCenter(String centerId) {
        try {
            return gymCenterDAO.updateCenterStatus(centerId, "APPROVED");
        } catch (Exception e) {
            System.out.println("Error approving center: " + e.getMessage());
            return false;
        }
    }

    /**
     * Remove/reject a gym center
     */
    public boolean removeCenter(String centerId) {
        try {
            return gymCenterDAO.deleteGymCenter(centerId);
        } catch (Exception e) {
            System.out.println("Error removing center: " + e.getMessage());
            return false;
        }
    }
}
