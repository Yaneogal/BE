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
    private String title;
    private String imgUrl;
    private int viewCount;
    private int loveCount;
    private int commentCount;
    private String regionCategory;
    private String priceCategory;

}
