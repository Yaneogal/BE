package com.sparta.finalproject6.service;

import com.sparta.finalproject6.dto.requestDto.ProfileUpdateRequestDto;
import com.sparta.finalproject6.dto.responseDto.*;
import com.sparta.finalproject6.model.Bookmark;
import com.sparta.finalproject6.model.Post;
import com.sparta.finalproject6.model.User;
import com.sparta.finalproject6.model.UserImg;
import com.sparta.finalproject6.repository.BookmarkRepository;
import com.sparta.finalproject6.repository.PostRepository;
import com.sparta.finalproject6.repository.ThemeCategoryRepository;
import com.sparta.finalproject6.repository.UserRepository;
import com.sparta.finalproject6.security.UserDetailsImpl;
import com.sparta.finalproject6.validator.UserValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.awt.print.Book;
import java.io.IOException;
import java.util.ArrayList;
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
    private final S3Service s3Service;
    private final UserValidator userValidator;

    // My Page Profile 조회
    public ProfileUpdateResponseDto getMyProfile(UserDetailsImpl userDetails) {
        if (userDetails == null) {
            throw new IllegalArgumentException("승인되지 않은 사용자 입니다.");
        }
        User found = userRepository.findByUsername(userDetails.getUsername()).orElseThrow(
                () -> new IllegalArgumentException("사용자를 찾을 수 없습니다.")
        );
        String nickname = found.getUsername();

        ProfileUpdateResponseDto responseDto = ProfileUpdateResponseDto.builder()
                .nickname(found.getNickname())
                .userImgUrl(found.getUserImgUrl())
                .userInfo(found.getUserInfo())
                .build();

        return responseDto;
    }

    // 회원정보 수정
    @Transactional
    public ProfileUpdateResponseDto updateProfile(
            MultipartFile file,
            ProfileUpdateRequestDto updateDto,
            UserDetailsImpl userDetails
    ) throws IOException {

        User user = userDetails.getUser();

        // 닉네임 중복검사용
        Optional<User> foundNickname = userRepository
                .findByNickname(updateDto.getNickname());

        String nickname = user.getNickname();
        if (updateDto.getNickname() != null) {
            // 변경하고자 하는 닉네임과 동일하면 유효성 검사하지 않음
            if (!updateDto.getNickname().equals(user.getNickname())){
                // 닉네임 중복 검사
                userValidator.checkNickname(foundNickname);

                // 닉네임 유효성 검사
                userValidator.checkNicknameIsValid(updateDto.getNickname());
            }
            nickname = updateDto.getNickname();
        }

        String userInfo = updateDto.getUserInfo();

        // 프로필 이미지를 직접 업로드 했을 경우
        if (file != null) {
            String userImgUrl = String.valueOf(s3Service.uploadFile(file));
            UserImg profileImg = new UserImg(userImgUrl);
            // userRepository.save(profileImg);
            user.setUserImgUrl(profileImg.getUserImgUrl());

        }

        user.setNickname(nickname);
        user.setUserInfo(userInfo);

        User savedUser = userRepository.save(user);

        return new ProfileUpdateResponseDto(
                savedUser.getId(),
                savedUser.getUserImgUrl(),
                savedUser.getNickname(),
                savedUser.getUserInfo()
        );
    }


    // 내가 작성한 포스트 리스트
    public MypageResponseDto getMyPostList (int pageNo, int sizeNo, UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        Pageable sortedByModifiedAtDesc = PageRequest.of(1, 6, Sort.by("modifiedAt").descending());

        List<MYPostListDto> postListDto = new ArrayList<>();
        List<Post> myPost = postRepository.findAllByUserOrderByCreatedAtDesc(user, sortedByModifiedAtDesc);


        for (Post post : myPost) {

            MYPostListDto postDto = MYPostListDto.builder()
                    .postId(post.getId())
                    .userId(post.getUser().getId())
                    .title(post.getTitle())
                    .imgUrl(post.getPlace().get(0).getImgUrl().get(0))
                    .content(post.getContent())
                    .regionCategory(post.getRegionCategory())
                    .priceCategory(post.getPriceCategory())
                    .themeCategory(post.getThemeCategories())
                    .loveCount(post.getLoveCount())
                    .createdAt(post.getCreatedAt())
                    .modifiedAt(post.getModifiedAt())
                    .build();
            postListDto.add(postDto);
        }

        return (MypageResponseDto) postListDto;
//        MypageResponseDto responseDto = MypageResponseDto.builder()
//                .userId(user.getId())
//                .nickname(user.getNickname())
//                .userImgUrl(user.getUserImgUrl())
//                .userInfo(user.getUserInfo())
//                .myPostList(postListDto)
//                .myBookmarkList(bookmarkListDto)
//                .build();
//
//        return responseDto;

    }


    // 내가 Bookmark 한 포스트 리스트
    public List<MyBookmarkListDto> getMyBookmark(int pageNo, int sizeNo, UserDetailsImpl userDetails) {

        User user = userDetails.getUser();
        Long userId = user.getId();

        Pageable sortedByModifiedAtDesc = PageRequest.of(pageNo, sizeNo, Sort.by("modifiedAt").descending());

        // 북마크 한 포스트 리스트
        List<MyBookmarkListDto> bookmarkList = new ArrayList<>();

        // 북마크 entity에서 북마크 한 포스트 가져오기
        List<Bookmark> bookmarks = bookmarkRepository.findAllByUserId(userDetails.getUser().getId());

        Pageable paging = PageRequest.of(0,6,Sort.Direction.DESC);

        for (Bookmark bookmark : bookmarks) {
            Optional<Post> postOptional = postRepository.findById(bookmark.getPostId());
            if (postOptional.isPresent()) {
                Post post = postOptional.get();
                post.setIsBookmark(true);

                MyBookmarkListDto myBookmarkListDto = MyBookmarkListDto.builder()
                        .postId(post.getId())
                        .userId(post.getUser().getId())
                        .title(post.getTitle())
                        .imgUrl(post.getPlace().get(0).getImgUrl().get(0))
                        .build();
                bookmarkList.add(myBookmarkListDto);
            }
        }
        return bookmarkList;
    }

    // Paging
    private Pageable getPageable(int pageNo) {
        Sort.Direction direction = Sort.Direction.DESC;
        Sort sort = Sort.by(direction, "id");
        return PageRequest.of(pageNo, 6, sort);
    }

}
