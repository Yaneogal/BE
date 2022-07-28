package com.sparta.finalproject6.dto.responseDto;

import com.querydsl.core.annotations.QueryProjection;
import com.sparta.finalproject6.dto.requestDto.ThemeCategoryDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class MyPagePostResponseDto {

    private Long postId;
    private Long userId;
    private String title;

    private String imgUrl;
    private String regionCategory;
    private String priceCategory;

    private List<ThemeCategoryDto> themeCategory;
    private int viewCount;
    private int loveCount;
    private int commentCount;

    @QueryProjection
    public MyPagePostResponseDto(Long postId, Long userId, String title, String regionCategory, String priceCategory, int viewCount, int loveCount, int commentCount) {
        this.postId = postId;
        this.userId = userId;
        this.title = title;
        this.regionCategory = regionCategory;
        this.priceCategory = priceCategory;
        this.viewCount = viewCount;
        this.loveCount = loveCount;
        this.commentCount = commentCount;
    }
}
