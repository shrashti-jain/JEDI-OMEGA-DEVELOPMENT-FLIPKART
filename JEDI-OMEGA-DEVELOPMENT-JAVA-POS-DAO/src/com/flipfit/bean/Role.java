package com.flipfit.bean;

// TODO: Auto-generated Javadoc
/**
 * The Class Role.
 * Represents a specific role or permission set assigned to a user within the FlipFit system.
 * Examples include 'Admin', 'Customer', or 'GymOwner'.
 *
 * @author Shreya
 * @ClassName Role
 */
public class Role {
    private String roleId;
    private String roleName;
    private String roleDescription;

    /**
     * Instantiates a new Role.
     *
     * @param roleId the unique identifier for the role
     * @param roleName the display name of the role
     * @param roleDescription a brief description of the role's permissions or purpose
     */
    public Role(String roleId, String roleName, String roleDescription) {
        this.roleId = roleId;
        this.roleName = roleName;
        this.roleDescription = roleDescription;
    }

    // Getters and Setters

    /**
     * Gets the role id.
     *
     * @return the role id
     */
    public String getRoleId() { return roleId; }

    /**
     * Gets the role name.
     *
     * @return the role name
     */
    public String getRoleName() { return roleName; }

    /**
     * Gets the role description.
     *
     * @return the role description
     */
    public String getRoleDescription() { return roleDescription; }
}