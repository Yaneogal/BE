package com.sparta.finalproject6.dto.requestDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CommentRequestDto {

    @NotNull(message = "댓글을 입력해 주세요.")
    private String comment;

}
