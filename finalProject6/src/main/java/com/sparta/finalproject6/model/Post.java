package com.sparta.finalproject6.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.sparta.finalproject6.dto.requestDto.PostRequestDto;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@SequenceGenerator(name = "POST_A",
        sequenceName = "POST_B",
        initialValue = 1, allocationSize = 50)
public class Post extends Timestamped{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "POST_A")
    @Column(name = "POST_ID")
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column
    private String imgUrl;

    @Column(nullable = false)
    private String content;

    @Column
    private int loveCount;

    @Column
    private int bookmarkCount;

    @Column
    private int viewCount;

    @Column(nullable = false)
    private RegionCategory regionCategory;

    @Column(nullable = false)
    private PriceCategory priceCategory;

    @OneToMany(mappedBy = "post", orphanRemoval = true) // 부모 객체 삭제시 하위 객첵도 삭제
    @JsonManagedReference //직렬화 허용
    private List<Comment> comments;

    @OneToMany(mappedBy = "post", orphanRemoval = true)
    private List<Love> loves;

    @OneToMany(mappedBy = "post", orphanRemoval = true)
    @JsonManagedReference
    private List<Bookmark> bookmarks;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    public Post(User user, PostRequestDto requestDto) {
        this.user = user;
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
        this.imgUrl = requestDto.getImgUrl();
        this.regionCategory = requestDto.getRegionCategory();
        this.priceCategory = requestDto.getPriceCategory();
    }

    public void update(User user, PostRequestDto postRequestDto) {
        this.user = user;
        this.title = postRequestDto.getTitle();;
        this.content = postRequestDto.getContent();
        this.imgUrl = postRequestDto.getImgUrl();
        this.regionCategory = postRequestDto.getRegionCategory();
        this.priceCategory = postRequestDto.getPriceCategory();
    }
}
