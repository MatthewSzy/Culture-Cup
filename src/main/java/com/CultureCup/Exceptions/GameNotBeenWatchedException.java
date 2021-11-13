package com.CultureCup.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class GameNotBeenWatchedException extends RuntimeException{

    public GameNotBeenWatchedException(String message) {
        super(message);
    }
}
