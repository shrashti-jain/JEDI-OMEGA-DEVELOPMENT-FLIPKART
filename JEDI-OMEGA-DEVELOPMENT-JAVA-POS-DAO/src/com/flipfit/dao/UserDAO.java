package com.flipfit.dao;

import com.flipfit.bean.User;

import java.util.List;

public interface UserDAO {
    boolean registerUser(User user);
    User authenticate(String email, String password);
    boolean updatePassword(String email, String newPassword);
    int getUserIdByEmail(String email);
    public List<User> getAllUsers();

}
