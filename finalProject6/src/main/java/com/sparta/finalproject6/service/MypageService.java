package com.sparta.finalproject6.service;

import com.sparta.finalproject6.dto.responseDto.MypageResponseDto;
import com.sparta.finalproject6.dto.responseDto.PostResponseDto;
import com.sparta.finalproject6.model.Post;
import com.sparta.finalproject6.model.User;
import com.sparta.finalproject6.repository.BookmarkRepository;
import com.sparta.finalproject6.repository.LoveRepository;
import com.sparta.finalproject6.repository.PostRepository;
import com.sparta.finalproject6.repository.UserRepository;
import com.sparta.finalproject6.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MypageService {

    private final UserRepository userRepository;
    private final PostRepository postrepository;
    private final LoveRepository loveRepository;
    private final BookmarkRepository bookmarkRepository;

    // My Page 조회
    public MypageResponseDto getMyProfile(UserDetailsImpl userDetails) {
        if (userDetails == null) {
            throw new IllegalArgumentException("승인되지 않은 사용자 입니다.");
        }
        User found = userRepository.findByUsername(userDetails.getUsername()).orElseThrow(
                () -> new IllegalArgumentException("사용자를 찾을 수 없습니다.")
        );
        String nickname = found.getUsername();

        MypageResponseDto responseDto = MypageResponseDto.builder()
                .nickname(found.getNickname())
                .userImgUrl(found.getUserImgUrl())
                .userInfo(found.getUserInfo())
                .build();

        return responseDto;
    }

    // 내가 Bookmark 한 포스트 조회
    public List<PostResponseDto> getMyBookmarkPostList(UserDetailsImpl userDetails, int page) {
        if (userDetails == null) {
            throw new IllegalArgumentException("승인되지 않은 사용자 입니다.");
        }
        User found = userRepository.findByUsername(userDetails.getUsername()).orElseThrow(
                () -> new IllegalArgumentException("사용자를 찾을 수 없습니다.")
        );
        Pageable pageable = PageRequest.of(page, 8);
        // Page<Post> postList = postrepository.findAllByOrderByCreatedAtDesc(found, pageable);
        return null;
    }

}
