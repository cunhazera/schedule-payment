package com.schedule.exception;

public class CreditCardAlreadyExistsException extends CustomException {

    public CreditCardAlreadyExistsException(long cardNumber) {
        super(String.format("Credit card with number %d already exists!", cardNumber));
    }

}
