package com.ssafy.cnnect.userSentence.controller;

import com.ssafy.cnnect.result.ResultCode;
import com.ssafy.cnnect.result.ResultResponse;
import com.ssafy.cnnect.userSentence.dto.UserSentenceCreateRequestDto;
import com.ssafy.cnnect.userSentence.dto.UserSentenceGetRequestDto;
import com.ssafy.cnnect.userSentence.service.UserSentenceService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/sentence")
@RequiredArgsConstructor
@RestController
public class UserSentenceController {

    private final UserSentenceService userSentenceService;

    @Operation(summary = "발음 평가 시작 시 학습 문장 생성")
    @PostMapping(value = "")
    public ResponseEntity<ResultResponse> createUserSentence(@RequestBody UserSentenceCreateRequestDto userSentenceCreateRequestDto){
        return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS, userSentenceService.createUserSentence(userSentenceCreateRequestDto)));
    }

    @Operation(summary = "학습 문장 조회")
    @GetMapping(value = "")
    public ResponseEntity<ResultResponse> getUserSentence(UserSentenceGetRequestDto userSentenceGetRequestDto){
        return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS, userSentenceService.getUserSentence(userSentenceGetRequestDto)));
    }
}
