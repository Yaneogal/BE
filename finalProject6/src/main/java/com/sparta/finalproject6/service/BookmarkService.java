package com.sparta.finalproject6.service;


import com.sparta.finalproject6.model.Bookmark;
import com.sparta.finalproject6.model.Post;
import com.sparta.finalproject6.model.User;
import com.sparta.finalproject6.repository.BookmarkRepository;
import com.sparta.finalproject6.repository.PostRepository;
import com.sparta.finalproject6.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookmarkService {

    private final BookmarkRepository bookmarkRepository;
    private final PostRepository postRepository;

    @Transactional
    public Boolean bookmark(Long postId, UserDetailsImpl userDetails){
        User user = userDetails.getUser();
        Long userId = user.getId();

        Post post = postRepository.findById(postId).orElseThrow(
                ()->new IllegalArgumentException("게시글이 존재하지 않습니다.")
        );
        Optional<Bookmark> bookmark = bookmarkRepository.findByPostIdAndUserId(postId,userId);

        boolean countUp = true;

        if(bookmark.isPresent()){
            countUp = false;
            bookmarkRepository.deleteById(bookmark.get().getId());
        }
        else{
            Bookmark addBookmark = new Bookmark(postId,userId);
            bookmarkRepository.save(addBookmark);
        }

        post.updateBookmarkCount(countUp);

        return countUp;
    }
}
