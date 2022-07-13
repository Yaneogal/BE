package com.sparta.finalproject6.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Bookmark {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "BOOKMARK_ID")
    private Long id;

//    @ManyToOne
//    @JoinColumn(name = "USER_ID")
//    private User user;
//
//    @ManyToOne
//    @JoinColumn(name = "POST_ID")
//    private Post post;

    @Column
    private Long postId;

    @Column
    private Long userId;


    public Bookmark(Long postId, Long userId) {
        this.postId = postId;
        this.userId = userId;
    }
}
