package com.sparta.finalproject6.controller;

import com.sparta.finalproject6.dto.requestDto.CommentRequestDto;
import com.sparta.finalproject6.dto.responseDto.CommentResponseDto;
import com.sparta.finalproject6.security.UserDetailsImpl;
import com.sparta.finalproject6.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@Validated
@RequiredArgsConstructor
@RestController
public class CommentController {

    private final CommentService commentService;

    @GetMapping("/api/comment/{postId}")
    public ResponseEntity<List<CommentResponseDto>> getComments(@PathVariable Long postId,
                                                                @AuthenticationPrincipal UserDetailsImpl userDetails) {
        try {
            List<CommentResponseDto> commentList = commentService.getComment(postId);
            return new ResponseEntity<>(commentList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/api/comment/{postId}")
    public ResponseEntity<String> commentWrite(@PathVariable Long postId,
                                                           @RequestBody CommentRequestDto commentRequestDto,
                                                           @AuthenticationPrincipal UserDetailsImpl userDetails){
        try {
            commentService.addComment(postId, commentRequestDto, userDetails);
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
