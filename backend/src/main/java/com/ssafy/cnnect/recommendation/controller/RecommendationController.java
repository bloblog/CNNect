package com.ssafy.cnnect.recommendation.controller;

import com.ssafy.cnnect.recommendation.dto.RecommendedNews;
import com.ssafy.cnnect.recommendation.service.RecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import com.ssafy.cnnect.oauth.jwt.JwtTokenProvider;

import java.util.List;

@RestController
public class RecommendationController {

    private final RecommendationService recommendationService;
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public RecommendationController(RecommendationService recommendationService, JwtTokenProvider jwtTokenProvider) {
        this.recommendationService = recommendationService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @GetMapping("/recommendations")
    public ResponseEntity<List<RecommendedNews>> getRecommendations(@RequestHeader("Authorization") String authorizationHeader) {
        String userId = extractUserIdFromAuthorizationHeader(authorizationHeader);

        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        List<RecommendedNews> recommendedNews = recommendationService.getRecommendedNews(userId);

        return ResponseEntity.ok(recommendedNews);
    }


    // Authorization 헤더에서 사용자 ID 추출
    private String extractUserIdFromAuthorizationHeader(String authorizationHeader) {
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);

            Authentication authentication = jwtTokenProvider.getUserFromJwt(token);
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            return userDetails.getUsername();
        }
        return null;
    }

}
