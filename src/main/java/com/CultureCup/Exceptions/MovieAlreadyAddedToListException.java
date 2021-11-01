package com.CultureCup.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class MovieAlreadyAddedToListException extends RuntimeException{

    public MovieAlreadyAddedToListException(String message) {
        super(message);
    }
}
