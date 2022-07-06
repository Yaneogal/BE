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
//@SequenceGenerator(name = "LOVE_A",
//        sequenceName = "LOVE_B",
//        initialValue = 1, allocationSize = 50)
public class Love {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LOVE_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "POST_ID")
    private Post post;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    @Column
    private Boolean postLoveStatus;

    public Love(PostLoveDto loveDto) {
        this.user = loveDto.getUser();
        this.post = loveDto.getPost();
    }

    public Love(User user, Post post, Boolean postLoveStatus) {
        this.user = user;
        this.post = post;
        this.postLoveStatus = postLoveStatus;
    }
}
