package com.flipfit.bean;

public class Admin extends User{
    public Admin(){}

    public Admin(String userId, String name, String email, String phone){
        super(userId,name,email,phone);
    }

    public void configure(User user){}
    public void validateCenter(String centerId){}

}
