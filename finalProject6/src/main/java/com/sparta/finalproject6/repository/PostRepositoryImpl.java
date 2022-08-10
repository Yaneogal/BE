package com.sparta.finalproject6.repository;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.core.util.StringUtils;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.finalproject6.dto.responseDto.MyPagePostResponseDto;
import com.sparta.finalproject6.dto.responseDto.PostResponseDto;
import com.sparta.finalproject6.dto.responseDto.QMyPagePostResponseDto;
import com.sparta.finalproject6.dto.responseDto.QPostResponseDto;
import com.sparta.finalproject6.model.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

import static com.sparta.finalproject6.model.QPlace.place;
import static com.sparta.finalproject6.model.QPost.post;
import static com.sparta.finalproject6.model.QThemeCategory.themeCategory1;

@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Override
    public Slice<PostResponseDto> keywordSearch(String keyword, Pageable pageable) {

        List<PostResponseDto> content = queryFactory
                .select(new QPostResponseDto(
                        post.id,
                        post.user.id,
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
                .innerJoin(post.user)
                .leftJoin(themeCategory1)
                .on(post.id.eq(themeCategory1.post.id))
                .leftJoin(place)
                .on(themeCategory1.post.id.eq(place.post.id))
                .where(keywordFinder(keyword))
                .groupBy(post.id)
                .orderBy(getOrderSpecifier(pageable.getSort()).stream().toArray(OrderSpecifier[]::new))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1)
                .fetch();

        boolean hasNext = false;
        if (content.size() > pageable.getPageSize()) {
            content.remove(pageable.getPageSize());
            hasNext = true;
        }

        return new SliceImpl<>(content, pageable, hasNext);
    }

    @Override
    public Slice<PostResponseDto> filterSearch(String region, String price, List<String> theme, Pageable pageable) {

        List<PostResponseDto> content = queryFactory
                .select(new QPostResponseDto(
                        post.id,
                        post.user.id,
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
                .where(regionFilter(region),
                        (priceFilter(price)),
                        (themeFilter(theme)))
                .groupBy(post.id)
                .orderBy(getOrderSpecifier(pageable.getSort()).stream().toArray(OrderSpecifier[]::new))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1)
                .fetch();

        boolean hasNext = false;
        if (content.size() > pageable.getPageSize()) {
            content.remove(pageable.getPageSize());
            hasNext = true;
        }

        return new SliceImpl<>(content, pageable, hasNext);
    }

    @Override
    public Slice<MyPagePostResponseDto> getMyWrittenPosts(Long userId, Pageable pageable) {

        List<MyPagePostResponseDto> content = queryFactory
                .select(new QMyPagePostResponseDto(
                        post.id,
                        post.user.id,
                        post.title,
                        post.regionCategory,
                        post.priceCategory,
                        post.viewCount,
                        post.loveCount,
                        post.commentCount
                ))
                .from(post)
                .innerJoin(post.user)
                .where(post.user.id.eq(userId))
                .orderBy(getOrderSpecifier(pageable.getSort()).stream().toArray(OrderSpecifier[]::new))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1)
                .fetch();

        boolean hasNext = false;
        if (content.size() > pageable.getPageSize()) {
            content.remove(pageable.getPageSize());
            hasNext = true;
        }

        return new SliceImpl<>(content, pageable, hasNext);
    }

    @Override
    public Slice<MyPagePostResponseDto> getMyBookmarkPosts(List<Long> postsId, Pageable pageable) {

        List<MyPagePostResponseDto> content = queryFactory
                .select(new QMyPagePostResponseDto(
                        post.id,
                        post.user.id,
                        post.title,
                        post.regionCategory,
                        post.priceCategory,
                        post.viewCount,
                        post.loveCount,
                        post.commentCount
                ))
                .from(post)
                .innerJoin(post.user)
                .where(post.id.in(postsId))
                .orderBy(getOrderSpecifier(pageable.getSort()).stream().toArray(OrderSpecifier[]::new))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1)
                .fetch();

        boolean hasNext = false;
        if (content.size() > pageable.getPageSize()) {
            content.remove(pageable.getPageSize());
            hasNext = true;
        }

        return new SliceImpl<>(content, pageable, hasNext);
    }

    private BooleanExpression keywordFinder(String keyword) {
        if (StringUtils.isNullOrEmpty(keyword))
            return null;
        return post.title.contains(keyword)
                .or(post.regionCategory.contains(keyword))
                .or(post.priceCategory.contains(keyword))
                .or(themeCategory1.themeCategory.contains(keyword))
                .or(place.place_name.contains(keyword));
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

    private List<OrderSpecifier> getOrderSpecifier(Sort sort) {
        List<OrderSpecifier> orders = new ArrayList<>();

        sort.stream().forEach(order -> {
            Order direction = order.isAscending() ? Order.ASC : Order.DESC;
            String prop = order.getProperty();
            PathBuilder orderByExpression = new PathBuilder(Post.class, "post");
            orders.add(new OrderSpecifier(direction, orderByExpression.get(prop)));
        });

        return orders;
    }
}
