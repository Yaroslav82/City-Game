package com.game.cities.presentation.exceptions;

public class SessionNotFoundException extends RuntimeException {

    private final static String DEFAULT_MESSAGE = "Такої сессії не існує";

    public SessionNotFoundException() {
        super(DEFAULT_MESSAGE);
    }
}
