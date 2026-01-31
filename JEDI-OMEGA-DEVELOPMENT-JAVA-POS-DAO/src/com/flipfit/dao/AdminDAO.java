package com.flipfit.dao;

import com.flipfit.bean.GymCenter;
import com.flipfit.bean.User;

import java.util.List;

public interface AdminDAO {

    boolean approveOwner(int userId);

    boolean approveGymCenter(int centerId);

    List<User> getAllOwners();

    List<GymCenter> getAllGymCenters();

    boolean deleteUser(int uid);

    boolean deleteGymCenter(int centerId);
}
