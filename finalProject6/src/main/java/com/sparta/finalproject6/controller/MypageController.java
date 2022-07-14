package com.sparta.finalproject6.controller;

import com.sparta.finalproject6.dto.requestDto.ProfileUpdateRequestDto;
import com.sparta.finalproject6.dto.responseDto.MyBookmarkListDto;
import com.sparta.finalproject6.dto.responseDto.MypageResponseDto;
import com.sparta.finalproject6.dto.responseDto.ProfileUpdateResponseDto;
import com.sparta.finalproject6.model.User;
import com.sparta.finalproject6.security.UserDetailsImpl;
import com.sparta.finalproject6.service.MypageService;
import com.sparta.finalproject6.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class MypageController {

    private final MypageService mypageService;
    private final UserService userService;

    // My Page 회원정보 조회 API
    @GetMapping("/api/user")
    public ProfileUpdateResponseDto getMyProfile (
            @AuthenticationPrincipal UserDetailsImpl userDetails) {

        return mypageService.getMyProfile(userDetails);
    }

    // 마이페이지 회원정보 수정
    @PutMapping("/api/user")
    public ProfileUpdateResponseDto updateProfile (
            @RequestPart MultipartFile multipartFile,
            @RequestPart ProfileUpdateRequestDto updateRequestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails)

        throws IOException {

        return mypageService.updateProfile(multipartFile, updateRequestDto, userDetails);
    }

    // 마이페이지 내가 쓴 게시글
    @GetMapping("/api/user/mypost")
    public MypageResponseDto getMyPostList (
            @RequestParam int pageNo,
            @RequestParam int sizeNo,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {

        return mypageService.getMyPostList(pageNo, sizeNo, userDetails);
    }

    // 마이페이지 내가 북마크 한 게시글
    @GetMapping("/api/user/mybookmark")
    public List<MyBookmarkListDto> getMyBookmarkList (
            @RequestParam int pageNo,
            @RequestParam int sizeNo,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {

        return mypageService.getMyBookmark(pageNo, sizeNo, userDetails);
    }
}
