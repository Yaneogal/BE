package com.sparta.finalproject6.dto.responseDto;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MyBookmarkListDto {

    private Long postId;
    private Long userId;
    private String imgUrl;
    private String title;


}
