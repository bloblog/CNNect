package com.ssafy.cnnect.history.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class HistoryResponseDto {
    private Long historyId;
    private String videoName;
    private String videoId;
    private String lastSentence;
    private int videoLevel;
    private int completedSentenceNum;
    private int totalSentenceNum;

}
