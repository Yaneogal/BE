package com.sparta.finalproject6.dto.requestDto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ThemeCategoryDto {
    private String themeCategory;

    public ThemeCategoryDto(String themeCategory) {
        this.themeCategory = themeCategory;
    }
}
