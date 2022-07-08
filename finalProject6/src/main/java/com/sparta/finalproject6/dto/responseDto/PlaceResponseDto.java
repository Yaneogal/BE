package com.sparta.finalproject6.dto.responseDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Builder
public class PlaceResponseDto {
    private String address_name;
    private String category_group_code;
    private String category_group_name;
    private String category_name;
    private String distance;
    private List<String> imgUrl;
    private Long id;
    private String phone;
    private String place_name;
    private String place_url;
    private String road_address_name;
    private double x;
    private double y;
}
