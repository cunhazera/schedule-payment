package com.schedule.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
public class CreditCard extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id;
    @Column(unique = true)
    public int cardNumber;
    @Column(unique = true)
    public int securityCode;
    @Column(unique = true)
    public String ownerName;
    public LocalDate emissionDate;
    public LocalDate validThru;

}
