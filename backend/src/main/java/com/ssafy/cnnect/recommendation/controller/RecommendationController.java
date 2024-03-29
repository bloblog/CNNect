package com.ssafy.cnnect.recommendation.controller;



import com.ssafy.cnnect.recommendation.dto.RecommendedNewsResponseDto;
import com.ssafy.cnnect.recommendation.entity.RecommendedNews;
import com.ssafy.cnnect.recommendation.service.RecommendationService;
import com.ssafy.cnnect.result.ResultCode;
import com.ssafy.cnnect.result.ResultResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RecommendationController {

    private final RecommendationService recommendationService;

    @Autowired
    public RecommendationController(RecommendationService recommendationService) {
        this.recommendationService = recommendationService;
    }

    @GetMapping("/recommendations")
    public ResponseEntity<ResultResponse> getRecommendations() {
        List<RecommendedNewsResponseDto> recommendedNews = recommendationService.getRecommendedNews();
        return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS, recommendedNews));
    }


}

