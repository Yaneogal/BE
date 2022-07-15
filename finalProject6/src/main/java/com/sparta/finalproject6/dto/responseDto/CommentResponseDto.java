package com.sparta.finalproject6.dto.responseDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CommentResponseDto {

    private Long postId;
    private Long commentId;
    private String comment;
    private String nickname;
    private String userImgUrl;
    private LocalDateTime createdAt;
}
