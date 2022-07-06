package com.sparta.finalproject6.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
//@SequenceGenerator(
//        name = "BOOKMARK_A",
//        sequenceName = "BOOKMARK_B",
//        initialValue = 1, allocationSize = 50)
public class Bookmark {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "BOOKMARK_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    @ManyToOne
    @JoinColumn(name = "POST_ID")
    private Post post;

    @Column
    private Boolean bookmarkStatus;



    public Bookmark(User user, Post post, Boolean bookmarkStatus) {
        this.user = user;
        this.post = post;
        this.bookmarkStatus = bookmarkStatus;
    }
}
