package com.sparta.finalproject6.dto.requestDto;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProfileUpdateRequestDto {

    private String userImgUrl;
    private String nickname;
    private String userInfo;

}
