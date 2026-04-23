//package com.example.givinghandproject.mapper.exceptions;
//
//import jakarta.ws.rs.NotAuthorizedException;
//import jakarta.ws.rs.core.Response;
//import jakarta.ws.rs.ext.ExceptionMapper;
//import jakarta.ws.rs.ext.Provider;
//
//import java.util.Map;
//
//@Provider
//public class UnAuthorizedExceptionMapper implements ExceptionMapper<NotAuthorizedException> {
//    @Override
//    public Response toResponse(NotAuthorizedException authenticationException)
//    {
//        return Response.status(Response.Status.UNAUTHORIZED).entity(
//                Map.of("UNAUTHORIZED" , "Session expired or invalid credentials, please login again")).build();
//    }
//}
/*
 This is useless because:

Container-Level Enforcement: JBoss (WildFly/Elytron) processes Basic Authentication at the Servlet Container level before the request ever reaches the JAX-RS (Jakarta REST) engine.

JAX-RS Bypass: Since the authentication failure happens at the "gate," the server generates a 401 Unauthorized response directly. No NotAuthorizedException is actually thrown within your application code for the ExceptionMapper to catch.

Error-Page Precedence: By defining an `<error-page>for code **401** inweb.xml`, you have instructed the server to forward all authentication failures to a specific URI. This mechanism completely bypasses the JAX-RS exception-handling chain.

Dead Code: The toResponse method in this provider will never be triggered ("Dead Code") during a failed login attempt, as the server's internal security mechanism handles the response generation entirely.

*/

