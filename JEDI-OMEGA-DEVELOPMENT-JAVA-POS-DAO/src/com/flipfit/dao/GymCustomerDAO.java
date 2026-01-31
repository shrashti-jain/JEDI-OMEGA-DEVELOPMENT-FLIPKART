package com.flipfit.dao;

public interface GymCustomerDAO {
    boolean createCustomer(int userId, String address, String city);
}
