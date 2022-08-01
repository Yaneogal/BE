package com.sparta.finalproject6.service;

import com.sparta.finalproject6.dto.requestDto.SignUpRequestDto;
import com.sparta.finalproject6.dto.requestDto.logInRequestDto;
import com.sparta.finalproject6.dto.responseDto.HttpResponse;
import com.sparta.finalproject6.handler.exception.SignUpValidException;
import com.sparta.finalproject6.handler.exception.UserDuplicateException;
import com.sparta.finalproject6.handler.exception.UserIdNotFoundException;
import com.sparta.finalproject6.handler.exception.WrongPasswordException;
import com.sparta.finalproject6.model.User;
import com.sparta.finalproject6.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final BCryptPasswordEncoder encoder;
    private final UserRepository userRepo;

    public HttpResponse signUp(SignUpRequestDto dto) {
        HttpResponse httpResponse = new HttpResponse(201, "회원가입 성공!", HttpStatus.CREATED, null);

        checkSignupdto(dto);

        if(httpResponse.getStatusCode() == HttpStatus.CREATED.value()){
            dto.pwEncoding(encoder.encode(dto.getPassword()));
            User user = new User(dto);
            userRepo.save(user);
        }
        return httpResponse;
    }

    public HttpResponse logIn(logInRequestDto dto) {
        String message = "로그인에 성공하였습니다.";
        HttpStatus status = HttpStatus.CREATED;
        Integer statusCode = HttpStatus.CREATED.value();

        checkLogin(dto);


        return HttpResponse.builder()
                .status(status)
                .statusCode(statusCode)
                .message(message).build();
    }

    public void checkLogin(logInRequestDto requestDto) {

        User user = userRepo.findByUsername(requestDto.getUsername())
                .orElseThrow(() -> new UserIdNotFoundException("존재하지 않는 아이디입니다!"));
        if (!encoder.matches(requestDto.getPassword(), user.getPassword())) {
            throw new WrongPasswordException("비밀번호를 다시 확인해주세요!");
        }

    }

    public String checkDupName(String username){

        Optional<User> user = userRepo.findByUsername(username);
        if(user.isPresent()) {
            throw new UserDuplicateException("이미 존재하는 아이디입니다!");
        }

        return "사용 가능한 아이디입니다!";
    }

    public String checkDupNick(String nickname){

        Optional<User> user = userRepo.findByNickname(nickname);
        if(user.isPresent()) {
            throw new UserDuplicateException("이미 존재하는 닉네임입니다!");
        }
        return "사용 가능한 닉네임입니다!";
    }

    public void checkSignupdto(SignUpRequestDto dto) {

        if (!dto.getPassword().equals(dto.getPasswordCheck())) {
            throw new SignUpValidException("비밀번호와 비밀번호 확인이 맞지 않습니다!");
        }

        Optional<User> user = userRepo.findByUsername(dto.getUsername());
        if (user.isPresent()) {
            throw new UserDuplicateException("이미 존재하는 아이디입니다!");
        }

        user = userRepo.findByNickname(dto.getNickname());
        if (user.isPresent()) {
            throw new UserDuplicateException("이미 존재하는 닉네임입니다!");
        }
    }
}
