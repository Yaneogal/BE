package com.sparta.finalproject6.repository;

import com.sparta.finalproject6.model.Post;
import com.sparta.finalproject6.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;


import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long>, PostRepositoryCustom {

//    @Query("select p from Post p")
//    Page<Post> findAllPosts(Pageable pageable);
//
//    @Query("select p from Post p")
//    List<Post> findAllByOrderByCreatedAtDesc(Pageable pageable);

//    @Query("select p from Post p left join fetch ThemeCategory t on p.id = t.post.id where p.title " +
//            "like %:keyword% or p.content like %:keyword% " +
//            "or p.regionCategory like %:keyword% " +
//            "or p.priceCategory like %:keyword% " +
//            "or t.themeCategory like %:keyword%")
//    List<Post> findSearchKeyword(String keyword, Pageable pageable);

    List<Post> findAllByOrderByCreatedAtDesc(Pageable pageable);
    Page<Post> findByUserOrderByCreatedAtDesc(User user, Pageable pageable);

    @EntityGraph(attributePaths = "place")
    List<Post> findAllByUserOrderByCreatedAtDesc(User user);

    @EntityGraph(attributePaths = "themeCategories")
    List<Post> findAllByUserIdOrderByCreatedAtDesc(Long userId);

    @EntityGraph(attributePaths = "restroomOption")
    Optional<Post> findById(Long postId);

    @Modifying
    @Query("update Post p set p.view = p.view + 1 where p.id = :id ")
    int updateView(Long id);

}
