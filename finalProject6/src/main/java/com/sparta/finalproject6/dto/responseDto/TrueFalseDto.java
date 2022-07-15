package com.sparta.finalproject6.dto.responseDto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
public class TrueFalseDto {
    private Boolean trueOrFalse;
    private Long postId;
}
