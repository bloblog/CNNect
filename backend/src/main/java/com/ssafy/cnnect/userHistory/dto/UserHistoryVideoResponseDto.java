package com.ssafy.cnnect.userHistory.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class UserHistoryVideoResponseDto {
    private Long historyId;
    private String videoName;
    private String videoId;
    private String lastSentence;
    private Long videoLevel;
    private int completedSentenceNum;
    private int totalSentenceNum;

}
