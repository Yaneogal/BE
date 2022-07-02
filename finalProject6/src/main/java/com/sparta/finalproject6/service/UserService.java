package com.sparta.finalproject6.service;

import com.sparta.finalproject6.dto.requestDto.SignUpRequestDto;
import com.sparta.finalproject6.dto.requestDto.logInRequestDto;
import com.sparta.finalproject6.dto.responseDto.HttpResponse;
import com.sparta.finalproject6.model.User;
import com.sparta.finalproject6.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final BCryptPasswordEncoder encoder;
    private final UserRepository userRepo;

    public HttpResponse signUp(SignUpRequestDto dto){
        HttpResponse httpResponse = new HttpResponse();

        httpResponse = checkSignupdto(dto);

        if(httpResponse.getStatusCode() == HttpStatus.CREATED.value()){
            dto.pwEncoding(encoder.encode(dto.getPassword()));
            User user = new User(dto);
            userRepo.save(user);
        }
        return httpResponse;
    }

    public HttpResponse logIn(logInRequestDto dto){
        String message = "로그인에 성공하였습니다.";
        HttpStatus status = HttpStatus.CREATED;
        Integer statusCode = HttpStatus.CREATED.value();

        HttpResponse httpResponse = new HttpResponse();
        Optional<User> user = userRepo.findByUsername(dto.getUsername());

        if(!user.isPresent()){
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            statusCode = HttpStatus.INTERNAL_SERVER_ERROR.value();
            message = "존재하지 않는 아이디입니다.";
        }
        else{
            if(!encoder.matches(dto.getPassword(),user.get().getPassword())){
                status = HttpStatus.INTERNAL_SERVER_ERROR;
                statusCode = HttpStatus.INTERNAL_SERVER_ERROR.value();
                message = "비밀번호가 일치하지 않습니다.";
            }
        }

        httpResponse = HttpResponse.builder()
                .status(status)
                .statusCode(statusCode)
                .message(message).build();
        return httpResponse;
    }

    public String checkDupName(String username){
        String result = "사용 가능한 ID입니다.";

        Optional<User> user = userRepo.findByUsername(username);
        if(user.isPresent()) {
            throw new IllegalArgumentException("이미 사용중인 ID입니다.");
        }
        else{

        }

        return result;
    }

    public String checkDupNick(String nickname){
        String result = "사용 가능한 nickname입니다.";

        Optional<User> user = userRepo.findByNickname(nickname);
        if(user.isPresent()) {
            throw new IllegalArgumentException("이미 사용중인 nickname입니다.");
        }
        else{

        }

        return result;
    }

    public HttpResponse checkSignupdto(SignUpRequestDto dto){
        String message = "회원 가입에 성공하였습니다.";
        HttpStatus status = HttpStatus.CREATED;
        Integer statusCode = HttpStatus.CREATED.value();

        HttpResponse httpResponse = new HttpResponse();

        Optional<User> user = userRepo.findByUsername(dto.getUsername());

        if(user.isPresent()){
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            statusCode = HttpStatus.INTERNAL_SERVER_ERROR.value();
            message = "이미 존재하는 이메일입니다.";
        }


        user = userRepo.findByNickname(dto.getNickname());
        if(user.isPresent()){
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            statusCode = HttpStatus.INTERNAL_SERVER_ERROR.value();
            message = "이미 존재하는 닉네임입니다.";

        }
        //한영 언더바 숫자만 허용 2~8자
        if(!dto.getNickname().matches("^[a-zA-Z가-힣_\\d]{2,8}$")){
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            statusCode = HttpStatus.INTERNAL_SERVER_ERROR.value();
            message = "닉네임을 확인해주세요";
        }

        //비밀번호 확인과 일치하는지 체크
        if(!dto.getPassword().equals(dto.getPasswordCheck())){
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            statusCode = HttpStatus.INTERNAL_SERVER_ERROR.value();
            message = "비밀번호가 일치하지 않습니다.";
        }
        //비밀번호 조건 확인
        //영어숫자
        else if(!dto.getPassword().matches("^[a-zA-Z\\d]{8,16}$")){
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            statusCode = HttpStatus.INTERNAL_SERVER_ERROR.value();
            message = "비밀번호 형식을 확인해주세요";
        }
        httpResponse = HttpResponse.builder()
                .status(status)
                .statusCode(statusCode)
                .message(message).build();
        return httpResponse;
    }
}
