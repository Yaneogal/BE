package com.sparta.finalproject6.dto.responseDto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MypageResponseDto {

    private String userImgUrl;
    private String nickname;
    private String userInfo;
}
