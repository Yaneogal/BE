package com.sparta.finalproject6.handler.exception;

public class UserDuplicateException extends IllegalArgumentException {

    private static final long serialVersionUID = 1L;

    public UserDuplicateException(String message) {
        super(message);
    }
}
