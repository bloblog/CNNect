package com.ssafy.cnnect.userSentence.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class UserSentenceResponseDto {
    private int sentenceOrder;
    private String sentenceContent;
    private Double sentenceScore;
    private Long userHistoryId;
}