package com.sparta.finalproject6.dto.responseDto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@Data
public class PostResponseDto {

    private Long postId;
    private String title;
    private List<String> imgUrl;
    private String content;
    private int viewCount;
    private int loveCount;
    private int bookmarkCount;
    private int commentCount;
    private String regionCategory;
    private String priceCategory;
    private List<String> themeCategory;
    private Boolean LoveStatus;
    private Boolean BookmarkStatus;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    private List<PostCommentResponseDto> comments;
    private List<LoveResponseDto> loves;


}
