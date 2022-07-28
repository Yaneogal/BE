package com.sparta.finalproject6.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sparta.finalproject6.dto.requestDto.PostRequestDto;
import lombok.*;

import javax.persistence.*;
import java.util.List;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Post extends Timestamped{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "POST_ID")
    private Long id;

    @Column(length = 50, nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column
    private int loveCount;

    @Column
    private int bookmarkCount;

    @Column
    private int viewCount;

    private int commentCount;

    @Column(nullable = false)
    private String regionCategory;
    @Column(nullable = false)
    private String priceCategory;


    @OneToMany(mappedBy = "post", orphanRemoval = true)
    @JsonIgnore
    private List<Comment> comments;

    @Transient
    @Builder.Default
    private Boolean isLove = false;

    @Transient
    @Builder.Default
    private Boolean isBookmark = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;

    public Post(User user, PostRequestDto requestDto) {
        this.user = user;
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
        this.regionCategory = requestDto.getRegionCategory();
        this.priceCategory = requestDto.getPriceCategory();
    }

    @Builder
    public Post(String title, String content, String regionCategory, String priceCategory, User user, String restroom, List<String> restroomOption) {
        this.title = title;
        this.content = content;
        this.regionCategory = regionCategory;
        this.priceCategory = priceCategory;
        this.user = user;
    }


    public void update(PostRequestDto postRequestDto) {
//        this.user = user;
        this.title = postRequestDto.getTitle();;
        this.content = postRequestDto.getContent();
        this.regionCategory = postRequestDto.getRegionCategory();
        this.priceCategory = postRequestDto.getPriceCategory();
    }

    //좋아요 수 업데이트
    public void updateLikeCount(boolean countUp){
        if (countUp) {
            ++this.loveCount;
        } else {
            --this.loveCount;
        }
    }

    public void updateBookmarkCount(boolean countUp){
        if(countUp){
            ++this.bookmarkCount;
        }
        else{
            --this.bookmarkCount;
        }
    }

    public void viewCountUp(){
        this.viewCount++;
    }

    public void updateCommentCount(int commentCount) {

        this.commentCount = commentCount;
    }
}
