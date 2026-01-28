package com.flipfit.bean;

public class GymCustomer extends User {
    public GymCustomer(String userId, String name, String email, String phone, String password) {
        // Must pass 8 arguments to match the updated User bean
        super(userId, name, email, phone, password, "CUSTOMER", null, true);
    }
}