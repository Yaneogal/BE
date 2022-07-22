package com.sparta.finalproject6.dto.requestDto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
public class idCheckDto {

    @Email(message = "이메일 형식을 확인해주세요!")
    @NotBlank(message = "아이디를 입력해주세요!")
    private String username;
}
