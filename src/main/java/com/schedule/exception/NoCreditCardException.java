package com.schedule.exception;

public class NoCreditCardException extends CustomException {

    public NoCreditCardException(long cardNumber) {
        super(String.format("No credit card with number: %d", cardNumber));
    }

}
