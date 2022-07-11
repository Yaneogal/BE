package com.sparta.finalproject6.dto.requestDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostRequestDto {

    private String title;
    private String content;
    private String regionCategory;
    private String priceCategory;
    private List<ThemeCategoryDto> themeCategories;

    //TODO : 20220701 restroom추가
//    private String restroom;

}
