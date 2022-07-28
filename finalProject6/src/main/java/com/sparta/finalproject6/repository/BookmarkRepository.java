package com.sparta.finalproject6.repository;

import com.sparta.finalproject6.model.Bookmark;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {

    Optional<Bookmark> findByPostIdAndUserId(Long postId, Long userId);

    List<Bookmark> findAllByUserId(Long userId);

    void deleteAllByPostId(Long postId);

}
