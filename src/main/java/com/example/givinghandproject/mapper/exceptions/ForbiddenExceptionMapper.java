package com.example.givinghandproject.mapper.exceptions;

import jakarta.ws.rs.ForbiddenException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import java.util.Map;

@Provider
public class ForbiddenExceptionMapper implements ExceptionMapper<ForbiddenException> {
    @Override
    public Response toResponse(ForbiddenException exception)
    {
        return Response.status(Response.Status.FORBIDDEN).entity(Map.of(
                "ACCESS_DENIED" ,"This is outside your access rights"))
                .build();
    }
}
