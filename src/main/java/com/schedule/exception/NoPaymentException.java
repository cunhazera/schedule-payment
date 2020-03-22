package com.schedule.exception;

public class NoPaymentException extends CustomException {

    public NoPaymentException(Integer id) {
        super(String.format("There is no payment with id %d", id));
    }

}
