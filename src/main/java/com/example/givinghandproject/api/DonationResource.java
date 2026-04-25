package com.example.givinghandproject.api;

import com.example.givinghandproject.dao.UserDAO;
import com.example.givinghandproject.dto.donation.DonationRequestDTO;
import com.example.givinghandproject.entity.User;
import com.example.givinghandproject.service.DonationService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;

import java.util.Collections;

@Path("/donations")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class DonationResource {

    @Inject private DonationService donationService;
    @Inject private UserDAO userDAO;
    @Context private SecurityContext securityContext;

    @POST
    @RolesAllowed("Donor")
    public Response create(DonationRequestDTO dto) {
        String email = securityContext.getUserPrincipal().getName();
        Long id = donationService.commitDonation(dto, email);
        return Response.status(Response.Status.CREATED).entity(Collections.singletonMap("id", id)).build();
    }

    @PUT
    @Path("/{id}")
    @RolesAllowed("Donor")
    public Response update(@PathParam("id") Long id, DonationRequestDTO dto) {
        donationService.updateCommitment(id, dto.getQuantity(), getCurrentUser());
        return Response.ok(Collections.singletonMap("message", "Donation updated successfully")).build();
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed("Donor")
    public Response cancel(@PathParam("id") Long id) {
        donationService.cancelCommitment(id, getCurrentUser());
        return Response.ok(Collections.singletonMap("message", "Donation cancelled successfully")).build();
    }

    @PATCH
    @Path("/{id}/receive")
    @RolesAllowed({"Organization", "Admin"})
    public Response receive(@PathParam("id") Long id) {
        donationService.markAsReceived(id);
        return Response.ok(Collections.singletonMap("message", "Donation received and campaign updated")).build();
    }

    @PATCH
    @Path("/{id}/distribute")
    @RolesAllowed({"Organization", "Admin"})
    public Response distribute(@PathParam("id") Long id) {
        donationService.markAsDistributed(id);
        return Response.ok(Collections.singletonMap("message", "Donation distributed and logged in donor history")).build();
    }

    private User getCurrentUser() {
        String email = securityContext.getUserPrincipal().getName();
        return userDAO.getByEmail(email).orElseThrow();
    }
}
