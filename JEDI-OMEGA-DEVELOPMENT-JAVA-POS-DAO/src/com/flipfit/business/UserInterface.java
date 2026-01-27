package com.flipfit.business;

import com.flipfit.bean.User;

public interface UserInterface {
    // General registration that returns a User object
    void registerCustomer(String name, String email, String password, String address, String city);
    void registerOwner(String name, String email, String password, String gymName, String address, String city);
    User authenticate(String email, String password);
    boolean updatePassword(String email, String newPassword);
    //void register(String userId, String name, String email, String password, String roleName);
    boolean login(String email, String password);
    void logout();
}