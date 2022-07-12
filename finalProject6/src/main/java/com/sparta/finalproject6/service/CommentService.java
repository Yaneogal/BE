package com.sparta.finalproject6.service;

import com.sparta.finalproject6.dto.requestDto.CommentRequestDto;
import com.sparta.finalproject6.dto.responseDto.CommentResponseDto;
import com.sparta.finalproject6.model.Comment;
import com.sparta.finalproject6.model.Post;
import com.sparta.finalproject6.model.User;
import com.sparta.finalproject6.repository.CommentRepository;
import com.sparta.finalproject6.repository.PostRepository;
import com.sparta.finalproject6.repository.UserRepository;
import com.sparta.finalproject6.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;

    private final CommentRepository commentRepository;

    // 댓글 작성
    public void addComment(Long postId, CommentRequestDto commentRequestDto, String nickname) {

        Post post = postRepository.findById(postId).orElseThrow(
                () -> new IllegalArgumentException("게시글이 존재하지 않습니다.")
        );

        User user = new User();
        Long userId = user.getId();
        User commentWriter = userRepository.findById(userId).orElseThrow(
                () -> new IllegalArgumentException("사용자 정보를 찾을 수 없습니다.")
        );
        Comment comment = new Comment(commentRequestDto, post, nickname);
        // System.out.println("comment 테스트!!");
//        CommentResponseDto commentResponseDto = new CommentResponseDto(comment);
        commentRepository.save(comment);

        List<Comment> comments = post.getComments();
        comments.add(comment);

//        return commentResponseDto;
    }

        // 댓글 삭제
        public void deleteComment(Long commentId, String nickname) {

            Comment comment = commentRepository.findById(commentId).orElseThrow(
                    () -> new IllegalArgumentException("댓글이 존재하지 않습니다."));
            String commentWriter = comment.getNickname();
            if (commentWriter.equals(nickname)) {
                commentRepository.delete(comment);
            } else {
                throw new IllegalArgumentException("댓글을 작성한 유저가 아닙니다.");
            }
        }

}
