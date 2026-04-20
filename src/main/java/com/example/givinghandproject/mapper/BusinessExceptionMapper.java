package com.example.givinghandproject.mapper;

import com.example.givinghandproject.dto.ErrorDTO;
import com.example.givinghandproject.utilities.exceptions.BusinessException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.util.Objects;

@Provider
public class BusinessExceptionMapper implements ExceptionMapper<BusinessException> {
    @Override
    public Response toResponse(BusinessException exception)
    {
        ErrorDTO error = new ErrorDTO(exception.getField() , exception.getMessage());
        if(Objects.equals(exception.getField(), "credentials")) // handling the login function permission
            return Response.status(Response.Status.UNAUTHORIZED).entity(error).build();
        return Response.status(400).entity(error).build();
    }
}
