package com.sparta.finalproject6.dto.responseDto;

import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MypageResponseDto {

    private Long userId;
    private String userImgUrl;
    private String nickname;
    private String userInfo;
    private List<MYPostListDto> myPostList;
    private List<MyBookmarkListDto> myBookmarkList;
}
