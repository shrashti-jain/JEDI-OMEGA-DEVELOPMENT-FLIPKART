package com.flipfit.bean;

public class GymCustomer extends User {
    // Default constructor for DAO
    public GymCustomer() {
        super();
    }

    public GymCustomer(String userId, String name, String email, String phone, String password) {
        super(userId, name, email, phone, password, "Customer");
    }
}