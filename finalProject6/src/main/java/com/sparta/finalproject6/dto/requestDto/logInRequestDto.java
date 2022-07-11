package com.sparta.finalproject6.dto.requestDto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
public class logInRequestDto {
    private String username;
    private String password;
}

