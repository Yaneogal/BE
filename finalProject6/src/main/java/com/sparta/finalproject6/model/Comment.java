package com.sparta.finalproject6.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Comment extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "COMMENT_ID")
    private Long id;

    @Column
    private String nickname;
    @Column
    private String comment;

    @Column
    private String userImgUrl;


    @Column
    private int commentCount;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "POST_ID")
//    @JsonBackReference
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
//    @JsonBackReference // 순환참조 방지
//    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private User user;

    @Builder
    public Comment(User user, Post post, String getComment) {
        this.user = user;
        this.post = post;
        this.comment = getComment;
    }

    public Comment(String comment, Post post, User user, String nickname, String userImgUrl) {
        this.comment = comment;
        this.user = user;
        this.post = post;
        this.nickname = nickname;
        this.userImgUrl = userImgUrl;
    }
}
