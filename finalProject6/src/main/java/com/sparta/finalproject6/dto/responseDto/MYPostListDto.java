package com.sparta.finalproject6.dto.responseDto;

import com.sparta.finalproject6.dto.requestDto.ThemeCategoryDto;
import com.sparta.finalproject6.model.ThemeCategory;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MYPostListDto {

    private Long postId;
    private Long userId;
    private String title;
    private String imgUrl;
    private String content;
    private int viewCount;
    private int loveCount;
    private int bookmarkCount;
    private int commentCount;
    private String regionCategory;
    private String priceCategory;
    private Boolean loveStatus;
    private Boolean bookmarkStatus;
    private List<ThemeCategory> themeCategory;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private List<PostCommentResponseDto> comments;
}
