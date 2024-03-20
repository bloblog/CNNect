package com.ssafy.cnnect.history.controller;

import com.ssafy.cnnect.history.dto.HistoryRegisterRequestDto;
import com.ssafy.cnnect.history.service.HistoryService;
import com.ssafy.cnnect.result.ResultCode;
import com.ssafy.cnnect.result.ResultResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/api/history")
@RequiredArgsConstructor
@RestController
public class HistoryController {
    private final HistoryService historyService;
    @Operation(summary = "유저 회원가입 시 관심 영상 등록")
    @PostMapping(value = "")
    public ResponseEntity<ResultResponse> insertHistory(@RequestBody List<HistoryRegisterRequestDto> historyList){
        historyService.saveRegistHistory(historyList);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS));
    }

}
