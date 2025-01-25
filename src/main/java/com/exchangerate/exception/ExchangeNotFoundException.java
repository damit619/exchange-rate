package com.exchangerate.exception;

public class ExchangeNotFoundException extends RuntimeException {
    public ExchangeNotFoundException(String message) {
        super(message);
    }
}
