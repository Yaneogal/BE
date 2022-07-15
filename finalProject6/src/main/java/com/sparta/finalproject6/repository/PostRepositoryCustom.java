package com.sparta.finalproject6.repository;

import com.sparta.finalproject6.dto.responseDto.MyWrittenPostResponseDto;
import com.sparta.finalproject6.dto.responseDto.PostResponseDto;
import com.sparta.finalproject6.security.UserDetailsImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.List;

public interface PostRepositoryCustom {
//    Slice<Post> keywordSearch(String keyword, Pageable pageable);

    Slice<PostResponseDto> keywordSearch(String keyword, Pageable pageable);
    Slice<PostResponseDto> filterSearch(String region, String price, List<String> theme, Pageable pageable, UserDetailsImpl userDetails);

    Slice<MyWrittenPostResponseDto> getMyWrittenPosts(Long userId, Pageable pageable);
}
