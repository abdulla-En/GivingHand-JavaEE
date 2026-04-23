package com.example.givinghandproject.api;

import jakarta.annotation.security.PermitAll;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.Map;
@Path("/server-errors")
public class ServerResource {

    private Response getCommonResponse(HttpServletRequest request) {
        Object statusCode = request.getAttribute("jakarta.servlet.error.status_code");
        if (statusCode == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.status(Response.Status.UNAUTHORIZED)
                .entity(Map.of(
                        "error_code", "UNAUTHORIZED",
                        "message", "Credentials invalid or missing - Handled for: " + request.getMethod()
                ))
                .build();
    }

    @GET
    @Path("/unauthorized")
    @PermitAll
    @Produces(MediaType.APPLICATION_JSON)
    public Response handleGet(@Context HttpServletRequest request) {
        return getCommonResponse(request);
    }

    @POST
    @Path("/unauthorized")
    @PermitAll
    @Produces(MediaType.APPLICATION_JSON)
    public Response handlePost(@Context HttpServletRequest request) {
        return getCommonResponse(request);
    }

    @DELETE
    @Path("/unauthorized")
    @PermitAll
    @Produces(MediaType.APPLICATION_JSON)
    public Response handleDelete(@Context HttpServletRequest request) {
        return getCommonResponse(request);
    }

    @PUT
    @Path("/unauthorized")
    @PermitAll
    @Produces(MediaType.APPLICATION_JSON)
    public Response handlePut(@Context HttpServletRequest request) {
        return getCommonResponse(request);
    }
}
