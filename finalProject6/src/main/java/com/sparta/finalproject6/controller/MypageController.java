package com.sparta.finalproject6.controller;

import com.sparta.finalproject6.dto.requestDto.ProfileUpdateRequestDto;
import com.sparta.finalproject6.dto.responseDto.*;
import com.sparta.finalproject6.security.UserDetailsImpl;
import com.sparta.finalproject6.service.MypageService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
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

    // My Page 회원정보 조회 API
    @GetMapping("/api/user")
    public ProfileUpdateResponseDto getMyProfile (
            @AuthenticationPrincipal UserDetailsImpl userDetails) {

        return mypageService.getMyProfile(userDetails);
    }

    // 마이페이지 회원정보 수정
    @PutMapping ("/api/user")
    public ResponseEntity<String> updateProfile (
            @RequestPart("userImgUrl") MultipartFile multipartFile,
            @RequestParam("nickname") String nickname,
            @RequestParam("userInfo") String userInfo,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {

        ProfileUpdateRequestDto updateRequestDto = new ProfileUpdateRequestDto(nickname, userInfo);
        try {
            mypageService.updateProfile(multipartFile, updateRequestDto, userDetails);
            return new ResponseEntity<>("회원정보를 수정 했습니다.", HttpStatus.OK);
        }catch(IOException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // 마이페이지 내가 쓴 게시글
//    @GetMapping("/api/user/mypost")
//    public ResponseEntity<List<MYPostListDto>> getMyPostList (
//            @AuthenticationPrincipal UserDetailsImpl userDetails) { //@RequestParam int pageNo,
//        //@RequestParam int sizeNo,
//        // User user = userDetails.getUser();
//        try {
//            List<MYPostListDto> myPostListDto = mypageService.getMyPostList(userDetails); //pageNo, sizeNo,
//            return new ResponseEntity<>(myPostListDto, HttpStatus.OK);
//        } catch (IllegalArgumentException e) {
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
//    }

    // 마이페이지 내가 쓴 게시글(이호진)
    @GetMapping("/api/user/mypost")
    public ResponseEntity<Slice<MyWrittenPostResponseDto>> getMyWrittenPosts(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                                             Pageable pageable) {

        return mypageService.getMyWrittenPosts(userDetails, pageable);
    }

    // 마이페이지 내가 북마크 한 게시글
    @GetMapping("/api/user/mybookmark")
    public ResponseEntity<List<MyBookmarkListDto>> getMyBookmarkList (
            @AuthenticationPrincipal UserDetailsImpl userDetails) {

            List<MyBookmarkListDto> myBookmarkListDto = mypageService.getMyBookmark(userDetails);
            return new ResponseEntity<>(myBookmarkListDto, HttpStatus.OK);

    }
}
