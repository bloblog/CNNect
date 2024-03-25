package com.ssafy.cnnect.video.repository;

import com.ssafy.cnnect.video.dto.VideoListResponseDto;
import com.ssafy.cnnect.video.entity.Video;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface VideoRepository extends MongoRepository<Video, String> {
    @Query("{}")
    List<Video> findAllVideo();
    @Query("{category_id : ?0}")
    List<Video> findByCategoryId(int categoryId);
    @Query("{video_id : ?0}")
    Video findByVideoId(String videoId);
}
