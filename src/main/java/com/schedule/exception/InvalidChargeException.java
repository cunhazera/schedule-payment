package com.schedule.exception;

public class InvalidChargeException extends CustomException {

    public InvalidChargeException(long paymentId) {
        super(String.format("A payment can not be charged two times! Transaction id: %d", paymentId));
    }

}
