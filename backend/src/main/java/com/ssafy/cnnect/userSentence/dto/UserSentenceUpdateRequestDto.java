package com.ssafy.cnnect.userSentence.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class UserSentenceUpdateRequestDto {
    private int sentenceOrder;
    private Double sentenceScore;
    private Long historyId;
}
