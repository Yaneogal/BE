package com.sparta.finalproject6.handler.aop;

import com.sparta.finalproject6.handler.exception.CustomSignUpValidException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;

@Component
@Aspect
public class ValidationAdvice {

    @Around("execution(* com.sparta.finalproject6.*controller.*(..))")
    public Object Advice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        Object[] args = proceedingJoinPoint.getArgs();
        for (Object arg : args) {
            if (arg instanceof BindingResult) {
                BindingResult bindingResult = (BindingResult) arg;
                Map<String, String> errorMap = new HashMap<>();

                if (bindingResult.hasErrors()) {
                    for (FieldError error : bindingResult.getFieldErrors()) {
                        errorMap.put(error.getField(), error.getDefaultMessage());
                    }
                    throw new CustomSignUpValidException("유효성 검사 실패!", errorMap);
                }
            }
        }

        return proceedingJoinPoint.proceed();
    }
}
