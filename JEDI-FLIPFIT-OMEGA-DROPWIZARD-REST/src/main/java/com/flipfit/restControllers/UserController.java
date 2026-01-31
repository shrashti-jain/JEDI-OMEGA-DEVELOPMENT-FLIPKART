package com.flipfit.restControllers;

import com.flipfit.bean.GymCustomer;
import com.flipfit.bean.GymOwner;
import com.flipfit.business.UserImpl;
import com.flipfit.business.UserInterface;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/user")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserController {

    private final UserInterface userService = new UserImpl();

    /**
     * Endpoint: POST /user/registerCustomer
     */
    @POST
    @Path("/registerCustomer")
    public Response registerCustomer(GymCustomer customer) {
        try {
            userService.registerCustomer(
                    customer.getName(),
                    customer.getEmail(),
                    customer.getPassword(),
                    customer.getPhone(),
                    customer.getAddress(),
                    customer.getCity()
            );
            return Response.status(Response.Status.CREATED)
                    .entity("{\"message\": \"Customer registered successfully.\"}").build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\": \"" + e.getMessage() + "\"}").build();
        }
    }

    /**
     * Endpoint: POST /user/registerOwner
     */
    @POST
    @Path("/registerOwner")
    public Response registerOwner(GymOwner owner) {
        try {
            userService.registerOwner(
                    owner.getName(),
                    owner.getEmail(),
                    owner.getPassword(),
                    owner.getPhone(),
                    owner.getIdentityNo()
            );
            return Response.status(Response.Status.CREATED)
                    .entity("{\"message\": \"Registration request sent! Pending Admin approval.\"}").build();
        } catch (Exception e) {
            e.printStackTrace(); // This prints the full error to your IntelliJ console
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"debug_error\": \"" + e.getMessage() + "\"}").build();
        }
    }
}