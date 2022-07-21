package com.sparta.finalproject6.handler;

import com.sparta.finalproject6.dto.responseDto.ErrorResponse;
import com.sparta.finalproject6.handler.exception.SignUpValidException;
import com.sparta.finalproject6.handler.exception.UserDuplicateException;
import com.sparta.finalproject6.handler.exception.UserIdNotFoundException;
import com.sparta.finalproject6.handler.exception.WrongPasswordException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RestController
@RestControllerAdvice
public class ControllerExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    public ErrorResponse validaionException(
            MethodArgumentNotValidException e, HttpServletRequest request) {

        e.printStackTrace();
        String path = request.getRequestURI();
        return ErrorResponse.of(path, e.getBindingResult());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    public ErrorResponse UserIdNotFoundException(
            UserIdNotFoundException e, HttpServletRequest request) {

        e.printStackTrace();
        String path = request.getRequestURI();
        return new ErrorResponse(e.getMessage(), path);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    public ErrorResponse SignUpValidException(
            SignUpValidException e, HttpServletRequest request) {

        e.printStackTrace();
        String path = request.getRequestURI();
        return new ErrorResponse(e.getMessage(), path);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    public ErrorResponse WrongPasswordException(
            WrongPasswordException e, HttpServletRequest request) {

        e.printStackTrace();
        String path = request.getRequestURI();
        return new ErrorResponse(e.getMessage(), path);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler
    public ErrorResponse UserDuplicateException(
            UserDuplicateException e, HttpServletRequest request) {

        e.printStackTrace();
        String path = request.getRequestURI();
        return new ErrorResponse(e.getMessage(), path);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler
    public ErrorResponse nonExpectedException(Exception e) {

        e.printStackTrace();
        return new ErrorResponse("예상치 못한 문제가 발생되었습니다.");
    }

}
