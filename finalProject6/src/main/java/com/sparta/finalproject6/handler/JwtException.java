package com.sparta.finalproject6.handler;

import com.sparta.finalproject6.handler.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class JwtException extends RuntimeException{
    private final ErrorCode errorCode;
}
