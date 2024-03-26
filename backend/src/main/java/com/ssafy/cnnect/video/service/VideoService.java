package com.ssafy.cnnect.video.service;

import com.ssafy.cnnect.video.entity.Video;
import com.ssafy.cnnect.video.repository.VideoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VideoService {

    private final VideoRepository videoRepository;
//    private final CategoryRepository categoryRepository;

    public Page<Video> findByCategory_idAndPage(int categoryId, int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        return videoRepository.findByCategoryId(categoryId, pageable);
    }

    @Transactional
    public List<Video> getAllVideo(){
        List<Video> videolist = videoRepository.findAllVideo();
        return videolist;
    }

    @Transactional
    public List<Video> getCatVideo(Long categoryId){
        List<Video> videolist = videoRepository.findByCategoryId(categoryId);
        return videolist;
    }
}
