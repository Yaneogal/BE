package com.sparta.finalproject6.repository;

import com.sparta.finalproject6.model.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

//    Page<Comment> findAllByPostId(Long postId, Pageable pageable);

    Page<Comment> findAllByPostIdOrderByCreatedAtDesc(Long postId, Pageable pageable);
    List<Comment> findAllByPostId(Long postId);


}
