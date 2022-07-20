package com.sparta.finalproject6.dto.requestDto;


import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@NoArgsConstructor
public class nickCheckDto {

    @NotBlank(message = "닉네임을 입력해주세요!")
    @Pattern(regexp = "^[a-zA-Z가-힣_\\d]{2,8}$", message = "닉네임 형식을 확인해주세요!")
    private String nickname;
}
