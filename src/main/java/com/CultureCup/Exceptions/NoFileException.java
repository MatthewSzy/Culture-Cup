package com.CultureCup.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
public class NoFileException extends RuntimeException {

    public NoFileException(String message) {
        super(message);
    }
}
