package com.ssafy.cnnect.recommendation.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
@AllArgsConstructor
public class RecommendedNewsResponseDto {
    private Long recommendedId;
    private String videoId;
    private String videoName;
    private int videoLevel;
    private String videoThumbnail;
}