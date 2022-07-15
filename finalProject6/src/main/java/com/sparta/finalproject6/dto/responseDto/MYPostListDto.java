package com.sparta.finalproject6.dto.responseDto;

import com.sparta.finalproject6.model.ThemeCategory;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class MYPostListDto {

    private Long postId;
    private Long userId;
    private String title;
//    private String imgUrl;
    private int viewCount;
    private int loveCount;

    // private int bookmarkCount;
//    private int commentCount;
    private String regionCategory;
    private String priceCategory;
    private List<ThemeCategory> themeCategory;
//    private LocalDateTime createdAt;

//    private LocalDateTime modifiedAt;
//    private List<PostCommentResponseDto> comments;


    @Builder
    public MYPostListDto(Long postId, Long userId, String title, int viewCount, int loveCount, String regionCategory, String priceCategory, List<ThemeCategory> themeCategory) {//String imgUrl, int commentCount,
        this.postId = postId;
        this.userId = userId;
        this.title = title;
//        this.imgUrl = imgUrl;
        this.viewCount = viewCount;
        this.loveCount = loveCount;
//        this.commentCount = commentCount;
        this.regionCategory = regionCategory;
        this.priceCategory = priceCategory;
        this.themeCategory = themeCategory;
    }
}
