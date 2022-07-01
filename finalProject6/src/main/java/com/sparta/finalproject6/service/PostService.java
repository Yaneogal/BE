package com.sparta.finalproject6.service;

import com.sparta.finalproject6.dto.requestDto.PostRequestDto;
import com.sparta.finalproject6.dto.responseDto.LoveResponseDto;
import com.sparta.finalproject6.dto.responseDto.PostCommentResponseDto;
import com.sparta.finalproject6.dto.responseDto.PostResponseDto;
import com.sparta.finalproject6.model.*;
import com.sparta.finalproject6.repository.*;
import com.sparta.finalproject6.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.awt.print.Pageable;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    // TODO: 2022-07-01 : 오늘의 목표 -> 같이 게시글 기능 테스트해보기(백엔드만) 
    
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final LoveRepository loveRepository;
    private final ThemeCategoryRepository themeRepository;

    // 전체 포스트 조회
    @Transactional(readOnly = true)
    public ResponseEntity<PostResponseDto> getPosts(Pageable pageable, UserDetailsImpl userDetails) {
        List<Post> posts = postRepository.findAllPosts(pageable);
        
        Long userId = userDetails.getUser().getId();
        List<PostResponseDto> postList = new ArrayList<>();

        for (Post post : posts) {
            
            List<Love> postLoves = loveRepository.findAllByPostId(post.getId());
            List<LoveResponseDto> loveUserList = new ArrayList<>();
            for (Love love : postLoves) {
                LoveResponseDto loveResponseDto = new LoveResponseDto(userId);
                loveUserList.add(loveResponseDto);
            }
            PostResponseDto postResponseDto = PostResponseDto.builder()
                    .postId(post.getId())
                    .title(post.getTitle())
                    .imgUrl(post.getImgUrl())
                    .content(post.getContent())
                    .regionCategory(post.getRegionCategory())
                    .priceCategory(post.getPriceCategory())
                    .viewCount(post.getViewCount())
                    .loveCount(post.getLoveCount())
                    .bookmarkCount(post.getBookmarkCount())
                    .createdAt(post.getCreatedAt())
                    .modifiedAt(post.getModifiedAt())
                    .build();

            postList.add(postResponseDto);
        }
        return new ResponseEntity(postList, HttpStatus.OK);
    }

    // 포스트 상세 페이지
    @Transactional(readOnly = true)
    public ResponseEntity<PostResponseDto> getPostDetail(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new IllegalArgumentException("게시글이 존재하지 않습니다.")
        );

        List<Comment> comments = commentRepository.findAllByPostId(postId);
        List<PostCommentResponseDto> commentList = new ArrayList<>();
        for (Comment comment : comments) {
            PostCommentResponseDto postCommentResponseDto = PostCommentResponseDto.builder()
                    .commentId(comment.getId())
                    .userImgUrl(comment.getPost().getUser().getUserImgUrl())
                    .comment(comment.getComment())
                    .nickname(comment.getNickname())
                    .build();
            commentList.add(postCommentResponseDto);
        }
        List<Love> loves = post.getLoves();
        List<LoveResponseDto> loveUserList = new ArrayList<>();
        for (Love love : loves) {
//            Long userId = love.getUserId();
            LoveResponseDto loveResponseDto = new LoveResponseDto(love.getId());
            loveUserList.add(loveResponseDto);
        }

        PostResponseDto detailResponseDto = PostResponseDto.builder()
                .postId(post.getId())
                .title(post.getTitle())
                .imgUrl(post.getImgUrl())
                .content(post.getContent())
                .regionCategory(post.getRegionCategory())
                .priceCategory(post.getPriceCategory())
                .viewCount(post.getViewCount())
                .loveCount(post.getLoveCount())
                .bookmarkCount(post.getBookmarkCount())
                .createdAt(post.getCreatedAt())
                .modifiedAt(post.getModifiedAt())
                .comments(commentList)
                .loves(loveUserList)
                .build();

        return new ResponseEntity<>(detailResponseDto, HttpStatus.OK);
    }

    //  포스트 등록
    @Transactional
    public void addPost(UserDetailsImpl userDetails, PostRequestDto requestDto, MultipartFile multipartFile) {
        User user = userRepository.findByUsername(userDetails.getUsername()).orElseThrow(
                () -> new IllegalArgumentException("유저가 존재하지 않습니다.")
        );

//        Map<String, String> imgResult = awsS3Service.uploadFile(multipartFile);

        Post post = Post.builder()
                .title(requestDto.getTitle())
                .content(requestDto.getContent())
                .regionCategory(requestDto.getRegionCategory())
                .priceCategory(requestDto.getPriceCategory())
                .user(user)
                .build();

        postRepository.save(post);

        // post 등록시 테마카테고리 복수 저장은 위한 로직.
        requestDto.getThemeCategories()
                .forEach(t ->
                        themeRepository.save(new ThemeCategory(t.getThemeCategory(), post))
                );

    }

    // 포스트 수정
    // 포스트 삭제

    private void validateUser(UserDetailsImpl userDetails, Post post) {
        if (!post.getUser().getUsername().equals(userDetails.getUsername())) {
            throw new IllegalArgumentException("게시글 작성자만 수정할 수 있습니다.");
        }
    }
}

