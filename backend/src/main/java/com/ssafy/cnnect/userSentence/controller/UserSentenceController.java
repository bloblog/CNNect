package com.ssafy.cnnect.userSentence.controller;

import com.ssafy.cnnect.result.ResultCode;
import com.ssafy.cnnect.result.ResultResponse;
import com.ssafy.cnnect.userSentence.service.UserSentenceService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/sentence")
@RequiredArgsConstructor
@RestController
public class UserSentenceController {
    private final UserSentenceService userSentenceervice;
    @Operation(summary = "발음 평가 시작 시 학습 문장 생성")
    @PostMapping(value = "/{historyId}")
    public ResponseEntity<ResultResponse> createUserHistory(@RequestBody UserSentenceRequestDto userSentenceRequestDto){
        return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS, userSentenceervice.createUserSentence(userSentenceRequestDto)));
    }
}
