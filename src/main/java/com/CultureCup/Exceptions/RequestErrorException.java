package com.CultureCup.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.REQUEST_TIMEOUT)
public class RequestErrorException extends RuntimeException {
    public RequestErrorException(String message) {
        super(message);
    }
}

