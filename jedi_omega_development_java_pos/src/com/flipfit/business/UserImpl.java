package com.flipfit.business;

public class UserImpl implements UserInterface{
    @Override
    public boolean register() {
        // logic: get user bean -> call userDao.saveUser(user)
        return true;
    }

    @Override
    public boolean login() {
        // logic: check credentials in DB -> set session state
        return true;
    }

    @Override
    public boolean logout() {
        // logic: clear current user session
        return true;
    }
}
