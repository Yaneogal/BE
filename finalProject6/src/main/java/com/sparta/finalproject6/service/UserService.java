package com.sparta.finalproject6.service;

import com.sparta.finalproject6.dto.requestDto.SignUpRequestDto;
import com.sparta.finalproject6.dto.requestDto.logInRequestDto;
import com.sparta.finalproject6.dto.responseDto.HttpResponse;
import com.sparta.finalproject6.handler.exception.CustomDuplicateException;
import com.sparta.finalproject6.handler.exception.CustomSignUpValidException;
import com.sparta.finalproject6.model.User;
import com.sparta.finalproject6.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final BCryptPasswordEncoder encoder;
    private final UserRepository userRepo;

    public HttpResponse signUp(SignUpRequestDto dto) throws CustomDuplicateException, CustomSignUpValidException{
        HttpResponse httpResponse = new HttpResponse(201, "회원가입 성공!", HttpStatus.CREATED, null);

        checkSignupdto(dto);

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
                status = HttpStatus.BAD_REQUEST;
                statusCode = HttpStatus.BAD_REQUEST.value();
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

        Optional<User> user = userRepo.findByUsername(username);
        if(user.isPresent()) {
            throw new CustomDuplicateException("이미 존재하는 아이디입니다!");
        }

        return "사용 가능한 아이디입니다!";
    }

    public String checkDupNick(String nickname){
//        HttpStatus status = HttpStatus.OK;
//        Integer statusCode = HttpStatus.OK.value();
//        String message = "사용 가능한 nickname입니다.";

        Optional<User> user = userRepo.findByNickname(nickname);
        if(user.isPresent()) {
            throw new CustomDuplicateException("이미 사용중인 닉네임입니다.");
        }
        return "사용 가능한 닉네임입니다!";
//        else{
//            if(!nickname.matches("^[a-zA-Z가-힣_\\d]{2,8}$")){
//                status = HttpStatus.BAD_REQUEST;
//                statusCode = HttpStatus.BAD_REQUEST.value();
//                message = "닉네임을 확인해주세요";
//            }
//        }
//
//        return HttpResponse.builder()
//                .status(status)
//                .statusCode(statusCode)
//                .message(message)
//                .build();
    }

    public void checkSignupdto(SignUpRequestDto dto) throws CustomDuplicateException, CustomSignUpValidException{

        if (!dto.getPassword().equals(dto.getPasswordCheck())) {
            throw new CustomSignUpValidException("비밀번호와 비밀번호 확인이 맞지 않습니다!");
        }

        Optional<User> user = userRepo.findByUsername(dto.getUsername());
        if (user.isPresent()) {
            throw new CustomDuplicateException("이미 존재하는 아이디입니다!");
        }

        user = userRepo.findByNickname(dto.getNickname());
        if (user.isPresent()) {
            throw new CustomDuplicateException("이미 존재하는 닉네임입니다");
        }



//        String message = "회원 가입에 성공하였습니다.";
//        HttpStatus status = HttpStatus.CREATED;
//        Integer statusCode = HttpStatus.CREATED.value();
//
//        HttpResponse httpResponse = new HttpResponse();
//
//        Optional<User> user = userRepo.findByUsername(dto.getUsername());
//
//        if(user.isPresent()){
//            status = HttpStatus.INTERNAL_SERVER_ERROR;
//            statusCode = HttpStatus.INTERNAL_SERVER_ERROR.value();
//            message = "이미 존재하는 이메일입니다.";
//        }
//
//
//        user = userRepo.findByNickname(dto.getNickname());
//        if(user.isPresent()){
//            status = HttpStatus.INTERNAL_SERVER_ERROR;
//            statusCode = HttpStatus.INTERNAL_SERVER_ERROR.value();
//            message = "이미 존재하는 닉네임입니다.";
//        }
//        //한영 언더바 숫자만 허용 2~8자
//        if(!dto.getNickname().matches("^[a-zA-Z가-힣_\\d]{2,8}$")){
//            status = HttpStatus.INTERNAL_SERVER_ERROR;
//            statusCode = HttpStatus.INTERNAL_SERVER_ERROR.value();
//            message = "닉네임을 확인해주세요";
//        }
//
//        //비밀번호 확인과 일치하는지 체크
//        if(!dto.getPassword().equals(dto.getPasswordCheck())){
//            status = HttpStatus.INTERNAL_SERVER_ERROR;
//            statusCode = HttpStatus.INTERNAL_SERVER_ERROR.value();
//            message = "비밀번호가 일치하지 않습니다.";
//        }
//        //비밀번호 조건 확인
//        //영어숫자
//        else if(!dto.getPassword().matches("^[a-zA-Z\\d]{8,16}$")){
//            status = HttpStatus.INTERNAL_SERVER_ERROR;
//            statusCode = HttpStatus.INTERNAL_SERVER_ERROR.value();
//            message = "비밀번호 형식을 확인해주세요";
//        }
//        httpResponse = HttpResponse.builder()
//                .status(status)
//                .statusCode(statusCode)
//                .message(message).build();
//        return httpResponse;
    }
}
