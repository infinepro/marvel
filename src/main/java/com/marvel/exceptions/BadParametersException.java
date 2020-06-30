package com.marvel.exceptions;

public class BadParametersException extends RuntimeException{

    public BadParametersException() {
    }

    public BadParametersException(String s) {
        super(s);
    }
}
