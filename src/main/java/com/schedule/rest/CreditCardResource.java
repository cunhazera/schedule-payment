package com.schedule.rest;

import com.schedule.dto.CreditCardDTO;
import com.schedule.exception.CreditCardAlreadyExistsException;
import com.schedule.service.CreditCardService;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/credit-cards")
public class CreditCardResource {

    @Inject
    private CreditCardService service;

    @POST
    @Produces(value = MediaType.APPLICATION_JSON)
    @Consumes(value = MediaType.APPLICATION_JSON)
    public CreditCardDTO addCreditCard(CreditCardDTO creditCard) throws CreditCardAlreadyExistsException {
        return service.addNewCreditCard(creditCard);
    }

    @GET
    @Path("/{card-number}")
    @Produces(value = MediaType.APPLICATION_JSON)
    public CreditCardDTO findCreditCardByNumber(@PathParam("card-number") Integer cardNumber) {
        return service.findCardById(cardNumber);
    }

    @DELETE
    @Path("/{id}")
    public void removeCreditCard(@PathParam("id") Integer id) {
        service.remove(id);
    }

}
