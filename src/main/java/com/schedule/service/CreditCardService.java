package com.schedule.service;

import com.schedule.dto.CreditCardDTO;
import com.schedule.entity.CreditCard;
import com.schedule.exception.CreditCardAlreadyExistsException;
import com.schedule.exception.InvalidCardNumbers;
import com.schedule.exception.InvalidEmissionDateException;
import com.schedule.exception.InvalidExpirationDate;
import com.schedule.exception.NoCreditCardException;
import com.schedule.repository.CreditCardRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.time.LocalDate;

@ApplicationScoped
public class CreditCardService {

    @Inject
    private CreditCardRepository repository;

    @Transactional
    public CreditCardDTO addNewCreditCard(CreditCardDTO creditCard) {
        CreditCard card = new CreditCard();
        card.cardNumber = creditCard.getCardNumber();
        card.ownerName = creditCard.getOwnerName();
        card.emissionDate = LocalDate.parse(creditCard.getEmissionDate());
        card.validThru = LocalDate.parse(creditCard.getValidThru());
        card.securityCode = creditCard.getSecurityCode();
        validateCardData(card);

        if (repository.findCreditCardByCardNumber(card.cardNumber).isPresent()) {
            throw new CreditCardAlreadyExistsException(card.cardNumber);
        }
        repository.persistAndFlush(card);
        return creditCard;
    }

    @Transactional
    public void remove(Integer id) {
        repository.delete(repository.findById(id));
    }

    public CreditCardDTO findCardById(Integer cardNumber) {
        CreditCard card = repository.findCreditCardByCardNumber(cardNumber).orElseThrow(() -> new NoCreditCardException(cardNumber));
        CreditCardDTO creditCard = new CreditCardDTO();
        creditCard.setValidThru(card.validThru.toString());
        creditCard.setEmissionDate(card.emissionDate.toString());
        creditCard.setOwnerName(card.ownerName);
        creditCard.setSecurityCode(card.securityCode);
        creditCard.setCardNumber(card.cardNumber);
        return creditCard;
    }

    private void validateCardData(CreditCard card) {
        if (card.emissionDate.isAfter(LocalDate.now())) {
            throw new InvalidEmissionDateException();
        }
        if (card.validThru.isBefore(card.emissionDate)) {
            throw new InvalidExpirationDate();
        }
        if (String.valueOf(card.cardNumber).length() != 8 || String.valueOf(card.securityCode).length() != 3) {
            throw new InvalidCardNumbers(card.cardNumber, card.securityCode);
        }
    }

}
