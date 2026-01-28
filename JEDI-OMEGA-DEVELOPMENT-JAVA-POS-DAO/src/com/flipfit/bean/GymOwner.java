package com.flipfit.bean;

public class GymOwner extends User {
    private boolean isApproved; // For Admin validation logic in diagram

    public GymOwner(String userId, String name, String email, String phone, String password, String identityNo) {
        super(userId, name, email, phone, password, "OWNER", identityNo, false);
        this.isApproved = false;
    }
    public boolean isApproved() { return isApproved; }
    public void setApproved(boolean approved) { isApproved = approved; }
}