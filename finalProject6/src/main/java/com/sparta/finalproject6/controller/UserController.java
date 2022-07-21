package com.sparta.finalproject6.controller;

import com.sparta.finalproject6.dto.requestDto.SignUpRequestDto;
import com.sparta.finalproject6.dto.requestDto.idCheckDto;
import com.sparta.finalproject6.dto.requestDto.logInRequestDto;
import com.sparta.finalproject6.dto.requestDto.nickCheckDto;
import com.sparta.finalproject6.dto.responseDto.HttpResponse;
import com.sparta.finalproject6.security.JwtProvider;
import com.sparta.finalproject6.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;


@Slf4j
@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService service;
    private final JwtProvider provider;

    //회원가입
    @PostMapping("/api/user/signup")
    public ResponseEntity<HttpResponse> signUp(@RequestBody @Valid SignUpRequestDto dto){
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
    public ResponseEntity<String> checkId(@RequestBody @Valid idCheckDto idCheckDto){

            String result = service.checkDupName(idCheckDto.getUsername());
            return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/api/user/nickCheck")
    public ResponseEntity<String> checkNick(@RequestBody @Valid nickCheckDto nickCheckDto) {

        String result = service.checkDupNick(nickCheckDto.getNickname());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
