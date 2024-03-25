package com.ssafy.cnnect.video.controller;

import com.ssafy.cnnect.result.ResultCode;
import com.ssafy.cnnect.result.ResultResponse;
import com.ssafy.cnnect.video.service.VideoService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/video")
@RequiredArgsConstructor
@RestController
public class VideoController {
    private final VideoService videoService;

    @Operation(summary = "전체 비디오 조회")
    @GetMapping(value = "/all")
    public ResponseEntity<ResultResponse> getAllVideo(){
       return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS, videoService.getAllVideo()));
    }

    @Operation(summary = "카테고리별 조회")
    @GetMapping(value = "/category")
    public ResponseEntity<ResultResponse> getCatVideo(int categoryId){
        return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS, videoService.getCatVideo(categoryId)));
    }

}
