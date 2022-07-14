package com.sparta.finalproject6.controller;

import com.sparta.finalproject6.dto.requestDto.CommentRequestDto;
import com.sparta.finalproject6.exception.ApiResponseMessage;
import com.sparta.finalproject6.model.Post;
import com.sparta.finalproject6.security.UserDetailsImpl;
import com.sparta.finalproject6.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;


@Validated
@RequiredArgsConstructor
@RestController
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/api/post/{postId}/comment")
    public ResponseEntity<String> commentWrite(@PathVariable Long postId,
                                                           @RequestBody @Valid CommentRequestDto commentRequestDto,
                                                           String nickname,
                                                           @AuthenticationPrincipal UserDetailsImpl userDetails){
        try {
            commentService.addComment(postId, commentRequestDto, nickname);
            return new ResponseEntity<>("댓글을 작성 했습니다.", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        // ApiResponseMessage message = new ApiResponseMessage("Success", "댓글을 작성했습니다.", "405", "로그인이 필요한 기능입니다.");
        // return new  ResponseEntity<ApiResponseMessage>(message, HttpStatus.OK);
    }

    @DeleteMapping("/api/comment/{commentId}")
    public ResponseEntity<String> commentDelete(@PathVariable Long commentId,
                                                String nickname,
                                                @AuthenticationPrincipal UserDetailsImpl userDetails) {
        try {
            commentService.deleteComment(commentId, nickname);
            return new ResponseEntity<>("댓글을 삭제 했습니다.", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
//        ApiResponseMessage message = new ApiResponseMessage("Success", "댓글이 삭제 되었습니다.", "400", "작성자만 삭제할 수 있습니다.");
//        return new ResponseEntity<ApiResponseMessage>(message, HttpStatus.OK);
    }

}
