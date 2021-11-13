package com.CultureCup.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class GameAlreadyAddedToListException extends RuntimeException{

    public GameAlreadyAddedToListException(String message) {
        super(message);
    }
}
