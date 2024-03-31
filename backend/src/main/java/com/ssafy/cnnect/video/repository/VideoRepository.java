package com.ssafy.cnnect.video.repository;

import com.ssafy.cnnect.video.dto.VideoListResponseDto;
import com.ssafy.cnnect.video.entity.Video;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface VideoRepository extends MongoRepository<Video, String> {
    @Query("{'category_id' : ?0}")
    Page<Video> findByCategoryId(int categoryId, Pageable pageable);

    @Query("{}")
    List<Video> findAllVideo();

    @Query("{category_id : ?0}")
    List<Video> findByCategoryId(Long categoryId);

    @Query("{video_id : ?0}")
    Video findByVideoId(String videoId);

    @Query(value = "{ 'category_id' : ?0 }")
    List<Video> findByCategoryIdOrderByVideoDateDesc(Long categoryId, Pageable pageable);
}
