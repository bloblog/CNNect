package com.ssafy.cnnect.video.repository;

import com.ssafy.cnnect.video.entity.Video;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface VideoRepository extends MongoRepository<Video, String> {
    @Query("{category_id : ?0}")
    List<Video> findByCategoryId(Long categoryId);
}
