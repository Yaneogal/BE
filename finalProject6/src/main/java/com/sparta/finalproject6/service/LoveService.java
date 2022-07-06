package com.sparta.finalproject6.service;


import com.sparta.finalproject6.model.Love;
import com.sparta.finalproject6.model.Post;
import com.sparta.finalproject6.model.User;
import com.sparta.finalproject6.repository.LoveRepository;
import com.sparta.finalproject6.repository.PostRepository;
import com.sparta.finalproject6.repository.UserRepository;
import com.sparta.finalproject6.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoveService {

    private final LoveRepository loveRepo;

    private final UserRepository userRepo;
    private final PostRepository postRepo;


    //좋아요 등록 or 취소
    //이미 좋아요가 되어있으면 취소하기
    @Transactional
    public void love(UserDetailsImpl userDetails , Long postId){
        HttpStatus result = HttpStatus.OK;

        User user = userRepo.findByUsername(userDetails.getUsername()).orElseThrow(
                ()-> new IllegalArgumentException("로그인 해주세요.")
        );

        Long userId = user.getId();
        boolean countUp = true;

        Optional<Love> love = loveRepo.findByPostIdAndUserId(postId , userId);

        //넘겨받은 postId의 게시글이 존재하지 않을경우
        Post post = postRepo.findById(postId).orElseThrow(
                () -> new IllegalArgumentException("게시글이 존재하지 않습니다.")
        );

        // userId의 유저가 postId의 게시글에 좋아요를 했다면 좋아요 취소 = DB에서 제거
        if(love.isPresent()){
            //좋아요 햇던 기록 삭제
            loveRepo.deleteById(love.get().getId());
            countUp = false;
        }
        else{
            Love addLove = new Love(postId , userId);
            loveRepo.save(addLove);
        }
        post.updateLikeCount(countUp);

    }
}
