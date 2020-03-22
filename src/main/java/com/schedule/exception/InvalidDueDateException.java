package com.schedule.exception;

public class InvalidDueDateException extends CustomException {

    public InvalidDueDateException(String dueDate) {
        super(String.format("The due date %s is invalid, it must be a date in the future!", dueDate));
    }

}
