package com.sparta.finalproject6.service;

import com.sparta.finalproject6.model.Post;
import com.sparta.finalproject6.model.ThemeCategory;
import com.sparta.finalproject6.repository.PostRepository;
import com.sparta.finalproject6.repository.ThemeCategoryRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Getter
@RequiredArgsConstructor
@Service
public class ThemeCategoryService {

    private final ThemeCategoryRepository themeRepository;
    private final PostRepository postRepository;

    @Transactional
    public void saveTheme(String themeCategory, Post post) {
        ThemeCategory theme = new ThemeCategory(themeCategory, post);
        themeRepository.save(theme);
    }
}
