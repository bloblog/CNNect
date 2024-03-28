package com.ssafy.cnnect.voca.controller;

import com.ssafy.cnnect.voca.dto.VocaRequestDto;
import com.ssafy.cnnect.voca.service.VocaService;
import com.ssafy.cnnect.result.ResultCode;
import com.ssafy.cnnect.result.ResultResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/voca")
@RequiredArgsConstructor
@RestController
public class VocaController {
    private final VocaService wordHistoryService;

    @Operation(summary = "단어 기록 불러오기")
    @GetMapping(value = "")
    public ResponseEntity<ResultResponse> getWordList(){
        return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS, wordHistoryService.getWordList()));
    }

    @Operation(summary = "단어장 커스텀 단어 추가")
    @PostMapping(value = "")
    public ResponseEntity<ResultResponse> insertWord(@RequestParam VocaRequestDto word){
        wordHistoryService.saveWord(word);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS));
    }

    @Operation(summary = "단어장에서 단어 삭제하기")
    @DeleteMapping(value = "/{wordId}")
    public ResponseEntity<ResultResponse> deleteWord(@PathVariable Long wordId){
        wordHistoryService.deleteWord(wordId);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS));
    }
}
