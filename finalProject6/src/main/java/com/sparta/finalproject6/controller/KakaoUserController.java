package com.sparta.finalproject6.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sparta.finalproject6.dto.responseDto.HttpResponse;
import com.sparta.finalproject6.model.User;
import com.sparta.finalproject6.security.JwtProvider;
import com.sparta.finalproject6.service.KakaoUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@Slf4j
@RequiredArgsConstructor
@RestController
public class KakaoUserController {
    private final KakaoUserService kakaoUserService;
    private final JwtProvider jwtProvider;

    @GetMapping("/api/user/kakaoLogin/callback")
    public ResponseEntity<HttpResponse> kakaoLogin(HttpServletResponse response, @RequestParam String code) throws JsonProcessingException {
        System.out.println("인가 코드: " + code);
        User kakaoUser = kakaoUserService.kakaoLogin(code);
        String token = jwtProvider.createToken(kakaoUser.getUsername());
        HttpResponse httpResponse = HttpResponse.builder()
                .message("카카오로그인 성공!")
                .statusCode(HttpStatus.CREATED.value())
                .status(HttpStatus.CREATED)
                .KakaoId(kakaoUser.getKakaoId())
                .build();

        response.addHeader("Authorization", token);
        return new ResponseEntity<>(httpResponse,httpResponse.getStatus());
    }
}
