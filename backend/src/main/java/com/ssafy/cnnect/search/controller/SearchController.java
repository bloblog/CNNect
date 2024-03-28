package com.ssafy.cnnect.search.controller;

import com.ssafy.cnnect.result.ResultCode;
import com.ssafy.cnnect.result.ResultResponse;
import com.ssafy.cnnect.search.service.SearchService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/search")
public class SearchController {
    private final SearchService searchService;

    @Operation(summary = "영상 제목 검색")
    @GetMapping(value = "/title")
    public ResponseEntity<ResultResponse> searchByTitle(@RequestParam String keyword){
        return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS, searchService.searchByTitle(keyword)));
    }

    @Operation(summary = "영상 스크립트 검색")
    @GetMapping(value = "/script")
    public ResponseEntity<ResultResponse> searchByScript(@RequestParam String keyword){
        return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS, searchService.searchByScript(keyword)));
    }
}
