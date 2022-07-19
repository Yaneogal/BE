package com.sparta.finalproject6.service;

import com.sparta.finalproject6.dto.requestDto.PlaceRequestDto;
import com.sparta.finalproject6.dto.requestDto.PostRequestDto;
import com.sparta.finalproject6.dto.requestDto.ThemeCategoryDto;
import com.sparta.finalproject6.dto.responseDto.PlaceResponseDto;
import com.sparta.finalproject6.dto.responseDto.PostCommentResponseDto;
import com.sparta.finalproject6.dto.responseDto.PostDetailResponseDto;
import com.sparta.finalproject6.dto.responseDto.PostResponseDto;
import com.sparta.finalproject6.model.*;
import com.sparta.finalproject6.repository.*;
import com.sparta.finalproject6.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
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
    private final PlaceRepository placeRepository;
    private final BookmarkRepository bookmarkRepository;



     //전체 포스트 조회(검색기능추가)
    @Transactional(readOnly = true)
    public ResponseEntity<Slice<PostResponseDto>> getPosts(String keyword, Pageable pageable, UserDetailsImpl userDetails) {

        Slice<PostResponseDto> content = postRepository.keywordSearch(keyword, pageable);

        Long userId = userDetails.getUser().getId();

        content.forEach(c ->{
            Post post = postRepository.findById(c.getPostId())
                    .orElseThrow(() -> new IllegalArgumentException("post does not exist"));

            List<Place> place = placeRepository.findAllByPostId(post.getId());
            List<String> imgUrl = new ArrayList<>();
            for (int i = 0; i < place.size(); i++) {
                imgUrl.addAll(place.get(i).getImgUrl());
            }
            c.setImgUrl(imgUrl);

            System.out.println("imgUrl = " + imgUrl);

            Optional<Bookmark> bookmark = bookmarkRepository.findByPostIdAndUserId(post.getId(),userId);
            if(bookmark.isPresent()){
                post.setIsBookmark(true);
            }

            c.setBookmarkStatus(post.getIsBookmark());

            Optional<Love> love = loveRepository.findByPostIdAndUserId(post.getId(),userId);
            if(love.isPresent()){
                post.setIsLove(true);
            }

            c.setLoveStatus(post.getIsLove());

            List<ThemeCategoryDto> themeCategory = themeRepository.findByPost_Id(c.getPostId())
                    .stream()
                    .map(theme ->
                            new ThemeCategoryDto(theme.getThemeCategory()))
                    .collect(Collectors.toList());
            c.setThemeCategory(themeCategory);
        });

        return new ResponseEntity(content, HttpStatus.OK);
    }

//        List<Post> posts;
//        if (keyword != null) {
//            posts = postRepository.findSearchKeyword(keyword, pageable);
//        } else {
//            posts = postRepository.findAllByOrderByCreatedAtDesc(pageable);
//        }

//        Slice<Post> posts = postRepository.keywordSearch(keyword, pageable);

//        Long userId = userDetails.getUser().getId();
//        List<PostResponseDto> postList = new ArrayList<>();
//
//        Slice<PostResponseDto> postSlice = null;
//        for (Post post : posts) {
//            Optional<Love> love = loveRepository.findByPostIdAndUserId(post.getId(), userId);
//            if (love.isPresent()) {
//                post.setIsLove(true);
//            }
//
//            List<ThemeCategory> themes = post.getThemeCategories();
//            List<ThemeCategoryDto> themesToDto = themes.stream()
//                    .map(t ->
//                            new ThemeCategoryDto(t.getThemeCategory())
//                    )
//                    .collect(Collectors.toList());
//
//            post.getImgUrl().get(0);
//            PostResponseDto postResponseDto = PostResponseDto.builder()
//                    .postId(post.getId())
//                    .nickName(post.getUser().getNickname())
//                    .userImgUrl(post.getUser().getUserImgUrl())
//                    .title(post.getTitle())
//                    .imgUrl(post.getImgUrl())
//                    .content(post.getContent())
//                    .loveStatus(post.getIsLove())
//                    .regionCategory(post.getRegionCategory())
//                    .priceCategory(post.getPriceCategory())
//                    .themeCategory(themesToDto)
//                    .viewCount(post.getViewCount())
//                    .loveCount(post.getLoveCount())
//                    .bookmarkCount(post.getBookmarkCount())
//                    .createdAt(post.getCreatedAt())
//                    .modifiedAt(post.getModifiedAt())
//                    .build();
//
//            postList.add(postResponseDto);
//            postSlice = new SliceImpl<>(postList);
//        }


