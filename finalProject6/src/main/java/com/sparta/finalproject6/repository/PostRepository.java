package com.sparta.finalproject6.repository;

import com.sparta.finalproject6.model.Post;
import com.sparta.finalproject6.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("select p from Post p")
    Page<Post> findAllPosts(Pageable pageable);

    List<Post> findAllByOrderByCreatedAtDesc(Pageable pageable);
    Page<Post> findByUserOrderByCreatedAtDesc(User user, Pageable pageable);

    List<Post> findAllByUserOrderByCreatedAtDesc(User user, Pageable pageable);

    Optional<Post> findById(Long postId);

    @Modifying
    @Query("update Post p set p.view = p.view + 1 where p.id = :id ")
    int updateView(Long id);


}
