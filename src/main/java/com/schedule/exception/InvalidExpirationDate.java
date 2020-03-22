package com.schedule.exception;

public class InvalidExpirationDate extends CustomException {

    public InvalidExpirationDate() {
        super("The card expiration date can't be before the emission date!");
    }

}
