package com.sparta.finalproject6.controller;

import com.sparta.finalproject6.dto.requestDto.SignUpRequestDto;
import com.sparta.finalproject6.dto.requestDto.idCheckDto;
import com.sparta.finalproject6.dto.requestDto.logInRequestDto;
import com.sparta.finalproject6.dto.requestDto.nickCheckDto;
import com.sparta.finalproject6.dto.responseDto.HttpResponse;
import com.sparta.finalproject6.handler.exception.CustomSignUpValidException;
import com.sparta.finalproject6.security.JwtProvider;
import com.sparta.finalproject6.security.UserDetailsImpl;
import com.sparta.finalproject6.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;


@Slf4j
@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService service;
    private final JwtProvider provider;

    //회원가입
    @PostMapping("/api/user/signup")
    public ResponseEntity<HttpResponse> signUp(@RequestBody @Valid SignUpRequestDto dto, BindingResult bindingResult){
            HttpResponse httpResponse = new HttpResponse();
            httpResponse = service.signUp(dto);
            return new ResponseEntity<>(httpResponse,httpResponse.getStatus());
    }

    //로그인
    @PostMapping("/api/user/login")
    public ResponseEntity<HttpResponse> logIn(HttpServletResponse response, @RequestBody @Valid logInRequestDto dto){
        HttpResponse httpResponse = new HttpResponse();
        httpResponse = service.logIn(dto);

        if(httpResponse.getStatus().equals(HttpStatus.CREATED)){
            String token = provider.createToken(dto.getUsername());
            System.out.println(token);
//          httpResponse.setToken(token); //body에 토큰 실어서 전달
            response.addHeader("Authorization",token);
        }
        return new ResponseEntity<>(httpResponse,httpResponse.getStatus());
    }

    @PostMapping("/api/user/idCheck")
    public ResponseEntity<String> checkId(@RequestBody @Valid idCheckDto idCheckDto, BindingResult bindingResult){

        Map<String, String> errorMap = new HashMap<>();
        if (bindingResult.hasErrors()) {
            for (FieldError error : bindingResult.getFieldErrors()) {
                errorMap.put(error.getField(), error.getDefaultMessage());
            }
            throw new CustomSignUpValidException("아이디 유효성 검사 실패!", errorMap);
        }else {
            String result = service.checkDupName(idCheckDto.getUsername());

            return new ResponseEntity<>(result, HttpStatus.OK);
        }
    }

    @PostMapping("/api/user/nickCheck")
    public ResponseEntity<String> checkNick(@RequestBody @Valid nickCheckDto nickCheckDto ,BindingResult bindingResult) {

        Map<String, String> errorMap = new HashMap<>();
        if (bindingResult.hasErrors()) {
            for (FieldError error : bindingResult.getFieldErrors()) {
                errorMap.put(error.getField(), error.getDefaultMessage());
            }
            throw new CustomSignUpValidException("닉네임 유효성 검사 실패!", errorMap);
        } else {
            String result = service.checkDupNick(nickCheckDto.getNickname());

            return new ResponseEntity<>(result, HttpStatus.OK);
        }
    }

    @GetMapping("/user/test")
    public void test(@AuthenticationPrincipal UserDetailsImpl userDetails){
        System.out.println(userDetails.getUsername());
    }

}
