package com.sparta.finalproject6.repository;

import com.sparta.finalproject6.model.Love;
import com.sparta.finalproject6.model.Post;
import com.sparta.finalproject6.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LoveRepository extends JpaRepository<Love, Long> {

    //TODO : postId와 userId로 해당유저가 postId의 게시글에 좋아요를 햇는지 안햇는지체크
    Optional<Love> findByPostIdAndUserId(Long postId,Long userId);

    void deleteAllByPostId(Long postId);
    List<Love> findAllByPostId(Long postId);
}
