package com.sparta.finalproject6.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@SequenceGenerator(
        name = "LOVECOUNT_A",
        sequenceName = "LOVECOUNT_B",
        initialValue = 1, allocationSize = 50)
public class LoveToggle {

    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator = "LOVECOUNT_A")
    @Column(name = "LOVECOUNT_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    @JsonBackReference
    private User user;

    @ManyToOne
    @JoinColumn(name = "POST_ID")
    @JsonBackReference // 직렬화 하지 않도록 막음
    private Post post;

    @Column
    private Boolean postLoveStatus;

    public LoveToggle(User user, Post post, Boolean postLoveStatus) {
        this.user = user;
        this.post = post;
        this.postLoveStatus = postLoveStatus;
    }
}
