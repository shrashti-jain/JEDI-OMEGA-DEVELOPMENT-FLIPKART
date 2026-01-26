package com.flipfit.bean;

public class User {
    private String userId;
    private String name;
    private String email;

    public void setPassword(String password) {
        this.password = password;
    }

    private String phone;
    private String password; // Required for login logic
    private String role;     // Matches the "role" logic in your FlipFitApplication.java

    public User(String userId, String name, String email, String phone, String password, String role) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.role = role;
    }

    // Getters
    public String getUserId() { return userId; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public String getRole() { return role; }

}