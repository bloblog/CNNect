package com.ssafy.cnnect.userHistory.controller;

import com.ssafy.cnnect.result.ResultCode;
import com.ssafy.cnnect.result.ResultResponse;
import com.ssafy.cnnect.userHistory.dto.UserHistoryRegisterRequestDto;
import com.ssafy.cnnect.userHistory.dto.UserHistoryUpdateRequestDto;
import com.ssafy.cnnect.userHistory.service.UserHistoryService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/history")
@RequiredArgsConstructor
@RestController
public class UserHistoryController {
    private final UserHistoryService userHistoryService;
    @Operation(summary = "유저 회원가입 시 관심 영상 등록")
    @PostMapping(value = "")
    public ResponseEntity<ResultResponse> insertHistory(@RequestBody List<UserHistoryRegisterRequestDto> historyList){
        userHistoryService.saveRegistHistory(historyList);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS));
    }

    @Operation(summary = "새로운 영상 학습 시 학습 기록 생성")
    @PostMapping(value = "/{videoId}")
    public ResponseEntity<ResultResponse> createUserHistory(@PathVariable String videoId){
        return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS, userHistoryService.createUserHistory(videoId)));
    }

    @Operation(summary = "학습 기록 조회")
    @GetMapping(value = "/{videoId}")
    public ResponseEntity<ResultResponse> getUserHistory(@PathVariable String videoId){
        return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS, userHistoryService.getUserHistory(videoId)));
    }

    @Operation(summary = "학습 기록의 마지막 학습문장과 마지막 학습시간 수정")
    @PatchMapping(value = "")
    public ResponseEntity<ResultResponse> updateUserSentence(@RequestBody UserHistoryUpdateRequestDto userHistoryUpdateRequestDto){
        userHistoryService.updateUserHistory(userHistoryUpdateRequestDto);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS));
    }

//    @Operation(summary = "학습중인 영상 조회")
//    @GetMapping(value = "/proceeding")
//    public ResponseEntity<ResultResponse> getLearningVideo(){
//        return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS , userHistoryService.getLearningVideo()));
//    }
//
//    @Operation(summary = "학습완료 영상 조회")
//    @GetMapping(value = "/done")
//    public ResponseEntity<ResultResponse> getCompletedVideo(){
//        return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS , userHistoryService.getCompletedVideo()));
//    }

}
