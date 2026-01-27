package com.flipfit.business;

public class AdminImpl implements AdminInterface {
    @Override
    public void configureUser(String userId) {
        System.out.println("[Admin] Configuring profile and permissions for User ID: " + userId);
    }

    @Override
    public void validateCenter(String centerId) {
        System.out.println("[Admin] Verifying gym center credentials for ID: " + centerId);
    }
}