package com.schedule.exception;

public class InvalidEmissionDateException extends CustomException {

    public InvalidEmissionDateException() {
        super("The emission date can't be a date in the future!");
    }

}
