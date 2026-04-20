package com.example.givinghandproject.api;


import com.example.givinghandproject.dto.User.UserRegisterRequest;
import com.example.givinghandproject.dto.User.UserResponse;
import com.example.givinghandproject.dto.User.UserUpdateRequest;
import com.example.givinghandproject.entity.User;
import com.example.givinghandproject.mapper.UserMapper;
import com.example.givinghandproject.service.UserService;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;

import java.util.Map;

@Path("/user")
@RequestScoped
@RolesAllowed({"Organization", "Donor"})
public class UserResource {

    @Inject
    UserService userService;

    @POST
    @PermitAll
    @Path("/register")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response register(@Valid UserRegisterRequest request) {
        User user = userService.register(request);
        UserResponse response = UserMapper.fromUser(user);
        return Response.status(Response.Status.CREATED).entity(response).build();
    }

    @POST
    @Path("/login")
    @RolesAllowed({"Organization", "Donor"})
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(@Context SecurityContext securityContext) {
        String email = securityContext.getUserPrincipal().getName(); // catch the mail
        String name = userService.login(email);

        return Response.status(200).entity(Map.of("message", "Welcome " + name)).build();
    }

    @PUT
    @Path("/update-profile")
    @RolesAllowed({"Organization", "Donor"})
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateProfile(@Context SecurityContext securityContext, @Valid UserUpdateRequest request) {

        String email = securityContext.getUserPrincipal().getName();

        return Response.ok(Map.of("message", "Your profile has been updated successfully")).build();
    }
}