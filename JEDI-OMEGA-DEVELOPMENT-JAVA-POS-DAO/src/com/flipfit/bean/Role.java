package com.flipfit.bean;

// TODO: Auto-generated Javadoc
/**
 * The Class Role.
 *
 * This class represents a user role in the FlipFit system.
 * Roles are used to define access levels such as
 * CUSTOMER, OWNER, and ADMIN.
 *
 * Each role contains an ID, name, and description.
 *
 * @author Shreya
 * @ClassName "Role"
 */
public class Role {

    /** The unique role ID */
    private int roleId;

    /** The name of the role */
    private String roleName;

    /** The description of the role */
    private String roleDescription;

    /**
     * Instantiates a new Role.
     *
     * @param roleId the role ID
     * @param roleName the role name
     * @param roleDescription the role description
     */
    public Role(int roleId, String roleName, String roleDescription) {
        this.roleId = roleId;
        this.roleName = roleName;
        this.roleDescription = roleDescription;
    }

    /**
     * Gets the role ID.
     *
     * @return the role ID
     */
    public int getRoleId() {
        return roleId;
    }

    /**
     * Gets the role name.
     *
     * @return the role name
     */
    public String getRoleName() {
        return roleName;
    }

    /**
     * Gets the role description.
     *
     * @return the role description
     */
    public String getRoleDescription() {
        return roleDescription;
    }
}
