package com.sparta.finalproject6.dto.requestDto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@NoArgsConstructor
public class SignUpRequestDto {

    //이메일이어야 한다.
    @Pattern(regexp = "^[a-zA-Z0-9]+@[a-zA-Z0-9]+\\.[a-z]+$", message = "이메일 형식을 확인해주세요!")
    @NotBlank(message = "이메일을 입력해주세요!")
    private String username;

    @NotBlank(message = "닉네임을 입력해주세요!")
    @Pattern(regexp = "^[a-zA-Z가-힣_\\d]{2,8}$", message = "닉네임 형식을 확인해주세요!")
    private String nickname;
    @NotBlank(message = "비밀번호를 입력해주세요!")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[$@$!%*#?&])[A-Za-z[0-9]$@$!%*#?&]{8,16}$", message = "비밀번호의 형식을 확인해주세요!")
    private String password;

    @NotBlank(message = "비밀번호 확인을 입력해주세요!")
    private String passwordCheck;

    public void pwEncoding(String password){
        this.password = password;
    }

}
