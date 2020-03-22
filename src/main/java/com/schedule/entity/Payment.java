package com.schedule.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Entity
public class Payment extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id;
    @Column(unique = true)
    public String productId;
    public String description;
    public LocalDateTime dueDate;
    public String customerMail;
    public int amount;
    @Enumerated(value = EnumType.STRING)
    public Currency currency;
    @Enumerated(value = EnumType.STRING)
    public Status status;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, targetEntity = CreditCard.class)
    public CreditCard creditCard;
}
