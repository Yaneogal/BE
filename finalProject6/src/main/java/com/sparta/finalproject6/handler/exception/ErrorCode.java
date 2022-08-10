package com.sparta.finalproject6.handler.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED,"만료된 토큰입니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
