package com.sparta.finalproject6.dto.requestDto;

import com.sparta.finalproject6.model.ThemeCategory;
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
    private String imgUrl;
    private String regionCategory;
    private String priceCategory;
    private List<ThemeCategory> themeCategories;
    //themeCategory 추가 예정
    //private List<ThemeCategory> themeCategories;

    //TODO : 20220701 restroom추가
//    private String restroom;

}
