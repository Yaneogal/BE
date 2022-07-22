package com.sparta.finalproject6.handler.exception;

public class WrongPasswordException extends IllegalArgumentException{

    private static final long serialVersionUID = 1L;

    public WrongPasswordException(String message) {
        super(message);
    }
}
