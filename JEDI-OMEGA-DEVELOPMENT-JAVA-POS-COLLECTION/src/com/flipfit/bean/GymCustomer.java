package com.flipfit.bean;

public class GymCustomer extends User {
    public GymCustomer(String userId, String name, String email, String phone, String password) {
        super(userId, name, email, phone, password, "Customer");
    }
}