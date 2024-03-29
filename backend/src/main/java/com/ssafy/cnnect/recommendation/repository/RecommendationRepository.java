package com.ssafy.cnnect.recommendation.repository;

import com.ssafy.cnnect.recommendation.dto.RecommendedNews;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecommendationRepository extends JpaRepository<RecommendedNews, Long> {

    List<RecommendedNews> findByUserId(String userId);
}
