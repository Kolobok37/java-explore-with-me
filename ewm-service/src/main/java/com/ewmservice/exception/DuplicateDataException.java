package com.ewmservice.exception;

public class DuplicateDataException extends ValidationDataException {
    public DuplicateDataException(String message) {
        super(message);
    }
}
