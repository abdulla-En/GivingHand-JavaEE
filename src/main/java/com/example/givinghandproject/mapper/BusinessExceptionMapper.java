package com.example.givinghandproject.mapper;

import com.example.givinghandproject.dto.ErrorDTO;
import com.example.givinghandproject.utilities.exceptions.BusinessException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class BusinessExceptionMapper implements ExceptionMapper<BusinessException> {
    @Override
    public Response toResponse(BusinessException exception)
    {
        ErrorDTO error = new ErrorDTO(exception.getField() , exception.getMessage());
        return Response.status(400).entity(error).build();
    }
}
