package com.ssafy.cnnect.recommendation.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
@AllArgsConstructor
public class RecommendedNews {
    private Long userId;
    private String RecommendId;
    private String videoId;
    private String videoName;
    private int videoLevel;
    private String videoThumbnail;
    private String videoDate;
    private List<String> sentenceList;
    private List<String> wordList;
    private int categoryId;
}