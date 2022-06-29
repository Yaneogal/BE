package com.sparta.finalproject6.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sparta.finalproject6.dto.responseDto.HttpResponse;
import com.sparta.finalproject6.model.User;
import com.sparta.finalproject6.security.JwtProvider;
import com.sparta.finalproject6.service.KakaoUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
@RestController
public class KakaoUserController {
    private final KakaoUserService kakaoUserService;
    private final JwtProvider jwtProvider;

    @GetMapping("/api/user/kakaoLogin/callback")
    public ResponseEntity<HttpResponse> kakaoLogin(HttpServletResponse reponse, @RequestParam String code) throws JsonProcessingException {
        HttpResponse httpResponse = new HttpResponse();
        User kakaoUser = kakaoUserService.kakaoLogin(code);
        reponse.addHeader("Authorization", jwtProvider.createToken(kakaoUser.getUsername()));
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
