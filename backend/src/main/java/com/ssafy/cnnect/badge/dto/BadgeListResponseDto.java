package com.ssafy.cnnect.badge.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class BadgeListResponseDto {
    private Long badgeId;
    private String badgeName;
    private String badgeCondition;
    private String badgeCategory;
    private int badgeCount;
    private int badgeScore;
}
