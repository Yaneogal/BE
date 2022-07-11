package com.sparta.finalproject6.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.util.StringUtils;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.finalproject6.dto.responseDto.PostResponseDto;
import com.sparta.finalproject6.dto.responseDto.QPostResponseDto;
import com.sparta.finalproject6.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;

import java.util.List;

import static com.sparta.finalproject6.model.QPost.post;
import static com.sparta.finalproject6.model.QThemeCategory.themeCategory1;

@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepositoryCustom{

    private final JPAQueryFactory queryFactory;

//    @Override
//    public Slice<Post> keywordSearch(String keyword, Pageable pageable) {
//        List<Post> content = queryFactory
//                .selectFrom(post)
//                .leftJoin(themeCategory1)
//                .on(post.id.eq(themeCategory1.post.id))
//                .where(titleSearch(keyword)
//                        .or(regionSearch(keyword))
//                        .or(priceSearch(keyword))
//                        .or(themeSearch(keyword)))
//                .offset(pageable.getOffset())
//                .limit(pageable.getPageSize())
//                .fetch();
//
//        return new SliceImpl<>(content);
//    }

    @Override
    public Slice<PostResponseDto> keywordSearch(String keyword, Pageable pageable) {
        List<PostResponseDto> content = queryFactory
                .select(new QPostResponseDto(
                        post.id,
                        post.user.nickname,
                        post.user.userImgUrl,
                        post.title,
                        post.content,
                        post.bookmarkCount,
                        post.loveCount,
                        post.regionCategory,
                        post.priceCategory,
                        post.createdAt,
                        post.modifiedAt
                ))
                .from(post)
                .leftJoin(post.user)
                .leftJoin(themeCategory1)
                .on(post.id.eq(themeCategory1.post.id))
                .where(titleSearch(keyword)
                        .or(regionSearch(keyword))
                        .or(priceSearch(keyword))
                        .or(themeSearch(keyword)))
                .groupBy(post.id)
                .orderBy(post.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return new SliceImpl<>(content);
    }

    private BooleanExpression titleSearch(String keyword) {
        return StringUtils.isNullOrEmpty(keyword) ? null : post.title.contains(keyword);
    }

    private BooleanExpression regionSearch(String keyword) {
        return StringUtils.isNullOrEmpty(keyword) ? null : post.regionCategory.contains(keyword);
    }

    private BooleanExpression priceSearch(String keyword) {
        return StringUtils.isNullOrEmpty(keyword) ? null : post.priceCategory.contains(keyword);
    }

    private BooleanExpression themeSearch(String keyword) {
        return StringUtils.isNullOrEmpty(keyword) ? null : themeCategory1.themeCategory.contains(keyword);
    }




    @Override
    public Slice<PostResponseDto> filterSearch(String region, String price, List<String> theme, Pageable pageable, UserDetailsImpl userDetails) {
        List<PostResponseDto> content = queryFactory
                .select(new QPostResponseDto(
                        post.id,
                        post.user.nickname,
                        post.user.userImgUrl,
                        post.title,
                        post.content,
                        post.bookmarkCount,
                        post.loveCount,
                        post.regionCategory,
                        post.priceCategory,
                        post.createdAt,
                        post.modifiedAt
                ))
                .from(post)
                .leftJoin(post.user)
                .leftJoin(themeCategory1)
                .on(post.id.eq(themeCategory1.post.id))
                .where(regionFilter(region)
                        .and(priceFilter(price))
                        .and(themeFilter(theme)))
                .groupBy(post.id)
                .orderBy(post.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return new SliceImpl<>(content);
    }

    private BooleanExpression regionFilter(String region) {
        return StringUtils.isNullOrEmpty(region) ? null : post.regionCategory.eq(region);
    }

    private BooleanExpression priceFilter(String price) {
        return StringUtils.isNullOrEmpty(price) ? null : post.priceCategory.eq(price);
    }

    private BooleanExpression themeFilter(List<String> theme) {
        return theme.isEmpty() ? null : themeCategory1.themeCategory.in(theme);
    }
}