//    public Page<Post> getAllPosts(int page, int size, String sortBy, boolean isAsc) {
//        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
//        Sort sort = Sort.by(direction, sortBy);
//        org.springframework.data.domain.Pageable pageable = PageRequest.of(page, size, sort);
//
//        return postRepository.findAll(pageable);
//    }

    //필터 적용 게시글 조회
    @Transactional(readOnly = true)
    public ResponseEntity<Slice<PostResponseDto>> getFilterPosts(String region, String price, List<String> theme, Pageable pageable, UserDetailsImpl userDetails) {

        Slice<PostResponseDto> content = postRepository.filterSearch(region, price, theme, pageable, userDetails);

        Long userId = userDetails.getUser().getId();

        content.forEach(c -> {
            Post post = postRepository.findById(c.getPostId())
                    .orElseThrow(() -> new IllegalArgumentException("post does not exist"));
            List<Place> place = placeRepository.findAllByPostId(post.getId());
            List<String> imgUrl = new ArrayList<>();
            for (int i = 0; i < place.size(); i++) {
                imgUrl.addAll(place.get(i).getImgUrl());
            }
            c.setImgUrl(imgUrl);

            Optional<Bookmark> bookmark = bookmarkRepository.findByPostIdAndUserId(post.getId(),userId);
            if(bookmark.isPresent()){
                post.setIsBookmark(true);
            }

            c.setBookmarkStatus(post.getIsBookmark());

            Optional<Love> love = loveRepository.findByPostIdAndUserId(post.getId(),userId);
            if(love.isPresent()){
                post.setIsLove(true);
            }

            c.setLoveStatus(post.getIsLove());

            List<ThemeCategoryDto> themeCateroies = themeRepository.findByPost_Id(c.getPostId())
                    .stream()
                    .map(t ->
                            new ThemeCategoryDto(t.getThemeCategory()))
                    .collect(Collectors.toList());
            c.setThemeCategory(themeCateroies);
        });
        return new ResponseEntity(content, HttpStatus.OK);
    }


    // 포스트 상세 페이지
    @Transactional(readOnly = true)
    public ResponseEntity<PostDetailResponseDto> getPostDetail(Long postId, UserDetailsImpl userDetails) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new IllegalArgumentException("게시글이 존재하지 않습니다.")
        );

        post.viewCountUp();

        User user = userDetails.getUser();

        List<Comment> comments = commentRepository.findAllByPostId(postId);
        List<PostCommentResponseDto> commentList = new ArrayList<>();
        for (Comment comment : comments) {

            String createdAt = convertLocalTimeToTime(comment.getCreatedAt());

            PostCommentResponseDto postCommentResponseDto = PostCommentResponseDto.builder()
                    .commentId(comment.getId())
                    .userImgUrl(comment.getUserImgUrl())
                    .comment(comment.getComment())
                    .nickname(comment.getNickname())
                    .createdAt(createdAt)
                    .build();
            commentList.add(postCommentResponseDto);
        }

        //TODO : 게시글의 전체 좋아요 수랑 유저의 좋아요 유무 판단하기
        //postId와 userId를 넘겨서 좋아요가 존재하면 상태를 true로
        Optional<Love> love = loveRepository.findByPostIdAndUserId(postId, user.getId());
        if(love.isPresent()){
            post.setIsLove(true);
        }

        Optional<Bookmark> bookmark = bookmarkRepository.findByPostIdAndUserId(post.getId(),user.getId());
        if(bookmark.isPresent()){
            post.setIsBookmark(true);
        }

        List<ThemeCategory> themes = themeRepository.findByPost_Id(postId);
        List<ThemeCategoryDto> themesToDto = themes.stream()
                .map(t ->
                    new ThemeCategoryDto(t.getThemeCategory())
                )
                .collect(Collectors.toList());

        List<PlaceResponseDto> placeResponseDtos = new ArrayList<>();
        List<Place> place = placeRepository.findAllByPostId(post.getId());
        for (int i = 0; i < place.size(); i++) {

            placeResponseDtos.add(PlaceResponseDto.builder()
                    .address_name(place.get(i).getAddressName())
                    .category_group_code(place.get(i).getCategoryGroupCode())
                    .category_group_name(place.get(i).getCategoryGroupName())
                    .category_name(place.get(i).getCategoryName())
                    .distance(place.get(i).getDistance())
                    .phone(place.get(i).getPhone())
                    .place_name(place.get(i).getPlace_name())
                    .place_url(place.get(i).getPlace_url())
                    .road_address_name(place.get(i).getRoad_address_name())
                    .id(place.get(i).getId())
                    .x(place.get(i).getX())
                    .y(place.get(i).getY())
                    .imgUrl(place.get(i).getImgUrl())
                    .build());
        }

        PostDetailResponseDto detailResponseDto = PostDetailResponseDto.builder()
                .postId(post.getId())
                .nickname(post.getUser().getNickname())
                .userImgUrl(post.getUser().getUserImgUrl())
                .title(post.getTitle())
                .content(post.getContent())
                .regionCategory(post.getRegionCategory())
                .priceCategory(post.getPriceCategory())
                .themeCategory(themesToDto)
                .viewCount(post.getViewCount())
                .loveStatus(post.getIsLove())
                .loveCount(post.getLoveCount())
                .bookmarkCount(post.getBookmarkCount())
                .bookmarkStatus(post.getIsBookmark())
                .createdAt(post.getCreatedAt())
                .modifiedAt(post.getModifiedAt())
                .comments(commentList)
                .place(placeResponseDtos)
                .restroom(post.getRestroom())
                .restroomOption(post.getRestroomOption())
                .build();

        return new ResponseEntity<>(detailResponseDto, HttpStatus.OK);
    }

    // 조회수 Views counting
    @Transactional
    public int updateView(Long postId) {
        Optional<Post> post = postRepository.findById(postId);
        return postRepository.updateView(postId);
    }

    @Transactional
    public void addPost(UserDetailsImpl userDetails, PostRequestDto requestDto, List<PlaceRequestDto> placeRequestDto, List<MultipartFile> multipartFile) {

        User user = userRepository.findByUsername(userDetails.getUsername()).orElseThrow(
                () -> new IllegalArgumentException("유저가 존재하지 않습니다.")
        );

        Post post = Post.builder()
                .title(requestDto.getTitle())
                .content(requestDto.getContent())
                .regionCategory(requestDto.getRegionCategory())
                .priceCategory(requestDto.getPriceCategory())
                .user(userDetails.getUser())
                .restroom(requestDto.getRestroom())
                .restroomOption(requestDto.getRestroomOption())
                .build();

        postRepository.save(post);

        requestDto.getThemeCategories()
                .forEach(t -> {
                    themeService.saveTheme(t.getThemeCategory(), post);
                });

        int count = 0;

        for (int i = 0; i < placeRequestDto.size(); i++) {
            List<MultipartFile> files = new ArrayList<>();
//            List<Integer> imgOrder =placeRequestDto.get(i).getImgOrder();
//            for (int j = 0; j < imgOrder.size(); j++) {
//                files.add(multipartFile.get(imgOrder.get(j)-1));
//            }
            for (int j = 0; j < placeRequestDto.get(i).getImgCount(); j++) {
                files.add(multipartFile.get(count));
                count++;
            }

            List<Map<String, String>> imgResult = getImageList(files);

            List<String> imgUrls = new ArrayList<>(imgResult.size());
            List<String> imgFileNames = new ArrayList<>(imgResult.size());

            for(Map<String , String> getImage : imgResult){
                imgUrls.add(getImage.get("url"));
                imgFileNames.add(getImage.get("fileName"));
            }

            Place place = Place.builder()
                    .addressName(placeRequestDto.get(i).getAddress_name())
                    .categoryGroupCode(placeRequestDto.get(i).getCategory_group_code())
                    .categoryGroupName(placeRequestDto.get(i).getCategory_group_name())
                    .categoryName(placeRequestDto.get(i).getCategory_name())
                    .distance(placeRequestDto.get(i).getDistance())
                    .imgUrl(imgUrls)
                    .imgFileName(imgFileNames)
                    .id(placeRequestDto.get(i).getId())
                    .phone(placeRequestDto.get(i).getPhone())
                    .place_name(placeRequestDto.get(i).getPlace_name())
                    .place_url(placeRequestDto.get(i).getPlace_url())
                    .road_address_name(placeRequestDto.get(i).getRoad_address_name())
                    .x(placeRequestDto.get(i).getX())
                    .y(placeRequestDto.get(i).getY())
                    .post(post)
                    .build();

            placeRepository.save(place);
        }

    }

    // 포스트 수정
    @Transactional
    public void modifyPost(UserDetailsImpl userDetails, PostRequestDto requestDto, List<PlaceRequestDto> placeRequestDto, List<MultipartFile> multipartFile, Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 게시글입니다.")
        );
        validateUser(userDetails, post);

        //TODO : 장소가 늘어났을때랑 줄어들었을때 이미지랑 장소를 어떻게 해야할지 생각

        List<Place> places = placeRepository.findAllByPostId(postId);
        int count = 0;

        for (int i = 0; i < placeRequestDto.size(); i++) {

            List<Map<String, String>> imgResult = new ArrayList<>();
            List<MultipartFile> files = new ArrayList<>();

//            List<Integer> imgOrder =placeRequestDto.get(i).getImgOrder();
//            for (int k = 0; k < imgOrder.size(); k++) {
//                files.add(multipartFile.get(imgOrder.get(k)-1));
//            }

            for (int k = 0; k < placeRequestDto.get(i).getImgCount(); k++) {
                files.add(multipartFile.get(count));
                count++;
            }

            //장소 수 가 아직 기존장소 수 보다 작을때
            if(i < (places.size())) {
                imgResult = updateImage(places.get(i),files);
                List<String> imgUrls = new ArrayList<>(imgResult.size());
                List<String> imgFileNames = new ArrayList<>(imgResult.size());

                for (Map<String, String> getImage : imgResult) {
                    imgUrls.add(getImage.get("url"));
                    imgFileNames.add(getImage.get("fileName"));
                }

                places.get(i).updatePlace(placeRequestDto.get(i));
                places.get(i).updatePlaceImage(imgUrls, imgFileNames);

                //수정된 내용이 기존내용보다 숫자가 적을때
                if(i == (placeRequestDto.size()-1)){
                    for (int j = i+1; j < places.size(); j++) {
                        placeRepository.delete(places.get(j));
                    }
                }
            }

            //수정해서 장소가 더 늘어났을때는 등록해주기
            else {
                imgResult = getImageList(files);
                List<String> imgUrls = new ArrayList<>(imgResult.size());
                List<String> imgFileNames = new ArrayList<>(imgResult.size());
                for (Map<String, String> getImage : imgResult) {
                    imgUrls.add(getImage.get("url"));
                    imgFileNames.add(getImage.get("fileName"));
                }

                Place place = Place.builder()
                        .addressName(placeRequestDto.get(i).getAddress_name())
                        .categoryGroupCode(placeRequestDto.get(i).getCategory_group_code())
                        .categoryGroupName(placeRequestDto.get(i).getCategory_name())
                        .categoryName(placeRequestDto.get(i).getCategory_name())
                        .distance(placeRequestDto.get(i).getDistance())
                        .imgUrl(imgUrls)
                        .imgFileName(imgFileNames)
                        .id(placeRequestDto.get(i).getId())
                        .phone(placeRequestDto.get(i).getPhone())
                        .place_name(placeRequestDto.get(i).getPlace_name())
                        .place_url(placeRequestDto.get(i).getPlace_url())
                        .road_address_name(placeRequestDto.get(i).getRoad_address_name())
                        .x(placeRequestDto.get(i).getX())
                        .y(placeRequestDto.get(i).getY())
                        .post(post)
                        .build();
                placeRepository.save(place);
            }
        }

        post.update(requestDto);

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
            //게시물 삭제시 좋아요 햇던 유저한테서도 삭제
            loveRepository.deleteAllByPostId(postId);

            bookmarkRepository.deleteAllByPostId(postId);
            //게시글 삭제시 장소도 삭제
            placeRepository.deleteAllByPostId(postId);

            postRepository.delete(post);

        }
        catch(IllegalArgumentException e){
            System.out.println(e.getMessage());
        }

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
    public List<Map<String, String>> updateImage(Place place, List<MultipartFile> images) {
        List<Map<String, String>> imagesResult = new ArrayList<>();
        Map<String, String> mapImageResult = new HashMap<>();

        //기존 이미지 제거
        for (int i = 0; i < place.getImgFileName().size(); i++) {
            s3Service.deleteFile(place.getImgFileName().get(i));
        }
        //새로 들어온 이미지들 업로드
        for (int i = 0; i < images.size(); i++) {
            mapImageResult = s3Service.uploadFile(images.get(i));
            imagesResult.add(mapImageResult);
        }
        return imagesResult;
    }

    public static String convertLocalTimeToTime(LocalDateTime localDateTime) {

        LocalDateTime now = LocalDateTime.now();

        long SEC = 60;
        long MIN = 3600;
        long HOUR = 86400;
        long DAY = 2678400;
        long MONTH = 32140800;

        long diffTime = localDateTime.until(now, ChronoUnit.SECONDS);

        if (diffTime <= SEC) {
            return diffTime + "초 전";
        }
        if (diffTime < MIN) {
            return diffTime / SEC + "분 전";
        }
        if (diffTime < HOUR) {
            return diffTime / MIN+ "시간 전";
        }
        if (diffTime < DAY) {
            return diffTime / HOUR + "일 전";
        }
        if (diffTime < MONTH) {
            return diffTime / DAY + "개월 전";
        }
        return diffTime / MONTH + "년 전";
    }
}

