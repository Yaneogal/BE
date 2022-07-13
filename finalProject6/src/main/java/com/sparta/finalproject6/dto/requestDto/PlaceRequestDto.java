package com.sparta.finalproject6.dto.requestDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlaceRequestDto {
    private String address_name;
    private String category_group_code;
    private String category_group_name;
    private String category_name;
    private String distance;
    private List<MultipartFile> files;
    private String id;
    private String phone;
    private String place_name;
    private String place_url;
    private String road_address_name;
    private String x;
    private String y;

    //이거는 혹시모를 Json안에 이미지파일이 안담겨져서 올시 넣어줄것
    //이게 추가될 시 List<MultipartFile> files 이놈은 삭제
    private int imgCount;

    //TODO : 20220712
    //장소에 이미지 추가할때 이미지 넣는 순서가 뒤죽박죽이여도 들어갈 수 있게끔
    private List<Integer> imgOrder;

}
