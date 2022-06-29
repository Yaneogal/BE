package com.sparta.finalproject6.dto.responseDto;

import com.sparta.finalproject6.model.Comment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CommentResponseDto {

    private Long id;
    private String comment;

    @Builder
    public CommentResponseDto(Comment comment) {
        this.id = comment.getId();
        this.comment = comment.getComment();
    }
}
