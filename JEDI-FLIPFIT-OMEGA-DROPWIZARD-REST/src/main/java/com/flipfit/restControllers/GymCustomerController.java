package com.flipfit.restControllers;

import com.flipfit.bean.GymCenter;
import com.flipfit.bean.Slot;
import com.flipfit.business.GymCustomerImpl;
import com.flipfit.business.GymCustomerImpl;
import com.flipfit.bean.Booking;
import com.flipfit.bean.Waitlist;
import com.flipfit.business.GymCustomerInterface;
import com.flipfit.business.GymOwnerImpl;
import com.flipfit.exception.BookingFailedException;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.Date;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

@Path("/customer")
@Produces(MediaType.APPLICATION_JSON) // Automatically converts Java objects to JSON
public class GymCustomerController {

    private final GymCustomerImpl customerService = new GymCustomerImpl();

    @GET
    @Path("/dashboard/{email}")
    public Response getDashboard(@PathParam("email") String email) {
        try {
            List<Booking> bookings = customerService.viewBookings(email);
            List<Waitlist> waitlist = customerService.viewWaitlist(email);

            // Combining both lists into one JSON object
            Map<String, Object> dashboardData = new HashMap<>();
            dashboardData.put("confirmedBookings", bookings);
            dashboardData.put("pendingWaitlist", waitlist);

            return Response.ok(dashboardData).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(e.getMessage()).build();
        }
    }

    /**
     * Book a Slot
     * POST http://localhost:8080/customer/bookSlot
     */
    @POST
    @Path("/bookSlot")
    public Response bookSlot(Booking request) {
        try {
            Booking booking = customerService.bookSlot(
                    request.getUserId(),
                    request.getSlotId(),
                    request.getCenterId(),
                    request.getSlotDate()
            );

            if (booking != null) {
                return Response.status(Response.Status.CREATED).entity(booking).build();
            } else {
                // If null, it means decreaseAvailableSeats failed (Gym full)
                return Response.status(Response.Status.CONFLICT)
                        .entity("{\"message\": \"Slot is full. Please join the waitlist.\"}").build();
            }
        } catch (BookingFailedException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\": \"" + e.getMessage() + "\"}").build();
        }
        catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    /**
     * 5. View My Bookings (By Email)
     * GET http://localhost:8080/customer/myBookings?email=user@mail.com
     */
    @GET
    @Path("/myBookings")
    public Response viewBookings(@QueryParam("email") String email) {
        List<Booking> bookings = customerService.viewBookings(email);
        return Response.ok(bookings).build();
    }

    /**
     * Add to Waitlist
     * POST http://localhost:8080/customer/waitlist
     */
    @POST
    @Path("/waitlist")
    public Response addToWaitlist(Waitlist request) {
        boolean added = customerService.addWaitlist(request.getUserId(), request.getSlotId(), request.getBookingDate());
        if (added) {
            return Response.ok("{\"message\": \"Added to waitlist successfully.\"}").build();
        }
        return Response.status(Response.Status.BAD_REQUEST).entity("Failed to add to waitlist.").build();
    }

    /**
     * View My Waitlist (By Email)
     * GET http://localhost:8080/customer/myWaitlist?email=user@mail.com
     */
    @GET
    @Path("/myWaitlist")
    public Response viewWaitlist(@QueryParam("email") String email) {
        List<Waitlist> waitlist = customerService.viewWaitlist(email);
        return Response.ok(waitlist).build();
    }

    /**
     * Retrieves all available slots for a specific gym center.
     * Uses the static helper from your GymOwnerImpl.
     */
    @GET
    @Path("/viewSlots/{centerId}")
    public Response getSlotsByCenter(@PathParam("centerId") int centerId) {
        // Your logic uses a Date object; here we pass the current date
        List<Slot> slots = GymOwnerImpl.getSlotsByCenter(centerId, new Date());

        Date today = new Date();
        for(Slot s : slots) {
            s.setDate(today);
        }

        if (slots.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"message\": \"No slots found for this center.\"}").build();
        }
        return Response.ok(slots).build();
    }

    /**
     * 1. View Centers by City
     * GET http://localhost:8080/customer/centers/{city}
     */
    @GET
    @Path("/centers/{city}")
    public Response viewCenters(@PathParam("city") String city) {
        try {
            List<GymCenter> centers = customerService.viewCenters(city);
            return Response.ok(centers).build();
        } catch (Exception e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    /**
     * 2. View Slot Availability
     * GET http://localhost:8080/customer/slots?centerId=3
     */
    @GET
    @Path("/slots")
    public Response viewSlots(@QueryParam("centerId") int centerId) {
        try {
            // Passing current date by default for simplicity
            List<Slot> slots = customerService.viewSlotAvailability(centerId, new Date());
            return Response.ok(slots).build();
        } catch (Exception e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    /**
     * Fetches a specific slot's details by its ID.
     */
    @GET
    @Path("/slotDetails/{slotId}")
    public Response getSlotDetails(@PathParam("slotId") int slotId) {
        // You can use your SlotDAOImpl's getSlotById logic here
        // (Assuming you have access to it or a business layer wrapper)
        return Response.ok("Endpoint ready for slot detail retrieval").build();
    }

    /**
     * 7. Cancel Booking
     * DELETE http://localhost:8080/customer/cancel/{bookingId}
     */
    @DELETE
    @Path("/cancel/{bookingId}")
    public Response cancelBooking(@PathParam("bookingId") String bookingId) {
        try {
            boolean success = customerService.cancelBooking(bookingId);
            if (success) {
                return Response.ok("{\"message\": \"Booking cancelled. Waitlisted users may have been promoted.\"}").build();
            }
            return Response.status(Response.Status.NOT_FOUND).entity("Booking not found.").build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }
}