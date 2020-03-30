package com.schedule.service;

import com.schedule.dto.CreditCardDTO;
import com.schedule.entity.CreditCard;
import com.schedule.exception.CreditCardAlreadyExistsException;
import com.schedule.repository.CreditCardRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CreditCardTest {

    @Mock
    private CreditCardRepository creditCardRepository;

    @InjectMocks
    private CreditCardService creditCardService;

    @BeforeAll
    public void before() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreditCardAlreadyExistsException() {
        Mockito.when(creditCardRepository.findCreditCardByCardNumber(Mockito.anyInt())).thenReturn(Optional.of(new CreditCard()));
        CreditCardDTO creditCard = new CreditCardDTO();
	    creditCard.setCardNumber(12341234);
	    creditCard.setSecurityCode(123);
        creditCard.setEmissionDate("2012-12-12");
        creditCard.setValidThru("2020-12-12");
        Assertions.assertThrows(CreditCardAlreadyExistsException.class, () -> creditCardService.addNewCreditCard(creditCard));
    }

}
