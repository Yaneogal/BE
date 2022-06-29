package com.sparta.finalproject6.repository;

import com.sparta.finalproject6.model.Love;
import com.sparta.finalproject6.model.Post;
import com.sparta.finalproject6.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LoveRepository extends JpaRepository<Love, Long> {
    Optional<Love> findByPostAndUser(Post post, User user);
    List<Love> findAllByPostId(Long postId);
}
