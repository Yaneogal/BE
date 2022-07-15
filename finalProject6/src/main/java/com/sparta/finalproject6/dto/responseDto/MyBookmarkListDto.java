package com.sparta.finalproject6.dto.responseDto;

import com.sparta.finalproject6.model.ThemeCategory;
import lombok.*;

import java.util.List;

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
    // private int bookmarkCount;
    private int commentCount;
    private String regionCategory;
    private String priceCategory;
//    private List<ThemeCategory> themeCategory;


}
