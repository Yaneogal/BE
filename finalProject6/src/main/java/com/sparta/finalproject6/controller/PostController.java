package com.sparta.finalproject6.controller;

import com.sparta.finalproject6.dto.requestDto.PostRequestDto;
import com.sparta.finalproject6.dto.responseDto.PostResponseDto;
import com.sparta.finalproject6.model.Post;
import com.sparta.finalproject6.model.PriceCategory;
import com.sparta.finalproject6.model.RegionCategory;
import com.sparta.finalproject6.security.UserDetailsImpl;
import com.sparta.finalproject6.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.awt.print.Pageable;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    // 포스트 전체 조회
    @GetMapping("/api/posts")
    public ResponseEntity<PostResponseDto> getAllPosts(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                       Pageable pageable) {
        return postService.getPosts(pageable, userDetails);
    }

    // 포스트 상세페이지
    @GetMapping("/api/post/{postId}")
    public ResponseEntity<PostResponseDto> getPostDetail(@PathVariable Long postId) {
        try{
            return new ResponseEntity(postService.getPostDetail(postId), HttpStatus.OK);
        }catch(IllegalArgumentException e){
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // 포스트 등록
    @PostMapping("/api/post")
    public ResponseEntity<String>createPost(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                         PostRequestDto requestDto, MultipartFile multipartFile){
        try{
            postService.addPost(userDetails, requestDto, multipartFile);
            return new ResponseEntity<>("게시글 등록을 성공하였습니다.", HttpStatus.CREATED);
        }catch(IllegalArgumentException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
