package com.example.givinghandproject.mapper;

import com.example.givinghandproject.dto.ErrorDTO;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class ValidationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {
    @Override
    public Response toResponse(ConstraintViolationException exception) {

        ConstraintViolation<?> violation = exception.getConstraintViolations().iterator().next();
        String field = violation.getPropertyPath().toString().trim().split("\\.")[2];
        String message = violation.getMessage();

        ErrorDTO error = new ErrorDTO(field , message);

        return Response.status(400).entity(error).build();
    }
}