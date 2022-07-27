package com.sparta.finalproject6.dto.responseDto;

import com.sparta.finalproject6.dto.requestDto.ThemeCategoryDto;
import com.sparta.finalproject6.model.Grade;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class PostDetailResponseDto {
    private Long postId;
    private String nickname;
    private String userImgUrl;
    private String title;
    private String content;
    private int viewCount;
    private int loveCount;
    private int bookmarkCount;
    private int commentCount;
    private String regionCategory;
    private String priceCategory;
    private Boolean loveStatus;
    private Boolean bookmarkStatus;
    private List<ThemeCategoryDto> themeCategory;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private List<PostCommentResponseDto> comments;

    //TODO : 게시글 상세페이지 랭크, 점수 리턴
    private Grade grade;
    private int totalPoint;

    private List<PlaceResponseDto> place;
}
