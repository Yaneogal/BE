package com.sparta.finalproject6.repository;


import com.sparta.finalproject6.model.Place;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlaceRepository extends JpaRepository<Place,Long> {
    void deleteAllByPostId(Long postId);
    List<Place> findAllByPostId(Long postId);
}
