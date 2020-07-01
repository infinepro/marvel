package com.marvel.exceptions;

public class NotValidCharacterParametersException extends RuntimeException{
    public NotValidCharacterParametersException() {
        super();
    }

    public NotValidCharacterParametersException(String s) {
        super(s);
    }

    public NotValidCharacterParametersException(String s, Throwable throwable) {
        super(s, throwable);
    }
}
