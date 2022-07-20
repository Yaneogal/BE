package com.sparta.finalproject6.handler.exception;

import java.util.Map;

public class CustomDuplicateException extends IllegalArgumentException{

    private static final long serialVersionUID = 1L;
    private Map<String, String> errorMap;

    public CustomDuplicateException(String messeage) {
        super(messeage);
    }

    public CustomDuplicateException(String message, Map<String, String> errorMap) {
        super(message);
        this.errorMap = errorMap;;
    }
}
