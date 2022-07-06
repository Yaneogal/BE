package com.sparta.finalproject6.repository;

import com.sparta.finalproject6.model.ThemeCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ThemeCategoryRepository extends JpaRepository<ThemeCategory, Long> {
    List<ThemeCategory> findByPost_Id(Long postId);
    void deleteByPost_Id(Long postId);

}
