package com.example.givinghandproject.api;

import com.example.givinghandproject.dao.UserDAO;
import com.example.givinghandproject.dao.WarehouseDAO;
import com.example.givinghandproject.dto.inventory.InventoryAddRequest;
import com.example.givinghandproject.dto.inventory.InventoryAllocationRequest;
import com.example.givinghandproject.dto.inventory.WarehouseResponseDTO;
import com.example.givinghandproject.entity.User;
import com.example.givinghandproject.entity.Warehouse;
import com.example.givinghandproject.service.WarehouseService;
import com.example.givinghandproject.utilities.exceptions.BusinessException;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;

import java.util.Collections;

@Path("/warehouse")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class WarehouseResource {
    @Inject private WarehouseService warehouseService;
    @Inject private UserDAO userDAO;
    @Inject private WarehouseDAO warehouseDAO;
    @Context private SecurityContext securityContext;

    @POST
    @Path("/setup")
    @RolesAllowed("Organization")
    public Response setup(WarehouseResponseDTO request) {
        warehouseService.setupWarehouse(request.getName(), getCurrentUser());
        return Response.ok(Collections.singletonMap("message", "Warehouse created")).build();
    }

    @POST
    @Path("/allocate")
    @RolesAllowed("Organization")
    public Response allocate(InventoryAllocationRequest request) {
        warehouseService.allocateResources(request);
        return Response.ok(Collections.singletonMap("message", "Allocation completed successfully")).build();
    }

    @GET
    @Path("/dashboard")
    @RolesAllowed("Organization")
    public Response getDashboard() {
        WarehouseResponseDTO dashboard = warehouseService.getDashboard(getCurrentUser().getId());
        return Response.ok(dashboard).build();
    }
    @POST
    @Path("/inventory")
    @RolesAllowed("Organization")
    public Response addManualInventory(InventoryAddRequest request) {
        User org = getCurrentUser();
        Warehouse wh = warehouseDAO.findByOrganization(org.getId());

        if (wh == null) {
            throw new BusinessException("Setup", "No warehouse found for this organization. Please run /setup first.");
        }

        warehouseService.addOrUpdateInventory(
                wh.getId(),
                request.getItemId(),
                request.getQuantity(),
                request.getThreshold()
        );

        return Response.ok(Collections.singletonMap("message", "Inventory added or updated successfully")).build();
    }

    private User getCurrentUser() {
        return userDAO.getByEmail(securityContext.getUserPrincipal().getName()).orElseThrow();
    }
}
