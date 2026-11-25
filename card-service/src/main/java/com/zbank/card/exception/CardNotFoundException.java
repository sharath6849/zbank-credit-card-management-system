package com.zbank.card.exception;

public class CardNotFoundException
        extends RuntimeException {

    public CardNotFoundException(
            String message) {

        super(message);
    }
}