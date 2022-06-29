package com.sparta.finalproject6.dto.responseDto;

import com.sparta.finalproject6.model.Post;
import com.sparta.finalproject6.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostLoveDto {
    private Post post;
    private User user;
}
