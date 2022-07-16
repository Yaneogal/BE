package com.sparta.finalproject6.dto.responseDto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class
PostCommentResponseDto {

    private Long commentId;
    private String nickname;
    private String userImgUrl;
    private String comment;
    private String createdAt;


}
