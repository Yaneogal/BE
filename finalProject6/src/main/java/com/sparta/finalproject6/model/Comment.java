package com.sparta.finalproject6.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@SequenceGenerator(
        name = "COMMENT_A",
        sequenceName = "COMMENT_B",
        initialValue = 1, allocationSize = 50)
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "COMMENT_A")
    @Column(name = "COMMENT_ID")
    private Long id;

    @Column
    private String nickname;
    @Column
    private String comment;

    @Column
    private String userImgUrl;

    @ManyToOne
    @JoinColumn(name = "POST_ID")
    @JsonBackReference
    private Post post;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    @JsonBackReference // 순환참조 방지
    private User user;

    @Builder
    public Comment(User user, Post post, String nickname) {
        this.user = user;
        this.post = post;
        this.nickname = nickname;
    }

}
