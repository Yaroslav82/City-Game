package com.game.cities.presentation.exceptions.handler;

import com.game.cities.presentation.exceptions.ErrorDetails;
import com.game.cities.presentation.exceptions.SessionNotFoundException;
import com.game.cities.presentation.exceptions.WrongCityException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler(SessionNotFoundException.class)
    public final ResponseEntity<ErrorDetails> handleSessionNotFoundException(SessionNotFoundException e) {
        ErrorDetails details = new ErrorDetails(e.getMessage(), HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(details, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(WrongCityException.class)
    public final ResponseEntity<ErrorDetails> handleWrongCityException(WrongCityException e) {
        ErrorDetails details = new ErrorDetails(e.getMessage(), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(details, HttpStatus.BAD_REQUEST);
    }
}
