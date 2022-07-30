package com.sparta.finalproject6.repository;

import com.sparta.finalproject6.dto.responseDto.MyPagePostResponseDto;
import com.sparta.finalproject6.dto.responseDto.PostResponseDto;
import com.sparta.finalproject6.security.UserDetailsImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.List;

public interface PostRepositoryCustom {
    
    Slice<PostResponseDto> keywordSearch(String keyword, Pageable pageable);
    Slice<PostResponseDto> filterSearch(String region, String price, List<String> theme, Pageable pageable);
    Slice<MyPagePostResponseDto> getMyWrittenPosts(Long userId, Pageable pageable);
    Slice<MyPagePostResponseDto> getMyBookmarkPosts(List<Long> postsId, Pageable pageable);
}
