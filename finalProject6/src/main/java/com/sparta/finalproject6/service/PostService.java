package com.sparta.finalproject6.service;

import com.sparta.finalproject6.dto.requestDto.PostRequestDto;
import com.sparta.finalproject6.dto.requestDto.ThemeCategoryDto;
import com.sparta.finalproject6.dto.responseDto.LoveResponseDto;
import com.sparta.finalproject6.dto.responseDto.PostCommentResponseDto;
import com.sparta.finalproject6.dto.responseDto.PostResponseDto;
import com.sparta.finalproject6.model.*;
import com.sparta.finalproject6.repository.*;
import com.sparta.finalproject6.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {
    
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final LoveRepository loveRepository;
    private final S3Service s3Service;
    private final ThemeCategoryRepository themeRepository;
    private final ThemeCategoryService themeService;


    // 전체 포스트 조회
    @Transactional(readOnly = true)
    public ResponseEntity<PostResponseDto> getPosts(Pageable pageable, UserDetailsImpl userDetails) {
        List<Post> posts = postRepository.findAllByOrderByCreatedAtDesc(pageable);

        Long userId = userDetails.getUser().getId();
        List<PostResponseDto> postList = new ArrayList<>();

        for (Post post : posts) {
//            List<Love> postLoves = loveRepository.findAllByPostId(post.getId()); //해당 게시글의 종아요 목록을 받아온다.
//            List<LoveResponseDto> loveUserList = new ArrayList<>(); //게시글의 좋아요를 누른 유저의 목록을 주기 위한 Dto??
//            for (Love love : postLoves) {
//                LoveResponseDto loveResponseDto = new LoveResponseDto(userId); //그런데 로그인한 사용자의 정보를 주는 이유는 뭘까요??
//                loveUserList.add(loveResponseDto); //로그인한 사용자의 정보니 같은 사용자 id만 들어가는 건가요??
//            }
            Optional<Love> love = loveRepository.findByPostIdAndUserId(post.getId(),userId);
            if(love.isPresent()){
                post.setIsLove(true);
            }
            post.getImgUrl().get(0);
            PostResponseDto postResponseDto = PostResponseDto.builder()
                    .postId(post.getId())
                    .title(post.getTitle())
                    .imgUrl(post.getImgUrl())
                    .content(post.getContent())
                    .loveStatus(post.getIsLove())
                    .regionCategory(post.getRegionCategory())
                    .priceCategory(post.getPriceCategory())
                    .viewCount(post.getViewCount())
                    .loveCount(post.getLoveCount())
                    .bookmarkCount(post.getBookmarkCount())
                    .createdAt(post.getCreatedAt())
                    .modifiedAt(post.getModifiedAt())
                    .build();

            postList.add(postResponseDto);
        }
        return new ResponseEntity(postList, HttpStatus.OK);
    }

//    public Page<Post> getAllPosts(int page, int size, String sortBy, boolean isAsc) {
//        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
//        Sort sort = Sort.by(direction, sortBy);
//        org.springframework.data.domain.Pageable pageable = PageRequest.of(page, size, sort);
//
//        return postRepository.findAll(pageable);
//    }


    // 포스트 상세 페이지
    @Transactional
    public ResponseEntity<PostResponseDto> getPostDetail(Long postId , UserDetailsImpl userDetails) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new IllegalArgumentException("게시글이 존재하지 않습니다.")
        );

        post.getImgUrl().get(0);

        post.viewCountUp();

        User user = userDetails.getUser();

        List<Comment> comments = commentRepository.findAllByPostId(postId);
        List<PostCommentResponseDto> commentList = new ArrayList<>();
        for (Comment comment : comments) {
            PostCommentResponseDto postCommentResponseDto = PostCommentResponseDto.builder()
                    .commentId(comment.getId())
                    .userImgUrl(comment.getPost().getUser().getUserImgUrl())
                    .comment(comment.getComment())
                    .nickname(comment.getNickname())
                    .build();
            commentList.add(postCommentResponseDto);
        }

        //TODO : 게시글의 전체 좋아요 수랑 유저의 좋아요 유무 판단하기
        //postId와 userId를 넘겨서 좋아요가 존재하면 상태를 true로
        Optional<Love> love = loveRepository.findByPostIdAndUserId(postId, user.getId());
        if(love.isPresent()){
            post.setIsLove(true);
        }

//        List<Love> loves = post.getLoves();
//        List<LoveResponseDto> loveUserList = new ArrayList<>();
//        for (Love love : loves) {
////            Long userId = love.getUserId();
//            LoveResponseDto loveResponseDto = new LoveResponseDto(love.getId());
//            loveUserList.add(loveResponseDto);
//        }

        //상세페이지 조회시 테마 카테고리 동반 조회
//        List<ThemeCategory> themes = themeRepository.findByPost_Id(postId);
//        List<String> themesToString = new ArrayList<>();
//        themes.forEach(t -> {
//            themesToString.add(t.getThemeCategory());
//        });

        List<ThemeCategory> themes = themeRepository.findByPost_Id(postId);
        List<ThemeCategoryDto> themesToDto = themes.stream()
                .map(t ->
                    new ThemeCategoryDto(t.getThemeCategory())
                )
                .collect(Collectors.toList());

        PostResponseDto detailResponseDto = PostResponseDto.builder()
                .postId(post.getId())
                .title(post.getTitle())
                .imgUrl(post.getImgUrl())
                .content(post.getContent())
                .regionCategory(post.getRegionCategory())
                .priceCategory(post.getPriceCategory())
                .themeCategory(themesToDto)
                .viewCount(post.getViewCount())
                .loveStatus(post.getIsLove())
                .loveCount(post.getLoveCount())
                .bookmarkCount(post.getBookmarkCount())
                .createdAt(post.getCreatedAt())
                .modifiedAt(post.getModifiedAt())
                .comments(commentList)
