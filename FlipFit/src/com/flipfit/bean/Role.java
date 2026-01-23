package com.flipfit.bean;

public class Role {
    private String roleId;
    private String roleName;
    private String roleDescription;

    public Role(String roleId, String roleName, String roleDescription) {
        this.roleId = roleId;
        this.roleName = roleName;
        this.roleDescription = roleDescription;
    }

    // Getters and Setters
    public String getRoleId() { return roleId; }
    public String getRoleName() { return roleName; }
    public String getRoleDescription() { return roleDescription; }
}