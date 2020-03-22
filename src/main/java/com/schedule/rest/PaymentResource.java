package com.schedule.rest;

import com.schedule.dto.PaymentDTO;
import com.schedule.entity.Payment;
import com.schedule.exception.InvalidDueDateException;
import com.schedule.exception.NoPaymentException;
import com.schedule.service.PaymentService;
import org.quartz.SchedulerException;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/payments")
public class PaymentResource {

    @Inject
    private PaymentService service;

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Payment findPaymentById(@PathParam("id") Integer id) throws NoPaymentException {
        return service.findPayment(id);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Payment newPayment(PaymentDTO payment) throws InvalidDueDateException, SchedulerException {
        return service.scheduleNewPayment(payment);
    }
}
