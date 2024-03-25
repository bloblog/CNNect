package com.ssafy.cnnect.video.controller;

import com.ssafy.cnnect.result.ResultCode;
import com.ssafy.cnnect.result.ResultResponse;
import com.ssafy.cnnect.video.entity.Video;
import com.ssafy.cnnect.video.repository.VideoRepository;
import com.ssafy.cnnect.video.service.VideoService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/video")
@RequiredArgsConstructor
@RestController
public class VideoController {
    private final VideoService videoService;
    private final VideoRepository videoRepository;

    @Operation(summary = "전체 비디오 조회")
    @GetMapping(value = "/all")
    public ResponseEntity<ResultResponse> getAllVideo(){
       return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS, videoService.getAllVideo()));
    }

    @Operation(summary = "카테고리별 조회")
    @GetMapping(value = "/category/{categoryId}")
    public ResponseEntity<ResultResponse> getCatVideo(@PathVariable Long categoryId){
        return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS, videoService.getCatVideo(categoryId)));
    }

//    @Operation(summary = "카테고리별 비디오 페이징")
//    @GetMapping(value = "/category")
//    public Page<Video> getVideos(
//            @RequestParam(defaultValue = "0") int page,
//            @RequestParam(defaultValue = "10") int size) {
//        return videoRepository.findByCategoryId(PageRequest.of(page, size));
//    }
}
