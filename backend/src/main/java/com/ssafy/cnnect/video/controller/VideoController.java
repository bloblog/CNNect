package com.ssafy.cnnect.video.controller;

import com.ssafy.cnnect.result.ResultCode;
import com.ssafy.cnnect.result.ResultResponse;
import com.ssafy.cnnect.video.entity.Video;
import com.ssafy.cnnect.video.service.VideoService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/video")
@RequiredArgsConstructor
@RestController
public class VideoController {
    private final VideoService videoService;

    @GetMapping("/entities")
    @Operation(summary = "비디오 페이징 처리")
    public ResponseEntity<Page<Video>> getEntities(
            @RequestParam(defaultValue = "") int categoryId,
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize
    ) {
        Page<Video> entities = videoService.findByCategory_idAndPage(categoryId, pageNo, pageSize);
        return ResponseEntity.ok(entities);
    }
    
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

    @Operation(summary = "학습할 비디오 조회")
    @GetMapping(value = "/study/{videoId}")
    public ResponseEntity<ResultResponse> getStudyVideo(@PathVariable String videoId){
        return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS, videoService.getStudyVideo(videoId)));
    }
}