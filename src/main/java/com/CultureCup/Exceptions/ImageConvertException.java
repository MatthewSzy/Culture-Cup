package com.CultureCup.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class ImageConvertException extends RuntimeException {
    public ImageConvertException(String message) {
        super(message);
    }
}
