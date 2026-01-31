package com.flipfit.business;

import com.flipfit.bean.GymCenter;
import com.flipfit.bean.User;

import java.util.List;

public interface AdminInterface {
    boolean configureUser(String userId);
    boolean validateCenter(String centerId);
    List<User> getAllUsers();

    boolean removeGymCenter(String centerId);

    boolean removeUser(int removeId);

    List<User> getOwnersByStatus(boolean b);

    List<GymCenter> getGymCentersByStatus(boolean b);
}