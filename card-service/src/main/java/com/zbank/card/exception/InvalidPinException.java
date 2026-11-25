package com.zbank.card.exception;

public class InvalidPinException
        extends RuntimeException {

    public InvalidPinException(
            String message) {

        super(message);
    }
}