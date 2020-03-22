package com.schedule.repository;

import com.schedule.entity.Payment;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PaymentRepository implements PanacheRepositoryBase<Payment, Integer> {
}
