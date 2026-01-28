package com.flipfit.business;

import com.flipfit.bean.GymCenter;
import com.flipfit.bean.User;

import java.util.List;

public interface AdminInterface {

    List<User> getPendingOwners();

    List<GymCenter> getPendingCenters();

    boolean removeCenter(String removeId);

    boolean approveCenter(String approveId);

    boolean approveOwner(String ownerEmail);

    boolean removeOwner(String ownerEmail);

    List<User> getOwnersByStatus(boolean b);

    List<GymCenter> getGymCentersByStatus(boolean b);
}