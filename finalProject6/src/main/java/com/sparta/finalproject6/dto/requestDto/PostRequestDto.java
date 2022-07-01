package com.sparta.finalproject6.dto.requestDto;

import com.sparta.finalproject6.model.PriceCategory;
import com.sparta.finalproject6.model.RegionCategory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostRequestDto {

    private String title;
    private String content;
    private String imgUrl;
    private RegionCategory regionCategory;
    private PriceCategory priceCategory;
    // themeCategory 추가 예정

}
