package com.sparta.finalproject6.dto.responseDto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class PostResponseDto {

    private Long postId;
    private String title;
    private String imgUrl;
    private String content;
//    private List<PostCommentResponseDto> comments;
//    private List<LoveResponseDto> loves;


}
