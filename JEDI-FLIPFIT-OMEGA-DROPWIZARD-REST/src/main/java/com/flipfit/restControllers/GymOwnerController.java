package com.flipfit.restControllers;

import com.flipfit.bean.GymCenter;
import com.flipfit.bean.Slot;
import com.flipfit.business.GymOwnerImpl;
import com.flipfit.business.GymOwnerInterface;
import com.flipfit.exception.FlipFitException;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/owner")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class GymOwnerController {

    private final GymOwnerInterface ownerService = new GymOwnerImpl();

    /**
     * Endpoint: POST /owner/addCenter
     * Since your business logic takes 7 primitive/String arguments,
     * we receive a GymCenter object and extract the fields.
     */
    @POST
    @Path("/addCenter")
    public Response addGymCenter(GymCenter center) {
        try {
            ownerService.addCenter(
                    center.getOwnerId(), // Mapping userId from the object
                    center.getCenterName(),
                    center.getLocation(),
                    center.getCity(),
                    center.getPincode(),
                    center.getGstNo(),
                    center.getCapacity()
            );
            return Response.status(Response.Status.CREATED)
                    .entity("Gym Center request submitted successfully.")
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage()).build();
        }
    }

    /**
     * Endpoint: POST /owner/addSlot/{centerId}
     */
    @POST
    @Path("/addSlot/{centerId}")
    public Response addSlot(@PathParam("centerId") int centerId, Slot slot) {
        try {
            // Your logic requires centerId and a Slot object
            slot.setCenterId(centerId);
            ownerService.addSlot(centerId, slot);
            return Response.status(Response.Status.CREATED)
                    .entity("Slot successfully added.")
                    .build();
        } catch (FlipFitException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\": \"Logic Failure: " + e.getMessage() + "\"}").build();
        }
        catch (Exception e) {
            return Response.status(Response.Status.FORBIDDEN)
                    .entity(e.getMessage()).build();
        }

    }

    /**
     * Endpoint: GET /owner/centers/{userId}
     */
    @GET
    @Path("/centers/{userId}")
    public Response getMyCenters(@PathParam("userId") int userId) {
        try {
            List<GymCenter> centers = ownerService.getCentersByOwner(userId);
            return Response.ok(centers).build();
        } catch (Exception e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage()).build();
        }
    }

    /**
     * Endpoint: GET /owner/checkApproval/{centerId}
     */
    @GET
    @Path("/checkApproval/{centerId}")
    public Response checkCenterApproval(@PathParam("centerId") int centerId) {
        boolean isApproved = ownerService.isCenterApproved(centerId);
        return Response.ok("{\"approved\": " + isApproved + "}").build();
    }


}