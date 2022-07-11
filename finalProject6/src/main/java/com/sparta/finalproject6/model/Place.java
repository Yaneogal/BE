package com.sparta.finalproject6.model;

import com.sparta.finalproject6.dto.requestDto.PlaceRequestDto;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Place{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PLACE_ID")
    private Long placeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "POST_ID")
    private Post post;

    @Column
    private String addressName;

    @Column
    private String categoryGroupCode;

    @Column
    private String categoryGroupName;

    @Column
    private String categoryName;

    @Column
    private String distance;

    @Column
    private Long id;

    @Column
    @ElementCollection
    @CollectionTable(name = "placeImagesUrl",joinColumns = {@JoinColumn(name = "place_id",referencedColumnName = "PLACE_ID")})
    private List<String> imgUrl;

    //S3에서 기존 파일을 삭제하기 위해 추가한 파일네임.
    @Column
    @ElementCollection
    @CollectionTable(name = "placeImagesFileName",joinColumns = {@JoinColumn(name = "place_id",referencedColumnName = "PLACE_ID")})
    private List<String> imgFileName;

    @Column
    private String phone;
    @Column
    private String place_name;
    @Column
    private String place_url;
    @Column
    private String road_address_name;
    @Column
    private double x;
    @Column
    private double y;

    public void updatePlace(PlaceRequestDto dto){
        this.addressName = dto.getAddress_name();
        this.categoryGroupCode = dto.getCategory_group_code();
        this.categoryGroupName = dto.getCategory_group_name();
        this.categoryName = dto.getCategory_name();
        this.distance = dto.getDistance();
        this.id = dto.getId();
        this.phone = dto.getPhone();
        this.place_name = dto.getPlace_name();
        this.place_url = dto.getPlace_url();
        this.road_address_name = dto.getRoad_address_name();
        this.x = dto.getX();
        this.y = dto.getY();
    }
    public void updatePlaceImage(List<String> imgUrl,List<String> imgFileName){
        this.imgUrl = imgUrl;
        this.imgFileName = imgFileName;
    }
}
