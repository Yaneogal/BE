package com.sparta.finalproject6.service;

import com.sparta.finalproject6.dto.requestDto.ProfileUpdateRequestDto;
import com.sparta.finalproject6.dto.requestDto.ThemeCategoryDto;
import com.sparta.finalproject6.dto.responseDto.MyPagePostResponseDto;
import com.sparta.finalproject6.dto.responseDto.ProfileUpdateResponseDto;
import com.sparta.finalproject6.model.*;
import com.sparta.finalproject6.repository.*;
import com.sparta.finalproject6.security.UserDetailsImpl;
import com.sparta.finalproject6.validator.UserValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MypageService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final BookmarkRepository bookmarkRepository;
    private final ThemeCategoryRepository themeRepository;
    private final PlaceRepository placeRepository;
    private final S3Service s3Service;
    private final UserValidator userValidator;

    //다른 사람의 페이지 && 내 페이지
    @Transactional(readOnly = true)
    public ProfileUpdateResponseDto getYourProfile(Long userId, UserDetailsImpl userDetails) {

        boolean isMine;
        if (userId.equals(userDetails.getUser().getId())) {
            isMine = true;
        } else {
            isMine = false;
        }

        if (userDetails.getUser().getId() == null) {
            throw new IllegalArgumentException("승인되지 않은 사용자 입니다.");
        }
//        User found = userRepository.findByUsername(userDetails.getUsername()).orElseThrow(
//                () -> new IllegalArgumentException("사용자를 찾을 수 없습니다.")
//        );
        User found = userRepository.findById(userId).orElseThrow(
                () -> new IllegalArgumentException("사용자를 찾을 수 없습니다.")
        );

        return ProfileUpdateResponseDto.builder()
                .nickname(found.getNickname())
                .userId(found.getId())
                .userImgUrl(found.getUserImgUrl())
                .userInfo(found.getUserInfo())
                .totalPoint(found.getTotalPoint())
                .grade(found.getGrade())
                .totalPoint(found.getTotalPoint())
                .isMine(isMine)
                .build();
    }

    //마이 페이지 조회
    @Transactional(readOnly = true)
    public ProfileUpdateResponseDto getMyProfile(UserDetailsImpl userDetails) {

        Long userId = userDetails.getUser().getId();
        if (userId == null) {
            throw new IllegalArgumentException("승인되지 않은 사용자 입니다.");
        }

        User found = userRepository.findById(userId).orElseThrow(
                () -> new IllegalArgumentException("사용자를 찾을 수 없습니다.")
        );

        return ProfileUpdateResponseDto.builder()
                .nickname(found.getNickname())
                .userId(found.getId())
                .userImgUrl(found.getUserImgUrl())
                .userInfo(found.getUserInfo())
                .totalPoint(found.getTotalPoint())
                .grade(found.getGrade())
                .totalPoint(found.getTotalPoint())
                .isMine(true)
                .build();
    }

    // 회원정보 수정
    @Transactional
    public void updateProfile(
            MultipartFile multipartFile,
            ProfileUpdateRequestDto updateDto,
            UserDetailsImpl userDetails
    ) throws IOException {

        User user = userRepository.findById(userDetails.getUser().getId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        // 닉네임 중복검사용
        Optional<User> foundNickname = userRepository
                .findByNickname(updateDto.getNickname());

        String nickname = user.getNickname();
        if (updateDto.getNickname() != null) {
            // 변경하고자 하는 닉네임과 동일하면 유효성 검사하지 않음
            if (!updateDto.getNickname().equals(nickname)) {
                // 닉네임 중복 검사
                userValidator.checkNickname(foundNickname);

                // 닉네임 유효성 검사
                userValidator.checkNicknameIsValid(updateDto.getNickname());
            }
//            nickname = updateDto.getNickname();

            String userImgUrl = null;

            // 프로필 이미지를 직접 업로드 했을 경우
            if (multipartFile != null) {
                Map<String, String> imgUrl = s3Service.uploadFile(multipartFile);
                userImgUrl = imgUrl.get("url");
                UserImg profileImg = new UserImg(userImgUrl);
                user.setUserImgUrl(profileImg.getUserImgUrl());
            }

            user.updateUser(updateDto.getNickname(), updateDto.getUserInfo(), userImgUrl);

        }

    }


    // 내가 작성한 포스트 리스트 조회
    @Transactional(readOnly = true)
    public ResponseEntity<Slice<MyPagePostResponseDto>> getYourWrittenPosts(Long userId, UserDetailsImpl userDetails, Pageable pageable) {
        // Long userId = userDetails.getUser().getId();
        Slice<MyPagePostResponseDto> content = postRepository.getMyWrittenPosts(userId, pageable);

        content.forEach(c -> {
            Post post = postRepository.findById(c.getPostId())
                    .orElseThrow(() -> new IllegalArgumentException("post does not exist"));
            
            List<Place> place = placeRepository.findAllByPostId(post.getId());
            String imgUrl = place.get(0).getImgUrl().get(0);

            c.setImgUrl(imgUrl);

            List<ThemeCategoryDto> themeCategory = themeRepository.findByPost_Id(c.getPostId())
                    .stream()
                    .map(theme ->
                            new ThemeCategoryDto(theme.getThemeCategory()))
                    .collect(Collectors.toList());
            c.setThemeCategory(themeCategory);
        });

        return new ResponseEntity<>(content, HttpStatus.OK);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<Slice<MyPagePostResponseDto>> getMyWrittenPosts(UserDetailsImpl userDetails, Pageable pageable) {
         Long userId = userDetails.getUser().getId();
        Slice<MyPagePostResponseDto> content = postRepository.getMyWrittenPosts(userId, pageable);

        content.forEach(c -> {
            Post post = postRepository.findById(c.getPostId())
                    .orElseThrow(() -> new IllegalArgumentException("post does not exist"));

            List<Place> place = placeRepository.findAllByPostId(post.getId());
            String imgUrl = place.get(0).getImgUrl().get(0);

            c.setImgUrl(imgUrl);

            List<ThemeCategoryDto> themeCategory = themeRepository.findByPost_Id(c.getPostId())
                    .stream()
                    .map(theme ->
                            new ThemeCategoryDto(theme.getThemeCategory()))
                    .collect(Collectors.toList());
            c.setThemeCategory(themeCategory);
        });

        return new ResponseEntity<>(content, HttpStatus.OK);
    }



    //내가 북마크한 게시글 조회
    @Transactional(readOnly = true)
    public ResponseEntity<Slice<MyPagePostResponseDto>> getYourBookmarkPosts(Long userId, UserDetailsImpl userDetails, Pageable pageable) {

        // Long userId = userDetails.getUser().getId();

        List<Bookmark> bookmarks = bookmarkRepository.findAllByUserId(userId);

        List<Long> postsId = bookmarks.stream()
                .map(Bookmark::getPostId)
                .collect(Collectors.toList());

        Slice<MyPagePostResponseDto> content = postRepository.getMyBookmarkPosts(postsId, pageable);

        content.forEach(c -> {
            Post post = postRepository.findById(c.getPostId())
                    .orElseThrow(() -> new IllegalArgumentException("post does not exist"));

            List<Place> place = placeRepository.findAllByPostId(post.getId());
            String imgUrl = place.get(0).getImgUrl().get(0);

            c.setImgUrl(imgUrl);

            List<ThemeCategoryDto> themeCategory = themeRepository.findByPost_Id(c.getPostId())
                    .stream()
                    .map(theme ->
                            new ThemeCategoryDto(theme.getThemeCategory()))
                    .collect(Collectors.toList());
            c.setThemeCategory(themeCategory);
        });

        return new ResponseEntity<>(content, HttpStatus.OK);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<Slice<MyPagePostResponseDto>> getMyBookmarkPosts(UserDetailsImpl userDetails, Pageable pageable) {

         Long userId = userDetails.getUser().getId();

        List<Bookmark> bookmarks = bookmarkRepository.findAllByUserId(userId);

        List<Long> postsId = bookmarks.stream()
                .map(Bookmark::getPostId)
                .collect(Collectors.toList());

        Slice<MyPagePostResponseDto> content = postRepository.getMyBookmarkPosts(postsId, pageable);

        content.forEach(c -> {
            Post post = postRepository.findById(c.getPostId())
                    .orElseThrow(() -> new IllegalArgumentException("post does not exist"));

            List<Place> place = placeRepository.findAllByPostId(post.getId());
            String imgUrl = place.get(0).getImgUrl().get(0);

            c.setImgUrl(imgUrl);

            List<ThemeCategoryDto> themeCategory = themeRepository.findByPost_Id(c.getPostId())
                    .stream()
                    .map(theme ->
                            new ThemeCategoryDto(theme.getThemeCategory()))
                    .collect(Collectors.toList());
            c.setThemeCategory(themeCategory);
        });

        return new ResponseEntity<>(content, HttpStatus.OK);
    }

}
