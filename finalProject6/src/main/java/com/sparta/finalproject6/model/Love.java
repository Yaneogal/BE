package com.sparta.finalproject6.model;

import com.sparta.finalproject6.dto.responseDto.PostLoveDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Love extends Timestamped {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LOVE_ID")
    private Long id;

//    @ManyToOne
//    @JoinColumn(name = "POST_ID")
//    private Post post;
//
//    @ManyToOne
//    @JoinColumn(name = "USER_ID")
//    private User user;


    //TODO : postId와 userId로 좋아요 체크하기 위해 추가
    @Column(nullable = false)
    private Long postId;

    @Column(nullable = false)
    private Long userId;

    public Love(Long postId, Long userId) {
        this.postId = postId;
        this.userId = userId;
    }
}
