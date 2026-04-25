package com.example.givinghandproject.api;


import com.example.givinghandproject.dto.campigan.CampaignListUpdateDTO;
import com.example.givinghandproject.dto.campigan.CampaignRequestDTO;
import com.example.givinghandproject.dto.campigan.CampaignResponseDTO;
import com.example.givinghandproject.entity.User;
import com.example.givinghandproject.service.CampaignService;
import com.example.givinghandproject.dao.UserDAO;
import com.example.givinghandproject.utilities.exceptions.BusinessException;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

import java.util.List;
import java.util.Map;

@Path("/campaigns")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CampaignResource {

    @Inject
    private CampaignService campaignService;

    @Inject
    private UserDAO userDAO;

    @Context
    private SecurityContext securityContext;

    @POST
    @RolesAllowed("Organization")
    public Response createCampaign(CampaignRequestDTO request) {
        String userMail = securityContext.getUserPrincipal().getName();
        String title = campaignService.create(request, userMail);
        return Response.status(Response.Status.CREATED)
                .entity(Map.of("message","Campaign : " + title + " created successfully"))
                .build();
    }

    @GET
    public Response getAllCampaigns(@QueryParam("category") String category) {
        List<CampaignResponseDTO> campaigns = campaignService.getCampaigns(category);
        return Response.ok(campaigns).build();
    }

    @PATCH
    @Path("/{id}/status")
    @RolesAllowed({"Organization", "Admin"})
    public Response updateStatus(@PathParam("id") Long id, String status) {
        User currentUser = getCurrentUser();
        String title = campaignService.updateCampaignStatus(id, status, currentUser);
         status = status.toUpperCase().replace("\"", "").trim();
        return Response.ok(Map.of("message" ,"Status of campaign '" + title + "' updated to "+ status) ).build();
    }

    @PUT
    @Path("/{id}/resources")
    @RolesAllowed({"Organization", "Admin"})
    public Response updateResources(@PathParam("id") Long id, CampaignListUpdateDTO dto) {
        User currentUser = getCurrentUser();
        String message = campaignService.updateCampaignRequiredResources(id, currentUser, dto);
        return Response.ok(Map.of("message",message)).build();
    }


    // utility for fetching user
    private User getCurrentUser() {
        String email = securityContext.getUserPrincipal().getName();
        return userDAO.getByEmail(email)
                .orElseThrow(() -> new BusinessException("Auth", "User session not found in database"));
    }
}
