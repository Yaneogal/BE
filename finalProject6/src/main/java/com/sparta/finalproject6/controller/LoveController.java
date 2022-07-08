package com.sparta.finalproject6.controller;


import com.sparta.finalproject6.security.UserDetailsImpl;
import com.sparta.finalproject6.service.LoveService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LoveController {

    private final LoveService loveService;


    @PostMapping("/api/love/{postId}")
    public ResponseEntity<String> like(@PathVariable Long postId , @AuthenticationPrincipal UserDetailsImpl userDetails){
            loveService.love(userDetails,postId);
            return new ResponseEntity<>(HttpStatus.OK);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleException(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<String> usernameNotFoundException(UsernameNotFoundException ex){
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(ex.getMessage());
    }
}

