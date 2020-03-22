package com.schedule.repository;

import com.schedule.entity.CreditCard;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

import javax.enterprise.context.ApplicationScoped;
import java.util.Optional;

@ApplicationScoped
public class CreditCardRepository implements PanacheRepositoryBase<CreditCard, Integer> {

    public Optional<CreditCard> findCreditCardByCardNumber(Integer id) {
        return find("from CreditCard where cardNumber = ?1", id).singleResultOptional();
    }

}
