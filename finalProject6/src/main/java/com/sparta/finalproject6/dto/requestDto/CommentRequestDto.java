package com.sparta.finalproject6.dto.requestDto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class CommentRequestDto {

    @NotNull(message = "댓글을 입력해 주세요.")
    private String comment;

}
