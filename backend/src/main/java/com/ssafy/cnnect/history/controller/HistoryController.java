package com.ssafy.cnnect.history.controller;

import com.ssafy.cnnect.history.dto.HistoryRegisterRequestDto;
import com.ssafy.cnnect.history.dto.HistoryResponseDto;
import com.ssafy.cnnect.history.service.HistoryService;
import com.ssafy.cnnect.voca.service.VocaService;
import com.ssafy.cnnect.result.ResultCode;
import com.ssafy.cnnect.result.ResultResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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

    @Operation(summary = "학습중인 영상 조회")
    @GetMapping(value = "/proceeding")
    public ResponseEntity<ResultResponse> getLearningVideo(){
        List<HistoryResponseDto> vocaList = new ArrayList<>();
        HistoryResponseDto a = HistoryResponseDto.builder()
                .videoName("가가가")
                .videoId("zsBZ_WEIuJ4")
                .historyId(1l)
                .videoLevel(3)
                .lastSentence("lalala")
                .completedSentenceNum(3)
                .totalSentenceNum(10)
                .build();
        vocaList.add(a);
        vocaList.add(a);
        vocaList.add(a);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS , vocaList));
    }

}
