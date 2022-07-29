package com.sparta.finalproject6.repository;

import com.sparta.finalproject6.model.Love;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LoveRepository extends JpaRepository<Love, Long> {

    Optional<Love> findByPostIdAndUserId(Long postId,Long userId);
    void deleteAllByPostId(Long postId);

}
