package com.sparta.finalproject6.model;


import com.sparta.finalproject6.dto.requestDto.SignUpRequestDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Users")
public class User extends Timestamped{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID")
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String password;

    @Column
    private String userImgUrl;

    @Column
    private Long kakaoId;

    @Column
    private String userInfo;

    @Column(nullable = false)
    private int totalPoint;

    @Enumerated(EnumType.STRING)
    private Grade grade;

    public User(String username, int totalPoint) {
        this.username = username;
        this.totalPoint = totalPoint;
    }

    public boolean availableGradeUp() {
        return Grade.availableGradeUp(this.getGrade(), this.getTotalPoint());
    }

    public Grade gradeUp() {
        Grade nextGrade = Grade.getNextGrade((this.getTotalPoint()));
        this.grade = nextGrade;

        return nextGrade;
    }

    public User(String username, String nickname, String password) {
        this.username = username;
        this.nickname = nickname;
        this.password = password;
    }
    public User(SignUpRequestDto dto){
        this.username = dto.getUsername();
        this.password = dto.getPassword();
        this.nickname = dto.getNickname();
    }

    public User(String username, String nickname, String password, String thumbnailImage, Long kakaoId) {
        this.username = username;
        this.nickname = nickname;
        this.password = password;
        this.userImgUrl = thumbnailImage;
        this.kakaoId = kakaoId;
    }

    public void updateUser(String nickname, String userInfo, String userImgUrl) {
        this.nickname = nickname;
        this.userInfo = userInfo;
        this.userImgUrl = userImgUrl;
    }

    public void updatePoint(int totalPoint) {
        this.totalPoint += totalPoint;
        if(this.totalPoint <= 0) {
            this.totalPoint = 0;
        }
    }
}
