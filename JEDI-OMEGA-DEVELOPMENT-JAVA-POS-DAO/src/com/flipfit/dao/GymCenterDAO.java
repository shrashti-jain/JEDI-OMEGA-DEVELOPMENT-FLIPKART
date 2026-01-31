package com.flipfit.dao;

import com.flipfit.bean.GymCenter;
import java.util.List;

public interface GymCenterDAO {


    boolean addGymCenter(GymCenter newCenter);

    List<GymCenter> getCentersByOwner(int ownerId);

    boolean checkApprovalStatus(int centerId);

    List<GymCenter> getApprovedCentersByCity(String city);

    String getCenterNameById(int centerId);

}
