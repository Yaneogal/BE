package com.sparta.finalproject6.handler.exception;

import lombok.Getter;

@Getter
public class UserIdNotFoundException extends IllegalArgumentException{

    private static final long serialVersionUID = 1L;

    public UserIdNotFoundException(String message) {
        super(message);
    }
}