//                .loves(loveUserList)
                .build();

        return new ResponseEntity<>(detailResponseDto, HttpStatus.OK);
    }

    // 조회수 Views counting
    @Transactional
    public int updateView(Long postId) {
        Optional<Post> post = postRepository.findById(postId);
        return postRepository.updateView(postId);
    }
    //  포스트 등록
    @Transactional
    public void addPost(UserDetailsImpl userDetails, PostRequestDto requestDto, List<MultipartFile> multipartFile) {

        User user = userRepository.findByUsername(userDetails.getUsername()).orElseThrow(
                () -> new IllegalArgumentException("유저가 존재하지 않습니다.")
        );

        List<Map<String, String>> imgResult = getImageList(multipartFile);
        List<String> imgUrls = new ArrayList<>(imgResult.size());
        List<String> imgFileNames = new ArrayList<>(imgResult.size());

        for(Map<String , String> getImage : imgResult){
            imgUrls.add(getImage.get("url"));
            imgFileNames.add(getImage.get("fileName"));
        }

        Post post = Post.builder()
                .title(requestDto.getTitle())
                .content(requestDto.getContent())
                .regionCategory(requestDto.getRegionCategory())
                .priceCategory(requestDto.getPriceCategory())
                .user(userDetails.getUser())
                .imgUrl(imgUrls)
                .imgFileName(imgFileNames)
                .build();

        postRepository.save(post);

        // post 등록시 테마 카테고리 복수 저장 로직.
//        requestDto.getThemeCategories()
//                .forEach(t -> {
//                    themeRepository.save(new ThemeCategory(t, post));
//                });

        postRepository.save(post);
        requestDto.getThemeCategories()
                .forEach(t -> {
                    themeService.saveTheme(t.getThemeCategory(), post);
                });

    }

    // 포스트 수정
    @Transactional
    public void modifyPost(UserDetailsImpl userDetails,PostRequestDto requestDto , List<MultipartFile> multipartFile, Long postId){
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 게시글입니다.")
        );
        validateUser(userDetails,post);

        List<Map<String, String>> imgResult = new ArrayList<>();
        List<String> imgUrls = new ArrayList<>();
        List<String> imgFileNames = new ArrayList<>();

        if(!multipartFile.isEmpty()){
            imgResult = updateImage(post,multipartFile);
            for(Map<String , String> getImage : imgResult){
                imgUrls.add(getImage.get("url"));
                imgFileNames.add(getImage.get("fileName"));
            }
        }

        //테마 카테고리 수정 로직
//        List<ThemeCategory> themeCategories = themeRepository.findByPost_Id(postId);
//        ThemeCategory theme = new ThemeCategory();
//
//        requestDto.getThemeCategories()
//                .forEach(theme::update);

        post.update(requestDto,imgUrls,imgFileNames);

        //테마 카테고리 수정 로직
        themeRepository.deleteByPost_Id(postId);
        requestDto.getThemeCategories()
                .forEach(t -> {
                    themeService.saveTheme(t.getThemeCategory(), post);
                });
    }


    // 포스트 삭제
    @Transactional
    public void deletePost(UserDetailsImpl userDetails, Long postId){
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 게시글입니다.")
        );
        //테마카테고리 삭제 로직
        themeRepository.deleteByPost_Id(postId);

        try{
            validateUser(userDetails,post);
            for (int i = 0; i < post.getImgUrl().size(); i++) {
                s3Service.deleteFile(post.getImgFileName().get(i));
            }
            postRepository.delete(post);
            //게시물 삭제시 좋아요 햇던 유저한테서도 삭제
            loveRepository.deleteAllByPostId(postId);
        }
        catch(IllegalArgumentException e){
            System.out.println(e.getMessage());
        }

        //테마카테고리 삭제 로직
//        themeRepository.deleteByPost_Id(postId);

    }


    private void validateUser(UserDetailsImpl userDetails, Post post) {
        log.info("userDetails = {}", userDetails);
        if (!post.getUser().getUsername().equals(userDetails.getUsername())) {
            throw new IllegalArgumentException("게시글 작성자만 조작할 수 있습니다.");
        }
    }

    //이미지를 여러장 받기위한 메서드
    public List<Map<String , String>> getImageList(List<MultipartFile> images){
        List<Map<String,String>> imagesResult = new ArrayList<>();
        Map<String,String> mapImageResult = new HashMap<>();

        for (int i = 0; i <images.size(); i++) {
            mapImageResult = s3Service.uploadFile(images.get(i));
            imagesResult.add(mapImageResult);
        }
        return imagesResult;
    }

    //포스트 수정 API에서 이미지 수정을 위한 메서드
    public List<Map<String, String>> updateImage(Post post, List<MultipartFile> images) {
        List<Map<String, String>> imagesResult = new ArrayList<>();
        Map<String, String> mapImageResult = new HashMap<>();

        for (int i = 0; i < post.getImgUrl().size(); i++) {
            s3Service.deleteFile(post.getImgFileName().get(i));
        }
        for (int i = 0; i < images.size(); i++) {
            mapImageResult = s3Service.uploadFile(images.get(i));
            imagesResult.add(mapImageResult);
        }
        return imagesResult;
    }
}

