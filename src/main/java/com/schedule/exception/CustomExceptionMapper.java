package com.schedule.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import static javax.ws.rs.core.Response.Status.BAD_REQUEST;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;

@Provider
public class CustomExceptionMapper implements ExceptionMapper<CustomException> {

    @Override
    public Response toResponse(CustomException e) {
        return Response.status(e instanceof NoCreditCardException || e instanceof NoPaymentException ? NOT_FOUND : BAD_REQUEST).entity(e.getMessage()).build();
    }

}
