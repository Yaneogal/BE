package com.sparta.finalproject6.dto.responseDto;

import com.sparta.finalproject6.model.Grade;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProfileUpdateResponseDto {

    private Long userId;
    private String userImgUrl;
    private String nickname;
    private String userInfo;
    private Grade grade;
    private int totalPoint;
    private boolean isMine;

}
