package com.sparta.finalproject6.repository;

import com.sparta.finalproject6.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findAllByPostIdOrderByCreatedAtDesc(Long postId);
    List<Comment> findAllByPostId(Long postId);

}
