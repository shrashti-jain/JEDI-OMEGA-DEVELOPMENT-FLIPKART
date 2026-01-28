package com.flipfit.bean;

public class User {
    private String userId;
    private String name;
    private String email;
    private String contact;

    public User(String userId, String name, String email, String contact, String password, String role, String identityNo, boolean isApproved) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.contact = contact;
        this.password = password;
        this.role = role;
        this.identityNo = identityNo;
        this.isApproved = isApproved;
    }

    private String password;
    private String role;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getIdentityNo() {
        return identityNo;
    }

    public void setIdentityNo(String identityNo) {
        this.identityNo = identityNo;
    }

    public boolean isApproved() {
        return isApproved;
    }

    public void setApproved(boolean approved) {
        isApproved = approved;
    }

    private String identityNo; // New Field
    private boolean isApproved;


}