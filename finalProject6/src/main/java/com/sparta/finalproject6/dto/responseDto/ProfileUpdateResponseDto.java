package com.sparta.finalproject6.dto.responseDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProfileUpdateResponseDto {

    private Long userId;
    private String userImgUrl;
    private String nickname;
    private String userInfo;
    // private List<MYPostListDto> myPostList;
}
