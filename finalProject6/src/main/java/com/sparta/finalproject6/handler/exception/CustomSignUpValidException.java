package com.sparta.finalproject6.handler.exception;

import lombok.Getter;

import java.util.Map;

@Getter
public class CustomSignUpValidException extends IllegalArgumentException {

    private static final long serialVersionUID = 1L;
    private Map<String, String> errorMap;

    public CustomSignUpValidException(String messeage) {
        super(messeage);
    }

    public CustomSignUpValidException(String message, Map<String, String> errorMap) {
        super(message);
        this.errorMap = errorMap;;
    }
}
