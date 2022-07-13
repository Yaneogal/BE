package com.sparta.finalproject6.dto.requestDto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostRequestDto {

    private String title;
    private String content;
    private String regionCategory;
    private String priceCategory;
    private List<ThemeCategoryDto> themeCategories;
    //화장실 추가
    private String restroom;
    private List<String> restroomOption;

    public PostRequestDto(String title, String content,String regionCategory, String priceCategory, String restroom,List<String> restroomOption ,List<ThemeCategoryDto> themeCategories) {
        this.title = title;
        this.content = content;
        this.regionCategory = regionCategory;
        this.priceCategory = priceCategory;
        this.restroom = restroom;
        this.restroomOption = restroomOption;
        this.themeCategories = themeCategories;
    }

    //TODO : 20220701 restroom추가
//    private String restroom;

}
