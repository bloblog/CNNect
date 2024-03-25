package com.ssafy.cnnect.video.service;

import com.ssafy.cnnect.video.entity.Video;
import com.ssafy.cnnect.video.repository.VideoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VideoService {

    private final VideoRepository videoRepository;
//    private final CategoryRepository categoryRepository;

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
