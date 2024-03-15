package com.ssafy.cnnect.badge.controller;

import com.ssafy.cnnect.badge.service.BadgeService;
import com.ssafy.cnnect.result.ResultCode;
import com.ssafy.cnnect.result.ResultResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.transform.Result;

@RequestMapping("/api/badge")
@RequiredArgsConstructor
@RestController
public class BadgeController {
    private final BadgeService badgeService;

    @Operation(summary = "전체 뱃지 조회")
    @GetMapping(value = "/alllist")
    public ResponseEntity<ResultResponse> getAllBadge(){
       return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS, badgeService.getAllBadge()));
    }

    @Operation(summary = "나의 획득 뱃지 조회")
    @GetMapping(value = "/mylist")
    public ResponseEntity<ResultResponse> getMyBadge(){
        return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS, badgeService.getMyBadge()));
    }

    @Operation(summary = "학습완료 후 새로 얻은 뱃지 확인")
    @GetMapping(value = "/newbadge")
    public ResponseEntity<ResultResponse> getNewBadge(){
        return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS, badgeService.getNewBadge()));
    }

}
