package com.ssafy.cnnect.userHistory.controller;

import com.ssafy.cnnect.userHistory.dto.UserHistoryRegisterRequestDto;
import com.ssafy.cnnect.userHistory.dto.UserHistoryRequestDto;
import com.ssafy.cnnect.userHistory.service.UserHistoryService;
import com.ssafy.cnnect.result.ResultCode;
import com.ssafy.cnnect.result.ResultResponse;
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
        userHistoryService.createUserHistory(videoId);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS));
    }
}
