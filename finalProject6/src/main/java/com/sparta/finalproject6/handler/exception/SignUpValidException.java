package com.sparta.finalproject6.handler.exception;

public class SignUpValidException extends IllegalArgumentException{

    private static final long serialVersionUID = 1L;

    public SignUpValidException(String message) {
        super(message);
    }
}
