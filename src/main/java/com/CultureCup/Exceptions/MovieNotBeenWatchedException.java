package com.CultureCup.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class MovieNotBeenWatchedException extends RuntimeException{

    public MovieNotBeenWatchedException(String message) {
        super(message);
    }
}