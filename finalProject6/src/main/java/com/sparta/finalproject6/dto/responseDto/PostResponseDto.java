package com.sparta.finalproject6.dto.responseDto;

import com.querydsl.core.annotations.QueryProjection;
import com.sparta.finalproject6.dto.requestDto.ThemeCategoryDto;
import com.sparta.finalproject6.model.Grade;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class PostResponseDto {

    private Long postId;
    private Long userId;
    private String nickName;
    private String userImgUrl;
    private String title;
    private List<String> imgUrl;
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

    private Grade grade;
    private int totalPoint;


    public PostResponseDto(Long postId, String nickName, String userImgUrl, String title, List<String> imgUrl, String content, int viewCount, int loveCount, int bookmarkCount, int commentCount, String regionCategory, String priceCategory, Boolean loveStatus, Boolean bookmarkStatus, List<ThemeCategoryDto> themeCategory, LocalDateTime createdAt, LocalDateTime modifiedAt, List<PostCommentResponseDto> comments,Grade grade , int totalPoint) {
        this.postId = postId;
        this.nickName = nickName;
        this.userImgUrl = userImgUrl;
        this.title = title;
        this.imgUrl = imgUrl;
        this.content = content;
        this.viewCount = viewCount;
        this.loveCount = loveCount;
        this.bookmarkCount = bookmarkCount;
        this.commentCount = commentCount;
        this.regionCategory = regionCategory;
        this.priceCategory = priceCategory;
        this.loveStatus = loveStatus;
        this.bookmarkStatus = bookmarkStatus;
        this.themeCategory = themeCategory;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
        this.comments = comments;
        this.grade = grade;
        this.totalPoint = totalPoint;
    }

    @QueryProjection
    public PostResponseDto(Long postId, Long userId, String nickName, String userImgUrl, String title, String content, int bookmarkCount, int loveCount, String regionCategory, String priceCategory, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.postId = postId;
        this.userId= userId;
        this.nickName = nickName;
        this.userImgUrl = userImgUrl;
        this.title = title;
        this.content = content;
        this.bookmarkCount = bookmarkCount;
        this.loveCount = loveCount;
        this.regionCategory = regionCategory;
        this.priceCategory = priceCategory;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }
}
