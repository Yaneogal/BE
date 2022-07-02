package com.sparta.finalproject6.repository;

import com.sparta.finalproject6.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.awt.print.Pageable;
import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("select p from Post p left join ThemeCategory t on p.id = t.id")
    List<Post> findAllPosts(Pageable pageable);


}
