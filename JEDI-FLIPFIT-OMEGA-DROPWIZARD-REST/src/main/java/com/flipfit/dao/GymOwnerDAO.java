package com.flipfit.dao;

public interface GymOwnerDAO {
    int getOwnerIdByUserId(int userId);
    boolean approveGymOwner(int userId);
    boolean createOwner(int userId, String identityNo);





}
