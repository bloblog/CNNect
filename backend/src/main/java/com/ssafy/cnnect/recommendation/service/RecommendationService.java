package com.ssafy.cnnect.recommendation.service;

import com.ssafy.cnnect.recommendation.dto.RecommendedNews;
import com.ssafy.cnnect.recommendation.repository.RecommendationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class RecommendationService {


    @Autowired
    private RecommendationRepository recommendationRepository;

    public List<RecommendedNews> getRecommendedNews(String userId) {
        return recommendationRepository.findByUserId(userId);
    }


}


