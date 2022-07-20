package com.sparta.finalproject6.handler;

import com.sparta.finalproject6.dto.responseDto.CMResponseDto;
import com.sparta.finalproject6.handler.exception.CustomDuplicateException;
import com.sparta.finalproject6.handler.exception.CustomSignUpValidException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestController
@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(CustomSignUpValidException.class)
    public ResponseEntity<CMResponseDto> validationException(CustomSignUpValidException e) {
        e.printStackTrace();
        return new ResponseEntity<>(new CMResponseDto(e.getMessage(), e.getErrorMap()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CustomDuplicateException.class)
    public ResponseEntity<CMResponseDto> duplicateException(CustomDuplicateException e) {
        e.printStackTrace();
        return new ResponseEntity<>(new CMResponseDto(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<CMResponseDto> nonExpectedException(Exception e) {
        e.printStackTrace();
        return new ResponseEntity<>(new CMResponseDto("예상치 못한 문제가 발생되었습니다."), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
