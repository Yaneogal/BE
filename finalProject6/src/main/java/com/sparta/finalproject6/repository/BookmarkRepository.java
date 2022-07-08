package com.sparta.finalproject6.repository;

import com.sparta.finalproject6.model.Bookmark;
import com.sparta.finalproject6.model.Post;
import com.sparta.finalproject6.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.awt.print.Book;
import java.util.Optional;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {

    Optional<Bookmark> findByPostIdAndUserId(Long postId, Long userId);

    void deleteAllByPostId(Long postId);

}
