package com.flipfit.business;

import com.flipfit.bean.User;

public interface UserInterface {
    // General registration that returns a User object
    void register(String userId, String name, String email, String password, String roleName);
    boolean login(String email, String password);
    void logout();
}