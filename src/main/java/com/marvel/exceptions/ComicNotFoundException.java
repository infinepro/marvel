package com.marvel.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ComicNotFoundException extends RuntimeException {

    public ComicNotFoundException() {
    }

    public ComicNotFoundException(String message) {
        super(message);
    }

    public ComicNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ComicNotFoundException(Throwable cause) {
        super(cause);
    }

    public ComicNotFoundException(String message,
                                  Throwable cause,
                                  boolean enableSuppression,
                                  boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
