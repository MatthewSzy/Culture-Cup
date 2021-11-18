package com.CultureCup.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class LikeAlreadyAddedException extends RuntimeException{

    public LikeAlreadyAddedException(String message) {
        super(message);
    }
}

