package com.sparta.finalproject6.dto.requestDto;

import com.sparta.finalproject6.model.PriceCategory;
import com.sparta.finalproject6.model.RegionCategory;
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
    private RegionCategory regionCategory;
    private PriceCategory priceCategory;
    private List<ThemeCategory> themeCategories;
}
