package com.ewmservice.exception;

public class RequestException extends RuntimeException {
    public RequestException(String message) {
        super(message);
    }
}