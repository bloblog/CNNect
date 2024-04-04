package com.ssafy.cnnect.recommendation.service;


import com.ssafy.cnnect.recommendation.dto.RecommendedNewsResponseDto;
import com.ssafy.cnnect.recommendation.entity.RecommendedNews;
import com.ssafy.cnnect.recommendation.repository.RecommendationRepository;
import com.ssafy.cnnect.user.entity.User;
import com.ssafy.cnnect.user.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RecommendationService {


    @Autowired
    private RecommendationRepository recommendationRepository;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    public List<RecommendedNewsResponseDto> getRecommendedNews() {
        User user = customUserDetailsService.getUserByAuthentication();
        List<RecommendedNews> recommendedNewsList = recommendationRepository.findAllByUser(user);
        List<RecommendedNewsResponseDto> recommendedNewsResponseDtoList = new ArrayList<>();
        for(RecommendedNews r : recommendedNewsList){
            recommendedNewsResponseDtoList.add(RecommendedNewsResponseDto.builder()
                            .recommendedId(r.getRecommendedId())
                            .videoId(r.getVideoId())
                            .videoLevel(r.getVideoLevel())
                            .videoName(r.getVideoName())
                            .videoThumbnail(r.getVideoThumbnail())
                    .build());
        }
        return recommendedNewsResponseDtoList;
    }


}


