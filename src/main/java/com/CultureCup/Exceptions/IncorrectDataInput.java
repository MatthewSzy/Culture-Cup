package com.CultureCup.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.EXPECTATION_FAILED)
public class IncorrectDataInput extends RuntimeException {

    public IncorrectDataInput(String message) {
        super(message);
    }
}
