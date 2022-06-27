package com.sparta.finalproject6.repository;

import com.sparta.finalproject6.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
