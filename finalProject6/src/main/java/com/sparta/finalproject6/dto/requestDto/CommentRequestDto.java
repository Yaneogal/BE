package com.sparta.finalproject6.dto.requestDto;

import com.sparta.finalproject6.model.Comment;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class CommentRequestDto {

    private Long id;
    private String comment;

    @Builder
    public CommentRequestDto(Comment comment) {
        this.id = comment.getId();
        this.comment = comment.getComment();
    }
}
