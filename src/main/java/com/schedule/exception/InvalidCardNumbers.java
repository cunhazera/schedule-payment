package com.schedule.exception;

public class InvalidCardNumbers extends CustomException {

    public InvalidCardNumbers(int cardNumber, int securityCode) {
        super(String.format("Invalid card number and/or security code.\nCardNumber: %d\nSecurity code: %d", cardNumber, securityCode));
    }

}
