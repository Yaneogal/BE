package com.sparta.finalproject6.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@SequenceGenerator(name = "POST_A",
        sequenceName = "POST_B",
        initialValue = 1, allocationSize = 100)
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "POST_A")
    @Column(name = "POST_ID")
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String imgUrl;

    @Column(nullable = false)
    private String content;

//    @Column(nullable = false)
//    private RegionCategory regionCategory;
//
//    @Column(nullable = false)
//    private PriceCategory priceCategory;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

}
