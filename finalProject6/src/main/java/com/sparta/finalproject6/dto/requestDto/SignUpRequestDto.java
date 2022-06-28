package com.sparta.finalproject6.dto.requestDto;

import com.sparta.finalproject6.repository.UserRepository;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.Email;

@Getter
@NoArgsConstructor
public class SignUpRequestDto {

    //이메일이어야 한다.
    @Email
    private String username;

    private String nickname;
    private String password;
    private String passwordCheck;

    public void signUpValidation(SignUpRequestDto dto){

    }
    public void pwEncoding(String password){
        this.password = password;
    }

}
