package com.flipfit.bean;

public class Admin extends User {
    public Admin(String userId, String name, String email, String phone, String password, String identityNo) {
        super(userId, name, email, phone, password, "OWNER", identityNo, false);
    }
}