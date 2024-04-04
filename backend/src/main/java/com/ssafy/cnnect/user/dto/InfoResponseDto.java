package com.ssafy.cnnect.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class InfoResponseDto {
    private Long userId;
    private String userNickname;
    private Long userBadgeCount;
    private Long userVideoCount;
    private int userLevel;
}
