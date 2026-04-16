package com.example.givinghandproject.api;

import com.example.givinghandproject.dto.User.UserLoginRequest;
import com.example.givinghandproject.dto.User.UserRegisterRequest;
import com.example.givinghandproject.dto.User.UserResponse;
import com.example.givinghandproject.dto.User.UserUpdateRequest;
import com.example.givinghandproject.entity.User;
import com.example.givinghandproject.mapper.UserMapper;
import com.example.givinghandproject.service.UserService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.Map;

@Path("/user")
public class UserResource {

    @Inject
    UserService userService;

    @POST
    @Path("/register")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    //
    public Response register(@Valid UserRegisterRequest request)
    {
        User user = userService.register(request);
        UserResponse response = UserMapper.fromUser(user);
        return Response.status(Response.Status.CREATED).entity(response).build();
    }

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(@Valid UserLoginRequest request)
    {
        String userName = userService.login(request);
        return Response.status(200).entity(Map.of("message" , "Welcome "+userName)).build();
    }

    @PUT
    @Path("/update-profile/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response UpdateProfile(@PathParam("id") Long id, @Valid UserUpdateRequest request)
    {
        Long userId = userService.update(id , request);
        return Response.status(200).entity(Map.of("message" , "User with id "+userId+" updated successfully")).build();
    }
}
