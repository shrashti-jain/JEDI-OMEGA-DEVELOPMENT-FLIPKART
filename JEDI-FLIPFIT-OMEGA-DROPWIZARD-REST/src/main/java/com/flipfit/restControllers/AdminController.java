package com.flipfit.restControllers;

import com.flipfit.bean.GymCenter;
import com.flipfit.bean.User;
import com.flipfit.business.AdminImpl;
import com.flipfit.business.AdminInterface;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/admin")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AdminController {

    private final AdminInterface adminService = new AdminImpl();

    /**
     * Approves a Gym Owner.
     * Maps to adminService.configureUser(String userIdStr)
     */
    @PUT
    @Path("/approveOwner/{userId}")
    public Response approveOwner(@PathParam("userId") String userId) {
        try {
            boolean success = adminService.configureUser(userId);
            return Response.ok("{\"message\": \"Owner " + userId + " approved.\"}").build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    /**
     * Approves a Gym Center.
     * Maps to adminService.validateCenter(String centerId)
     */
    @PUT
    @Path("/approveCenter/{centerId}")
    public Response approveCenter(@PathParam("centerId") String centerId) {
        try {
            boolean success = adminService.validateCenter(centerId);
            if (success) {
                return Response.ok("{\"message\": \"Center " + centerId + " is now active.\"}").build();
            }
            return Response.status(Response.Status.NOT_FOUND).entity("Approval failed.").build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    /**
     * Fetches owners based on status (true for approved, false for pending).
     */
    @GET
    @Path("/owners")
    public Response getOwnersByStatus(@QueryParam("approved") boolean status) {
        List<User> owners = adminService.getOwnersByStatus(status);
        return Response.ok(owners).build();
    }

    /**
     * Fetches gym centers based on status.
     */
    @GET
    @Path("/centers")
    public Response getCentersByStatus(@QueryParam("approved") boolean status) {
        // Casting to implementation to access getGymCentersByStatus
        AdminImpl impl = (AdminImpl) adminService;
        List<GymCenter> centers = impl.getGymCentersByStatus(status);
        return Response.ok(centers).build();
    }

    /**
     * Deletes a user.
     */
    @DELETE
    @Path("/deleteUser/{userId}")
    public Response deleteUser(@PathParam("userId") int userId) {
        boolean deleted = adminService.removeUser(userId);
        return deleted ? Response.ok("User deleted").build() : Response.status(404).build();
    }
}