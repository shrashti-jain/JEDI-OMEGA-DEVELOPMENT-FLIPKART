package com.flipfit.business;

import com.flipfit.bean.User;

public interface UserInterface {
    void registerCustomer(String name, String email, String pass, String contact, String addr, String city);

    void registerOwner(String name, String email, String pass, String contact, String idNo, String owner);

    boolean updatePassword(String email, String confirmPass);

    User authenticate(String email, String pass);

    public boolean login(String email, String password);
    public void logout();
    // General registration that returns a User object
   
}