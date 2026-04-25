package com.example.givinghandproject.api;

import com.example.givinghandproject.dto.item.ItemRequestDTO;
import com.example.givinghandproject.dto.item.ItemResponseDTO;
import com.example.givinghandproject.service.ItemService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;
import java.util.Map;

@Path("/items")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ItemResource {

    @Inject
    private ItemService itemService;

    @POST
    @RolesAllowed("Admin")
    public Response createItem(ItemRequestDTO dto) {
        Long id = itemService.createItem(dto);
        return Response.status(Response.Status.CREATED).entity(Map.of("message", "Item created with ID: " + id)).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<ItemResponseDTO> listItems() {
        return itemService.getAllItems();
    }

    @GET
    @Path("/{id}")
    public ItemResponseDTO getItem(@PathParam("id") Long id) {
        return itemService.getItemById(id);
    }
}
